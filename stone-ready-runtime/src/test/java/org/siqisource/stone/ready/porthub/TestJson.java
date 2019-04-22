package org.siqisource.stone.ready.porthub;

import java.util.Map;

import org.siqisource.stone.ready.tools.porthub.ServicePort;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestJson {

	public static void main(String[] args) throws Exception {
		String json = "{\"8ef1f234-43ea-457e-a790-055d431fc039\":{\"id\":null,\"remoteIp\":\"172.254.223.83\",\"remotePort\":1980,\"hubPort\":33024,\"localPort\":0,\"hubSession\":null}}";
		ObjectMapper mapper = new ObjectMapper();
		Map<String, ServicePort> readValue = mapper.readValue(json, new TypeReference<Map<String, ServicePort>>() {
		});
		ServicePort test = readValue.get("8ef1f234-43ea-457e-a790-055d431fc039");
		System.out.println(test.getHubPort());
	}

}
