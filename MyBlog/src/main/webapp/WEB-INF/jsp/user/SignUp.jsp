<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script type="text/javascript"
	src="https://code.jquery.com/jquery-3.2.0.min.js"></script>

<link
	href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
	rel="stylesheet" id="bootstrap-css">
<script
	src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>

<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.0.8/css/all.css">
<title>Insert title here</title>

<style type="text/css">
.divider-text {
	position: relative;
	text-align: center;
	margin-top: 15px;
	margin-bottom: 15px;
}

.divider-text span {
	padding: 7px;
	font-size: 12px;
	position: relative;
	z-index: 2;
}

.divider-text:after {
	content: "";
	position: absolute;
	width: 100%;
	border-bottom: 1px solid #ddd;
	top: 55%;
	left: 0;
	z-index: 1;
}

.btn-facebook {
	background-color: #405D9D;
	color: #fff;
}

.btn-twitter {
	background-color: #42AEEC;
	color: #fff;
}
</style>
</head>
<body>
	<div class="container">
		<br>
		<p class="text-center">
			More bootstrap 4 components on <a
				href="http://bootstrap-ecommerce.com/"> Bootstrap-ecommerce.com</a>
		</p>
		<hr>
		<div class="card bg-light">
			<article class="card-body mx-auto" style="max-width: 400px;">
				<h4 class="card-title mt-3 text-center">Create Account</h4>
				<p class="text-center">Get started with your free account</p>
				<p>
					<a href="" class="btn btn-block btn-twitter"> <i
						class="fab fa-twitter"></i>   Login via Twitter
					</a> <a href="" class="btn btn-block btn-facebook"> <i
						class="fab fa-facebook-f"></i>   Login via facebook
					</a>
				</p>
				<p class="divider-text">
					<span class="bg-light">OR</span>
				</p>
				<form class="form" action="SignUp" method="post" name="signform">


					<!-- form-group// -->
					<!-- 아이디 -->
					<div class="form-group input-group">
						<div class="input-group-prepend">
							<span class="input-group-text"> <i class="fa fa-envelope"></i>
							</span>
						</div>
						<input name="userid" id="userid" class="form-control"
							placeholder="ID address" type="text" value="${UserVo.userid}"
							value="<c:out value="${userid}"/>" required>
					</div>
					<div id="id_check"></div>
					<!-- 비밀번호 -->
					<div class="form-group input-group">
						<div class="input-group-prepend">
							<span class="input-group-text"> <i class="fa fa-lock"></i>
							</span>
						</div>
						<input class="form-control pw" placeholder="Create password"
							type="password" name="userpw" id="password_1"
							value="${UserVo.userpw}" required>
					</div>
					<!-- form-group// -->
					<!-- 비밀번호 체크 -->
					<div class="form-group input-group">
						<div class="input-group-prepend">
							<span class="input-group-text"> <i class="fa fa-lock"></i>
							</span>
						</div>
						<input class="form-control pw" placeholder="Repeat password"
							type="password" id="password_2" value="${UserVo.userpw}" required>
					</div>
					<span id="alert-success" style="display: none; color: green;">비밀번호가
						일치합니다.</span> <span id="alert-danger"
						style="display: none; color: #d92742;">비밀번호가 일치하지 않습니다.</span>

					<!-- 이름 -->
					<div class="form-group input-group">
						<div class="input-group-prepend">
							<span class="input-group-text"> <i class="fa fa-user"></i>
							</span>
						</div>
						<input name="username" class="form-control"
							placeholder="Full name" type="text"
							onkeydown="if(event.keyCode==13) signform.usernickname.focus();"
							value="${UserVo.username}" required>
					</div>
					
					<!-- 닉네임 -->
					<div class="form-group input-group">
						<div class="input-group-prepend">
							<span class="input-group-text"> <i class="fa fa-user"></i>
							</span>
						</div>
						<input name="usernickname" class="form-control"
							placeholder="Nick name" type="text"
							onkeydown="if(event.keyCode==13) signform.useremail.focus();"
							value="${UserVo.usernickname}" required>
					</div>
					<!-- form-group// -->
					<!-- 이메일 -->
					<div class="form-group input-group">
						<div class="input-group-prepend">
							<span class="input-group-text"> <i class="fa fa-envelope"></i>
							</span>
						</div>
						<input name="useremail" class="form-control"
							placeholder="Email address" type="email"
							value="${UserVo.useremail}" required> <input
							type="button" value="전송" class="btn btn-primary"
							onclick="submitNum()">
					</div>
					<!-- 인증번호 입력 칸 -->
					<div class="form-group input-group">
						<div class="input-group-prepend">
							<span class="input-group-text"> <i class="fa fa-envelope"></i>
							</span>
						</div>
						<input name="checkNum" class="form-control"
							placeholder="Certification Number" type="text"
							value="${UserVo.checkNum}" required> <input type="button"
							value="인증" onclick="EmailcheckNum()" name="checkbutton"
							class="btn btn-primary"> <input type="hidden"
							value="${UserVo.rdmNum}" name="rdmNum">
					</div>
					<!--   
    <div class="form-group input-group">
    	<div class="input-group-prepend">
		    <span class="input-group-text"> <i class="fa fa-phone"></i> </span>
		</div>
		<select class="custom-select" style="max-width: 120px;">
		    <option selected="">+971</option>
		    <option value="1">+972</option>
		    <option value="2">+198</option>
		    <option value="3">+701</option>
		</select>
    	<input name="" class="form-control" placeholder="Phone number" type="text">
    </div> form-group//
    <div class="form-group input-group">
    	<div class="input-group-prepend">
		    <span class="input-group-text"> <i class="fa fa-building"></i> </span>
		</div>
		<select class="form-control">
			<option selected=""> Select job type</option>
			<option>Designer</option>
			<option>Manager</option>
			<option>Accaunting</option>
		</select> 
	</div> <!-- form-group end.// -->


					<!-- form-group// -->
					<div class="form-group">
						<c:choose>
							<c:when test="${msg != 1}">
								<button type="button" class="btn btn-primary btn-block"
									onclick="alert('인증을 먼저 해주세요.')">Create Account</button>
							</c:when>
							<c:when test="${msg == 1}">
								<button type="submit" class="btn btn-primary btn-block">
									Create Account</button>
							</c:when>
						</c:choose>
					</div>
					<!-- form-group// -->
					<p class="text-center">
						Have an account? <a href="Home">Log In</a>
					</p>
				</form>
			</article>
		</div>
		<!-- card.// -->

	</div>
	<!--container end.//-->
	<script>
		//아이디 유효성 검사(1 = 중복 / 0 != 중복)
		$("#userid").blur(function() {
			// id = "id_reg" / name = "userId"
			let userID = $('#userid').val();

			$.ajax({
				url : '/MyBlog/idCheck2?userid=' + userID,
				type : 'get',
				success : function(data) {
					console.log("1 = 중복o / 0 = 중복x : " + data);
					if (data == 1) {
						// 1 : 아이디가 중복되는 문구
						$("#id_check").text("이미 사용중인 아이디 입니다.");
						$("#id_check").css("color", "#d92742");
					} else {
						if (userID == "") {
							$('#id_check').text(idnull);
							$('#id_check').css('color', '#d92742');
						} else {
							$('#id_check').text("사용 가능한 아이디 입니다.");
							$('#id_check').css('color', 'green');
						}
					}
				},
				error : function() {
					console.log("실패");
				}
			});
		});
	</script>

	<script type="text/javascript">
		function submitNum() {
			alert('인증번호 전송 성공');
			const form = document.querySelector('.form');
			form.action = 'emailCheck';
			form.submit();
		}
	</script>

	<script type="text/javascript">
		function EmailcheckNum() {
			const form = document.querySelector('.form');
			form.action = 'emailCheckGet';
			form.submit();
		}
	</script>

	<c:choose>
		<c:when test="${msg == 0}">
			<script type="text/javascript">
				alert('인증을 먼저 해주세요.');
			</script>
		</c:when>

		<c:when test="${msg == 1}">
			<script type="text/javascript">
				alert('인증 성공');
			</script>
		</c:when>

		<c:when test="${msg == 2}">
			<script type="text/javascript">
				alert('인증 실패');
			</script>
		</c:when>
	</c:choose>

	<script>
    $('.pw').focusout(function () {
        var pwd1 = $("#password_1").val();
        var pwd2 = $("#password_2").val();
  
        if ( pwd1 != '' && pwd2 == '' ) {
            null;
        } else if (pwd1 != "" || pwd2 != "") {
            if (pwd1 == pwd2) {
                $("#alert-success").css('display', 'inline-block');
                $("#alert-danger").css('display', 'none');
            } else {
                alert("비밀번호가 일치하지 않습니다. 비밀번호를 재확인해주세요.");
                $("#alert-success").css('display', 'none');
                $("#alert-danger").css('display', 'inline-block');
            }
        }
    });
</script>




</body>
</html>