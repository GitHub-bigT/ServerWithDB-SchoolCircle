package com.oracle.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oracle.common.CallBack;
import com.oracle.common.StatusCode;
import com.oracle.entity.ChatRecordsEntity;
import com.oracle.service.ChattingService;

public class GetChattomgRecordsServlet extends BaseServlet {
	@Override
	protected void doPost(final HttpServletRequest request,final
			HttpServletResponse response) throws ServletException, IOException {
		requestProcess(new CallBack() {
			
			@Override
			public String callBack(Map<String, Object> map) {
				int user_id = Integer.parseInt(request.getParameter("user_id"));
				int target_user_id = Integer.parseInt(request.getParameter("target_user_id"));
				ChattingService service = new ChattingService();
				List<ChatRecordsEntity> list = service.getAllChat(user_id,target_user_id);
				if (list!=null) {
					map.put("flag", StatusCode.Dao.SELECT_SUCCESS);
					map.put("list", list);
				}else {
					map.put("flag", StatusCode.Dao.SELECT_FAIL);
				}
				return null;
			}
		}, request, response);
	}
}
