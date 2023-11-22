<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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

<link href="dist/css/loading.css" rel="stylesheet" type="text/css" />
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
.emp-profile {
	padding: 3%;
	margin-top: 3%;
	margin-bottom: 3%;
	border-radius: 0.5rem;
	background: #fff;
}

.profile-img {
	text-align: center;
}

.profile-img img {
	width: 70%;
	height: 100%;
}

.profile-img .file {
	position: relative;
	overflow: hidden;
	margin-top: -20%;
	width: 70%;
	border: none;
	border-radius: 0;
	font-size: 15px;
	background: #212529b8;
}

.profile-img .file input {
	position: absolute;
	opacity: 0;
	right: 0;
	top: 0;
}

.profile-head h5 {
	color: #333;
}

.profile-head h6 {
	color: #0062cc;
}

.profile-edit-btn {
	border: none;
	border-radius: 1.5rem;
	width: 70%;
	padding: 2%;
	font-weight: 600;
	color: #6c757d;
	cursor: pointer;
}

.proile-rating {
	font-size: 12px;
	color: #818182;
	margin-top: 5%;
}

.proile-rating span {
	color: #495057;
	font-size: 15px;
	font-weight: 600;
}

.profile-head .nav-tabs {
	margin-bottom: 5%;
}

.profile-head .nav-tabs .nav-link {
	font-weight: 600;
	border: none;
}

.profile-head .nav-tabs .nav-link.active {
	border: none;
	border-bottom: 2px solid #0062cc;
}

.profile-work {
	padding: 14%;
	margin-top: -15%;
}

.profile-work p {
	font-size: 12px;
	color: #818182;
	font-weight: 600;
	margin-top: 10%;
}

.profile-work a {
	text-decoration: none;
	color: #495057;
	font-weight: 600;
	font-size: 14px;
}

.profile-work ul {
	list-style: none;
}

.profile-tab label {
	font-size: 18px;
	font-weight: 600;
}

.profile-tab p {
	font-size: 18px;
	font-weight: 600;
	color: #0062cc;
}

#myTabContent {
	margin-top: -70px;
}

@media ( min-width : 768px) {
	.modal-xl {
		width: 90%;
		max-width: 1200px;
	}
	.col-sm-12 {
		-webkit-box-flex: 0;
		-ms-flex: 0 0 100%;
		flex: 0 0 100%;
		max-width: 58%;
	}
	.page-title {
		color: Teal;
	}
	.modal-content {
		width: 100%;
		margin-left: 20px;
	}
	#formDiv {
		display: inline-flex;
	}
}

.user-row {
	margin-bottom: 14px;
}

.user-row:last-child {
	margin-bottom: 0;
}

.dropdown-user {
	margin: 13px 0;
	padding: 5px;
	height: 100%;
}

.dropdown-user:hover {
	cursor: pointer;
}

.table-user-information>tbody>tr {
	border-top: 1px solid rgb(221, 221, 221);
}

.table-user-information>tbody>tr:first-child {
	border-top: 0;
}

.table-user-information>tbody>tr>td {
	border-top: 0;
}

.toppad {
	margin-top: 20px;
}

