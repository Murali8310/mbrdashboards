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
<!-- Custom CSS
<link href="assets/libs/flot/css/float-chart.css" rel="stylesheet">  -->
<!-- Custom CSS -->
<link href=" dist/css/style.min.css" rel="stylesheet">
<link href="dist/css/loading.css" rel="stylesheet" type="text/css" />


<link
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"
	rel="stylesheet" type="text/css" />
<link
	href="https://cdn.datatables.net/1.10.25/css/jquery.dataTables.min.css"
	rel="stylesheet" type="text/css" />
<link
	href="https://cdn.datatables.net/buttons/2.3.2/css/buttons.dataTables.min.css"
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

.table-container {
	overflow-x: auto; /* Enable vertical scrolling */
	max-height: 500px; /* Set a maximum height for the container */
}

table.dataTable td {
  font-size: 1em;
}

table.dataTable thead tr {
  background-color: green; !important
}

table.dataTable tbody tr.selected a {
  color: #090a0b;
  color: rgb(var(--dt-row-selected-link));
}

.col-sm-3 {
    width: 25%;
    padding-bottom: 5px;
}

</style>

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
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
						<h5 class="page-title display-6">Distributer Receipt Filter Page</h5> 
						<div class="ml-auto text-right">
							<nav aria-label="breadcrumb">
								<ol class="breadcrumb">
									<li class="breadcrumb-item"><a href="landPage">Home</a></li>
									<li class="breadcrumb-item"><a href="manageByAdmin">Management</a></li>
									<li class="breadcrumb-item active" aria-current="page">Distributer Receipt Filter Page</li>
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
    
    
    function myFunction () {
    	var Year=$('#yearDropdown').val();
		var Month=$('#Month').val();
		if(Month == ''){
			Swal.fire({
				
				icon : 'error',
				title : 'Please select the Month',
				showCloseButton : false,
				focusConfirm : true,
			});
			return;
		} 
		
if(Year == ''){
	Swal.fire({
		
		icon : 'error',
		title : 'Please select the Year',
		showCloseButton : false,
		focusConfirm : true,
	});
	return;
		} 
		
        location.href = 'https://stationery.titan.in/stationary/distributerFilterShowPage?Year='+Year+'&Month='+Month;
    }
    
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
				<script>

