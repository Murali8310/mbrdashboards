<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<html>
<head>
<style>
table td {
	vertical-align: top;
	border: solid 1px #888;
	padding: 10px;
}
</style>
</head>
<body>
	<table>
		<tr>
			<td>
					<img width="100%" height="200px" src="assets/images/stationaryError.png" alt="logo" />
			</td>
			<td>
			<img width="100%" height="200px" src="assets/images/errorimage.jpg" alt="logo" />
			</td>
		</tr>
		<tr>
			<td>Date</td>
			<td>${timestamp} <h4 style="color: red;">Send screenshots to
					nirajprasad@titan.co.in and masinenikrishnasai@titan.co.in. <a href="login" role="button"> Click here to go to Login Page
					</a></h4></td>
		</tr>
		<tr>
			<td>Error</td>
			<td>${error}</td>
		</tr>
		<tr>
			<td>Status</td>
			<td>${status}</td>
		</tr>
		<tr>
			<td>Message</td>
			<td>${message}
			
			</td>
		</tr>
		<tr>
			<td>Exception</td>
			<td>${exception}</td>
		</tr>
		<tr>
			<td>Trace</td>
			<td><pre>${trace}</pre></td>
		</tr>
	</table>
</body>
</html>