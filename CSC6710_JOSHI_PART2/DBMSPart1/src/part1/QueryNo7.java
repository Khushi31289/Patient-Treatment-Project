package part1;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class QueryNo7
 */
@WebServlet("/QueryNo7")
public class QueryNo7 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private int pID;
	private int valPid ;
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
		valPid = 0;
		try {
			valPid = Integer.parseInt(request.getParameter("txtpid"));
			if(valPid > 0){
				getNewPatientID(request,response);
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		 request.setAttribute("var3", myResponse);
		 request.getRequestDispatcher("/QueryNumber7.jsp").forward(request, response);
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
				//System.out.println("");
				try {
					MyJDBCDriver .getConn();
					MyJDBCDriver .getStatement();
					String query = TextBank.SEARCH_QUERY_7 + " = " + valPid;
					//"select distinct(senderpid) from messages where rcvrpid  = 1";
					MyJDBCDriver.getResultSet(query);
					
			        if(MyJDBCDriver.resultSet != null){
			        	while(MyJDBCDriver.resultSet.next()){
			        		pID = Integer.parseInt(MyJDBCDriver.resultSet.getString("senderpid"));
			        		_pids.add(pID);
			        	}
			        }
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
						Iterator<Integer> i1 = _pids.iterator();
					
						Map<Integer,Integer> msg_SenderIDMap = new HashMap<Integer,Integer>();
						//List<Integer> listOfPatients = new ArrayList<Integer>();
						msg_SenderIDMap = getMessageCount(_pids);
						Iterator<Integer> mapIterator = msg_SenderIDMap.keySet().iterator();
						System.out.println();
						while(mapIterator .hasNext()){
			        		int senderPid = mapIterator.next();
			        		System.out.print("Sender pid: "+senderPid);
			        		int noOfMsgs = msg_SenderIDMap.get(senderPid);
			        		System.out.println("Msg count: "+noOfMsgs);
			        	}
			        	//i1 = listOfPatients.iterator();
			        	
			        	List<Integer> totalMsgsList = new ArrayList<Integer>();
			        	totalMsgsList.addAll(msg_SenderIDMap.values());
			        	
			        	
			        	if (totalMsgsList.size() > 0 ) {
							int maxMsg = Collections.max(totalMsgsList);
							System.out.println("MAX Msg count = " + maxMsg);
							Iterator<Integer> finalIterator = msg_SenderIDMap.keySet().iterator();
							System.out.println();
							List<Integer> finalPatientsList = new ArrayList<Integer>();
							mapIterator = msg_SenderIDMap.keySet().iterator();
							while (mapIterator.hasNext()) {
								int senderPid = mapIterator.next();
								int noOfMsgs = msg_SenderIDMap.get(senderPid);
								if (noOfMsgs == maxMsg) {
									finalPatientsList.add(senderPid);
								}
							}
							System.out.println("********************");
							//						System.out.println(finalPatientsList.toArray().toString());
							System.out.println("********************");
							i1 = finalPatientsList.iterator();
							String PidList = "";
							while (i1.hasNext()) {
								//System.out.println(i1.next());
								if (PidList == "") {
									PidList = i1.next().toString();
								} else {
									PidList += "," + i1.next().toString();
								}
							}
							System.out.println(PidList);
							//System.out.println(PidList);
							if (!PidList.equals("")) {
								try {
									if (MyJDBCDriver.stmt.isClosed()) {
										MyJDBCDriver.getStatement();
									}
									String query = TextBank.SEARCH_QUERY_7_PATIENTS + TextBank._WHERE + "pid IN ("
											+ PidList + ")";
									System.out.println(query);
									MyJDBCDriver.getResultSet(query);
									if (MyJDBCDriver.resultSet != null) {
										displayDataInTable(MyJDBCDriver.resultSet, resp);
										System.out.println(resp.getWriter().toString());
									} else {
										resp.getWriter().print("\nNo match found for the current search\n");
									}
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							} 
						}else{
							myResponse += TextBank.NORECORDFOUND;
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
	
	Map<Integer,Integer> getMessageCount(List<Integer> _pids) {
		// TODO Auto-generated method stub
		boolean driver= true;
		Map<Integer,Integer> msg_SenderIDMap = new HashMap<Integer,Integer>();
		try {
				if(MyJDBCDriver.stmt.isClosed()){
					MyJDBCDriver .getStatement();
				}
				Iterator<Integer> i1 = _pids.iterator();
				while(i1.hasNext()){
					int senderPid = i1.next();
					//"select count(*) as msgcount from messages where rcvrpid = 1";
					String queryString = TextBank.SEARCH_QUERY_7_COUNT_MSGS + " = " + valPid;
					queryString += TextBank._AND + "senderpid = " + senderPid ;
					System.out.println(queryString);
						MyJDBCDriver.getResultSet(queryString);
						//" AND s.pid = 10 and LOWER(symname) = 'cough'";
						
				        if(MyJDBCDriver.resultSet != null){
				        	while(MyJDBCDriver.resultSet.next()){
				        		 int msgCount = Integer.parseInt(MyJDBCDriver.resultSet.getString("msgcount"));
				        		 msg_SenderIDMap.put(senderPid, msgCount);
				        	}
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				//found = false;
			}
		return msg_SenderIDMap;
	}
}