</script>
				<c:if test="${role == 'Tray Manager'}">
					<!-- Only show the page content if the user is an Buyer -->
					<div class="card">
						<div class="card-body">
							<!-- <div class=""> -->
							<div id="zero_config_wrapper"
								class="dataTables_wrapper container-fluid dt-bootstrap4">
								<div class="row">

									<div class="col-sm-12 table-containere">
									<div class="col-sm-3">
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
										<div class="col-sm-3">
											<select id="yearDropdown" class="form-control"
												aria-invalid="true" required="required" Style="width: 97%;">
												<option value="">Select a year</option>
											</select>
										</div>
										<div class="col-sm-3">

											<button class="btn btn-warning"  onclick="myFunction()" id="btn-search">search</button>
										</div>
										<div style="text-align: end;display:none;">
											<button class="btn btn-success" id="btn-submit">Submit</button>
												<button class="btn btn-primary" id="btn-sendtostore">Send to Store</button>
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
											<thead class="thead-light">
												<tr>
													<th scope="col" class='headerStyles'><b>Description</b></th>
													<th scope="col" class='headerStyles'><b>Make</b></th>
													<th scope="col" class='headerStyles'><b>UOM</b></th>
													<c:forEach items="${Collen}" var="costcenter" varStatus="loop2">
													<th scope="col" class='headerStyles'><b>CC${costcenter}</b></th>
													</c:forEach> 
													<th scope="col" class='headerStyles'><b>Final Qty</b></th>
													<th scope="col" class='headerStyles'><b>Final Val(Rs)</b></th>
													<th scope="col" class='headerStyles'><b>Unit Price(Rs)</b></th>													
													<!-- Receipt -->
													<th scope="col" class='headerStyles'><b>Receipt Qty</b></th>
													<th scope="col" class='headerStyles'><b>Receipt Val</b></th>
													<th scope="col" class='headerStyles'><b>Stock At DIST.Team (QTY)</b></th>
													<th scope="col" class='headerStyles'><b>STK Val(Rs)</b></th>
													
												</tr>
											</thead>
											<tbody class="customtable">
												<c:forEach items="${BuyerList}" var="user" varStatus="loop">
													<tr>
														<%-- <td>${loop.index+1}</td> --%>
														<td class='tbodyCustomColor'>${user[0]}</td>
														<td class='tbodyCustomColor'>${user[1]}</td>
														<td class='tbodyCustomColor'>${user[2]}</td>
														<c:set var="collectionSize" value="${0}" />
														<c:forEach items="${Collen}" var="ccen" varStatus="loop2">
													<td class="editable-cell tbodyCustomColor">${user[loop2.index + 3]}</td>
													  <c:set var="collectionSize" value="${collectionSize + 1}" />
													
													</c:forEach>													
														<td class='tbodyCustomColor'>${user[collectionSize + 8]}</td>
														<td class='tbodyCustomColor' style="text-align:right !important">${user[7]*user[6]}</td>
														<td class='tbodyCustomColor' style="text-align:right !important">${user[collectionSize + 3]}</td>
														<!-- Receipt -->
														<td class="editable-cell tbodyCustomColor">${user[collectionSize + 8]}</td>
														<td class='tbodyCustomColor' style="text-align:right !important"></td>
														<td class="editable-cell tbodyCustomColor" style="text-align:center !important">${user[collectionSize + 6]}</td>
														<td class='tbodyCustomColor' style="text-align:right !important"></td>

													</tr>

												</c:forEach>
											</tbody>
											<tfoot>

												<c:forEach items="${FooterList}" var="foot" varStatus="loop">

													<tr class="footer-row">
													 <th ><div class="sticky-col first-col tbodyCustomColor">${foot[0]}</div></th>
														<th class='tbodyCustomColor'></th>
														<th class='tbodyCustomColor'></th>
														<c:forEach items="${Collen}" var="ccen" varStatus="loop2">
														<th class='tbodyCustomColor'>${foot[loop2.index + 1]}</th>
													</c:forEach> 
														<th class='tbodyCustomColor' id="finalQty"></th>
														<th class='tbodyCustomColor' id="finalValue"></th>
														<th class='tbodyCustomColor' style="text-align:center !important"></th>
														<th class='tbodyCustomColor' id="ReciptQty"></th>

														<!-- Receipt -->
														<th class='tbodyCustomColor' style="text-align:right !important" id="Reciptvalue"></th>
														<th class='tbodyCustomColor' style="text-align:center !important" id="stockQty"></th>
														<th class='tbodyCustomColor' style="text-align:right !important" id="stockValue"></th>
													</tr>
												</c:forEach>
											</tfoot>
										</table>

									</div>

								</div>
							</div>

							<!-- </div> -->
						</div>

					</div>
				</c:if>
				<c:if test="${role != 'Tray Manager'}">
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
	<!--	<script type="text/javascript"
		src="assets/libs/datatables/media/js/jquery.dataTables.min.js"></script>
 -->



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

