package com.netanel.coupons.web.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.netanel.coupons.exception.DAOException;

@Provider
public class DaoExceptionMapper extends DAOException implements ExceptionMapper<DAOException>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Response toResponse(DAOException ex) {
		System.out.println("in mapper");
		return Response.status(Response.Status.NOT_FOUND).build();
	}

}
