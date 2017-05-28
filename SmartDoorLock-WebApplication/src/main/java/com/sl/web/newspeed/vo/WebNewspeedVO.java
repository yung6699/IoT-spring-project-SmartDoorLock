package com.sl.web.newspeed.vo;

public class WebNewspeedVO {
	
	//AJAX를 통해 SERVER가 받는 값.
	private String 	email;
	
	private String answer="";
	
	// LOG 테이블의 속성들 , APP으로 RETURN 하는 값들.
	private String 	recv_name;
	private String	send_name;
	private String 	idx;
	private String 	message;
	
	private String 	send_email;
	private String 	recv_email;
	private String 	state;
	private String 	log_date;
	private String 	serial_no;
	private int 	read_flag;
		//	private String message;			//사용하지 않음.	
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRecv_name() {
		return recv_name;
	}
	public void setRecv_name(String recv_name) {
		this.recv_name = recv_name;
	}
	public String getSend_name() {
		return send_name;
	}
	public void setSend_name(String send_name) {
		this.send_name = send_name;
	}
	public String getSend_email() {
		return send_email;
	}
	public void setSend_email(String send_email) {
		this.send_email = send_email;
	}
	public String getRecv_email() {
		return recv_email;
	}
	public void setRecv_email(String recv_email) {
		this.recv_email = recv_email;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getLog_date() {
		return log_date;
	}
	public void setLog_date(String log_date) {
		this.log_date = log_date;
	}
	public String getSerial_no() {
		return serial_no;
	}
	public void setSerial_no(String serial_no) {
		this.serial_no = serial_no;
	}
	public int getRead_flag() {
		return read_flag;
	}
	public void setRead_flag(int read_flag) {
		this.read_flag = read_flag;
	}
	public String getIdx() {
		return idx;
	}
	public void setIdx(String idx) {
		this.idx = idx;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
