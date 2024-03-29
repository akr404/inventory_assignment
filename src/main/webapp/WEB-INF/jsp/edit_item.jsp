<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>       
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
	"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Edit Item</title>
</head>
<body>
	<div align="center">
		<h2>Edit Item</h2>
		<form:form action="saveItem" method="post" modelAttribute="item">
			<table border="0" cellpadding="5">
				<tr>
					<td>ID: </td>
					<td>${item.id}
						<form:hidden path="id"/>
					</td>
				</tr>	
				<tr>
					<td>Name: </td>
					<td><form:input path="name" /></td>
				</tr>
				<tr>
					<td>Category: </td>
					<td><form:input path="category" /></td>
				</tr>
				<tr>
					<td>Description: </td>
					<td><form:input path="description" /></td>
				</tr>	
				<tr>
					<td colspan="2"><input type="submit" value="Save"></td>
				</tr>						
			</table>
		</form:form>
	</div>
</body>
</html>