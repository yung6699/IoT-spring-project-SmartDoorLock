package com.sl.app.common.context;


public class AppContextKey {
	

	public static final String AJAX_RESULT = "AJAX_RESULT";
	public static final String AJAX_SUCCESS = "AJAX_SUCCESS";
	public static final String AJAX_FAIL = "AJAX_FAIL";
	
	public static final String RESULT_MSG = "RESULT_MSG";
	public static final String RESULT_SUCCESS = "RESULT_SUCCESS";
	public static final String RESULT_FAIL = "RESULT_FAIL";
	
	public static final String MSG_JOIN_EMAIL_CEHCK_EMAIL_SUCCESS = "해당 이메일은 사용하실 수 있습니다.";
	public static final String MSG_JOIN_EMAIL_CEHCK_EMAIL_FAIL = "해당 이메일은 이미 사용중 입니다.";
	
	public static final String MSG_LOGIN_FAIL = "이메일과 비밀번호를 확인해 주세요. 또는 이메일 인증을 해주시기 바랍니다.";
	
	public static final String MSG_JOIN_FAIL = "회원가입에 실패하였습니다.";
	public static final String MSG_JOIN_SUCCESS = "회원가입에 성공하였습니다. 이메일 인증을 해주세요.";
	
	public static final String MSG_FIND_ACCOUNT_SUCCESS = "] 이메일 주소로  \n회원님의 변경된 비밀번호를 전송하였습니다.";
	public static final String MSG_FIND_ACCOUNT_FAIL = "] 해당 계정의 정보를 \n 다시한번 확인 하시기 바랍니다.";
	
	
	public static final String MSG_DOORLOCK_REGISTER_CHECK_FAIL = "해당 제품번호의 도어락은 존재하지 않거나.\n 이미 등록되어있습니다.";
	public static final String MSG_DOORLOCK_REGISTER_INSERT_FAIL = "도어락 등록에 실패하였습니다.";
	
	
	public static final String MSG_KEY_REGISTER_CHECK_FAIL = "해당 유저는 이미 등록되어 있습니다.";
	public static final String MSG_KEY_REGISTER_INSERT_FAIL = "유저 등록에 실패하였습니다."; 
	
	public static final String MSG_AUTOLOCKING_ADD_INSERT_FAIL = "오토락킹 추가에 실패하였습니다."; 
	public static final String MSG_AUTOLOCKING_DELETE_FAIL = "오토락킹 삭제에 실패하였습니다.";
	
	public static final String MSG_KEY_DELETE_FAIL = "키 삭제에 실패하였습니다.";
	
	public static final String MSG_KEY_NAME_UPDATE_FAIL = "키 이름 수정에 실패하였습니다.";
	public static final String MSG_KEY_MEMBER_FAIL = "가입된 유저가 없습니다.";
	public static final String MSG_DOORLOCK_NAME_UPDATE_FAIL = "도어락 이름 수정에 실패하였습니다.";
	public static final String MSG_DOORLOCK_PLACE_UPDATE_FAIL = "도어락 위치 수정에 실패하였습니다.";
	public static final String MSG_DOORLOCK_DELETE_FAIL = "도어락 삭제에 실패하였습니다.";
	
	public static final String MSG_DOORLOCK_COUNT = "도어락의 키를 전부 삭제해주세요";
	
	public static final String RESULT_MSG1 = "RESULT_MSG1";
	public static final String RESULT_MSG2 = "RESULT_MSG2";
	public static final String RESULT_MSG3 = "RESULT_MSG3";
	public static final String RESULT_MSG4 = "RESULT_MSG4";
	public static final String RESULT_MSG5 = "RESULT_MSG5";
	public static final String RESULT_MSG6 = "RESULT_MSG6";
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static final String KEY_YES = "YES";
	public static final String KEY_NO = "NO";
	public static final String KEY_Y = "Y";
	public static final String KEY_N = "N";

	public static final String INSERT_MODE = "I";
	public static final String UPDATE_MODE = "U";
	public static final String DELETE_MODE = "D";

	public static final String RESULT_CODE = "RESULT_CODE";
	
	public static final String ADM_CANNOT_CHANGE_SELF_TYPE = "관리자는 스스로의 권한을 변경할 수 없습니다.";
	public static final String ADM_CANNOT_DELETE_SELF_ACCOUNT = "관리자는 스스로 삭제할 수 없습니다.";
	public static final String MY_INFO_FILE_PATH = "/images/my_info/";
	public static final String SYMBOLIC_LINK="";
	
	public static final String LOGIN_MEMBER = "LOGIN_MEMBER";
	
	public static final String ACTION_ID = "ACTION_ID";

