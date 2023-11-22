<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="resources/js/bootstrapValidator.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Reset Password</title>
<script type="text/javascript"
	src="resources/js/custom/login/changePwd.js"></script>
</head>
<body>
	<div class="change-pwd">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal">&times;</button>
			<h4 class="modal-title">Change Password</h4>
		</div>
		<form id="pwdform" method="post" action="" class="form-horizontal"
			style="padding: 10px 0;">
			<div class="modal-body">
				<div class="row">

					<input type="hidden" value="<c:out value="${user.userId}"></c:out>"
						id="userId" name="userId" />
					<div class="col-md-12" id="msg-1">
						<div id="msg-error" class="alert alert-danger fade in"></div>
						<div id="msg-success" class="alert alert-success fade in"></div>
						<div class="OpenSans-font">
							<div class="form-group">
								<label for="tme_oldPassword" class="col-sm-5 control-label">Old
									Password</label>
								<div class="col-sm-6">
									<input type="password" class="form-control reval"
										id="tme_oldPassword" name="current_password"
										placeholder="Old Password">
								</div>
							</div>
							<div class="form-group">
								<label for="tme_newPassword" class="col-sm-5 control-label">New
									Password</label>
								<div class="col-sm-6">
									<input type="password" class="form-control reval"
										id="tme_newPassword" name="new_password"
										placeholder="New Password">
								</div>
							</div>
							<div class="form-group">
								<label for="tme_confirmPwd" class="col-sm-5 control-label">Confirm
									New Password</label>
								<div class="col-sm-6">
									<input type="password" class="form-control reval"
										id="tme_confirmPwd" name="confirm_password"
										placeholder="Confirm New Password">
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<button type="button"
					class="btn btn-default chnage-close pull-right"
					data-dismiss="modal">CLOSE</button>
				<button type="submit" id="validateBtn"
					class="btn btn-primary chnagepwd-reset resetBtn  marginR10"
					disabled>RESET</button>
			</div>
		</form>
	</div>
</body>
</html>
