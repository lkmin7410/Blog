package com.blog.home;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.user.UserVo;

@Service
public class HomeSvc {

	@Autowired
	private SqlSessionTemplate sqlSession;
	
	//Home 글 목록 갯수 가져오기
	public Integer selectHomeCount(HomeSearchVO param) {

		return sqlSession.selectOne("selectHomeCount", param);
	}
	
	//Home 나의 글 목록 갯수 가져오기
		public Integer selectMyListCount(HomeSearchVO param) {

			return sqlSession.selectOne("selectMyListCount", param);
		}

	//글 목록 가져오기
	public List<?> GetPostList(HomeSearchVO param){
		
		return sqlSession.selectList("GetPostList",param);
	}
	
	//목록 가져오기
		public List<?> GetList(HomeSearchVO param){
			
			return sqlSession.selectList("GetList",param);
		}
	
	//글 목록 가져오기
		public List<?> GetMYPostList(HomeSearchVO param){
			
			return sqlSession.selectList("GetMYPostList",param);
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
	
	//일괄 공개설정 수정
	public void Multi_Edit(HomeVo param) {

		sqlSession.selectOne("Multi_Edit", param);
	}
	
	
	//글 삭제
	public void Remove_Post(HomeVo param) {

		sqlSession.selectOne("Remove_Post", param);
	}
	//일괄 삭제
		public void All_Remove(String param) {

			sqlSession.selectOne("All_Remove", param);
		}
	
	
	//댓글 목록 가져오기
	public List<HomeCommentVo> GetCommentList(HomeVo param) {
		
		return sqlSession.selectList("GetCommentList",param);
	}
	
	//대댓글 목록 가져오기
	public List<HomeCommentVo> GetReplyList(HomeCommentVo param) {
		
		return sqlSession.selectList("GetReplyList",param);
	}

	//최근 댓글 목록 가져오기
	public List<HomeVo> GetRecentComments() {
		
		return sqlSession.selectList("GetRecentComments");
	}
	
	//카테고리 생성 하기
	public void RemoveCategories(HomeVo param) {
		
		sqlSession.insert("RemoveCategories",param);
	}
	
	//카테고리 삭제 하기
	public void SetCategories(HomeVo param) {
		
		sqlSession.insert("SetCategories",param);
	}
	
	//카테고리 목록 가져오기
	public List<?> GetCategories() {
		
		return sqlSession.selectList("GetCategories");
	}
	
	//내 정보 가져오기
	public UserVo GetMyInfo(String param) {

		return sqlSession.selectOne("GetMyInfo", param);
	}

	public void RemovePost(HomeVo param) {
		
			sqlSession.delete("RemovePost",param);
	}

	
}
