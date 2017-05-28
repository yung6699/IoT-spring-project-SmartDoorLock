package com.sl.web.mykey.service;

import com.sl.web.mykey.vo.WebMyKeyVO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface WebMyKeyService {

	public JSONArray mykeyList(WebMyKeyVO vo);
	public JSONObject mykeyListDetail(WebMyKeyVO vo);
	public JSONArray mykeyListSearch(WebMyKeyVO vo); // 검색할 데이터를 미리 자져오기 위한 메소드
	public JSONObject updateMyKeyName(WebMyKeyVO vo);
	public JSONObject deleteKey(WebMyKeyVO vo);
}
