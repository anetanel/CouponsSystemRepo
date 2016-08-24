package com.netanel.coupons.jbeans;

import java.util.Set;

import com.netanel.coupons.crypt.Password;

public class Company extends Client{
	//
	// Attributes
	//
	private String email;	
	
	//
	// Constructors
	//
	public Company() {
		super();
	}

	public Company(long id, String name, Password password, String email, Set<Coupon> coupons) {
		super(id, name, password, coupons);
		setEmail(email);
	}


	public Company(String name, Password password, String email, Set<Coupon> coupons) {
		super(name, password, coupons);
		setEmail(email);		
	}

	//
	// Functions
	//
	public String getEmail() {
		return email;
	}
		
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public Object[] getDetails() {
		Object[] detail = {getId(), getName(), getEmail(), getCoupons().size()};
		return detail;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Company other = (Company) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}
	

	
}
