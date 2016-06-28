package com.netanel.coupons.dao.db;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.DriverManager;
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
	
	public static Connection getConnection() {
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		Connection con = null;
		try {
			cpds.setDriverClass( "org.sqlite.JDBC" );
			cpds.setJdbcUrl( "jdbc:sqlite:db/CouponsDB.db" );
			con = cpds.getConnection();
		} catch (PropertyVetoException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return con;
	}
	
	public static void updateJoin(String joinDb, long id1, long id2) {
		String sqlCmdStr = "INSERT INTO " + joinDb + " VALUES(?,?)";
		PreparedStatement stat;
		try (Connection con = getConnection()){
			stat = con.prepareStatement (sqlCmdStr);
			stat.setLong(1, id1);
			stat.setLong(2, id2);
			stat.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	public static boolean foundInDb(String table, String columnName, String queryValue) {
		String sqlCmdStr = "SELECT * FROM " + table + " WHERE " + columnName + "=?";
		PreparedStatement stat;
		boolean foundBool = false;
		try (Connection con = getConnection()){
			stat = con.prepareStatement (sqlCmdStr);
			stat.setString(1, queryValue);
			ResultSet rs = stat.executeQuery();
			foundBool = rs.next();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return foundBool;
	}
	
	
}
