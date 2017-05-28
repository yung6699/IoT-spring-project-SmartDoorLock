package com.sl.app.mykey.list.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sl.app.mykey.list.dao.AppMykeyListDAO;
import com.sl.app.mykey.list.vo.AppMykeyListVO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class AppMykeyListServiceImpl implements AppMykeyListService{
	
	@Autowired
	AppMykeyListDAO dao;
	
	@Override
	public JSONArray selectMyKeyList(AppMykeyListVO vo) {
		// TODO Auto-generated method stub
		return dao.selectMyKeyList(vo);
	}

	@Override
	public JSONObject updateMyKeyName(AppMykeyListVO vo) {
		// TODO Auto-generated method stub
		return dao.updateMyKeyName(vo);
	}

	@Override
	public JSONObject deleteMyKey(AppMykeyListVO vo) {
		return dao.deleteMyKey(vo);
	}

}
