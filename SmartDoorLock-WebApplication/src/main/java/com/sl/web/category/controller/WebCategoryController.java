package com.sl.web.category.controller;

import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sl.web.category.service.WebCategoryService;
import com.sl.web.category.vo.WebCategoryListVO;
import com.sl.web.common.context.ContextKey;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class WebCategoryController {


	@Autowired
	private HttpSession session;
	private final Logger logger = LoggerFactory.getLogger(WebCategoryController.class);
	
	
	@Autowired
	private WebCategoryService service;
	
	//사용자가 카테고리 메뉴로 접근합니다.
	@RequestMapping(value = "/{email}/category", method = RequestMethod.GET)
	public String categoryList(@PathVariable(value = "email") String email, Model model,Locale locale){
		logger.info("["+email+"]님이 :카테고리 관리에 들어왔습니다.");
		return "/web/category/category_list";
	}
	@RequestMapping(value = "/{email}/category/list.json", method = RequestMethod.POST)
	@ResponseBody
	public JSONArray categoryListJSON(@PathVariable(value = "email") String email){
		logger.info("["+email+"]님이 :카테고리 관리의 목록 데이터를 요청합니다.");
		JSONObject obj = (JSONObject)session.getAttribute(ContextKey.LOGIN_MEMBER);
		return service.categoryListJSON(obj);
	}
/*
 	사용하지 않고있음.
	@RequestMapping(value = "/{email}/category/keys.json", method = RequestMethod.POST)
	@ResponseBody
	public JSONArray categoryKeysJSON(@PathVariable(value = "email") String email){
		logger.info("["+email+"]님이 :카테고리 관리의 열쇠 데이터를 요청합니다.");
		JSONObject obj = (JSONObject)session.getAttribute(ContextKey.LOGIN_MEMBER);
		return service.categoryKeysJSON(obj);
	}
	@RequestMapping(value = "/{email}/category/listandkeys.json", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject categoryListAndKeysJSON(@PathVariable(value = "email") String email){
		JSONObject result = new JSONObject();
		result.put("LIST", this.categoryListJSON(email));
		result.put("KEYS", this.categoryKeysJSON(email));	
		return null;
	}
*/
	@RequestMapping(value = "/{email}/category/create.do", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject categoryCreateDo(@PathVariable(value = "email") String email,WebCategoryListVO vo){
		vo.setCrt_email(email);
		vo.setMember_id(((JSONObject)session.getAttribute(ContextKey.LOGIN_MEMBER)).getInt("MEMBER_ID"));
		return service.categoryCreateDo(vo);
	}

	
	//카테고리 리스트는 그릴 수 있을 만큼의 데이터를 가져온다. 
	//해당 리스트의 총 가입자 수, 
}
