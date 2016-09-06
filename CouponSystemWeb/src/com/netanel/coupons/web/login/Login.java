package com.netanel.coupons.web.login;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.netanel.coupons.exception.DAOException;
import com.netanel.coupons.exception.LoginException;
import com.netanel.coupons.facades.ClientType;
import com.netanel.coupons.facades.CustomerFacade;

@WebServlet({ "/Login", "/login" })
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public Login() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		CustomerFacade facade = null;
		try {
			facade = new CustomerFacade().login(request.getParameter("username"), request.getParameter("password").toCharArray(),
					ClientType.CUSTOMER);
		} catch (LoginException | DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (facade == null) {
			response.getWriter().println("login failed");
		}
			
		request.getSession().setAttribute("FACADE", facade);
		response.getWriter().println("login successful");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	
}
