package com.sl.web.category.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;

import com.sl.system.log.setter.SystemLogSetter;
import com.sl.system.session.member.SessionMember;
import com.sl.web.category.vo.WebCategoryListVO;
import com.sl.web.common.context.ContextKey;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Repository
public class WebCategoryDAOImpl implements WebCategoryDAO{

	@Autowired
	PlatformTransactionManager platformTransactionManager;
	
	@Autowired
	SystemLogSetter logSetter;
	
	@Autowired
	private SqlSession sqlSession;
	
	final String NS = "mapper.com.sl.web.category.";

			
			
	@Override
	public JSONArray categoryListJSON(JSONObject vo) {
		// TODO Auto-generated method stub
		JSONArray ary = JSONArray.fromObject(sqlSession.selectList(NS+"categoryListJSON1",vo));
		logSetter.setSystemLog(logSetter.builder(
			ContextKey.LOG_STATUS_CATEGORY_LIST,
			vo.getString("EMAIL"),
			null,
			null,
			null
			)
			
		);
		
		//별로 좋지 못한 로직. 병신같은 로직이었군.
		if(ary.size()==0){
			for(int i=0; i< ary.size();i++){
				JSONObject temp = ary.getJSONObject(i);
				ary.remove(i);
				temp.put("list",JSONArray.fromObject(sqlSession.selectList(NS+"categoryListJSON2",temp)));
				ary.add(i,temp);
			}
			
			System.out.println(ary);
			return ary;
		}else{	
			return ary;
		}
	}

	@Override
	public JSONObject categoryCreateDo(WebCategoryListVO vo) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		if((Integer)sqlSession.selectOne(NS+"categoryCreateDo1",vo)==0){
			if(sqlSession.insert(NS+"categoryCreateDo2",vo)==1){
				result.put(ContextKey.AJAX_RESULT,ContextKey.AJAX_SUCCESS);
				result.put("CAT_LIST", JSONObject.fromObject(sqlSession.selectOne(NS+"categoryCreateDo3",vo)));
				logSetter.setSystemLog(logSetter.builder(
					ContextKey.LOG_STATUS_CATEGORY_CREATE, 
					vo.getCrt_email(),
					null,
					null,
					null
					)
				);
			}else{
				
				logSetter.setSystemLog(logSetter.builder(
					ContextKey.LOG_STATUS_CATEGORY_CREATE_FAIL, 
					vo.getCrt_email(),
					null,
					null,
					null
					)
				);
				result.put(ContextKey.AJAX_RESULT,ContextKey.AJAX_FAIL);
				result.put(ContextKey.RESULT_MSG,"카테고리를 생성할 수 없습니다. 새로고침 후 다시 시도 해 주십시오.");
			}
		}else{
			logSetter.setSystemLog(logSetter.builder(
				ContextKey.LOG_STATUS_CATEGORY_CREATE_FAIL, 
				vo.getCrt_email(),
				null,
				null,
				null
				)
			);
			result.put(ContextKey.AJAX_RESULT,ContextKey.AJAX_FAIL);
			result.put(ContextKey.RESULT_MSG,"["+vo.getCat_name()+"]은 이미 만들어져 있습니다.");		
		}
		return result;
	}


}
