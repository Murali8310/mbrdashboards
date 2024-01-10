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
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.0/css/all.min.css">
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
.buttons-html5 {
font-size: 12px !important;
       background: #01AFAE !important;
    color: white !important;
    height: -3px;
    padding: 9px !important;
    border-radius: 8px !important;
}
section label {
	padding-top: 10px;
}
.loading-spinner-container {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh; /* Adjust the height to your preference */
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
		document.getElementById('sending-spin-icon').style.visibility = 'hidden'
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
			<div class="container-fluid" style="min-height:556px;max-height:556px;overflow:scroll;">
				<!-- ============================================================== -->
				<!-- Start Page Content -->
				<!-- ============================================================== -->
				<script
					src="https://cdn.rawgit.com/rainabba/jquery-table2excel/1.1.0/dist/jquery.table2excel.min.js"></script>
				<c:if test="${role == 'Buyer'}">
					<!-- Only show the page content if the user is an Buyer -->

					<div class="card">
						<div class="card-body">
							<h5 class="card-title"></h5>
							<div class="">

								<div id="zero_config_wrapper"
									class="dataTables_wrapper container-fluid dt-bootstrap4">
									<div class="row">

									<div class="col-sm-12 table-container">
									<div class="col-sm-3" style="display:none;">
											<select id="Month" name="Month" class="form-control"
												aria-invalid="true" required="required" Style="width: 97%;">
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
										<div class="col-sm-3" style="display:none;">
											<select id="yearDropdown" class="form-control"
												aria-invalid="true" required="required" Style="width: 97%;">
												<option value="">Select a year</option>
											</select>
										</div>
										<div class="col-sm-3" style="display:none;">

											<button class="btn btn-warning" id="btn-search">search</button>
										</div> -
										<div style="text-align: end;">
											<button class="btn btn-success" id="btn-submit">Submit</button>
											<button class="btn btn-primary" id="btn-sendtovendor"><span id="sending-text">Send to Vendor</span> <i id="sending-spin-icon" class="fas fa-spinner fa-spin"></i></button>
										</div>

<style>
.headerStyles{
 background: #01AFAE !important;
 color: white !important;      
    font-size: 17px;
 
	}
	.tbodyCustomColor {
	    color: black !important;
    font-size: 17px !important;
	}
</style>

										<table id="example"
											class="table table-striped table-bordered display nowrap example"
											style="width: 100%;">
											<thead class="thead-light">
												<tr>	
													<c:forEach items="${Finalcol}" var="finalcoll" varStatus="loop">
													${finalcoll}
													</c:forEach>
												</tr>
											</thead>
											<tbody class="customtable">
												<c:forEach items="${BuyerList}" var="user" varStatus="loop">
													<tr>
													
													<td class="first-cell tbodyCustomColor" style="position:sticky;width:350px !important;">${user[0]}</td>
														<td class='tbodyCustomColor'>${user[1]}</td>
														<td class='tbodyCustomColor'>${user[2]}</td>
													<c:set var="collectionSize" value="${0}" />
													<c:forEach items="${Collen}" var="ccen" varStatus="loop2">
													<td class="editable-cell tbodyCustomColor" style="text-align:center">${user[loop2.index + 3]}</td>
													<c:set var="collectionSize" value="${collectionSize + 1}" />
													</c:forEach> 
													
														<td class='tbodyCustomColor' style="text-align:center"></td>
														<td class="editable-cell tbodyCustomColor remove">
															${user[collectionSize + 4]}	
														</td>
															<td class='tbodyCustomColor' style="text-align:right">${user[collectionSize + 3] * user[collectionSize + 4]}</td>
															<td style="text-align:center" class='tbodyCustomColor'></td>
															<td class='tbodyCustomColor' style="text-align:right"></td>
															<td class='tbodyCustomColor' style="text-align:right">${user[collectionSize + 3]}</td>
															<td class='tbodyCustomColor' style="text-align:center" >${user[collectionSize + 6]}</td>
															<td class='tbodyCustomColor' style="text-align:right" >${user[collectionSize + 7]}</td>
 </tr>
													</c:forEach>
												</tbody>
												<tfoot>
													<tr>
														<th class='tbodyCustomColor'>Total</th>
														<th class='tbodyCustomColor'></th>
														<th class='tbodyCustomColor'></th>
													<c:forEach items="${Collen}" var="ccen" varStatus="loop2">
															<th class='tbodyCustomColor'></th>
													</c:forEach>	
																												<th class='tbodyCustomColor' id="footerUserQty"></th>
																												<th class='tbodyCustomColor' id="footerMoqty"></th>
																												<th class='tbodyCustomColor' style="text-align:right" id="footerMoqValue"></th>
																												<th class='tbodyCustomColor' id="footerTotalQty"	></th>
																												<th class='tbodyCustomColor' style="text-align:right"  id="footerTotalValue"></th>
																												<th class='tbodyCustomColor' style="text-align:center"></th>
																												<th class='tbodyCustomColor' id="footerStockQty"></th>
																												<th class='tbodyCustomColor' style="text-align:right" id="footerStockValue"></th>
														

												</tr>
												<c:forEach items="${FooterList}" var="foot" varStatus="loop">

													<tr class="footer-row reassign-footer">
															<th ><div
																	class="sticky-col first-col">${foot[0]}</div></th>
														<th></th>
														<th></th>
														<c:forEach items="${Collen}" var="ccen" varStatus="loop2">
                                                         <th class="compare-value" style="text-align:right" data-threshold="${foot[loop2.index + 1]}">${foot[loop2.index + 1]}</th>													
                                                         </c:forEach>
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
	//This will return the total sum of evry coulm;
	
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
	var table = $('#myDataTable').DataTable();

	function updateRowSums() {
	    var table = $('#example').DataTable();
	       // var tableSize = table.rows().count();
	    var tableLengthElement = document.getElementById("example");
            // Get the number of rows in the table
	   // var tableLength = tableLengthElement.rows.length;
	    var numColumns = table.columns().header().length;
	        numColumns = numColumns - 8;
	        table.rows().every(function (rowIdx, tableLoop, rowLoop) {
	        var row = this.data();
	        let sum = 0;
	        for (var colIndex = 3; colIndex < numColumns; colIndex++) {
			      console.log('dd',row[colIndex],colIndex)
		      sum = sum + Number(row[colIndex]); // Convert to numbers
		    }
	        console.log('this is console',sum)
	       this.cell(rowIdx, numColumns).data(sum);
	       this.cell(rowIdx, numColumns + 2).data((row[numColumns+1] *row[numColumns+5]).toFixed(2));
	       this.cell(rowIdx, numColumns + 3).data(row[numColumns] > row[numColumns+1]? row[numColumns]:row[numColumns+1]);
	       this.cell(rowIdx, numColumns + 4).data((row[numColumns+3] *row[numColumns+5]).toFixed(2));
	       this.cell(rowIdx, numColumns + 7).data((row[numColumns+6] *row[numColumns+5]).toFixed(2));	
	        });
	    table.draw();
	   $('#footerUserQty').text(updateColumnData(numColumns));
	  $('#footerMoqty').text(updateColumnData(numColumns + 1));
	  $('#footerMoqValue').text(updateColumnData(numColumns + 2));
	  $('#footerTotalQty').text(updateColumnData(numColumns + 3));
	  $('#footerTotalValue').text(updateColumnData(numColumns + 4));
	  $('#footerStockQty').text(updateColumnData(numColumns + 6));
	  $('#footerStockValue').text(updateColumnData(numColumns + 7)); 
	}

$(document).ready(function () {
	const myElement = document.getElementById("btn-sendtovendor");
	
    // Add the "disabled" property to the element
    myElement.disabled = true;
		 const currentDate = new Date();
		  const currentMonth = currentDate.toLocaleString('default', { month: 'long' });
		  console.log('currentMonth',currentMonth)
		  document.getElementById('Month').value = currentMonth;
			// Get a reference to the table element by its ID
		 var table = document.getElementById("example");

			 // Get the number of rows in the table
			 var rowCount = table.rows.length;

			 // Output the row count to the console
			 console.log("Number of rows in the table: " + rowCount);
			// Get a reference to the table element by its ID
			 var table = document.getElementById("example");

			// Get a reference to the table element by its ID
			 var table = document.getElementById("example");

			 // Get the number of columns in the first row (assuming all rows have the same number of columns)
			 var numColumns = table.rows[0].cells.length;
			 numColumns = numColumns - 7;
		     let data = [];
			 // Iterate through each column
	
	 var currentYear = new Date().getFullYear();
     var yearDropdown = document.getElementById("yearDropdown");

     for (var i = currentYear; i >= currentYear - 10; i--) {
         var option = document.createElement("option");
         option.value = i;
         option.text = i;
         if (i === currentYear) {
             option.selected = true;
         }
         yearDropdown.appendChild(option);
     }
  
    var table = $('#example').DataTable({
    	
            dom: 'Bfrtip',
            buttons: [
                {
                  extend: 'excelHtml5',
                  text: '<i class="fa fa-file-excel-o"> Export</i>',
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
    updateRowSums();
 // Usage example:
	var newColumnData = ['Updated 1', 'Updated 2', 'Updated 3', 'Updated 4'];
	// Call the function to update the sums
	

	$("#btn-search").on("click", function() {
		 var table = document.getElementById("example");

		 // Get the number of rows in the table
		 var rowCount = table.rows.length;

		 // Output the row count to the console
		 console.log("Number of rows in the table: " + rowCount);
		// Get a reference to the table element by its ID
		 var table = document.getElementById("example");

		// Get a reference to the table element by its ID
		 var table = document.getElementById("example");

		 // Get the number of columns in the first row (assuming all rows have the same number of columns)
		 var numColumns = table.rows[0].cells.length;
		 numColumns = numColumns - 7;
	     let data = [];
		 // Iterate through each column
		 for (var colIndex = 0; colIndex < numColumns; colIndex++) {
		     var columnIsEmpty = false; // Assume the column is empty initially
		     let data2 = [];
		     // Iterate through each row to check the content of the cells in the current column
		     for (var rowIndex = 0; rowIndex < table.rows.length; rowIndex++) {
		         var cell = table.rows[rowIndex].cells[colIndex];
		         // Check if the cell is empty
		         if (cell.innerHTML.trim() !== "" && cell.innerHTML.trim() !== "0.00" && cell.innerHTML.trim() !== "0") {
		        	 columnIsEmpty = false; // The column is not empty if any cell has content
		         } else {
		        	 columnIsEmpty = true;
		         }
		         data2.push(columnIsEmpty)
		     }
		     data2= data2.slice(1);
		    if(data2.indexOf(false) === -1){
		    	for (var removeIndex = 0; removeIndex < table.rows.length; removeIndex++) {
		    	//	table.rows[removeIndex].cells[colIndex].remove();
		    		//table.rows[removeIndex].cells[colIndex].style.display = 'none';
			    		}
		     } 
		 }
		var Year=$('#yearDropdown').val();
		var Month=$('#Month').val();
		if(currentMonth==Month && currentYear==Year){
			location.reload();
		}
		else{
	 	$.ajax({
			type : "Get",
			url : "getBuyerFooterListBasedOnFilter",
			contentType : 'application/json',
			data : "Year="+Year+"&Month="+Month,

			success : function(response) {
				updateRowSums();
				data = jQuery.parseJSON(response);
			//	var footerTable = $('#example').DataTable().table().footer();
			    
			 //   $(footerTable).find('tr:gt(0)').empty();
			 if(data.length==7){
			   // table.clear().draw();
			   var footerTable = $('#example').DataTable().table().footer();
    var footerRows = $(footerTable).find('tr');
    for (var i = 1; i < footerRows.length; i++) {
        var currentFooterRow = footerRows[i];
        var rowData = data[i - 1]; // Assuming your data corresponds to footer
        for (var j = 3; j < rowData.length + 3; j++) {
            var currentCell = $(currentFooterRow).find('th').eq(j);
            $(currentCell).text(rowData[j]); // Assign data to the cell
        }
    }
					
			}else{
				var footerTable = $('#example').DataTable().table().footer();
				var footerRows = $(footerTable).find('tr');
				for (var i = 0; i < footerRows.length; i++) {
				    var currentFooterRow = footerRows[i];
				    for (var j = 3; j < $(currentFooterRow).find('th').length; j++) {
				        var currentCell = $(currentFooterRow).find('th').eq(j);
				        $(currentCell).text(0); // Assign zero to the cell
				    }
				}
			}}
		});
	
	 		
		$.ajax({
			type : "Get",
			url : "getBuyerIndentListBasedOnFilter",
			contentType : 'application/json',
			data : "Year="+Year+"&Month="+Month,

			success : function(response) {
				data = jQuery.parseJSON(response);
				//console.log(data)
				var wwww = $('#example').DataTable({
      // Configuration options go here
                });
				wwww.clear().draw();
				wwww.rows.add(data).draw();
				 
				  /* var startColumnIndex = 3; 
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
				  }); */
		      	    //-------------------------------------------------
		      	    
		      	    // -------------Footer calculations on change Start -----------------
		      
				  
		      	  
			}
        });
		
		
	}
		 
	});
   // table.column(0).width('350px');
   
    function compareFooterValues() {
    	console.log($('#example').DataTable().table().footer())
    var	rowData2=[]
    var	rowData3=[]
	var footerTable = $('#example').DataTable().table().footer();
   	var footerRows = $(footerTable).find('tr');
        var currentFooterRow2 = footerRows[2];
        $(currentFooterRow2).find('th').each(function() {
            rowData2.push($(this).text());
        });
        
        var currentFooterRow3 = footerRows[3];
        $(currentFooterRow3).find('th').each(function() {
            rowData3.push($(this).text());
        });
        
         for (var j = 3; j <76; j++) {
        		 var currentCell2 = $(currentFooterRow2).find('th').eq(j);
        	    var currentCell3 = $(currentFooterRow3).find('th').eq(j);
        	    var row2 = parseInt(rowData2[j]);
        	    var row3 = parseInt(rowData3[j]);
        	    if (row3 > row2) {
        	        $(currentCell3).css("background-color", "red");
        	        $(currentCell3).css("color", "#fff");
        	    } else if (row3 < row2) {
        	        $(currentCell3).css("background-color", "green");
        	        $(currentCell3).css("color", "#fff");
        	    } else if (row3 === row2) {
        	        $(currentCell3).css("background-color", "yellow");
        	    }
        } 
         
    }
    

    // to dissable zero values edit  || parseFloat(cellContent) === 0
    $('.editable-cell').each(function() {
        var cellContent = $(this).text().trim();
        if (cellContent === '') {
         if ($(this).hasClass('editable-cell')) {
        	 if(!$(this).hasClass('remove')){
          $(this).removeClass('editable-cell');}}
         }
      });
  
   

   
    
   // table.column(-1).visible(false);
    
    $("#btn-submit").on("click", function() {
        var data = []; // An array to store the data from the table
        console.log('this is click')
      
        var columns = [];

        //table.column(-1).visible(true);
        $("#example thead th").each(function() {
            var columnName = $(this).text();
              if (columnName.startsWith("CC")) {
                columnName = columnName;
            } 
            columns.push(columnName);
            console.log(columns)
            document.getElementById('btn-sendtovendor').style['pointer-events'] = '';
            const myElement = document.getElementById("btn-sendtovendor");
            // Add the "disabled" property to the element
            myElement.disabled = false;
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
                    const unitPrice = item["Unit Price(Rs)"];
                    const MoqQty = item["MOQ Qty"];
                    const MOQValue = item["MOQ Val(Rs)"];
                    const TMTQty = item["Stock At TMT (QTY)"];
                    const TMTValue = item["STK Val(Rs)"];
 
                    
                   if(parseInt(quantity)>=0){
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
					title : 'Indent Updated Successfully. </br></br> Please Proceed and Click <span style="color: #3d69d4;"> "Send to Vendor</span>"',
					showCloseButton : false,
					focusConfirm : true,
				}).then((result) => {
				    if (result.isConfirmed) {
				       // location.reload();
				    }
				});
			//	document.getElementById('btn-sendtovendor').style['pointer-events'] = 'cursor'
					const myElement = document.getElementById("btn-sendtovendor");
			    // Add the "disabled" property to the element
			    myElement.disabled = false;
			    location.reload();
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

    	    var input = $('<input type="text" id="input-text" class="input-text" >');
    	    
    	    input.on('input', function() {
    	        this.value = this.value.replace(/[^0-9]/g, '');
    	    });
 			setInputWidth(input, columnHeaderWidth);
    	    input.val(originalValue);

    	    input.on('blur', function() {
    	      var newValue = input.val();
    	      console.log('cell',cell)
    	      cell.data(newValue);
    	      setInputWidth(input, columnHeaderWidth);
    	      updateRowSums();
    	    });

    	    $(this).html(input);
  	    	 
  	      
    	    input.focus();
    	    input.select();

    	    input.get(0).contentEditable = true;
    	  }

    });
   
      function setInputWidth(input, width) {
    	    input.css('width', width);
    	}

    	function getTextWidth(text) {
    	  var element = $('<span>').text(text).css('display', 'none');
    	  $('body').append(element);
    	  var width = element.width();
    	  element.remove();
    	  return width;
    	}
    
    	 $("#btn-sendtovendor").on("click", function() {
    		// document.getElementById('btn-sendtovendor').style['pointer-events'] = 'none';
    		 const myElement = document.getElementById("btn-sendtovendor");
    		    // Add the "disabled" property to the element
    		    myElement.disabled = true;
    	    	var header = table.columns().header().toArray().map(function(th) {
    	            return $(th).text();
    	        });	
    	        
    	        var data = table.rows().data().toArray();
    	        
    	        var footerRows = document.getElementById("example").tFoot.rows;

    	        // Initialize an array to store footer data
    	        var footerData = [];

    	        // Iterate through each footer row
    	        for (var i = 0; i < footerRows.length; i++) {
    	            var footerRow = footerRows[i];
    	            var footerCells = footerRow.cells;
    	            var rowData = [];

    	            // Iterate through the cells in the footer row and extract text
    	            for (var j = 0; j < footerCells.length; j++) {
    	                rowData.push(footerCells[j].textContent);
    	            }

    	            // Add the row data to the footerData array
    	            footerData.push(rowData);
    	        }

    	        // Now, the footerData array contains the data from all footer rows
    	        console.log(footerData,'foot');

    	        var payload = {
    	            header:['Product Id','Description','Total Qty'],
    	            data: removeColumnsFromData(data, [76, 77, 80, 82, 83])
    	        };
    			//console.log(JSON.stringify(payload),'data');
    			 document.getElementById('sending-spin-icon').style.visibility = 'visible';
    	         document.getElementById("sending-text").textContent = "Sending..."
    		 	$.ajax({
    				type : "POST",
    				url : "sendToVendor",
    				contentType : 'application/json',
    				data : JSON.stringify(payload),
    				success : function(response) {
    					document.getElementById('sending-spin-icon').style.visibility = 'hidden';
    		    	         document.getElementById("sending-text").textContent = "Send to Vendor";
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
