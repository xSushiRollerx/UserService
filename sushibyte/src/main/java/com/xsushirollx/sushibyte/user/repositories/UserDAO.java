package com.xsushirollx.sushibyte.user.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xsushirollx.sushibyte.user.entities.User;

/**
 * @author dyltr Added addition methods to find user by keys other than primary
 */
@Repository
public interface UserDAO extends JpaRepository<User, Integer> {

	@Query("select u from User u where u.username=?1")
	public User findByUsername(String username);

	@Query("select u from User u where u.email=?1")
	public User findByEmail(String email);

	@Query("select u from User u where u.phone=?1")
	public User findByPhone(String phone);

	/**
	 * To be used for validating emails
	 * @param verificationCode
	 * @return
	 */
	@Query("select u from User u where u.verificationCode=?1")
	public User findByVericationCode(String verificationCode);

}
