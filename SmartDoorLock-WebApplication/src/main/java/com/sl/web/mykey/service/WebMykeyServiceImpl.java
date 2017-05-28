package com.sl.web.mykey.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sl.web.mykey.dao.WebMyKeyDAO;
import com.sl.web.mykey.vo.WebMyKeyVO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class WebMykeyServiceImpl implements WebMyKeyService {

	@Autowired
	private WebMyKeyDAO dao;
	
	@Override
	public JSONArray mykeyList(WebMyKeyVO vo) {
		// TODO Auto-generated method stub
		return dao.mykeyList(vo);
	}

	@Override
	public JSONObject mykeyListDetail(WebMyKeyVO vo) {
		// TODO Auto-generated method stub
		return dao.mykeyListDetail(vo);
	}

	@Override
	public JSONArray mykeyListSearch(WebMyKeyVO vo) {
		// TODO Auto-generated method stub
		return dao.mykeyListSearch(vo);
	}

	@Override
	public JSONObject updateMyKeyName(WebMyKeyVO vo) {
		// TODO Auto-generated method stub
		return dao.updateMyKeyName(vo);
	}

	@Override
	public JSONObject deleteKey(WebMyKeyVO vo) {
		// TODO Auto-generated method stub
		return dao.deleteKey(vo);
	}
	
}
