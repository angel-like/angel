package com.xmsy.server.zxyy.manager.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.baomidou.mybatisplus.toolkit.StringUtils;

import cn.hutool.json.JSONException;
import cn.hutool.json.JSONObject;

/**
 * ip工具类
 * 
 * @author Administrator
 *
 */
public class IpUtil {
	// 获取用户ip
	public static String getIPAddress(HttpServletRequest request) {
		String ip = null;

		// X-Forwarded-For：Squid 服务代理
		String ipAddresses = request.getHeader("X-Forwarded-For");

		if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
			// Proxy-Client-IP：apache 服务代理
			ipAddresses = request.getHeader("Proxy-Client-IP");
		}

		if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
			// WL-Proxy-Client-IP：weblogic 服务代理
			ipAddresses = request.getHeader("WL-Proxy-Client-IP");
		}

		if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
			// HTTP_CLIENT_IP：有些代理服务器
			ipAddresses = request.getHeader("HTTP_CLIENT_IP");
		}

		if (ipAddresses == null || ipAddresses.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
			// X-Real-IP：nginx服务代理
			ipAddresses = request.getHeader("X-Real-IP");
		}

		// 有些网络通过多层代理，那么获取到的ip就会有多个，一般都是通过逗号（,）分割开来，并且第一个ip为客户端的真实IP
		if (ipAddresses != null && ipAddresses.length() != 0) {
			ip = ipAddresses.split(",")[0];
		}

		// 还是不能获取到，最后再通过request.getRemoteAddr();获取
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ipAddresses)) {
			ip = request.getRemoteAddr();
			if ("0:0:0:0:0:0:0:1".equals(ip)) {
				InetAddress address = null;
				try {
					address = InetAddress.getLocalHost();
					ip = address.getHostAddress();
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		if (!StringUtils.isEmpty(ip)) {
			return ip.split(",")[0];
		}
		return ip;
	}

	/**
	 * 读取所有内容
	 * 
	 * @param rd
	 * @return
	 * @throws IOException
	 */
	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	/**
	 * 拉取网页所有内容，并转化为Json格式
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 * @throws JSONException
	 */
	public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
		InputStream is = new URL(url).openStream();
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			String jsonText = readAll(rd);
			JSONObject json = new JSONObject(jsonText);
			return json;
		} finally {
			is.close();
		}
	}

	// 获取ip地址
	@SuppressWarnings("null")
	public static JSONObject getAddress() {
		String ip = "211.149.165.188";
		// 改用了太平洋的一个网址
		String chinaz = "http://whois.pconline.com.cn/";

		StringBuilder inputLine = new StringBuilder();
		String read = "";
		URL url = null;
		HttpURLConnection urlConnection = null;
		BufferedReader in = null;
		try {
			url = new URL(chinaz);
			urlConnection = (HttpURLConnection) url.openConnection();
			// 如有乱码的，请修改相应的编码集，这里是 gbk
			in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "gbk"));
			while ((read = in.readLine()) != null) {
				inputLine.append(read + "\r\n");
			}
			// System.out.println(inputLine.toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		// 通过正则表达式匹配我们想要的内容，根据拉取的网页内容不同，正则表达式作相应的改变
		Pattern p = Pattern.compile("显示IP地址为(.*?)的位置信息");
		Matcher m = p.matcher(inputLine.toString());
		if (m.find()) {
			String ipstr = m.group(0);
			// 这里根据具体情况，来截取想要的内容
			ip = ipstr.substring(ipstr.indexOf("为") + 2, ipstr.indexOf("的") - 1);
		}
		JSONObject obj = null;
		JSONObject json = null;

		String province = null;
		String city = null;

		try {
			// 这里调用百度的ip定位api服务 详见 http://api.map.baidu.com/lbsapi/cloud/ip-location-api.htm
			json = readJsonFromUrl("http://api.map.baidu.com/location/ip?ak=F454f8a5efe5e577997931cc01de3974&ip=" + ip);
			city = (((JSONObject) ((JSONObject) json.get("content")).get("address_detail")).get("city")).toString();
			province = (((JSONObject) ((JSONObject) json.get("content")).get("address_detail")).get("province"))
					.toString();
			obj.put("city", city);
			obj.put("province", province);
			obj.put("ip", ip);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}

	public static void main(String[] args) {
		// JSONObject obj=getAddress();
		System.out.println("1111");
	}

}
