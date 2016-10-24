package com.netanel.coupons.jbeans;

import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;

import com.netanel.coupons.crypt.Password;

/**
 * Coupon System Client JBean Superclass. 
 *
 */
@XmlRootElement
@XmlSeeAlso({Customer.class,Company.class})
public class Client {
	//
	// Attributes
	//
	protected long id = -1;
	protected Password password;
	protected Set<Coupon> coupons;
	protected String name;
	
	//
	// Constructors
	//
	public Client() {
	}
	
	public Client(String name, Password password, Set<Coupon> coupons) {
		this.name = name;
		this.password = password;
		this.coupons = coupons;
	}
	
	public Client(long id, String name, Password password, Set<Coupon> coupons) {
		this(name, password, coupons);
		this.id = id;
	}
	
	
	//
	// Functions
	//
	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Password getPassword() {
		return password;
	}

	public Set<Coupon> getCoupons() {
		return coupons;
	}
	
	public void setId(long id) {
		if (this.id == -1) { 
			this.id = id;
		} else {
			throw new IllegalArgumentException("ID already set" + this.id);
		}
	}
	
	public void setName(String name) {
		this.name = name;
	}


	public void setCoupons(Set<Coupon> coupons) {
		this.coupons = coupons;
	}

	public void setPassword(char[] password) {
		this.password.setNewPassword(password);
	}

	
	/**
	 * Get Client details. Used by table model. 
	 * @return an {@code Object[]} array of {@code [long id, String name, int number_of_coupons]}.
	 */
	@XmlTransient
	public Object[] getDetails() {
		Object[] detail = {getId(), getName(), getCoupons().size()};
		return detail;
	}

	@Override
	public String toString() {
		return "Client [id=" + id + ", password=" + password + ", coupons=" + coupons + ", name=" + name + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((coupons == null) ? 0 : coupons.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Client other = (Client) obj;
		if (coupons == null) {
			if (other.coupons != null)
				return false;
		} else if (!coupons.equals(other.coupons))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}
	
	
}
