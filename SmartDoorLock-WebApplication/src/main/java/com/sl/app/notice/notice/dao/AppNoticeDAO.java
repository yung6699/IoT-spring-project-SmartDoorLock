package com.sl.app.notice.notice.dao;



import com.sl.app.notice.notice.vo.AppNoticeVO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface AppNoticeDAO{
	JSONArray selectUserNotice(AppNoticeVO vo);
	JSONObject responseUserNotice(AppNoticeVO vo);
}