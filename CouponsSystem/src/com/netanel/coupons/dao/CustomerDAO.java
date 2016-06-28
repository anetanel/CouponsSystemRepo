package com.netanel.coupons.dao;

import java.util.Set;

import com.netanel.coupons.jbeans.Coupon;
import com.netanel.coupons.jbeans.Customer;

public interface CustomerDAO {
	public long createCustomer(Customer customer);
	
	public void removeCustomer(Customer customer);
	public void removeCustomer(long custId);
	public void removeCustomer(String custName);
		
	public void updateCustomer(Customer customer);
	
	public Customer getCustomer(long custId);
	
	public Set<Customer> getAllCustomers();
	
	public Set<Coupon> getCoupons(long custId);
	
	public boolean login(String custName, char[] password);
	
	public void addCoupon(Customer customer, Coupon coupon);
}
