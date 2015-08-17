package org.easydb.abstractions.connectionhelper;

public interface InterfaceDBDetails {

	void setUserName(String userName);

	String getUserName();

	void setPassword(String password);

	String getPassword();

	void setServer(String server);

	String getServer();

	void setPort(String port);

	String getPort();

	void setService(String service);

	String getService();

	void setVendor(String vendor);

	String getVendor();

	void setConnectionName(String connectionName);

	String getConnectionName();
	
	boolean getWindowsIntegratedAuth();

	void setWindowsIntegratedAuth(boolean windowsIntegratedAuth);
}
