package com.sl.system.auth.join.controller;

import java.util.Locale;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sl.system.auth.join.service.SystemAuthJoinService;
import com.sl.system.auth.join.vo.SystemAuthJoinVO;
import com.sl.system.common.context.SystemContextKey;
import com.sl.system.session.member.SessionMember;
import com.sl.web.common.context.ContextKey;

import net.sf.json.JSONObject;

@Controller
public class SystemAuthJoinController {
	
	@Autowired
	private HttpSession session;

	@Autowired
	SystemAuthJoinService service;
	
	@Autowired
	SessionMember sessionMember;
	
	@RequestMapping(value = "/system/auth/join/email/accept", method = RequestMethod.GET)
	public String updateMemberState(SystemAuthJoinVO vo, Locale locale, Model model) throws MessagingException {
		JSONObject obj =sessionMember.getSessionMember(JSONObject.fromObject(vo));
		System.out.println("get password - > "+vo.getPassword());
		String state = obj==null?"X":obj.getString("STATE");
		if(state.equals("0")){
			service.updateMemberState(vo);
			obj =sessionMember.getSessionMember(JSONObject.fromObject(vo));
			model.addAttribute("STATE","ACCEPT");	
			session.setAttribute(ContextKey.LOGIN_MEMBER, obj);
			return "/system/auth/join/email/SystemAuthJoinEmailAccept";
		}else if(state.equals("1")||state.equals("2")||state.equals("3")||state.equals("4")){
			model.addAttribute("STATUS","이미 가입이 완료된 사용자입니다.<br/> 이메일 인증이 이미 완료 되었으므로 다시한번 하실 필요는 없습니다. > <  ");
			return "/common/exception/http405";
		}else{
			model.addAttribute("STATUS","시스템 장애입니다.<br/>고객센터에 문의해주세요 <a href='dydwls121200@gmail.com'>dydwls121200@gmail.com </a>문의 주세용 .&rt; &gt;  ");
			return "/common/exception/http405";	
		}
	}
	
	@RequestMapping(value = "/system/auth/join/email/cancel", method = RequestMethod.GET)
	public String cancelMember(SystemAuthJoinVO vo, Locale locale, Model model) throws MessagingException {
		model.addAttribute(SystemContextKey.LOGIN_MEMBER,sessionMember.getSessionMember(JSONObject.fromObject(vo)));
		JSONObject obj =sessionMember.getSessionMember(JSONObject.fromObject(vo));
		
		if(obj.getString("STATE").equals("0")){
			model.addAttribute("STATE","CANCEL");
			return "/system/auth/join/email/SystemAuthJoinEmailCancel";
		}else if(obj.getString("STATE").equals("1")){
			model.addAttribute("STATUS","이미 가입이 완료된 사용자입니다.<br/> 이메일 인증이 이미 완료 되었으므로 정상적인 경로로 회원탈퇴를 이용해주세요. > <  ");
			return "/common/exception/http405";
		}else{
			return "/common/exception/http405";	
		}
	}
	
	@RequestMapping(value = "/system/auth/join/email/delete", method = RequestMethod.POST)
	public String deleteMember(SystemAuthJoinVO vo, Locale locale, Model model) throws MessagingException {
		service.deleteMember(vo);
		model.addAttribute("STATE","DELETE");
		return "/system/auth/join/email/SystemAuthJoinEmailDelete";
	}
}
