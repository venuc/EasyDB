package org.easydb.abstractions.compositions;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// Couldn't come up with a better name

public class JavaSqlComponents {
	private ResultSet resultSet;
	private PreparedStatement preparedStatement;
	
	public ResultSet getResultSet() {
		return resultSet;
	}
	
	public void setResultSet(ResultSet resultSet) {
		this.resultSet = resultSet;
	}
	
	public PreparedStatement getPreparedStatement() {
		return preparedStatement;
	}
	
	public void setPreparedStatement(PreparedStatement preparedStatement) {
		this.preparedStatement = preparedStatement;
	}
	
	public void closeComponents() {
		
		if (this.resultSet != null) {
			try {
				this.resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (this.preparedStatement != null) {
					try {
						this.preparedStatement.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
