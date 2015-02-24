package org.easydb.exception;

public class EntityMappingException extends EasydbCheckedException {
	public String toString() {
		return "Incorrect mapping found in entity class";
	}
}
