package com.sl.sub.expirer.common;

public class ContextKey {

	public static final String PATH_SMART_DOOR_LOCK = "PATH_SMART_DOOR_LOCK";	
	public static final String PATH_CONFIG = "PATH_CONFIG";
	public static final String PATH_SQL = "PATH_SQL";
	public static final String PATH_LOGS = "PATH_LOGS";	
	public static final String PATH_LOGBACK = "logback.configurationFile";
	public static final String SUB_KEY_EXPIRE="SUB_KEY_EXPIRE";
	//"{send_email}님이 {serial_no}에 대한 열쇠가 만료 되었습니다. 만료 기간 : {message}";
	public static final String SUB_KEY_EXPIRE_MSG="{send_email}님이 {serial_no}에 대한 열쇠가 만료 되었습니다. 만료 기간 : {message}";
	
	public static final String MONGO_COLLECTION_USER_USAGE = "USER_USAGE";
	public static final String MONGO_COLLECTION_USER_KEY_HAVE = "USER_KEY_HAVE";
	
	public static final String MONGO_COLLECTION_DOORLOCK_USAGE = "DOORLOCK_USAGE";
	public static final String MONGO_COLLECTION_DOORLOCK_HAVE_KEY = "DOORLOCK_HAVE_KEY";
	
	public static final String MONGO_COLLECTION_CAT_COMMON_KEY_HAVE = "CAT_COMMON_KEY_HAVE";
	public static final String MONGO_COLLECTION_CAT_COMMON_KEY_USAGE = "CAT_COMMON_KEY_USAGE";
	public static final String MONGO_COLLECTION_CAT_COMMON_KEY_MEMBER = "CAT_COMMON_KEY_MEMBER";
	
	public static final String MONGO_CAT_COMPANY_SECURITY_ENTER_EXIT = "CAT_COMPANY_SECURITY_ENTER_EXIT";
	public static final String MONGO_COLLECTION_CAT_ACCOMO_SALES = "CAT_ACCOMO_SALES";
	
}
