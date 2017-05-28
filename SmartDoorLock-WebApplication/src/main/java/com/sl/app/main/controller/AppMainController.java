package com.sl.app.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sl.app.main.service.AppMainService;
import com.sl.app.main.vo.AppMainVO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class AppMainController {

	@Autowired
	AppMainService service;
	
	@RequestMapping(value = "/app/main", method = RequestMethod.POST)
	@ResponseBody
	public JSONArray selectListKeys(AppMainVO vo) {
		System.out.println(JSONObject.fromObject(vo));
		
		return service.selectListKeys(vo);
	}
	@RequestMapping(value = "/app/notice/count", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject countNotice(AppMainVO vo){
		return service.countNotice(vo);
	}
//	
	@RequestMapping(value = "/app/logout", method = RequestMethod.POST)
	@ResponseBody
	public void logout(AppMainVO vo){
		service.logout(vo);
	}

	
}
