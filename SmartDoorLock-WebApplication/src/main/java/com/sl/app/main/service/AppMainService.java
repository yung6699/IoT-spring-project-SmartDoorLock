package com.sl.app.main.service;

import com.sl.app.main.vo.AppMainVO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface AppMainService {
	JSONArray selectListKeys(AppMainVO vo);
	public JSONObject countNotice(AppMainVO vo);
	public void logout(AppMainVO vo);
}
