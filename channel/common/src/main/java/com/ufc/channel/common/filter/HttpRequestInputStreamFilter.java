package com.ufc.channel.common.filter;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;


@Component
@Order(HIGHEST_PRECEDENCE + 1)
public class HttpRequestInputStreamFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        // 转换为可以多次获取流的request
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        InputStreamHttpServletRequestWrapper inputStreamHttpServletRequestWrapper = new InputStreamHttpServletRequestWrapper(httpServletRequest);

        // 放行
        filterChain.doFilter(inputStreamHttpServletRequestWrapper, servletResponse);
    }
}
