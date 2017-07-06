package part1;

import java.io.IOException;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GetSymptomByAjax
 */
@WebServlet("/GetSymptomByAjax")
public class GetSymptomByAjax extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String responseString;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		responseString = "";
		String valPid = request.getParameter("Pid");
		System.out.println(valPid);
		if(valPid != "" && valPid != null){
			try{
				if(Integer.parseInt(valPid) > 0){
					getSymptomIDs(request,response,valPid);
					response.setContentType("text/plain");
					System.out.println(responseString);
				}
			}catch(NumberFormatException ex){
				ex.printStackTrace();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		response.getWriter().write(responseString);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private void getSymptomIDs(HttpServletRequest req, HttpServletResponse resp, String pid) {
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
				try {
					MyJDBCDriver .getConn();
					MyJDBCDriver .getStatement();
					System.out.println(pid);
					MyJDBCDriver.getResultSet(TextBank.AJAX_SEL_SYMPTOM_INFO_UPDATE + 
										" where sympid=" + Integer.parseInt(pid));
					int pID = 0;
			        if(MyJDBCDriver.resultSet != null){
			        	int cnt = 0;
			        	while(MyJDBCDriver.resultSet.next()){
			        		setFormData(MyJDBCDriver.resultSet, req);
			        		System.out.println("Post sendformdata" );
			        	}
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
		System.out.println("pid: " + resultSet.getString("pid") + ", symname: " + 
				resultSet.getString("symname") + ", status: "+ resultSet.getString("sympstatus"));
		responseString += resultSet.getString("pid") + "<?>"; 
		responseString += resultSet.getString("symname") + "<?>"; 
		responseString += resultSet.getString("sympstatus")+ "<?>";
		responseString += resultSet.getString("dttime")+ "<?>";
		//req.setAttribute("pid", resultSet.getString("pid"));
		//req.setAttribute("name", resultSet.getString("symname"));
		//req.setAttribute("sympstatus", resultSet.getString("sympstatus"));
	} catch (SQLException e) {
		e.printStackTrace();
		}
	}
}
