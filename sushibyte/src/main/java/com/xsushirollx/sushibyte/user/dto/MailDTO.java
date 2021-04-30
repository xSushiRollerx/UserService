package com.xsushirollx.sushibyte.user.dto;

/**
 * @author dyltr
 * Better to send information back then just doing a redirect
 */
public class MailDTO {
	private String email;
	private String body;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
}
