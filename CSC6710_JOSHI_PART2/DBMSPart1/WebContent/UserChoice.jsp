<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Make your choice</title>
</head>
<body>
<form>
	<p>Select from below given choices</p><br>
	<h1>1.Patient</h1>
		<a href="<%=request.getContextPath()%>/AddPatients">Add New Patient</a><br>
		<a href="index3.html">Search a Patient</a><br>
		<a href="<%=request.getContextPath()%>/UpdatePatients">Update a Patient</a><br>
		<a href="<%=request.getContextPath()%>/DeletePatients">Delete a Patient</a>
	<h1>2.Symptom</h1>
		<a href="<%=request.getContextPath()%>/AddSymptoms">Add Symptom</a><br>
		<a href="<%=request.getContextPath()%>/UpdateSymptoms">Update Symptom</a><br>
		<a href="<%=request.getContextPath()%>/DeleteSymptoms">Delete Symptom</a><br>
		
	<h1>3.Condition</h1>
		<a href="<%=request.getContextPath()%>/AddPatientConditions">Add Condition</a><br>
		<a href="<%=request.getContextPath()%>/UpdatePatientConditions">Update Condition</a><br>
		<a href="<%=request.getContextPath()%>/DeletePatientConditions">Delete Condition</a>
	
	<h1>4.Treatment</h1>
		<a href="<%=request.getContextPath()%>/AddPatients">Add Treatment</a><br>
		<a href="<%=request.getContextPath()%>/UpdatePatients">Update Treatment</a><br>
		<a href="<%=request.getContextPath()%>/DeletePatients">Delete Treatment</a>
		
 	<h1>5. Search the patients</h1>
  		<a href="<%=request.getContextPath()%>/QueryNo5"> Search patients that have both symptoms cough and fatigue right now.</a><br>
	
	<h1>6. Search the patients</h1>
  		<a href="<%=request.getContextPath()%>/QueryNo6"> Search patients that have conditions <b>diabetes</b> sometime in the past but have no such conditions now.</a><br>
	
	<h1>7. Find the patients</h1>
	<a href="<%=request.getContextPath()%>/QueryNo7"> Given a patient X (user specified), find the patients who sent the most number of message to patient X. 
	<br>If there is a tie among several such patients, then list them all; otherwise, just list one.
	</a>
	
	<h1>8. Find the patients</h1>
	<a href="<%=request.getContextPath()%>/QueryNo8"> Find the patients who are under treatment <b>physical therapy</b> and who have never sent out any messages
	</a>
	
	<h1>9. Find the patients</h1>
	<a href="<%=request.getContextPath()%>/QueryNo9"> Find the patients who are the second oldest in the database. 
	<br>If there is a tie among several such patients, then list them all; 
	otherwise, just list one.
	</a>
	
	<h1>10. Find the patients</h1>
	<a href="<%=request.getContextPath()%>/QueryNo10">Find a female patient who sent a message to a patient who has the condition <b>diabetes</b> now</a>
</form>
</body>
</html>