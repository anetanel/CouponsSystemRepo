package com.netanel.coupons.app;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
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
					new Company("Dell", new Password("abcde".toCharArray()), "info@dell.com", new HashSet<Coupon>()));
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
			admin.createCompany(
					new Company("Home Center", new Password("1234".toCharArray()), "info@homecenter.co.il", new HashSet<Coupon>()));
			admin.createCompany(
					new Company("ACE", new Password("1234".toCharArray()), "info@ace.co.il", new HashSet<Coupon>()));
			admin.createCompany(
					new Company("Yamaha", new Password("1234".toCharArray()), "info@yamaha.com", new HashSet<Coupon>()));
			admin.createCompany(
					new Company("Habima", new Password("1234".toCharArray()), "info@habima.co.il", new HashSet<Coupon>()));
			admin.createCompany(
					new Company("Globus Group", new Password("1234".toCharArray()), "info@gg.co.il", new HashSet<Coupon>()));
			admin.createCompany(
					new Company("Opticana", new Password("1234".toCharArray()), "info@opticana.co.il", new HashSet<Coupon>()));
			admin.createCompany(
					new Company("Erroca", new Password("1234".toCharArray()), "info@erroca.co.il", new HashSet<Coupon>()));
			admin.createCompany(
					new Company("Sony", new Password("1234".toCharArray()), "info@sony.com", new HashSet<Coupon>()));
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
							"5% discount off any new IBM Mainframe Purchase", 199.9, ""));
			ibm.createCoupon(
					new Coupon("15% Mainframe", LocalDate.now(), LocalDate.of(2017, 6, 16), 5, CouponType.ELECTRONICS,
							"15% discount off any new IBM Mainframe Purchase", 299.9, ""));
			ibm.createCoupon(
					new Coupon("25% Mainframe", LocalDate.now(), LocalDate.of(2017, 8, 18), 1, CouponType.ELECTRONICS,
							"25% discount off any new IBM Mainframe Purchase", 399.9, ""));

			emc.createCoupon(new Coupon("Free VMAX", LocalDate.now(), LocalDate.of(2017, 8, 18), 1,
					CouponType.ELECTRONICS, "Free EMC VMAX Storage System", 5999.9, null));
			emc.createCoupon(new Coupon("Free VNX", LocalDate.now(), LocalDate.of(2017, 12, 18), 5,
					CouponType.ELECTRONICS, "Free EMC VNX Storage System", 1899.9, null));

			emc.createCoupon(new Coupon("EMC Coupon 1", LocalDate.now(), LocalDate.of(2017, 11, 18), 5,
					CouponType.CARS, "EMC Coupon number 1 description", 2899.9, null));
			emc.createCoupon(new Coupon("EMC Coupon 2", LocalDate.now(), LocalDate.of(2017, 10, 18), 5,
					CouponType.HEALTH, "EMC Coupon number 2 description", 3899.9, null));
			emc.createCoupon(new Coupon("EMC Coupon 3", LocalDate.now(), LocalDate.of(2017, 9, 18), 5,
					CouponType.KIDS, "EMC Coupon number 3 description", 4899.9, "icons/blah.png"));
			emc.createCoupon(new Coupon("EMC Coupon 4", LocalDate.now(), LocalDate.of(2017, 9, 18), 5,
					CouponType.KIDS, "EMC Coupon number 4 description", 4899.9, "icons/vmax.png"));

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
			// moshe.buyCoupon("Free VMAX");
			moshe.buyCoupon("Free VNX");
			dana.buyCoupon("5% Mainframe");
			// dana.buyCoupon("Free VMAX");
			dana.buyCoupon("Free VNX");
			dana.buyCoupon("EMC Coupon 1");
			dana.buyCoupon("EMC Coupon 2");
			dana.buyCoupon("EMC Coupon 3");
		} catch (DAOException | CouponException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Show customers coupons
//		try {
//			System.out.println("moshe:");
//			System.out.println(moshe.getMyCoupons());
//			System.out.println("dana:");
//			System.out.println(dana.getMyCoupons());
//			System.out.println("KIDS:");
//			System.out.println(dana.getMyCouponsByType(CouponType.KIDS));
//			System.out.println(dana.getMyCouponsByPrice(3000));
//			System.out.println(emc.getCouponsByDate(LocalDate.of(2016, 10, 18)));
//		} catch (DAOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

//		// Delete coupon
//		try {
//			emc.deleteCoupon(emc.getCoupon("Free VNX"));
//		} catch (DAOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (CouponException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

//		// Delete company
//		
//		try {
//			admin.deleteCompany(admin.getCompany("EMC"));
//		} catch (DAOException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		
		// Update company
		try {
			Company lenovo = admin.getCompany("IBM");

			lenovo.setEmail("support@lenovo.com");
			lenovo.setCompName("Lenovo");
			admin.updateCompanyDetails(lenovo);
			
//			System.out.println(admin.getAllCompanies());
//			System.out.println(admin.getCompany("Dell"));
		} catch (DAOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
		
		// Update customer
		
		try {
			Customer misha = admin.getCustomer("moshe");
			misha.setCustName("misha");
			admin.updateCustomerDetails(misha);
//			System.out.println(admin.getAllCustomers());
		} catch (DAOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		// Show customers coupons
//		try {
//			System.out.println("moshe:");
//			System.out.println(moshe.getMyCoupons());
//			System.out.println("dana:");
//			System.out.println(dana.getMyCoupons());
//		} catch (DAOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
