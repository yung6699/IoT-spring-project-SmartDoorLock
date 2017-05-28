package com.sl.app.access.find.account.controller;

import java.util.Locale;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sl.app.access.find.account.service.AppAccessFindAccountService;
import com.sl.app.access.find.account.vo.AppAccessFindAccountVO;

import net.sf.json.JSONObject;

@Controller
public class AppAccessFindAccountController {

	@Autowired
	AppAccessFindAccountService service;
	
	@RequestMapping(value = "/app/access/find/account", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject updateFindMember(AppAccessFindAccountVO vo,Locale locale, Model model) throws MessagingException {
		return service.updateFindMember(vo);
	}
	
}