<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
		try
		{
			if(request.getAttribute("var3") != "")
			{ 
		%>
			<br>
			<p align="center">
			<table border="1">
			<tr>
			<th>Patient Id</th>
			<th>First Name</th>
			<th>Last Name</th>
			<th>Address</th>
			<th>Age</th>
			<th>Email</th>
			<th>Gender</th>
			<th>Timestamp</th>
			<th>Status(1=patient is present)</th>
			</tr>  
		
		<% 			String data =request.getAttribute("var3").toString();
					out.println(data);	
		%>
			</table>  
			</p>
		<% 
			}
		}catch(Exception e)
		{
			out.println("No Records found for this search Criteria");
		}
		%>
</body>
</html>