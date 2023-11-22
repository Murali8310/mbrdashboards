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
<script src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/4.1.1/crypto-js.min.js" integrity="sha512-E8QSvWZ0eCLGk4km3hxSsNmGWbLtSCSUcewDQPQWZF6pEU8GlT8a5fF32wOl1i8ftdMhssTrF/OhyGWwonTcXA==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/4.1.1/aes.min.js" integrity="sha512-4b1zfeOuJCy8B/suCMGNcEkMcQkQ+/jQ6HlJIaYVGvg2ZydPvdp7GY0CuRVbNpSxNVFqwTAmls2ftKSkDI9vtA==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/4.1.1/cipher-core.min.js" integrity="sha512-8tSa8JGzVhm1quXtz7BpvEm3wFvwtHkXmYkaEmaU1t7WghNxPdZLjGchi2pARJF2zhwQygyozEegjFROONKsBw==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>



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
	opacity:80%;
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


.sm{
display : flex
}

.abm{
display : none
}

.admin{
display : none
}

input[type="radio"] {
    -ms-transform: scale(1.7);
    -webkit-transform: scale(1.7);
    transform: scale(1.7);
    margin-left: 5px;
    margin-right: 8px;
}
.btn-color{

background-color:#01AFAE;
border-color:#01AFAE;
}

.btn-color:hover {
	background-color: #01AFAE;
	border-color: #01AFAE;
}

.body {
	color: #fff
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
							style="text-align: center; color: #fff !important;">Stationary
							Portal</h1>
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
				<div id="loginform">
					<div class="text-center p-t-20 p-b-20">
						<span class="db"> <img width="230px" height="120px"
							src="assets/images/Titan_Company_Logo.png" alt="logo" />
						</span>
					</div>
					<!-- tdvino -->
					<%-- <c:if test="${not empty MESSAGE}">
						<div class="text-center">
							<span class="text-red">${MESSAGE}</span>
						</div>
					</c:if>

					<c:if test="${not empty EMAILID_MISMATCH}">
						<div class="text-center">
							<span class="text-red">${EMAILID_MISMATCH}</span>
						</div>
					</c:if>
					<c:if test="${not empty LOGINID_NOT_FOUND}">
						<div class="text-center">
							<span class="text-red">${LOGINID_NOT_FOUND}</span>
						</div>
					</c:if> --%>

					<%-- <c:if test="${not empty LOGINID_NOT_FOUND_MESSAGE}">
						<div class="text-center">
						<span class="text-red">${LOGINID_NOT_FOUND_MESSAGE}
						</span></div>
					</c:if>
					<c:if test="${not empty LOGINID_EMAILID_MISMATCH}">
						<div class="text-center">
						<span class="text-red">${LOGINID_EMAILID_MISMATCH} </span></div>
					</c:if> --%>
					<c:if test="${not empty LOGINID_EMAILID_MISMATCH}">
						<div class="text-center">
						<span class="text-red">${LOGINID_EMAILID_MISMATCH}</span></div>
					</c:if>

					<!-- Display login ID not found error -->
					<c:if test="${not empty LOGINID_NOT_FOUND_MESSAGE}">
						<div class="text-center">
						<span class="text-red">${LOGINID_NOT_FOUND_MESSAGE}</span></div>
					</c:if>

					<!-- Display combined login ID and email not found error -->
					<c:if test="${not empty LOGINID_EMAILID_NOT_FOUND}">
					<div class="text-center">
						<span class="text-red">${LOGINID_EMAILID_NOT_FOUND}</span></div>
					</c:if>
					<!-- Form -->
					<form class="col-12" action="sendOTP" method="post">

						<div class="input-group mb-3">
							<div class="input-group-prepend">
								<span class="input-group-text bg-success text-white"
									id="basic-addon1"><i class="ti-user"></i></span>
							</div>
							<!-- <input type="text" name="userName" class="form-control form-control-lg" placeholder="Username" aria-label="Username" aria-describedby="basic-addon1" required=""> -->
							<input type="text" name="login_id"
								class="form-control form-control-lg" placeholder="CostCentre Id"
								aria-label="Username" aria-describedby="basic-addon1"
								required="">
						</div>
						<div class="input-group mb-3">
							<div class="input-group-prepend">
								<span class="input-group-text bg-danger text-white"
									id="basic-addon1"><i class="ti-email"></i></span>
							</div>
							<input type="text" name="email"
								class="form-control form-control-lg" placeholder="Email Address"
								aria-label="Username" aria-describedby="basic-addon1"
								required="required">
						</div>
						<!-- <div class="row m-t-20 p-t-20 border-secondary">
							<div class="col-12">
								<a class="btn btn-danger" href="login" id="to-login">Back To
									Login</a>
								<button class="btn btn-success float-right" type="submit">Recover</button>
							</div>
						</div> -->




						<div class="row">
							<div class="col-12">
								<div class="p-t-12">
									<a href="login" class="btn btn-warning btn-color"
										style="background-color: #01AFAE; color: #fff; border-color: #01AFAE"
										role="button"> <i class="fa fa-lock m-r-5"></i> Back To
										Login
									</a>
									<div class="form-group"
										style="display: inline-block; float: right;">
										<button onclick="login()" class="btn btn-warning btn-color"
											style="background-color: #01AFAE; color: #fff; border-color: #01AFAE"
											type="submit">
											<i class="fa fa-arrow-right m-r-5"></i>Recover
										</button>
									</div>
								</div>
								<!-- Note: Add the note below the Back to Login button and end before the last digit "r" in Recover button -->
								<div class="input-group mb-3">
									<strong style="color:;">Note:</strong> Forget password feature
									is only for Indent Manager. For Buyer and Distribution Team,
									use the link - <a href="https://pxp.titan.in/">https://pxp.titan.in/</a><br>
								</div>
							</div>
						</div>






					</form>

				</div>

			</div>
		</div>
		<!-- ============================================================== -->
		<!-- All Required js -->
		<!-- ============================================================== -->
		<script src="assets/libs/jquery/dist/jquery.min.js"></script>
		<!-- Bootstrap tether Core JavaScript -->
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
