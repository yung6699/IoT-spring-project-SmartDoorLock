package com.sl.sub.expirer.log;

public class SystemLogSetterVO {
	private String serial_no="";
	private String send_email="";
	private String recv_email="";
	private String state="";
	private String message="";
	private int new_flag=0;
	private int log_no=0;
	

	public void setSerial_no(String serial_no) {
		serial_no = serial_no ==null ? "" : serial_no;
		this.serial_no = serial_no;
	}
	public void setSend_email(String send_email) {
		send_email = send_email == null ? "" : send_email;
		this.send_email = send_email;
	}
	public void setRecv_email(String recv_email) {
		recv_email = recv_email == null ? "" : recv_email;
		this.recv_email = recv_email;
	}
	public void setMessage(String message) {
		message = message == null ? "" : message;
		this.message = message;
	}
	public void setState(String state) {
		state = state == null ? "":state;
		this.state = state;
	}
	
	
	
	
	
	
	
	
	
	
	
	public int getLog_no() {
		return log_no;
	}
	public void setLog_no(int log_no) {
		this.log_no = log_no;
	}
	public int getNew_flag() {
		return new_flag;
	}
	public void setNew_flag(int new_flag) {
		this.new_flag = new_flag;
	}
	
	public String getSerial_no() {
		return serial_no;
	}

	public String getSend_email() {
		return send_email;
	}

	
	public String getRecv_email() {
		return recv_email;
	}

	public String getState() {
		return state;
	}

	public String getMessage() {
		return message;
	}

}
