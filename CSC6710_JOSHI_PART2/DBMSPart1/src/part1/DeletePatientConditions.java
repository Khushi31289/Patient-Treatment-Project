package part1;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DeleteConditions
 */
@WebServlet("/DeletePatientConditions")
public class DeletePatientConditions extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private String ListOfPids;
	private String valPConId;
	private String valStatus;
	private String valName;
	private String myResponse;
	private String deleteStatus;
	private String valPid;
    /**
    /**
     * @see HttpServlet#HttpServlet()
     */
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		doPost(request, response);// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		deleteStatus = TextBank.DELETEDATA_SYMPTOM;
		request.setAttribute("status", "Appear");
		TextBank.DELETE_PATIENTCONDITION_QUERY = "DELETE FROM PATIENTCONDITION";
		valPConId = "";
		valPConId = request.getParameter("selPid");
		if(null != valPConId ){
			if(valPConId != "0" && !valPConId.isEmpty())
				getFormData(request);
		}
		getPatientConditionIDs(request, response);
		request.setAttribute("deleteStatus", deleteStatus);
		request.getRequestDispatcher("/DeleteCondition.jsp").forward(request, response);

//		doGet(request, response);
	}
	
	private void getPatientConditionIDs(HttpServletRequest req, HttpServletResponse resp) {
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
			        	valPConId = req.getParameter("selPid");
			        	while(MyJDBCDriver.resultSet.next()){
			        		cnt++;
			        		pID = Integer.parseInt(MyJDBCDriver.resultSet.getString("pconid"));
			        		String  str = String.format("%03d", (Object)pID);
			        		if(valPConId != null && valPConId  != "0"){
			        			if(Integer.parseInt(valPConId) == pID){
			        				ListOfPids += "<option value='"+ str +"' selected='selected'>"+ str +
			        								"</option>";
			        				//setFormData(MyJDBCDriver.resultSet, req);
			        				
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
		if(valPConId != null && valPConId != "0" ){
			valName = req.getParameter("txtName");
			valStatus = req.getParameter("selStatus");
			valPid = req.getParameter("txtPid");
			System.out.println("Patient id = " + valPid);
			System.out.println("Here 2 " + TextBank.DELETEDATA_PATIENTCONDITION );
				if(Integer.parseInt(valPConId) > 0){
					String  str = String.format("%03d", (Object)Integer.parseInt(valPConId));
					System.out.println("con id2  = " + Integer.parseInt(valPConId));
					TextBank.DELETE_PATIENTCONDITION_QUERY += TextBank._WHERE +	"pconid = " + str 
															+ TextBank._AND + "pid = "+ Integer.parseInt(valPid);
					System.out.println(TextBank.DELETE_PATIENTCONDITION_QUERY);
					deleteValues(req);
				}else{
					deleteStatus = TextBank.DELETEDATA_PATIENTCONDITION;
				}
			}
	}
		
	
	private void deleteValues(HttpServletRequest req) {
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
				deleteStatus = TextBank.DELETEDATA_PATIENTCONDITION;
				try {
					MyJDBCDriver .getConn();
					MyJDBCDriver .getStatement();
					MyJDBCDriver.getResultSetForExecuteUpdate(TextBank.DELETE_PATIENTCONDITION_QUERY);
					if(MyJDBCDriver.queryOP >= 1 ){
						deleteStatus = TextBank.ABLE_TO_DELETE_PATIENTCONDITION;
					}
				} catch (Exception e) {
					GetErrorMsg.printError(e);
					deleteStatus = TextBank.UNABLE_TO_DELETE_PATIENTCONDITION;
				}finally{
						MyJDBCDriver.closeAll();
				}
			}
	}
}
