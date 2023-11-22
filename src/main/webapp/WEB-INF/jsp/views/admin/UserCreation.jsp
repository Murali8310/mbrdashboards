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
<title>ISCM Stationery</title>
<!-- Custom CSS -->
<link href="assets/libs/flot/css/float-chart.css" rel="stylesheet">
<!-- Custom CSS -->
<link href=" dist/css/style.min.css" rel="stylesheet">

<link href="dist/css/loading.css" rel="stylesheet" type="text/css" />
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

<!-- <link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.css" /> -->

<!--  <link rel="stylesheet" type="text/css" href="dist/css/example-styles.css">
    <link rel="stylesheet" type="text/css" href="dist/css/demo-styles.css"> -->
<script type="text/javascript" src="dist/js/jquery-3.6.4.min.js"></script>

<!-- <script type="text/javascript"
	src=" https://cdnjs.cloudflare.com/ajax/libs/limonte-sweetalert2/11.6.16/sweetalert2.all.min.js"></script> -->
<script src="dist/js/sweetalert2.min.js"></script>
<link rel="stylesheet" href="dist/css/sweetalert2.min.css" />
<style>
.table {
	margin-top: 20px;
}

.multi-select-container {
	font-size: 22px;
	/* height: 35px; */
	width: 100%;
	/* margin-top: 32px */
}

#LMSIdselectID_chosen {
	width: 350px !important;
}

#InterchangeLMSIdselectID_chosen {
	width: 350px !important;
}

#repairLMSIdselectID_chosen {
	width: 350px !important;
}

.table th, .table td {
	border-top: none !important;
}

label {
	font-weight: bold;
}

.popimage {
	margin-top: 30px;
}

