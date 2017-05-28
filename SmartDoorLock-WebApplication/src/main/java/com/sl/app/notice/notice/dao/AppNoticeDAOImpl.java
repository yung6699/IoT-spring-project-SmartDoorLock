package com.sl.app.notice.notice.dao;


import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.sl.app.access.join.dao.AppAccessJoinDAOImpl;
import com.sl.app.common.context.AppContextKey;
import com.sl.app.mydoorlock.key.vo.AppMyDoorLockVO;
import com.sl.app.notice.notice.controller.AppNoticeController;
import com.sl.app.notice.notice.vo.AppNoticeVO;
import com.sl.system.log.setter.SystemLogSetter;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Repository
public class AppNoticeDAOImpl implements AppNoticeDAO {
	private static final Logger logger = LoggerFactory.getLogger(AppNoticeController.class);
	@Autowired 
	private SqlSession sqlSession;
	private final String NS = "mapper.com.sl.app.notice.notice.";
	
	@Autowired
	PlatformTransactionManager platformTransactionManager;
	
	@Autowired
	SystemLogSetter logSetter;

	@Override
	public JSONArray selectUserNotice(AppNoticeVO vo) {
		logSetter.updateNewFlag(vo.getEmail());	// 뉴스피드에 들어오면, 모든 read_flag가 0이 된다.
		logSetter.setSystemLog(
			logSetter.builder(
				AppContextKey.LOG_STATUS_NEWSPEED_LIST,
				vo.getEmail(),
				null,
				null,
				null
			)
		);
		return JSONArray.fromObject(sqlSession.selectList(NS+"selectNoticeList",vo));
	}
	
	@Override
	public JSONObject responseUserNotice(AppNoticeVO vo){
		JSONObject result = new JSONObject();

		AppNoticeVO notice = (AppNoticeVO)sqlSession.selectOne(NS+"selectNotice",vo.getIdx());
		AppMyDoorLockVO key = (AppMyDoorLockVO) sqlSession.selectOne(NS+"selectKey",vo);
		
		logger.debug(JSONObject.fromObject(notice).toString());
		logger.debug(JSONObject.fromObject(vo).toString());
		
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setName(AppAccessJoinDAOImpl.class.getName());
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = platformTransactionManager.getTransaction(def);
		
		try{
			if(sqlSession.delete(NS+"deleteNotice",vo.getIdx())!=1){
				throw new Exception("");
			}
			
			logger.debug(vo.getAnswer());
			if(vo.getAnswer().equals("ACCEPT")){
				if(sqlSession.update(NS+"updateStateKey",key)!=1){
					throw new Exception("");
				}
				logSetter.setNewspeed(
					logSetter.builder(
						AppContextKey.LOG_STATUS_NEWSPEED_KEY_RESPONSE_ACCEPT, 
						vo.getSend_email(), 
						vo.getRecv_email(),
						vo.getSerial_no(),
						key.getGrade()
					),"ALL"
				);
			}else if(vo.getAnswer().equals("REFUSE")){
				//key_val_time 삭제
				if(sqlSession.delete(NS+"refuseKey_1",key)!=1){
					throw new Exception("");
				}
				//key_mst 삭제
				if(sqlSession.delete(NS+"refuseKey_2",key)!=1){
					throw new Exception("");
				}
				
				logSetter.setNewspeed(
					logSetter.builder(
						AppContextKey.LOG_STATUS_NEWSPEED_KEY_RESPONSE_REFUSE, 
						vo.getSend_email(), 
						vo.getRecv_email(),
						vo.getSerial_no(),
						key.getGrade()
					),null
				);
			}else{
				throw new Exception("");
			}
			platformTransactionManager.commit(status);
			result.put(AppContextKey.AJAX_RESULT,AppContextKey.AJAX_SUCCESS);
			result.put(AppContextKey.RESULT_MSG,notice.getSend_name()+"("+notice.getSend_email()+")님이 부여한 "+notice.getSerial_no()+"에 대한 열쇠의 ["+vo.getAnswer()+"]처리는 성공 하였습니다.");
			return result;
			
		}catch (Exception e){
			e.printStackTrace();
			if(vo.getAnswer().equals("ACCEPT")){
				logSetter.setSystemLog(
					logSetter.builder(
						AppContextKey.LOG_STATUS_NEWSPEED_KEY_RESPONSE_ACCEPT_FAIL, 
						vo.getSend_email(), 
						vo.getRecv_email(),
						vo.getSerial_no(),
						key.getGrade()
					)
				);
			}else{
				logSetter.setSystemLog(
					logSetter.builder(
						AppContextKey.LOG_STATUS_NEWSPEED_KEY_RESPONSE_REFUSE_FAIL,
						vo.getSend_email(), 
						vo.getRecv_email(),
						vo.getSerial_no(),
						key.getGrade()
					)
				);
			}
			
			platformTransactionManager.rollback(status);
			result.put(AppContextKey.AJAX_RESULT,AppContextKey.AJAX_FAIL);
			result.put(AppContextKey.RESULT_MSG,notice.getSend_name()+"("+notice.getSend_email()+")님이 부여한 "+notice.getSerial_no()+"에 대한 열쇠의 ["+vo.getAnswer()+"]처리는 시스템 상의 문제로 실패 하였습니다.");
			return result;
		}
	}
	

}