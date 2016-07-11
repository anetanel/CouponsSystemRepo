package com.netanel.coupons.facades;

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
	
	//
	// Constructors
	//
	public CustomerFacade() {
		if (custDao == null) {
			custDao = new CustomerDbDAO();
		}
	}
	
	//
	// Functions
	//
	@Override
	public CouponClientFacade login(String custName, char[] password, ClientType clientType) throws LoginException, DAOException {
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

	public void buyCoupon(Coupon coupon) throws DAOException{		
		custDao.addCoupon(custId, coupon);
	}
	
	public Set<Coupon> getAllCoupons() throws DAOException{
		return custDao.getCoupons(custId);
	}
	
	public Set<Coupon> getCouponsByType(CouponType couponType) throws DAOException {
		Set<Coupon> coupons = new HashSet<>();
		for (Coupon coupon : custDao.getCoupons(custId)) {
			if (coupon.getType().equals(couponType) ) {
				coupons.add(coupon);
			}
		}
		return coupons;
	}
	
	public Set<Coupon> getCouponsByPrice(double price) throws DAOException{
		Set<Coupon> coupons = new HashSet<>();
		for (Coupon coupon : custDao.getCoupons(custId)) {
			if (coupon.getPrice() == price ) {
				coupons.add(coupon);
			}
		}
		return coupons;
	}
	
}
