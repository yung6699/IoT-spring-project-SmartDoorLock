package com.sl.sub.expirer.context;

import org.springframework.context.support.AbstractApplicationContext;

import net.sf.json.JSONObject;

public class ExpirerContext {
	public static final String GCM_API_KEY = "AIzaSyB5hKgeCqBLmnty3EVwvT9JMx7KqKb7TJ8";
	
	public static AbstractApplicationContext context;
	public static long TASK_INTERVAL_KEY_VAL_TIME = 0; 
	public static long TASK_INTERVAL_KEY_MST = 0;
	public static long SCHEDULER_INTERVAL_TIME = 0; 
	public static long SCHEDULER_START_TIME = 0; 
	public static long TASK_INTERVAL_GRAPH = 0;

	public static JSONObject data;

	public static int CAT_ACCOMODATION_USUAL_PRICE=0;
	public static int CAT_ACCOMODATION_HOLIDAY_PRICE=0;
	
	
}
