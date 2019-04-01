package org.siqisource.stone.ready.porthub;

public class TestClient {

	public static void main(String[] args) throws Exception {
		PortHubClient portHubClient = new MemoryPortHubClient();
		portHubClient.setIp("192.168.161.128");
		portHubClient.setPort(22);
		portHubClient.setAccount("root");
		portHubClient.setPassword("root");
		portHubClient.mapServicePort();
		portHubClient.connect(8081);
	}

}
