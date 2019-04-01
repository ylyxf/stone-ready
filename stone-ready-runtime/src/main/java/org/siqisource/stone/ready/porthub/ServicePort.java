package org.siqisource.stone.ready.porthub;

import com.jcraft.jsch.Session;

public class ServicePort {

	private String id;

	private String remoteIp;

	private int remotePort;

	private int hubPort;

	private int localPort;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRemoteIp() {
		return remoteIp;
	}

	public void setRemoteIp(String remoteIp) {
		this.remoteIp = remoteIp;
	}

	public int getRemotePort() {
		return remotePort;
	}

	public void setRemotePort(int remotePort) {
		this.remotePort = remotePort;
	}

	public int getHubPort() {
		return hubPort;
	}

	public void setHubPort(int hubPort) {
		this.hubPort = hubPort;
	}

	public int getLocalPort() {
		return localPort;
	}

	public void setLocalPort(int localPort) {
		this.localPort = localPort;
	}

}
