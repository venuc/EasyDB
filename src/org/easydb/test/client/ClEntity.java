package org.easydb.test.client;

import java.util.HashMap;

import org.easydb.abstractions.entity.Entity;

public class ClEntity implements Entity {

	private String host;
	private String userName;
	private String password;

	public HashMap<String, String> mapping() {
		HashMap<String, String> mapping = new HashMap<String, String>();
		mapping.put("USER", "userName");
		mapping.put("PASSWORD", "password");
		mapping.put("HOST", "host");
		return mapping;
	}

	public String getUser() {
		return userName;
	}

	public void setUser(String user) {
		this.userName = user;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
