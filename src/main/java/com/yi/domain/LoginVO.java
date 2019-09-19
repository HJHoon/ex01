package com.yi.domain;

public class LoginVO {
	private String userid;
	private String username;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "LoginVO [userid=" + userid + ", username=" + username + "]";
	}

}
