package com.xsushirollx.sushibyte.user.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "driver")
public class Driver {
	@Id
	private Integer id;
	@Column(name = "rating")
	private Integer rating;
	@Column(name = "is_active")
	private Boolean isActive;
	@Column(name = "total_deliveries")
	private Integer totalDeliveries;

	public Driver() {

	}

	public Driver(Integer id) {
		this.id = id;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}

	public Integer getTotalDeliveries() {
		return totalDeliveries;
	}

	public void setTotalDeliveries(Integer totalDeliveries) {
		this.totalDeliveries = totalDeliveries;
	}

}
