package com.sl.web.doorlock.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.sl.system.common.util.ContextUtil;
import com.sl.system.gcm.NotificationService;
import com.sl.system.log.setter.SystemLogSetter;
import com.sl.system.log.setter.SystemLogSetterVO;
import com.sl.web.common.context.ContextKey;
import com.sl.web.doorlock.vo.WebDoorlockVO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Repository
public class WebDoorlockDAOImpl implements WebDoorlockDAO {
	
	@Autowired
	private SqlSession sqlSession;
	
	@Autowired
	SystemLogSetter logSetter;

	@Autowired
	PlatformTransactionManager platformTransactionManager;

	@Autowired
	NotificationService GCM;
			
	private final String NS = "mapper.com.sl.web.doorlock.";
	
	@Override
	public List<WebDoorlockVO> doorlockList(WebDoorlockVO vo){
		logSetter.setSystemLog(
			logSetter.builder(
				ContextKey.LOG_STATUS_DOORLOCK_LIST,
				vo.getEmail(),
				null,
				null,
				null
			)
		);
		return sqlSession.selectList(NS+"doorlockList",vo);
	}
	
	@Override
	public JSONObject doorlockDetail(WebDoorlockVO vo){
		JSONObject ob = new JSONObject();
		ob = JSONObject.fromObject(sqlSession.selectOne(NS+"doorlockDetail",vo));
		System.out.println(ob);
		logSetter.setSystemLog(
			logSetter.builder(
				ContextKey.LOG_STATUS_DOORLOCK_INFO,
				vo.getEmail(),
				null,
				vo.getSerial_no(),
				null
			)
		);
		return ob;
	}
	
	@Override
	public JSONObject doorlockCreateCheck(WebDoorlockVO vo) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();  
		
