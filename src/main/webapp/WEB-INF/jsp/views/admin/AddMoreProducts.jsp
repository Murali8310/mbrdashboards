<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ISCM Stationery</title>
<link rel="icon" type="image/png" sizes="16x16"
	href="assets/images/titan-logo.png">
	<link href="assets/libs/magnific-popup/dist/magnific-popup.css"
	rel="stylesheet">
<!-- Custom CSS -->
<link href="assets/libs/magnific-popup/dist/magnific-popup.css"
	rel="stylesheet">
<link href="dist/css/loading.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet"
	href="https://cdn.materialdesignicons.com/5.9.55/css/materialdesignicons.min.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

	<script src="dist/js/sweetalert2.min.js"></script>
		<link rel="stylesheet" href="dist/css/sweetalert2.min.css" /> 
		
<link href="dist/css/style.min.css" rel="stylesheet">

</head>
<style>
.el-element-overlay .el-card-item {
	position: relative;
	padding-bottom: 10px;
}
.col, .col-1, .col-2, .col-3, .col-4, .col-5, .col-6, .col-7, .col-8, .col-9, .col-10, .col-11, .col-12, .col-auto, .col-lg, .col-lg-1, .col-lg-2, .col-lg-3, .col-lg-4, .col-lg-5, .col-lg-6, .col-lg-7, .col-lg-8, .col-lg-9, .col-lg-10, .col-lg-11, .col-lg-12, .col-lg-auto, .col-md, .col-md-1, .col-md-2, .col-md-3, .col-md-4, .col-md-5, .col-md-6, .col-md-7, .col-md-8, .col-md-9, .col-md-10, .col-md-11, .col-md-12, .col-md-auto, .col-sm, .col-sm-1, .col-sm-2, .col-sm-3, .col-sm-4, .col-sm-5, .col-sm-6, .col-sm-7, .col-sm-8, .col-sm-9, .col-sm-10, .col-sm-11, .col-sm-12, .col-sm-auto, .col-xl, .col-xl-1, .col-xl-2, .col-xl-3, .col-xl-4, .col-xl-5, .col-xl-6, .col-xl-7, .col-xl-8, .col-xl-9, .col-xl-10, .col-xl-11, .col-xl-12, .col-xl-auto {
    position: relative;
    width: 100%;
    min-height: 1px;
    padding-right: 5px;
    padding-left: 5px;
}

.el-element-overlay .el-card-item .el-overlay-1 img {
	display: block;
	position: relative;
	-webkit-transition: all .4s linear;
	-o-transition: all .4s linear;
	transition: all .4s linear;
	width: 73%;
	height: 50%;
	padding-left: 76px;
}

.container-fluid {
	margin-bottom: 30px;
}

.page-wrapper>.container-fluid {
	padding: 10px;
	min-height: calc(120vh - 180px)
}

.sidebar-item .first-level {
	height: 500px;
	overflow-y: auto;
}


.card {
margin-bottom: 10px;
}

.sidebar-item {
	margin-left: 10px;
}

.logo-text {
	padding: 5px 5px 5px 0px !important;
}

.topbar .top-navbar .navbar-header {
	width: 250px;
	line-height: 50px !important;
}

.highlight {
}

.highlight1 {
	
}

.bold-and-large {
  font-weight: 800;
      font-size: 18px !important;

  color: red !important;
  text-align:center;
}
.form-control {
padding:0px !important;
}
.row {
     margin-right: 0px !important; 
     margin-left: 0px !important;  
}
</style>
<body style="zoom: 85%;">
	<div class="preloader" style="display: none;">
		<div class="lds-ripple">
			<div class="lds-pos"></div>
			<div class="lds-pos"></div>
		</div>
	</div>

	<div id="main-wrapper" data-sidebartype="full" class="mini-sidebar">
		
		<jsp:include page="/WEB-INF/jsp/views/header/header.jsp"></jsp:include>
		
		<aside class="left-sidebar" style="position:fixed;" data-sidebarbg="skin5">
			<!-- Sidebar scroll-->
			<div class="scroll-sidebar">
				<!-- Sidebar navigation-->
				<nav class="sidebar-nav">
					<ul id="sidebarnav" class="p-t-30">
						<li class="sidebar-item"><label
							class="sidebar-link hide-menu">
								<button class="btn btn-danger" style="width: 100%;"
									id="resetCategoriesID">Reset</button> &nbsp;&nbsp;
								<button class="btn btn-success" style="width: 100%;"
									id="submitCategoriesID">Apply</button>
						</label></li>
						<li class="sidebar-item"><a
							class="sidebar-link has-arrow glyphicon "
							href="javascript:void(0)" aria-expanded="false"><i
								class="fa fa-list"></i> <span class="hide-menu">Category
							</span></a>
							<ul aria-expanded="false" class="collapse  first-level"
								style="background-color: #ffffff14;">
								<c:forEach items="${CategoryList}" var="Category">
									<li class="sidebar-item"><label
										class="sidebar-link hide-menu"> <input type="checkbox"
											id="Category" name="Category" value="${Category[0]}">&nbsp;
											${Category[0]}
									</label></li>
								</c:forEach>
							</ul></li>



					</ul>
				</nav>
				<!-- End Sidebar navigation -->
			</div>
			<!-- End Sidebar scroll-->
		</aside>
		<div class="page-wrapper" style="background-color: #d9d9cd;">
			<div class="page-breadcrumb">
				<div class="row">
					<div class="col-12 d-flex no-block align-items-center">
						<h4 class="page-title">Add More Products</h4>
						<div class="ml-auto text-right">
							<nav aria-label="breadcrumb">
								<ol class="breadcrumb">
									<li class="breadcrumb-item"><a href="landPage">Home</a></li>
									<li class="breadcrumb-item active" aria-current="page">
										Add More Products <!-- <a href="productDetails">Product Details</a> -->
									</li>
								</ol>
							</nav>
						</div>
					</div>
				</div>
			</div>
			
				<style>
						/* Style for the cell */
