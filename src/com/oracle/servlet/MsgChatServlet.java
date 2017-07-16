package com.oracle.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.asiainfo.push.MqttBroker;
import com.oracle.entity.MessageEntity;
import com.oracle.entity.User;
import com.oracle.service.MsgChatService;
import com.oracle.service.UserService;

import net.sf.json.JSONObject;

public class MsgChatServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse resp)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		//接收到的参数  请求用户  目标用户 内容
		int user_id = Integer.parseInt(request.getParameter("user_id"));
		int target_user_id = Integer.parseInt(request.getParameter("target_user_id"));
		String message_content = request.getParameter("message_content");
		//日期
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date(System.currentTimeMillis());
		String message_date = simpleDateFormat.format(date);
		
		System.out.println("user_id: "+user_id+" target_user_id: "+target_user_id+" message_content: "+message_content);
		MsgChatService msgChatService = new MsgChatService();
		MessageEntity message = new MessageEntity();
		message.setUser_id(user_id);
		message.setTarget_user_id(target_user_id);
		message.setMessage_content(message_content);
		message.setMessage_date(message_date);
		String voice_time = "";
		int row = msgChatService.addMessage(message);
		if (row>0) {
			System.out.println("发送消息存入数据库成功，正在请求用户信息");
			UserService userService = new UserService();
			User user = userService.getUserInfo(user_id);
			if (user!=null) {
				System.out.println("请求用户信息成功，正在及时发送给目标端");
				JSONObject json = new JSONObject();
				json.put("message_type", "message");
				json.put("message_content", message_content);
				json.put("user_id", user_id+"");
				json.put("target_user_id", target_user_id);
				json.put("user_name", user.getUser_name());
				json.put("user_img", user.getUser_img());
				json.put("message_date", message_date);
				json.put("voice_time", voice_time);
				MqttBroker.getInstance().sendMessage(target_user_id+"", json.toString());
			}
			User target_user = userService.getUserInfo(target_user_id);
				if (user!=null)	{
				System.out.println("请求用户信息成功，正在及时发送给发送端");
				JSONObject json = new JSONObject();
				json.put("message_type", "message");
				json.put("user_id", user_id+"");
				json.put("target_user_id", target_user_id);
				json.put("message_content", message_content);
				json.put("message_date", message_date);
				json.put("user_name", target_user.getUser_name());
				json.put("user_img", target_user.getUser_img());
				json.put("voice_time", voice_time);
				MqttBroker.getInstance().sendMessage(user_id+"", json.toString());
			}		
		}
		
	}
}
