package com.oracle.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oracle.common.CallBack;
import com.oracle.common.StatusCode;
import com.oracle.service.UserService;

public class UserInfoServlet extends BaseServlet {
	@Override
	protected void doPost(final HttpServletRequest request,final
			HttpServletResponse response) throws ServletException, IOException {
		requestProcess(new CallBack() {
			
			@Override
			public String callBack(Map<String, Object> map) {
				String user_id = request.getParameter("user_id");
				String user_name = request.getParameter("user_name");
				UserService userService= new UserService();
				int row = userService.updateUserName(Integer.parseInt(user_id), user_name);
				if (row>0) {
					map.put("flag", StatusCode.Dao.UPDATE_SUCCESS);
				}else {
					map.put("flag", StatusCode.Dao.UPDATE_FAIL);
				}
				return null;
			}
		}, request, response);
	}
}
