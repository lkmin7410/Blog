package com.blog.home;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HomeSvc {

	@Autowired
	private SqlSessionTemplate sqlSession;
	
	//Home 글 목록 갯수 가져오기
	public Integer selectHomeCount(HomeSearchVO param) {

		return sqlSession.selectOne("selectHomeCount", param);
	}

	//글 목록 가져오기
	public List<?> GetPostList(HomeSearchVO param){
		
		return sqlSession.selectList("GetPostList",param);
	}
	
	//글 쓰기
	public void SetWrite(HomeVo param) {

		sqlSession.insert("SetWrite",param);
	}
	
	//댓글 쓰기
	public void SetPostComment(HomeCommentVo param) {

		sqlSession.insert("SetPostComment",param);
	}
	
	//대댓글 쓰기
	public void SetCommentReply(HomeCommentVo param) {
		sqlSession.insert("SetCommentReply",param);
	}

	//글 디테일 가져오기
	public HomeVo GetViewDetail(HomeVo param) {

		return sqlSession.selectOne("GetViewDetail",param);
	}
	
	//글 수정
	public void Edit_Write(HomeVo param) {

		sqlSession.selectOne("Edit_Write", param);
	}
	
	//글 삭제
	public void Remove_Post(HomeVo param) {

		sqlSession.selectOne("Remove_Post", param);
	}
	
	//댓글 목록 가져오기
	public List<HomeCommentVo> GetCommentList(HomeVo param) {
		
		return sqlSession.selectList("GetCommentList",param);
	}
	
	//대댓글 목록 가져오기
	public List<HomeCommentVo> GetReplyList(HomeCommentVo param) {
		
		return sqlSession.selectList("GetReplyList",param);
	}

	public List<HomeVo> GetRecentComments() {
		
		return sqlSession.selectList("GetRecentComments");
	}

	public void SetCategories(String param) {
		
		sqlSession.insert("SetCategories",param);
	}

	public List<?> GetCategories() {
		
		return sqlSession.selectList("GetCategories");
	}
	


	
}