	public static final String RESULT_DATA = "RESULT_DATA";
	public static final String RESULT_DATA_2 = "RESULT_DATA_2";
	public static final String RESULT_DATA_3 = "RESULT_DATA_3";
	public static final String RESULT_DATA_4 = "RESULT_DATA_4";
	public static final String RESULT_DATA_5 = "RESULT_DATA_5";
	public static final String RESULT_DATA_6 = "RESULT_DATA_6";
	public static final String RESULT_DATA_7 = "RESULT_DATA_7";
	public static final String RESULT_DATA_8 = "RESULT_DATA_8";
	public static final String RESULT_DATA_9 = "RESULT_DATA_9";

	public static final String RESULT_LIST_DATA = "RESULT_LIST_DATA";
	public static final String RESULT_LIST_DATA_2 = "RESULT_LIST_DATA_2";
	public static final String RESULT_LIST_DATA_3 = "RESULT_LIST_DATA_3";
	public static final String RESULT_LIST_DATA_4 = "RESULT_LIST_DATA_4";
	public static final String RESULT_LIST_DATA_5 = "RESULT_LIST_DATA_5";
	public static final String RESULT_LIST_DATA_6 = "RESULT_LIST_DATA_6";
	public static final String RESULT_LIST_DATA_7 = "RESULT_LIST_DATA_7";
	public static final String RESULT_LIST_DATA_8 = "RESULT_LIST_DATA_8";
	public static final String RESULT_LIST_DATA_9 = "RESULT_LIST_DATA_9";

	public static final String INFO_DATA = "INFO_DATA";
	public static final String INFO_DATA_2 = "INFO_DATA_2";
	public static final String INFO_DATA_3 = "INFO_DATA_3";
	public static final String INFO_DATA_4 = "INFO_DATA_4";
	public static final String INFO_DATA_5 = "INFO_DATA_5";
	public static final String INFO_DATA_6 = "INFO_DATA_6";
	public static final String INFO_DATA_7 = "INFO_DATA_7";
	public static final String INFO_DATA_8 = "INFO_DATA_8";
	public static final String INFO_DATA_9 = "INFO_DATA_9";
	
	
	public static final String LOG_STATUS_ACCOUNT_CREATE = "APP_ACCOUNT_CREATE";
	public static final String LOG_STATUS_ACCOUNT_FIND = "APP_ACCOUNT_FIND";
	public static final String LOG_STATUS_ACCOUNT_LOGIN = "APP_ACCOUNT_LOGIN";
	public static final String LOG_STATUS_KEY_LIST = "APP_KEY_LIST";
	public static final String LOG_STATUS_KEY_INFO = "APP_KEY_INFO";
	public static final String LOG_STATUS_KEY_UPDATE = "APP_KEY_UPDATE";
	public static final String LOG_STATUS_KEY_DELETE = "APP_KEY_DELETE";
	public static final String LOG_STATUS_DOORLOCK_LIST = "APP_DOORLOCK_LIST";
	public static final String LOG_STATUS_DOORLOCK_INFO = "APP_DOORLOCK_INFO";
	public static final String LOG_STATUS_DOORLOCK_UPDATE = "APP_DOORLOCK_UPDATE";
	public static final String LOG_STATUS_DOORLOCK_CREATE = "APP_DOORLOCK_CREATE";
	public static final String LOG_STATUS_DOORLOCK_CREATE_MASTER = "APP_DOORLOCK_CREATE_MASTER";
	public static final String LOG_STATUS_DOORLOCK_DELETE = "APP_DOORLOCK_DELETE";
	public static final String LOG_STATUS_DOORLOCK_KEY_LIST = "APP_DOORLOCK_KEY_LIST";
	public static final String LOG_STATUS_DOORLOCK_KEY_INFO = "APP_DOORLOCK_KEY_INFO";
	public static final String LOG_STATUS_DOORLOCK_KEY_CREATE = "APP_DOORLOCK_KEY_CREATE";
	public static final String LOG_STATUS_DOORLOCK_KEY_CREATE_REQUEST = "APP_DOORLOCK_KEY_CREATE_REQUEST";
	public static final String LOG_STATUS_DOORLOCK_KEY_DELETE = "APP_DOORLOCK_KEY_DELETE";
	public static final String LOG_STATUS_DOORLOCK_KEY_UPDATE = "APP_DOORLOCK_KEY_UPDATE";
	public static final String LOG_STATUS_NEWSPEED_KEY_RESPONSE_ACCEPT = "APP_NEWSPEED_KEY_RESPONSE_ACCEPT";
	public static final String LOG_STATUS_NEWSPEED_KEY_RESPONSE_REFUSE = "APP_NEWSPEED_KEY_RESPONSE_REFUSE";
	public static final String LOG_STATUS_NEWSPEED_LIST = "APP_NEWSPEED_LIST";
	public static final String LOG_STATUS_MAIN_KEY_LIST = "APP_MAIN_KEY_LIST";
	public static final String LOG_STATUS_MAIN_GET_KEY = "APP_MAIN_GET_KEY";
	public static final String LOG_STATUS_ACCOUNT_LOGIN_AUTO = "APP_ACCOUNT_LOGIN_AUTO";
	public static final String LOG_STATUS_PUSH_NOTI = "APP_PUSH_NOTI";

