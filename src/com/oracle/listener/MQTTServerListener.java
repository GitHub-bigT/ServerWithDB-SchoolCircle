package com.oracle.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.asiainfo.push.MqttBroker;
import com.ibm.mqtt.MqttException;

public class MQTTServerListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
//		// TODO Auto-generated method stub
		try {
			MqttBroker.getInstance().connect();
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
