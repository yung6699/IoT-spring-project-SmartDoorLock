package com.sl.web.doorlock.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sl.system.log.setter.SystemLogSetter;
import com.sl.system.log.setter.SystemLogSetterVO;
import com.sl.web.common.context.ContextKey;
import com.sl.web.doorlock.dao.WebDoorlockDAO;
import com.sl.web.doorlock.vo.WebDoorlockVO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class WebDoorlockServiceImpl implements WebDoorlockService{
	
	@Autowired
	private WebDoorlockDAO dao;
	
	@Autowired
	private HttpSession session;

	@Autowired
	private SystemLogSetter logSetter;
	
	@Override
	public List<WebDoorlockVO> doorlockList(WebDoorlockVO vo){
		JSONObject sessionObj = (JSONObject) session.getAttribute(ContextKey.LOGIN_MEMBER);
		vo.setMember_id(sessionObj.getInt("MEMBER_ID"));
		vo.setEmail(sessionObj.getString("EMAIL"));		
		return dao.doorlockList(vo);
	}
	
	@Override
	public JSONObject doorlockDetail(WebDoorlockVO vo){
		return dao.doorlockDetail(vo);
	}

	@Override
	public JSONObject doorlockCreateCheck(WebDoorlockVO vo) {
		// TODO Auto-generated method stub
		JSONObject sessionObj = (JSONObject) session.getAttribute(ContextKey.LOGIN_MEMBER);
		vo.setMember_id(sessionObj.getInt("MEMBER_ID"));
		vo.setEmail(sessionObj.getString("EMAIL"));		
		return dao.doorlockCreateCheck(vo);
	}
	
	@Override
	public JSONObject doorlockCreate(WebDoorlockVO vo) {
		// TODO Auto-generated method stub
		JSONObject sessionObj = (JSONObject) session.getAttribute(ContextKey.LOGIN_MEMBER);
		vo.setMember_id(sessionObj.getInt("MEMBER_ID"));
		vo.setEmail(sessionObj.getString("EMAIL"));		
		return dao.doorlockCreate(vo);
	}

	
	
	@Override
	public JSONObject doorlockUpdate(WebDoorlockVO vo){
		JSONObject sessionObj = (JSONObject) session.getAttribute(ContextKey.LOGIN_MEMBER);
		JSONObject result = new JSONObject();

		vo.setMember_id(sessionObj.getInt("MEMBER_ID"));
		if(vo.getEmail().equals(sessionObj.getString("EMAIL"))){
			if(dao.doorlockUpdate(vo)==1){
				result.put(ContextKey.AJAX_RESULT, ContextKey.AJAX_SUCCESS);
				result.put(ContextKey.RESULT_MSG, "도어락 수정에 성공하였습니다.");
				logSetter.setNewspeed(
					logSetter.builder(
						ContextKey.LOG_STATUS_DOORLOCK_UPDATE,
						vo.getEmail(),
						null,
						vo.getSerial_no(),
						null
					),null
				);
				
				return result;
			}else{
				result.put(ContextKey.AJAX_RESULT, ContextKey.AJAX_FAIL);
				result.put(ContextKey.RESULT_MSG, "도어락 수정에 실패하였습니다. \n 시스템 장애입니다.");
				logSetter.setDoorlockLog(
					logSetter.builder(
						ContextKey.LOG_STATUS_DOORLOCK_UPDATE_FAIL,
						vo.getEmail(),
						null,
						vo.getSerial_no(),
						null
					)
				);
				return result;
			}
		}else{
			result.put(ContextKey.AJAX_RESULT, ContextKey.AJAX_FAIL);
			result.put(ContextKey.RESULT_MSG, "도어락 수정에 실패하였습니다. \n 사용자의 권한이 올바른지 확인하시기 바랍니다.");
			logSetter.setDoorlockLog(
				logSetter.builder(
					ContextKey.LOG_STATUS_DOORLOCK_UPDATE_FAIL,
					vo.getEmail(),
					null,
					vo.getSerial_no(),
					null
				)
			);
			return result;
		}
	}
	
	@Override
	public JSONObject doorlockDelete(WebDoorlockVO vo) {
		// TODO Auto-generated method stub
		
		JSONObject sessionObj = (JSONObject) session.getAttribute(ContextKey.LOGIN_MEMBER);
		vo.setMember_id(sessionObj.getInt("MEMBER_ID"));
		
		JSONObject result = dao.doorlockDelete(vo);
		String temp = result.getString(ContextKey.RESULT_CODE);
		
		if(ContextKey.RESULT_CODE_1.equals(temp)){
			result.put(ContextKey.AJAX_RESULT,ContextKey.AJAX_SUCCESS);
			result.put(ContextKey.RESULT_MSG, "도어락이 삭제 되었습니다.\n 해당 도어락은 비 활성화 상태가 됩니다.");
			SystemLogSetterVO log = logSetter.builder(ContextKey.LOG_STATUS_DOORLOCK_DELETE,vo.getEmail(),null,vo.getSerial_no(),null);
			for(String email : dao.getEmail(vo.getSerial_no())){
				logSetter.setNewspeed(log,email);	
			}
			return result;
		}else if(ContextKey.RESULT_CODE_2.equals(temp)){
			logSetter.setDoorlockLog(
				logSetter.builder(
					ContextKey.LOG_STATUS_DOORLOCK_DELETE_FAIL,
					vo.getEmail(),
					null,
					vo.getSerial_no(),
					null
				)
			);
			result.put(ContextKey.AJAX_RESULT,ContextKey.AJAX_FAIL);
			result.put(ContextKey.RESULT_MSG, "도어락이 삭제 되지 않았습니다. 아직 "+result.getInt("count")+"명의 권한 이용자가 존재합니다. ");
			return result;
		}else if(ContextKey.RESULT_CODE_3.equals(temp)){
			logSetter.setDoorlockLog(
				logSetter.builder(
					ContextKey.LOG_STATUS_DOORLOCK_DELETE_FAIL,
					vo.getEmail(),
					null,
					vo.getSerial_no(),
					null
				)
			);
			result.put(ContextKey.AJAX_RESULT,ContextKey.AJAX_FAIL);
			result.put(ContextKey.RESULT_MSG, "도어락이 삭제 되지 않았습니다. 정상적인 서비스이용을 부탁 드립니다. ");
			return result;
		}else{
			logSetter.setDoorlockLog(
				logSetter.builder(
					ContextKey.LOG_STATUS_DOORLOCK_DELETE_FAIL,
					vo.getEmail(),
					null,
					vo.getSerial_no(),
					null
				)
			);
			result.put(ContextKey.AJAX_RESULT,ContextKey.AJAX_FAIL);
			result.put(ContextKey.RESULT_MSG, "시스템 장애입니다. 문의 부탁드립니다.");
			return result;
		}
		 
	}

	@Override
	public JSONArray selectListDoorlocklogs(SystemLogSetterVO vo) {
		// TODO Auto-generated method stub
		return dao.selectListDoorlocklogs(vo);
	}
	
}
