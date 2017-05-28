package com.sl.system.gcm;


import java.io.IOException; 

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;


import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.sl.system.common.context.SystemContextKey;

@Repository
public class NotificationService {
	
	Logger logger =LoggerFactory.getLogger(this.getClass());	
	public void pushNotificationToGCM(String gcmRegId,String str){
		Sender  sender = new Sender(SystemContextKey.GCM_API_KEY);
		String title = "스마트락 알림";
		Message msg = new Message.Builder().addData("title",title).addData("message",str).build();
		try{
			if(gcmRegId!=null){
				// retries 3 
				Result result = sender.send(msg, gcmRegId, 3);				
				if(StringUtils.isEmpty(result.getErrorCodeName())){
					logger.debug("GCM push is success :"+result.toString());
				}else{
					logger.debug("GCM occured while sending push notification :"+result.toString());
				}
			}else{
				logger.debug("GCM_ID is not found");
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}
}
