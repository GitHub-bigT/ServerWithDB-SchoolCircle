package com.oracle.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jspsmart.upload.File;
import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;
import com.oracle.common.CallBack;
import com.oracle.common.StatusCode;
import com.oracle.entity.NewsEntity;
import com.oracle.service.NewsService;

public class NewsServlet extends BaseServlet {
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
					//得到类型 增删改查的类型
					String type = smartUpload.getRequest().getParameter("type");
					int row = 0;
					if ("add".equals(type)) {
						row = addNews(smartUpload);
						if (row>0) {
							map.put("flag", StatusCode.Dao.INSERT_SUCCESS);
						}else {
							map.put("flag", StatusCode.Dao.INSERT_FAIL);
						}
					}
					
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
			private int addNews(SmartUpload smartUpload) {
				String news_title = smartUpload.getRequest().getParameter("news_title");
				String news_content = smartUpload.getRequest().getParameter("news_content");
				File file = smartUpload.getFiles().getFile(0);
				String news_pic = "";
				if (file!=null) {
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
					//upload+文件夹名+UUID文件名+扩展名 
					String s_news_pic = folder.getAbsolutePath()+"/"+newFileName+"."+file.getFileExt();
					//存入数据库的名字为 upload+文件夹名+uuid+扩展名
					news_pic = "upload/"+currentDate+"/"+newFileName+"."+file.getFileExt();
					try {
						file.saveAs(s_news_pic);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SmartUploadException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				SimpleDateFormat dataFormat = new SimpleDateFormat("YYYY-MM-dd HH:MM:ss");
				Date dataDate =new  Date(System.currentTimeMillis());
				String news_date = dataFormat.format(dataDate);
				System.out.println(news_title+" "+news_content+" "+news_pic+" "+news_date);
				NewsEntity news = new NewsEntity();
				news.setNews_title(news_title);
				news.setNews_content(news_content);
				news.setNews_pic(news_pic);
				news.setNews_date(news_date);
				NewsService newsService = new NewsService();
				return newsService.addNews(news);		
			}

		}, request, response);
	}
}
