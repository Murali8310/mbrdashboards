<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html dir="ltr">

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<!-- Tell the browser to be responsive to screen width -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<!-- Favicon icon -->
<link rel="icon" type="image/png" sizes="20x20"
	href="assets/images/titan-logo.png">
<title>ISCM Stationery</title>
<!-- Custom CSS -->
<link href="dist/css/style.min.css" rel="stylesheet">

<link href="dist/css/loading.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="dist/js/AesUtil.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/4.1.1/crypto-js.min.js"
	integrity="sha512-E8QSvWZ0eCLGk4km3hxSsNmGWbLtSCSUcewDQPQWZF6pEU8GlT8a5fF32wOl1i8ftdMhssTrF/OhyGWwonTcXA=="
	crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/4.1.1/aes.min.js"
	integrity="sha512-4b1zfeOuJCy8B/suCMGNcEkMcQkQ+/jQ6HlJIaYVGvg2ZydPvdp7GY0CuRVbNpSxNVFqwTAmls2ftKSkDI9vtA=="
	crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/4.1.1/cipher-core.min.js"
	integrity="sha512-8tSa8JGzVhm1quXtz7BpvEm3wFvwtHkXmYkaEmaU1t7WghNxPdZLjGchi2pARJF2zhwQygyozEegjFROONKsBw=="
	crossorigin="anonymous" referrerpolicy="no-referrer"></script>



<style>
label {
	color: #1F1B64;
	font-size: 13px;
}

.main-wrapper {
	margin: 0;
	padding: 0;
	font-family: sans-serif;
	background: linear-gradient(to right, #b92b27, #1565c0)
}

.auth-wrapper .auth-box {
	margin-top: 6%;
	padding: 18px;
	background-color: #fff;
	margin: 0px;
	width: 99%;
}

.form-group {
	text-align: right;
}

.text-red {
	color: red;
	font: icon;
}

.text-green {
	color: green;
	font: icon;
}

#protitle {
	color: #00b2ad;
	/*   text-shadow:0px 0px 34px mediumspringgreen; */
}

.border-secondary {
	background-color: rgba(0, 0, 0, 0.5) !important;
	max-width: 500px !important;
}

.bg-dark {
	/*  background-color: #ffff!important; */
	background-position-x: center;
	background-repeat: no-repeat;
	background-size: 120%;
	background-image: url(assets/images/watch-service-bg.jpg;);
}

.wrapper {
	/* background-image: url(assets/images/background.jpg);  */
	height: 100%;
	background-position: center;
	background-repeat: repeat;
	background-size: contain;
	background-color: #01AFAE;
	opacity: 80%;
}

.login-card {
	border: 0;
	border-radius: 4px;
	box-shadow: 0 10px 30px 0 rgba(172, 168, 168, 0.43);
	overflow: hidden;
	background: #fff;
}

.login-card-img {
	border-radius: 0;
	position: absolute;
	width: 100%;
	height: 100%;
	-o-object-fit: cover;
	object-fit: cover;
}

.login-card .card-body {
	padding: 14px 20px 20px 20px;
}

input[type="radio"] {
	-ms-transform: scale(1.7);
	-webkit-transform: scale(1.7);
	transform: scale(1.7);
}

.sm {
	display: flex
}

.abm {
	display: none
}

.admin {
	display: none
}

input[type="radio"] {
	-ms-transform: scale(1.7);
	-webkit-transform: scale(1.7);
	transform: scale(1.7);
	margin-left: 5px;
	margin-right: 8px;
}

.btn-color {
	background-color: #01AFAE;
	border-color: #01AFAE;
}

.btn-color:hover {
	background-color: #01AFAE;
	border-color: #01AFAE;
}
</style>
</head>

