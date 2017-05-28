package com.sl.web.doorlock.dao;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.sl.system.common.util.ContextUtil;
import com.sl.system.log.setter.SystemLogSetter;
import com.sl.system.log.setter.SystemLogSetterVO;
import com.sl.web.common.context.ContextKey;
import com.sl.web.doorlock.vo.WebDoorlockVO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Repository
public class WebDoorlockKeysDAOImpl implements WebDoorlockKeysDAO{
	
	@Autowired
	private SqlSession sqlSession;
	
	
	@Autowired
	SystemLogSetter logSetter;

	@Autowired
	PlatformTransactionManager platformTransactionManager;

	Logger logger = LoggerFactory.getLogger(this.getClass());
			
	private final String NS = "mapper.com.sl.web.doorlock.";
	
	
	@Override
	public JSONArray doorlockSelectKeys(WebDoorlockVO vo){
		logSetter.setSystemLog(
			logSetter.builder(
				ContextKey.LOG_STATUS_DOORLOCK_KEY_LIST,
				vo.getEmail(),
				null,
				vo.getSerial_no(),
				null
			)
		);
		return JSONArray.fromObject(sqlSession.selectList(NS+"doorlockSelectKeys",vo));
	}

	@Override
	public JSONObject doorlockSelectKeysDetail(WebDoorlockVO vo) {
		// TODO Auto-generated method stub
		JSONObject result = JSONObject.fromObject(sqlSession.selectOne(NS+"doorlockSelectKeysDetail_1", vo));
		vo.setSerial_no(result.getString("serial_no"));
		result.put("my_grade", (String) sqlSession.selectOne(NS+"doorlockSelectKeysDetail_2", vo));
		logSetter.setSystemLog(
			logSetter.builder(
				ContextKey.LOG_STATUS_DOORLOCK_KEY_INFO,
				vo.getEmail(),
				(String)sqlSession.selectOne(NS+"getEmail",vo),
				vo.getSerial_no(),
				null
			)
		);
		return result;
	}	
	
	@Override
	public JSONObject doorlockKeysCreateCheck(WebDoorlockVO vo){
		Object obj = sqlSession.selectOne(NS+"doorlockKeysCreateCheck",vo);
		if(obj==null){
			return null;
		}else{
			return JSONObject.fromObject(obj);	
		}
	}
	
