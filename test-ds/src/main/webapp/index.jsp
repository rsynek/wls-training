<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Datasource Test</title>
</head>
<body>
	<h1>Test a DataSource</h1>
	<form method="post" action="${pageContext.request.contextPath}/test">
		<table>
			<tr>
				<td>JNDI Name of DataSource:</td>
				<td><input type="text" width="60" name="jndiName"/></td>
			</tr>
			<tr>
				<td>Table Name to Query (optional):</td>
				<td><input type="text" width="60" name="tableName"/></td>
			</tr>
			<tr>
				<td colspan="2" align="center"><input type="submit" value="Submit" name="submit"/></td>
			</tr>
		</table>
	</form>
</body>
</html>