<script src="dist/js/sweetalert2.min.js"></script>
<link rel="stylesheet" href="dist/css/sweetalert2.min.css" />

	<!-- FixedColumns -->
	<link rel="stylesheet"
		href="https://cdn.datatables.net/fixedcolumns/4.0.0/css/fixedColumns.dataTables.min.css">
	<script
		src="https://cdn.datatables.net/fixedcolumns/4.0.0/js/dataTables.fixedColumns.min.js"></script>


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
	    	console.log('d',Number(value));
	    	}
	    	totalSum = totalSum + Number(value);	        	
	    });
         console.log('3',totalSum)
	    // Redraw the table to reflect the changes
	    table.draw();
	    return totalSum;
	}
	function updateRowSums() {
	    var table = $('#example').DataTable();
	       // var tableSize = table.rows().count();
	    var tableLengthElement = document.getElementById("example");
            // Get the number of rows in the table
	       var numColumns = tableLengthElement.rows[0].cells.length;
			 numColumns = numColumns - 7;
	        table.rows().every(function (rowIdx, tableLoop, rowLoop) {
	        var row = this.data();
	        let sum = 0;
	        for (var colIndex = 3; colIndex < numColumns; colIndex++) {
		      sum = sum + Number(row[colIndex]); // Convert to numbers
		    }
	       
	        // Update the sum in the last cell (assuming it's the last column)
	        console.log('this.cell(rowIdx, numColumns).data(sum)',Number(row[numColumns + 3]),sum,Number(this.cell(rowIdx, numColumns).data()))
	       if(sum < Number(this.cell(rowIdx, numColumns).data())){
	        	sum = Number(this.cell(rowIdx, numColumns).data());
	        }
	     this.cell(rowIdx, numColumns).data(sum);
	     this.cell(rowIdx, numColumns + 1).data(sum * Number(row[numColumns + 2]));
	     this.cell(rowIdx, numColumns + 4).data(Number(row[numColumns + 2]) * Number(row[numColumns + 3]));
	     this.cell(rowIdx, numColumns + 6).data(Number(row[numColumns + 2]) * Number(row[numColumns + 5]));
	    });
	        //This is to update the moq or userqty based total value.
	        
	      /*   table.rows().every(function (rowIdx, tableLoop, rowLoop) {
		        var row = this.data();
		        let totalValue = 0;
		        let totalQty = 0;
		        if(Number(row[numColumns +1]) > Number(row[numColumns + 2])){
		        	totalValue = Number(row[numColumns +1]) * Number(row[numColumns + 6]);
		        	totalQty = Number(row[numColumns +1]);
		        }else {
		        	totalValue = Number(row[numColumns +2]) * Number(row[numColumns + 6]);
		        	totalQty = Number(row[numColumns +2]);
		        }	
		        this.cell(rowIdx, numColumns + 3).data(Number(row[numColumns +2]) * Number(row[numColumns + 6]));
		        this.cell(rowIdx, numColumns + 4).data(Number(row[numColumns +4]) * Number(row[numColumns + 6]));
		    }); */
	        
	    // Redraw the table to reflect the changes
	    table.draw();
	 $('#finalQty').text(updateColumnData(numColumns));
	  $('#finalValue').text(updateColumnData(numColumns + 1));
	  $('#ReciptQty').text(updateColumnData(numColumns + 3));
	   $('#Reciptvalue').text(updateColumnData(numColumns + 4));
	   $('#stockQty').text(updateColumnData(numColumns + 5));
	   $('#stockValue').text(updateColumnData(numColumns + 6));
	   //$('#footerStockValue').text(updateColumnData(numColumns + 8));
	}
