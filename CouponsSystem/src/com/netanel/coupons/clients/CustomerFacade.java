package com.netanel.coupons.clients;

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
	private Customer customer;
	private CustomerDAO custDao;
	
	//
	// Constructors
	//
	public CustomerFacade(String custName) throws DAOException {
		//this.customer = customer;
		this.custDao = new CustomerDbDAO();
		this.customer = custDao.getCustomer(custName);
	}
	
	//
	// Functions
	//
	@Override
	public CouponClientFacade login(String custName, char[] password, ClientType clientType) throws LoginException {
		boolean loginSuccessful = custDao.login(custName, password);
		if (loginSuccessful && clientType.equals(ClientType.CUSTOMER)) {
			return this;
		} else {
			throw new LoginException("Customer Login Failed. Incorrect parameters.");
		}		
	}

	public void buyCoupon(Coupon coupon) throws DAOException{		
		custDao.addCoupon(customer, coupon);
	}
	
	public Set<Coupon> getAllCoupons() throws DAOException{
		return custDao.getCoupons(customer.getId());
	}
	
	public Set<Coupon> getCouponsByType(CouponType couponType) throws DAOException {
		Set<Coupon> coupons = new HashSet<>();
		for (Coupon coupon : custDao.getCoupons(customer.getId())) {
			if (coupon.getType().equals(couponType) ) {
				coupons.add(coupon);
			}
		}
		return coupons;
	}
	
	public Set<Coupon> getCouponsByPrice(double price) throws DAOException{
		Set<Coupon> coupons = new HashSet<>();
		for (Coupon coupon : custDao.getCoupons(customer.getId())) {
			if (coupon.getPrice() == price ) {
				coupons.add(coupon);
			}
		}
		return coupons;
	}
	
}
