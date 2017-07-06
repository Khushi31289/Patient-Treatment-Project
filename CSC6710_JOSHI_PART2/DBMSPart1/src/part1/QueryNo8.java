package part1;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class QueryNo8
 */
@WebServlet("/QueryNo8")
public class QueryNo8 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String myResponse;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		TextBank.SELECTQUERY = "Select * from patients";
		response.getWriter().append("\nNote: The page will be blank if no records found");
		searchForPatients(request, response);
		//doGet(request, response);
	}
	private void searchForPatients(HttpServletRequest req, HttpServletResponse resp) {
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
				System.out.println("Query 8");
				try {
					MyJDBCDriver .getConn();
					MyJDBCDriver .getStatement();
					MyJDBCDriver.getResultSet(TextBank.SEARCH_QUERY_8);
					System.out.println(TextBank.SEARCH_QUERY_8);
			        if(MyJDBCDriver.resultSet != null){
			        	displayDataInTable(MyJDBCDriver.resultSet,resp);
			        }
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
						MyJDBCDriver.closeAll();
				   }
			}
	}
	
	private void displayDataInTable(ResultSet rs, HttpServletResponse resp) {
		// TODO Auto-generated method stub
		int rowCount = 0;
			try {
				resp.getWriter().print("<P ALIGN='center'><TABLE BORDER=1>");
				 ResultSetMetaData rsmd = rs.getMetaData();
				 int columnCount = rsmd.getColumnCount();
				 // table header
				 resp.getWriter().print("<TR>");
				 for (int i = 0; i < columnCount; i++) {
					 String temp ="<TH>" +
							 rsmd.getColumnLabel(i + 1).toUpperCase() + "</TH>";
					 resp.getWriter().print(temp);
				   }
				 resp.getWriter().print("</TR>");
				 // the data
				 boolean RecordFound = false;
				 while (rs.next()) {
				  rowCount++;
				  resp.getWriter().print("<TR>");
				  myResponse +="<TR>";
				  for (int i = 0; i < columnCount; i++) {
					  resp.getWriter().print("<TD>" + rs.getString(i + 1) + "</TD>");
					  myResponse +="<TD>" + rs.getString(i + 1) + "</TD>";
					  RecordFound = true;
				    }
				  resp.getWriter().print("</TR>");
				  myResponse += "</TR>";
				  }
				 if(RecordFound == false){
					resp.getWriter().print("<TR>");
					myResponse += "<TR>";
					resp.getWriter().print("<TD colspan = '"+columnCount + "'>" +  
							TextBank.NORECORDFOUND + "</TD>");
					myResponse += "<TD colspan = '"+columnCount + "'>" +  
							TextBank.NORECORDFOUND + "</TD>";
					resp.getWriter().print("</TR>");
					myResponse += "</TR>";
				 }
				 resp.getWriter().print("</TABLE></P>");
			} catch (IOException e) {
				GetErrorMsg.printError(e);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				GetErrorMsg.printError(e);
			}
			 //return rowCount;
	}
}
