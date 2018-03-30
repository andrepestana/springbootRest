package br.com.superpestana.springbootRest.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.springframework.dao.DataIntegrityViolationException;

@Provider
public class DataIntegrityViolationExceptionMapper implements ExceptionMapper<DataIntegrityViolationException>{

	@Override
	public Response toResponse(DataIntegrityViolationException exception) {
		return Response.status(Status.BAD_REQUEST).entity(exception.getClass().toString()).build(); 
	}

}
