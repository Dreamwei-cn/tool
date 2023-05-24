package com.ufc.channel.common.aop;

import com.ufc.channel.common.sm2.SM2Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.NoSuchElementException;

@Slf4j
public class BeforeRequestWrapper extends HttpServletRequestWrapper {

    String priKey = "3690655E33D5EA3D9A4AE1A1ADD766FDEA045CDEAA43A9206FB8C430CEFE0D94";

    Logger filterLog = LoggerFactory.getLogger("filter");

    private String tempBody;

    public BeforeRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        testRean(request);
        String context = getBodyString(request);
//        String result = SM2Utils.decrypt(priKey, context);
        String result = context;
        filterLog.info("链接为：{},解密后报文：{}", request.getRequestURI(), result);
        tempBody = result;
    }


    public void testRean(HttpServletRequest request){

        try {
            InputStream inputStream = request.getInputStream();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            IOUtils.copy(inputStream, byteArrayOutputStream);
            String body = new String(byteArrayOutputStream.toByteArray());
            System.out.println(body);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ServletInputStream getInputStream() {
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(tempBody.getBytes(Charset.forName("UTF-8")));
        return new ServletInputStream() {
            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public int readLine(byte[] b, int off, int len) throws IOException {
                return super.readLine(b, off, len);
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() {
                return byteArrayInputStream.read();
            }
        };
    }

    @Override
    public BufferedReader getReader() {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }

    public String getBody() {
        return this.tempBody;
    }

    public void setBody(String body) {
        this.tempBody = body;
    }


    /**
     * 获取请求Body
     *
     * @param request request
     * @return String
     */
    public String getBodyString(final ServletRequest request) {
        try {
            return inputStream2String(request.getInputStream());
        } catch (IOException e) {
            log.error("", e);
            throw new RuntimeException(e);
        }
    }


    /**
     * 将inputStream里的数据读取出来并转换成字符串
     *
     * @param inputStream inputStream
     * @return String
     */
    private String inputStream2String(InputStream inputStream) {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new InputStreamReader(inputStream, Charset.defaultCharset()));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            log.error("", e);
            throw new RuntimeException(e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    log.error("", e);
                }
            }
        }

        return sb.toString();
    }

    @Override
    public String getHeader(String name) {
        String headerValue = super.getHeader(name);
        if ("content-type".equals(name.toLowerCase())) {
            return "application/json";
        }
        return headerValue;
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        if (null != name && name.toLowerCase().equals("content-type")) {
            return new Enumeration<String>() {
                private boolean hasGetted = false;

                @Override
                public String nextElement() {
                    if (hasGetted) {
                        throw new NoSuchElementException();
                    } else {
                        hasGetted = true;
                        return "application/json;charset=utf-8";
                    }
                }

                @Override
                public boolean hasMoreElements() {
                    return !hasGetted;
                }
            };
        }
        return super.getHeaders(name);
    }

}
