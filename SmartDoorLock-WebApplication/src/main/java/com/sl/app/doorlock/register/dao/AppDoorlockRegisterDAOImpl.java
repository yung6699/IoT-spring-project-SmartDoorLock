package com.sl.app.doorlock.register.dao;

import java.util.Random;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.sl.app.access.join.dao.AppAccessJoinDAOImpl;
import com.sl.app.common.context.AppContextKey;
import com.sl.app.doorlock.register.vo.AppDoorlockRegisterVO;
import com.sl.system.common.util.ContextUtil;
import com.sl.system.log.setter.SystemLogSetter;
import com.sl.system.log.setter.SystemLogSetterVO;

import net.sf.json.JSONObject;

@Repository
public class AppDoorlockRegisterDAOImpl implements AppDoorlockRegisterDAO {

	@Autowired
	SqlSession sqlSession;
	final private String NS = "mapper.com.sl.app.doorlock.register.";
	
	@Autowired
	PlatformTransactionManager platformTransactionManager;
	
	@Autowired
	SystemLogSetter logSetter;
	
	
	@Override
	public JSONObject selectOneCheck(AppDoorlockRegisterVO vo) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();  
		
		if((Integer)sqlSession.selectOne(NS+"selectOneCheck",vo)==1){
			result.put(AppContextKey.AJAX_RESULT, AppContextKey.AJAX_SUCCESS);
			return result;
		}else{
			result.put(AppContextKey.AJAX_RESULT, AppContextKey.AJAX_FAIL);
			result.put(AppContextKey.RESULT_MSG, AppContextKey.MSG_DOORLOCK_REGISTER_CHECK_FAIL);
			return result;
		}
	}

	@Override
	public JSONObject selectOneInsert(AppDoorlockRegisterVO vo) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setName(AppAccessJoinDAOImpl.class.getName());
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = platformTransactionManager.getTransaction(def);
		
		
		
		try{
			if(Integer.valueOf((Integer)sqlSession.selectOne(NS+"selectOneCheck",vo))!=1){
				throw new Exception("selectOneCheck");
			}
			if(sqlSession.update(NS+"selectOneInsert_1",vo)!=1){
				throw new Exception("selectOneInsert_1");
			}
			AppDoorlockRegisterVO vo_2 = (AppDoorlockRegisterVO) sqlSession.selectOne(NS+"selectOneInsert_2",vo);
			if(vo_2==null){
				throw new Exception("selectOneInsert_2");	
			}else{
				vo.setType(vo_2.getType());	
			}
			
			Object obj_3 = sqlSession.selectOne(NS+"selectOneInsert_3",vo);
			int sort_3 = 0;
			if(obj_3==null){
				sort_3 = 0;			
			}else{
				sort_3 = (Integer) obj_3;
				vo.setSort(sort_3);
			}
			if(sqlSession.insert(NS+"selectOneInsert_4",vo)!=1){
				throw new Exception("selectOneInsert_4");
			}
			Object obj_5 = sqlSession.selectOne(NS+"selectOneInsert_5",vo);
			int sort_5 = 0;
			if(obj_5==null){
				sort_5 = 0;		
			}else{
				sort_5 = (Integer) obj_5;
				vo.setSort(sort_5);
			}
			vo = this.recursiveQuery(vo);
			if(sqlSession.insert(NS+"selectOneInsert_7",vo)!=1){
				throw new Exception("selectOneInsert_7");
			}
			if(sqlSession.insert(NS+"selectOneInsert_8",vo)!=1){
				throw new Exception("selectOneInsert_8");
			}
			
			AppDoorlockRegisterVO vo_8 = (AppDoorlockRegisterVO) sqlSession.selectOne(NS+"selectOneInsert_9",vo);
			if(vo_8==null){
				throw new Exception("selectOneInsert_9");	
			}else{
				vo.setState_name(vo_8.getState_name());
				vo.setCrt_dt(vo_8.getCrt_dt());	
			}
			
			result=JSONObject.fromObject(vo);
			result.put(AppContextKey.AJAX_RESULT,AppContextKey.AJAX_SUCCESS);
			platformTransactionManager.commit(status);
			
			logSetter.setNewspeed(
				logSetter.builder(
					AppContextKey.LOG_STATUS_DOORLOCK_CREATE,
					vo.getEmail(),
					null,
					vo.getSerial_no(),
					null
				),vo.getEmail()
			);
			
			logSetter.setNewspeed(
				logSetter.builder(
					AppContextKey.LOG_STATUS_DOORLOCK_CREATE_MASTER,
					vo.getEmail(),
					null,
					vo.getSerial_no(),
					null
				),vo.getEmail()
			);
			return result;

		}catch(Exception e){
			result.put(AppContextKey.AJAX_RESULT,AppContextKey.AJAX_FAIL);
			result.put(AppContextKey.RESULT_MSG,AppContextKey.MSG_DOORLOCK_REGISTER_INSERT_FAIL);
			platformTransactionManager.rollback(status);
			e.printStackTrace();
			logSetter.setSystemLog(
				logSetter.builder(
					AppContextKey.LOG_STATUS_DOORLOCK_CREATE_FAIL,
					vo.getEmail(),
					null,
					vo.getSerial_no(),
					null
				)
			);
			
			logSetter.setSystemLog(
				logSetter.builder(
					AppContextKey.LOG_STATUS_DOORLOCK_CREATE_MASTER_FAIL,
					vo.getEmail(),
					null,
					vo.getSerial_no(),
					null
				)
			);
			return result;
			
		}
		
	}

	//재귀 호출
	public AppDoorlockRegisterVO recursiveQuery(AppDoorlockRegisterVO vo){
		vo.setKey_id(ContextUtil.generateKey());
		if((Integer)sqlSession.selectOne(NS+"selectOneInsert_6",vo)!=0){
			this.recursiveQuery(vo);
		}
		return vo;
	}
}
