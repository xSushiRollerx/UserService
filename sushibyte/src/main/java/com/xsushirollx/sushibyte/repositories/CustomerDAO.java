package com.xsushirollx.sushibyte.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xsushirollx.sushibyte.entities.Customer;

@Repository
public interface CustomerDAO extends JpaRepository<Customer, Integer> {

}
