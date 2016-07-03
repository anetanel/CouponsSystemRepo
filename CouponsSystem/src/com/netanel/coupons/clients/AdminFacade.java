package com.netanel.coupons.clients;

import java.util.Set;

import com.netanel.coupons.crypt.Password;
import com.netanel.coupons.dao.CompanyDAO;
import com.netanel.coupons.dao.CustomerDAO;
import com.netanel.coupons.dao.db.CompanyDbDAO;
import com.netanel.coupons.dao.db.CustomerDbDAO;
import com.netanel.coupons.exception.DAOException;
import com.netanel.coupons.exception.LoginException;
import com.netanel.coupons.jbeans.Company;
import com.netanel.coupons.jbeans.Coupon;
import com.netanel.coupons.jbeans.Customer;

public class AdminFacade implements CouponClientFacade {

	public AdminFacade() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public CouponClientFacade login(String name, char[] password, ClientType clientType) throws LoginException {
		if (name.toLowerCase().equals("admin") && String.valueOf(password).equals("1234")
				&& clientType.equals(ClientType.ADMIN)) {
			return this;
		} else {
			throw new LoginException("Admin Login Failed. Incorrect parameters.");
		}
	}

	public void createCompany(Company company) throws DAOException {
		CompanyDAO compDao = new CompanyDbDAO();
		compDao.createCompany(company);
	}

	public void deleteCompany(Company company) throws DAOException {
		CompanyDAO compDao = new CompanyDbDAO();
		CustomerDAO custDao = new CustomerDbDAO();
		
		Set<Coupon> coupons = company.getCoupons();
		for (Coupon coupon : coupons) {
			compDao.removeCoupon(company.getId(), coupon.getId());
			//custDao.removeCoupon(customer, coupon);
		}
		compDao.removeCompany(company);
		
	}

	public void updateCompanyDetails(Company company) {

	}

	public Company getCompany(long compId) {
		Company company = null;
		return company;
	}

	public Set<Company> getAllCompanies() {
		Set<Company> companies = null;
		return companies;
	}

	public void createCustomer(Customer customer) {

	}

	public void deleteCustomer(Customer customer) {

	}

	public void updateCustomerDetails(Customer customer) {

	}

	public Customer getCustomer(long custID) {
		Customer customer = null;
		return customer;
	}

	public Set<Customer> getAllCustomers() {
		Set<Customer> customers = null;
		return customers;
	}
}
