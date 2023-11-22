<%@ page session="true"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
<title>Titan Care Tech Portal</title>
<!-- Custom CSS -->
<link href="assets/libs/flot/css/float-chart.css" rel="stylesheet">
<!-- Custom CSS -->
<link href=" dist/css/style.min.css" rel="stylesheet">
<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
<![endif]-->

<link
	href="assets/libs/bootstrap-datepicker/dist/css/bootstrap-datepicker.min.css"
	rel="stylesheet" type="text/css">
<link href="assets/libs/datatables/media/css/jquery.dataTables.min.css"
	rel="stylesheet" type="text/css" />
<link
	href="/assets/libs/datatables.net-bs4/css/dataTables.bootstrap4.css"
	rel="stylesheet" type="text/css" />

<link
	href="https://cdn.datatables.net/buttons/1.5.6/css/buttons.bootstrap4.min.css"
	rel="stylesheet" type="text/css" />


<style>
.page-title {
	color: Teal;
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

		<!-- ============================================================== -->
		<!-- End Topbar header -->
		<!-- ============================================================== -->
		<!-- ============================================================== -->
		<!-- Left Sidebar - style you can find in sidebar.scss  -->
		<!-- ============================================================== -->

		<jsp:include page="/WEB-INF/jsp/views/header/sideBar.jsp"></jsp:include>

		<!-- ============================================================== -->
		<!-- End Left Sidebar - style you can find in sidebar.scss  -->
		<!-- ============================================================== -->
		<!-- ============================================================== -->
		<!-- Page wrapper  -->
		<div class="page-wrapper">
			<!-- ============================================================== -->
			<!-- Bread crumb and right sidebar toggle -->
			<!-- ============================================================== -->
			<div class="page-breadcrumb">
				<div class="row">
					<div class="col-12 d-flex no-block align-items-center">
						<h4 class="page-title  display-4">User Logs</h4>
						<div class="ml-auto text-right">
							<nav aria-label="breadcrumb">
								<ol class="breadcrumb">
									<li class="breadcrumb-item"><a href="homePage">Home</a></li>
									<li class="breadcrumb-item"><a
										href="getUserManagementDetails">Employee Management</a></li>
									<li class="breadcrumb-item active" aria-current="page">User
										Logs</li>
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
				<!-- Start Page Content -->
				<!-- ============================================================== -->
				<div class="card">
					<div class="card-body wizard-content">
						<!--   <h4 class="card-title"></h4> -->
						<h6 class="card-subtitle"></h6>
						<c:if test="${SUCCESS != null}">
							<div id="menuSubmitSuccessDivID"
								class="alert alert-success alert-block">
								<a class="close" data-dismiss="alert" href="#">X</a>
								<h4 class="alert-heading">Success!</h4>
								<c:out value="${SUCCESS}"></c:out>
							</div>
						</c:if>

						<c:if test="${ERROR != null}">
							<div class="alert alert-error alert-block">
								<a class="close" data-dismiss="alert" href="#">X</a>
								<h4 class="alert-heading">Error!</h4>
								<c:out value="${ERROR}"></c:out>
							</div>
						</c:if>
						<c:if test="${DELETEMENU != null}">
							<div class="alert alert-success alert-block">
								<a class="close" data-dismiss="alert" href="#">×</a>
								<h4 class="alert-heading">Success!</h4>
								<c:out value="${DELETEMENU}"></c:out>
							</div>
						</c:if>

						<form class="m-t-40">
							<div class="col-md-12 oneline" style="display: inline-flex;">
								<div class="col-md-4 ">
									<label for="User">Select User</label> <select id="selectUser"
										name="user_Name" class="form-control" aria-invalid="true"
										required="required">
										<option data-select2-id="3">Select User</option>
										<c:forEach items="${userName}" var="user">
											<option value="${user[0]}">${user[1]}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<br /> <br />

						</form>
					</div>

				</div>
				<div class="card" id="divCardId" style="display: none">
					<div class="card-body wizard-content">
						<h6 class="card-subtitle"></h6>
						<div>
							<h5 id="menuDetailHeader"
								style="display: none; margin-left: 30px">User Logs Details</h5>
							<div id="getUserDetails"></div>
						</div>
					</div>
				</div>
			</div>
			<%-- <jsp:include page="/WEB-INF/jsp/views/header/footer.jsp"></jsp:include> --%>
		</div>

	</div>

	<jsp:include page="/WEB-INF/jsp/views/header/footer.jsp"></jsp:include>
	<script src="assets/libs/jquery/dist/jquery.min.js"></script>
	<script src="assets/libs/bootstrap/dist/js/bootstrap.min.js"></script>
	<script
		src="assets/libs/perfect-scrollbar/dist/perfect-scrollbar.jquery.min.js"></script>
	<script src="dist/js/custom.min.js"></script>

	<script type="text/javascript"
		src="assets/libs/datatables/media/js/jquery.dataTables.min.js"></script>
	<!-- JS-Table data download -->
	<script
		src="https://cdn.datatables.net/1.10.19/js/dataTables.bootstrap4.min.js"></script>
	<script
		src="https://cdn.datatables.net/buttons/1.5.6/js/dataTables.buttons.min.js"></script>
	<script
		src="https://cdn.datatables.net/buttons/1.5.6/js/buttons.bootstrap4.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/jszip/3.1.3/jszip.min.js"></script>
	<script
		src="https://cdn.datatables.net/buttons/1.5.6/js/buttons.html5.min.js"></script>
	<script
		src="https://cdn.datatables.net/buttons/1.5.6/js/buttons.colVis.min.js"></script>

	<script src="assets/libs/jquery-validation/dist/jquery.validate.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.17.0/additional-methods.min.js"></script>


	<script type="text/javascript">
		history.pushState(null, null, location.href);
		window.onpopstate = function() {
			history.go(1);
		};

		$(function() {
			$("#selectUser")
					.on(
							"change",
							function() {
								var userId = $("#selectUser").val();
								// 				alert("Selected User ID: " + userId);
								$
										.ajax({
											type : "GET",
											url : "getUserLogsDetailsByUserId",
											data : "user_id=" + userId,

											success : function(response) {

												var data = jQuery
														.parseJSON(response);
												var data = jQuery
														.parseJSON(response);
												
												var getAllAcessDetails = "";
												getAllAcessDetails += '<table id="example" class="table table-striped table-bordered display nowrap" style="width:100%; text-align: center;"><thead class="thead-light"><tr><th scope="col">SL.NO</th><th scope="col">User Name</th><th scope="col">Login_Id</th><th scope="col">Login_Time</th><th scope="col">Logout_Time</th></tr></thead><tbody  class="customtable">';
												for (var i = 0; i < data.length; i++) {
													if(data[i][3] == null)
														{
														   data[i][3]="-";
														}
													if(data[i][2] == null){
														data[i][2]="-";
													}
													getAllAcessDetails += '<tr><td>'
															+ (i + 1)+'</td><td>'+data[i][0]+'</td><td>'+data[i][1]+'</td><td>'+data[i][2]+'</td><td>'+data[i][3]+'</td></tr>'
															
												}
												getAllAcessDetails += '</tbody></table>';

												$("#getUserDetails").children()
														.remove();

												$("#getUserDetails").append(
														getAllAcessDetails);

												$("#menuDetailHeader").show();
												$("#divCardId").show();

												$(document)
														.ready(
																function() {
																	var table = $(
																			'#example')
																			.DataTable(
																					{
																						scrollX : true,
																						lengthChange : false,
																					});

																	table
																			.buttons()
																			.container()
																			.appendTo(
																					'#example_wrapper .col-md-6:eq(0)');
																});
											},
											error : function(error) {
												alert("error : " + error);
											}
										});

							});
		});
	</script>
</body>
</html>
