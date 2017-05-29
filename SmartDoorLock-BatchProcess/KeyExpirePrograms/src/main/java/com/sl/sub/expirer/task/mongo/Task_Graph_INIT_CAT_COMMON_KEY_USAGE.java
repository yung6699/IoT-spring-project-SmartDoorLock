package com.sl.sub.expirer.task.mongo;


import java.text.SimpleDateFormat;
import java.util.Date;

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
public class Task_Graph_INIT_CAT_COMMON_KEY_USAGE implements ExpirerTask{

	private SqlSession oracleSession;
	private MongoTemplate mongoTemplate;
	private	SystemLogSetter logSetter;
	
	final String ORACLE_NS = "mapper.com.sl.sub.expirer.task.graph.";
	final String COLLECTION = "CAT_COMMON_KEY_USAGE";
	
	private static Logger logger = LoggerFactory.getLogger(Task_Graph_INIT_CAT_COMMON_KEY_USAGE.class);
	
	
	//logic temp variable
	JSONArray doorlockList = null;
	JSONObject doorlockUsage = new JSONObject();
	JSONObject temp;
	JSONObject tempObj; 
	JSONArray tempAry;
	
	JSONObject catListDoorlock = new JSONObject();
	JSONObject catListKeys = null;
	
	JSONArray catList = null;	
	JSONArray catListTemp =null;
	
	JSONObject catTemp = null;
	
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	Date date = new Date(System.currentTimeMillis());
	String dateString="";
	int count = 0;

	
	//mongoDB update or insert opt
	String query = "";
	String set = "";
	
	@Override
	public JSONObject execute(JSONObject data) {
		// TODO Auto-generated method stub
		this.beforeExecute();
		//start of this method
		try{	
/*			
			data.put("userList", userList);
			data.put("userListKeys", userListKeys);
			data.put("userListDoorlock", userListDoorlock);
			
			data.put("catList", catList);
			data.put("catListKeys", catListKeys);
			
			data.put("doorlockList", doorlockList);
			data.put("doorlockListKeys", doorlockListKeys);		
			
			data.put("keyList", keyList);
*/			
			
			logger.info(data.toString());
			DBCursor cursor = mongoTemplate.getCollection("DOORLOCK_USAGE").find();
			date = new Date(System.currentTimeMillis());
			dateString = df.format(date);
			while(cursor.hasNext()){
				temp = null;
				temp = JSONObject.fromObject(cursor.next());
				tempObj = new JSONObject();
				tempAry = temp.getJSONArray("GRAPH");
				String serial_no = temp.getString("SERIAL_NO");
				for(int i=0;i<tempAry.size();i++){
					temp = tempAry.getJSONObject(i);
					tempObj.put(temp.getString("LOG_DATE"),temp.getString("COUNT"));
				}
				doorlockUsage.put(serial_no,tempObj);
			}
			
			
			doorlockList = data.getJSONArray("doorlockList");
			catListKeys = data.getJSONObject("catListKeys");
			catList = data.getJSONArray("catList");
			logger.info("doorlockUsage:"+doorlockUsage.toString());

			
			for(int i=0;i<catList.size();i++){
				catListTemp = catListKeys.getJSONArray(catList.getJSONObject(i).getString("CAT_ID"));
				logger.info(catListTemp.toString());
				count=0;
				for(int j=0;j<catListTemp.size();j++){
					tempObj = doorlockUsage.getJSONObject(catListTemp.getJSONObject(j).getString("SERIAL_NO"));
					if(tempObj.containsKey(dateString)){
						count+=tempObj.getInt(dateString);
					}
				}
				temp=null;
				temp=new JSONObject();
				temp.put("DATE", dateString);
				temp.put("CAT_ID", catList.getJSONObject(i).getString("CAT_ID"));
				temp.put("COUNT", count);
				
				if(category_isExist(temp)){
					if(date_isExist(temp)){
						//날짜가 있을때 업데이트를 칩니다.
						query=	"{CAT_ID:'(1)',GRAPH:{$elemMatch:{DATE:'(2)'}}}";
						query = query
								.replace("(1)", temp.getString("CAT_ID"))
								.replace("(2)", temp.getString("DATE"));
						set = "{$set:{GRAPH.$.DATE:'(1)',"
								+ "GRAPH.$.COUNT:(2)}}";
						set = set.replace("(1)",temp.getString("DATE"))
								.replace("(2)", temp.getString("COUNT"));
						
						WriteResult result = mongoTemplate.getCollection(COLLECTION).update(
																(DBObject)JSON.parse(query),
																(DBObject)JSON.parse(set),
																false,
																true);
						logger.debug(result.toString());
						
					}else{
						//날짜가 없을때 새로 생성합니다.
						query=	"{CAT_ID:'(1)'}";
						query = query.replace("(1)", temp.getString("CAT_ID"));
						set = "{$push:{GRAPH:{DATE:'(1)',"
								+ "COUNT:(2)}}}";
						set = set.replace("(1)",temp.getString("DATE"))
								.replace("(2)", temp.getString("COUNT"));
						
						WriteResult result = mongoTemplate.getCollection(COLLECTION).update(
																(DBObject)JSON.parse(query),
																(DBObject)JSON.parse(set),
																false,
																true);
						
						logger.debug(result.toString());
					}
				}else{
					//등록된 사용자가 없을경우 새로 DB를 생성합니다.
					set = "{CAT_ID:'(1)',GRAPH:["
							+ "{DATE:'(2)',"
							+ "COUNT:(3)}]}";
					set = set.replace("(1)",temp.getString("CAT_ID"))
							.replace("(2)",temp.getString("DATE"))
							.replace("(3)", temp.getString("COUNT"));
					
					WriteResult result = mongoTemplate.getCollection(COLLECTION).insert((DBObject)JSON.parse(set));						
					logger.debug(result.toString());
				}
			
			}

			
			date = new Date(System.currentTimeMillis());
			df.format(date);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		this.afterExecute(true);
		return data;
	}
	
	public boolean category_isExist(JSONObject user){
		query="{CAT_ID:'"+user.getString("CAT_ID")+"'}";
		DBCursor cursor = mongoTemplate.getCollection(COLLECTION).find((DBObject)JSON.parse(query));
		logger.debug(cursor.toString());
		if(cursor.hasNext()){
			logger.debug(" TRUE : category_isExist:["+user.getString("CAT_ID")+"]");
			return true;
		}else{
			logger.debug(" FALSE : category_isExist:["+user.getString("CAT_ID")+"]");
			return false;
		}	
	}
	public boolean date_isExist(JSONObject user){
		query=	"{"+
					"CAT_ID:'"+user.getString("CAT_ID")+"',"+ 
					"GRAPH:{"+
						"$elemMatch:{DATE:'"+user.getString("DATE")+"'}"+
					"}"+
				"}";
		DBCursor cursor = mongoTemplate.getCollection(COLLECTION).find((DBObject)JSON.parse(query));
		logger.debug(cursor.toString());
		if(cursor.hasNext()){
			logger.debug(" TRUE : date_isExist:["+user.getString("CAT_ID")+"]");
			return true;
		}else{
			logger.debug(" FALSE : date_isExist:["+user.getString("CAT_ID")+"]");
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
