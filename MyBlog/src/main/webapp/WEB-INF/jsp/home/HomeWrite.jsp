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
<script type="text/javascript" src="js/ckeditor/ckeditor.js"></script>

<style>
/* 
.form-control{
border-color:rgb(24, 26, 27); 
} */
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

	<form action="Write" method="post">
		<div class="container">
			<div class="row" style="display: block;">
				<!-- Post content-->
				<div class="col-lg-8" style="max-width: 100%;">
					<!-- Title-->
					<div class="form-group" style="margin-top: 50px;">
						<input type="text" name="title" placeholder="제목"
							class="form-control" style="border-color: rgb(24, 26, 27);">
					</div>
					<hr />
					<!-- Post content-->
					<div class="form-group">
						<textarea name="content" id="p_content" class="form-control"
							rows="30"></textarea>
						<script type="text/javascript">
							$(function() {
								CKEDITOR.replace('p_content', {
								/* filebrowserUploadUrl : 'fileUpload' */
								});
							});
						</script>
					</div>
					<hr />
					<input type="hidden" name="writer"
						value="${sessionScope.session_id}">
					<button class="btn btn-primary" type="submit">Submit</button>
				</div>
			</div>
		</div>
	</form>

	<!-- Bootstrap core JS-->
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"></script>
	<!-- Core theme JS-->
	<script src="js/scripts.js"></script>
</body>
</html>