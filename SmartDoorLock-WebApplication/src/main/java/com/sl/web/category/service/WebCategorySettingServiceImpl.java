package com.sl.web.category.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sl.web.category.dao.WebCategorySettingDAO;
import com.sl.web.category.vo.WebCategoryListVO;
import com.sl.web.category.vo.WebCategoryVO;
import com.sl.web.common.context.ContextKey;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class WebCategorySettingServiceImpl implements WebCategorySettingService{

	
	@Autowired
	WebCategorySettingDAO dao;
	
	@Autowired
	HttpSession session;
	
	@Override
	public boolean checkCategory(String email, int cat_id) {
		// TODO Auto-generated method stub
		WebCategoryListVO vo = new WebCategoryListVO();
		vo.setCat_id(cat_id);
		vo.setCrt_email(email);
		
		
		return dao.checkCategory(vo);
	}

	@Override
	public JSONObject getCategoryListInfo(String email, int cat_id) {
		// TODO Auto-generated method stub
		WebCategoryListVO vo = new WebCategoryListVO();
		vo.setCat_id(cat_id);
		vo.setCrt_email(email);
		return dao.getCategoryListInfo(vo);
	}

	@Override
	public JSONObject submitCategoryModify(JSONObject obj) {
		// TODO Auto-generated method stub
		JSONObject ses = (JSONObject)session.getAttribute(ContextKey.LOGIN_MEMBER);
		
		List<WebCategoryVO> list = new ArrayList<WebCategoryVO>();
		Map<String,Object> map = new HashMap<String,Object>();

		int cat_id = obj.getInt("cat_id");
		int member_id = ses.getInt("MEMBER_ID");
		String email = ses.getString("EMAIL");
		JSONArray ary = obj.getJSONArray("category");
		for(int i=0;i<ary.size();i++){
			WebCategoryVO vo = new WebCategoryVO();
			vo.setKey_id(ary.getJSONObject(i).getString("keyId"));
			vo.setCat_id(cat_id);
			vo.setMember_id(member_id);
			vo.setCrt_email(email);
			vo.setUpdt_email(email);
			list.add(vo);
 		}
		map.put("list", list);
		map.put("cat_id",cat_id );
		map.put("member_id",member_id );
		map.put("email",email );
		
		
		return dao.submitCategoryModify(email, map);
	}

	@Override
	public boolean deleteCategoryModify(String email, int cat_id) {
		// TODO Auto-generated method stub
		WebCategoryListVO vo = new WebCategoryListVO();
		vo.setCat_id(cat_id);
		vo.setCrt_email(email);
		
		
		return dao.deleteCategoryModify(vo);
	}
}
