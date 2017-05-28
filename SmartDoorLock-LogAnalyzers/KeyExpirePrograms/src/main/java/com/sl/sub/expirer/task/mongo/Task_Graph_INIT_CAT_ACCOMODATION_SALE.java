package com.sl.sub.expirer.task.mongo;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.xml.soap.Detail;

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
public class Task_Graph_INIT_CAT_ACCOMODATION_SALE implements ExpirerTask{

	private SqlSession oracleSession;
	private MongoTemplate mongoTemplate;
	private	SystemLogSetter logSetter;
	
	final String ORACLE_NS = "mapper.com.sl.sub.expirer.task.graph.";
	final String COLLECTION = "CAT_ACCOMODATION_SALE";
	
	private static Logger logger = LoggerFactory.getLogger(Task_Graph_INIT_CAT_ACCOMODATION_SALE.class);
	
	//logic temp variable
	JSONArray catList = null;
	JSONObject catListKeys = null;
	
	JSONArray catAccomodationSales=null;
	JSONObject catAccomodationSale = null;
	//logic temp variable
	JSONObject catTemp = null;
	JSONArray catTempList = null;
	JSONObject catTempKey = null;
	JSONObject saleDetail = null;
	JSONArray detail = null;
	JSONObject resultTemp = null;
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	Date date;
	String dateString = "";

	//######################### 모텔 운영
	//총 판매 금액 int allIncome
	int allIncome;
	//총 판매 개수 int allCount
	int allCount;
	
	//DOORLOCK NAME 내역 DETAIL
	//도어락 이름 int doorlockName
	String serial_no;
	//도어락 당 판매 금액 int doorlockIncome
	int doorlockIncome;
	//도어락 당 판매 횟수 int doorlockCount 
	int doorlockCount;
	
	//int price 룸당 가격
	int price=0;

	
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
			catAccomodationSales = new JSONArray();
			catAccomodationSale = new JSONObject();
			
			date = new Date(System.currentTimeMillis());
			dateString = df.format(date).toString();
//			dateString = "2016-11-12";
			price = 0;
			switch(Calendar.getInstance().get(Calendar.DAY_OF_WEEK)-1){
				case 0:
				case 6:
					price = ExpirerContext.CAT_ACCOMODATION_HOLIDAY_PRICE;
					break;
				case 1:
				case 2:
				case 3:
				case 4:
				case 5:
					price = ExpirerContext.CAT_ACCOMODATION_USUAL_PRICE;
					break;
				default:
					price = ExpirerContext.CAT_ACCOMODATION_HOLIDAY_PRICE;
					break;
			}
			
			catList = data.getJSONArray("catList");
			catListKeys = data.getJSONObject("catListKeys");
			
			catAccomodationSales = new JSONArray();
			catAccomodationSales.addAll(oracleSession.selectList("initSetAccomodationSale",dateString));
			for(int i=0;i<catAccomodationSales.size();i++){
				catTemp = catAccomodationSales.getJSONObject(i);
				catAccomodationSale.put(catTemp.getString("SERIAL_NO"),catTemp.getString("COUNT"));
			}
			logger.info(catAccomodationSale.toString());
			for(int i=0;i<catList.size();i++){
				catTemp = catList.getJSONObject(i);
				catTempList = catListKeys.getJSONArray(catTemp.getString("CAT_ID"));
				
				allCount=0;
				allIncome=0;
				serial_no="";
				doorlockCount=0;
				doorlockIncome=0;
				detail=new JSONArray();
				for(int j=0;j<catTempList.size();j++){
					catTempKey= catTempList.getJSONObject(j);
					serial_no=catTempKey.getString("SERIAL_NO");
					if(catAccomodationSale.containsKey(serial_no)){
						doorlockCount = catAccomodationSale.getInt(serial_no);
						allCount += doorlockCount;
						doorlockIncome = doorlockCount*price;
						allIncome += doorlockIncome;
					}
					saleDetail = new JSONObject();
					saleDetail.put("SERIAL_NO", serial_no);
					saleDetail.put("COUNT", doorlockCount);
					saleDetail.put("INCOME", doorlockIncome);
					detail.add(saleDetail);
				}
				resultTemp=new JSONObject();
				resultTemp.put("CAT_ID", catTemp.getString("CAT_ID"));
				resultTemp.put("DATE", dateString);
				resultTemp.put("ALL_COUNT", allCount);
				resultTemp.put("ALL_INCOME", allIncome);
				resultTemp.put("DETAIL", detail);
				
				logger.info("resultTemp: "+resultTemp.toString());
				
				if(category_isExist(resultTemp)){
					if(date_isExist(resultTemp)){
						//날짜가 있을때 업데이트를 칩니다.
						query=	"{CAT_ID:'(1)',GRAPH:{$elemMatch:{DATE:'(2)'}}}";
						query = query
								.replace("(1)", resultTemp.getString("CAT_ID"))
								.replace("(2)", resultTemp.getString("DATE"));
						set = "{$set:{GRAPH.$.DATE:'(1)',"
								+ "GRAPH.$.ALL_COUNT:(2),"
								+ "GRAPH.$.ALL_INCOME:(3),"
								+ "GRAPH.$.DETAIL:(4)}}";
						set = set.replace("(1)",resultTemp.getString("DATE"))
								.replace("(2)", resultTemp.getString("ALL_COUNT"))
								.replace("(3)", resultTemp.getString("ALL_INCOME"))
								.replace("(4)", resultTemp.getJSONArray("DETAIL").toString());
						
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
								+ "ALL_COUNT:(2),"
								+ "ALL_INCOME:(3),"
								+ "DETAIL:(4)}}}";
						set = set.replace("(1)",resultTemp.getString("DATE"))
								.replace("(2)", resultTemp.getString("ALL_COUNT"))
								.replace("(3)", resultTemp.getString("ALL_INCOME"))
								.replace("(4)", resultTemp.getJSONArray("DETAIL").toString());
						
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
							+ "ALL_COUNT:(3),"
							+ "ALL_INCOME:(4),"
							+ "DETAIL:(5)}]}";
					set = set.replace("(1)",resultTemp.getString("CAT_ID"))
							.replace("(2)",resultTemp.getString("DATE"))
							.replace("(3)", resultTemp.getString("ALL_COUNT"))
							.replace("(4)", resultTemp.getString("ALL_INCOME"))
							.replace("(5)", resultTemp.getJSONArray("DETAIL").toString());
					
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
