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

#loading {
	position: fixed;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	background-color: rgba(0, 0, 0, 0.7);
	display: flex;
	justify-content: center;
	align-items: center;
	z-index: 9999;
}

#loading img {
	width: 100px; /* Adjust the size of the spinner image as needed */
	height: 100px;
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
					<h4 class="page-title display-5">Change Password</h4>
					<div class="ml-auto text-right">
						<nav aria-label="breadcrumb">
							<ol class="breadcrumb">
								<li class="breadcrumb-item"><a href="landPage">Home</a></li>
								<li class="breadcrumb-item"><a href="manageByAdmin">Management</a></li>
								<li class="breadcrumb-item active" aria-current="page">Change
									Password</li>
							</ol>
						</nav>
					</div>
				</div>
			</div>
		</div>
		<!-- <script
			src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script> -->
			<script
			src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
			
		<div class="container-fluid">
			<!-- ============================================================== -->
			<!-- Start Page Content -->
			<!-- ============================================================== -->
			<div id="cfaalert" style=""></div>
			<c:if test="${role == 'Buyer'}">
				<!-- Only show the page content if the user is an Buyer -->

				<div class="card">
					<div class="card-body">
						<h5 class="card-title"></h5>
						<div class="">

							<div id="zero_config_wrapper"
								class="dataTables_wrapper container-fluid dt-bootstrap4">
								<div class="row">
									<div class="col-sm-12">

										<table id="example"
											class="table table-striped table-bordered display nowrap"
											style="width: 100%;">
											
																					
<style>
    table {
      border-collapse: collapse;
      width: 100%;
    }

    th {
      font-size: 21px;
      text-align: center !important;
    }

    td, th {
      border: 1px solid #dddddd;
      padding: 8px;
      font-size: 17px;
    }
    table {
    box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);
    border-radious:3px;
}
 .headerStyles{
 background: #01AFAE !important;
 color: white !important;      
    font-size: 17px;
 
	}
	.tbodyCustomColor {
	    color: black !important;
    font-size: 17px;
	}
  </style>
											<thead class="thead-light">
												<tr>
													<!-- <th scope="col"><b>SL.NO</b></th> -->
													<!--                                                 <th scope="col" hidden><b>USER ID</b></th> -->
													<th scope="col" class='headerStyles'><b>Cost Centre</b></th>
													<th scope="col" class='headerStyles'><b>Employee Name</b></th>
													<th scope="col" class='headerStyles'><b>Email</b></th>
													<th scope="col" class='headerStyles'><b>Department</b></th>
													<th scope="col" class='headerStyles'><b>Password</b></th>
													<th scope="col" class='headerStyles'><b>New Password</b></th>
													<th scope="col" class='headerStyles'><b>Update</b></th>

												</tr>
											</thead>
											<tbody class="customtable">


												<c:forEach items="${userDetails}" var="user"
													varStatus="loop">
													<tr>
														<%-- <td>${loop.index+1}</td> --%>
														<td class="costcenter tbodyCustomColor">${user[0]}</td>
														<td class='tbodyCustomColor'>${user[1]}</td>
														<td class="address tbodyCustomColor">${user[2]}</td>
														<td class='tbodyCustomColor'>${user[3]}</td>
														<td class="account tbodyCustomColor">${user[4]}</td>
														<td class="editable-cell tbodyCustomColor">${user[5]}</td>

														<td><button class="btn btn-primary update-btn">Update</button></td>



													</tr>

												</c:forEach>
											</tbody>
										</table>

									</div>
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
	</div>

</div>


<script
	src="https://cdn.jsdelivr.net/npm/sweetalert2@10.15.5/dist/sweetalert2.all.min.js"></script>

<
<!-- div id="loading-spinner" style="display: none;">
  Insert your image-based spinner here
  <img src="assets/images/titan-logo.png" alt="Loading..." />
</div> -->

