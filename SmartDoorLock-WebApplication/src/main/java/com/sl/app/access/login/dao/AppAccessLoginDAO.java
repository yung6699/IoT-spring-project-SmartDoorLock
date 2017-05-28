package com.sl.app.access.login.dao;

import com.sl.app.access.login.vo.AppAccessLoginVO;



public interface AppAccessLoginDAO {
	int selectOneMember(AppAccessLoginVO vo);
}
