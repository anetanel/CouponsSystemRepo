package com.netanel.coupons.app;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.netanel.coupons.dao.db.CompanyDbDAO;
import com.netanel.coupons.dao.db.DB;
import com.netanel.coupons.jbeans.Company;
import com.netanel.coupons.jbeans.Coupon;
import com.netanel.coupons.jbeans.CouponType;

public class Test {
	public static void main(String[] args) throws Exception {
				
		DB.connectDB().createStatement().executeUpdate("DELETE FROM Company");
		DB.connectDB().createStatement().executeUpdate("UPDATE sqlite_sequence set seq=0");
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		Company a = new Company("CompA", "1234", "compa@compa.com");
		Company b = new Company("CompB", "1234", "compb@compb.com");
		Company c = new Company("CompC", "1234", "compc@compc.com");
		Company d = new Company("CompD", "1234", "compd@compd.com");
		
//		Coupon c1 = new Coupon("Coupon1",new Date(),sdf.parse("29.7.2018"), 10, CouponType.CARS, "Car Coupon", 19.90, "files/cars.jpg");
//		Coupon c2 = new Coupon("Coupon2",new Date(),sdf.parse("15.05.2017"), 10, CouponType.ELECTRONICS, "Electronics Coupon", 89.90, "files/electronics.jpg");
//		Coupon c3 = new Coupon("Coupon3",new Date(),sdf.parse("32.7.2018"), 10, CouponType.FOOD, "Food Coupon", 12.90, "files/food.jpg");
		
//		System.out.println(a);
//		System.out.println(b);
//		System.out.println(c);
//		System.out.println(c1);
//		System.out.println(c2);
//		System.out.println(c3);
		
		CompanyDbDAO db1 = new CompanyDbDAO();
		db1.createCompany(a);
		db1.createCompany(b);
		db1.createCompany(c);
		
		db1.removeCompany(b);
		
		c.setCompName("New CompC");
		c.setPassword("abc");
		db1.updateCompany(c);
		
		db1.createCompany(d);
		
		System.out.println(db1.getAllCompanies());
//		Statement stat = DB.connectDB().createStatement();
//		stat.executeUpdate("INSERT INTO Company (COMP_NAME, PASSWORD, EMAIL) VALUES('blag','b','c')");
//		ResultSet rs = stat.getGeneratedKeys();
//		rs.next();
//		System.out.println(rs.getInt(1));
//		System.out.println("generatedkeyId: " + stat.getGeneratedKeys());
		//ResultSet rs = DB.connectDB().createStatement().executeQuery("SELECT * FROM Company WHERE COMP_NAME='CompA' AND PASSWORD='12345'");
		

		
		
		System.out.println(db1.login("CompA", "1234"));		
		
	}

}
