package com.oracle.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.oracle.entity.NewsEntity;

public class NewsDao extends BaseDao {

	public int add(NewsEntity news) {
		openCon();
		int row = 0;
		try {
			ps = con.prepareStatement("insert into news(news_id,news_title,news_content,news_pic,news_date) values(sco_id.nextval,?,?,?,?)");
			ps.setString(1, news.getNews_title());
			ps.setString(2, news.getNews_content());
			ps.setString(3, news.getNews_pic());
			ps.setString(4, news.getNews_date());
			row = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return row;
	}

	public ArrayList<NewsEntity> selectNews() {
		openCon();
		ArrayList<NewsEntity> list = null;
		try {
			ps = con.prepareStatement("select news_id,news_title,news_content,news_pic,news_date from news");
			ResultSet rs = ps.executeQuery();
			if (rs!=null) {
				list = new ArrayList<>();
				while(rs.next()){
					int news_id = rs.getInt(1);
					String news_title = rs.getString(2);
					String news_content = rs.getString(3);
					String news_pic = rs.getString(4);
					String news_date = rs.getString(5);
					NewsEntity news = new NewsEntity();
					news.setNews_id(news_id);
					news.setNews_title(news_title);
					news.setNews_content(news_content);
					news.setNews_pic(news_pic);
					news.setNews_date(news_date);
					list.add(news);
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

}
