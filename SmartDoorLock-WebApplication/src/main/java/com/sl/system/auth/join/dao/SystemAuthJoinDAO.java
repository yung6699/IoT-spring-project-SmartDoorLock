package com.sl.system.auth.join.dao;

import com.sl.system.auth.join.vo.SystemAuthJoinVO;

public interface SystemAuthJoinDAO {
	int updateMemberState(SystemAuthJoinVO vo);
	int deleteMember(SystemAuthJoinVO vo);
}
