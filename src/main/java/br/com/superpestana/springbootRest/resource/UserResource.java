package br.com.superpestana.springbootRest.resource;

import java.net.URI;
import java.util.stream.StreamSupport;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.superpestana.springbootRest.model.User;
import br.com.superpestana.springbootRest.service.UserService;

@Path("/auth/users")
public class UserResource {

	@Autowired
	private UserService service;
	
    @GET
    @Produces(MediaType.APPLICATION_JSON)
	public Response findAll() {
    	Iterable<User> users = service.findAll();
    	StreamSupport.stream(users.spliterator(), false).forEach(user -> user.setPassword("******"));
    	for (User user : users) {
			System.out.println(user);
		}
		return Response.ok(users).encoding("UTF-8").build();
	}

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("id") long id) {
    	User user = service.findById(id);
    	user.setPassword("******");
    	return Response.ok(user).build();
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response save(@RequestBody User user) {
    	User savedUser = service.save(user);
    	URI uri = URI.create("/auth/users/" + savedUser.getId());
    	return Response.created(uri).build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") long id, @RequestBody User user) {
    	user.setId(id);//overwrite id
    	service.save(user);
    	return Response.noContent().build();
    }
    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") long id) {
    	service.delete(id);
    	return Response.noContent().build();
    }
}
