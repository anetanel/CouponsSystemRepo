package com.netanel.coupons.facades;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.netanel.coupons.app.CouponSystem;
import com.netanel.coupons.dao.*;
import com.netanel.coupons.exception.*;
import com.netanel.coupons.jbeans.*;

/**
 * Coupon System Company Facade
 */
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
			loginSuccessful = compDao.login(compName, password) && clientType.equals(ClientType.COMPANY);
		} catch (Exception e) {
			throw new LoginException("Company Login Failed.");
		}
		
		if (loginSuccessful) {
			this.compId = compDao.getCompany(compName).getId();
			this.compName = compName;
			return this;
		} else {
			throw new LoginException("Company Login Failed.");
		}
	}

	/**
	 * Create a new coupon.
	 * @param coupon a {@code Coupon} object.
	 * @throws DAOException
	 */
	public void createCoupon(Coupon coupon) throws DAOException{
		validateCouponName(coupon);
		couponDao.createCoupon(coupon);
		compDao.addCoupon(compId, coupon);
	}
	
	/**
	 * Delete a coupon from Coupon System.
	 * @param coupon a {@code Coupon} object.
	 * @throws DAOException
	 * @throws IOException
	 */
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
	
	/**
	 * Update coupon details.
	 * @param coupon a {@code Coupon} object.
	 * @throws DAOException
	 */
	public void updateCoupon(Coupon coupon) throws DAOException {
		validateCouponName(coupon);
		couponDao.updateCoupon(coupon);
	}
	
	/**
	 * Get this facade's company.
	 * @return a {@code Company} object of this company.
	 * @throws DAOException
	 */
	public Company getCompany() throws DAOException{
		return compDao.getCompany(compId);
	}
	
	/**
	 * Get all of this company's coupons.
	 * @return a {@code Set<Coupon>} of all of this company's coupons.
	 * @throws DAOException
	 */
	public Set<Coupon> getAllCoupons() throws DAOException{
		return compDao.getCoupons(compId);
	}
	
	/**
	 * Get a coupon by ID.
	 * @param couponId a {@code long} value of the coupon's ID.
	 * @return a {@code Coupon} object.
	 * @throws DAOException
	 * @throws CouponException
	 */
	public Coupon getCoupon(long couponId) throws DAOException, CouponException {
		for (Coupon coupon : compDao.getCoupons(compId)) {
			if (coupon.getId() == couponId ) {
				return coupon;
			}
		}
		throw new CouponException("Could not find Coupon ID:" + couponId);
	}
	
	/**
	 * Get a coupon by Title.
	 * @param couponTitle a {@code String} value of the coupon's title.
	 * @return a {@code Coupon} object.
	 * @throws DAOException
	 * @throws CouponException
	 */
	public Coupon getCoupon(String couponTitle) throws DAOException, CouponException {
		for (Coupon coupon : compDao.getCoupons(compId)) {
			if (coupon.getTitle().equals(couponTitle)) {
				return coupon;
			}
		}
		throw new CouponException("Could not find Coupon Title:'" + couponTitle + "'" );
	}
	
	/**
	 * Get coupons by Type
	 * @param couponType a {@code CouponType} Enum.
	 * @return a {@code Set<Coupon>} of all of the company's coupons of a specific type.
	 * @throws DAOException
	 */
	public Set<Coupon> getCouponsByType(CouponType couponType) throws DAOException {
		Set<Coupon> coupons = new HashSet<>();
		for (Coupon coupon : compDao.getCoupons(compId)) {
			if (coupon.getType().equals(couponType) ) {
				coupons.add(coupon);
			}
		}
		return coupons;
	}
	
	/**
	 * Get coupons by Expiration Date.
	 * @param endDate a {@code LocalDate} expiration date.
	 * @return a {@code Set<Coupon>} of all of the company's coupons whose expiration date is before the specified date.
	 * @throws DAOException
	 */
	public Set<Coupon> getCouponsByDate(LocalDate endDate) throws DAOException {
		Set<Coupon> coupons = new HashSet<>();
		for (Coupon coupon : compDao.getCoupons(compId)) {
			if (coupon.getEndDate().equals(endDate) || coupon.getEndDate().isBefore(endDate) ) {
				coupons.add(coupon);
			}
		}
		return coupons;
	}
	
	/**
	 * Get coupons by Price.
	 * @param price a {@code double} value of the maximum coupon price.
	 * @return a {@code Set<Coupon>} of all of the company's coupons whose price is equal or lower than the specified price.
	 * @throws DAOException
	 */
	public Set<Coupon> getCouponsByPrice(double price) throws DAOException{
		Set<Coupon> coupons = new HashSet<>();
		for (Coupon coupon : compDao.getCoupons(compId)) {
			if (coupon.getPrice() <= price ) {
				coupons.add(coupon);
			}
		}
		return coupons;
	}

	/**
	 * Get this facade's company ID.
	 * @return a {@code long} value of company ID.
	 */
	public long getCompId() {
		return compId;
	}

	/**
	 * Get this facade's company name.
	 * @return a {@code String} value of the company name.
	 */
	public String getCompName() {
		return compName;
	}

	// Validate coupon name
	private void validateCouponName(Coupon coupon) throws DAOException {
		if (coupon.getTitle().equals("")) {
			throw new DAOException("Coupon name can't be empty.");
		}
	}
	
}
