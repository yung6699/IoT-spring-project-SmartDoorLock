package com.sl.web.category.dao;

import java.util.Map;

import com.sl.web.category.vo.WebCategoryListVO;

import net.sf.json.JSONObject;

public interface WebCategorySettingDAO {
	boolean checkCategory(WebCategoryListVO vo);
	JSONObject getCategoryListInfo(WebCategoryListVO vo);
	JSONObject submitCategoryModify(String email, Map<String,Object> map);
	boolean deleteCategoryModify(WebCategoryListVO vo);
}
