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

<title>Manager - New Employee</title>

<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/background.css" rel="stylesheet" type="text/css">

</head>

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
			<li><a href="/Project11/logout"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
		</ul>
		</nav>
	</div>
	
	<div class="col-md-12 content">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h2><span class="label label-default"> ${curUser.getFirstname()} ${curUser.getLastname()} </span></h2>
				<br> 
				<h1><span class="label label-success"> CREATE NEW EMPLOYEE </span></h1>
			</div>

<!--****************************************************************Employees editable table****************************************************************-->
			<div class="panel-body">
				
					<div class="alert alert-success hide" id="created">
						New employee created successfully
					</div>
					<div class="alert alert-danger hide" id="dupname">
						Username already existed! Please choose another one
					</div>
					<div class="alert alert-danger hide" id="dupe">
						Email address already existed! Please choose another one
					</div>
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
							<td><select class="form-control" name="role" id="roleId">
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
				
			</div>
		</div>
	</div>
</body>

<script type="text/javascript">
function newUser() {
		var user = $('#inputUsername').val();
		var fname = $('#inputFname').val();
		var lname = $('#inputLname').val();
		var email = $('#inputEmail').val();
		var role = $('#roleId').val();
		$.ajax({
			type : "POST",
			url : "newu",
			data : {
				"username" : user,
				"fname" : fname,
				"lname" : lname,
				"email" : email,
				"role" : role
			},
			success : function(result) {
				if (result == 'dupUname') {
					document.getElementById("dupname").classList.remove("hide");
					setTimeout(function (){document.getElementById("dupname").classList.add("hide")},2000);
				} else if (result == 'dupEmail') {
					document.getElementById("dupe").classList.remove("hide");
					setTimeout(function (){document.getElementById("dupe").classList.add("hide")},2000);
				} else {
					document.getElementById("created").classList.remove("hide");
					setTimeout(function (){document.getElementById("created").classList.add("hide")},2000);
				}
			}
		});
	}
document.getElementById("upload-button").addEventListener("click",
		newUser, false);
</script>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>

</body>
</html>