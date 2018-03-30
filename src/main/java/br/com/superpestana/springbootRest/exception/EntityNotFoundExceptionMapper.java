package br.com.superpestana.springbootRest.exception;

import javax.persistence.EntityNotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class EntityNotFoundExceptionMapper implements ExceptionMapper<EntityNotFoundException>{

	@Override
	public Response toResponse(EntityNotFoundException exception) {
		return Response.status(Status.NOT_FOUND).entity(exception.getMessage()).build(); 
	}
}
