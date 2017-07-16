package com.oracle.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jspsmart.upload.SmartUpload;
import com.oracle.common.CallBack;
import com.oracle.common.StatusCode;
import com.oracle.entity.NewsEntity;
import com.oracle.entity.SCOTypeEntity;
import com.oracle.service.NewsService;
import com.oracle.service.SCOTypeService;

public class SCOTypeServlet extends BaseServlet {
	@Override
	protected void doPost(final HttpServletRequest request,final
			HttpServletResponse response) throws ServletException, IOException {
		requestProcess(new CallBack() {
			
			@Override
			public String callBack(Map<String, Object> map) {
				try {
					//得到类型 增删改查的类型
					String type = request.getParameter("type");
					int row = 0;
					if ("add".equals(type)) {
						row = addSCOTypes(request);
						if (row>0) {
							map.put("flag", StatusCode.Dao.INSERT_SUCCESS);
						}else {
							map.put("flag", StatusCode.Dao.INSERT_FAIL);
						}
					}
					if ("select".equals(type)) {
						ArrayList<SCOTypeEntity> list = null;
						list = selectSCOTypes();
						if (list!=null) {
							map.put("list", list);
							map.put("flag", StatusCode.Dao.SELECT_SUCCESS);
						}else {
							map.put("flag", StatusCode.Dao.SELECT_FAIL);
						}
					}
					
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}

			private ArrayList<SCOTypeEntity> selectSCOTypes() {
				SCOTypeService scoTypeService = new SCOTypeService();
				return scoTypeService.selectSCOTypes();
			}

			private int addSCOTypes(HttpServletRequest request) {
				String sco_type = request.getParameter("sco_type");
				SCOTypeService scoTypeService = new SCOTypeService();
				return scoTypeService.addSCOType(sco_type);
			}
		}, request, response);
	}
}
