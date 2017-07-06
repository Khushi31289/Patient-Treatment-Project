<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.sql.*"%>
<!DOCTYPE html> <!-- PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> --> 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"> 
<title>Add a new symptom for a Patient</title>
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
</style>
<script type="text/javascript">
/* var err = getQueryVariable("err");
 function checkForError(){
	alert(err);
	if(err != null && err != ""){
		var node = document.createElement("p");
		var textnode = document.createTextNode(err);
		node.appendChild(textnode); 
		document.getElementById("myBody").appendChild(node);
	} 
 }*/
</script>
</head>
<body id="myBody" onload="">
	<h1>Add New Symptoms for a Patient</h1>
	<form action="AddSymptoms" method="post" id="frmAdd"> 
		<div>
			<label>Patient Id<sup>*</sup>:</label>
			&nbsp;
			<select id = "selPid" name = "selPid" required="required" > 
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
			<label>Name:<sup>*</sup></label>
			&nbsp;<input type="text" name="txtName" id="txtName" required="required" 
			maxlength="255" value="${name}"><br>
		</div>
		<div>
			<label>Status<sup>*</sup></label>&nbsp;
			<select id = "selStatus" name = "selStatus" required="required" > 
					<option value="Appear" selected="selected">Appear</option>
					<option value="Disappear">Disappear</option>
			</select>
		</div>
		<div>
			<input type="submit" name="btnAdd" id="btnAdd" 
			 value=" Add ">
				&nbsp;&nbsp;		
			<a href="UserChoice.jsp">
				<input type="button" name="btnBack" id="btnBack" value="Back">
			</a>
		</div>		
	</form>
	<p align="center"><b><i>${addStatus}</i></b></p>
</body>
</html>
