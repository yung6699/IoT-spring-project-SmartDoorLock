package com.sl.sub.expirer.task;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.sl.sub.expirer.common.ContextKey;
import com.sl.sub.expirer.context.ExpirerContext;
import com.sl.sub.expirer.log.SystemLogSetter;
import com.sl.sub.expirer.vo.KeyVO;

import net.sf.json.JSONObject;

@Repository
public class Task_CheckExpiredKey implements ExpirerTask{

	private SqlSession sqlSession;
	private	SystemLogSetter logSetter;
	
	final String NS = "mapper.com.sl.sub.expirer.task.expiredKey.";

	private static Logger logger = LoggerFactory.getLogger(Task_CheckExpiredKey.class);

	private List<KeyVO> voList;
	private List<String> userList;
	private String newspeedMessage = "";

	public JSONObject execute(JSONObject data) {
		// TODO Auto-generated method stub
		this.beforeExecute();
		//start of this method
		try{	
			this.voList=sqlSession.selectList(NS+"getExpiredKeySerial_no");
			for(KeyVO vo : this.voList){
				//LogSetter
				
				this.logSetter.setNewspeed(
					this.logSetter.builder(
						ContextKey.SUB_KEY_EXPIRE,
						vo.getEmail(),
						null,
						vo.getSerial_no(),
						vo.getStart_date()+"~"+vo.getExpire_date()
					)
					, null
				);
				this.newspeedMessage = ContextKey.SUB_KEY_EXPIRE_MSG;
				this.newspeedMessage = this.newspeedMessage.replace("{send_email}", vo.getEmail())
												.replace("{serial_no}", vo.getSerial_no())
												.replace("{message}", vo.getStart_date()+"~"+vo.getExpire_date());
				this.userList = this.sqlSession.selectList(NS+"getExpiredKeySerial_no");
				this.sendPush(this.userList,this.newspeedMessage);
			}			
			this.sqlSession.update(NS+"setExpired");
			
			
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
		this.logSetter = (SystemLogSetter) ExpirerContext.context.getBean("logSetter");
	}

	@Override
	public void afterExecute(boolean commit) {
		// TODO Auto-generated method stuf
	}


	public boolean sendPush(List<String> regList, String str){
		Sender  sender = new Sender(ExpirerContext.GCM_API_KEY);
		Message msg = new Message.Builder().addData("message",str).build();
		try{
			if(regList!=null &&regList.size()==0){
				// retries 3 
				MulticastResult multicastResult = sender.send(msg, regList, 3);
				for(Result result : multicastResult.getResults()){
					if(StringUtils.isEmpty(result.getErrorCodeName())){
						logger.debug(result.getCanonicalRegistrationId()+ " success : "+result.toString());
					}else{
						logger.debug(result.getCanonicalRegistrationId()+ " occured error : "+result.toString());
					}					
				}
			}else{
				logger.debug("no have list");
				return false;
			}
		}
		catch(IOException e){
			return false;
		}
		return false;
	}
	
}
