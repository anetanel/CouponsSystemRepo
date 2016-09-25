package com.netanel.coupons.web.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.netanel.coupons.exception.DAOException;

@Provider
public class DaoExceptionMapper implements ExceptionMapper<DAOException> {

	@Override
	public Response toResponse(DAOException ex) {
		Response.StatusType type = getStatusType(ex);

		ErrorBean error = new ErrorBean(type.getStatusCode(), type.getReasonPhrase(), ex.getLocalizedMessage());

		return Response.status(error.getStatusCode()).entity(error).type(MediaType.APPLICATION_JSON).build();
	}

	private Response.StatusType getStatusType(Throwable ex) {
		if (ex instanceof WebApplicationException) {
			return ((WebApplicationException) ex).getResponse().getStatusInfo();
		} else {
			return Response.Status.INTERNAL_SERVER_ERROR;
		}
	}
}
