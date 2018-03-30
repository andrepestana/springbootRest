package br.com.superpestana.springbootRest;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.superpestana.springbootRest.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SmokeApplicationTests {

	@Autowired
	private UserService service;
	
	@Test
	public void contextLoads() {
		Assert.assertTrue(service!=null);
	}

}