.red-star {
	color: red;
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

.card-default {
	color: #333;
	background: linear-gradient(#fff, #ebebeb) repeat scroll 0 0 transparent;
	font-weight: 600;
	border-radius: 6px;
}

.a { `
	color: white !important;
	text-decoration: none !important;
}

@media only screen and (min-width: 260px) and (max-width: 320px) {
	.page-title {
		/*margin-top: 20% !important;*/
		font-size: 24px !important;
	}
	.breadcrumb {
		display: none;
	}
}
</style>

</head>

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

	<!------ Include the above in your HEAD tag ---------->



	<!-- ============================================================== -->
	<!-- End Left Sidebar - style you can find in sidebar.scss  -->
	<!-- ============================================================== -->
	<!-- ============================================================== -->
	<!-- Page wrapper  -->
	<div class="page-wrapper">
		<div class="page-breadcrumb">
			<div class="row">
				<div class="col-12 d-flex no-block align-items-center">
					<h4 class="page-title display-5">Indent Manager Creation</h4>
					<div class="ml-auto text-right">
						<nav aria-label="breadcrumb">
							<ol class="breadcrumb">
								<li class="breadcrumb-item"><a href="landPage">Home</a></li>
								<li class="breadcrumb-item"><a href="manageByAdmin">Management</a></li>
								<li class="breadcrumb-item active" aria-current="page">Indent
									Manager Creation</li>
							</ol>
						</nav>
					</div>
				</div>
			</div>
		</div>
		<script
			src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
		<div class="container-fluid">
			<!-- ============================================================== -->
			<!-- Start Page Content -->
			<!-- ============================================================== -->
			<div id="cfaalert" style=""></div>
			<div class="card">
				<div id="collapse1" class="collapse show">
					<div class="card-body">
						<form id="abmcretion" onreset="clearAll()">
							<div class="row">
								<div class="col-sm-6 col-md-6 col-lg-4 col-12">
									<div class="form-group">
										<label class="control-label">Employee Code <span
											class="red-star">*</span></label> <input type="text" id="empid"
											class="form-control" />
									</div>
								</div>
								<!-- <div class="col-sm-1 col-md-1 col-lg-1 col-2">

									<a data-toggle="modal" data-target="#myModal"> <img
										class="popimage"
										src="https://ui-uat.suraksha.live/assets/images/icon-add.svg"
										alt=""></a>
								</div> -->

								<div class="col-sm-6  col-12 col-md-6 col-lg-4">
									<div class="form-group">
										<label class="control-label">Employee Name<span
											class="red-star">*</span></label> <input type="text" id="empname"
											class="form-control" />
									</div>
								</div>
								<div class="col-sm-6 col-12 col-md-6 col-lg-4">
									<div class="form-group">
										<label class="control-label">Cost Centre <span
											class="red-star">*</span></label> <input type="text" id="designation"
											name="designation" class="form-control" />

									</div>
								</div>

								<div class="col-sm-6 col-12 col-md-6 col-lg-4">
									<div class="form-group">
										<label class="control-label">Department <span
											class="red-star">*</span></label> <input type="text" id="city"
											maxlength=40 name="city" class="form-control" />
									</div>
								</div>


								<div class="col-sm-6 col-12 col-md-6 col-lg-4">
									<div class="form-group">
										<label class="control-label">Email id <span
											class="red-star">*</span></label> <input type="text" id="email"
											maxlength=30 name="email" class="form-control" />
									</div>
								</div>
								<div class="col-sm-6 col-12 col-md-6 col-lg-4">
									<div class="form-group">
										<label class="control-label">Mobile Number<span
											class="red-star">*</span></label> <input
											onkeypress="return (event.charCode !=8 && event.charCode ==0 || (event.charCode >= 48 && event.charCode <= 57))"
											maxlength=10 type="text" id="mobile" name="mobile"
											class="form-control" />
									</div>
								</div>
								<div class="col-6 col-sm-6 col-md-6  col-lg-4">
									<div class="form-group">
										<label class="control-label">Category <span
											class="red-star">*</span></label> <select name="category"
											id="category" class="form-control">
											<option value="">Please Select Category</option>
											<option value="IndentManager">Indent Manager</option>
											<option value="BuyerManager">Buyer Manager</option>
											<option value="Distribution Manager">Distribution
												Manager</option>

										</select>
									</div>
								</div>




							</div>
							<div style="float: right; margin-bottom: 31px;">
								<div class="row">
									<div class="col-md-4 col-lg-4 col-4">
										<a href="manageByAdmin"> <input type="button"
											class="btn btn-primary" id="expiryDatebut" value="Cancel">
										</a>

									</div>
									<div class="col-md-4 col-lg-4 col-4">
										<button type="reset" id="reset" class="btn btn-warning">Reset</button>

									</div>
									<div class="col-md-4 col-lg-4 col-4">
										<a onclick="createabm()" class="btn btn-success" id="submitId">Submit</a>

									</div>
								</div>

							</div>
						</form>
					</div>
					`
				</div>
			</div>



		</div>
	</div>
</div>


<div class="loading" style="display: none;">Loading&#8230;</div>
<script>
	function clearAll() {
		$(".multi-select-button").each(function(index, element) {
			element.innerHTML = "--Select--"
		});

		$("#productQualityDivId").children().remove();
	}

	function createabm() {
		var empid = $("#empid").val();
		var empname = $("#empname").val();
		var designation = $("#designation").val();
		//var department = $("#department").val();
		var city = $("#city").val();
		var email = $("#email").val();
		//var state = $("#state").val();
		//var statecode = $("#statecode").val();
		//var region = $("#region").val();
		//var jdate = "null"
		//var ldate = "null"
		//var storecode = $("#storecode").val();
		var mobile = $("#mobile").val();

		if (empid == "") {
			Swal.fire({
				icon : 'warning',
				title : 'Employee Code required',
				focusConfirm : false,
			})
			return;
		}
		if (empname == "") {
			Swal.fire({
				icon : 'warning',
				title : 'Employee Name required',
				focusConfirm : false,
			})
			return;
		}
		if (designation == "" || designation == null) {
			Swal.fire({
				icon : 'warning',
				title : 'Cost Centre required',
				focusConfirm : false,
			})
			return;

		}
		if (city == "") {
			Swal.fire({
				icon : 'warning',
				title : 'Department required',
				focusConfirm : false,
			})
			return;
		}

		if (email == "") {
			Swal.fire({
				icon : 'warning',
				title : 'Email required',
				focusConfirm : false,
			})
			return;
		}

		if (mobile == "") {
			Swal.fire({
				icon : 'warning',
				title : 'Mobilenumber required',
				focusConfirm : false,
			})
			return;
		}
		var phoneno = /^\d{10}$/;
		if (mobile.match(phoneno)) {

		} else {
			Swal.fire({
				icon : 'warning',
				title : 'Please enter valid mobile number.',
				focusConfirm : false,
			})
			return;
		}

		$(".loading").show();
		//    alert(jdate);
		$
				.ajax({
					type : "POST",
					url : "UserCreation",
					data : "empid=" + empid + "&empname=" + empname
							+ "&designation=" + designation + "&city=" + city
							+ "&email=" + email

							+ "&mobile=" + mobile,

					success : function(response) {
						//alert(response);

						var res = jQuery.parseJSON(response);

						if (res == "User Created Sucessfully") {

							$(".loading").hide();
							$("#abmcretion")
									.find(
											'input:text, input:password,input:checkbox, input:file, select, textarea')
									.val('');
							$("#statecode").val("");
							$('input[type=checkbox]').prop('checked', false);
							$('.multi-select-button').text("--select--");
							Swal.fire({

								icon : 'success',
								title : res,
								showCloseButton : false,
								focusConfirm : true,

							})

						} else {

							$(".loading").hide();
							Swal.fire({
								icon : 'error',
								title : res,
								focusConfirm : false,
							})
						}
					},//end of success function
					error : function(error) {

						$(".loading").hide();
						Swal.fire({
							icon : 'error',
							title : JSON.stringify(error),
							focusConfirm : false,
						})
					}
				});
	}

	$("body").on("click", "#userselect", function() {

		//alert("tes");
		//var appd =  $("#popupdata").val();

		var id = $(this).val()
		//console.log(nameArr);

		var empid = $("#empid" + id).val();
		var empname = $("#empname" + id).val();
		var designation = $("#designation" + id).val();
		//var department = $("#department" + id).val();
		var city = $("#city" + id).val();
		var email = $("#email" + id).val();

		$("#empid").val(empid);
		$("#empname").val(empname);
		$("#designation").val(designation);
		$("#city").val(city);
		$("#email").val(email);

		// Accessing individual values
		// alert(nameArr[0]); // Outputs: Harry
		// alert(nameArr[1]);

		// $("#close").click();
		//$( "#close" ).trigger( "click" );
		//$('.modal').removeClass('show');
		$('#myModal').modal('toggle');
		$("#close").trigger("click");
		//$('#myModal').modal().hide();
		$("#my_field").val("");

	});
	$('#myModal').on('hidden.bs.modal', function(e) {

		$("#productQualityDivId").children().remove();
		$('#my_field').val('');
	});
	$(document)
			.ready(
					function() {
						$("#my_field")
								.keydown(
										function(e) {

											if (e.keyCode != 8
													&& e.keyCode != 9) {
												//alert (String.fromCharCode(e.which));
												var search = $("#my_field")
														.val()
														+ String
																.fromCharCode(e.which);
												// alert("search "+ search);
												if (search.length > 3) {
													$(".loading").show();
													$
															.ajax({
																type : "GET",
																url : "searchbyusers",
																data : "firstName="
																		+ search,

																success : function(
																		response) {
																	var data = jQuery
																			.parseJSON(response);
																	$(
																			".loading")
																			.hide();
																	//alert("data"+data);
																	var productQualityDetails = "";
																	productQualityDetails += '<div id="producttable"><table id="data" class="table table-striped table-bordered table-hover" > <thead><tr>'

																			+ '<th>Select</th>'
																			+ '<th>Employee ID</th>'
																			+ ' <th>First Name</th>'

																			+ ' <th>Designation</th>'
																			+ ' <th>Email</th>'

																			+ ' </tr> </thead> <tbody class="customtable">';

																	var array = [];
																	for (var i = 0; i < data.length; i++) {

																		if (data[i][6] == 1) {
																			var status = "Working";
																		} else {
																			var status = "Not Working";
																		}
																		productQualityDetails += '<tr><td>'

																				+ '<input name="userselect" id="userselect" type="radio" value="' + i + '"></td><td>'
																				+ data[i][0]
																				+ '<input name="empid' + i + '" id="empid' + i + '" type="hidden" value="' + data[i][0] + '"></td><td>'
																				+ data[i][1]
																				+ '<input name="empname' + i + '" id="empname' + i + '" type="hidden" value="' + data[i][1] + '"></td><td>'
																				+ data[i][2]
																				+ '<input name="designation' + i + '" id="designation' + i + '" type="hidden" value="' + data[i][2] + '"></td><td>'

																				+ '<input name="department' + i + '" id="department' + i + '" type="hidden" value="' + data[i][3] + '">'
																				+ '<input name="email' + i + '" id="email' + i + '" type="hidden" value="' + data[i][5] + '">'

																				+ '<input name="city' + i + '" id="city' + i + '" type="hidden" value="' + data[i][4] + '">'

																				+ data[i][5]
																				+ '</td></tr>'

																	}
																	productQualityDetails += '</tbody></table></div>';

																	$(
																			"#dtBasicExample_wrapper")
																			.css(
																					"display",
																					"none");

																	$(
																			"#productQualityDivId")
																			.children()
																			.remove();
																	$(
																			"#productQualityDivId")
																			.append(
																					productQualityDetails);
																	$(
																			".loading")
																			.hide();
																	$(
																			"#productQualityFeedbackID")
																			.show();
																	//  $("#dataTables-example").bootstrapTable('refresh');
																	//  $('#dataTables-example').dataTable();

																},//end of success function
																error : function(
																		error) {
																	/* alert("error : "
																			+ error); */
																	Swal
																			.fire({
																				icon : 'error',
																				title : error,
																				showCloseButton : false,
																				focusConfirm : false,

																			})

																	$(
																			".loading")
																			.hide();
																}
															});
												}
											}
										});
					});
</script>

<style>
#producttable {
	max-height: 500px;
	overflow: scroll;
}

table {
	border: 1px solid black;
	border-collapse: collapse;
	padding: 0px;
}

th {
	padding: 10px;
	border: 1px solid black;
}

td {
	padding: 10px;
	border: 1px solid black;
	text-align: left;
}

tr:nth-child(even) {
	background-color: white;
}

tr:nth-child(odd) {
	background-color: #eee;
}
</style>
<div class="modal" id="myModal" role="dialog">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
				<h2>Employee search</h2>
				<button type="button" class="close" id="close" data-dismiss="modal">&times;</button>

			</div>
			<div class="modal-body">
				<input id="my_field" type="text" class="form-control"
					onkeypress="return /[0-9a-zA-Z]/i.test(event.key);validateType(event)"
					onpaste="return validate(event)">
				<div id="productQualityDivId"></div>

			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			</div>
		</div>
	</div>
</div>
</div>
<div class="loading" style="display: none;">Loading&#8230;</div>
<script>
	function validateType(event) {
		// Get the input value
		const input = event.target.value;
		const keyCode = event.keyCode || event.which;
		// Check if the input contains special characters
		if (/[^a-zA-Z0-9]/.test(input) || keyCode === 39 || keyCode === 34) {
			// Cancel the keypress event
			event.preventDefault();
			return false;
		}

		return true;
	}
	function validate(event) {
		// Get the pasted text from the event object
		const pastedText = event.clipboardData.getData('text/plain');

		// Check if the pasted text contains special characters
		if (/[^a-zA-Z0-9]/.test(pastedText)) {
			// Cancel the paste operation
			event.preventDefault();
			return false;
		}

		return true;
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

<!-- <script
	src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/1.1.3/sweetalert.min.js"></script> -->

<script src="dist/js/sweetalert2.min.js"></script>
<link rel="stylesheet" href="dist/css/sweetalert2.min.css">
<link rel="stylesheet" type="text/css"
	href="dist/css/example-styles.css">
<script type="text/javascript" src="dist/js/jquery.multi-select.js"></script>

<script type="text/javascript">
	$(function() {
		$('#storecode').multiSelect();
	});
</script>
</body>

</html>

