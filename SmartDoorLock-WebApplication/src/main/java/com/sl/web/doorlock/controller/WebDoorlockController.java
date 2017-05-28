package com.sl.web.doorlock.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sl.system.log.setter.SystemLogSetterVO;
import com.sl.web.doorlock.service.WebDoorlockService;
import com.sl.web.doorlock.vo.WebDoorlockVO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class WebDoorlockController {
	
	@Autowired
	private WebDoorlockService service;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(value="/{email}/doorlock",method=RequestMethod.GET)
	public String doorlock(@PathVariable(value="email") String email, Model model, Locale locale, WebDoorlockVO vo){
		return "/web/doorlock/doorlock";
	}
	
	@RequestMapping(value="/{email}/doorlock/list",method=RequestMethod.POST)
	@ResponseBody
	public JSONArray doorlockList(WebDoorlockVO vo, Locale locale){
		return JSONArray.fromObject(service.doorlockList(vo));
	}

	@RequestMapping(value="/{email}/doorlock/detail",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject doorlockDetail(WebDoorlockVO vo, Locale locale){
		return service.doorlockDetail(vo);
	}

	@RequestMapping(value="/{email}/doorlock/create/check",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject doorlockCreateCheck(WebDoorlockVO vo, Locale locale){
		return service.doorlockCreateCheck(vo);
	}
	
	@RequestMapping(value="/{email}/doorlock/create",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject doorlockCreate(WebDoorlockVO vo, Locale locale){
		return service.doorlockCreate(vo);
	}

	@RequestMapping(value="/{email}/doorlock/update",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject doorlockUpdate(WebDoorlockVO vo, Locale locale){
		return service.doorlockUpdate(vo);
	}
	
	@RequestMapping(value="/{email}/doorlock/delete",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject deleteDoorlock(WebDoorlockVO vo, Locale locale){
		return service.doorlockDelete(vo);
	}
	
	@RequestMapping(value="/{email}/doorlock/logs",method=RequestMethod.POST)
	@ResponseBody
	public JSONArray selectListDoorlocklogs(SystemLogSetterVO vo, Locale locale){
		return service.selectListDoorlocklogs(vo);
	}
}
