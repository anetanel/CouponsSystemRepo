package com.netanel.coupons.dao;

import java.util.Set;

import com.netanel.coupons.exception.DAOException;
import com.netanel.coupons.jbeans.Coupon;
import com.netanel.coupons.jbeans.Customer;

public interface CustomerDAO {
	
	/**
	 * Create a new Customer in the underling database (or any other persistence storage), from a given {@code Customer} object.
	 * @param customer a {@code Customer} object.
	 * @return a positive {@code long} value corresponding to the ID of the newly created customer, or {@code -1} if the operation failed.
	 * @throws DAOException
	 */
	long createCustomer(Customer customer) throws DAOException;
	
	/**
	 * Remove a Customer from the underling database (or any other persistence storage).
	 * @param customer a {@code Customer} object.
	 * @throws DAOException
	 */
	void removeCustomer(Customer customer) throws DAOException;
	
	/**
	 * Remove a Customer from the underling database (or any other persistence storage).
	 * @param custId a {@code long} Customer ID.
	 * @throws DAOException
	 */
	void removeCustomer(long custId) throws DAOException;
	
	/**
	 * Updates a Customer in the underling database (or any other persistence storage), from a given {@code Customer} object. The fields affected are Name, Password and Salt.
	 * @param customer a {@code Customer} object.
	 * @throws DAOException
	 */
	void updateCustomer(Customer customer) throws DAOException;
	
	/**
	 * Returns a {@code Customer} object from the underling database (or any other persistence storage).
	 * @param custId a {@code long} Customer ID.
	 * @return a {@code Customer} object.
	 * @throws DAOException
	 */
	Customer getCustomer(long custId) throws DAOException;
	
	/**
	 * Returns a {@code Set<Customer>} of all Customers from the underling database (or any other persistence storage).
	 * @return a {@code Set<Customer>}.
	 * @throws DAOException
	 */
	Set<Customer> getAllCustomers() throws DAOException;
	
	/**
	 * Returns a {@code Set<Coupon>} of all Coupons of a specific Customer, from the underling database (or any other persistence storage).
	 * @param custId a {@code long} Customer ID.
	 * @return a {@code Set<Coupon>}.
	 * @throws DAOException
	 */
	Set<Coupon> getCoupons(long custId) throws DAOException;
	
	/**
	 * Checks if the password and customer name given, matches the credentials stored in the underling database (or any other persistence storage).
	 * @param custName a {@code String} Customer name. 
	 * @param password a {@code char[]} password.
	 * @return
	 */
	boolean login(String custName, char[] password);
	
	/**
	 * Adds a correlation between a Coupon and a Customer in the underling database (or any other persistence storage).
	 * @param customer a {@code Customer} object.
	 * @param coupon a {@code Coupon} object.
	 * @throws DAOException
	 */
	void addCoupon(Customer customer, Coupon coupon) throws DAOException;
	
	/**
	 * Removes a correlation between a Coupon and a Customer in the underling database (or any other persistence storage).
	 * @param custId a {@code long} Customer ID.
	 * @param couponId a {@code long} Coupon ID.
	 * @throws DAOException
	 */
	void removeCoupon(long custId, long couponId) throws DAOException;
	
	/**
	 * Removes a correlation between a Coupon and a Customer in the underling database (or any other persistence storage).
	 * @param customer a {@code Customer} object.
	 * @param coupon a {@code Coupon} object.
	 * @throws DAOException
	 */
	void removeCoupon(Customer customer, Coupon coupon) throws DAOException;
}
