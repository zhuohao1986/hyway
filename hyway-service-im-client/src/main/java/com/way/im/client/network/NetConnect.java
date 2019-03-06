package com.way.im.client.network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NetConnect {
	
	private Logger logger=LoggerFactory.getLogger(NetConnect.class);

	private Socket mClientSocket = null;
	private static final String SERVER_IP = "62.234.110.157";
	private static final int SERVER_PORT = 8399;
	private boolean mIsConnected = false;

	public NetConnect() {
	}

	public void startConnect() {
		try {
			mClientSocket = new Socket();
			mClientSocket.connect(
					new InetSocketAddress(SERVER_IP, SERVER_PORT), 3000);
			logger.debug("Network", "服务器连接成功");
			if (mClientSocket.isConnected()) {
				mIsConnected = true;
			} else {
				mIsConnected = false;
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
			logger.debug("Network", "服务器地址无法解析");
		} catch (IOException e) {
			e.printStackTrace();
			logger.debug("Network", "Socket io异常");
		}
	}

	public boolean getIsConnected() {
		return mIsConnected;
	}

	public Socket getSocket() {
		return mClientSocket;
	}

}
