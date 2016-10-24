package com.netanel.coupons.web.services;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import com.netanel.coupons.crypt.Password;
import com.netanel.coupons.exception.DAOException;
import com.netanel.coupons.facades.AdminFacade;
import com.netanel.coupons.jbeans.Company;
import com.netanel.coupons.jbeans.Coupon;
import com.netanel.coupons.jbeans.Customer;

@Path("admin")
public class AdminService {

	//
	// Attributes
	//
	@Context
	HttpServletRequest request;

	private static final String FACADE = "FACADE";

	//
	// Constructors
	//
	public AdminService() {
	}

	
	//
	// Functions
	//
	@POST
	@Path("testCompany")
	@Consumes(MediaType.APPLICATION_JSON)
	public void testCompany(Company company) {
		System.out.println("in testCompany");
		System.out.println(company);
	}
	
	@GET
	@Path("getPass")
	@Produces(MediaType.APPLICATION_JSON)
	public Password getPass() {
		return new Password("1234".toCharArray());
	}
	
	@POST
	@Path("createcompany")
	@Consumes(MediaType.APPLICATION_JSON)
	public void createCompany(Map<String, String> companyMap) throws DAOException {
		Company company = new Company(companyMap.get("name"), 
				new Password(companyMap.get("password").toCharArray()), 
				companyMap.get("email"),
				new HashSet<Coupon>());
		getFacade().createCompany(company);
	}
	
	@DELETE
	@Path("deletecompany")
	//TODO: deal with IO exception
	public void deleteCompany(@QueryParam("compId") long compId) throws DAOException, IOException {
		getFacade().deleteCompany(getCompany(compId));
	}
	
	@POST
	@Path("updatecompany")
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateCompanyDetails(Map<String, String> companyMap) throws DAOException {
		Company company = new Company(Long.parseLong(companyMap.get("id")),
				companyMap.get("name"), 
				new Password(companyMap.get("password").toCharArray()), 
				companyMap.get("email"),
				null);
		getFacade().updateCompanyDetails(company);
	}
	
	@GET
	@Path("getcompanybyid")
	@Produces(MediaType.APPLICATION_JSON)
	public Company getCompany(@QueryParam("compId") long compId) throws DAOException {
		System.out.println(getFacade().getCompany(compId));
		return getFacade().getCompany(compId);
	}
	
	@GET
	@Path("getcompanybyname")
	@Produces(MediaType.APPLICATION_JSON)
	public Company getCompany(@QueryParam("compName") String compName) throws DAOException {
		return getFacade().getCompany(compName);
	}
	
	@GET
	@Path("getallcompanies")
	@Produces(MediaType.APPLICATION_JSON)
	public Set<Company> getAllCompanies() throws DAOException {
		return getFacade().getAllCompanies();
	}

	
	@GET
	@Path("getcustomerbyid")
	@Produces(MediaType.APPLICATION_JSON)
	public Customer getCustomer(@QueryParam("custId") long custId) throws DAOException {
		return getFacade().getCustomer(custId);
	}
	
	@GET
	@Path("getcustomerbyname")
	@Produces(MediaType.APPLICATION_JSON)
	public Customer getCustomer(@QueryParam("custName") String custName) throws DAOException {
		return getFacade().getCustomer(custName);
	}
	
	@GET
	@Path("getallcustomers")
	@Produces(MediaType.APPLICATION_JSON)
	public Set<Customer> getAllCustomers() throws DAOException {
		return getFacade().getAllCustomers();
	}
	
	@POST
	@Path("createcustomer")
	@Consumes(MediaType.APPLICATION_JSON)
	public void createCustomer(Map<String, String> customerMap) throws DAOException {
		Customer customer = new Customer(customerMap.get("name"), 
				new Password(customerMap.get("password").toCharArray()), 
				new HashSet<Coupon>());
		getFacade().createCustomer(customer);
	}
	
	@DELETE
	@Path("deletecustomer")
	public void deleteCustomer(@QueryParam("custId") long custId) throws DAOException {
		getFacade().deleteCustomer(getCustomer(custId));
	}
	
	@POST
	@Path("updatecustomer")
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateCustomerDetails(Map<String, String> customerMap) throws DAOException {
		Customer customer = new Customer(Long.parseLong(customerMap.get("id")),
				customerMap.get("name"), 
				new Password(customerMap.get("password").toCharArray()), 
				null);
		getFacade().updateCustomerDetails(customer);
	}
	
	@GET
	@Path("whoami")
	@Produces(MediaType.TEXT_PLAIN)
	public String whoAmI() throws DAOException {
		return getFacade().toString();
	}

	private AdminFacade getFacade() throws DAOException {
		if (request.getSession().getAttribute(FACADE) instanceof AdminFacade) {
			return (AdminFacade) request.getSession().getAttribute(FACADE);
		} else {
			throw new DAOException("Could not find an Admin login session");
		}
	}

}
