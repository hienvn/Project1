<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html lang="en">
<head>
<meta charset="utf-8">

<title>Login Page</title>

<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/login.css" rel="stylesheet">

</head>
<style>
body{
	position: absolute;
	background:
		url(http://missionsprings.net/wp-content/uploads/2016/06/Huge-Backgrounds-26.jpg);
	background-size: cover;
}

.panel-default {
	margin-top: 60px;
}

.form-group.last {
	margin-bottom: 0px;
}
</style>

<body>
	<div class="container">
		<div class="row" id="login">
			<div class="col-md-5 col-md-offset-10">
				<div class="panel panel-default">
					<div class="panel-heading">
						<span class="glyphicon glyphicon-lock"></span> Login
					</div>
					<div class="panel-body">
						<div class="form-horizontal" role="form">
							<div class="alert alert-danger hide" id="error">
								<b>Error </b>User or password is incorrect.
							</div>
							<div class="form-group">
								<label for="loginUsername" class="col-sm-3 control-label">Username</label>
								<div class="col-sm-9">
									<input type="text" class="form-control" name="username"
										id="loginUsername" placeholder="username" required>
								</div>
							</div>
							<div class="form-group">
								<label for="loginPass" class="col-sm-3 control-label">Password</label>
								<div class="col-sm-9">
									<input type="password" class="form-control" name="password"
										id="loginPass" placeholder="password" required>
								</div>
							</div>
							<div class="form-group last">
								<div class="col-sm-offset-3 col-sm-9">
									<button type="button" class="btn btn-success btn-md" id="loginButton">
										Sign in</button>
								</div>
							</div>
						</div>
					</div>
					<div class="panel-footer">
						Forgot your password? <a onClick="forgotPass()">Reset it here</a>
					</div>
				</div>
			</div>
		</div>

		<div class="row hide" id="reset">
			<div class="col-md-5 col-md-offset-10">
				<div class="panel panel-default">
					<div class="panel-heading">
						<span class="glyphicon glyphicon-plus"></span> Reset Password
					</div>
					<div class="panel-body">
						<div class="form-horizontal" role="form">
							<div class="alert alert-success" id="newpass">
								A new password will be send to your email address
							</div>
							<div class="alert alert-danger hide" id="wronginfo">
								User does not exist
							</div>
							<div class="form-group">
								<label for="curUsername" class="col-sm-3 control-label">
									Username</label>
								<div class="col-sm-9">
									<input type="text" class="form-control" name="curUname"
										id="uname" placeholder="username" required>
								</div>
							</div>
							<div class="form-group">
								<label for="curEmailAddress" class="col-sm-3 control-label">
									Email Address</label>
								<div class="col-sm-9">
									<input type="text" class="form-control" name="curEmail"
										id="emailaddr" placeholder="example@yahoo.com" required>
								</div>
							</div>
							<div class="form-group last">
								<div class="col-sm-offset-3 col-sm-9">
									<button type="button" class="btn btn-success btn-md" id="resetButton">
										Reset</button>
								</div>
							</div>
						</div>
					</div>
					
					<div class="panel-footer">
						Remember your password now? <a onCLick="login()">Sign in</a>
					</div>
				</div>
			</div>
		</div>
	</div>



<script type="text/javascript">
function forgotPass(){
	document.getElementById("reset").classList.remove("hide");
	document.getElementById("login").classList.add("hide");
}

function login(){
	document.getElementById("login").classList.remove("hide");
	document.getElementById("reset").classList.add("hide");
}



	function loginCheck() {
		var user = $('#loginUsername').val();
		var pwd = $('#loginPass').val();
		$.ajax({
			type : "POST",
			url : "master",
			data : {
				"username" : user,
				"password" : pwd
			},
			success : function(result) {
				if (result == 'failed') {
					document.getElementById("error").classList.remove("hide");
					setTimeout(function (){document.getElementById("error").classList.add("hide")},2000);
				} else {
					window.location.href = result;
				}
			}
		});
	}
	function resetPass() {
		var user = $('#uname').val();
		var email = $('#emailaddr').val();
		$.ajax({
				type : "POST",
				url : "resetpass",
				data : {
					"curUname" : user,
					"curEmail" : email
				},
				success : function(result) {
					if (result == 'done') {
						document.getElementById("newpass").classList.remove("hide");
						setTimeout(function (){document.getElementById("newpass").classList.add("hide")},2000);
					}
					else{
						document.getElementById("wronginfo").classList.remove("hide");
						
						setTimeout(function (){document.getElementById("wronginfo").classList.add("hide")},2000);
					}
					
				}
			});
	}
	document.getElementById("loginButton").addEventListener("click",
			loginCheck, false);
	document.getElementById("resetButton").addEventListener("click", resetPass,
			false);
</script>

	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="js/bootstrap.min.js"></script>
</body>
</html>