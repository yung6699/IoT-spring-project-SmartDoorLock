package com.sl.web.myinfo.service;

import com.sl.web.myinfo.vo.WebMyInfoVO;

import net.sf.json.JSONObject;

public interface WebMyInfoService {

	JSONObject updateSubmitMyInfo(WebMyInfoVO vo);
	JSONObject updateDeleteMyInfo(WebMyInfoVO vo);
}