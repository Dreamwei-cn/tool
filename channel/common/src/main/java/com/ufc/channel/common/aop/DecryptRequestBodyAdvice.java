package com.ufc.channel.common.aop;

import com.alibaba.fastjson.JSONObject;
import com.ufc.channel.common.annotations.ChannelBefore;
import com.ufc.channel.common.domain.ChannelApp;
import com.ufc.channel.common.service.ChannelAppInterfaceLogService;
import com.ufc.channel.common.service.ChannelAppService;
import com.ufc.channel.common.sm2.SM2Utils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;


@ControllerAdvice
@Slf4j
public class DecryptRequestBodyAdvice implements RequestBodyAdvice {


    @Autowired
    private ChannelAppService channelAppServiceImpl;

    @Autowired
    private ChannelAppInterfaceLogService channelAppInterfaceLogServiceImpl;

    /**
     * 方法上有DecryptionAnnotation注解的，进入此拦截器
     * @param methodParameter 方法参数对象
     * @param targetType 参数的类型
     * @param converterType 消息转换器
     * @return true，进入，false，跳过
     */
    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return methodParameter.hasMethodAnnotation(ChannelBefore.class);
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        return inputMessage;
    }

    /**
     * 转换之后，执行此方法，解密，赋值
     * @param body spring解析完的参数
     * @param inputMessage 输入参数
     * @param parameter 参数对象
     * @param targetType 参数类型
     * @param converterType 消息转换类型
     * @return 真实的参数
     */
    @SneakyThrows
    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {

        ChannelBefore channelBefore = parameter.getMethodAnnotation(ChannelBefore.class);
        // 获取request
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        HttpServletRequest request = servletRequestAttributes.getRequest();
        // 非 post请求返回原请求体
        if (!"POST".equals(request.getMethod())){
            return body;
        }
        InputStream inputStream = request.getInputStream();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        IOUtils.copy(inputStream, byteArrayOutputStream);
        String result = new String(byteArrayOutputStream.toByteArray());
        log.info(channelBefore.name()+ ":" + result);
        JSONObject jsonObject = JSONObject.parseObject(result);
        String appKey = jsonObject.getString("appkey");
        ChannelApp app = channelAppServiceImpl.queryByAppKey(appKey);
        String content = jsonObject.getString("content");

        String data = SM2Utils.decrypt(app.getPriKey(), content);
        channelAppInterfaceLogServiceImpl.addLog(channelBefore.value(), app.getId(), result, data);
        return  JSONObject.parseObject(data);
    }

    /**
     * 如果body为空，转为空对象
     * @param body spring解析完的参数
     * @param inputMessage 输入参数
     * @param parameter 参数对象
     * @param targetType 参数类型
     * @param converterType 消息转换类型
     * @return 真实的参数
     */
    @SneakyThrows
    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        String typeName = targetType.getTypeName();
        Class<?> bodyClass = Class.forName(typeName);
        return bodyClass.newInstance();
    }
}
