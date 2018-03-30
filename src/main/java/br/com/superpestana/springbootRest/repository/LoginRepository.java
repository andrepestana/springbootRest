package br.com.superpestana.springbootRest.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.superpestana.springbootRest.model.User;

@Repository
public interface LoginRepository extends CrudRepository<User, Long>{

    @Query("select u from User u where u.username = ? and u.password = ?" )
	public User findByUserAndPassword(String username, String password);
	
}
