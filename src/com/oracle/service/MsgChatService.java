package com.oracle.service;

import java.util.List;

import com.oracle.dao.MsgChatDao;
import com.oracle.entity.MessageEntity;

public class MsgChatService {

	public int addMessage(MessageEntity message) {
		// TODO Auto-generated method stub
		MsgChatDao msgChatDao = new MsgChatDao();
		return msgChatDao.addMessage(message);
	}

	public List<MessageEntity> getAllLast(int user_id) {
		MsgChatDao msgChatDao = new MsgChatDao();
		return msgChatDao.getAllLast(user_id);
	}

	public List<MessageEntity> getAllFriend(int user_id) {
		MsgChatDao msgChatDao = new MsgChatDao();
		return msgChatDao.getAllFriend(user_id);
	}

}
