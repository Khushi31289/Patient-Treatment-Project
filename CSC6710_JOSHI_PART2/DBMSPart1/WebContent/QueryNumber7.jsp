<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Query No 7</title>
<style type="text/css">
	div{
		padding-top: 15px;
	}
	sup{
		color: red;
	}
	h1, form,p {
		text-align: center;
	}
</style>
</head>
<body>
	<h1>Search the Patients for Query 7</h1>
	<form action="QueryNo7" method="post" id="frmSearch"> 
		<div>
			<label>Patient Id:</label>
			&nbsp;<input type="number" name="txtpid" id="txtpid" 
					min="1" max="999"><br>
		</div>
		&nbsp;&nbsp;	
		<div>
			<input type="submit" name="btnsearch" id="btnsearch" 
				onclick="checkForError" value="Search Patient">
			&nbsp;&nbsp;			
			<a href="UserChoice.jsp">
				<input type="button" name="btnBack" id="btnBack" value="Back">
			</a>
		</div>
	</form>
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
			<th>StatusStatus(1=patient is present)</th>
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