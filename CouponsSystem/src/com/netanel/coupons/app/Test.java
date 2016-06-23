package com.netanel.coupons.app;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import com.netanel.coupons.crypt.Password;
import com.netanel.coupons.dao.db.CompanyDbDAO;
import com.netanel.coupons.dao.db.CouponDbDAO;
import com.netanel.coupons.dao.db.CustomerDbDAO;
import com.netanel.coupons.dao.db.DB;
import com.netanel.coupons.jbeans.Company;
import com.netanel.coupons.jbeans.Coupon;
import com.netanel.coupons.jbeans.CouponType;
import com.netanel.coupons.jbeans.Customer;

public class Test {
	public static void main(String[] args) throws Exception {
		// Initialize DB		
		DB.connectDB().createStatement().executeUpdate("DELETE FROM Company");
		DB.connectDB().createStatement().executeUpdate("DELETE FROM Customer");
		DB.connectDB().createStatement().executeUpdate("DELETE FROM Coupon");
		DB.connectDB().createStatement().executeUpdate("UPDATE sqlite_sequence set seq=0");
		
		//Create passwords
		Password p1 = new Password("1234".toCharArray());
		Password p2 = new Password("1234".toCharArray());
		Password p3 = new Password("1234".toCharArray());
		Password p4 = new Password("1234".toCharArray());
		Password p5 = new Password("1234".toCharArray());
		Password p6 = new Password("1234".toCharArray());
		Password p7 = new Password("1234".toCharArray());
		
		Company a = new Company("CompA", p1, "compa@compa.com");
		Company b = new Company("CompB", p2, "compb@compb.com");
		Company c = new Company("CompC", p3, "compc@compc.com");
		Company d = new Company("CompD", p4, "compd@compd.com");
		
		Customer cust1 = new Customer("moshe", p5);
		Customer cust2 = new Customer("david", p6);
		Customer cust3 = new Customer("sarah", p7);
		
		
		Coupon c1 = new Coupon("Coupon1",LocalDate.now(),LocalDate.of(2018, 7, 29), 10, CouponType.CARS, "Car Coupon", 19.90, "files/cars.jpg");
		Coupon c2 = new Coupon("Coupon2",LocalDate.now(),LocalDate.of(2017, 05, 15), 10, CouponType.ELECTRONICS, "Electronics Coupon", 89.90, "files/electronics.jpg");
		Coupon c3 = new Coupon("Coupon3",LocalDate.now(),LocalDate.of(2018,9 , 30), 10, CouponType.FOOD, "Food Coupon", 12.90, "files/food.jpg");
		Coupon c4 = new Coupon("test", LocalDate.now(), LocalDate.of(2017, 6, 17), 10, CouponType.CARS, "my message", 19.90, "files/images/car.jpg");
		
//		System.out.println(a);
//		System.out.println(b);
//		System.out.println(c);
//		System.out.println(c1);
//		System.out.println(c2);
//		System.out.println(c3);
		CustomerDbDAO custDB = new CustomerDbDAO();
		custDB.createCustomer(cust1);
		custDB.createCustomer(cust2);
		custDB.createCustomer(cust3);
		
		custDB.removeCustomer("david");
		
		CompanyDbDAO compDB = new CompanyDbDAO();
		compDB.createCompany(a);
		compDB.createCompany(b);
		compDB.createCompany(c);
		
		compDB.removeCompany(b);
		
		c.setCompName("New CompC");
		c.setPassword("abc".toCharArray());
		compDB.updateCompany(c);
		
		compDB.createCompany(d);
		
		System.out.println(compDB.getAllCompanies());
//		Statement stat = DB.connectDB().createStatement();
//		stat.executeUpdate("INSERT INTO Company (COMP_NAME, PASSWORD, EMAIL) VALUES('blag','b','c')");
//		ResultSet rs = stat.getGeneratedKeys();
//		rs.next();
//		System.out.println(rs.getInt(1));
//		System.out.println("generatedkeyId: " + stat.getGeneratedKeys());
		//ResultSet rs = DB.connectDB().createStatement().executeQuery("SELECT * FROM Company WHERE COMP_NAME='CompA' AND PASSWORD='12345'");
				
		System.out.println("Login: " + compDB.login("CompA", "1234".toCharArray()));
		System.out.println("Login: " + custDB.login("moshe", "12344".toCharArray()));
		
		System.out.println(p1);
		System.out.println(c1);
		
		CouponDbDAO couponDB = new CouponDbDAO();
		couponDB.createCoupon(c1);
		couponDB.createCoupon(c2);
		couponDB.createCoupon(c3);
		couponDB.createCoupon(c4);
		
		Coupon myCoupon = couponDB.getCoupon(2);
		System.out.println(myCoupon);
		myCoupon.setAmount(80);
		couponDB.updateCoupon(myCoupon);
		couponDB.createCoupon(c1);
		
		
	}

}
