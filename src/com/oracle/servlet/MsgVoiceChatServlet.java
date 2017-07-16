package com.oracle.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.asiainfo.push.MqttBroker;
import com.jspsmart.upload.File;
import com.jspsmart.upload.SmartUpload;
import com.oracle.common.CallBack;
import com.oracle.entity.MessageEntity;
import com.oracle.entity.User;
import com.oracle.service.MsgChatService;
import com.oracle.service.UserService;

public class MsgVoiceChatServlet extends BaseServlet {
	@Override
	protected void doPost(final HttpServletRequest request,final
			HttpServletResponse response) throws ServletException, IOException {
		requestProcess(new CallBack() {
			
			@Override
			public String callBack(Map<String, Object> map) {
				SmartUpload smartUpload = new SmartUpload();
				try {
					smartUpload.initialize(getServletConfig(), request, response);
					smartUpload.upload();
					File file = smartUpload.getFiles().getFile(0);
					if (file.getSize()>0) {
						System.out.println("------后台接收到语音文件------");
					}
					//以年月日为文件夹名
					//得到当前时间
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
					Date date =new  Date(System.currentTimeMillis());
					String currentDate = simpleDateFormat.format(date);
					//判断有没有文件
					java.io.File folder = new java.io.File(request.getServletContext().getRealPath("upload")+"/"+currentDate);
					if (!folder.exists()) {
						folder.mkdirs();
					}
					//以UUID生成的随机ID为文件名
					String fileName = UUID.randomUUID().toString();
					//将生成UUID字符串中的-替换成a变成一整长串
					String newFileName = fileName.replace("-", "a");
					//upload+文件夹名+UUID文件名+扩展名 folder.getAbsolutePath()+"/"+
					String s_message_content = folder.getAbsolutePath()+"/"+newFileName+"."+file.getFileExt();
					//存入数据库的名字为 文件夹名+uuid+扩展名
					String message_content = "upload/"+currentDate+"/"+newFileName+"."+file.getFileExt();
					//保存文件
					file.saveAs(s_message_content);
					//取到 
					//用户id  
					//目标用户id  
					//语音的时间
					String user_id = smartUpload.getRequest().getParameter("user_id");
					String target_user_id = smartUpload.getRequest().getParameter("target_user_id");
					SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					Date dataDate =new  Date(System.currentTimeMillis());
					String message_date = dataFormat.format(dataDate);
					String voice_time = smartUpload.getRequest().getParameter("voice_time");
					
					System.out.println("语音消息：user_id: "+user_id+" target_user_id: "+target_user_id+" message_content: "+message_content +"  语音长度为："+voice_time);
					MsgChatService msgChatService = new MsgChatService();
					
					MessageEntity message = new MessageEntity();
					message.setUser_id(Integer.parseInt(user_id));
					message.setTarget_user_id(Integer.parseInt(target_user_id));
					message.setMessage_content(message_content);
					message.setMessage_date(message_date);
					message.setVoice_time(voice_time);
					int row = msgChatService.addMessage(message);
					
					if (row>0) {
						System.out.println("发送消息存入数据库成功，正在请求用户信息");
						UserService userService = new UserService();
						User user = userService.getUserInfo(Integer.parseInt(user_id));
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
						User target_user = userService.getUserInfo(Integer.parseInt(target_user_id));
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
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
		}, request, response);
	}
}
