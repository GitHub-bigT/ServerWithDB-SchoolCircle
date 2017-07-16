package com.asiainfo.push;


import java.util.Timer;
import java.util.TimerTask;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibm.mqtt.MqttClient;
import com.ibm.mqtt.MqttException;
import com.ibm.mqtt.MqttSimpleCallback;

/**
 * MQTT消息发送与接收
 * @author Join
 *
 */
public class MqttBroker {
	private final static Log logger = LogFactory.getLog(MqttBroker.class);// ��־����
	
	private final static String CONNECTION_STRING = "tcp://192.168.5.100:1883";
//	private final static String CONNECTION_STRING = "tcp://172.20.10.7:1883";
//	private final static String CONNECTION_STRING = "tcp://172.28.79.18:1883";
//	private final static String CONNECTION_STRING = "tcp://192.168.1.101:1883";
	private final static boolean CLEAN_START = true;//true:不记忆连接中的任何状态
	private final static short KEEP_ALIVE = 1;//设置心跳1s  
	private final static String CLIENT_ID = "master1";//客户端标识 
	private final static int[] QOS_VALUES = { 0, 0, 0, 2 };// 对应主题的消息级别
	private final static String[] TOPICS = { "Test/TestTopics/Topic1",
			"Test/TestTopics/Topic2", "Test/TestTopics/Topic3",
			"tokudu/sly1"};
	private static MqttBroker instance = new MqttBroker();
	/**
	 * 返回实例对象
	 * @return
	 */
	public static MqttBroker getInstance() {
		return instance;
	}
	private MqttClient mqttClient;
	/**
	 * 重新连接服务
	 */
	public void connect() throws MqttException {
		logger.info("connect to mqtt broker.");
		mqttClient = new MqttClient(CONNECTION_STRING);
		logger.info("***********register Simple Handler***********");
		SimpleCallbackHandler simpleCallbackHandler = new SimpleCallbackHandler();
		mqttClient.registerSimpleHandler(simpleCallbackHandler);// 注册接收消息方法
		mqttClient.connect(CLIENT_ID, CLEAN_START, KEEP_ALIVE);
		logger.info("***********subscribe receiver topics***********");
		mqttClient.subscribe(TOPICS, QOS_VALUES);// 订阅接主题
		logger.info("***********CLIENT_ID:" + CLIENT_ID);
		/**
		 * 完成订阅后，可以增加心跳，保持网络通畅，也可以发布自己的消息
		 */
		mqttClient.publish("keepalive", "keepalive".getBytes(), QOS_VALUES[3],
				true);// 增加心跳，保持网络通畅
		
	}

	/**
	 * 发送消息
	 * 
	 * @param clientId
	 * @param messageId
	 */
	public void sendMessage(String clientId, String message) {
		try {
//			if (mqttClient == null || !mqttClient.isConnected()) {
//				connect();
//			}

			logger.info("send message to " + clientId + ", message is "
					+ message);
			// 发布自己的消息
			mqttClient.publish("tokudu/"+clientId, message.getBytes(),
					0, false);
			
		} catch (MqttException e) {
			logger.error(e.getCause());
			e.printStackTrace();
		}
	}
	

	/**
	 * 简单回调函数，处理server接收到的主题消息
	 * 
	 * @author Join
	 * 
	 */
	class SimpleCallbackHandler implements MqttSimpleCallback {

		/**
		 * 当客户机和broker意外断开时触发 可以再此处理重新订阅
		 */
		@Override
		public void connectionLost() throws Exception {
			// TODO Auto-generated method stub
			System.out.println("客户机和broker已经断开");
		}

		/**
		 * 客户端订阅消息后，该方法负责回调接收处理消息
		 */
		@Override
		public void publishArrived(String topicName, byte[] payload, int Qos,
				boolean retained) throws Exception {
			// TODO Auto-generated method stub
			System.out.println("订阅主题: " + topicName);
			System.out.println("消息数据: " + new String(payload));
			System.out.println("消息级别(0,1,2): " + Qos);
			System.out.println("是否是实时发送的消息(false=实时，true=服务器上保留的最后消息): ");
		}

	}

	public static void main(String[] args) {
		MqttBroker mqttBroker = new MqttBroker();
		try {
			mqttBroker.connect();
			JSONObject json=new JSONObject();
			json.put("type", "phone");
			json.put("message", "已YY人抢购，数量有限，速来！");
			mqttBroker.sendMessage("sly12", json.toString());
		} catch (MqttException e) {
			e.printStackTrace();
		}
		
		/*Timer timer = new Timer();
		TestTimerTask task = new TestTimerTask();
        task.setMqttBroker(mqttBroker);
        timer.schedule(task, 500L, 20000L);*/
	}
}
