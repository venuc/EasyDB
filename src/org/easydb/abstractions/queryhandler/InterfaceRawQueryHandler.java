package org.easydb.abstractions.queryhandler;

import java.util.ArrayList;

import org.easydb.abstractions.entity.Entity;
import org.easydb.exception.EntityMappingException;

public interface InterfaceRawQueryHandler {
	
	<T extends Entity> ArrayList<T> executeSelect(String statement, T row)
			throws EntityMappingException;
	
	int executeUpdate(String statement);
	
	int executeInsert(String statement);
	
	int[] batchUpdate(ArrayList<String> statements);
	
	void setAutoCommit(boolean ac);
}
