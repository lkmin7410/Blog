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

	//로그인
	public UserVo SetLogIn(UserVo param) {
		
		return sqlSession.selectOne("SetLogIn", param);
	}
}
