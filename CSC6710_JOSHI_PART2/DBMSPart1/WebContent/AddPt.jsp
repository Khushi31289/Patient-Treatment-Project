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
	<h1>Add New Patient</h1>
	<form action="AddPatients" method="post" id="frmAdd"> 
		<div>
			<label>Patient Id<sup>*</sup>:</label>
			&nbsp;<input type="number" name="txtpid" id="txtpid" required="required" 
					min="1" max="999" readonly="readonly" value="${newPid}"><br>
		</div>
		<div>
			<label>First Name:<sup>*</sup></label>
			&nbsp;<input type="text" name="txtfname" id="txtfname" required="required" 
			maxlength="255"><br>
		</div>
		<div>
			<label>Last Name:<sup>*</sup></label>&nbsp;
			<input type="text" name="txtlname" id="txtlname" maxlength="255"><br>
		</div>
		<div>
			<label>Gender:<sup>*</sup></label>&nbsp;
			<input type="radio" name="radioGender" id="radioGender" value="Male">
			Male&nbsp;
			<input type="radio" name="radioGender" id="radioGender" checked="checked" value="Female">
			Female<br>
		</div>
		<div>
			<label>Age:<sup>*</sup></label>&nbsp;
			<input type="number" id="txtage" name="txtage" min="1" max="150" required="required" ><br>
		</div>
		<div>
			<label>Address:<sup>*</sup></label>&nbsp;
			<input type="text" id="txtaddress" name="txtaddress" maxlength="255" required="required" ><br>
		</div>
		
		<div>
			<label>Email:<sup>*</sup></label>&nbsp;
			<input type="email" name="txtemail" id="txtemail" maxlength="255" required="required" ><br>
		</div>
		<div>
			<input type="submit" name="btnAdd" id="btnAdd" 
			onclick="checkForError" value=" Add ">
				&nbsp;&nbsp;		
			<a href="UserChoice.jsp">
				<input type="button" name="btnBack" id="btnBack" value="Back">
			</a>
		</div>		
	</form>
	<p align="center"><b><i>${insertStatus}</i></b></p>
</body>
</html>
