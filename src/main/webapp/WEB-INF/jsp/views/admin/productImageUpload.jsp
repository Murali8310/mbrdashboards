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
<title>Stationery</title>
<!-- Custom CSS -->
<link href="assets/libs/flot/css/float-chart.css" rel="stylesheet">
<!-- Custom CSS -->
<link href=" dist/css/style.min.css" rel="stylesheet">

<link href="dist/css/loading.css" rel="stylesheet" type="text/css" />
<link
	href="assets/libs/bootstrap-datepicker/dist/css/bootstrap-datepicker.min.css"
	rel="stylesheet" type="text/css">
<link href="assets/libs/datatables/media/css/jquery.dataTables.min.css"
	rel="stylesheet" type="text/css" />
<link
	href="/assets/libs/datatables.net-bs4/css/dataTables.bootstrap4.css"
	rel="stylesheet" type="text/css" />

<link
	href="https://cdn.datatables.net/buttons/1.5.6/css/buttons.bootstrap4.min.css"
	rel="stylesheet" type="text/css" />
<!-- sweet alert -->

	<script src="dist/js/sweetalert2.min.js"></script>
		<link rel="stylesheet" href="dist/css/sweetalert2.min.css" /> 
<style>
.frame {
	position: absolute;
	top: 50%;    
	left: 52%;
    width: 385px;
    height: 402px;
	margin-top: -200px;
	margin-left: -200px;
	border-radius: 2px;
	box-shadow: 4px 8px 16px 0 rgba(0, 0, 0, 0.1);
	overflow: hidden;
	background: -webkit-linear-gradient(bottom left, #3A92AF 0%, #5CA05A 100%);;
	color: #333;
	font-family: 'Roboto';
}

.center {
	position: absolute;
	top: 50%;
	left: 50%;
	transform: translate(-50%, -50%);
	width: 301px;
    height: 249px;
	border-radius: 3px;
	box-shadow: 8px 10px 15px 0 rgba(0, 0, 0, 0.2);
	background: #fff;
	display: flex;
	align-items: center;
	justify-content: space-evenly;
	flex-direction: column;
}

.title {
	width: 100%;
	height: 50px;
	border-bottom: 1px solid #999;
	text-align: center;
}

h1 {
	font-size: 16px;
	font-weight: 300;
	color: #666;
}

.dropzone {
	width: 100px;
	height: 80px;
	border: 1px dashed #999;
	border-radius: 3px;
	text-align: center;
}

.upload-icon {
	margin: 7px 2px 2px 2px;
}

.upload-input {
	position: relative;
	top: -62px;
	left: 0;
	width: 100%;
	height: 100%;
	opacity: 0;
}

.btn {
	display: block;
	width: 140px;
	height: 40px;
	
	background: #6ECE3B;
	/* background: #1F1B64; */
	color: #fff;
	border-radius: 3px;
	border: 0;
	transition: all 0.3s ease-in-out;
	font-size: 14px;
}

.content {
	display: table-cell;
	vertical-align: middle;
}

.filename {
	display: block;
	color: #676767;
	font-size: 14px;
	line-height: 18px;
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

@media only screen and (max-width: 578px) and (min-width: 320px) {
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
/* 	.page-wrapper>.container-fluid {
		padding: 20px;
		min-height: calc(120vh - 180px);
	} */
}

@media only screen and (max-width: 578px) and (min-width: 375px) {
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
/* 	.page-wrapper>.container-fluid {
		padding: 20px;
		min-height: calc(120vh - 180px);
	} */
}

@media only screen and (max-width: 578px) and (min-width: 425px) {
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
/* 	.page-wrapper>.container-fluid {
		padding: 20px;
		min-height: calc(120vh - 180px);
	} */
}

@media only screen and (min-width: 260px) and (max-width: 320px) {
	.page-title {
		/*margin-top: 20% !important;*/
		font-size: 24px !important;
	}
	.breadcrumb {
		display: none;
	}
}

</style>
<script>
	function startUpload() {
		if (!uploading && fileName != '') {
			uploading = true;
			$button.html('Uploading...');
			/* $dropzone.fadeOut(); */
			$syncing.addClass('active');
			$done.addClass('active');
			$bar.addClass('active');
			timeoutID = window.setTimeout(showDone, 3200);
		}
	}

	function showDone() {
		$button.html('Done');
	}
</script>
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

		<div class="page-wrapper">
			
			<div class="page-breadcrumb">
				<div class="row">
					<div class="col-12 d-flex no-block align-items-center">
						<h4 class="page-title display-5">Product Image Upload</h4>
						<div class="ml-auto text-right">
							<nav aria-label="breadcrumb">
								<ol class="breadcrumb">
									<li class="breadcrumb-item"><a href="landPage">Home</a></li>
									<li class="breadcrumb-item"><a href="usermanagementupload">Bulk Upload</a></li>
									<li class="breadcrumb-item active" aria-current="page">Product Image Upload</li>
								</ol>
							</nav>
						</div>
					</div>
				</div>
			</div>
			<div class="container-fluid">
				<div class="row">
					<div class="col-12">
						<div id="cfaalert"></div>
						<div class="frame" style="top: 215px">
								<form id="example-form" method="post" class=""
									enctype="multipart/form-data">
									
									<div class="center">
								<select id="productId" name="productId" class="form-control"
									aria-invalid="true" required="required" Style="width:97%;">
									<option value="">Select Product Id</option>
									<c:forEach items="${ProductIdList}" var="productId">
										<option value="${productId[0]}">${productId[1]}</option>
									</c:forEach>
								</select>
								<div class="dropzone">
								<div class="content">
									<img src="assets/images/cloud.png" style="width: 94px; height: 70px;"
										class="upload-icon" />
								<input type="file"  id="file" name="file"  accept=".png"
										class="upload-input input" />
								</div>

								</div>
								<span class="filename"></span>
								<button  type="button" id="submitfiles" class="btn" name="uploadbutton">Upload
									file</button>
									</div>
								</form>
							
						
						</div>
					</div>
				</div>

			</div>

			<jsp:include page="/WEB-INF/jsp/views/header/footer.jsp"></jsp:include>
		</div>
	</div>
	<div class="loading" style="display: none;">Loading&#8230;</div>
	<script src="assets/libs/jquery/dist/jquery.min.js"></script>
	<script src="assets/libs/bootstrap/dist/js/bootstrap.min.js"></script>
	<script
		src="assets/libs/perfect-scrollbar/dist/perfect-scrollbar.jquery.min.js"></script>
	<script src="dist/js/custom.min.js"></script>


	<script>

$("input:file").change(function (){
	fileName = $(this)[0].files[0].name;
	var fileExtension = fileName.split('.').pop();
	var fileNameWithoutExtension = fileName.split('.').slice(0, -1).join('.');
	$('.filename').html($('#productId').val()+'.'+fileExtension);
});

$(document).ready(function () {

	$('#submitfiles').click(function(){
		//alert( $('#example-form')[0])
		      var form = $('#example-form')[0]; 
		      var data = new FormData();
		      var productId = $('#productId').val();
		        var file = $('#file')[0].files[0];
		        var fileExtension = file.name.split('.').pop();
		        var newFilename = productId + '.' + fileExtension;
		        data.append('productId', productId);
		       // data.append('file', file);
		        data.append('file', file, newFilename);
	        var queryString = JSON.stringify($("#example-form").serializeArray());
		/* var file = $('#file').val();
		var productId = $('#productId').val(); */
		if (productId == "" ) {
			Swal.fire({
				icon : 'warning',
				title : 'Select product id required',
				focusConfirm : false,
			})
			return;
		}
		if (file == "" ) {
			Swal.fire({
				icon : 'warning',
				title : 'File required',
				focusConfirm : false,
			})
			return;
		}
		console.log(data,'data')
		$.ajax({
	        url: "uploadProductImageFile",
	        type: "POST",
	        enctype: 'multipart/form-data',
	        data: data, 
	        processData: false,
	        contentType: false,
	        success: function (data) {
	        	Swal.fire({
					icon : 'success',
					title : data,
					showCloseButton : false,
					focusConfirm : true,

				})
	        }

		});
	});
	});

</script>
</body>
</html>


