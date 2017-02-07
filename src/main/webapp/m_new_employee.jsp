<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="com.revature.beans.User"%>
<!-- Only the first 3 matter -->
<!-- Core = java core e.g. if/loops/print -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- fmt = formatting date/time/money etc -->
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!-- functions = useful for Strings -->
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, shrink-to-fit=no, initial-scale=1">

<title>Manager Create New Employee</title>

<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet">
</head>

<style>
body:before {
	content: "";
	position: absolute;
	background:
		url(http://s1.picswalls.com/wallpapers/2015/11/21/league-of-legends-hd-wallpapers_111242969_289.jpg);
	background-size: cover;
	z-index: -1; /* Keep the background behind the content */
	height: 100%;
	width: 100%; /* Using Glen Maddern's trick /via @mente */
	/* don't forget to use the prefixes you need */
	transform: scale(1);
	transform-origin: top left;
	overflow-y: scroll;
	filter: blur(2px);
}
</style>

<!--****************************************************************Body Pages****************************************************************-->
<body>
	<div class="container-fluid main-container">
		<div class="absolute-wrapper"></div>
		<nav class="navbar navbar-default" role="navigation">
		<ul class="nav navbar-nav">
			<li class="active"><a href="managerPage.jsp"><span class="glyphicon glyphicon-user"></span> Back</a></li>
			<li><a href="m_reimbursements.jsp"><span class="glyphicon glyphicon-piggy-bank"></span> View Reimbursement</a></li>
			<li><a href="m_employees.jsp"><span class="glyphicon glyphicon-folder-open"></span> View Employee</a></li>
			<li><a href="m_new_employee.jsp"><span class="glyphicon glyphicon-plus"></span> Register Employee</a></li>
			<li><a href="#"><span class="glyphicon glyphicon-pencil"></span> Tools</a></li>
			<li><a href="/Project1/logout"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
		</ul>
		</nav>
	</div>
	
	<div class="col-md-12 content">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h2><span class="label label-default"> ${curUser.getFirstname()} ${curUser.getLastname()} </span></h2>
				<br> 
				<h3><span class="label label-success"> CREATE NEW EMPLOYEE </span></h3>
			</div>

<!--****************************************************************Employees editable table****************************************************************-->
			<div class="panel-body">
				<form class="form-horizontal" role="form" method="POST"
					action="newu">
					<table class="table table-hover">
						<tr>
							<th>Username</th>
							<th>First Name</th>
							<th>Last Name</th>
							<th>Email</th>
							<th>Role</th>
						</tr>

						<tr>
							<td><input type="text" class="form-control" name="username"
								id="inputUsername" placeholder="username" required></td>
							<td><input type="text" class="form-control" id="inputFname"
								name="fname" placeholder="first name" required></td>
							<td><input type="text" class="form-control" id="inputLname"
								name="lname" placeholder="last name" required></td>
							<td><input type="text" class="form-control" id="inputEmail"
								name="email" placeholder="example@yahoo.com" required></td>
							<td><select class="form-control" name=role>
									<option value="1">Employee</option>
									<option value="2">Manager</option>
								</select>
							</td>
						</tr>
						
					</table>
					<div align="right">
									<button type="submit" class="btn btn-success"
										id="upload-button">Submit</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>


<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="js/bootstrap.min.js"></script>

</body>
</html>