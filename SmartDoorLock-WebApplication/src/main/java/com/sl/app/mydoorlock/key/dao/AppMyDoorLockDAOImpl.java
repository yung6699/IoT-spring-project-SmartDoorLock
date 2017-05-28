
package com.sl.app.mydoorlock.key.dao;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.sl.app.common.context.AppContextKey;
import com.sl.app.mydoorlock.key.vo.AppMyDoorLockVO;
import com.sl.system.common.util.ContextUtil;
import com.sl.system.log.setter.SystemLogSetter;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Repository
public class AppMyDoorLockDAOImpl implements AppMyDoorLockDAO {

	@Autowired
	private SqlSession sqlSession;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private PlatformTransactionManager platformTransactionManager;
	
	@Autowired
	SystemLogSetter logSetter;

	private final String NS = "mapper.com.sl.app.mydoorlock.";

	@Override
	public JSONArray selectDoorLockList(AppMyDoorLockVO vo) {
		// TODO Auto-generated method stub
		logSetter.setSystemLog(
			logSetter.builder(
				AppContextKey.LOG_STATUS_DOORLOCK_LIST,
				vo.getEmail(), 
				null, 
				null, 
				null
			)
		);

		return JSONArray.fromObject(sqlSession.selectList(NS + "selectMyDoorLockList", vo));
	}

	// 도어락에 등록된 유저를 전부 검색해서 제이슨 객체 배열로 만든다.
	@Override
	public JSONArray selectKeyList(AppMyDoorLockVO vo) {
		// TODO Auto-generated method stub
		logger.info(JSONObject.fromObject(vo).toString());
		logSetter.setSystemLog(logSetter.builder(AppContextKey.LOG_STATUS_DOORLOCK_KEY_LIST,
				vo.getEmail(), // 확인해봐야함.
				null, vo.getSerial_no(), null));
		return JSONArray.fromObject(sqlSession.selectList(NS + "selectKeyList", vo));
	}

	// 키 등록시 유저를 검색하는 기능이다.
	@Override
	public JSONObject selectSearchMember(AppMyDoorLockVO vo) {
		JSONObject result = new JSONObject();
		if (sqlSession.selectOne(NS + "selectSearchMember", vo) == null) {
			result.put(AppContextKey.AJAX_RESULT, AppContextKey.AJAX_FAIL);
			result.put(AppContextKey.RESULT_MSG, AppContextKey.MSG_KEY_MEMBER_FAIL);
			return result;
		}
		return JSONObject.fromObject(sqlSession.selectOne(NS + "selectSearchMember", vo));
	}

	// 도어락에 유저를 새로 등록한다.
	@Override
	public JSONObject selectOneInsert(AppMyDoorLockVO vo) {
		logger.info(JSONObject.fromObject(vo).toString());
		
		JSONObject result = new JSONObject();
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setName(this.getClass().getName());
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = platformTransactionManager.getTransaction(def);

		try {
			if ((Integer) sqlSession.selectOne(NS + "selectOneCheck", vo) != 1) {
				vo.setState("GRANTING");
				vo.setKey_id(ContextUtil.generateKey());
				
				System.out.println("key_name : " + vo.getKey_name());
				System.out.println("Serial_no : " + vo.getSerial_no());
				System.out.println("key_id : "+ vo.getKey_id());
			
				
				Integer sort = (Integer)sqlSession.selectOne(NS + "selectOneInsert_0", vo);
				if(sort == null){
					sort = 0;
				} 
				
				vo.setSort(sort);
				
				if (sqlSession.insert(NS + "selectOneInsert_1", vo) != 1) {
					throw new Exception("selectOneInsert_1");
				}
				if (sqlSession.insert(NS + "selectOneInsert_2", vo) != 1) {
					throw new Exception("selectOneInsert_2");
				}
				result.put(AppContextKey.AJAX_RESULT, AppContextKey.AJAX_SUCCESS);
			} else {
				result.put(AppContextKey.AJAX_RESULT, AppContextKey.AJAX_FAIL);
				result.put(AppContextKey.RESULT_MSG, AppContextKey.MSG_KEY_REGISTER_CHECK_FAIL);
				logSetter.setDoorlockLog(logSetter.builder(AppContextKey.LOG_STATUS_DOORLOCK_KEY_CREATE_FAIL,
						vo.getCrt_email(), vo.getMember_email(), vo.getSerial_no(), "[" + vo.getGrade() + "] "));
				logSetter.setDoorlockLog(logSetter.builder(AppContextKey.LOG_STATUS_DOORLOCK_KEY_CREATE_REQUEST_FAIL,
						vo.getCrt_email(), vo.getMember_email(), vo.getSerial_no(), "[" + vo.getGrade() + "] "));
				platformTransactionManager.rollback(status);
				return result;
			}

		} catch (Exception e) {
			result.put(AppContextKey.AJAX_RESULT, AppContextKey.AJAX_FAIL);
			result.put(AppContextKey.RESULT_MSG, AppContextKey.MSG_KEY_REGISTER_INSERT_FAIL);
			logSetter.setDoorlockLog(logSetter.builder(AppContextKey.LOG_STATUS_DOORLOCK_KEY_CREATE_FAIL,
					vo.getCrt_email(), vo.getMember_email(), vo.getSerial_no(), "[" + vo.getGrade() + "] "));
			logSetter.setDoorlockLog(logSetter.builder(AppContextKey.LOG_STATUS_DOORLOCK_KEY_CREATE_REQUEST_FAIL,
					vo.getCrt_email(), vo.getMember_email(), vo.getSerial_no(), "[" + vo.getGrade() + "] "));
			platformTransactionManager.rollback(status);
			e.printStackTrace();
			return result;
		}
		logSetter.setNewspeed(logSetter.builder(AppContextKey.LOG_STATUS_DOORLOCK_KEY_CREATE, vo.getCrt_email(),
				vo.getMember_email(), vo.getSerial_no(), "[" + vo.getGrade() + "] "), null);
		logSetter.setNewspeed(
				logSetter.builder(AppContextKey.LOG_STATUS_DOORLOCK_KEY_CREATE_REQUEST, vo.getCrt_email(),
						vo.getMember_email(), vo.getSerial_no(), "[" + vo.getGrade() + "] "),
				vo.getMember_email());
		platformTransactionManager.commit(status);
		return result;
	}

