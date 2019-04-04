package com.way.im.client;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.way.im.client.bean.TranObject;
import com.way.im.client.network.NetConnect;
import com.way.im.client.network.NetService;

public class UserNet {
	
	private static Logger logger=LoggerFactory.getLogger(NetConnect.class);
	
	private static NetService mNetService = NetService.getInstance();
	
	
	public static void main(String[] args) throws IOException {
		mNetService.closeConnection();
		//mNetService.onInit();
		mNetService.setupConnection();
		logger.debug("network", "set up connection");
		if (!mNetService.isConnected()) {
			return;
		}
		TranObject t=new TranObject();
		t.setSendId(1);
		mNetService.send(t);
	}

}
