package com.sl.web.mykey.controller;

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

import com.sl.app.mydoorlock.key.vo.AppMyDoorLockVO;
import com.sl.web.mykey.service.WebMyKeyService;
import com.sl.web.mykey.vo.WebMyKeyVO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class WebMyKeyController {

	private static final Logger logger = LoggerFactory.getLogger(WebMyKeyController.class);

	@Autowired
	private WebMyKeyService service;
	
	// mykey 홈페이지 보이기
	@RequestMapping(value = "/{email}/mykey", method = RequestMethod.GET)
	public String mykeyList(@PathVariable(value = "email") String email, Model model) {
		model.addAttribute("email", email);
		return "/web/myKey/myKey";
	}

	

	@RequestMapping(value = "/{email}/mykey", method = RequestMethod.POST)
	@ResponseBody
	public JSONArray mykeyList(@PathVariable(value = "email") String email, WebMyKeyVO vo) {
		logger.info("[" + email + "]님이 :내 계정 관리에 들어왔습니다.");
		vo.setEmail(email);
		return service.mykeyList(vo);
	}

	
	
	// 키를 검색할 데이터를 미리 가져온다.
	@RequestMapping(value = "/{email}/mykey/search", method = RequestMethod.POST)
	@ResponseBody
	public JSONArray mykeyListSearch(@PathVariable(value = "email") String email, WebMyKeyVO vo) {
		logger.info("[" + email + "]님이 : 검색할 데이터를 요청함");
		vo.setEmail(email);
		return service.mykeyListSearch(vo);
	}

	
	
	// 키 상세 정보 보기
	@RequestMapping(value = "/{email}/mykey/detail", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject mykeyListDetail(@PathVariable(value = "email") String email, WebMyKeyVO vo) {
		logger.info("[" + email + "]님이 :키에 대한 상세 데이터 요청함");
		vo.setEmail(email);
		return service.mykeyListDetail(vo);
	}

	
	// 사용자 정의 컨트롤러 
	@RequestMapping(value = "/{email}/mykey/keyName/update", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject updateMyKeyName(@PathVariable(value = "email") String email, WebMyKeyVO vo) {
		vo.setEmail(email);
		return service.updateMyKeyName(vo);
	}
	
	/*키를 삭제하기 위한 메소드
	 * 삭제 할시 member_email도 받아야 한다. DB에 안들어가도
	 * 로그 등록시 반드시 필요하다.*/
	@RequestMapping(value = "/{email}/mykey/delete", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject deleteKey(@PathVariable(value = "email") String email, WebMyKeyVO vo){
		vo.setEmail(email);
		return service.deleteKey(vo);
	}

}
