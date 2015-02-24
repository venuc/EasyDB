package org.easydb.exception;

public class DestroyFailedException extends EasydbUncheckedException {
	public String toString() {
		return "Could not destroy handle to database. A handle has to be registered to destroy.";
	}
}
