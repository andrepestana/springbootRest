package br.com.superpestana.springbootRest.integrationTests;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.logging.LoggingFeature;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.superpestana.springbootRest.Application;
import br.com.superpestana.springbootRest.model.User;
import br.com.superpestana.springbootRest.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserIntegrationTest {

	@LocalServerPort
	private int port;
	
	@Autowired
	private UserRepository userRepository;

	@Before
	public void before() {
		ClientConfig config = new ClientConfig();
		config.register(new LoggingFeature());
		
		User admin = getAdminUser();
		userRepository.save(admin);
	}

	private User getAdminUser() {
		User admin = new User("admin", "admin", "Admin","of Universe", new Date());
		return admin;
	}

	@Test
	public void userIntegration() {
		
		// Admin logon 
		ResponseEntity<User> adminLoginResponse = getAdminLoginResponseEntity();
		assertEquals("Response wrong status", HttpStatus.OK.value(), adminLoginResponse.getStatusCode().value());

		String token = adminLoginResponse.getHeaders().get("Authentication").get(0);
		
		
		// Create user 1
		ResponseEntity<User> user1Response = createUser1ResponseEntity(token);
		assertEquals("Response wrong status", HttpStatus.CREATED.value(), user1Response.getStatusCode().value());

		// Create user 2
		ResponseEntity<User> user2Response = createUser2ResponseEntity(token);
		assertEquals("Response wrong status", HttpStatus.CREATED.value(), user2Response.getStatusCode().value());

		// Create user 1 again
		ResponseEntity<String> user1TwiceResponse = createUser1TwiceUserResponseEntity(token);
		assertEquals("Response wrong status", HttpStatus.BAD_REQUEST.value(),
				user1TwiceResponse.getStatusCode().value());

		// Get user 2 from application
		ResponseEntity<User> user2ResponseFromApplication = getUser2ResponseEntity(token);
		assertEquals("Response wrong status", HttpStatus.OK.value(),
				user2ResponseFromApplication.getStatusCode().value());
		assertEquals("John", user2ResponseFromApplication.getBody().getUsername());
		assertEquals("Doe", user2ResponseFromApplication.getBody().getLastName());

		// Update user 2
		User user2 = user2ResponseFromApplication.getBody();
		user2.setFirstName("Jon");
		user2.setLastName("Does");
		user2.setId(10l);
		ResponseEntity<User> updateUser2Response = updateUser2ResponseEntity(user2, token);
		assertEquals("Response wrong status", HttpStatus.NO_CONTENT.value(),
				updateUser2Response.getStatusCode().value());
		ResponseEntity<User> user2ResponseFromApplication2 = getUser2ResponseEntity(token);
		assertEquals(new Long(3), user2ResponseFromApplication2.getBody().getId());

		// Get all user from application
		ResponseEntity<User[]> allUsersResponseFromApplication = getAllUsersResponseEntity(token);
		assertEquals("Response wrong status", HttpStatus.OK.value(),
				allUsersResponseFromApplication.getStatusCode().value());
		User[] users = allUsersResponseFromApplication.getBody();
		assertEquals(3, users.length);
		assertEquals("Admin", users[0].getFirstName());
		assertEquals("André", users[1].getFirstName());
		assertEquals("Jon", users[2].getFirstName());

		// Delete user 2
		ResponseEntity<User> deleteUser2Response = deleteUser2ResponseEntity(token);
		assertEquals("Response wrong status", HttpStatus.NO_CONTENT.value(),
				deleteUser2Response.getStatusCode().value());

		// Get all user from application
		ResponseEntity<User[]> allUsersResponseFromApplication2 = getAllUsersResponseEntity(token);
		assertEquals("Response wrong status", HttpStatus.OK.value(),
				allUsersResponseFromApplication2.getStatusCode().value());
		User[] users2 = allUsersResponseFromApplication2.getBody();
		assertEquals(2, users2.length);
		assertEquals("André", users[1].getFirstName());

	}

	private ResponseEntity<User> getAdminLoginResponseEntity() {
		User admin = getAdminUser();
		HttpHeaders headers = setHeaders(null);
		HttpEntity<User> entity = new HttpEntity<User>(admin, headers);
		TestRestTemplate restTemplate = new TestRestTemplate();
		ResponseEntity<User> response = restTemplate.exchange(createURLWithPort("/rest/login"), HttpMethod.POST, entity,
				User.class);
		return response;
	}
	
	private ResponseEntity<User> updateUser2ResponseEntity(User user2, String token) {
		HttpHeaders headers = setHeaders(token);
		HttpEntity<User> entity = new HttpEntity<User>(user2, headers);
		TestRestTemplate restTemplate = new TestRestTemplate();
		ResponseEntity<User> response = restTemplate.exchange(createURLWithPort("/rest/auth/users/3"), HttpMethod.PUT, entity,
				User.class);
		return response;
	}

	private ResponseEntity<User> deleteUser2ResponseEntity(String token) {
		HttpHeaders headers = setHeaders(token);
		HttpEntity<User> entity = new HttpEntity<User>(headers);
		TestRestTemplate restTemplate = new TestRestTemplate();
		ResponseEntity<User> response = restTemplate.exchange(createURLWithPort("/rest/auth/users/3"), HttpMethod.DELETE, entity,
				User.class);
		return response;
	}

	private ResponseEntity<String> createUser1TwiceUserResponseEntity(String token) {
		User user = createUser1();
		HttpHeaders headers = setHeaders(token);
		HttpEntity<User> entity = new HttpEntity<User>(user, headers);
		TestRestTemplate restTemplate = new TestRestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/rest/auth/users"), HttpMethod.POST, entity,
				String.class);
		return response;
	}

	private ResponseEntity<User> createUser1ResponseEntity(String token) {
		User user = createUser1();
		HttpHeaders headers = setHeaders(token);
		HttpEntity<User> entity = new HttpEntity<User>(user, headers);
		TestRestTemplate restTemplate = new TestRestTemplate();
		ResponseEntity<User> response = restTemplate.exchange(createURLWithPort("/rest/auth/users"), HttpMethod.POST, entity,
				User.class);
		return response;
	}

	private ResponseEntity<User> createUser2ResponseEntity(String token) {
		User user2 = createUser2();
		HttpHeaders headers = setHeaders(token);
		HttpEntity<User> entity = new HttpEntity<User>(user2, headers);
		TestRestTemplate restTemplate = new TestRestTemplate();
		ResponseEntity<User> response = restTemplate.exchange(createURLWithPort("/rest/auth/users"), HttpMethod.POST, entity,
				User.class);
		return response;
	}

	private ResponseEntity<User> getUser2ResponseEntity(String token) {
		HttpHeaders headers = setHeaders(token);
		HttpEntity<User> entity = new HttpEntity<User>(headers);
		TestRestTemplate restTemplate = new TestRestTemplate();
		ResponseEntity<User> response = restTemplate.exchange(createURLWithPort("/rest/auth/users/3"), HttpMethod.GET, entity,
				User.class);
		return response;
	}

	private ResponseEntity<User[]> getAllUsersResponseEntity(String token) {
		HttpHeaders headers = setHeaders(token);
		HttpEntity<User> entity = new HttpEntity<User>(headers);
		TestRestTemplate restTemplate = new TestRestTemplate();
		ResponseEntity<User[]> response = restTemplate.exchange(createURLWithPort("/rest/auth/users"), HttpMethod.GET, entity,
				User[].class);
		return response;
	}

	private HttpHeaders setHeaders(String token) {
		HttpHeaders headers = new HttpHeaders();
		if(token != null){
			headers.add("Authentication", token);
		}
		headers.setAccept(Collections.singletonList(MediaType.ALL));
		return headers;
	}

	private User createUser1() {
		User user = new User("andre", "xxx", "André", "Pestana", Date
				.from(LocalDateTime.of(1978, Month.DECEMBER, 1, 0, 0, 0).atZone(ZoneId.systemDefault()).toInstant()));
		return user;
	}

	private User createUser2() {
		User user2 = new User("John", "xxx", "John", "Doe", Date
				.from(LocalDateTime.of(2000, Month.JANUARY, 1, 0, 0, 0).atZone(ZoneId.systemDefault()).toInstant()));
		return user2;
	}

	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}
}
