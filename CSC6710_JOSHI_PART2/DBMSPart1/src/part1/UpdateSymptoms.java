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

@WebServlet("/UpdateSymptoms")
public class UpdateSymptoms extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private String ListOfPids;
	private String valSympId;
	private String valStatus;
	private String valName;
	private String myResponse;
	private String updateStatus;
	private String valPid;
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
		updateStatus = TextBank.ENTRDATA_SYMPTOM;
		/*
		 * UPDATE symptom SET symname='Fever', pid='002', dttime='2015-10-11 13:30:42', 
		 * sympstatus='Disappear' WHERE sympid='6' and pid='001' and symname='Nausea';
		 * */
		request.setAttribute("status", "Appear");
		TextBank.UPDATE_SYMPTOM_QUERY = "UPDATE symptom SET ";
		valSympId = "";
		valSympId = request.getParameter("selPid");
		if(null != valSympId ){
			if(valSympId != "0" && !valSympId.isEmpty())
				getFormData(request);
		}
		getPatientIDs(request, response);
		request.setAttribute("updateStatus", updateStatus);
		request.getRequestDispatcher("/UpdateSymptom.jsp").forward(request, response);
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
					MyJDBCDriver.getResultSet(TextBank.SEL_PIDS_FROM_SYMPTOM_QUERY);
					int pID = 0;
			        if(MyJDBCDriver.resultSet != null){
			        	int cnt = 0;
			        	ListOfPids= "";
			        	valSympId = req.getParameter("selPid");
			        	while(MyJDBCDriver.resultSet.next()){
			        		cnt++;
			        		pID = Integer.parseInt(MyJDBCDriver.resultSet.getString("sympid"));
			        		String  str = String.format("%03d", (Object)pID);
			        		if(valSympId != null && valSympId  != "0"){
			        			if(Integer.parseInt(valSympId) == pID){
			        				ListOfPids += "<option value='"+ str +"' selected='selected'>"+ str +
			        								"</option>";
			        				setFormData(MyJDBCDriver.resultSet, req);
			        				
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
	
	
	private void setFormData(ResultSet resultSet, HttpServletRequest req) {
		try {
			System.out.println("pid: " + resultSet.getString("pid") + ", symname: " + 
					resultSet.getString("symname") + ", status: "+ resultSet.getString("sympstatus"));
			req.setAttribute("pid", resultSet.getString("pid"));
			req.setAttribute("name", resultSet.getString("symname"));
			req.setAttribute("status", resultSet.getString("sympstatus"));
			req.setAttribute("time", resultSet.getString("dttime"));
		} catch (SQLException e) {
			e.printStackTrace();
			}
		}
	
	private void getFormData(HttpServletRequest req) {
		
		if(valSympId != null && valSympId != "0" ){
			valName = req.getParameter("txtName");
			valStatus = req.getParameter("selStatus");
			System.out.println("Here 2 " + TextBank.UPDATEDATA_SYMPTOM );
			if(valName !="" && valName !=null && 
					valStatus != "" && valStatus != null ){
				/*
				 *    insert into Symptom (
					    symname , pid , dttime ,
					    sympstatus )
					    values('Cold',1,'2015-10-11 09:30:00',
					    'Appear' );*/
				if(Integer.parseInt(valSympId) > 0){
					String  str = String.format("%03d", (Object)Integer.parseInt(valSympId));
					System.out.println("Here : "+ str );
					TextBank.UPDATE_SYMPTOM_QUERY +=  "symname = '"+ valName + "' , "+
													 "sympstatus = '"
													+ valStatus + "'" +
													 TextBank._WHERE +	"sympid = " + str;
					System.out.println(TextBank.UPDATE_SYMPTOM_QUERY);
					UpdateValues(req);
				}else{
					updateStatus = TextBank.UPDATEDATA_SYMPTOM;
				}
			}
		}
	}
		
	
	private void UpdateValues(HttpServletRequest req) {
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
				updateStatus = TextBank.UPDATEDATA_SYMPTOM;
				try {
					MyJDBCDriver .getConn();
					MyJDBCDriver .getStatement();
					MyJDBCDriver.getResultSetForExecuteUpdate(TextBank.UPDATE_SYMPTOM_QUERY);
					if(MyJDBCDriver.queryOP >= 1 ){
						updateStatus = TextBank.ABLE_TO_UPDATE_SYMPTOM;
					}
				} catch (Exception e) {
					GetErrorMsg.printError(e);
					updateStatus = TextBank.UNABLE_TO_UPDATE_SYMPTOM;
				}finally{
						MyJDBCDriver.closeAll();
				}
			}
	}
}
