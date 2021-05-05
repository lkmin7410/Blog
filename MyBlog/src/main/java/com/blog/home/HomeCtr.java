package com.blog.home;

import java.util.ArrayList;
import java.util.List;

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
	 * 메인 홈페이지 글 목록 가져오기
	 */
	@RequestMapping(value = "Home")
	public String Home(ModelMap modelMap, HomeVo HomeVo) {

		List<?> PostList = HomeSvc.GetPostList(HomeVo);

		modelMap.addAttribute("PostList", PostList);
		return "home/Home";
	}

	/* 글 쓰기 페이지 */
	@RequestMapping(value = "Write")
	public String HomeWrite() {

		return "home/HomeWrite";
	}

	/* 글 쓰기 POST방식 데이터 받는 컨트롤러 */
	@RequestMapping(value = "Write", method = RequestMethod.POST)
	public String HomeWritePost(HttpServletRequest request, ModelMap modelMap, HomeVo HomeVo) {

		HomeSvc.SetWrite(HomeVo);

		return "redirect:/Home";
	}

	/* 글 보기 디테일 */
	@RequestMapping(value = "HomePost")
	public String HomePostView(HttpServletRequest request, ModelMap modelMap, HomeVo HomeVo) {
		/* 글 디테일 보기 */
		HomeVo = HomeSvc.GetViewDetail(HomeVo);
		
		/* 부모 댓글 리스트 가져오기 */
		List<HomeCommentVo>CommentList = HomeSvc.GetCommentList(HomeVo);
		
		//부모
        List<HomeCommentVo> boardReplyListParent = new ArrayList<HomeCommentVo>();
        //자식
        List<HomeCommentVo> boardReplyListChild = new ArrayList<HomeCommentVo>();
        //통합
        List<HomeCommentVo> newBoardReplyList = new ArrayList<HomeCommentVo>();
        
        
		
		for(HomeCommentVo Reply : CommentList) {
			
			boardReplyListParent.add(Reply);
			
			/* 자식 댓글 리스트 가져오기 */
			List<HomeCommentVo>ReplyList = HomeSvc.GetReplyList(Reply);
			for(HomeCommentVo d : ReplyList) {
				boardReplyListChild.add(d);
				}
			}
		
		//2.부모를 돌린다.
        for(HomeCommentVo boardReplyParent: boardReplyListParent){
            //2-1. 부모는 무조건 넣는다.
        	//newBoardReplyList.add(boardReplyParent);
            //3.자식을 돌린다.
            for(HomeCommentVo boardReplyChild: boardReplyListChild){
                //3-1. 부모의 자식인 것들만 넣는다.
                if(boardReplyParent.getSeq()==boardReplyChild.getReply_seq()){
                    newBoardReplyList.add(boardReplyChild);
                }
 
            }
 
        }
        for(HomeCommentVo g : newBoardReplyList) {
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+g.getComment());
        }
		modelMap.addAttribute("HomeVo", HomeVo);
		modelMap.addAttribute("CommentList", CommentList);
		modelMap.addAttribute("newBoardReplyList",newBoardReplyList);
		
		return "home/HomePost";
	}
	
	/* 댓글 쓰기 POST방식 데이터 받는 컨트롤러 */
	@RequestMapping(value = "PostComment", method = RequestMethod.POST)
	public String HomePostComment(HttpServletRequest request, HomeCommentVo HomeCommentVo) {

		HomeSvc.SetPostComment(HomeCommentVo);

		return "redirect:/HomePost?seq="+HomeCommentVo.getPost_seq();
	}
	
	/* 대댓글 쓰기 POST방식 데이터 받는 컨트롤러 */
	@RequestMapping(value = "Commentreply", method = RequestMethod.POST)
	public String HomeCommentreply(HttpServletRequest request, HomeCommentVo HomeCommentVo) {

		HomeSvc.SetCommentReply(HomeCommentVo);

		return "redirect:/HomePost?seq="+HomeCommentVo.getPost_seq();
	}
	
	

}
