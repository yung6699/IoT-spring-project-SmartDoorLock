package com.sl.app.main.vo;

public class AppMainVO {
	
	private int member_id;
	private String member_email;
	private int notice_count;
	private String serial_no;
	private String key_name;
	private String key_id;
	private String grade;
	private String grade_name;
	private int sort;
	private String state;
	private String state_name;
	private int nowTime;
	
	private String start_date;
	private String expire_date;
	private String bluetooth_id;
	private String doorlock_name;
	private String installed_place;
	
	public String getDoorlock_name() {
		return doorlock_name;
	}
	public void setDoorlock_name(String doorlock_name) {
		this.doorlock_name = doorlock_name;
	}
	public String getInstalled_place() {
		return installed_place;
	}
	public void setInstalled_place(String installed_place) {
		this.installed_place = installed_place;
	}
	public int getNotice_count() {
		return notice_count;
	}
	public void setNotice_count(int notice_count) {
		this.notice_count = notice_count;
	}
	public String getMember_email() {
		return member_email;
	}
	public void setMember_email(String member_email) {
		this.member_email = member_email;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getBluetooth_id() {
		return bluetooth_id;
	}
	public void setBluetooth_id(String bluetooth_id) {
		this.bluetooth_id = bluetooth_id;
	}
	public String getExpire_date() {
		return expire_date;
	}
	public void setExpire_date(String expire_date) {
		this.expire_date = expire_date;
	}
	


	public int getMember_id() {
		return member_id;
	}
	public void setMember_id(int member_id) {
		this.member_id = member_id;
	}
	public String getSerial_no() {
		return serial_no;
	}
	public void setSerial_no(String serial_no) {
		this.serial_no = serial_no;
	}
	public String getKey_name() {
		return key_name;
	}
	public void setKey_name(String key_name) {
		this.key_name = key_name;
	}
	public String getKey_id() {
		return key_id;
	}
	public void setKey_id(String key_id) {
		this.key_id = key_id;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getGrade_name() {
		return grade_name;
	}
	public void setGrade_name(String grade_name) {
		this.grade_name = grade_name;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getState_name() {
		return state_name;
	}
	public void setState_name(String state_name) {
		this.state_name = state_name;
	}
	public int getNowTime() {
		return nowTime;
	}
	public void setNowTime(int nowTime) {
		this.nowTime = nowTime;
	}


}
