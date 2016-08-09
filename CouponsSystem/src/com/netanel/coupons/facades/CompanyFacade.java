package com.netanel.coupons.facades;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.netanel.coupons.dao.db.*;
import com.netanel.coupons.app.CouponSystem;
import com.netanel.coupons.dao.*;
import com.netanel.coupons.exception.*;
import com.netanel.coupons.jbeans.*;

public class CompanyFacade implements CouponClientFacade{
	//
	// Attributes
	//
	//private Company company;
	private long compId;
	private String compName;
	private CompanyDAO compDao = null;
	private CouponDAO couponDao = null;
	private CustomerDAO custDao = null;
	
	//
	// Constructors
	//
	public CompanyFacade() {
		compDao = CouponSystem.getInstance().getCompDao();
		custDao = CouponSystem.getInstance().getCustDao();
		couponDao = CouponSystem.getInstance().getCouponDao();
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
			this.compId = compDao.getCompany(compName).getId();
			this.compName = compName;
			return this;
		} else {
			throw new LoginException("Company Login Failed.");
		}
	}

	public void createCoupon(Coupon coupon) throws DAOException{
		if (coupon.getTitle().equals("")) {
			throw new DAOException("Coupon name can't be empty.");
		}
		couponDao.createCoupon(coupon);
		compDao.addCoupon(compId, coupon);
	}
	
	public void deleteCoupon(Coupon coupon) throws DAOException, IOException{
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
		if (coupon.getTitle().equals("")) {
			throw new DAOException("Coupon name can't be empty.");
		}
		couponDao.updateCoupon(coupon);
	}
	
	public Company getCompany() throws DAOException{
		return compDao.getCompany(compId);
	}
	
	public Set<Coupon> getAllCoupons() throws DAOException{
		return compDao.getCoupons(compId);
	}
	
	public Coupon getCoupon(long couponId) throws DAOException, CouponException {
		for (Coupon coupon : compDao.getCoupons(compId)) {
			if (coupon.getId() == couponId ) {
				return coupon;
			}
		}
		throw new CouponException("Could not find Coupon ID:" + couponId);
	}
	
	public Coupon getCoupon(String couponTitle) throws DAOException, CouponException {
		for (Coupon coupon : compDao.getCoupons(compId)) {
			if (coupon.getTitle().equals(couponTitle)) {
				return coupon;
			}
		}
		throw new CouponException("Could not find Coupon Title:'" + couponTitle + "'" );
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
			if (coupon.getEndDate().equals(endDate) || coupon.getEndDate().isBefore(endDate) ) {
				coupons.add(coupon);
			}
		}
		return coupons;
	}
	
	public Set<Coupon> getCouponsByPrice(double price) throws DAOException{
		Set<Coupon> coupons = new HashSet<>();
		for (Coupon coupon : compDao.getCoupons(compId)) {
			if (coupon.getPrice() <= price ) {
				coupons.add(coupon);
			}
		}
		return coupons;
	}

	public long getCompId() {
		return compId;
	}

	public String getCompName() {
		return compName;
	}
	
}
