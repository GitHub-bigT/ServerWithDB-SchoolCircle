package com.oracle.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.oracle.entity.User;

public class LoginDao extends BaseDao {

	public Map<String, Object> isLogin(String tel, String pwd) {
		openCon();
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			ps = con.prepareStatement("select user_id,user_tel,user_pwd,user_name,user_img from users");
			ResultSet rs = ps.executeQuery();
			ArrayList<User> list = new ArrayList<>();
			while(rs.next()){
				int user_id = rs.getInt(1);
				String user_tel = rs.getString(2);
				String user_pwd = rs.getString(3);
				String user_name = rs.getString(4);
				String user_img = rs.getString(5);
				User user = new User();
				user.setUser_id(user_id);
				user.setUser_tel(user_tel);
				user.setUser_pwd(user_pwd);
				user.setUser_name(user_name);
//				System.out.println("user_img:"+user_img);
				if (user_img==null) {
					user_img = "null";
				}
				user.setUser_img(user_img);
				list.add(user);
			}
			for(User user : list){
				if (tel.equals(user.getUser_tel())&&pwd.equals(user.getUser_pwd())) {
					map.put("isLogin", "true");
					map.put("user", user);
					return map;
				}else {
					map.put("isLogin", "false");
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

}
