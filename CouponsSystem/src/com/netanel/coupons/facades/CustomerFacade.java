package com.netanel.coupons.facades;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.netanel.coupons.app.CouponSystem;
import com.netanel.coupons.dao.*;
import com.netanel.coupons.exception.*;
import com.netanel.coupons.jbeans.*;

/**
 * Coupon System Customer Facade
 */
public class CustomerFacade implements CouponClientFacade{
	//
	// Attributes
	//
	//private Customer customer;
	private long custId;
	private String custName;
	private CustomerDAO custDao = null;
	private CouponDAO couponDao = null;
	
	//
	// Constructors
	//
	public CustomerFacade() {
		custDao = CouponSystem.getInstance().getCustDao();
		couponDao = CouponSystem.getInstance().getCouponDao();
	}
	
	//
	// Functions
	//
	@Override
	public CustomerFacade login(String custName, char[] password, ClientType clientType) throws LoginException, DAOException {
		boolean loginSuccessful = false;
		try {
			loginSuccessful = custDao.login(custName, password) && clientType.equals(ClientType.CUSTOMER);
		} catch (Exception e) {
			throw new LoginException("Customer Login Failed.");
		}
		
		if (loginSuccessful) {
			this.custId = custDao.getCustomer(custName).getId();
			this.custName = custName;
			return this;
		} else {
			throw new LoginException("Customer Login Failed.");
		}
	}

	/**
	 * Buy a coupon.
	 * @param coupon a {@code Coupon} object.
	 * @throws DAOException
	 * @throws CouponException
	 */
	public void buyCoupon(Coupon coupon) throws DAOException, CouponException{
		// Check amount
		if (coupon.getAmount() < 1) {
			throw new CouponException("Coupon ID:" + coupon.getId() + " (" + coupon.getTitle() + ") is not in stock");
		}
		// Check Date
		if (LocalDate.now().isAfter(coupon.getEndDate())) {
			throw new CouponException("Coupon ID:" + coupon.getId() + " (" + coupon.getTitle() + ") is expired. EndDate:" + coupon.getEndDate());
		}
		
		custDao.addCoupon(custId, coupon);
		coupon.setAmount(coupon.getAmount() - 1);
		couponDao.updateCoupon(coupon);
	}
	
	
	/**
	 * Buy a coupon by ID
	 * @param couponId a {@code long} value of the coupon's ID
	 * @throws DAOException
	 * @throws CouponException
	 */
	public void buyCoupon(long couponId) throws DAOException, CouponException{		
		for (Coupon coupon : getAllCoupons()) {
			if (coupon.getId() == couponId) {
				buyCoupon(coupon);
				break;
			}
		}
	}
	
	/**
	 * Buy a coupon by title.
	 * @param couponTitle a {@code String} value of the coupon's title.
	 * @throws DAOException
	 * @throws CouponException
	 */
	public void buyCoupon(String couponTitle) throws DAOException, CouponException{		
		for (Coupon coupon : getAllCoupons()) {
			if (coupon.getTitle().equals(couponTitle)) {
				buyCoupon(coupon);
				break;
			}
		}
	}
	
	/**
	 * Get all the coupons in the system.
	 * @return a {@code Set<Coupon>} of all coupons in the system (include expired, out of stock and bought coupons).
	 * @throws DAOException
	 */
	public Set<Coupon> getAllCoupons() throws DAOException {
		return couponDao.getAllCoupons();
	}
	
	/**
	 * Get costumer's coupons.
	 * @return a {@code Set<Coupon>} of the coupons of the current customer.
	 * @throws DAOException
	 */
	public Set<Coupon> getMyCoupons() throws DAOException{
		return custDao.getCoupons(custId);
	}
	
	/**
	 * Get coupon by ID.
	 * @param couponId a {@code long} value of the coupon's ID.
	 * @return a {@code Coupon} object.
	 * @throws DAOException
	 */
	public Coupon getCoupon(long couponId) throws DAOException {
		return couponDao.getCoupon(couponId);
	}
	
	/**
	 * Get coupins by Type.
	 * @param couponType a {@code CouponType} Enum.
	 * @return  a {@code Set<Coupon>} of all of the customer's coupons of a specific type.
	 * @throws DAOException
	 */
	public Set<Coupon> getMyCouponsByType(CouponType couponType) throws DAOException {
		Set<Coupon> coupons = new HashSet<>();
		for (Coupon coupon : custDao.getCoupons(custId)) {
			if (coupon.getType().equals(couponType) ) {
				coupons.add(coupon);
			}
		}
		return coupons;
	}
	
	/**
	 * Get coupons by Price.
	 * @param price a {@code double} value of the maximum coupon price.
	 * @return a {@code Set<Coupon>} of all of the customer's coupons whose price is equal or lower than the specified price.
	 * @throws DAOException
	 */
	public Set<Coupon> getMyCouponsByPrice(double price) throws DAOException{
		Set<Coupon> coupons = new HashSet<>();
		for (Coupon coupon : custDao.getCoupons(custId)) {
			if (coupon.getPrice() <= price ) {
				coupons.add(coupon);
			}
		}
		return coupons;
	}

	/**
	 * Get this facade's customer ID.
	 * @return a {@code long} value if the customer ID.
	 */
	public long getCustId() {
		return custId;
	}

	/**
	 * Get this facade's customer name.
	 * @return a {@code String} value of the customer name.
	 */
	public String getCustName() {
		return custName;
	}

	@Override
	public String toString() {
		return "CustomerFacade [custId=" + custId + ", custName=" + custName + "]";
	}


	
	
}
