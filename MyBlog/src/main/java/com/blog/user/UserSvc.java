package com.blog.user;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserSvc {

	@Autowired
	private SqlSessionTemplate sqlSession;

	//회원가입
	public void SetSignUp(UserVo param) {

		sqlSession.insert("SetSignUp",param);
	}
	
	// 아이디 중복 체크
	public Integer idCheck(String param) {
		return sqlSession.selectOne("idCheck", param);
	}

	//로그인
	public UserVo SetLogIn(UserVo param) {
		
		return sqlSession.selectOne("SetLogIn", param);
	}
	
	//내 정보 수정
		public void SetMyInfo(UserVo param) {

			sqlSession.insert("SetMyInfo",param);
		}
		
		//내 정보 수정(프로필사진)
		public void SetMyInfoImg(UserVo param) {

			sqlSession.insert("SetMyInfoImg",param);
		}
}
