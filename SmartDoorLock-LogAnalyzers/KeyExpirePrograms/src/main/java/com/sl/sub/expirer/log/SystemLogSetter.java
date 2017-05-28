package com.sl.sub.expirer.log;


public interface SystemLogSetter {

	public SystemLogSetterVO builder(String state, String send_email, String recv_email, String serial_no, String message);
	public SystemLogSetterVO builder(String state, SystemLogSetterVO vo);

	public void setSystemLog(SystemLogSetterVO vo);
	public void setDoorlockLog(SystemLogSetterVO vo);
	public void setNewspeed(SystemLogSetterVO vo,String oneMember);
	
	//진영균 영역
	public void updateNewFlag(String email);
	public void deleteGrantRequest(SystemLogSetterVO vo);

}
