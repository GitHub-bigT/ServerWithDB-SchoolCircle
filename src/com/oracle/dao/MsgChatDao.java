package com.oracle.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.oracle.comparator.DateComparator;
import com.oracle.entity.MessageEntity;

public class MsgChatDao extends BaseDao {

	public int addMessage(MessageEntity message) {
		openCon();
		int row = 0;
		try {
			ps = con.prepareStatement("insert into sco_messages(message_id,user_id,target_user_id,message_content,message_date,voice_time) values(sco_id.nextval,?,?,?,?,?)");
			ps.setInt(1, message.getUser_id());
			ps.setInt(2, message.getTarget_user_id());
			ps.setString(3, message.getMessage_content());
			ps.setString(4, message.getMessage_date());
			ps.setString(5, message.getVoice_time());
			row = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return row;
	}
	

	public List<MessageEntity> getAllLast(int user_id) {
		openCon();
		List<MessageEntity> list = null;
//		List<MessageEntity> lastRecordsList = null;
		try {
			/**
			 * 得到该用户的所有聊天消息（发送方和接收方为user_id）
			 */
			List<ExtraEntity> allList = null;
			ps = con.prepareStatement("select user_id||','||target_user_id,message_id,user_id,target_user_id,message_content,message_date,voice_time from sco_messages where user_id=? or target_user_id=?");
			ps.setInt(1, user_id);
			ps.setInt(2, user_id);
			ResultSet rs = ps.executeQuery();
			if (rs!=null) {
				allList = new ArrayList<>();
			}
			while(rs.next()){
				String mixture_id = rs.getString(1);
				int message_id = rs.getInt(2);
				int new_user_id = rs.getInt(3);
				int new_target_user_id = rs.getInt(4);
				String message_content = rs.getString(5);
				if (message_content==null) {
					message_content="";
				}
				String message_date = rs.getString(6);
				String voice_time = rs.getString(7);
				if (voice_time==null) {
					voice_time="";
				}
				
				MessageEntity messageEntity = new MessageEntity();
				messageEntity.setMessage_id(message_id);
				messageEntity.setUser_id(new_user_id);
				messageEntity.setTarget_user_id(new_target_user_id);
				messageEntity.setMessage_content(message_content);
				messageEntity.setMessage_date(message_date);
				messageEntity.setVoice_time(voice_time);
				
				//将拼接的字段和message实体类存入到额外的实体类中
				//并将这个额外的实体类放入allList当中
				ExtraEntity mEntity = new ExtraEntity();
				mEntity.setUsers(mixture_id);
				mEntity.setMessageEntity(messageEntity);
				allList.add(mEntity);
			}
			//对allList进行分组  获得 user_id与目标用户 target_user_id是一类的聊天记录
			//返回一个map key为数组{103,43}  value为消息的集合
			Map<Object, List<MessageEntity>> map = getMap(allList);
			Set<Object> keys = map.keySet();
			System.out.println("key的个数为:"+keys.size());
			int ii =0;
			for (Object key : keys) {
				String[] key1 = (String[]) key;
				System.out.println("-----------------------");
				System.out.println("map：");
				System.out.println("key的值为:"+key1[0]+","+key1[1]);
				ii++;
				List<MessageEntity> t1 = map.get(key1);
				System.out.println("第"+ii+"个集合的个数有:"+t1.size());
				for(int mm = 0 ;mm<t1.size();mm++) {
					System.out.println("第"+mm+"个消息类：");
					System.out.println("消息内容为："+t1.get(mm).getMessage_content());
					System.out.println("消息时间:"+t1.get(mm).getMessage_date());
				}
				System.out.println("-----------------------");
			}
			
			//对map进行日期排列并只取最后一条
			//返回一个只有每个好友最后一条聊天记录的list
			List<MessageEntity> lastRecordsList = getSortList(map);
			for(int i = 0 ; i<lastRecordsList.size() ; i++){
				MessageEntity testEntity = lastRecordsList.get(i);
				System.out.println("用户id： "+testEntity.getUser_id()+" 目标用户id："+testEntity.getTarget_user_id());
				System.out.println("消息内容:"+testEntity.getMessage_content()+"  消息时间："+testEntity.getMessage_date());
			}
			if (lastRecordsList.size()!=0) {
				list = new ArrayList<>();
			}
			for(int i = 0;i<lastRecordsList.size();i++){
				MessageEntity m = lastRecordsList.get(i);
				int mUser_id = m.getUser_id();
				int mTarget_user_id = m.getTarget_user_id();
				int target_user_id = isTargetId(user_id,mUser_id,mTarget_user_id);
				//请求用户头像和用户名
				ps = con.prepareStatement("select user_name,user_img from users where user_id=?");
				ps.setInt(1, target_user_id);
				ResultSet rss = ps.executeQuery();
				
				while(rss.next()){
					String user_name = rss.getString(1);
					String user_img = rss.getString(2);
					if (user_img==null) {
						user_img = "null";
					}
					m.setUser_name(user_name);
					m.setUser_img(user_img);
					list.add(m);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 验证哪个是除了请求者id外的另一个id
	 * @param user_id
	 * @param mUser_id
	 * @param mTarget_user_id
	 */
	private int isTargetId(int user_id, int mUser_id, int mTarget_user_id) {
		// TODO Auto-generated method stub
		if (user_id==mUser_id) {
			return mTarget_user_id;
		}
		if (user_id==mTarget_user_id) {
			return mUser_id;
		}
		return 0;
	}


	/**
	 * 对map进行日期排列并只取最后一条
	 * @param map  key为数组{103,43}  value为消息的集合
	 * @return  返回一个只有每个好友最后一条聊天记录的list
	 */
	private List<MessageEntity> getSortList(Map<Object, List<MessageEntity>> map) {
		List<MessageEntity> lastRecordsList = new ArrayList<>();
		//从map中取出来
		for(Object key : map.keySet()){
			List<MessageEntity> lm = map.get(key);
			//遍历map当中的集合  对时间进行排序
			DateComparator comparator = new DateComparator();
			Collections.sort(lm,comparator);
			//升序排  最后一个为最后一个时间
			//提取出最后一个时间的记录来 存入lastRecordsList  返回
			MessageEntity mMessageEntity = lm.get(lm.size()-1);
			lastRecordsList.add(mMessageEntity);
		}
		return lastRecordsList;
	}


	/**
	 * 对allList进行分组  获得 user_id与目标用户 target_user_id是一类的聊天记录
	 * @param allList 得到该用户的所有聊天消息（发送方和接收方为user_id）
	 * @return 分好组的map
	 */
	private Map<Object, List<MessageEntity>> getMap(List<ExtraEntity> allList) {
		//返回的map
		Map<Object, List<MessageEntity>> map = new HashMap();
		
		
		//创建一个切割后的集合  此时 额外实体类中的users为 [103,43]
		List<ExtraEntity> splitList = new ArrayList<>();
		for(ExtraEntity entity : allList) {
			String mixture_id = (String) entity.getUsers();
			String[] users = mixture_id.split(",");
			entity.setUsers(users);
			splitList.add(entity);
		}
		//遍历切割后的集合  然后分组
		for (int i = 0; i < splitList.size(); i++) {
			ExtraEntity e1 = splitList.get(i);
			MessageEntity m1 = e1.getMessageEntity();
			//map中的key
			String[] users1 = (String[]) e1.getUsers();
			//说明map中还没有值
			if(map.size()==0) {
				//map中存放的list
				List<MessageEntity> mList = new ArrayList<>();
				mList.add(m1);
				map.put(users1, mList);
			}else {
				//遍历map的所有key值 看看是否包含
				//-1表示包含
				int isContailFlag = 0;
				for(Object key :map.keySet()) {
					isContailFlag++ ;
					//users2 是从map中取出来的key  如果存在 替换掉  如果不存在  key存users1
					String[] users2 = (String[]) key;
					if ((users1[0].equals(users2[0])&&users1[1].equals(users2[1]))
						||(users1[0].equals(users2[1])&&users1[1].equals(users2[0]))	) 
					{
						//说明是一组
						//将把map中的list取出来  然后将新值加入进去  在存入map中 
						List<MessageEntity> mList = map.get(key);
						mList.add(m1);
						map.put(users2, mList);
						//
						isContailFlag = -1;
						break;
					}else {
						//判断是否遍历完成
						if (isContailFlag==map.keySet().size()) {
							//说明不是一组
							//新创建一个list对象  然后将新list和新key存入map中
							//map中存放的list
							List<MessageEntity> mmList = new ArrayList<>();
							mmList.add(m1);
							map.put(users1, mmList);
						}
					}
				}
					
			}
			
		}
		return map;
	}

	/**
	 * 额外的实体类
	 * users 用来存储从数据库取出的拼接好的字符串
	 * messageEntity  消息实体类
	 * @author Administrator
	 *
	 */
	private class ExtraEntity{
		private Object users;
		private MessageEntity messageEntity;
		public Object getUsers() {
			return users;
		}
		public void setUsers(Object users) {
			this.users = users;
		}
		public MessageEntity getMessageEntity() {
			return messageEntity;
		}
		public void setMessageEntity(MessageEntity messageEntity) {
			this.messageEntity = messageEntity;
		}
		
	}

	public List<MessageEntity> getAllFriend(int user_id) {
		openCon();
		List<MessageEntity> list = null;
		try {
			ps = con.prepareStatement("select user_id friend_id,user_name,user_img,user_tel from users where user_id in (select friend_id from sco_friend where user_id=?)");
			ps.setInt(1, user_id);
			ResultSet rs = ps.executeQuery();
			if (rs!=null) {
				list = new ArrayList<>();
			}
			while(rs.next()){
				int friend_id = rs.getInt(1);
				String user_name = rs.getString(2);
				String user_img = rs.getString(3);
				if (user_img==null) {
					user_img="null";
				}
				String user_tel = rs.getString(4);
				MessageEntity messageEntity = new MessageEntity();
				messageEntity.setFriend_id(friend_id);
				messageEntity.setUser_name(user_name);
				messageEntity.setUser_img(user_img);
				messageEntity.setUser_tel(user_tel);
				list.add(messageEntity);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

}
