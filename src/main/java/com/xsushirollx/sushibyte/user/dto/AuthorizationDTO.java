/**
 * 
 */
package com.xsushirollx.sushibyte.user.dto;

/**
 * @author dyltr
 * Allows user operations linked to specific account
 * Placed into list/map of active users
 */
public class AuthorizationDTO {
	private Integer id;
	private Integer userRole;
	private Integer hashCode;
	public AuthorizationDTO(Integer id, Integer userRole) {
		super();
		this.id = id;
		this.userRole = userRole;
		this.hashCode = hashCode();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
		result = prime * result +((id == null) ? 0 : userRole);
		result = prime * result + ((id == null) ? 0 : id);
		return result;
	}
	public int getHashCode() {
		return hashCode;
	}
	public void setHashCode(int hashCode) {
		this.hashCode = hashCode;
	}
	
}
