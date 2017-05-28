package com.sl.system.session.member;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import net.sf.json.JSONObject;

@Repository
public class SessionNoticeImpl implements SessionNotice{

	@Autowired
	private SqlSession sqlSession;
	
	final String NS = "mapper.com.sl.system.session.member.";
	
	
	@Override
	public JSONObject getSessionNotice(JSONObject vo) {
		// TODO Auto-generated method stub
		return (JSONObject) sqlSession.selectOne(NS+"checkNotice",vo) ;
	}
}
