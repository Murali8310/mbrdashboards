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
<link href="dist/css/style.min.css" rel="stylesheet">

<link href="dist/css/loading.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet"
	href="https://cdn.materialdesignicons.com/5.9.55/css/materialdesignicons.min.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

	<script src="dist/js/sweetalert2.min.js"></script>
		<link rel="stylesheet" href="dist/css/sweetalert2.min.css" /> 

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
	padding: 10px;
}

.page-title {
	color: Teal;
}

.sidebar-item {
	margin-left: 10px;
}

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
	background-color: yellow;
	font-weight: bold;margin-right: 30px;
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
  font-weight: 800;
  font-size: 25px;
  color: red;
  text-align:center;
}
</style>
</head>

<body style="zoom: 85%;" class="">
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
							<ul aria-expanded="false" class="expanded  first-level"
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
		
		<div class="page-wrapper" style="background-color: white;">
		<div class="container-fluid2">
		
				</div>
			<div class="page-breadcrumb">
				<div class="row">
					<div class="col-12 d-flex no-block align-items-center">
						<h4 class="page-title"  >Product Catalogue</h4>
						<div  id="totalOutput"
						style="text-align: start; font-size: 18px;padding-left:10px;"></div>
						<div class="ml-auto text-right">
							<nav aria-label="breadcrumb">
								<ol class="breadcrumb">
									<li class="breadcrumb-item"><a href="landPage">Home</a></li>
									<li class="breadcrumb-item active" aria-current="page">Product
										Catalogue <!-- <a href="productDetails">Product Details</a> -->
									</li>
								</ol>
							</nav>
						</div>
					</div>
				</div>
			</div>
			<!-- 	<div id="variantCountDetails" style="margin: 5px 0px 5px 20px;"><span style="color:Teal">Showing <b> 1-12</b> of <b>62480</b> Watches </span></div>
			<div id="errorMessage" style="margin: 5px 0px 5px 20px;"></div> -->

			
				<div class="container-fluid">

				<div class="row el-element-overlay" id="el-element-overlay"></div>

				<!-- <div class="row el-element-overlay-loadMore"></div> -->


			

			</div>

			<script>
			$(document)
			.ready(
					function() {
						getAllProducts();
						getBudgetDetails();
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
//console.log(product,'productbu')
					//	var product = response[i];
						const productImage = document
								.getElementById("productImage");

						const imageSource = 'product/'
								+ product[0] + '.png';
						// productImage.src = imageSource;
						//console.log(imageSource)
						  const defaultImage = 'assets/images/No_Image_Availabl.png';
						//console.log(response, 'response')
						productListHTML += '<div class="col-lg-4 col-md-6 col-12">';
						productListHTML += '<div class="card"  style="background-color: #f7faf5" >';
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
						productListHTML += '<h5 class="product" class="m-b-0" style="text-align: start;font-weight:bold">'
								+ product[1] + '</h5>';

						productListHTML += '<div class="row">';
						productListHTML += '<div class="col-8">';
						productListHTML += '	<h5 class="m-b-0" style="text-align: start;font-weight:bold">';
						productListHTML += '		<label class="price" id="price">₹ '
								+ product[4] + '</label>';
						productListHTML += '		<label style="border:1px solid lightgrey;left:5px;">UOM : '
								+ product[5] + '</label>';
						productListHTML += '		<label class="productID" style="left:5px;">Product ID : '
								+ product[0] + '</label>';
						productListHTML += '	</h5>';
						productListHTML += '</div>';
						
						
						/* productListHTML += '<div class="col-4 ">';
						productListHTML += '	<div class="form-group d-flex justify-content-between"';
						 productListHTML += '	style="align-items: center;">';
						productListHTML += '	<a onclick="decrementCount(this)"';
						productListHTML += '		class="btn btn-sm btn-decre decreme"><i';
					    productListHTML += '		class="fas fa-minus-square fa-2x"></i></a> <input type="text"';
						productListHTML += '		id="countInput'
								+ i
								+ '" name="quantity" class="form-control count-input bold-and-large"';
						productListHTML += '		value="' + product[7] + '" onchange="saveInputdata(this)"> <a';
						productListHTML += '			onclick="incrementCount(this)" class="btn bnt-sm btn-incre "><i';
						productListHTML += '			class="fas fa-plus-square fa-2x"></i></a>';
						productListHTML += '	</div>';
						productListHTML += '</div>';*/
						
						productListHTML += '</div>'; 
						// Add more properties as needed
						productListHTML += '</div>';
						productListHTML += '</div>';
						productListHTML += '</div>';
						productListHTML += '</div>';
						

					}
					$('#el-element-overlay').html(productListHTML);
					calculateTotal();
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
					productListHTML += '<div class="card" style="background-color: #fff;">';
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
					productListHTML += '<h5 class="product" class="m-b-0" style="text-align: start;font-weight:bold">'
							+ product[1] + '</h5>';

					productListHTML += '<div class="row">';
					productListHTML += '<div class="col-8">';
					productListHTML += '	<h5 class="m-b-0" style="text-align: start;font-weight:bold">';
					productListHTML += '		<label class="price" id="price">₹ '
							+ product[4] + '</label>';
					productListHTML += '		<label style="border:1px solid lightgrey;left:5px;">UOM : '
							+ product[5] + '</label>';
					productListHTML += '		<label class="productID" style="left:5px;">Product ID : '
							+ product[0] + '</label>';
					productListHTML += '	</h5>';
					productListHTML += '</div>';
					
				/* 	productListHTML += '<div class="col-4 ">';
					productListHTML += '	<div class="form-group d-flex justify-content-between"';
					 productListHTML += '	style="align-items: center;">';
					productListHTML += '	<a onclick="decrementCount(this)"';
					productListHTML += '		class="btn btn-sm btn-decre decreme"><i';
				    productListHTML += '		class="fas fa-minus-square fa-2x"></i></a> <input type="text"';
					productListHTML += '		id="countInput'
							+ i
							+ '" name="quantity" class="form-control count-input bold-and-large"';
					productListHTML += '		value="' + product[7] + '" onchange="saveInputdata(this)"> <a';
					productListHTML += '			onclick="incrementCount(this)" class="btn bnt-sm btn-incre "><i';
					productListHTML += '			class="fas fa-plus-square fa-2x"></i></a>';
					productListHTML += '	</div>';
					productListHTML += '</div>';*/
					
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
				console.log( jQuery.parseJSON(response)[0] ,'response')
				var budgt =jQuery.parseJSON(response)[0]
			 var output = "Yearly Budget:   ₹<span class='highlight'>"
					+ budgt[3]  
					+ "</span>   Monthly Budget:   ₹<span class='highlight'>"
					+ budgt[4]   + "</span>  "+ "</span> Cumulative Balance:   ₹<span class='highlight'>"
					+ budgt[5]   + "</span>  "+ "</span> Indended Amount:   ₹<span class='highlight'>"
					+ budgt[6]   + "</span>  ";
			$('#totalOutput2').html(output); 
			}
		});
 }
	
</script>
			<script>



	$("#resetCategoriesID").on("click", function() {
		$('input[type=checkbox]').prop('checked', false);
		
	});

	

	
							</script>


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

			<script type="text/javascript">
				document.body.style.zoom = "85%";
			</script>





		</div>
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



</body>
</html>