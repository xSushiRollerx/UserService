package com.xsushirollx.sushibyte.user.dto;

public class DriverDTO {
	private String username;
	private String password;
	private String firstName;
	private String phone;
	private Integer rating;
	private Integer totalDeliveries;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getRating() {
		return rating;
	}
	public Integer getTotalDeliveries() {
		return totalDeliveries;
	}
	
}
