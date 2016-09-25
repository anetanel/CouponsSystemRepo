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
	
//	@GET
//	@Path("/login")
//	@Produces(MediaType.TEXT_PLAIN)
//	public String login(@QueryParam("username") String custName,
//				@QueryParam("password") String password){
//		CustomerFacade facade = null;
//		try {
//			facade = new CustomerFacade().login(custName, password.toCharArray(), ClientType.CUSTOMER);
//		} catch (LoginException | DAOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		if (facade == null) {
//			return "login failed";
//		}
//			
//		request.getSession().setAttribute(FACADE, facade);
//		return "login successful";
//	}

	@GET
	@Path("getallcoupons")
	@Produces(MediaType.APPLICATION_JSON)
	public Set<Coupon> getAllCoupons() {
		CustomerFacade facade = (CustomerFacade) request.getSession().getAttribute(FACADE);
		try {
			return facade.getAllCoupons();
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@GET
	@Path("whoami")
	@Produces(MediaType.APPLICATION_JSON)
	public String whoAmI() {
		CustomerFacade facade = (CustomerFacade) request.getSession().getAttribute(FACADE);
		return facade.toString();
	}

	
}
