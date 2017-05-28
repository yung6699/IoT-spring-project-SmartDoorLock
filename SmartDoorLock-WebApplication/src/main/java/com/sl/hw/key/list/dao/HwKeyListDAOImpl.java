package com.sl.hw.key.list.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sl.hw.key.list.vo.HwKeyListVO;

import net.sf.json.JSONArray;

@Repository
public class HwKeyListDAOImpl implements HwKeyListDAO {
	
	@Autowired
	private SqlSession sqlSession;

	private final String NS = "mapper.com.sl.hw.key.list.";

	@Override
	public JSONArray selectKeyList(String serial_no) {
		return JSONArray.fromObject(sqlSession.selectList(NS + "selectKeyList",serial_no));
	}

}
