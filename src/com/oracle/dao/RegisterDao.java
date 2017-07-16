package com.oracle.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RegisterDao extends BaseDao {

	public boolean isRepeat(String tel) {
		openCon();
		try {
			ArrayList<String> list = new ArrayList<>();
			ps = con.prepareStatement("select user_tel from users");
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				String user_tel = rs.getString(1);
				list.add(user_tel);
			}
			for(String user_tel : list) {
				if (tel.equals(user_tel)) {
					return true;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}

	public int addUser(String tel, String pwd) {
		openCon();
		int row = 0;
		int user_id = -1;
		try {
			ps = con.prepareStatement("select sco_id.nextval from dual");
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				user_id = rs.getInt(1);
			}
			ps = con.prepareStatement("insert into users(user_id,user_tel,user_pwd,user_name) values(?,?,?,?)");
			ps.setInt(1, user_id);
			ps.setString(2, tel);
			ps.setString(3, pwd);
			ps.setString(4, tel);
			row = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user_id;
	}

}
