package org.easydb.exception;

public class InvalidHandleNameException extends EasydbUncheckedException {
	public String toString() {
		return "The handle name is invalid or was not found in the register";
	}
}
