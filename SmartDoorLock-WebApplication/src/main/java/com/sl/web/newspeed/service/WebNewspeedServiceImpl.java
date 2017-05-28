package com.sl.web.newspeed.service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sl.app.notice.notice.controller.AppNoticeController;
import com.sl.app.notice.notice.dao.AppNoticeDAO;
import com.sl.app.notice.notice.vo.AppNoticeVO;
import com.sl.web.newspeed.dao.WebNewspeedDao;
import com.sl.web.newspeed.vo.WebNewspeedVO;

@Service
public class WebNewspeedServiceImpl implements WebNewspeedService{

	private static final Logger logger = LoggerFactory.getLogger(AppNoticeController.class);
	
	@Autowired
	private WebNewspeedDao dao;


	@Override
	public JSONArray selectUserNotice(WebNewspeedVO vo) {
		return dao.selectUserNotice(vo);
	}

	@Override
	public JSONObject responseUserNotice(WebNewspeedVO vo) {
		return dao.responseUserNotice(vo);
	}

}


