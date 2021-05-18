package com.blog.home;

import java.util.Date;

import org.springframework.stereotype.Repository;

@Repository
public class HomeVo {
	
	private int seq;
	private String categories ="";
	private String public_setting;
	private String reply_setting;
	private String title;
	private String content;
	private String writer;
	private Date regdate;
	
	
	public String getReply_setting() {
		return reply_setting;
	}
	public void setReply_setting(String reply_setting) {
		this.reply_setting = reply_setting;
	}
	public String getPublic_setting() {
		return public_setting;
	}
	public void setPublic_setting(String public_setting) {
		this.public_setting = public_setting;
	}
	public String getCategories() {
		return categories;
	}
	public void setCategories(String categories) {
		this.categories = categories;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	

	
	
	
}
