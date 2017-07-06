package part1;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class GetPatientIdsByAjax
 */
@WebServlet("/GetPatientIdsByAjax")
public class GetPatientIdsByAjax extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String responseString;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
									throws ServletException, IOException {
		responseString = "";
		String valPid = request.getParameter("Pid");
		System.out.println(valPid);
		if(valPid != "" && valPid != null){
			try{
				if(Integer.parseInt(valPid) > 0){
					getPatientIDs(request,response,valPid);
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

	private void getPatientIDs(HttpServletRequest req, HttpServletResponse resp, String pid) {
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
					MyJDBCDriver.getResultSet(TextBank.AJAX_SEL_PATIENT_INFO_UPDATE + 
										" where pid=" + Integer.parseInt(pid));
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
			//responseString = new String[6];
			responseString += resultSet.getString("firstname")+ "<?>"; 
			responseString += resultSet.getString("lastname") + "<?>";
			responseString += resultSet.getString("age") + "<?>";
			responseString += resultSet.getString("address") + "<?>";
			responseString += resultSet.getString("email") + "<?>";
			responseString += resultSet.getString("gender");
			System.out.println(responseString);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
}
