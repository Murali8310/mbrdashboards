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
	margin-left: 0px;
}

.logo-text {
	padding: 5px 5px 5px 0px !important;
}

.topbar .top-navbar .navbar-header {
	width: 250px;
	line-height: 50px !important;
}

.highlight {
font-weight: bold;
    color: white; 
        margin-left: 3px;
        /* background-color: #1ea496; */

}


.bold-and-large {
  font-weight: 800;
      font-size: 18px !important;

  color: red !important;
  text-align:center;
}

.page-wrapper {
    background: #ffffff;
    position: relative;
    margin-left: 250px;
    margin-top: 0px !importamt;
}

.form-control {
padding:0px !important;
}

.row {
     margin-right: 0px !important; 
     margin-left: 0px !important;  
}
.paddingzero {
     padding-right: 4px !important;
    padding-left: 4px !important;
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
		
		<aside class="left-sidebar" style="position:fixed" data-sidebarbg="skin5">
			<!-- Sidebar scroll-->
			<div class="scroll-sidebar">
				<!-- Sidebar navigation-->
				<nav class="sidebar-nav">
					<ul id="sidebarnav" class="p-t-30">
						<li class="sidebar-item"><label
							class="sidebar-link hide-menu">
								<button class="btn-new-styles" style="width: 100%;"
									id="resetCategoriesID">Reset</button> &nbsp;&nbsp;
								<button class="btn-new-styles" style="width: 100%;"
									id="submitCategoriesID">Apply</button>
						</label></li>
						<li class="sidebar-item"><a id='catid'
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
	
			<div class="page-breadcrumb" >
				
				<div class="row">
					<div class="col-12 d-flex no-block align-items-center">
						<div class="ml-auto text-right">
							<nav aria-label="breadcrumb">
								<ol class="breadcrumb">
									<li class="breadcrumb-item"><a href="landPage">Home</a></li>
									<li class="breadcrumb-item active" aria-current="page">
										Indent Update <!-- <a href="productDetails">Product Details</a> -->
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
    margin-left: 7px;
    color:white;
   /*  background-color: #1ea496; */
}

/* Style for the value */
.value {
    flex: 0 0 60%; /* Adjust width of the value */
}

/* Style for the highlight */
.highlight {
    color: white; /* Adjust highlight color */
        margin-left: 3px;
    /* Any other highlight styles */
}
</style>
			<div class="container-fluid2 card-total card-static">
				<div class="row" id="totalOutput2"
					style="margin-right: 8px !important;
    margin-left: 8px !important;
    background-color: #4db719;
    box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.5);
    border-radius: 10px;
    padding: 8px;
    font-size: 16px;">
				</div>
				<div class="row"
					style="margin-right: 8px !important;
    margin-left: 8px !important;
    background-color: #4db719;
    box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.5);
    border-radius: 10px;
    padding: 1px;
    font-size: 16px;
    margin-top: 3px;">
					<div id="IndentNumber" style="text-align: start; font-size: 18px;" class="col-4"></div>
					<div id="totalOutput" style="text-align: start; font-size: 18px;" class="col-4"></div>
					<div class="col-md-4 col-lg-4 col-4"
						style="text-align: end; float: right;">
						<button onclick="redirectToPage('AddMoreProducts')" class="btn btn-primary" style="font-size: 16px;background:#2027d6 !important;color: #fff;
    background-color: #3021cf;
    border-color: #d58512;"> 
						Add More Products
						</button>
						 <button onclick="redirectToPage('IndentList')" class="btn btn-warning" style="font-size: 16px;color: #fff;
    background-color: #3021cf;
    border-color: #d58512;">Back
						</button>
					</div>
				</div>
				<div class="row el-element-overlay" style='min-height: auto !important;
    overflow: scroll !important;
    max-height: 84vh !important;
    margin-top: 5px;
    min-height: 82vh !important;' id="el-element-overlay"></div>
				<!-- <footer class="footer">
					<div class="footer-copyright">
						<p>
							© 2023 <span>TITAN COMPANY LTD.</span> ALL RIGHTS RESERVED.
						</p>
					</div>
				</footer> -->
				<!-- <div style="float: right; margin-bottom: 31px;">
					<div class="row">
						<div class="col-md-12 col-lg-12 col-12">
						<a href="AddMoreProducts"> <input type="button"
								class="btn btn-primary" id="expiryDatebut" value="Add More Products">
							</a> 
							<a href="IndentList"> <input type="button"
								class="btn btn-warning" id="expiryDatebut" value="Back">
							</a> 
							<a class="btn btn-success" id="submitId" onclick="submit()">Update</a>

						</div>
					</div>

				</div> -->
			</div>
		</div>
		<jsp:include page="/WEB-INF/jsp/views/header/footer.jsp"></jsp:include>
	</div>
