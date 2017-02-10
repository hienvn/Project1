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

<title>Employee - Home Page</title>

<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/mainPage.css" rel="stylesheet" type="text/css">

</head>

<!--****************************************************************Body Pages ****************************************************************-->
<body>
	<div class="container-fluid main-container">
		<div class="absolute-wrapper"></div>
		<nav class="navbar navbar-default" role="navigation">
		<ul class="nav navbar-nav">
			<li class="active"><a href="#" data-toggle="modal" data-target="#profileModal"><span class="glyphicon glyphicon-user"></span> Profile</a></li>
			<li><a href="e_reimbursements.jsp"><span class="glyphicon glyphicon-piggy-bank"></span> Current Reimbursement</a></li>
			<li><a href="e_new_reimbursement.jsp"><span class="glyphicon glyphicon-usd"></span> New Reimbursement</a></li>
			<li><a href="#" onCLick="game()"><span class="glyphicon glyphicon-pencil"></span> Tools</span></a>
			<li><a href="/Project11/logout"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
		</ul>
		</nav>
	</div>
	<div class="col-md-12 content">
	<h1><span class="label label-default"><i><font color="#D8D8D8">Welcome, employee </font></i> ${curUser.getFirstname()}</span></h1>
		
			<div class="col-md-8 pull-left hide" id="playGame">
				<center><object type="application/x-shockwave-flash" data="http://cdn.ebaumsworld.com/flash/kcbigmack/pc.swf" width="600" height="450" id="flashObject" style="visibility: visible;"><param name="wmode" value="opaque"><param name="menu" value="false"><param name="swliveconnect" value="true"><param name="allowscriptaccess" value="always"></object></center>
			</div>
		
	</div>

<!--****************************************************************Profile Modal****************************************************************-->
	<div class="modal fade" id="profileModal" role="dialog">
		<div class="modal-dialog">
			<!-- Modal content-->
			<div class="modal-header">
				<span class="glyphicon glyphicon-list-alt"></span> Profile
			</div>
			<div class="modal-body">
				<div class="text-center">
					<img class="img-circle avatar avatar-original"
						style="width: 120px; height: 120px; margin: auto; -webkit-user-select: none; display: block;"
						src="https://lh6.googleusercontent.com/-O9QNVhPOCyk/AAAAAAAAAAI/AAAAAAAABUI/w4ADGr8iXyA/photo.jpg">
				</div>
				<div class="text-center">
					<h1>${curUser.firstname} ${curUser.lastname}</h1><br>
					<h2>ID: ${curUser.user_id}</h2> 
					<h2>First Name: ${curUser.firstname}</h2>
					<h2>Last Name: ${curUser.lastname}</h2>
					<h2>Username: ${curUser.username}</h2>
					<h2>Password: ${curUser.password}</h2>
					<h2>Role: ${curUser.role}</h2> 
					<h2>Email: ${curUser.email}</h2><br>
				</div>
			</div>
			<div class="modal-footer">
				<button class="btn btn-warning btn-lg pull-right" data-toggle="modal" data-target="#editModal"><i class="glyphicon glyphicon-pencil"></i> Edit
				</button>
			</div>
		</div>
	</div>

<!--****************************************************************Edit Profile Modal****************************************************************-->
	<div class="modal fade" id="editModal" role="dialog">
		<div class="modal-dialog">
			<form method="POST" action="updateuser">
				<!-- Modal content-->
				<div class="modal-header">
					<span class="glyphicon glyphicon-list-alt"></span> Profile
				</div>
				<div class="modal-body">
					<div class="text-center">
						<img class="img-circle avatar avatar-original"
							style="width: 120px; height: 120px; margin: auto; -webkit-user-select: none; display: block;"
							src="https://lh6.googleusercontent.com/-O9QNVhPOCyk/AAAAAAAAAAI/AAAAAAAABUI/w4ADGr8iXyA/photo.jpg">
					</div>

					<div class="text-center">
					<h1>${curUser.getFirstname()} ${curUser.getLastname()}</h1><br>
					<h2><label>First Name: </label><input type="text" class="mytext" id="fname"
								name="inputFname" placeholder=${curUser.firstname} required></h2>
					<h2><label>Last Name: </label><input type="text" class="mytext" id="lname"
								name="inputLname" placeholder=${curUser.lastname} required></h2>
					<h2><label>Username: </label><input type="text" readonly="readonly" class="mytext" id="uname"
								name="inputUname" placeholder=${curUser.username} required style="background-color:#663300;"></h2>
					<h2><label>Password: </label><input type="text" class="mytext" id="pass"
								name="inputPass" placeholder=${curUser.password} required></h2>
					<h2><label>Role: </label><input type="text" readonly="readonly" class="mytext" id="role"
								name="inputRole" placeholder=${curUser.role} required style="background-color:#663300;"></h2> 
					<h2><label>Email: </label><input type="text" readonly="readonly" class="mytext" id="email"
								name="inputEmail" placeholder=${curUser.email} required  style="background-color:#663300;"></h2><br>
					</div>
				</div>
				<div class="modal-footer">
					<button type="submit" class="btn btn-warning btn-lg pull-right">
						<i class="glyphicon glyphicon-pencil"></i> Submit
					</button>
				</div>
			</form>
		</div>
	</div>
</body>

<script type="text/javascript">
function game(){
	document.getElementById("playGame").classList.remove("hide");
	//document.getElementById("playGame").classList.add("hide");
}
</script>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
</body>
</html>