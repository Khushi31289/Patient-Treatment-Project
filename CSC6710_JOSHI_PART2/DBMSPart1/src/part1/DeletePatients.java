package part1;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DeletePatients
 */

//@WebServlet("/DeletePatients")
@WebServlet(name = "DeletePatients", urlPatterns = { "/DeletePatients" })
public class DeletePatients extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String deleteStatus;
	private String ListOfPids;
	private String[] valPid;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
					throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
					throws ServletException, IOException {
		// TODO Auto-generated method stub
		ListOfPids = "";
		deleteStatus = TextBank.DELETE_DATA_PT;
		TextBank.DELETE_PATIENTS_QUERY = "DELETE FROM patients WHERE pid IN ";
		processPatientIds(request);
		System.out.println(Arrays.toString(valPid));
		getPatientIDs(request, response);
		request.setAttribute("deleteStatus", deleteStatus);
		request.getRequestDispatcher("/DeletePt.jsp").forward(request, response);
	}
	
	private void processPatientIds(HttpServletRequest request) {
		String temp[] = request.getParameterValues("selPid");
		if(temp != null){
			deleteStatus += temp.length + " - ";
			if(temp.length == 1  && temp[0] == "0"){
				//skip this because its 0 entry
			}
			else{
				int len = temp.length;
				TextBank.DELETE_PATIENTS_QUERY += " ( ";
				for(int i=0; i < len; i++){
					if(i == len-1){
						TextBank.DELETE_PATIENTS_QUERY += temp[i];
					}else{
						TextBank.DELETE_PATIENTS_QUERY += temp[i] + ",";
					}
				}
				TextBank.DELETE_PATIENTS_QUERY += " )";
				System.out.println(TextBank.DELETE_PATIENTS_QUERY);
				deletePatient(request);
			}
		}
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
					MyJDBCDriver.getResultSet(TextBank.SELECT_PIDS_FOR_UPDATE);
					int pID = 0;
			        if(MyJDBCDriver.resultSet != null){
			        	while(MyJDBCDriver.resultSet.next()){
			        		pID = Integer.parseInt(MyJDBCDriver.resultSet.getString("pid"));
			        		String  str = String.format("%03d", (Object)pID);
			        		ListOfPids += "<option value='"+ str +"'>"+ str +"</option>";
			        	}
			        	setPID(req);
			        }
				} catch (Exception e) {
					// TODO Auto-generated catch block
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
	
	private void deletePatient(HttpServletRequest req) {
		boolean driver= true;
		try {
				Class.forName(TextBank.JDBC_DRIVER);
		} catch (ClassNotFoundException e) {
				driver =  false;
				GetErrorMsg.printError(e);
		}
		if(driver ==  true){
				System.out.println("Inside driver for connection");
				deleteStatus = TextBank.DELETE_DATA_PT;
				try {
					MyJDBCDriver .getConn();
					MyJDBCDriver .getStatement();
					MyJDBCDriver.getResultSetForExecuteUpdate(TextBank.DELETE_PATIENTS_QUERY);
					if(MyJDBCDriver.queryOP >= 1 ){
						deleteStatus = TextBank.ABLE_TO_DELETE_PT;
					}
				} catch (Exception e) {
					GetErrorMsg.printError(e);
					deleteStatus = TextBank.UNABLE_TO_DELETE_PT;
				}finally{
						MyJDBCDriver.closeAll();
				}
		}
	}
}
