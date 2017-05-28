package com.sl.web.myinfo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sl.web.myinfo.dao.WebMyInfoDAO;
import com.sl.web.myinfo.vo.WebMyInfoVO;

import net.sf.json.JSONObject;

@Service
public class WebMyInfoServiceImpl implements WebMyInfoService{

	@Autowired
	private WebMyInfoDAO dao;
	
	@Override
	public JSONObject updateSubmitMyInfo(WebMyInfoVO vo) {
		// TODO Auto-generated method stub
		return dao.updateSubmitMyInfo(vo);
	}

	@Override
	public JSONObject updateDeleteMyInfo(WebMyInfoVO vo) {
		// TODO Auto-generated method stub
		return dao.updateDeleteMyInfo(vo);
	}

}