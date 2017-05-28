package com.sl.app.notice.notice.service;



import com.sl.app.notice.notice.vo.AppNoticeVO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public interface AppNoticeService{
	JSONArray selectUserNotice(AppNoticeVO vo);
	JSONObject responseUserNotice(AppNoticeVO vo);
}