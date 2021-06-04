package com.blog.home;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.blog.user.UserCtr;
import com.blog.user.UserSvc;
import com.blog.user.UserVo;
import com.google.gson.JsonObject;

@Controller
public class HomeCtr {

	@Autowired
	private HomeSvc HomeSvc;

	@Autowired
	private UserSvc UserSvc;

	/**
	 * 메인 홈페이지 글 목록 가져오기 네이버 로그인
	 */
	@RequestMapping(value = "Home")
	public String Home(HttpServletRequest req, ModelMap modelMap, HomeSearchVO HomeSearchVO, UserVo UserVo)
			throws IOException {

		HttpSession session = req.getSession();
		String s_id = (String) session.getAttribute("session_id"); // 세션 아이디 가져오기

		HomeSearchVO.pageCalculate(HomeSvc.selectHomeCount(HomeSearchVO)); // 페이징 처리
		List<?> PostList = HomeSvc.GetPostList(HomeSearchVO); // 게시글 리스트
		List<HomeVo> CommentList = HomeSvc.GetRecentComments(); // 최근 댓글 리스트
		List<?> Categories = HomeSvc.GetCategories(); // 카테고리 리스트 가져오기
		UserVo = HomeSvc.GetMyInfo(s_id); // 내 정보 가져오기

		// 네이버 로그인
		SecureRandom random = new SecureRandom();
		String state = new BigInteger(130, random).toString();
		String N_clientId = "P3DILcndDBYRpCcWvUq0";
		String N_redirectURI = URLEncoder.encode("http://localhost:8080/MyBlog/N_callback", "UTF-8");

		String N_apiURL = "https://nid.naver.com/oauth2.0/authorize?response_type=code";
		N_apiURL += "&client_id=" + N_clientId;
		N_apiURL += "&redirect_uri=" + N_redirectURI;
		N_apiURL += "&state=" + state;
		session.setAttribute("state", state);

		// 카카오 로그인
		String K_clientId = "1cc6ba13ef828544b70efacdf8c7d570";
		// 아마존 서버
		// String K_redirectURI = URLEncoder.encode("http://3.34.54.186:8080/MyBlog/K_callback","UTF-8");
		// 로컬 서버
		String K_redirectURI = URLEncoder.encode("http://localhost:8080/MyBlog/K_callback", "UTF-8");
		String K_apiURL = "https://kauth.kakao.com/oauth/authorize?response_type=code";
		K_apiURL += "&client_id=" + K_clientId;
		K_apiURL += "&redirect_uri=" + K_redirectURI;
		
		//카카오 로그아웃 URI 
		//아마존 서버
//		String K_LogoutredirectURI = URLEncoder.encode("http://3.34.54.186:8080/MyBlog/K_logout", "UTF-8");
		//로컬 서
		String K_LogoutredirectURI = URLEncoder.encode("http://localhost:8080/MyBlog/K_logout", "UTF-8");
		String K_LogOutapiURL = "https://kauth.kakao.com/oauth/logout?client_id="+ K_clientId +"&logout_redirect_uri="+ K_LogoutredirectURI;
		//글쓴이 아아디를 닉네임으로 보이게
		if(s_id != null) {
			session.setAttribute("session_usernickname", UserVo.getUsernickname());
		}
		
		modelMap.addAttribute("K_LogOutapiURL", K_LogOutapiURL);
		modelMap.addAttribute("K_apiURL", K_apiURL);
		modelMap.addAttribute("N_apiURL", N_apiURL);
		modelMap.addAttribute("Myinfo", UserVo);
		modelMap.addAttribute("so", HomeSearchVO);
		modelMap.addAttribute("PostList", PostList);
		modelMap.addAttribute("CommentList", CommentList);
		modelMap.addAttribute("Categories", Categories);

		return "home/Home";
	}
	
	//카카오 로그아웃 url
	@RequestMapping(value = "K_logout")
	public String K_logout(HttpServletRequest req, HttpServletResponse resp) {
		
		System.out.println("카카오 로그아웃 들어옴");
		
		HttpSession session = req.getSession();
		session.removeAttribute("session_id");
		session.removeAttribute("access_token");
		
		return "redirect:/Home";

	}

