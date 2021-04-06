/**
 * 
 */
package com.xsushirollx.sushibyte.user.dto;

/**
 * @author dyltr
 * Allows user operations linked to specific account
 * Placed into list/map of active users
 */
public class LoggedUser {
	private String username;
	private int userRole;
	private int hashCode;
	public LoggedUser(String username, int userRole) {
		super();
		this.username = username;
		this.userRole = userRole;
		hashCode = this.hashCode();
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getUserRole() {
		return userRole;
	}
	public void setUserRole(int userRole) {
		this.userRole = userRole;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + hashCode;
		result = prime * result + userRole;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}
	public int getHashCode() {
		return hashCode;
	}
	public void setHashCode(int hashCode) {
		this.hashCode = hashCode;
	}
	
}
