<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<body>

<div>
	<h3 style="color: red;">Login</h3>
    <form method="POST" action="${contextPath}/login" class="form-signin">
        <div class="form-group ${error != null ? 'has-error' : ''}">
            <span>${msg}</span>
            <input name="username" type="text" class="form-control" placeholder="Username"
                   autofocus="true"/></br>
            <input name="password" type="password" class="form-control" placeholder="Password"/></br>
            <span>${errorMsg}</span></br>
			 <span>Not registered? <a href="/register" >Register</br>
            <button class="btn btn-lg btn-primary btn-block" type="submit">Log In</button>
        </div>

    </form>

</div>
<!-- /container -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script></body>
</html>
