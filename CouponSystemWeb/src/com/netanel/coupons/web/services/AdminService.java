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
	
	private AdminFacade facade = (AdminFacade) request.getAttribute("FACADE");
	
	public AdminService() {
		// TODO Auto-generated constructor stub
	}
	
	@GET
	@Path("getallcompanies")
	@Produces(MediaType.APPLICATION_JSON)
	public Set<Company> getAllCompanies() throws DAOException {
		return facade.getAllCompanies();
	}
	
	@GET
	@Path("/whoami")
	@Produces(MediaType.APPLICATION_JSON)
	public String whoAmI() {
		return facade.toString();
	}
	


}
