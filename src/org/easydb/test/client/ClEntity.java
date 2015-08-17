package org.easydb.test.client;

import java.util.HashMap;

import org.easydb.abstractions.entity.Entity;


public class ClEntity implements Entity {

	private String folName;
	private String mappedTo;

	public HashMap<String, String> mapping() {
		HashMap<String, String> mapping = new HashMap<String, String>();
		mapping.put("abc", "folName");
		mapping.put("MAPPED_TO", "mappedTo");
		return mapping;
	}

	public String getFolName() {
		return folName;
	}

	public void setFolName(String folName) {
		this.folName = folName;
	}

	public String getMappedTo() {
		return mappedTo;
	}

	public void setMappedTo(String mappedTo) {
		this.mappedTo = mappedTo;
	}
}
