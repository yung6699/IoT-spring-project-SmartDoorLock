package com.sl.hw.key.list.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sl.hw.key.list.dao.HwKeyListDAO;
import com.sl.hw.key.list.vo.HwKeyListVO;

import net.sf.json.JSONArray;

@Service
public class HwKeyListServiceImpl implements HwKeyListService{
	
	@Autowired
	HwKeyListDAO dao;
	
	@Override
	public JSONArray selectKeyList(String serial_no) {
		// TODO Auto-generated method stub
		return dao.selectKeyList(serial_no);
	}

}
