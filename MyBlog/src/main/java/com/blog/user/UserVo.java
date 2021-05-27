package com.blog.user;

import org.springframework.stereotype.Repository;

@Repository
public class UserVo {
	
private	String userid;
private	String userpw;
private	String username;
private String usernickname;
private String userblogname;
private	String useremail;
private String rdmNum;
private String checkNum;
private String userimg;
private String userIntroduction;


public UserVo() {}

public UserVo(String n_id, String n_name, String n_mail, String n_nickname, String n_img) {
	this.userid = n_id;
	this.username = n_name;
	this.useremail = n_mail;
	this.usernickname = n_nickname;
	this.userimg = n_img;
}


public String getUserIntroduction() {
	return userIntroduction;
}
public void setUserIntroduction(String userIntroduction) {
	this.userIntroduction = userIntroduction;
}
public String getUserimg() {
	return userimg;
}
public void setUserimg(String userimg) {
	this.userimg = userimg;
}
public String getUserblogname() {
	return userblogname;
}
public void setUserblogname(String userblogname) {
	this.userblogname = userblogname;
}
public String getUsernickname() {
	return usernickname;
}
public void setUsernickname(String usernickname) {
	this.usernickname = usernickname;
}
public String getRdmNum() {
	return rdmNum;
}
public void setRdmNum(String rdmNum) {
	this.rdmNum = rdmNum;
}
public String getCheckNum() {
	return checkNum;
}
public void setCheckNum(String checkNum) {
	this.checkNum = checkNum;
}
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
