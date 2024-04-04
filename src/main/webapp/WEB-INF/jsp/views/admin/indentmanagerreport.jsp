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
<!-- Custom CSS
<link href="assets/libs/flot/css/float-chart.css" rel="stylesheet">  -->
<!-- Custom CSS -->
<link href=" dist/css/style.min.css" rel="stylesheet">
<link href="dist/css/loading.css" rel="stylesheet" type="text/css" />

<link
	href="https://cdn.datatables.net/1.13.1/css/jquery.dataTables.min.css"
	rel="stylesheet" type="text/css" />
<link
	href="https://cdn.datatables.net/buttons/2.3.2/css/buttons.dataTables.min.css"
	rel="stylesheet" type="text/css" />
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"
	rel="stylesheet" type="text/css" />
<style>
section label {
	padding-top: 10px;
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

.oneline {
	margin-left: -20px;
}

.modal-lg {
	width: 1060px !important;
}

#quarterDivId   .custom-control-label {
	margin-top: -10px;
	padding-right: 20px
}

.error {
	color: #a94442;
}

.modal-lg {
	max-width: 1100px !important;
}
/*@media ( min-width : 768px) {
 .oneline{
display: inline-flex;
}
 }
 
@media ( max-width : 768px) {

.col-md-4{
    padding-right: 0px !important;
}
.page-title{
margin-top: 20px !important;

}
.btn-secondary {
color: #fff;
background-color: #0980ea !important;
border-color: #6c757d;
}
} */
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

.filter-controls {
	display: flex;
	align-items: center;
}

.filter-item {
	display: flex;
	align-items: center;
	margin-right: 20px;
}

.filter-item label {
	margin-right: 5px;
	font-weight: bold;
}

.month-label {
	color: black;
}

.year-label {
	color: black;
}

.btn-search {
	margin-top: 2px;
}
</style>

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<!-- <script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script> -->
	 <script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script> 
	

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
						<h4 class="page-title display-5">Indent Report</h4>
						<div class="ml-auto text-right">
							<nav aria-label="breadcrumb">
								<ol class="breadcrumb">
									<li class="breadcrumb-item"><a href="landPage">Home</a></li>
									<li class="breadcrumb-item"><a href="adminreports">Reports</a></li>
									<li class="breadcrumb-item active" aria-current="page">Indent
										Report</li>
								</ol>
							</nav>
						</div>
					</div>
				</div>
			</div>
			<script>
				function checkPasswordMatch() {
					var password = $("#password").val();
					var confirmPassword = $("#cpwd").val();
					if (password != confirmPassword)

						//$("#CheckPasswordMatch").css("color", "red");
						$("#CheckPasswordMatch").html(
								"Passwords does not match!")
								.css("color", "red");
					// $('input[type="submit"]').attr('disabled' , false);
					// $("#submitId").prop('disabled', true);
					//   $("#CheckPasswordMatch").css("color", "red");

					else
						$("#CheckPasswordMatch").html("Passwords match.").css(
								"color", "red");
					//   $("#CheckPasswordMatch").css("color", "green");
					//           

				}
			</script>
			<!-- ============================================================== -->
			<!-- Container fluid  -->
			<!-- ============================================================== -->
			<div class="container-fluid">
				<!-- ============================================================== -->
				<!-- Start Page Content -->
				<!-- ============================================================== -->
				<script
					src="https://cdn.rawgit.com/rainabba/jquery-table2excel/1.1.0/dist/jquery.table2excel.min.js"></script>
				<script>
					
				</script>

				<div class="card">
					<div class="card-body">
						<h5 class="card-title"></h5>
						<div class="">

							<div id="zero_config_wrapper"
								class="dataTables_wrapper container-fluid dt-bootstrap4">
								<div class="row">
									<div class="col-sm-12">
										<div class="filter-controls">
											<div class="col-md-3 filter-item">
												<label style="font-size: 18px !important;
    font-weight: bold !important;" for="Month" class="month-label">Month:</label> <select
													id="Month" name="Month" class="form-control"
													aria-invalid="true" required="required"
													style="width: 100%;">
													<option value="All">All</option>
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
												</select>
											</div>
											<div class="col-md-3 filter-item">
												<label style="font-size: 18px !important;
    font-weight: bold !important;" for="yearDropdown" class="year-label">Year:</label> <select
													id="Year" class="form-control" aria-invalid="true"
													required="required" style="width: 97%;">
													<c:forEach items="${years}" var="year">
														<option value="${year}">${year}</option>
													</c:forEach>
												</select>
											</div>
											<div class="col-md-3 filter-item">
												<button class="btn btn-primary btn-search" style="font-size: 18px !important;
    font-weight: bold !important;">Search</button>
											</div>
										</div>

										<table id="example"
											class="table table-striped table-bordered display nowrap"
											style="width: 100%;">
																																
