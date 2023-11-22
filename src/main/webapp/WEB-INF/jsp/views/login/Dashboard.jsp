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
</style>
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
		
		<div class="page-wrapper" style="margin-top:80px;">
			<div class="row" >
				<div class="col-sm-12">
					<div class="filter-controls">
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
					</div>
				</div>
				<div class="col-sm-6 col-lg-6 col-md-6">
					<canvas id="pieChart"></canvas>
					<div id="chart-notes"></div>
				</div>
				<!-- <div class="col-sm-12 col-lg-3 col-md-3 col-12">
					
				</div> -->
			</div>

			<jsp:include page="/WEB-INF/jsp/views/header/footer.jsp"></jsp:include>
		</div>

	</div>

	<script type="text/javascript">
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
<script>
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

var ctx = document.getElementById('pieChart').getContext('2d');
var color=['#FF6384', '#36A2EB', '#FFCE56']
var data = { 
    labels: ['Yearly Budget Value', 'Cumulative Indent Value', 'Balance Budget Value'],
    datasets: [{
    	label: 'Sales',
        data: [0, 0, 0], // Data values for each segment
        backgroundColor:color  // Colors for each segment
    }]
};

// Create a new pie chart
var myPieChart = new Chart(ctx, {
    type: 'pie',
    data: data,
    options: {
        responsive: true,
    }
});


$.ajax({
    url: 'getBudgetDataforChart', 
    method: 'GET', 
    success: function(response) {
        var parsedData = JSON.parse(response);
        sessionStorage.setItem("allData", response);
        search();
        },
    error: function(error) {
        console.error('Error fetching data:', error);
    }
});

function updatePieChart(data) {
	var backgroundColors = ['#FF6384', '#36A2EB', '#FFCE56']; // Your slice colors
	myPieChart.data.datasets[0].data = data[0];
    myPieChart.data.datasets[0].backgroundColor = backgroundColors;
    myPieChart.data.datasets[0].borderColor = 'transparent';	
    myPieChart.update();
    var chartNotes = document.getElementById('chart-notes');
    var labels = myPieChart.data.labels;
    var values = myPieChart.data.datasets[0].data;
    console.log( myPieChart.data.datasets[0].data,' myPieChart.data.datasets[0].data')
	    for (var i = 0; i < labels.length; i++) {
	    	 var note = document.createElement('div');
	         note.className = 'chart-note';
	         note.style.color  = color[i];
	         note.innerHTML = labels[i] + ': ' + values[i];
	         chartNotes.appendChild(note);
	    }
}

function search(){
const textContent = document.getElementById("chart-notes");
// Reset the inner HTML to an empty string
textContent.innerHTML = "";
myPieChart.data.datasets[0].data = [];
const allData = JSON.parse(sessionStorage.getItem("allData"));
const selectedMonth = document.getElementById('Month').value;
const selectedYear = document.getElementById('Year').value;
console.log('search',selectedMonth, selectedYear)
updatePieChartData(allData ,selectedMonth ,selectedYear);
}

function updatePieChartData(totalData,monthName,yearName) {
	console.log(totalData)
const yearIndex = totalData.findIndex(function(element) {
  return (element[1] == yearName);
});
 const months = [
	  "April",
	  "May",
	  "June",
	  "July",
	  "August",
	  "September",
	  "October",
	  "November",
	  "December",
	  "January",
	  "February",
	  "March"
	];
 const monthIndex = months.findIndex(function(element) {
	  return element == monthName;
	});
 let cumulativeAmount = 0;
 for(let index = 13; index< (13 + monthIndex);index++){
	 if(totalData[yearIndex][index] != null){
	cumulativeAmount = cumulativeAmount + totalData[yearIndex][index];
	 }
 }
 	var backgroundColors = ['#FF6384', '#36A2EB', '#FFCE56']; // Your slice colors
	myPieChart.data.datasets[0].data = [];
 	console.log('checking',totalData[yearIndex])
	myPieChart.data.datasets[0].data.push(totalData[yearIndex][8]);
	myPieChart.data.datasets[0].data.push(cumulativeAmount);
	myPieChart.data.datasets[0].data.push(totalData[yearIndex][8]-cumulativeAmount);
    myPieChart.data.datasets[0].backgroundColor = backgroundColors;
    myPieChart.data.datasets[0].borderColor = 'transparent';
    myPieChart.update();
    var chartNotes = document.getElementById('chart-notes');
    var labels = myPieChart.data.labels;
    var values = myPieChart.data.datasets[0].data;
   // console.log( myPieChart.data.datasets[0].data,' myPieChart.data.datasets[0].data')
	    for (var i = 0; i < labels.length; i++) {
	    	 var note = document.createElement('div');
	         note.className = 'chart-note';
	         note.style.color  = color[i];
	         note.innerHTML = labels[i] + ': ' + values[i];
	         chartNotes.appendChild(note);
	    }
}
</script>

</body>

</html>