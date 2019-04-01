package org.siqisource.stone.ready.porthub;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public abstract class PortHubClient {

	private Map<String, SessionServicePort> mappedServicePort = new HashMap<String, SessionServicePort>();

	private SessionServicePort localServicePort = new SessionServicePort();

	@Value("${stone.ready.portHubClient.ip:}")
	protected String ip;

	@Value("${stone.ready.portHubClient.port:0}")
	protected int port;

	@Value("${stone.ready.portHubClient.account:}")
	protected String account;

	@Value("${stone.ready.portHubClient.password:}")
	protected String password;

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ServicePort connect(int localPort) throws Exception {
		// 连接
		JSch jsch = new JSch();
		Session session = jsch.getSession(account, ip, port);
		session.setPassword(password);

		session.setConfig("StrictHostKeyChecking", "no");
		session.setConfig("PreferredAuthentications", "password");
		session.setDaemonThread(true);
		session.setServerAliveInterval(3000);
		session.connect();

		int remotePort = session.setPortForwardingR("*:0:127.0.0.1:" + localPort);
		localServicePort.setId(UUID.randomUUID().toString());
		localServicePort.setHubPort(remotePort);
		InetAddress addr = InetAddress.getLocalHost();
		String ip = addr.getHostAddress().toString();
		localServicePort.setRemoteIp(ip);
		localServicePort.setRemotePort(localPort);
		localServicePort.setHubSession(session);
		registryToHub(localServicePort);
		return localServicePort;
	}

	public void mapServicePort() {
		new Thread() {
			public void run() {
				try {
					while (true) {
						Map<String, ServicePort> remoteServicePortMap = listServicePortFromHub();
						// 剔除掉远程已经没有的端口
						for (String localKey : mappedServicePort.keySet()) {
							if (!remoteServicePortMap.containsKey(localKey)) {
								SessionServicePort toRemove = mappedServicePort.get(localKey);
								toRemove.getHubSession().disconnect();
								mappedServicePort.remove(localKey);
							}
						}
						// 检查本地是不是映射过，映射过，检查监控否，没映射或者不健康则重新映射
						for (Map.Entry<String, ServicePort> entry : remoteServicePortMap.entrySet()) {
							String key = entry.getKey();
							ServicePort remoteServicePort = entry.getValue();
							SessionServicePort servicePort = new SessionServicePort();
							servicePort.setId(remoteServicePort.getId());
							servicePort.setRemoteIp(remoteServicePort.getRemoteIp());
							servicePort.setRemotePort(remoteServicePort.getRemotePort());
							servicePort.setHubPort(remoteServicePort.getHubPort());
							if (mappedServicePort.containsKey(key)) {
								continue;
							}
							// 连接
							JSch jsch = new JSch();
							Session session = jsch.getSession(account, ip, port);
							session.setPassword(password);

							session.setConfig("StrictHostKeyChecking", "no");
							session.setConfig("PreferredAuthentications", "password");
							session.setDaemonThread(true);
							session.setServerAliveInterval(3000);
							session.connect();
							int localPort = session.setPortForwardingL("*:0:127.0.0.1:" + servicePort.getHubPort());
							servicePort.setLocalPort(localPort);
							servicePort.setHubSession(session);
							mappedServicePort.put(key, servicePort);
						}
						status();
						Thread.sleep(3000);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	public void status() {
		System.out.println("--------------------------");
		for (Map.Entry<String, SessionServicePort> entry : mappedServicePort.entrySet()) {
			ServicePort servicePort = entry.getValue();
			System.out.println("127.0.0.1:" + servicePort.getLocalPort() + "=>" + this.ip + ":"
					+ servicePort.getHubPort() + "=>" + servicePort.getRemoteIp() + ":" + servicePort.getRemotePort());
		}
		System.out.println("--------------------------");
	}

	public abstract void registryToHub(ServicePort servicePort);

	public abstract void unRegistryFromHub(ServicePort servicePort);

	public abstract Map<String, ServicePort> listServicePortFromHub();

}