.cell {
    display: flex;
    flex-wrap: wrap;
    margin-bottom: 10px;
}

/* Style for the title */
.title {
   /* flex: 0 0 40%;  *//* Adjust width of the title */
    font-weight: bold;
    margin-left: 30px;
        font-size: 18px;
    color: white;
}

/* Style for the value */
.value {
    flex: 0 0 60%; /* Adjust width of the value */
}

/* Style for the highlight */
.highlight {
    color: white !important; /* Adjust highlight color */
        margin-left: 3px;
    /* Any other highlight styles */
}
</style>
			<div class="container-fluid2 card-total card-static">
					<div  id="totalOutput2" class="row"style="background-color: #01AFAE; align-items: baseline;padding-top: 5px;margin-left:13px !important;width: 98%;box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.5);
    border-radius: 10px;
    margin-bottom: 2px;">
					<div style="text-align: start; font-size: 18px; background-color: #01AFAE;color: white;padding: 6px;
    border-radius: 10px;
    margin-bottom: 20px;
        height: 27px;"></div>
					</div>
					<div class="cell" style="text-align: start; font-size: 18px; background-color: #01AFAE;color: white;padding: 6px;
    border-radius: 10px;
    margin-bottom: 20px;
        height: 49px;
    width: 98%;
    margin-left: 13px;">
						<div class="col-md-4 col-lg-4 col-12" id="IndentNumber"
							style="text-align: start; font-size: 18px;    font-weight: bold;
							 "></div>
						<div class="col-md-4 col-lg-4 col-12" id="totalOutput"
							style="text-align: start; font-size: 18px;    font-weight: bold;
							 "></div>
						<div class="col-md-4 col-lg-4 col-12" style="text-align: end;">
						
							<a href="indentTransactionUpdates"> <input type="button"
								class="btn btn-primary" id="expiryDatebut" value="Cancel">
							</a> <a class="btn btn-success" id="submitId" onclick="submit()">Update</a>
						</div>
					</div>
				<div class="row el-element-overlay" id="el-element-overlay"></div>
			<style>
.footer {
	background-color: Teal;
	padding-bottom: 1px;
	height: 300px;
}

footer h6 {
	color: white;
	text-transform: uppercase;
	text-align: left;
}

footer a {
	text-transform: uppercase;
	color: white;
	text-align: left;
}

footer li {
	list-style-type: none;
	text-transform: uppercase;
	text-align: left;
}

footer ul {
	padding-left: 0px;
}

.footer-copyright {
	color: #fffffff0;
	text-align: center;
	font-weight: 600;
	font-size: 11px;
}

.footer-copyright:hover {
	color: white;
}

.footer-copyright span:hover {
	color: Teal;
	font-weight: bolder;
}
</style>




			<footer class="footer">

				<div class="footer-copyright">
					<p>
						© 2019 <span>TITAN COMPANY LTD.</span> ALL RIGHTS RESERVED.
					</p>
				</div>
			</footer>
				<!-- <div style="float: right; margin-bottom: 31px;">
					<div class="row">

						<div class="col-md-6 col-lg-6 col-12">
							<a href="indentTransactionUpdates"> <input type="button" onclick="localStorage.removeItem('totalQuantity')"
								class="btn btn-primary" id="expiryDatebut" value="Cancel">
							</a>
						</div>
						<div class="col-md-6 col-lg-6 col-12">
							<a class="btn btn-success" id="submitId" onclick="submit()">Update</a>

						</div>
					</div>

				</div> -->
			</div>
		</div>
	</div>
</body>

<script type="text/javascript">
$(document)
.ready(
		function() {
			getProductByIndent();
			getBudgetDetails();
		});


