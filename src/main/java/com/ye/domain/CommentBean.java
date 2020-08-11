package com.ye.domain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommentBean {
	//时间转换格式
	DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	private int comment_id;//评论id
	private String comment_name;//评论者姓名
	private int comment_name_id;//评论者id
	private int comment_two_id;//评论的课程
	private int comment_three_id;//评论的课程名
	private String comment_content;//评论内容
	private Date comment_createtime;//创建时间
	private String comment_name_url;//评论者头像url
	private int roleid;//评论者角色
	public int getComment_id() {
		return comment_id;
	}
	public void setComment_id(int comment_id) {
		this.comment_id = comment_id;
	}
	public String getComment_name() {
		return comment_name;
	}
	public void setComment_name(String comment_name) {
		this.comment_name = comment_name;
	}
	public int getComment_name_id() {
		return comment_name_id;
	}
	public void setComment_name_id(int comment_name_id) {
		this.comment_name_id = comment_name_id;
	}
	public int getComment_two_id() {
		return comment_two_id;
	}
	public void setComment_two_id(int comment_two_id) {
		this.comment_two_id = comment_two_id;
	}
	public int getComment_three_id() {
		return comment_three_id;
	}
	public void setComment_three_id(int comment_three_id) {
		this.comment_three_id = comment_three_id;
	}
	public String getComment_content() {
		return comment_content;
	}
	public void setComment_content(String comment_content) {
		this.comment_content = comment_content;
	}
	public String getComment_createtime() {
		String createtime = format1.format(comment_createtime);
		return createtime;
	}
	public void setComment_createtime(Date comment_createtime) {
		this.comment_createtime = comment_createtime;
	}
	
	public String getComment_name_url() {
		return comment_name_url;
	}
	public void setComment_name_url(String comment_name_url) {
		this.comment_name_url = comment_name_url;
	}
	public int getRoleid() {
		return roleid;
	}
	public void setRoleid(int roleid) {
		this.roleid = roleid;
	}
	public String toJSONString() {
		return "comment_id=" + comment_id + ", comment_name=" + comment_name
				+ ", comment_name_id=" + comment_name_id + ", comment_two_id=" + comment_two_id + ", comment_three_id="
				+ comment_three_id + ", comment_content=" + comment_content + ", comment_createtime="
				+ comment_createtime + ", comment_name_url=" + comment_name_url + ", roleid=" + roleid;
	}
	
}
