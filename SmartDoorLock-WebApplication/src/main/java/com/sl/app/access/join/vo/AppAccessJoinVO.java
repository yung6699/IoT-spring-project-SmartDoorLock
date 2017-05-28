package com.sl.app.access.join.vo;

import org.apache.ibatis.type.Alias;

@Alias("AppAccessJoinVo")
public class AppAccessJoinVO {
	private int member_id;
	private String email;
	private String password;
	private String name;
	private String phone_num;
	private String state;
	private String crt_dt;
	private String crt_email;
	private String updt_dt;
	private String updt_email;
	public int getMember_id() {
		return member_id;
	}
	public void setMember_id(int member_id) {
		this.member_id = member_id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone_num() {
		return phone_num;
	}
	public void setPhone_num(String phone_num) {
		this.phone_num = phone_num;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCrt_dt() {
		return crt_dt;
	}
	public void setCrt_dt(String crt_dt) {
		this.crt_dt = crt_dt;
	}
	public String getCrt_email() {
		return crt_email;
	}
	public void setCrt_email(String crt_email) {
		this.crt_email = crt_email;
	}
	public String getUpdt_dt() {
		return updt_dt;
	}
	public void setUpdt_dt(String updt_dt) {
		this.updt_dt = updt_dt;
	}
	public String getUpdt_email() {
		return updt_email;
	}
	public void setUpdt_email(String updt_email) {
		this.updt_email = updt_email;
	}
	
}
