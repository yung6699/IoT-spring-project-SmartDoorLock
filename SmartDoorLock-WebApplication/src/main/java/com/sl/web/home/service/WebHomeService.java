package com.sl.web.home.service;

import com.sl.web.home.vo.WebHomeVO;

import net.sf.json.JSONObject;

public interface WebHomeService {
	JSONObject login(WebHomeVO vo);
	JSONObject joinEmail(WebHomeVO vo);
	JSONObject joinAccount(WebHomeVO vo);
	JSONObject findAccount(WebHomeVO vo);
}
