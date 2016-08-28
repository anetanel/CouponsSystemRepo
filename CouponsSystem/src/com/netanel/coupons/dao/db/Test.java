package com.netanel.coupons.dao.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.netanel.coupons.crypt.Password;
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

@SuppressWarnings("unused")
public class Test {
	public static void main(String[] args) throws Exception {

		// Initialize DB
		try (Connection con = DB.getConnection()) {
			con.createStatement().executeUpdate("DELETE FROM Company");
			con.createStatement().executeUpdate("DELETE FROM Customer");
			con.createStatement().executeUpdate("DELETE FROM Coupon");
			con.createStatement().executeUpdate("DELETE FROM Company_Coupon");
			con.createStatement().executeUpdate("DELETE FROM Customer_Coupon");
			con.createStatement().executeUpdate("UPDATE sqlite_sequence set seq=0");
		}

		/////////////////////////////////////////////

		// Get Admin Facade
		AdminFacade admin = null;
		admin = new AdminFacade().login("admin", "1234".toCharArray(), ClientType.ADMIN);

		// Create some companies
		admin.createCompany(
				new Company("IBM", new Password("1234".toCharArray()), "info@ibm.com", new HashSet<Coupon>()));
		admin.createCompany(
				new Company("EMC", new Password("1234".toCharArray()), "info@emc.com", new HashSet<Coupon>()));
		admin.createCompany(
				new Company("Dell", new Password("1234".toCharArray()), "info@dell.com", new HashSet<Coupon>()));
		admin.createCompany(
				new Company("HP", new Password("1234".toCharArray()), "info@hp.com", new HashSet<Coupon>()));
		admin.createCompany(
				new Company("Tnuva", new Password("1234".toCharArray()), "info@tnuva.co.il", new HashSet<Coupon>()));
		admin.createCompany(
				new Company("Tara", new Password("1234".toCharArray()), "info@tara.co.il", new HashSet<Coupon>()));
		admin.createCompany(
				new Company("Osem", new Password("1234".toCharArray()), "info@osem.co.il", new HashSet<Coupon>()));
		admin.createCompany(
				new Company("Samsung", new Password("1234".toCharArray()), "info@samsung.com", new HashSet<Coupon>()));
		admin.createCompany(
				new Company("Nike", new Password("1234".toCharArray()), "info@nike.com", new HashSet<Coupon>()));
		admin.createCompany(
				new Company("Adidas", new Password("1234".toCharArray()), "info@adidas.com", new HashSet<Coupon>()));
		admin.createCompany(
				new Company("Shilav", new Password("1234".toCharArray()), "info@shilav.co.il", new HashSet<Coupon>()));
		admin.createCompany(
				new Company("Gazit", new Password("1234".toCharArray()), "info@gazit.co.il", new HashSet<Coupon>()));
		admin.createCompany(
				new Company("Mazda", new Password("1234".toCharArray()), "info@mazda.com", new HashSet<Coupon>()));
		admin.createCompany(
				new Company("Toyota", new Password("1234".toCharArray()), "info@toyota.com", new HashSet<Coupon>()));
		admin.createCompany(
				new Company("BMW", new Password("1234".toCharArray()), "info@bmw.com", new HashSet<Coupon>()));
		admin.createCompany(
				new Company("Bagir", new Password("1234".toCharArray()), "info@bagir.co.il", new HashSet<Coupon>()));
		admin.createCompany(new Company("Home Center", new Password("1234".toCharArray()), "info@homecenter.co.il",
				new HashSet<Coupon>()));
		admin.createCompany(
				new Company("ACE", new Password("1234".toCharArray()), "info@ace.co.il", new HashSet<Coupon>()));
		admin.createCompany(
				new Company("Yamaha", new Password("1234".toCharArray()), "info@yamaha.com", new HashSet<Coupon>()));
		admin.createCompany(
				new Company("Habima", new Password("1234".toCharArray()), "info@habima.co.il", new HashSet<Coupon>()));
		admin.createCompany(new Company("Globus Group", new Password("1234".toCharArray()), "info@gg.co.il",
				new HashSet<Coupon>()));
		admin.createCompany(new Company("Opticana", new Password("1234".toCharArray()), "info@opticana.co.il",
				new HashSet<Coupon>()));
		admin.createCompany(
				new Company("Erroca", new Password("1234".toCharArray()), "info@erroca.co.il", new HashSet<Coupon>()));
		admin.createCompany(
				new Company("Sony", new Password("1234".toCharArray()), "info@sony.com", new HashSet<Coupon>()));

		// Create some customers

		admin.createCustomer(new Customer("moshe", new Password("1234".toCharArray()), new HashSet<Coupon>()));
		admin.createCustomer(new Customer("david", new Password("1234".toCharArray()), new HashSet<Coupon>()));
		admin.createCustomer(new Customer("sarah", new Password("1234".toCharArray()), new HashSet<Coupon>()));
		admin.createCustomer(new Customer("dana", new Password("1234".toCharArray()), new HashSet<Coupon>()));

		// Get Companies facades
		CompanyFacade ibm = null;
		CompanyFacade emc = null;

		ibm = new CompanyFacade().login("IBM", "1234".toCharArray(), ClientType.COMPANY);
		emc = new CompanyFacade().login("EMC", "1234".toCharArray(), ClientType.COMPANY);

		// Create some coupons

		ibm.createCoupon(new Coupon("5% Mainframe", LocalDate.now(), LocalDate.of(2017, 5, 15), 10,
				CouponType.ELECTRONICS, "5% discount off any new IBM Mainframe Purchase", 199.9, ""));
		ibm.createCoupon(new Coupon("15% Mainframe", LocalDate.now(), LocalDate.of(2017, 6, 16), 5,
				CouponType.ELECTRONICS, "15% discount off any new IBM Mainframe Purchase", 299.9, ""));
		ibm.createCoupon(new Coupon("25% Mainframe", LocalDate.now(), LocalDate.of(2017, 8, 18), 1,
				CouponType.ELECTRONICS, "25% discount off any new IBM Mainframe Purchase", 399.9, ""));

		emc.createCoupon(new Coupon("Free VMAX", LocalDate.now(), LocalDate.of(2017, 8, 18), 1, CouponType.ELECTRONICS,
				"Free EMC VMAX Storage System", 5999.9, null));
		emc.createCoupon(new Coupon("Free VNX", LocalDate.now(), LocalDate.of(2017, 12, 18), 5, CouponType.ELECTRONICS,
				"Free EMC VNX Storage System", 1899.9, null));

		emc.createCoupon(new Coupon("EMC Coupon 1", LocalDate.now(), LocalDate.of(2017, 11, 18), 5, CouponType.CARS,
				"EMC Coupon number 1 description", 2899.9, null));
		emc.createCoupon(new Coupon("EMC Coupon 2", LocalDate.now(), LocalDate.of(2017, 10, 18), 5, CouponType.HEALTH,
				"EMC Coupon number 2 description", 3899.9, null));
		// Create coupons with invalid icons
		emc.createCoupon(new Coupon("EMC Coupon 3", LocalDate.now(), LocalDate.of(2017, 9, 18), 5, CouponType.KIDS,
				"EMC Coupon number 3 description", 4899.9, "icons/blah.png"));
		emc.createCoupon(new Coupon("EMC Coupon 4", LocalDate.now(), LocalDate.of(2017, 9, 18), 5, CouponType.KIDS,
				"EMC Coupon number 4 description", 4899.9, "icons/vmax.png"));

		// Get customers facades
		CustomerFacade moshe = null;
		CustomerFacade dana = null;

		moshe = new CustomerFacade().login("moshe", "1234".toCharArray(), ClientType.CUSTOMER);
		dana = new CustomerFacade().login("dana", "1234".toCharArray(), ClientType.CUSTOMER);

		// Buy some coupons

		moshe.buyCoupon("Free VMAX");
		moshe.buyCoupon("Free VNX");
		dana.buyCoupon("5% Mainframe");
		dana.buyCoupon("Free VNX");
		dana.buyCoupon("EMC Coupon 1");
		dana.buyCoupon("EMC Coupon 2");
		dana.buyCoupon("EMC Coupon 3");

	}
}
