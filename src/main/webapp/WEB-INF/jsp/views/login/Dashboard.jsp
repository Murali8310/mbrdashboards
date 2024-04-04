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
    	 	        		chartData = [0,0]
    	 	        		Cumulative = 'Cumulative Indent Value Rs:' + 0; 
    	 	        		balancevalue = 'Balance Budget Value Rs:'+ 0;
    	 	        		yearlyBudget = 'Yearly Budget Value Rs:'+ 0;
    	 	        		//document.getElementsByClassName('NoBudgetIsDefined')[0].style.visibility = 'visible';
    	 	        		//document.getElementsByClassName('NoBudgetIsDefined')[1].style.visibility = 'visible'

    	 	        		return;
    	 	        	}else {
        	 	        	chartData = [allData[3]-allData[4],allData[4]];
        	 	        	yearlyBudget = 'Yearly Budget Value Rs:'+ allData[3];
        	 	        	balancevalue = 'Balance Budget Value Rs:'+ allData[4].toFixed(2);
        	 	        	console.log('Number(allData[3]hhhhh)',Number(allData[3]),Number(allData[3])- Number(allData[4]))
    	 	        		Cumulative = 'Cumulative Indent Value Rs:' + (Number(allData[3])- Number(allData[4])).toFixed(2); 
        	 	        	console.log('allData[3]),allData[3]-allData[4],allData[4]',allData[3],allData[3]-allData[4],allData[4])
        	 	        	//document.getElementsByClassName('NoBudgetIsDefined')[0].style.visibility = 'hidden'
        	 	           //document.getElementsByClassName('NoBudgetIsDefined')[1].style.visibility = 'hidden'

    	 	        	}
    	 	        	console.log('chartData',chartData)
    	 	          var options = {
    chart: {
        type: 'pie',
        height: '450'
    },
    series: chartData,
    labels: ['Cumulative Indent Value Rs.(Incl. PO, Route)', 'Balance Budget Value (Rs)'],
    legend: {
        position: 'bottom'
    },
    dataLabels: {
        style: {
            fontSize: '12px !important', // You can adjust the font size if needed
            padding: '10px !important',
            colors: ['black'] // Adjust the padding value as per your requirement
        }
    }
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
            	 var today = new Date();
            	  var year = today.getFullYear();
            	  let currentDate = new Date();

            	// Get the current month (returns a number from 0 to 11)
            	let currentMonth = currentDate.getMonth() + 1;
            	console.log('currentMonth',currentMonth)
                      if(currentMonth < 4){
                    	  year = Number(year)- 1; 
                      } else {
                    	  year = Number(year);
                      }
console.log('year',year)
            	const index = parsedData.findIndex(function(element) {
            		  return element[0] == Number(costCenter) && (element[1] === year);
            		});
            	if(index != -1){
            	costCenterDetails = parsedData[index]; 
            	console.log('costCenterDetails',costCenterDetails)
            	var options = {
            		    chart: {
            		        type: 'bar',
            		        height: '400' // Set the desired height here
            		    },
            		    series: [{
            		        name: 'Indent Amount',
            		        data: [costCenterDetails[13], costCenterDetails[14], costCenterDetails[15], costCenterDetails[16], costCenterDetails[17], costCenterDetails[18],costCenterDetails[19],costCenterDetails[20],costCenterDetails[21],costCenterDetails[22], costCenterDetails[23], costCenterDetails[24]]
            		    }],
            		    xaxis: {
            		        categories: ['Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep','Oct','Nov','Dec','Jan', 'Feb', 'Mar'],
            		        title: {
            		            text: 'Month',
            		            style: {
            		                fontSize: '15px' // Adjust the font size for y-axis title
            		            }
            		        }
            		    },
            		    yaxis: {
            		        title: {
            		            text: 'Indent value (Rs)',
            		            style: {
            		                fontSize: '15px' // Adjust the font size for y-axis title
            		            }
            		        }
            		    },
            		    dataLabels: {
            		        style: {
            		            fontSize: '12px !important', // You can adjust the font size if needed
            		            padding: '10px !important',
            		            colors: ['black'] // Adjust the padding value as per your requirement
            		        }
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

      #chart .apexcharts-legend-text {
     color: black !important;
    font-size: 15px !important; 
    font-weight: bold !important;
    font-family: Helvetica, Arial, sans-serif;
           }
 #chart .apexcharts-legend {
inset: auto 0px 1px;
    position: absolute;
    max-height: 250px;
    margin-left: -400px !important;
}
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
.card {
  height: 254px;
  border-radius: 10px;
  background: #e0e0e0;
  box-shadow: 15px 15px 30px #bebebe,
             -15px -15px 30px #ffffff;
}

.apexcharts-legend.apx-legend-position-bottom.apexcharts-align-center, .apexcharts-legend.apx-legend-position-top.apexcharts-align-center {
    justify-content: left !important;
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
		<div class="page-wrapper row" style="min-height: 100vh !important;text-align: center;
    justify-content: center;
    align-items: center;">
				<div class="col-sm-5 col-lg-5 col-md-5" style="text-align: center;font-weight: bold;">
				<h4 style="font-weight:bold">Budget vs Actual</h4>
				</div>
				<div class="col-sm-5 col-lg-5 col-md-5" style="text-align: center;font-weight: bold;">
                <h4 style="font-weight:bold">Budget Uitilized (Monthly)</h4>
				</div>
				 <div class="col-sm-12 col-lg-5 col-md-5 card" style="margin-right:20px" id="chart">
				</div>
				 <div class="col-sm-12 col-lg-5 col-md-5 card mb-4" id="chart3">
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

<script>

document.body.style.zoom = 0.85

</script>

</html>