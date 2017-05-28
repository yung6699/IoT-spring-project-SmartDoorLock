package com.sl.web.home.dao;

import java.net.URLEncoder;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.sl.system.common.util.AES256Util;
import com.sl.system.common.util.ContextUtil;
import com.sl.system.log.setter.SystemLogSetter;
import com.sl.web.common.context.ContextKey;
import com.sl.web.home.vo.WebHomeVO;

import net.sf.json.JSONObject;

@Repository
public class WebHomeDAOImpl implements WebHomeDAO{
	
	@Autowired 
	private SqlSession sqlSession;
	
	@Autowired
	PlatformTransactionManager platformTransactionManager;
	
	@Autowired
	private SystemLogSetter logSetter;

	Logger logger = LoggerFactory.getLogger(this.getClass());

	final String NS = "mapper.com.sl.web.home.";
	
	@Override
	public JSONObject login(WebHomeVO vo) {
		// TODO Auto-generated method stub
		JSONObject obj = new JSONObject();
		try{
			vo.setPassword(ContextUtil.AES_EncodeMaker(vo.getPassword()));
		}catch(Exception e){
			e.printStackTrace();
			obj.put(ContextKey.AJAX_RESULT, ContextKey.AJAX_FAIL);
			obj.put(ContextKey.RESULT_MSG, ContextKey.MSG_LOGIN_FAIL);
			
			logSetter.setSystemLog(
				logSetter.builder(
					ContextKey.LOG_STATUS_ACCOUNT_LOGIN_FAIL,
					vo.getEmail(),
					null,
					null,
					null
				)
			);
			return obj;
		}
		if((Integer) sqlSession.selectOne(NS+"checkMember",vo)==1){
			obj.put(ContextKey.AJAX_RESULT, ContextKey.AJAX_SUCCESS);
			logSetter.setSystemLog(
				logSetter.builder(
					ContextKey.LOG_STATUS_ACCOUNT_LOGIN,
					vo.getEmail(),
					null,
					null,
					null
				)
			);
			return obj;
		}else{
			obj.put(ContextKey.AJAX_RESULT, ContextKey.AJAX_FAIL);
			obj.put(ContextKey.RESULT_MSG, ContextKey.MSG_LOGIN_FAIL);
			logSetter.setSystemLog(
				logSetter.builder(
					ContextKey.LOG_STATUS_ACCOUNT_LOGIN_FAIL,
					vo.getEmail(),
					null,
					null,
					null
				)
			);
			return obj;
		}
	}

	@Override
	public JSONObject joinEmail(WebHomeVO vo) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();  
		if((Integer)sqlSession.selectOne(NS+"getCheckEmail",vo)==0){
			result.put(ContextKey.AJAX_RESULT, ContextKey.AJAX_SUCCESS);
			result.put(ContextKey.RESULT_MSG, ContextKey.MSG_JOIN_EMAIL_CEHCK_EMAIL_SUCCESS);
			return result;
		}else{
			result.put(ContextKey.AJAX_RESULT, ContextKey.AJAX_FAIL);
			result.put(ContextKey.RESULT_MSG, ContextKey.MSG_JOIN_EMAIL_CEHCK_EMAIL_FAIL);
			return result;
		}
	}

	@Override
	public JSONObject joinAccount(WebHomeVO vo) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			vo.setPassword(ContextUtil.AES_EncodeMaker(vo.getPassword()));
		}catch(Exception e){
			e.printStackTrace();
			logSetter.setSystemLog(
				logSetter.builder(
					ContextKey.LOG_STATUS_ACCOUNT_CREATE_FAIL,
					vo.getEmail(),
					null,
					null,
					null
				)
			);
		}
		
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setName(WebHomeDAOImpl.class.getName());
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = platformTransactionManager.getTransaction(def);
		
		try{
			if(Integer.valueOf((Integer)sqlSession.selectOne(NS+"getCheckEmail",vo))!=0){
				result.put(ContextKey.AJAX_RESULT,ContextKey.AJAX_FAIL);
				result.put(ContextKey.RESULT_MSG,ContextKey.MSG_JOIN_EMAIL_CEHCK_EMAIL_FAIL);
				logSetter.setSystemLog(
					logSetter.builder(
						ContextKey.LOG_STATUS_ACCOUNT_CREATE_FAIL,
						vo.getEmail(),
						null,
						null,
						null
					)
				);
			}else{
				if(sqlSession.insert(NS+"setCompleteJoin",vo)!=0){
					//result.put(ContextKey.RESULT_MSG, "["+vo.getEmail()+"]"+ContextKey.MSG_JOIN_COMPLETE_SUCCESS);
					result.put(ContextKey.AJAX_RESULT, ContextKey.AJAX_SUCCESS);
					result.put(ContextKey.RESULT_MSG,ContextKey.MSG_JOIN_SUCCESS);
					result.put("member_id", (Integer)sqlSession.selectOne(NS+"getCurrentMember_id"));
					platformTransactionManager.commit(status);
					logSetter.setSystemLog(
						logSetter.builder(
							ContextKey.LOG_STATUS_ACCOUNT_CREATE,
							vo.getEmail(),
							null,
							null,
							null
						)
					);
				}else{
					//result.put(ContextKey.RESULT_MSG, "["+vo.getEmail()+"]"+ContextKey.MSG_JOIN_COMPLETE_FAIL);
					result.put(ContextKey.AJAX_RESULT,ContextKey.AJAX_FAIL);
					result.put(ContextKey.RESULT_MSG,ContextKey.MSG_JOIN_FAIL);
					platformTransactionManager.rollback(status);
					logSetter.setSystemLog(
						logSetter.builder(
							ContextKey.LOG_STATUS_ACCOUNT_CREATE_FAIL,
							vo.getEmail(),
							null,
							null,
							null
						)
					);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			platformTransactionManager.rollback(status);
		}
		//메일 인증을 보내는 곳
		return result;				
	}

	@Override
	public JSONObject findAccount(WebHomeVO vo) {
		// TODO Auto-generated method stub
		JSONObject obj = new JSONObject();
		try{
			vo.setPassword(ContextUtil.AES_EncodeMaker(vo.getPassword()));
		}catch(Exception e){
			e.printStackTrace();
			logSetter.setSystemLog(
				logSetter.builder(
					ContextKey.LOG_STATUS_ACCOUNT_FIND_FAIL,
					vo.getEmail(),
					null,
					null,
					null
				)
			);
		}
		if(sqlSession.update(NS+"updateFindMember",vo)==1){
			obj.put(ContextKey.AJAX_RESULT,ContextKey.AJAX_SUCCESS);
			obj.put(ContextKey.RESULT_MSG, "["+vo.getEmail() + ContextKey.MSG_FIND_ACCOUNT_SUCCESS);
			logSetter.setSystemLog(
				logSetter.builder(
					ContextKey.LOG_STATUS_ACCOUNT_FIND,
					vo.getEmail(),
					null,
					null,
					null
				)
			);
		}else{
			obj.put(ContextKey.AJAX_RESULT,ContextKey.AJAX_SUCCESS);
			obj.put(ContextKey.RESULT_MSG, "["+vo.getEmail() + ContextKey.MSG_FIND_ACCOUNT_FAIL);
			logSetter.setSystemLog(
				logSetter.builder(
					ContextKey.LOG_STATUS_ACCOUNT_FIND_FAIL,
					vo.getEmail(),
					null,
					null,
					null
				)
			);
		}
		return obj;
	}
}
