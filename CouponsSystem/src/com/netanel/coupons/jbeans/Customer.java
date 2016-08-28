package com.netanel.coupons.jbeans;

import java.util.Set;

import com.netanel.coupons.crypt.Password;

/**
 * Coupon System Customer JBean Class. Extends {@link Client}.
 */
public class Customer extends Client{
	
	//
	// Constructors
	//

	public Customer() {
		super();
	}

	public Customer(long id, String name, Password password, Set<Coupon> coupons) {
		super(id, name, password, coupons);
	}

	public Customer(String name, Password password, Set<Coupon> coupons) {
		super(name, password, coupons);
	}

	//
	// Functions
	//
	@Override
	public String toString() {
		return "Customer [id=" + id + ", password=" + password + ", coupons=" + coupons + ", name=" + name + "]";
	}
	

	
}
