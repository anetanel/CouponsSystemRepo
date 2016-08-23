package com.netanel.coupons.dao.db;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.netanel.coupons.exception.DaoSQLException;

/**
 * A collection of static functions for DB handling.
 * Includes Connection Pool creation. 
 */
public class DB {
	//
	// Arguments
	//
	private static ComboPooledDataSource cpds = null;
	
	//
	// Functions
	//
	
	// Start connection pool
	private static void startPool() {
		cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass( "org.sqlite.JDBC" );
			cpds.setJdbcUrl( "jdbc:sqlite:db/CouponsDB.db" );
			cpds.setMaxStatements( 180 ); 
		} catch (PropertyVetoException e) {
			System.out.println("ERROR: Connection Pool error:");
			System.out.println(e.getMessage());
		}
	}
	
	
	/**
	 * Get connection from pool
	 * @return a {@code Connection} object.
	 * @throws SQLException
	 */
	static Connection getConnection() throws SQLException {
		if (cpds == null) {
			startPool();
		}
		return cpds.getConnection();
	}
	
	/**
	 * Update join table.
	 * @param sqlCmd a {@code SqlCmd} Enum of the desired command.
	 * @param joinTable a {@code Tables} Enum of the table to be modified. Must be a Join table.
	 * @param companyOrCustomerID a {@code long} Company or Customer ID value.
	 * @param couponID a {@code long} coupon ID value.
	 * @throws DaoSQLException
	 */
	static void updateJoin(SqlCmd sqlCmd, Tables joinTable, long companyOrCustomerID, long couponID) throws DaoSQLException {
		String sqlCmdStr = "";
		String column1;
		// Set column name according to the Join table given.
		if (joinTable.equals(Tables.Company_Coupon)) {
			column1 = "COMP_ID";
		} else if (joinTable.equals(Tables.Customer_Coupon)){
			column1 = "CUST_ID";
		} else {
			throw new DaoSQLException("Table '" + joinTable + "' is not a Join table!");
		}
		
		// SQL command:
		switch (sqlCmd) {
			case INSERT:
				sqlCmdStr = "INSERT INTO " + joinTable + " VALUES(?,?)";
				break;
			case DELETE:
				sqlCmdStr = "DELETE FROM " + joinTable + " WHERE "+ column1 + "=? AND COUPON_ID=?";
				break;
		}
		
		PreparedStatement stat;
		try (Connection con = getConnection()){
			stat = con.prepareStatement (sqlCmdStr);
			stat.setLong(1, companyOrCustomerID);
			stat.setLong(2, couponID);
			stat.executeUpdate();
		} catch (SQLException e) {
			throw new DaoSQLException(e.getMessage());
		}		
	}
	
	/**
	 * Checks if two given values are in the database. The SQL query will take the form of: <p>
	 * {@code "SELECT * FROM table WHERE columnName1=queryValue1 AND columnName2=queryValue2"}
	 * @param table a {@code Table} Enum of the table to be queried.
	 * @param columnName1 a {@code Columns} Enum of the 1st column to be queried.
	 * @param columnName2 a {@code Columns} Enum of the 2nd column to be queried.
	 * @param queryValue1 a {@code String} value of the 1st query.
	 * @param queryValue2 a {@code String} value of the 2nd query.
	 * @return {@code true} if both values are found in the database, otherwise returns {@code false}.
	 * @throws DaoSQLException
	 */
	static boolean foundInDb(Tables table, Columns columnName1, Columns columnName2, String queryValue1, String queryValue2) throws DaoSQLException {
		String sqlCmdStr = "SELECT * FROM " + table + " WHERE " + columnName1 + "=? AND " + columnName2 + "=?";
		PreparedStatement stat;
		boolean foundBool = false;
		try (Connection con = getConnection()){
			stat = con.prepareStatement (sqlCmdStr);
			stat.setString(1, queryValue1);
			stat.setString(2, queryValue2);
			ResultSet rs = stat.executeQuery();
			foundBool = rs.next();
		} catch (SQLException e) {
			throw new DaoSQLException(e.getMessage());
		}
		return foundBool;
	}
	
	/**
	 * Checks if a values is in the database. The SQL query will take the form of:<p>
	 * {@code "SELECT * FROM table WHERE columnName=queryValue"} 
	 * @param table a {@code Table} Enum of the table to be queried.
	 * @param columnName a {@code Columns} Enum of the column to be queried.
	 * @param queryValue a {@code String} value of the query.
	 * @return {@code true} if value is found in the database, otherwise returns {@code false}.
	 * @throws DaoSQLException
	 */
	public static boolean foundInDb(Tables table, Columns columnName, String queryValue) throws DaoSQLException {
		// Actually, overloading with the same arguments twice, in order to use the same foundInDb function.
		return foundInDb(table, columnName, columnName, queryValue, queryValue);	
	}
}
