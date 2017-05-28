package com.sl.web.category.vo;

public class WebCategoryVO {
	private int cat_id;
	private int member_id;
	private String key_id;
	private int sort;
	private String crt_dt;
	private String crt_email;
	private String updt_dt;
	private String updt_email;
	public int getCat_id() {
		return cat_id;
	}
	public void setCat_id(int cat_id) {
		this.cat_id = cat_id;
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
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
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
