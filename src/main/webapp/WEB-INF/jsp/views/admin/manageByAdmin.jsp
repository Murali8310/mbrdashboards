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
<title>ISCM Stationery</title>
<!-- Custom CSS -->
<!-- <link href="assets/libs/flot/css/float-chart.css" rel="stylesheet"> -->
<!-- Custom CSS -->
<link href=" dist/css/style.min.css" rel="stylesheet">

<style>
	/*
.page-wrapper {
	 background-position-x: center;
  background-repeat: no-repeat;
  background-size: 100%;
background-image: url(assets/images/Backgroundimg.jpg;); 
	
}*/
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
/* #anchorbutton {
	background-color: #2e92c3;
} */

h6 {
	font-size: 24px
}

h1 {
	font-size: 65px;
}
</style>
	<style>
.card-default {
	color: #333;
	background: linear-gradient(#fff, #ebebeb) repeat scroll 0 0 transparent;
	font-weight: 600;
	border-radius: 6px;
}

.form-control {
	border-color: #ced4da;
	font-weight: 600;
	font-size: 16px;
	line-height: 22px;
	padding: 0.5rem 0.9rem;
	height: 42px !important;
}

.rows {
	padding: 20px 20px 10px 12px;
}

@media only screen and (max-width: 769px) and (min-width: 570px) {
	.page-title {
		font-size: 24px !important;
	}
	.h4 {
		font-size: 24px;
	}
	.breadcrumb {
		display: none;
	}
}

@media only screen and (max-width: 569px) and (min-width: 320px) {
	.page-title {
		/*margin-top: 20% !important;*/
		font-size: 24px !important;
	}
	.h4 {
		font-size: 24px;
	}
	.breadcrumb {
		display: none;
	}
}
</style>
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
					
					<h4 class="page-title display-10" style="color: red !important;">
		<c:if test="${message !='fine'}">
		<c:out value="${message}"></c:out>
	        	</c:if>
		
 						</h4>
 						<div class="ml-auto text-right">
							<nav aria-label="breadcrumb">
								<ol class="breadcrumb">
									<li class="breadcrumb-item"><a href="landPage">Home</a></li>
									<li class="breadcrumb-item active" aria-current="page">Employee
										Management</li>
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
			<div class="container-fluid" >
				<!-- ============================================================== -->
				<!-- Sales Cards  -->
				<!-- ============================================================== -->

		 <%
		 Object obj = session.getAttribute("role");
			String role = (String)obj ;
		
			 %> 
			 	<c:if test="${role =='Buyer'}">
					<div class="row rows">
					<div class="col-md-6 col-lg-4 col-xlg-3">
						<div class="card card-hover">
							<a href="viewProductCatelogue" id="anchorbutton">
								<div class="box tile-color text-center">
									<h1 class="font-light text-white display-2">
										<i class=" fa fa-shopping-cart fa-3x"></i>
									</h1>
								</div>
								<div class="box tile-color text-center">
									<h6 class="text-white display-5">View Catalog</h6>
								</div>
							</a>
						</div>
					</div>
					
					<div class="col-md-6 col-lg-4 col-xlg-3">
						<div class="card card-hover">
							<a href="BuyerIndentPage" id="anchorbutton">
								<div class="box tile-color text-center">
									<h1 class="font-light text-white display-2">
										 <i class=" fa fa-indent fa-3x"  aria-hidden="true"></i>
									</h1>
								</div>
								<div class="box tile-color text-center">
									<h6 class="text-white display-5">Indent Validation</h6>
								</div>
							</a>
						</div>
					</div> 
					
					<div class="col-md-6 col-lg-4 col-xlg-3">
						<div class="card card-hover">
							<a href="changepasswordbyadmin" id="anchorbutton">
								<div class="box tile-color text-center">
									<h1 class="font-light text-white display-2">
										 <i class=" fa fa-key fa-3x"  aria-hidden="true"></i>
										<!-- <img  src="assets/images/indentval.jfif"> -->
									</h1>
								</div>
								<div class="box tile-color text-center">
									<h6 class="text-white display-5">User Password Manage</h6>
								</div>
							</a>
						</div>
					</div> 
					</div>
					
					<div class="row rows">
					<!-- <div class="col-md-6 col-lg-4 col-xlg-3">
						<div class="card card-hover">
							<a href="budgetmaster" id="anchorbutton">
								<div class="box tile-color text-center">
									<h1 class="font-light text-white display-2">
										<i class="fa fa-university fa-3x" aria-hidden="true"></i>
									</h1>
								</div>
								<div class="box tile-color text-center">
									<h6 class="text-white display-5">Manage Budget</h6>
								</div>
							</a>
						</div>
					</div> -->
					<div class="col-md-6 col-lg-4 col-xlg-3">
						<div class="card card-hover">
							<a href="productmaster" id="anchorbutton">
								<div class="box tile-color text-center">
									<h1 class="font-light text-white display-2">
										<i class="fa fa-users fa-3x" aria-hidden="true"></i>
									</h1>
								</div>
								<div class="box tile-color text-center">
									<h6 class="text-white display-5">Product Master</h6>
								</div>
							</a>
						</div>
					</div>
					
					<div class="col-md-6 col-lg-4 col-xlg-3">
						<div class="card card-hover">
							<a href="BudgetMasterList" id="anchorbutton">
								<div class="box tile-color text-center">
									<h1 class="font-light text-white display-2">
										<i class="fa fa-university fa-3x" aria-hidden="true"></i>
									</h1>
								</div>
								<div class="box tile-color text-center">
									<h6 class="text-white display-5">Budget Master</h6>
								</div>
							</a>
						</div>
					</div>
					<div class="col-md-6 col-lg-4 col-xlg-3">
						<div class="card card-hover">
							<a href="poEntry" id="budjetElement">
								<div class="box tile-color text-center">
									<h1 class="font-light text-white display-2">
										<i class="fa fa-university fa-3x" aria-hidden="true"></i>
									</h1>
								</div>
								<div class="box tile-color text-center">
									<h6 class="text-white display-5">P O Entry</h6>
								</div>
							</a>
						</div>
					</div>
					<div class="col-md-6 col-lg-4 col-xlg-3">
						<div class="card card-hover">
							<a href="poEntryTable" id="budjetElement">
								<div class="box tile-color text-center">
									<h1 class="font-light text-white display-2">
										<i class="fa fa-university fa-3x" aria-hidden="true"></i>
									</h1>
								</div>
								<div class="box tile-color text-center">
									<h6 class="text-white display-5">P O Entry List</h6>
								</div>
							</a>
						</div>
					</div>
					<div class="col-md-6 col-lg-4 col-xlg-3">
						<div class="card card-hover">
							<a href="buyerFilterPage?Year=0000&Month=0000" id="budjetElement">
								<div class="box tile-color text-center">
									<h1 class="font-light text-white display-2">
										<i class="fa fa-university fa-3x" aria-hidden="true"></i>
									</h1>
								</div>
								<div class="box tile-color text-center">
									<h6 class="text-white display-5">Indent Filter List</h6>
								</div>
							</a>
						</div>
					</div>
					<div class="col-md-6 col-lg-4 col-xlg-3">
						<div class="card card-hover">
							<a href="cccreation" id="cccreation">
								<div class="box tile-color text-center">
									<h1 class="font-light text-white display-2">
										<i class="fa fa-university fa-3x" aria-hidden="true"></i>
									</h1>
								</div>
								<div class="box tile-color text-center">
									<h6 class="text-white display-5">Cost Center Creation</h6>
								</div>
							</a>
						</div>
					</div>
					</div>
				</c:if>
	
			
				<c:if test="${role =='Tray Manager'}">
				<div class="row rows">
				
				<div class="col-md-6 col-lg-4 col-xlg-3">
						<div class="card card-hover">
							<a href="viewProductCatelogue" id="anchorbutton">
								<div class="box tile-color text-center">
									<h1 class="font-light text-white display-2">
										<i class=" fa fa-shopping-cart fa-3x"></i>
									</h1>
								</div>
								<div class="box tile-color text-center">
									<h6 class="text-white display-5">View Catalog</h6>
								</div>
							</a>
						</div>
					</div>
					
					<div class="col-md-6 col-lg-4 col-xlg-3">
						<div class="card card-hover">
							<a href="DistriReceiptPage" id="anchorbutton">
								<div class="box tile-color text-center">
									<h1 class="font-light text-white display-2">
										<i class=" fa fa-users fa-3x"  aria-hidden="true"></i>
									</h1>
								</div>
								<div class="box tile-color text-center">
									<h6 class="text-white display-5">Distributer Receipt Page</h6>
								</div>
							</a>
						</div>
					</div> 
						<div class="col-md-6 col-lg-4 col-xlg-3">
						<div class="card card-hover">
							<a href="distributerFilterPage" id="anchorbutton">
								<div class="box tile-color text-center">
									<h1 class="font-light text-white display-2">
										<i class=" fa fa-users fa-3x"  aria-hidden="true"></i>
									</h1>
								</div>
								<div class="box tile-color text-center">
									<h6 class="text-white display-5">Distributer Receipt Filter Page</h6>
								</div>
							</a>
						</div>
					</div> 
				</div>
				
				</c:if>
				
					<c:if test="${role =='Indent Manager'}">
				<div class="row rows">
					

					<div class="col-md-6 col-lg-4 col-xlg-3">
						<div class="card card-hover">
							<a href="productCatelogue" id="anchorbutton">
								<div class="box tile-color text-center">
									<h1 class="font-light text-white display-2">
										<i class=" fa fa-shopping-cart fa-3x"></i>
									</h1>
								</div>
								<div class="box tile-color text-center">
									<h6 class="text-white display-5">Indent Creation</h6>
								</div>
							</a>
						</div>
					</div>

					<div class="col-md-6 col-lg-4 col-xlg-3">
						<div class="card card-hover">
							<a href="IndentList" id="anchorbutton">
								<div class="box tile-color text-center">
									<h1 class="font-light text-white display-2">
										<i class=" fa fa-list fa-3x"  aria-hidden="true"></i>
									</h1>
								</div>
								<div class="box tile-color text-center">
									<h6 class="text-white display-5">Indent List</h6>
								</div>
							</a>
						</div>
					</div> 
				</div>
				</c:if>
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