	public static final String LOG_STATUS_ACCOUNT_CREATE_FAIL = "APP_ACCOUNT_CREATE_FAIL";
	public static final String LOG_STATUS_ACCOUNT_FIND_FAIL = "APP_ACCOUNT_FIND_FAIL";
	public static final String LOG_STATUS_ACCOUNT_LOGIN_FAIL = "APP_ACCOUNT_LOGIN_FAIL";
	public static final String LOG_STATUS_KEY_LIST_FAIL = "APP_KEY_LIST_FAIL";
	public static final String LOG_STATUS_KEY_INFO_FAIL = "APP_KEY_INFO_FAIL";
	public static final String LOG_STATUS_KEY_UPDATE_FAIL = "APP_KEY_UPDATE_FAIL";
	public static final String LOG_STATUS_KEY_DELETE_FAIL = "APP_KEY_DELETE_FAIL";
	public static final String LOG_STATUS_DOORLOCK_LIST_FAIL = "APP_DOORLOCK_LIST_FAIL";
	public static final String LOG_STATUS_DOORLOCK_INFO_FAIL = "APP_DOORLOCK_INFO_FAIL";
	public static final String LOG_STATUS_DOORLOCK_UPDATE_FAIL = "APP_DOORLOCK_UPDATE_FAIL";
	public static final String LOG_STATUS_DOORLOCK_CREATE_FAIL = "APP_DOORLOCK_CREATE_FAIL";
	public static final String LOG_STATUS_DOORLOCK_CREATE_MASTER_FAIL = "APP_DOORLOCK_CREATE_MASTER_FAIL";
	public static final String LOG_STATUS_DOORLOCK_DELETE_FAIL = "APP_DOORLOCK_DELETE_FAIL";
	public static final String LOG_STATUS_DOORLOCK_KEY_LIST_FAIL = "APP_DOORLOCK_KEY_LIST_FAIL";
	public static final String LOG_STATUS_DOORLOCK_KEY_INFO_FAIL = "APP_DOORLOCK_KEY_INFO_FAIL";
	public static final String LOG_STATUS_DOORLOCK_KEY_CREATE_FAIL = "APP_DOORLOCK_KEY_CREATE_FAIL";
	public static final String LOG_STATUS_DOORLOCK_KEY_CREATE_REQUEST_FAIL = "APP_DOORLOCK_KEY_CREATE_REQUEST_FAIL";
	public static final String LOG_STATUS_DOORLOCK_KEY_DELETE_FAIL = "APP_DOORLOCK_KEY_DELETE_FAIL";
	public static final String LOG_STATUS_DOORLOCK_KEY_UPDATE_FAIL = "APP_DOORLOCK_KEY_UPDATE_FAIL";
	public static final String LOG_STATUS_NEWSPEED_KEY_RESPONSE_ACCEPT_FAIL = "APP_NEWSPEED_KEY_RESPONSE_ACCEPT_FAIL";
	public static final String LOG_STATUS_NEWSPEED_KEY_RESPONSE_REFUSE_FAIL = "APP_NEWSPEED_KEY_RESPONSE_REFUSE_FAIL";
	public static final String LOG_STATUS_NEWSPEED_LIST_FAIL = "APP_NEWSPEED_LIST_FAIL";
	public static final String LOG_STATUS_MAIN_KEY_LIST_FAIL = "APP_MAIN_KEY_LIST_FAIL";
	public static final String LOG_STATUS_MAIN_GET_KEY_FAIL = "APP_MAIN_GET_KEY_FAIL";
	public static final String LOG_STATUS_ACCOUNT_LOGIN_AUTO_FAIL = "APP_ACCOUNT_LOGIN_AUTO_FAIL";
	public static final String LOG_STATUS_PUSH_NOTI_FAIL = "APP_PUSH_NOTI_FAIL";
	
	public static final String LOG_STATUS_SUB_KEY_EXPIRE = "SUB_KEY_EXPIRE";
	public static final String LOG_STATUS_SUB_KEY_ACTIVE = "SUB_KEY_ACTIVE";
	public static final String LOG_STATUS_SUB_KEY_INACTIVE = "SUB_KEY_INACTIVE";
	public static final String LOG_STATUS_HW_KEY_OPEN = "HW_KEY_OPEN";

	
	
}
