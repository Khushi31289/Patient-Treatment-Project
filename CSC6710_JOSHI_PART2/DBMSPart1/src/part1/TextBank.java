package part1;

public class TextBank {
	//DB connection, Driver details.
	 static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	 static final String DB_URL = "jdbc:mysql://localhost:3306/sampledb";
	 static final String UNM = "root";
	 static final String PWD = "pass1234";
	 
	 //For Login page
	 static final String LOGINUNSUCCESS = "Login UnSuccessful,Please retry..!";
	 static final String LOGINSUCCESS = "Login Successful";
	 static final String INITSUCCESS = "able to initialize the DB with 10 tupples";

	 //For searching a Patient	 
	 static final String errMsg ="These fields can accept only numeric values,re-enter";
	 static final String errMsgIdRange = "Patient Id can only be in range of 1-999";
	 static final String errMsgAgeRange = "Age can only be in range of 1 to 150";
	 static String SELECTQUERY = "Select * from patients";
	 static String NORECORDFOUND="No Records found for this search Criteria";
	 static final String _WHERE = " WHERE ";
	 static final String _AND = " AND ";
	 static final String _EQNUM = " = ";
	 static final String _EQSTR1 = " = '";
	 static final String _EQSTR2 = "'";
	 static final String _LIKESTR = " LIKE ";
	 static final String _LIKESTR1="'%"; 
	 static final String _LIKESTR2="%'";
	 
	 //For Inserting into patients
	 static final String GETMAXOFPID = "SELECT MAX(pid) as NEWPID FROM patients";
	 static String INSERTQUERYPT =
	 "INSERT INTO PATIENTS(firstname ,lastname, address , age , email, gender ) VALUES( ";
	 static final String UNABLETOINSERTPT = "Unable to add new patients, "
	 		+ "Please re-enter values in above fields properly.";
	 static final String ABLETOINSERTPT = "New patient added successfully.";
	 static final String ENTRDATAPT = "All above are mandatory fields, Enter data in all fields to add a new patient.";
	
	 //For Updating patient info
	 static final String UPDATE_DATA_PT = "All above are mandatory fields, Enter data in all fields to update a patient's information.";
	 static final String ABLE_TO_UPDATE_PT = "Patient's information updated successfully.";
	 static final String UNABLE_TO_UPDATE_PT = "Unable to update patient's information, "
		 		+ "Please re-enter values in above fields properly.";
	 static final String SELECT_PIDS_FOR_UPDATE =  "SELECT * FROM patients ORDER BY pid ASC";
	 static String UPDATE_PATIENTS_QUERY  = "UPDATE patients SET ";
	 
	 static final String AJAX_SEL_PATIENT_INFO_UPDATE =  "SELECT * FROM patients";
		 
	 /*UPDATE patients SET firstname='Khushiii', lastname='Joshiii', 
			 address='Detroit-USA-48202', age=23, 
			 email='kj123@gmail.com', gender='Female' WHERE pid=001; */
	 
	//For Deleting patient info
	 static final String DELETE_DATA_PT = "Select patient id's from the drop-down to delete a patient's information.";
	 static final String ABLE_TO_DELETE_PT = "Patient's information deleted successfully.";
	 static final String UNABLE_TO_DELETE_PT = "Unable to delete patient's information, "
		 		+ "Please re-select the patient ids from drop-down.";
	 static String DELETE_PATIENTS_QUERY = "DELETE FROM patients WHERE pid IN ";
	 
	 //Insert symptom
	 	/*insert into Symptom (
					    symname , pid , dttime ,
					    sympstatus )
					    values('Cold',1,'2015-10-11 09:30:00',
					    'Appear' )
	 	 * */	 
	 static String INSERT_SYMPTOM_QUERY = "INSERT INTO Symptom (symname , pid ,sympstatus ) values";
	 static final String UNABLE_TO_INSERT_SYMPTOM = "Unable to add new Symptom, "
		 		+ "Please re-enter values in above fields properly.";
	 static final String ABLE_TO_INSERT_SYMPTOM = "New Symptom added successfully.";
	 static final String ENTRDATA_SYMPTOM = "All above are mandatory fields, Enter data in all fields to add a new symptom.";

