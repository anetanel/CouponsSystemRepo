package com.netanel.coupons.clients;

import java.util.Set;

import com.netanel.coupons.dao.*;
import com.netanel.coupons.dao.db.*;
import com.netanel.coupons.exception.*;
import com.netanel.coupons.jbeans.*;

public class AdminFacade implements CouponClientFacade {

	//
	// Attributes
	//
	private CompanyDAO compDao;
	private CustomerDAO custDao;
	private CouponDAO couponDao;
	private static final String ADMIN_PASS = "1234";
	
	//
	// Constructors
	//
	public AdminFacade() {
		this.compDao = new CompanyDbDAO();
		this.custDao = new CustomerDbDAO();
		this.couponDao = new CouponDbDAO();
	}

	//
	// Functions
	//
	@Override
	public CouponClientFacade login(String name, char[] password, ClientType clientType) throws LoginException {
		if (name.toLowerCase().equals("admin") && String.valueOf(password).equals(ADMIN_PASS)
				&& clientType.equals(ClientType.ADMIN)) {
			return this;
		} else {
			throw new LoginException("Admin Login Failed. Incorrect parameters.");
		}
	}

	public void createCompany(Company company) throws DAOException {
		compDao.createCompany(company);
	}

	public void deleteCompany(Company company) throws DAOException {
		for (Coupon coupon : company.getCoupons()) {
			// Remove coupon from company
			compDao.removeCoupon(company.getId(), coupon.getId());
			//Remove coupon from all customers
			for (long custId : couponDao.getCustomersId(coupon)) {
				custDao.removeCoupon(custId, coupon.getId());
			}
			// Delete coupon
			couponDao.deleteCoupon(coupon);
		}
		// Remove company
		compDao.removeCompany(company);		
	}

	public void updateCompanyDetails(Company company) throws DAOException {
		compDao.updateCompany(company);
	}

	public Company getCompany(long compId) throws DAOException {
		return compDao.getCompany(compId);
	}

	public Set<Company> getAllCompanies() throws DAOException {
		return compDao.getAllCompanies();
	}

	public void createCustomer(Customer customer) throws DAOException {
		custDao.createCustomer(customer);
	}

	public void deleteCustomer(Customer customer) throws DAOException {
		// Remove all Coupons from Customer
		for (Coupon coupon : customer.getCoupons()) {
			custDao.removeCoupon(customer, coupon);
		}
		// Remove Customer
		custDao.removeCustomer(customer);
	}

	public void updateCustomerDetails(Customer customer) throws DAOException {
		custDao.updateCustomer(customer);
	}

	public Customer getCustomer(long custId) throws DAOException {
		return custDao.getCustomer(custId);
	}

	public Set<Customer> getAllCustomers() throws DAOException {
		return custDao.getAllCustomers();
	}

	@Override
	public String toString() {
		return "AdminFacade [compDao=" + compDao + ", custDao=" + custDao + ", couponDao=" + couponDao + "]";
	}
}
