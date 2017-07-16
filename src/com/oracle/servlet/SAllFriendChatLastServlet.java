package com.oracle.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oracle.common.CallBack;
import com.oracle.common.StatusCode;
import com.oracle.dao.BaseDao;
import com.oracle.entity.MessageEntity;
import com.oracle.service.MsgChatService;

public class SAllFriendChatLastServlet extends BaseServlet {
	@Override
	protected void doPost(final HttpServletRequest request,final
			HttpServletResponse response) throws ServletException, IOException {
		requestProcess(new CallBack() {
			
			@Override
			public String callBack(Map<String, Object> map) {
				String type = request.getParameter("type");
				int user_id = Integer.parseInt(request.getParameter("user_id"));
				/**
				 * 得到所有好友的最后一条信息
				 */
				if ("message".equals(type)) {
					MsgChatService msgChatService = new MsgChatService();
					List<MessageEntity> list = msgChatService.getAllLast(user_id);
					if (list!=null) {
						map.put("flag", StatusCode.Dao.SELECT_SUCCESS);
						map.put("list", list);
					}else {
						map.put("flag", StatusCode.Dao.SELECT_FAIL);
					}
				}
				/**
				 * 得到所有好友及用户信息
				 */
				if ("contact".equals(type)) {
					MsgChatService msgChatService = new MsgChatService();
					List<MessageEntity> list = msgChatService.getAllFriend(user_id);
					if (list!=null) {
						map.put("flag", StatusCode.Dao.SELECT_SUCCESS);
						map.put("list", list);
					}else {
						map.put("flag", StatusCode.Dao.SELECT_FAIL);
					}
				}
				return null;
			}
		}, request, response);
		super.doPost(request, response);
	}
}
