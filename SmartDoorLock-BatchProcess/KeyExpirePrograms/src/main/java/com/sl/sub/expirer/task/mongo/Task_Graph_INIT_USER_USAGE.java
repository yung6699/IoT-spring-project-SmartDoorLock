package com.sl.sub.expirer.task.mongo;


import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import com.mongodb.util.JSON;
import com.sl.sub.expirer.context.ExpirerContext;
import com.sl.sub.expirer.log.SystemLogSetter;
import com.sl.sub.expirer.task.ExpirerTask;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Repository
public class Task_Graph_INIT_USER_USAGE implements ExpirerTask{

	private SqlSession oracleSession;
	private MongoTemplate mongoTemplate;

	private	SystemLogSetter logSetter;
	
	
	final String ORACLE_NS = "mapper.com.sl.sub.expirer.task.graph.";

	private static Logger logger = LoggerFactory.getLogger(Task_Graph_INIT_USER_USAGE.class);
	JSONArray userList = new JSONArray();
	
	JSONObject temp =null;
	//mongoDB update or insert opt
	String query = "";
	String set = "";
	BasicDBObject opt = new BasicDBObject();
	JSONObject user_usageMap = new JSONObject();
	JSONArray user_usageAry = new JSONArray();
	//ㅇㅅㅇ
	JSONArray userUsageBefore = new JSONArray();
	
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
			userUsageBefore = JSONArray.fromObject(oracleSession.selectList(ORACLE_NS+"initSetUsage"));
			logger.info("HelloWorld:"+userUsageBefore.toString());
			
			for(int i=0;i<userUsageBefore.size();i++){
				
				temp = null;
				temp = userUsageBefore.getJSONObject(i);
				if(!temp.containsKey("EMAIL"))temp.put("EMAIL","Anonymous");
				

				//값이 없으니 insert를 때려도 됩니다.
				if(usage_user_isExist(temp)){
					if(this.usage_date_isExist(temp)){
						//값이 존재하니 update를 쳐야한다.
						query=	"{EMAIL:'(1)',GRAPH:{$elemMatch:{LOG_DATE:'(2)'}}}";
						query = query.replace("(2)", temp.getString("LOG_DATE"));
						set = "{$set:{GRAPH.$.LOG_DATE:'(1)',GRAPH.$.COUNT:(2)}}";
						set = set.replace("(1)",temp.getString("LOG_DATE")).replace("(2)", temp.getString("COUNT"));
						WriteResult result = mongoTemplate.getCollection("USER_USAGE").update(
																(DBObject)JSON.parse(query),
																(DBObject)JSON.parse(set),
																false,
																true);
						logger.debug(result.toString());
					}else{
						query=	"{EMAIL:'(1)'}";
						query = query.replace("(1)", temp.getString("EMAIL"));
						set = "{$push:{GRAPH:{LOG_DATE:'(1)',COUNT:(2)}}}";
						set = set.replace("(1)",temp.getString("LOG_DATE")).replace("(2)", temp.getString("COUNT"));
						WriteResult result = mongoTemplate.getCollection("USER_USAGE").update(
																(DBObject)JSON.parse(query),
																(DBObject)JSON.parse(set),
																false,
																true);
						logger.debug(result.toString());
					}
				}else{
					set="{EMAIL:'"+temp.getString("EMAIL")+"',GRAPH:[{LOG_DATE:'"+temp.getString("LOG_DATE")+"',COUNT:"+temp.getString("COUNT")+"}]}";
					WriteResult result = mongoTemplate.getCollection("USER_USAGE").insert((DBObject)JSON.parse(set));						
					logger.debug(result.toString());
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		this.afterExecute(true);
		return data;
	}
	public boolean usage_user_isExist(JSONObject user){
		query="{EMAIL:'"+user.getString("EMAIL")+"'}";
		DBCursor cursor = mongoTemplate.getCollection("USER_USAGE").find((DBObject)JSON.parse(query));
		logger.debug(cursor.toString());
		if(cursor.hasNext()){
			logger.debug(" TRUE : usage_user_isExist:["+user.getString("EMAIL")+"]");
			return true;
		}else{
			logger.debug(" FALSE : usage_user_isExist:["+user.getString("EMAIL")+"]");
			return false;
		}	
	}
	public boolean usage_date_isExist(JSONObject user){
		query=	"{"+
					"EMAIL:'"+user.getString("EMAIL")+"',"+ 
					"GRAPH:{"+
						"$elemMatch:{LOG_DATE:'"+user.getString("LOG_DATE")+"'}"+
					"}"+
				"}";
		DBCursor cursor = mongoTemplate.getCollection("USER_USAGE").find((DBObject)JSON.parse(query));
		logger.debug(cursor.toString());
		if(cursor.hasNext()){
			logger.debug(" TRUE : usage_date_isExist:["+user.getString("EMAIL")+"]");
			return true;
		}else{
			logger.debug(" FALSE : usage_date_isExist:["+user.getString("EMAIL")+"]");
			return false;
		}	
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
