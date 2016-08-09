package com.netanel.coupons.facades;

import java.io.IOException;
import java.util.Set;

import com.netanel.coupons.app.CouponSystem;
import com.netanel.coupons.dao.*;
import com.netanel.coupons.email.EmailValidator;
import com.netanel.coupons.exception.*;
import com.netanel.coupons.jbeans.*;

public class AdminFacade implements CouponClientFacade {

	//
	// Attributes
	//
	private CompanyDAO compDao = null;
	private CustomerDAO custDao = null;
	private CouponDAO couponDao = null;
	private static final String ADMIN_PASS = "1234";
	
	//
	// Constructors
	//
	public AdminFacade() {
		compDao = CouponSystem.getInstance().getCompDao();
		custDao = CouponSystem.getInstance().getCustDao();
		couponDao = CouponSystem.getInstance().getCouponDao();
	}

	//
	// Functions
	//
	@Override
	public AdminFacade login(String name, char[] password, ClientType clientType) throws LoginException {
		if (name.toLowerCase().equals("admin") && String.valueOf(password).equals(ADMIN_PASS)
				&& clientType.equals(ClientType.ADMIN)) {
			return this;
		} else {
			throw new LoginException("Admin Login Failed.");
		}
	}

	public void createCompany(Company company) throws DAOException {
		if (company.getCompName().equals("")){
			throw new DAOException("Company name can't be empty.");
		}
		if (!EmailValidator.validate(company.getEmail())){
			throw new DAOException("Invalid Email address!");
		}
		compDao.createCompany(company);
	}

	public void deleteCompany(Company company) throws DAOException, IOException {
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
		if (company.getCompName().equals("")){
			throw new DAOException("Company name can't be empty.");
		}
		if (!EmailValidator.validate(company.getEmail())) {
			throw new DAOException("Invalid Email address!");
		}
		compDao.updateCompany(company);
	}

	public Company getCompany(long compId) throws DAOException {
		return compDao.getCompany(compId);
	}

	public Company getCompany(String compName) throws DAOException {
		return compDao.getCompany(compName);
	}
	
	public Set<Company> getAllCompanies() throws DAOException {
		return compDao.getAllCompanies();
	}

	public void createCustomer(Customer customer) throws DAOException {
		if (customer.getCustName().equals("")){
			throw new DAOException("Customer name can't be empty.");
		}
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
		if (customer.getCustName().equals("")){
			throw new DAOException("Customer name can't be empty.");
		}
		custDao.updateCustomer(customer);
	}

	public Customer getCustomer(long custId) throws DAOException {
		return custDao.getCustomer(custId);
	}

	public Customer getCustomer(String custName) throws DAOException {
		return custDao.getCustomer(custName);
	}
	
	public Set<Customer> getAllCustomers() throws DAOException {
		return custDao.getAllCustomers();
	}

	@Override
	public String toString() {
		return "AdminFacade [compDao=" + compDao + ", custDao=" + custDao + ", couponDao=" + couponDao + "]";
	}
}
