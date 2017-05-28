package com.sl.app.doorlock.register.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sl.app.doorlock.register.service.AppDoorlockRegisterService;
import com.sl.app.doorlock.register.vo.AppDoorlockRegisterVO;

import net.sf.json.JSONObject;

@Controller
public class AppDoorlockRegisterController {
	
	private static final Logger logger = LoggerFactory.getLogger(AppDoorlockRegisterController.class);

	@Autowired
	private AppDoorlockRegisterService service;
	
	@RequestMapping(value = "/app/doorlock/register/check", method = RequestMethod.POST)
	@ResponseBody
	JSONObject selectOneCheck(AppDoorlockRegisterVO vo){
		logger.info(JSONObject.fromObject(vo).toString());
		return service.selectOneCheck(vo);
	}
	
	@RequestMapping(value = "/app/doorlock/register/insert", method = RequestMethod.POST)
	@ResponseBody
	JSONObject selectOneInsert(AppDoorlockRegisterVO vo){
		logger.info(JSONObject.fromObject(vo).toString());
		return service.selectOneInsert(vo);
	}
	
}