	 //Update Symptom
	 /*
	 * UPDATE symptom SET symname='Fever', pid='002', dttime='2015-10-11 13:30:42', 
	 * sympstatus='Disappear' WHERE sympid='6' and pid='001' and symname='Nausea';
	 * */	 
	 static String UPDATE_SYMPTOM_QUERY = "UPDATE symptom SET ";
	 static final String UNABLE_TO_UPDATE_SYMPTOM = "Unable to update Symptom, "
		 		+ "Please re-enter values in above fields properly.";
	 static final String ABLE_TO_UPDATE_SYMPTOM = "Symptom updated successfully.";
	 static final String UPDATEDATA_SYMPTOM = "All above are mandatory fields, Enter data in all fields to update symptom.";
	 static final String SEL_PIDS_FROM_SYMPTOM_QUERY = "SELECT * FROM symptom ORDER BY sympid";//"SELECT DISTINCT(pid) FROM symptom ORDER BY pid";
	 static final String AJAX_SEL_SYMPTOM_INFO_UPDATE = "SELECT * FROM symptom ";
	 
	//Delete Symptom
	 static String DELETE_SYMPTOM_QUERY = "DELETE FROM symptom";
	 static final String UNABLE_TO_DELETE_SYMPTOM = "Unable to delete Symptom, "
		 		+ "Please re-try.";
	 static final String ABLE_TO_DELETE_SYMPTOM = "Symptom deleted successfully.";
	 static final String DELETEDATA_SYMPTOM = "Select symptom id to delete a symptom.";
	 
	 /*insert into PatientCondition(
	    pid,pconname, dttime, pconstatus)
	    values(1, 'Ligament tear', '2015-10-11 20:00:00',
	    'Appear' );
			 * */
	 static String INSERT_PATIENTCON_QUERY = "INSERT INTO PatientCondition(pconname, pid,pconstatus) VALUES";
	 static final String UNABLE_TO_INSERT_PATIENTCONDITION = "Unable to add new condition, "
		 		+ "Please re-enter values in above fields properly.";
	 static final String ABLE_TO_INSERT_PATIENTCONDITION = "New condition added successfully.";
	 static final String ENTRDATA_PATIENTCONDITION = "All above are mandatory fields, Enter data in all fields to add a new condition.";

	 /*UPDATE sampledb.patientcondition SET pid='002', 
	  * pconname='Ligament tear2', dttime='2015-10-31 10:40:00', pconstatus='Disappeara' 
	  * WHERE pid='001' andpconname='Ligament tear' andpconstatus='Disappear';
	  * */
	 static final String AJAX_SEL_PATIENTCONDITION_INFO_UPDATE = "SELECT * FROM PatientCondition";
	 static String UPDATE_PATIENTCON_QUERY = "UPDATE patientcondition SET ";
	 static final String UNABLE_TO_UPDATE_PATIENTCONDITION = "Unable to update a condition, "
		 		+ "Please re-enter values in above fields properly.";
	 static final String ABLE_TO_UPDATE_PATIENTCONDITION = "The condition updated successfully.";
	 static final String UPDATEDATA_PATIENTCONDITION = "All above are mandatory fields, Enter data in all fields to update a new condition.";
	 static final String SEL_PIDS_FROM_PATIENTCONDITION_QUERY = "SELECT * FROM PatientCondition ORDER BY pconid";//"SELECT DISTINCT(pid) FROM symptom ORDER BY pid";
	 
	 
	//Delete PATIENTCONDITION
		 static String DELETE_PATIENTCONDITION_QUERY = "DELETE FROM PATIENTCONDITION";
		 static final String UNABLE_TO_DELETE_PATIENTCONDITION = "Unable to delete the condition for a patient, "
			 		+ "Please re-try.";
		 static final String ABLE_TO_DELETE_PATIENTCONDITION = "The condition deleted successfully.";
		 static final String DELETEDATA_PATIENTCONDITION = "Select Condition id to delete a condition.";
		 
		 
		 
	//QUERY NO 5 
		 static final String SEARCH_QUERY_5  =  "select distinct(pid) from symptom s where s.pid IN ("+
				 "select pid from symptom s1 where LOWER(s1.symname) = 'cough' order by s1.dttime DESC) and "+ 
				  "s.pid IN ("+
				 "select pid from symptom s2 where LOWER(s2.symname) = 'fatigue' order by s2.dttime DESC) "+
				  "order by dttime desc";
		 
