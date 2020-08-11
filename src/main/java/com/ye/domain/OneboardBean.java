package com.ye.domain;

public class OneboardBean {
	private int one_id;//专业id
	private String  one_title;//专业名
	private int one_admin_id;
	@Override
	public String toString() {
		return "OneboardBean [one_id=" + one_id + ", one_title=" + one_title + ", one_admin_id=" + one_admin_id + "]";
	}
	public int getOne_id() {
		return one_id;
	}
	public void setOne_id(int one_id) {
		this.one_id = one_id;
	}
	public String getOne_title() {
		return one_title;
	}
	public void setOne_title(String one_title) {
		this.one_title = one_title;
	}
	public int getOne_admin_id() {
		return one_admin_id;
	}
	public void setOne_admin_id(int one_admin_id) {
		this.one_admin_id = one_admin_id;
	}
	

}
