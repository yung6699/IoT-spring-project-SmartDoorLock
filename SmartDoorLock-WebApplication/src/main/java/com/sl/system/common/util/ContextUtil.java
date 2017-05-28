package com.sl.system.common.util;

import java.net.URLEncoder;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContextUtil {
	public static Logger logger = LoggerFactory.getLogger("ContextUtil");
	
	
	public static String generateKey(){
		//난수 문자열 생성
		char chars[]={'0','1','2','3','4','5','6','7','8','9',
				  'a','b','c','d','e','f','g','h','i','j','k','l','m',
		          'n','o','p','q','r','s','t','u','v','w','x','y','z',
		          'A','B','C','D','E','F','G','H','I','J','K','L','M',
		          'N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
		Random random = new Random();
		//int howmany = random.nextInt(6)+8; //최소 8개 최대 14개
		int howmany = 10; //최소 8개 최대 14개
		String str = "";
		for(int i =0; i<howmany; i++){
			str+=chars[random.nextInt(61)];
		}
		
		return str;
		
	}
	
	public static String AES_EncodeMaker(String str) throws Exception{
		// 1. 기준 키 지정 : String
		// 2. 기준 키를 이용하여 AES256 암호화 하여 키 생성 (기준키 string HelloFactory)
		AES256Util aesUtil = new AES256Util("Smart");
		String key2 = aesUtil.aesEncode("Doorlock");
		aesUtil = new AES256Util(key2);
		logger.info("##### encrypt before "+str);
		str = aesUtil.aesEncode(str);
		logger.info("##### encrypt after "+str);
		return str;
	}
	public static String AES_DecodeMaker(String str) throws Exception{
		// 1. 기준 키 지정 : String
		// 2. 기준 키를 이용하여 AES256 암호화 하여 키 생성 (기준키 string HelloFactory)
		String key1 = "Smart";
		AES256Util aesUtil = new AES256Util(key1);
		String str1 = "Doorlock";
		String key2 = aesUtil.aesDecode(str1);
		
		// 3. 생성된 키를 통해 패스워드를 AES256 암호화
		aesUtil = new AES256Util(key2);
		String encPassword = aesUtil.aesDecode(str);
		logger.info("##### decrypt before "+str);
		str = aesUtil.aesDecode(str);
		logger.info("##### decrypt after "+str);
		return str;
	}
	public static String encryptionToUri(String str){
		return str.replace("+", "%2b");
	}
	
/*    public static String AES_DecodeMaker(String str) throws Exception{
		// 1. 기준 키 지정 : String
		// 2. 기준 키를 이용하여 AES256 암호화 하여 키 생성 (기준키 string HelloFactory)
		String str1 = "Doorlock";

		// 3. 생성된 키를 통해 패스워드를 AES256 암호화
		AES256Util aesUtil = new AES256Util(key2);
		String key1 = "Smart";
		aesUtil = new AES256Util(key1);

		
		String encPassword = aesUtil.aesDecode(str);
		
		logger.info("===========================gen EncPassword utf before "+encPassword.toString());
		// 4. 암호화 된 패스워드를 URL 인코딩
		String urlEncPassword = URLEncoder.encode(encPassword, "UTF-8");
		logger.info("===========================gen EncPassword utf after "+urlEncPassword);

		
		
		return decPassword;
	}*/
	
}
