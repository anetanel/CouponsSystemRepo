package com.netanel.coupons.web.services;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.netanel.coupons.exception.DAOException;
import com.netanel.coupons.facades.AdminFacade;
import com.netanel.coupons.jbeans.Company;

@Path("admin")
public class AdminService {

	@Context HttpServletRequest request;
	
	private static final String FACADE = "FACADE";
	
	public AdminService() {
	}
	
	@GET
	@Path("getallcompanies")
	@Produces(MediaType.APPLICATION_JSON)
	public Set<Company> getAllCompanies() throws DAOException {
		AdminFacade facade = (AdminFacade) request.getSession().getAttribute(FACADE);
		return facade.getAllCompanies();
	}
	
	@GET
	@Path("whoami")
	@Produces(MediaType.APPLICATION_JSON)
	public String whoAmI() {
		AdminFacade facade = (AdminFacade) request.getSession().getAttribute(FACADE);
		return facade.toString();
	}
	


}
