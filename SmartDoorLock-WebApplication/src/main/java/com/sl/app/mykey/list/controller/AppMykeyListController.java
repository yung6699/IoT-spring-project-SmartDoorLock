package com.sl.app.mykey.list.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sl.app.mykey.list.service.AppMykeyListService;
import com.sl.app.mykey.list.vo.AppMykeyListVO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class AppMykeyListController {
	private static final Logger logger = LoggerFactory.getLogger(AppMykeyListController.class);

	@Autowired
	private AppMykeyListService service;
	
	/*전체 MyKey 리스트를 보여주는 메소드*/
	@RequestMapping(value = "/app/mykey/list", method = RequestMethod.POST)
	@ResponseBody
	public JSONArray selectMyKeyList(AppMykeyListVO vo,Locale locale){
		logger.info("/app/mykey/list.", locale);
		return service.selectMyKeyList(vo);
	}
	
	@RequestMapping(value = "/app/mykey/name/update", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject updateMyKeyName(AppMykeyListVO vo,Locale locale){
		logger.info("/app/mykey/name/update.", locale);
		logger.info("serial_no : " + vo.getSerial_no());
		logger.info("member_no : " + vo.getMember_id());
		logger.info("key_name : " + vo.getKey_name());
		return service.updateMyKeyName(vo);
	}
	
	@RequestMapping(value = "/app/mykey/delete", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject deleteMyKey(AppMykeyListVO vo,Locale locale){
		logger.info("/app/mykey/delete.", locale);
		return service.deleteMyKey(vo);
	}
	
}
