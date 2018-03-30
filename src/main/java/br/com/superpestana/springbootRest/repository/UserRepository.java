package br.com.superpestana.springbootRest.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.superpestana.springbootRest.model.User;

public interface UserRepository extends CrudRepository<User, Long>{

}
