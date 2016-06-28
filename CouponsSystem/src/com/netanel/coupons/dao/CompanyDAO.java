package com.netanel.coupons.dao;

import java.util.*;

import com.netanel.coupons.exception.DAOException;
import com.netanel.coupons.jbeans.*;

public interface CompanyDAO {
	public long createCompany(Company company) throws DAOException;
	
	public void removeCompany(long compId) throws DAOException;
	public void removeCompany(String compName) throws DAOException;
	public void removeCompany(Company company) throws DAOException;
	
	public void updateCompany(Company company) throws DAOException;
	
	public Company getCompany(long compId) throws DAOException;
	
	public Set<Company> getAllCompanies() throws DAOException;
	
	public Set<Coupon> getCoupons(long CompId) throws DAOException;
	
	public boolean login(String compName, char[] password);
	
	public void issueCoupon(Company company, Coupon coupon) throws DAOException;
	public void removeCoupon(Coupon coupon) throws DAOException;
	public void removeCoupon(long couponId) throws DAOException;
	
}
