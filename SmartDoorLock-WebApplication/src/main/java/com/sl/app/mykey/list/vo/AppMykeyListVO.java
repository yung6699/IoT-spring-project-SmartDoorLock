package com.sl.app.mykey.list.vo;

public class AppMykeyListVO {

	private int member_id; // 열쇠 찾을때 사용

	// 사용자가 정의한 이름
	private String key_id; // NFC로 넘길때
	private String key_name; // 열쇠 이름
	private String grade;
	private String grade_name;
	private String serial_no; // 도어락 코드
	private String crt_dt; // 부여날짜.
	private String crt_email; // 키를 부여한 이메일
	private String start_date; // 유효기간
	private String expire_date; // 유효기간.
	private String email;
	private String bluetooth_id;
	

	public String getBluetooth_id() {
		return bluetooth_id;
	}

	public void setBluetooth_id(String bluetooth_id) {
		this.bluetooth_id = bluetooth_id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCrt_email() {
		return crt_email;
	}

	public void setCrt_email(String crt_email) {
		this.crt_email = crt_email;
	}

	public int getMember_id() {
		return member_id;
	}

	public void setMember_id(int member_id) {
		this.member_id = member_id;
	}

	public String getKey_id() {
		return key_id;
	}

	public void setKey_id(String key_id) {
		this.key_id = key_id;
	}

	public String getKey_name() {
		return key_name;
	}

	public void setKey_name(String key_name) {
		this.key_name = key_name;
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

	public String getSerial_no() {
		return serial_no;
	}

	public void setSerial_no(String serial_no) {
		this.serial_no = serial_no;
	}

	public String getCrt_dt() {
		return crt_dt;
	}

	public void setCrt_dt(String crt_dt) {
		this.crt_dt = crt_dt;
	}

	public String getStart_date() {
		return start_date;
	}

	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}

	public String getExpire_date() {
		return expire_date;
	}

	public void setExpire_date(String expire_date) {
		this.expire_date = expire_date;
	}

}
