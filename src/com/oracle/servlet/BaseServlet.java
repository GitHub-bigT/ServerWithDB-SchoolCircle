package com.oracle.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.oracle.common.CallBack;
import com.oracle.common.StatusCode;
import com.oracle.exception.PageException;

/**
 * Servlet implementation class BaseServlet
 */
public class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static String CONTENTTYPE_HTML="text/html;charset=UTF-8";
	private final static String CONTENTTYPE_JSON="application/json;charset=UTF-8";
    //gsonĿ�ģ���java����ת��Ϊjson����
	private Gson gson = new GsonBuilder().setDateFormat("yyyy-mm-dd hh:mm:ss").create();  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BaseServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    /**
     * 1.�ܹ������ظ�
     * 2.�ܹ��ó���Աд����
     * @param call
     * @param request
     * @param response
     */
    public void requestProcess(CallBack call, HttpServletRequest request, HttpServletResponse response) {
    	try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	Map<String, Object> map = new HashMap<>();
    	String result = null;
    	int status = StatusCode.Common.SUCCESS;
    	try {
    		result = call.callBack(map);
			
		} catch (Exception e) {
			status =  StatusCode.Common.FAIL;
			e.printStackTrace();
		}
    	map.put("code", status);
		/**
		 * �������Ա����null����ʾjson����
		 * �����Ϊnull����ʾ��ҳ��ת��stu.jsp��
		 */
		if (result==null) {
			response.setContentType(CONTENTTYPE_JSON);
			String json = gson.toJson(map);
			try {
				response.getWriter().write(json);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (result!=null) {
			response.setContentType(CONTENTTYPE_HTML);
			if ("".equals(result)) {
				//�׳��쳣
				throw new PageException("ҳ�淵��ֵ����Ϊ��");
			}
			request.setAttribute("map", map);
			try {
				request.getRequestDispatcher(result).forward(request, response);
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
