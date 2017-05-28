package com.sl.app.mydoorlock.key.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sl.app.mydoorlock.key.dao.AppMyDoorLockDAO;
import com.sl.app.mydoorlock.key.vo.AppMyDoorLockVO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class AppMyDoorLockServiceImpl implements AppMyDoorLockService{

	@Autowired
	AppMyDoorLockDAO dao;
	
	@Override
	public JSONArray selectDoorLockList(AppMyDoorLockVO vo) {
		// TODO Auto-generated method stub
		return dao.selectDoorLockList(vo);
	}

	
	@Override
	public JSONArray selectKeyList(AppMyDoorLockVO vo) {
		// TODO Auto-generated method stub
		return dao.selectKeyList(vo);
	}

	
	@Override
	public JSONObject selectSearchMember(AppMyDoorLockVO vo) {
		// TODO Auto-generated method stub
		return dao.selectSearchMember(vo);
	}


	@Override
	public JSONObject selectOneInsert(AppMyDoorLockVO vo) {
		// TODO Auto-generated method stub
		return dao.selectOneInsert(vo);
	}


	@Override
	public JSONObject deleteKey(AppMyDoorLockVO vo) {
		// TODO Auto-generated method stub
		return dao.deleteKey(vo);
	}


	@Override
	public JSONObject updateDoorLockName(AppMyDoorLockVO vo) {
		// TODO Auto-generated method stub
		return dao.updateDoorLockName(vo);
	}


	@Override
	public JSONObject updateDoorLockPlace(AppMyDoorLockVO vo) {
		// TODO Auto-generated method stub
		return dao.updateDoorLockPlace(vo);
	}


	@Override
	public JSONObject deleteMyDoorLock(AppMyDoorLockVO vo) {
		// TODO Auto-generated method stub
		return dao.deleteMyDoorLock(vo);
	}

}
