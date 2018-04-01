package br.com.superpestana.springbootRest.resource;

import javax.security.auth.message.AuthException;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.superpestana.springbootRest.auth.JwtService;
import br.com.superpestana.springbootRest.model.ExceptionMessageResponse;
import br.com.superpestana.springbootRest.model.LoginResponse;
import br.com.superpestana.springbootRest.model.User;
import br.com.superpestana.springbootRest.service.LoginService;

@Configuration
@PropertySource("classpath:application.properties")
@Path("/login")
public class LoginResource {

	@Autowired
	private LoginService loginService;

	@Autowired
	private JwtService jwtService;

	@Value("${jwt.key}")
	String key;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(@RequestBody User user) throws AuthException {
		if (user != null) {
			User userFromDb = loginService.findByUserAndPassword(user.getUsername(), user.getPassword());
			if (userFromDb == null)
				throw new AuthException("Invalid user or password");

			String token = jwtService.generateNewToken(userFromDb);
			return Response.ok(new LoginResponse(token)).header("Authentication", token).build();
		}
		return Response.status(Status.UNAUTHORIZED).
				entity(new ExceptionMessageResponse("Invalid user or password", Status.UNAUTHORIZED))
				.build();
	}

}
