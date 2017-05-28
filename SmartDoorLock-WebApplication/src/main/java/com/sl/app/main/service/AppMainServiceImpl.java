package com.sl.app.main.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sl.app.main.dao.AppMainDAO;
import com.sl.app.main.vo.AppMainVO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


@Service
public class AppMainServiceImpl implements AppMainService{

	
	@Autowired
	AppMainDAO dao;
	
	@Override
	public JSONArray selectListKeys(AppMainVO vo) {
		// TODO Auto-generated method stub
		//gcm test
		//gcm.send_GCM("smartlock-1353","HELLO");
		///////
		return dao.selectListKeys(vo);
	} 
	
	@Override
	public JSONObject countNotice(AppMainVO vo){
		return dao.countNotice(vo);
	}
	
	@Override
	public void logout(AppMainVO vo){
		dao.logout(vo);
	}

}
