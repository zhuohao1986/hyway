package com.way.im.client.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.net.Socket;

import com.way.im.client.bean.TranObject;

public class ClientListenThread extends Thread {
	private Socket mSocket = null;
	private ObjectInputStream mOis;

	private boolean isStart = true;

	public ClientListenThread(Socket socket) {
		this.mSocket = socket;
		try {
			mOis = new ObjectInputStream(mSocket.getInputStream());
		} catch (StreamCorruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setSocket(Socket socket) {
		this.mSocket = socket;
	}

	@Override
	public void run() {
		try {
			isStart = true;
			while (isStart) {
				TranObject mReceived = null;
				System.out.println("开始接受服务器");
				mReceived = (TranObject) mOis.readObject();
				System.out.println("接受成功");
				System.out.println(mReceived.getTranType());
				switch (mReceived.getTranType()) {
				case REGISTER_ACCOUNT:
					
					 System.out.println("注册账号成功");
					break;
				case REGISTER:
					
					break;
				case LOGIN:
					
					break;
				case SEARCH_FRIEND:
					System.out.println("收到朋友查找结果");
					
					break;
				case FRIEND_REQUEST:
					
					break;
				case MESSAGE:
					
					break;
				default:
					break;
				}
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	public void close() {
		isStart = false;
	}
}