$(document).ready(function () {
	//document.getElementById('btn-sendtostore').style['pointer-events'] = 'none';
	const myElement = document.getElementById("btn-sendtostore");
    // Add the "disabled" property to the element
    myElement.disabled = true;
	 const currentDate = new Date();
			 	// Get a reference to the table element by its ID
			 var table = document.getElementById("example");
			 // Get the number of rows in the table
			 var rowCount = table.rows.length;
			// Get a reference to the table element by its ID
			 var table = document.getElementById("example");

			// Get a reference to the table element by its ID
			 var table = document.getElementById("example");

			 // Get the number of columns in the first row (assuming all rows have the same number of columns)
			 var numColumns = table.rows[0].cells.length;
			 numColumns = numColumns - 7;
		     let data = [];
			
			// Call the function to update the sums
				//updateRowSums();
	  function UpdateStockData(indentTransactionData, month ,currentYear) {
	    	//const currentMonth = 'october'; // Month is 0-based (0 = January, 11 = December)
	    	//const currentYear = 2023;
	    	const months = ["January", "February", "March","April", "May", "June","July", "August", "September", "October", "November", "December"];
	    	const monthIndex = months.findIndex(function(element) {
	    		  return element == currentMonth;
	    		});
	    	let prepareRestrictedMonth = [];
	    	let filteredDataIncludingCurrentYrData = [];
	    	for(index=0;index < indentTransactionData.length; index++){
	    	const costDataObj = indentTransactionData[index];
	    		if(costDataObj[5]<= currentYear){
	    			const monthIndex = months.findIndex(function(element) {
	  	    		  return element == month;
	  	    		});
	    			if(monthIndex != -1){
	    				const restrictedMonthCount = 12 - monthIndex;
	    				for(index2 =(monthIndex);index2 < 12; index2++){
		    				prepareRestrictedMonth.push(months[index2]);
	    				}
	    			}
	    		if((costDataObj[5]< currentYear) ||((costDataObj[5] == currentYear) && (prepareRestrictedMonth.indexOf(costDataObj[3])== -1))){
	    			if(costDataObj[9] < costDataObj[12]){
	    				costDataObj.push(costDataObj[12]-costDataObj[9]);
	    				costDataObj.push((costDataObj[12]*costDataObj[10] - costDataObj[9]*costDataObj[10]));
	    			} else {
	    				costDataObj.push(0);
	    				costDataObj.push(0);
	    			}
	    			filteredDataIncludingCurrentYrData.push(costDataObj)	    				
	    			}
	    		}
	    	}
	    	// Get a reference to the tbody element
		        var tbody = document.querySelector('tbody');
		        // Get all the rows within the tbody
		        var rows = tbody.getElementsByTagName('tr'); 
		        console.log('length',rows.length)// or: tbody.querySelectorAll('tr');
		        for (var i = 0; i < rows.length; i++) {
		            var row = rows[i];
		            var descriptionName = row.getElementsByTagName('td')[0];
		            let stockQuantity = row.getElementsByTagName('td')[84];
		            let stockValue = row.getElementsByTagName('td')[85];
		          let  tempstockQuantity = 0;
		          let  tempstockAmoutnt = 0;
		            for (let productIndex = 0; productIndex < filteredDataIncludingCurrentYrData.length; productIndex++) {
		                var productObj = filteredDataIncludingCurrentYrData[productIndex];
		                if (productObj[8] == descriptionName.textContent) {
		                	console.log('tempstockQuantity',productObj[28])
		                	tempstockQuantity = tempstockQuantity + productObj[28];
		                	tempstockAmoutnt = tempstockAmoutnt + productObj[29];
		                }
		                stockQuantity.textContent = tempstockQuantity;
	                    stockValue.textContent = tempstockAmoutnt;
		            }
		        }
	    	return  filteredDataIncludingCurrentYrData;
	    }
	 $.ajax({
		    url: 'indentList', 
		    method: 'GET', 
		    success: function(response) {
		        var parsedData = JSON.parse	(response);
		     //   const updatedStockData = UpdateStockData(parsedData,'October',2023);
		       // console.log('this is final data',updatedStockData);
		        },
		    error: function(error) {
		        console.error('Error fetching data:', error);
		    }
		});

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
                  footer: true,
                  text: '<i class="fa fa-file-excel-o"> Export</i>',
                  titleAttr: 'Excel',
                  title: 'Distribution Receipt',
                  customize: function(xlsx) {
                	  console.log('this is frpm export')
                	  removeEmptyColumnsFromDataTable();
                      var sheet = xlsx.xl.worksheets['sheet1.xml'];
                      var tfootRows = $('#example tfoot tr');
                      tfootRows.each(function(index, row) {
                          var rowXML = generateRowXML(row);
                          $('sheetData', sheet).append(rowXML);
                      });
                  }
              },
          ],
        
    	/* "pageLength" : 10, */
    	paging: false,
    	scrollX: true,
    	searching: false,
        fixedHeader: true, 
        scrollCollapse: true,
        scrollY: '300px',
        
        
    	 fixedColumns: {
    	        leftColumns: 1 // Number of columns to be fixed on the left side
    	      }, 
    	    
    });
    
    updateRowSums();
    
    function generateRowXML(row) {
        var rowXML = '<row>';
        $(row).find('th, td').each(function(index, cell) {
            var cellValue = $(cell).text();
            rowXML += '<cell>' + cellValue + '</cell>';
        });
        rowXML += '</row>';
        return rowXML;
    }
   
    function compareFooterValues() {
        var $footerCells = $('#example tfoot .compare-value');
        $footerCells.each(function() {
            var cellValue = parseFloat($(this).text());
           
            var threshold = parseFloat($(this).data('threshold'));
            if (!isNaN(cellValue) && !isNaN(threshold)) {
                if (cellValue > threshold) {
                    $(this).css('background-color', 'green');
                } else {
                    $(this).css('background-color', 'red');
                }
            }
        });
    }

    function removeEmptyColumnsFromDataTable() {
    	  var table = $('#yourDataTable').DataTable();
    	  var columnsToRemove = [];

    	  // Iterate through each column
    	  table.columns().every(function () {
    	    var data = this.data();
    	    
    	    // Check if the column is empty
    	    if (data.every(cell => cell === '' || cell === null)) {
    	      columnsToRemove.push(this.index());
    	    }
    	  });

    	  // Remove the empty columns
    	  for (var i = columnsToRemove.length - 1; i >= 0; i--) {
    	    table.column(columnsToRemove[i]).remove();
    	  }

    	  table.draw();
    	}

    
   // table.column(-1).visible(false);
    
    $("#btn-submit").on("click", function() {
       //	document.getElementById('btn-sendtostore').style['pointer-events'] = 'cursor';
       	const myElement = document.getElementById("btn-sendtostore");
     // Add the "disabled" property to the element
     myElement.disabled = false;
    	//alert("Indent Updated Successfully");
    	console.log('this is from submit your change')
        var data = []; // An array to store the data from the table

        var columns = [];

       // table.column(-1).visible(true);
        $("#example thead th").each(function() {
            var columnName = $(this).text();
              if (columnName.startsWith("CC")) {
                columnName = columnName;
            } 
            columns.push(columnName);
           // console.log(columns)
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

       // table.column(-1).visible(false);
        

        const BuyerList = [];
console.log("murali checkking",data)
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
                    const MOQValue = item["MOQ Val(RS)"];
                    const TMTQty = item["Stock At TMT (QTY)"];
                    const TMTValue = item["STK Val(Rs)"];
                    const receivedqty = item["Receipt Qty"];
                    const receivedvalue = item["Receipt Val"];
                    const userQuantity = item["User Qty"];
                   console.log('this is fr checking TMTQty',TMTQty)
                   if(parseInt(quantity)>0){
                	   //console.log(quantity1,'quantity1');
                	    if (costcenter.startsWith("CC")) {
                	    	console.log('this is cost cenrterdata'+costcenter)
                    const newItem = {
                        "documnet": documentNumber,
                        "costcenter": costcenter,
                        "description": description,
                        "quantity": quantity=="0.00"?"0":parseInt(quantity),
                        "unitPrice": unitPrice,
                        "receivedqty":receivedqty=="0.00" || receivedqty==''?"0":receivedqty,
                        "receivedvalue":receivedvalue,
                        "moqQty": MoqQty,
                       "MOQValue" :MOQValue,
                        "userQuantity" : userQuantity,
                        "balanceTMTQty":TMTQty=="0.00"?"0":TMTQty,
                        "balanceTMTValue":TMTValue
                    };

                    console.log(newItem,'newItem');
                    
                    BuyerList.push(newItem);
                    console.log(BuyerList,'BuyerList');
                    }}
              //  }
            }
            
        });
        console.log(BuyerList,'BuyerList');
       $.ajax({
			type : "POST",
			url : "DistributionPageSave",
			contentType : 'application/json',
			data : JSON.stringify(BuyerList),

		success : function(response) {
				Swal.fire({
					
					icon : 'success',
					title : response,
					showCloseButton : false,
					focusConfirm : true,
				});
				console.log('this is from hte murali333')
				//document.getElementById('btn-sendtostore').style['pointer-events'] = '';
				const myElement = document.getElementById("btn-sendtostore");
			     // Add the "disabled" property to the element
			     myElement.disabled = false;
				//location.href="BuyerIndentPage";
			}
        });
   	document.getElementById('btn-sendtostore').style['pointer-events'] = 'cursor';
      //  console.log(data);
      //  console.log(BuyerList,'convertedData');  // You can check the data in the browser console or process it further as needed
    });

 
   
    
     
    $('#example').on('click', 'td', function() {
  	  if ($(this).hasClass('editable-cell')) {
  	  var cell = table.cell(this);
  	    var originalValue = cell.data();

  	    var columnIndex = cell.index().column;
  	    var columnHeaderWidth = $(table.column(columnIndex).header()).width();

  	    var input = $('<input type="text" id="input-text" class="input-text">');
  	    input.on('input', function() {
  	        this.value = this.value.replace(/[^0-9]/g, '');
  	        console.log('ttttttt',this.value)
  	        
  	    });
			setInputWidth(input, columnHeaderWidth);
  	    input.val(originalValue);

  	    input.on('blur', function() {
  	      var newValue = input.val();
  	      cell.data(newValue);
  	      console.log('this is consoe')
   	      updateRowSums();
  	      setInputWidth(input, columnHeaderWidth);
  	    });

  	    $(this).html(input);
	    	 
	      
  	    input.focus();
  	    input.select();

  	    input.get(0).contentEditable = true;
  	  }
  	    input.on('blur', function() {
  	    	 var newValue = input.val();
  	    	 
  	    	if (input.val() === "") {
  				newValue=0;
  			}
    	     // var newValue = input.val();
    	   
    	    if(newValue >= 0){
    	    	
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
    	    
    	 
    	  var receicedqtyIndex = 82; // received qty
        var PriceIndex = 79; // price
        var targetColumnIndex = 83; // target column received value

        table.rows().every(function(rowIdx, tableLoop, rowLoop) {
          var row = this;
          var ReceivedQty = parseFloat(row.data()[receicedqtyIndex]);
          var Price = parseFloat(row.data()[PriceIndex]);
          var product = isNaN(ReceivedQty) || isNaN(Price) ? 0 : (ReceivedQty * Price);

          row.cell(rowIdx, targetColumnIndex).data(product.toFixed(2));
        });
        
        //This is to update the stockqty and stock value.
        var receicedqtyIndex = 84; // received qty
        var PriceIndex = 79; // price
        var targetColumnIndex = 85; // target column received value

        table.rows().every(function(rowIdx, tableLoop, rowLoop) {
          var row = this;
          var ReceivedQty = parseFloat(row.data()[receicedqtyIndex]);
          var Price = parseFloat(row.data()[PriceIndex]);
          var product = isNaN(ReceivedQty) || isNaN(Price) ? 0 : (ReceivedQty * Price);

          row.cell(rowIdx, targetColumnIndex).data(product.toFixed(2));
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
    	
   	 $("#btn-sendtostore").on("click", function() {
   		//document.getElementById('btn-sendtostore').style['pointer-events'] = 'none';
   		const myElement = document.getElementById("btn-sendtostore");
        // Add the "disabled" property to the element
        myElement.disabled = true;
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
				url : "sendToStore",
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
    

    var columnIndices = [76, 77, 78,80,81,82,83,84,85]; // Array of column indices to calculate the sums
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
	table.draw();
    compareFooterValues();
    table.on('draw.dt', function() {
        compareFooterValues();
    });

    $("#btn-search").on("click", function() {
    	console.log('this is search')
    	console.log('this is whats thi  yaar')
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
		 console.log("table.rows[0].cells.length" + numColumns);
		 numColumns = numColumns - 7;
	     let data = [];
		 // Iterate through each column
		 for (var colIndex = 0; colIndex < numColumns; colIndex++) {
		     var columnIsEmpty = false; // Assume the column is empty initially
		     let data2 = [];
		     // Iterate through each row to check the content of the cells in the current column
		     for (var rowIndex = 0; rowIndex < table.rows.length; rowIndex++) {
		         var cell = table.rows[rowIndex].cells[colIndex];
		         
		        
		        // console.log('cell.innerHTML.trim()',cell.innerHTML.trim())
		         // Check if the cell is empty
		         if (cell.innerHTML.trim() !== "" && cell.innerHTML.trim() !== "0.00") {
		        	 columnIsEmpty = false; // The column is not empty if any cell has content
		         } else {
		        	 columnIsEmpty = true;
		         }
		         data2.push(columnIsEmpty)
		         if(cell.innerHTML.trim() === "<b>MOQ Qty</b>"){
		        	 console.log('this is removed',data2,rowIndex,colIndex, numColumns)
		        	 for (var removeIndex = 0; removeIndex < table.rows.length; removeIndex++) {
		    		//table.rows[removeIndex].cells[colIndex].style.display = 'none'
			    		}
		         }
		         
		         if(cell.innerHTML.trim() === "<b>MOQ Val(RS)</b>"){
		        	 console.log('this is <b>MOQ Val(RS)</b>',data2,rowIndex,colIndex)

		        	 for (var removeIndex = 0; removeIndex < table.rows.length; removeIndex++) {
				    		//table.rows[removeIndex].cells[colIndex].style.display = 'none'
					   }
		         }
		     }
		     data2= data2.slice(1);
		    if(data2.indexOf(false) === -1){
		    	for (var removeIndex = 0; removeIndex < table.rows.length; removeIndex++) {
		    		//table.rows[removeIndex].cells[colIndex].style.display = 'none'
			    		}
		     } 
		 }	
		var Year=$('#yearDropdown').val();
		var Month=$('#Month').val();
		if(currentMonth==Month  && currentYear==Year){
			location.reload();
		}else{
			$.ajax({
			    type: "GET",
			    url: "getDistributionFooterListBasedOnFiltert",
			    contentType: 'application/json',
			    data: "Year=" + Year + "&Month=" + Month,
			    success: function(response) {
			        data = jQuery.parseJSON(response);
			        var footerTable = $('#example').DataTable().table().footer();
			        var footerRow = $(footerTable).find('tr').eq(0); // Assuming the footer row is the second row
			        //table.clear().draw()
			        
			        var cells = $(footerRow).find('th');
			        console.log(cells,'cells');
			        for (var j = 3; j < cells.length; j++) {
			        	console.log(cells,'cells');
			            $(cells[j]).text(''); 
			        }

			        // Assign new data to footer
			        var rowData = data[0]; 
			        console.log(rowData)
			        for (var j = 3; j < rowData.length + 3; j++) {
			            var currentCell = $(footerRow).find('th').eq(j);
			            $(currentCell).text(rowData[j]); // Assign data to the cell
			        }
			    }
			});


			
			$.ajax({
				type : "GET",
				url : "getDistributionListBasedOnFilter",
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
			      	    
			      	 
			      	  var receicedqtyIndex = 82; // received qty
			          var PriceIndex = 79; // price
			          var targetColumnIndex = 83; // target column received value

			          table.rows().every(function(rowIdx, tableLoop, rowLoop) {
			            var row = this;
			            var ReceivedQty = parseFloat(row.data()[receicedqtyIndex]);
			            var Price = parseFloat(row.data()[PriceIndex]);
			            var product = isNaN(ReceivedQty) || isNaN(Price) ? 0 : (ReceivedQty * Price);

			            row.cell(rowIdx, targetColumnIndex).data(product.toFixed(2));
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
			      	  
				}
			})
		}
					
		})
});


function handleBlur(currentRow){
	console.log('this is consoel from tr')
}
</script>
	<script src="dist/js/sweetalert2.min.js"></script>
	<link rel="stylesheet" href="dist/css/sweetalert2.min.css" />

</body>
</html>