	/*
	 * 쓰고 있지 않음 /* 키 거부시 발생하는 메소드
	 */
	@Override
	public JSONObject deleteDenyKey(AppMyDoorLockVO vo) {
		logger.info(JSONObject.fromObject(vo).toString());
		
		JSONObject result = new JSONObject();


		try {
			if (sqlSession.delete(NS + "deleteDenyKey", vo) != 1) {
				throw new Exception("deleteDenyKey");
			}
			result.put(AppContextKey.AJAX_RESULT, AppContextKey.AJAX_SUCCESS);
		} catch (Exception e) {
			result.put(AppContextKey.AJAX_RESULT, AppContextKey.AJAX_FAIL);
			result.put(AppContextKey.RESULT_MSG, AppContextKey.MSG_KEY_DELETE_FAIL);
			e.printStackTrace();
			return result;
		}
		return result;
	}

	/* 도어락에 등록된 키를 삭제 할 때 처리를 위한 메소드 */
	@Override
	public JSONObject deleteKey(AppMyDoorLockVO vo) {
		logger.info(JSONObject.fromObject(vo).toString());
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setName(this.getClass().getName());
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = platformTransactionManager.getTransaction(def);
		
		JSONObject result = new JSONObject();

		try {
			result.put(AppContextKey.AJAX_RESULT, AppContextKey.AJAX_SUCCESS);
			logSetter.setNewspeed(
				logSetter.builder(
					AppContextKey.LOG_STATUS_DOORLOCK_KEY_DELETE, 
					vo.getEmail(),
					vo.getMember_email(),
					vo.getSerial_no(),
					null
				),
				null
			);
			

			if (sqlSession.delete(NS + "deleteKey_1", vo) != 1) {
				throw new Exception("deleteKey1");
			}
			if (sqlSession.delete(NS + "deleteKey_2", vo) != 1) {
				throw new Exception("deleteKey2");
			}
			sqlSession.delete(NS + "deleteKey_3", vo);
			sqlSession.delete(NS + "deleteKey_4", vo);
			
			
			

		} catch (Exception e) {
			result.put(AppContextKey.AJAX_RESULT, AppContextKey.AJAX_FAIL);
			result.put(AppContextKey.RESULT_MSG, AppContextKey.MSG_KEY_DELETE_FAIL);
			platformTransactionManager.rollback(status);
			logSetter.setNewspeed(
				logSetter.builder(
					AppContextKey.LOG_STATUS_DOORLOCK_KEY_DELETE_FAIL, 
					vo.getEmail(),
					vo.getMember_email(),
					vo.getSerial_no(), 
					null
				),
				null
			);
			e.printStackTrace();
			return result;
		}
		platformTransactionManager.commit(status);
		return result;
	}

