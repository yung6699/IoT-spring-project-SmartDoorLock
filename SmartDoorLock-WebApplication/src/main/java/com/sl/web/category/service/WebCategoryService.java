package com.sl.web.category.service;

import com.sl.web.category.vo.WebCategoryListVO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface WebCategoryService {

	JSONArray categoryListJSON(JSONObject vo);
	JSONObject categoryCreateDo(WebCategoryListVO vo);
	
}
