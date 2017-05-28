package com.sl.app.mydoorlock.key.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sl.app.mydoorlock.key.service.AppMyDoorLockService;
import com.sl.app.mydoorlock.key.vo.AppMyDoorLockVO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class AppMyDoorLockController {
	
	private static final Logger logger = LoggerFactory.getLogger(AppMyDoorLockController.class);

	@Autowired
	AppMyDoorLockService service;
	
    /*도어락 리스트 데이터를 가져오기 위한 메소드*/
	@RequestMapping(value = "/app/mydoorlock/list", method = RequestMethod.POST)
	@ResponseBody
	public JSONArray selectDoorLockList(AppMyDoorLockVO vo, Locale locale){
		logger.info("/app/mydoorlock/key.", locale);
		return service.selectDoorLockList(vo);
	}
	
	
	/*도어락에 등록된 키 리스트를 보여주는 메소드*/
	@RequestMapping(value = "/app/mydoorlock/key", method = RequestMethod.POST)
	@ResponseBody
	public JSONArray selectKeyList(AppMyDoorLockVO vo, Locale locale){
		logger.info("/app/mydoorlock/key.", locale);
		return service.selectKeyList(vo);
	}
	
	
	/*도어락에 키 등록시  멤버를 검색한다.*/
	@RequestMapping(value = "/app/mydoorlock/key/search/member", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject selectSearchMember(AppMyDoorLockVO vo,Locale locale){;
		return service.selectSearchMember(vo);
	}
	
			
	/* 도어락에 키를 등록하기 위한 메소드
	 * 등록 받을시 member_email도 받아야 한다. DB에 안들어가도
	 * 로그 등록시 반드시 필요하다.*/
	@RequestMapping(value = "/app/mydoorlock/key/register", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject selectOneInsert(AppMyDoorLockVO vo,Locale locale){
		return service.selectOneInsert(vo);
	}
	
	
	/* 도어락에 등록된 키를 삭제하기 위한 메소드
	 * 삭제 할시 member_email도 받아야 한다. DB에 안들어가도
	 * 로그 등록시 반드시 필요하다.*/
	@RequestMapping(value = "/app/mydoorlock/key/delete", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject deleteKey(AppMyDoorLockVO vo,Locale locale){
		return service.deleteKey(vo);
	}
	
	
	/* 도어락 이름을 변경하는 메소드*/
	@RequestMapping(value = "/app/mydoorlock/name/update", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject updateDoorLockName(AppMyDoorLockVO vo,Locale locale){
		return service.updateDoorLockName(vo);
	}
	
	
	/* 도어락 설치 위치 설명을 변경하기 위한 메소드
	 * 나중에 2개로 나뉠지도??*/
	@RequestMapping(value = "/app/mydoorlock/location/update", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject updateDoorLockPlace(AppMyDoorLockVO vo,Locale locale){
		return service.updateDoorLockPlace(vo);
	}
	
	
	/* 도어락 삭제를 위한 메소드*/
	@RequestMapping(value = "/app/mydoorlock/delete/doorlock", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject deleteMyDoorLock(AppMyDoorLockVO vo,Locale locale){
		return service.deleteMyDoorLock(vo);
	}
	
	
}
