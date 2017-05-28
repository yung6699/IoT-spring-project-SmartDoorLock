package com.sl.hw.key.list.dao;

import net.sf.json.JSONArray;

public interface HwKeyListDAO {

	JSONArray selectKeyList(String serial_no);

}
