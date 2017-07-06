<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.sql.*"%>
<!DOCTYPE html>  
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
	h1, form {
		text-align: center;
	}
	.paraClass{
		font-style: italic;
		font-weight: bold;
	}
</style>
 <script src="jquery-1.11.3.js"></script>
</head>
<body id="myBody" onload="">
	<h1>Delete Patient</h1>
	<form action="DeletePatients" method="post" id="frmDeletePatients"> 
		<div>
			<label>Patient Id<sup>*</sup>:</label>
			&nbsp;
			<select id = "selPid" name = "selPid" required="required" multiple="multiple"> 
					<option value="0">&nbsp;</option>
					<%
					try
					{
						if(request.getAttribute("_pids") != "" && 
										request.getAttribute("_pids") != null )
						{ 
							String data =request.getAttribute("_pids").toString();
							out.println(data);	
						}
					}catch(Exception e)
					{
						//out.println("");
					}
				%>
			</select><br>
		</div>
		<div>
			<a><input type="submit" name="btnDelete" id="btnDelete" 
			value="Delete Patient"></a>
			<a href="UserChoice.jsp">
				<input type="button" name="btnBack" id="btnBack" value="Back">
			</a>
		</div>		
	</form>
	<p align="center" id="paraMsg" class="paraClass">${deleteStatus}</p>
	<div id="myResponse">
	</div>
</body>
</html>
