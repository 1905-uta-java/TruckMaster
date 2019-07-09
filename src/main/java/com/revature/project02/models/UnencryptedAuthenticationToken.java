package com.revature.project02.models;

import java.io.Serializable;

public class UnencryptedAuthenticationToken implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer userId;

	private String username;
	
	private String role;
	
	private String ip;
	
	private Long timestampLong;
	
	public UnencryptedAuthenticationToken(Integer userId, String username, String role, String ip,
			Long timestampLong) {
		super();
		this.userId = userId;
		this.username = username;
		this.role = role;
		this.ip = ip;
		this.timestampLong = timestampLong;
	}

	public UnencryptedAuthenticationToken() {
		super();
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Long getTimestampLong() {
		return timestampLong;
	}

	public void setTimestampLong(Long timestampLong) {
		this.timestampLong = timestampLong;
	}

	@Override
	public String toString() {
		return "UnencryptedAuthenticationToken [userId=" + userId + ", username=" + username + ", role=" + role
				+ ", ip=" + ip + ", timestampAsString=" + timestampLong + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ip == null) ? 0 : ip.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result + ((timestampLong == null) ? 0 : timestampLong.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UnencryptedAuthenticationToken other = (UnencryptedAuthenticationToken) obj;
		if (ip == null) {
			if (other.ip != null)
				return false;
		} else if (!ip.equals(other.ip))
			return false;
		if (role == null) {
			if (other.role != null)
				return false;
		} else if (!role.equals(other.role))
			return false;
		if (timestampLong == null) {
			if (other.timestampLong != null)
				return false;
		} else if (!timestampLong.equals(other.timestampLong))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

}
