package com.netanel.coupons.facades;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.netanel.coupons.dao.*;
import com.netanel.coupons.dao.db.*;
import com.netanel.coupons.exception.*;
import com.netanel.coupons.jbeans.*;

public class CustomerFacade implements CouponClientFacade{
	//
	// Attributes
	//
	//private Customer customer;
	private long custId;
	private static CustomerDAO custDao = null;
	private static CouponDAO couponDao = null;
	
	//
	// Constructors
	//
	public CustomerFacade() {
		if (custDao == null) {
			custDao = new CustomerDbDAO();
		}
		if (couponDao == null) {
			couponDao = new CouponDbDAO();
		}
	}
	
	//
	// Functions
	//
	@Override
	public CustomerFacade login(String custName, char[] password, ClientType clientType) throws LoginException, DAOException {
		boolean loginSuccessful = false;
		try {
			loginSuccessful = custDao.login(custName, password);
		} catch (Exception e) {
			throw new LoginException("Customer Login Failed.");
		}
		
		if (loginSuccessful && clientType.equals(ClientType.CUSTOMER)) {
		//	customer = custDao.getCustomer(custName);
			custId = custDao.getCustomer(custName).getId();
			return this;
		} else {
			throw new LoginException("Customer Login Failed.");
		}
	}

	public void buyCoupon(Coupon coupon) throws DAOException, CouponException{
		if (coupon.getAmount() < 1) {
			throw new CouponException("Coupon ID:" + coupon.getId() + " (" + coupon.getTitle() + ") is not in stock");
		}
	
		if (LocalDate.now().isAfter(coupon.getEndDate())) {
			throw new CouponException("Coupon ID:" + coupon.getId() + " (" + coupon.getTitle() + ") is expired. EndDate:" + coupon.getEndDate());
		}
		custDao.addCoupon(custId, coupon);
		coupon.setAmount(coupon.getAmount() - 1);
		couponDao.updateCoupon(coupon);
	}
	
	
	public void buyCoupon(long couponId) throws DAOException, CouponException{		
		for (Coupon coupon : getAllCoupons()) {
			if (coupon.getId() == couponId) {
				buyCoupon(coupon);
				break;
			}
		}
	}
	
	public void buyCoupon(String couponTitle) throws DAOException, CouponException{		
		for (Coupon coupon : getAllCoupons()) {
			if (coupon.getTitle().equals(couponTitle)) {
				buyCoupon(coupon);
				break;
			}
		}
	}
	
	public Set<Coupon> getAllCoupons() throws DAOException {
		return couponDao.getAllCoupons();
	}
	
	public Set<Coupon> getMyCoupons() throws DAOException{
		return custDao.getCoupons(custId);
	}
	
	public Set<Coupon> getMyCouponsByType(CouponType couponType) throws DAOException {
		Set<Coupon> coupons = new HashSet<>();
		for (Coupon coupon : custDao.getCoupons(custId)) {
			if (coupon.getType().equals(couponType) ) {
				coupons.add(coupon);
			}
		}
		return coupons;
	}
	
	public Set<Coupon> getMyCouponsByPrice(double price) throws DAOException{
		Set<Coupon> coupons = new HashSet<>();
		for (Coupon coupon : custDao.getCoupons(custId)) {
			if (coupon.getPrice() == price ) {
				coupons.add(coupon);
			}
		}
		return coupons;
	}
	
}
