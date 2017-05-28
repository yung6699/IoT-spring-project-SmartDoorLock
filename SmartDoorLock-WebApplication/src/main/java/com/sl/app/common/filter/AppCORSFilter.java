package com.sl.app.common.filter;

import java.io.IOException; 
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AppCORSFilter implements Filter {
//앱과 연결을 위한 필터! Access-Controll-* 에 response 에다가 헤더값을 넣어주어야만 합니다. 
	
	Logger logger =  (Logger) LoggerFactory.getLogger(this.getClass());
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) res;
		HttpServletRequest request = (HttpServletRequest) req;
		// TODO Auto-generated method stub
		
		@SuppressWarnings("unchecked")
		Enumeration<String> enumeration = request.getHeaderNames();
		while(enumeration.hasMoreElements()){
			String name = (String) enumeration.nextElement();
			logger.info(name +" @@ "+request.getHeader(name));
		}		
		
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
		response.setHeader("Access-Control-Allow-Origin", "*"); //모든 도메인 이름에 대하여 허용. 헤더를 넣는다.
		chain.doFilter(req, res);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
