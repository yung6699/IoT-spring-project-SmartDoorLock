package com.sl.sub.expirer.task.mongo;


import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.sl.sub.expirer.context.ExpirerContext;
import com.sl.sub.expirer.log.SystemLogSetter;
import com.sl.sub.expirer.task.ExpirerTask;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Repository
public class Task_Graph_MakeData implements ExpirerTask{

	private SqlSession oracleSession;
	private MongoTemplate mongoTemplate;

	private	SystemLogSetter logSetter;
	
	
	final String ORACLE_NS = "mapper.com.sl.sub.expirer.task.graph.";

	private static Logger logger = LoggerFactory.getLogger(Task_Graph_MakeData.class);

	
	JSONObject temp =null;
	//users info
	JSONArray userList = new JSONArray();
	JSONObject userListKeys = new JSONObject();
	JSONObject userListDoorlock = new JSONObject();
	
	//cat info
	JSONArray catList = new JSONArray();
	JSONObject catListKeys = new JSONObject();
	
	//doorlock info
	JSONArray doorlockList = new JSONArray();
	JSONObject doorlockListKeys = new JSONObject();
	
	//key info
	JSONArray keyList = new JSONArray();
	
	@Override
	public JSONObject execute(JSONObject data) {
		// TODO Auto-generated method stub
		this.beforeExecute();
		//start of this method
		try{	
			//모든 사용자들의 정보를 가져온다.
			userList.addAll(oracleSession.selectList(ORACLE_NS+"selectListMembers"));
			//모든 사용자 들의 열쇠를 가져온다.
			for(int i=0;i<userList.size();i++){
				temp = null;
				temp = userList.getJSONObject(i);
				userListKeys.put(
					temp.getString("EMAIL"),
					oracleSession.selectList(ORACLE_NS+"selectListMembersKeys",temp)
				);
			}
			//모든 사용자들의 도어락을 가져 온다.
			for(int i=0;i<userList.size();i++){
				temp = null;
				temp =  userList.getJSONObject(i);
				userListDoorlock.put(
					temp.getString("EMAIL"),
					oracleSession.selectList(ORACLE_NS+"selectListMembersDoorlock",temp)
				);
			}
			//모든 사용자의 카테고리를 가져온다.
			catList.addAll(oracleSession.selectList(ORACLE_NS+"selectListCat"));
			//모든 사용자의 카테고리의 열쇠들을 가져온다.
			for(int i=0;i<catList.size();i++){
				temp = null;
				temp = catList.getJSONObject(i);
				catListKeys.put(
					temp.getString("CAT_ID"),
					oracleSession.selectList(ORACLE_NS+"selectListCatKey",temp)
				);
			}
			
			//모든 도어락을 가져온다.
			doorlockList.addAll(oracleSession.selectList(ORACLE_NS+"selectListDoorlock"));
			//모든 도어락의 열쇠들을 가져온다.
			for(int i=0;i<doorlockList.size();i++){
				temp = null;
				temp = doorlockList.getJSONObject(i);
				doorlockListKeys.put(
					temp.getString("SERIAL_NO"),
					oracleSession.selectList(ORACLE_NS+"selectListDoorlockKey",temp)
				);
			}
			
			keyList.addAll(oracleSession.selectList(ORACLE_NS+"selectListKey"));
		}catch(Exception e){
			e.printStackTrace();
		}
		//end of this method
		this.afterExecute(true);
		
		data.put("userList", userList);
		data.put("userListKeys", userListKeys);
		data.put("userListDoorlock", userListDoorlock);
		
		data.put("catList", catList);
		data.put("catListKeys", catListKeys);
		
		data.put("doorlockList", doorlockList);
		data.put("doorlockListKeys", doorlockListKeys);
		
		data.put("keyList", keyList);
		
		return data;
	}
	@Override
	public void beforeExecute() {
		// TODO Auto-generated method stub
		this.oracleSession = (SqlSessionTemplate) ExpirerContext.context.getBean("sqlSessionTemplate");
		this.mongoTemplate = (MongoTemplate) ExpirerContext.context.getBean("mongoTemplate");
		this.logSetter = (SystemLogSetter) ExpirerContext.context.getBean("logSetter");

	}

	@Override
	public void afterExecute(boolean commit) {
		// TODO Auto-generated method stuf
	}
}
