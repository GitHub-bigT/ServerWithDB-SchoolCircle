package com.oracle.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oracle.common.CallBack;
import com.oracle.common.StatusCode;
import com.oracle.entity.ContentEntity;
import com.oracle.service.ContentService;

public class SContentServlet extends BaseServlet {
	@Override
	protected void doPost(final HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		requestProcess(new CallBack() {
			
			@Override
			public String callBack(Map<String, Object> map) {
				//取什么类型
				int type_id = Integer.parseInt(request.getParameter("type_id"));
				//取几页  取几条 page line
				
				ArrayList<ContentEntity> list = null;
				ContentService contentService = new ContentService();
				list = contentService.getContents(type_id);
				if (list!=null) {
					map.put("list", list);
					map.put("flag", StatusCode.Dao.SELECT_SUCCESS);
				}else {
					map.put("flag", StatusCode.Dao.SELECT_FAIL);
				}
				return null;
			}
		}, request, response);
	}
}
