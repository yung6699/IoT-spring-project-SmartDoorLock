package com.sl.app.doorlock.register.dao;

import com.sl.app.doorlock.register.vo.AppDoorlockRegisterVO;

import net.sf.json.JSONObject;

public interface AppDoorlockRegisterDAO {
	JSONObject selectOneCheck(AppDoorlockRegisterVO vo);
	JSONObject selectOneInsert(AppDoorlockRegisterVO vo);
}
