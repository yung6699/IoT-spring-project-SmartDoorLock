package com.sl.system.common.context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.sl.web.common.context.ContextKey;

import net.sf.json.JSONObject;

public class SystemContextInterceptor extends HandlerInterceptorAdapter {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		Object obj = request.getSession().getAttribute(ContextKey.LOGIN_MEMBER);
		try{
 			logger.info(request.getRequestURI());
			String[] split = request.getRequestURI().split("/");
			logger.info(split.length+"");
/*
 			for(int i=0; i<split.length;i++){
				logger.info(split[i]+": "+i+"번째");
			}
*/
			
			if(split.length>2){
				if(obj==null){
					request.getSession().removeAttribute(ContextKey.LOGIN_MEMBER);
					response.sendRedirect("/");
					return false;
				}else{
					JSONObject sessionObj = (JSONObject) obj;	
					if(split[1].equals(sessionObj.getString("EMAIL"))){
						logger.info("filter OK ");
					}else if(split[1].equals("favicon")){
						
					}else{
						request.getSession().removeAttribute(ContextKey.LOGIN_MEMBER);
						response.sendRedirect("/");
						return false;
					}
				}
			}
			
		}catch(Exception e){
			response.sendRedirect("/");
			e.printStackTrace();
		}
		return super.preHandle(request, response, handler);
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		logger.info("안녕하세요 postHandle 입니다.",this.getClass());
		super.postHandle(request, response, handler, modelAndView);
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		logger.info("안녕하세요 afterCompletion 입니다.",this.getClass());
		super.afterCompletion(request, response, handler, ex);
	}
	
	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		logger.info("안녕하세요 afterConcurrentHandlingStarted 입니다.",this.getClass());
		super.afterConcurrentHandlingStarted(request, response, handler);
	}
}
