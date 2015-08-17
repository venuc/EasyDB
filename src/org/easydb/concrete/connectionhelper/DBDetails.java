package org.easydb.concrete.connectionhelper;

import org.easydb.abstractions.connectionhelper.InterfaceDBDetails;
import org.easydb.constants.Vendors;
import org.easydb.exception.UnknownVendorException;


public class DBDetails implements InterfaceDBDetails {

	String userName;
	String password;
	String server;
	String port;
	String service;
	String vendor;
	String connectionName;
	boolean windowsIntegratedAuth = false;

	public boolean getWindowsIntegratedAuth() {
		return windowsIntegratedAuth;
	}

	public void setWindowsIntegratedAuth(boolean windowsIntegratedAuth) {
		this.windowsIntegratedAuth = windowsIntegratedAuth;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return this.password;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getServer() {
		return this.server;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getPort() {
		return this.port;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getService() {
		return this.service;
	}

	public void setVendor(String vendor) {
		// Add all supported vendors here
		if ((vendor.equalsIgnoreCase(Vendors.MYSQL))
				|| (vendor.equalsIgnoreCase(Vendors.ORACLE))
				|| (vendor.equalsIgnoreCase(Vendors.MSSQL))) {
			this.vendor = vendor;
		} else {
			throw new UnknownVendorException();
		}
	}

	public String getVendor() {
		return this.vendor;
	}

	public void setConnectionName(String connectionName) {
		this.connectionName = connectionName;
	}

	public String getConnectionName() {
		return this.connectionName;
	}
}
