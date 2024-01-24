<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html>
<html dir="ltr" lang="en">
<head>
<meta charset="utf-8">
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
		

<style>
.el-element-overlay .el-card-item {
	position: relative;
	padding-bottom: 10px;
}

.product-list-container {
	display: flex;
	flex-wrap: wrap;
	justify-content: space-between;
	background-color: #fff;
}

.product-card {
	width: 100%;
	margin-bottom: 8px;
	border: 1px solid #ccc;
	border-radius: 5px;
	padding: 10px;
	box-sizing: border-box;
}

.product-card img {
	width: 100%;
	height: auto;
	border-radius: 5px;
	margin-bottom: 8px;
}

.product-title {
	font-size: 20px;
	margin: 0;
}

.card {
margin-bottom: 10px;
}

.product-description {
	margin: 8px 0;
}

.col, .col-1, .col-2, .col-3, .col-4, .col-5, .col-6, .col-7, .col-8, .col-9, .col-10, .col-11, .col-12, .col-auto, .col-lg, .col-lg-1, .col-lg-2, .col-lg-3, .col-lg-4, .col-lg-5, .col-lg-6, .col-lg-7, .col-lg-8, .col-lg-9, .col-lg-10, .col-lg-11, .col-lg-12, .col-lg-auto, .col-md, .col-md-1, .col-md-2, .col-md-3, .col-md-4, .col-md-5, .col-md-6, .col-md-7, .col-md-8, .col-md-9, .col-md-10, .col-md-11, .col-md-12, .col-md-auto, .col-sm, .col-sm-1, .col-sm-2, .col-sm-3, .col-sm-4, .col-sm-5, .col-sm-6, .col-sm-7, .col-sm-8, .col-sm-9, .col-sm-10, .col-sm-11, .col-sm-12, .col-sm-auto, .col-xl, .col-xl-1, .col-xl-2, .col-xl-3, .col-xl-4, .col-xl-5, .col-xl-6, .col-xl-7, .col-xl-8, .col-xl-9, .col-xl-10, .col-xl-11, .col-xl-12, .col-xl-auto {
    position: relative;
    width: 100%;
    min-height: 1px;
    padding-right: 5px;
    padding-left: 5px;
}

.product-price {
	font-weight: bold;
	margin: 0;
}

#variantHeader {
	margin-left: 40%;
	color: Teal;
	text-shadow: 0px -1px 1px #193131;
}

#variantHeader:hover {
	color: #16dede;
	text-shadow: 0px -1px 1px #193131;
	-webkit-text-fill-color: #1bd2d8
}

.page-wrapper {
	background: #ffffff;
	position: relative;
	margin-left: 250px;
	margin-top: 60px !important;
}

.table td, .table th {
	padding-bottom: 5px;
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

.bomWatchDetailsHeading {
	color: Teal;
	text-align: center;
}

.modal-header {
	border-bottom: 2px solid red;
	/*  background-image:linear-gradient(to left, Teal, #00808063) */
	/*  background-color: #63d471;
	background-image: linear-gradient(315deg, #63d471 0%, #23332959 35%) */
	/*     background-color: teal;
    background-image: linear-gradient(315deg, #63d471 0%, #2333modal2959 */
	/* linear-gradient(315deg, #3bb78f 0%, #0bab64 74%); */
	background-color: #00808000;
	background-image: linear-gradient(315deg, #f7f9f8e8 0%, #f2f5f300 74%)
		/* background-image:linear-gradient(315deg, #2b886ae8 0%, #059857b8 74%); */
}

.container-fluid {
	margin-bottom: 30px;
}

.page-wrapper>.container-fluid {
	padding: 10px;
	min-height: calc(120vh - 180px)
}

.page-wrapper>.container-fluid2 {
	padding: 2px;
}

.page-title {
	color: Teal;
}

/* .sidebar-item {
	margin-left: 10px;
} */

@media ( min-width : 768px) {
	.modal-xl {
		width: 90%;
		max-width: 1200px;
	}
}

.table th, .table thead th {
	font-weight: bolder;
}

.btn-secondary:hover {
	color: #fff;
	background-color: teal;
	border-color: #0c0c0c00
}

.videoLink:active {
	color: blue;
}

.videoLink:visited {
	color: purple;
}

.videoLink a:link {
	color: blue;
}

table a:hover {
	text-decoration: underline;
}

.sidebar-item .first-level {
	height: 500px;
	overflow-y: auto;
}

#loadMoreProductsID {
	margin-left: 40%;
	background-color: Teal;
	border-radius: 20px;
	border: 1px solid white;
}

#loadMoreProductsID:hover {
	/* border: 4px solid cadetblue */
	border: 1px solid #37906e;
	color: Teal;
	background-color: white;
}

.el-element-overlay .el-card-item .el-overlay-1 {
	cursor: pointer;
}

body {
	font-size: 0.780rem
}

/* .ImageGalleryID,.imageGallery {
		-webkit-animation: rotation 4s infinite linear;
}

@-webkit-keyframes rotation {
		from {
				-webkit-transform: rotate(0deg);
		}
		to {
				-webkit-transform: rotate(359deg);
		}
}
 */

/* .footer{
 background-color: #1f262d4a;
} */
.logo-text {
	padding: 5px 5px 5px 0px !important;
}

.topbar .top-navbar .navbar-header {
	width: 250px;
	line-height: 50px !important;
}

