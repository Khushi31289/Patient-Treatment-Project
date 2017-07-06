package part1;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UpdatePatientConditions
 */
@WebServlet("/UpdatePatientConditions")
public class UpdatePatientConditions extends HttpServlet {
	private static final long serialVersionUID = 1L;
	  private String ListOfPids;
		private String valPconId;
		private String valStatus;
		private String valName;
		private String myResponse;
		private String updateStatus;
	    /**
	     * @see HttpServlet#HttpServlet()
	     */
		protected void doGet(HttpServletRequest request, HttpServletResponse response) 
							throws ServletException, IOException {
			// TODO Auto-generated method stub
			doPost(request, response);
		}

		protected void doPost(HttpServletRequest request, HttpServletResponse response) 
										throws ServletException, IOException {
			updateStatus = TextBank.UPDATEDATA_PATIENTCONDITION;
			request.setAttribute("status", "Appear");			
			/*UPDATE patientcondition SET pconname='Ligament tear2', dttime='2015-10-31 10:40:00', 
 				pconstatus='Disappear' 
					WHERE pconid='001' 
					*/
			/*UPDATE sampledb.patientcondition SET pid='002', 
			   pconname='Ligament tear2', dttime='2015-10-31 10:40:00', pconstatus='Disappeara' 
					  * WHERE pid='001' andpconname='Ligament tear' andpconstatus='Disappear';*/
			
			TextBank.UPDATE_PATIENTCON_QUERY = "UPDATE patientcondition SET ";
			//pconname, pid,pconstatus) VALUES";
			
			valPconId = "";
			valPconId = request.getParameter("selPid");
			if(null != valPconId ){
				if(valPconId != "0" && !valPconId.isEmpty())
					getFormData(request);
			}
			getPatientIDs(request, response);
			request.setAttribute("updateStatus", updateStatus);
			request.getRequestDispatcher("/UpdateCondition.jsp").forward(request, response);
		}
		
		private void getPatientIDs(HttpServletRequest req, HttpServletResponse resp) {
			boolean driver= true;
			try {
					Class.forName(TextBank.JDBC_DRIVER);
			} catch (ClassNotFoundException e) {
					driver =  false;
					GetErrorMsg.printError(e);
			}
			if(driver ==  true){
					System.out.println("Inside driver for connection");
					try {
						MyJDBCDriver .getConn();
						MyJDBCDriver .getStatement();
						MyJDBCDriver.getResultSet(TextBank.SEL_PIDS_FROM_PATIENTCONDITION_QUERY);
						int pID = 0;
				        if(MyJDBCDriver.resultSet != null){
				        	int cnt = 0;
				        	ListOfPids= "";
				        	valPconId = req.getParameter("selPid");
				        	while(MyJDBCDriver.resultSet.next()){
				        		cnt++;
				        		pID = Integer.parseInt(MyJDBCDriver.resultSet.getString("pconid"));
				        		String  str = String.format("%03d", (Object)pID);
				        		if(valPconId != null && valPconId  != "0"){
				        			if(Integer.parseInt(valPconId) == pID){
				        				ListOfPids += "<option value='"+ str +"' selected='selected'>"+ str +
				        								"</option>";
				        			}else{
						        		ListOfPids += "<option value='"+ str +"'>"+ str +"</option>";
				        			}
				        		}else{
				        			ListOfPids += "<option value='"+ str +"'>"+ str +"</option>";
				        		}
				        	}
				        	setPID(req);
				        }
					} catch (Exception e) {
						e.printStackTrace();
					}finally{
							MyJDBCDriver.closeAll();
					   }
				}
		}

		private void setPID(HttpServletRequest req) {
			// TODO Auto-generated method stub
	        req.setAttribute("_pids", ListOfPids);
	        System.out.println(ListOfPids);
		}
		
		private void getFormData(HttpServletRequest req) {
			
			if(valPconId != null && valPconId != "0" ){
				valName = req.getParameter("txtName");
				valStatus = req.getParameter("selStatus");
				if(valName !="" && valName !=null && 
						valStatus != "" && valStatus != null ){
					/*
					 *    insert into Symptom (
						    symname , pid , dttime ,
						    sympstatus )
						    values('Cold',1,'2015-10-11 09:30:00',
						    'Appear' );*/
					if(Integer.parseInt(valPconId) > 0){
						String  str = String.format("%03d", (Object)Integer.parseInt(valPconId));
						System.out.println("Here : "+ str );
						//pconname, pid,pconstatus
						TextBank.UPDATE_PATIENTCON_QUERY += "pconname = '" + valName + "' , "+
														"pconstatus = '"+ valStatus + "'"+
														TextBank._WHERE + "pconid = " + valPconId;
						System.out.println(TextBank.UPDATE_PATIENTCON_QUERY);
						updateValues(req);
					}else{
						updateStatus = TextBank.UPDATEDATA_PATIENTCONDITION;
					}
				}
			}
		}
			
		
		private void updateValues(HttpServletRequest req) {
			// TODO Auto-generated method stub
			boolean driver= true;
			try {
					Class.forName(TextBank.JDBC_DRIVER);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
					driver =  false;
					GetErrorMsg.printError(e);
			}
			if(driver ==  true){
					System.out.println("Inside driver for connection");
					updateStatus = TextBank.UPDATEDATA_PATIENTCONDITION;
					try {
						MyJDBCDriver .getConn();
						MyJDBCDriver .getStatement();
						MyJDBCDriver.getResultSetForExecuteUpdate(TextBank.UPDATE_PATIENTCON_QUERY );
						if(MyJDBCDriver.queryOP >= 1 ){
							updateStatus = TextBank.ABLE_TO_UPDATE_PATIENTCONDITION;
						}
					} catch (Exception e) {
						GetErrorMsg.printError(e);
						updateStatus = TextBank.UNABLE_TO_UPDATE_PATIENTCONDITION;
					}finally{
							MyJDBCDriver.closeAll();
					}
				}
		}
	}
