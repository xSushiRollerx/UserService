package com.xsushirollx.sushibyte.user.entities;

import java.sql.Timestamp;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import net.bytebuddy.utility.RandomString;

import javax.persistence.Id;

@Entity
@Table(name = "verification")
public class Verification {
	@Id
	private Integer id;
	@Column(name="verification_code")
	private String verificationCode;
	@Column(name="created_at")
	private Timestamp createdAt;
	
	public Verification() {
		
	}
	
	public Verification(Integer id) {
		this.id = id;
		verificationCode=RandomString.make(64);
		createdAt=Timestamp.from(Instant.now());
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Verification other = (Verification) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (verificationCode == null) {
			if (other.verificationCode != null)
				return false;
		} else if (!verificationCode.equals(other.verificationCode))
			return false;
		return true;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getVerificationCode() {
		return verificationCode;
	}
	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	
}