		if((Integer)sqlSession.selectOne(NS+"doorlockCreateCheck",vo)==1){
			result.put(ContextKey.AJAX_RESULT, ContextKey.AJAX_SUCCESS);
			return result;
		}else{
			result.put(ContextKey.AJAX_RESULT, ContextKey.AJAX_FAIL);
			result.put(ContextKey.RESULT_MSG, ContextKey.MSG_DOORLOCK_REGISTER_CHECK_FAIL);
			return result;
		}
	}
	
	
	
	@Override
	public JSONObject doorlockCreate(WebDoorlockVO vo) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setName(this.getClass().getName());
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = platformTransactionManager.getTransaction(def);
		
		
		
		try{
			if(Integer.valueOf((Integer)sqlSession.selectOne(NS+"doorlockCreate_0",vo))>=1){
				throw new Exception("doorlockCreate");
			}
			if(sqlSession.update(NS+"doorlockCreate_1",vo)!=1){
				throw new Exception("doorlockCreate_1");
			}
			WebDoorlockVO vo_2 = (WebDoorlockVO) sqlSession.selectOne(NS+"doorlockCreate_2",vo);
			if(vo_2==null){
				throw new Exception("doorlockCreate_2");	
			}else{
				vo.setType(vo_2.getType());	
			}
			
			Object obj_3 = sqlSession.selectOne(NS+"doorlockCreate_3",vo);
			int sort_3 = 0;
			if(obj_3==null){
				sort_3 = 0;			
			}else{
				sort_3 = (Integer) obj_3;
				vo.setSort(sort_3);
			}
			
			if(sqlSession.insert(NS+"doorlockCreate_4",vo)!=1){
				throw new Exception("doorlockCreate_4");
			}
			
			Object obj_5 = sqlSession.selectOne(NS+"doorlockCreate_5",vo);
			int sort_5 = 0;
			if(obj_5==null){
				sort_5 = 0;		
			}else{
				sort_5 = (Integer) obj_5;
				vo.setSort(sort_5);
			}
			
			vo = this.recursiveQuery(vo);
			
			
			
			
			if(sqlSession.insert(NS+"doorlockCreate_7",vo)!=1){
				throw new Exception("doorlockCreate_7");
			}
			if(sqlSession.insert(NS+"doorlockCreate_8",vo)!=1){
				throw new Exception("doorlockCreate_8");
			}
			
			WebDoorlockVO vo_8 = (WebDoorlockVO) sqlSession.selectOne(NS+"doorlockCreate_9",vo);
			if(vo_8==null){
				throw new Exception("doorlockCreate_9");	
			}else{
				vo.setState_name(vo_8.getState_name());
				vo.setCrt_dt(vo_8.getCrt_dt());	
			}
			
			result=JSONObject.fromObject(vo);
			result.put(ContextKey.AJAX_RESULT,ContextKey.AJAX_SUCCESS);
			platformTransactionManager.commit(status);
			

		}catch(Exception e){
			result.put(ContextKey.AJAX_RESULT,ContextKey.AJAX_FAIL);
			result.put(ContextKey.RESULT_MSG,ContextKey.MSG_DOORLOCK_REGISTER_INSERT_FAIL);
			platformTransactionManager.rollback(status);
			e.printStackTrace();
			return result;
		}
		logSetter.setNewspeed(
			logSetter.builder(
				ContextKey.LOG_STATUS_DOORLOCK_CREATE,
				vo.getEmail(),
				vo.getEmail(),
				vo.getSerial_no(),
				null
			),vo.getEmail()
		);
		logSetter.setNewspeed(
			logSetter.builder(
				ContextKey.LOG_STATUS_DOORLOCK_CREATE_MASTER,
				vo.getEmail(),
				vo.getEmail(),
				vo.getSerial_no(),
				null
			),vo.getEmail()
		);
		
		return result;
	}

	
	//이거 .. 그닥 좋지 못한 모델임... 
	//재귀 호출
	public WebDoorlockVO recursiveQuery(WebDoorlockVO vo){
		vo.setKey_id(ContextUtil.generateKey());
		if((Integer)sqlSession.selectOne(NS+"doorlockCreate_6",vo)!=0){
			this.recursiveQuery(vo);
		}
		return vo;
	}

	
	@Override
	public int doorlockUpdate(WebDoorlockVO vo){
		int gab;
		gab = sqlSession.update(NS+"updateDoorlock",vo);
		return gab;
	}
	
	@Override
	public JSONObject doorlockDelete(WebDoorlockVO vo) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setName(this.getClass().getName());
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = platformTransactionManager.getTransaction(def);
		try{
			int num =(Integer)sqlSession.selectOne(NS+"deleteDoorlock1",vo);
			
			if(num!=0){
				result.put(ContextKey.RESULT_CODE,ContextKey.RESULT_CODE_2);
				result.put("count", num);
				
				platformTransactionManager.rollback(status);
				return result;
			}
			num = sqlSession.delete(NS+"deleteDoorlock2",vo);
			if(num!=1){
				result.put(ContextKey.RESULT_CODE,ContextKey.RESULT_CODE_3);
				platformTransactionManager.rollback(status);
				return result;
			}
			num = sqlSession.delete(NS+"deleteDoorlock3",vo);
			if(num!=1){
				result.put(ContextKey.RESULT_CODE,ContextKey.RESULT_CODE_3);
				platformTransactionManager.rollback(status);
				return result;
			}
			num = sqlSession.delete(NS+"deleteDoorlock4",vo);
			if(num!=1){
				result.put(ContextKey.RESULT_CODE,ContextKey.RESULT_CODE_3);
				platformTransactionManager.rollback(status);
				return result;
			}

			result.put(ContextKey.RESULT_CODE, ContextKey.RESULT_CODE_1);
			platformTransactionManager.commit(status);
		}catch(Exception e){
			result.put(ContextKey.RESULT_CODE,ContextKey.RESULT_CODE_4);
			platformTransactionManager.rollback(status);
			e.printStackTrace();
			return result;
		}
		return result;
	}

	@Override
	public List<String> getEmail(String serial_no) {
		// TODO Auto-generated method stub
		return sqlSession.selectList(NS+"getEmailList",serial_no);
	}

	@Override
	public JSONArray selectListDoorlocklogs(SystemLogSetterVO vo) {
		// TODO Auto-generated method stub
		return JSONArray.fromObject(sqlSession.selectList(NS+"selectListDoorlocklogs",vo));
	}
	
}
