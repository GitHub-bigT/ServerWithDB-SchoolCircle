package com.oracle.service;

import com.oracle.dao.RegisterDao;

public class RegisterService {

	public boolean isRepeat(String tel) {
		RegisterDao registerDao = new RegisterDao();
		return registerDao.isRepeat(tel);
	}

	public int addUser(String tel, String pwd) {
		RegisterDao registerDao = new RegisterDao();
		return registerDao.addUser(tel,pwd);
	}

}
