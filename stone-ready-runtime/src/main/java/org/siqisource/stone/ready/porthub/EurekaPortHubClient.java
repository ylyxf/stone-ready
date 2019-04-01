package org.siqisource.stone.ready.porthub;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class EurekaPortHubClient extends PortHubClient implements ApplicationListener<WebServerInitializedEvent> {

	private int serverPort;

	@Value("${eureka.instance.hostname}")
	private String eurekaHost;

	@Override
	public void registryToHub(ServicePort servicePort) {
		String url = "http://" + eurekaHost + ":1979/porthub/registryToHub?";
		url += "id=" + servicePort.getId();
		url += "&remoteIp=" + servicePort.getRemoteIp();
		url += "&remotePort=" + servicePort.getRemotePort();
		url += "&hubPort=" + servicePort.getHubPort();
		doGet(url);
	}

	@Override
	public void unRegistryFromHub(ServicePort servicePort) {

	}

	@Override
	public Map<String, ServicePort> listServicePortFromHub() {
		String url = "http://" + eurekaHost + ":1979/porthub/listServicePortFromHub";
		String repo = doGet(url);
		System.out.println(repo);
		ObjectMapper mapper = new ObjectMapper();
		try {
			Map<String, ServicePort> result = mapper.readValue(repo, new TypeReference<Map<String, ServicePort>>() {
			});
			return result;
		} catch (Exception e) {
			return new HashMap<String, ServicePort>();
		}

	}

	@Override
	public void onApplicationEvent(WebServerInitializedEvent event) {
		if (StringUtils.isEmpty(this.ip) || this.port == 0 || StringUtils.isEmpty(this.account)
				|| StringUtils.isEmpty(this.password)) {
			return;
		}
		this.serverPort = event.getWebServer().getPort();
		try {
			this.connect(this.serverPort);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.mapServicePort();
	}

	private String doGet(String httpurl) {
		HttpURLConnection connection = null;
		InputStream is = null;
		BufferedReader br = null;
		String result = null;// 返回结果字符串
		try {
			// 创建远程url连接对象
			URL url = new URL(httpurl);
			// 通过远程url连接对象打开一个连接，强转成httpURLConnection类
			connection = (HttpURLConnection) url.openConnection();
			// 设置连接方式：get
			connection.setRequestMethod("GET");
			// 设置连接主机服务器的超时时间：15000毫秒
			connection.setConnectTimeout(15000);
			// 设置读取远程返回的数据时间：60000毫秒
			connection.setReadTimeout(15000);
			// 发送请求
			connection.connect();
			// 通过connection连接，获取输入流
			if (connection.getResponseCode() == 200) {
				is = connection.getInputStream();
				// 封装输入流is，并指定字符集
				br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				// 存放数据
				StringBuffer sbf = new StringBuffer();
				String temp = null;
				while ((temp = br.readLine()) != null) {
					sbf.append(temp);
				}
				result = sbf.toString();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭资源
			if (null != br) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (null != is) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			connection.disconnect();// 关闭远程连接
		}

		return result;
	}

}
