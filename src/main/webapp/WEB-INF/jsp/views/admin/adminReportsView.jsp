<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" session="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<title>stationary</title>
<!-- Custom CSS -->
<!-- <link href="assets/libs/flot/css/float-chart.css" rel="stylesheet">-->
<!-- Custom CSS -->
<link href=" dist/css/style.min.css" rel="stylesheet">

<style>
.page-wrapper {
	/* background-position-x: center;
  background-repeat: no-repeat;
  background-size: 100%; */
	/* background-image: url(assets/images/Backgroundimg.jpg;); */
	
}

.breadcrumb-item.active {
	color: #e6ecf1;
}

.a {
	text-decoration: none !important;
}
</style>
</head>

<body>
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
		<!-- ============================================================== -->
		<!-- Topbar header - style you can find in pages.scss -->
		<!-- ============================================================== -->

		<jsp:include page="/WEB-INF/jsp/views/header/header.jsp"></jsp:include>
		<jsp:include page="/WEB-INF/jsp/views/header/sideBar.jsp"></jsp:include>

		<div class="page-wrapper">
			<!-- ============================================================== -->
			<!-- Bread crumb and right sidebar toggle -->
			<!-- ============================================================== -->
			<div class="page-breadcrumb">
				<div class="row">
					<div class="col-12 d-flex no-block align-items-center">
						<!-- <h4 class="page-title display-5">Reports</h4> -->
						<div class="ml-auto text-right">
							<nav aria-label="breadcrumb">
								<ol class="breadcrumb">
									<li class="breadcrumb-item"><a href="landPage">Home</a></li>
									<li class="breadcrumb-item active" aria-current="page">Reports</li>
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
			<div class="container-fluid">
				<!-- ============================================================== -->
				<!-- Sales Cards  -->
				<!-- ============================================================== -->

				<div class="row">
					<%
					String role = (String) session.getAttribute("role");
					if (role == null) {
					%>
					<c:redirect url="login"></c:redirect>

					<%
					}
					%>

					<c:if test="${role =='Indent Manager'}">
						<div class="col-md-6 col-lg-4 col-xlg-3">
							<div class="card card-hover">
								<a href="indentmanagerreport" id="anchorbutton"> <!-- Controller -->
									<div class="box tile-color text-center"
										style="border-radius: 30px !important; margin: 5px;">
										<h1 class="font-light text-white display-2">
											 <i class=" fa fa-indent fa-3x"  aria-hidden="true"></i>
										</h1>
									</div>
									<div class="box tile-color text-center"
										style="border-radius: 30px !important; margin: 5px;">
										<h6 class="text-white display-5">Indent Report</h6>
									</div>
								</a>
							</div>
						</div>
					</c:if>
					<c:if test="${role =='Buyer'}">
						<div class="col-md-6 col-lg-4 col-xlg-3">
							<div class="card card-hover">
								<a href="indentreport" id="anchorbutton"> <!-- Controller -->
									<div class="box tile-color text-center"
										style="border-radius: 30px !important; margin: 5px;">
										<h1 class="font-light text-white display-2">
											 <i class=" fa fa-indent fa-3x"  aria-hidden="true"></i>
										</h1>
									</div>
									<div class="box tile-color text-center"
										style="border-radius: 30px !important; margin: 5px;">
										<h6 class="text-white display-5">Indent Report</h6>
									</div>
								</a>
							</div>
						</div>
					</c:if>
					
					<c:if test="${role =='Tray Manager'}">
						<div class="col-md-6 col-lg-4 col-xlg-3">
							<div class="card card-hover">
								<a href="indentreport" id="anchorbutton"> <!-- Controller -->
									<div class="box tile-color text-center"
										style="border-radius: 30px !important; margin: 5px;">
										<!-- <h1 class="font-light text-white"><i class="fa  fa-list"></i></h1> -->
										<h1 class="font-light text-white display-2">
											<!-- <i class="fa fa-users fa-3x"></i> -->

											<img height="170" width="200"
												src="assets/images/storestaff.png">
										</h1>
									</div>
									<div class="box tile-color text-center"
										style="border-radius: 30px !important; margin: 5px;">
										<h6 class="text-white display-5">Indent Report</h6>
									</div>
								</a>
							</div>
						</div>
					</c:if>


				</div>
			</div>
		</div>
		</div>
		<jsp:include page="/WEB-INF/jsp/views/header/footer.jsp"></jsp:include>
		<!-- ============================================================== -->
		<!-- End Wrapper -->
		<!-- ============================================================== -->
		<!-- ============================================================== -->

		<script type="text/javascript">
			history.pushState(null, null, location.href);
			window.onpopstate = function() {
				history.go(1);
			};
		</script>


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
		<script src="assets/libs/flot/excanvas.js"></script>
		<script src="assets/libs/flot/jquery.flot.js"></script>
		<script src="assets/libs/flot/jquery.flot.pie.js"></script>
		<script src="assets/libs/flot/jquery.flot.time.js"></script>
		<script src="assets/libs/flot/jquery.flot.stack.js"></script>
		<script src="assets/libs/flot/jquery.flot.crosshair.js"></script>
		<script src="assets/libs/flot.tooltip/js/jquery.flot.tooltip.min.js"></script>
		<script src="dist/js/pages/chart/chart-page-init.js"></script>
</body>
</html>