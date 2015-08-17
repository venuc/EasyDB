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
		dbDetails.setServer("35.54.118.44\\SSCE_MAIN1_TEST2");
		dbDetails.setPort("1433");
		dbDetails.setService("Staging - Complaints");
		dbDetails.setUserName("RBB_CALC_ENDUSER");
		dbDetails.setPassword("Endu3er123");
		dbDetails.setVendor(Vendors.MSSQL);
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
				"select fol_name as ABC, mapped_to from folders", entity);
		
		// ******************** INSERT **********************//
		//int insert = rawQueryHandler.executeInsert("insert into v_test (empid, name)"
		//		+ " values (1, 'venu')");
		
		// ************** BATCH INSERT / UPDATE *******************//
		
		int counter = 0;
		ArrayList<String> batchStatements = new ArrayList<String>();
/*
		while (counter <= 9) {
			if (counter == 800) {
				batchStatements.add(new StringBuilder("insert into v_test (empid, name)")
				.append(" values (").append("800").append(", 'venuchv@gmail.com')").toString());
			} else {
				batchStatements.add(new StringBuilder("insert into v_test (empid, name)")
					.append(" values (").append(counter).append(", 'venuchv@gmail.com')").toString());
			}
			counter++;
			
		}
*/
		batchStatements.add(new StringBuilder("insert into folders (fol_name) values ('fol4')").toString());
		batchStatements.add(new StringBuilder("insert into folders (fol_name) values ('fol10')").toString());
		batchStatements.add(new StringBuilder("insert into folders (fol_name) values ('fol6')").toString());
		
		int[] batchResults = rawQueryHandler.batchUpdateForce(batchStatements);
	
		// ******************** DISPLAY *********************//

		for (Entity r : results) {
			ClEntity cle = (ClEntity) r;
			System.out.println("folder: " + cle.getFolName());
			System.out.println("mapped to: " + cle.getMappedTo());
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