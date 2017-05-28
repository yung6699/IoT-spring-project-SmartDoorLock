package com.sl.app.doorlock.register.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sl.app.doorlock.register.dao.AppDoorlockRegisterDAO;
import com.sl.app.doorlock.register.vo.AppDoorlockRegisterVO;

import net.sf.json.JSONObject;

@Service
public class AppDoorlockRegisterServiceImpl implements AppDoorlockRegisterService{

	
	@Autowired
	private AppDoorlockRegisterDAO dao;
	
	@Override
	public JSONObject selectOneCheck(AppDoorlockRegisterVO vo) {
		// TODO Auto-generated method stub
		return dao.selectOneCheck(vo);
	}

	@Override
	public JSONObject selectOneInsert(AppDoorlockRegisterVO vo) {
		// TODO Auto-generated method stub
		return dao.selectOneInsert(vo);
	}

}
