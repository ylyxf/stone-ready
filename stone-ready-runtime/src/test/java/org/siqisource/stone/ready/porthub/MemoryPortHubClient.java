package org.siqisource.stone.ready.porthub;

import java.util.HashMap;
import java.util.Map;

import org.siqisource.stone.ready.tools.porthub.PortHubClient;
import org.siqisource.stone.ready.tools.porthub.ServicePort;

public class MemoryPortHubClient extends PortHubClient {

	private Map<String, ServicePort> servicePortMap = new HashMap<String, ServicePort>();

	@Override
	public void registryToHub(ServicePort servicePort) {
		ServicePort tmpServicePort = new ServicePort();
		tmpServicePort.setHubPort(servicePort.getHubPort());
		tmpServicePort.setRemoteIp(servicePort.getRemoteIp());
		tmpServicePort.setRemotePort(servicePort.getRemotePort());
		String key = servicePort.getRemoteIp() + ":" + servicePort.getRemotePort();
		servicePortMap.put(key, tmpServicePort);
	}

	@Override
	public void unRegistryFromHub(ServicePort servicePort) {
		String key = servicePort.getRemoteIp() + ":" + servicePort.getRemotePort();
		servicePortMap.remove(key);
	}

	@Override
	public Map<String, ServicePort> listServicePortFromHub() {
		return servicePortMap;
	}

}
