package com.oracle.entity;

public class ChatRecordsEntity {
	private int message_id;
	private int user_id;
	private int target_user_id;
	private String message_content;
	private String message_date;
	private String voice_time;
	public String getVoice_time() {
		return voice_time;
	}
	public void setVoice_time(String voice_time) {
		this.voice_time = voice_time;
	}
	public int getMessage_id() {
		return message_id;
	}
	public void setMessage_id(int message_id) {
		this.message_id = message_id;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getTarget_user_id() {
		return target_user_id;
	}
	public void setTarget_user_id(int target_user_id) {
		this.target_user_id = target_user_id;
	}
	public String getMessage_content() {
		return message_content;
	}
	public void setMessage_content(String message_content) {
		this.message_content = message_content;
	}
	public String getMessage_date() {
		return message_date;
	}
	public void setMessage_date(String message_date) {
		this.message_date = message_date;
	}
}
