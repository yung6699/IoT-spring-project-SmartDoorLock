package com.sl.web.main.controller;

import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sl.web.common.context.ContextKey;
import com.sl.web.home.vo.WebHomeMessageVO;

/**
 * Handles requests for the application home page.
 */
@Controller
public class WebMainController {

	/**
	 * Simply selects the home view to render by returning its name.
	 * @throws MessagingException 
	 */	
	@Autowired
	private HttpSession session;
	private final Logger logger = LoggerFactory.getLogger(WebMainController.class);
	
	
	@RequestMapping(value = "/basic", method = RequestMethod.GET)
	public String basic(WebHomeMessageVO contactUs, Locale locale, Model model) {
		return "/web/basic";
	}

	//홈페이지 보이기
	@RequestMapping(value = "/{email}", method = RequestMethod.GET)
	public String main(@PathVariable(value = "email") String email, Locale locale, Model model) {
		return "/web/main/main";
	}
	//홈페이지 보이기
	@RequestMapping(value = "/{email}/logout", method = RequestMethod.GET)
	public String logout(@PathVariable(value = "email") String email, Locale locale, Model model) {
		logger.info("["+email+"]:로그아웃 하였습니다.",locale);
		session.removeAttribute(ContextKey.LOGIN_MEMBER);
		return "redirect:/";
	}
		
}

