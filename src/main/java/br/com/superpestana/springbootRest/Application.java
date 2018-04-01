package br.com.superpestana.springbootRest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import br.com.superpestana.springbootRest.auth.AuthFilter;
import br.com.superpestana.springbootRest.model.User;
import br.com.superpestana.springbootRest.repository.UserRepository;

@SpringBootApplication
public class Application {
	
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
		context.getBean(UserRepository.class).save(new User("admin", "admin"));
	}

	@Bean
	public FilterRegistrationBean registerFilters() {
		FilterRegistrationBean frb = new FilterRegistrationBean();
		frb.setFilter(new AuthFilter());
		frb.addUrlPatterns("/rest/auth/*");
		return frb;
	}


}
