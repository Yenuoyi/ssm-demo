package com.ye.domain;

import java.io.Serializable;

@SuppressWarnings("serial")
public class TwoboardBean implements Serializable{

	//时间转换格式
	//DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	private int two_id;//课程id
	private String two_title;//课程名
	private int two_teacher_id;//创建该课程的老师
	private String two_teacher_name;//创建该课程的老师
	private String two_teacher_headpicurl;//创建该课程的老师头像地址
	private int two_one_id;//所属专业ID
	private String two_profile;//课程简介
	private int two_statistics;//该课程用户回复统计
	private String two_create_time;//该课程创建时间
	private String two_update_time;//该课程更新时间
	
	
	
	public int getTwo_id() {
		return two_id;
	}
	public void setTwo_id(int two_id) {
		this.two_id = two_id;
	}
	public String getTwo_title() {
		return two_title;
	}
	public void setTwo_title(String two_title) {
		this.two_title = two_title;
	}
	public int getTwo_teacher_id() {
		return two_teacher_id;
	}
	public void setTwo_teacher_id(int two_teacher_id) {
		this.two_teacher_id = two_teacher_id;
	}
	public String getTwo_teacher_name() {
		return two_teacher_name;
	}
	public void setTwo_teacher_name(String two_teacher_name) {
		this.two_teacher_name = two_teacher_name;
	}
	public String getTwo_teacher_headpicurl() {
		return two_teacher_headpicurl;
	}
	public void setTwo_teacher_headpicurl(String two_teacher_headpicurl) {
		this.two_teacher_headpicurl = two_teacher_headpicurl;
	}
	public int getTwo_one_id() {
		return two_one_id;
	}
	public void setTwo_one_id(int two_one_id) {
		this.two_one_id = two_one_id;
	}
	public String getTwo_profile() {
		return two_profile;
	}
	public void setTwo_profile(String two_profile) {
		this.two_profile = two_profile;
	}
	public int getTwo_statistics() {
		return two_statistics;
	}
	public void setTwo_statistics(int two_statistics) {
		this.two_statistics = two_statistics;
	}
	public String getTwo_create_time() {
		return two_create_time;
	}
	public void setTwo_create_time(String two_create_time) {
		this.two_create_time = two_create_time;
	}
	public String getTwo_update_time() {
		return two_update_time;
	}
	public void setTwo_update_time(String two_update_time) {
		this.two_update_time = two_update_time;
	}
	
	@Override
	public String toString() {
		return "TwoboardBean [two_id=" + two_id + ", two_title=" + two_title + ", two_teacher_id=" + two_teacher_id
				+ ", two_teacher_name=" + two_teacher_name + ", two_teacher_headpicurl=" + two_teacher_headpicurl
				+ ", two_one_id=" + two_one_id + ", two_profile=" + two_profile + ", two_statistics=" + two_statistics
				+ ", two_create_time=" + two_create_time + ", two_update_time=" + two_update_time + "]";
	}
	public String toSendString() {
		return  two_id + "  |  " + two_title + "  |  " + two_teacher_id
				+ "  |  " + two_teacher_name + "  |  " + two_one_id + "  |  "
				+ two_profile + "  |  " + two_statistics + "  |  " + two_create_time
				+ "  |  " + two_update_time ;
	}
	public String toJsonString() {
		return "two_id:" + two_id + ", two_title:" + two_title + ", two_teacher_id:" + two_teacher_id
				+ ", two_teacher_name:" + two_teacher_name + ", two_one_id:" + two_one_id + ", two_profile:"
				+ two_profile + ", two_statistics:" + two_statistics + ", two_create_time:" + two_create_time
				+ ", two_update_time:" + two_update_time + "";
	}
}
