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
public class Task_Graph_INIT_CAT_COMMON_KEY_MEMBER implements ExpirerTask{

	private SqlSession oracleSession;
	private MongoTemplate mongoTemplate;
	private	SystemLogSetter logSetter;
	
	final String ORACLE_NS = "mapper.com.sl.sub.expirer.task.graph.";
	final String COLLECTION = "CAT_COMMON_KEY_MEMBER";
	
	private static Logger logger = LoggerFactory.getLogger(Task_Graph_INIT_CAT_COMMON_KEY_MEMBER.class);
	
	//logic temp variable
	JSONArray catList = null;
	JSONArray catKeys =null;
	JSONArray keyList = null;
	JSONObject catListKeys = null;
	JSONObject catTemp = null;
	JSONObject catMembers =null;
	JSONObject doorlockListKeys = null;
	JSONObject resultTemp=null;
	JSONObject keyTemp=null;
	JSONObject keyToSerial = null;
	String email=null;
	
	int allCount = 0;
	int master = 0;
	int manager = 0;
	int member = 0;
	
	
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	Date date;
	
	
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
			
			

			
			date = new Date(System.currentTimeMillis());
			df.format(date);
			catList = data.getJSONArray("catList");
			catListKeys = data.getJSONObject("catListKeys");
			doorlockListKeys = data.getJSONObject("doorlockListKeys");
			keyList = data.getJSONArray("keyList");
				
			logger.debug("\n\n\n\n\n\n\n\n");
			logger.debug("doorlockListKeys : "+doorlockListKeys.toString());
			logger.debug("catList : "+catList.toString());
			logger.debug("catListKeys : "+catListKeys.toString());
			logger.debug("keyList : "+keyList.toString());
			
			keyToSerial = new JSONObject();

			for(int i=0;i<keyList.size();i++){
				keyTemp = keyList.getJSONObject(i);
				keyToSerial.put(keyTemp.getString("KEY_ID"),keyTemp.getString("SERIAL_NO"));				
			}
			logger.debug("keyToSerial : "+keyToSerial.toString());
			
