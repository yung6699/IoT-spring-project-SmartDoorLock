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

import com.sl.web.category.service.WebCategorySettingService;
import com.sl.web.common.context.ContextKey;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class WebCategorySettingController {


	@Autowired
	private HttpSession session;
	private final Logger logger = LoggerFactory.getLogger(WebCategorySettingController.class);
	
	
	@Autowired
	private WebCategorySettingService service;
	
	
	//사용자가 카테고리 관리로 접근합니다.
	@RequestMapping(value = "/{email}/category/{cat_id}/modify", method = RequestMethod.GET)
	public String categorySetting(@PathVariable(value = "email") String email,@PathVariable(value = "cat_id") int cat_id, Model model,Locale locale){
		logger.info("["+email+"]님이 :카테고리 관리에 들어왔습니다.");
		
		if(((JSONObject)session.getAttribute(ContextKey.LOGIN_MEMBER)).getString("EMAIL").equals(email)){
			if(service.checkCategory(email,cat_id)){
				model.addAttribute("CAT_ID",cat_id);
				return "/web/category/category_setting";	
			}else{
				model.addAttribute("ERROR","<script>alert('올바른 경로로 이용해주시기 바랍니다.');location.replace('/"+email+"/category');</script>");
				return "/web/category/category_list";
			}
		}else{
			model.addAttribute("ERROR","<script>alert('올바른 경로로 이용해주시기 바랍니다.');location.replace('/"+email+"/category');</script>");
			return "/web/category/category_list";
		}
	}
	

	@RequestMapping(value = "/{email}/category/{cat_id}/modify.json", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject categoryKeysJSON(@PathVariable(value = "email") String email,@PathVariable(value = "cat_id") int cat_id){
		logger.info("["+email+"]님이 :카테고리 관리의 페이지를 그릴 데이터를 json요청합니다.");
		
		if(service.checkCategory(email,cat_id)){
			
			return service.getCategoryListInfo(email, cat_id);
		}else{
			JSONObject result = new JSONObject();
			result.put(ContextKey.AJAX_RESULT,ContextKey.AJAX_FAIL);
			result.put(ContextKey.RESULT_MSG, "올바른 경로로 이용해 주시기 바랍니다.");
			return result;
		}
	}
	

	@RequestMapping(value = "/{email}/category/{cat_id}/modify/submit.do", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject submitCategoryModify(@PathVariable(value = "email") String email,@PathVariable(value = "cat_id") int cat_id,String json){
		logger.info("["+email+"]님이 :카테고리 관리를 수정하여 저장합니다.");
		
		if(service.checkCategory(email,cat_id)){
			JSONObject obj = JSONObject.fromObject(json);
			obj.put("cat_id", cat_id);
			return service.submitCategoryModify(obj);
		}else{
			JSONObject result = new JSONObject();
			result.put(ContextKey.AJAX_RESULT,ContextKey.AJAX_FAIL);
			result.put(ContextKey.RESULT_MSG, "올바른 경로로 이용해 주시기 바랍니다.");
			return result;
		}

		
	}

	@RequestMapping(value = "/{email}/category/{cat_id}/modify/delete.do", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject deleteCategoryModify(@PathVariable(value = "email") String email,@PathVariable(value = "cat_id") int cat_id){
		logger.info("["+email+"]님이 :카테고리 관리를 제거 합니다.");
		JSONObject obj = (JSONObject) session.getAttribute(ContextKey.LOGIN_MEMBER);
		JSONObject result = new JSONObject();
		if(email.equals(obj.getString("EMAIL"))){
			if(service.checkCategory(email,cat_id)){
				if(service.deleteCategoryModify(email,cat_id)){
					result.put(ContextKey.AJAX_RESULT,ContextKey.AJAX_SUCCESS);
					return result;	
					
				}else{
					result.put(ContextKey.AJAX_RESULT,ContextKey.AJAX_FAIL);
					result.put(ContextKey.RESULT_MSG, "올바른 경로로 이용해 주시기 바랍니다.");
					return result;	
				}
			}else{
				result.put(ContextKey.AJAX_RESULT,ContextKey.AJAX_FAIL);
				result.put(ContextKey.RESULT_MSG, "올바른 경로로 이용해 주시기 바랍니다.");
				return result;
				
			}
		}else{
			result.put(ContextKey.AJAX_RESULT,ContextKey.AJAX_FAIL);
			result.put(ContextKey.RESULT_MSG, "올바른 경로로 이용해 주시기 바랍니다.");
			return result;
		}
	}
}
