<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html dir="ltr">

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<!-- Tell the browser to be responsive to screen width -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<!-- Favicon icon -->
<link rel="icon" type="image/png" sizes="20x20"
	href="assets/images/titan-logo.png">
<title>ISCM Stationery</title>
<!-- Custom CSS -->
<link href="dist/css/style.min.css" rel="stylesheet">

<link href="dist/css/loading.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="dist/js/AesUtil.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/4.1.1/crypto-js.min.js" integrity="sha512-E8QSvWZ0eCLGk4km3hxSsNmGWbLtSCSUcewDQPQWZF6pEU8GlT8a5fF32wOl1i8ftdMhssTrF/OhyGWwonTcXA==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/4.1.1/aes.min.js" integrity="sha512-4b1zfeOuJCy8B/suCMGNcEkMcQkQ+/jQ6HlJIaYVGvg2ZydPvdp7GY0CuRVbNpSxNVFqwTAmls2ftKSkDI9vtA==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/4.1.1/cipher-core.min.js" integrity="sha512-8tSa8JGzVhm1quXtz7BpvEm3wFvwtHkXmYkaEmaU1t7WghNxPdZLjGchi2pARJF2zhwQygyozEegjFROONKsBw==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>


        

<style>
label {
	color: #1F1B64;
	font-size: 13px;
}

.main-wrapper {
	margin: 0;
	padding: 0;
	font-family: sans-serif;
	background: linear-gradient(to right, #b92b27, #1565c0)
}

.auth-wrapper .auth-box {
	margin-top: 6%;
	padding: 18px;
	background-color: #fff;
	margin: 0px;
	width: 99%;
}
.form-group {
	text-align: right;
}

.text-red {
	color: red;
	font: icon;
}

.text-green {
	color: green;
	font: icon;
}

#protitle {
	color: #00b2ad;
	/*   text-shadow:0px 0px 34px mediumspringgreen; */
}

.border-secondary {
	background-color: rgba(0, 0, 0, 0.5) !important;
	max-width: 500px !important;
}

.bg-dark {
	/*  background-color: #ffff!important; */
	background-position-x: center;
	background-repeat: no-repeat;
	background-size: 120%;
	background-image: url(assets/images/watch-service-bg.jpg;);
}

.wrapper {
	/* background-image: url(assets/images/background.jpg);  */
	height: 100%;
	background-position: center;
	background-repeat: repeat;
	background-size: contain;
	background-color: #01AFAE;
	opacity:80%;
}

.login-card {
	border: 0;
	border-radius: 4px;
	box-shadow: 0 10px 30px 0 rgba(172, 168, 168, 0.43);
	overflow: hidden;
	background: #fff;
}

.login-card-img {
	border-radius: 0;
	position: absolute;
	width: 100%;
	height: 100%;
	-o-object-fit: cover;
	object-fit: cover;
}

.login-card .card-body {
	padding: 14px 20px 20px 20px;
}

input[type="radio"] {
  -ms-transform: scale(1.7);
  -webkit-transform: scale(1.7); 
  transform: scale(1.7);
}


.sm{
display : flex
}

.abm{
display : none
}

.admin{
display : none
}

input[type="radio"] {
    -ms-transform: scale(1.7);
    -webkit-transform: scale(1.7);
    transform: scale(1.7);
    margin-left: 5px;
    margin-right: 8px;
}
.btn-color{

background-color:#01AFAE;
border-color:#01AFAE;
}
.btn-color:hover{

background-color:#01AFAE;
border-color:#01AFAE;
}
</style>

</head>

