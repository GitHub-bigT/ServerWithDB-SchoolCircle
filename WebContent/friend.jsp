<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="AddFriendServlet" method="POST">
		类型 select add
		<input type="text" name="type"/>
		请求的id
		<input type="text" name="user_id"/>
		<input type="submit" value="查询推荐的朋友">
	</form>
	<form action="AddFriendServlet" method="POST">
		类型 select add
		<input type="text" name="type"/>
		请求的id
		<input type="text" name="user_id"/>
		好友id
		<input type="text" name="friend_id"/>
		<input type="submit" value="添加好友">
	</form>
</body>
</html>