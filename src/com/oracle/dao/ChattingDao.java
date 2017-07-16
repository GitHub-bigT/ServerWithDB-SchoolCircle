package com.oracle.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.oracle.entity.ChatRecordsEntity;

public class ChattingDao extends BaseDao {

	public List<ChatRecordsEntity> getAllChat(int user_id, int target_user_id) {
		openCon();
		List<ChatRecordsEntity> list = null;
		try {
			ps = con.prepareStatement("select message_id,user_id,target_user_id,message_content,message_date,voice_time from sco_messages where user_id in (?,?) and target_user_id in (?,?)");
			ps.setInt(1, user_id);
			ps.setInt(2, target_user_id);
			ps.setInt(3, target_user_id);
			ps.setInt(4, user_id);
			ResultSet rs = ps.executeQuery();
			if (rs!=null) {
				list = new ArrayList<>();
			}
			while(rs.next()){
				int message_id = rs.getInt(1);
				int new_user_id = rs.getInt(2);
				int new_target_user_id = rs.getInt(3);
				String message_content = rs.getString(4);
				if (message_content==null) {
					message_content="";
				}
				String message_date = rs.getString(5);
				String voice_time = rs.getString(6);
				if (voice_time==null) {
					voice_time="";
				}
				ChatRecordsEntity chat = new ChatRecordsEntity();
				chat.setMessage_id(message_id);
				chat.setUser_id(new_user_id);
				chat.setTarget_user_id(new_target_user_id);
				chat.setMessage_content(message_content);
				chat.setMessage_date(message_date);
				chat.setVoice_time(voice_time);
				list.add(chat);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

}
