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
<title>ISCM Stationary</title>
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

.col-sm-3 {
    width: 25%;
    padding-bottom: 5px;
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

<style type="text/css">
.table-container {
    position: relative;
    overflow: hidden;
}

.table-container table {
    width: auto; /* Change this to auto to allow table to expand */
    border-collapse: collapse;
}

.table-container th,
.table-container td {
    padding: 8px;
    border: 1px solid #ccc;
}

.table-container thead {
    position: sticky;
    top: 0;
    background-color: #f0f0f0;
    z-index: 1;
}

.table-container tfoot {
    position: sticky;
    bottom: 0;
    background-color: #f0f0f0;
    z-index: 1;
}

/* Set the width of the first column */
.table-container th:first-child,
.table-container td:first-child {
    position: sticky;
    left: 0;
    z-index: 2;
    background-color: #fff;
    width: 350px; /* Adjust the width as needed */
}

/* Apply a class to the first cell in each row to adjust its width */
.table-container tbody td.first-cell {
    width: 350px; /* Adjust the width as needed */
}

</style>

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

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
						<h4 class="page-title display-5">Buyer Indent Validation & Confirmation</h4>
						<div class="ml-auto text-right">
							<nav aria-label="breadcrumb">
								<ol class="breadcrumb">
									<li class="breadcrumb-item"><a href="landPage">Home</a></li>
									<li class="breadcrumb-item"><a href="manageByAdmin">Management</a></li>
									<li class="breadcrumb-item active" aria-current="page">Buyer
										Indent Page</li>
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
            $("#CheckPasswordMatch").html("Passwords does not match!").css("color", "red");
       // $('input[type="submit"]').attr('disabled' , false);
           // $("#submitId").prop('disabled', true);
         //   $("#CheckPasswordMatch").css("color", "red");
        
        else
            $("#CheckPasswordMatch").html("Passwords match.").css("color", "red");
         //   $("#CheckPasswordMatch").css("color", "green");
//           
        
    }
    $(document).ready(function () {
       $("#cpwd").keyup(checkPasswordMatch);
    });
    
    function downloadtxt(id) {
   // 	alert(id);
        var checkstr =  confirm('are you sure you want to delete this?');
        if(checkstr == true){
          // do your code
          
          window.location.href = "deleteUser?userId="+id;
        }else{
        return false;
        }
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
			

				<div class="card">
					<div class="card-body">
						<h5 class="card-title"></h5>
						<div class="">

							<div id="zero_config_wrapper"
								class="dataTables_wrapper container-fluid dt-bootstrap4">
								<div class="row">

									<div class="col-sm-12 table-container">
									<div class="col-sm-3">
									<select id="Month" name="Month" class="form-control"
									aria-invalid="true" required="required" Style="width:97%;">
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
										
								</select>
									</div>
									<div class="col-sm-3">
									<select id="yearDropdown" class="form-control"
									aria-invalid="true" required="required" Style="width:97%;">
       								 <option value="">Select a year</option>
   									 </select>
									</div>
									<div class="col-sm-3">
									
											<button class="btn btn-warning" id="btn-search">search</button>
									</div>
										<div style="text-align: end;">
											<button class="btn btn-success" id="btn-submit">Submit</button>
											<button class="btn btn-primary" id="btn-sendtovendor">Send to Vendor</button>
										</div>

										<table id="example"
											class="table table-striped table-bordered display nowrap example"
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
  </style>
											
											
											<thead class="thead-light">
												<tr>
													<!-- <th scope="col"><b>SL.NO</b></th> -->
													<!-- <th scope="col" hidden><b>USER ID</b></th> -->
													<th scope="col" style="position:sticky;width:350px !important;" ><b>Description</b></th>
													<th scope="col"><b>Vendors</b></th>
													<th scope="col"><b>UOM</b></th>
													<th scope="col"><b>CC1100</b></th>
													<th scope="col"><b>CC1101</b></th>
													<th scope="col"><b>CC1102</b></th>
													<th scope="col"><b>CC1103</b></th>
													<th scope="col"><b>CC1200</b></th>
													<th scope="col"><b>CC1201</b></th>
													<th scope="col"><b>CC1202</b></th>
													<th scope="col"><b>CC1203</b></th>
													<th scope="col"><b>CC1204</b></th>
													<th scope="col"><b>CC1205</b></th>
													<th scope="col"><b>CC1206</b></th>
													<th scope="col"><b>CC1207</b></th>
													<th scope="col"><b>CC1209</b></th>
													<th scope="col"><b>CC1230</b></th>
													<th scope="col"><b>CC1231</b></th>
													<th scope="col"><b>CC1232</b></th>
													<th scope="col"><b>CC1233</b></th>
													<th scope="col"><b>CC1234</b></th>
													<th scope="col"><b>CC1235</b></th>
													<th scope="col"><b>CC1300</b></th>
													<th scope="col"><b>CC1301</b></th>
													<th scope="col"><b>CC1302</b></th>
													<th scope="col"><b>CC1303</b></th>
													<th scope="col"><b>CC1305</b></th>
													<th scope="col"><b>CC1313</b></th>
													<th scope="col"><b>CC1320</b></th>
													<th scope="col"><b>CC1321</b></th>
													<th scope="col"><b>CC1322</b></th>
													<th scope="col"><b>CC1323</b></th>
													<th scope="col"><b>CC1330</b></th>
													<th scope="col"><b>CC1331</b></th>
													<th scope="col"><b>CC1332</b></th>
													<th scope="col"><b>CC1333</b></th>
													<th scope="col"><b>CC1334</b></th>
													<th scope="col"><b>CC1335</b></th>
													<th scope="col"><b>CC1337</b></th>
													<th scope="col"><b>CC1338</b></th>
													<th scope="col"><b>CC1340</b></th>
													<th scope="col"><b>CC1380</b></th>
													<th scope="col"><b>CC1400</b></th>
													<th scope="col"><b>CC1401</b></th>
													<th scope="col"><b>CC1402</b></th>
													<th scope="col"><b>CC1406</b></th>
													<th scope="col"><b>CC1500</b></th>
													<th scope="col"><b>CC1501</b></th>
													<th scope="col"><b>CC1502</b></th>
													<th scope="col"><b>CC1503</b></th>
													<th scope="col"><b>CC1504</b></th>
													<th scope="col"><b>CC1506</b></th>
													<th scope="col"><b>CC1515</b></th>
													<th scope="col"><b>CC1520</b></th>
													<th scope="col"><b>CC1521</b></th>
													<th scope="col"><b>CC1522</b></th>
													<th scope="col"><b>CC1523</b></th>
													<th scope="col"><b>CC1524</b></th>
													<th scope="col"><b>CC1525</b></th>
													<th scope="col"><b>CC1528</b></th>
													<th scope="col"><b>CC1529</b></th>
													<th scope="col"><b>CC1540</b></th>
													<th scope="col"><b>CC1542</b></th>
													<th scope="col"><b>CC1544</b></th>
													<th scope="col"><b>CC1545</b></th>
													<th scope="col"><b>CC1546</b></th>
													<th scope="col"><b>CC1547</b></th>
													<th scope="col"><b>CC1549</b></th>
													<th scope="col"><b>CC1551</b></th>
													<th scope="col"><b>CC1552</b></th>
													<th scope="col"><b>CC1554</b></th>
													<th scope="col"><b>CC1555</b></th>
													<th scope="col"><b>CC1557</b></th>
													<th scope="col"><b>CC1558</b></th>
													<th scope="col"><b>CC1559</b></th>
													<th scope="col"><b>CC7646</b></th>
													<th scope="col"><b>User Qty</b></th>
													<th scope="col"><b>MOQ Qty</b></th>
													<th scope="col"><b>Total Qty</b></th>
													<th scope="col"><b>Unit Price(RS)</b></th>
													<th scope="col"><b>MOQ Val(RS)</b></th>
													<th scope="col"><b>Total Val</b></th>
													<th scope="col"><b>Stock At TMT (QTY)</b></th>
													<th scope="col"><b>STK Val(RS)</b></th>
												</tr>
											</thead>
											<tbody class="customtable">
												<c:forEach items="${BuyerList}" var="user" varStatus="loop">
													<tr>
														<%-- <td>${loop.index+1}</td> --%>
														<td class="first-cell" style="position:sticky;width:350px !important;">${user[0]}</td>
														<td>${user[1]}</td>
														<td>${user[2]}</td>
														<td class="editable-cell">${user[3]}</td>
														<td class="editable-cell">${user[4]}</td>
														<td class="editable-cell">${user[5]}</td>
														<td class="editable-cell">${user[6]}</td>
														<td class="editable-cell">${user[7]}</td>
														<td class="editable-cell">${user[8]}</td>
														<td class="editable-cell">${user[9]}</td>
														<td class="editable-cell">${user[10]}</td>
														<td class="editable-cell">${user[11]}</td>
														<td class="editable-cell">${user[12]}</td>
														<td class="editable-cell">${user[13]}</td>
														<td class="editable-cell">${user[14]}</td>
														<td class="editable-cell">${user[15]}</td>
														<td class="editable-cell">${user[16]}</td>
														<td class="editable-cell">${user[17]}</td>
														<td class="editable-cell">${user[18]}</td>
														<td class="editable-cell">${user[19]}</td>
														<td class="editable-cell">${user[20]}</td>
														<td class="editable-cell">${user[21]}</td>
														<td class="editable-cell">${user[22]}</td>
														<td class="editable-cell">${user[23]}</td>
														<td class="editable-cell">${user[24]}</td>
														<td class="editable-cell">${user[25]}</td>
														<td class="editable-cell">${user[26]}</td>
														<td class="editable-cell">${user[27]}</td>
														<td class="editable-cell">${user[28]}</td>
														<td class="editable-cell">${user[29]}</td>
														<td class="editable-cell">${user[30]}</td>
														<td class="editable-cell">${user[31]}</td>
														<td class="editable-cell">${user[32]}</td>
														<td class="editable-cell">${user[33]}</td>
														<td class="editable-cell">${user[34]}</td>
														<td class="editable-cell">${user[35]}</td>
														<td class="editable-cell">${user[36]}</td>
														<td class="editable-cell">${user[37]}</td>
														<td class="editable-cell">${user[38]}</td>
														<td class="editable-cell">${user[39]}</td>
														<td class="editable-cell">${user[40]}</td>
														<td class="editable-cell">${user[41]}</td>
														<td class="editable-cell">${user[42]}</td>
														<td class="editable-cell">${user[43]}</td>
														<td class="editable-cell">${user[44]}</td>
														<td class="editable-cell">${user[45]}</td>
														<td class="editable-cell">${user[46]}</td>
														<td class="editable-cell">${user[47]}</td>
														<td class="editable-cell">${user[48]}</td>
														<td class="editable-cell">${user[49]}</td>
														<td class="editable-cell">${user[50]}</td>
														<td class="editable-cell">${user[51]}</td>
														<td class="editable-cell">${user[52]}</td>
														<td class="editable-cell">${user[53]}</td>
														<td class="editable-cell">${user[54]}</td>
														<td class="editable-cell">${user[55]}</td>
														<td class="editable-cell">${user[56]}</td>
														<td class="editable-cell">${user[57]}</td>
														<td class="editable-cell">${user[58]}</td>
														<td class="editable-cell">${user[59]}</td>
														<td class="editable-cell">${user[60]}</td>
														<td class="editable-cell">${user[61]}</td>
														<td class="editable-cell">${user[62]}</td>
														<td class="editable-cell">${user[63]}</td>
														<td class="editable-cell">${user[64]}</td>
														<td class="editable-cell">${user[65]}</td>
														<td class="editable-cell">${user[66]}</td>
														<td class="editable-cell">${user[67]}</td>
														<td class="editable-cell">${user[68]}</td>
														<td class="editable-cell">${user[69]}</td>
														<td class="editable-cell">${user[70]}</td>
														<td class="editable-cell">${user[71]}</td>
														<td class="editable-cell">${user[72]}</td>
														<td class="editable-cell">${user[73]}</td>
														<td class="editable-cell">${user[74]}</td>
														<td class="editable-cell">${user[75]}</td>
														<td>
															 <%-- <!-- user qty --> <div class="moq-qty" data-row="${loop.index}" data-col="79" 
														contenteditable="true">${user[79]}</div> --%>
														</td>

														<td class="editable-cell remove">${user[77]}</td>
														<td></td>
														<td>${user[76]}</td>
														<td>${user[78]}</td>
														
														<td></td>
														<td>${user[79]}</td>
														<td>${user[80]}</td>

													</tr>

												</c:forEach>
											</tbody>
											<tfoot>

												<tr>

													<th>Total</th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>
													<th></th>

												</tr>
												<c:forEach items="${FooterList}" var="foot" varStatus="loop">

													<tr class="footer-row reassign-footer">
															<th ><div
																	class="sticky-col first-col">${foot[0]}</div></th>
														<th></th>
														<th></th>
														<th class="compare-value" data-threshold="${foot[1]}">${foot[1]}</th>
														<th class="compare-value" data-threshold="${foot[2]}">${foot[2]}</th>
														<th class="compare-value" data-threshold="${foot[3]}">${foot[3]}</th>
														<th>${foot[4]}</th>
														<th>${foot[5]}</th>
														<th>${foot[6]}</th>
														<th>${foot[7]}</th>
														<th>${foot[8]}</th>
														<th>${foot[9]}</th>
														<th>${foot[10]}</th>
														<th>${foot[11]}</th>
														<th>${foot[12]}</th>
														<th>${foot[13]}</th>
														<th>${foot[14]}</th>
														<th>${foot[15]}</th>
														<th>${foot[16]}</th>
														<th>${foot[17]}</th>
														<th>${foot[18]}</th>
														<th>${foot[19]}</th>
														<th>${foot[20]}</th>
														<th>${foot[21]}</th>
														<th>${foot[22]}</th>
														<th>${foot[23]}</th>
														<th>${foot[24]}</th>
														<th>${foot[25]}</th>
														<th>${foot[26]}</th>
														<th>${foot[27]}</th>
														<th>${foot[28]}</th>
														<th>${foot[29]}</th>
														<th>${foot[30]}</th>
														<th>${foot[31]}</th>
														<th>${foot[32]}</th>
														<th>${foot[33]}</th>
														<th>${foot[34]}</th>
														<th>${foot[35]}</th>
														<th>${foot[36]}</th>
														<th>${foot[37]}</th>
														<th>${foot[38]}</th>
														<th>${foot[39]}</th>
														<th>${foot[40]}</th>
														<th>${foot[41]}</th>
														<th>${foot[42]}</th>
														<th>${foot[43]}</th>
														<th>${foot[44]}</th>
														<th>${foot[45]}</th>
														<th>${foot[46]}</th>
														<th>${foot[47]}</th>
														<th>${foot[48]}</th>
														<th>${foot[49]}</th>
														<th>${foot[50]}</th>
														<th>${foot[51]}</th>
														<th>${foot[52]}</th>
														<th>${foot[53]}</th>
														<th>${foot[54]}</th>
														<th>${foot[55]}</th>
														<th>${foot[56]}</th>
														<th>${foot[57]}</th>
														<th>${foot[58]}</th>
														<th>${foot[59]}</th>
														<th>${foot[60]}</th>
														<th>${foot[61]}</th>
														<th>${foot[62]}</th>
														<th>${foot[63]}</th>
														<th>${foot[64]}</th>
														<th>${foot[65]}</th>
														<th>${foot[66]}</th>
														<th>${foot[67]}</th>
														<th>${foot[68]}</th>
														<th>${foot[69]}</th>
														<th>${foot[70]}</th>
														<th>${foot[71]}</th>
														<th>${foot[72]}</th>
														<th>${foot[73]}</th>
														<th></th>
														<th></th>
														<th></th>
														<th></th>
														<th></th>
														<th></th>
														<th></th>
														<th></th>
													</tr>
												</c:forEach>
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


	<jsp:include page="/WEB-INF/jsp/views/header/footer.jsp"></jsp:include>

<script src="assets/libs/jquery/dist/jquery.min.js"></script>
	<script src="assets/libs/bootstrap/dist/js/bootstrap.min.js"></script>
	<script
		src="assets/libs/perfect-scrollbar/dist/perfect-scrollbar.jquery.min.js"></script>
	<script src="dist/js/custom.min.js"></script>
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

	<script type="text/javascript">
		history.pushState(null, null, location.href);
		window.onpopstate = function() {
			history.go(1);
		};
	</script>

	<script type="text/javascript">


$(document).ready(function () {
		 const currentDate = new Date();

	    // Get the current month as a string
	    const currentMonth = currentDate.toLocaleString('default', { month: 'long' });

	    // Find the option element with the corresponding value and set it as selected
	    const monthSelect = document.getElementById("Month");
	    for (let i = 0; i < monthSelect.options.length; i++) {
	        if (monthSelect.options[i].value === currentMonth) {
	            monthSelect.options[i].selected = true;
	            break;
	        }
	    }
	
	 var currentYear = new Date().getFullYear();
     var yearDropdown = document.getElementById("yearDropdown");

     for (var i = currentYear; i >= currentYear - 10; i--) {
         var option = document.createElement("option");
         option.value = i;
         option.text = i;
         yearDropdown.appendChild(option);
     }
  
    var table = $('#example').DataTable({
    	
            dom: 'Bfrtip',
            buttons: [
                {
                  extend: 'excelHtml5',
                  text: '<i class="fa fa-file-excel-o"></i>',
                  titleAttr: 'Excel',
                  title: 'Buyer List',
                  customize: function(xlsx) {
                      var sheet = xlsx.xl.worksheets['sheet1.xml'];
                      var tfootRows = $('#example tfoot tr');
                      tfootRows.each(function(index, row) {
                          var rowXML = generateRowXML(row);
                          $('sheetData', sheet).append(rowXML);
                      });
                  }
              },
          ],
          initComplete: function () {
              compareFooterValues();
          },
    	/* "pageLength" : 10, */
    	paging: false,
    	scrollX: true,
    	searching: false,
        scrollCollapse: true,
        scrollY: '300px',
        
    	    
    });
    
	$("#btn-search").on("click", function() {
		var Year=$('#yearDropdown').val();
		var Month=$('#Month').val();
		$.ajax({
			type : "Get",
			url : "getBuyerFooterListBasedOnFilter",
			contentType : 'application/json',
			data : "Year="+Year+"&Month="+Month,

			success : function(response) {
				 var footerData = jQuery.parseJSON(response);
			        var footerCells = table.columns().footer(); // Get all footer cells

			        footerCells.each(function(column, footerCell) {
			            var rowData = footerData[column]; // Get the corresponding row data
			            console.log(rowData,'rowData');
		            	console.log(rowData[0],'rowData[0]');
			            if (rowData && rowData.length > 0) {
			            	
			                var cellContent = rowData[0]; // Assuming the data you want is in the first element of the row data
			                $(footerCell).html(cellContent);
			            }
			        });
			}
		});
		
		$.ajax({
			type : "Get",
			url : "getBuyerIndentListBasedOnFilter",
			contentType : 'application/json',
			data : "Year="+Year+"&Month="+Month,

			success : function(response) {
				data = jQuery.parseJSON(response);
				//console.log(data)
				
				table.clear().draw();
				table.rows.add(data).draw();
				 
				  var startColumnIndex = 3; 
		      	    var endColumnIndex = 75; 
		      	    var targetColumnIndex = 76;
		      	    table.rows().every(function(rowIdx, tableLoop, rowLoop) {
		      	      var row = this;
		      	      var sum = 0;

		      	      for (var i = startColumnIndex; i <= endColumnIndex; i++) {
		      	        var cellValue = parseFloat(row.data()[i]);
		      	        sum += isNaN(cellValue) ? 0 : cellValue;
		      	      }
		      	      row.cell(rowIdx, targetColumnIndex).data(sum.toFixed(2));
		      	    }); 
		      	    
		      	 
		      	    var startColumnIndex = 76; 
		      	    var endColumnIndex = 77; 
		      	    var targetColumnIndex = 78;
		      	    table.rows().every(function(rowIdx, tableLoop, rowLoop) {
		      	    	 var row = this;
		      	    	  var userQTY = parseFloat(row.data()[startColumnIndex]);
		      	    	  var moqQTY = parseFloat(row.data()[endColumnIndex]);
		      	    	  console.log(userQTY,'userQTY');	  
		      	    	  console.log(moqQTY,'moqQTY');
		      	    	moqQTY= isNaN(moqQTY) ?0:moqQTY;
		      	    	  var largerValue = Math.max(userQTY, moqQTY);

		      	    	  if (isNaN(largerValue)) {
		      	    	    largerValue = 0; 
		      	    	  }

		      	    	  row.cell(rowIdx, targetColumnIndex).data(largerValue.toFixed(2));
		      	    });
		      	    
		      	    //-------------------------------------------------
		      	    
		      	     //Calculationof total-------------------
		      	    var UserQtyIndec = 76; 
		      	    var MOQQtyIndex = 77; 
		      	   // var targetColumnIndex = 78; 
		      	    table.rows().every(function(rowIdx, tableLoop, rowLoop) {
		      	    	 var row = this;
		      	    	  var userQTY = parseFloat(row.data()[UserQtyIndec]);
		      	    	  var moqQTY = parseFloat(row.data()[MOQQtyIndex]);
		      	    	  var largerValue = Math.max(userQTY, moqQTY);
		      	    	 
		      	    	  if(largerValue == moqQTY) {
		      	    		  var Moq = 77;
		        	    	    var price = 79; 
		        	    	    table.rows().every(function(rowIdx, tableLoop, rowLoop) {
		        	    	      var row = this;
		        	    	      var MoqQTy = parseFloat(row.data()[Moq]);
		        	    	      var Prices = parseFloat(row.data()[price]);
		        	    	      var product = isNaN(MoqQTy) || isNaN(Prices) ? 0 : (MoqQTy * Prices);
		         	    	     row.cell(rowIdx, 81).data(product.toFixed(2));
		         	    	     row.cell(rowIdx, 80).data(product.toFixed(2));
		        	    	     
		        	    	    }); 
							// Remaining Qty from moq calculation
		        	    		var user = 76;
		        	    		  var Moq = 77;
		          	    	    var price = 79; 
		          	    	    table.rows().every(function(rowIdx, tableLoop, rowLoop) {
		          	    	      var row = this;
		          	    	      var userQRY = parseFloat(row.data()[user]);
		          	    	      var MoqQTy = parseFloat(row.data()[Moq]);
		          	    	      var Prices = parseFloat(row.data()[price]);
		          	    	      var productQTY = isNaN(MoqQTy) || isNaN(userQRY) ? 0 : (MoqQTy - userQRY);
		          	    	    var Value = 0;
		          	    	  if (productQTY > 0 && !isNaN(productQTY) && !isNaN(Prices)) {
		          	    	    Value = productQTY * Prices;
		          	    	  	row.cell(rowIdx, 82).data(productQTY);
		        	    	    row.cell(rowIdx, 83).data(Value.toFixed(2));
		          	    	  }
		          	    	     // var Value = productQTY > 0 ||isNaN(productQTY) || isNaN(Prices) ? 0 : (productQTY * Prices);
		           	    	   
		          	    	     
		          	    	    }); 
		        	    	     
		      	    	  }else if(largerValue == userQTY){
		        	    		var user = 78;
		          	    	    var  price= 79; 
		        	    	    
		          	    	    table.rows().every(function(rowIdx, tableLoop, rowLoop) {
		          	    	      var row = this;
		          	    	      var UserQty = parseFloat(row.data()[user]);
		          	    	      var Price = parseFloat(row.data()[price]);
		          	    	      var product = isNaN(UserQty) || isNaN(Price) ? 0 : (UserQty * Price);

		          	    	      row.cell(rowIdx, 81).data(product.toFixed(2));
		          	    	    }); 
		          	    	 
		          	    	  }
		      	    	

		      	    });
		      	  var columnIndices = [76, 77, 78,80,81,82,83]; // Array of column indices to calculate the sums
				  var columnTotals = Array(columnIndices.length).fill(0); // Array to store the running totals for each column

				  // Iterate over each column index
				  columnIndices.forEach(function(columnIndex) {
				    table.column(columnIndex, { search: 'applied' }).data().each(function(cellData) {
				      var cellValue = parseFloat(cellData);
				      if (!isNaN(cellValue)) {
				        columnTotals[columnIndices.indexOf(columnIndex)] += cellValue;
				      }
				    });

				    // Create a footer element for the current column and assign the calculated total to it
				    var footerNode = table.column(columnIndex).footer();
				    footerNode.innerHTML = columnTotals[columnIndices.indexOf(columnIndex)].toFixed(2);
				  });
		      	    //-------------------------------------------------
		      	    
		      	    // -------------Footer calculations on change Start -----------------
		      
				  
		      	  
			}
        });
		
		
		
		
	});
   // table.column(0).width('350px');
   
    function compareFooterValues() {
        var $footerCells = $('#example tfoot .compare-value');
        $footerCells.each(function() {
            var cellValue = parseFloat($(this).text());
            var threshold = parseFloat($(this).data('threshold'));
            console.log(cellValue,'cellValue');
            console.log(threshold,'threshold');
            if (!isNaN(cellValue) && !isNaN(threshold)) {
                if (cellValue > threshold) {
                    $(this).css('background-color', 'green');
                } else {
                    $(this).css('background-color', 'red');
                }
            }
        });
    }
    

    // to dissable zero values edit
    $('.editable-cell').each(function() {
        var cellContent = $(this).text().trim();
        if (cellContent === '' || parseFloat(cellContent) === 0) {
         if ($(this).hasClass('editable-cell')) {
        	 if(!$(this).hasClass('remove')){
          $(this).removeClass('editable-cell');}}
         }
      });
  
   

   
    
   // table.column(-1).visible(false);
    
    $("#btn-submit").on("click", function() {
        var data = []; // An array to store the data from the table

        var columns = [];

        //table.column(-1).visible(true);
        $("#example thead th").each(function() {
            var columnName = $(this).text();
              if (columnName.startsWith("CC")) {
                columnName = columnName;
            } 
            columns.push(columnName);
            console.log(columns)
        });

        // Function to extract data from each page
        function extractDataFromPage(pageIndex) {
            table.page(pageIndex).draw('page'); // Go to the specified page
            $("#example tbody tr").each(function() {
                var rowData = {};
                $(this).find("td").each(function(index) {
                	
                    rowData[columns[index]] = $(this).text();
                });
                data.push(rowData);
               // console.log('data',data)
            });
        }

        var table1 = $("#example").DataTable(); // Get the DataTable instance
        var numPages = table1.page.info().pages; // Get the total number of pages

        for (var i = 0; i < numPages; i++) {
            extractDataFromPage(i);
        }

        //table.column(-1).visible(false);
        

        const BuyerList = [];

        data.forEach(item => {
            for (const key in item) {
            	 console.log(key,'key');
                 console.log(item,'item');
                 console.log(item[key],'item[key]');
              
               // if (/^\d{4}$/.test(key)) {
                    const documentNumber = item["Documnet"];
                    const costcenter = key;
                    const description = item["Description"];
                    const quantity = item[key];
                    const unitPrice = item["Unit Price(RS)"];
                    const MoqQty = item["MOQ Qty"];
                    const MOQValue = item["MOQ Val(RS)"];
                    const TMTQty = item["Stock At TMT (QTY)"];
                    const TMTValue = item["STK Val(RS)"];
 
                    
                   if(parseInt(quantity)>0){
                	   console.log(TMTValue,'TMTValue');
                	   //console.log(quantity1,'quantity1');
                    const newItem = {
                        "documnet": documentNumber,
                        "costcenter": costcenter,
                        "description": description,
                        "quantity": quantity=="0.00"?"0":parseInt(quantity),
                        "unitPrice": unitPrice,
                        "moqQty":MoqQty=="0.00"?"0":MoqQty,
                        "moqValue":MOQValue,
                        "balanceTMTQty":TMTQty=="0.00"?"0":TMTQty,
                        "balanceTMTValue":TMTValue
                    };

                    //console.log(newItem,'newItem');
                    
                    BuyerList.push(newItem);
                    console.log(BuyerList,'BuyerList');
                    }
              //  }
            }
            
        });
        console.log(BuyerList,'BuyerList');
        $.ajax({
			type : "POST",
			url : "BuyerIndentUpdateSave",
			contentType : 'application/json',
			data : JSON.stringify(BuyerList),

			success : function(response) {
				Swal.fire({
					icon : 'success',
					title : response,
					showCloseButton : false,
					focusConfirm : true,
				});
				//location.href="BuyerIndentPage";
			}
        });
        
      //  console.log(data);
      //  console.log(BuyerList,'convertedData');  // You can check the data in the browser console or process it further as needed
    });

 
    function generateRowXML(row) {
        var cells = $(row).find('th, td');
        var rowXML = '<row>';
        cells.each(function(index, cell) {
            var cellValue = $(cell).text();
            rowXML += '<c t="inlineStr"><is><t>' + cellValue + '</t></is></c>';
        });
        rowXML += '</row>';
        return rowXML;
    }

    
     
      $('#example').on('click', 'td', function() {
    	  if ($(this).hasClass('editable-cell')) {
    	  var cell = table.cell(this);
    	    var originalValue = cell.data();

    	    var columnIndex = cell.index().column;
    	    var columnHeaderWidth = $(table.column(columnIndex).header()).width();

    	    var input = $('<input type="text" id="input-text" class="input-text">');
    	   // input.css('width', columnHeaderWidth); // Set input width to match column header
 			setInputWidth(input, columnHeaderWidth);
    	    input.val(originalValue);

    	    input.on('blur', function() {
    	      var newValue = input.val();
    	      cell.data(newValue);
    	      setInputWidth(input, columnHeaderWidth);
    	    });

    	    $(this).html(input);
  	    	 
  	      
    	    input.focus();
    	    input.select();

    	    input.get(0).contentEditable = true;
    	  }
    	    input.on('blur', function() {
      	      var newValue = input.val();
      	    if(newValue){
      	    var startColumnIndex = 3; 
      	    var endColumnIndex = 75; 
      	    var targetColumnIndex = 76;
      	    table.rows().every(function(rowIdx, tableLoop, rowLoop) {
      	      var row = this;
      	      var sum = 0;

      	      for (var i = startColumnIndex; i <= endColumnIndex; i++) {
      	        var cellValue = parseFloat(row.data()[i]);
      	        sum += isNaN(cellValue) ? 0 : cellValue;
      	      }
      	      row.cell(rowIdx, targetColumnIndex).data(sum.toFixed(2));
      	    }); 
      	    
      	 
      	    var startColumnIndex = 76; 
      	    var endColumnIndex = 77; 
      	    var targetColumnIndex = 78;
      	    table.rows().every(function(rowIdx, tableLoop, rowLoop) {
      	    	 var row = this;
      	    	  var userQTY = parseFloat(row.data()[startColumnIndex]);
      	    	  var moqQTY = parseFloat(row.data()[endColumnIndex]);
      	    	  console.log(userQTY,'userQTY');	  
      	    	  console.log(moqQTY,'moqQTY');
      	    	moqQTY= isNaN(moqQTY) ?0:moqQTY;
      	    	  var largerValue = Math.max(userQTY, moqQTY);

      	    	  if (isNaN(largerValue)) {
      	    	    largerValue = 0; 
      	    	  }

      	    	  row.cell(rowIdx, targetColumnIndex).data(largerValue.toFixed(2));
      	    });
      	    
      	    //-------------------------------------------------
      	    
      	     //Calculationof total-------------------
      	    var UserQtyIndec = 76; 
      	    var MOQQtyIndex = 77; 
      	   // var targetColumnIndex = 78; 
      	    table.rows().every(function(rowIdx, tableLoop, rowLoop) {
      	    	 var row = this;
      	    	  var userQTY = parseFloat(row.data()[UserQtyIndec]);
      	    	  var moqQTY = parseFloat(row.data()[MOQQtyIndex]);
      	    	  var largerValue = Math.max(userQTY, moqQTY);
      	    	 
      	    	  if(largerValue == moqQTY) {
      	    		  var Moq = 77;
        	    	    var price = 79; 
        	    	    table.rows().every(function(rowIdx, tableLoop, rowLoop) {
        	    	      var row = this;
        	    	      var MoqQTy = parseFloat(row.data()[Moq]);
        	    	      var Prices = parseFloat(row.data()[price]);
        	    	      var product = isNaN(MoqQTy) || isNaN(Prices) ? 0 : (MoqQTy * Prices);
         	    	     row.cell(rowIdx, 81).data(product.toFixed(2));
         	    	     row.cell(rowIdx, 80).data(product.toFixed(2));
        	    	     
        	    	    }); 
					// Remaining Qty from moq calculation
        	    		var user = 76;
        	    		  var Moq = 77;
          	    	    var price = 79; 
          	    	    table.rows().every(function(rowIdx, tableLoop, rowLoop) {
          	    	      var row = this;
          	    	      var userQRY = parseFloat(row.data()[user]);
          	    	      var MoqQTy = parseFloat(row.data()[Moq]);
          	    	      var Prices = parseFloat(row.data()[price]);
          	    	      var productQTY = isNaN(MoqQTy) || isNaN(userQRY) ? 0 : (MoqQTy - userQRY);
          	    	    var Value = 0;
          	    	  if (productQTY > 0 && !isNaN(productQTY) && !isNaN(Prices)) {
          	    	    Value = productQTY * Prices;
          	    	  	row.cell(rowIdx, 82).data(productQTY);
        	    	    row.cell(rowIdx, 83).data(Value.toFixed(2));
          	    	  }
          	    	     // var Value = productQTY > 0 ||isNaN(productQTY) || isNaN(Prices) ? 0 : (productQTY * Prices);
           	    	   
          	    	     
          	    	    }); 
        	    	     
      	    	  }else if(largerValue == userQTY){
        	    		var user = 78;
          	    	    var  price= 79; 
        	    	    
          	    	    table.rows().every(function(rowIdx, tableLoop, rowLoop) {
          	    	      var row = this;
          	    	      var UserQty = parseFloat(row.data()[user]);
          	    	      var Price = parseFloat(row.data()[price]);
          	    	      var product = isNaN(UserQty) || isNaN(Price) ? 0 : (UserQty * Price);

          	    	      row.cell(rowIdx, 81).data(product.toFixed(2));
          	    	    }); 
          	    	 
          	    	  }


      	    });
      	    
      	    //-------------------------------------------------
      	    
      	    // -------------Footer calculations on change Start -----------------
      
		    
		  var columnIndices = [76, 77, 78,80,81,82,83]; // Array of column indices to calculate the sums
		  var columnTotals = Array(columnIndices.length).fill(0); // Array to store the running totals for each column

		  // Iterate over each column index
		  columnIndices.forEach(function(columnIndex) {
		    table.column(columnIndex, { search: 'applied' }).data().each(function(cellData) {
		      var cellValue = parseFloat(cellData);
		      if (!isNaN(cellValue)) {
		        columnTotals[columnIndices.indexOf(columnIndex)] += cellValue;
		      }
		    });

		    // Create a footer element for the current column and assign the calculated total to it
		    var footerNode = table.column(columnIndex).footer();
		    footerNode.innerHTML = columnTotals[columnIndices.indexOf(columnIndex)].toFixed(2);
		  });
      	    
      	    
      	    //----------------Footer calculations on change end -----------------
      	 
      	 }
      	    });

    });
   
    function setInputWidth(input, columnHeaderWidth) {
    	  var inputValue = input.val();
    	  var bodyValueWidth = getTextWidth(inputValue);
    	  var maxWidth = Math.max(columnHeaderWidth, bodyValueWidth);
    	  input.css('width', maxWidth);
    	}

    	function getTextWidth(text) {
    	  var element = $('<span>').text(text).css('display', 'none');
    	  $('body').append(element);
    	  var width = element.width();
    	  element.remove();
    	  return width;
    	}
    
    	 $("#btn-sendtovendor").on("click", function() {
    	    	var header = table.columns().header().toArray().map(function(th) {
    	            return $(th).text();
    	        });
    	        
    	        var data = table.rows().data().toArray();
    	        
    	        var payload = {
    	            header: removeColumns(header, [76, 77, 80, 82, 83]),
    	            data: removeColumnsFromData(data, [76, 77, 80, 82, 83])
    	        };
    			//console.log(JSON.stringify(payload),'data');
    		 	$.ajax({
    				type : "POST",
    				url : "sendToVendor",
    				contentType : 'application/json',
    				data : JSON.stringify(payload),

    				success : function(response) {
    					Swal.fire({
    						icon : 'success',
    						title : response,
    						showCloseButton : false,
    						focusConfirm : true,
    					});
    					//location.href="BuyerIndentPage";
    				}
    		    }); 
    		});
    	 
    	 function removeColumns(array, indices) {
    		    return array.filter(function(_, index) {
    		        return !indices.includes(index);
    		    });
    		}

    		function removeColumnsFromData(data, indices) {
    		    return data.map(function(row) {
    		        return removeColumns(row, indices);
    		    });
    		}
    var startColumnIndex = 3; // Starting column index
    var endColumnIndex = 75; // Ending column index
    var targetColumnIndex = 76; // Index of the column to assign the sum
    table.rows().every(function(rowIdx, tableLoop, rowLoop) {
      var row = this;
      var sum = 0;

      for (var i = startColumnIndex; i <= endColumnIndex; i++) {
        var cellValue = parseFloat(row.data()[i]);
        sum += isNaN(cellValue) ? 0 : cellValue;
      }
      row.cell(rowIdx, targetColumnIndex).data(sum.toFixed(2));
    });

    var startColumnIndex = 76; // Starting column index
    var endColumnIndex = 77; // Ending column index
    var targetColumnIndex = 78; // Index of the column to assign the sum
    table.rows().every(function(rowIdx, tableLoop, rowLoop) {
      var row = this;
      var sum = 0;

     /*  for (var i = startColumnIndex; i <= endColumnIndex; i++) {
        var cellValue = parseFloat(row.data()[i]);
        sum += isNaN(cellValue) ? 0 : cellValue;
      }
      row.cell(rowIdx, targetColumnIndex).data(sum.toFixed(2)); */
      
      var userQTY = parseFloat(row.data()[startColumnIndex]);
  	  var moqQTY = parseFloat(row.data()[endColumnIndex]);
  	 //console.log(moqQTY,'moqQTY');
  	//console.log(userQTY,'userQTY');
  	moqQTY= isNaN(moqQTY) ?0:moqQTY;
  	  var largerValue = Math.max(userQTY, moqQTY);

  	  if (isNaN(largerValue)) {
  	    largerValue = 0; 
  	  }

  	  row.cell(rowIdx, targetColumnIndex).data(largerValue.toFixed(2));
    });

    var colIndex1 = 77; // Column index of the first operand (column 77)
    var colIndex2 = 79; // Column index of the second operand (column 79)
    var targetColumnIndex = 80; // Index of the column to assign the product

    table.rows().every(function(rowIdx, tableLoop, rowLoop) {
      var row = this;
      var value1 = parseFloat(row.data()[colIndex1]);
      var value2 = parseFloat(row.data()[colIndex2]);
      var product = isNaN(value1) || isNaN(value2) ? 0 : (value1 * value2);

      row.cell(rowIdx, targetColumnIndex).data(product.toFixed(2));
    });
    
    var colIndex1 = 78; // Column index of the first operand (column 77)
    var colIndex2 = 79; // Column index of the second operand (column 79)
    var targetColumnIndex = 81; // Index of the column to assign the product

    table.rows().every(function(rowIdx, tableLoop, rowLoop) {
      var row = this;
      var value1 = parseFloat(row.data()[colIndex1]);
      var value2 = parseFloat(row.data()[colIndex2]);
      var product = isNaN(value1) || isNaN(value2) ? 0 : (value1 * value2);

      row.cell(rowIdx, targetColumnIndex).data(product.toFixed(2));
    });
    
    var columnIndex = 76; 
    var columnTotal = 0;

    table.column(columnIndex, { search: 'applied' }).data().each(function(cellData) {
      var cellValue = parseFloat(cellData);
      if (!isNaN(cellValue)) {
        columnTotal += cellValue;
      }
    });

    // Create a footer element and assign the calculated total to it
    var footerNode = table.column(columnIndex).footer();
    footerNode.innerHTML = columnTotal.toFixed(2);
    
    var columnIndex = 77; 
    var columnTotal = 0;

    table.column(columnIndex, { search: 'applied' }).data().each(function(cellData) {
      var cellValue = parseFloat(cellData);
      if (!isNaN(cellValue)) {
        columnTotal += cellValue;
      }
    });

    // Create a footer element and assign the calculated total to it
    var footerNode = table.column(columnIndex).footer();
    footerNode.innerHTML = columnTotal.toFixed(2);
    
    var columnIndex = 78; 
    var columnTotal = 0;

    table.column(columnIndex, { search: 'applied' }).data().each(function(cellData) {
      var cellValue = parseFloat(cellData);
      if (!isNaN(cellValue)) {
        columnTotal += cellValue;
      }
    });

    // Create a footer element and assign the calculated total to it
    var footerNode = table.column(columnIndex).footer();
    footerNode.innerHTML = columnTotal.toFixed(2);
    
    var columnIndex = 80; 
    var columnTotal = 0;

    table.column(columnIndex, { search: 'applied' }).data().each(function(cellData) {
      var cellValue = parseFloat(cellData);
      if (!isNaN(cellValue)) {
        columnTotal += cellValue;
      }
    });

    // Create a footer element and assign the calculated total to it
    var footerNode = table.column(columnIndex).footer();
    footerNode.innerHTML = columnTotal.toFixed(2);

    var columnIndex = 81 // Index of column 76 (Total Qty)
    var columnTotal = 0;

    table.column(columnIndex, { search: 'applied' }).data().each(function(cellData) {
      var cellValue = parseFloat(cellData);
      if (!isNaN(cellValue)) {
        columnTotal += cellValue;
      }
    });

    // Create a footer element and assign the calculated total to it
    var footerNode = table.column(columnIndex).footer();
    footerNode.innerHTML = columnTotal.toFixed(2);
    
    var columnIndex = 82;
    var columnTotal = 0;

    table.column(columnIndex, { search: 'applied' }).data().each(function(cellData) {
      var cellValue = parseFloat(cellData);
      if (!isNaN(cellValue)) {
        columnTotal += cellValue;
      }
    });

    // Create a footer element and assign the calculated total to it
    var footerNode = table.column(columnIndex).footer();
    footerNode.innerHTML = columnTotal.toFixed(2);
    
    var columnIndex = 83;
    var columnTotal = 0;

    table.column(columnIndex, { search: 'applied' }).data().each(function(cellData) {
      var cellValue = parseFloat(cellData);
      if (!isNaN(cellValue)) {
        columnTotal += cellValue;
      }
    });

    // Create a footer element and assign the calculated total to it
    var footerNode = table.column(columnIndex).footer();
    footerNode.innerHTML = columnTotal.toFixed(2);
	table.draw();
	 table.on('draw', function () {
	        compareFooterValues();
	    });


});
</script>
	<script src="dist/js/sweetalert2.min.js"></script>
		<link rel="stylesheet" href="dist/css/sweetalert2.min.css" /> 
</body>
</html>