<body>
	<div class="wrapper">
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
		<!-- Preloader - style you can find in spinners.css -->
		<!-- ============================================================== -->
		<!-- ============================================================== -->
		<!-- Login box.scss -->
		<!-- ============================================================= -->
		<div class="auth-wrapper d-flex no-block justify-content-end align-items-centre">
             <div class="col p-0 auth-full-height">
                <div class="d-flex justify-content-between flex-column h-100 px-5 py-3">
                    <div></div>
                    <div class="w-100 ">
                        <h1 class="display-4 text-white mb-4"  style="text-align:center;color:#fff !important;">ISCM Stationery Portal</h1>
                        <p class="text-white lead" style="text-align: center;
    color: #fff !important;
    font-weight: bold;">(Watches and Wearables)</p>
                    </div>
                    <div class="d-flex justify-content-between">
                        <span class="text-white" style="color:#fff !important;font-weight:bold;"> 2023-2024 Titan Company Limited</span>
                      
                    </div>
                </div>
            </div>
            <div class="auth-box">
         		
                <div id="loginform">
                    <div class="text-center p-t-20 p-b-20">
                        <span class="db">
                        <img width="230px" height="220px" src="assets/images/Titan_Company_Logo.png" alt="logo" />
                        </span>
                    </div>
                    <!-- tdvino -->
                    <c:if test="${not empty MESSAGE}">
	                    <div class="text-center">
	                        <span  id="error-message" class="text-red">${MESSAGE}</span>
	                    </div>
                    </c:if> 
                     <c:if test="${not empty PASSWORD_CREATE_SUCCESS}">
	                    <div class="text-center">
	                        <span class="text-green">${PASSWORD_CREATE_SUCCESS}</span>
	                    </div>
                    </c:if>   
                    
                     <c:if test="${not empty EMAIL_MISMATCH}">
	                    <div class="text-center">
	                        <span class="text-red">${EMAIL_MISMATCH}</span>
	                    </div>
                    </c:if>  
                    <c:if test="${not empty OTP_EXPIRED}">
	                    <div class="text-center">
	                        <span class="text-red">${OTP_EXPIRED}</span>
	                    </div>
                    </c:if>
                    

                    
                    <!-- Form -->
                  
                        <div class="row p-b-30 p-t-15">
                            <div class="col-12">
                            <div class="row" style="margin-bottom: 12px;">
														<div class="col-md-4 col-sm-4 col-4">
						<style>
						
						
						label {
	color: #1F1B64;
    font-size: 13px;
    font-weight: 600 !important;
						}
						</style>								
														
														
														<label class="labelcol"><input type="radio"  checked="checked"
															 name="user_selection" onclick="textboxchange1()"
															id="user_selection1" value="Indent Manager" >User</label>
													</div>
													<div class="col-md-4 col-sm-4 col-4">
														<label class="labelcol"><input type="radio"  onclick="textboxchange2()"
															name="user_selection" id="user_selection2" value="Buyer">
															Buyer </label>
													</div>
													<div class="col-md-4 col-sm-4 col-4">
													
														<label class="labelcol">
														<input type="radio"  onclick="textboxchange3()"
															name="user_selection" id="user_selection3"
															value="Tray Manager"><span>Distribution <span style="margin-left: 27px;">Team</span></span></label>
													</div>
													</div>
                             <div  class="input-group mb-3 sm" id="sm">
														<input type="text" name="login_id"  id="login_id"
															class="form-control form-control-lg"
															 aria-label="Username"
															aria-describedby="basic-addon1" >
													</div>
													<div class="input-group mb-3">
														<input type="password" name="password" id="password"
															class="form-control form-control-lg"
															placeholder="Password" aria-label="Password"
															aria-describedby="basic-addon1" required="">
													</div>
                            </div>
                        </div>
                       	<div class="row ">
						<div class="col-6" id="forgotPass">
							<div class="p-t-12">
								<a href="forgotPassword" class="btn btn-warning btn-color"
									role="button"><i class="fa fa-lock m-r-5"></i> Forgot
									password</a>
							</div>
						</div>
						<div class="col-6">
							<div class="form-group">
								<button onclick="login()" class="btn btn-success btn-color"
									type="button">
									<i class="fa fa-arrow-right m-r-5"></i>Login
								</button>
							</div>

						</div>
					</div>

</div></div></div></div>
<div class="loading" style="display: none;">Loading&#8230;</div>
		<!-- ============================================================== -->
		<!-- All Required js -->
		<!-- ============================================================== -->
		<script src="assets/libs/jquery/dist/jquery.min.js"></script>
		<!-- Bootstrap tether Core JavaScript -->
		<script src="assets/libs/popper.js/dist/umd/popper.min.js"></script>
		<script src="assets/libs/bootstrap/dist/js/bootstrap.min.js"></script>
		<!-- ============================================================== -->
		<!-- This page plugin js -->
		<!-- ============================================================== -->
		   <script>
        // Check if the page was reloaded using navigation
        if (performance.navigation.type === 1) {
            // Clear the MESSAGE value
            document.getElementById("error-message").textContent = "";
        }
    </script>
		<script>
		sm="Indent Manager";
    $('[data-toggle="tooltip"]').tooltip();
    $(".preloader").fadeOut();
    // ============================================================== 
    // Login and Recover Password 
    // ============================================================== 
    $('#to-recover').on("click", function() {
        $("#loginform").slideUp();
        $("#recoverform").fadeIn();
    });
    $('#to-login').click(function(){
        
        $("#recoverform").hide();
        $("#loginform").fadeIn();
    });
    </script>
		<script type="text/javascript">
 	history.pushState(null, null, location.href);
 		window.onpopstate = function () {
    	 history.go(-1);
 	};
 	
 	$("input:text[name='login_id']").attr('placeholder','Enter Your Cost Center');
 	function textboxchange1(){

 		$("input:text[name='login_id']").attr('placeholder','Enter Your Cost Center');
 		$("input:text[name='login_id']").val('');
 		
 	}
