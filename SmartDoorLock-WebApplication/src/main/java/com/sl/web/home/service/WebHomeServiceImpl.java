package com.sl.web.home.service;

import java.net.URLEncoder; 
import java.util.Random;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sl.system.common.util.AES256Util;
import com.sl.system.common.util.ContextUtil;
import com.sl.system.email.sender.MailOrderManager;
import com.sl.web.common.context.ContextKey;
import com.sl.web.home.dao.WebHomeDAO;
import com.sl.web.home.vo.WebHomeVO;

import net.sf.json.JSONObject;

@Service
public class WebHomeServiceImpl implements WebHomeService{

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private WebHomeDAO dao;
	
	@Autowired
	private MailOrderManager mailOrderManager;
	
	@Override
	public JSONObject login(WebHomeVO vo) {
		// TODO Auto-generated method stub
		return dao.login(vo);
	}

	@Override
	public JSONObject joinEmail(WebHomeVO vo) {
		// TODO Auto-generated method stub
		return dao.joinEmail(vo);
	}

	@Override
	public JSONObject joinAccount(WebHomeVO vo) {
		// TODO Auto-generated method stub
		return dao.joinAccount(vo);
	}

	@Override
	public JSONObject findAccount(WebHomeVO vo) {
		// TODO Auto-generated method stub
		//vo.setPassword(str);
		String pass = ContextUtil.generateKey();
		vo.setPassword(pass);
		logger.debug(JSONObject.fromObject(vo).toString());
		JSONObject obj = dao.findAccount(vo);
		if(obj.getString(ContextKey.AJAX_RESULT).equals(ContextKey.AJAX_SUCCESS)){
			String msg=
			"<html> <head> <meta charset='utf-8'> <title>asdasd</title> </head> <body> <div> <h1>SmartLock-비밀번호 찾기</h1> <div> </div> <hr style='display: block; margin-top: 0.5em; margin-bottom: 0.5em; margin-left: auto; margin-right: auto; border-style: inset; border-width: 1px;'/> <h3>SmartLock이 {email}[{name}]님의 비밀번호를 가져왔어요!</h3> <ul> <li>회원님의 비밀번호는 SmartLock도 모릅니다! <br/>&nbsp; </li> <li>그렇지만 회원님의 비밀번호를 변경할 수 있어요. <br/>&nbsp; </li> <li>임시로 비밀번호를 바꿔드릴태니, 로그인해서 자신만의 비밀번호로 바꾸길 바래요! <br/>&nbsp; </li> </ul> <table style='width:100%; margin:auto; padding:auto; text-align:center;'> <tr style='background-color:#eee; height:3em'> <th style='border-bottom:#ccc 1px solid;'> 항목 </th> <th style='border-bottom:#ccc 1px solid;'> 입력값 </th> </tr> <tr> <td style='font-weight:bold; border-bottom:#ccc 1px solid; height:2em'> 이메일 </td> <td style='border-bottom:#ccc 1px solid; height:2em;'> {email} </td> </tr> <tr> <td style='font-weight:bold; border-bottom:#ccc 1px solid; height:2em'> 비밀번호 </td> <td style='border-bottom:#ccc 1px solid; height:2em'> {password} </td> </tr> </table> <br/> <center> <a href='http://smartlock.fun25.co.kr:12604' style='float:left;height:2.2em;width:70%; border:none; border-radius:1em; cursor:pointer; background-color:#1f4279; color:#fff; text-align:center; padding-top:1em; margin-left:15%; margin-right:15%;'> 홈페이지 방문 </a> </center> <br/> <br/> <br/> <br/> <br/> <center> <a href='mailto:dydwls121200@gmail.com' target='_top'>dydwls121200@gmail.com 관리자에게</a> 문의 메일 보내기 </center> </div> </body> </html>";
			//소스코드 한줄로 만드는 웹 사이트 : http://www.kinoa.co.kr/onehtml.php
			msg = msg.replace("{name}", vo.getName());
			msg = msg.replace("{email}",vo.getEmail());
			msg = msg.replace("{password}", pass);
			logger.debug(JSONObject.fromObject(vo).toString());
			try {
				mailOrderManager.sendEmail(vo.getEmail(),"[비밀번호찾기] SmartLock이 비밀번호를 가져왔습니다.",msg);
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return obj;
			}
		}
		return obj;
	}
}
