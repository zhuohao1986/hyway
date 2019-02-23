package com.way.open.pay.util.wx;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class WXConfig {
	
	private static WXConfig config = null;

	private static Properties pros = null;

	static {
		if (pros == null) {
			pros = new Properties();
		}
	}

	private WXConfig() {

	}

	public static WXConfig getInstance() {
		if (config == null) {
			config = new WXConfig();
		}
		return config;
	}

	// 实时读取
	public static String get(String key) {
		getInstance();
		try {
			String path = WXConfig.class.getClassLoader().getResource("application.properties").getPath();
			InputStream in = new BufferedInputStream(new FileInputStream(path));
			pros.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pros.getProperty(key);
	}
}
