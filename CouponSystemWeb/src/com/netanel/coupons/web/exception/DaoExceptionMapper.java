package com.netanel.coupons.web.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.netanel.coupons.exception.DAOException;

@Provider
public class DaoExceptionMapper implements ExceptionMapper<DAOException> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Response toResponse(DAOException exception) {
		System.out.println("in mapper");
		return Response.status(Status.BAD_REQUEST).entity(exception.getMessage()).build();
	}

}
