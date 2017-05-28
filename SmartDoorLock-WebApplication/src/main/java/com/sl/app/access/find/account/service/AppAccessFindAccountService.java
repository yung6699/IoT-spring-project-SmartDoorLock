package com.sl.app.access.find.account.service;

import javax.mail.MessagingException;

import com.sl.app.access.find.account.vo.AppAccessFindAccountVO;

import net.sf.json.JSONObject;

public interface AppAccessFindAccountService {
	JSONObject updateFindMember(AppAccessFindAccountVO vo) throws MessagingException;
}
