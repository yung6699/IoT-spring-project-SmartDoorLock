package com.sl.system.session.member;

import net.sf.json.JSONObject;

public interface SessionMember {
	//sessionMember 를 사용해주기 위해서는 JSONObject 라는 객체를 만들어서 state에다가 값을 넣어주어야만 한다.
	JSONObject getSessionMember(JSONObject obj); 
}
