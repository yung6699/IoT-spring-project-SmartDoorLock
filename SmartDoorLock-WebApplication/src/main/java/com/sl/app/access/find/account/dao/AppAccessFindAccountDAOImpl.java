package com.sl.app.access.find.account.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sl.app.access.find.account.vo.AppAccessFindAccountVO;
import com.sl.app.common.context.AppContextKey;
import com.sl.system.log.setter.SystemLogSetter;
import com.sl.system.log.setter.SystemLogSetterVO;

import net.sf.json.JSONObject;

@Repository
public class AppAccessFindAccountDAOImpl implements AppAccessFindAccountDAO {

	@Autowired
	SqlSession sqlSession;
	
	@Autowired
	SystemLogSetter logSetter;
	
	private final String NS = "mapper.com.sl.app.access.find.account.";
	
	@Override
	public JSONObject updateFindMember(AppAccessFindAccountVO vo) {
		// TODO Auto-generated method stub
		JSONObject obj = new JSONObject();
		if(sqlSession.update(NS+"updateFindMember",vo)==1){
			obj.put(AppContextKey.AJAX_RESULT,AppContextKey.AJAX_SUCCESS);
			obj.put(AppContextKey.RESULT_MSG, "["+vo.getEmail() + AppContextKey.MSG_FIND_ACCOUNT_SUCCESS);
			logSetter.setSystemLog(
				logSetter.builder(
					AppContextKey.LOG_STATUS_ACCOUNT_FIND, 
					vo.getEmail(), 
					null,
					null,
					null
				)
			);
		}else{
			obj.put(AppContextKey.AJAX_RESULT,AppContextKey.AJAX_SUCCESS);
			obj.put(AppContextKey.RESULT_MSG, "["+vo.getEmail() + AppContextKey.MSG_FIND_ACCOUNT_FAIL);
			SystemLogSetterVO log = logSetter.builder(null, vo.getEmail(), vo.getEmail(),AppContextKey.LOG_STATUS_ACCOUNT_FIND_FAIL, null);
			logSetter.setSystemLog(
				logSetter.builder(
					AppContextKey.LOG_STATUS_ACCOUNT_FIND_FAIL, 
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
