package com.netanel.coupons.web.services;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import com.netanel.coupons.crypt.Password;
import com.netanel.coupons.exception.DAOException;
import com.netanel.coupons.facades.AdminFacade;
import com.netanel.coupons.jbeans.Company;
import com.netanel.coupons.jbeans.Coupon;

@Path("admin")
public class AdminService {

	@Context
	HttpServletRequest request;

	private static final String FACADE = "FACADE";

	public AdminService() {
	}

	@POST
	@Path("createcompany")
	@Consumes(MediaType.APPLICATION_JSON)
	public void createCompany(Map<String, String> map) throws DAOException {
		AdminFacade facade = getFacade();
		Company company = new Company(map.get("name"), 
				new Password(map.get("password").toCharArray()), 
				map.get("email"),
				new HashSet<Coupon>());
		facade.createCompany(company);
	}
		
	@GET
	@Path("getallcompanies")
	@Produces(MediaType.APPLICATION_JSON)
	public Set<Company> getAllCompanies() throws DAOException {
		AdminFacade facade = getFacade();
		return facade.getAllCompanies();
	}

	@GET
	@Path("whoami")
	@Produces(MediaType.TEXT_PLAIN)
	public String whoAmI() throws DAOException {
		AdminFacade facade = getFacade();
		return facade.toString();
	}

	private AdminFacade getFacade() throws DAOException {
		if (request.getSession().getAttribute(FACADE) instanceof AdminFacade) {
			return (AdminFacade) request.getSession().getAttribute(FACADE);
		} else {
			throw new DAOException("Could not find an Admin login session");
		}
	}

}
