package com.netanel.coupons.dao;

import java.util.*;

import com.netanel.coupons.exception.DAOException;
import com.netanel.coupons.jbeans.*;

public interface CompanyDAO {
	
	/**
	 * Create a new Company in the underling database (or any other persistence storage), from a given {@code Company} object. 
	 * @param company a {@code Company} object.
	 * @return a positive {@code long} value corresponding to the ID of the newly created company, or {@code -1} if the operation failed.  
	 * @throws DAOException
	 */
	long createCompany(Company company) throws DAOException;
	
	/**
	 * Remove a Company from the underling database (or any other persistence storage).
	 * @param compId a {@code long} Company ID.
	 * @throws DAOException
	 */
	void removeCompany(long compId) throws DAOException;
	
	/**
	 * Remove a Company from the underling database (or any other persistence storage).
	 * @param company a {@code Company} object.
	 * @throws DAOException
	 */
	void removeCompany(Company company) throws DAOException;
	
	/**
	 * Updates a Company in the underling database (or any other persistence storage), from a given {@code Company} object.
	 * @param company a {@code Company} object.
	 * @throws DAOException
	 */
	void updateCompany(Company company) throws DAOException;
	
	/**
	 * Returns a {@code Company} object from the underling database (or any other persistence storage).
	 * @param compId a {@code long} Company ID.
	 * @return a {@code Company} object.
	 * @throws DAOException
	 */
	Company getCompany(long compId) throws DAOException;
	
	/**
	 * Returns a {@code Set<Company>} of all Companies from the underling database (or any other persistence storage).
	 * @return a {@code Set<Company>}.
	 * @throws DAOException
	 */
	Set<Company> getAllCompanies() throws DAOException;
	
	/**
	 * Returns a {@code Set<Coupon>} of all Coupons of a specific Company, from the underling database (or any other persistence storage).
	 * @param CompId a {@code long} Company ID.
	 * @return a {@code Set<Coupon>}.
	 * @throws DAOException
	 */
	Set<Coupon> getCoupons(long CompId) throws DAOException;
	
	/**
	 * Checks if the password and company name given, matches the credentials stored in the underling database (or any other persistence storage).
	 * @param compName a {@code String} Company name. 
	 * @param password a {@code char[]} password.
	 * @return {@code true} if the credentials match, otherwise {@code false}.
	 */
	boolean login(String compName, char[] password);
	
	/**
	 * Adds a correlation between a Coupon and a Company in the underling database (or any other persistence storage).
	 * @param company a {@code Company} object.
	 * @param coupon a {@code Coupon} object.
	 * @throws DAOException
	 */
	void addCoupon(Company company, Coupon coupon) throws DAOException;
	
	/**
	 * Removes a correlation between a Coupon and a Company in the underling database (or any other persistence storage).
	 * @param compId a {@code long} Company ID.
	 * @param couponId a {@code long} Coupon ID.
	 * @throws DAOException
	 */
	void removeCoupon(long compId, long couponId) throws DAOException;
	/**
	 * Removes a correlation between a Coupon and a Company in the underling database (or any other persistence storage).
	 * @param company a {@code Company} object.
	 * @param coupon a {@code Coupon} object.
	 * @throws DAOException
	 */
	void removeCoupon(Company company, Coupon coupon) throws DAOException;
	
}
