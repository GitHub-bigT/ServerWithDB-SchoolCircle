package com.oracle.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.oracle.entity.Comment;

public class CommentDao extends BaseDao {

	public int addComment(Comment comment) {
		openCon();
		int row = 0;
		try {
			ps = con.prepareStatement("insert into sco_content_comment(comment_id,user_id,content_id,comment_date,comment_content) values(sco_id.nextval,?,?,?,?)");
			ps.setInt(1, comment.getUser_id());
			ps.setInt(2, comment.getContent_id());
			ps.setString(3, comment.getComment_date());
			ps.setString(4, comment.getComment_content());
			row = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return row;
	}

	public List<Comment> getAllComment(int content_id) {
		openCon();
		List<Comment> list = null;
		try {
			ps = con.prepareStatement("select u.user_id,u.user_name,u.user_img,u.user_tel,c.comment_id,c.comment_date,c.comment_content from sco_content_comment c, users u where c.user_id=u.user_id and c.content_id=?");
			ps.setInt(1, content_id);
			ResultSet rs = ps.executeQuery();
			list = new ArrayList<>();
				while (rs.next()) {
					int user_id = rs.getInt(1);
					String user_name = rs.getString(2);
					String user_img = rs.getString(3);
					if (user_img==null) {
						user_img= "null";
					}
					String user_tel = rs.getString(4);
					int comment_id = rs.getInt(5);
					String comment_date = rs.getString(6);
					String comment_content = rs.getString(7);
					Comment comment = new Comment();
					comment.setUser_id(user_id);
					comment.setUser_name(user_name);
					comment.setUser_img(user_img);
					comment.setUser_tel(user_tel);
					comment.setComment_id(comment_id);
					comment.setComment_date(comment_date);
					comment.setComment_content(comment_content);
					list.add(comment);
				}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

}
