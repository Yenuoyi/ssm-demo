package com.ye.emailstatistics;

import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.util.MailSSLSocketFactory;
import com.ye.domain.CommentBean;
import com.ye.domain.ThreeboardBean;
import com.ye.domain.TwoboardBean;
import com.ye.domain.UserBean;

public class SendEmail {
    public int send(String to,int commentCount,List<TwoboardBean> two,List<ThreeboardBean> three,
			List<CommentBean> comment,List<UserBean> user) throws GeneralSecurityException 
    {
    	int result = 0;
        String from = "19689405@qq.com";

        String host = "smtp.qq.com";

        Properties properties = System.getProperties();

        properties.setProperty("mail.smtp.host", host);

        properties.put("mail.smtp.auth", "true");
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.ssl.socketFactory", sf);
        Session session = Session.getDefaultInstance(properties,new Authenticator(){
            public PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication("19689405@qq.com", "egsoypawzxexbgdd"); 
            }
        });

        try{
        	StringBuffer html = new StringBuffer();
        	html.append("<table border='1' width='80%' align='center'><tr><td>一周评论数:"
        	+commentCount+"</td></tr></table><br>");

        	html.append("<table border='1' width='80%' align='center'><tr><td colspan='9'>一周内热门课程</td></tr>");
        	html.append("<tr><td width='5%'>课程id</td><td width='10%'>课程标题</td><td width='5%'>课程教师id</td><td width='10%'>课程教师名</td>"
        			+ "<td width='5%'>专业id</td><td width='45%'>课程简介</td><td width='5%'>课程评论统计</td><td width='15%'>课程创建时间</td>"
        			+ "<td>课程更新时间</td></tr>");
    		for(TwoboardBean twoboardBean : two){
    			html.append("<tr><td>"+twoboardBean.getTwo_id()+"</td>"
    			+"<td>"+twoboardBean.getTwo_title()+"</td>"+"<td>"+twoboardBean.getTwo_teacher_id()+"</td>"
    			+"<td>"+twoboardBean.getTwo_teacher_name()+"</td>"+"<td>"+twoboardBean.getTwo_one_id()+"</td>"
    			+"<td>"+twoboardBean.getTwo_profile()+"</td>"+"<td>"+twoboardBean.getTwo_statistics()+"</td>"
    			+"<td>"+twoboardBean.getTwo_create_time()+"</td>"+"<td>"+twoboardBean.getTwo_update_time()+"</td></tr>");
    		}
    		html.append("</table><br>");
    		//添加一周热门专题
    		html.append("<table border='1' width='80%' align='center'><tr><td colspan='6'>一周热门专题</td></tr>"
    				+"<tr><td width='5%'>id</td><td width='15%'>标题</td><td width='5%'>所属课程id</td><td width='55%'>章节简介</td>"
    				+ "<td width='5%'>章节统计</td><td width='15%'>创建时间</td></tr>");
			for(ThreeboardBean threeboardBean : three){
				html.append("<tr><td>"+threeboardBean.getThree_id()+"</td><td>"+threeboardBean.getThree_title()+"</td>"
						+"<td>"+threeboardBean.getThree_two_id()+"</td><td>"+threeboardBean.getThree_profile()+"</td>"
						+"<td>"+threeboardBean.getThree_statistics()+"</td><td>"+threeboardBean.getThree_creat_time()+"</td></tr>");
			}
			html.append("</table><br>");
			//判断评论最多的教师列表是否为空，为空，停止执行下面获取教师信息代码
			if(!comment.isEmpty()){
				html.append("<table border='1' width='80%' align='center'><tr><td colspan='7'>一周解答最多最多教师</td></tr>"
						+ "<tr><td width='5%'>解答数</td><td width='5%'>id</td><td width='15%'>教师名</td><td width='15%'>专业</td><td width='15%'>iphone</td><td width='15%'>email</td>"
						+ "<td width='15%'>创建时间</td>");
				for(int j=0;j<user.size();j++){
					html.append("<tr><td>"+comment.get(j).getComment_content()+"</td>"
					+"<td>"+user.get(j).getId()+"</td><td>"+user.get(j).getName()+"</td>"
					+"<td>"+user.get(j).getProfessional()+"</td><td>"+user.get(j).getIphone()+"</td>"
					+"<td>"+user.get(j).getEmail()+"</td><td>"+user.get(j).getCreate_time()+"</td></tr>");
				}
				html.append("</table><br>");
			}
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(from));

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            message.setSubject("This is an email from Mr. Ye!");

            message.setContent(html.toString(),"text/html;charset=utf-8");
            
            Transport.send(message);
            result=1;
        }catch (MessagingException mex) {
            mex.printStackTrace();
        }
		return result;
    }
}
