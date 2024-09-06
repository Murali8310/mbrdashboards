<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<script type="text/javascript" src="dist/js/jquery-3.6.4.min.js"></script>

<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">

<!-- Google Fonts -->
<link href='dist/css/fonts.css'
	rel='stylesheet' type='text/css' />

<!-- <link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/chosen/1.5.1/chosen.min.css">
 -->	
<link
	href="https://cdn.jsdelivr.net/npm/@mdi/font@5.x/css/materialdesignicons.min.css"
	rel="stylesheet">
<link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet" />
<!-- <link   src="assets/libs/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet"  > -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>stationary</title>

<style>
/* #navbarSupportedContent {
	background: #f58e0f;
} */

.badge {
	display: inline-block;
	min-width: 10px;
	padding: 3px 7px;
	font-size: 31px;
	font-weight: 700;
	line-height: 1;
	color: #fff;
	text-align: center;
	white-space: nowrap;
	vertical-align: middle;
	background-color: #0f95f5;
	/* border-radius: 10px; */
}

a:hover {
	text-decoration: none !important
}

.titanlogo {
	font-size: 31px;
	font-weight: 700;
	color: #fff;
}

@media only screen and (max-width: 769px) and (min-width: 280px) {
.float-right {
    float: none !important;
}
}

</style>
</head>

<body>

	<header class="topbar" style="position: fixed; width: 100%;"> <nav
		class="navbar top-navbar navbar-expand-md navbar-dark" style="background: #01AFAE;
		">
	<div class="navbar-header">
		<!-- This is for the sidebar toggle which is visible on mobile only -->
		<a class="nav-toggler waves-effect waves-light d-block d-md-none"
			href="javascript:void(0)"><i class="ti-menu ti-close"></i></a>
		<!-- ============================================================== -->
		<!-- Logo -->
		<!-- ============================================================== -->
		<a class="navbar-brand" href="landPage"> 
		<span 	
			style="padding: 5px 5px 5px 0px;"> <img
				src="assets/images/titan-logo.png" alt="homepage" class="light-logo"
				height="50px" width="50px" /> 
		

		</span> 
				
		</a>
		<!-- ============================================================== -->
		<!-- End Logo -->
		<!-- ============================================================== -->
		<!-- ============================================================== -->
		<!-- Toggle which is visible on mobile only -->
		<!-- ============================================================== -->
		<a class="topbartoggler d-block d-md-none waves-effect waves-light"
			href="javascript:void(0)" data-toggle="collapse"
			data-target="#navbarSupportedContent"
			aria-controls="navbarSupportedContent" aria-expanded="false"
			aria-label="Toggle navigation"><i class="ti-more"></i></a>
	</div>
	<!-- ============================================================== -->
	<!-- End Logo --> <!-- ============================================================== -->
	<div class="navbar-collapse collapse" id="navbarSupportedContent" style="background: #01AFAE;">
		<!-- ============================================================== -->
		<!-- toggle and nav items -->
		<!-- ============================================================== -->
		<ul class="navbar-nav float-left mr-auto">
			<li class="nav-item d-none d-md-block" id='sidebarToggle'><a
				class="nav-link sidebartoggler waves-effect waves-light"
				href="javascript:void(0)" data-sidebartype="mini-sidebar" id='clickid'>
				<i class="mdi mdi-menu font-24"></i>
				</a></li>		
		<ul class="navbar-nav" style="align-items: center;font-size: 20px;font-weight:550;"> Welcome To Stationery Portal</ul>	
			<!-- ============================================================== -->
			<!-- create new -->
			<!-- ============================================================== -->
			<li class="nav-item dropdown"><a
				class="nav-link dropdown-toggle" href="#" id="navbarDropdown"
				role="button" data-toggle="dropdown" aria-haspopup="true"
				aria-expanded="false"> <!--     <span class="d-none d-md-block"> <i class="fa fa-angle-down"></i></span>
                             <span class="d-block d-md-none"><i class="fa fa-plus"></i></span>   -->
			</a> <!--  <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                                <a class="dropdown-item" href="#">Action</a>
                                <a class="dropdown-item" href="#">Another action</a>
                                <div class="dropdown-divider"></div>
                                <a class="dropdown-item" href="#">Something else here</a>
                            </div> --></li>
			<!-- ============================================================== -->
			<!-- Search -->
			<!-- ============================================================== -->
			<!--  <li class="nav-item search-box"> <a class="nav-link waves-effect waves-dark" href="javascript:void(0)"><i class="ti-search"></i></a>
                            <form class="app-search position-absolute">
                                <input type="text" class="form-control" placeholder="Search &amp; enter"> <a class="srh-btn"><i class="ti-close"></i></a>
                            </form>
                        </li> -->
		</ul>
		<!-- ============================================================== -->
		<!-- Right side toggle and nav items -->
		<!-- ============================================================== -->
		<ul class="navbar-nav float-right" style="align-items: center;">
		<div style="font-size: 20px;"> 
				
		<i class="ti-user m-r-5 m-l-5"></i>  <c:if test="${role == 'Indent Manager'}">  
   Cost Center : <c:out value="${user_Name}"/>
</c:if> 

<c:if test="${role == 'Tray Manager'}">  
   Welcome : <c:out value="${user_Name}"/>
</c:if> 

<c:if test="${role == 'Buyer'}">  
   Welcome : <c:out value="${user_Name}"/>
