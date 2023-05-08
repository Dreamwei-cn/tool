package com.ufc.channel.common.aop;


import com.ufc.channel.common.annotations.ChannelBefore;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

//@Aspect
//@Component
//@Order(-1)
public class BeforeAop
{

    @Pointcut("@annotation(com.ufc.channel.common.annotations.ChannelBefore)")
    public void pointBefore(){

    }

    @Before(value = "pointBefore()&&@annotation(before)")
    @ResponseBody
    public void doResultAfter(JoinPoint joinPoint, ChannelBefore before){

        RequestAttributes ra= RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra=(ServletRequestAttributes)ra;
        HttpServletRequest request = sra.getRequest();

        System.out.println("method:" +  request.getMethod());
        if ("GET".equals(request.getMethod())){
            return;
        }
        BeforeRequestWrapper req = null;
        try {
            req = new BeforeRequestWrapper(request);
//            InputStream inputStream = request.getInputStream();
//            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//            IOUtils.copy(inputStream, byteArrayOutputStream);
//            String body = new String(byteArrayOutputStream.toByteArray());
//            System.out.println(body);
            String body = req.getBody();  //获取body
            /*修改逻辑*/
            System.out.printf(body);
//            body = StringUtils.replace(body, "\"operator1\"", "\"operator\"");
            req.setBody(body);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