		 static final String SEARCH_COUGH_QUERY_5 = "select  s.sympstatus from symptom s where s.pid IN (" + 
				 "select pid from symptom s1 where LOWER(s1.symname) = 'cough' order by s1.dttime DESC) and"
				 +" s.pid IN ( "
				+"select pid from symptom s2 where LOWER(s2.symname) = 'fatigue' order by s2.dttime DESC)";
				//+" AND s.pid = 10 and LOWER(symname) = 'cough' order by dttime desc limit 1";
		
		 

		 static final String SEARCH_FEVER_QUERY_5 = "select  s.sympstatus from symptom s where s.pid IN (" + 
				 "select pid from symptom s1 where LOWER(s1.symname) = 'cough' order by s1.dttime DESC) and"
				 +" s.pid IN ( "
				+"select pid from symptom s2 where LOWER(s2.symname) = 'fatigue' order by s2.dttime DESC)";
				//+" AND s.pid = 10 and LOWER(symname) = 'fatigue' order by dttime desc limit 1";

		 static final String QUERY5_ORDERBY_DT_DESC = " order by dttime desc limit 1"; 

		 //QUERY 6
		 
		 /*select distinct(pid) from patientcondition p where p.pid IN (
				 select p1.pid from patientcondition p1 where LOWER(p1.pconname) = 'diabetes' order by p1.dttime DESC) 
				 AND p.pconstatus= 'appear' order by dttime desc;

				BELOW QUERY TO BE RUN FOR PIDS GOT IN PREV QUERY
				 select pconstatus from patientcondition p where p.pid IN (
				 select p1.pid from patientcondition p1 where LOWER(p1.pconname) = 'diabetes' order by p1.dttime DESC) 
				 AND p.pid=5 order by dttime desc limit 1;
		*/
		 static final String SEARCH_QUERY_6  = "select distinct(pid) from patientcondition p where p.pid IN ("
				 +" select p1.pid from patientcondition p1 where LOWER(p1.pconname) = 'diabetes' order by p1.dttime DESC)" 
				 + " AND p.pconstatus= 'appear' order by dttime desc";
		 
		 static final String SEARCH_DIABETES_DISAPPEAR_QUERY = "select pconstatus from patientcondition p where p.pid IN ("
				 + " select p1.pid from patientcondition p1 where LOWER(p1.pconname) = 'diabetes' order by p1.dttime DESC) ";  

		 /*
		  */
		 
		 static final String SEARCH_QUERY_7 = "select distinct(senderpid) from messages where rcvrpid ";
		 
		 static final String SEARCH_QUERY_7_COUNT_MSGS  = "select count(*) as msgcount from messages where rcvrpid ";
		 static final String SEARCH_QUERY_7_PATIENTS = "SELECT * FROM patients ";
		 
		 
		/* select * from patients where pid IN 
		 (select distinct(t1.pid) from treatment t1 where LOWER(t1.treatname) = 'physical therapy') and
		 pid NOT IN (select distinct(senderpid) from messages);
		 */
		 static final String SEARCH_QUERY_8  = "select * from patients where pid IN "
				 + "(select distinct(t1.pid) from treatment t1 where LOWER(t1.treatname) = 'physical therapy') and"
				 + " pid NOT IN (select distinct(senderpid) from messages)";
		 
		 /* QUERY 9*/
		 static final String SEARCH_QUERY_9  ="SELECT * FROM patients p2 where dttime = ("
				 + "SELECT  p1.dttime  FROM patients p1 "
				 + "WHERE p1.dttime > ( SELECT MIN( dttime ) FROM patients) limit 1)";

		 
		 /*Query 10 */
		 
		 static final String SEARCH_QUERY_10 = "select * from patients pc where LOWER(gender) = 'female'" 
				 + "AND pc.pid IN (select senderpid from messages "; 
		 /*where rcvrpid in (those who have diabetes now)*/

		 
		 static final String SEARCH_QUERY_10_DIABETES = "select distinct(pid) from patientcondition p where p.pid IN ("
				 + " select p1.pid from patientcondition p1 where LOWER(p1.pconname) = 'diabetes' order by p1.dttime DESC)" 
				 + " AND p.pconstatus= 'appear' order by dttime desc";
		 //Will give patients who had appear entry for diabetes
		 
		 static final String SEARCH_QUERY_10_APPEAR_DIABETES = "select pconstatus from patientcondition p where p.pid IN ("
				+ " select p1.pid from patientcondition p1 where LOWER(p1.pconname) = 'diabetes' order by p1.dttime DESC)"; 
				 //AND p.pid=5 order by dttime desc limit 1;
		 
}
