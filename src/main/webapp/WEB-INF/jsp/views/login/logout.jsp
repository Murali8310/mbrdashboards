<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isErrorPage="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<script type="text/javascript" src="resources/js/jquery-3.6.4.min.js"></script>
<script type="text/javascript">
		history.pushState(null, null, '');
		window.addEventListener('popstate', function(event) {
			history.pushState(null, null, '');
		});
	</script>
</head>
<body class="OpenSans-font">
	<div class="container-fluid paddR10">
		<header>

			<div class="common-header user-header-bg">
				<div class="col-xs-12 col-sm-6 col-md-6" style="padding: 0px;">
					<span> <img src="resources/images/Unilever-logo.png"
						class="img-responsive pull-left brand-logo" alt="logo">
					</span> <span>
						<h3 class="header-title">Titan Care Tech Portal</h3>
					</span>
				</div>
			</div>

		</header>
		<div class="body-content">

			<div class="login-details">
				<div class="row">
					<div
						class="col-md-4 col-sm-9 col-sm-offset-2 col-md-offset-4 error-box">
						<div class="row">
							<div class="error-header">Logged out</div>
							<div class="error-content" align="center">You've been
								logged out successfully !!!</div>
							<div class="error-footer  text-center">
								<a class="btn marginR10 btn-primary btn-sm" href="index.jsp">Click
									here to login again !!!</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	</div>
	<%@include file="../header/footer.jsp"%>

	<script>
		$(document).ready(function() {
			var introHeight = $('.error-box').height();
			popupLoading(introHeight);

		});

		function popupLoading(h) {
// 			var top = (window.innerHeight / 2) - (h / 2);

			$('.error-box').css({
				"top" : top
			});

		}
	</script>

	<script type="text/javascript">
 	history.pushState(null, null, location.href);
 		window.onpopstate = function () {
    	 history.go(1);
 	};
    </script>
</body>
</html>