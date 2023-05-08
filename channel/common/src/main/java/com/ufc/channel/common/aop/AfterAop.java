package com.ufc.channel.common.aop;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mysql.cj.util.StringUtils;
import com.ufc.channel.common.annotations.ChannelAfter;
import com.ufc.channel.common.bean.ResponseResult;
import com.ufc.channel.common.domain.ChannelApp;
import com.ufc.channel.common.domain.ChannelAppInterface;
import com.ufc.channel.common.event.OnFailureEvent;
import com.ufc.channel.common.event.OnSuccessEvent;
import com.ufc.channel.common.service.ChannelAppBackLogService;
import com.ufc.channel.common.service.ChannelAppInterfaceService;
import com.ufc.channel.common.service.ChannelAppService;
import com.ufc.channel.common.sm2.SM2Utils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.concurrent.TimeUnit;


@Aspect
@Component
@Slf4j
public class AfterAop {

    @Autowired
    private ChannelAppService channelAppServiceImpl;

    @Autowired
    private ChannelAppInterfaceService channelAppInterfaceService;

    @Autowired
    private ChannelAppBackLogService channelAppBackLogServiceImpl;


    @Autowired
    private ApplicationContext applicationContext;


    @Pointcut("@annotation(com.ufc.channel.common.annotations.ChannelAfter)")
    public void pointAfter(){

    }

    @AfterReturning(pointcut = "pointAfter()&&@annotation(after)", returning = "result")
    @ResponseBody
    public void doResultAfter(JoinPoint joinPoint, ResponseResult result, ChannelAfter after){
        JSONObject jsonObject = (JSONObject) result.getData();
        String appKey = jsonObject.getString("appKey");
        ChannelApp app =  channelAppServiceImpl.queryByAppKey(appKey);
        QueryWrapper<ChannelAppInterface> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("appId", app.getId()).eq("apiUrl", after.value());
        ChannelAppInterface appInterface = channelAppInterfaceService.getOne(queryWrapper);
        //  没回调 直接结束
        if (appInterface == null || StringUtils.isNullOrEmpty(appInterface.getBackurl())){
            return;
        }
        try {
            String encryData = SM2Utils.encrypt(app.getPulKey(), jsonObject.toJSONString());
            JSONObject json = new JSONObject();
            json.put("code", result.getCode());
            json.put("message", result.getMessage());
            json.put("data", encryData);
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .build();
            //MediaType  设置Content-Type 标头中包含的媒体类型值
            RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), json.toJSONString());

            Request request = new Request.Builder()
                    .url(appInterface.getBackurl())//请求的url
                    .post(requestBody)
                    .build();
            //创建/Call
            Call call = okHttpClient.newCall(request);
            //加入队列 异步操作
            call.enqueue(new Callback() {
                //请求错误回调方法
                @Override
                public void onFailure(Call call, IOException e) {
                    // 发布失败事件
                    applicationContext.publishEvent(new OnFailureEvent(result));
                    System.out.println("连接失败");
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    log.info("回调接口响应:" + response.body().toString());
                    channelAppBackLogServiceImpl.addBackLog(app.getId(), appInterface.getId(), json.toJSONString(), jsonObject.toJSONString(), jsonObject.getString("orderNo"), response.body().string());
                    // 发布成功事件
                    applicationContext.publishEvent(new OnSuccessEvent(result, response));
                }
            });


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
