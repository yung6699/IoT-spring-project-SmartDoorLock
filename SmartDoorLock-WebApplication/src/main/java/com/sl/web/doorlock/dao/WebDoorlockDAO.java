package com.sl.web.doorlock.dao;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.sl.system.log.setter.SystemLogSetterVO;
import com.sl.web.doorlock.vo.WebDoorlockVO;

public interface WebDoorlockDAO {
	public List<WebDoorlockVO> doorlockList(WebDoorlockVO vo);
	public JSONObject doorlockDetail(WebDoorlockVO vo);
	public JSONObject doorlockCreateCheck(WebDoorlockVO vo);
	public JSONObject doorlockCreate(WebDoorlockVO vo);
	public int doorlockUpdate(WebDoorlockVO vo);
	public JSONObject doorlockDelete(WebDoorlockVO vo);
	public List<String> getEmail(String serial_no);
	public JSONArray selectListDoorlocklogs(SystemLogSetterVO vo);
	
}
