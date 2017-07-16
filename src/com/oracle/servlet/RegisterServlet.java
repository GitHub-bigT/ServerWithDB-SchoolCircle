package com.oracle.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oracle.common.CallBack;
import com.oracle.common.StatusCode;
import com.oracle.service.RegisterService;

public class RegisterServlet extends BaseServlet {
	@Override
	protected void doPost(final HttpServletRequest request,final
			HttpServletResponse response) throws ServletException, IOException {
		requestProcess(new CallBack() {
			
			@Override
			public String callBack(Map<String, Object> map) {
				String tel = request.getParameter("user_tel");
				//得到电话之后先去判断有没有重复
				RegisterService registerService = new RegisterService();
				if (registerService.isRepeat(tel)) {
					//如果有重复的
					map.put("flag", StatusCode.Dao.INSERT_FAIL);
					map.put("提示", "手机号已被注册");
				}else {
					String pwd = request.getParameter("user_pwd");
					System.out.println(tel+" "+pwd);
					int user_id = registerService.addUser(tel,pwd);
					if (user_id != -1) {
						System.out.println(user_id + "ssss");
						map.put("flag", user_id);
					}else {
						map.put("flag", StatusCode.Dao.INSERT_FAIL);
					}
				}
				
			
				return null;
			}
		}, request, response);
	}
}
