package com.ye.domain;

public class ThreeboardBean {

	private int three_id;//章节id
	private String three_title;//章节名
	private int three_two_id;//所属课程id
	private String three_profile;//章节介绍
	private int three_statistics;//章节统计
	private String three_creat_time;//章节创建时间
	public int getThree_id() {
		return three_id;
	}
	public void setThree_id(int three_id) {
		this.three_id = three_id;
	}
	public String getThree_title() {
		return three_title;
	}
	public void setThree_title(String three_title) {
		this.three_title = three_title;
	}
	public int getThree_two_id() {
		return three_two_id;
	}
	public void setThree_two_id(int three_two_id) {
		this.three_two_id = three_two_id;
	}
	public String getThree_profile() {
		return three_profile;
	}
	public void setThree_profile(String three_profile) {
		this.three_profile = three_profile;
	}
	public int getThree_statistics() {
		return three_statistics;
	}
	public void setThree_statistics(int three_statistics) {
		this.three_statistics = three_statistics;
	}
	public String getThree_creat_time() {
		return three_creat_time;
	}
	public void setThree_creat_time(String three_creat_time) {
		this.three_creat_time = three_creat_time;
	}
	@Override
	public String toString() {
		return "ThreeboardBean [three_id=" + three_id + ", three_title=" + three_title + ", three_two_id="
				+ three_two_id + ", three_profile=" + three_profile + ", three_statistics=" + three_statistics
				+ ", three_creat_time=" + three_creat_time + "]";
	}

}
