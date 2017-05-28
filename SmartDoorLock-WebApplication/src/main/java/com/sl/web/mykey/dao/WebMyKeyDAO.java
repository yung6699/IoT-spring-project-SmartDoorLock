package com.sl.web.mykey.dao;

import com.sl.web.mykey.vo.WebMyKeyVO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface WebMyKeyDAO {
	public JSONArray mykeyList(WebMyKeyVO vo);
	public JSONObject mykeyListDetail(WebMyKeyVO vo);
	public JSONArray mykeyListSearch(WebMyKeyVO vo);
	public JSONObject updateMyKeyName(WebMyKeyVO vo);
	public JSONObject deleteKey(WebMyKeyVO vo);
}
