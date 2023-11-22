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
<title>ISCM Stationery</title>
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

    
.dataTables_scrollBody td:last-child {
  background-color: white;
}

/* set background color for the header of the fixed column */
.dataTables_scrollHead th:last-child {
  background-color: white;
}

/* fix the position of the first column */
.dataTables_scrollBody td:last-child,
.dataTables_scrollHead th:last-child ,{
  position: sticky;
  right: 0;
  z-index: 1000;
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
/* Customize the styles for the fixed columns */
.dataTables_wrapper .DTFC_LeftBodyWrapper {
    background-color: #f2f2f2; /* Background color for fixed columns */
    /* Add other CSS styles as needed */
}

/* Style the content in the fixed columns */
.dataTables_wrapper .DTFC_LeftBodyWrapper table.dataTable {
    width: auto; /* Set the width of the fixed columns table */
    /* Add other CSS styles as needed */
}

/* Style the header cells of the fixed columns */
.dataTables_wrapper .DTFC_LeftHeadWrapper table.dataTable thead th {
    background-color: #333; /* Header background color for fixed columns */
    color: white; /* Header text color for fixed columns */
    /* Add other CSS styles as needed */
}

/* Style the cells in the fixed columns body */
.dataTables_wrapper .DTFC_LeftBodyWrapper table.dataTable tbody td {
    /* Add your styles for the fixed columns' cells */
    /* You can use additional CSS selectors to target specific columns */
}

.headerStyles{
 width: 183.199px !important;
 background: rgb(39 150 203) !important;
 color: white !important;      
 font-weight: 600 !important;
 
	}
	.tbodyCustomColor {
	    color: black !important;
        font-weight: 600 !important;
	}
</style>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

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
		
		<jsp:include page="/WEB-INF/jsp/views/header/header.jsp"></jsp:include>
		<jsp:include page="/WEB-INF/jsp/views/header/sideBar.jsp"></jsp:include>
		<div class="page-wrapper">
			<!-- ============================================================== -->
			<!-- Bread crumb and right sidebar toggle -->
			<!-- ============================================================== -->
			<div class="page-breadcrumb">
				<div class="row">
					<div class="col-12 d-flex no-block align-items-center">
						<h4 class="page-title display-5">P O Entry list</h4>
						<div class="ml-auto text-right">
							<nav aria-label="breadcrumb">
								<ol class="breadcrumb">
									<li class="breadcrumb-item"><a href="landPage">Home</a></li>
									<li class="breadcrumb-item"><a href="manageByAdmin">Management</a></li>
									<li class="breadcrumb-item active" aria-current="page">
									P O Entry list</li>
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
				<script
					src="https://cdn.rawgit.com/rainabba/jquery-table2excel/1.1.0/dist/jquery.table2excel.min.js"></script>
				<script>

</script>
				<c:if test="${role == 'Buyer'}">
					<!-- Only show the page content if the user is an Indent Manager -->
					<div class="card">
						<div class="card-body">
							<h5 class="card-title"></h5>
							<div class="">
								<div id="zero_config_wrapper"
									class="dataTables_wrapper container-fluid dt-bootstrap4">
									<div style="text-align:end;margin-bottom:4px;">
									<button onclick="redirectToPoEntryCreation()" class="btn btn-primary">Add New PO Entry</button>
										</div>
									<div class="row">
										<div class="col-sm-12">
  										<table id="example"
											class="table table-striped table-bordered display nowrap"
											style="width: 100%;">
											<thead class="thead-light">
												<tr>
												  <th class="headerStyles" scope="col"><b>Cost Center</b></th>
													<th class="headerStyles" scope="col"><b>Year</b></th>
													<th class="headerStyles" scope="col"><b>Month</b></th>
													<th class="headerStyles" scope="col"><b>P O Amount</b></th>
													<th class="headerStyles" scope="col"><b>Created By</b></th>
													<th class="headerStyles" scope="col"><b>Created On</b></th>
													<th class="headerStyles">Actions</th>
												</tr>
											</thead>
											<tbody class="customtable">
												<c:forEach items="${PoEntryList}" var="list"
													varStatus="loop">
													<tr>
													    <td class="tbodyCustomColor">${list[1] }</td>
														<td class="tbodyCustomColor">${list[0] }</td>
														<td class="tbodyCustomColor">${list[2] }</td>
														<td class="tbodyCustomColor">${list[3] }</td>
														<td class="tbodyCustomColor">${list[4] }</td>
														<td class="tbodyCustomColor">${list[5] }</td>
														<td class="tbodyCustomColor"></td> 
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
	<jsp:include page="/WEB-INF/jsp/views/header/footer.jsp"></jsp:include>
	</div>
	</div>

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
	<script type="text/javascript" 
		src="https://cdn.datatables.net/fixedcolumns/4.2.2/js/dataTables.fixedColumns.min.js"></script>

	<script type="text/javascript">
		history.pushState(null, null, location.href);
		window.onpopstate = function() {
			history.go(1);
		};
	</script>
	
	<script type="text/javascript">
	function redirectToPoEntryCreation (){
		location.href = "poEntry"
	}
	</script>
	
	<script>
$(document).ready(function() {
	var table=  $('#example').DataTable( {
		scrollX:        true,
        scrollCollapse: true,
        paging:        true,
    	pageLength : 50,
        fixedColumns:   {
        	left:0,
            right: 1				        },
		 columnDefs: [
	            {
	                searchable: false,
	                orderable: false,
	                targets: 0,
	            },
	        ],
	        columnDefs: [
	            {
	                searchable: true, 
	                //orderable: true,
	                targets: [0,1,2,3] 
	            },
	            {
	                searchable: false,
	                orderable: false,
	                targets: 6
	            }
	        ],
	        order: [[1, 'asc']],
    	responsive :true,
        "sScrollX": "100%",
        "sScrollXInner": "110%",
        "bScrollCollapse": true,
        dom: 'Bfrtip',
        buttons: [
        	 {
                 extend: 'excelHtml5',
					text : '<i class="fa fa-file-excel-o"> Export</i>',
					titleAttr : 'Excel',
                 title: 'PO Entry List'
             },
        ],
        responsive: true,
        "columns" : [{
			"data" : "costCenter"
		},
		{
			"data" : "year"
		},
		{
			"data" : "month"
		},
		{
			"data" : "poAmount"
		},
		{
			"data" : "created By"
		},
		{
			"data" : "created On"
		},
		{"targets": -1, // The last column (Actions)
            "data": null,
            "render": function(data, type, row) {
                   return "<button class='btn btn-success Edit-btn' id='Edit'>Edit</button>";
            },
            "searchable": false,
            "sortable": false,
            "createdCell": function(td, cellData, rowData, rowIndex, colIndex) {
                $(td).attr("data-row", rowIndex);
            }
        }
    ],
  
	} );
	
	
    table.buttons().container().appendTo('#example_wrapper .col-md-6:eq(0)' );
    
    
    $('#example').on('click', '.Edit-btn', function () {
	    console.log('this is murali')
    	  var $row = $(this).closest('tr');
    	    var rowData = table.row($row).data();
    	    var indentDocumentNumber = rowData['Product ID'];
    	    var productName = rowData['Product Name'];
    	    window.location.href = 'poEntryUpdate'; 
	        sessionStorage.setItem('costomerData', JSON.stringify(rowData));
    	   /*  if (table.search() === '') {
    	        sessionStorage.setItem('rowData', JSON.stringify(rowData));
    	    } else {
    	        // A search filter is applied, use the filtered data
    	        var filteredData = table.rows({"search": "applied"}).data().toArray();
    	        var matchingRow = filteredData.find(function (row) {
    	            return row['Product ID'] === indentDocumentNumber && row['Product Name'] === productName; ;
    	        });
    	        if (matchingRow) {
    	            console.log(matchingRow);
    	            sessionStorage.setItem('rowData', JSON.stringify(matchingRow));
    	        } else {
    	            console.log('Matching row not found in filtered data. Edit canceled.');
    	        }
    	    }

    	    window.location.href = 'budgetmasterupdate'; */
    });
});

</script>
</body>
</html>
