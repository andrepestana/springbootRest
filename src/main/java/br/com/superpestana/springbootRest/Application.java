package br.com.superpestana.springbootRest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import br.com.superpestana.springbootRest.auth.AuthFilter;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public FilterRegistrationBean registerFilters() {
		FilterRegistrationBean frb = new FilterRegistrationBean();
		frb.setFilter(new AuthFilter());
		frb.addUrlPatterns("/rest/auth/*");
		return frb;
	}


}
