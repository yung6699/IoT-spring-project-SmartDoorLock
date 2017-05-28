package com.sl.app.main.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sl.app.common.context.AppContextKey;
import com.sl.app.main.vo.AppMainVO;
import com.sl.system.log.setter.SystemLogSetter;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Repository
public class AppMainDAOImpl implements AppMainDAO {

	
	@Autowired
	private SqlSession sqlSession;
	
	@Autowired
	SystemLogSetter logSetter;
	

	private final String NS = "mapper.com.sl.app.main.";

	@Override
	public JSONArray selectListKeys(AppMainVO vo) {
		// TODO Auto-generated method stub
		logSetter.setSystemLog(
			logSetter.builder(
				AppContextKey.LOG_STATUS_MAIN_KEY_LIST,
				(String)sqlSession.selectOne(NS+"getEmail",vo.getMember_id()),
				null,
				null,
				null
			)
		);
		return JSONArray.fromObject(sqlSession.selectList(NS + "selectListKeys", vo));
	}
	@Override
	public JSONObject countNotice(AppMainVO vo){
		return JSONObject.fromObject(sqlSession.selectOne(NS+"countNotice",vo));
	}
	
	@Override
	public void logout(AppMainVO vo){
		sqlSession.update(NS+"logout",vo);
	}
}
