package com.sl.system.auth.join.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sl.system.auth.join.dao.SystemAuthJoinDAO;
import com.sl.system.auth.join.vo.SystemAuthJoinVO;

@Service
public class SystemAuthJoinServiceImpl implements SystemAuthJoinService{

	@Autowired
	private SystemAuthJoinDAO dao;
	
	@Override
	public int updateMemberState(SystemAuthJoinVO vo) {
		// TODO Auto-generated method stub
		return dao.updateMemberState(vo);
	}

	@Override
	public int deleteMember(SystemAuthJoinVO vo) {
		// TODO Auto-generated method stub
		return dao.deleteMember(vo);
	}

}
