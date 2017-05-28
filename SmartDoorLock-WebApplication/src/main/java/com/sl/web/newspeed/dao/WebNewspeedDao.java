package com.sl.web.newspeed.dao;

import com.sl.web.newspeed.vo.WebNewspeedVO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface WebNewspeedDao {
	JSONArray selectUserNotice(WebNewspeedVO vo);
	JSONObject responseUserNotice(WebNewspeedVO vo);
}
