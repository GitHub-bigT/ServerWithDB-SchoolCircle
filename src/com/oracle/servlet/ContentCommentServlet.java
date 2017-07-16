package com.oracle.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oracle.common.CallBack;
import com.oracle.common.StatusCode;
import com.oracle.entity.Comment;
import com.oracle.service.CommentService;

public class ContentCommentServlet extends BaseServlet {
	@Override
	protected void doPost(final HttpServletRequest request,final
			HttpServletResponse response) throws ServletException, IOException {
		requestProcess(new CallBack() {
			
			@Override
			public String callBack(Map<String, Object> map) {
				String type = request.getParameter("type");
				/**
				 * 添加评论
				 */
				String content_id = request.getParameter("content_id");
				System.out.println(content_id+"");
				if ("add".equals(type)) {
					String user_id = request.getParameter("user_id");
					String comment_content = request.getParameter("comment_content");
					SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					Date dataDate =new  Date(System.currentTimeMillis());
					String comment_date = dataFormat.format(dataDate);
					Comment comment = new Comment();
					comment.setUser_id(Integer.parseInt(user_id));
					comment.setContent_id(Integer.parseInt(content_id));
					comment.setComment_content(comment_content);
					comment.setComment_date(comment_date);
					CommentService service = new CommentService();
					int row = service.addComment(comment);
					if (row>0) {
						map.put("flag", StatusCode.Dao.INSERT_SUCCESS);
					}else {
						map.put("flag", StatusCode.Dao.INSERT_FAIL);
					}
				}
				if ("select".equals(type)) {
					CommentService service = new CommentService();
					List<Comment> list = service.getAllComment(Integer.parseInt(content_id));
					if (list!=null) {
						map.put("flag", StatusCode.Dao.SELECT_SUCCESS);
						map.put("list", list);
					}else {
						map.put("flag", StatusCode.Dao.SELECT_FAIL);
					}
				}
				
				return null;
			}
		}, request, response);
	}
}
