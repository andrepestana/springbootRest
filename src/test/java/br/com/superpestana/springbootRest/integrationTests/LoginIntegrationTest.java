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
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import br.com.superpestana.springbootRest.Application;
import br.com.superpestana.springbootRest.model.User;
import br.com.superpestana.springbootRest.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginIntegrationTest {

	@LocalServerPort
	private int port;

	@Autowired
	private UserRepository userRepository;
	
	@Before
	public void before() {
		ClientConfig config = new ClientConfig();
		config.register(new LoggingFeature());
		
	}
	
	@Test
	public void loginIntegration() {
		ResponseEntity<String> user1LoginResponse = getUser1LoginResponseEntity();
		assertEquals("Response wrong status", HttpStatus.UNAUTHORIZED.value(), user1LoginResponse.getStatusCode().value());
		
		userRepository.save(createUser1());

		ResponseEntity<String> user1LoginResponse2 = getUser1LoginResponseEntity();
		assertEquals("Response wrong status", HttpStatus.OK.value(), user1LoginResponse2.getStatusCode().value());

	}
	
	private ResponseEntity<String> getUser1LoginResponseEntity() {
		User user = createUser1();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.ALL));
		HttpEntity<User> entity = new HttpEntity<User>(user, headers);
		
		
		SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
		simpleClientHttpRequestFactory.setOutputStreaming(false);
		RestTemplate restTemplate = new RestTemplate(simpleClientHttpRequestFactory);
		
		TestRestTemplate testRestTemplate = new TestRestTemplate(restTemplate);
		ResponseEntity<String> response = testRestTemplate.exchange(createURLWithPort("/rest/login"), HttpMethod.POST, entity,
				String.class);
		return response;
	}
	
	private User createUser1() {
		User user = new User("newUser", "xxx", "Neil", "User", Date
				.from(LocalDateTime.of(1988, Month.MARCH, 15, 0, 0, 0).atZone(ZoneId.systemDefault()).toInstant()));
		return user;
	}
	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}
}


