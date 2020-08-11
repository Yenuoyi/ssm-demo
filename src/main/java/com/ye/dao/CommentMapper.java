package com.ye.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.ye.domain.CommentBean;
import com.ye.domain.ReplyBean;

@Repository
public interface CommentMapper {

	//创建评论
	int addComment(CommentBean commentbean);
	//创建回复
	int addReply(ReplyBean replybean);
	//通过id删除评论
	int deleteComment(int comment_id);
	
	//更新评论表中用户姓名
	void updateCommentname(@Param("comment_name") String comment_name,@Param("comment_name_id") int comment_name_id);
	
	//<!-- 根据所评论的目录id查询指定评论 -->
	List<CommentBean> getComment(@Param("comment_three_id") int comment_three_id, @Param("start") int start, @Param("end") int end);
	List<ReplyBean> getReply(@Param("reply_three_id") int reply_three_id, @Param("commentIdList") List<Integer> commentIdList);
	//查询指定目录共有多少条评论
	int getTotal(@Param("comment_three_id") int comment_three_id);
	
}