package com.oracle.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.oracle.entity.ContentEntity;

public class ContentDao extends BaseDao {

	public int addContent(ContentEntity contentEntity) {
		openCon();
		int row = 0;
		//先获取id
		try {
			ps = con.prepareStatement("select sco_id.nextval from dual");
			ResultSet rs = ps.executeQuery();
			int content_id = 0;
			while(rs.next()){
				content_id = rs.getInt(1);
			}
			if (content_id!=0) {
				ps = con.prepareStatement("insert into sco_contents(content_id,content_content,content_date,user_id,type_id,content_title) values(?,?,?,?,?,?)");
				ps.setInt(1, content_id);
				ps.setString(2, contentEntity.getContent_content());
				ps.setString(3, contentEntity.getContent_date());
				ps.setInt(4, contentEntity.getUser_id());
				ps.setInt(5, contentEntity.getType_id());
				ps.setString(6, contentEntity.getContent_title());
				row = ps.executeUpdate();
				if (row > 0) {
					for(String content_pic : contentEntity.getContent_pics()){
						System.out.println("dao:"+content_id+" "+content_pic);
						ps = con.prepareStatement("insert into sco_contents_pic(sco_contents_pic_id,content_id,content_pic) values(sco_id.nextval,?,?)");
						ps.setInt(1,content_id);
						ps.setString(2, content_pic);
						row = ps.executeUpdate();
					}
				}else {
					return 0;
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		
		return row;
	}

	public ArrayList<ContentEntity> getContents(int type_idd) {
		openCon();
		ArrayList<ContentEntity> list = null;
 		try {
			ps = con.prepareStatement("select c.content_id,c.content_content,c.content_date,c.content_title,c.user_id,c.type_id,u.user_name,u.user_img,t.type_name,p.content_pic from sco_contents c right outer join sco_contents_pic p on c.content_id=p.content_id,users u ,sco_types t where c.user_id=u.user_id and c.type_id=t.type_id and t.type_id=?");
			ps.setInt(1, type_idd);
			ResultSet rs = ps.executeQuery();
			Map<String, ContentEntity> map = new HashMap<>();
			String key = "";
			while(rs.next()){
				//得到内容id
				int content_id = rs.getInt(1);
				//内容
				String content_content = rs.getString(2);
				//内容日期
				String content_date = rs.getString(3);
				//内容标题
				String content_title = rs.getString(4);
				//用户id
				int user_id = rs.getInt(5);
				//类型id
				int type_id = rs.getInt(6);
				//用户名
				String user_name = rs.getString(7);
				//用户头像地址
				String user_img = rs.getString(8);
				//类型名
				String type_name = rs.getString(9);
				//内容图片
				String content_pic = rs.getString(10);
				
				//内容id+用户id+类型id作为key值
				key = rs.getInt(1)+""+rs.getInt(5)+""+rs.getInt(6);
				
				if (map.keySet().contains(key)) {
					//map中包含key的话
					//将图片list根据key值从map中取出来实体类，然后将图片存入list  不存map
					map.get(key).getContent_pics().add(content_pic);
					
				}else {
					//如果不包含的话
					//new出照片集合，将照片存入集合当中，然后将集合存入实体
					//将实体直接存入map
					ContentEntity contentEntity = new ContentEntity();
					contentEntity.setContent_id(content_id);
					contentEntity.setContent_content(content_content);
					contentEntity.setContent_title(content_title);
					contentEntity.setContent_date(content_date);
					contentEntity.setUser_id(user_id);
					if (user_img==null) {
						user_img = "null";
					}
					contentEntity.setUser_img(user_img);
					contentEntity.setUser_name(user_name);
					contentEntity.setType_id(type_id);
					contentEntity.setType_name(type_name);
					ArrayList<String> content_pics = new ArrayList<>();
					content_pics.add(content_pic);
					contentEntity.setContent_pics(content_pics);
					map.put(key, contentEntity);
				}
			}
			if (map.size()!=0) {
				list = new ArrayList<>();
				Set<String> keySet = map.keySet();
				for(String mKey : keySet){
					list.add(map.get(mKey));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

}