	// 네이버 로그인 콜백
	@RequestMapping(value = "N_callback")
	public String NaverCallback(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String clientId = "P3DILcndDBYRpCcWvUq0";
		String clientSecret = "kBv199HnOR";
		String code = request.getParameter("code");
		String redirectURI = URLEncoder.encode("http://localhost:8080/MyBlog/N_callback", "UTF-8");
		String state = request.getParameter("state");

		String apiURL;
		apiURL = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&";
		apiURL += "client_id=" + clientId;
		apiURL += "&client_secret=" + clientSecret;
		apiURL += "&redirect_uri=" + redirectURI;
		apiURL += "&code=" + code;
		apiURL += "&state=" + state;
		String access_token = "";
		String refresh_token = "";
		System.out.println("apiURL=" + apiURL);

		try {
			URL url = new URL(apiURL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			int responseCode = con.getResponseCode();
			BufferedReader br;
			System.out.print("responseCode=" + responseCode);
			if (responseCode == 200) {
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			} else {
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			String inputLine;
			StringBuffer res = new StringBuffer();
			while ((inputLine = br.readLine()) != null) {
				res.append(inputLine);
			}
			br.close();
			if (responseCode == 200) {
				System.out.println(res.toString());
				JSONParser parsing = new JSONParser();
				Object obj = parsing.parse(res.toString());
				JSONObject jsonObj = (JSONObject) obj;

				access_token = (String) jsonObj.get("access_token");
				refresh_token = (String) jsonObj.get("refresh_token");

				System.out.println("acc_to: " + access_token);
				System.out.println("refresh_token : " + refresh_token);

				Naverinfo(request, response, access_token); // Naverinfo라는 메소드에 값들을 전달하여 정보값을 받아올꺼임!
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return "redirect:/Home";
	}

	// 네이버 유저 정보
	private void Naverinfo(HttpServletRequest req, HttpServletResponse response, String access_token) {

		String reqURL = "https://openapi.naver.com/v1/nid/me";
		String name = "";
		String email = "";
		String id = "";
		String nickname = "";
		String naverprofileimage = "";

		UserVo uo = new UserVo();

		try {
			URL url = new URL(reqURL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("Authorization", "Bearer " + access_token);
			int responseCode = con.getResponseCode();
			BufferedReader br;
			System.out.print("responseCode=" + responseCode);
			if (responseCode == 200) {
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			} else {
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			String inputLine;
			StringBuffer res = new StringBuffer();
			while ((inputLine = br.readLine()) != null) {
				res.append(inputLine);
			}
			br.close();

			System.out.println(inputLine);
			// 여기서 사용자 정보들이 json형태로 받아와짐

			if (responseCode == 200) {
				System.out.println(res.toString());
				JSONParser parsing = new JSONParser();
				Object obj = parsing.parse(res.toString());
				JSONObject jsonObj = (JSONObject) obj;
				JSONObject naver_account = (JSONObject) obj;

				naver_account = (JSONObject) jsonObj.get("response");

				id = (String) naver_account.get("id");
				name = (String) naver_account.get("name");
				email = (String) naver_account.get("email");
				nickname = (String) naver_account.get("nickname");
				naverprofileimage = (String) naver_account.get("profile_image");

				// 받아오는 정보 값들

				System.out.println("id : " + id);
				System.out.println("이름 : " + name);
				System.out.println("메일 : " + email);
				System.out.println("닉넴 : " + nickname);
				System.out.println("프로필 이미지 : " + naverprofileimage);

				int check = UserSvc.idCheck(id);

				if (check == 0) {
					uo = new UserVo(id, name, email, nickname, naverprofileimage);
					System.out.println("111111111111111111111" + uo.getUserid());
					System.out.println("111111111111111111111" + uo.getUserimg());

					UserSvc.SetSignUp(uo);

					System.out.println("회원가입 완료");
				} else {
					System.out.println("로그인 됨");
				}

				UserCtr uc = new UserCtr();

				uc.HomeLoginPost(req, uo, null);

				HttpSession session = req.getSession();
				session.setAttribute("session_id", id);

			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// 카카오 콜백
	@RequestMapping(value = "K_callback")
	public String KakaoCallback(HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException {

		System.out.println("카카오 콜백 11111111111111111111111111111111111111111111111111111111");
		String clientId = "1cc6ba13ef828544b70efacdf8c7d570"; // ﻿REST API키!
		String code = request.getParameter("code");
		System.out.println("code : " + code);
		// 아마존 서버
//			String redirectURI = URLEncoder.encode("http://3.34.54.186:8080/Spring_individual_project/K_callback", "UTF-8");
		// 로컬 서버
		String redirectURI = URLEncoder.encode("http://localhost:8080/MyBlog/K_callback", "UTF-8");
		String apiURL;
		apiURL = "https://kauth.kakao.com/oauth/token?grant_type=authorization_code&";
		apiURL += "client_id=" + clientId;
		apiURL += "&redirect_uri=" + redirectURI;
		apiURL += "&code=" + code;
		String access_token = "";
		String refresh_token = "";
		System.out.println("apiURL=" + apiURL);

		try {
			URL url = new URL(apiURL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			int responseCode = con.getResponseCode();
			BufferedReader br;
			System.out.print("responseCode=" + responseCode);
			if (responseCode == 200) {
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			} else {
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			String inputLine;
			StringBuffer res = new StringBuffer();
			while ((inputLine = br.readLine()) != null) {
				res.append(inputLine);
			}
			br.close();
			if (responseCode == 200) {
				System.out.println(res.toString());
				JSONParser parsing = new JSONParser();
				Object obj = parsing.parse(res.toString());
				JSONObject jsonObj = (JSONObject) obj;

				access_token = (String) jsonObj.get("access_token");
				refresh_token = (String) jsonObj.get("refresh_token");

				System.out.println("acc_to: " + access_token);
				System.out.println("refresh_token : " + refresh_token);

				Kakaoinfo(request, response, access_token); // kakaoinfo에 값들을 전달하여 본인 정보를 받아오는 곳
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return "redirect:/Home";
	}

	// 카카오 인포
	private void Kakaoinfo(HttpServletRequest req, HttpServletResponse response, String access_token) {
		System.out.println("카카오 인포 11111111111111111111111111111111111111111111111111111111");

		String reqURL = "https://kapi.kakao.com/v2/user/me";
		String name = "";
		String nickname = "";
		String email = "";
		String profileimg = "";
		long id1 = 0; // id값이 long형태임 (String이나 int는 안됨)

		UserVo uo = new UserVo();

		try {
			URL url = new URL(reqURL);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Authorization", "Bearer " + access_token);
			int responseCode = con.getResponseCode();
			BufferedReader br;
			System.out.print("responseCode=" + responseCode);
			if (responseCode == 200) {
				br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			} else {
				br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			String inputLine;
			StringBuffer res = new StringBuffer();
			while ((inputLine = br.readLine()) != null) {
				res.append(inputLine);
			}
			br.close();
			System.out.println(inputLine); // 여기서 json형태로 정보값들을 받아옴

			if (responseCode == 200) {
				System.out.println(res.toString());
				JSONParser parsing = new JSONParser();
				Object obj = parsing.parse(res.toString());
				JSONObject jsonObj = (JSONObject) obj;
				JSONObject properties = (JSONObject) obj;
				JSONObject kakao_account = (JSONObject) obj;

				properties = (JSONObject) jsonObj.get("properties");
				kakao_account = (JSONObject) jsonObj.get("kakao_account");

				id1 = (long) jsonObj.get("id");
				nickname = (String) properties.get("nickname");
				email = (String) kakao_account.get("email");
				profileimg = (String) properties.get("profile_image");

				System.out.println("properties" + properties);
				System.out.println("id : " + id1);
				System.out.println("nickname : " + nickname);
				System.out.println("메일 : " + email);
				System.out.println("img : " + profileimg);

				String id = String.valueOf(id1);
				int check = UserSvc.idCheck(id);

				if (check == 0) {
					uo = new UserVo(id, name, email, nickname, profileimg);
					UserSvc.SetSignUp(uo);
					System.out.println("회원가입 완료");
				} else {
					System.out.println("로그인 됨");
					
				}
				HttpSession session = req.getSession();
				session.setAttribute("session_id", id);
				session.setAttribute("access_token", access_token);
				
				UserCtr uc = new UserCtr();
				uc.HomeLoginPost(req, uo, null);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	//관리 다중설정 (댓글, 공개설정 여부)
	@RequestMapping(value= "multi", method = RequestMethod.POST)
	public String multi(HttpServletRequest req, HomeVo HomeVo) {
		
		System.out.println("multi 들어옴");
		String [] abc = req.getParameterValues("p_checkbox");
		
		for(String a : abc) {
			HomeVo.setSeq(Integer.parseInt(a));
			System.out.println("public >> " + HomeVo.getPublic_setting());
			System.out.println("reply >> " + HomeVo.getReply_setting());
			HomeSvc.Multi_Edit(HomeVo);
		}
		
		return "redirect:/HomeAdmin";
	}
	

	/* 카테고리 생성 */
	@RequestMapping(value = "Categories", method = RequestMethod.POST)
	public String Categories(HttpServletRequest req, HomeVo HomeVo) {

		HttpSession session = req.getSession();
		String s_id = (String) session.getAttribute("session_id");
		HomeVo.setWriter(s_id);

		HomeSvc.SetCategories(HomeVo);

		return "redirect:/Home";
	}

	/* 카테고리 삭제 */
	@RequestMapping(value = "RemoveCategories", method = RequestMethod.POST)
	public String RemoveCategories(HttpServletRequest req, HomeVo HomeVo) {

		HttpSession session = req.getSession();
		String s_id = (String) session.getAttribute("session_id");
		HomeVo.setWriter(s_id);

		HomeSvc.RemovePost(HomeVo);
		HomeSvc.RemoveCategories(HomeVo);

		return "redirect:/Home";
	}

	/* 관리 */
	@RequestMapping(value = "HomeAdmin")
	public String HomeAdmin(HttpServletRequest req, ModelMap modelMap, HomeSearchVO HomeSearchVO, UserVo UserVo) {

		HttpSession session = req.getSession();
		String s_id = (String) session.getAttribute("session_id");
		HomeSearchVO.setUserid(s_id);

		List<?> Categories = HomeSvc.GetCategories(); // 카테고리 리스트 가져오기
		HomeSearchVO.publicpageCalculate(HomeSvc.selectHomeCount(HomeSearchVO)); // 페이징 처리
		List<?> PostList = HomeSvc.GetMYPostList(HomeSearchVO); // 게시글 리스트
		UserVo = HomeSvc.GetMyInfo(s_id); // 정보 가져오기

		modelMap.addAttribute("Myinfo", UserVo);
		modelMap.addAttribute("Categories", Categories);
		modelMap.addAttribute("so", HomeSearchVO);
		modelMap.addAttribute("PostList", PostList);

		return "home/HomeAdmin";
	}

	/* 글 일괄삭제 */
	@RequestMapping(value = "deleteAction", method = RequestMethod.POST)
	public String deleteAction(HttpServletRequest req, ModelMap modelMap, HomeVo HomeVo) {

		System.out.println("여기는 글 일괄삭제 컨트롤러 입니다. ");
		String[] abc = req.getParameterValues("p_checkbox");

		for (String a : abc) {
			HomeSvc.All_Remove(a);
		}

		return "redirect:/HomeAdmin";
	}

	/* 글 쓰기 페이지 */
	@RequestMapping(value = "Write")
	public String HomeWrite(ModelMap modelMap) {

		List<?> Categories = HomeSvc.GetCategories();

		modelMap.addAttribute("Categories", Categories);

		return "home/HomeWrite";
	}

	/* 글 쓰기 POST방식 데이터 받는 컨트롤러 */
	@RequestMapping(value = "Write", method = RequestMethod.POST)
	public String HomeWritePost(HttpServletRequest request, ModelMap modelMap, HomeVo HomeVo) {
		HttpSession session = request.getSession();
		String s_nick = (String) session.getAttribute("session_usernickname");
		HomeVo.setUsernickname(s_nick);
		HomeSvc.SetWrite(HomeVo);

		return "redirect:/Home";
	}

	// ckeditor 파일 업로드
	@RequestMapping(value = "fileUpload", method = RequestMethod.POST)
	@ResponseBody
	public String fileUpload(HttpServletRequest req, HttpServletResponse resp, MultipartHttpServletRequest multiFile)
			throws Exception {

		System.out.println("파일업로드 넘어옴");
		JsonObject json = new JsonObject();
		PrintWriter printWriter = null;
		OutputStream out = null;
		MultipartFile file = multiFile.getFile("upload");
		if (file != null) {
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

						// json 데이터로 등록
						// {"uploaded" : 1, "fileName" : "test.jpg", "url" : "/img/test.jpg"}
						// 이런 형태로 리턴이 나가야함.
						System.out.println("111111111111111111111111111" + fileUrl);

						json.addProperty("uploaded", 1);
						json.addProperty("fileName", fileName);
						json.addProperty("url", fileUrl);

						printWriter.println(json);
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

	/* 글 보기 디테일 */
	@RequestMapping(value = "HomePost")
	public String HomePostView(HttpServletRequest request, ModelMap modelMap, HomeVo HomeVo) {

		/* 글 디테일 보기 */
		HomeVo = HomeSvc.GetViewDetail(HomeVo);
		/* 부모 댓글 리스트 가져오기 */
		List<HomeCommentVo> CommentList = HomeSvc.GetCommentList(HomeVo);
		// 부모
		List<HomeCommentVo> boardReplyListParent = new ArrayList<HomeCommentVo>();
		// 자식
		List<HomeCommentVo> boardReplyListChild = new ArrayList<HomeCommentVo>();
		// 통합
		List<HomeCommentVo> newBoardReplyList = new ArrayList<HomeCommentVo>();

		for (HomeCommentVo Reply : CommentList) {

			boardReplyListParent.add(Reply);

			/* 자식 댓글 리스트 가져오기 */
			List<HomeCommentVo> ReplyList = HomeSvc.GetReplyList(Reply);
			for (HomeCommentVo d : ReplyList) {
				boardReplyListChild.add(d);
			}
		}

		// 2.부모를 돌린다.
		for (HomeCommentVo boardReplyParent : boardReplyListParent) {
			// 2-1. 부모는 무조건 넣는다.
			// newBoardReplyList.add(boardReplyParent);
			// 3.자식을 돌린다.
			for (HomeCommentVo boardReplyChild : boardReplyListChild) {
				// 3-1. 부모의 자식인 것들만 넣는다.
				if (boardReplyParent.getSeq() == boardReplyChild.getReply_seq()) {
					newBoardReplyList.add(boardReplyChild);
				}
			}
		}
		List<?> Categories = HomeSvc.GetCategories(); // 카테고리 리스트 가져오기

		HttpSession session = request.getSession();
		String s_id = (String) session.getAttribute("session_id");
		if (s_id != null) {
			UserVo UserVo = HomeSvc.GetMyInfo(s_id); // 정보 가져오기
			modelMap.addAttribute("Myinfo", UserVo);
		}
		modelMap.addAttribute("HomeVo", HomeVo);
		modelMap.addAttribute("CommentList", CommentList);
		modelMap.addAttribute("newBoardReplyList", newBoardReplyList);
		modelMap.addAttribute("Categories", Categories);

		return "home/HomePost";
	}

	// Home 글 수정
	@RequestMapping(value = "Edit")
	public String ForumEdit(HttpServletRequest req, HomeVo HomeVo, ModelMap modelMap) {

		HomeVo HomeDetail = HomeSvc.GetViewDetail(HomeVo); // 게시글 내용 보기
		List<?> Categories = HomeSvc.GetCategories(); // 카테고리 리스트 가져오기

		modelMap.addAttribute("HomeDetail", HomeDetail);
		modelMap.addAttribute("Categories", Categories);

		return "home/HomeEdit";
	}

	// Home 글 수정 Post
	@RequestMapping(value = "Edit", method = RequestMethod.POST)
	public String ForumEditPost(HttpServletRequest req, HomeVo HomeVo, ModelMap modelMap) {

		HomeSvc.Edit_Write(HomeVo);
		return "redirect:/Home";
	}

	// Home 글 삭제
	@RequestMapping(value = "Remove_Post")
	public String HomeRemove(HttpServletRequest req, HomeVo HomeVo, ModelMap modelMap) {

		HomeSvc.Remove_Post(HomeVo);

		return "redirect:/Home";
	}

	/* 댓글 쓰기 POST방식 데이터 받는 컨트롤러 */
	@RequestMapping(value = "PostComment", method = RequestMethod.POST)
	public String HomePostComment(HttpServletRequest request, ModelMap modelMap, HomeCommentVo HomeCommentVo,
			UserVo UserVo) {

		UserVo = HomeSvc.GetMyInfo(HomeCommentVo.getUserid());
		HomeCommentVo.setUserimg(UserVo.getUserimg());
		HomeCommentVo.setUsernickname(UserVo.getUsernickname());
		HomeSvc.SetPostComment(HomeCommentVo);

		modelMap.addAttribute("Myinfo", UserVo);

		return "redirect:/HomePost?seq=" + HomeCommentVo.getPost_seq();
	}

	/* 대댓글 쓰기 POST방식 데이터 받는 컨트롤러 */
	@RequestMapping(value = "Commentreply", method = RequestMethod.POST)
	public String HomeCommentreply(HttpServletRequest request, ModelMap modelMap, HomeCommentVo HomeCommentVo,
			UserVo UserVo) {

		UserVo = HomeSvc.GetMyInfo(HomeCommentVo.getUserid());
		HomeCommentVo.setUserimg(UserVo.getUserimg());
		HomeCommentVo.setUsernickname(UserVo.getUsernickname());
		HomeSvc.SetCommentReply(HomeCommentVo);

		modelMap.addAttribute("Myinfo", UserVo);

		return "redirect:/HomePost?seq=" + HomeCommentVo.getPost_seq();
	}

}
