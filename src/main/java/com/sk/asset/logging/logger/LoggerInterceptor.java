package com.sk.asset.logging.logger;

import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StreamUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;


public class LoggerInterceptor implements HandlerInterceptor {
	protected Log log = LogFactory.getLog(LoggerInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("====================     START     ====================");
			log.debug(" Request URI \t:  " + request.getRequestURI());
			if(request.getHeaderNames().hasMoreElements())
				log.debug(combineHeaders(request));

			if(request.getParameterNames().hasMoreElements())
				log.debug(combineParameters(request));

			if(request.getContentLength()!=-1)
				log.debug(combineBody(request));

		}
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("====================      END      ====================");
		}
	}

	// request Parameters를 읽어서 String으로 변환
	private String combineParameters(HttpServletRequest request) {
		StringBuilder parametersStringBuilder = new StringBuilder();
		parametersStringBuilder.append("Request Parameters = {");
		request.getParameterNames().asIterator().forEachRemaining(paramName ->
				parametersStringBuilder.append(paramName).append(":").append(request.getParameter(paramName)).append(", "));
		parametersStringBuilder.deleteCharAt(parametersStringBuilder.lastIndexOf(","));
		parametersStringBuilder.append("}");
		return parametersStringBuilder.toString();
	}
	// request Header를 읽어서 String으로 변환
	private String combineHeaders(HttpServletRequest request) {
		StringBuilder headersStringBuilder = new StringBuilder();
		headersStringBuilder.append("Request Headers = {");
		request.getHeaderNames().asIterator().forEachRemaining(headerName ->
				headersStringBuilder.append(headerName).append(":").append(request.getHeader(headerName)).append(", "));
		headersStringBuilder.deleteCharAt(headersStringBuilder.lastIndexOf(","));
		headersStringBuilder.append("}");
		return headersStringBuilder.toString();
	}
	// request Body 읽어서 String으로 변환
	private String combineBody(HttpServletRequest request) throws IOException {
		StringBuilder bodyStringBuilder = new StringBuilder();
		InputStream inputStream = request.getInputStream();
		String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
		bodyStringBuilder.append("Request Body = ").append(messageBody);
		return bodyStringBuilder.toString();
	}
}