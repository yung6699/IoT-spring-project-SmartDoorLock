package com.sl.system.log.setter;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sl.system.gcm.NotificationService;


@Repository
public class SystemLogSetterImpl implements SystemLogSetter{
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SqlSession sqlSession;
	
	@Autowired
	private NotificationService gcm;
	
	private final String NS="mapper.com.sl.system.log.setter.";
	
	@Override
	public SystemLogSetterVO builder(String state, String send_email, String recv_email, String serial_no, String message){
		SystemLogSetterVO vo = new SystemLogSetterVO();	
		vo.setState(state);
		vo.setSerial_no(serial_no);
		vo.setSend_email(send_email);
		vo.setRecv_email(recv_email);
		vo.setMessage(message);
		String msg = sqlSession.selectOne(NS+"getMessage", vo.getState());
		msg=msg.replace("{serial_no}", vo.getSerial_no());
		msg=msg.replace("{recv_email}", vo.getRecv_email());
		msg=msg.replace("{send_email}", vo.getSend_email());
		msg=msg.replace("{message}", vo.getMessage());

		logger.debug(msg);
		vo.setMessage(msg);
		return vo;
	}
	@Override
	public SystemLogSetterVO builder(String state, SystemLogSetterVO vo){
		vo.setState(state);
		String msg = sqlSession.selectOne(NS+"getMessage", state);
		
		msg=msg.replace("{serial_no}", vo.getSerial_no());
		msg=msg.replace("{recv_email}", vo.getRecv_email());
		msg=msg.replace("{send_email}", vo.getSend_email());
		msg=msg.replace("{message}", vo.getMessage());

		logger.debug(msg);
		vo.setMessage(msg);
		return vo;
	}
	
	@Override
	public void setSystemLog(SystemLogSetterVO vo) {
		// TODO Auto-generated method stub
		sqlSession.insert(NS+"setSystemLog",vo);		
	}
	
	@Override
	public void setDoorlockLog(SystemLogSetterVO vo) {
		// TODO Auto-generated method stub
		this.setSystemLog(vo);
		sqlSession.insert(NS+"setDoorlockLog",vo);
	}
	
	@Override
	public void setNewspeed(SystemLogSetterVO vo,String oneMember) {
		// TODO Auto-generated method stub
		this.setDoorlockLog(vo);
		if(oneMember==null||oneMember.equals("")){
		//기본 관리자 급들에게만 전송
			List<String> user= sqlSession.selectList(NS+"getNewspeedDoorlockUser",vo);
			for(String temp : user){
				vo.setRecv_email(temp);
				sqlSession.insert(NS+"setNewspeed",vo);
				gcm.pushNotificationToGCM((String)sqlSession.selectOne(NS+"getGcmId",vo.getRecv_email()), vo.getMessage());
			}
		}else if(oneMember.equals("ALL")){
		//모두에게 로그를 전송
			List<String> user= sqlSession.selectList(NS+"getNewspeedDoorlockALLUser",vo);
			for(String temp : user){
				logger.info(temp +": sellermoon");
				vo.setRecv_email(temp);
				sqlSession.insert(NS+"setNewspeed",vo);
				gcm.pushNotificationToGCM((String)sqlSession.selectOne(NS+"getGcmId",vo.getRecv_email()), vo.getMessage());
			}
		} else {
		// 단 한명에게만 전송
			vo.setRecv_email(oneMember);
			sqlSession.insert(NS+"setNewspeed",vo);
			gcm.pushNotificationToGCM((String)sqlSession.selectOne(NS+"getGcmId",vo.getRecv_email()), vo.getMessage());
		}
	}
	
	@Override
	public void updateNewFlag(String email){
		System.out.println("update New Flag");
		logger.debug("email:"+email+" is read the newspeed");
		sqlSession.update(NS+"updateNewFlag",email);
	}
	
	@Override
	public void deleteGrantRequest(SystemLogSetterVO vo){
		sqlSession.delete(NS+"deleteGrantRequest",vo);
	}
}
