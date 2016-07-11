package com.netanel.coupons.app;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.netanel.coupons.crypt.Password;
import com.netanel.coupons.dao.db.DB;
import com.netanel.coupons.exception.CouponException;
import com.netanel.coupons.exception.DAOException;
import com.netanel.coupons.exception.LoginException;
import com.netanel.coupons.facades.AdminFacade;
import com.netanel.coupons.facades.ClientType;
import com.netanel.coupons.facades.CompanyFacade;
import com.netanel.coupons.facades.CustomerFacade;
import com.netanel.coupons.jbeans.Company;
import com.netanel.coupons.jbeans.Coupon;
import com.netanel.coupons.jbeans.CouponType;
import com.netanel.coupons.jbeans.Customer;

public class Test3 {
	public static void main(String[] args) {

		System.setProperty("com.mchange.v2.log.MLog", "fallback");
		System.setProperty("com.mchange.v2.log.FallbackMLog.DEFAULT_CUTOFF_LEVEL", "WARNING");
		// Initialize DB
		try {
			DB.getConnection().createStatement().executeUpdate("DELETE FROM Company");
			DB.getConnection().createStatement().executeUpdate("DELETE FROM Customer");
			DB.getConnection().createStatement().executeUpdate("DELETE FROM Coupon");
			DB.getConnection().createStatement().executeUpdate("DELETE FROM Company_Coupon");
			DB.getConnection().createStatement().executeUpdate("DELETE FROM Customer_Coupon");
			DB.getConnection().createStatement().executeUpdate("UPDATE sqlite_sequence set seq=0");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/////////////////////////////////////////////

		// Get Admin Facade
		AdminFacade admin = null;
		try {
			admin = new AdminFacade().login("admin", "1234".toCharArray(), ClientType.ADMIN);
		} catch (LoginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Create some companies
		try {
			admin.createCompany(
					new Company("IBM", new Password("12345".toCharArray()), "info@ibm.com", new HashSet<Coupon>()));
			admin.createCompany(
					new Company("EMC", new Password("1q2w3e".toCharArray()), "info@emc.com", new HashSet<Coupon>()));
			admin.createCompany(
					new Company("DELL", new Password("abcde".toCharArray()), "info@dell.com", new HashSet<Coupon>()));
			admin.createCompany(
					new Company("HP", new Password("1234".toCharArray()), "info@hp.com", new HashSet<Coupon>()));
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Create some customers
		try {
			admin.createCustomer(new Customer("moshe", new Password("1234".toCharArray()), new HashSet<Coupon>()));
			admin.createCustomer(new Customer("david", new Password("1234".toCharArray()), new HashSet<Coupon>()));
			admin.createCustomer(new Customer("sarah", new Password("1234".toCharArray()), new HashSet<Coupon>()));
			admin.createCustomer(new Customer("dana", new Password("1234".toCharArray()), new HashSet<Coupon>()));
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		// Get Companies facades
		CompanyFacade ibm = null;
		CompanyFacade emc = null;
		try {
			ibm = new CompanyFacade().login("IBM", "12345".toCharArray(), ClientType.COMPANY);
			emc = new CompanyFacade().login("EMC", "1q2w3e".toCharArray(), ClientType.COMPANY);
			
		} catch (LoginException | DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Create some coupons
		try {
			ibm.createCoupon(
					new Coupon("5% Mainframe", LocalDate.now(), LocalDate.of(2017, 5, 15), 10, CouponType.ELECTRONICS,
							"5% discount off any new IBM Mainframe Purchase", 199.9, "images/ibm/mf.png"));
			ibm.createCoupon(
					new Coupon("15% Mainframe", LocalDate.now(), LocalDate.of(2018, 6, 16), 5, CouponType.ELECTRONICS,
							"15% discount off any new IBM Mainframe Purchase", 299.9, "images/ibm/mf.png"));
			ibm.createCoupon(
					new Coupon("25% Mainframe", LocalDate.now(), LocalDate.of(2016, 8, 18), 1, CouponType.ELECTRONICS,
							"25% discount off any new IBM Mainframe Purchase", 399.9, "images/ibm/mf.png"));
			
			emc.createCoupon(
					new Coupon("Free VMAX", LocalDate.now(), LocalDate.of(2016, 8, 18), 1, CouponType.ELECTRONICS,
							"Free EMC VMAX Storage System", 5999.9, "images/emc/vmax.png"));
			emc.createCoupon(
					new Coupon("Free VNX", LocalDate.now(), LocalDate.of(2016, 8, 18), 5, CouponType.ELECTRONICS,
							"Free EMC VNX Storage System", 4899.9, "images/emc/vnx.png"));
			
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Get customers facades
		CustomerFacade moshe = null;
		CustomerFacade dana = null;
		
		try {
			moshe = new CustomerFacade().login("moshe", "1234".toCharArray(), ClientType.CUSTOMER);
			dana = new CustomerFacade().login("dana", "1234".toCharArray(), ClientType.CUSTOMER);
		} catch (LoginException | DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Buy some coupons
		try {
			moshe.buyCoupon("Free VMAX");
			//moshe.buyCoupon("Free VMAX");
			moshe.buyCoupon("Free VNX");
			dana.buyCoupon("5% Mainframe");
			//dana.buyCoupon("Free VMAX");
			dana.buyCoupon("Free VNX");
		} catch (DAOException |CouponException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Show customers coupons
		try {
			System.out.println(moshe.getMyCoupons());
			System.out.println(dana.getMyCoupons());
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
}