package com.xsushirollx.sushibyte.user.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xsushirollx.sushibyte.user.entities.Customer;

/**
 * @author dyltr
 * Currently using base JpaRepository for Customers
 */
@Repository
public interface CustomerDAO extends JpaRepository<Customer, Integer> {

}
