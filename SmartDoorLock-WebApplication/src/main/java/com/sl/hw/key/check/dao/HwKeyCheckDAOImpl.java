package com.sl.hw.key.check.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sl.app.common.context.AppContextKey;
import com.sl.hw.key.check.vo.HwKeyCheckVO;
import com.sl.system.log.setter.SystemLogSetter;
import com.sl.system.log.setter.SystemLogSetterVO;
import com.sl.web.common.context.ContextKey;

@Repository
public class HwKeyCheckDAOImpl implements HwKeyCheckDAO {

	@Autowired
	private SqlSession sqlSession;

	private final String NS = "mapper.com.sl.hw.key.check.";

	@Autowired
	SystemLogSetter logSetter;

	@Override
	public String selectOneCheck(HwKeyCheckVO vo) {
		
		if ((Integer) sqlSession.selectOne(NS + "selectOneCheck", vo) == 1) {
			HwKeyCheckVO vo1 = sqlSession.selectOne(NS + "selectKey", vo);
			logSetter.setNewspeed(
					logSetter.builder(
						ContextKey.LOG_STATUS_HW_KEY_OPEN,
						vo1.getEmail(),
						null,
						vo1.getSerial_no(),
						null
					),null
				);
			return "OK";
		} else {
			HwKeyCheckVO vo1 = sqlSession.selectOne(NS + "selectKey2", vo);
			logSetter.setNewspeed(
				logSetter.builder(
					ContextKey.LOG_STATUS_HW_KEY_OPEN,
					vo1.getEmail(),
					null,
					vo1.getSerial_no(),
					null
				),null
			);
			return "FAIL";
		}

	}

}
