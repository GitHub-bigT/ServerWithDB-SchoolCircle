<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form method="POST" action="ContentsServlet" enctype="multipart/form-data">
内容<input type="text" name="content_content">
发表内容的用户id<input type="text" name="user_id">
类型id<input type="text" name="type_id">
内容的标题<input type="text" name="content_title">
选择图片1<input type="file" name="content_pics">
选择图片2<input type="file" name="content_pics">
选择图片3<input type="file" name="content_pics">
选择图片4<input type="file" name="content_pics">
选择图片5<input type="file" name="content_pics">
<input type="submit" value="提交">
</form>

<form method="POST" action="SContentServlet">
类型id<input type="text" name="type_id">
<input type="submit" value="请求内容">
</form>

</body>
</html>