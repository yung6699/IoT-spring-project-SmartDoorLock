package com.sl.app.mykey.list.dao;

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
import com.sl.app.mykey.list.vo.AppMykeyListVO;
import com.sl.system.gcm.NotificationService;
import com.sl.system.log.setter.SystemLogSetter;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Repository
public class AppMykeyListDAOImpl implements AppMykeyListDAO {
	
	
	@Autowired
	private SqlSession sqlSession;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	PlatformTransactionManager platformTransactionManager;
	
	@Autowired
	NotificationService GCM;
	
	@Autowired
	SystemLogSetter logSetter;

	
	private final String NS = "mapper.com.sl.app.mykey.";

	
	@Override
	public JSONArray selectMyKeyList(AppMykeyListVO vo){
		
		return JSONArray.fromObject(sqlSession.selectList(NS+"selectMykeyList", vo));
	}

	
	
	@Override
	public JSONObject updateMyKeyName(AppMykeyListVO vo) {

		logger.info(JSONObject.fromObject(vo).toString());
		JSONObject result = new JSONObject();

		try {
			
			if (sqlSession.update(NS+"updateMyKeyName",vo)!=1){
				throw new Exception("updateMyKeyName"); 
			}
			result.put(AppContextKey.AJAX_RESULT,AppContextKey.AJAX_SUCCESS);
			logSetter.setSystemLog(logSetter.builder(AppContextKey.LOG_STATUS_KEY_UPDATE, vo.getEmail(),null,vo.getSerial_no(),null));
			return result;
			
		} catch (Exception e) {
			e.printStackTrace();
			result.put(AppContextKey.AJAX_RESULT,AppContextKey.AJAX_FAIL);
			result.put(AppContextKey.RESULT_MSG,AppContextKey.MSG_KEY_NAME_UPDATE_FAIL);
			logSetter.setSystemLog(logSetter.builder(AppContextKey.LOG_STATUS_KEY_UPDATE_FAIL, vo.getEmail(),null,vo.getSerial_no(),null));
			
			return result;
		}
	}
	
	
	/* 내 키 직접 삭제시 */
	@Override
	public JSONObject deleteMyKey(AppMykeyListVO vo) {
		JSONObject result = new JSONObject();
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setName(this.getClass().getName());
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = platformTransactionManager.getTransaction(def);
		try {
			if (sqlSession.delete(NS + "deleteKey_1", vo) != 1) {
				throw new Exception("deleteKey");
			}
			
			if (sqlSession.delete(NS + "deleteKey_2", vo) != 1) {
				throw new Exception("deleteKey");
			}
			sqlSession.delete(NS + "deleteKey_3", vo);
			sqlSession.delete(NS + "deleteKey_4", vo);

			result.put(AppContextKey.AJAX_RESULT, AppContextKey.AJAX_SUCCESS);
			platformTransactionManager.commit(status);
		} catch (Exception e) {
			result.put(AppContextKey.AJAX_RESULT, AppContextKey.AJAX_FAIL);
			result.put(AppContextKey.RESULT_MSG, AppContextKey.MSG_KEY_DELETE_FAIL);
			logSetter.setDoorlockLog(logSetter.builder(AppContextKey.LOG_STATUS_KEY_DELETE_FAIL, vo.getEmail(),null,vo.getSerial_no(),null));
			platformTransactionManager.rollback(status);
			e.printStackTrace();
			return result;
		}
		
		// 키 삭제 로그등록
		logSetter.setNewspeed(logSetter.builder(AppContextKey.LOG_STATUS_KEY_DELETE, vo.getEmail(),null,vo.getSerial_no(),null), null);
		logSetter.setNewspeed(logSetter.builder(AppContextKey.LOG_STATUS_KEY_DELETE, vo.getEmail(),null,vo.getSerial_no(),null), vo.getEmail());
		return result;
	}	

}
