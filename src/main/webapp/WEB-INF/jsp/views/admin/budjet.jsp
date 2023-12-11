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
<title>Stationery</title>
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

<script type="text/javascript" src="dist/js/jquery-3.6.4.min.js"></script>
<script src="dist/js/sweetalert2.min.js"></script>
<link rel="stylesheet" href="dist/css/sweetalert2.min.css" />
<style>
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

section label {
	padding-top: 10px;
}

.m-t-40 {
	margin-top: 20px !important;
}

vertical-align: text-bottom ; footer {
	color: Teal;
	font-weight: bold;
	/* color:#ffffffd9;
  	position: fixed; */
	bottom: 0;
	left: 0;
	width: 100%;
	text-align: center;
}

.page-title {
	color: Teal;
}

.oneline {
	margin-left: 20px;
}

#quarterDivId   .custom-control-label {
	margin-top: -10px;
	padding-right: 20px
}

.error {
	color: #a94442;
}

label {
	font-size: 25px;
	font-weight: bold;
}

@media ( min-width : 800px) {
	.oneline {
		display: inline-flex;
	}
	.paghedding {
		margin-bottom: 20px;
	}
	.page-titleing {
		margin-top: 15px;
		font-size: 30px;
		color: black;
	}
	/* .card-body {
		margin-left: 15%;
		margin-right: 15%;
		background-color: #98daf9;
		border-radius: 50px;
		margin-top: 15px;
		margin-bottom: 20px
	} */
	#cfaalert {
		width: 400px;
		margin-left: 34%;
	}
}
</style>

</head>

<!-- ============================================================== -->
<!-- Main wrapper - style you can find in pages.scss -->
<!-- ============================================================== -->
<div id="main-wrapper" data-sidebartype="full" class="mini-sidebar">
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
					<h4 class="page-title display-5">SAP Indent</h4>
					<div class="ml-auto text-right">
						<nav aria-label="breadcrumb">
							<ol class="breadcrumb">
								<li class="breadcrumb-item"><a href="landPage">Home</a></li>
								<li class="breadcrumb-item"><a href="manageByAdmin">Management</a></li>
								<li class="breadcrumb-item active" aria-current="page">SAP Indent</li>
							</ol>
						</nav>
					</div>
				</div>
			</div>
		</div>
		<div class="container-fluid">
			<!-- ============================================================== -->
			<!-- Start Page Content -->
			<!-- ============================================================== -->
			<div id="cfaalert" style=""></div>
			<c:if test="${role == 'Buyer'}">
				<!-- Only show the page content if the user is an Buyer -->
				<div class="card">
					<div class="card-body wizard-content">
						<!--   <h4 class="card-title"></h4> -->
						<h6 class="card-subtitle"></h6>
						<div id="cfaalert" align="center" style="width: 400px"></div>
						<div class="col-md-12 oneline">
								<div class="col-md-2"></div>
								<div style="margin-top: 10px;" class="col-md-3">
									<label for="Year"> Year <label
										style="color: red;">*</label>:
									</label>
								</div>
								<div class="col-md-5">
										<select id="year-element" name="Year" class="form-control"
										aria-invalid="true" required="required">
										<option value="">Select Year</option>
										<option id=currentYear></option>
										<%-- <c:forEach items="${years}" var="year">
											<option value="${year[0]}">${year[0]}</option>
										</c:forEach> --%>
									</select>
								</div>
							</div>
							<div class="col-md-12 oneline">
								<div class="col-md-2"></div>
								<div style="margin-top: 10px;" class="col-md-3">
									<label for="Year"> Month<label
										style="color: red;">*</label>:
									</label>
								</div>
								<div class="col-md-5">	
										<select id=month-element name="Year" class="form-control"
										aria-invalid="true" required="required">
										<option value="">Select Month</option>
										<option value="January">January</option>
													<option value="February">February</option>
													<option value="March">March</option>
													<option value="April">April</option>
													<option value="May">May</option>
													<option value="June">June</option>
													<option value="July">July</option>
													<option value="August">August</option>
													<option value="September">September</option>
													<option value="October">October</option>
													<option value="November">November</option>
													<option value="December">December</option>
										<%-- <c:forEach items="${years}" var="year">
											<option value="${year[0]}">${year[0]}</option>
										</c:forEach> --%>
									</select>
								</div>
							</div>
							<div class="col-md-12 oneline">
								<div class="col-md-2"></div>
								<div style="margin-top: 10px;" class="col-md-3">
									<label for="CCID"> Cost Center <label
										style="color: red;">*</label>:
									</label>
								</div>
								<div class="col-md-5">
										<select id="cs-element" name="CCID" class="form-control"
										aria-invalid="true" required="required">
										<c:forEach items="${ccEmpsIds}" var="ccid">
											<option value="${ccid[0]}">${ccid[0]}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="col-md-12 oneline">
								<div class="col-md-2"></div>
								<div style="margin-top: 10px;" class="col-md-3">
									<label for="COSTCENTERDESC"> P O Amount <label style="color: red;">*</label>:
									</label>
								</div>
								<div class="col-md-5">
								<input type="text" id="po-element" maxlength="7" name="COSTCENTERDESC" 
										class="form-control" aria-invalid="true" required="required">
									<%-- <select id="COSTCENTERDESC" name="COSTCENTERDESC" class="form-control"
										aria-invalid="true" required="required">
										<option value="">Select Vendor</option>
										<c:forEach items="${vendorList}" var="vendor">
											<option value="${vendor}">${vendor}</option>
										</c:forEach>
									</select> --%>
								
								</div>
							</div>
							<div class="col-md-12 oneline">
								<div class="col-md-4"></div>
								<div class="col-md-4 text-center">
									<!-- Add "text-center" class to center the button -->
									<button onclick="poEntryReset()" class="btn btn-primary"
										id="submitId">Reset</button>
									<button onclick="poEntryCreation()" class="btn btn-success"
										id="submitId">Submit</button>
								</div>
								<div class="col-md-4"></div>
							</div>
					</div>
					
				</div>

		</div>
	</div>

	</c:if>

	<c:if test="${role != 'Buyer'}">
		<!-- Show an access denied message or redirect if the user is not an Indent Manager -->
		<div class="card">
			<div class="card-body">
				<h5 class="card-title">Access Denied</h5>
				<p>You do not have permission to access this page.</p>
			</div>
		</div>
	</c:if>

