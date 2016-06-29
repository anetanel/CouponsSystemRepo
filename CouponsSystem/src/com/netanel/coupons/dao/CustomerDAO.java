package com.netanel.coupons.dao;

import java.util.Set;

import com.netanel.coupons.exception.DAOException;
import com.netanel.coupons.jbeans.Coupon;
import com.netanel.coupons.jbeans.Customer;

public interface CustomerDAO {
	long createCustomer(Customer customer) throws DAOException;
	
	void removeCustomer(Customer customer);
	void removeCustomer(long custId);

	void updateCustomer(Customer customer);
	
	Customer getCustomer(long custId);
	
	Set<Customer> getAllCustomers();
	
	Set<Coupon> getCoupons(long custId);
	
	boolean login(String custName, char[] password);
	
	void addCoupon(Customer customer, Coupon coupon);
}
