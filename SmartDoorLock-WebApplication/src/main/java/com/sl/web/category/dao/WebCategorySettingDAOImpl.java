package com.sl.web.category.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.sl.app.common.context.AppContextKey;
import com.sl.system.log.setter.SystemLogSetter;
import com.sl.system.session.member.SessionMember;
import com.sl.web.category.vo.WebCategoryListVO;
import com.sl.web.category.vo.WebCategoryVO;
import com.sl.web.common.context.ContextKey;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Repository
public class WebCategorySettingDAOImpl implements WebCategorySettingDAO{

	@Autowired
	PlatformTransactionManager platformTransactionManager;
	
	@Autowired
	SystemLogSetter logSetter;
	
	@Autowired
	private SqlSession sqlSession;
	
	@Autowired
	private SessionMember sessionMember;
	
	final String NS = "mapper.com.sl.web.category.";
	//우선은 새로 추가 할 수 있도록...
	//모두를 관리 하는것도 있지만 아 시발.. 

	@Override
	public boolean checkCategory(WebCategoryListVO vo) {
		// TODO Auto-generated method stub
		if(((Integer)sqlSession.selectOne(NS+"checkCategory",vo))==1){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public JSONObject getCategoryListInfo(WebCategoryListVO vo) {
		// TODO Auto-generated method stub
		JSONObject obj = new JSONObject();
		obj.put("CAT_LIST", (JSONObject.fromObject( sqlSession.selectOne(NS+"getCategoryListInfo1",vo))));
		obj.put("CAT_KEY", (JSONArray.fromObject(sqlSession.selectList(NS+"getCategoryListInfo2",vo))));
		obj.put("KEY_MST", (JSONArray.fromObject(sqlSession.selectList(NS+"getCategoryListInfo3",vo))));
		
		
		return obj;
	}

	@Override
	public JSONObject submitCategoryModify(String email, Map<String, Object> map) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setName(WebCategorySettingDAOImpl.class.getName());
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = platformTransactionManager.getTransaction(def);
		
		try{
			sqlSession.delete(NS+"submitCategoryModify1",map);
			
			@SuppressWarnings("unchecked")
			List<WebCategoryVO> list = (List<WebCategoryVO>) map.get("list");
			int count=0;
			for(int i=0; i<list.size();i++){
				WebCategoryVO vo = list.get(i);
				vo.setSort(i);
				sqlSession.insert(NS+"submitCategoryModify2",vo);
				count++;
			}
			if(count == list.size()){
				result.put(AppContextKey.AJAX_RESULT,AppContextKey.AJAX_SUCCESS);
				platformTransactionManager.commit(status);
				logSetter.setSystemLog(logSetter.builder(
					ContextKey.LOG_STATUS_CATEGORY_UPDATE, 
					email,
					null,
					null,
					null
					)
				);
				return result;
			}else{
				new Exception();
			}
		}catch(Exception e){
			result.put(AppContextKey.AJAX_RESULT,AppContextKey.AJAX_FAIL);
			platformTransactionManager.rollback(status);
			e.printStackTrace();
			logSetter.setSystemLog(logSetter.builder(
				ContextKey.LOG_STATUS_CATEGORY_UPDATE_FAIL, 
				email,
				null,
				null,
				null
				)
			);
			return result;
		}
		return result;
	}

	@Override
	public boolean deleteCategoryModify(WebCategoryListVO vo) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setName(WebCategorySettingDAOImpl.class.getName());
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		TransactionStatus status = platformTransactionManager.getTransaction(def);
		
		try{
			if(sqlSession.delete(NS+"deleteCategoryModify1",vo)>=0){
				if(sqlSession.delete(NS+"deleteCategoryModify2",vo)==1){
					logSetter.setSystemLog(logSetter.builder(
						ContextKey.LOG_STATUS_CATEGORY_DELETE, 
						vo.getCrt_email(),
						null,
						null,
						null
						)
					);
					return true;
				}else{
					new Exception();
				}
			}else{
				new Exception();
			}
		}catch(Exception e){
			result.put(AppContextKey.AJAX_RESULT,AppContextKey.AJAX_FAIL);
			platformTransactionManager.rollback(status);
			e.printStackTrace();
			logSetter.setSystemLog(logSetter.builder(
				ContextKey.LOG_STATUS_CATEGORY_DELETE_FAIL, 
				vo.getCrt_email(),
				null,
				null,
				null
				)
			);
			return false;
		}
		return false;
	}
}
