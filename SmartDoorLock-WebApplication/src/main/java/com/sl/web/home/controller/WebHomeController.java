package com.sl.web.home.controller;

import java.util.Locale;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sl.system.common.util.ContextUtil;
import com.sl.system.email.sender.MailOrderManager;
import com.sl.system.log.setter.SystemLogSetter;
import com.sl.system.session.member.SessionMember;
import com.sl.web.common.context.ContextKey;
import com.sl.web.home.service.WebHomeService;
import com.sl.web.home.vo.WebHomeMessageVO;
import com.sl.web.home.vo.WebHomeVO;

import net.sf.json.JSONObject;

/**
 * Handles requests for the application home page.
 */
@Controller
public class WebHomeController {
	//어노테이션 = 검수 .
	//상속을 받아서 쓰는 메서드 들이 있는데 . 그 메서드들을 가져다 쓸때
	//어노테이션 Override를 설명해보자면, 오버라이드라고 하는것은  재정의 => 내용을 바꾼다. 
	//play라고 어노테이션을 해놓고 paly라고 오버라이드 를 해놓으면 
	//

	//어노테이션은 각 각의 정의 이름마다. 
	
	//	왜 DI를 해결하기위해 애너테이션 DI를 사용하는가?
	//스프링이 DI를 사용한 개념을 이해
	//getter/setter 
	//DI는 스프링을 쓰는 이유중에 하나 ... 
	
	@Autowired
	private MailOrderManager emailSender;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private SessionMember sessionMember;
	
	@Autowired
	private WebHomeService service;
	
	@Autowired
	private SystemLogSetter logSetter;
	
	private static final Logger logger = LoggerFactory.getLogger(WebHomeController.class);
	
	/**1
	 * Simply selects the home view to render by returning its name.
	 * @throws MessagingException 
	 */
	//홈페이지 보이기
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String main(WebHomeMessageVO contactUs, Locale locale, Model model) {
		
		return "/web/home/home";
	}
	//asd
	@RequestMapping(value = "/1", method = RequestMethod.GET)
	public String one(WebHomeMessageVO contactUs, Locale locale, Model model) {
		return "/system/auth/join/email/SystemAuthJoinEmailAccept";
	}
	//asd
	@RequestMapping(value = "/2", method = RequestMethod.GET)
	public String two(WebHomeMessageVO contactUs, Locale locale, Model model) {
		return "/system/auth/join/email/SystemAuthJoinEmailCancel";
	}
	//asd
	@RequestMapping(value = "/3", method = RequestMethod.GET)
	public String three(WebHomeMessageVO contactUs, Locale locale, Model model) {
		return "/system/auth/join/email/SystemAuthJoinEmailDelete";
	}

	//문의 하기
	@RequestMapping(value = "/message", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject message(WebHomeMessageVO contactUs) throws MessagingException {
		String msg=
				"<html> <head> <meta charset='utf-8'> <title>asdasd</title> </head> <body> <div> <h1>SmartLock-문의 메일</h1> <div> </div> <hr style='display: block; margin-top: 0.5em; margin-bottom: 0.5em; margin-left: auto; margin-right: auto; border-style: inset; border-width: 1px;'/> <h3>SmartLock이 {email}[{name}]님의 문의 메세지를 가져왔어요! 샤샤샤~</h3> <ul> <li>무슨 주제인지는 모르지만~ <br/>&nbsp; </li> <li>문의 메일이니까~~ <br/>&nbsp; </li> <li>친~절~~~ 하게! 샤샤샤샤! <br/>&nbsp; </li> </ul> <table style='width:100%; margin:auto; padding:auto; text-align:center;'> <tr style='background-color:#eee; height:3em'> <th style='border-bottom:#ccc 1px solid;'> 항목 </th> <th style='border-bottom:#ccc 1px solid;'> 입력값 </th> </tr> <tr> <td style='font-weight:bold; border-bottom:#ccc 1px solid; height:2em'> 이름 </td> <td style='border-bottom:#ccc 1px solid; height:2em;'> {name} </td> </tr> <tr> <td style='font-weight:bold; border-bottom:#ccc 1px solid; height:2em'> 이메일 </td> <td style='border-bottom:#ccc 1px solid; height:2em'> {email} </td> </tr> <tr> <td style='font-weight:bold; border-bottom:#ccc 1px solid; height:2em'> 전화번호 </td> <td style='border-bottom:#ccc 1px solid; height:2em'> {tel} </td> </tr> <tr> <td style='font-weight:bold; border-bottom:#ccc 1px solid; height:2em'> 내용 </td> <td style='border-bottom:#ccc 1px solid; height:2em'> {message} </td> </tr> </table> <br/> <br/> <center> <a href='mailto:{email}' style='float:left;height:2.2em;width:70%; border:none; border-radius:1em; cursor:pointer; background-color:#1f4279; color:#fff; text-align:center; padding-top:1em; margin-left:15%; margin-right:15%;'> 반송 메일 보내기 </a> </center> <br/> <br/> <br/> <br/> <br/> </div> </body> </html>";
		//소스코드 한줄로 만드는 웹 사이트 : http://www.kinoa.co.kr/onehtml.php
		msg = msg.replace("{name}", contactUs.getName());
        msg = msg.replace("{email}", contactUs.getEmail());
        msg = msg.replace("{tel}", contactUs.getTel());
        msg = msg.replace("{message}", contactUs.getMessage());
				       
		emailSender.sendEmail("dydwls121200@gmail.com","[문의] "+contactUs.getName()+"님의 문의 메일입니다. 친절하게 샤샤샤샤~",msg);
		return new JSONObject();
	}
	//로그인 하기
	@RequestMapping(value = "/login.do", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject login(WebHomeVO vo) throws Exception{
		JSONObject result =  service.login(vo);
		if(result.getString(ContextKey.AJAX_RESULT).equals(ContextKey.AJAX_SUCCESS)){
			System.out.println(result);
			JSONObject sessionObj = sessionMember.getSessionMember(JSONObject.fromObject(vo));
			session.setAttribute(ContextKey.LOGIN_MEMBER, sessionObj);
			result.put(ContextKey.LOGIN_MEMBER, sessionObj);
			return result;
		}else{
			logger.info(vo.getEmail()+"님이 로그인에 실패 하였습니다.");
			return result;
		}
	}
	
	
	//회원가입하기-이메일 인증
	@RequestMapping(value = "/join/email.do", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject joinEmail(WebHomeVO vo){
		return service.joinEmail(vo);
	}

	//회원가입하기-정보 입력
	@RequestMapping(value = "/join.do", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject joinAccount(WebHomeVO vo){
		JSONObject result = service.joinAccount(vo);
		vo.setMember_id(result.getInt("member_id"));
		if(result.getString(ContextKey.AJAX_RESULT).equals(ContextKey.AJAX_SUCCESS)){

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
			try {
				emailSender.sendEmail(vo.getEmail(),"[인증메일] SmartLock 회원가입을 위해 Email인증 부탁드립니다.",msg);
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
			return result;
		}else{
			return result;	
		}
	}
	
	//회원 계정 찾기
	@RequestMapping(value = "/find/account.do", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject findAccount(WebHomeVO vo) {
		return service.findAccount(vo);
	}
}

