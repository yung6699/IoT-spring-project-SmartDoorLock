package com.sl.web.category.dao;

import com.sl.web.category.vo.WebCategoryListVO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface WebCategoryDAO {
	JSONArray categoryListJSON(JSONObject vo);
	JSONObject categoryCreateDo(WebCategoryListVO vo);
}
