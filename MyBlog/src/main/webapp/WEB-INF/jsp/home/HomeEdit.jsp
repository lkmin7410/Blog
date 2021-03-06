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
					<li class="nav-item"><a class="nav-link" href="#!">About</a></li>
					<li class="nav-item"><a class="nav-link" href="#!">Services</a></li>
					<li class="nav-item"><a class="nav-link" href="#!">Contact</a></li>
				</ul>
			</div>
		</div>
	</nav>
	<!-- Page content-->

	<form action="Edit" method="post">
		<div class="container">
			<div class="row" style="display: block;">
				<!-- Post content-->
				<div class="col-lg-8" style="max-width: 100%;">
					<!-- Title-->
						<div class="form-group" style="margin-top: 50px;">
						<select name="categories" class="form-control">
						<option>카테고리를 선택해주세요.</option>
						<c:forEach var="c" items="${Categories}">
						<c:if test="${HomeDetail.categories == c.categories}"></c:if>
							<option value="${c.categories}" selected="${HomeDetail.categories}">${c.categories}</option>
						</c:forEach>
						</select>
						<br>
						 <input type="text" name="title" placeholder="제목을 입력해 주세요."
							class="form-control" style="border-color: rgb(24, 26, 27);" required value="${HomeDetail.title}">
					</div>
					<hr />
					<!-- Post content-->
					<div class="form-group">
						<textarea name="content" id="p_content" class="form-control"
							rows="30">${HomeDetail.content}</textarea>

					</div>
					<hr />
					<input type="hidden" name="writer"
						value="${sessionScope.session_id}">
						 <input type="hidden" name="seq" value="${HomeDetail.seq}">
						 <input type="button" class="btn btn-primary save" value="저장">
						 <input type="button" class="btn btn-primary" value="취소" onclick="location.href='Home'">
					<div class="save_button" style="display: none;">
						<span>공개 여부</span>
						<input type="radio" name="public_setting" checked="checked" value="전체공개">전체 공개
						<input type="radio" name="public_setting" value="비공개">비공개
						<br>
						<span>댓글 여부</span>
						<input type="radio" name="reply_setting" checked="checked" value="Y">허용
						<input type="radio" name="reply_setting" value="N">불허
						
						<script type="text/javascript">
						$(function() {
							$("input[name='public_setting'][value='" + (("${HomeDetail.public_setting}" == '') ? "Y" : "${HomeDetail.public_setting}") + "']").prop("checked", true);   // radio버튼
						    $("input[name='reply_setting'][value='" + (("${HomeDetail.reply_setting}" == '') ? "Y" : "${HomeDetail.reply_setting}") + "']").prop("checked", true);   // radio버튼
						});
						</script>
						
						<br>
					 <button class="btn btn-primary">저장</button>
					</div>
				</div>
			</div>
		</div>
	</form>
	<script type="text/javascript">
	$(function() {
		$('.save').click(function() {
			$('.save_button').toggle();
			$('.save').hide();
		});
	});
	</script>


	<script type="text/javascript">
							$(function() {
								CKEDITOR.replace('p_content', {
									filebrowserUploadUrl : 'fileUpload'
								});
							});
						</script>
	<script type="text/javascript">
							CKEDITOR.config.disallowedContent = 'img{width,height}';
							CKEDITOR.on('instanceReady',function(ev) {
										ev.editor.dataProcessor.htmlFilter.addRules({
										elements : {$ : function(element) {
										if (element.name == 'img') {
										if (element.attributes.style) {
										element.attributes.style = element.attributes.style
										.replace(/(height|width)[^;]*;/gi,'');}}
										if (!element.attributes.style)
										delete element.attributes.style;
										return element;}
										}
										});
										});
						</script>
	<script type="text/javascript">
							CKEDITOR.on('dialogDefinition',function(ev) {
							var dialogName = ev.data.name;
							var dialog = ev.data.definition.dialog;
							var dialogDefinition = ev.data.definition;
							if (dialogName == 'image') {dialog.on('show',function(obj) {
							this.selectPage('Upload'); //업로드텝으로 시작
							});
							dialogDefinition.removeContents('advanced'); // 자세히탭 제거
							dialogDefinition.removeContents('Link'); // 링크탭 제거
							}
							});
						</script>
	<!-- Bootstrap core JS-->
	<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"></script>
	<!-- Core theme JS-->
	<script src="js/scripts.js"></script>
</body>
</html>