.highlight {
	}
</style>
<style type="text/css">
.jqstooltip {
	position: absolute;
	left: 0px;
	top: 0px;
	visibility: hidden;
	background: rgb(0, 0, 0) transparent;
	background-color: rgba(0, 0, 0, 0.6);
	filter: progid:DXImageTransform.Microsoft.gradient(startColorstr=#99000000,
		endColorstr=#99000000);
	-ms-filter:
		"progid:DXImageTransform.Microsoft.gradient(startColorstr=#99000000, endColorstr=#99000000)";
	color: white;
	font: 10px arial, san serif;
	text-align: left;
	white-space: nowrap;
	padding: 5px;
	border: 1px solid white;
	z-index: 10000;
}

.jqsfield {
	color: white;
	font: 10px arial, san serif;
	text-align: left;
}

.bold-and-large {
  /* font-weight: 800; */
  font-size: 20px !important;
  color: red !important;
  text-align:center;
}

.form-control {
padding:0px !important;
}

.row {
     margin-right: 0px !important; 
     margin-left: 0px  !important; 
}

.h5, h5 {
    font-size: 15px  !important; 
}
.col-lg-1, .col-lg-10, .col-lg-11, .col-lg-12, .col-lg-2, .col-lg-3, .col-lg-4, .col-lg-5, .col-lg-6, .col-lg-7, .col-lg-8, .col-lg-9, .col-md-1, .col-md-10, .col-md-11, .col-md-12, .col-md-2, .col-md-3, .col-md-4, .col-md-5, .col-md-6, .col-md-7, .col-md-8, .col-md-9, .col-sm-1, .col-sm-10, .col-sm-11, .col-sm-12, .col-sm-2, .col-sm-3, .col-sm-4, .col-sm-5, .col-sm-6, .col-sm-7, .col-sm-8, .col-sm-9, .col-xs-1, .col-xs-10, .col-xs-11, .col-xs-12, .col-xs-2, .col-xs-3, .col-xs-4, .col-xs-5, .col-xs-6, .col-xs-7, .col-xs-8, .col-xs-9 {
    position: relative;
    min-height: 1px;
    padding-right: 5px !important;
    padding-left: 5px  !important;
}

.card-static{position: fixed; z-index: 9;width: 100%;}
</style>
</head>

<body style="zoom: 85%;"  >
	<!-- ============================================================== -->
	<!-- Preloader - style you can find in spinners.css -->
	<!-- ============================================================== -->
	<div class="preloader" style="display: none;">
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
							href="javascript:void(0)" aria-expanded="false"><i style="text-align: justify;"
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
		<!-- //cfcff22e -->
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
   /*  background-color: #1ea496; */
}

/* Style for the value */
.value {
    flex: 0 0 60%; /* Adjust width of the value */
}

