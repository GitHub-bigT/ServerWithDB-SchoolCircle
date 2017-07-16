package com.oracle.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.oracle.entity.User;

public class FriendDao extends BaseDao {

	public List<User> getNoFriendList(String user_id) {
		openCon();
		List<User> list = null;
		try {
			ps = con.prepareStatement("select user_id,user_name,user_img,user_tel from users where user_id not in(select friend_id from sco_friend where user_id=?)");
			ps.setInt(1, Integer.parseInt(user_id));
			ResultSet rs = ps.executeQuery();
			if (rs!=null) {
				list = new ArrayList<>();
			}
			while(rs.next()){
				int mUserId = rs.getInt(1);
				if (mUserId==Integer.parseInt(user_id)) {
					continue;
				}
				String user_name = rs.getString(2);
				if (user_name==null) {
					user_name = "";
				}
				String user_img = rs.getString(3);
				if (user_img==null) {
					user_img = "null";
				}
				String user_tel = rs.getString(4);
				User u = new User();
				u.setUser_id(mUserId);
				u.setUser_name(user_name);
				u.setUser_img(user_img);
				u.setUser_tel(user_tel);
				list.add(u);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public int addFriend(String user_id, String friend_id) {
		openCon();
		int row = 0;
		try {
			ps = con.prepareStatement("insert into sco_friend(user_id,friend_id) values(?,?)");
			ps.setInt(1, Integer.parseInt(user_id));
			ps.setInt(2, Integer.parseInt(friend_id));
			row = ps.executeUpdate();
			if (row>0) {
				ps = con.prepareStatement("insert into sco_friend(user_id,friend_id) values(?,?)");
				ps.setInt(1, Integer.parseInt(friend_id));
				ps.setInt(2, Integer.parseInt(user_id));
				row = ps.executeUpdate();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return row;
	}

}
