package com.oracle.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oracle.common.CallBack;
import com.oracle.common.StatusCode;
import com.oracle.entity.NewsEntity;
import com.oracle.service.NewsService;

public class SNewsServlet extends BaseServlet {
	@Override
	protected void doPost(final HttpServletRequest request,final
			HttpServletResponse response) throws ServletException, IOException {
		requestProcess(new CallBack() {
			
			@Override
			public String callBack(Map<String, Object> map) {
				//得到类型 增删改查的类型
				String type = request.getParameter("type");
				if ("select".equals(type)) {
					ArrayList<NewsEntity> list = null;
					list = selectNews();
					if (list!=null) {
						map.put("list", list);
						map.put("flag", StatusCode.Dao.SELECT_SUCCESS);
					}else {
						map.put("flag", StatusCode.Dao.SELECT_FAIL);
					}
				}
				return null;
			}
			private ArrayList<NewsEntity> selectNews() {
				NewsService newsService = new NewsService();
				return newsService.selectNews();
			}
		}, request, response);
	}
}
