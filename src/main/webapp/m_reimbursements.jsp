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

<title>Manager View Reimbursements</title>

<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdn.datatables.net/1.10.13/css/jquery.dataTables.min.css" rel="stylesheet">

<script src="//code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://cdn.datatables.net/1.10.13/js/jquery.dataTables.min.js"></script>
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
				<h3><span class="label label-success"> ALL REIMBURSEMENTS </span></h3>
			</div>
			
<!--****************************************************************Editable Table****************************************************************-->				
			<div class="panel-body">
			<table id="reimbursementsTable" class="display" cellspacing="0" width="100%">
					<thead>
						<tr>
							<th>ID</th>
							<th>Amount</th>
							<th>Description</th>
							<th>Date Submitted</th>
							<th>Date Resolved</th>
							<th>ID_Author</th>
							<th>ID_Resolver</th>
							<th>Type</th>
							<th>Status</th>
							<th>Receipt</th>
							<th>Decision</th>
						</tr>
					</thead>
        			<tfoot>
        				<tr>
							<th>ID</th>
							<th>Amount</th>
							<th>Description</th>
							<th>Date Submitted</th>
							<th>Date Resolved</th>
							<th>ID_Author</th>
							<th>ID_Resolver</th>
							<th>Type</th>
							<th>Status</th>
							<th>Receipt</th>
							<th>Decision</th>
						</tr>
					</tfoot>
					<tbody>
						<c:forEach items="${curRe}" var="re">
							<tr>
								<td>${re.r_id}</td>
								<td>${re.r_amount}</td>
								<td>${re.r_description}</td>
								<td>${re.r_submitted}</td>
								<td>${re.r_resolved}</td>
								<td>${re.uid_author}</td>
								<td>${re.uid_resolver}</td>
								<td>${re.r_type}</td>
								<td>${re.r_status}</td>
								<td><a
									href="data:image/jpeg;base64,${re.getR_receipt_string()}" target="_blank"><span
										class="glyphicon glyphicon-picture"></span> View</a></td>
								<td>
									<div class="form-group">
										<form id=${re.r_id } method="POST" action="update_re">		
  											<select class="form-control" name=${re.r_id } onChange="update(${re.r_id})">
												<option value="Default">${re.r_status}</option>
												<option value="Approve">Approve</option>
												<option value="Deny">Deny</option>
											</select>
										</form>
									</div>
								</td>
							</tr>
						</c:forEach>
					</tbody>
					</table>
					<div class="col-sm-offset-11">
						<br>
					</div>
					<div class="col-sm-offset-11">
						<button type="submit" class="btn btn-success" id="upload-button">Submit</button>
					</div>
			</div>
		</div>
	</div>
</body>


<script>
$(document).ready(function() {
    var table = $('#reimbursementsTable').DataTable();
} );

</script>


<script type="text/javascript">

function update(id)
{	
	    var form = document.getElementById(id);
	    form.submit();   
}
</script>

</html>