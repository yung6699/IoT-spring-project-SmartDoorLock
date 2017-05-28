package com.sl.app.access.join.service;

import com.sl.app.access.join.vo.AppAccessJoinVO;

import net.sf.json.JSONObject;

public interface AppAccessJoinService {

	JSONObject getCheckEmail(AppAccessJoinVO vo);
	JSONObject setCompleteJoin(AppAccessJoinVO vo);
}
