package com.oracle.service;

import java.util.ArrayList;

import com.oracle.dao.NewsDao;
import com.oracle.entity.NewsEntity;

public class NewsService {

	public int addNews(NewsEntity news) {
		NewsDao newsDao = new NewsDao();
		return newsDao.add(news);
	}

	public ArrayList<NewsEntity> selectNews() {
		NewsDao newsDao = new NewsDao();
		return newsDao.selectNews();
	}

}
