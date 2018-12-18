package com.risepu.ftk.utils;

import java.io.IOException;
import java.util.Properties;

public class ConfigUtil {
	private static Properties prop = new Properties();
	
	static {
		try {
			prop.load(ConfigUtil.class.getClassLoader().getResourceAsStream("config.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String getValue(String key) {
		return prop.getProperty(key);
	}
}
