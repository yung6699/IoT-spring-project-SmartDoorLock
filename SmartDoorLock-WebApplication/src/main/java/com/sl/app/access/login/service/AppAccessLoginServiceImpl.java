package com.sl.app.access.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sl.app.access.login.dao.AppAccessLoginDAO;
import com.sl.app.access.login.vo.AppAccessLoginVO;



@Service
public class AppAccessLoginServiceImpl implements AppAccessLoginService{

	@Autowired
	private AppAccessLoginDAO dao;
	
	@Override
	public int selectOneMember(AppAccessLoginVO vo) {
		// TODO Auto-generated method stub
		return dao.selectOneMember(vo);
	}
}
