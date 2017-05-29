package com.sl.sub.expirer.config;

import java.util.Map;

import javax.naming.Context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sl.sub.expirer.common.ContextKey;

@Component
public class ExpirerConfig {
	Map<String, String> map;

	private static Logger logger = LoggerFactory.getLogger(ExpirerConfig.class);

	public ExpirerConfig() {

		String path;

		// path = ResourceUtils.getURL("src").getPath();
		// 개발
		path = "C:/Workspace/SpringWorkspace/SmartLockSubProgram/SMART_DOOR_LOCK"; 
		// 운영
//		path = "/usr/local/develop/tomcat7/subapps/SMART_DOOR_LOCK";
		System.setProperty(ContextKey.PATH_SMART_DOOR_LOCK,path);
		System.setProperty(ContextKey.PATH_CONFIG, System.getProperty(ContextKey.PATH_SMART_DOOR_LOCK) + "/config");
		System.setProperty(ContextKey.PATH_SQL, System.getProperty(ContextKey.PATH_SMART_DOOR_LOCK) + "/sql");
		System.setProperty(ContextKey.PATH_LOGS, System.getProperty(ContextKey.PATH_SMART_DOOR_LOCK) + "/logs");
		System.setProperty(ContextKey.PATH_LOGBACK, System.getProperty(ContextKey.PATH_SMART_DOOR_LOCK)+"/logback.xml");
		System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.xbean.spring.jndi.SpringInitialContextFactory");
		// ExpirerContext.TASK_INTERVAL_KEY_MST = (long)System.getP;
		// ---- properties/context.properties
	}

}
