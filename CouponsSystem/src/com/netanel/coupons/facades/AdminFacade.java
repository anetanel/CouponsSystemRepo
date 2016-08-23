package com.netanel.coupons.facades;

import java.io.IOException;
import java.util.Set;

import com.netanel.coupons.app.CouponSystem;
import com.netanel.coupons.dao.*;
import com.netanel.coupons.email.EmailValidator;
import com.netanel.coupons.exception.*;
import com.netanel.coupons.jbeans.*;

/**
 * Coupon System Admin Facade
 */
public class AdminFacade implements CouponClientFacade {

	//
	// Attributes
	//
	private CompanyDAO compDao = null;
	private CustomerDAO custDao = null;
	private CouponDAO couponDao = null;
	// "Hard-coded" admin password:
	private static final String ADMIN_PASS = "1234";
	private static final String ADMIN_USERNAME = "admin";

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
		if (name.toLowerCase().equals(ADMIN_USERNAME) && String.valueOf(password).equals(ADMIN_PASS)
				&& clientType.equals(ClientType.ADMIN)) {
			return this;
		} else {
			throw new LoginException("Admin Login Failed.");
		}
	}

	/**
	 * Create new company in the Coupon System.
	 * @param company a {@code Company} object.
	 * @throws DAOException
	 */
	public void createCompany(Company company) throws DAOException {
		validateCompanyNameAndEmail(company);
		compDao.createCompany(company);
	}

	/**
	 * Delete a company from the Coupon System.
	 * @param company a {@code Company} object.
	 * @throws DAOException
	 * @throws IOException
	 */
	public void deleteCompany(Company company) throws DAOException, IOException {
		for (Coupon coupon : company.getCoupons()) {
			// Remove coupon from company
			compDao.removeCoupon(company.getId(), coupon.getId());
			// Remove coupon from all customers
			for (long custId : couponDao.getCustomersId(coupon)) {
				custDao.removeCoupon(custId, coupon.getId());
			}
			// Delete coupon
			couponDao.deleteCoupon(coupon);
		}
		// Remove company
		compDao.removeCompany(company);
	}

	/**
	 * Update a company's details
	 * @param company a {@code Company} object.
	 * @throws DAOException
	 */
	public void updateCompanyDetails(Company company) throws DAOException {
		validateCompanyNameAndEmail(company);
		compDao.updateCompany(company);
	}

	/**
	 * Returns a Company object by ID from the Coupon System.
	 * @param compId a {@code long} value of the requested company's ID.
	 * @return a {@code Company} object.
	 * @throws DAOException
	 */
	public Company getCompany(long compId) throws DAOException {
		return compDao.getCompany(compId);
	}

	/**
	 * Returns a Company object by name from the Coupon System.
	 * @param compName a {@code String} value of the requested company's name.
	 * @return a {@code Company} object.
	 * @throws DAOException
	 */
	public Company getCompany(String compName) throws DAOException {
		return compDao.getCompany(compName);
	}

	/**
	 * Returns all companies from the Coupon System.
	 * @return a {@code Set<Company>} of all the companies in the Coupon System.
	 * @throws DAOException
	 */
	public Set<Company> getAllCompanies() throws DAOException {
		return compDao.getAllCompanies();
	}

	/**
	 * Create a new customer in the Coupon System.
	 * @param customer a {@code Customer} object.
	 * @throws DAOException
	 */
	public void createCustomer(Customer customer) throws DAOException {
		validateCustomerName(customer);
		custDao.createCustomer(customer);
	}

	/**
	 * Delete a customer from the Coupon System.
	 * @param customer a {@code Customer} object.
	 * @throws DAOException
	 */
	public void deleteCustomer(Customer customer) throws DAOException {
		// Remove all Coupons from Customer
		for (Coupon coupon : customer.getCoupons()) {
			custDao.removeCoupon(customer, coupon);
		}
		// Remove Customer
		custDao.removeCustomer(customer);
	}

	/**
	 * Update a customer's details
	 * @param customer a {@code Customer} object.
	 * @throws DAOException
	 */
	public void updateCustomerDetails(Customer customer) throws DAOException {
		validateCustomerName(customer);
		custDao.updateCustomer(customer);
	}

	/**
	 * Returns a Customer object by ID from the Coupon System. 
	 * @param custId a {@code long} value if the customer's ID.
	 * @return a {@code Customer} object.
	 * @throws DAOException
	 */
	public Customer getCustomer(long custId) throws DAOException {
		return custDao.getCustomer(custId);
	}

	/**
	 * Returns a Customer object by name from the Coupon System.
	 * @param custName a {@code String} value of the customer's name.
	 * @return a {@code Customer} object.
	 * @throws DAOException
	 */
	public Customer getCustomer(String custName) throws DAOException {
		return custDao.getCustomer(custName);
	}

	/**
	 * Returns all customers from the Coupon System.
	 * @return a {@code Set<Customer>} of all the cudtomers in the Coupon System.
	 * @throws DAOException
	 */
	public Set<Customer> getAllCustomers() throws DAOException {
		return custDao.getAllCustomers();
	}

	// Validate company name and email
	private void validateCompanyNameAndEmail(Company company) throws DAOException {
		if (company.getCompName().equals("")) {
			throw new DAOException("Company name can't be empty.");
		}
		if (!EmailValidator.validate(company.getEmail())) {
			throw new DAOException("Invalid Email address!");
		}		
	}

	// Validate customer name
	private void validateCustomerName(Customer customer) throws DAOException {
		if (customer.getCustName().equals("")) {
			throw new DAOException("Customer name can't be empty.");
		}
	}

	@Override
	public String toString() {
		return "AdminFacade [compDao=" + compDao + ", custDao=" + custDao + ", couponDao=" + couponDao + "]";
	}
}
