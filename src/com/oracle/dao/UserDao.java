package com.oracle.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.oracle.entity.User;

public class UserDao extends BaseDao {

	public User getUserInfo(int user_id) {
		openCon();
		User user = null;
		try {
			ps = con.prepareStatement("select user_name,user_img,user_tel,user_pwd from users where user_id=?");
			ps.setInt(1, user_id);
			ResultSet rs = ps.executeQuery();
			
			if (rs!=null) {
				user = new User();
			}
			while(rs.next()){
				String user_name = rs.getString(1); 
				String user_img = rs.getString(2); 
				String user_tel = rs.getString(3);
				String user_pwd = rs.getString(4);
				if (user_img==null) {
					user_img="null";
				}
				user.setUser_id(user_id);
				user.setUser_name(user_name);
				user.setUser_img(user_img);
				user.setUser_tel(user_tel);
				user.setUser_pwd(user_pwd);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}

	public int updateUserImg(int user_id,String user_img) {
		openCon();
		int row = 0 ;
		try {
			ps = con.prepareStatement("update users set user_img=? where user_id=?");
			ps.setString(1, user_img);
			ps.setInt(2, user_id);
			row = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return row;
	}

	public int updateUserName(int user_id, String user_name) {
		openCon();
		int row = 0 ;
		try {
			ps = con.prepareStatement("update users set user_name=? where user_id=?");
			ps.setString(1, user_name);
			ps.setInt(2, user_id);
			row = ps.executeUpdate();
			System.out.println("dasdasda   "+user_id+"  "+user_name+"  "+row);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return row;
	}

}
