package com.blog.home;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

@Repository
public class HomeCommentVo {

	//댓글
	private int seq;
	private int post_seq;
	private	String userid;
	private String userimg;
	private	String comment;
	private	Date regdate;
	
	//댓글의 답글
	private int reply_seq;
	
	
	
	public String getUserimg() {
		return userimg;
	}
	public void setUserimg(String userimg) {
		this.userimg = userimg;
	}
	public int getReply_seq() {
		return reply_seq;
	}
	public void setReply_seq(int reply_seq) {
		this.reply_seq = reply_seq;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public int getPost_seq() {
		return post_seq;
	}
	public void setPost_seq(int post_seq) {
		this.post_seq = post_seq;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	
	
}