	@Override
	public JSONObject doorlockKeysCreate(WebDoorlockVO vo){
		logger.info(JSONObject.fromObject(vo).toString());
		JSONObject result = new JSONObject();
		
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setName(this.getClass().getName());
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = platformTransactionManager.getTransaction(def);

		try{
			if((Integer)sqlSession.selectOne(NS+"doorlockKeysCreate_1",vo)!=0){
				result.put(ContextKey.AJAX_RESULT, ContextKey.AJAX_FAIL);
				result.put(ContextKey.RESULT_MSG, ContextKey.MSG_KEY_REGISTER_ALREADY_INSERTED);
				platformTransactionManager.rollback(status);
				logSetter.setDoorlockLog(
					logSetter.builder(
						ContextKey.LOG_STATUS_DOORLOCK_KEY_CREATE_FAIL,
						vo.getSend_email(),
						vo.getEmail(),
						vo.getSerial_no(),
						"["+vo.getGrade()+"] "
					)
				);
				return result;
			}
			//내가 설정한 권한이 서비스 로직에 부여가 가능한 권한인가?
			WebDoorlockVO myself = (WebDoorlockVO) sqlSession.selectOne(NS+"doorlockKeysCreate_2",vo);
			if(myself.getGrade().equals("MASTER")){
				if(vo.getGrade().equals("MASTER")){
					result.put(ContextKey.AJAX_RESULT, ContextKey.AJAX_FAIL);
					result.put(ContextKey.RESULT_MSG, ContextKey.MSG_KEY_REGISTER_NON_AUTH);
					platformTransactionManager.rollback(status);
					logSetter.setDoorlockLog(
						logSetter.builder(
							ContextKey.LOG_STATUS_DOORLOCK_KEY_CREATE_FAIL,
							vo.getSend_email(),
							vo.getEmail(),
							vo.getSerial_no(),
							"["+vo.getGrade()+"] "
						)
					);
					return result;
				}
			}else if(myself.getGrade().equals("MANAGER")){
				if(!(vo.getGrade().equals("MEMBER"))){
					result.put(ContextKey.AJAX_RESULT, ContextKey.AJAX_FAIL);
					result.put(ContextKey.RESULT_MSG, ContextKey.MSG_KEY_REGISTER_NON_AUTH);
					platformTransactionManager.rollback(status);
					logSetter.setDoorlockLog(
						logSetter.builder(
							ContextKey.LOG_STATUS_DOORLOCK_KEY_CREATE_FAIL,
							vo.getSend_email(),
							vo.getEmail(),
							vo.getSerial_no(),
							"["+vo.getGrade()+"] "
						)
					);
					return result;	
				}
			}else{
				result.put(ContextKey.AJAX_RESULT, ContextKey.AJAX_FAIL);
				result.put(ContextKey.RESULT_MSG, ContextKey.MSG_KEY_REGISTER_NON_AUTH);
				platformTransactionManager.rollback(status);
				logSetter.setDoorlockLog(
					logSetter.builder(
						ContextKey.LOG_STATUS_DOORLOCK_KEY_CREATE_FAIL,
						vo.getSend_email(),
						vo.getEmail(),
						vo.getSerial_no(),
						"["+vo.getGrade()+"] "
					)
				);
				return result;
			}
			//도어락이 있는가? 상태인가? 또는 존재 하는가? DOORLOCK_MST
			if((Integer)sqlSession.selectOne(NS+"doorlockKeysCreate_3",vo)!=1){
				result.put(ContextKey.AJAX_RESULT, ContextKey.AJAX_FAIL);
				result.put(ContextKey.RESULT_MSG, ContextKey.MSG_KEY_REGISTER_DOORLOCK_REJECT);
				platformTransactionManager.rollback(status);
				logSetter.setDoorlockLog(
					logSetter.builder(
						ContextKey.LOG_STATUS_DOORLOCK_KEY_CREATE_FAIL,
						vo.getSend_email(),
						vo.getEmail(),
						vo.getSerial_no(),
						"["+vo.getGrade()+"] "
					)
				);
				return result;
			}
			//디폴트 이름을 정해주기 위해 도어락의 이름은 무엇인가?  DOORLOCK_DET
			vo.setKey_name((String)sqlSession.selectOne(NS+"doorlockKeysCreate_4",vo));
			vo.setMember_id((Integer)sqlSession.selectOne(NS+"doorlockKeysCreate_5",vo));
			Object obj = sqlSession.selectOne(NS+"doorlockKeysCreate_6",vo);
			if(obj==null){
				vo.setSort(0);
			}else{
				vo.setSort((int)obj);
			}
			vo.setKey_id(ContextUtil.generateKey());
			if(((Integer)sqlSession.insert(NS+"doorlockKeysCreate_7",vo))!=1){
				platformTransactionManager.rollback(status);
				result.put(ContextKey.AJAX_RESULT, ContextKey.AJAX_FAIL);
				result.put(ContextKey.RESULT_MSG, ContextKey.MSG_KEY_REGISTER_DOORLOCK_REJECT);
				logSetter.setDoorlockLog(
					logSetter.builder(
						ContextKey.LOG_STATUS_DOORLOCK_KEY_CREATE_FAIL,
						vo.getSend_email(),
						vo.getEmail(),
						vo.getSerial_no(),
						"["+vo.getGrade()+"] "
					)
				);
				return result;
			}
			if(((Integer)sqlSession.insert(NS+"doorlockKeysCreate_8",vo))!=1){
				platformTransactionManager.rollback(status);
				result.put(ContextKey.AJAX_RESULT, ContextKey.AJAX_FAIL);
				result.put(ContextKey.RESULT_MSG, ContextKey.MSG_KEY_REGISTER_DOORLOCK_REJECT);
				logSetter.setDoorlockLog(
					logSetter.builder(
						ContextKey.LOG_STATUS_DOORLOCK_KEY_CREATE_FAIL,
						vo.getSend_email(),
						vo.getEmail(),
						vo.getSerial_no(),
						"["+vo.getGrade()+"] "
					)
				);
				return result;
			}
			result = JSONObject.fromObject(sqlSession.selectOne(NS+"doorlockKeysCreate_9",vo));
			
			result.put(ContextKey.AJAX_RESULT, ContextKey.AJAX_SUCCESS);
			result.put(ContextKey.RESULT_MSG, ContextKey.MSG_KEY_REGISTER_SUCCESS);
			platformTransactionManager.commit(status);
			logSetter.setNewspeed(
				logSetter.builder(
					ContextKey.LOG_STATUS_DOORLOCK_KEY_CREATE,
					vo.getSend_email(),
					vo.getEmail(),
					vo.getSerial_no(),
					"["+vo.getGrade()+"] 부여"
				),null
			);
			logSetter.setNewspeed(
				logSetter.builder(
					ContextKey.LOG_STATUS_DOORLOCK_KEY_CREATE_REQUEST,
					vo.getSend_email(),
					vo.getEmail(),
					vo.getSerial_no(),
					"["+vo.getGrade()+"] 부여"
				),vo.getEmail()
			);
		}catch(Exception e){
			platformTransactionManager.rollback(status);
			result.put(ContextKey.AJAX_RESULT, ContextKey.AJAX_FAIL);
			result.put(ContextKey.RESULT_MSG, ContextKey.MSG_KEY_REGISTER_DOORLOCK_REJECT);
			e.printStackTrace();
			logSetter.setDoorlockLog(
				logSetter.builder(
					ContextKey.LOG_STATUS_DOORLOCK_KEY_CREATE_FAIL,
					vo.getSend_email(),
					vo.getEmail(),
					vo.getSerial_no(),
					"["+vo.getGrade()+"] "
				)
			);
			return result;
		}
		return result;
	}
	
