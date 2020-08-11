package com.ye.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ye.dao.CommentMapper;
import com.ye.domain.CommentBean;
import com.ye.domain.ReplyBean;
import com.ye.service.CommentService;
import com.ye.util.TokenUtil;

@Service @Transactional
public class CommentServiceImpl implements CommentService{
	@Autowired
	public CommentMapper commentMapper;
	
	//添加评论
	@Override
	public int addComment(CommentBean commentbean,HttpServletRequest request) throws Exception{
		int result;
		//获取session中用户信息
		HttpSession session = request.getSession();
		String user = session.getAttribute("user").toString();
		//解锁token
		TokenUtil token = new TokenUtil();
		String unsign = token.Vertify(user);
		//将token转换成json格式
		JsonObject json = new JsonParser().parse(unsign).getAsJsonObject();
		//json以string格式输出
		String name = json.get("name").getAsString();
		int id = json.get("id").getAsInt();
		String url = json.get("headpicurl").getAsString();
		int roleid = json.get("roleid").getAsInt();
		commentbean.setComment_name(name);
		commentbean.setComment_name_id(id);
		commentbean.setComment_name_url(url);
		commentbean.setRoleid(roleid);
		result = commentMapper.addComment(commentbean);
		return result;
	}
	
	//添加回复
		@Override
		public int addReply(ReplyBean replybean,HttpServletRequest request) throws Exception{
			int result;
			//获取session中用户信息
			HttpSession session = request.getSession();
			String user = session.getAttribute("user").toString();
			//解锁token
			TokenUtil token = new TokenUtil();
			String unsign = token.Vertify(user);
			//将token转换成json格式
			JsonObject json = new JsonParser().parse(unsign).getAsJsonObject();
			//json以string格式输出
			String name = json.get("name").getAsString();
			int id = json.get("id").getAsInt();
			String url = json.get("headpicurl").getAsString();
			int roleid = json.get("roleid").getAsInt();
			replybean.setReply_name(name);
			replybean.setReply_name_id(id);
			replybean.setReply_name_url(url);
			replybean.setRoleid(roleid);
			result = commentMapper.addReply(replybean);
			return result;
		}
	//根据评论id删除评论
	@Override
	public int deleteComment(int comment_id) throws Exception{
		int result;
		result = commentMapper.deleteComment(comment_id);
		return result;
	}
	
	//更改评论表中用户姓名
	@Override
	public void updateTwoboard_two_name(String comment_name, int comment_name_id) throws Exception {
		commentMapper.updateCommentname(comment_name, comment_name_id);
	}
	
	//根据所评论的目录id查询指定评论和回复
	@Override
	public String getComment(int comment_three_id,int start,int end) throws Exception{
		List<CommentBean> comment = commentMapper.getComment(comment_three_id,start,end);
		//存放commentid
		List<Integer> commentIdList = new ArrayList<Integer>();
		for(int i=0;i<comment.size();i++){
			commentIdList.add(comment.get(i).getComment_id());
		}
		List<ReplyBean> reply = new ArrayList<ReplyBean>();
		if(!commentIdList.isEmpty()){
			reply = commentMapper.getReply(comment_three_id,commentIdList);
		}
		//用于转换json最终返回前台的数据
		Map<String,String> map1;
		//用与存储评论和回复1对多关系
		Map<Integer,String> map2;
		List<String> list2;
		//用于存储所有的评论回复组
		List<String> list1 =  new ArrayList<String>();
		int j = 0;
		int commentId;
		//获取评论id
		for(int i=0;i<comment.size();i++){
			//存储评论和回复
			map1 = new HashMap<String,String>();
			//存储回复list，用于转换成json
			map2 = new HashMap<Integer,String>();
			//存储回复
			list2 = new ArrayList<String>();
			commentId = comment.get(i).getComment_id();
			while(reply.size()>0&&reply.get(j).getReply_comment_id()==commentId){
				map2.put(j, JSONObject.toJSONString(reply.get(j)));
				list2.add(JSONObject.toJSONString(reply.get(j)));
				reply.remove(j);
			}
			try{
			map1.put("\"reply\"", list2.toString());
			map1.put("\"comment_id\"", "\""+comment.get(i).getComment_id()+"\"");
			map1.put("\"comment_name_id\"", "\""+comment.get(i).getComment_name_id()+"\"");
			map1.put("\"comment_name\"", "\""+comment.get(i).getComment_name()+"\"");
			map1.put("\"comment_name_url\"", "\""+comment.get(i).getComment_name_url()+"\"");
			map1.put("\"comment_content\"", "\""+comment.get(i).getComment_content()+"\"");
			map1.put("\"comment_createtime\"", "\""+comment.get(i).getComment_createtime()+"\"");
			list1.add(map1.toString());
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		String result = StringEscapeUtils.unescapeJava(list1.toString());
		result = "{\"comment\":"+result+"}";
		return result.replaceAll("=", ":");
	}

	@Override
	public int getTotal(int comment_three_id) throws Exception {
		int result;
		result = commentMapper.getTotal(comment_three_id);
		if(result%10==0){
			result = result/10;
		}else{
			result = result/10+1;
		}
		return result;
	}
}