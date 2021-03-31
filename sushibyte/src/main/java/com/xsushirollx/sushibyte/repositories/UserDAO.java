package com.xsushirollx.sushibyte.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.xsushirollx.sushibyte.entities.User;

@Repository
public interface UserDAO extends JpaRepository<User, Integer> {

	/**
	 * Case sensitive
	 * @param username
	 * @return
	 */
	@Query("select u from User u where u.username=?1")
	public User findByUsername(String username);
	
	@Query("select u from User u where u.email=?1")
	public User findByEmail(String email);
	
	@Query("select u from User u where u.phone=?1")
	public User findByPhone(String phone);

}
