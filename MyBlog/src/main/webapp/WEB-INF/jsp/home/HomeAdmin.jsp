<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no" />
<meta name="description" content="" />
<meta name="author" content="" />
<title>경민이의 즐거운 코딩생활</title>
<!-- Favicon-->
<link rel="icon" type="image/x-icon" href="assets/favicon.ico" />
<!-- Core theme CSS (includes Bootstrap)-->
<link href="css/styles.css" rel="stylesheet" />
<script type="text/javascript"
	src="https://code.jquery.com/jquery-3.2.0.min.js"></script>

<style type="text/css">
.card-title, .card-text {
	white-space: nowrap;
	overflow: hidden;
	text-overflow: ellipsis;
}

.card-body img {
	max-width: 100%;
	height: auto;
}

.my_info {
	display: inline-block;
	float: left;
	width: 150px;
}

.my_info_blogname {
	width: 50%;
}

.info {
	display: none;
}

.menu td {
	font-size: 10pt;
}

table {
	text-align: center;
}

.newcategories {
	display: none;
}
</style>

</head>
<body>
	<!-- Navigation-->
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
		<div class="container">
			<c:if test="${empty sessionScope.session_id}">
				<a class="navbar-brand" href="Home">블로그</a>
			</c:if>

			<c:if test="${not empty sessionScope.session_id}">
				<a class="navbar-brand" href="Home">${Myinfo.userblogname} </a>
			</c:if>

			<button class="navbar-toggler" type="button" data-toggle="collapse"
				data-target="#navbarResponsive" aria-controls="navbarResponsive"
				aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarResponsive">
				<ul class="navbar-nav ml-auto">
					<li class="nav-item active"><a class="nav-link" href="Home">
							Home <span class="sr-only">(current)</span>
					</a></li>
					<%--  					<c:if test="${not empty sessionScope.session_id}">
						<li class="nav-item"><a class="nav-link" href="Login">로그아웃</a></li>
					</c:if>

					<c:if test="${empty sessionScope.session_id}">
						<li class="nav-item"><a class="nav-link" href="SignUp">회원가입</a></li>
					</c:if> --%>
				</ul>
			</div>
		</div>
	</nav>
	<!-- Page content-->
	<div class="container">
		<div class="row">
			<!-- Blog entries-->
			<div class="col-md-8">
				<form class="c_form">
					<h1 class="my-4">
						Categories :
						<c:if test="${empty so.categories}">
							<small>관리</small>
						</c:if>

						<c:if test="${not empty so.categories}">
							<small>${so.categories}</small>
							<input type="hidden" name="categories" value="${so.categories}">
						</c:if>
					</h1>
				</form>
				
				<!-- 내 정보 수정하는 div -->
				
				<form action="MyinfoEdit" method="post" class="profile"
					enctype="multipart/form-data">
					<!-- 내 정보 -->
					<div class="card mb-4 info">
						<!-- <img class="card-img-top"
							src="https://via.placeholder.com/750x300" alt="Card image cap" /> -->
						<div class="card-body">
							<p class="card-text my_info">블로그명</p>
							<input class="form-control my_info_blogname" type="text"
								name="userblogname" value="${Myinfo.userblogname}">
							<p>한글, 영문, 숫자 혼용가능 (한글 기준 25자 이내)</p>
							<hr>
							<p class="card-text my_info">별명</p>
							<input class="form-control my_info_blogname" type="text"
								name="usernickname" value="${Myinfo.usernickname}">
							<p>한글, 영문, 숫자 혼용가능 (한글 기준 25자 이내)</p>
							<hr>
							<p class="card-text my_info">소개글</p>
							<textarea class="form-control my_info_blogname"
								name="userIntroduction" rows="5" cols="3">${Myinfo.userIntroduction}</textarea>
							<p>블로그 프로필 영역의 프로필 이미지 아래에 반영됩니다. (한글 기준 200자 이내)</p>
							<hr>
							<p class="card-text my_info">프로필 이미지</p>
							<input type="file" name="img_file">

							<hr>
							<input type="hidden" name="userid" value="${Myinfo.userid}">
							<input type="submit" value="수정">
						</div>
					</div>
				</form>
				
				<!-- 메뉴,글 관리 -->
				<div class="card mb-4 menu">
					<div class="card-body">
						<form action="HomeAdmin" name="s_form" method="post">
							<input type="checkbox" onclick="selectAll(this)"
								style="float: left; position: relative; top: 11px; margin-right: 5px;">
							<table>
								<tr>
									<td style="width: 150px;"><select id="Select_Categories"
										name="categories" class="form-control"
										onchange="categories_select()" style="font-size: 10pt;">
											<option value="">카테고리 전체</option>
											<c:forEach var="c" items="${Categories}">
												<option ${(param.categories==c.categories)?"selected":""}
													value="${c.categories}">${c.categories}</option>
											</c:forEach>
									</select></td>

									<td style="width: 150px;"><select id="Select_public"
										name="public_setting" class="form-control"
										onchange="public_select()" style="font-size: 10pt;">
											<option value="">공개설정 전체</option>
											<option ${(param.public_setting=="전체공개")?"selected":""}
												value="전체공개">전체공개</option>
											<option ${(param.public_setting=="비공개")?"selected":""}
												value="비공개">비공개</option>
									</select></td>
									<td style="width: 100px ; text-align: center;"> 댓글 </td>
									<td style="width: 150px; text-align: center;">제목</td>
									<td style="width: 150px;">등록일</td>
								</tr>
							</table>
						</form>
						<hr>
						<!-- 게시글 리스트 -->
						<form action="deleteAction" method="post" name="p_form">
							<table>
								<c:forEach var="p" items="${PostList}">
									<tr
										style="border-bottom: solid 0.1px rgba(140, 130, 115, 0.1);">
										<td style="width: 150px;"><input type="checkbox"
											name="p_checkbox" value="${p.seq}"
											style="float: left; margin-right: 5px; margin-top: 4px;">

											<p>${p.categories}</p></td>
										<td style="width: 150px;">
											<p>${p.public_setting}</p>
										</td>
										<td style="width: 100px; text-align: center;">
											<p>${p.reply_setting}</p>
										</td>
										<td style="width: 150px; text-align: center;">
											<p>${p.title}</p>
										</td>
										<td>
											<p>${p.regdate}</p>
										</td>

									</tr>
								</c:forEach>

							</table>
							<input type="submit" value="삭제">
						 <button type="button" class="public_button">관리</button>
						<div class="public_div" style="display: none;">
										
					<div class="save_button">
						<span>공개 여부</span>
						<input type="radio" name="public_setting" checked="checked" value="전체공개">전체 공개
						<input type="radio" name="public_setting" value="비공개">비공개
						<br>
						<span>댓글 여부</span>
						<input type="radio" name="reply_setting" checked="checked" value="Y">허용
						<input type="radio" name="reply_setting" value="N">불허
						<br>
					 <button class="btn btn-primary" onclick = "multi_b()">저장</button>
					</div>
						
						<!-- 	<input type="button" onclick="public_a(1)" value="전체공개">
							<input type="button" onclick="public_a(2)" value="비공개"> -->
						</div>
						</form>
					</div>
				</div>
				
				<script type="text/javascript">
					function multi_b() {
						document.p_form.action='multi';
						document.p_form.submit();
					}
				</script>
				
				
				<script type="text/javascript">
				$(function() {
					$('.public_button').click(function() {
						$('.public_div').toggle();
					});
				});
				</script>
				
				<script type="text/javascript">
					function public_a(index) {
						if(index == 1){
							document.p_form.action='multi_public';
						}
						if(index == 2){
							document.p_form.action='multi_private';
						}
						document.p_form.submit();
					}
				</script>
				

				<!-- 페이징 설정을 위한 Startnum, Lastnum, Page 변수 -->
				<c:set var="page" value="${so.page}"></c:set>
				<c:set var="startNum" value="${page-(page-1)%5}"></c:set>
				<c:set var="lastNum" value="${so.pageEnd}"></c:set>

				<!-- Pagination-->
				<ul class="pagination justify-content-center mb-4">
					<c:if test="${startNum > 1}">
						<li class="page-item"><a class="page-link"
							href="?page=${startNum-1}">← Older</a></li>
					</c:if>
					<c:if test="${startNum <= 1}">
						<li><a class="page-link" href="#"
							onclick="alert('첫번째 페이지 입니다.');">← Older</a></li>
					</c:if>

					<!-- 페이징 -->
					<c:forEach var="i" begin="0" end="4">
						<c:if test="${(startNum+i) <= lastNum}">
							<li><a class="page-link"
								href="HomeAdmin?page=${startNum+i}&categories=${so.categories}&public_setting=${so.public_setting}">${startNum+i}</a></li>

						</c:if>
					</c:forEach>
					<c:if test="${startNum+5 <= lastNum}">
						<li class="page-item disabled"><a class="page-link"
							href="?page=${startNum+5}">Newer →</a></li>
					</c:if>
					<c:if test="${startNum+5 > lastNum}">
						<li><a class="page-link" href="#"
							onclick="alert('마지막 페이지 입니다.');">Newer →</a></li>
					</c:if>
				</ul>
			</div>

			<!-- Side widgets-->
			<div class="col-md-4">
				<!-- 프로필? -->
				<c:if test="${not empty sessionScope.session_id}">
					<div class="card my-4">
						<h5 class="card-header">Profile</h5>
						<div class="card-body">
							<div class="row">
								<div class="col-lg-6">
									<ul class="list-unstyled mb-0">
										<li><a href="#!" class="menu_button">메뉴·글 관리</a></li>
									</ul>
								</div>
								<div class="col-lg-6">
									<ul class="list-unstyled mb-0">
										<li><a href="#!" class="info_button">내 정보</a></li>
									</ul>
								</div>
							</div>
						</div>
					</div>
				</c:if>

				<!-- Categories widget-->
				<div class="card my-4">
					<h5 class="card-header">Categories</h5>
					<div class="card-body">
						<div>
							<div>
							
								<ul class="mb-0">
									<li><a href="Home" class="recent">전체 보기</a></li>
									<c:forEach var="Categories" items="${Categories}">
									
										<li>
										<form action="RemoveCategories" method="post">
											<a href="#"class="recent">
																     ${Categories.categories}
												<c:if test="${Categories.writer == sessionScope.session_id}">
													<input type="hidden" name="categories" value="${Categories.categories}">
														<button class="btn" type="submit" style="color: red">X</button>
												</c:if>
											</a>
										</form>		
										</li>
											 
									</c:forEach>
								</ul>
							
								<c:if test="${not empty sessionScope.session_id}">
									<hr>
									<ul class="list-unstyled mb-0">
										<li style="float: right;">
											<button class="btn btn-secondary categories">New!</button>
										</li>
									</ul>
									<div class="newcategories">
										<form action="Categories" method="post">
											<input class="form-control" type="text"
												placeholder="New Categories..." name="categories"> <span
												class="input-group-append">
												
												<button
													class="btn btn-secondary" type="submit">생성</button></span>
										</form>
									</div>
								</c:if>
								<script type="text/javascript">
									$(function() {
										$('.categories').click(function() {
											$('.newcategories').toggle();
										});
									});
								</script>

							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- Footer-->
	<footer class="py-5 bg-dark">
		<div class="container">
			<p class="m-0 text-center text-white">Copyright &copy; Your
				Website 2021</p>
		</div>
	</footer>
	<!-- Bootstrap core JS-->
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"></script>
	<!-- Core theme JS-->
	<script src="js/scripts.js"></script>

	<script type="text/javascript">
		$(function() {
			$('.menu_button').click(function() {
				$('.menu').show();
				$('.info').hide();
				$('.pagination').show();
			});

			$('.info_button').click(function() {
				$('.menu').hide();
				$('.info').show();
				$('.pagination').hide();
			});
		});
	</script>

	<script type="text/javascript">
							function categories_select() {
								var sc = document.getElementById("Select_Categories");
								var categories = sc.options[sc.selectedIndex].value;
								document.s_form.submit();
							}
						</script>

	<script type="text/javascript">
							function public_select() {
								var pc = document.getElementById("Select_public");
								var public_setting = pc.options[pc.selectedIndex].value;
								document.s_form.submit();
							}
						</script>

	<script type="text/javascript">
						function selectAll(selectAll)  {
							  const checkboxes 
							       = document.getElementsByName('p_checkbox');

							  checkboxes.forEach((checkbox) => {
							    checkbox.checked = selectAll.checked;
							  })
							}
						</script>
</body>
</html>
