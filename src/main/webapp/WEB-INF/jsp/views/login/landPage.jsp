<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" session="true"%>
<!DOCTYPE html>
<html dir="ltr" lang="en">
<head>

<meta charset="UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<!-- Tell the browser to be responsive to screen width -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<!-- Favicon icon -->
<link rel="icon" type="image/png" sizes="16x16"
	href="assets/images/titan-logo.png">
<title>ISCM Stationery</title>
<!-- Custom CSS -->
<link href="assets/libs/flot/css/float-chart.css" rel="stylesheet">
<!-- Custom CSS -->
<link href=" dist/css/style.min.css" rel="stylesheet">

<style>
.resp-iframe {
	position: fixed;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	border: 0;
}

.container-fluid {    
	width: 100%;
	padding-right: 0px !important;
	padding-left: 0px !important;
	margin-right: 0px !important;
	margin-left: 0px !important;
	
}
</style>
</head>

<body style="zoom: 85%;" data-sidebartype="mini-sidebar">
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
	<!-- Main wrapper - style you can find in pages.scss -->
	<!-- ============================================================== -->
	<div id="main-wrapper" data-sidebartype="full" class="mini-sidebar">
		
		 <jsp:include page="/WEB-INF/jsp/views/header/header.jsp"></jsp:include> 
		<jsp:include page="/WEB-INF/jsp/views/header/sideBar.jsp"></jsp:include> 
		
		<div class="page-wrapper container-fluid" style="width: 100%;" id="carouselExampleIndicators" data-ride="carousel">
							<div class="carousel-item active" >
								<img class="d-block w-100" src="assets/images/stn%20img4.jpg"
											height="100%" width="100%" alt="Manage Employee and Staff in single place" >
							</div>
							<div class="carousel-item">
								<img class="d-block w-100" src="assets/images/stn%20img3.jpg"
									style="background-size: cover; "
									height="100%" width="100%" alt="Manage Store and its Staff in single place">
							</div>
							<div class="carousel-item">
								<img class="d-block w-100" src="assets/images/stn%20img2.jpg"
									style="background-size: cover; "
									height="100%" width="100%" alt="Central repository for all channel staff.">
							</div>
							<div class="carousel-item">
								<img class="d-block w-100" src="assets/images/stn%20img1.jpg"
									style="background-size: cover; "
									height="100%" width="100%" alt="Central repository for all channel staff.">
							</div>
		</div>

	</div>

	<script type="text/javascript">
		history.pushState(null, null, location.href);
		window.onpopstate = function() {
			history.go(1);
		};
	</script>

	<!-- All Jquery -->
	<!-- ============================================================== -->
	<script src="assets/libs/jquery/dist/jquery.min.js"></script>
	<!-- Bootstrap tether Core JavaScript -->
	<script src="assets/libs/popper.js/dist/umd/popper.min.js"></script>
	<script src="assets/libs/bootstrap/dist/js/bootstrap.min.js"></script>
	<script
		src="assets/libs/perfect-scrollbar/dist/perfect-scrollbar.jquery.min.js"></script>
	<script src="assets/extra-libs/sparkline/sparkline.js"></script>
	<!--Wave Effects -->
	<script src="dist/js/waves.js"></script>
	<!--Menu sidebar -->
	<script src="dist/js/sidebarmenu.js"></script>
	<!--Custom JavaScript -->
	<script src="dist/js/custom.min.js"></script>
	<!--This page JavaScript -->
	<!-- <script src="dist/js/pages/dashboards/dashboard1.js"></script> -->
	<!-- Charts js Files -->

	<script type="text/javascript">
  $('.carousel').carousel({
	  interval: 2000
	})
  
		$(document).click(function(event) {
			if (!$(event.target).is('.navbar-collapse *')) {
				$('.navbar-collapse').collapse('hide');
			}
		});

		$(".modelClick").on('click', function(e) {
			if (e.which == 2) {
				e.preventDefault();
			}
		});

		$('.modelClick').on("contextmenu", function(e) {
			return false;
		});
		
		
	</script>
</body>

</html>