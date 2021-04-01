package com.xsushirollx.sushibyte.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "driver")
public class Driver {
	@Id
	private int id;
	@Column(name = "longitude")
	private double longitude;
	@Column(name = "lattitude")
	private double lattitude;
	@Column(name = "rating")
	private int rating;
	@Column(name = "total_deliveries")
	private int totalDeliveries;

	public Driver() {
		
	}
	
	public Driver(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Driver [id=" + id + ", longitude=" + longitude + ", lattitude=" + lattitude + ", rating=" + rating
				+ ", totalDeliveries=" + totalDeliveries + "]";
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public int getTotalDeliveries() {
		return totalDeliveries;
	}

	public void setTotalDeliveries(int totalDeliveries) {
		this.totalDeliveries = totalDeliveries;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLattitude() {
		return lattitude;
	}

	public void setLattitude(double lattitude) {
		this.lattitude = lattitude;
	}

}
