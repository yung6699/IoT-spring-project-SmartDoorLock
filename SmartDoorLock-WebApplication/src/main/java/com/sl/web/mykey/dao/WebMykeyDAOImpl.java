package com.sl.web.mykey.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sl.system.log.setter.SystemLogSetter;
import com.sl.system.log.setter.SystemLogSetterVO;
import com.sl.web.common.context.ContextKey;
import com.sl.web.mykey.vo.WebMyKeyVO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Repository
public class WebMykeyDAOImpl implements WebMyKeyDAO {
	
	@Autowired
	private SqlSession sqlSession;
	
	@Autowired
	SystemLogSetter logSetter;
	
	private final String NS = "mapper.com.sl.web.mykey.";

	@Override
	public JSONArray mykeyList(WebMyKeyVO vo) {
		logSetter.setSystemLog(
			logSetter.builder(
				ContextKey.LOG_STATUS_KEY_LIST,
				vo.getEmail(),
				null,
				null,
				null
			)
		);
		return JSONArray.fromObject(sqlSession.selectList(NS+"mykeyList", vo));
	}

	@Override
	public JSONObject mykeyListDetail(WebMyKeyVO vo) {
		// TODO Auto-generated method stub
		
		JSONObject obj =  JSONObject.fromObject(sqlSession.selectOne(NS+"mykeyListDetail", vo));
		logSetter.setSystemLog(
			logSetter.builder(
				ContextKey.LOG_STATUS_KEY_INFO,
				vo.getEmail(),
				null,
				obj.getString("serial_no"),
				null
			)
		);
	
		return obj;
	}

	@Override
	public JSONArray mykeyListSearch(WebMyKeyVO vo) {
		// TODO Auto-generated method stub
		return JSONArray.fromObject(sqlSession.selectList(NS+"mykeyList", vo));
	}

	@Override
	public JSONObject updateMyKeyName(WebMyKeyVO vo) {
		JSONObject result = new JSONObject();

		try {
			
			if (sqlSession.update(NS+"updateMyKeyName",vo)!=1){
				throw new Exception("updateMyKeyName"); 
			}
			result.put(ContextKey.AJAX_RESULT, ContextKey.AJAX_SUCCESS);
			logSetter.setSystemLog(
				logSetter.builder(
					ContextKey.LOG_STATUS_KEY_UPDATE,
					vo.getEmail(),
					null,
					vo.getSerial_no(),
					null
				)
			);
			return result;
			
		} catch (Exception e) {
			e.printStackTrace();
			result.put(ContextKey.AJAX_RESULT,ContextKey.AJAX_FAIL);
			result.put(ContextKey.RESULT_MSG,ContextKey.MSG_KEY_NAME_UPDATE_FAIL);
			logSetter.setSystemLog(
				logSetter.builder(
					ContextKey.LOG_STATUS_KEY_UPDATE_FAIL,
					vo.getEmail(),
					null,
					vo.getSerial_no(),
					null
				)
			);
			return result;
		}
	}

	@Override
	public JSONObject deleteKey(WebMyKeyVO vo) {
		
		JSONObject result = new JSONObject();

		try {
			if (sqlSession.delete(NS + "deleteKey", vo) != 1) {
				logSetter.setDoorlockLog(
					logSetter.builder(
						ContextKey.LOG_STATUS_KEY_DELETE_FAIL,
						vo.getEmail(),
						null,
						vo.getSerial_no(),
						null
					)
				);
				throw new Exception("deleteKey");
			}else{
				logSetter.setNewspeed(
					logSetter.builder(
						ContextKey.LOG_STATUS_KEY_DELETE,
						vo.getEmail(),
						null,
						vo.getSerial_no(),
						null
					)
					,null
				);
			}
			result.put(ContextKey.AJAX_RESULT, ContextKey.AJAX_SUCCESS);

		} catch (Exception e) {
			result.put(ContextKey.AJAX_RESULT, ContextKey.AJAX_FAIL);
			result.put(ContextKey.RESULT_MSG, ContextKey.MSG_KEY_DELETE_FAIL);
			logSetter.setDoorlockLog(
				logSetter.builder(
					ContextKey.LOG_STATUS_KEY_DELETE_FAIL,
					vo.getEmail(),
					null,
					vo.getSerial_no(),
					null
				)
			);
			e.printStackTrace();
			return result;
		}
		return result;
	}

}
