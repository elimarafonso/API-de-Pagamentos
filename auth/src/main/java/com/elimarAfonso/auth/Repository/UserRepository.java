package com.elimarAfonso.auth.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.elimarAfonso.auth.Entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

	
	@Query("SELECT u "
			+ "FROM User u "
			+ "WHERE u.userName =:userName")
	User findByUser(@Param("userName") String userName);
	
}
