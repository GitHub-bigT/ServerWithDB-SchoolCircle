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
import com.oracle.entity.ContentEntity;
import com.oracle.service.ContentService;

public class ContentsServlet extends BaseServlet {
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
//					int fileCount = (int)smartUpload.getFiles().getSize();
					int fileCount=smartUpload.getFiles().getCount();
					System.out.println("文件个数:"+fileCount);
					//创建一个集合用来存储照片的名字集合
					ArrayList<String> picList = new ArrayList<>();
					if (fileCount>0) {
						//带有文件的请求
						for (int i = 0; i < fileCount; i++) {
							File file = smartUpload.getFiles().getFile(i);
							if (file.getSize()==0) {
								continue;
							}
							System.out.println("执行次数:"+i);
							//以年月日为文件夹名
							//得到当前时间
							SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
							Date date =new  Date(System.currentTimeMillis());
							String currentDate = simpleDateFormat.format(date);
							//判断有没有文件
							java.io.File folder = new java.io.File(request.getServletContext().getRealPath("upload")+"\\"+currentDate);
							System.out.println(request.getServletContext().getRealPath("upload")+"\\"+currentDate);
							if (!folder.exists()) {
								folder.mkdirs();
							}
							//以UUID生成的随机ID为文件名
							String fileName = UUID.randomUUID().toString();
							//将生成UUID字符串中的-替换成a变成一整长串
							String newFileName = fileName.replace("-", "a");
							//upload+文件夹名+UUID文件名+扩展名 folder.getAbsolutePath()+"/"+
							String s_news_pic = folder.getAbsolutePath()+"/"+newFileName+"."+file.getFileExt();
							//存入数据库的名字为 文件夹名+uuid+扩展名
							String news_pic = "upload/"+currentDate+"/"+newFileName+"."+file.getFileExt();
							picList.add(news_pic);
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
					}
					//取到内容   用户id  类型id  发布的日期  内容标题
					String content_content = smartUpload.getRequest().getParameter("content_content");
					String user_id = smartUpload.getRequest().getParameter("user_id");
					String type_id = smartUpload.getRequest().getParameter("type_id");
					String content_title = smartUpload.getRequest().getParameter("content_title");
					SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					Date dataDate =new  Date(System.currentTimeMillis());
					String content_date = dataFormat.format(dataDate);
					
					System.out.println(content_content+"  "+user_id+"  "+type_id+"  "+content_title+"  "+content_date);
					for(String picUrl : picList){
						System.out.println(picUrl);
					}
					
					ContentEntity contentEntity = new ContentEntity();
					contentEntity.setContent_content(content_content);
					contentEntity.setContent_title(content_title);
					contentEntity.setContent_date(content_date);
					contentEntity.setUser_id(Integer.parseInt(user_id));
					contentEntity.setType_id(Integer.parseInt(type_id));
					contentEntity.setContent_pics(picList);
					ContentService contentService = new ContentService();
					int row = contentService.addContent(contentEntity);
					if (row > 0) {
						map.put("flag", StatusCode.Dao.INSERT_SUCCESS);
					}else {
						map.put("flag", StatusCode.Dao.INSERT_FAIL);
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
