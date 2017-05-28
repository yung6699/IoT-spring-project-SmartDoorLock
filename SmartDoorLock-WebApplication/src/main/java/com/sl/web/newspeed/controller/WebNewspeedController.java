package com.sl.web.newspeed.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sl.app.main.vo.AppMainVO;
import com.sl.web.newspeed.service.WebNewspeedService;
import com.sl.web.newspeed.vo.WebNewspeedVO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


@Controller
public class WebNewspeedController {
	private static final Logger logger = LoggerFactory.getLogger(WebNewspeedController.class);

	@Autowired
	private WebNewspeedService service;
	
	@RequestMapping(value = "/{email}/newspeed", method = RequestMethod.POST)
	@ResponseBody
	public JSONArray selectUserNotice(@PathVariable String email , WebNewspeedVO vo,Locale locale){
		vo.setEmail(email);
		return service.selectUserNotice(vo);	
	}
		
	@RequestMapping(value = "/{email}/newspeed/key/response",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject responseUserNotice(WebNewspeedVO vo,Locale locale){
		return service.responseUserNotice(vo);
	}
}