</c:if> 
</div>
		
			<!-- ============================================================== -->
			<!-- Comment -->
			<!-- ============================================================== -->
			<!-- <li class="nav-item dropdown"><a
				class="nav-link dropdown-toggle waves-effect waves-dark"
				href="assets/pdf/UID_Portal_Manual.pdf" title="Help" target="_blank">
					<i class="mdi mdi-help font-24"></i>
			</a></li> -->
			<!-- ============================================================== -->
			<!-- End Comment -->
			<!-- ============================================================== -->
			<!-- ============================================================== -->
			<!-- Messages -->
			<!-- ============================================================== -->
			<%-- <li id='greetings' class="display-8 ">
								</li>
								<li class="display-6">
									<%= (String)session.getAttribute("user_Name")%>
								</li> --%>
			<!-- ============================================================== -->
			<!-- End Messages -->
			<!-- ============================================================== -->

			<!-- ============================================================== -->
			<!-- User profile and search -->
			<!-- ============================================================== -->
			<li class="nav-item dropdown" id="dropdownButton" aria-expanded="false"><a
				class="nav-link dropdown-toggle text-muted waves-effect waves-dark pro-pic"
				 data-toggle="dropdown" aria-haspopup="true"
				aria-expanded="false"> 
					<img src="assets/images/img_avatar.png" width="50px" alt="user"
					class="rounded-circle" width="31">
			</a>

				<div class="dropdown-menu dropdown-menu-right user-dd animated" id="dropdownContent" aria-hidden="true">
					<!--  <a class="dropdown-item" href="javascript:void(0)"><i class="ti-user m-r-5 m-l-5"></i> My Profile</a> -->


 						
					<a class="dropdown-item" id="userName" href="javascript:void(0)" id="">
					<i class="ti-user m-r-5 m-l-5"></i> <%
						 String name = "";
													
						 Object obj = session.getAttribute("user_Name");
						 if (obj != null) {
						 	name = (String) obj;
						 	out.println("User Name : " + name);
						 }
						 %> </a>

					<%
					String Store = "";
					Object objs = session.getAttribute("stores");
					if (objs != null) {
						Store = (String) objs;
					%>
					<a class="dropdown-item" href="javascript:void(0)"><i
						class="ti-harddrives m-r-5 m-l-5"></i> <%
							 out.println("Store : " + Store);
							 }
							 %> </a>
					
					<a class=" dropdown-item" id="userRole" href="javascript:void(0)"><i
						class="ti-wallet m-r-5 m-l-5"></i> 
						<% String role = "";
						Object objss = (String) session.getAttribute("role");
						if (objss != null) {
							role = (String) objss;
 							out.println("Role : " + role);
						}
 						%> </a> 				
 						
 						<!-- <a class=" dropdown-item" href="changePassword"><i
						class="ti-lock m-r-5 m-l-5"></i> Change Password </a> -->
						 <a
						class="dropdown-item" href="logout"><i
						class="fa fa-power-off m-r-5 m-l-5"></i> Logout</a>

					<!-- <div class="dropdown-divider"></div>
                                <div class="p-l-30 p-10"><a href="javascript:void(0)" class="btn btn-sm btn-success btn-rounded">View Profile</a></div>
                           -->
				</div></li>
			<!-- ============================================================== -->
			<!-- User profile and search -->
			<!-- ============================================================== -->
		</ul>
	</div>
	</nav> </header>
	
	
	
	<!-- 	<script type="text/javascript">
	$("body").on("click", "#userselect", function(){
				document.getElementById('div1').style.display = 'flex';
		</script> -->
		
		<script type="text/javascript">
		document.addEventListener("DOMContentLoaded", function() {
		    setTimeout(function() {
		        var element = document.getElementById('clickid');
		        if (element) {
		         // ] element.click();
		        	
		        }
		        console.log('it is calling in header')

		    }, 10); // Adjust the time in milliseconds (e.g., 1000 for 1 second)
		});

		</script>
		  <script type="text/javascript">
		  if(document.getElementById('userRole').textContent.split(':')[1].trim() == 'Indent Manager'){
		 $.ajax({
 			type : 'GET',
 			url : 'getBudgetDetails',
 			success : function(response) {
 				var allData =jQuery.parseJSON(response)[0];
 	        if(allData == undefined){
 	        	var element = document.getElementById('dashboardiD');
		        if (element) {
		            element.remove();
		        }	
 	        }
 			}
 		}); 
		  }
		  		  function toggleExpand() {
			  var expandBtn = document.getElementById('expandBtn');
			  var expandableSection = document.getElementById('expandableSection');

			  var isExpanded = expandBtn.getAttribute('aria-expanded') === 'true';

			  if (isExpanded) {
			    expandBtn.setAttribute('aria-expanded', 'false');
			    expandableSection.setAttribute('aria-hidden', 'true');
			  } else {
			    expandBtn.setAttribute('aria-expanded', 'true');
			    expandableSection.setAttribute('aria-hidden', 'false');
			  }
			}
		  		//  dropdownBtn is the button that toggles the dropdown
		  		var dropdownBtn = document.getElementById('dropdownButton');
		  		var dropdownContent = document.getElementById('dropdownContent');

		  		// Function to handle clicks outside the dropdown
		  		function handleClickOutside(event) {
		  		  if (event.target !== dropdownBtn && !dropdownContent.contains(event.target)) {
		  		    // Click occurred outside the dropdown
		  		    dropdownContent.classList.remove('show');
		  		  }
		  		}

		  		// event listener to the document for clicks
		  		document.addEventListener('click', handleClickOutside);
		  	// Function to handle click on the dropdown button
		  		function handleDropdownClick(event) {
		  		  dropdownContent.classList.toggle('show');
		  		  event.stopPropagation(); // Prevent the click event from propagating to the document
		  		}
		  		dropdownBtn.addEventListener('click', handleDropdownClick);
		</script> 
		
</body>
</html>