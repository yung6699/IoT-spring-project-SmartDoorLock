package com.sl.web.doorlock.service;

import java.util.List;

import com.sl.system.log.setter.SystemLogSetterVO;
import com.sl.web.doorlock.vo.WebDoorlockVO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface WebDoorlockService {
	public List<WebDoorlockVO> doorlockList(WebDoorlockVO vo);
	public JSONObject doorlockDetail(WebDoorlockVO vo);
	public JSONObject doorlockCreate(WebDoorlockVO vo);
	public JSONObject doorlockCreateCheck(WebDoorlockVO vo);
	public JSONObject doorlockUpdate(WebDoorlockVO vo);
	public JSONObject doorlockDelete(WebDoorlockVO vo);
	public JSONArray selectListDoorlocklogs(SystemLogSetterVO vo);
	
}