<style>
#example_filter label {
font-size: 18px !important;
    font-weight: bold !important;
        color: black;
}
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
													<th scope="col" class='headerStyles'><b>Document Number</b></th>
													<th scope="col" class='headerStyles'><b>Cost Center</b></th>
													<th scope="col" class='headerStyles'><b>Product</b></th>
													<th scope="col" class='headerStyles'><b>Department</b></th>
													<th scope="col" class='headerStyles'><b>QTY</b></th>
													<th scope="col" class='headerStyles'><b>Buyer QTY</b></th>
													<th scope="col" class='headerStyles'><b>Unit Price(Rs)</b></th>
													<th scope="col" class='headerStyles'><b>Received QTY</b></th>
													<th scope="col" class='headerStyles'><b>Document Date</b></th>
													<th scope="col" class='headerStyles'><b>Value(Rs)</b></th>
													<th scope="col" class='headerStyles'><b>Month</b></th>
													<th scope="col" class='headerStyles'><b>Year</b></th>
													<th scope="col" class='headerStyles'><b>Status</b></th>
												</tr>
											</thead>
											<tbody class="customtable">
									<c:forEach items="${reportdetails}" var="user"
													varStatus="loop">
													<tr>
														<%-- <td>${loop.index+1}</td> --%>
														<td class='tbodyCustomColor'>${user[0]}</td>
														<td class='tbodyCustomColor'>${user[1]}</td>
														<td class='tbodyCustomColor'>${user[2]}</td>
														<td class='tbodyCustomColor'>${user[3]}</td>
														<td class='tbodyCustomColor'>${user[4]}</td>
														<td class='tbodyCustomColor'>${user[10]}</td>
														<td class='tbodyCustomColor'>${user[12]}</td>
														<td class='tbodyCustomColor'>${user[11]}</td>
														<td class='tbodyCustomColor' >${user[5]}</td>
														<td class='tbodyCustomColor' style="text-align:right">${user[6]}</td>
														<td class='tbodyCustomColor' style="text-align:center">${user[7]}</td>
														<td class='tbodyCustomColor'>${user[8]}</td>
														<td class='tbodyCustomColor'>${user[9]}</td>

													</tr>

												</c:forEach>
											</tbody>
											<tfoot id='tfootexample'>
													<tr>
														<th class='tbodyCustomColor'>Total</th>
														<th class='tbodyCustomColor'></th>
														<th class='tbodyCustomColor'></th>
																												<th class='tbodyCustomColor' id="footerUserQty" style="text-align:center"></th>
																												<th class='tbodyCustomColor' id="totalQty" style="text-align:left !important"></th>
																												<th class='tbodyCustomColor' style="text-align:left !important" id="totalBuyerQty"></th>
																												<th class='tbodyCustomColor' id="unitPrice"	style="text-align:left !important"></th>
																												<th class='tbodyCustomColor' style="text-align:right"  id="footerTotalValue"></th>
																												<th class='tbodyCustomColor' style="text-align:center"></th>
																												<th class='tbodyCustomColor' id="unitValue" style="text-align:right !important"></th>
																												<th class='tbodyCustomColor' style="text-align:right" id="footerStockValue"></th>
																												<th class='tbodyCustomColor' style="text-align:right" id="footerStockValue"></th>
																												<th class='tbodyCustomColor' style="text-align:right" id="footerStockValue"></th>
														

												</tr>

													
											</tfoot>
										</table>

									</div>
								</div>
							</div>

						</div>
					</div>

				</div>

			</div>
		</div>

	</div>

	<div class="modal fade" id="myModal" role="dialog">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h2>Indent report search</h2>
					<button type="button" class="close" id="close" data-dismiss="modal">&times;</button>

				</div>
				<div class="modal-body">
				
					<div id="productQualityDivId" style="overflow: scroll"></div>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>

	<jsp:include page="/WEB-INF/jsp/views/header/footer.jsp"></jsp:include>

	
	</div>
	</div>

	<!-- <div class="loading">Loading&#8230;</div> -->

	<script src="assets/libs/jquery/dist/jquery.min.js"></script>
	<script src="assets/libs/bootstrap/dist/js/bootstrap.min.js"></script>
	<script
		src="assets/libs/perfect-scrollbar/dist/perfect-scrollbar.jquery.min.js"></script>
	<script src="dist/js/custom.min.js"></script>



	<!-- JS-Table data download -->

	<script type="text/javascript"
		src="https://cdn.datatables.net/1.13.4/js/jquery.dataTables.min.js"></script>
	<script type="text/javascript"
		src="https://cdn.datatables.net/buttons/2.3.2/js/dataTables.buttons.min.js"></script>
	<script type="text/javascript"
		src="https://cdnjs.cloudflare.com/ajax/libs/jszip/3.10.1/jszip.min.js"></script>
	<script type="text/javascript"
		src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.53/pdfmake.min.js"></script>
	<script type="text/javascript"
		src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.53/vfs_fonts.js"></script>
	<script type="text/javascript"
		src="https://cdn.datatables.net/buttons/2.3.2/js/buttons.html5.min.js"></script>

	<!-- 
