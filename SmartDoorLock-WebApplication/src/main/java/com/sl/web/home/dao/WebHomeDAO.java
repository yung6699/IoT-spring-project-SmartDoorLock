package com.sl.web.home.dao;

import com.sl.web.home.vo.WebHomeVO;

import net.sf.json.JSONObject;

public interface WebHomeDAO {

	JSONObject login(WebHomeVO vo);
	JSONObject joinEmail(WebHomeVO vo);
	JSONObject joinAccount(WebHomeVO vo);
	JSONObject findAccount(WebHomeVO vo);
}
