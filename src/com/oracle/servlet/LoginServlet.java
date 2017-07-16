package com.oracle.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oracle.common.CallBack;
import com.oracle.common.StatusCode;
import com.oracle.service.LoginService;

public class LoginServlet extends BaseServlet {
	@Override
	protected void doPost(final HttpServletRequest request,final
			HttpServletResponse response) throws ServletException, IOException {
		requestProcess(new CallBack() {
			
			@Override
			public String callBack(Map<String, Object> map) {
				String tel = request.getParameter("user_tel");
				String pwd = request.getParameter("user_pwd");
				System.out.println(tel+" "+pwd);
				LoginService loginService = new LoginService();
				if ("true".equals(loginService.isLogin(tel,pwd).get("isLogin"))) {
					map.put("flag", StatusCode.Login.LOGIN_SUCCESS);
					map.put("user", loginService.isLogin(tel,pwd).get("user"));
				}else {
					map.put("flag", StatusCode.Login.LOGIN_FAIL);
				}
				return null;
			}
		}, request, response);
	}
}
