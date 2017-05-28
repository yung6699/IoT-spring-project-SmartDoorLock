package com.sl.web.graph.dao;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.sl.web.graph.vo.WebGraphVO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Repository
public class WebGraphDAOImpl implements WebGraphDAO {
	
	@Autowired
	private SqlSession sqlSession;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private final String NS = "mapper.com.sl.web.graph.";
	@Override
	public JSONArray getGraph(WebGraphVO vo){
		String query="";
		if("USER_USAGE".equals(vo.getCollection())){
			query="{EMAIL:'"+vo.getEmail()+"'}";
		}else if("USER_HAVE_KEY".equals(vo.getCollection())){
			query="{EMAIL:'"+vo.getEmail()+"'}";
		}else if("USER_MANAGE_DOORLOCK".equals(vo.getCollection())){
			vo.setCollection("DOORLOCK_USAGE");
			JSONArray ary = JSONArray.fromObject(sqlSession.selectList("selectListDoorlock",vo));
			JSONArray result1 = new JSONArray();
			for(int i=0;i<ary.size();i++){
				result1.add(ary.getJSONObject(i).getString("SERIAL_NO"));
			}
			query="{SERIAL_NO:{$in:"+result1.toString()+"}}";
		}else if("USER_MANAGE_DOORLOCK_USAGE".equals(vo.getCollection())){
			vo.setCollection("DOORLOCK_KEY_HAVE");
			JSONArray ary = JSONArray.fromObject(sqlSession.selectList("selectListDoorlock",vo));
			JSONArray result1 = new JSONArray();
			for(int i=0;i<ary.size();i++){
				result1.add(ary.getJSONObject(i).getString("SERIAL_NO"));
			}
			logger.info(result1.toString());
			query="{SERIAL_NO:{$in:"+result1.toString()+"}}";
			logger.info(query);
		}
		
		DBCursor cursor = mongoTemplate.getCollection(vo.getCollection()).find((DBObject)JSON.parse(query));
		JSONObject temp = null;
		JSONArray result = new JSONArray();
		while(cursor.hasNext()){
			temp = JSONObject.fromObject(cursor.next());

			result.add(temp);
		}
		return result;
	}
	@Override
	public JSONObject getServiceInfo(WebGraphVO vo) {
		// TODO Auto-generated method stub
		JSONObject result = new JSONObject();
		result.put("keyGrade",JSONArray.fromObject(sqlSession.selectList("selectServiceUsageKeyGrade")));
		result.put("usageKeyCount",JSONObject.fromObject(sqlSession.selectOne("selectServiceUsageKeyCount")));
		result.put("usageDoorlockCount",JSONObject.fromObject(sqlSession.selectOne("selectServiceUsageDoorlock")));
		result.put("usageServiceCount", JSONArray.fromObject(sqlSession.selectList("selectServiceUsageCountPerDay")));
		result.put("usageAppCount", JSONArray.fromObject(sqlSession.selectList("selectAppUsageCountPerDay")));
		result.put("usageWebCount", JSONArray.fromObject(sqlSession.selectList("selectWebUsageCountPerDay")));
		result.put("usageUserCount", JSONObject.fromObject(sqlSession.selectOne("selectServiceUsageMember")));
		
		return result;
	}
}
