package com.sl.app.access.find.account.dao;

import com.sl.app.access.find.account.vo.AppAccessFindAccountVO;

import net.sf.json.JSONObject;

public interface AppAccessFindAccountDAO {
	JSONObject updateFindMember(AppAccessFindAccountVO vo);
}
