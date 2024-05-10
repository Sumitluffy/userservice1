package com.training.userservice.dao;

public class User {

	private int userid;
	private String username;
	private String addr;
	private String email;
	
	public User(int userid, String username, String addr, String email) {
		this.userid = userid;
		this.username = username;
		this.addr = addr;
		this.email = email;
	}
	
	
	public int getUserId() {
		return userid;
	}
	public void setUserId(int userid) {
		this.userid = userid;
	}
	public String getUserName() {
		return username;
	}
	public void setUserName(String username) {
		this.username = username;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}
