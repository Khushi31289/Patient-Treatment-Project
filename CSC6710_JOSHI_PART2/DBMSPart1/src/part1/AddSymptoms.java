package part1;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Request;

/**
 * Servlet implementation class UpdatePatient
 */
@WebServlet("/AddSymptoms")
public class AddSymptoms extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private String ListOfPids;
	private String valPid;
	private String valStatus;
	private String valName;
	private String myResponse;
	private String addStatus;
    /**
     * @see HttpServlet#HttpServlet()
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
						throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
									throws ServletException, IOException {
		addStatus = TextBank.ENTRDATA_SYMPTOM;
		//TextBank.UPDATE_PATIENTS_QUERY = "UPDATE patients SET ";
		TextBank.INSERT_SYMPTOM_QUERY = "INSERT INTO Symptom (symname , pid ,sympstatus ) values";
		valPid = "";
		valPid = request.getParameter("selPid");
		if(null != valPid ){
			if(valPid != "0" && !valPid.isEmpty())
				getFormData(request);
		}
		getPatientIDs(request, response);
		request.setAttribute("addStatus", addStatus);
		request.getRequestDispatcher("/AddSymptom.jsp").forward(request, response);
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
			        	int cnt = 0;
			        	ListOfPids= "";
			        	valPid = req.getParameter("selPid");
			        	while(MyJDBCDriver.resultSet.next()){
			        		cnt++;
			        		pID = Integer.parseInt(MyJDBCDriver.resultSet.getString("pid"));
			        		String  str = String.format("%03d", (Object)pID);
			        		if(valPid != null && valPid  != "0"){
			        			if(Integer.parseInt(valPid) == pID){
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
		
		if(valPid != null && valPid != "0" ){
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
				if(Integer.parseInt(valPid) > 0){
					String  str = String.format("%03d", (Object)Integer.parseInt(valPid));
					System.out.println("Here : "+ str );
					TextBank.INSERT_SYMPTOM_QUERY += "('" + valName + "' , "+
													str + ", '"
													+ valStatus + "')";
					System.out.println(TextBank.INSERT_SYMPTOM_QUERY);
					insertValues(req);
				}else{
					addStatus = TextBank.ENTRDATA_SYMPTOM;
				}
			}
		}
	}
		
	
	private void insertValues(HttpServletRequest req) {
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
				addStatus = TextBank.ENTRDATA_SYMPTOM;
				try {
					MyJDBCDriver .getConn();
					MyJDBCDriver .getStatement();
					MyJDBCDriver.getResultSetForExecuteUpdate(TextBank.INSERT_SYMPTOM_QUERY );
					if(MyJDBCDriver.queryOP >= 1 ){
						addStatus = TextBank.ABLE_TO_INSERT_SYMPTOM;
					}
				} catch (Exception e) {
					GetErrorMsg.printError(e);
					addStatus = TextBank.UNABLE_TO_INSERT_SYMPTOM;
				}finally{
						MyJDBCDriver.closeAll();
				}
			}
	}
}
