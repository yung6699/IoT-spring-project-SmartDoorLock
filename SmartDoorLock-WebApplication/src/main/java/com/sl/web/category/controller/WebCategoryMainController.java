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

import com.sl.web.category.service.WebCategoryMainService;
import com.sl.web.category.service.WebCategorySettingService;
import com.sl.web.common.context.ContextKey;

import net.sf.json.JSONObject;

@Controller
public class WebCategoryMainController {

	@Autowired
	private HttpSession session;
	private final Logger logger = LoggerFactory.getLogger(WebCategoryMainController.class);

	
	
	@Autowired
	private WebCategorySettingService settingService;
	
	
	
	//사용자가 카테고리 관리로 접근합니다.
	@RequestMapping(value = "/{email}/category/{cat_id}", method = RequestMethod.GET)
	public String categoryMain(@PathVariable(value = "email") String email,@PathVariable(value = "cat_id") int cat_id, Model model,Locale locale){
		logger.info("["+email+"]님이 :카테고리 메인에 들어왔습니다.");
		
		if(((JSONObject)session.getAttribute(ContextKey.LOGIN_MEMBER)).getString("EMAIL").equals(email)){
			if(settingService.checkCategory(email,cat_id)){
				model.addAttribute("CAT_ID",cat_id);
				return "/web/category/category_main";	
			}else{
				return "/";	
			}
		}else{
			return "/";
		}
	}
	
	//사용자가 카테고리 관리로 접근합니다.
	@RequestMapping(value = "/{email}/category/{cat_id}.json", method = RequestMethod.POST)
	@ResponseBody
	public String categoryMainJSON(@PathVariable(value = "email") String email,@PathVariable(value = "cat_id") int cat_id, Model model,Locale locale){
		logger.info("["+email+"]님이 :카테고리 메인에 들어왔습니다.");
		
		if(((JSONObject)session.getAttribute(ContextKey.LOGIN_MEMBER)).getString("EMAIL").equals(email)){
			if(settingService.checkCategory(email,cat_id)){
				return null;
			}else{
				return null;
			}
		}else{
			return null;
		}
	}

}
