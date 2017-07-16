package com.oracle.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oracle.common.CallBack;
import com.oracle.common.StatusCode;
import com.oracle.entity.User;
import com.oracle.service.UserService;

public class UserServlet extends BaseServlet {
	@Override
	protected void doPost(final HttpServletRequest request,final
			HttpServletResponse response) throws ServletException, IOException {
		requestProcess(new CallBack() {
			
			@Override
			public String callBack(Map<String, Object> map) {
				String user_id = request.getParameter("user_id");
				System.out.println("用户id："+user_id);
				UserService userService = new UserService();
				User user = userService.getUserInfo(Integer.parseInt(user_id));
				if (user!=null) {
					map.put("flag", StatusCode.Dao.SELECT_SUCCESS);
					map.put("user", user);
				}else {
					map.put("flag", StatusCode.Dao.SELECT_FAIL);
				}
				return null;
			}
		}, request, response);
	}
}
