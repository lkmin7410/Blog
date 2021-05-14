package com.blog.user;

import java.net.MalformedURLException;
import java.util.Random;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserCtr {

	@Autowired
	private UserSvc UserSvc;

	@Autowired
	private JavaMailSender mailSender;

	/* 로그인 페이지 */
	@RequestMapping(value = "/Login")
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
			return "redirect:/Home";
		}
	}

	// 로그아웃
	@RequestMapping(value = "/Logout")
	public String Logout(HttpServletRequest req, UserVo uo, ModelMap modelMap) throws MalformedURLException {
		HttpSession session = req.getSession();

		session.removeAttribute("session_id");

		return "redirect:/Home";
	}

	/* 회원가입 페이지 */
	@RequestMapping(value = "/SignUp")
	public String HomeSignUp() {
		return "user/SignUp";
	}

	/* 회원가입 POST방식 데이터 받는 컨트롤러 */
	@RequestMapping(value = "/SignUp", method = RequestMethod.POST)
	public String HomeSignUpPost(HttpServletRequest request, ModelMap modelMap, UserVo UserVo) {

		UserSvc.SetSignUp(UserVo);

		return "redirect:/Home";
	}
	
	  // Ajax를 이용한 아이디 체크 방법
	   @ResponseBody
	   @RequestMapping(value = "/idCheck2")
	   public String idCheck2(UserVo UserVo, Model model) {
	      String idchk = UserVo.getUserid();
	      String check = UserSvc.idCheck(idchk).toString();
	      return check;
	   }

	/* 인증번호 */
	@RequestMapping(value = "/emailCheck")
	public String EmailCheck(HttpServletRequest req, ModelMap modelMap, UserVo UserVo) {

		Random rd = new Random();
		int rdNum = rd.nextInt(88888) + 11111;

		String setFrom = "lkmin7410@naver.com";
		String toMail = UserVo.getUseremail();
		String title = "회원가입 인증 메일 입니다.";
		String content = "회원가입을 환영합니다." + "<br><br>" + "인증 번호는 :: " + rdNum + "  입니다." + "<br>"
				+ "해당 인증번호를 인증번호 확인란에 기입해주세요";

		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");

			helper.setFrom(setFrom);
			helper.setTo(toMail);
			helper.setSubject(title);
			helper.setText(content, true);
			mailSender.send(message);

		} catch (Exception e) {
			e.printStackTrace();
		}
		UserVo.setRdmNum(Integer.toString(rdNum));

		modelMap.addAttribute("UserVo", UserVo);

		return "user/SignUp";
	}

	/* 인증번호 확인 */
	@RequestMapping(value = "/emailCheckGet")
	public String EmailCheckGet(HttpServletRequest req, ModelMap modelMap, UserVo UserVo) {

		System.out.println("여기까지 오는지 확인");
		String rdm = UserVo.getRdmNum();
		String ck = UserVo.getCheckNum();

		int msg = 0;
		
		if(rdm.equals(null) || rdm=="") {
			modelMap.addAttribute("msg", msg);
			modelMap.addAttribute("UserVo", UserVo);

			return "user/SignUp";
		}

		if (rdm.equals(ck)) {
			msg = 1;

			modelMap.addAttribute("msg", msg);
			modelMap.addAttribute("UserVo", UserVo);

			return "user/SignUp";
		} else if (!rdm.equals(ck)) {
			msg = 2;

			modelMap.addAttribute("msg", msg);
			modelMap.addAttribute("UserVo", UserVo);

			return "user/SignUp";
		}

		return null;

	}
}
