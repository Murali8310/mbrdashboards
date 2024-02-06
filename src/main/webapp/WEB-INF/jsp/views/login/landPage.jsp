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

 @media (max-width: 768px) {
.navbar-nav {
    margin: 1px !important;
}
      }
.resp-iframe {
	position: fixed;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	border: 0;
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
		
		<div class="page-wrapper">
			<!-- ============================================================== -->
			<!-- Bread crumb and right sidebar toggle -->
			<!-- ============================================================== -->

			<div class="page-breadcrumb">
				<div class="row">

					<!-- <h3 class="page-title display-4"
						style="color: Teal; margin-left: 30%">
						<b>Welcome Warehouse Stationery</b>
					</h3> -->

					<div class="col-12 d-flex no-block align-items-center">
						<div class="ml-auto text-right">
							<nav aria-label="breadcrumb">
								<ol class="breadcrumb">
									<!-- <li class="breadcrumb-item"><a href="#">Home</a></li>
                                    <li class="breadcrumb-item active" aria-current="page">Dashboard</li> -->
								</ol>
							</nav>
						</div>
					</div>
				</div>
			</div>
			<!-- ============================================================== -->
			<!-- End Bread crumb and right sidebar toggle -->
			<!-- ============================================================== -->
			<!-- ============================================================== -->
			<!-- Container fluid  -->
			<!-- ============================================================== -->

			<div class="container-fluid" style="width: 100%;">
				<!-- <div class="container-fluid"> -->
				<div class="col-md-12">
					<!-- <marquee behavior="scroll" scrollamount="20" direction="left"
						onmouseover="this.stop();" onmouseout="this.start();"
						scrolldelay="2" width="100%">
						<img src="assets/images/service1.jpg"
							style="background-size: cover; border-radius: 15px 50px;"
							height="400px" width="100%"></img> <img
							src="assets/images/service2.jpg"
							style="background-size: cover; border-radius: 15px 50px;"
							height="400px" width="100%"></img> <img
							src="assets/images/service3.png"
							style="background-size: cover; border-radius: 15px 50px;"
							height="400px" width="100%"></img>

					</marquee>
 -->
	 				<div id="carouselExampleIndicators" class="carousel slide"
						data-ride="carousel">
						<ol class="carousel-indicators">
							<li data-target="#carouselExampleIndicators" data-slide-to="0"
								class="active"></li>
							<li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
							<li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
						</ol>
						<div class="carousel-inner" style="border-radius: 15px 15px 15px 15px;">
							<div class="carousel-item active" style="height:563px">
								<img class="d-block w-100" src="assets/images/stn%20img4.jpg"
									style="border-radius: 15px 15px 15px 15px	;"
									height="600px" width="100%" alt="Manage Employee and Staff in single place" >
							</div>
							<div class="carousel-item" style="height:563px">
								<img class="d-block w-100" src="assets/images/stn%20img3.jpg"
									style="background-size: cover; border-radius: 15px 15px 15px 15px;"
									height="600px" width="100%" alt="Manage Store and its Staff in single place">
							</div>
							<div class="carousel-item" style="height:563px">
								<img class="d-block w-100" src="assets/images/stn%20img2.jpg"
									style="background-size: cover; border-radius: 15px 15px 15px 15px;"
									height="600px" width="100%" alt="Central repository for all channel staff.">>
							</div>
							<div class="carousel-item" style="height:563px">
								<img class="d-block w-100" src="assets/images/stn%20img1.jpg"
									style="background-size: cover; border-radius: 15px 15px 15px 15px;"
									height="600px" width="100%" alt="Central repository for all channel staff.">>
							</div>
						</div>
						<a class="carousel-control-prev" href="#carouselExampleIndicators"
							role="button" data-slide="prev"> <span
							class="carousel-control-prev-icon" aria-hidden="true"></span> <span
							class="sr-only">Previous</span>
						</a> <a class="carousel-control-next"
							href="#carouselExampleIndicators" role="button" data-slide="next">
							<span class="carousel-control-next-icon" aria-hidden="true"></span>
							<span class="sr-only">Next</span>
						</a>
					</div> 

				</div>


				<div class="col-md-12">
					<br /> <br />
					<div>
						<marquee behavior="scroll" scrollamount="10" direction="left"
							style="color: blue; font: bold 22px arial, sans-serif; background-color: #EEEEE; position: relative; z-index: 2;"
							onMouseOver="this.stop();" onMouseOut="this.start();">

						</marquee>
					</div>
					<div>
						<%-- 	<label style="color: red"><b>Note:</b> To stop the
							scrolling message, keep the cursor on message.${message}</label> --%>

					</div>

				</div>

			</div>
			<!-- </div> -->
			<jsp:include page="/WEB-INF/jsp/views/header/footer.jsp"></jsp:include>
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