package com.oracle.service;

import com.oracle.dao.UserDao;
import com.oracle.entity.User;

public class UserService {

	public User getUserInfo(int user_id) {
		UserDao userDao = new UserDao();
		return userDao.getUserInfo(user_id);
	}

	public int updateUserImg(int user_id,String user_img) {
		UserDao userDao = new UserDao();
		return userDao.updateUserImg(user_id,user_img);
	}

	public int updateUserName(int user_id,String user_name) {
		UserDao userDao = new UserDao();
		return userDao.updateUserName(user_id,user_name);
	}
	
}