/* Style for the highlight */
.highlight {
font-weight: bold;
    color: white; 
        margin-left: 3px;
        /* background-color: #1ea496; */
   
}
</style>
		<div class="page-wrapper" style="background-color: #cfcff22e;" data-sidebartype="full" class="mini-sidebar">
			<div class="container-fluid2 card-total card-static" style="    margin-top: 20px;">
					<div class="row" style="background-color: #4db719; align-items: baseline;
					padding-top: 5px;margin-left:13px !important;width: 83%;box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.5);
					    border-radius: 10px;
					    margin-bottom: 2px;">
						<div id="totalOutput2"
							style="text-align: start; font-size: 16px; color: white; padding: 6px;
					    border-radius: 10px;
					    margin-bottom: 20px;
        				height: 27px;">
    				</div>
					</div>
					<!-- <div class="row" style="background-color: #01AFAE; align-items: baseline;padding-top: 5px;margin-left:13px !important;width: 94%;box-shadow: 5px 5px 10px rgba(0, 0, 0, 0.5);
					    border-radius: 10px;
					    margin-bottom: 2px;">
					    <div>
							<a href="manageByAdmin"> <input type="button"
								class="btn btn-primary" id="expiryDatebut" value="Cancel">&nbsp;
							</a> <a class="btn btn-success" id="submitId" onclick="submit()">Submit</a>
						</div>
					</div> -->
			<div class="page-breadcrumb" style="position: fixed;z-index: 9;width: 83%;background-color:#fff;margin-left: 13px;">
				<div class="row">
					<div class="col-12 d-flex no-block align-items-center">	
						<h4 class="page-title display-6">Product Catalogue</h4>
						<div class="ml-auto text-right">
						<!-- <div id="totalOutput"
						style="text-align: end; font-size: 18px;padding-left:10px; background-color: #92e3f7;"></div> -->
							<nav aria-label="breadcrumb">
								<ol class="breadcrumb">
								<li id="totalOutput" class="breadcrumb-item" style="background-color: darkblue; color: white;font-weight: bolder;"></li>
									<li class="breadcrumb-item"><a href="landPage">Home</a></li>
									<li class="breadcrumb-item active" aria-current="page">Product Catalogue 
									</li>
										<div style="margin-left:17px;">
							<a href="manageByAdmin"> <input type="button" style="font-size: 16px;
    color: #fff;
    background-color: #3021cf;
    border-color: #d58512;"
								class="btn btn-primary" id="expiryDatebut" value="Cancel">&nbsp;
							</a> 
							<a class="btn btn-success" style="font-size: 16px;
    color: #fff;
    background-color: #3021cf;
    border-color: #d58512;" id="submitId" onclick="submit()">Submit</a>
						</div>
								</ol>
							</nav>
						</div>
					</div>
				</div>
			</div>
			</div>
			
			<!-- 	<div id="variantCountDetails" style="margin: 5px 0px 5px 20px;"><span style="color:Teal">Showing <b> 1-12</b> of <b>62480</b> Watches </span></div>
			<div id="errorMessage" style="margin: 5px 0px 5px 20px;"></div> -->

			
				<div class="container-fluid">
 
				<div class="row el-element-overlay" id="el-element-overlay" style="margin-top: 90px"></div>

			</div>

			<script>
			$(document)
			.ready(
					function() {
						getAllProducts();
						getBudgetDetails();
						console.log('this is murali for check')
					});
			
				$("#submitCategoriesID")
						.on(
								"click",
								function() {
									categoryArray = [];

									$("input[name=Category]:checked")
											.each(
													function() {
														var selectedCategory = $(
																this).val();
														
														categoryArray
																.push(selectedCategory);
													
													});

									var quantityInputs = document
											.getElementsByClassName('count-input');
									var InputArray = [];
									// Iterate over the quantity inputs
									console.log(quantityInputs.length,'quantityInputs.length');
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
												url : "getProductByCategory",
												data : "categoryList="
														+ categoryArray,
												success : function(response) {
													
													updateProducts(response)
												},
												error : function(xhr, status,
														error) {
													console.log("AJAX Error: "
															+ error);
													console.log("AJAX status: "
															+ status);
													console.log("AJAX xhr: "
															+ xhr);
												}

											});
									//console.log(categoryArray)
								});

				function updateProducts(data) {
					var productListElement = document
							.getElementById('el-element-overlay');
					var productListHTML = '';
					$('#el-element-overlay').html('');
					//console.log(data, 'data')

					for (var i = 0; i < jQuery.parseJSON(data).length; i++) {

						var product = jQuery.parseJSON(data)[i];
						const productImage = document
								.getElementById("productImage");

						const imageSource = 'product/'
								+ product[0] + '.png';
						// productImage.src = imageSource;
						//console.log(imageSource)
						  const defaultImage = 'assets/images/No_Image_Availabl.png';
						//console.log(response, 'response')
						productListHTML += '<div class="col-lg-4 col-md-6 col-12">';
						productListHTML += '<div class="card" style="background-color: #fff">';
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
						productListHTML += '<h5 class="product" class="m-b-0" style="text-align: start;font-weight: bold;">'
								+ product[1] + '</h5>';

						productListHTML += ' <div class="row">';
						productListHTML += ' <div class="col-7">';
						productListHTML += ' <h5 class="m-b-0" style="text-align: start;font-weight:bold;">';
						productListHTML += ' UCP : <label class="price" id="price">₹ '
								+ product[4] + '</label>';
						productListHTML += ' <label>UOM : '
								+ product[5] + '</label>';
						productListHTML += ' <label class="productID" style="left:5px;">Product ID : '
								+ product[0] + '</label>';
						productListHTML += '	</h5>';
						productListHTML += '</div>';
						productListHTML += '<div class="col-5">';
						productListHTML += '	<div class="form-group d-flex justify-content-between"';
						 productListHTML += ' style="align-items: center;">';
						productListHTML += ' <a onclick="decrementCount1(this)"';
						productListHTML += ' class="btn btn-sm btn-decre decreme"><i';
					    productListHTML += ' class="fas fa-minus-square fa-2x"></i></a> <input oninput="restrictNegative(this)" type="number"';
						productListHTML += ' id="countInput'
								+ i
								+ '" name="quantity" class="form-control count-input bold-and-large"';
						productListHTML += ' value="' + product[7] + '" > <a';
						productListHTML += ' onclick="incrementCount1(this)" class="btn bnt-sm btn-incre"><i';
						productListHTML += ' class="fas fa-plus-square fa-2x"></i></a>';
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
					//calculateTotal();
				}
			</script>

			<script>
 function getAllProducts(){
		$.ajax({	type : 'GET',
			url : 'GetAllProducts',
			dataType : 'json',

			success : function(response) {
				var productListElement = document
						.getElementById('el-element-overlay');
				var productListHTML = '';

				for (var i = 0; i < response.length; i++) {
					var product = response[i];
					const productImage = document
							.getElementById("productImage");

					const imageSource = 'product/'
							+ product[0] + '.png';
					// productImage.src = imageSource;
					//console.log(imageSource)
					  const defaultImage = 'assets/images/No_Image_Availabl.png';
					//console.log(response, 'response')
					productListHTML += '<div class="col-lg-4 col-md-6 col-12">';
					productListHTML += '<div class="card" style="background-color: #fff">';
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
					productListHTML += '<h5 class="product" class="m-b-0" style="text-align: start;font-weight: bold">'
							+ product[1] + '</h5>';

					productListHTML += '<div class="row">';
					productListHTML += '<div class="col-7">';
					productListHTML += '	<h5 class="m-b-0" style="text-align: start;font-weight:bold;">';
					productListHTML += '		UCP : <label class="price" id="price"><span style="font-weight:bold;">₹</span> '
							+ product[4] + '</label>&nbsp;';
					productListHTML += '		<label><span style="font-weight:bold;">UOM</span> : '
							+ product[5] + '</label>';
					productListHTML += '		<label class="productID" style="left:5px;"><span style="font-weight:bold;">Product ID :</span> '
							+ product[0] + '</label>';
					productListHTML += '	</h5>';
					productListHTML += '</div>';
					productListHTML += '<div class="col-5">';
					productListHTML += '	<div class="form-group d-flex justify-content-between"';
					 productListHTML += '	style="align-items: center;">';
					productListHTML += '	<a onclick="decrementCount(this)"';
					productListHTML += '		class="btn btn-sm btn-decre decreme"><i';
				    productListHTML += '		class="fas fa-minus-square fa-2x"></i></a> <input oninput="restrictNegative(this)" type="number"' ;
					productListHTML += '		id="countInput'
							+ i
							+ '" name="quantity" class="form-control count-input bold-and-large"';
					productListHTML += '		value="' + product[7] + '"> <a';
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
				calculateTotal();
			},
			error : function(xhr, status, error) {
				console.log("AJAX Error: " + error);
			}
		});
 }
 
 function getBudgetDetails(){
	 $
		.ajax({
			type : 'GET',
			url : 'getBudgetDetails',
			//dataType : 'json',
			success : function(response) {
				var budgt =jQuery.parseJSON(response)[0]
				if(budgt == undefined){
					$('#totalOutput2').html("No budget is defined");
				}else{
			  var output = "<div class='cell'>&nbsp;&nbsp;<span class='title'>Yearly Budget : ₹</span>  <span class='highlight' id='yearAmount'>"
					+ budgt[3]  + "</span>&nbsp;&nbsp;&nbsp;<span class='title' style='margin-left: 80px;'>Cumulative Indent Value Rs.(Incl. PO, Route): </span><span class='highlight'>"
					 + budgt[5]   + "</span>&nbsp;&nbsp;"+ "<span class='title' style='margin-left: 69px;'> Budget bal :   ₹ </span> <span class='highlight'>"
					+ budgt[4]   + "</span>&nbsp;&nbsp;&nbsp;"+ " <span class='title' style='margin-left: 46px;'>Indent Amt(Current month):   ₹</span>  <span class='highlight'>" 
					+ budgt[6]   + "</span> </div> " ;
			$('#totalOutput2').html(output);  
			
			/*  var output = "<div class='row'><div class='col-3'>Yearly Budget(INR):</div>  <div class='col-3 highlight' id='yearAmount'>"
					+ budgt[3]  + "</div><div class='col-3'>Cumulative (Incl. PO, Route) Indent Value:   ₹</div></div>"
					+ "<div class='row'><div class='col-3'>"
					 + budgt[5]   + "</div>"+ "<div class='col-3'> Budget bal:   ₹ </div> <div class='col-3'>"
					+ budgt[4]   + "</div>"+ " <div class='col-3'>Indent Amt(Current month):   ₹</div>  <div class='col-3'>" 
					+ budgt[6]   + "</div> </div> " ;
			$('#totalOutput2').html(output);  */
			
			
			}
			}
		});
 }
	
</script>
<script>



	$("#resetCategoriesID").on("click", function() {
		$('input[type=checkbox]').prop('checked', false);
		updateResetButton();
		getAllProducts();
		getBudgetDetails();
	});

	function submit() {
		console.log('this is submit')
		var quantityInputs = document.getElementsByClassName('count-input');
		if (document.getElementById('totalOutput2') && document.getElementById('totalOutput2').textContent && document.getElementById('totalOutput2').textContent === 'No budget is defined') {
			document.getElementById('totalOutput2').style.color = 'red';
		}
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
				console.log(InputArray,'murali2');
			}
		}
		
		var pricelement = document.querySelector("#totalOutput .highlight:nth-child(1)").textContent;
		var yearlyAmount = document.getElementById('yearAmount').textContent;

		console.log('this is checkingd',pricelement,yearlyAmount)
		
		if(Number(pricelement.trim()) > Number(yearlyAmount.trim())){
			Swal.fire({
						icon : 'error',
		        	  title: 'Indent amount is exceeded the yearly budget!',
						focusConfirm : false,
					})
					return;
		}
		
		if(InputArray.length <= 0){
			Swal.fire({
				icon : 'error',
        	  title: 'Please select atleast one product!',
				focusConfirm : false,
			})
			return;
		}

		$.ajax({
			type : "POST",
			url : "IndentCreation",
			contentType : 'application/json',
			data : JSON.stringify(InputArray),

			success : function(response) { 
				//var data = jQuery.parseJSON(response);
				
				if(response.startsWith("Indent Created Successfully")){
					
			        	Swal.fire({
			        	
			        	  icon: 'success',
			        	  title: response,
			        	  showCloseButton: false,
			        	  showCancelButton: false,
			        	  focusConfirm: false,			        	
			        	}).then((result) => {
				    	    if (result.isConfirmed) {
				    	    	location.href = 'IndentList';
				    	    }})			        	
			        	getAllProducts();
			        	getBudgetDetails();
			        	}else{
			        		$(".loading").hide();
			        		Swal.fire({
		   			        	
 	   			        	  icon: 'error',
 	   			        	  text: response,
 	   			        	  showCloseButton: false,
 	   			        	  focusConfirm: false,
 	   			        	
 	   			        	})
			        	}
				
			},//end of success function
			error : function(error) {
				$(".loading").hide();
				Swal.fire({
			        	
			        	  icon: 'error',
			        	  text: 'Insufficient Budget Balance',
			        	  showCloseButton: false,
			        	  focusConfirm: false,
			        	
			        	})
				//$(".loading").hide();
			}
		})
	}
	
	function calculateIncrementTotal(value,price) {
			var totalQuantityElement = document.querySelector("#totalOutput .highlight:nth-child(2)");
			var totalPriceElement = document.querySelector("#totalOutput .highlight:nth-child(1)");
			var currentTotalQuantity = parseInt(totalQuantityElement.textContent);
			var currentTotalPrice = parseFloat(totalPriceElement.textContent);
			console.log(currentTotalQuantity,'currentTotalQuantity');
			console.log(currentTotalPrice,'currentTotalPrice');
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
			console.log(totalPrices,'totalPrice');
			console.log(totalQuantitys,'totalQuantity');
			var output = "Cart Val: ₹<span class='highlight'>" + totalPrices.toFixed(2)
					+ "</span> Cart Qty: <span class='highlight'>" + totalQuantitys
					+ "</span>";
			$('#totalOutput').html(output);

		}
	
	function incrementCount1(element) {
		var noBudget =$('#totalOutput2').text();
		if(noBudget != 'No budget is defined'){
		var yearlybudget = document.querySelector("#totalOutput2 .highlight:nth-child(2)");
	//alert('yearlybudget' + yearlybudget)
		if(parseInt(yearlybudget.textContent)!=0){
			var InputArray = [];
			var input = element.parentNode.querySelector('.count-input');
			var productCard = input.closest('.card');
			var productName = productCard.querySelector('.product').innerText;
			var productPrice = productCard.querySelector('.price').innerText;
			var productID = productCard.querySelector('.productID').innerText.split(':')[1].trim();
			console.log('this is checkinge',productID)
			var currentValue = parseInt(input.value);
		
			input.value = currentValue + 1;

			InputArray.push({
				productName : productName,
				productPrice : productPrice,
				quantity : currentValue + 1,
				productID : productID
			});
			calculateIncrementTotal(input.value,productPrice);
		var yearlybudget = document.querySelector("#totalOutput2 .highlight:nth-child(2)");
		var monthlyBalanceText = $("#totalOutput2 span.highlight:eq(2)").text();
		var indentBalanceText = $("#totalOutput2 span.highlight:eq(4)").text();
		var monthlyBalance = parseFloat(monthlyBalanceText.replace(/[^\d.]/g, ''));
		var indentBalance = parseFloat(indentBalanceText.replace(/[^\d.]/g, ''));
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
		    	    	$.ajax({
		    				type : "POST",
		    				url : "tempCartIndentCreation",
		    				contentType : 'application/json',
		    				data : JSON.stringify(InputArray),

		    				success : function(response) {

		    					//var data = jQuery.parseJSON(response);
		    					
		    					if(response == "Indent Creation SuccessFully" ||response == 'Indent update SuccessFully'){
		    						//getAllProducts();
		    						//getBudgetDetails();
		    				        //	calculateTotal();
		    				        	}else{
		    				        		$(".loading").hide();
		    				        		Swal.fire({
		    			   			        	
		    	 	   			        	  icon: 'error',
		    	 	   			        	  text: response,
		    	 	   			        	  showCloseButton: false,
		    	 	   			        	  focusConfirm: false,
		    	 	   			        	
		    	 	   			        	})
		    	 	   			        
		    				        	}
		    					
		    				},//end of success function
		    				error : function(error) {
		    					$(".loading").hide();
		    					Swal.fire({
		    				        	
		    				        	  icon: 'error',
		    				        	  text: error,
		    				        	  showCloseButton: false,
		    				        	  focusConfirm: false,
		    				        	
		    				        	})
		    					//$(".loading").hide();
		    				}
		    			})
		    	    } else {
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
		    	    }}else if (result.dismiss === Swal.DismissReason.cancel) {
		    	    	getAllProducts();
						getBudgetDetails();
		    	    }
		    	});
		}else {
				$.ajax({
				type : "POST",
				url : "tempCartIndentCreation",
				contentType : 'application/json',
				data : JSON.stringify(InputArray),

				success : function(response) {

					//var data = jQuery.parseJSON(response);
					
					if(response == "Indent Creation SuccessFully" ||response == 'Indent update SuccessFully'){
				        	
				        	//calculateTotal();
				        	}else{
				        		$(".loading").hide();
				        		Swal.fire({
			   			        	
	 	   			        	  icon: 'error',
	 	   			        	  text: response,
	 	   			        	  showCloseButton: false,
	 	   			        	  focusConfirm: false,
	 	   			        	
	 	   			        	})
	 	   			        
				        	}
					
				},//end of success function
				error : function(error) {
					$(".loading").hide();
					Swal.fire({
				        	
				        	  icon: 'error',
				        	  text: error,
				        	  showCloseButton: false,
				        	  focusConfirm: false,
				        	
				        	})
					//$(".loading").hide();
				}
			})
		}
		}else {
			Swal.fire({
	        	  icon: 'error',
	        	  text: 'No yearly Budget For this Cost center',
	        	  showCloseButton: false,
	        	  focusConfirm: false,
	        	
	        	});
		}
		}else {
			Swal.fire({
	        	  icon: 'error',
	        	  text: 'No yearly Budget defined For this Cost center',
	        	  showCloseButton: false,
	        	  focusConfirm: false,
	        	
	        	});
		}
		}
	
	//..................
	function incrementCount(element) {
		var noBudget =$('#totalOutput2').text();
		if(noBudget != 'No budget is defined'){
		var yearlybudget = document.querySelector("#totalOutput2 .highlight:nth-child(2)");
	
		if(parseInt(yearlybudget.textContent)!=0){
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
		var yearlybudget = document.querySelector("#totalOutput2 .highlight:nth-child(2)");
		var monthlyBalanceText = $("#totalOutput2 span.highlight:eq(2)").text();
		var indentBalanceText = $("#totalOutput2 span.highlight:eq(4)").text();
		var monthlyBalance = parseFloat(monthlyBalanceText.replace(/[^\d.]/g, ''));
		var indentBalance = parseFloat(indentBalanceText.replace(/[^\d.]/g, ''));
		//var total =monthlyBalance+indentBalance;
		var totalAmount = document.querySelector("#totalOutput .highlight:nth-child(1)");
		var total = monthlyBalance -( parseFloat(totalAmount.textContent) -indentBalance);
		
		var pricelement = document.querySelector("#totalOutput .highlight:nth-child(1)").textContent;
		var yearlyAmount = document.getElementById('yearAmount').textContent;

		console.log('this is checkings',pricelement,yearlyAmount)
		
		if(Number(pricelement.trim()) > Number(yearlyAmount.trim())){
			Swal.fire({
						icon : 'error',
		        	  title: 'Indent amount is exceeded the yearly budget!',
						focusConfirm : false,
					})
					return;
		}
		if(total<=0){
			
			//alert(parseFloat(totalAmount.textContent));
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
		    				url : "tempCartIndentCreation",
		    				contentType : 'application/json',
		    				data : JSON.stringify(InputArray),

		    				success : function(response) {

		    					//var data = jQuery.parseJSON(response);
		    					
		    					if(response == "Indent Creation SuccessFully" ||response == 'Indent update SuccessFully'){
		    						//getAllProducts();
		    						//getBudgetDetails();
		    				        //	calculateTotal();
		    				        	}else{
		    				        		$(".loading").hide();
		    				        		Swal.fire({
		    			   			        	
		    	 	   			        	  icon: 'error',
		    	 	   			        	  text: response,
		    	 	   			        	  showCloseButton: false,
		    	 	   			        	  focusConfirm: false,
		    	 	   			        	
		    	 	   			        	})
		    	 	   			        
		    				        	}
		    					
		    				},//end of success function
		    				error : function(error) {
		    					$(".loading").hide();
		    					Swal.fire({
		    				        	
		    				        	  icon: 'error',
		    				        	  text: error,
		    				        	  showCloseButton: false,
		    				        	  focusConfirm: false,
		    				        	
		    				        	})
		    					//$(".loading").hide();
		    				}
		    			})
		    	    } else {
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
		    	    }}else if (result.dismiss === Swal.DismissReason.cancel) {
		    	    	getAllProducts();
						getBudgetDetails();
		    	    }
		    	});
		}else {
				$.ajax({
				type : "POST",
				url : "tempCartIndentCreation",
				contentType : 'application/json',
				data : JSON.stringify(InputArray),

				success : function(response) {

					//var data = jQuery.parseJSON(response);
					
					if(response == "Indent Creation SuccessFully" ||response == 'Indent update SuccessFully'){
				        	
				        	//calculateTotal();
				        	}else{
				        		$(".loading").hide();
				        		Swal.fire({
			   			        	
	 	   			        	  icon: 'error',
	 	   			        	  text: response,
	 	   			        	  showCloseButton: false,
	 	   			        	  focusConfirm: false,
	 	   			        	
	 	   			        	})
	 	   			        
				        	}
					
				},//end of success function
				error : function(error) {
					$(".loading").hide();
					Swal.fire({
				        	
				        	  icon: 'error',
				        	  text: error,
				        	  showCloseButton: false,
				        	  focusConfirm: false,
				        	
				        	})
					//$(".loading").hide();
				}
			})
		}
		}else {
			Swal.fire({
	        	  icon: 'error',
	        	  text: 'No yearly Budget For this Cost center',
	        	  showCloseButton: false,
	        	  focusConfirm: false,
	        	
	        	});
		}
		}else {
			Swal.fire({
	        	  icon: 'error',
	        	  text: 'No yearly Budget defined For this Cost center',
	        	  showCloseButton: false,
	        	  focusConfirm: false,
	        	
	        	});
		}
		}

	function decrementCount1(element) {
		var InputArray = [];
		var input = element.parentNode
				.querySelector('.count-input');
		var productCard = input.closest('.card');
		var productName = productCard.querySelector('.product').innerText;
		var productPrice = productCard.querySelector('.price').innerText;
		var productID = productCard.querySelector('.productID').innerText.split(':')[1].trim();
		var currentValue = parseInt(input.value);
		if (currentValue > 0) {
			input.value = currentValue - 1;

			InputArray.push({
				productName : productName,
				productPrice : productPrice,
				quantity : currentValue - 1,
				productID : productID
			});
			calculateDecrementTotal(input.value,productPrice);

			$.ajax({
				type : "POST",
				url : "tempCartIndentCreation",
				contentType : 'application/json',
				data : JSON.stringify(InputArray),

				success : function(response) {
						if(response == "Indent Creation SuccessFully" ||response == 'Indent update SuccessFully'){
				        	
				        	
				        	}else{
				        		$(".loading").hide();
				        		Swal.fire({
			   			        	
	 	   			        	  icon: 'error',
	 	   			        	  text: response,
	 	   			        	  showCloseButton: false,
	 	   			        	  focusConfirm: false,
	 	   			        	
	 	   			        	})
				        	}
					
				},//end of success function
				error : function(error) {
					$(".loading").hide();
					Swal.fire({
				        	
				        	  icon: 'error',
				        	  text: error,
				        	  showCloseButton: false,
				        	  focusConfirm: false,
				        	
				        	})
					//$(".loading").hide();
				}
			})
		}
	}
	
