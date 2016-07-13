package com.netanel.coupons.dao.db;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DB {
	//TODO: Change function access modifier to package  
//	public static Connection connectDB() throws ClassNotFoundException, SQLException {
//		String dbDriver = "org.sqlite.JDBC";
//		String url = "jdbc:sqlite:db/CouponsDB.db";
//		Class.forName(dbDriver);
//		Connection con = DriverManager.getConnection(url);
//		return con;
//	}
	
	private static ComboPooledDataSource cpds = null;
	
	public static void startPool() {
		cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass( "org.sqlite.JDBC" );
			cpds.setJdbcUrl( "jdbc:sqlite:db/CouponsDB.db" );
			cpds.setMaxStatements( 180 ); 
		} catch (PropertyVetoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() throws SQLException {
		if (cpds == null) {
			startPool();
		}
		return cpds.getConnection();
	}
	
	public static void updateJoin(SqlCmd sqlCmd, Tables joinDb, long companyOrCustomerID, long couponID) {
		String sqlCmdStr = "";
		String column1;
		if (joinDb.equals(Tables.Company_Coupon)) {
			column1 = "COMP_ID";
		} else {
			column1 = "CUST_ID";
		}
		switch (sqlCmd) {
			case INSERT:
				sqlCmdStr = "INSERT INTO " + joinDb + " VALUES(?,?)";
				break;
			case DELETE:
				sqlCmdStr = "DELETE FROM " + joinDb + " WHERE "+ column1 + "=? AND COUPON_ID=?";
				break;
			
		}
		
		PreparedStatement stat;
		try (Connection con = getConnection()){
			stat = con.prepareStatement (sqlCmdStr);
			stat.setLong(1, companyOrCustomerID);
			stat.setLong(2, couponID);
			stat.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public static boolean foundInDb(Tables table, Columns columnName1, Columns columnName2, String queryValue1, String queryValue2) {
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return foundBool;
	}
	
	public static boolean foundInDb(Tables table, Columns columnName, String queryValue) {
		// Overloading with the same arguments
		return foundInDb(table, columnName, columnName, queryValue, queryValue);	
	}
	
}
