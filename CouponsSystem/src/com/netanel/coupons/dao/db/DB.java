package com.netanel.coupons.dao.db;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
	
	public static Connection connectDB() throws SQLException, PropertyVetoException {
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		cpds.setDriverClass( "org.sqlite.JDBC" );
		cpds.setJdbcUrl( "jdbc:sqlite:db/CouponsDB.db" );
		return cpds.getConnection();
	}
	
	public static void updateJoin(String joinDb, long id1, long id2) throws ClassNotFoundException, SQLException, PropertyVetoException {
		Connection con = connectDB();
		String sqlCmdStr = "INSERT INTO " + joinDb + " VALUES(?,?)";
		PreparedStatement stat = con.prepareStatement (sqlCmdStr);
		stat.setLong(1, id1);
		stat.setLong(2, id2);
		stat.executeUpdate();
	}
	
	
}
