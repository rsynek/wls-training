<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JMS Test App</title>
</head>
<body>
	<h1>JMS Test Application</h1>
	<p>This application tests for the successful deployment of a JMS Connection Factory and JMS Destination (queue or topic). This
	application can optionally send test messages to the destination.</p>
	<p>Enter the JNDI names of the Connection Factory and Destination below:</p>
	<form action="${pageContext.request.contextPath}/test" method="post">
		<table>
			<tr>
				<td>Connection Factory JNDI name: </td>
				<td><input type="text" width="60" name="factoryName" /></td>
			</tr>
			<tr>
				<td>Destination JNDI name: </td>
				<td><input type="text" width="60" name="destName" /></td>
			</tr>
			<tr>
				<td>Number of test messages to send: </td>
				<td><input type="text" width="60" name="numOfTests" value="5" /></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="Submit"/></td>
			</tr>
		</table>
	</form>
</body>
</html>