package com.sl.sub.expirer.task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.sl.sub.expirer.context.ExpirerContext;
import com.sl.sub.expirer.vo.KeyVO;

import net.sf.json.JSONObject;

@Repository
public class Task_Check_KeyValTime implements ExpirerTask{

	SqlSession sqlSession;
	
	final String NS = "mapper.com.sl.sub.expirer.task.validationKey.";

	private static Logger logger = LoggerFactory.getLogger(Task_Check_KeyValTime.class);
	
	final String[] week = { "%일%", "%월%", "%화%", "%수%", "%목%", "%금%", "%토%" };
	
	SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
	Date date;

	private KeyVO vo;

	@Override
	public JSONObject execute(JSONObject data) {
		// TODO Auto-generated method stub
		this.beforeExecute();
		//start of this method
		try{	
			//사용 가능한 날짜 문자열 만들기
			this.date = new Date(System.currentTimeMillis());
			this.vo=new KeyVO();
			
			vo.setCurrentTime(Integer.parseInt((df.format(date).toString()).replace(":", "")));
			vo.setDayOfWeek(week[Calendar.getInstance( ).get(Calendar.DAY_OF_WEEK)-1]);
			
			int result1 = sqlSession.update(NS+"setActive",vo);
			int result2 = sqlSession.update(NS+"setInactive",vo);
			logger.info("ACTIVE result    :  "+result1);
			logger.info("INACTIVE result  :  "+result2);
			
			
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
		this.sqlSession = (SqlSessionTemplate) ExpirerContext.context.getBean("sqlSessionTemplate");
	}

	@Override
	public void afterExecute(boolean commit) {
		// TODO Auto-generated method stub

	}

}