function getProductByIndent(){
	$.ajax({
		type : 'GET',
		url : 'GetAllProductsByIndent',
		dataType : 'json',

		success : function(response) {
			var productListElement = document
					.getElementById('el-element-overlay');
			var productListHTML = '';

			var totalPrice=0;
			var totalQuantity=0;
			
				for (var i = 0; i < response.length; i++) {
					var quantity = parseInt(response[i][10]);
					var price = parseFloat(response[i][4]);
					if(quantity >=0){
					totalPrice += price * quantity;
					totalQuantity += quantity;}
				}
				localStorage.setItem('totalQuantity', totalQuantity);
				
				totalPrice = totalPrice.toFixed(2);
				var output = "<span class='title' style='background: red;padding: 2px;border-radius: 3px;'>Indent Value: ₹</span><span style='background: red;padding: 2px;border-radius: 3px;' class='highlight' style='font-size: 18px;font-weight: bold;'>" + totalPrice
						+ "</span> <span style='background: red;padding: 2px;border-radius: 3px;' class='title'>No of items:</span> <span style='background: red;padding: 2px;border-radius: 3px;' class='highlight'>" + totalQuantity
						+ "</span>";
				$('#totalOutput').html(output);
			
			for (var i = 0; i < response.length; i++) {
				var product = response[i];
				const productImage = document
						.getElementById("productImage");
				
			if(product[10]!=0){ 
			var output = "Indent Number: <span class='highlight1' style='font-size: 18px;font-weight: bold;'>" +  response[0][8]
					+ "</span> ";
			$('#IndentNumber').html(output);
			}
			if(product[10]==0){
				const imageSource = 'product/'
						+ product[0] + '.png';
				// productImage.src = imageSource;
				//console.log(imageSource)
				  const defaultImage = 'assets/images/No_Image_Availabl.png';
				//console.log(response, 'response')
				productListHTML += '<div class="col-lg-4 col-md-6 col-12">';
				productListHTML += '<div class="card">';
				productListHTML += '<div class="el-card-item">';
				productListHTML += '<div class="el-card-avatar el-overlay-1"';
				productListHTML += 'style="width: 100%; text-align: center">';
			//productListHTML += '<img class="ImageGalleryID" id="productImage" src="'+ imageSource +'" alt="Image Not Found" onerror="this.onerror=null;this.src=`${default2}`"';
						
			//	productListHTML += '			style="height: 240.86px;">';
			  productListHTML += '<img class="ImageGalleryID" id="productImage" src="'+ imageSource +'" alt="Image Not Found"';
					productListHTML += 'style="height: 240.86px;" onerror="this.src=\'' + defaultImage + '\';">';
				productListHTML += '	<div class="el-overlay"></div>';
				productListHTML += '</div>';
				productListHTML += ' <div class="el-card-content " style="padding-left: 10px">';
				productListHTML += '<h5 class="product" class="m-b-0" style="text-align: start;">'
						+ product[1] + '</h5>';

				productListHTML += '<div class="row">';
				productListHTML += '<div class="col-7">';
				productListHTML += '	<h5 class="m-b-0" style="text-align: start;">';
				productListHTML += '		<label class="price" id="price">₹ '
						+ product[4] + '</label>';
				productListHTML += '		<label style="border:1px solid lightgrey;left:5px;">UOM : '
						+ product[5] + '</label>';
				productListHTML += '		<label class="productID" style="left:5px;">Product ID : '
						+ product[0] + '</label>';
				productListHTML += '	</h5>';
				productListHTML += '</div>';
				productListHTML += '<div class="col-5 ">';
				productListHTML += '	<div class="form-group d-flex justify-content-between"';
				 productListHTML += '	style="align-items: center;">';
				productListHTML += '	<a onclick="decrementCount(this)"';
				productListHTML += '		class="btn btn-sm btn-decre decreme"><i';
			    productListHTML += '		class="fas fa-minus-square fa-2x"></i></a> <input disabled type="text"';
				productListHTML += '		id="countInput'
						+ i
						+ '" name="quantity" class="form-control count-input  bold-and-large" id="count-input"';
				productListHTML += '		value="' + product[9] + '"> <a';
				productListHTML += '			onclick="incrementCount(this)" class="btn bnt-sm btn-incre "><i';
				productListHTML += '			class="fas fa-plus-square fa-2x"></i></a>';
				productListHTML += '	</div>';
				productListHTML += '</div>';
				productListHTML += '</div>';
				// Add more properties as needed
				productListHTML += '</div>';
				productListHTML += '</div>';
				productListHTML += '</div>';
				productListHTML += '</div>';
				}

			$('#el-element-overlay').html(
					productListHTML);
		
			}
			calculateTotal();
			
		},
		error : function(xhr, status, error) {
			console.log("AJAX Error: " + error);
		}
	});
}

	function calculateTotal() {
		var countInputs = document.querySelectorAll('.count-input');
		var prices = document.querySelectorAll('.price');
		
		var totalQuantityElement = document.querySelector("#totalOutput .highlight:nth-child(2)");
		var totalPriceElement = document.querySelector("#totalOutput .highlight:nth-child(1)");
		var currentTotalQuantity = parseInt(totalQuantityElement.textContent);
		var currentTotalPrice = parseFloat(totalPriceElement.textContent);
		
		
		$('#totalOutput').html('');
		var totalPrice = 0;
		var totalQuantity = 0;

		 for (var i = 0; i < countInputs.length; i++) {
			var quantity = parseInt(countInputs[i].value);
			var price = parseFloat(prices[i].innerText.replace('₹ ', ''));
			totalPrice += price * quantity;
			totalQuantity += quantity;
		}
		 
		
		totalPrice = totalPrice.toFixed(2);
		currentTotalPrice = currentTotalPrice.toFixed(2);
		totalPrices = +totalPrice + +currentTotalPrice ;
		totalQuantitys = totalQuantity +currentTotalQuantity;
		console.log(totalPrices,'totalPrice');
		console.log(totalQuantitys,'totalQuantity');
		var output = "<span style='background: red;padding: 2px;border-radius: 3px;' class='title'>Indent val: ₹</span><span style='background: red;padding: 2px;border-radius: 3px;' class='highlight'>" + totalPrices.toFixed(2)
				+ "</span> <span style='background: red;padding: 2px;border-radius: 3px;' class='title'>No of items: </span><span style='background: red;padding: 2px;border-radius: 3px;' class='highlight'>" + totalQuantitys
				+ "</span>";
		$('#totalOutput').html(output);

	}
	
	

	function incrementCount(element) {
		var InputArray = [];
		var input = element.parentNode.querySelector('.count-input');
		var productCard = input.closest('.card');
		var productName = productCard.querySelector('.product').innerText;
		var productPrice = productCard.querySelector('.price').innerText;
		var productID = productCard.querySelector('.productID').innerText.split(':')[1].trim();
		var currentValue = parseInt(input.value);
		input.value = currentValue + 1;
		 InputArray.push({
	 			productName : productName,
	 			productPrice : productPrice,
	 			quantity : currentValue + 1,
	 			productID : productID
	 		});
		calculateIncrementTotal(input.value,productPrice);
		var yearlybudget = document.querySelector("#totalOutput2 .highlight:nth-child(1)");
		var monthlyBalanceText = $("#totalOutput2 span.highlight:eq(2)").text();
		var monthlyBalance = parseFloat(monthlyBalanceText.replace(/[^\d.]/g, ''));
		var indentBalanceText = $("#totalOutput2 span.highlight:eq(4)").text();
		var indentBalance = parseFloat(indentBalanceText.replace(/[^\d.]/g, ''));
		var totalAmount = document.querySelector("#totalOutput .highlight:nth-child(4)");
		var total = monthlyBalance -( parseFloat(totalAmount.textContent) -indentBalance);
			
		if(total<=0){
			Swal.fire({
				html: `
			        <div style="font-size: 1.3em;">Total indent value is exceeding monthly value.</div>
			        <div style="font-size: 1.3em;">Do you want to continue?</div>`,
		        icon: "warning",
		        showCancelButton: true,
		        confirmButtonColor: "#3085d6",
		        cancelButtonColor: "#d33",
		        confirmButtonText: "Yes",
		        cancelButtonText: "No",
		      }).then((result) => {
		    	    if (result.isConfirmed) {
		    	    	//alert(parseFloat(totalAmount.textContent))
		    	    	if(parseFloat(totalAmount.textContent)<=parseFloat(yearlybudget.textContent)){
		    	    	
		    	 		$.ajax({
		    	 			type : "POST",
		    	 			url : "tempIndentUpdateCreation",
		    	 			contentType : 'application/json',
		    	 			data : JSON.stringify(InputArray),

			success : function(response) {

		    	 			}
		    	 		}) 
		    	    	}else {
		    	    		Swal.fire({
			    				html: `
			    			        <div style="font-size: 1.3em;">Total indent value is exceeding yearly budget.</div>
			    			        <div style="font-size: 1.3em;">Can not proceed</div>
			    			        `,
			    		        icon: "error",
			    		      }).then((result) => {
						    	    if (result.isConfirmed) {
						    	    	location.reload()
						    	    }})
		    	    	}
		    	    } else if (result.dismiss === Swal.DismissReason.cancel) {
		    	    	getProductByIndent();
						getBudgetDetails();
		    	        console.log("User clicked No");
		    	    }
		    	});
		
		}else {
			 
 	 		$.ajax({
 	 			type : "POST",
 	 			url : "tempIndentUpdateCreation",
 	 			contentType : 'application/json',
 	 			data : JSON.stringify(InputArray),

 	 			success : function(response) {

 	 			}
 	 		}) 
		}
	}
	

	
	function calculateIncrementTotal(value,price) {
		
		console.log(value,'valuevaluevaluevaluevalue');
		console.log(price,'price');
		
		var totalQuantityElement = document.querySelector("#totalOutput .highlight:nth-child(4)");
		var totalPriceElement = document.querySelector("#totalOutput .highlight:nth-child(2)");
		var currentTotalQuantity = parseInt(totalQuantityElement.textContent);
		var currentTotalPrice = parseFloat(totalPriceElement.textContent);
		console.log(totalPriceElement,'totalPriceElement');

		$('#totalOutput').html('');
		var totalPrice = 0;
		var totalQuantity = 0;
		
		var quantity = parseInt(value);
		var price = parseFloat(price.replace('₹ ', ''));
		
		
		totalPrice += price * 1;
		totalQuantity += 1;
	
		totalPrice = totalPrice.toFixed(2);
		currentTotalPrice = currentTotalPrice.toFixed(2);
		
		totalPrices = +currentTotalPrice + +totalPrice  ;
		totalQuantitys =currentTotalQuantity + totalQuantity ;
		console.log(totalPrices,'totalPricDDDDDDDDDDDe');
		console.log(totalQuantitys,'totalQuantity');
		var output = "<span style='background: red;padding: 2px;border-radius: 3px;' class='title'>Total Price: ₹</span><span style='background: red;padding: 2px;border-radius: 3px;' class='highlight'>" + totalPrices.toFixed(2)
				+ "</span> <span class='title' style='background: red;padding: 2px;border-radius: 3px;'>No of items: </span><span style='background: red;padding: 2px;border-radius: 3px;' class='highlight'>" + totalQuantitys
				+ "</span>";
		$('#totalOutput').html(output);

	}
	
	function calculateInputTotal(value,price,prev) { 
		var currentTotalQuantity =0;
		var totalQuantityElement = document.querySelector("#totalOutput .highlight:nth-child(2)");
		var totalPriceElement = document.querySelector("#totalOutput .highlight:nth-child(1)");
		var currentTotalQuantity = parseInt(totalQuantityElement.textContent);
		var currentTotalPrice = parseFloat(totalPriceElement.textContent);
		
		$('#totalOutput').html('');
		var totalPrice = 0;
		var totalQuantity = 0;
		
		var quantity = parseInt(value);
		var cuurentQTY = quantity - prev
		var price = parseFloat(price.replace('₹ ', ''));
		totalPrice = price * cuurentQTY;
		totalPrice = totalPrice.toFixed(2);
		currentTotalPrice = currentTotalPrice.toFixed(2);
		
		totalPrices = +currentTotalPrice + +totalPrice  ;
		totalQuantitys =currentTotalQuantity + cuurentQTY ;
		var output = "<span class='title'>Total Price: ₹</span><span class='highlight'>" + totalPrices.toFixed(2)
				+ "</span> <span class='title'>No of items:</span> <span class='highlight'>" + totalQuantitys
				+ "</span>";
		$('#totalOutput').html(output);
		
	}
	
	function calculateDecrementTotal(value,price) {
		//alert('this is updated')
		var totalQuantityElement = document.querySelector("#totalOutput .highlight:nth-child(4)");
		var totalPriceElement = document.querySelector("#totalOutput .highlight:nth-child(2)");
		var currentTotalQuantity = parseInt(totalQuantityElement.textContent);
		var currentTotalPrice = parseFloat(totalPriceElement.textContent);
		$('#totalOutput').html('');
		var totalPrice = 0;
		var totalQuantity = 0;
		
		var quantity = parseInt(value);
		var price = parseFloat(price.replace('₹ ', ''));
		
	//	alert(value);
		
		totalPrice = price * 1;
		totalQuantity = 1;
		//alert(totalQuantity);
		
		
		//totalQuantity -= quantity;
		
		totalPrice = totalPrice.toFixed(2);
		currentTotalPrice = currentTotalPrice.toFixed(2);
		
		totalPrices = currentTotalPrice - totalPrice  ;
		totalQuantitys =currentTotalQuantity - totalQuantity ;
		console.log(totalPrices,'totalPrice');
		console.log(totalQuantitys,'totalQuantity');
		var output = "<span style='background: red;padding: 2px;border-radius: 3px;' class='title'>Indent Val: ₹</span><span style='background: red;padding: 2px;border-radius: 3px;' class='highlight'>" + totalPrices.toFixed(2)
				+ "</span><span class='title' style='background: red;padding: 2px;border-radius: 3px;'> No of items: </span><span style='background: red;padding: 2px;border-radius: 3px;' class='highlight'>" + totalQuantitys
				+ "</span>";
		$('#totalOutput').html(output);

	}
	
	function decrementCount(element) {
		
		//alert("decrementCount");
		
 		var InputArray = [];
		var input = element.parentNode.querySelector('.count-input');
		var productCard = input.closest('.card');
		var productName = productCard.querySelector('.product').innerText;
		var productPrice = productCard.querySelector('.price').innerText;
		var productID = productCard.querySelector('.productID').innerText;
		var currentValue = parseInt(input.value);
		if (currentValue > 0) {
			input.value = currentValue - 1;
		calculateDecrementTotal(input.value,productPrice);
			InputArray.push({
				productName : productName,
				productPrice : productPrice,
				quantity : currentValue - 1,
				productID : productID
			});
			//console.log(InputArray)
			//calculateTotal();

			$.ajax({
				type : "POST",
				url : "tempIndentUpdateCreation",
				contentType : 'application/json',
				data : JSON.stringify(InputArray),

				success : function(response) {

				}
			})
		} 
	}
	
	$(document).on('focusin', 'input', function(){
	    console.log("Saving value " + $(this).val());
	    $(this).data('val', $(this).val());
	}).on('change','input', function(){
	    var prev = $(this).data('val');
	    var current = $(this).val();
	    console.log("prev " +prev);
	    console.log("current " + current);
	    saveInputdata(this,prev)
	});
	
	function saveInputdata(element,prev) {
		console.log(prev,'pre')
		var InputArray = [];
		var input = element.parentNode
				.querySelector('.count-input');
		var productCard = input.closest('.card');
		var productName = productCard.querySelector('.product').innerText;
		var productPrice = productCard.querySelector('.price').innerText;
		var productID = productCard.querySelector('.productID').innerText;
		var currentValue = parseInt(input.value);
		if (currentValue >= 0) {
			input.value = currentValue ;
			calculateInputTotal(input.value,productPrice,prev);
			}
		
			InputArray.push({
				productName : productName,
				productPrice : productPrice,
				quantity : currentValue,
				productID : productID
			});
		
		var yearlybudget = document.querySelector("#totalOutput2 .highlight:nth-child(1)");
		var monthlyBalanceText = $("#totalOutput2 span.highlight:eq(2)").text();
		var indentBalanceText = $("#totalOutput2 span.highlight:eq(4)").text();
		var monthlyBalance = parseFloat(monthlyBalanceText.replace(/[^\d.]/g, ''));
		var indentBalance = parseFloat(indentBalanceText.replace(/[^\d.]/g, ''));
		//var total =monthlyBalance+indentBalance;
		var totalAmount = document.querySelector("#totalOutput .highlight:nth-child(1)");
		var total = monthlyBalance -( parseFloat(totalAmount.textContent) -indentBalance);
		//alert("input"+total)
		//alert("parseFloat(totalAmount.textContent)"+parseFloat(totalAmount.textContent))
		if(total<=0){
			Swal.fire({
				html: `
			        <div style="font-size: 1.3em;">Total indent value is exceeding monthly value.</div>
			        <div style="font-size: 1.3em;">Do you want to continue?</div>`,
		        icon: "warning",
		        showCancelButton: true,
		        confirmButtonColor: "#3085d6",
		        cancelButtonColor: "#d33",
		        confirmButtonText: "Yes",
		        cancelButtonText: "No",
		      }).then((result) => {
		    	    if (result.isConfirmed) {
		    	    	if(parseFloat(totalAmount.textContent)<=parseFloat(yearlybudget.textContent)){
		    	    	
		   		$.ajax({
				type : "POST",
				url : "tempIndentUpdateCreation",
				contentType : 'application/json',
				data : JSON.stringify(InputArray),

		    					success : function(response) {
		    						getProductByIndent();
		    						getBudgetDetails();
		    					}
		    				})
		    			
		    			}else{
		    				Swal.fire({
			    				html: `
			    			        <div style="font-size: 1.3em;">Total indent value is exceeding yearly budget.</div>
			    			        <div style="font-size: 1.3em;">Can not proceed</div>
			    			        `,
			    		        icon: "error",
			    		      }).then((result) => {
						    	    if (result.isConfirmed) {
						    	    	location.reload()
						    	    }})
		    			}
		    	    } else if (result.dismiss === Swal.DismissReason.cancel) {
		    	    	getProductByIndent();
						getBudgetDetails();
		    	    }
		    	});
		}else{
		var InputArray = [];
		var input = element.parentNode
				.querySelector('.count-input');
		var productCard = input.closest('.card');
		var productName = productCard.querySelector('.product').innerText;
		var productPrice = productCard.querySelector('.price').innerText;
		var productID = productCard.querySelector('.productID').innerText;
		var currentValue = parseInt(input.value);
		if (currentValue >= 0) {
			input.value = currentValue ;
			InputArray.push({
				productName : productName,
				productPrice : productPrice,
				quantity : currentValue,
				productID : productID
			});
			
			console.log('currentValue',currentValue)
			$.ajax({
				type : "POST",
				url : "tempIndentUpdateCreation",
				contentType : 'application/json',
				data : JSON.stringify(InputArray),

				success : function(response) {
				//	getProductByIndent();
				//	getBudgetDetails();
				}
			})
		}}
	}
	
	function submit() {
		
		var quantityInputs = document.getElementsByClassName('count-input');
		var InputArray = [];
		for (var i = 0; i < quantityInputs.length; i++) {
			var quantityInput = quantityInputs[i];
			var quantity = parseInt(quantityInput.value);
			if (quantity > 0) {
				var productCard = quantityInput.closest('.card');
				var productName = productCard.querySelector('.product').innerText;
				var productPrice = productCard.querySelector('.price').innerText;
				var productID = productCard.querySelector('.productID').innerText;

				InputArray.push({
					productName : productName,
					productPrice : productPrice,
					quantity : quantity,
					productID : productID
				});
			}
		}
		console.log('this is changes',quantity)
   var pricelement = document.querySelector("#totalOutput .highlight:nth-child(2)").textContent
   var yearlyAmount = document.getElementById('yearlyAmount').textContent
		if(Number(pricelement) > Number(yearlyAmount)){
			Swal.fire({
						icon : 'error',
		        	  title: 'Indent amount is exceeded the yearly budget!',
						focusConfirm : false,
					})
					return;
		}
		$.ajax({
			type : "POST",
			url : "IndentTransactionUpdate",
			contentType : 'application/json',
			data : JSON.stringify(InputArray),

			success : function(response) {
				console.log(response)
				if(response='Indent Updated SuccessFully'){
				Swal.fire({
					icon : 'success',
					title : response,
					showCloseButton : false,
					focusConfirm : true,

				})
				getBudgetDetails();
				getProductByIndent();
				}else{
					Swal.fire({
						icon : 'error',
		        	  title: JSON.stringify(response),
						focusConfirm : false,
					})
				}
			},	error : function(error) {

				$(".loading").hide();
				Swal.fire({
					icon : 'error',
	        	  title: JSON.stringify(error),
					focusConfirm : false,
				})
			}
		})
	}
	
	function getBudgetDetails(){
				$
				.ajax({
					type : 'GET',
					url : 'getBudgetDetails',
					//dataType : 'json',

					success : function(response) {
						//console.log( jQuery.parseJSON(response)[0] ,'response')
						var budgt =jQuery.parseJSON(response)[0]
						
					/*  var output = "Yearly Budget:   ₹<span class='highlight'>"
							+ budgt[3]  
							+ "</span>   Monthly Budget:   ₹<span class='highlight'>"
							+ budgt[4]   + "</span>  "+ "</span> Cumulative Balance:   ₹<span class='highlight'>"
							+ budgt[5]   + "</span>  "+ "</span> Indended Amount:   ₹<span class='highlight'>"
							+ budgt[6]   + "</span>  "; */
							
							var output = "<div class='cell'><span class='title'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Yearly Budget:   ₹</span> &nbsp; <span id='yearlyAmount' style='font-size: 18px;color: white;'>&nbsp;"
								+ budgt[3]  + "</span>&nbsp;&nbsp; <span class='title'> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Cumulative Actual Value:   ₹ </span>&nbsp; <span style='font-size: 18px;color: white;'>"
								 + budgt[5]   + "</span>&nbsp;&nbsp;   "+ "<span class='title'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Budget bal:   ₹</span> &nbsp; <span style='font-size: 18px;color: white;'>"
								+ budgt[4]   + "</span>&nbsp;&nbsp;  "+ " <span class='title'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Indent Amt(Current month):   ₹</span>  &nbsp;&nbsp;<span style='font-size: 18px;color: white;'>" 
								+ budgt[6]   + "</span>  </div> " ;
								
					$('#totalOutput2').html(output); 
					}
				});
			};

	
	
	$("#submitCategoriesID")
	.on(
			"click",
			function() {
				categoryArray = [];

				$("input[name=Category]:checked").each(function() {
					var selectedCategory = $(this).val();

					categoryArray.push(selectedCategory);

				});

				var quantityInputs = document
						.getElementsByClassName('count-input');
				var InputArray = [];
				// Iterate over the quantity inputs
				console.log(quantityInputs.length,
						'quantityInputs.length');
				for (var i = 0; i < quantityInputs.length; i++) {
					var quantityInput = quantityInputs[i];
					var quantity = parseInt(quantityInput.value);

					// Check if the quantity is greater than 0
					if (quantity > 0) {
						// Retrieve the product details
						var productCard = quantityInput
								.closest('.card');
						var productName = productCard
								.querySelector('.product').innerText;
						var productPrice = productCard
								.querySelector('.price').innerText;
						var productID = productCard
								.querySelector('.productID').innerText;

						InputArray.push({
							productName : productName,
							productPrice : productPrice,
							quantity : quantity,
							productID : productID
						});

					}
				}

				$.ajax({
					type : "GET",
					url : "getProductByCategoryaAddMore",
					data : "categoryList=" + categoryArray,
					success : function(response) {

						updateProducts(response)
					},
					error : function(xhr, status, error) {
						console.log("AJAX Error: " + error);
						console.log("AJAX status: " + status);
						console.log("AJAX xhr: " + xhr);
					}

				});
				//console.log(categoryArray)
			});

