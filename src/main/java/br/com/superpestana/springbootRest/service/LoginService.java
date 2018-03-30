package br.com.superpestana.springbootRest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.superpestana.springbootRest.model.User;
import br.com.superpestana.springbootRest.repository.LoginRepository;

@Service
public class LoginService {

	@Autowired
	private LoginRepository repository;
	
	public User findByUserAndPassword(String username, String password) {
		return repository.findByUserAndPassword(username, password);
	}
}
