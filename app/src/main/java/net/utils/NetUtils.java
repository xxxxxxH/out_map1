package net.utils;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetUtils {


	// 读取整个文件内容
	public static String getUrlContent(String urlStr) {
		String ret = null;
		try {
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			InputStream is = conn.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			
			StringBuffer buffer = new StringBuffer();
			char[] buff = new char[1024 * 4];
			int len;
			while((len = reader.read(buff)) > 0){
				buffer.append(buff, 0, len);
			}
			is.close();
			reader.close();			
			ret = buffer.toString();			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return ret;
	}


}
