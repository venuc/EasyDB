package org.easydb.concrete.queryhandler;

import java.lang.reflect.Field;
import java.sql.BatchUpdateException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import org.easydb.abstractions.entity.Entity;
import org.easydb.abstractions.queryhandler.InterfaceRawQueryHandler;
import org.easydb.concrete.connectionhelper.DBHandle;
import org.easydb.exception.EntityMappingException;
import org.easydb.utils.Mapping;

public class RawQueryHandler implements InterfaceRawQueryHandler {

	private DBHandle dbHandle;

	public RawQueryHandler(DBHandle dbHandle) {
		this.dbHandle = dbHandle;
	}

	public synchronized ArrayList<Entity> executeSelect(String statement, Entity entity)
			throws EntityMappingException {
		PreparedStatement stmt = null;
		// Handle to currently generated entity (which is to be added to list)
		Entity generatedEntity = null;

		Class entityClass = entity.getClass();

		HashMap<String, String> mapping = entity.mapping();
		ArrayList<Entity> entityList = new ArrayList<Entity>();

		// DB field names as specified in mapping table of entity
		Object[] dbColumns = entity.mapping().keySet().toArray();
		// mapping between dbColumns and declaredFieldsOfEntity
		Object[] mappedWithEntity = entity.mapping().values().toArray();
		// Private fields (corresponding to table) declared in entity object
		Field[] declaredFieldsOfEntity = entityClass.getDeclaredFields();

		// Check if mapping inside entity object is correct
		// TO-DO: can this check be moved to before we build a DB handle?
		if (!Mapping.checkEntityMapping(dbColumns, mappedWithEntity,
				declaredFieldsOfEntity)) {
			throw new EntityMappingException();
		}

		String dbColumn = null;
		int dbFieldCounter = 0;

		Connection con = dbHandle.getConnection();
		try {

			stmt = con.prepareStatement(statement);
			ResultSet rs = stmt.executeQuery();

			try {
				while (rs.next()) {

					dbFieldCounter = 0;
					/*
					 * 1. Create an instance of entity 
					 * 2. Get fields of the generated instance (should be same 
					 *    as the entity that was passed to executeStatement())
					 * 		1. Search for the proper field 
					 * 		2. Write data to the fields of that instance
					 */
					try {
						generatedEntity = (Entity) entityClass.newInstance();
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}

					while (dbFieldCounter < declaredFieldsOfEntity.length) {
						dbColumn = (String) dbColumns[dbFieldCounter];
						for (Field f : declaredFieldsOfEntity) {
							if (f.getName().equals(mapping.get(dbColumn))) {
								f.setAccessible(true);
								try {
									f.set(generatedEntity,
											rs.getObject(dbColumn));
								} catch (IllegalArgumentException e) {
									e.printStackTrace();
								} catch (IllegalAccessException e) {
									e.printStackTrace();
								}
							}
						}

						dbFieldCounter++;
					}

					// Here we have got one row from DB, putting the entity
					// object
					// in arraylist...
					entityList.add(generatedEntity);
				}
			} finally {
				if (rs != null) {
					rs.close();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return entityList;
	}

	public synchronized int executeUpdate(String statement) {
		PreparedStatement stmt = null;
		Connection con = dbHandle.getConnection();
		int nUpdatedRows = 0;

		try {
			stmt = con.prepareStatement(statement);
			nUpdatedRows = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return nUpdatedRows;
	}

	public synchronized int executeInsert(String statement) {
		return executeUpdate(statement);
	}

	public synchronized int[] batchUpdate(ArrayList<String> statements) {
		Statement stmt = null;
		Connection con = dbHandle.getConnection();
		boolean dbCommitMode = true;
		Savepoint beforeBatch = null;
		int[] batchCounts = { 0 };

		try {
			dbCommitMode = con.getAutoCommit();
			con.setAutoCommit(false);
			beforeBatch = con.setSavepoint();
			
			stmt = con.createStatement();
			
			for (String statement : statements) {
				stmt.addBatch(statement);
			}
			
			batchCounts = stmt.executeBatch();
			
			con.commit();
		} catch (BatchUpdateException b) {
			try {
				con.rollback(beforeBatch);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			b.printStackTrace();
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				con.setAutoCommit(dbCommitMode);
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (stmt != null) {
					try {
						stmt.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return batchCounts;
	}

	public synchronized void setAutoCommit(boolean ac) {
		try {
			this.dbHandle.getConnection().setAutoCommit(ac);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