function updateProducts(data) {
var productListElement = document.getElementById('el-element-overlay');
var productListHTML = '';
$('#el-element-overlay').html('');
console.log(jQuery.parseJSON(data), 'data')

for (var i = 0; i < jQuery.parseJSON(data).length; i++) {

	var product = jQuery.parseJSON(data)[i];
	console.log(jQuery.parseJSON(data)[0][8],'jQuery.parseJSON(data)[0][8]')
	
	//	var output = "Indent Number: <span class='highlight1'>" +  jQuery.parseJSON(data)[0][8]	+ "</span> ";
				//	$('#IndentNumber').html(output);
					if(product[7]==0){
	const imageSource = 'product/' + product[0] + '.png';
	
	const defaultImage = 'assets/images/No_Image_Availabl.png';
	
	productListHTML += '<div class="col-lg-4 col-md-6 col-12">';
	productListHTML += '<div class="card">';
	productListHTML += '<div class="el-card-item">';
	productListHTML += '<div class="el-card-avatar el-overlay-1"';
	productListHTML += 'style="width: 100%; text-align: center">';
	productListHTML += '<img class="ImageGalleryID" id="productImage" src="'+ imageSource +'" alt="Image Not Found"';
productListHTML += 'style="height: 240.86px;" onerror="this.src=\'' + defaultImage + '\';">';
	productListHTML += '	<div class="el-overlay"></div>';
	productListHTML += '</div>';
	productListHTML += ' <div class="el-card-content " style="padding-left: 10px">';
	productListHTML += '<h5 class="product" class="m-b-0" style="text-align: start;">'
			+ product[1] + '</h5>';

	productListHTML += '<div class="row">';
	productListHTML += '<div class="col-7">';
	productListHTML += '	<h5 class="m-b-0" style="text-align: start;">';
	productListHTML += '		<label class="price" id="price">₹ '
			+ product[4] + '</label>';
	productListHTML += '		<label style="border:1px solid lightgrey;left:5px;">UOM : '
			+ product[5] + '</label>';
	productListHTML += '		<label class="productID" style="left:5px;">Product ID : '
			+ product[0] + '</label>';
	productListHTML += '	</h5>';
	productListHTML += '</div>';
	productListHTML += '<div class="col-5 ">';
	productListHTML += '	<div class="form-group d-flex justify-content-between"';
productListHTML += '	style="align-items: center;">';
	productListHTML += '	<a onclick="decrementCount(this)"';
	productListHTML += '		class="btn btn-sm btn-decre decreme"><i';
productListHTML += '		class="fas fa-minus-square fa-2x"></i></a> <input 	disabled type="text"';
	productListHTML += '		id="countInput' + i
			+ '" name="quantity" class="form-control count-input bold-and-large" id="count-input"';
	productListHTML += '		value="' + product[7]
			+ '" > <a';
	productListHTML += '			onclick="incrementCount(this)" class="btn bnt-sm btn-incre "><i';
productListHTML += '			class="fas fa-plus-square fa-2x"></i></a>';
	productListHTML += '	</div>';
	productListHTML += '</div>';
	productListHTML += '</div>';
	// Add more properties as needed
	productListHTML += '</div>';
	productListHTML += '</div>';
	productListHTML += '</div>';
	productListHTML += '</div>';

}
$('#el-element-overlay').html(productListHTML);
}
//calculateTotal();
}

