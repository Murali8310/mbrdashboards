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
						<h4 class="page-title display-5">Po Entry bulk Upload</h4>
						<div class="ml-auto text-right">
							<nav aria-label="breadcrumb">
								<ol class="breadcrumb">
									<li class="breadcrumb-item"><a href="landPage">Home</a></li>
									<li class="breadcrumb-item"><a href="usermanagementupload">Bulk
											Upload</a></li>
									<li class="breadcrumb-item active" aria-current="page">PO Entry
										BulkUpload</li>
								</ol>
							</nav>
						</div>
					</div>
				</div>
			</div>
			<!-- ============================================================== -->
			<!-- End Bread crumb and right sidebar toggle -->
			<!-- ============================================================== -->
			<!-- ============================================================== -->
			<!-- Container fluid  -->
			<!-- ============================================================== -->

			<div class="container-fluid">
				<!-- ============================================================== -->
				<!-- Start Page Content -->
				<!-- ============================================================== -->
				<%--  <% String role = (String) session.getAttribute("role");
             
             if(role.trim().equalsIgnoreCase("vinoothan")){
                       %>   --%>


				<%--  <%}else if(role.trim().toString().equalsIgnoreCase("ADMIN")){ %>  --%>

				<c:if test="${role == 'Buyer'}">
					<!-- Only show the page content if the user is an Buyer -->
					<div class="row">
						<div class="col-12">
							<div id="cfaalert"></div>
							<div class="frame" style="top: 215px">

								<form id="example-form" method="post" class=""
									enctype="multipart/form-data">
									<div class="center">
										<div class="title">
											<h1 style="font-size: 14px;">Drop file to upload</h1>
										</div>

										<div class="dropzone">
											<div class="content">
												<img src="assets/images/cloud.png"
													style="width: 94px; height: 70px;" class="upload-icon" />
												<input type="file" id="file" name="file" accept=".xlsx,.xls"
													class="upload-input input" />
											</div>

										</div>
										<span class="filename"></span>
										<button type="button" id="submitfiles" class="btn"
											name="uploadbutton">Upload file</button>



									</div>
								</form>
								<div style="margin-top: 330px;">
									<!-- <p style="font-weight:700;padding: 5px 6px 0px 6px;color:#fff">Note : Mobile number should
										start with +91 in Excel file</p> -->

									<a
										style="padding-top: 25px; padding: 15px 6px 6px 6px; color: #fff"
										id="selectedFileType"
										class="sidebar-link far sidebar-link dwn"
										href="downloadPoentryFile"> <b><span
											class="hide-menu" style="font-family: 'Montserrat'">
												PO Entry File </span></b></a>
								</div>

							</div>




						</div>
						<!--  End of row -->
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
			<%--   <% } --%>

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
/* var droppedFiles = false;
var fileName = '';
var $dropzone = $('.dropzone');
var $button = $('.upload-btn');
var uploading = false;
var $syncing = $('.syncing');
var $done = $('.done');
var $bar = $('.bar');
var timeOut;

$dropzone.on('drag dragstart dragend dragover dragenter dragleave drop', function(e) {
	e.preventDefault();
	e.stopPropagation();
})
	.on('dragover dragenter', function() {
	$dropzone.addClass('is-dragover');
})
	.on('dragleave dragend drop', function() {
	$dropzone.removeClass('is-dragover');
})
	.on('drop', function(e) {
	droppedFiles = e.originalEvent.dataTransfer.files;
	fileName = droppedFiles[0]['name'];
	$('.filename').html(fileName);
	//$('.dropzone .upload').hide();
});

$button.bind('click', function() {
	startUpload();
}); */
$("input:file").change(function (){
	fileName = $(this)[0].files[0].name;
	//alert(fileName);
	$('.filename').html(fileName);
//	$('.dropzone .upload').hide();
});

$(document).ready(function () {

	$('#submitfiles').click(function(){ 
		//alert("test");
		      var form = $('#example-form')[0]; //get the form containing the files
		      var data = new FormData(form);
	        var queryString = JSON.stringify($("#example-form").serializeArray());
	//alert(form);
var file = $('#file').val();
	var insti = $('#insti').val();
	console.log("this is for checking",$('#example-form')[0],data,file,insti)

	if (file == "" ) {
		Swal.fire({
			icon : 'warning',
			title : 'File required',
			focusConfirm : false,
		})
		return;
	}

	$(".loading").show();
		     $.ajax({
		        url: "uploadBulkPoentryExcelFile",
		        type: "POST",
		        enctype: 'multipart/form-data',
		        data: data, //pass the form data
		        processData: false,
		        contentType: false,
		        success: function (data) {
		       
	        	if(data == 'Sucessfully created'){
	        		  $("#example-form")[0].reset();
	  	            $(".loading").hide();    
	  	            $('.filename').html("");    
		        	Swal.fire({
		    			icon : 'success',
		    			title : 'PO Entry ',
		    			html: 'Sucessfully created. <br/>Upload completed',
		    			
		    			focusConfirm : false,
		    		})
		    		return;
	        		
	        	}else {

					Swal.fire({
						icon : 'success',
		    			title : 'PO Entry',
						html: data+' <br/>Upload completed',
						
						focusConfirm : false,
					})
				}
	        		$("#example-form")[0].reset();
		            $(".loading").hide();    
		            $('.filename').html("");
		        },	
		        error: function (data) {
		        $("#example-form")[0].reset();
	            $(".loading").hide();    
	            $('.filename').html("");
		        	Swal.fire({
		    			icon : 'warning',
		    			title : 'Budget Master',
		    			text : data,
		    			focusConfirm : false,
		    		})
		    		return;
		        }
		      }); 



	});
	});

</script>
</body>
</html>