</body>

<script type="text/javascript">


$(document)
.ready(
		function() {
			var id = sessionStorage.getItem('id');
			getProduct(id);
			getBudgetDetails();
			console.log(id,'id');
			
		});
		


function getProduct(id){
	console.log(id,'id')
//alert("1");
	$.ajax({
		type : 'GET',
		url : 'GetAllProductsByIndentNumber',
		dataType : 'json',
		data :"IndentNumber="
		+ id,

		success : function(response) {
			var productListElement = document
					.getElementById('el-element-overlay');
			var productListHTML = '';

			for (var i = 0; i < response.length; i++) {
				var product = response[i];
				/* const productImage = document
						.getElementById("productImage"); */
				 var output = "<span class='title' style='padding: 2px;border-radius: 3px;' >Indent Number : " +  response[0][8] + "</span>";
			$('#IndentNumber').html(output);
			if(product[7]!=0){
				const imageSource = 'product/'
						+ product[0] + '.png';
				  const defaultImage = 'assets/images/No_Image_Availabl.png';
				productListHTML += '<div class="col-lg-4 col-md-6 col-12 paddingzero">';
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
				productListHTML += '<h5 class="product" class="m-b-0" style="text-align: start;font-weight:600">'
						+ product[1] + '</h5>';

				productListHTML += '<div class="row">';
				productListHTML += '<div class="col-7">';
				productListHTML += '	<h5 class="m-b-0" style="text-align: start;font-weight: 600">';
				productListHTML += '		<label class="price" style="font-weight: 600 !important" id="price">₹ '
						+ product[4] + '</label>';
				productListHTML += '		<label style="border:1px solid lightgrey;left:5px;font-weight : 600 !important">UOM : '
						+ product[5] + '</label>';
				productListHTML += '		<label class="productID" style="left:5px;font-weight: 600 !important">Product ID : '
						+ product[0] + '</label>';
				productListHTML += '	</h5>';
				productListHTML += '</div>';
				productListHTML += '<div class="col-5">';
				productListHTML += '	<div class="form-group d-flex justify-content-between"';
				 productListHTML += '	style="align-items: center;">';
				productListHTML += '	<a onclick="decrementCount(this)"';
				productListHTML += '		class="btn btn-sm btn-decre decreme"><i';
			    productListHTML += '		class="fas fa-minus-square fa-2x"></i></a> <input style="background:white"  type="text"';
				productListHTML += '		id="countInput'
						+ i
						+ '" name="quantity" class="form-control count-input bold-and-large"';
				productListHTML += '		value="' + product[7] + '" > <a';
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
		//alert("HI");
		console.log()
		var countInputs = document.querySelectorAll('.count-input');
		var prices = document.querySelectorAll('.price');
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
		var output = "<span class='title' style='padding: 2px;border-radius: 3px;' >Indent Value: ₹</span><span style='color:white;padding: 2px;border-radius: 3px;' class='highlight'>" + totalPrice
				+ "</span> <span style='color:white;padding: 2px;border-radius: 3px;' class='title'>Indent Qty:</span> <span style='color:white;color:white;padding: 2px;border-radius: 3px;' class='highlight'>" + totalQuantity
				+ "</span>";
		$('#totalOutput').html(output);

	}
	
	
	var IndentNumber = sessionStorage.getItem('id');
	function incrementCount(element) {
		var InputArray = [];
		var input = element.parentNode.querySelector('.count-input');
		var productCard = input.closest('.card');
		var productName = productCard.querySelector('.product').innerText;
		var productPrice = productCard.querySelector('.price').innerText;
		var productID = productCard.querySelector('.productID').innerText;
		var currentValue = parseInt(input.value);
		input.value = currentValue + 1;
		 InputArray.push({
				productName : productName,
				productPrice : productPrice,
				quantity : currentValue + 1,
				productID : productID
			});
		calculateTotal();
			var yearlybudget = document.querySelector("#totalOutput2 .highlight:nth-child(2)");
			var totalAmount = document.querySelector("#totalOutput .highlight:nth-child(2)");
			var monthlyBalanceText = $("#totalOutput2 span.highlight:eq(2)").text();
			var monthlyBalance = parseFloat(monthlyBalanceText.replace(/[^\d.]/g, ''));
			var indentBalanceText = $("#totalOutput2 span.highlight:eq(4)").text();
			var indentBalance = parseFloat(indentBalanceText.replace(/[^\d.]/g, ''));
			var total = monthlyBalance -( parseFloat(totalAmount.textContent) -indentBalance);
			//alert(total)
			var pricelement = document.querySelector("#totalOutput .highlight:nth-child(2)").textContent;
			var yearlyAmount = document.getElementById('yearAmount').textContent;

			console.log('this is checking',pricelement,yearlyAmount)
			
			if(Number(pricelement.trim()) > Number(yearlyAmount.trim())){
				Swal.fire({
							icon : 'error',
			        	  title: 'Indent amount is exceeded the yearly budget!',
							focusConfirm : false,
						})
						return;
			}
			
			
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
			    	    	
			    			
			    			//console.log(InputArray, 'InputArrayInputArrayInputArray')
			    			

			    			$.ajax({
			    				type : "POST",
			    				url : "IndentTransactionQuantitySave",
			    				contentType : 'application/json',
			    				data : JSON.stringify(InputArray),

			    				success : function(response) {
			    					//getProduct(IndentNumber);
			    					getBudgetDetails();
			    				}
			    			}) 
			    	    } else if (result.dismiss === Swal.DismissReason.cancel) {
			    	    	//getProduct(IndentNumber);
	    					getBudgetDetails();
			    	    }
			    	});
			}else{
				
			

				$.ajax({
					type : "POST",
					url : "IndentTransactionQuantitySave",
					contentType : 'application/json',
					data : JSON.stringify(InputArray),

					success : function(response) {
						//getProduct(IndentNumber);
						getBudgetDetails();
					}
				}) 
			}
		
	}
	function decrementCount(element) {
		 var input = element.parentNode.querySelector('.count-input');
		var currentValue = parseInt(input.value);
		/* if (currentValue > 0) {
			input.value = currentValue - 1;
			calculateTotal();
		}  */

		var InputArray = [];
		var input = element.parentNode.querySelector('.count-input');
		var productCard = input.closest('.card');
		var productName = productCard.querySelector('.product').innerText;
		var productPrice = productCard.querySelector('.price').innerText;
		var productID = productCard.querySelector('.productID').innerText;
		var currentValue = parseInt(input.value);
		if (currentValue > 0) {
			input.value = currentValue - 1;

			InputArray.push({
				productName : productName,
				productPrice : productPrice,
				quantity : currentValue - 1,
				productID : productID
			});
			//console.log(InputArray)
			calculateTotal();

			$.ajax({
				type : "POST",
				url : "IndentTransactionQuantitySave",
				contentType : 'application/json',
				data : JSON.stringify(InputArray),

				success : function(response) {
					//getProduct(IndentNumber);
					getBudgetDetails();
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
	
	function calculateInputTotal(value,price,prev) { 
		var currentTotalQuantity =0;
		var totalQuantityElement = document.querySelector("#totalOutput .highlight:nth-child(4)");
		var totalPriceElement = document.querySelector("#totalOutput .highlight:nth-child(2)");
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
		console.log('this is murali calling')

		var output = "Total Price: ₹<span class='highlight'>" + totalPrices.toFixed(2)
				+ "</span> Total Qty: <span class='highlight'>" + totalQuantitys
				+ "</span>";
		$('#totalOutput').html(output);

	}
	
	function saveInputdata(element,prev) {
		updateResetButton();
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
		var yearlybudget = document.querySelector("#totalOutput2 .highlight:nth-child(2)");
		var monthlyBalanceText = $("#totalOutput2 span.highlight:eq(2)").text();
		var indentBalanceText = $("#totalOutput2 span.highlight:eq(4)").text();
		var monthlyBalance = parseFloat(monthlyBalanceText.replace(/[^\d.]/g, ''));
		var indentBalance = parseFloat(indentBalanceText.replace(/[^\d.]/g, ''));
		//var total =monthlyBalance+indentBalance;
		var totalAmount = document.querySelector("#totalOutput .highlight:nth-child(1)");
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
		    	    	if(parseFloat(totalAmount.textContent)<=parseFloat(yearlybudget.textContent)){

		    				InputArray.push({
		    					productName : productName,
		    					productPrice : productPrice,
		    					quantity : currentValue,
		    					productID : productID
		    				});
		    				console.log(InputArray)

		    				$.ajax({
		    					type : "POST",
		    					url : "IndentTransactionQuantitySave",
		    					contentType : 'application/json',
		    					data : JSON.stringify(InputArray),

		    					success : function(response) {
		    						//getProduct(IndentNumber);
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
		    	       
		    	    	//getProduct(IndentNumber);
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
			console.log(InputArray)
			calculateTotal();

			$.ajax({
				type : "POST",
				url : "IndentTransactionQuantitySave",
				contentType : 'application/json',
				data : JSON.stringify(InputArray),

				success : function(response) {
					//getProduct(IndentNumber);
					getBudgetDetails();
				}
			})
		}
		}
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
		
		$.ajax({
			type : "POST",
			url : "IndentTransactionQuantitySave",
			contentType : 'application/json',
			data : JSON.stringify(InputArray),

			success : function(response) {
				if(response =='Indent update SuccessFully' || response =='Indent Creation SuccessFully'){
				Swal.fire({
					icon : 'success',
					title : response,
					showCloseButton : false,
					focusConfirm : true,

				})
				getProduct(IndentNumber);
				getBudgetDetails();
				
				}else{
					
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
							url : "getProductByCategoryIndentUpdate",
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
		//console.log(data, 'data')

		for (var i = 0; i < jQuery.parseJSON(data).length; i++) {

			var product = jQuery.parseJSON(data)[i];
			
// 			var output = "Indent Number: <span class='highlight1'>" +  jQuery.parseJSON(data)[0][8]	+ "</span> ";
							//$('#IndentNumber').html(output);
			if(product[7]!=0){
			const imageSource = 'product/' + product[0] + '.png';
			const defaultImage = 'assets/images/No_Image_Availabl.png';
			productListHTML += '<div class="col-lg-4 col-md-6 col-12 paddingzero">';
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
			productListHTML += '	<h5 class="m-b-0" style="text-align: start;font-weight: 600">';
			productListHTML += '		<label class="price" id="price" style="font-weight:600 !important">₹ '
					+ product[4] + '</label>';
			productListHTML += '		<label style="border:1px solid lightgrey;left:5px;font-weight:600 !important">UOM : '
					+ product[5] + '</label>';
			productListHTML += '		<label class="productID" style="left:5px;font-weight:600 !important">Product ID : '
					+ product[0] + '</label>';
			productListHTML += '	</h5>';
			productListHTML += '</div>';
			productListHTML += '<div class="col-5">';
			productListHTML += '	<div class="form-group d-flex justify-content-between"';
	 productListHTML += '	style="align-items: center;">';
			productListHTML += '	<a onclick="decrementCount(this)"';
			productListHTML += '		class="btn btn-sm btn-decre decreme"><i';
    productListHTML += '		class="fas fa-minus-square fa-2x"></i></a> <input style="background:white" type="text"';
			productListHTML += '		id="countInput' + i
					+ '" name="quantity" class="form-control count-input bold-and-large"';
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
		calculateTotal();
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
					
						var output = "<div class='col-4'><span class='title'>Yearly Budget: ₹ </span> <span class='highlight' id='yearAmount'>"
							+ budgt[3]  + "</span> </div><div class='col-4'><span class='title'>Cumulative Indent Value Rs.(Incl. PO, Route): </span>  <span class='highlight'>"
							 + budgt[5]   + "</span></div><div class='col-4'><span class='title'>Budget bal: ₹</span>  <span class='highlight'>"
							+ budgt[4]   + "</span>"
								+"</div> " ;
							
					$('#totalOutput2').html(output); 
					}
				});
}
	

	$("#resetCategoriesID").on("click", function() {
		$('input[type=checkbox]').prop('checked', false);
		var IndentNumber = sessionStorage.getItem('id');
		updateResetButton();
		getProduct(IndentNumber);
		
	});

</script>
			<style>
.footer {
	background-color: Teal;
	padding-bottom: 1px;
	/* height: 300px; */
	
}

.footer {
    position: fixed; 
    bottom: 0;
    width: 100%;
    padding: 10px;
    background-color: #01AFAE;
    text-align: center;
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
	
	<script type="text/javascript">
		document.addEventListener("DOMContentLoaded", function() {
		    setTimeout(function() {
		        var element = document.getElementById('catid');
		        if (element) {
		            element.click();
		        }
		      //  console.log('it is calling in header')

		    }, 10); // Adjust the time in milliseconds (e.g., 1000 for 1 second)
		    updateResetButton();
		});

		</script>
		 <script>
        function redirectToPage(pageUrl) {
            window.location.href = pageUrl;
        }
        // Function to enable or disable the reset button based on array length
        function updateResetButton() {
        	var newcategoryArray = [];

    		$("input[name=Category]:checked").each(function() {
    			var selectedCategory = $(this).val();
    			newcategoryArray.push(selectedCategory);
    		});
          var submitCategoriesIDElement = document.getElementById("submitCategoriesID");
          var resetCategoriesIDElement = document.getElementById("resetCategoriesID");

          // Check if the array length is greater than 0
          if (newcategoryArray.length > 0) {
            // Enable the reset button
            submitCategoriesIDElement.removeAttribute("disabled");
            resetCategoriesIDElement.removeAttribute("disabled");
            submitCategoriesIDElement.style.cursor = 'pointer';
            resetCategoriesIDElement.style.cursor = 'pointer';
          } else {
            // Disable the reset button
            submitCategoriesIDElement.setAttribute("disabled", "true");
            resetCategoriesIDElement.setAttribute("disabled", "true");
            submitCategoriesIDElement.style.cursor = 'not-allowed';
            resetCategoriesIDElement.style.cursor = 'not-allowed';
          }
        }
    </script>
</html>