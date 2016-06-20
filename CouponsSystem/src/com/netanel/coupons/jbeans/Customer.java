package com.netanel.coupons.jbeans;

import java.util.HashSet;

import com.netanel.coupons.crypt.Password;

public class Customer {
	//
	// Attributes
	//
	private long id;
	private String custName;
	private Password password;
	private HashSet<Coupon> coupons;
	
	//
	// Constructors
	//
	public Customer() {	}
	
	public Customer(String custName, Password password) {
		this.custName = custName;
		this.password = password;
	}
	
	public Customer(long id, String custName, Password password) {
		this(custName, password);
		this.id = id;
	}

	//
	// Functions
	//
	public long getId() {
		return id;
	}

	public String getCustName() {
		return custName;
	}

	public Password getPassword() {
		return password;
	}


	public HashSet<Coupon> getCoupons() {
		return coupons;
	}

	
	public void setCustName(String custName) {
		this.custName = custName;
	}


	public void setCoupons(HashSet<Coupon> coupons) {
		this.coupons = coupons;
	}

	public void setPassword(char[] password) {
		this.password.setNewPassword(password);
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", custName=" + custName + ", password=" + password + ", coupons=" + coupons
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((coupons == null) ? 0 : coupons.hashCode());
		result = prime * result + ((custName == null) ? 0 : custName.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		if (coupons == null) {
			if (other.coupons != null)
				return false;
		} else if (!coupons.equals(other.coupons))
			return false;
		if (custName == null) {
			if (other.custName != null)
				return false;
		} else if (!custName.equals(other.custName))
			return false;
		if (id != other.id)
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}

	
}
