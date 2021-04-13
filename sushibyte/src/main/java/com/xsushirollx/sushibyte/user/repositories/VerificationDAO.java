package com.xsushirollx.sushibyte.user.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.xsushirollx.sushibyte.user.entities.Verification;

/**
 * @author dyltr Currently using base JpaRepository for Customers
 */
@Repository
public interface VerificationDAO extends JpaRepository<Verification, Integer> {

	/**
	 * @param verificationCode
	 * @return Verification object with verification code
	 */
	@Query("select v from Verification v where v.verificationCode=?1")
	public Verification findByVericationCode(String verificationCode);
}
