package com.sl.web.tag.select.json;

import java.util.Locale;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class WebTagSelectJSON {

	@Autowired
	SqlSession sqlSession;
	
	final String NS="mapper.com.sl.web.tag.select.";

	
	//홈페이지 보이기
		@RequestMapping(value = "/tag/select", method = RequestMethod.POST)	
		@ResponseBody
		public JSONArray main(WebTagSelectVO vo, Locale locale, Model model) {
			JSONArray ary = JSONArray.fromObject(sqlSession.selectList(NS+"tagSelect",vo));
			if(ary.size()==0){
				JSONObject err = new JSONObject();
				err.put("id", "err");
				err.put("text", "CODE_MST,CODE_DET테이블에 없는 내용입니다.");
				ary.add(err);
				return ary;	
			}else{
				return ary;
			}
		}
	
	
}
