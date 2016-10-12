package com.netanel.coupons.web.services;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.netanel.coupons.exception.DAOException;
import com.netanel.coupons.facades.CompanyFacade;
import com.netanel.coupons.jbeans.Coupon;

@Path("company")
public class CompanyService {
	//
	// Attributes
	//
	@Context
	private HttpServletRequest request;
	
	private static final String FACADE = "FACADE"; 
	
	//
	// Constructors
	//
	public CompanyService() {
	}
	
	//
	// Functions
	//
	@POST
	@Path("createCoupon")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void createCoupon(Coupon coupon) throws DAOException {
		System.out.println(coupon.getTitle());
		System.out.println(coupon.getStartDate());
		System.out.println(coupon.getStartDate().getClass().getName());
		//getFacade().createCoupon(coupon);
	}
	
	@GET
	@Path("getallcoupons")
	@Produces(MediaType.APPLICATION_JSON)
	public Set<Coupon> getAllCoupons() throws DAOException {
		return getFacade().getAllCoupons();
	}
	
	@GET
	@Path("whoami")
	@Produces(MediaType.TEXT_PLAIN)
	public String whoAmI() throws DAOException {
		return getFacade().toString();
	}

	private CompanyFacade getFacade() throws DAOException {
		if (request.getSession().getAttribute(FACADE) instanceof CompanyFacade) {
			return (CompanyFacade) request.getSession().getAttribute(FACADE);
		} else {
			throw new DAOException("Could not find a Company login session");
		}
	}

	
}
