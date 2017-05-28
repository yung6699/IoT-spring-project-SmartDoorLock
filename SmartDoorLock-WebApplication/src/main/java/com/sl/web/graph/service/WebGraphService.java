package com.sl.web.graph.service;

import com.sl.web.graph.vo.WebGraphVO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface WebGraphService {
	public JSONArray getGraph(WebGraphVO vo);
	public JSONObject getServiceInfo(WebGraphVO vo);




}

