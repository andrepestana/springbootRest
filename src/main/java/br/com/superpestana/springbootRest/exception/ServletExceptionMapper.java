package br.com.superpestana.springbootRest.exception;

import javax.servlet.ServletException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ServletExceptionMapper implements ExceptionMapper<ServletException>{

	@Override
	public Response toResponse(ServletException exception) {
		return Response.status(Status.FORBIDDEN).entity(exception.getMessage()).build(); 
	}

}
