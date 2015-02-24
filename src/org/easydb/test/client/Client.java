package org.easydb.test.client;

import java.util.ArrayList;

import org.easydb.abstractions.entity.Entity;
import org.easydb.abstractions.queryhandler.InterfaceRawQueryHandler;
import org.easydb.concrete.connectionhelper.DBDetails;
import org.easydb.concrete.connectionhelper.DBHandleRegister;
import org.easydb.concrete.queryhandler.RawQueryHandler;
import org.easydb.constants.Vendors;
import org.easydb.exception.EntityMappingException;

public class Client {
	public static void main(String[] args) throws EntityMappingException {
		// ********************* CONFIG *******************//
		Entity entity = new ClEntity();
		// Declare DB details
		DBDetails dbDetails = new DBDetails();
		dbDetails.setServer("localhost");
		dbDetails.setPort("3306");
		dbDetails.setService("mysql");
		dbDetails.setUserName("root");
		dbDetails.setPassword("");
		dbDetails.setVendor(Vendors.MYSQL);
		dbDetails.setConnectionName("mysql_conn");

		System.out.println("Open DB handles: "
				+ DBHandleRegister.nOpenHandles());

		// Create DB handle in the register with the given details
		DBHandleRegister.createHandle(dbDetails);

		// Configure a raw query handler with the specified handle from register
		InterfaceRawQueryHandler rawQueryHandler = new RawQueryHandler(
				DBHandleRegister.getHandle("mysql_conn"));
		
		// ********************* FETCH **********************//
		//Fetch the results into a list of entities
		ArrayList<Entity> results = rawQueryHandler.executeSelect(
				"select user, host, password from mysql.user", entity);
		
		// ******************** INSERT **********************//
		//int insert = rawQueryHandler.executeInsert("insert into v_test (empid, name)"
		//		+ " values (1, 'fname')");
		
		// ******************** UPDATE **********************//
		// rawQueryHandler.executeUpdate();
		
		// ************** BATCH INSERT / UPDATE *******************//
		// Any statement which doesn't return a resultset can go in a batch
/*		
		int counter = 0;
		ArrayList<String> batchStatements = new ArrayList<String>();
		while (counter <= 900) {

				batchStatements.add(new StringBuilder("insert into v_test (empid, name)")
					.append(" values (").append(counter).append(", 'fname lname')").toString());
			}
			counter++;
			
		}

		batchStatements.add(new StringBuilder("update mysql.v_test set name = 'fname' where name='abc'").toString());
		batchStatements.add(new StringBuilder("update mysql.v_test set name = 'fname' where empid=2").toString());
		batchStatements.add(new StringBuilder("update mysql.v_test set name = 'cha' where empid=3").toString());
		
		int[] batchResults = rawQueryHandler.batchUpdate(batchStatements);
*/		
		// ******************** DISPLAY *********************//

		for (Entity r : results) {
			ClEntity cle = (ClEntity) r;
			System.out.println("User: " + cle.getUser());
			System.out.println("Host: " + cle.getHost());
			System.out.println("passwd: " + cle.getPassword());
		}

/*
		for (int batchResult : batchResults) {
			System.out.println(batchResult);
		}
*/
		DBHandleRegister.destroyAllHandles();
		System.out.println("done");
	}
}