			for(int i=0;i<catList.size();i++){
			//카테고리 하나에서 for문을 돌린다.
				catTemp = catList.getJSONObject(i);
				catKeys = catListKeys.getJSONArray(catTemp.getString("CAT_ID"));
				catMembers =null;catMembers=new JSONObject();
				master=0;manager=0;member=0;allCount=0;
				for(int j=0;j<catKeys.size();j++){
				//등록되어있던 열쇠들이 나옴
					keyList = doorlockListKeys.getJSONArray((keyToSerial.getString(catKeys.getJSONObject(j).getString("KEY_ID"))));
					for(int k=0;k<keyList.size();k++){
					//도어락이 가지고 있던 열쇠를 출력
						keyTemp = null;
						keyTemp = keyList.getJSONObject(k);
						switch(keyTemp.getString("GRADE")){
							case "MASTER":
								master++;
								break;
							case "MANAGER":
								manager++;
								break;
							case "MEMBER":
								member++;
								break;
							default:
								logger.warn("으아니 슈발 이건 무슨 등급이다냐아아아~~!!");
								break;
						}
						allCount++;
						if(catMembers.containsKey(keyTemp.getString("EMAIL").replace(".", "_"))){
							JSONArray temp = catMembers.getJSONArray(keyTemp.getString("EMAIL").replace(".", "_"));
							temp.add(keyTemp.getString("SERIAL_NO"));
							catMembers.remove(keyTemp.getString("EMAIL").replace(".", "_"));
							catMembers.put(keyTemp.getString("EMAIL").replace(".", "_"), temp);
						}else{
							JSONArray temp = new JSONArray();
							temp.add(keyTemp.getString("SERIAL_NO"));
							catMembers.put(keyTemp.getString("EMAIL").replace(".", "_"), temp);
						}
					}
				}
				resultTemp = new JSONObject();
				resultTemp.put("CAT_ID", catTemp.getString("CAT_ID"));
				resultTemp.put("MASTER", master);
				resultTemp.put("MANAGER", manager);
				resultTemp.put("MEMBER", member);
				resultTemp.put("CAT_NAME",catTemp.getString("CAT_NAME"));
				resultTemp.put("ALL_COUNT", allCount);
				resultTemp.put("DATE", df.format(date).toString());
				resultTemp.put("USERS", catMembers);
				//카테고리 번호로 저장한다.
				
				
				if(category_isExist(resultTemp)){
					if(date_isExist(resultTemp)){
						//날짜가 있을때 업데이트를 칩니다.
						query=	"{CAT_ID:'(1)',GRAPH:{$elemMatch:{DATE:'(2)'}}}";
						query = query
								.replace("(1)", resultTemp.getString("CAT_ID"))
								.replace("(2)", resultTemp.getString("DATE"));
						set = "{$set:{GRAPH.$.DATE:'(1)',"
								+ "GRAPH.$.MASTER:(2),"
								+ "GRAPH.$.MANAGER:(3),"
								+ "GRAPH.$.MEMBER:(4),"
								+ "GRAPH.$.ALL_COUNT:(5),"
								+ "GRAPH.$.CAT_NAME:'(6)',"
								+ "GRAPH.$.USERS:(7)}}";
						set = set.replace("(1)",resultTemp.getString("DATE"))
								.replace("(2)", resultTemp.getString("MASTER"))
								.replace("(3)", resultTemp.getString("MANAGER"))
								.replace("(4)", resultTemp.getString("MEMBER"))
								.replace("(5)", resultTemp.getString("ALL_COUNT"))
								.replace("(6)", resultTemp.getString("CAT_NAME"))
								.replace("(7)", resultTemp.getJSONObject("USERS").toString());
						
						WriteResult result = mongoTemplate.getCollection(COLLECTION).update(
																(DBObject)JSON.parse(query),
																(DBObject)JSON.parse(set),
																false,
																true);
						logger.debug(result.toString());
						
					}else{
						//날짜가 없을때 새로 생성합니다.
						query=	"{CAT_ID:'(1)'}";
						query = query.replace("(1)", resultTemp.getString("CAT_ID"));
						set = "{$push:{GRAPH:{DATE:'(1)',"
								+ "MASTER:(2),"
								+ "MANAGER:(3),"
								+ "MEMBER:(4),"
								+ "ALL_COUNT:(5),"
								+ "CAT_NAME:'(6)',"
								+ "USERS:(7)}}}";
						set = set.replace("(1)",resultTemp.getString("DATE"))
								.replace("(2)", resultTemp.getString("MASTER"))
								.replace("(3)", resultTemp.getString("MANAGER"))
								.replace("(4)", resultTemp.getString("MEMBER"))
								.replace("(5)", resultTemp.getString("ALL_COUNT"))
								.replace("(6)", resultTemp.getString("CAT_NAME"))
								.replace("(7)", resultTemp.getJSONObject("USERS").toString());
						
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
							+ "MASTER:(3),"
							+ "MANAGER:(4),"
							+ "MEMBER:(5),"
							+ "ALL_COUNT:(6),"
							+ "CAT_NAME:'(7)',"
							+ "USERS:(8)}]}";
					set = set.replace("(1)",resultTemp.getString("CAT_ID"))
							.replace("(2)",resultTemp.getString("DATE"))
							.replace("(3)", resultTemp.getString("MASTER"))
							.replace("(4)", resultTemp.getString("MANAGER"))
							.replace("(5)", resultTemp.getString("MEMBER"))
							.replace("(6)", resultTemp.getString("ALL_COUNT"))
							.replace("(7)", resultTemp.getString("CAT_NAME"))
							.replace("(8)", resultTemp.getJSONObject("USERS").toString());
					
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
