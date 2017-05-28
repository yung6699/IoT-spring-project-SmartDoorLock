package com.sl.hw.key.check.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sl.hw.key.check.dao.HwKeyCheckDAO;
import com.sl.hw.key.check.vo.HwKeyCheckVO;

@Service
public class HwKeyCheckServiceImpl implements HwKeyCheckService {
    
	@Autowired
	HwKeyCheckDAO dao;
	 
	@Override
	public String selectOneCheck(HwKeyCheckVO vo) {
		// TODO Auto-generated method stub
		return dao.selectOneCheck(vo);
	}

}
