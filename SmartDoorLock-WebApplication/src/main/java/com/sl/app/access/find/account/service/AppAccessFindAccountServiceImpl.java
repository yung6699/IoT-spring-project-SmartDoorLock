package com.sl.app.access.find.account.service;

import java.net.URLEncoder;
import java.util.Random;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sl.app.access.find.account.dao.AppAccessFindAccountDAO;
import com.sl.app.access.find.account.vo.AppAccessFindAccountVO;
import com.sl.app.common.context.AppContextKey;
import com.sl.system.common.util.AES256Util;
import com.sl.system.email.sender.MailOrderManager;

import net.sf.json.JSONObject;

@Service
public class AppAccessFindAccountServiceImpl implements AppAccessFindAccountService{

	
	@Autowired
	AppAccessFindAccountDAO dao;
	
	@Autowired
	MailOrderManager mailOrderManager;
	
	@Override
	public JSONObject updateFindMember(AppAccessFindAccountVO vo) throws MessagingException {
		// TODO Auto-generated method stub
		//난수 발생기
		char chars[]={'0','1','2','3','4','5','6','7','8','9',
				  'a','b','c','d','e','f','g','h','i','j','k','l','m',
		          'n','o','p','q','r','s','t','u','v','w','x','y','z',
		          'A','B','C','D','E','F','G','H','I','J','K','L','M',
		          'N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
		};
		Random random = new Random();
		int howmany = random.nextInt(6)+8;
		String str = "";
		String encPass="";
		for(int i =0; i<= howmany; i++){
			str+=chars[random.nextInt(61)];
		}
        System.out.println("+++++++++++++++++++++++++++++++");
        System.out.println("변경 전 패스워드 :"+str);
		try{
			encPass=AES_Maker(str);
		}catch(Exception e){
			e.printStackTrace();
		}
        vo.setPassword(encPass);
        System.out.println("변경 후 패스워드 : "+vo.getPassword());
        System.out.println("+++++++++++++++++++++++++++++++");
		JSONObject obj = dao.updateFindMember(vo);
		if(obj.getString(AppContextKey.AJAX_RESULT).equals(AppContextKey.AJAX_SUCCESS)){
			String msg=
			"<html> <head> <meta charset='utf-8'> <title>asdasd</title> </head> <body> <div> <h1>SmartLock-비밀번호 찾기</h1> <div> </div> <hr style='display: block; margin-top: 0.5em; margin-bottom: 0.5em; margin-left: auto; margin-right: auto; border-style: inset; border-width: 1px;'/> <h3>SmartLock이 {email}[{name}]님의 비밀번호를 가져왔어요!</h3> <ul> <li>회원님의 비밀번호는 SmartLock도 모릅니다! <br/>&nbsp; </li> <li>그렇지만 회원님의 비밀번호를 변경할 수 있어요. <br/>&nbsp; </li> <li>임시로 비밀번호를 바꿔드릴태니, 로그인해서 자신만의 비밀번호로 바꾸길 바래요! <br/>&nbsp; </li> </ul> <table style='width:100%; margin:auto; padding:auto; text-align:center;'> <tr style='background-color:#eee; height:3em'> <th style='border-bottom:#ccc 1px solid;'> 항목 </th> <th style='border-bottom:#ccc 1px solid;'> 입력값 </th> </tr> <tr> <td style='font-weight:bold; border-bottom:#ccc 1px solid; height:2em'> 이메일 </td> <td style='border-bottom:#ccc 1px solid; height:2em;'> {email} </td> </tr> <tr> <td style='font-weight:bold; border-bottom:#ccc 1px solid; height:2em'> 비밀번호 </td> <td style='border-bottom:#ccc 1px solid; height:2em'> {password} </td> </tr> </table> <br/> <center> <a href='http://smartlock.fun25.co.kr:12604' style='float:left;height:2.2em;width:70%; border:none; border-radius:1em; cursor:pointer; background-color:#1f4279; color:#fff; text-align:center; padding-top:1em; margin-left:15%; margin-right:15%;'> 홈페이지 방문 </a> </center> <br/> <br/> <br/> <br/> <br/> <center> <a href='mailto:dydwls121200@gmail.com' target='_top'>dydwls121200@gmail.com 관리자에게</a> 문의 메일 보내기 </center> </div> </body> </html>";
			//소스코드 한줄로 만드는 웹 사이트 : http://www.kinoa.co.kr/onehtml.php
	        msg = msg.replace("{name}", vo.getName());
	        msg = msg.replace("{email}",vo.getEmail());
//	        System.out.println("+++++++++++++++++++++++++++++++");
//	        System.out.println("변경 전 패스워드 :"+vo.getPassword());
//	        vo.setPassword(vo.getPassword().replace("+", "%2b"));
//	        System.out.println("변경 후 패스워드 : "+vo.getPassword());
//	        System.out.println("+++++++++++++++++++++++++++++++");
	        msg = msg.replace("{password}", vo.getPassword());
			mailOrderManager.sendEmail(vo.getEmail(),"[비밀번호찾기] SmartLock이 비밀번호를 가져왔습니다.",msg);
		}
		return obj;
	}
	private String AES_Maker(String str) throws Exception{
		// 1. 기준 키 지정 : String
		String key0 = "Smart";
		
		// 2. 기준 키를 이용하여 AES256 암호화 하여 키 생성 (기준키 string HelloFactory)
		String key1 = key0;
		AES256Util aesUtil = new AES256Util(key1);
		String str1 = "Doorlock";
		String key2 = aesUtil.aesEncode(str1);
		System.out.println("##### gen EncPassword "+key2.toString());
		
		// 3. 생성된 키를 통해 패스워드를 AES256 암호화
		aesUtil = new AES256Util(key2);
		String encPassword = aesUtil.aesEncode(str);

		System.out.println("##### gen EncPassword "+encPassword.toString());
		
		// 4. 암호화 된 패스워드를 URL 인코딩
		String urlEncPassword = URLEncoder.encode(encPassword, "UTF-8");
		return encPassword;
		//vo.setPassword(encPassword);
	}
}
