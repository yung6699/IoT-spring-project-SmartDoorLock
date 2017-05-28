package com.sl.web.graph.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sl.web.graph.dao.WebGraphDAO;
import com.sl.web.graph.vo.WebGraphVO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class WebGraphServiceImpl implements WebGraphService {
	
	@Autowired
	private WebGraphDAO dao;

	@Override
	public JSONArray getGraph(WebGraphVO vo) {
		// TODO Auto-generated method stub
		return dao.getGraph(vo);
	}

	@Override
	public JSONObject getServiceInfo(WebGraphVO vo) {
		// TODO Auto-generated method stub
		return dao.getServiceInfo(vo);
	}
	
}
