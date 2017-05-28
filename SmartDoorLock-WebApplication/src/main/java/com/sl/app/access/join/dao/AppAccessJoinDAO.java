package com.sl.app.access.join.dao;

import com.sl.app.access.join.vo.AppAccessJoinVO;

import net.sf.json.JSONObject;

public interface AppAccessJoinDAO {
	JSONObject getCheckEmail(AppAccessJoinVO vo);
	JSONObject setCompleteJoin(AppAccessJoinVO vo);
}
