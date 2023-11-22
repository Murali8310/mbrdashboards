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

/* #anchorbutton {
	background-color: #2e92c3;
} */

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
						<h4 class="page-title display-5" style="color: Teal;">Update</h4>
						<div class="ml-auto text-right">
							<nav aria-label="breadcrumb">
								<ol class="breadcrumb">
									<li class="breadcrumb-item"><a href="landPage">Home</a></li>
									<li class="breadcrumb-item active" aria-current="page">Update</li>
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

<% String role = (String) session.getAttribute("role");
				 %>
			
			<c:if test="${role =='ABM'}">
			
					<div class="col-md-6 col-lg-4 col-xlg-3">
						<div class="card card-hover">
							<a href="storeupdate" id="anchorbutton"> <!-- Controller -->
								<div class="box tile-color text-center">
									<!-- <h1 class="font-light text-white"><i class="fa  fa-list"></i></h1> -->
									<h1 class="font-light text-white display-2">
										<!-- <i class="fa fa-shopping-cart fa-3x"></i> -->
										<img height="170" width="200" src="assets/images/storeu.png">
									</h1>
									</div>
								<div class="box tile-color text-center">
								
									<h6 class="text-white display-5">Store Update</h6>
								</div>
							</a>
						</div>
					</div>
					<div class="col-md-6 col-lg-4 col-xlg-3">
						<div class="card card-hover">
							<a href="storereportdisable" id="anchorbutton"> 
								<div class="box tile-color text-center">
									<h1 class="font-light text-white display-2">
										<!-- <i class="fa fa-shopping-cart fa-3x"></i> -->
										<img height="170" width="200" src="assets/images/stored.png">
									</h1>
									</div>
									<div class="box tile-color text-center">
									<h6 class="text-white display-5">Store Disable</h6>
								</div>
							</a>
						</div>
					</div> 
					<div class="col-md-6 col-lg-4 col-xlg-3">
						<div class="card card-hover">
							<a href="storemanagerupdate" id="anchorbutton"> <!-- Controller -->
								<div class="box tile-color text-center">
									<!-- <h1 class="font-light text-white"><i class="fa  fa-list"></i></h1> -->
									<h1 class="font-light text-white display-2">
										<!-- <i class="fa fa-universal-access fa-3x"></i> -->
										<img height="170" width="200" src="assets/images/smu.png">
									</h1>
								</div>
								<div class="box tile-color text-center">
									<h6 class="text-white display-5">Store Manager Update</h6>
								</div>
							</a>
						</div>
					</div>
					
			 		<div class="col-md-6 col-lg-4 col-xlg-3">
						<div class="card card-hover">
							<a href="storemanagerreportdisable" id="anchorbutton"> 
								<div class="box tile-color text-center">
									<h1 class="font-light text-white display-2">
										<!-- <i class="fa fa-users fa-3x"></i> -->
										<img height="170" width="200" src="assets/images/smd.png">
									</h1>
								</div>
								<div class="box tile-color text-center">
									<h6 class="text-white display-5">Store Manager Disable</h6>
								</div>
							</a>
						</div>
					</div> 
			
			</c:if>
			
<c:if test="${role =='Super Admin'}">
					<div class="col-md-6 col-lg-4 col-xlg-3">
						<div class="card card-hover">
							<a href="abmcreationUpdate" id="anchorbutton">
								<div class="box tile-color text-center">
									<!-- <h1 class="font-light text-white"><i class="fa  fa-list"></i></h1> -->
									<h1 class="font-light text-white  display-2">
										<!-- <i class="fa fa-user-secret fa-3x"></i> -->
										<img height="170" width="200" src="assets/images/abmu.png">
									</h1>
								</div>
								<div class="box tile-color text-center">
									<h6 class="text-white display-5">ABM Update</h6>
								</div>
							</a>
						</div>
					</div>
				 	<div class="col-md-6 col-lg-4 col-xlg-3">
						<div class="card card-hover">
							<a href="abmreportdisable" id="anchorbutton">
								<div class="box tile-color text-center">
									<h1 class="font-light text-white  display-2">
										<!-- <i class="fa fa-user-secret fa-3x"></i> -->
										<img height="170" width="200" src="assets/images/abmd.png">
									</h1>
								</div>
								<div class="box tile-color text-center">
									<h6 class="text-white display-5">ABM Disable</h6>
								</div>
							</a>
						</div>
					</div> 
