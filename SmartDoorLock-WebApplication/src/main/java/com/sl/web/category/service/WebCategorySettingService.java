package com.sl.web.category.service;

import net.sf.json.JSONObject;

public interface WebCategorySettingService {
	boolean checkCategory(String email,int cat_id);
	JSONObject getCategoryListInfo(String email,int cat_id);
	JSONObject submitCategoryModify(JSONObject obj);
	boolean deleteCategoryModify(String email,int cat_id);
}
