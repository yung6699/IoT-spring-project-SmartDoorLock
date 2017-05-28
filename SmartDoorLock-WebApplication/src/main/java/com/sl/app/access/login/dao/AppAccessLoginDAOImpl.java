package com.sl.app.access.login.dao;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sl.app.access.login.vo.AppAccessLoginVO;
import com.sl.app.common.context.AppContextKey;
import com.sl.system.common.util.ContextUtil;
import com.sl.system.gcm.NotificationService;
import com.sl.system.log.setter.SystemLogSetter;

@Repository
public class AppAccessLoginDAOImpl implements AppAccessLoginDAO {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SqlSession sqlSession;

	final String NS = "mapper.com.sl.app.access.login.";
	
	@Autowired
	SystemLogSetter logSetter;
	
	@Override
	public int selectOneMember(AppAccessLoginVO vo) {
		// TODO Auto-generated method stub
		try {
			vo.setPassword(ContextUtil.AES_EncodeMaker(vo.getPassword()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		int temp =(Integer) sqlSession.selectOne(NS + "checkMember", vo);
		
		if (temp == 1) {
			if (vo.getAutologin() == 1) {
				System.out.println("AUTO LOGIN이니 GCM_NOTI DB-Table에 REG_ID를 저장하겠습니다.");
				if ((Integer) sqlSession.selectOne(NS + "searchRegId", vo) != 1) {
					System.out.println("처음 AUTOLOGIN을 하셨다면,,");
					sqlSession.insert(NS + "insertRegId", vo);
				} else if (vo.getRegId() != null) {
					System.out.println("AUTOLOGIN을 이미 해봤었다면,,");
					sqlSession.update(NS + "updateRegId", vo);
				}
				logSetter.setSystemLog(
					logSetter.builder(
						AppContextKey.LOG_STATUS_ACCOUNT_LOGIN_AUTO, 
						vo.getEmail(), 
						null,
						null,
						null
					)
				);
			}else{
				logSetter.setSystemLog(
					logSetter.builder(
						AppContextKey.LOG_STATUS_ACCOUNT_LOGIN, 
						vo.getEmail(), 
						null,
						null,
						null
					)
				);
			}
		}else{
			logSetter.setSystemLog(
				logSetter.builder(
					AppContextKey.LOG_STATUS_ACCOUNT_LOGIN_FAIL, 
					vo.getEmail(), 
					null,
					null,
					null
				)
			);
		}
		return temp;
	}

}
