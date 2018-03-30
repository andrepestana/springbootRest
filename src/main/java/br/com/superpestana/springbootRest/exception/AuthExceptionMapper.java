package br.com.superpestana.springbootRest.exception;

import javax.security.auth.message.AuthException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class AuthExceptionMapper implements ExceptionMapper<AuthException>{

	@Override
	public Response toResponse(AuthException exception) {
		return Response.status(Status.UNAUTHORIZED).entity(exception.getMessage()).build(); 
	}

}