	/* 도어락 이름 변경을 처리하기 위해 만든 메소드 */
	@Override
	public JSONObject updateDoorLockName(AppMyDoorLockVO vo) {
		JSONObject result = new JSONObject();

		AppMyDoorLockVO key = (AppMyDoorLockVO) sqlSession.selectOne(NS+"getKey", vo);
		
		try {
			if (sqlSession.update(NS + "updateMydoorLockName", vo) != 1) {
				throw new Exception("updateMydoorLockName");
			}
			result.put(AppContextKey.AJAX_RESULT, AppContextKey.AJAX_SUCCESS);
			logSetter.setNewspeed(
				logSetter.builder(
					AppContextKey.LOG_STATUS_DOORLOCK_UPDATE, 
					vo.getEmail(),
					null,
					vo.getSerial_no(), 
					"["+key.getDoorlock_name()+"" + vo.getDoorlock_name() + "] "
				),
				"ALL"
			);
			
			return result;

		} catch (Exception e) {
			e.printStackTrace();
			result.put(AppContextKey.AJAX_RESULT, AppContextKey.AJAX_FAIL);
			result.put(AppContextKey.RESULT_MSG, AppContextKey.MSG_DOORLOCK_NAME_UPDATE_FAIL);
			logSetter.setDoorlockLog(
				logSetter.builder(
					AppContextKey.LOG_STATUS_DOORLOCK_UPDATE_FAIL, 
					vo.getEmail(),
					null,
					vo.getSerial_no(), 
					"["+key.getDoorlock_name()+"" + vo.getDoorlock_name() + "] "
				)
			);
			
			return result;
		}
	}

	/* 도어락 설치장소 변경을 위한 메소드 */
	@Override
	public JSONObject updateDoorLockPlace(AppMyDoorLockVO vo) {
		
		JSONObject result = new JSONObject();
		AppMyDoorLockVO key = (AppMyDoorLockVO) sqlSession.selectOne(NS+"getKey", vo);
		try {
			if (sqlSession.update(NS + "updateDoorLockPlace", vo) != 1) {
				throw new Exception("updateDoorLockPlace");
			}
			result.put(AppContextKey.AJAX_RESULT, AppContextKey.AJAX_SUCCESS);
			logSetter.setNewspeed(
				logSetter.builder(
					AppContextKey.LOG_STATUS_DOORLOCK_UPDATE, 
					vo.getEmail(),
					null,
					vo.getSerial_no(), 
					"["+key.getInstalled_place()+"=>"+vo.getInstalled_place()+"]"
				),
				"ALL"
			);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result.put(AppContextKey.AJAX_RESULT, AppContextKey.AJAX_FAIL);
			result.put(AppContextKey.RESULT_MSG, AppContextKey.MSG_DOORLOCK_PLACE_UPDATE_FAIL);
			logSetter.setDoorlockLog(
				logSetter.builder(
					AppContextKey.LOG_STATUS_DOORLOCK_UPDATE_FAIL, 
					vo.getEmail(),
					null,
					vo.getSerial_no(), 
					"["+key.getInstalled_place()+"=>"+vo.getInstalled_place()+"]"
				)
			);
			return result;
		}
	}

	/* 내 도어락 삭제를 위한 메소드 처리 */
	@Override
	public JSONObject deleteMyDoorLock(AppMyDoorLockVO vo) {
		JSONObject result = new JSONObject();
		
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setName(this.getClass().getName());
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = platformTransactionManager.getTransaction(def);
		try{
			
			int num =(Integer)sqlSession.selectOne(NS+"deleteDoorlock1",vo);
			if(num!=0){
				throw new Exception("");
			}
			logSetter.setNewspeed(
				logSetter.builder(
					AppContextKey.LOG_STATUS_DOORLOCK_DELETE, 
					vo.getEmail(),
					null,
					vo.getSerial_no(), 
					null
				),
				"ALL"
			);
			num = sqlSession.delete(NS+"deleteDoorlock2",vo);
			if(num!=1){
				throw new Exception("");
			}
			num = sqlSession.delete(NS+"deleteDoorlock3",vo);
			if(num!=1){
				throw new Exception("");
			}
			num = sqlSession.update(NS+"updateDoorLockState",vo);					
			if(num!=1){
				throw new Exception("");
			}
			sqlSession.delete(NS+"deleteDoorlock4",vo);
			
			result.put(AppContextKey.AJAX_RESULT, AppContextKey.AJAX_SUCCESS);
			platformTransactionManager.commit(status);
			return result;
		}catch(Exception e){
			result.put(AppContextKey.AJAX_RESULT, AppContextKey.AJAX_FAIL);
			result.put(AppContextKey.RESULT_MSG, AppContextKey.MSG_DOORLOCK_DELETE_FAIL);
			platformTransactionManager.rollback(status);
			logSetter.setNewspeed(
				logSetter.builder(
					AppContextKey.LOG_STATUS_DOORLOCK_DELETE_FAIL, 
					vo.getEmail(),
					null,
					vo.getSerial_no(), 
					null
				),
				"ALL"
			);
			e.printStackTrace();
			return result;
		}
		
	}

}
