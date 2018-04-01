package br.com.superpestana.springbootRest.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.springframework.dao.DataIntegrityViolationException;

import br.com.superpestana.springbootRest.model.ExceptionMessageResponse;

@Provider
public class DataIntegrityViolationExceptionMapper implements ExceptionMapper<DataIntegrityViolationException>{

	@Override
	public Response toResponse(DataIntegrityViolationException exception) {
		return Response.status(Status.BAD_REQUEST)
				.entity(new ExceptionMessageResponse(exception.getClass().getName().toString(), Status.BAD_REQUEST))
				.build(); 
	}

}
