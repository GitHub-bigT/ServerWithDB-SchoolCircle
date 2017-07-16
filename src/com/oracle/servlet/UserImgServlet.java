package com.oracle.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jspsmart.upload.File;
import com.jspsmart.upload.SmartUpload;
import com.oracle.common.CallBack;
import com.oracle.common.StatusCode;
import com.oracle.service.UserService;

public class UserImgServlet extends BaseServlet {
	@Override
	protected void doPost(final HttpServletRequest request,final
			HttpServletResponse response) throws ServletException, IOException {
		requestProcess(new CallBack() {
			
			@Override
			public String callBack(Map<String, Object> map) {
				SmartUpload smartUpload = new SmartUpload();
				try {
					smartUpload.initialize(getServletConfig(), request, response);
					smartUpload.upload();
					File file = smartUpload.getFiles().getFile(0);
					
					//以年月日为文件夹名
					//得到当前时间
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
					Date date =new  Date(System.currentTimeMillis());
					String currentDate = simpleDateFormat.format(date);
					//判断有没有文件
					java.io.File folder = new java.io.File(request.getServletContext().getRealPath("upload")+"/"+currentDate);
					if (!folder.exists()) {
						folder.mkdirs();
					}
					//以UUID生成的随机ID为文件名
					String fileName = UUID.randomUUID().toString();
					//将生成UUID字符串中的-替换成a变成一整长串
					String newFileName = fileName.replace("-", "a");
					//upload+文件夹名+UUID文件名+扩展名 folder.getAbsolutePath()+"/"+
					String s_user_img = folder.getAbsolutePath()+"/"+newFileName+"."+file.getFileExt();
					file.saveAs(s_user_img);
					//存入数据库的名字为 文件夹名+uuid+扩展名
					String user_img = "upload/"+currentDate+"/"+newFileName+"."+file.getFileExt();
					//得到用户id
					String user_id = smartUpload.getRequest().getParameter("user_id");
					UserService userService = new UserService();
					int row = userService.updateUserImg(Integer.parseInt(user_id),user_img);
					if (row>0) {
						map.put("flag", StatusCode.Dao.UPDATE_SUCCESS);
					}else {
						map.put("flag", StatusCode.Dao.UPDATE_FAIL);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
		}, request, response);
	}
}
