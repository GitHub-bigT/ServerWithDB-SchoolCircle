<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form method="POST" action="ContentCommentServlet">
类型select add<input type="text" name="type">
内容id<input type="text" name="content_id">
用户id<input type="text" name="user_id">
评论内容<input type="text" name="comment_content">
<input type="submit" value="添加评论">
</form>

<form method="POST" action="ContentCommentServlet">
类型select add<input type="text" name="type">
内容id<input type="text" name="content_id">
<input type="submit" value="查看某个内容的评论">
</form>
</body>
</html>