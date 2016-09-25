package com.netanel.coupons.web.services;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.netanel.coupons.exception.DAOException;
import com.netanel.coupons.facades.CompanyFacade;
import com.netanel.coupons.jbeans.Coupon;

@Path("company")
public class CompanyService {
	@Context
	private HttpServletRequest request;
	
	private static final String FACADE = "FACADE"; 
	
	public CompanyService() {
	}
	
	@GET
	@Path("getallcoupons")
	@Produces(MediaType.APPLICATION_JSON)
	public Set<Coupon> getAllCoupons() {
		CompanyFacade facade = (CompanyFacade) request.getSession().getAttribute(FACADE);
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
		CompanyFacade facade = (CompanyFacade) request.getSession().getAttribute(FACADE);
		return facade.toString();
	}

	
}
