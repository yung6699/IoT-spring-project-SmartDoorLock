package com.sl.web.doorlock.dao;

import com.sl.web.doorlock.vo.WebDoorlockVO; 

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface WebDoorlockKeysDAO {
	public JSONArray doorlockSelectKeys(WebDoorlockVO vo);
	public JSONObject doorlockSelectKeysDetail(WebDoorlockVO vo);
	public JSONObject doorlockKeysCreateCheck(WebDoorlockVO vo);
	public JSONObject doorlockKeysCreate(WebDoorlockVO vo);
	public int doorlockKeysUpdate(WebDoorlockVO vo);
	public JSONObject doorlockKeysDelete(WebDoorlockVO vo);
	public String getMyGrade(int member_id,String serial_no);
	public WebDoorlockVO getDoorlockKey(String key_id);
}
