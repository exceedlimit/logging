package com.sk.asset.logging.logger;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class LoggerFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        MultiReadHttpServletRequest multiReadRequest = new MultiReadHttpServletRequest((HttpServletRequest) request);

        chain.doFilter(multiReadRequest, response);
    }
}
