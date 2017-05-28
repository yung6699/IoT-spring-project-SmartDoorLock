package com.sl.system.auth.join.service;

import com.sl.system.auth.join.vo.SystemAuthJoinVO;

public interface SystemAuthJoinService {
	int updateMemberState(SystemAuthJoinVO vo);
	int deleteMember(SystemAuthJoinVO vo);
}
