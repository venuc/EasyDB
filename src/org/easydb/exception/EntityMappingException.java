package org.easydb.exception;

public class EntityMappingException extends EasydbUncheckedException {
	public String toString() {
		return "Incorrect mapping found in entity class";
	}
}
