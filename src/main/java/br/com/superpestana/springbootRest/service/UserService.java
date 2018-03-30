package br.com.superpestana.springbootRest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.superpestana.springbootRest.model.User;
import br.com.superpestana.springbootRest.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;
	
	public Iterable<User> findAll(){
		return repository.findAll();
	}
	
	public User save(User user) {
		return repository.save(user);
	}
	public User findById(Long id) {
		return repository.findOne(id);
	}
	public void update(User user) {
		repository.delete(user);
	}
	public void delete(Long id) {
		repository.delete(id);
	}
}
