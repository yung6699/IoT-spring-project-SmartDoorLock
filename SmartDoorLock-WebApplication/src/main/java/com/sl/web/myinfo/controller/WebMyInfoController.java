package com.sl.web.myinfo.controller;

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

import com.sl.system.log.setter.SystemLogSetter;
import com.sl.web.common.context.ContextKey;
import com.sl.web.home.vo.WebHomeVO;
import com.sl.web.myinfo.service.WebMyInfoService;
import com.sl.web.myinfo.vo.WebMyInfoVO;

import net.sf.json.JSONObject;

@Controller
public class WebMyInfoController {

	private static final Logger logger = LoggerFactory.getLogger(WebMyInfoController.class);

	@Autowired
	WebMyInfoService service;
	
	@Autowired
	SystemLogSetter logSetter;
	
	@Autowired
	HttpSession session;
	
	//홈페이지 보이기
	@RequestMapping(value = "/{email}/myInfo", method = RequestMethod.GET)
	public String myInfo(@PathVariable(value = "email") String email, Model model,Locale locale){
		logger.info("["+email+"]님이 :내 계정 관리에 들어왔습니다.");
		return "/web/myInfo/checkPassword";
	}
	
	//비밀번호 체크
	@RequestMapping(value = "/{email}/myInfo/check.do", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject checkPassword(WebMyInfoVO vo){
		JSONObject result = new JSONObject();
		String password = ((JSONObject) this.session.getAttribute(ContextKey.LOGIN_MEMBER)).getString("PASSWORD");
		System.out.println(password);
		System.out.println(password);
		System.out.println(vo.getPassword());
		
		if(vo.getPassword().equals(password)){
			result.put(ContextKey.AJAX_RESULT, ContextKey.AJAX_SUCCESS);
			logSetter.setSystemLog(
				logSetter.builder(
					ContextKey.LOG_STATUS_ACCOUNT_INFO,
					vo.getEmail(),
					null,
					null,
					null
				)
			);
			return result;	
		}else{
			result.put(ContextKey.AJAX_RESULT, ContextKey.AJAX_FAIL);
			logSetter.setSystemLog(
				logSetter.builder(
					ContextKey.LOG_STATUS_ACCOUNT_INFO_FAIL,
					vo.getEmail(),
					null,
					null,
					null
				)
			);
			return result;
		}
	}
	
	//내 정보 수정 페이지 보여주기
	@RequestMapping(value = "/{email}/myInfo/update", method = RequestMethod.GET)
	public String updateMyInfo(@PathVariable(value = "email") String email,WebMyInfoVO vo){
		logger.info("["+email+"]님이 :내 계정 관리에 들어왔습니다.");
		return "/web/myInfo/myInfo";
	}
	
	//내 정보 수정 페이지 보여주기
	@RequestMapping(value = "/{email}/myInfo/update/submit", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject updateSubmitMyInfo(@PathVariable(value = "email") String email, WebMyInfoVO vo){
		logger.info("["+email+"]님이 :내 계정 관리의 정보를 수정하였습니다.");
		JSONObject result=service.updateSubmitMyInfo(vo);
		if(vo.getPassword()==null||"".equals(vo.getPassword())){
			result.put(ContextKey.RESULT_MSG,"비밀번호를 입력해주세요.");
			return result;				
		}
		
		System.out.println(result);
		if(result.getString(ContextKey.AJAX_RESULT).equals(ContextKey.AJAX_SUCCESS)){
			session.removeAttribute(ContextKey.LOGIN_MEMBER);
			session.setAttribute(ContextKey.LOGIN_MEMBER, result.getJSONObject(ContextKey.LOGIN_MEMBER));
			result.put(ContextKey.RESULT_MSG,"회원정보 수정에 성공하였습니다.");
			return result;	
		}else{
			result.put(ContextKey.RESULT_MSG,"회원정보 수정에 실패하였습니다.");
			return result;				
		}
	}
	//비밀번호 체크
	@RequestMapping(value = "/{email}/myInfo/update/delete.do", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject updateDeleteMyInfo(@PathVariable(value = "email") String email,WebMyInfoVO vo, Locale locale, Model model){
		logger.info("["+email+"]:회원 탈퇴를 합니다.",locale);
		session.removeAttribute(ContextKey.LOGIN_MEMBER);
		return service.updateDeleteMyInfo(vo);
	}
	
		
	
	
	
}