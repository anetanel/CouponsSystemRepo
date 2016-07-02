package com.netanel.coupons.clients;

import java.util.Set;

import com.netanel.coupons.crypt.Password;
import com.netanel.coupons.jbeans.Company;
import com.netanel.coupons.jbeans.Customer;

public class AdminFacade implements CouponClientFacade{
	
	public AdminFacade() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public CouponClientFacade login(String name, Password password, ClientType clientType) {
		// TODO Auto-generated method stub
		return null;
	}

	public void createCompany(Company company) {
		
	}
	
	public void deleteCompany(Company company){
		
	}
	
	public void updateCompanyDetails(Company company){
		
	}
	
	public Company getCompany(long compId) {
		Company company = null;
		return company;				
	}
	
	public Set<Company> getAllCompanies(){
		Set<Company> companies = null;
		return companies;
	}
	
	public void createCustomer(Customer customer){
		
	}
	
	public void deleteCustomer(Customer customer){
		
	}
	
	public void updateCustomerDetails(Customer customer){
		
	}
	
	public Customer getCustomer(long custID){
		Customer customer = null;
		return customer;
	}
	
	public Set<Customer> getAllCustomers(){
		Set<Customer> customers = null;
		return customers;
	}
}