<script>
$(document).ready(function() {
	  
	  $(".update-btn").click(function() {
	    var row = $(this).closest("tr");
	    var newPwdCell = row.find("td.editable-cell");
	    var costcenterCell = row.find("td.costcenter");
	    var accountCell = row.find("td.account");
	    var addressCell = row.find("td.address");

	    var newPwd = newPwdCell.text().trim();	
	    var oldPwd = accountCell.text().trim();
	    var login_id = costcenterCell.text().trim();
	    var email = addressCell.text().trim();

	    if (!newPwd) {
	      Swal.fire("Error!", "Please enter a new password.", "error");
	      return;
	    }

	    var passwordRegex = /^(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{7,}$/;

	    if (!passwordRegex.test(newPwd)) {
	      var errorMessages = [];

	      if (!/[A-Z]/.test(newPwd)) {
	        errorMessages.push("at least one capital letter");
	      }
	      if (!/\d/.test(newPwd)) {
	        errorMessages.push("at least one number");
	      }
	      if (!/[@$!%*?&]/.test(newPwd)) {
	        errorMessages.push("at least one special character");
	      }
	      
	      Swal.fire(
	        "Error!",
	        "New password must contain " + errorMessages.join(", ") + ", and be at least 7 characters long.",
	        "error"
	      );
	      return;
	    }
	    
	    Swal.fire({
	      title: "Are you sure you want to update the password?",
	     
	      icon: "info",
	      showCancelButton: true,
	      confirmButtonColor: "#3085d6",
	      cancelButtonColor: "#d33",
	      confirmButtonText: "Yes, update it!",
	      cancelButtonText: "Cancel",
	    }).then((result) => {
	      if (result.isConfirmed) {
	        // Show the loading spinner when the user clicks "Yes"
	        $("#loading").show();

	        $.ajax({
	          type: "POST",
	          url: "updatepasswordn",
	          data: {
	            newPwd: newPwd,
	            oldPwd: oldPwd,
	            login_id: login_id,
	            email: email,
	          },
	          success: function(response) {
	            var message = response.message;

	            // Hide the loading spinner when the AJAX request is complete
	            $("#loading").hide();

	            Swal.fire({
	              title: "Success!",
	              text: message,
	              icon: "success",
	            }).then(() => {
	              location.reload();
	            });
	          },
	          error: function(xhr, textStatus, errorThrown) {
	            // Hide the loading spinner if there is an error
	            $("#loading").hide();

	            Swal.fire("Error!", "Update failed: " + textStatus, "error");
	          },
	        });
	      }
	    });
	  });
	});

  
</script>







<script type="text/javascript">
	history.pushState(null, null, location.href);
	window.onpopstate = function() {
		history.go(1);
	};
</script>
<script type="text/javascript">
document.addEventListener("DOMContentLoaded", function() {

						// Setup - add a text input to each footer cell

						var table = $('#example')
								.DataTable(
										{
											/* "pageLength" : 10, */
											paging : false,
											scrollX : true,
											orderCellsTop : true,
											fixedHeader : true,
											initComplete : function() {
												var api = this.api();

												// For each column
												api
														.columns()
														.eq(0)
														.each(
																function(colIdx) {
																	// Set the header cell to contain the input element
																	var cell = $(
																			'.filters th')
																			.eq(
																					$(
																							api
																									.column(
																											colIdx)
																									.header())
																							.index());
																	var title = $(
																			cell)
																			.text();
																	$(cell)
																			.html(
																					'<input class="form-control" type="text" placeholder="' + title + '" />');

																	// On every keypress in this input
																	$(
																			'input',
																			$(
																					'.filters th')
																					.eq(
																							$(
																									api
																											.column(
																													colIdx)
																											.header())
																									.index()))
																			.off(
																					'keyup change')
																			.on(
																					'keyup change',
																					function(
																							e) {
																						e
																								.stopPropagation();

																						// Get the search value
																						$(
																								this)
																								.attr(
																										'title',
																										$(
																												this)
																												.val());
																						var regexr = '({search})'; //$(this).parents('th').find('select').val();

																						var cursorPosition = this.selectionStart;
																						// Search the column for that value
																						/*  api
																						     .column(colIdx)
																						     .search(
																						         this.value != ''
																						             ? regexr.replace('{search}', '(((' + this.value + ')))')
																						             : '',
																						         this.value != '',
																						         this.value == ''
																						     )
																						     .draw(); */

																						$(
																								this)
																								.focus()[0]
																								.setSelectionRange(
																										cursorPosition,
																										cursorPosition);
																					});
																});
											},
										});

						$('#example')
								.on(
										'click',
										'td',
										function() {
											if ($(this).hasClass(
													'editable-cell')) {
												var cell = table.cell(this);
												var originalValue = cell.data();

												var columnIndex = cell.index().column;
												var columnHeaderWidth = $(
														table.column(
																columnIndex)
																.header())
														.width();

												var input = $('<input type="text" id="input-text" class="input-text">');
												// input.css('width', columnHeaderWidth); // Set input width to match column header
												//setInputWidth(input, columnHeaderWidth);
												input.val(originalValue);

												input.on('blur', function() {
													var newValue = input.val();
													cell.data(newValue);
													//setInputWidth(input,columnHeaderWidth);
												});

												$(this).html(input);

												input.focus();
												input.select();

												input.get(0).contentEditable = true;
											}
										});

					});
</script>


<div id="loading" style="display: none;">

	<img src="assets/images/titan-logo.png" alt="Loading..." />
</div>

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


</script>
</body>

</html>

