package com.sl.web.newspeed.service;

import com.sl.web.newspeed.vo.WebNewspeedVO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface WebNewspeedService {
	JSONArray selectUserNotice(WebNewspeedVO vo);
	JSONObject responseUserNotice(WebNewspeedVO vo);
}
