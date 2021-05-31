package com.blog.user;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.blog.home.HomeSearchVO;
import com.blog.home.HomeSvc;
import com.google.gson.JsonObject;

@Controller
public class UserCtr {
	
	public static String SAVE_PATH = "D:\\workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\MyBlog\\upload\\";

	@Autowired
	private UserSvc UserSvc;

	@Autowired
	private JavaMailSender mailSender;
	
	
	/* 내 정보 수정 */
	@RequestMapping(value = "/MyinfoEdit")
	public String MyinfoEdit(UserVo UserVo){
		
		UserSvc.SetMyInfo(UserVo);
		
		return "redirect:/HomeAdmin";
	}
	
	/* 프로필 이미지 업로드 */
	@RequestMapping(value = "/MyinfoEdit", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView upload(
			MultipartHttpServletRequest multiFile,HttpServletRequest req,HttpServletResponse resp, ModelAndView mv, HomeSearchVO HomeSearchVO, UserVo UserVo) throws IOException {

		
		//내 pc에만 저장 됨
//		public ModelAndView upload(
//	            @RequestParam(value="img_file", required = false) MultipartFile mf,HttpServletRequest req, ModelAndView mv, HomeSearchVO HomeSearchVO, UserVo UserVo) {
		
//		String originalFileName = mf.getOriginalFilename();
//		String safeFile = SAVE_PATH + originalFileName;
//        String fileUrl = req.getContextPath() + "/upload/" + originalFileName;
//        
//        System.out.println("111111111111111111111111111111111111safeFile :: "+safeFile);
//        System.out.println("111111111111111111111111111111111111originalFileName ::" + originalFileName);
//        
//        UserVo.setUserimg(fileUrl);
//        UserSvc.SetMyInfo(UserVo);
//        
//        try {
//            mf.transferTo(new File(safeFile));
//
//           } catch (IllegalStateException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            } catch (IOException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        
//        mv.setViewName("redirect:/HomeAdmin");
//
//        return mv;
		
		System.out.println("파일업로드 넘어옴");
		JsonObject json = new JsonObject();
		PrintWriter printWriter = null;
		OutputStream out = null;
		MultipartFile file = multiFile.getFile("img_file");
		
		if(file.getSize() <= 0) {
		UserSvc.SetMyInfo(UserVo);
		
		resp.sendRedirect("HomeAdmin");
		
		} else if (file != null) {
			if (file.getSize() > 0 && StringUtils.isNotBlank(file.getName())) {
				if (file.getContentType().toLowerCase().startsWith("image/")) {
					try {
						String fileName = file.getName();
						byte[] bytes = file.getBytes();
						String uploadPath = req.getServletContext().getRealPath("/upload");
						File uploadFile = new File(uploadPath);
						if (!uploadFile.exists()) {
							uploadFile.mkdirs();
						}
						fileName = UUID.randomUUID().toString();
						uploadPath = uploadPath + "/" + fileName;
						System.out.println(uploadPath);
						out = new FileOutputStream(new File(uploadPath));
						out.write(bytes);

						printWriter = resp.getWriter();
						resp.setContentType("text/html");
						String fileUrl = req.getContextPath() + "/upload/" + fileName;

						System.out.println("111111111111111111111111111" + fileUrl);

						json.addProperty("uploaded", 1);
						json.addProperty("fileName", fileName);
						json.addProperty("url", fileUrl);
						
						UserVo.setUserimg(fileUrl);
						UserSvc.SetMyInfoImg(UserVo);

						resp.sendRedirect("HomeAdmin");
						
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						if (out != null) {
							out.close();
						}
						if (printWriter != null) {
							printWriter.close();
						}
					}
				}
			}
		}
		return null;
    }

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
			e.printStackTrace();
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
