package com.ye.domain;

public class ReplyBean {
	private int reply_id;
	private String reply_name;
	private int reply_name_id;
	private String reply_name_url;
	private int reply_two_id;
	private int reply_three_id;
	private String reply_content;
	private String reply_createtime;
	private int reply_comment_id;
	private String reply_comment_name;
	private String reply_comment_name_id;
	private int roleid;
	public int getReply_id() {
		return reply_id;
	}
	public void setReply_id(int reply_id) {
		this.reply_id = reply_id;
	}
	public String getReply_name() {
		return reply_name;
	}
	public void setReply_name(String reply_name) {
		this.reply_name = reply_name;
	}
	public int getReply_name_id() {
		return reply_name_id;
	}
	public void setReply_name_id(int reply_name_id) {
		this.reply_name_id = reply_name_id;
	}
	public String getReply_name_url() {
		return reply_name_url;
	}
	public void setReply_name_url(String reply_name_url) {
		this.reply_name_url = reply_name_url;
	}
	public int getReply_two_id() {
		return reply_two_id;
	}
	public void setReply_two_id(int reply_two_id) {
		this.reply_two_id = reply_two_id;
	}
	public int getReply_three_id() {
		return reply_three_id;
	}
	public void setReply_three_id(int reply_three_id) {
		this.reply_three_id = reply_three_id;
	}
	public String getReply_content() {
		return reply_content;
	}
	public void setReply_content(String reply_content) {
		this.reply_content = reply_content;
	}
	public String getReply_createtime() {
		return reply_createtime;
	}
	public void setReply_createtime(String reply_createtime) {
		this.reply_createtime = reply_createtime;
	}
	public int getReply_comment_id() {
		return reply_comment_id;
	}
	public void setReply_comment_id(int reply_comment_id) {
		this.reply_comment_id = reply_comment_id;
	}
	public String getReply_comment_name() {
		return reply_comment_name;
	}
	public void setReply_comment_name(String reply_comment_name) {
		this.reply_comment_name = reply_comment_name;
	}
	public String getReply_comment_name_id() {
		return reply_comment_name_id;
	}
	public void setReply_comment_name_id(String reply_comment_name_id) {
		this.reply_comment_name_id = reply_comment_name_id;
	}
	
	public int getRoleid() {
		return roleid;
	}
	public void setRoleid(int roleid) {
		this.roleid = roleid;
	}
	@Override
	public String toString() {
		return "ReplyBean [reply_id=" + reply_id + ", reply_name=" + reply_name + ", reply_name_id=" + reply_name_id
				+ ", reply_name_url=" + reply_name_url + ", reply_two_id=" + reply_two_id + ", reply_three_id="
				+ reply_three_id + ", reply_content=" + reply_content + ", reply_createtime=" + reply_createtime
				+ ", reply_comment_id=" + reply_comment_id + ", reply_comment_name=" + reply_comment_name
				+ ", reply_comment_name_id=" + reply_comment_name_id + ", roleid=" + roleid + "]";
	}
	
}
