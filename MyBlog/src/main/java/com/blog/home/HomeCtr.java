package com.blog.home;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeCtr {    
   
	@Autowired
	private HomeSvc HomeSvc;
	
    /**
     * 메인 홈페이지
     */
    @RequestMapping(value = "Home")
    public String Home(HttpServletRequest request, ModelMap modelMap) {
        return "home/Home";
    }
    
    @RequestMapping(value = "Write")
    public String HomeWrite(HttpServletRequest request, ModelMap modelMap) {
    	
        return "home/HomeWrite";
    }
    
    @RequestMapping(value = "Write", method=RequestMethod.POST)
    public String HomeWritePost(HttpServletRequest request, ModelMap modelMap, HomeVo HomeVo) {
    	
    	System.out.println("11111111111111111"+HomeVo.getTitle());
    	HomeSvc.setwrite(HomeVo);
    	
        return "home/HomeWrite";
    }

}
