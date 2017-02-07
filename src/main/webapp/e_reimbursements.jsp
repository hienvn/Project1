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

<meta name="viewport"
	content="width=device-width, shrink-to-fit=no, initial-scale=1">

<!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
<title>Employee Current Reimbursement</title>

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
			<li><a href="/Project1/logout"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
		</ul>
		</nav>
	</div>
	
	<div class="col-md-12 content">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h2><span class="label label-default"> ${curUser.getFirstname()} ${curUser.getLastname()} </span></h2>
				<br> 
				<h3><span class="label label-success"> REIMBURSEMENTS HISTORY </span></h3>
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
								<td>
								<a href="#" id="pop" onclick="openImage(${re.r_id })">
    								<img id=${re.r_id } src="data:image/jpeg;base64,${re.getR_receipt_string()}" style="display:none">
    								<span class="glyphicon glyphicon-picture"></span> View
    							</a>
								<!-- <a href="data:image/jpeg;base64,${re.getR_receipt_string()}"><span class="glyphicon glyphicon-picture"></span> View</a> -->
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	
	
	
	
	
<!-- Creates the bootstrap modal where the image will appear -->
<div class="modal fade" id="imagemodal" >
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"> Close </button>
        <h4 class="modal-title" id="myModalLabel">View Receipt</h4>
      </div>
      <div class="modal-body" style="margin:auto; text-align: center;">
        <img src="" id="imagepreview" style="width: 400px; height: 500px;" >
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
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

<script>
function openImage(number) {
	document.getElementById("imagepreview").src=document.getElementById(number).src;
    $('#imagemodal').modal('show');
}
</script>

<script src="js/bootstrap.min.js"></script>
    
</html>