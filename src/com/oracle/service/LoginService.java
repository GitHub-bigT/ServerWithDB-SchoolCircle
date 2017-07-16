package com.oracle.service;

import java.util.Map;

import com.oracle.dao.LoginDao;

public class LoginService {

	public Map<String, Object> isLogin(String tel, String pwd) {
		LoginDao loginDao = new LoginDao();
		return loginDao.isLogin(tel,pwd);
	}

}
