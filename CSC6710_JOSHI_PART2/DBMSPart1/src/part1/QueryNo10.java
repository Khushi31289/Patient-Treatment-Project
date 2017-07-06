package part1;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class QueryNo10
 */
@WebServlet("/QueryNo10")
public class QueryNo10 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private int pID;
	String myResponse;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		TextBank.SELECTQUERY = "Select * from patients";
		response.getWriter().append("\nNote: The page will be blank if no records found");
		getNewPatientID(request, response);
		
		if(response.getWriter().toString().equals("")){
			response.getWriter().print("\nNo match found for the current search\n");
		}
	}
	
	
	private void getNewPatientID(HttpServletRequest req, HttpServletResponse resp) {
		// TODO Auto-generated method stub
		boolean driver= true;
		myResponse = "";
		List<Integer> _pids = new ArrayList<Integer>();
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
					MyJDBCDriver.getResultSet(TextBank.SEARCH_QUERY_10_DIABETES);
					
			        if(MyJDBCDriver.resultSet != null){
			        	while(MyJDBCDriver.resultSet.next()){
			        		pID = Integer.parseInt(MyJDBCDriver.resultSet.getString("pid"));
			        		_pids.add(pID);
			        	}
			        }
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
						Iterator<Integer> i1 = _pids.iterator();
						List<Integer> listOfPatients = new ArrayList<Integer>();
			        	while(i1.hasNext()){
			        	//	System.out.println(i1.next());
			        		int thisPid = i1.next();
			        		boolean found = checkForDiabetesAppear(thisPid);
			        		if(found == true){
			        			listOfPatients.add(thisPid);
			        		}
			        	}
			        	i1 = listOfPatients.iterator();
			        	String PidList = "";
			        	while(i1.hasNext()){
			        		//System.out.println(i1.next());
			        		if(PidList == ""){
			        			PidList = i1.next().toString();
			        		}else{
			        			PidList += "," + i1.next().toString();
			        		}
			        	}
			        	//System.out.println(PidList);
			        	if(!PidList.equals("")){
				        	try {
								if(MyJDBCDriver.stmt.isClosed()){
									MyJDBCDriver .getStatement();
								}
								String query = TextBank.SEARCH_QUERY_10 + TextBank._WHERE + 
													"rcvrpid IN (" + PidList  +" ) )";
								System.out.println(query);
								MyJDBCDriver.getResultSet(query);
								if(MyJDBCDriver.resultSet != null){
								    displayDataInTable(MyJDBCDriver.resultSet,resp);
									System.out.println(resp.getWriter().toString());
								}else{
									resp.getWriter().print("\nNo match found for the current search\n");
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
			        	}
			        
			        	MyJDBCDriver.closeAll();
				   }
			}
		
		req.setAttribute("var3", myResponse);
		//req.getRequestDispatcher("/SearchPt.jsp").forward(req, resp);
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
	
	
	boolean checkForDiabetesAppear(int _pid) {
		// TODO Auto-generated method stub
		boolean driver= true;
		boolean found = false;
		try {
				if(MyJDBCDriver.stmt.isClosed()){
					MyJDBCDriver .getStatement();
				}
				String queryString = TextBank.SEARCH_QUERY_10_APPEAR_DIABETES;
				queryString += TextBank._AND + "p.pid = " + _pid +
								TextBank.QUERY5_ORDERBY_DT_DESC;
				System.out.println(queryString);
					MyJDBCDriver.getResultSet(queryString);
					//" AND s.pid = 10 and LOWER(symname) = 'cough'";
					
			        if(MyJDBCDriver.resultSet != null){
			        	while(MyJDBCDriver.resultSet.next()){
			        		String status = MyJDBCDriver.resultSet.getString("pconstatus");
			        		if(status.toLowerCase().equals("appear")){
			        			found = true;
			        		}else{
			        			found = false;
			        		}
			        	}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				found = false;
			}
		return found;
	}
}
