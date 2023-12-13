<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" session="true"%>
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
</script>
  <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/apexcharts"></script>
     <script>
    document.addEventListener("DOMContentLoaded", function() {
    	  if(document.getElementById('userRole').textContent.split(':')[1].trim() == 'Indent Manager'){
    	     $.ajax({
    	 			type : 'GET',
    	 			url : 'getBudgetDetails',
    	 			//dataType : 'json',

    	 			success : function(response) {
    	 				var allData =jQuery.parseJSON(response)[0];
    	 	        	console.log('response',allData)
    	 	        	var chartData = [];
    	 	        	var Cumulative = '';
    	 	        	var yearlyBudget = '';
    	 	        	var balancevalue = '';
    	 	        	if(typeof allData === 'undefined'){
    	 	        		console.log('if is 0')
    	 	        		chartData = [0,0,0]
    	 	        		Cumulative = 'Cumulative Indent Value Rs:' + 0; 
    	 	        		balancevalue = 'Balance Budget Value Rs:'+ 0;
    	 	        		yearlyBudget = 'Yearly Budget Value Rs:'+ 0;
    	 	        		document.getElementsByClassName('NoBudgetIsDefined')[0].style.visibility = 'visible';
    	 	        		document.getElementsByClassName('NoBudgetIsDefined')[1].style.visibility = 'visible'

    	 	        		return;
    	 	        	}else {
        	 	        	chartData = [allData[3]-allData[4],allData[4]];
        	 	        	yearlyBudget = 'Yearly Budget Value Rs:'+ allData[3];
        	 	        	balancevalue = 'Balance Budget Value Rs:'+ allData[4].toFixed(2);
        	 	        	console.log('Number(allData[3]hhhhh)',Number(allData[3]),Number(allData[3])- Number(allData[4]))
    	 	        		Cumulative = 'Cumulative Indent Value Rs:' + (Number(allData[3])- Number(allData[4])).toFixed(2); 
        	 	        	console.log('allData[3]),allData[3]-allData[4],allData[4]',allData[3],allData[3]-allData[4],allData[4])
        	 	        	document.getElementsByClassName('NoBudgetIsDefined')[0].style.visibility = 'hidden'
        	 	           document.getElementsByClassName('NoBudgetIsDefined')[1].style.visibility = 'hidden'

    	 	        	}
    	 	        	console.log('chartData',chartData)
    	 	           var options = {
    	 			        chart: {
    	 			          type: 'pie',
    	 			          width: '700',
    	 			          height: '700'
    	 			        },
    	 			        series: chartData,
    	 			        labels: [Cumulative, balancevalue,yearlyBudget]
    	 			      };

    	 			      var chart = new ApexCharts(document.querySelector("#chart"), options);
    	 			      chart.render();
    	 			}
    	 		});
    	     }
   
    });
  </script>
  
   <script>
    document.addEventListener("DOMContentLoaded", function() {
    	         $.ajax({
        url: 'getBudgetDataforChart', 
        method: 'GET', 
        success: function(response) {
        	var costCenter;
        	var costCenterDetails;
            var parsedData = JSON.parse(response);
            sessionStorage.setItem("allData", response);
            console.log('allData', parsedData);
            if(document.getElementById('userRole').textContent.split(':')[1].trim() == 'Indent Manager'){
            	costCenter = document.getElementById('userName').textContent.split(':')[1].trim();
            	const index = parsedData.findIndex(function(element) {
            		  return element[0] == Number(costCenter);
            		});
            	if(index != -1){
            	costCenterDetails = parsedData[index]; 
            	 var options = {
            		        chart: {
            		          type: 'bar',
            		          height: '500' // Set the desired height here
            		        },
            		        series: [{
            		          name: 'Indent Amount',
            		          data: [costCenterDetails[22], costCenterDetails[23], costCenterDetails[24], costCenterDetails[13], costCenterDetails[14], costCenterDetails[15], costCenterDetails[16], costCenterDetails[17], costCenterDetails[18],costCenterDetails[19],costCenterDetails[20],costCenterDetails[21]]
            		        }],
            		        xaxis: {
            		          categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep','Oct','Nov','Dec']
            		        }
            		      };

            		      var chart = new ApexCharts(document.querySelector("#chart3"), options);
            		      chart.render();
            	}
            }
            },
        error: function(error) {
            console.error('Error fetching data:', error);
        }
    }); 
    });
  </script>
