package com.netanel.coupons.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(urlPatterns = { "/LoginFilter" }, servletNames = { "Jersey REST Service" })
public class LoginFilter implements Filter {

    public LoginFilter() {
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		//TODO: remove system outs
		System.out.println("Filtered!");

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String url = req.getContextPath() + "/loginpage.html";
		HttpSession session = req.getSession(false);
		
		if (session == null) {
			System.out.println("no session!");
			System.out.println("redirect to " + url);
			res.sendRedirect(url);
			return;
		} else if (session.getAttribute("FACADE") == null){
			System.out.println("no facade in session!");
			System.out.println("redirect to " + url);
			res.sendRedirect(url);
			return;
		} else {
			
			System.out.println(session.getAttribute("FACADE").getClass().getSimpleName() + " found in session!");
		}
		
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}


	public void init(FilterConfig fConfig) throws ServletException {
	}

}
