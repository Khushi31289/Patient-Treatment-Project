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
	.paraClass{
		font-style: italic;
		font-weight: bold;
	}
</style>
 <script src="jquery-1.11.3.js"></script>
 <script type="text/javascript">
 //$('#selPid').change(function() {
	 function GetPidsByAjax(){
	     var valPid = $('#selPid').val();
	     $.get('GetPatientIdsByAjax', {
	             Pid : valPid
	     }, function(responseText) {
	    	 var tempResponse = responseText;
	    	 if(tempResponse.length > 0 && tempResponse != null 
	    			 && tempResponse != ""){
		    	   var res = tempResponse.split("<?>");
		    	   $("#txtfname").val(res[0]);
		    	   $("#txtlname").val(res[1]);
		    	   $("#txtage").val(parseInt(res[2]));
		    	   $("#txtaddress").val(res[3]);
		    	   $("#txtemail").val(res[4]);
		    	   $(":radio").removeAttr('checked');
		    	   if(res[5] == "Female"){
		    	   		document.getElementsByName("radioFemale")[0].checked = true;
		    	   }else if(res[5] == "Male"){
		    		   document.getElementsByName("radioMale")[0].checked = true;
		    	   }
		    	   $('#paraMsg').html("All above are mandatory fields, Enter data in all fields to update a patient's information.");
	    	 }
	     });
	 }
	//});
 </script>
</head>
<body id="myBody" onload="">
	<h1>Update Patient</h1>
	<form action="UpdatePatients" method="post" id="frmUpdatePatients"> 
		<div>
			<label>Patient Id<sup>*</sup>:</label>
			&nbsp;
			<select id = "selPid" name = "selPid" required="required" onchange="GetPidsByAjax();"> 
					<option value="0">&nbsp;</option>
					<%
					try
					{
						if(request.getAttribute("_pids") != "" && request.getAttribute("_pids") != null )
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
			<!--  <input type="number" name="txtpid" id="txtpid" required="required" 
					min="1" max="999" readonly="readonly" value="${newPid}"><br>
					 -->
		</div>
		<div>
			<label>First Name:<sup>*</sup></label>
			&nbsp;<input type="text" name="txtfname" id="txtfname" required="required" 
			maxlength="255" value="${fname}"><br> <!-- ${fname} -->
		</div>
		<div>
			<label>Last Name:<sup>*</sup></label>&nbsp;
			<input type="text" name="txtlname" id="txtlname" maxlength="255" value="${lname}"><br> <!-- ${lname} -->
		</div>
		<div>
			<label>Gender:<sup>*</sup></label>&nbsp;
			 <%
				String gender =null;
			 	gender = request.getAttribute("valGender").toString();
				if(gender != null && gender != "" && gender.equals("Female")){
			%>
				<input type="radio" name="radioMale" id="radioGender" value="Male">
				Male&nbsp;
				<input type="radio" name="radioFemale" id="radioGender" checked="checked" value="Female">
				Female<br>  
			<%		
			}else if(gender != null && gender != "" && gender.equals("Male")){
			%>
				<input type="radio" name="radioMale" id="radioGender" value="Male" checked="checked" >
				Male&nbsp;
				<input type="radio" name="radioFemale" id="radioGender" value="Female">
				Female<br>
			<%
			}else{
			%>
				<input type="radio" name="radioMale" id="radioGender" value="Male">
				Male&nbsp;
				<input type="radio" name="radioFemale" id="radioGender" value="Female">
				Female<br>
			<%
			}
			%>
		</div>
		<div>
			<label>Age:<sup>*</sup></label>&nbsp;
			<input type="number" id="txtage" name="txtage" min="1" max="150" required="required" 
			value = "${valAge}"><br> <!-- ${valAge} -->
		</div>
		<div>
			<label>Address:<sup>*</sup></label>&nbsp;
			<input type="text" id="txtaddress" name="txtaddress" maxlength="255" required="required" 
			value="${valAddress}"><br> <!-- ${valAddress} -->
		</div>
		
		<div>
			<label>Email:<sup>*</sup></label>&nbsp;
			<input type="email" name="txtemail" id="txtemail" maxlength="255" required="required" 
				value="${valEmail}"><br>  <!-- ${valEmail} -->
		</div>
		<div>
			<a><input type="submit" name="btnAdd" id="btnAdd" 
			onclick="checkForError" value="Update Patient"></a>
			<a href="UserChoice.jsp">
				<input type="button" name="btnBack" id="btnBack" value="Back">
			</a>
		</div>		
	</form>
	<p align="center" id="paraMsg" class="paraClass">${updateStatus}</p>
	<div id="myResponse">
	</div>
</body>
</html>
