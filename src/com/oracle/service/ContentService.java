package com.oracle.service;

import java.util.ArrayList;

import com.oracle.dao.ContentDao;
import com.oracle.entity.ContentEntity;

public class ContentService {

	public int addContent(ContentEntity contentEntity) {
		ContentDao contentDao = new ContentDao();
		return contentDao.addContent(contentEntity);
	}

	public ArrayList<ContentEntity> getContents(int type_id) {
		ContentDao contentDao = new ContentDao();
		return contentDao.getContents(type_id);
	}

}
