package com.britesnow.contactsdemo.entity;

public class User extends BaseEntity<Long> {

	private String username;
	private String password;

	// --------- Persistent Properties --------- //

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
}
