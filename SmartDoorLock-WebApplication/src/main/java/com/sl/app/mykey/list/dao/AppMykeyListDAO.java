package com.sl.app.mykey.list.dao;

import com.sl.app.mykey.list.vo.AppMykeyListVO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface AppMykeyListDAO {
	JSONArray selectMyKeyList(AppMykeyListVO vo);
	JSONObject updateMyKeyName(AppMykeyListVO vo);
	JSONObject deleteMyKey(AppMykeyListVO vo);
}
