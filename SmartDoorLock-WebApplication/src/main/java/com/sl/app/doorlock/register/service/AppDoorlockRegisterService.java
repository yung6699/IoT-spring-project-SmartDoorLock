package com.sl.app.doorlock.register.service;

import com.sl.app.doorlock.register.vo.AppDoorlockRegisterVO;

import net.sf.json.JSONObject;

public interface AppDoorlockRegisterService {

	JSONObject selectOneCheck(AppDoorlockRegisterVO vo);
	JSONObject selectOneInsert(AppDoorlockRegisterVO vo);
}
