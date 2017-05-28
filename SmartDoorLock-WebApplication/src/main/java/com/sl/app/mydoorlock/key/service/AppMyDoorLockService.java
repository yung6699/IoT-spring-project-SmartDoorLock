package com.sl.app.mydoorlock.key.service;

import com.sl.app.mydoorlock.key.vo.AppMyDoorLockVO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface AppMyDoorLockService {
	
	JSONArray selectKeyList(AppMyDoorLockVO vo);
	JSONObject selectOneInsert(AppMyDoorLockVO vo);
	JSONObject deleteKey(AppMyDoorLockVO vo);
	JSONObject selectSearchMember(AppMyDoorLockVO vo);
	JSONArray selectDoorLockList(AppMyDoorLockVO vo);
	JSONObject updateDoorLockName(AppMyDoorLockVO vo);
	JSONObject updateDoorLockPlace(AppMyDoorLockVO vo);
	JSONObject deleteMyDoorLock(AppMyDoorLockVO vo);

}
