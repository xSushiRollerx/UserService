package com.xsushirollx.sushibyte.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="customer")
public class Customer{
	@Id
	private int id;
	@Column(name="is_elderly")
	private boolean isElderly;
	@Column(name="is_veteran")
	private boolean isVeteran;
	@Column(name="loyalty_points")
	private int loyaltyPoints;
	@Column(name="longitude")
	private double longitude;
	@Column(name="lattitude")
	private double lattitude;
	
	public Customer() {
		
	}
	public Customer(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isElderly() {
		return isElderly;
	}

	public void setElderly(boolean isElderly) {
		this.isElderly = isElderly;
	}

	public boolean isVeteran() {
		return isVeteran;
	}

	public void setVeteran(boolean isVeteran) {
		this.isVeteran = isVeteran;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", isElderly=" + isElderly + ", isVeteran=" + isVeteran + ", loyaltyPoints="
				+ loyaltyPoints + ", longitude=" + longitude + ", lattitude=" + lattitude + "]";
	}

	public int getLoyaltyPoints() {
		return loyaltyPoints;
	}

	public void setLoyaltyPoints(int loyaltyPoints) {
		this.loyaltyPoints = loyaltyPoints;
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
