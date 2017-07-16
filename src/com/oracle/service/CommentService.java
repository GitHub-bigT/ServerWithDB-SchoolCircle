package com.oracle.service;

import java.util.List;

import com.oracle.dao.CommentDao;
import com.oracle.entity.Comment;

public class CommentService {

	public int addComment(Comment comment) {
		CommentDao dao = new CommentDao();
		return dao.addComment(comment);
	}

	public List<Comment> getAllComment(int content_id) {
		CommentDao dao = new CommentDao();
		return dao.getAllComment(content_id);
	}
	
}
