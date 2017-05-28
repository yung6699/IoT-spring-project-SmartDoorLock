package com.sl.sub.expirer.log;

import java.util.List; 

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class SystemLogSetterImpl implements SystemLogSetter{
//com.sl.sub.expirer.log.SystemLogSetterImpl
	private static Logger logger = LoggerFactory.getLogger(SystemLogSetterImpl.class);
	
	@Autowired
	private SqlSession sqlSession;
	
	
	private final String NS="mapper.com.sl.sub.expirer.log.";
	
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
			}
		}else if(oneMember.equals("ALL")){
		//모두에게 로그를 전송
			List<String> user= sqlSession.selectList(NS+"getNewspeedDoorlockALLUser",vo);
			for(String temp : user){
				vo.setRecv_email(temp);
				sqlSession.insert(NS+"setNewspeed",vo);
			}
		} else {
		// 단 한명에게만 전송
			vo.setRecv_email(oneMember);
			sqlSession.insert(NS+"setNewspeed",vo);
		}
	}
	@Override
	public void updateNewFlag(String email) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void deleteGrantRequest(SystemLogSetterVO vo) {
		// TODO Auto-generated method stub
		
	}

}