</div>




<div class="loading" style="display: none;">Loading&#8230;</div>

<script>
var currentYear = new Date().getFullYear();
// Update the content of the HTML element with the current year
document.getElementById("currentYear").textContent = currentYear;


	function poEntryCreation() {
		var year = $("#year-element").val();
		var month = $("#month-element").val();
		var csCenter = $("#cs-element").val();
		var poEntry = $("#po-element").val();
         if (year == "") {
			Swal.fire({
				icon : 'warning',
				title : 'Please enter Year ',
				focusConfirm : false,
			})
			return;
		}else if (month == "") {
				Swal.fire({
					icon : 'warning',
					title : 'Please enter month ',
					focusConfirm : false,
				})
				return;
			} else if ((csCenter == "")) {
				Swal.fire({
					icon : 'warning',
					title : 'Please enter costomer center ',
					focusConfirm : false,
				})
				return;
			} else if ((poEntry == "") || (poEntry <= 0)) {
				Swal.fire({
					icon : 'warning',
					title : 'Please enter po Amount ',
					focusConfirm : false,
				})
				return;
			} 
			
			else {
				//Swal.fire({
				//	icon : 'success',
				//	title : 'The Po entry data has been submitted successfully.',
				//	focusConfirm : false,
			//	})
		   // location.href = 'poEntryTable';
			}
		$(".loading").show();
		// to update the form data to the dara
		$.ajax({
			type : "POST",
			url : "poEntryDataCreatioonSave",
			data : "YEAR=" + year + "&MONTH=" + month + "&COSTCENTER="
					+ csCenter + "&POAMOUNT=" + poEntry,
			success : function(response) {
				var data = jQuery.parseJSON(response);
				if (data == "Budget created sucessfully") {
					$(".loading").hide();
					const successMessage = "Po data entered successfully"
					Swal.fire({
						icon : 'success',
						title : successMessage,
						showCloseButton : false,
						showCancelButton : false,
						focusConfirm : false,
					}).then((result) => {
			    	    if (result.isConfirmed) {
		   location.href = 'poEntryTable';
           		}})	

				} else {
					$(".loading").hide();
					Swal.fire({

						icon : 'error',
						title : data,
						showCloseButton : false,
						focusConfirm : false,

					}).then((result) => {
			    	    if (result.isConfirmed) {
		}})	
				}

			},//end of success function
			error : function(error) {

				$(".loading").hide();
				Swal.fire({

					icon : 'error',
					title : JSON.stringify(error),
					showCloseButton : false,
					focusConfirm : false,

				})
				//$(".loading").hide();
			}
		});
	}
	
	function poEntryReset(){
		 $("#year-element").val('');
		 $("#month-element").val('');
		 $("#cs-element").val('');
	     $("#po-element").val('');
	}
</script>

<script>
	$('select[name="vendor"]').on('change', function() {
		var vendorname = $('#vendor').val();

		$(".loading").show();
		$.ajax({
			type : "GET",
			url : "getVendorEmailId",
			data : "vendorname=" + vendorname,
			success : function(response) {
				data = jQuery.parseJSON(response);
				$(".loading").hide();
				console.log(data)
				$('#EmailID').val(data[0])
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