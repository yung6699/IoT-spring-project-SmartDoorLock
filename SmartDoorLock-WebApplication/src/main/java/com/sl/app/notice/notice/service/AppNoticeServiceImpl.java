package com.sl.app.notice.notice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sl.app.notice.notice.controller.AppNoticeController;
import com.sl.app.notice.notice.dao.AppNoticeDAO;
import com.sl.app.notice.notice.vo.AppNoticeVO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class AppNoticeServiceImpl implements AppNoticeService {
	private static final Logger logger = LoggerFactory.getLogger(AppNoticeController.class);
	@Autowired
	AppNoticeDAO dao;

	@Override
	public JSONArray selectUserNotice(AppNoticeVO vo) {
		logger.info("/app/notice/Service에 들어오셨습니다.");
		return dao.selectUserNotice(vo);
	}

	@Override
	public JSONObject responseUserNotice(AppNoticeVO vo) {
		logger.info("/app/notice/Response/Service에 들어오셨습니다.");
		return dao.responseUserNotice(vo);
	}

}