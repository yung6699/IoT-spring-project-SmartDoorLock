package com.sl.web.myinfo.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.sl.app.common.context.AppContextKey;
import com.sl.system.common.util.ContextUtil;
import com.sl.system.log.setter.SystemLogSetter;
import com.sl.system.session.member.SessionMember;
import com.sl.web.common.context.ContextKey;
import com.sl.web.myinfo.vo.WebMyInfoVO;

import net.sf.json.JSONObject;

@Repository
public class WebMyInfoDAOImpl implements WebMyInfoDAO{

	
	@Autowired
	PlatformTransactionManager platformTransactionManager;
	
	@Autowired
	SystemLogSetter logSetter;
	
	@Autowired
	private SqlSession sqlSession;
	
	@Autowired
	private SessionMember sessionMember;
	
	final String NS = "mapper.com.sl.web.myinfo.";
	
	@Override
	public JSONObject updateSubmitMyInfo(WebMyInfoVO vo) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		try{
			vo.setPassword(ContextUtil.AES_EncodeMaker(vo.getPassword()));
		}catch(Exception e){
			e.printStackTrace();
			logSetter.setSystemLog(
				logSetter.builder(
					ContextKey.LOG_STATUS_ACCOUNT_UPDATE_FAIL,
					vo.getEmail(),
					null,
					null,
					null
				)
			);
			
		}
		if(sqlSession.update(NS+"updateSubmitMyInfo",vo)==1){
			result.put(ContextKey.LOGIN_MEMBER,sessionMember.getSessionMember(JSONObject.fromObject(vo)));
			result.put(ContextKey.AJAX_RESULT,ContextKey.AJAX_SUCCESS);
			logSetter.setSystemLog(
				logSetter.builder(
					ContextKey.LOG_STATUS_ACCOUNT_UPDATE,
					vo.getEmail(),
					null,
					null,
					null
				)
			);
			return result;
		}else{
			result.put(ContextKey.AJAX_RESULT,ContextKey.AJAX_FAIL);
			logSetter.setSystemLog(
				logSetter.builder(
					ContextKey.LOG_STATUS_ACCOUNT_UPDATE_FAIL,
					vo.getEmail(),
					null,
					null,
					null
				)
			);
			return result;
		}
	}

	@Override
	public JSONObject updateDeleteMyInfo(WebMyInfoVO vo) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setName(WebMyInfoDAOImpl.class.getName());
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = platformTransactionManager.getTransaction(def);
		
		try{
			if(Integer.valueOf((Integer)sqlSession.delete(NS+"updateDeleteMyInfo1",vo))==1){
				List<String> doorlockList = sqlSession.selectList(NS+"updateDeleteMyInfo0",vo.getEmail());
				for(String serial_no : doorlockList){
					logSetter.setNewspeed(
						logSetter.builder(
							ContextKey.LOG_STATUS_ACCOUNT_DELETE,
							vo.getEmail(),
							null,
							serial_no,
							null
						), null
					);
				}
				sqlSession.delete(NS+"updateDeleteMyInfo2",vo);
				sqlSession.delete(NS+"updateDeleteMyInfo3",vo);
				sqlSession.delete(NS+"updateDeleteMyInfo4",vo);
				sqlSession.delete(NS+"updateDeleteMyInfo5",vo);
				sqlSession.delete(NS+"updateDeleteMyInfo6",vo);
				
			}else{
				throw new Exception("updateDeleteMyInfo1");
			}		
			result=JSONObject.fromObject(vo);
			result.put(AppContextKey.AJAX_RESULT,AppContextKey.AJAX_SUCCESS);
			platformTransactionManager.commit(status);
			
		}catch(Exception e){
			result.put(AppContextKey.AJAX_RESULT,AppContextKey.AJAX_FAIL);
			platformTransactionManager.rollback(status);
			e.printStackTrace();
			logSetter.setSystemLog(
				logSetter.builder(
					ContextKey.LOG_STATUS_ACCOUNT_DELETE_FAIL,
					vo.getEmail(),
					null,
					null,
					null
				)
			);
			return result;
		}
		return result;
	}

}