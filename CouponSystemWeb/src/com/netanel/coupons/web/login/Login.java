package com.netanel.coupons.web.login;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.netanel.coupons.exception.DAOException;
import com.netanel.coupons.exception.LoginException;
import com.netanel.coupons.facades.AdminFacade;
import com.netanel.coupons.facades.ClientType;
import com.netanel.coupons.facades.CompanyFacade;
import com.netanel.coupons.facades.CouponClientFacade;
import com.netanel.coupons.facades.CustomerFacade;

@WebServlet({ "/Login", "/login" })
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Login() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		String username = request.getParameter("username");
		char[] password = request.getParameter("password").toCharArray();
		ClientType clientType = ClientType.valueOf(request.getParameter("clienttype"));

		CouponClientFacade facade = login(username, password, clientType);

		if (facade == null) {
			out.print("{\"login\": \"false\"}");
		} else {
			HttpSession session = request.getSession();
			session.setAttribute("FACADE", facade);
			session.setAttribute("CLIENT_TYPE", clientType);
			out.print("{\"login\": \"true\"}");
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private CouponClientFacade login(String username, char[] password, ClientType clientType) {
		CouponClientFacade facade = null;
		switch (clientType) {
		case ADMIN:
			facade = new AdminFacade();
			break;
		case CUSTOMER:
			facade = new CustomerFacade();
			break;
		case COMPANY:
			facade = new CompanyFacade();
			break;
		}
		try {
			facade = facade.login(username, password, clientType);
		} catch (LoginException | DAOException e) {
			return null;
		}
		return facade;

	}

}
