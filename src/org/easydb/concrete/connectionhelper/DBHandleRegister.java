package org.easydb.concrete.connectionhelper;

import java.sql.SQLException;
import java.util.HashMap;

import org.easydb.exception.DestroyFailedException;
import org.easydb.exception.DuplicateDBHandleException;
import org.easydb.exception.InvalidHandleNameException;

public class DBHandleRegister {

	private static HashMap<String, DBHandle> register = new HashMap<String, DBHandle>();
	private static DBHandle dbHandle;

	private DBHandleRegister() {
	}

	/*
	 * createHandle should be synchronized on the Class object to prevent dirty
	 * writes in the register
	 */
	public static void createHandle(DBDetails dbDetails) {
		synchronized (DBHandleRegister.class) {
			dbHandle = new DBHandle(dbDetails);
			register(dbDetails.getConnectionName(), dbHandle);
		}
	}

	/*
	 * getHandle should be synchronized on the Class object to prevent dirty
	 * reads from the register
	 */
	public static DBHandle getHandle(String handleName) {
		synchronized (DBHandleRegister.class) {
			dbHandle = register.get(handleName);
			if (dbHandle != null)
				return dbHandle;
			else
				throw new InvalidHandleNameException();
		}
	}

	/*
	 * destroyHandle should be synchronized on the Class object to prevent dirty
	 * read / writes
	 */
	public static void destroyHandle(String handleName) {
		synchronized (DBHandleRegister.class) {
			dbHandle = register.get(handleName);
			if (dbHandle != null) {
				try {
					dbHandle.getConnection().close();
				} catch (SQLException e) {
					throw new DestroyFailedException();
				}
				register.remove(handleName);
			} else {
				throw new InvalidHandleNameException();
			}
		}
	}
	
	/*
	 * Destroy all DB handles (simple case of reentrant synchronization)
	 */
	public static void destroyAllHandles() {
		synchronized (DBHandleRegister.class) {
			if (register.size() > 0) {
				Object[] handleNames 
							= register.keySet().toArray();
				for (Object handleName : handleNames) {
					destroyHandle((String)handleName);
				}
			}
		}	
	}

	/*
	 * Count the number of open handles at a given time. No modifications
	 * allowed on register when counting.
	 */
	public static int nOpenHandles() {
		synchronized (DBHandleRegister.class) {
			return register.size();
		}
	}

	/*
	 * It's better to let the client know about the duplicate name instead of
	 * quietly overwriting a handle.
	 */
	private static void register(String handleName, DBHandle dbHandle) {
		if (!register.containsKey(handleName))
			register.put(handleName, dbHandle);
		else
			throw new DuplicateDBHandleException();
	}
}