@media ( max-width : 768px) {
	.col-md-4 {
		padding-right: 0px !important;
	}
	.page-title {
		margin-top: 20px !important;
	}
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
			<div class="page-breadcrumb">
				<div class="row">
					<div class="col-12 d-flex no-block align-items-center">
						<h4 class="page-title  display-4">Logged in User Details</h4>
						<div class="ml-auto text-right">
							<nav aria-label="breadcrumb">
								<ol class="breadcrumb">
									<li class="breadcrumb-item"><a href="homePage">Home</a></li>
									<li class="breadcrumb-item active" aria-current="page">User
										Details</li>
								</ol>
							</nav>
						</div>
					</div>
				</div>
			</div>
			<%-- 	<div class="container-fluid">
		

			<div class="card">
				
					<div class="card-body" 
						id="productQualityTableCardId">
					<div class="row">
                <div class="col-md-4 col-lg-4 " align="center">  </div>
                
              
                <div class=" col-md-4 col-lg-4 "> 
                	<c:forEach items="${userData}" var="user">
											
										
                  <table class="table table-user-information">
                    <tbody>
                      <tr>
                        <td style="font-weight: 600;font-size: 18px;" >User Name :</td>
                        <td>${user[0]}</td>
                      </tr>
                      <tr>
                         <td style="font-weight: 600;font-size: 18px;" >User ID :</td>
                        <td>${user[1]}</td>
                      </tr>
                      <tr>
                        <td style="font-weight: 600;font-size: 18px;" >StoreName:</td>
                        <td>${user[2]}</td>
                      </tr>
                   
                       
                             <tr>
                         <td style="font-weight: 600;font-size: 18px;" >Address1 :</td>
                        <td>${user[4]}</td>
                      </tr>
                        <tr>
                         <td style="font-weight: 600;font-size: 18px;" >Address2 :</td>
                        <td>${user[5]}</td>
                      </tr>
                      <tr>
                         <td style="font-weight: 600;font-size: 18px;" >City :</td>
                        <td>${user[6]}</td>
                      </tr>
                     <tr>
                        <td style="font-weight: 600;font-size: 18px;" >Country :</td>
                        <td>${user[8]}</td>
                      </tr>
                      <tr>
                         <td style="font-weight: 600;font-size: 18px;" >Pincode :</td>
                        <td>${user[9]}</td>
                      </tr>
                    </tbody>
                  </table>
                  
               </c:forEach>
                </div>
					
					</div>

				
					
					
				</div>
			</div>
			</div>
			 --%>


			<div class="container-fluid">


				<div class="card">
					<div class="container emp-profile">

						<div class="row">
							<div class="col-md-4">
								<div class="profile-img">
									<img src="assets/images/img_avatar.png" alt="" />

								</div>
							</div>
							<c:forEach items="${userData}" var="user">
								<div class="col-md-6">
									<div class="profile-head">
										<h5>${user[1]}</h5>

										<p class="proile-rating"></p>
										<ul class="nav nav-tabs" id="myTab" role="tablist">
											<li class="nav-item"><a class="nav-link active"
												id="home-tab" data-toggle="tab" href="#home" role="tab"
												aria-controls="home" aria-selected="true">About</a></li>
											<li class="nav-item"><a class="nav-link"
												id="profile-tab" data-toggle="tab" href="#profile"
												role="tab" aria-controls="profile" aria-selected="false">Address</a>
											</li>
										</ul>
									</div>
								</div>
						</div>
						<div class="row">
							<div class="col-md-4"></div>
							<div class="col-md-8">

								<div class="tab-content profile-tab" id="myTabContent">

									<div class="tab-pane fade show active" id="home"
										role="tabpanel" aria-labelledby="home-tab">

										<div class="row">
											<div class="col-md-6">
												<label>User Id</label>
											</div>
											<div class="col-md-6">
												<p>${user[1]}</p>
											</div>
										</div>
										<div class="row">
											<div class="col-md-6">
												<label>Name</label>
											</div>
											<div class="col-md-6">
												<p>${user[1]}</p>
											</div>
										</div>
										<div class="row">
											<div class="col-md-6">
												<label>Email</label>
											</div>
											<div class="col-md-6">
												<p>${user[3]}</p>
											</div>
										</div>
										<div class="row">
											<div class="col-md-6">
												<label>Store Name</label>
											</div>
											<div class="col-md-6">
												<p>${user[2]}</p>
											</div>
										</div>

									</div>
									<div class="tab-pane fade" id="profile" role="tabpanel"
										aria-labelledby="profile-tab">
										<div class="row">
											<div class="col-md-6">
												<label>Address1</label>
											</div>
											<div class="col-md-6">
												<p>${user[4]}</p>
											</div>
										</div>
										<div class="row">
											<div class="col-md-6">
												<label>Address2<label>
											</div>
											<div class="col-md-6">
												<p>${user[5]}</p>
											</div>
										</div>
										<div class="row">
											<div class="col-md-6">
												<label>City</label>
											</div>
											<div class="col-md-6">
												<p>${user[6]}</p>
											</div>
										</div>
										<div class="row">
											<div class="col-md-6">
												<label>Country</label>
											</div>
											<div class="col-md-6">
												<p>${user[8]}</p>
											</div>
										</div>
										<div class="row">
											<div class="col-md-6">
												<label>Pincode</label>
											</div>
											<div class="col-md-6">
												<p>${user[9]}</p>
											</div>
										</div>

									</div>
								</div>

							</div>
						</div>
						</c:forEach>
					</div>

				</div>
			</div>
		</div>
		<script>
function CFAreportFunction() {

	
	var cfaname = $('#cfaname').val();
	var fromDate = $('#fromDate').val();
	var endDate = $('#endDate').val();
alert(fromDate);
alert(cfaname);
	
	
}
</script>

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

		<script
			src="assets/libs/jquery-validation/dist/jquery.validate.min.js"></script>
		<script
			src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.17.0/additional-methods.min.js"></script>

		<!-- <script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.53/pdfmake.min.js"></script>
     <script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.53/vfs_fonts.js"></script> 
     <script src="https://cdn.datatables.net/buttons/1.5.6/js/buttons.print.min.js"></script> -->
</body>
</html>
