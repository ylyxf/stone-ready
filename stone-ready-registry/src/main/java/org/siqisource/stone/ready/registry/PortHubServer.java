package org.siqisource.stone.ready.registry;

import java.util.HashMap;
import java.util.Map;

import org.siqisource.stone.ready.porthub.ServicePort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PortHubServer {

	private Map<String, ServicePort> servicePortMap = new HashMap<String, ServicePort>();

	@RequestMapping("/porthub/registryToHub")
	@ResponseBody
	public String registryToHub(ServicePort servicePort) {
		ServicePort tmpServicePort = new ServicePort();
		tmpServicePort.setHubPort(servicePort.getHubPort());
		tmpServicePort.setRemoteIp(servicePort.getRemoteIp());
		tmpServicePort.setRemotePort(servicePort.getRemotePort());
		String id = servicePort.getId();
		servicePortMap.put(id, tmpServicePort);
		return "true";
	}

	@RequestMapping("/porthub/unRegistryFromHub")
	@ResponseBody
	public String unRegistryFromHub(ServicePort servicePort) {
		String id = servicePort.getId();
		servicePortMap.remove(id);
		return "true";
	}

	@RequestMapping("/porthub/listServicePortFromHub")
	@ResponseBody
	public Map<String, ServicePort> listServicePortFromHub() {
		return servicePortMap;
	}
	
	//TODO 启动一个线程，定时检查servicePortMap中的127.0.0.1:hubPort端口还通不通，不通的话，剔除掉

}
