<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Register</title>
</head>
<body>
	<h3 style="color: red;">Register</h3>

	<div id="registerUser">

    <form method="POST" action="${contextPath}/register" class="form-signin">
        <div class="form-group ${error != null ? 'has-error' : ''}">
            <span>${msg}</span>
            <input name="username" type="text" class="form-control" placeholder="Username"
                   autofocus="true"/>
            <input name="password" type="password" class="form-control" placeholder="Password"/>
            <input name="role" type="role" class="form-control" placeholder="seller or user"/>
            <span>${errorMsg}</span>
			 <span>Already registered? <a href="/login" >Login
            <button class="btn btn-lg btn-primary btn-block" type="submit">Register</button>
        </div>
		</form>
	</div>
</body>
</html>
