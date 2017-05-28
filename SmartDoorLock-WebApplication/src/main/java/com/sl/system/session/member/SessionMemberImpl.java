package com.sl.system.session.member;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sl.system.common.util.ContextUtil;

import net.sf.json.JSONObject;

@Repository
public class SessionMemberImpl implements SessionMember{
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SqlSession sqlSession;
	
	final String NS = "mapper.com.sl.system.session.member.";
	
	
	@Override
	public JSONObject getSessionMember(JSONObject vo) {
		// TODO Auto-generated method stub
		JSONObject obj= (JSONObject) sqlSession.selectOne(NS+"getSessionMember",vo);
		
		return obj;
	}


	
	
}
