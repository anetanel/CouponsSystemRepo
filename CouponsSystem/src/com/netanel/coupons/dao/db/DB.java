package com.netanel.coupons.dao.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
	public static Connection connectDB() throws ClassNotFoundException, SQLException {
		String dbDriver = "org.sqlite.JDBC";
		String url = "jdbc:sqlite:db/CouponsDB.db";
		Class.forName(dbDriver);
		Connection con = DriverManager.getConnection(url);
		return con;
	}
}
