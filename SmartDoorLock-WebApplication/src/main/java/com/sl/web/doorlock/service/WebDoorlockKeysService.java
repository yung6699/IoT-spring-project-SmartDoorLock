package com.sl.web.doorlock.service;

import com.sl.web.doorlock.vo.WebDoorlockVO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface WebDoorlockKeysService {
	public JSONArray doorlockSelectKeys(WebDoorlockVO vo);
	public JSONObject doorlockSelectKeysDetail(WebDoorlockVO vo);
	public JSONObject doorlockKeysCreateCheck(WebDoorlockVO vo);
	public JSONObject doorlockKeysCreate(WebDoorlockVO vo);
	public JSONObject doorlockKeysUpdate(WebDoorlockVO vo);
	public JSONObject doorlockKeysDelete(WebDoorlockVO vo);
}

