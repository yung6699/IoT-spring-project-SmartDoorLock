package com.sl.web.graph.dao;

import com.sl.web.graph.vo.WebGraphVO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface WebGraphDAO {
	public JSONArray getGraph(WebGraphVO vo);
	public JSONObject getServiceInfo(WebGraphVO vo);

}
