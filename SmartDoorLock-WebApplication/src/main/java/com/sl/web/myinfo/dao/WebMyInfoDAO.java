package com.sl.web.myinfo.dao;

import com.sl.web.myinfo.vo.WebMyInfoVO;

import net.sf.json.JSONObject;

public interface WebMyInfoDAO {
	JSONObject updateSubmitMyInfo(WebMyInfoVO vo);
	JSONObject updateDeleteMyInfo(WebMyInfoVO vo);
}