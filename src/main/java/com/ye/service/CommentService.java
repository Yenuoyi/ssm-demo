package com.ye.service;

import javax.servlet.http.HttpServletRequest;

import com.ye.domain.CommentBean;
import com.ye.domain.ReplyBean;

public interface CommentService {
	//创建评论
	int addComment(CommentBean commentbean,HttpServletRequest request) throws Exception;
	//创建回复
	int addReply(ReplyBean replybean,HttpServletRequest request) throws Exception;
	//通过用户id删除评论
	int deleteComment(int comment_id) throws Exception;
	//更新评论表用户姓名
	void updateTwoboard_two_name(String comment_name,int comment_name_id) throws Exception;
	//根据所评论的目录id查询指定评论和回复
	String getComment(int comment_three_id,int start,int end) throws Exception;
	//通过章节id查询评论总数
	int getTotal(int comment_three_id) throws Exception;
}