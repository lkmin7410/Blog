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
<title>Blog Post - Start Bootstrap Template</title>
<!-- Favicon-->
<link rel="icon" type="image/x-icon" href="assets/favicon.ico" />
<!-- Core theme CSS (includes Bootstrap)-->
<link href="css/styles.css" rel="stylesheet" />
<script type="text/javascript"
	src="https://code.jquery.com/jquery-3.2.0.min.js"></script>

<style>
p>img{
 max-width: 100%;
  height: auto;
}

.hide{
display: none;
}
</style>

</head>
<body>
	<!-- Navigation-->
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
		<div class="container">
			<a class="navbar-brand" href="Home">Start Bootstrap</a>
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
					<li class="nav-item"><a class="nav-link" href="#!">About</a></li>
					<li class="nav-item"><a class="nav-link" href="#!">Services</a></li>
					<li class="nav-item"><a class="nav-link" href="#!">Contact</a></li>
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
					by <a href="#!">${HomeVo.writer}</a>
				</p>
				<hr />
				<!-- Date and time-->
				<p style="display: inline;">${HomeVo.regdate}</p>
				<c:if test="${sessionScope.session_id == HomeVo.writer}">
				<button class="btn btn-primary" type="button" onclick="location.href='Remove_Post?seq=${HomeVo.seq}'" style="float: right; margin-left: 5px;">삭제</button>
				<button class="btn btn-primary" type="button" onclick="location.href='Edit?seq=${HomeVo.seq}'" style="float: right;">수정</button>
				</c:if>
				<hr />
				<!-- Preview image-->
				<!-- <img class="img-fluid rounded"
					src="https://via.placeholder.com/900x300" alt="..." /> -->
				<!-- <hr /> -->
				<!-- Post content-->
				<p class="lead">${HomeVo.content}</p>
				<hr />
				<!-- Comments form-->
				<div class="card my-4">
					<h5 class="card-header">Leave a Comment:</h5>
					<div class="card-body">
						<form action="PostComment" method="post">
							<div class="form-group">
								<textarea name="comment" class="form-control" rows="3"></textarea>
							</div>
							<input type="hidden" name="userid"
								value="${sessionScope.session_id}"> <input type="hidden"
								name="post_seq" value="${HomeVo.seq}">
							<button class="btn btn-primary" type="submit">Submit</button>
						</form>
					</div>
				</div>
				<!-- Single comment-->
				<c:forEach var="co" items="${CommentList}"  varStatus="status">
					<div class="media mb-4">
						<img class="d-flex mr-3 rounded-circle"
							src="https://via.placeholder.com/50x50" alt="..." />
						<div class="media-body">
							<h5 class="mt-0">Commenter Name :: ${co.userid}</h5>
							${co.comment} <br> ${co.regdate} <br>
							<button class="btn btn-primary reply_button${status.count}" >Reply</button>
							
							<!-- 대댓글 리스트 -->
							<c:forEach var="cr" items="${newBoardReplyList}">
							<c:if test="${co.seq == cr.reply_seq}">
							<div class="media mt-4">
							<img class="d-flex mr-3 rounded-circle"
								src="https://via.placeholder.com/50x50" alt="..." />
							<div class="media-body">
								<h5 class="mt-0">Commenter Name :: ${cr.userid}</h5>
								${cr.comment} <br> ${cr.regdate}
							</div>
						</div>
						</c:if>
						</c:forEach>
						
							<!-- 대댓글 입력 창 -->
							<div class="card my-4 reply${status.count} hide" >
								<h5 class="card-header">Leave a Comment:</h5>
								<div class="card-body">
									<form action="Commentreply" method="post">
										<div class="form-group">
											<textarea name="comment" class="form-control" rows="3"></textarea>
										</div>
										<input type="hidden" name="userid"
											value="${sessionScope.session_id}"> <input
											type="hidden" name="reply_seq" value="${co.seq}">
											<input type="hidden" name="post_seq" value="${HomeVo.seq}">
										<button class="btn btn-primary" type="submit">Submit</button>
									</form>
								</div>
							</div>
							
							<script type="text/javascript">
							$(function(){
								$('.reply_button${status.count}').click(function(){
									$('.reply${status.count}').toggle();
								});
							});
                            </script>
						</div>
					</div>
				</c:forEach>
				
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
						<div class="row">
							<div class="col-lg-6">
								<ul class="list-unstyled mb-0">
									<li><a href="#!">Web Design</a></li>
									<li><a href="#!">HTML</a></li>
									<li><a href="#!">Freebies</a></li>
								</ul>
							</div>
							<div class="col-lg-6">
								<ul class="list-unstyled mb-0">
									<li><a href="#!">JavaScript</a></li>
									<li><a href="#!">CSS</a></li>
									<li><a href="#!">Tutorials</a></li>
								</ul>
							</div>
						</div>
					</div>
				</div>
				<!-- Side widget-->
				<div class="card my-4">
					<h5 class="card-header">Side Widget</h5>
					<div class="card-body">You can put anything you want inside
						of these side widgets. They are easy to use, and feature the new
						Bootstrap 4 card containers!</div>
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
