package com.sl.web.category.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sl.web.category.dao.WebCategoryDAO;
import com.sl.web.category.vo.WebCategoryListVO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class WebCategoryServiceImpl implements WebCategoryService{

	@Autowired
	WebCategoryDAO dao;
	
	@Override
	public JSONArray categoryListJSON(JSONObject vo) {
		// TODO Auto-generated method stub
		return dao.categoryListJSON(vo);
	}
	
	@Override
	public JSONObject categoryCreateDo(WebCategoryListVO vo) {
		// TODO Auto-generated method stub
		return dao.categoryCreateDo(vo);
	}

	
	
	
}
