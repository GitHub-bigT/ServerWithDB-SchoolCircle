package com.oracle.service;

import java.util.List;

import com.oracle.dao.FriendDao;
import com.oracle.entity.User;

public class FriendService {

	public List<User> getNoFriendList(String user_id) {
		FriendDao friendDao = new FriendDao();
		return friendDao.getNoFriendList(user_id);
	}

	public int addFriend(String user_id, String friend_id) {
		FriendDao friendDao = new FriendDao();
		return friendDao.addFriend(user_id,friend_id);
	}

}
