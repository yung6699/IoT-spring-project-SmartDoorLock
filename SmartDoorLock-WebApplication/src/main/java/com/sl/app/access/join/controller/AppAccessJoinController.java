package com.sl.app.access.join.controller;

import java.util.Locale;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sl.app.access.join.service.AppAccessJoinService;
import com.sl.app.access.join.vo.AppAccessJoinVO;
import com.sl.system.common.util.ContextUtil;
import com.sl.system.email.sender.MailOrderManager;

import net.sf.json.JSONObject;


@Controller
public class AppAccessJoinController {
	
	private static final Logger logger = LoggerFactory.getLogger(AppAccessJoinController.class);

	@Autowired
	private AppAccessJoinService service;
	
	@Autowired
	private MailOrderManager emailSender;
	
	//email
	@RequestMapping(value = "/app/access/join/email", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject checkEmail(AppAccessJoinVO vo,Locale locale, Model model) throws MessagingException {
		return service.getCheckEmail(vo);
	}
	
	@RequestMapping(value = "/app/access/join/complete", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject completeJoin(AppAccessJoinVO vo, Locale locale, Model model) throws MessagingException {
		try{
			vo.setPassword(ContextUtil.AES_EncodeMaker(vo.getPassword()));
		}catch(Exception e){
			logger.debug("Encoding Password Occur a Error");
			e.printStackTrace();
		}
		JSONObject obj = service.setCompleteJoin(vo);
		vo.setMember_id(obj.getInt("member_id"));
		
		// 이메일 내용
		String msg=
"<html> <head> <meta charset='utf-8'> <title>asdasd</title> </head> <body> <div> <div> <h1>SmartLock회원가입-이메일인증</h1> </div> <hr style='display: block; margin-top: 0.5em; margin-bottom: 0.5em; margin-left: auto; margin-right: auto; border-style: inset; border-width: 1px;'/> <h3>저희 SmartLock은 {name}님의 이메일을 필요로 합니다!</h3> <ul> <li>회원님의 이메일은 광고를 수신하기위해 필요한것이 아닌 아이디로 사용되기위해 필요한 것 이며, 이것은 사용자들을 식별하기위해 사용합니다. <br/>&nbsp; </li> <li>또한, 이아이디값을 이용하여 고객님에게 더 나은 서비스를 제공할 것을 약속하며 ,SmartLock 이용에 서비스에 대한 변동사항 같은것들을 수신하기 위해 필요합니다. <br/>&nbsp; </li> <li>메일인증을 하지 않으실 경우 서비스 이용에 제한이 됩니다. 부디 이 점 양해 부탁드립니다. <br/>&nbsp; </li> </ul> <table style='width:100%; margin:auto; padding:auto; text-align:center;'> <tr style='background-color:#eee; height:3em'> <th style='border-bottom:#ccc 1px solid;'> 항목 </th> <th style='border-bottom:#ccc 1px solid;'> 입력값 </th> </tr> <tr> <td style='font-weight:bold; border-bottom:#ccc 1px solid; height:2em'> 이메일 </td> <td style='border-bottom:#ccc 1px solid; height:2em;'> {email} </td> </tr> <tr> <td style='font-weight:bold; border-bottom:#ccc 1px solid; height:2em'> 이름 </td> <td style='border-bottom:#ccc 1px solid; height:2em'> {name} </td> </tr> <tr> <td style='font-weight:bold; border-bottom:#ccc 1px solid; height:2em'> 전화번호 </td> <td style='border-bottom:#ccc 1px solid; height:2em'> {phone_num} </td> </tr> </table> <br/> <div style='width:100%; margin:auto; padding:auto;'> <a href='http://smartlock.fun25.co.kr:12604/system/auth/join/email/cancel?email={email}&password={password}&member_id={member_id}' style='float:left;height:2.2em;width:45%; border:none; border-radius:1em; cursor:pointer; background-color:#e22222; color:#fff; text-align:center; padding-top:1em;'> 회원가입취소 </a> <span style='float:left;width:10%'>&nbsp;</span> <a href='http://smartlock.fun25.co.kr:12604/system/auth/join/email/accept?email={email}&password={password}&member_id={member_id}' style='float:left; height:2.2em; width:45%; border:none; border-radius:1em; cursor:pointer; background-color:#1f4279; color:#fff; text-align:center; padding-top:1em;'> 회원가입완료 </a> </div> <br/> <br/> <br/> <br/> <br/> <center> <a href='mailto:dydwls121200@gmail.com' target='_top'>dydwls121200@gmail.com 관리자에게</a> 문의 메일 보내기 </center> </div> </body> </html>";
		
		//소스코드 한줄로 만드는 웹 사이트 : http://www.kinoa.co.kr/onehtml.php
		msg = msg.replace("{name}", vo.getName());
		msg = msg.replace("{email}", vo.getEmail());
		msg = msg.replace("{phone_num}", vo.getPhone_num());
		vo.setPassword(ContextUtil.encryptionToUri(vo.getPassword()));
		msg = msg.replace("{password}", vo.getPassword());
		msg = msg.replace("{member_id}", ""+vo.getMember_id());
		
		emailSender.sendEmail(vo.getEmail(),"[인증메일] SmartLock 회원가입을 위해 Email인증 부탁드립니다.",msg);

		return obj;
	}	
	
	
	/*
	 *commmit!!
	 *
	 */
}
