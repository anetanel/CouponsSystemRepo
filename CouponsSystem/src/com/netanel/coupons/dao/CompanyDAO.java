package com.netanel.coupons.dao;

import java.util.*;

import com.netanel.coupons.jbeans.*;

public interface CompanyDAO {
	public long createCompany(Company company);
	
	public void removeCompany(long compId);
	public void removeCompany(String compName);
	public void removeCompany(Company company);
	
	public void updateCompany(Company company);
	
	public Company getCompany(long compId);
	
	public Set<Company> getAllCompanies();
	
	public Set<Coupon> getCoupons(long CompId);
	
	public boolean login(String compName, char[] password);
	
	public void addCoupon(Company company, Coupon coupon);
	
}