<style>
.resp-iframe {
	position: fixed;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	border: 0;
}

/* #pieChart {
    width: 554px !important;
    height: 554px !important;
	margin: 10px auto;
} */

#pie-chart-container {
	display: flex;
	align-items: center;
	justify-content: center;
	flex-direction: column;
}

/* #chart-notes {
	margin-top: 10px;
	font-size: 20px;
	position: relative;
	top: 50%;
	left: 50%;
	transform: translate(-50%, -50%);  
	background-color: #f9f9f9;
    border: 1px solid #ccc;
} */

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

.apexcharts-legend {

    position: absolute;
    left: 410px !important;
    top: 5px;
    right: 5px;
}
</style>
</head>

<body>
	<div class="preloader">
		<div class="lds-ripple">
			<div class="lds-pos"></div>
			<div class="lds-pos"></div>
		</div>
	</div>
	<div id="main-wrapper" data-sidebartype="full" class="mini-sidebar">
		 <jsp:include page="/WEB-INF/jsp/views/header/header.jsp"></jsp:include> 
		<jsp:include page="/WEB-INF/jsp/views/header/sideBar.jsp"></jsp:include> 
		<div class="page-wrapper" style="margin-top:80px;">
			<div class="row" >
				<div class="col-sm-12">
					<%-- <div class="filter-controls">
						<div class="col-md-3 filter-item">
							<label for="Month" class="month-label">Month:</label> <select
								id="Month" name="Month" class="form-control" aria-invalid="true"
								required="required" style="width: 100%;">
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
							<label for="yearDropdown" class="year-label">Year:</label> <select
								id="Year" class="form-control" aria-invalid="true"
								required="required" style="width: 97%;">
								<c:forEach items="${years}" var="year">
									<option value="${year}">${year}</option>
								</c:forEach>

							</select>
						</div>
						<div class="col-md-3 filter-item">
							<button class="btn btn-primary btn-search" onclick="search()">Search</button>
						</div>
					</div> --%>
					<span style="font-weight: bold;margin-left:208px">Budget Details</span><span style="font-weight: bold;float:right;margin-right:258px">Indent Details</span>
					
					
				</div>
				 <div class="col-sm-6 col-lg-6 col-md-6" style="margin-top: 12px;">
					<div id="chart"></div>
					<div class="NoBudgetIsDefined">
  No Budget is defined
  <style>
  /* Basic styling */
.NoBudgetIsDefined {
  font-family: Arial, sans-serif;
  font-size: 16px;
  color: #333;
  background-color: #01AFAE;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  margin-top: 200px !important;
}

/* Center the div */
.NoBudgetIsDefined {
  margin: 0 auto;
  width: 60%; /* Adjust width as needed */
}

/* Text alignment and spacing */
.NoBudgetIsDefined {
  text-align: center;
  line-height: 1.5;
}

/* Adding shadow effect */
.NoBudgetIsDefined {
  box-shadow: 2px 2px 5px rgba(0, 0, 0, 0.1);
}

/* Hover effect */
.NoBudgetIsDefined:hover {
  background-color: #01AFAE;
  transition: background-color 0.3s ease;
}
  
  </style>
</div>
				</div>
				 <div class="col-sm-6 col-lg-6 col-md-6">
	           <div id="chart3"></div>
	           <div class="NoBudgetIsDefined">
  No Indent Data
  </div>
				</div>   
			</div>
		</div>

	</div>

	<script type="text/javascript">
	console.log('murali')
		history.pushState(null, null, location.href);
		window.onpopstate = function() {
			history.go(1);
		};
	</script>

	<!-- All Jquery -->
	<!-- ============================================================== -->
	<script src="assets/libs/jquery/dist/jquery.min.js"></script>
	<!-- Bootstrap tether Core JavaScript -->
	<script src="assets/libs/popper.js/dist/umd/popper.min.js"></script>
	<script src="assets/libs/bootstrap/dist/js/bootstrap.min.js"></script>
	<script
		src="assets/libs/perfect-scrollbar/dist/perfect-scrollbar.jquery.min.js"></script>
	<script src="assets/extra-libs/sparkline/sparkline.js"></script>
	<!--Wave Effects -->
	<script src="dist/js/waves.js"></script>
	<!--Menu sidebar -->
	<script src="dist/js/sidebarmenu.js"></script>
	<!--Custom JavaScript -->
	<script src="dist/js/custom.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</body>

</html>