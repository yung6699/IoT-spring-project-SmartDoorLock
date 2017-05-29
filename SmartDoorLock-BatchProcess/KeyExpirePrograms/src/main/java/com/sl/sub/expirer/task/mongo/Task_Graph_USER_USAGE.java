package com.sl.sub.expirer.task.mongo;


import java.util.HashMap;
import java.util.Iterator;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.mongodb.DBObject;
import com.sl.sub.expirer.context.ExpirerContext;
import com.sl.sub.expirer.log.SystemLogSetter;
import com.sl.sub.expirer.task.ExpirerTask;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Repository
public class Task_Graph_USER_USAGE implements ExpirerTask{

	private SqlSession oracleSession;
	private MongoTemplate mongoTemplate;

	private	SystemLogSetter logSetter;
	
	
	final String ORACLE_NS = "mapper.com.sl.sub.expirer.task.graph.";

	private static Logger logger = LoggerFactory.getLogger(Task_Graph_USER_USAGE.class);
	JSONArray userList = new JSONArray();
	JSONArray result = null;
	JSONObject temp =null;
	private DBObject arr;
			
	@Override
	public JSONObject execute(JSONObject data) {
		// TODO Auto-generated method stub
		this.beforeExecute();
		//start of this method
		try{	
			logger.info(data.toString());
/*			
			data.put("userList", userList);
			data.put("userListKeys", userListKeys);
			data.put("userListDoorlock", userListDoorlock);
			
			data.put("catList", catList);
			data.put("catListKeys", catListKeys);
			
			data.put("doorlockList", doorlockList);
			data.put("doorlockListKeys", doorlockListKeys);		
*/			
			userList = data.getJSONArray("userList");
			for(int i=0;i<userList.size();i++){
				temp = userList.getJSONObject(i);
				//
			}
//			mongoTemplate.insert(userList, "temp");
			
			
			
			
			
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		//end of this method
		this.afterExecute(true);
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
