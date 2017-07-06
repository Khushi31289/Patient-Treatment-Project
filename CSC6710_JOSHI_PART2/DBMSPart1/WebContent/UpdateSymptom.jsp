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
 <script src="jquery-1.11.3.js"></script>
<script type="text/javascript">
function GetSymptomsByAjax(){
    var valPid = $('#selPid').val();
   // alert(valpid);
    if(valPid > 0){
	    $.get('GetSymptomByAjax', {
	            Pid : valPid
	    }, function(responseText) {
	   	 var tempResponse = responseText;
	   	 if(tempResponse.length > 0 && tempResponse != null 
	   			 && tempResponse != ""){
		    	   var res = tempResponse.split("<?>");
		    	   console.log(res[0] + " , "+res[1] + " , "+res[2]);
		    	   $("#txtPid").val(res[0]);
		    	   $("#txtName").val(res[1]);
		    	   $("#selStatus").val([]);
		    	   $("#selStatus").val(res[2]);
		    	   $("#txtTime").val(res[3]);
	   	 	}
	    });
    }else{
    	$("#txtPid").val("");
 	   $("#txtName").val("");
 	  $("#selStatus").val("Appear");
 	 $("#txtTime").val("");
    }
}
</script>
</head>
<body id="myBody" onload="">
	<h1>Update Symptoms for a Patient</h1>
	<form action="UpdateSymptoms" method="post" id="frmUpdate"> 
		<div>
			<label>Symptom Id<sup>*</sup>:</label>
			&nbsp;
			<select id = "selPid" name = "selPid" required="required" onchange="GetSymptomsByAjax();"> 
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
			<label>Patient Id:<sup>*</sup></label>
			&nbsp;<input type="text" name="txtPid" id="txtPid" required="required" 
			maxlength="255" value="${pid}" readonly="readonly"><br>
		</div>
		<div>
			<label>Symptom Name:<sup>*</sup></label>
			&nbsp;<input type="text" name="txtName" id="txtName" required="required" 
			maxlength="255" value="${name}"><br>
		</div>
		<div>
			<label>Status<sup>*</sup></label>&nbsp;
			<%
			String status = "";
			status = request.getAttribute("status").toString();
			
			if(status != null && status != "" && status.equals("Disappear")){
			%>
			
			<select id = "selStatus" name = "selStatus" required="required" > 
					<option value="Appear" >Appear</option>
					<option value="Disappear" selected="selected">Disappear</option>
			</select>
			<%}else if(status != null && status != "" && status.equals("Appear")){
				%>
				<select id = "selStatus" name = "selStatus" required="required" > 
					<option value="Appear" selected="selected">Appear</option>
					<option value="Disappear">Disappear</option>
				</select>
			<%}else{ %>
				<select id = "selStatus" name = "selStatus" required="required" > 
					<option value="Appear" selected="selected">Appear</option>
					<option value="Disappear">Disappear</option>
				</select>
			<%} %>
		</div>
		<div>
			<label>Timestamp<sup>*</sup>:</label>
			&nbsp;<input type="text" name="txtTime" id="txtTime" required="required" 
			maxlength="255" value="${time}" readonly="readonly" ><br>
		</div>
		<div>
			<input type="submit" name="btnUpdate" id="btnUpdate" 
			 value=" Update ">
				&nbsp;&nbsp;		
			<a href="UserChoice.jsp">
				<input type="button" name="btnBack" id="btnBack" value="Back">
			</a>
		</div>		
	</form>
	<p align="center"><b><i>${updateStatus}</i></b></p>
</body>
</html>
