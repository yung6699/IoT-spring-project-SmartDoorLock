package com.sl.app.mydoorlock.key.dao;

import com.sl.app.mydoorlock.key.vo.AppMyDoorLockVO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface AppMyDoorLockDAO {
	JSONArray selectKeyList(AppMyDoorLockVO vo);
	JSONObject selectOneInsert(AppMyDoorLockVO vo);
	JSONObject deleteKey(AppMyDoorLockVO vo);
	JSONObject selectSearchMember(AppMyDoorLockVO vo);
	JSONObject deleteDenyKey(AppMyDoorLockVO vo);
	JSONArray selectDoorLockList(AppMyDoorLockVO vo);
	JSONObject updateDoorLockName(AppMyDoorLockVO vo);
	JSONObject updateDoorLockPlace(AppMyDoorLockVO vo);
	JSONObject deleteMyDoorLock(AppMyDoorLockVO vo);
}

