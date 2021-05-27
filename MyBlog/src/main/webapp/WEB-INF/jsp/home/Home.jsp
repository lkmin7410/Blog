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
				<a class="navbar-brand" href="Home">${Myinfo.userblogname}</a>
			</c:if>

			<button class="navbar-toggler" type="button" data-toggle="collapse"
				data-target="#navbarResponsive" aria-controls="navbarResponsive"
				aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarResponsive">
				<ul class="navbar-nav ml-auto">
					<li class="nav-item active"><a class="nav-link" href="#!">
							Home <span class="sr-only">(current)</span>
					</a></li>
					<c:if test="${not empty sessionScope.session_id}">
						<li class="nav-item"><a class="nav-link" href="Logout">로그아웃</a></li>
					</c:if>

					<c:if test="${empty sessionScope.session_id}">
						<li class="nav-item"><a class="nav-link" href="SignUp">회원가입</a></li>
					</c:if>
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
							<small>전체 보기</small>
						</c:if>

						<c:if test="${not empty so.categories}">
							<small>${so.categories}</small>
							<input type="hidden" name="categories" value="${so.categories}">
						</c:if>
					</h1>
				</form>

				<!-- Blog post-->
				<c:forEach var="i" items="${PostList}">
					<c:choose>
						<c:when test="${i.public_setting =='전체공개'}">
							<div class="card mb-4">
								<!-- <img class="card-img-top"
							src="https://via.placeholder.com/750x300" alt="Card image cap" /> -->
								<div class="card-body">
									<h2 class="card-title">${i.title}</h2>
									<p class="card-text">${i.content}</p>
									<a class="btn btn-primary" href="HomePost?seq=${i.seq}">Read
										More →</a>
								</div>
								<div class="card-footer text-muted">${i.regdate}</div>
							</div>
						</c:when>
						<c:when test="${sessionScope.session_id == i.writer}">
							<div class="card mb-4">
								<!-- <img class="card-img-top"
							src="https://via.placeholder.com/750x300" alt="Card image cap" /> -->
								<div class="card-body">
									<c:if test="${i.public_setting == '비공개'}">
										<h2 class="card-title">🔒 ${i.title}</h2>
									</c:if>
									<p class="card-text">${i.content}</p>
									<a class="btn btn-primary" href="HomePost?seq=${i.seq}">Read
										More →</a>
								</div>
								<div class="card-footer text-muted">${i.regdate}</div>
							</div>
						</c:when>

					</c:choose>
				</c:forEach>

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
								href="Home?page=${startNum+i}&searchKeyword=${so.searchKeyword}&categories=${so.categories}">${startNum+i}</a></li>

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
				<form action="Home">
					<!-- Search widget-->
					<div class="card my-4">
						<h5 class="card-header">Search</h5>
						<div class="card-body">
							<div class="input-group">
								<input class="form-control" type="text"
									placeholder="Search for..." name="searchKeyword" /> <span
									class="input-group-append"><button
										class="btn btn-secondary" type="submit">Go!</button></span>
							</div>
						</div>
					</div>
				</form>

				<c:if test="${empty sessionScope.session_id}">
					<form action="Login" method="post">
						<!-- Search widget-->
						<div class="card my-4">
							<h5 class="card-header">Login</h5>
							<div class="card-body">
								<div class="input-group">
									<input class="form-control" type="text" placeholder="ID"
										name="userid" />
								</div>
								<br>
								<div class="input-group">
									<input class="form-control" type="password"
										placeholder="PASSWORD" name="userpw" /> <span
										class="input-group-append"><button
											class="btn btn-secondary" type="submit">Go!</button></span>
								</div>
								<a href="#" class="morebutton">more..</a>

								<div class="more" style="display: none;">
									<hr>
									<ul class="mb-0">
										<li><a href="${N_apiURL}"> 네이버 로그인</a></li>
										<li>카카오 로그인</li>
										<li><a href="SignUp">회원가입</a></li>
									</ul>
								</div>
							</div>



							<script type="text/javascript">
								$(function() {
									$('.morebutton').click(function() {
										$('.more').toggle();
									});
								});
							</script>
						</div>
					</form>
				</c:if>

				<!-- 프로필? -->
				<c:if test="${not empty sessionScope.session_id}">
					<div class="card my-4">
						<h5 class="card-header">Profile</h5>
						<div class="card-body">
							<div class="row">
								<div class="col-lg-6"
									style="border: solid 1px white; padding: 5px;">
									<ul class="list-unstyled mb-0">
										<li><img alt="" src="${Myinfo.userimg}" /></li>
										<li><c:if test="${empty Myinfo.userpw}">
										(네이버)
										</c:if><a href="#!">${Myinfo.usernickname} </a></li>
										<li>
											<p>${Myinfo.userIntroduction}</p>
										</li>

									</ul>
								</div>
								<div class="col-lg-6">
									<ul class="list-unstyled mb-0">
										<li><a href="Write">글 쓰기</a></li>
										<li><a href="HomeAdmin">관리</a></li>
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
										<li><a href="Home?categories=${Categories.categories}"
											class="recent">${Categories.categories}</a></li>
									</c:forEach>
								</ul>
							</div>
						</div>
					</div>
				</div>

				<!-- Side widget-->
				<div class="card my-4">
					<h5 class="card-header">Recent Comments</h5>
					<div class="card-body">
						<div>
							<div>
								<!-- <ul class="list-unstyled mb-0"> -->
								<ul class="mb-0">
									<c:forEach var="r" items="${CommentList}" begin="0" end="4">
										<li><a href="HomePost?seq=${r.seq}" class="recent">${r.title}</a></li>
									</c:forEach>
								</ul>
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
</body>
</html>
