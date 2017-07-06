<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.sql.*"%>
<!DOCTYPE html> <!-- PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> --> 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"> 
<title>Searching a Patient</title>
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
<script type="text/javascript">
 var err = getQueryVariable("err");
 function checkForError(){
	///alert(err);
	/*if(err != null && err != ""){
		var node = document.createElement("p");
		var textnode = document.createTextNode(err);
		node.appendChild(textnode); 
		document.getElementById("myBody").appendChild(node);
	} */
 }
</script>
</head>
<body id="myBody">
	<h1>Search the Patients by</h1>
	<form action="SearchPatients" method="post" id="frmSearch"> 
		<div>
			<label>Patient Id:</label>
			&nbsp;<input type="number" name="txtpid" id="txtpid" 
					min="1" max="999"><br>
		</div>
		<div>
			<label>First Name:</label>
			&nbsp;<input type="text" name="txtfname" id="txtfname"
			maxlength="255"><br>
		</div>
		<div>
			<label>Last Name:</label>&nbsp;
			<input type="text" name="txtlname" id="txtlname" maxlength="255"><br>
		</div>
		<div>
			<label>Gender:</label>&nbsp;
			<input type="radio" name="radioGender" id="radioGender" value="Male" >
			Male&nbsp;
			<input type="radio" name="radioGender" id="radioGender" value="Female">
			Female<br>
		</div>
		<div>
			<label>Age:</label>&nbsp;
			<input type="number" id="txtage" name="txtage" min="1" max="150"><br>
		</div>
		<div>
			<label>Address:</label>&nbsp;
			<input type="text" id="txtaddress" name="txtaddress" maxlength="255"><br>
		</div>
		
		<div>
			<label>Email:</label>&nbsp;
			<input type="email" name="txtemail" id="txtemail" maxlength="255"><br>
		</div>
		<div>
			<input type="submit" name="btnsearch" id="btnsearch" 
			onclick="checkForError" value="Search Patient">
		&nbsp;&nbsp;		
		<a href="UserChoice.jsp"><input type="button" name="btnBack" id="btnBack" value="Back"></a></div>
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
