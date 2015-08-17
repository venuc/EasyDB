package org.easydb.exception;

public class DuplicateDBHandleException extends EasydbUncheckedException {
	public String toString() {
		return "A database handle with the same name is already registered.";
	}
}
