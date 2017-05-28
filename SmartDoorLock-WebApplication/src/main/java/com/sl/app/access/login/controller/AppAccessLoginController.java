package com.sl.app.access.login.controller;

import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.resource.DefaultServletHttpRequestHandler;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.sl.app.access.login.service.AppAccessLoginService;
import com.sl.app.access.login.vo.AppAccessLoginVO;
import com.sl.app.common.context.AppContextKey;
import com.sl.system.common.util.ContextUtil;
import com.sl.system.session.member.SessionMember;
import com.sl.web.common.context.ContextKey;

import net.sf.json.JSONObject;

@Controller
public class AppAccessLoginController {
	private static final Logger logger = LoggerFactory.getLogger(AppAccessLoginController.class);
	
	
	InternalResourceViewResolver f;
	DefaultServletHttpRequestHandler d;
	
	@Autowired
	private AppAccessLoginService service;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private SessionMember sessionMember;
	
	
	@RequestMapping(value = "/app/access/login", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject selectOneMember(AppAccessLoginVO vo,Locale locale){
		JSONObject obj = new JSONObject();
		if(service.selectOneMember(vo)==1){
			JSONObject sessionObj = sessionMember.getSessionMember(JSONObject.fromObject(vo));
			session.setAttribute(ContextKey.LOGIN_MEMBER, sessionObj);
			obj.put(AppContextKey.AJAX_RESULT, AppContextKey.AJAX_SUCCESS);
			obj.put(AppContextKey.LOGIN_MEMBER, sessionObj);
			obj.put("AUTOLOGIN", vo.getAutologin());
			return obj;
		}else{
			obj.put(AppContextKey.AJAX_RESULT, AppContextKey.AJAX_FAIL);
			obj.put(AppContextKey.RESULT_MSG, AppContextKey.MSG_LOGIN_FAIL);
			return obj;
		}
	}
}
