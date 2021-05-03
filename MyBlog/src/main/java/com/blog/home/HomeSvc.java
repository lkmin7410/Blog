package com.blog.home;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HomeSvc {

	@Autowired
	private SqlSessionTemplate sqlSession;

	public void setwrite(HomeVo param) {

		sqlSession.insert("setwrite",param);
	}

}
