package com.oracle.service;

import java.util.List;

import com.oracle.dao.ChattingDao;
import com.oracle.entity.ChatRecordsEntity;

public class ChattingService {

	public List<ChatRecordsEntity> getAllChat(int user_id, int target_user_id) {
		ChattingDao chattingDao = new ChattingDao();
		return chattingDao.getAllChat(user_id, target_user_id);
	}

}
