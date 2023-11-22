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

.cursor {
	cursor: pointer;
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

@media ( max-width : 768px) {
	.col-md-4 {
		padding-right: 0px !important;
	}
	.page-title {
		margin-top: 20px !important;
	}
}

@media ( min-width : 768px) {
	.oneline {
		display: inline-flex;
	}
}
</style>
</head>
<script>
function deletemenu(id) {
	   // 	alert(id);
	        var checkstr =  confirm('are you sure you want to delete this?');
	        if(checkstr == true){
	          // do your code
	          
	          window.location.href = "deleteMenu?menuName="+id;
	        }else{
	        return false;
	        }
	      }
	    
</script>
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
						<h4 class="page-title display-5">Master Menu</h4>
						<div class="ml-auto text-right">
							<nav aria-label="breadcrumb">
								<ol class="breadcrumb">
									<li class="breadcrumb-item"><a href="landPage">Home</a></li>
									<li class="breadcrumb-item"><a
										href="getUserManagementSubMenu">Employee Management</a></li>
									<li class="breadcrumb-item active" aria-current="page">Master
										Menu</li>
								</ol>
							</nav>
						</div>
					</div>
				</div>
			</div>

			<!-- ============================================================== -->
			<!-- Container fluid  -->
			<!-- ============================================================== -->
			<div class="container-fluid">
				<!-- ============================================================== -->
				<!-- Start Page Content -->
				<!-- ============================================================== -->
				<div class="card">
					<div class="card-body wizard-content">
						<!--   <h4 class="card-title"></h4> -->
						<h6 class="card-subtitle"></h6>
						<c:if test="${SUCCESS!= null}">
							<div class="alert alert-success alert-block">
								<a class="close" data-dismiss="alert" href="#">�</a>
								<h4 class="alert-heading">Success!</h4>
								<c:out value="${SUCCESS}"></c:out>
							</div>
						</c:if>

						<c:if test="${SUCCESSDELETEUSER != null}">
							<div class="alert alert-success alert-block">
								<a class="close" data-dismiss="alert" href="#">X</a>
								<h4 class="alert-heading">Success!</h4>
								<c:out value="${SUCCESSDELETEUSER}"></c:out>
							</div>
						</c:if>


						<c:if test="${ERROR != null}">
							<div class="alert alert-success alert-block"
								style="background-color: #a52a2a26">
								<a class="close" data-dismiss="alert" href="#">X</a>
								<h4 class="alert-heading" style="color: red">Error!</h4>
								<span style="color: lightRed"></span>
								<c:out value="${ERROR}"></c:out>
								</span>
							</div>
						</c:if>

						<form id="" action="addMenu" method="post" class="m-t-40">

							<div class="col-md-12 oneline">
								<div class="col-md-4 ">
									<lable>
									<b>Menu Name</b></lable>
									</br>
									</br> <input type="text" name="menuName" class="form-control"
										aria-invalid="true" required="required">
								</div>
								<div class="col-md-4 ">
									<lable>
									<b>Source Name</b></lable>
									</br>
									</br> <input type="text" name="source" class="form-control"
										aria-invalid="true" required="required">
								</div>
							</div>
							&nbsp;&nbsp;&nbsp;&nbsp; <input type="submit"
								class="btn btn-primary" style="margin-top: 10px;" value="Add" />
						</form>
					</div>
				</div>


				<div class="card">
					<div class="card-body">
						<h5 class="card-title"></h5>
						<div class="">
							<div id="zero_config_wrapper"
								class="dataTables_wrapper container-fluid dt-bootstrap4">
								<!--         <div class="row">
                                    <div class="col-sm-12 col-md-6"><div class="dataTables_length" id="zero_config_length"><label>Show 
	                                    <select name="zero_config_length" aria-controls="zero_config" class="form-control form-control-sm"><option value="10">10</option>
	                                    <option value="25">25</option><option value="50">50</option><option value="100">100</option></select> entries</label></div>
	                                  </div>
                                    <div class="col-sm-12 col-md-6"><div id="zero_config_filter" class="dataTables_filter"><label>Search
										<input type="search" class="form-control form-control-sm" placeholder="" aria-controls="zero_config"></label></div>
									</div>
									</div> -->
								<div class="row">
									<div class="col-sm-12">

										<table id="example"
											class="table table-striped table-bordered display nowrap"
											style="width: 100%">
											<thead class="thead-light">
												<tr>
													<th scope="col"><b>SL.NO</b></th>
													<th scope="col" hidden=""><b>Menu_ID</b></th>
													<th scope="col"><b>Menu Name</b></th>
													<th scope="col"><b>Source Name</b></th>
													<th scope="col"><b>DELETE</b></th>
													<th scope="col"><b>UPDATE</b></th>
													<!-- <th scope="col"><b>SELECT</b></th> -->
												</tr>
											</thead>
											<tbody class="customtable">

												<c:forEach items="${menuNames}" var="menu" varStatus="loop">

													<tr>
														<!-- <th>
                                                    <label class="customcheckbox">
                                                        <input type="checkbox" class="listCheckbox" />
                                                        <span class="checkmark"></span>
                                                    </label>
                                                </th> -->
														<td>${loop.index+1}</td>
														<td hidden=""><input type="text" name="menu_id"
															value="${menu[0]}"></td>
														<td><input type="text" name="menuName"
															value="${menu[1]}"></td>
														<td>${menu[2]}</td>
														<td><input id="downloadtxt" type="button"
															value="Delete" onclick="deletemenu('${menu[1]}')"
															class="btn btn-danger" /></td>
														<%-- <td><a
															href="updateMenu?menu_id=${menu[0]}&menuName=${menu[1]}"><input
																id="updateMenu" type="button" value="UPDATE" class="btn btn-info" /></a></td>
								 --%>

														<td><a class="updateMenu"><input type="button"
																class="btn btn-info updateMenuClass" value="Update"></a>
														</td>
													</tr>

												</c:forEach>
											</tbody>
										</table>

									</div>
								</div>
							</div>

						</div>
					</div>

				</div>

			</div>
		</div>

	</div>
	</div>
	</div>
	<jsp:include page="/WEB-INF/jsp/views/header/footer.jsp"></jsp:include>

	<!-- 	<footer class="footer text-center">
				<span>2019 &copy Titan Company</span>
			</footer> -->


	<!-- 	<div class="loading">Loading&#8230;</div> -->

	<script src="assets/libs/jquery/dist/jquery.min.js"></script>
	<script src="assets/libs/bootstrap/dist/js/bootstrap.min.js"></script>
	<script
		src="assets/libs/perfect-scrollbar/dist/perfect-scrollbar.jquery.min.js"></script>
	<script src="dist/js/custom.min.js"></script>
	<script type="text/javascript"
		src="assets/libs/datatables/media/js/jquery.dataTables.min.js"></script>


	<!-- JS-Table data download -->
	<script
		src="https://cdn.datatables.net/1.10.19/js/dataTables.bootstrap4.min.js"></script>
	<script
		src="https://cdn.datatables.net/buttons/1.5.6/js/dataTables.buttons.min.js"></script>
	<script
		src="https://cdn.datatables.net/buttons/1.5.6/js/buttons.bootstrap4.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/jszip/3.1.3/jszip.min.js"></script>
	<script
		src="https://cdn.datatables.net/buttons/1.5.6/js/buttons.html5.min.js"></script>
	<script
		src="https://cdn.datatables.net/buttons/1.5.6/js/buttons.colVis.min.js"></script>

	<script src="assets/libs/jquery-validation/dist/jquery.validate.min.js"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/jquery-validate/1.17.0/additional-methods.min.js"></script>
	<script
		src="assets/libs/perfect-scrollbar/dist/perfect-scrollbar.jquery.min.js"></script>

	<!-- <script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.53/pdfmake.min.js"></script>
     <script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.53/vfs_fonts.js"></script> 
     <script src="https://cdn.datatables.net/buttons/1.5.6/js/buttons.print.min.js"></script> -->

	<script type="text/javascript">
		history.pushState(null, null, location.href);
		window.onpopstate = function() {
			history.go(1);
		};
	</script>

	<script>
		$(document).ready(
				function() {
					var table = $('#example').DataTable({
						scrollX : true,
						lengthChange : false,
						 'aoColumnDefs': [{
						        'bSortable': false,
						        'aTargets': [-1,-2,-4], 
						    }],
				
					//  buttons: [ 'copy', 'excel', 'pdf', 'colvis' ]
					//         buttons: [ 'excel','colvis' ]
					});

					table.buttons().container().appendTo(
							'#example_wrapper .col-md-6:eq(0)');
				});
		
		
		$("#example").on("click",".updateMenuClass", function(){
			var menu_id = $(this).closest('tr').find('input[name="menu_id"]').val();
			var menuName = $(this).closest('tr').find('input[name="menuName"]').val();
			//alert("menuId : "+menu_id+"MenuName : "+menuName);
			$(this).closest('tr').find(".updateMenu").attr('href','updateMenu?menu_id='+menu_id+'&menuName='+menuName);
			
			return true
			
		});
			
		
	</script>
</body>
</html>
