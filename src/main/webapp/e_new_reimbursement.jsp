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

<title>Employee - New Reimbursement</title>

<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/background.css" rel="stylesheet" type="text/css">

</head>

<!--****************************************************************Body Pages ****************************************************************-->
<body>
	<div class="container-fluid main-container">
		<div class="absolute-wrapper"></div>
		<nav class="navbar navbar-default" role="navigation">
		<ul class="nav navbar-nav">
			<li class="active"><a href="employeePage.jsp"><span class="glyphicon glyphicon-user"></span> Back</a></li>
			<li><a href="e_reimbursements.jsp"><span class="glyphicon glyphicon-piggy-bank"></span> Current Reimbursement</a></li>
			<li><a href="e_new_reimbursement.jsp"><span class="glyphicon glyphicon-usd"></span> New Reimbursement</a></li>
			<li><a href="#"><span class="glyphicon glyphicon-pencil"></span> Tools</span></a>
			<li><a href="/Project11/logout"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
		</ul>
		</nav>
	</div>
	
	<div class="col-md-12 content">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h2><span class="label label-default"> ${curUser.getFirstname()} ${curUser.getLastname()} </span></h2>
				<br> 
				<h1><span class="label label-success"> NEW REIMBURSEMENT </span></h1>
			</div>


			<div class="panel-body">
				<form class="form-horizontal" role="form" method="POST"
					action="newre" enctype="multipart/form-data">
					<table class="table table-hover">
						<tr>
							<th>Amount</th>
							<th>Description</th>
							<th>Type</th>
							<th>Receipt</th>
						</tr>
						<tr>
							<td><input type="number" class="form-control" name="amount"
								id="inputAmount" placeholder="$0.00" required></td>
							<td><input type="text" class="form-control" id="inputDesc"
								name="desc" placeholder="reason" required></td>
							<td>
								<select class="form-control" name="type">
												<option value="1">Travel</option>
												<option value="2">Housing</option>
												<option value="3">Food</option>
												<option value="4">Utilities</option>
												<option value="5">Other</option>
								</select>
							</td>
							<td>
								<div class="input-group">
									<label class="input-group-btn"> <span 
										class="btn btn-primary"> Browse<input type="file"
											accept="image/jpg,image/gif" style="display: none;"
											id="file-select" name="receipt">
									</span>
									</label> <input type="text" class="form-control" id="filePath" size="20" onmouseover="uploadImage()" readonly>
								</div>
							</td>
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

<script>
function uploadImage() {
	document.getElementById("filePath").value = document.getElementById("file-select").value;
}
</script>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="js/bootstrap.min.js"></script>

</body>
</html>