function textboxchange2(){

		$("input:text[name='login_id']").attr('placeholder','Enter Buyer AD ID');
		$("input:text[name='login_id']").val('');
	
 	}
function textboxchange3(){

	$("input:text[name='login_id']").attr('placeholder','Enter Distributor User ID');
	$("input:text[name='login_id']").val('');
	}
	


	//document.body.style.zoom = "85%";
    </script>
    						<%
String session_val = (String)session.getAttribute("key"); 
System.out.println("session_val"+session_val);
%>	
    <script type="text/javascript">
  function  login() {
	  var key= '<%=session_val%>';
	 // alert("session_obj"+session_obj);
	//alert( $("#key").val());
	  var iv = CryptoJS.lib.WordArray.random(128/8).toString(CryptoJS.enc.Hex);
      var salt = CryptoJS.lib.WordArray.random(128/8).toString(CryptoJS.enc.Hex);

      var aesUtil = new AesUtil(128, 1000);
      var ciphertext = aesUtil.encrypt(salt, iv, key, $("#password").val());

  
      var aesPassword = (iv + "::" + salt + "::" + ciphertext);
      var password = btoa(aesPassword);
      console.log(aesPassword)
      console.log(password)
		var user_selection1 = $('#user_selection1').is(':checked');
		var user_selection2= $('#user_selection2').is(':checked');
		var user_selection3 = $('#user_selection3').is(':checked');
		if(user_selection1 == true){
			user_selection= 'Indent Manager'
		}else	if(user_selection2 == true){
			user_selection= 'Buyer'
		}else	if(user_selection3 == true){
			user_selection= 'Tray Manager'
		}

		  const login_id = $("#login_id").val();
		  $(".loading").show();
 	  $
		.ajax({
			type : "POST",
			url : "loginSubmit",
			data : "login_id=" + login_id + "&user_selection=" + user_selection
					+ "&password=" + password ,

			success : function(response) {
				
				if(response=="landPage"){
					$(".loading").hide();
					location.href = "landPage"
				}
				if(response=="login"){
					$(".loading").hide();
					location.href = "login"
				}
				//var res = jQuery.parseJSON(response);

				
			}, error: function(xhr, textStatus, errorThrown) {
	              $("#loading").hide();

	             // Swal.fire("Error!", "Update failed: " + textStatus, "error");
	            },
		});  

	  
  } 
  
/*   $(document)
  .ready(
  		function() {
  			window.onbeforeunload = function() {
  			    $(".text-red").text(""); // Clear the error message text
  			    $("#login_id").removeClass("border-danger"); // Remove the red border from the input field
  			    $("#error-message").text(""); // Clear the error message content
  			};
  		});
  		
  */
  
  
  </script>
  
  <script>
            $(document).ready(function(){
                document.getElementById("login_id").value = localStorage.getItem("item1");
               // document.getElementById("user_selection2").value = localStorage.getItem("item2");
              
                
                var data = localstorage.getItem('datax');
                // alert(JSON.parse(data));
                 if(JSON.parse(data).myRadioButtonId == 'checked'){
                   document.getElementById("user_selection2").checked = true;
                 }
                 
            });
        </script>
        <script>
            $(window).on('beforeunload', function() {
            	
                localStorage.setItem("item1",document.getElementById("login_id").value);
               // localStorage.setItem("item2",document.getElementById("user_selection2").value);
                
            });

        </script>
        
        
  <script src="dist/js/sweetalert2.min.js"></script>
<link rel="stylesheet" href="dist/css/sweetalert2.min.css">
  <script type="text/javascript">

</script>
</body>

</html>