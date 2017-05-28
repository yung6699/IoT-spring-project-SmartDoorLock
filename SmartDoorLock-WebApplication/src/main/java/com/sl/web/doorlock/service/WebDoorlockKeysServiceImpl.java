package com.sl.web.doorlock.service;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sl.system.log.setter.SystemLogSetter;
import com.sl.web.common.context.ContextKey;
import com.sl.web.doorlock.dao.WebDoorlockKeysDAO;
import com.sl.web.doorlock.vo.WebDoorlockVO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class WebDoorlockKeysServiceImpl implements WebDoorlockKeysService {
	
	@Autowired
	private WebDoorlockKeysDAO dao;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	SystemLogSetter logSetter;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public JSONArray doorlockSelectKeys(WebDoorlockVO vo){
		return dao.doorlockSelectKeys(vo);
	}

	@Override
	public JSONObject doorlockSelectKeysDetail(WebDoorlockVO vo) {
		// TODO Auto-generated method stub
		JSONObject sessionObj = (JSONObject) session.getAttribute(ContextKey.LOGIN_MEMBER);
		vo.setMember_id(sessionObj.getInt("MEMBER_ID"));
		return dao.doorlockSelectKeysDetail(vo);
	}
	
	@Override
	public JSONObject doorlockKeysCreateCheck(WebDoorlockVO vo){
		JSONObject sessionObj = (JSONObject) session.getAttribute(ContextKey.LOGIN_MEMBER);
		JSONObject result = dao.doorlockKeysCreateCheck(vo);
		if(result!=null){
			if(result.getString("email").equals(sessionObj.getString("EMAIL"))){
				result = new JSONObject();
				result.put(ContextKey.AJAX_RESULT, ContextKey.AJAX_FAIL);
				result.put(ContextKey.RESULT_MSG,ContextKey.MSG_KEY_REGISTER_INSERT_SELF);
			}else{
				result.put(ContextKey.AJAX_RESULT, ContextKey.AJAX_SUCCESS);			
			}
		}else{
			result = new JSONObject();
			result.put(ContextKey.AJAX_RESULT, ContextKey.AJAX_FAIL);
			result.put(ContextKey.RESULT_MSG,ContextKey.MSG_KEY_REGISTER_NONE);	
		}
		return result;		
	}
	
	@Override
	public JSONObject doorlockKeysCreate(WebDoorlockVO vo){
		JSONObject sessionObj = (JSONObject) session.getAttribute(ContextKey.LOGIN_MEMBER);
		//이것만 로그인한 사용자의 데이터
		//member_id
		vo.setMember_id(sessionObj.getInt("MEMBER_ID"));
		vo.setSend_email(sessionObj.getString("EMAIL"));
		//부여할 회원의 정보는 vo에 들어있으며 내용은
		//email : 부여할 회원의 정보
		//serial_no : 부여할 도어락
		//grade : 부여할 도어락에 대한 권한

		//쿼리를 보내기 전에 한번 더 체크
		return dao.doorlockKeysCreate(vo);	
	}

	@Override
	public JSONObject doorlockKeysUpdate(WebDoorlockVO vo) {
		// TODO Auto-generated method stub
		JSONObject result =new JSONObject();
		logger.debug(JSONObject.fromObject(vo).toString());
		JSONObject sessionObj = (JSONObject) session.getAttribute(ContextKey.LOGIN_MEMBER);
		vo.setUpdt_email(sessionObj.getString("UPDT_EMAIL"));
		//사용자이거나 매니저가 마스터를 수정하려고 한다면
		String my_grade=dao.getMyGrade(sessionObj.getInt("MEMBER_ID"),vo.getSerial_no());
		
		if("MEMBER".equals(my_grade)||
			("MANAGER".equals(my_grade)&&
			"MASTER".equals(vo.getGrade()))
		){
			result.put(ContextKey.AJAX_RESULT, ContextKey.AJAX_FAIL);
			result.put(ContextKey.RESULT_MSG, ContextKey.MSG_KEY_UPDATE_FAIL);
			logSetter.setDoorlockLog(
				logSetter.builder(
					ContextKey.LOG_STATUS_DOORLOCK_KEY_UPDATE_FAIL,
					vo.getSend_email(),
					vo.getEmail(),
					vo.getSerial_no(),
					""
				)
			);
			return result;
		}
		WebDoorlockVO keyInfo = dao.getDoorlockKey(vo.getKey_id());
		keyInfo.setStart_date(keyInfo.getStart_date().split(" ")[0]);
		keyInfo.setExpire_date(keyInfo.getExpire_date().split(" ")[0]);
		
		result.put(ContextKey.AJAX_RESULT,ContextKey.AJAX_SUCCESS);
		String msg="";
		if(!(keyInfo.getGrade().trim().equals(vo.getGrade().trim()))){
			msg+="<br/>권한변경 : ["+keyInfo.getGrade()+"] => ["+vo.getGrade()+"]";
		}
		
		if(!(vo.getStart_date().equals(keyInfo.getStart_date()))||!(vo.getExpire_date().equals(keyInfo.getExpire_date()))){
			msg+="<br/>만료기간 변경 : ["+keyInfo.getStart_date()+"~"+keyInfo.getExpire_date()+"] => ["+vo.getStart_date()+"~"+vo.getExpire_date()+"]";
		}
		if(!(vo.getState().equals(keyInfo.getState()))){
			msg+="<br/>상태 변경 : ["+keyInfo.getState()+"] => ["+vo.getState()+"]";
		}
		
		
		
		
		int query1 = dao.doorlockKeysUpdate(vo);
		if(query1==1){
			result.put(ContextKey.AJAX_RESULT, ContextKey.AJAX_SUCCESS);
			logSetter.setNewspeed(logSetter.builder(
					ContextKey.LOG_STATUS_DOORLOCK_KEY_UPDATE,
					vo.getSend_email(),
					vo.getEmail(),
					vo.getSerial_no(),
					msg
				),null
			);
			return result;
		}else{
			result.put(ContextKey.AJAX_RESULT, ContextKey.AJAX_FAIL);
			result.put(ContextKey.RESULT_MSG,ContextKey.MSG_KEY_UPDATE_FAIL);
			logSetter.setDoorlockLog(
				logSetter.builder(
					ContextKey.LOG_STATUS_DOORLOCK_KEY_UPDATE_FAIL,
					vo.getSend_email(),
					vo.getEmail(),
					vo.getSerial_no(),
					"["+vo.getGrade()+"] "
				)
			);
			return result;
		}
	}

	@Override
	public JSONObject doorlockKeysDelete(WebDoorlockVO vo) {
		// TODO Auto-generated method stub
		JSONObject result =new JSONObject();
		JSONObject sessionObj = (JSONObject) session.getAttribute(ContextKey.LOGIN_MEMBER);
		vo.setUpdt_email(sessionObj.getString("UPDT_EMAIL"));
		String my_grade=dao.getMyGrade(sessionObj.getInt("MEMBER_ID"),vo.getSerial_no());
		//사용자이거나 매니저가 마스터를 수정하려고 한다면
		if("MEMBER".equals(my_grade)||
			("MANAGER".equals(my_grade)&&
			"MASTER".equals(vo.getGrade()))
		){
			result.put(ContextKey.AJAX_RESULT, ContextKey.AJAX_FAIL);
			result.put(ContextKey.RESULT_MSG, ContextKey.MSG_KEY_UPDATE_FAIL);
			logSetter.setDoorlockLog(
				logSetter.builder(
					ContextKey.LOG_STATUS_DOORLOCK_KEY_DELETE_FAIL,
					vo.getSend_email(),
					vo.getEmail(),
					vo.getSerial_no(),
					null
				)
			);
			return result;
		}
		logSetter.setNewspeed(
			logSetter.builder(
				ContextKey.LOG_STATUS_DOORLOCK_KEY_DELETE,
				vo.getSend_email(),
				vo.getEmail(),
				vo.getSerial_no(),
				null
			),"ALL"
		);
		result = dao.doorlockKeysDelete(vo);
		return result;
	}
}