<script type="text/javascript" src=""></script>
<script type="text/javascript" src=""></script>
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


-->
	<!-- <script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.53/pdfmake.min.js"></script>
     <script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.53/vfs_fonts.js"></script> 
     <script src="https://cdn.datatables.net/buttons/1.5.6/js/buttons.print.min.js"></script> -->


	<script type="text/javascript">
		history.pushState(null, null, location.href);
		window.onpopstate = function() {
			history.go(1);
		};
	</script>
	<script type="text/javascript">
		function detailsReport(data) {
			console.log(data)
		}
	</script>



	<script>
		const currentDate = new Date();

		// Get the current month as a string
		const currentMonth = currentDate.toLocaleString('default', {
			month : 'long'
		});

		// Find the option element with the corresponding value and set it as selected
		const monthSelect = document.getElementById("Month");
		for (let i = 0; i < monthSelect.options.length; i++) {
			if (monthSelect.options[i].value === currentMonth) {
				monthSelect.options[i].selected = true;
				break;
			}
		}

		var Year = document.getElementById("Year");
		var currentYear = new Date().getFullYear();

		
		var currentYearExists = false;
		for (var i = 0; i < Year.options.length; i++) {
		    if (Year.options[i].value === currentYear.toString()) {
		        currentYearExists = true;
		        break;
		    }
		}

		// If the current year doesn't exist, add it to the dropdown
		if (!currentYearExists) {
		    var option = document.createElement("option");
		    option.value = currentYear;
		    option.text = currentYear;
		    option.selected = true;
		    Year.appendChild(option);
		}


	</script>




	<script type="text/javascript">
		$(document)
				.ready(
						function() {

										var table = $('#example')
									.DataTable(
											{

												dom : 'Bfrtip',
												buttons : [ {
													extend : 'excelHtml5',
													text : '<i class="fa fa-file-excel-o"> Export</i>',
													titleAttr : 'Excel',
													title : 'Indent Report'
												} ],

												"pageLength" : 10,
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
																	function(
																			colIdx) {
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
																							api
																									.column(
																											colIdx)
																									.search(
																											this.value != '' ? regexr
																													.replace(
																															'{search}',
																															'((('
																																	+ this.value
																																	+ ')))')
																													: '',
																											this.value != '',
																											this.value == '')
																									.draw();

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

							$(".btn-search").on("click", function() {
								var Month = $('#Month').val();
								var Year = $('#Year').val();

								// Send an AJAX request
								$.ajax({
									type : "GET",
									url : "getreportbyid",
									data : {
										Month : Month,
										Year : Year
									},
									success : function(response) {
										table.clear().draw();
										data = jQuery.parseJSON(response);
										table.clear().draw();
										table.rows.add(data).draw();
										// alert("No data available for the selected month.");
 $('#totalQty').text(Math.floor(updateColumnData(4)));
    $('#totalBuyerQty').text(Math.floor(updateColumnData(5)));
    $('#unitPrice').text(updateColumnData(6));
    $('#unitValue').text(updateColumnData(9));
										var message = response.message;
										//alert(message); 

									},
									error : function(xhr, status, error) {

										console.error(error);
									}
								});
							});

							$('tbody')
									.on(
											'click',
											'tr',
											function() {
												var rowdata = table.row(this)
														.data();
												// alert( 'You clicked on '+data[1]+'\'s row' )
												$
														.ajax({
															type : "GET",
															url : "ABMHistoryDetailsByID",
															data : "EmployeeCode="
																	+ rowdata[1],
															success : function(
																	response) {
																// alert();
																data = jQuery
																		.parseJSON(response)
																var productQualityDetails = "";
																productQualityDetails += '<div id="producttable" ><table id="data" class="table table-striped table-bordered table-hover" > <thead><tr>'

																		+ '<th>Document Number</th>'
																		+ ' <th>Cost Center</th>'

																		+ ' <th>Designation</th>'
																		+ ' <th>Product</th>'
																		+ ' <th>City</th>'
																		+ ' <th>State</th>'
																		+ ' <th>Region</th>'
																		+ ' <th>Change By</th>'
																		+ ' <th>Change On</th>'

																		+ ' </tr> </thead> <tbody class="customtable">';

																var array = [];
																for (var i = 0; i < data.length; i++) {

																	if (data[i][6] == 1) {
																		var status = "Working";
																	} else {
																		var status = "Not Working";
																	}
																	productQualityDetails += '<tr><td>'

																			+ data[i][0]
																			+ '<input name="empid' + i + '" id="empid' + i + '" type="hidden" value="' + data[i][0] + '"></td><td>'
																			+ data[i][1]
																			+ '<input name="empname' + i + '" id="empname' + i + '" type="hidden" value="' + data[i][1] + '"></td><td>'
																			+ data[i][2]
																			+ '<input name="designation' + i + '" id="designation' + i + '" type="hidden" value="' + data[i][2] + '"></td><td>'
																			+ data[i][3]
																			+ '<input name="department' + i + '" id="department' + i + '" type="hidden" value="' + data[i][3] + '"></td><td>'
																			+ data[i][4]
																			+ '<input name="email' + i + '" id="email' + i + '" type="hidden" value="' + data[i][5] + '"></td><td>'
																			+ data[i][5]

																			+ '<input name="city' + i + '" id="city' + i + '" type="hidden" value="' + data[i][4] + '"></td><td>'

																			+ data[i][6]
																			+ '<input name="city' + i + '" id="city' + i + '" type="hidden" value="' + data[i][4] + '"></td><td>'

																			+ data[i][7]
																			+ '<input name="city' + i + '" id="city' + i + '" type="hidden" value="' + data[i][4] + '"></td><td>'

																			+ data[i][8]
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
																$(".loading")
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

																$(".loading")
																		.hide();
															}
														});
											});
						});
		
		
		function updateColumnData(columnIndex) {
			let totalSum = 0;
		     var table = $('#example').DataTable();
		    table.column(columnIndex).data().each(function (value, index) {
		    	if(typeof value == 'string'){
		    	value = value.replaceAll(',','').trim();
		    	console.log('murali total cal',Number(value));
		    	}
		    	totalSum = totalSum + Number(value);	        	
		    });  
		    // Redraw the table to reflect the changes
		    table.draw();
		    return totalSum.toFixed(2);
		}
	</script>
	
	<script>
$(document).ready(function() {
	var Month = $('#Month').val();
	var Year = $('#Year').val();

	
	$.ajax({
		type : "GET",
		url : "getreportbyid",
		data : {
			Month : Month,
			Year : Year
		},
		success : function(response) {
			table.clear().draw();
			data = jQuery.parseJSON(response);
			table.clear().draw();
			table.rows.add(data).draw();
			// alert("No data available for the selected month.");
 // Update footer with the result of updateColumnData
    $('#totalQty').text(Math.floor(updateColumnData(4)));
    $('#totalBuyerQty').text(Math.floor(updateColumnData(5)));
    $('#unitPrice').text(updateColumnData(6));
    $('#unitValue').text(updateColumnData(9));
			var message = response.message;
			//alert(message); 

		},
		error : function(xhr, status, error) {

			console.error(error);
		}
	});
	
	
    // Initialize DataTable
    var table = $('#example').DataTable();

    // Function to update column data
    function updateColumnData(columnIndex) {
        let totalSum = 0;
        table.column(columnIndex).data().each(function(value, index) {
            if (typeof value == 'string') {
                value = value.replaceAll(',', '').trim();
                console.log('murali total cal', Number(value));
            }
            totalSum = totalSum + Number(value);
        });
        // Redraw the table to reflect the changes
        table.draw();
        return totalSum.toFixed(2);
    }

    // Update footer with the result of updateColumnData
    $('#totalQty').text(Math.floor(updateColumnData(4)));
    $('#totalBuyerQty').text(Math.floor(updateColumnData(5)));
    $('#unitPrice').text(updateColumnData(6));
    $('#unitValue').text(updateColumnData(9));

});
</script>


</body>
</html>
