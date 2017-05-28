package com.sl.web.doorlock.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sl.web.doorlock.service.WebDoorlockKeysService;
import com.sl.web.doorlock.vo.WebDoorlockVO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


@Controller
public class WebDoorlockKeysController {


	@Autowired
	private WebDoorlockKeysService service;
	

	@RequestMapping(value="/{email}/doorlock/keys",method=RequestMethod.POST)
	@ResponseBody
	public JSONArray doorlockSelectKeys(WebDoorlockVO vo, Locale locale){
		return service.doorlockSelectKeys(vo);
	}
	
	@RequestMapping(value="/{email}/doorlock/keys/detail",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject doorlockSelectKeysDetail(WebDoorlockVO vo, Locale locale){
		return service.doorlockSelectKeysDetail(vo);
	}
	//도어락 관리 -> 열쇠 정보 수정
	
	//도어락 관리 -> 열쇠 삭제 
	
	@RequestMapping(value="/{email}/doorlock/keys/create/check",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject doorlockKeysCreateCheck(WebDoorlockVO vo,Locale locale){
		return service.doorlockKeysCreateCheck(vo);
	}
	
	@RequestMapping(value="/{email}/doorlock/keys/create",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject doorlockKeysCreate(WebDoorlockVO vo, Locale locale){
		return service.doorlockKeysCreate(vo);
	}
	@RequestMapping(value="/{email}/doorlock/keys/update",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject doorlockKeysUpdate(@PathVariable(value = "email") String email, WebDoorlockVO vo, Locale locale){
		vo.setSend_email(email);
		return service.doorlockKeysUpdate(vo);
	}
	@RequestMapping(value="/{email}/doorlock/keys/delete",method=RequestMethod.POST)
	@ResponseBody
	public JSONObject doorlockKeysDelete(@PathVariable(value = "email") String email, WebDoorlockVO vo, Locale locale){
		vo.setSend_email(email);
		return service.doorlockKeysDelete(vo);
	}
	
	
}
