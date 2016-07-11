package com.netanel.coupons.facades;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.netanel.coupons.dao.db.*;
import com.netanel.coupons.dao.*;
import com.netanel.coupons.exception.*;
import com.netanel.coupons.jbeans.*;

public class CompanyFacade implements CouponClientFacade{
	//
	// Attributes
	//
	//private Company company;
	private long compId;
	private static CompanyDAO compDao = null;
	private static CouponDAO couponDao = null;
	private static CustomerDAO custDao = null;
	
	//
	// Constructors
	//
	public CompanyFacade() {
		if (compDao == null) {
			compDao = new CompanyDbDAO();
		}
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
	public CompanyFacade login(String compName, char[] password, ClientType clientType) throws LoginException, DAOException {
		boolean loginSuccessful = false;
		try {
			loginSuccessful = compDao.login(compName, password);
		} catch (Exception e) {
			throw new LoginException("Company Login Failed.");
		}
		
		if (loginSuccessful && clientType.equals(ClientType.COMPANY)) {
			//company = compDao.getCompany(compName);
			compId = compDao.getCompany(compName).getId();
			return this;
		} else {
			throw new LoginException("Company Login Failed.");
		}
	}

	public void createCoupon(Coupon coupon) throws DAOException{
		couponDao.createCoupon(coupon);
		compDao.addCoupon(compId, coupon);
	}
	
	public void deleteCoupon(Coupon coupon) throws DAOException{
		// Remove coupon from company
		compDao.removeCoupon(compId, coupon.getId());
		//Remove coupon from all customers
		for (long custId : couponDao.getCustomersId(coupon)) {
			custDao.removeCoupon(custId, coupon.getId());
		}
		// Delete coupon
		couponDao.deleteCoupon(coupon);
	}
	
	public void updateCoupon(Coupon coupon) throws DAOException {
		couponDao.updateCoupon(coupon);
	}
	
	public Company getCompany() throws DAOException{
		return compDao.getCompany(compId);
	}
	
	public Set<Coupon> getAllCoupons() throws DAOException{
		return compDao.getCoupons(compId);
	}
	
	public Coupon getCoupon(long couponId) throws DAOException {
		for (Coupon coupon : compDao.getCoupons(compId)) {
			if (coupon.getId() == couponId ) {
				return coupon;
			}
		}
		return null;
	}
	
	public Set<Coupon> getCouponsByType(CouponType couponType) throws DAOException {
		Set<Coupon> coupons = new HashSet<>();
		for (Coupon coupon : compDao.getCoupons(compId)) {
			if (coupon.getType().equals(couponType) ) {
				coupons.add(coupon);
			}
		}
		return coupons;
	}
	
	public Set<Coupon> getCouponsByDate(LocalDate endDate) throws DAOException {
		Set<Coupon> coupons = new HashSet<>();
		for (Coupon coupon : compDao.getCoupons(compId)) {
			if (coupon.getEndDate().equals(endDate) ) {
				coupons.add(coupon);
			}
		}
		return coupons;
	}
	
	public Set<Coupon> getCouponsByPrice(double price) throws DAOException{
		Set<Coupon> coupons = new HashSet<>();
		for (Coupon coupon : compDao.getCoupons(compId)) {
			if (coupon.getPrice() == price ) {
				coupons.add(coupon);
			}
		}
		return coupons;
	}
}
