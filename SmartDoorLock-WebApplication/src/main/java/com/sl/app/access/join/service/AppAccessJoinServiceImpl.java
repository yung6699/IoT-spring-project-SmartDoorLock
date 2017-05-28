package com.sl.app.access.join.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sl.app.access.join.dao.AppAccessJoinDAO;
import com.sl.app.access.join.vo.AppAccessJoinVO;

import net.sf.json.JSONObject;

@Service
public class AppAccessJoinServiceImpl implements AppAccessJoinService{
	
	@Autowired
	private AppAccessJoinDAO dao; 

	@Override
	public JSONObject getCheckEmail(AppAccessJoinVO vo) {
		// TODO Auto-generated method stub
		String str = vo.getEmail();
		str = str.replace("<", "&lt;");
		str = str.replace(">", "&rt;");
		vo.setEmail(str);
		return dao.getCheckEmail(vo);
	}
	@Override
	public JSONObject setCompleteJoin(AppAccessJoinVO vo) {
		// TODO Auto-generated method stub
		return dao.setCompleteJoin(vo);
	}



	
}
