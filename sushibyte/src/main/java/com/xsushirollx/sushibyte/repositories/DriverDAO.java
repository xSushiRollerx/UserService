package com.xsushirollx.sushibyte.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xsushirollx.sushibyte.entities.Driver;

@Repository
public interface DriverDAO extends JpaRepository<Driver, Integer> {

}
