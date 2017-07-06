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
@WebServlet("/UpdatePatient")
public class UpdatePatients extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private String ListOfPids;
	private String valPid;
	private String valFName;
	private String valLName;
	private String valGender;
	private String valAge;
	private String valAddress;
	private String valEmail;
	private String myResponse;
	private String updateStatus;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdatePatients() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
						throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
									throws ServletException, IOException {
		// TODO Auto-generated method stub
		updateStatus = TextBank.UPDATE_DATA_PT;
		TextBank.UPDATE_PATIENTS_QUERY = "UPDATE patients SET ";
		request.setAttribute("valGender", "");
		getFormData(request);
		getPatientIDs(request, response);
		request.setAttribute("updateStatus", updateStatus);
		request.getRequestDispatcher("/UpdatePt.jsp").forward(request, response);
		//String valPid = request.getParameter("Pid");
		//doGet(request, response);
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
			        		//ListOfPids += "<option value='"+ str +"'>"+ str +"</option>";
			        		if(valPid != null && valPid  != "0"){
			        			if(Integer.parseInt(valPid) == pID){
			        				setFormData(MyJDBCDriver.resultSet, req);
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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
						MyJDBCDriver.closeAll();
				   }
			}
	}

	private void setFormData(ResultSet resultSet, HttpServletRequest req) {
		try {
			req.setAttribute("fname", resultSet.getString("firstname"));
			req.setAttribute("lname", resultSet.getString("lastname"));
			req.setAttribute("valAge", resultSet.getString("age"));
			req.setAttribute("valAddress", resultSet.getString("address"));
			req.setAttribute("valEmail", resultSet.getString("email"));
			req.setAttribute("valGender", resultSet.getString("gender"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void setPID(HttpServletRequest req) {
		// TODO Auto-generated method stub
        req.setAttribute("_pids", ListOfPids);
        System.out.println(ListOfPids);
	}
	
	private void getFormData(HttpServletRequest req) {
		valPid = req.getParameter("selPid");
		System.out.println("Here : "+ valPid);
		if(valPid != null && valPid != "0" ){
			if(req.getParameter("radioMale") != null ){
				valGender = req.getParameter("radioMale");	
			}else{
				valGender = req.getParameter("radioFemale");
			}
			valAge = req.getParameter("txtage");
			valFName = req.getParameter("txtfname");
			valLName = req.getParameter("txtlname");
			valEmail = req.getParameter("txtemail");
			valAddress = req.getParameter("txtaddress");
			System.out.println("Here 2 " + TextBank.UPDATE_PATIENTS_QUERY );
			if(valFName !="" && valFName !=null && 
					valLName != "" && valLName != null
					&& valEmail != "" && valEmail != null
					&&  valAddress != "" && valEmail != null
					&& valAge != "" && valAge != null
					&& valGender != "" && valGender != null
					){
				/*
				 * UPDATE patients SET firstname='Khushiii', lastname='Joshiii', 
				 address='Detroit-USA-48202', age=23, 
				 email='kj123@gmail.com', gender='Female' WHERE pid=001;*/
				TextBank.UPDATE_PATIENTS_QUERY += "firstname = '"+ valFName +"'" + ","
												+ "lastname= '" + valLName +"' , " 
												+ "address= '"+ valAddress +"' , " 
												+ "age=" + valAge + " , " 
												+ "email= '" + valEmail + "' , "
												+ "gender= '" + valGender  +"' "
												+ TextBank._WHERE + 
												" pid = "+Integer.parseInt(valPid);
				System.out.println(TextBank.UPDATE_PATIENTS_QUERY );
				updateValues(req);
			}
		}
		else{
			//updateStatus= TextBank.ENTRDATAPT;
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
				updateStatus = TextBank.UPDATE_DATA_PT;
				try {
					MyJDBCDriver .getConn();
					MyJDBCDriver .getStatement();
					MyJDBCDriver.getResultSetForExecuteUpdate(TextBank.UPDATE_PATIENTS_QUERY );
					if(MyJDBCDriver.queryOP >= 1 ){
						updateStatus = TextBank.ABLE_TO_UPDATE_PT;
						//pID++;
			        	//setPID(pID,req);
					}
				} catch (Exception e) {
					GetErrorMsg.printError(e);
					updateStatus = TextBank.UNABLE_TO_UPDATE_PT;
				}finally{
						MyJDBCDriver.closeAll();
				}
			}
	}
}
