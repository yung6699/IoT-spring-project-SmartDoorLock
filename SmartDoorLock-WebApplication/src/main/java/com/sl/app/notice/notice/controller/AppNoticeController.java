package com.sl.app.notice.notice.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sl.app.notice.notice.service.AppNoticeService;
import com.sl.app.notice.notice.vo.AppNoticeVO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;



@Controller
public class AppNoticeController {
	private static final Logger logger = LoggerFactory.getLogger(AppNoticeController.class);

	@Autowired
	AppNoticeService service;
	
	@RequestMapping(value = "/app/notice/notice", method = RequestMethod.POST)
	@ResponseBody
	public JSONArray selectUserNotice(AppNoticeVO vo,Locale locale){
		return service.selectUserNotice(vo);	
	}
	@RequestMapping(value = "/app/notice/response",method = RequestMethod.POST)
	@ResponseBody
	public JSONObject responseUserNotice(AppNoticeVO vo,Locale locale){
		return service.responseUserNotice(vo);
	}
}
