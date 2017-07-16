package com.oracle.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oracle.common.CallBack;
import com.oracle.common.StatusCode;
import com.oracle.entity.User;
import com.oracle.service.FriendService;

public class AddFriendServlet extends BaseServlet {
	@Override
	protected void doPost(final HttpServletRequest request,final
			HttpServletResponse response) throws ServletException, IOException {
		requestProcess(new CallBack() {
			
			@Override
			public String callBack(Map<String, Object> map) {
				String type = request.getParameter("type");
				if("select".equals(type)) {
					String user_id = request.getParameter("user_id");
					//查好友
					FriendService friendService = new FriendService();
					List<User> list = friendService.getNoFriendList(user_id);
					if (list!=null) {
						map.put("flag", StatusCode.Dao.SELECT_SUCCESS);
						map.put("list", list);
					}else {
						map.put("flag", StatusCode.Dao.SELECT_FAIL);
					}
				}
				if ("add".equals(type)) {
					//添加好友
					String user_id = request.getParameter("user_id");
					String friend_id = request.getParameter("friend_id");
					System.out.println(user_id+" "+friend_id);
					FriendService friendService = new FriendService();
					int row = friendService.addFriend(user_id,friend_id);
					if (row>0) {
						map.put("flag", StatusCode.Dao.INSERT_SUCCESS);
					}else {
						map.put("flag", StatusCode.Dao.INSERT_FAIL);
					}
				}
				
				return null;
			}
		}, request, response);
		super.doPost(request, response);
	}
}
