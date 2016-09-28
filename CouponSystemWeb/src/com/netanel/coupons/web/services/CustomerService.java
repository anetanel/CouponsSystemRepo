package com.netanel.coupons.web.services;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.netanel.coupons.exception.DAOException;
import com.netanel.coupons.facades.CustomerFacade;
import com.netanel.coupons.jbeans.Coupon;

@Path("customer")
public class CustomerService {
	@Context
	private HttpServletRequest request;
	
	private static final String FACADE = "FACADE"; 
	
	public CustomerService() {
		
	}
	
	@GET
	@Path("getallcoupons")
	@Produces(MediaType.APPLICATION_JSON)
	public Set<Coupon> getAllCoupons() throws DAOException {
		CustomerFacade facade = getFacade();
		return facade.getAllCoupons();
	}
	
	@GET
	@Path("whoami")
	@Produces(MediaType.APPLICATION_JSON)
	public String whoAmI() throws DAOException {
		CustomerFacade facade = getFacade();
		return facade.toString();
	}

	private CustomerFacade getFacade() throws DAOException {
		if (request.getSession().getAttribute(FACADE) instanceof CustomerFacade) {
			return (CustomerFacade) request.getSession().getAttribute(FACADE);
		} else {
			throw new DAOException("Could not find an Admin login session");
		}
	}
	
}