	@Override
	public int doorlockKeysUpdate(WebDoorlockVO vo) {
		// TODO Auto-generated method stub
		logSetter.setNewspeed(
				logSetter.builder(
					ContextKey.LOG_STATUS_DOORLOCK_KEY_CREATE_REQUEST,
					vo.getEmail(),
					vo.getSend_email(),
					vo.getSerial_no(),
					"['"+vo.getGrade()+"','"+vo.getStart_date()+"~"+vo.getExpire_date()+"'] 로 변경"
				),null
			);
		return sqlSession.update(NS+"doorlockKeysUpdate",vo);
	}

	@Override
	public JSONObject doorlockKeysDelete(WebDoorlockVO vo) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setName(this.getClass().getName());
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = platformTransactionManager.getTransaction(def);

		try{
			
			sqlSession.delete(NS+"doorlockKeysDelete_1",vo);
			if(1!=sqlSession.delete(NS+"doorlockKeysDelete_2",vo)){
				platformTransactionManager.rollback(status);
				result.put(ContextKey.AJAX_RESULT, ContextKey.AJAX_FAIL);
				result.put(ContextKey.RESULT_MSG, ContextKey.MSG_KEY_DELETE_FAIL);
				return result;
			}
			sqlSession.delete(NS+"doorlockKeysDelete_3",vo);
			result.put(ContextKey.AJAX_RESULT, ContextKey.AJAX_SUCCESS);
			platformTransactionManager.commit(status);
			logSetter.setNewspeed(
				logSetter.builder(
					ContextKey.LOG_STATUS_DOORLOCK_KEY_DELETE,
					vo.getEmail(),
					vo.getSend_email(),
					vo.getSerial_no(),
					null
				),null
			);
			return result;
			
		}catch(Exception e){
			platformTransactionManager.rollback(status);
			result.put(ContextKey.AJAX_RESULT, ContextKey.AJAX_FAIL);
			result.put(ContextKey.RESULT_MSG, ContextKey.MSG_KEY_DELETE_FAIL);
			logSetter.setNewspeed(
				logSetter.builder(
					ContextKey.LOG_STATUS_DOORLOCK_KEY_DELETE_FAIL,
					vo.getEmail(),
					vo.getSend_email(),
					vo.getSerial_no(),
					null
				),null
			);
			e.printStackTrace();
			return result;
		}
	}

	@Override
	public String getMyGrade(int member_id,String serial_no) {
		// TODO Auto-generated method stub
		WebDoorlockVO vo = new WebDoorlockVO();
		vo.setMember_id(member_id);
		vo.setSerial_no(serial_no);
		return (String)sqlSession.selectOne(NS+"getMyGrade",vo);
	}

	@Override
	public WebDoorlockVO getDoorlockKey(String key_id) {
		// TODO Auto-generated method stub
		return (WebDoorlockVO) sqlSession.selectOne(NS+"getDoorlockKey",key_id);
	}
	
	

	




}