$("#resetCategoriesID").on("click", function() {
	$('input[type=checkbox]').prop('checked', false);
	
});
</script>
	<div class="loading" style="display: none;">Loading…</div>
	<!-- All Jquery -->
	<!-- ============================================================== -->
	<script src="assets/libs/jquery/dist/jquery.min.js"></script>
	<!-- Bootstrap tether Core JavaScript -->
	<script src="assets/libs/popper.js/dist/umd/popper.min.js"></script>
	<script src="assets/libs/bootstrap/dist/js/bootstrap.min.js"></script>
	<!-- slimscrollbar scrollbar JavaScript -->
	<script
		src="assets/libs/perfect-scrollbar/dist/perfect-scrollbar.jquery.min.js"></script>
	<script src="assets/extra-libs/sparkline/sparkline.js"></script>
	<!--Wave Effects -->
	<script src="dist/js/waves.js"></script>
	<!--Menu sidebar -->
	<script src="dist/js/sidebarmenu.js"></script>
	<!--Custom JavaScript -->
	<script src="dist/js/custom.min.js"></script>
	<!-- this page js -->
	<script
		src="assets/libs/magnific-popup/dist/jquery.magnific-popup.min.js"></script>
	<script src="assets/libs/magnific-popup/meg.init.js"></script>
</html>