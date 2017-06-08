package com.transengen.tier4.medsupputil;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MedSuppUtilConfig {

	public static Properties props = new Properties();

	public static void loadProperties() {

		try {

			InputStream inputStream =
					MedSuppUtilConfig.class.getClassLoader().getResourceAsStream("medsupputil.properties");

			props.load(inputStream);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getString(String key) {
		return props.getProperty(key);
	}

	public static String getString(String key, String defaultValue) {
		return props.getProperty(key, defaultValue);
	}

	public static boolean getBoolean(String key) {
		return Boolean.parseBoolean(props.getProperty(key));
	}

	public static boolean getBoolean(String key, String defaultValue) {
		return Boolean.parseBoolean((props.getProperty(key, defaultValue)));
	}

	public static int getInteger(String key) {
		return Integer.parseInt(props.getProperty(key));
	}

	public static int getInteger(String key, int defaultValue) {
		String val = props.getProperty(key);
		if(val == null) return Integer.parseInt(val); 
		else return defaultValue;
		
	}

}
