package com.netanel.coupons.jbeans;

import java.util.Arrays;
import java.util.HashSet;

public class Customer {
	//
	// Attributes
	//
	private long id;
	private String custName;
	private char[]  password;
	private String salt;
	private HashSet<Coupon> coupons;
	
	//
	// Constructors
	//
	public Customer() {	}
	
	public Customer(String custName, char[] password) {
		this.custName = custName;
		this.password = password;
	}
	
	public Customer(long id, String custName, char[] password) {
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

	public char[] getPassword() {
		return password;
	}

	public String getSalt() {
		return salt;
	}

	public HashSet<Coupon> getCoupons() {
		return coupons;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public void setPassword(char[] password) {
		this.password = password;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public void setCoupons(HashSet<Coupon> coupons) {
		this.coupons = coupons;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", custName=" + custName + ", password=" + Arrays.toString(password) + ", salt="
				+ salt + ", coupons=" + coupons + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((coupons == null) ? 0 : coupons.hashCode());
		result = prime * result + ((custName == null) ? 0 : custName.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + Arrays.hashCode(password);
		result = prime * result + ((salt == null) ? 0 : salt.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Customer other = (Customer) obj;
		if (coupons == null) {
			if (other.coupons != null) {
				return false;
			}
		} else if (!coupons.equals(other.coupons)) {
			return false;
		}
		if (custName == null) {
			if (other.custName != null) {
				return false;
			}
		} else if (!custName.equals(other.custName)) {
			return false;
		}
		if (id != other.id) {
			return false;
		}
		if (!Arrays.equals(password, other.password)) {
			return false;
		}
		if (salt == null) {
			if (other.salt != null) {
				return false;
			}
		} else if (!salt.equals(other.salt)) {
			return false;
		}
		return true;
	}	
}
