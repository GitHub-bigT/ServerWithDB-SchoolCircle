<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="MsgChatServlet" method="POST">
		用户id<input type="text" name="user_id"/>
		目标用户id<input type="text" name="target_user_id"/>
		消息<input type="text" name="message_content"/>
		<input type="submit" value="发送消息">
	</form>
	得到该用户的所有好友的最后一条消息
	<form action="SAllFriendChatLastServlet" method="POST">
		请求类型message或者contact<input type="text" name="type"/>
		用户id<input type="text" name="user_id"/>
		<input type="submit" value="查询">
	</form>
	得到用户与目标用户之间的聊天记录
	<form action="GetChattomgRecordsServlet" method="POST">
		用户id<input type="text" name="user_id"/>
		目标用户id<input type="text" name="target_user_id"/>
		<input type="submit" value="查询聊天记录">
	</form>
</body>
</html>