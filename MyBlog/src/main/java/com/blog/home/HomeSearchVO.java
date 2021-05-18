package com.blog.home;

import org.springframework.stereotype.Repository;

@Repository
public class HomeSearchVO {

private	String searchType = "title";			//제목
private String searchKeyword = ""; 				//검색어
private	Integer displayRowCount = 3; 			//화면, 10개씩 뿌림
private Integer rowStart; 						//시작 행 번호
private	Integer rowEnd;							//종료 행 번호
private	Integer totPage; 						//전체 페이지 갯수
private	Integer totRow = 0; 					//전체 데이터 갯수
private	Integer page;							//현재 페이지
private	Integer pageStart;						//시작 페이지 번호
private	Integer pageEnd;						//마지막 페이지 번호

private String categories = "";
private String public_setting = "";
private String userid;

public String getUserid() {
	return userid;
}
public void setUserid(String userid) {
	this.userid = userid;
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

public void pageCalculate(Integer total) {
	
	getPage();
	totRow = total;
	totPage = (int)(total / displayRowCount); // 전체 페이지 수는 조회된 전체 건수 나누기 10
	
	if(total % displayRowCount > 0) { //전체 건수를 10으로 나누어서 0보다 크면 페이지 수를 하나 더 늘린다.
		totPage++;					  //만약 101건 이 조회 되었다면 totpage 는 10이 나오지만 페이지 수를 하나 더 올림.
	}
	pageStart = (page-(page-1)%10);   //만약 페이지가 12이면 (12-(12-1)%10) 항상 1, 11, 21, 식의 숫자가 표시됨
	pageEnd = pageStart + 9;		  //시작 페이지에서 9 더하면 10개씩 출력
	if(pageEnd > totPage) {			  
		pageEnd = totPage;
	}

	
	rowStart = total - (page-1)*displayRowCount; // (3-1)*11 
	rowEnd = rowStart - (displayRowCount -1);	 //마지막 row번호

}

public void publicpageCalculate(Integer total) {
	
	this.displayRowCount = 5;
	
	getPage();
	totRow = total;
	totPage = (int)(total / displayRowCount); // 전체 페이지 수는 조회된 전체 건수 나누기 10
	
	if(total % displayRowCount > 0) { //전체 건수를 10으로 나누어서 0보다 크면 페이지 수를 하나 더 늘린다.
		totPage++;					  //만약 101건 이 조회 되었다면 totpage 는 10이 나오지만 페이지 수를 하나 더 올림.
	}
	pageStart = (page-(page-1)%10);   //만약 페이지가 12이면 (12-(12-1)%10) 항상 1, 11, 21, 식의 숫자가 표시됨
	pageEnd = pageStart + 9;		  //시작 페이지에서 9 더하면 10개씩 출력
	if(pageEnd > totPage) {			  
		pageEnd = totPage;
	}

	
	rowStart = total - (page-1)*displayRowCount; // (3-1)*11 
	rowEnd = rowStart - (displayRowCount -1);	 //마지막 row번호

}




public String getSearchType() {
	return searchType;
}
public void setSearchType(String searchType) {
	this.searchType = searchType;
}
public String getSearchKeyword() {
	return searchKeyword;
}
public void setSearchKeyword(String searchKeyword) {
	this.searchKeyword = searchKeyword;
}
public Integer getDisplayRowCount() {
	return displayRowCount;
}
public void setDisplayRowCount(Integer displayRowCount) {
	this.displayRowCount = displayRowCount;
}
public Integer getRowStart() {
	return rowStart;
}
public void setRowStart(Integer rowStart) {
	this.rowStart = rowStart;
}
public Integer getRowEnd() {
	return rowEnd;
}
public void setRowEnd(Integer rowEnd) {
	this.rowEnd = rowEnd;
}
public Integer getTotPage() {
	return totPage;
}
public void setTotPage(Integer totPage) {
	this.totPage = totPage;
}
public Integer getTotRow() {
	return totRow;
}
public void setTotRow(Integer totRow) {
	this.totRow = totRow;
}
public Integer getPage() {
	if(page == null || page == 0) {
		page =1;
	}
	return page;
}
public void setPage(Integer page) {
	this.page = page;
}
public Integer getPageStart() {
	return pageStart;
}
public void setPageStart(Integer pageStart) {
	this.pageStart = pageStart;
}
public Integer getPageEnd() {
	return pageEnd;
}
public void setPageEnd(Integer pageEnd) {
	this.pageEnd = pageEnd;
}
	
	
}