function calculateDecrementTotal(value,price) {
		var totalQuantityElement = document.querySelector("#totalOutput .highlight:nth-child(2)");
		var totalPriceElement = document.querySelector("#totalOutput .highlight:nth-child(1)");
		var currentTotalQuantity = parseInt(totalQuantityElement.textContent);
		var currentTotalPrice = parseFloat(totalPriceElement.textContent);
		$('#totalOutput').html('');
		var totalPrice = 0;
		var totalQuantity = 0;
		
		var quantity = parseInt(value);
		var price = parseFloat(price.replace('₹ ', ''));
		totalPrice = price * 1;
		totalQuantity = 1;
		totalPrice = totalPrice.toFixed(2);
		currentTotalPrice = currentTotalPrice.toFixed(2);
		
		totalPrices = currentTotalPrice - totalPrice  ;
		totalQuantitys =currentTotalQuantity - totalQuantity ;
		console.log(totalPrices,'totalPrice');
		console.log(totalQuantitys,'totalQuantity');
		var output = "Indent Val: ₹<span class='highlight'>" + totalPrices.toFixed(2)
				+ "</span> Indent Qty: <span class='highlight'>" + totalQuantitys
				+ "</span>";
		$('#totalOutput').html(output);

	}

				function decrementCount(element) {
					var InputArray = [];
					var input = element.parentNode
							.querySelector('.count-input');
					var productCard = input.closest('.card');
					var productName = productCard.querySelector('.product').innerText;
					var productPrice = productCard.querySelector('.price').innerText;
					var productID = productCard.querySelector('.productID').innerText.split(':')[1].trim();
					var currentValue = parseInt(input.value);
					if (currentValue > 0) {
						input.value = currentValue - 1;

						InputArray.push({
							productName : productName,
							productPrice : productPrice,
							quantity : currentValue - 1,
							productID : productID
						});
						console.log(InputArray)
						calculateTotal();

						$.ajax({
							type : "POST",
							url : "tempCartIndentCreation",
							contentType : 'application/json',
							data : JSON.stringify(InputArray),

							success : function(response) {


								//var data = jQuery.parseJSON(response);
								
								if(response == "Indent Creation SuccessFully" ||response == 'Indent update SuccessFully'){
							        	
							        	
							        	}else{
							        		$(".loading").hide();
							        		Swal.fire({
						   			        	
				 	   			        	  icon: 'error',
				 	   			        	  text: response,
				 	   			        	  showCloseButton: false,
				 	   			        	  focusConfirm: false,
				 	   			        	
				 	   			        	})
							        	}
								
							},//end of success function
							error : function(error) {
								$(".loading").hide();
								Swal.fire({
							        	
							        	  icon: 'error',
							        	  text: error,
							        	  showCloseButton: false,
							        	  focusConfirm: false,
							        	
							        	})
								//$(".loading").hide();
							}
						})
					}
				}

				function calculateTotal() {
					var countInputs = document.querySelectorAll('.count-input');
					var prices = document.querySelectorAll('.price');
					$('#totalOutput').html('');
					var totalPrice = 0;
					var totalQuantity = 0;

					for (var i = 0; i < countInputs.length; i++) {
						//alert(countInputs[i].value);
						//alert(prices[i].innerText);
						var quantity = parseInt(countInputs[i].value);
						var price = parseFloat(prices[i].innerText.replace(
								'₹ ', ''));
						//alert(price);
						totalPrice += price * quantity;
						//alert(totalPrice);
						totalQuantity += quantity;
					}
					totalPrice = totalPrice.toFixed(2);
					var output = "  Cart Val: ₹ <span class='highlight'>"
							+ totalPrice
							+ "</span> Cart Qty: <span class='highlight'>"
							+ totalQuantity + "</span> ";
					$('#totalOutput').append(output);

				}
				
				$(document).keypress(
						  function(event){
						    if (event.which == '13') {
						      event.preventDefault();
						    }
						});
			
				$(document).on('focusin', 'input', function(){
				    console.log("Saving value " + $(this).val());
				 $(this).data('val', $(this).val());
				}).on('change','input', function(){
				    var prev = $(this).data('val');
				    var current = $(this).val();				    
				    console.log("prev " +prev);
				    console.log("currentchangechangechange " + current);
				    saveInputdata(this,prev)
				});
				
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
					console.log(prev,'prev');
					console.log(cuurentQTY,'cuurentQTY');
					var price = parseFloat(price.replace('₹ ', ''));
					totalPrice = price * cuurentQTY;
					totalPrice = totalPrice.toFixed(2);
					currentTotalPrice = currentTotalPrice.toFixed(2);
					
					totalPrices = +currentTotalPrice + +totalPrice  ;
					totalQuantitys =currentTotalQuantity + cuurentQTY ;

					console.log(totalPrices,'totalPrices');
					console.log(totalQuantitys,'totalQuantitys');
					var output = "Total Price: ₹<span class='highlight'>" + totalPrices.toFixed(2)
							+ "</span> Total Qty: <span class='highlight'>" + totalQuantitys
							+ "</span>";
					$('#totalOutput').html(output);
					
				}
				
				function saveInputdata(element,prev) {
					updateResetButton();
					var noBudget =$('#totalOutput2').text();
					
					if(noBudget != 'No budget is defined')
					{
						var yearlybudget = document.querySelector("#totalOutput2 .highlight:nth-child(2)");
						if(parseInt(yearlybudget.textContent)!=0){
							
							var yearlybudget = document.querySelector("#totalOutput2 .highlight:nth-child(2)");
						//	var monthlyBalanceText = $("#totalOutput2 span.highlight:eq(2)").text();
							var indentBalanceText = $("#totalOutput2 span.highlight:eq(4)").text();
							//var monthlyBalance = parseFloat(monthlyBalanceText.replace(/[^\d.]/g, ''));
							var indentBalance = parseFloat(indentBalanceText.replace(/[^\d.]/g, ''));
							//var total =monthlyBalance+indentBalance;
							var totalAmount = document.querySelector("#totalOutput .highlight:nth-child(1)");
							//var total = monthlyBalance -( parseFloat(totalAmount.textContent) -indentBalance);
							
								var InputArray = [];
								var input = element.parentNode
										.querySelector('.count-input');
								var productCard = input.closest('.card');
								var productName = productCard.querySelector('.product').innerText;
								var productPrice = productCard.querySelector('.price').innerText;
								var productID = productCard.querySelector('.productID').innerText.split(':')[1].trim();
								var currentValue = parseInt(input.value);
								if (currentValue >= 0) {
									input.value = currentValue ;
									var backpricelement = document.querySelector("#totalOutput .highlight:nth-child(1)").textContent;
									var backqty = document.querySelector("#totalOutput .highlight:nth-child(2)").textContent;
									calculateInputTotal(input.value,productPrice,prev);
									var pricelement = document.querySelector("#totalOutput .highlight:nth-child(1)").textContent;
									var yearlyAmount = document.getElementById('yearAmount').textContent;

									console.log('this is checkingd',pricelement,yearlyAmount)
									if(Number(pricelement.trim()) > Number(yearlyAmount.trim())){
										Swal.fire({
													icon : 'error',
									        	  title: 'Indent amount is exceeded the yearly budget!',
													focusConfirm : false,
												})
												input.value=prev;
										document.querySelector("#totalOutput .highlight:nth-child(1)").textContent = backpricelement;
										document.querySelector("#totalOutput .highlight:nth-child(2)").textContent = backqty;
										return;
									}
									
									InputArray.push({
										productName : productName,
										productPrice : productPrice,
										quantity : currentValue,
										productID : productID
									});
									console.log(InputArray,'murali1')
									//calculateTotal();
									$.ajax({
										type : "POST",
										url : "tempCartIndentCreation",
										contentType : 'application/json',
										data : JSON.stringify(InputArray),

										success : function(response) {

											//var data = jQuery.parseJSON(response);
											
											if(response == "Indent Creation SuccessFully" ||response == 'Indent update SuccessFully'){
										        	
										        	
										        	}else{
										        		$(".loading").hide();
										        		Swal.fire({
									   			        	
							 	   			        	  icon: 'error',
							 	   			        	  text: response,
							 	   			        	  showCloseButton: false,
							 	   			        	  focusConfirm: false,
							 	   			        	
							 	   			        	})
										        	}
											
										},//end of success function
										error : function(error) {
											$(".loading").hide();
											Swal.fire({
										        	
										        	  icon: 'error',
										        	  text: error,
										        	  showCloseButton: false,
										        	  focusConfirm: false,
										        	
										        	})
											//$(".loading").hide();
										}
									})
								}
							}
							
				}else {
					Swal.fire({
			        	  icon: 'error',
			        	  text: 'No yearly Budget defined For this Cost center',
			        	  showCloseButton: false,
			        	  focusConfirm: false,
			        	
			        	});
					input.value=0;
				}
				}
			</script>


			<style>
.footer {
	background-color: #1F262D;
	padding-bottom: 1px;
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




			<!-- <footer class="footer">
				<div class="footer-copyright">
					<p>
						© 2019 <span>TITAN COMPANY LTD.</span> ALL RIGHTS RESERVED.
					</p>
				</div>
			</footer> -->

			<script type="text/javascript">
				document.body.style.zoom = "85%";
			</script>





		</div>
		<jsp:include page="/WEB-INF/jsp/views/header/footer.jsp"></jsp:include>
	</div>

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
	 <script>
    function restrictNegative(e) {
      // Get the input element value
      var inputValue = e.value;

      // Check if the input value is a negative number
      if (parseFloat(inputValue) < 0) {
        // Clear the input field
        e.value = 0;
          }
    }
    </script>

<script type="text/javascript">
		document.addEventListener("DOMContentLoaded", function() {
		    setTimeout(function() {
		        var element = document.getElementById('catid');
		        if (element) {
		            element.click();
		        }
		        console.log('it is calling in header')

		    }, 10); // Adjust the time in milliseconds (e.g., 1000 for 1 second)
		    updateResetButton();
		});
		
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
   

</body>
</html>