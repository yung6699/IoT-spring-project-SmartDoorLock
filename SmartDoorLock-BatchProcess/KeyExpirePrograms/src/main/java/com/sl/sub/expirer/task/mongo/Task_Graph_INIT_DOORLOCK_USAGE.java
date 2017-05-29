package com.sl.sub.expirer.task.mongo;


import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

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
public class Task_Graph_INIT_DOORLOCK_USAGE implements ExpirerTask{

	private SqlSession oracleSession;
	private MongoTemplate mongoTemplate;
	private	SystemLogSetter logSetter;
	
	final String ORACLE_NS = "mapper.com.sl.sub.expirer.task.graph.";
	final String COLLECTION = "DOORLOCK_USAGE";
	
	private static Logger logger = LoggerFactory.getLogger(Task_Graph_INIT_DOORLOCK_USAGE.class);
	
	//logic temp variable
	JSONArray doorlockList =null;
	JSONArray doorlockLogCount=null;
	JSONObject doorlockTemp =null;
	JSONObject graphTemp = null;
	String serial_no = "";
	
	
	//mongoDB upLOG_DATE or insert opt
	String query = "";
	String set = "";
	
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
			doorlockLogCount = (JSONArray.fromObject(oracleSession.selectList(ORACLE_NS+"initSetDoorlockUsage")));
			
			for(int i=0;i<doorlockLogCount.size();i++){
				graphTemp=null;
				graphTemp=doorlockLogCount.getJSONObject(i);
				logger.debug(graphTemp.toString());
				
				if(doorlock_isExist(graphTemp)){
					if(date_isExist(graphTemp)){
						//날짜가 있을때 업데이트를 칩니다.
						query=	"{SERIAL_NO:'(1)',GRAPH:{$elemMatch:{LOG_DATE:'(2)'}}}";
						query = query
								.replace("(1)", graphTemp.getString("SERIAL_NO"))
								.replace("(2)", graphTemp.getString("LOG_DATE"));
						set = "{$set:{GRAPH.$.LOG_DATE:'(1)',GRAPH.$.COUNT:(2)}}";
						set = set.replace("(1)",graphTemp.getString("LOG_DATE"))
								.replace("(2)", graphTemp.getString("COUNT"));
						
						WriteResult result = mongoTemplate.getCollection(COLLECTION).update(
																(DBObject)JSON.parse(query),
																(DBObject)JSON.parse(set),
																false,
																true);
						logger.debug(result.toString());
						
						
					}else{
						//날짜가 없을때 새로 생성합니다.
						query=	"{SERIAL_NO:'(1)'}";
						query = query.replace("(1)", graphTemp.getString("SERIAL_NO"));
						set = "{$push:{GRAPH:{LOG_DATE:'(1)',COUNT:(2)}}}";
						set = set.replace("(1)",graphTemp.getString("LOG_DATE"))
								.replace("(2)", graphTemp.getString("COUNT"));
						
						WriteResult result = mongoTemplate.getCollection(COLLECTION).update(
																(DBObject)JSON.parse(query),
																(DBObject)JSON.parse(set),
																false,
																true);
						logger.debug(result.toString());
					}
				}else{
					//등록된 사용자가 없을경우 새로 DB를 생성합니다.
					set = "{SERIAL_NO:'(1)',GRAPH:[{LOG_DATE:'(2)',COUNT:(3)}]}";
					set = set
							.replace("(1)", graphTemp.getString("SERIAL_NO"))
							.replace("(2)", graphTemp.getString("LOG_DATE"))
							.replace("(3)", graphTemp.getString("COUNT"));
					WriteResult result = mongoTemplate.getCollection(COLLECTION).insert((DBObject)JSON.parse(set));						
					logger.debug(result.toString());
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		this.afterExecute(true);
		return data;
	}
	
	public boolean doorlock_isExist(JSONObject user){
		query="{SERIAL_NO:'"+user.getString("SERIAL_NO")+"'}";
		DBCursor cursor = mongoTemplate.getCollection(COLLECTION).find((DBObject)JSON.parse(query));
		logger.debug(cursor.toString());
		if(cursor.hasNext()){
			logger.debug(" TRUE : doorlock_isExist:["+user.getString("SERIAL_NO")+"]");
			return true;
		}else{
			logger.debug(" FALSE : doorlock_isExist:["+user.getString("SERIAL_NO")+"]");
			return false;
		}	
	}
	public boolean date_isExist(JSONObject user){
		query=	"{"+
					"SERIAL_NO:'"+user.getString("SERIAL_NO")+"',"+ 
					"GRAPH:{"+
						"$elemMatch:{LOG_DATE:'"+user.getString("LOG_DATE")+"'}"+
					"}"+
				"}";
		DBCursor cursor = mongoTemplate.getCollection(COLLECTION).find((DBObject)JSON.parse(query));
		logger.debug(cursor.toString());
		if(cursor.hasNext()){
			logger.debug(" TRUE : date_isExist:["+user.getString("SERIAL_NO")+"]");
			return true;
		}else{
			logger.debug(" FALSE : date_isExist:["+user.getString("SERIAL_NO")+"]");
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
