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
<title>Stationery</title>
<!-- Custom CSS -->
<link href="assets/libs/flot/css/float-chart.css" rel="stylesheet">
<!-- Custom CSS -->
<link href=" dist/css/style.min.css" rel="stylesheet">

<link href="dist/css/loading.css" rel="stylesheet" type="text/css" />

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
.btn-color:hover{

background-color:#01AFAE;
border-color:#01AFAE;
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
						<h4 class="page-title display-4">Change Password</h4>
						<div class="ml-auto text-right">
							<nav aria-label="breadcrumb">
								<ol class="breadcrumb">
									<li class="breadcrumb-item"><a href="homePage">Home</a></li>

									<li class="breadcrumb-item active" aria-current="page">Change
										Password</li>
								</ol>
							</nav>
						</div>
					</div>
				</div>
			</div>

			<!-- ============================================================== -->
			<!-- Container fluid  -->
			<!-- ============================================================== -->
			<div class="container-fluid">
				<!-- ============================================================== -->
				<!-- Start Page Content -->
				<!-- ============================================================== -->
				<div class="card">
					<div class="card-body wizard-content">
						<div
							class="auth-wrapper d-flex no-block justify-content-center align-items-center ">
<div class="col p-0 auth-full-height">
                <div class="d-flex justify-content-between flex-column h-100 px-5 py-3">
                    <div></div>
                    <div class="w-100 ">
                        <h1 class="display-4 text-white mb-4"  style="text-align:center;color:#fff !important;">ISCM Stationery</h1>
                        <p class="text-white lead" style="text-align:center;color:#fff  !important;">(Watches and Wearables)</p>
                    </div>
                    <div class="d-flex justify-content-between">
                        <span class="text-white" style="color:#fff !important;">© 2023-2024 Titan Company Limited</span>
                      
                    </div>
                </div>
            </div>
							<div class="auth-box border-secondary">
								<div id="loginform">
									<!-- tdvino -->
									<div id="cfaalert"></div>
									
									<%
									String login_id = "";
							Object loginobj =  session.getAttribute("login_id");
									if(loginobj !=null)
										login_id = loginobj.toString();
										
						
					%>
									<!-- Form -->
									<!-- <form class="form-horizontal m-t-20" id="change-password" action="changePassword"   method="post"> -->
									<form class="form-horizontal m-t-20" id="change-password"
										method="post">
										<div class="row p-b-30">
											<div class="col-12">
												<div class="input-group mb-3">
													<div class="input-group-prepend">
														<span class="input-group-text bg-success text-white"
															id="basic-addon1"><i class="ti-user"></i></span>
													</div>
													<input type="text" autocomplete="off" id="login_id" value="<%=login_id%>"
														name="login_id" class="form-control form-control-lg"
														placeholder="User ID" aria-label="UserId"
														aria-describedby="basic-addon1" readonly="readonly">
												</div>

												<div class="input-group mb-3">
													<div class="input-group-prepend">
														<span class="input-group-text bg-warning text-white"
															id="basic-addon2"><i class="ti-pencil"></i></span>
													</div>
													<input type="password" autocomplete="off" id="newPassword"
														onkeyup="checkPasswordStrength();" name="newPwd"
														class="form-control form-control-lg"
														placeholder="New Password" aria-label="New Password"
														aria-describedby="basic-addon1" required="">
												</div>
												<div id="password-strength-status"></div>
												<div class="input-group mb-3">
													<div class="input-group-prepend">
														<span class="input-group-text bg-warning text-white"
															id="basic-addon2"><i class="ti-pencil"></i></span>
													</div>
													<input type="password" autocomplete="off" id="confirmPassword" name="conPwd"
														class="form-control form-control-lg"
														placeholder="Confirm Password"
														aria-label="Confirm Password"
														aria-describedby="basic-addon1" required="">
												</div>
											</div>
										</div>
										<div class="row border-secondary">
											<div class="col-12">
												<div class="p-t-20">
													<button onclick="window.location.href='login';"
														class="btn btn-warning btn-color" type="submit">Back To
														Login</button>
													<button class="btn btn-success float-right" type="button"
														id="submitfiles">Change Password</button>
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
<!-- 						<script src="assets/libs/popper.js/dist/umd/popper.min.js"></script>
						<script src="assets/libs/bootstrap/dist/js/bootstrap.min.js"></script> -->
						<!-- ============================================================== -->
						<!-- This page plugin js -->
						<!-- ============================================================== -->
						<script>
						<!-- $('[data-toggle="tooltip"]').tooltip(); 
			$(".preloader").fadeOut(); -->
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


						<script type="text/javascript">
		
		
		$(document).ready(function () {
			$('#submitfiles').click(function(){ 
				//alert("test");
				      var form = $('#change-password')[0]; //get the form containing the files
				      var data = new FormData(form);
			        var queryString = JSON.stringify($("#example-form").serializeArray());

			//alert(queryString);
			//return;
			//confirmPassword
			var newpassword = $('#newPassword').val();
			var p = $('#newPassword').val();
				var confirmPassword = $('#confirmPassword').val();
				if (newpassword == "" ) {
					 trs = '<div class="alert alert-success alert-block"  style="background-color: #a52a2a26">'
			 		+ '<a class="close" data-dismiss="alert" href="#">X</a>'
			          + '<h4 class="alert-heading" style="color:red;font-size:20px">Error!</h4>'
			          + '<span style="color:lightRed">New password is empty please fill</span></span>'
			     + '</div>';
			 	$("#cfaalert").children()
			 	.remove();
			 $("#cfaalert").append(
			 	trs);
					return false;
				}
				
				
				 if (p.length < 8) {
				       // errors.push("Your password must be at least 8 characters");
				        trs = '<div class="alert alert-success alert-block"  style="background-color: #a52a2a26">'
					 		+ '<a class="close" data-dismiss="alert" href="#">X</a>'
					          + '<h4 class="alert-heading" style="color:red;font-size:20px">Error!</h4>'
					          + '<span style="color:lightRed">Your password must be at least 8 characters</span></span>'
					     + '</div>';
					 	$("#cfaalert").children()
					 	.remove();
					 $("#cfaalert").append(
					 	trs);
							return false;
				    }
				   
				    if (p.search(/[a-z]/) < 0) {
				       // errors.push("Your password must contain at least one lower case letter."); 
				        trs = '<div class="alert alert-success alert-block"  style="background-color: #a52a2a26">'
					 		+ '<a class="close" data-dismiss="alert" href="#">X</a>'
					          + '<h4 class="alert-heading" style="color:red;font-size:20px">Error!</h4>'
					          + '<span style="color:lightRed">Your password must contain at least one lower case letter.</span></span>'
					     + '</div>';
					 	$("#cfaalert").children()
					 	.remove();
					 $("#cfaalert").append(
					 	trs);
							return false;
				    }
				    if (p.search(/[A-Z]/) < 0) {
				        //errors.push("Your password must contain at least one upper case letter."); 
				        trs = '<div class="alert alert-success alert-block"  style="background-color: #a52a2a26">'
					 		+ '<a class="close" data-dismiss="alert" href="#">X</a>'
					          + '<h4 class="alert-heading" style="color:red;font-size:20px">Error!</h4>'
					          + '<span style="color:lightRed">Your password must contain at least one upper case letter</span></span>'
					     + '</div>';
					 	$("#cfaalert").children()
					 	.remove();
					 $("#cfaalert").append(
					 	trs);
							return false;
				    }

				    if (p.search(/[0-9]/) < 0) {
				      //  errors.push("Your password must contain at least one digit.");
				        trs = '<div class="alert alert-success alert-block"  style="background-color: #a52a2a26">'
					 		+ '<a class="close" data-dismiss="alert" href="#">X</a>'
					          + '<h4 class="alert-heading" style="color:red;font-size:20px">Error!</h4>'
					          + '<span style="color:lightRed">Your password must contain at least one digit.</span></span>'
					     + '</div>';
					 	$("#cfaalert").children()
					 	.remove();
					 $("#cfaalert").append(
					 	trs);
							return false;
				    }
				   if (p.search(/[!@#\$%\&\_]/) < 0) {
				       // errors.push("Your password must contain at least special char from -[ ! @ # $ %  & * _ ]"); 
				        
				        trs = '<div class="alert alert-success alert-block"  style="background-color: #a52a2a26">'
					 		+ '<a class="close" data-dismiss="alert" href="#">X</a>'
					          + '<h4 class="alert-heading" style="color:red;font-size:20px">Error!</h4>'
					          + '<span style="color:lightRed">Your password must contain at least special char from -[ ! @ # $ %  & * _ ]</span></span>'
					     + '</div>';
					 	$("#cfaalert").children()
					 	.remove();
					 $("#cfaalert").append(
					 	trs);
							return false;
				    }
				   
				   if (confirmPassword == "" ) {
						 trs = '<div class="alert alert-success alert-block"  style="background-color: #a52a2a26">'
				 		+ '<a class="close" data-dismiss="alert" href="#">X</a>'
				          + '<h4 class="alert-heading" style="color:red;font-size:20px">Error!</h4>'
				          + '<span style="color:lightRed">Confirmpassword is empty please fill</span></span>'
				     + '</div>';
				 	$("#cfaalert").children()
				 	.remove();
				 $("#cfaalert").append(
				 	trs);
						return false;
					}
					
				   if (newpassword != confirmPassword ) {
						 trs = '<div class="alert alert-success alert-block"  style="background-color: #a52a2a26">'
				 		+ '<a class="close" data-dismiss="alert" href="#">X</a>'
				          + '<h4 class="alert-heading" style="color:red;font-size:20px">Error!</h4>'
				          + '<span style="color:lightRed">Password and confirmpassword should be same</span></span>'
				     + '</div>';
				 	$("#cfaalert").children()
				 	.remove();
				 $("#cfaalert").append(
				 	trs);
						return false;
					}
				
					
				
			$(".loading").show();
				      $.ajax({
				        url: "changePassword",
				        type: "POST",
				        enctype: 'multipart/form-data',
				        data: data, //pass the form data
				        processData: false,
				        contentType: false,
				        success: function (data) {
				        	
			            
			            trs = '<div class="alert alert-success alert-block"  style="background-color: #a52a2a26">'
					 		+ '<a class="close" data-dismiss="alert" href="#">X</a>'
					          + '<h4 class="alert-heading" style="color:green;font-size:20px">Sucess!</h4>'
					          + '<span style="color:lightRed">'+data+'.</span></span>'
					     + '</div>';
					 	$("#cfaalert").children()
					 	.remove();
					 $("#cfaalert").append(
					 	trs);
			            if (data ="Successfully updated password"){
			            	
			            	setTimeout( function(){ 
			            		window.location.href = "login";
			            	  }  , 1000 );
			            	//window.location.href = "login";
			            }
			            
			      
				           // $(".loading").hide();    
				          //  $("#example-form")[0].reset();
				           // $('.filename').html("");
				        },
				        error: function (data) {
				        	$(".loading").hide();
				        	 $('.filename').html("");
				        //var er =	JSON.parse(data);
				        const myJSON = JSON.stringify(data);
				        trs = '<div class="alert alert-success alert-block"  style="background-color: #a52a2a26">'
			        		+ '<a class="close" data-dismiss="alert" href="#">X</a>'
			                 + '<h4 class="alert-heading" style="color:red">Error!</h4>'
			                 + '<span style="color:lightRed">'+data+'</span></span>'
			            + '</div>';
			        	$("#cfaalert").children()
			        	.remove();
			        $("#cfaalert").append(
			        	trs); 
				           // console.log("upload failed"+myJSON);
				        	//alert(myJSON);
				        }
				      });



			});
			});

function formValidation() {
	//alert("test");
	var newPwd = document.getElementById('newPassword').value;
	var conPwd = document.getElementById('confirmPassword').value;
	var oldPwd = document.getElementById('oldPassword').value;
	var login_Id = document.getElementById('login_id').value;
	if(login_Id==""){
		alert("Please enter User Id")
	}
	if(newPwd == ""){
		alert("Please enter New Password")
	}
	else if(conPwd == ""){
		alert("Please enter Confirm Password")
	}
	
	else if (newPwd != conPwd) {
		alert("Password does not match");
		return false;
	}
	
	ajax(login_Id,oldPwd,newPwd,conPwd);
}

 function ajax(login_Id,oldPwd,newPwd,conPwd){
	// alert("hi");
	 $.ajax({
			type: "POST",
			url: "changePassword",
			data: {login_Id:login_Id,oldPwd:oldPwd,newPwd:newPwd,conPwd:conPwd},
			success:function(response){
				//alert(response);
				window.location.href="http://localhost:8080/CRO/loginSubmit"
			}
		});
 } 
 
 
</script>
					</div>
				</div>
				<jsp:include page="/WEB-INF/jsp/views/header/footer.jsp"></jsp:include>

				<script src="assets/libs/jquery/dist/jquery.min.js"></script>

				<script
					src="assets/libs/perfect-scrollbar/dist/perfect-scrollbar.jquery.min.js"></script>
				<script src="dist/js/custom.min.js"></script>
				<script type="text/javascript"
					src="assets/libs/datatables/media/js/jquery.dataTables.min.js"></script>
</body>
</html>
