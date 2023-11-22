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
<title>Titan UID Portal</title>
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
<style>
.example-form {
	margin-top: 2px !important;
}

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

.page-title {
	color: Teal;
}

.oneline {
	margin-left: -20px;
}

#quarterDivId   .custom-control-label {
	margin-top: -10px;
	padding-right: 20px
}

.error {
	color: #a94442;
}

@media ( min-width : 768px) {
	.oneline {
		display: inline-flex;
		margin-left: 20%;
	}
	.getbutton {
		margin-left: 25%;
	}
	.text-right {
		font-size: 18px;
	}
	.form-control-file {
		font-size: 18px;
	}
}

@import url(https://fonts.googleapis.com/css?family=Open+Sans:400);

.frame {
	position: absolute;
	top: 50%;
	left: 44%;
	width: 44%;
	height: 400px;
	/*   margin-top: -200px; */
	margin-left: -20%;
	border-radius: 2px;
	box-shadow: 1px 2px 10px 0px rgba(0, 0, 0, 0.3);
	background: #3A92AF;
	background: -webkit-linear-gradient(bottom left, #3A92AF 0%, #5CA05A 100%);
	background: -moz-linear-gradient(bottom left, #3A92AF 0%, #5CA05A 100%);
	background: -o-linear-gradient(bottom left, #3A92AF 0%, #5CA05A 100%);
	background: linear-gradient(to top right, #3A92AF 0%, #5CA05A 100%);
	filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#3A92AF',
		endColorstr='#5CA05A', GradientType=1);
	color: #fff;
	font-family: 'Open Sans', Helvetica, sans-serif;
	-webkit-font-smoothing: antialiased;
	-moz-osx-font-smoothing: grayscale;
}

.input {
	position: absolute !important;
	top: 0 !important;
	left: 0 !important;
	right: 0 !important;
	bottom: 0 !important;
	opacity: 0 !important;
}

.center {
	position: absolute;
	width: 300px;
	height: 260px;
	top: 70px;
	left: 25%;
	background: #fff;
	box-shadow: 8px 10px 15px 0 rgba(0, 0, 0, 0.2);
	border-radius: 3px;
}

.title {
	font-size: 16px;
	color: #676767;
	line-height: 50px;
	height: 50px;
	border-bottom: 1px solid #D8D8D8;
	text-align: center;
}

.dropzone {
	position: absolute;
	z-index: 1;
	box-sizing: border-box;
	display: table;
	table-layout: fixed;
	width: 100px;
	height: 80px;
	top: 86px;
	left: 100px;
	border: 1px dashed #A4A4A4;
	border-radius: 3px;
	text-align: center;
	overflow: hidden; &. is-dragover { border-color : #666;
	background: #eee;
}

.content {
	display: table-cell;
	vertical-align: middle;
}

.upload {
	margin: 6px 0 0 2px;
}

.input {
	position: absolute;
	top: 0;
	left: 0;
	right: 0;
	bottom: 0;
	opacity: 0;
}

}
.upload-btn {
	position: absolute;
	width: 140px;
	height: 40px;
	left: 80px;
	bottom: 24px;
	background: #6ECE3B;
	border-radius: 3px;
	text-align: center;
	line-height: 40px;
	font-size: 14px;
	box-shadow: 0 2px 0 0 #498C25;
	cursor: pointer;
	transition: all .2s ease-in-out;
	&:
	hover
	{
	box-shadow
	:
	0
	2px
	0
	0
	#498C25
	,
	0
	2px
	10px
	0
	#6ECE3B;
}

}
.bar {
	position: absolute;
	z-index: 1;
	width: 300px;
	height: 3px;
	top: 49px;
	left: 0;
	background: #6ECE3B;
	transition: all 3s ease-out;
	transform: scaleX(0);
	transform-origin: 0 0;
	&.
	active
	{
	transform
	:
	scaleX(
	1
	)
	translate3d(
	0
	,
	0
	,
	0
	);
}

}
.syncing {
	position: absolute;
	top: 109px;
	left: 134px;
	opacity: 0;
	&.
	active
	{
	animation
	:
	syncing
	3.2s
	ease-in-out;
}

}
.dwn {
	position: absolute;
	width: 300px;
	height: 40px;
	left: 18px;
	bottom: -2px;
	color: azure;
	/* background: #6ECE3B; */
	border-radius: 3px;
	/* text-align: center; */
	/* line-height: 36px; */
	/* font-size: 14px; */
	/* box-shadow: 0 2px 0 0 #498c25; */
	/* cursor: pointer; */
	/* transition: all 0.2s ease-in-out; */
}

.alert-success {
	background-color: #fff6f6 !important;
	color: #000066 !important;
	z-index: 1;
	font-size: 16px;
	border-color: #c3ebd9 !important;
}

.filename {
	display: block;
	color: #676767;
	font-size: 14px;
	line-height: 18px;
	margin-left: 30%;
	margin-top: 126px;
}

.done {
	position: absolute;
	top: 112px;
	left: 132px;
	opacity: 0; &. active { animation : done .5s ease-in 3.2s;
	animation-fill-mode: both;
}

}
@
keyframes syncing { 0% {
	transform: rotate(0deg);
}

10
%
{
opacity
:
1;
}
90
%
{
opacity
:
1;
}
100
%
{
transform
:
rotate(
360deg
);
opacity
:
0;
}
}
@
keyframes done {from { opacity:0;
	
}

to {
	opacity: 1;
}

}
@media ( max-width : 768px) {
	.page-title {
		margin-top: 20px !important;
	}
}

th {
	font-size: 16px;
}

input[type="radio"] {
	background-color: initial;
	cursor: default;
	appearance: auto;
	box-sizing: border-box;
	margin: 3px 3px 0px 5px;
	padding: initial;
	border: initial;
}
</style>
<script>




function startUpload() {
	if (!uploading && fileName != '' ) {
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
	<div id="main-wrapper">
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
						<h4 class="page-title  display-4">Bulk User Upload File</h4>
						<div class="ml-auto text-right">
							<nav aria-label="breadcrumb">
								<ol class="breadcrumb">
									<li class="breadcrumb-item"><a href="landPage">Home</a></li>
									<li class="breadcrumb-item"><a href="getUploadSubMenu">Employee
											Management</a></li>
									<li class="breadcrumb-item active" aria-current="page">Upload
										User File</li>
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


				<div class="row">
					<div class="col-12">
						<div id="cfaalert"></div>

						<div class="frame">

							<a id="selectedFileType"
								class="sidebar-link far sidebar-link dwn"
								href="downloadUserMasterFormatFile"> <b><span
									class="hide-menu dwn" style="font-family: verdana"> Bulk
										Users Format File </span></b></a>
							<div class="center">
								<form id="example-form" method="post" class=""
									enctype="multipart/form-data">
									<div class="bar"></div>
									<div class="title">Drop file to upload</div>
									<div class="dropzone">
										<div class="content">
											<img src="assets/images/up1.jpg"
												style="width: 98px; height: 85px;" class="upload"> <input
												type="file" class="input" id="file" name="file">
										</div>

									</div>
									<span class="filename"></span>
									<!-- 	<img src="https://100dayscss.com/codepen/syncing.svg" class="syncing">
		<img src="https://100dayscss.com/codepen/checkmark.svg" class="done"> -->

									<button type="button" id="submitfiles" class="upload-btn">Upload</button>
								</form>
							</div>

						</div>





					</div>
					<!--  End of row -->
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
var droppedFiles = false;
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
});
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

	//alert(queryString);
var file = $('#file').val();
	var insti = $('#insti').val();
	if (file == "" ) {
		 trs = '<div class="alert alert-success alert-block"  style="background-color: #a52a2a26">Please Select file'
 		+ '<a class="close" data-dismiss="alert" href="#">X</a>';
          + '<h4 class="alert-heading" style="color:red">Error!</h4>';
          + '<span style="color:lightRed">'+data+'</span></span>'
     + '</div>';
 	$("#cfaalert").children()
 	.remove();
 $("#cfaalert").append(
 	trs);
		return false;
	}

	$(".loading").show();
		      $.ajax({
		        url: "uploadBulkUserExcelFile",
		        type: "POST",
		        enctype: 'multipart/form-data',
		        data: data, //pass the form data
		        processData: false,
		        contentType: false,
		        success: function (data) {
		        	
	         //   alert(data);
	          //  console.log(data);
	            
	             trs = '<div class="alert alert-success alert-block"  style="background-color: #a52a2a26">'
	        		+ '<a class="close" data-dismiss="alert" href="#">X</a>'
	                 + '<h4 class="alert-heading" style="color:red"></h4>'
	                 + '<span style="color:lightRed">'+data+'</span></span>'
	            + '</div>';
	        	$("#cfaalert").children()
	        	.remove();
	        $("#cfaalert").append(
	        	trs); 
	        $("#example-form")[0].reset();
		          //  console.log("Successfully uploaded " + data);
		            $(".loading").hide();    
		           // $("#example-form")[0].reset();
		            $('.filename').html("");
		        },
		        error: function (data) {
		        	$(".loading").hide();
		        	
		        	 trs = '<div class="alert alert-success alert-block"  style="background-color: #a52a2a26">'
			        		+ '<a class="close" data-dismiss="alert" href="#">X</a>'
			                 + '<h4 class="alert-heading" style="color:red">Error!</h4>'
			                 + '<span style="color:lightRed">'+data+'</span></span>'
			            + '</div>';
			        	$("#cfaalert").children()
			        	.remove();
			        $("#cfaalert").append(
			        	trs);    	
		        	
		        //	//alert("error");
		            //console.log(data.json);
		        }
		      });



	});
	});

</script>
</body>
</html>


