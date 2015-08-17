package org.easydb.exception;

public class UnknownVendorException extends EasydbUncheckedException {
	public String toString() {
		return "The specified database vendor is not yet supported.";
	}
}