<!-- 
					<div class="col-md-6 col-lg-4 col-xlg-3">
						<div class="card card-hover">
							<a href="storeupdate" id="anchorbutton"> Controller
								<div class="box bg-warning text-center">
									<h1 class="font-light text-white"><i class="fa  fa-list"></i></h1>
									<h1 class="font-light text-white display-2">
										<i class="fa fa-shopping-cart fa-3x"></i>
									</h1>
									<h6 class="text-white display-5">Store Update</h6>
								</div>
							</a>
						</div>
					</div>
					<div class="col-md-6 col-lg-4 col-xlg-3">
						<div class="card card-hover">
							<a href="storemanagerupdate" id="anchorbutton"> Controller
								<div class="box bg-primary text-center">
									<h1 class="font-light text-white"><i class="fa  fa-list"></i></h1>
									<h1 class="font-light text-white display-2">
										<i class="fa fa-universal-access fa-3x"></i>
									</h1>
									<h6 class="text-white display-5">Store Manager Update</h6>
								</div>
							</a>
						</div>
					</div> -->
					<div class="col-md-6 col-lg-4 col-xlg-3">
						<div class="card card-hover">
							<a href="designtionsupdate" id="anchorbutton"> <!-- Controller -->
								<div class="box tile-color text-center">
									<!-- <h1 class="font-light text-white"><i class="fa  fa-list"></i></h1> -->
									<h1 class="font-light text-white display-2">
									<!-- 	<i class="fa fa-briefcase fa-3x"></i>
										 -->
										<img height="170" width="200" src="assets/images/designation.png">
									</h1>
								</div>
								<div class="box tile-color text-center">
									<h6 class="text-white display-5">Designation Update</h6>
								</div>
							</a>
						</div>
					</div>
					<!-- 	<div class="col-md-6 col-lg-4 col-xlg-3">
						<div class="card card-hover">
							<a href="designationreportdisable" id="anchorbutton"> Controller
								<div class="box bg-dark text-center">
									<h1 class="font-light text-white"><i class="fa  fa-list"></i></h1>
									<h1 class="font-light text-white display-2">
										<i class="fa fa-briefcase fa-3x"></i>
									</h1>
									<h6 class="text-white display-5">Designation Disable</h6>
								</div>
							</a>
						</div>
					</div> -->
					
					<!-- <div class="col-md-6 col-lg-4 col-xlg-3">
						<div class="card card-hover">
							<a href="storestaffupdate" id="anchorbutton"> Controller
								<div class="box bg-danger text-center">
									<h1 class="font-light text-white"><i class="fa  fa-list"></i></h1>
									<h1 class="font-light text-white display-2">
										<i class="fa fa-users fa-3x"></i>
									</h1>
									<h6 class="text-white display-5">Store Staff Update</h6>
								</div>
							</a>
						</div>
					</div> -->

</c:if>
		<c:if test="${role =='Store Manager'}">
				
						<div class="col-md-6 col-lg-4 col-xlg-3">
						<div class="card card-hover">
							<a href="storestaffupdate" id="anchorbutton"> <!-- Controller -->
								<div class="box tile-color text-center">
									<!-- <h1 class="font-light text-white"><i class="fa  fa-list"></i></h1> -->
									<h1 class="font-light text-white display-2">
										<!-- <i class="fa fa-users fa-3x"></i> -->
										<img height="170" width="200" src="assets/images/staffu.png">
								
									</h1>
								</div>
								<div class="box tile-color text-center">
									<h6 class="text-white display-5">Store Staff Update</h6>
								</div>
							</a>
						</div>
					</div>	
					
					<div class="col-md-6 col-lg-4 col-xlg-3">
						<div class="card card-hover">
							<a href="storestaffDiasble" id="anchorbutton"> <!-- Controller -->
								<div class="box tile-color text-center">
									<!-- <h1 class="font-light text-white"><i class="fa  fa-list"></i></h1> -->
									<h1 class="font-light text-white display-2">
										<!-- <i class="fa fa-users fa-3x"></i> -->
										<img height="170" width="200" src="assets/images/staffd.png">
								
									</h1>
								</div>
								<div class="box tile-color text-center">
									<h6 class="text-white display-5">Store Staff Disable</h6>
								</div>
							</a>
						</div>
					</div>				
			</c:if>
			
				<c:if test="${role =='Store franchise Manager'}">
					<div class="col-md-6 col-lg-4 col-xlg-3">
						<div class="card card-hover">
							<a href="storestaffupdate" id="anchorbutton"> <!-- Controller -->
								<div class="box bg-danger text-center">
									<!-- <h1 class="font-light text-white"><i class="fa  fa-list"></i></h1> -->
									<h1 class="font-light text-white display-2">
										<i class="fa fa-users fa-3x"></i>
									</h1>
									<h6 class="text-white display-5">Store Staff Update</h6>
								</div>
							</a>
						</div>
					</div>
						
</c:if>



					<%-- 						<c:if test="${menuNames[0] =='Menu Creation'}">
							<div class="col-md-6 col-lg-4 col-xlg-3">
								<div class="card card-hover">
									<a href="${menuNames[1]}">
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
									<a href="${menuNames[1]}">
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

					 --%>


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