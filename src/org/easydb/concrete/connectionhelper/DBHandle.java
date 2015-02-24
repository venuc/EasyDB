package org.easydb.concrete.connectionhelper;

import java.sql.Connection;
import java.sql.DriverManager;

import org.easydb.abstractions.connectionhelper.InterfaceDBDetails;
import org.easydb.abstractions.connectionhelper.InterfaceDBHandle;
import org.easydb.constants.Seperators;
import org.easydb.constants.Vendors;
import org.easydb.exception.UnknownVendorException;

public class DBHandle implements InterfaceDBHandle {

	private Connection connection;
	private StringBuilder connectionString;
	private InterfaceDBDetails dbDetails;

	// build a handle and register it with DBHandleRegister
	public DBHandle(InterfaceDBDetails dbDetails) {
		this.dbDetails = dbDetails;
		try {
			if (Vendors.MYSQL.equalsIgnoreCase(dbDetails.getVendor())) {
				Class.forName("com.mysql.jdbc.Driver");

				connectionString = new StringBuilder("jdbc:mysql://")
						.append(this.dbDetails.getServer())
						.append(Seperators.COLON)
						.append(this.dbDetails.getPort())
						.append(Seperators.FSLASH)
						.append(this.dbDetails.getService());

			} else if (Vendors.ORACLE.equalsIgnoreCase(this.dbDetails
					.getVendor())) {
				Class.forName("oracle.jdbc.driver.OracleDriver");

				connectionString = new StringBuilder("jdbc:oracle:thin:@")
						.append(this.dbDetails.getServer())
						.append(Seperators.COLON)
						.append(this.dbDetails.getPort())
						.append(Seperators.FSLASH)
						.append(this.dbDetails.getService());
		
				// Handle other vendors here
			} else {
				throw new UnknownVendorException();
			}
			
			connection = DriverManager.getConnection(
					connectionString.toString(),
					this.dbDetails.getUserName(),
					this.dbDetails.getPassword());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection() {
		return this.connection;
	}
}