<body>
	<div class="wrapper">
		<!-- ============================================================== -->
		<!-- Preloader - style you can find in spinners.css -->
		<!-- ============================================================== -->
		<div class="preloader">
			<div class="lds-ripple">
				<div class="lds-pos"></div>
				<div class="lds-pos"></div>
			</div>
		</div>
		<!-- ============================================================== -->
		<!-- Preloader - style you can find in spinners.css -->
		<!-- ============================================================== -->
		<!-- ============================================================== -->
		<!-- Login box.scss -->
		<!-- ============================================================== -->
		<div
			class="auth-wrapper d-flex no-block justify-content-end align-items-centre">
			<div class="col p-0 auth-full-height">
				<div
					class="d-flex justify-content-between flex-column h-100 px-5 py-3">
					<div></div>
					<div class="w-100 ">
						<h1 class="display-4 text-white mb-4"
							style="text-align: center; color: #fff !important;">ISCM Stationery
							</h1>
						<p class="text-white lead"
							style="text-align: center; color: #fff !important;">(Watches
							and Wearables)</p>
					</div>
					<div class="d-flex justify-content-between">
						<span class="text-white" style="color: #fff !important;">©
							2023-2024 Titan Company Limited</span>

					</div>
				</div>
			</div>
			<div class="auth-box">
				<h3 id="protitle">
					<center>Stationery</center>
				</h3>
				<div id="loginform">
					<div class="text-center p-t-20 p-b-20">
						<span class="db"><img width="140px" height="140px"
							src="assets/images/Titan_Company_Logo.png" alt="logo" /></span>
					</div>
					<!-- tdvino -->
					<c:if test="${not empty MESSAGE}">
						<div class="text-center">
							<span class="text-red">${MESSAGE}</span>
						</div>
					</c:if>

					<c:if test="${not empty SUCCESS}">
						<div class="text-center">
							<span class="text-red">${SUCCESS}</span>
						</div>
					</c:if>

					<%--                     <c:if test="${not empty PASSWORDMISMATCH}">
	                    <div class="text-center">
	                        <span class="text-red">${PASSWORDMISMATCH}</span>
	                    </div>
                    </c:if>
                     <c:if test="${not empty USERIDMISMATCH}">
	                    <div class="text-center">
	                        <span class="text-red">${USERIDMISMATCH}</span>
	                    </div>
                    </c:if>
                      <c:if test="${not empty ERROR}">
	                    <div class="text-center">
	                        <span class="text-red">${ERROR}</span>
	                    </div>
                    </c:if>
                     <c:if test="${not empty SUCCESS}">
	                    <div class="text-center">
	                        <span class="text-red">${SUCCESS}</span>
	                    </div>
                    </c:if> --%>

					<!-- Form -->
					<form action="confirmOtp" id="otp" method="POST">
						<div class="row">
							<div class="col-sm-4">

								<label>User ID</label>
								<div class="form-group ">
									<input type="text" id="userId" name="userId"
										class="form-control" placeholder="User ID" required="true">
								</div>
								<label>OTP</label>
								<div class="form-group pass_show">
									<input type="text" id="otp" name="otp" class="form-control"
										placeholder="Enter Otp" required="true">
								</div>

								<div class="form-group">
									<input type="submit" id="submit" class="form-control"
										value="Validate OTP">
								</div>
							</div>
						</div>
					</form>
				</div>

			</div>
		</div>
	</div>
	<!-- ============================================================== -->
	<!-- All Required js -->
	<!-- ============================================================== -->
	<script src="assets/libs/jquery/dist/jquery.min.js"></script>
	<!-- Bootstrap tether Core JavaScript -->
	<script src="assets/libs/popper.js/dist/umd/popper.min.js"></script>
	<script src="assets/libs/bootstrap/dist/js/bootstrap.min.js"></script>
	<!-- ============================================================== -->
	<!-- This page plugin js -->
	<!-- ============================================================== -->
	<script>
		$('[data-toggle="tooltip"]').tooltip();
		$(".preloader").fadeOut();
		// ============================================================== 
		// Login and Recover Password 
		// ============================================================== 
		$('#to-recover').on("click", function() {
			$("#loginform").slideUp();
			$("#recoverform").fadeIn();
		});
		$('#to-login').click(function() {

			$("#recoverform").hide();
			$("#loginform").fadeIn();
		});
	</script>
	<script type="text/javascript">
		history.pushState(null, null, location.href);
		window.onpopstate = function() {
			history.go(1);
		};
	</script>

</body>

</html>