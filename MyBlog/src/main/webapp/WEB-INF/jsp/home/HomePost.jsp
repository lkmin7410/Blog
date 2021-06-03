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

<style>
p>img {
	max-width: 100%;
	height: auto;
}

.hide {
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
					<li class="nav-item active"><a class="nav-link" href="Home">
							Home <span class="sr-only">(current)</span>
					</a></li>

				</ul>
			</div>
		</div>
	</nav>
	<!-- Page content-->
	<div class="container">
		<div class="row">
			<!-- Post content-->
			<div class="col-lg-8">
				<!-- Title-->
				<h1 class="mt-4">${HomeVo.title}</h1>
				<!-- Author-->
				<p class="lead">
					by <a href="#!">${HomeVo.usernickname}</a>
				</p>
				<hr />
				<!-- Date and time-->
				<p style="display: inline;">${HomeVo.regdate}</p>
				<c:if test="${sessionScope.session_id == HomeVo.writer}">
					<button class="btn btn-primary" type="button"
						onclick="location.href='Remove_Post?seq=${HomeVo.seq}'"
						style="float: right; margin-left: 5px;">삭제</button>
					<button class="btn btn-primary" type="button"
						onclick="location.href='Edit?seq=${HomeVo.seq}'"
						style="float: right;">수정</button>
				</c:if>
				<hr />
				<!-- Preview image-->
				<!-- <img class="img-fluid rounded"
					src="https://via.placeholder.com/900x300" alt="..." /> -->
				<!-- <hr /> -->
				<!-- Post content-->
				<p class="lead">${HomeVo.content}</p>
				<hr />
				<c:if test="${HomeVo.reply_setting == 'Y'}">
				<c:if test="${not empty sessionScope.session_id}">
					<!-- Comments form-->
					<div class="card my-4">
						<h5 class="card-header">Leave a Comment:</h5>
						<div class="card-body">
							<form action="PostComment" method="post">
								<div class="form-group">
									<textarea name="comment" class="form-control" rows="3"></textarea>
								</div>
								<input type="hidden" name="userid"
									value="${sessionScope.session_id}"> <input
									type="hidden" name="post_seq" value="${HomeVo.seq}"> <input
									type="hidden" name="userimg" value="${Myinfo.userimg}">
									<input type="hidden" value="${Myinfo.usernickname}"> 
								<button class="btn btn-primary" type="submit">Submit</button>
							</form>
						</div>
					</div>
				</c:if>
				<c:if test="${empty sessionScope.session_id}">
				<!-- Comments form-->
					<div class="card my-4">
						<h5 class="card-header">Leave a Comment:</h5>
						<div class="card-body">
								<div class="form-group">
									<textarea name="comment" class="form-control" rows="3" readonly>로그인 후에 이용해 주세요.</textarea>
								</div>
						</div>
					</div>
				</c:if>
				
					<!-- Single comment-->
					<c:forEach var="co" items="${CommentList}" varStatus="status">
						<div class="media mb-4">
							<img class="d-flex mr-3 rounded-circle" src="${co.userimg}"
								alt="..." style="width: 50px; height: 50px;" />
							<div class="media-body">
								<h5 class="mt-0">Commenter Name :: ${co.usernickname}</h5>
								${co.comment} <br> ${co.regdate} <br>
								<c:if test="${not empty sessionScope.session_id}">
								<button class="btn btn-primary reply_button${status.count}">Reply</button>
								</c:if>	
								<!-- 대댓글 리스트 -->
								<c:forEach var="cr" items="${newBoardReplyList}">
									<c:if test="${co.seq == cr.reply_seq}">
										<div class="media mt-4">
											<img class="d-flex mr-3 rounded-circle" src="${cr.userimg}"
												alt="..." style="width: 50px; height: 50px;" />
											<div class="media-body">
												<h5 class="mt-0">Commenter Name :: ${cr.usernickname}</h5>
												${cr.comment} <br> ${cr.regdate}
											</div>
										</div>
									</c:if>
								</c:forEach>

								<!-- 대댓글 입력 창 -->
								<div class="card my-4 reply${status.count} hide">
									<h5 class="card-header">Leave a Comment:</h5>
									<div class="card-body">
										<form action="Commentreply" method="post">
											<div class="form-group">
												<textarea name="comment" class="form-control" rows="3"></textarea>
											</div>
											<input type="hidden" name="userid"
												value="${sessionScope.session_id}"> <input
												type="hidden" name="reply_seq" value="${co.seq}"> <input
												type="hidden" name="post_seq" value="${HomeVo.seq}">
											<input type="hidden" name="userimg" value="${Myinfo.userimg}">
											<button class="btn btn-primary" type="submit">Submit</button>
										</form>
									</div>
								</div>

								<script type="text/javascript">
									$(function() {
										$('.reply_button${status.count}')
												.click(
														function() {
															$(
																	'.reply${status.count}')
																	.toggle();
														});
									});
								</script>
							</div>
						</div>
					</c:forEach>

				</c:if>
				<c:if test="${HomeVo.reply_setting == 'N'}">
					<div class="card my-4">
						<h5 class="card-header" style="text-align: center;">댓글을 남길 수
							없는 게시글입니다.</h5>

					</div>
				</c:if>

			</div>
			<!-- Sidebar widgets column-->
			<div class="col-md-4">
				<!-- Search widget-->
				<div class="card my-4">
					<h5 class="card-header">Search</h5>
					<div class="card-body">
						<div class="input-group">
							<input class="form-control" type="text"
								placeholder="Search for..." /> <span class="input-group-append"><button
									class="btn btn-secondary" type="button">Go!</button></span>
						</div>
					</div>
				</div>
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
				<!-- Side widget-->
				<!-- <div class="card my-4">
					<h5 class="card-header">Side Widget</h5>
					<div class="card-body">You can put anything you want inside
						of these side widgets. They are easy to use, and feature the new
						Bootstrap 4 card containers!</div>
				</div> -->
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
