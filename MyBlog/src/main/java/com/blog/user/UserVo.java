package com.blog.user;

import org.springframework.stereotype.Repository;

@Repository
public class UserVo {
	
private	String userid;
private	String userpw;
private	String username;
private	String useremail;

public String getUserid() {
	return userid;
}
public void setUserid(String userid) {
	this.userid = userid;
}
public String getUserpw() {
	return userpw;
}
public void setUserpw(String userpw) {
	this.userpw = userpw;
}
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public String getUseremail() {
	return useremail;
}
public void setUseremail(String useremail) {
	this.useremail = useremail;
}
	
}
