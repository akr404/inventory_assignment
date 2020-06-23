<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>List of Items</title>
</head>
<body>
<div align="right">
<a href="<c:url value="/logout" />">Logout</a>
</div>
<div align="center">
	<h2>Welcome</h2>
	<span>${name}</span>

	<h3><a href="new_item">New Item</a></h3>
	<table border="1" cellpadding="5">
		<tr>
			<th>ID</th>
			<th>Name</th>
			<th>Description</th>
			<th>Category</th>
			<th>Action</th>
		</tr>
		<c:forEach items="${listItems}" var="item">
		<tr>
			<td>${item.id}</td>
			<td>${item.name}</td>
			<td>${item.description}</td>
			<td>${item.category}</td>
			<td>
				<a href="editItem?id=${item.id}">Edit</a>
				&nbsp;&nbsp;&nbsp;
				<a href="deleteItem?id=${item.id}">Delete</a>
			</td>
		</tr>
		</c:forEach>
	</table>
</div>	
</body>
</html>
<!-- /container -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script></body>
</html>
