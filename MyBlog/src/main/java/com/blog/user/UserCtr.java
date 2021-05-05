package com.blog.user;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserCtr {    
	
	@Autowired
	private UserSvc UserSvc;
    
    /* 로그인 페이지 */
    @RequestMapping(value = "Login")
    public String HomeLogin() {
    	
        return "user/Login";
    }
    
 // 로그인 페이지 POST
 	@RequestMapping(value = "/Login", method = RequestMethod.POST)
 	public String HomeLoginPost(HttpServletRequest req, UserVo UserVo, ModelMap modelMap) {
 		
 		System.out.println("여기는 로그인 페이지 포스트 방식");
 		try {
 			UserVo = UserSvc.SetLogIn(UserVo);
 			HttpSession session = req.getSession();
 			session.setAttribute("session_id", UserVo.getUserid());

 			modelMap.addAttribute("log", UserVo);
 			return "redirect:/Home";
 			
 		} catch (Exception e) {
 			return "user/Login";
 		}

 	}
    
    /* 회원가입 페이지 */
    @RequestMapping(value = "SignUp")
    public String HomeSignUp() {
        return "user/SignUp";
    }
    
	/* 회원가입 POST방식 데이터 받는 컨트롤러 */
    @RequestMapping(value = "SignUp", method=RequestMethod.POST)
    public String HomeSignUpPost(HttpServletRequest request, ModelMap modelMap, UserVo UserVo) {
    	
    	UserSvc.SetSignUp(UserVo);
    	
    	return "redirect:/Login";
    }
    

    
    

}
