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
<title>Titan UID Portal</title>
<!-- Custom CSS -->
<link href="assets/libs/flot/css/float-chart.css" rel="stylesheet">
<!-- Custom CSS -->
<link href=" dist/css/style.min.css" rel="stylesheet">

<style>
.page-wrapper {
	/* background-position-x: center;
  background-repeat: no-repeat;
  background-size: 100%;
background-image: url(assets/images/Backgroundimg.jpg;); */
	
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

	<style>
#anchorbutton {
	background-color: #2e92c3;
}
</style>
	<!-- ============================================================== -->
	<!-- Main wrapper - style you can find in pages.scss -->
	<!-- ============================================================== -->
	<div id="main-wrapper">
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
						<h4 class="page-title display-5" style="color: white;">Employee
							Management Menu View</h4>
						<div class="ml-auto text-right">
							<nav aria-label="breadcrumb">
								<ol class="breadcrumb">
									<li class="breadcrumb-item"><a href="landPage">Home</a></li>
									<li class="breadcrumb-item active" aria-current="page"><a
										href="getUserManagementSubMenu">Employee Management</a></li>
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

					<c:forEach items="${listOfMenuNames}" var="menuNames">

						<c:if test="${menuNames[0] =='User Creation'}">
							<div class="col-md-6 col-lg-4 col-xlg-3">
								<div class="card card-hover">
									<a href="${menuNames[1]}" id="anchorbutton">
										<div class="box bg-success text-center">
											<!-- <h1 class="font-light text-white"><i class="fa  fa-list"></i></h1> -->
											<h1 class="font-light text-white">
												<i class="fa fa-users"></i>
											</h1>
											<h6 class="text-white">${menuNames[0]}</h6>
										</div>
									</a>
								</div>
							</div>
						</c:if>

						<c:if test="${menuNames[0] =='Menu Creation'}">
							<div class="col-md-6 col-lg-4 col-xlg-3">
								<div class="card card-hover">
									<a href="${menuNames[1]}" id="anchorbutton">
										<div class="box bg-dark text-center">
											<h1 class="font-light text-white">
												<i class="fa fa-list-alt"></i>
											</h1>
											<h6 class="text-white">${menuNames[0]}</h6>
										</div>
									</a>
								</div>
							</div>
						</c:if>

						<c:if test="${menuNames[0] =='Sub Menu Creation'}">
							<div class="col-md-6 col-lg-4 col-xlg-3">
								<div class="card card-hover">
									<a href="${menuNames[1]}" id="anchorbutton">
										<div class="box bg-warning text-center">
											<h1 class="font-light text-white">
												<i class=" fa fa-th-list"></i>
											</h1>
											<h6 class="text-white">${menuNames[0]}</h6>
										</div>
									</a>
								</div>
							</div>
						</c:if>

						<c:if test="${menuNames[0] =='Menu Access'}">
							<div class="col-md-6 col-lg-4 col-xlg-3">
								<div class="card card-hover">
									<a href="${menuNames[1]}" id="anchorbutton">
										<div class="box bg-cyan text-center">
											<h1 class="font-light text-white">
												<i class="fa fa-list-alt"></i>
											</h1>
											<h6 class="text-white">${menuNames[0]}</h6>
										</div>
									</a>
								</div>
							</div>
						</c:if>
						<c:if test="${menuNames[0] =='User Detail'}">
							<div class="col-md-6 col-lg-4 col-xlg-3">
								<div class="card card-hover">
									<a href="${menuNames[1]}" id="anchorbutton">
										<div class="box bg-cyan text-center">
											<h1 class="font-light text-white">
												<i class=" fa fa-user"></i>
											</h1>
											<h6 class="text-white">${menuNames[0]}</h6>
										</div>
									</a>
								</div>
							</div>
						</c:if>
					</c:forEach>

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