package com.sl.app.access.join.dao;
 
import org.apache.ibatis.session.SqlSession; 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.sl.app.access.join.vo.AppAccessJoinVO;
import com.sl.app.common.context.AppContextKey;
import com.sl.system.log.setter.SystemLogSetter;
import com.sl.system.log.setter.SystemLogSetterVO;

import net.sf.json.JSONObject;

@Repository
public class AppAccessJoinDAOImpl implements AppAccessJoinDAO{

	@Autowired 
	private SqlSession sqlSession;
	
	@Autowired
	PlatformTransactionManager platformTransactionManager;
	
	@Autowired
	private SystemLogSetter logSetter;
	
	// end must be point .  
	final String NS = "mapper.com.sl.app.access.join.";
	private static final Logger logger = LoggerFactory.getLogger(AppAccessJoinDAOImpl.class);
	
	@Override
	public JSONObject getCheckEmail(AppAccessJoinVO vo) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();  
		if((Integer)sqlSession.selectOne(NS+"getCheckEmail",vo)==0){
			result.put(AppContextKey.AJAX_RESULT, AppContextKey.AJAX_SUCCESS);
			result.put(AppContextKey.RESULT_MSG, AppContextKey.MSG_JOIN_EMAIL_CEHCK_EMAIL_SUCCESS);
			return result;
		}else{
			result.put(AppContextKey.AJAX_RESULT, AppContextKey.AJAX_FAIL);
			result.put(AppContextKey.RESULT_MSG, AppContextKey.MSG_JOIN_EMAIL_CEHCK_EMAIL_FAIL);
			return result;
		}
	}

	@Override
	public JSONObject setCompleteJoin(AppAccessJoinVO vo) {
		// TODO Auto-generated method stub
		logger.info("setCompleteJoin을 실행합니다. ",vo );
		JSONObject result = new JSONObject();
		
		
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setName(AppAccessJoinDAOImpl.class.getName());
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = platformTransactionManager.getTransaction(def);
		
		
		
		try{
			if(Integer.valueOf((Integer)sqlSession.selectOne(NS+"getCheckEmail",vo))!=0){
				result.put(AppContextKey.AJAX_RESULT,AppContextKey.AJAX_FAIL);
				result.put(AppContextKey.RESULT_MSG,AppContextKey.MSG_JOIN_EMAIL_CEHCK_EMAIL_FAIL);
				
			}else{
				if(sqlSession.insert(NS+"setCompleteJoin",vo)!=0){
					//result.put(AppContextKey.RESULT_MSG, "["+vo.getEmail()+"]"+AppContextKey.MSG_JOIN_COMPLETE_SUCCESS);
					result.put(AppContextKey.AJAX_RESULT, AppContextKey.AJAX_SUCCESS);
					result.put(AppContextKey.RESULT_MSG,AppContextKey.MSG_JOIN_SUCCESS);
					result.put("member_id", (Integer)sqlSession.selectOne(NS+"getCurrentMember_id"));
					logSetter.setSystemLog(
						logSetter.builder(
							AppContextKey.LOG_STATUS_ACCOUNT_CREATE, 
							vo.getEmail(), 
							null,
							null,
							null
						)
					);
					platformTransactionManager.commit(status);
				}else{
					//result.put(AppContextKey.RESULT_MSG, "["+vo.getEmail()+"]"+AppContextKey.MSG_JOIN_COMPLETE_FAIL);
					result.put(AppContextKey.AJAX_RESULT,AppContextKey.AJAX_FAIL);
					result.put(AppContextKey.RESULT_MSG,AppContextKey.MSG_JOIN_FAIL);
					logSetter.setSystemLog(
						logSetter.builder(
							AppContextKey.LOG_STATUS_ACCOUNT_CREATE_FAIL, 
							vo.getEmail(), 
							null,
							null,
							null
						)
					);
					platformTransactionManager.rollback(status);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			platformTransactionManager.rollback(status);
			logSetter.setSystemLog(
				logSetter.builder(
					AppContextKey.LOG_STATUS_ACCOUNT_CREATE_FAIL, 
					vo.getEmail(), 
					null,
					null,
					null
				)
			);
		}
		//메일 인증을 보내는 곳
		return result;				
	}

	

	

}
