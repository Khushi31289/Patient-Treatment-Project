package part1;

import java.io.IOException;
import java.sql.*;

import javax.servlet.http.*;
import javax.servlet.*;
import part1.TextBank;

public class SearchPatients extends HttpServlet{
	String valPid;
	String valFName;
	String valLName;
	String valGender;
	String valAge;
	String valAddress;
	String valEmail;
	String myResponse;
	static int cnt;
	static boolean ISEXEC = false;
	//static final String errMessage="These fields can accept only numeric values,re-enter";
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		TextBank.SELECTQUERY = "Select * from patients";
		ISEXEC = false;
		//clearAll();
		getFormData(req);
		String err = "";
		myResponse = "";
		cnt=0;
			//if(checkNumber("pid",valPid) > 0){
			if(ISEXEC == false){
				boolean driver = true;
				if(valPid != "" ){
					if(checkNumber("pid",valPid ) < 0){
						err += "\n"+TextBank.errMsgIdRange;
						printErrorMsg(resp,err);
					}
				}
				if(valAge != "" ){
						if(checkNumber("age",valAge) < 0){
							err += "\n"+TextBank.errMsgAgeRange;
							printErrorMsg(resp,err);
					}
				}
				checkValue("gender",valGender);
				checkValue("firstname",valFName);
				checkValue("lastname",valLName);
				checkValue("address",valAddress);
				checkValue("email",valEmail);
				
				if(ISEXEC == false){
					if(!TextBank.SELECTQUERY.endsWith(";")){
						TextBank.SELECTQUERY += ";";
						ISEXEC= true;
					}
				}
				try {
						Class.forName(TextBank.JDBC_DRIVER);
						resp.getWriter().println("Inside JDBC Driver");
						System.out.println("MySQL JDBC Driver Registered!");
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					driver =  false;
					resp.getWriter().println("Exception occured :" 
					+ e.getMessage()+"\n" +e.getClass() );
				}
				if(driver ==  true){
					System.out.println("Inside driver for connection");
					try {
						MyJDBCDriver .getConn();
						MyJDBCDriver .getStatement();
						MyJDBCDriver.getResultSet(TextBank.SELECTQUERY);
				        if(MyJDBCDriver.resultSet != null){
					        displayDataInTable(MyJDBCDriver.resultSet,resp);
				        }
				        req.setAttribute("var3", myResponse);
						req.getRequestDispatcher("/SearchPt.jsp").forward(req, resp);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}finally{
							MyJDBCDriver.closeAll();
					   }
				}
			}else{
			err = TextBank.errMsgIdRange;
			printErrorMsg(resp,err);
		}
	}

	private void getFormData(HttpServletRequest req) {
		// TODO Auto-generated method stub
		valPid = req.getParameter("txtpid");
		valFName = req.getParameter("txtfname");
		valLName = req.getParameter("txtlname");
		valGender = req.getParameter("radioGender");
		valAge = req.getParameter("txtage");
		valEmail = req.getParameter("txtemail");
		valAddress = req.getParameter("txtaddress");
	}

	private void printErrorMsg(HttpServletResponse resp, String error) {
		// TODO Auto-generated method stub
		try {
			resp.getWriter().print(error);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

	protected int checkNumber(String colName, String val){
		 boolean isNumb = false;
		 int temp = 0;
		    try{
		        temp = Integer.parseInt(val);
		    }catch(NumberFormatException e){
		    	isNumb = false;
		    	temp = -1;
		    }finally{
		    	if(temp > 0){
					modifyQuery(colName, val,cnt);
					cnt++;
		    	}
		    }
		    return temp;
	}
	
	protected void checkValue(String colName, String val){
		 boolean isNumb = false;
		 int temp = 0;
		    try{
				if(val != "" && val != null){
					modifyQuery(colName,val,cnt);
					cnt++;
				}
		    }catch(NumberFormatException e){
		    	isNumb = false;
		    	temp = -1;
		    }
	}
	
	private void modifyQuery(String colName, Object val, int cnt) {
		// TODO Auto-generated method stub
		if(!TextBank.SELECTQUERY.endsWith(";")){
				if(cnt == 0){
					TextBank.SELECTQUERY += TextBank._WHERE;
				}else{
					TextBank.SELECTQUERY += TextBank._AND;
				}
				if(colName.equals("pid") ){
					//TextBank.SELECTQUERY += TextBank._WHERE;
					TextBank.SELECTQUERY += colName +TextBank._EQNUM+
							Integer.parseInt(val.toString());
				}
				else if(colName.equals("age")){
					//TextBank.SELECTQUERY += TextBank._AND;
					TextBank.SELECTQUERY += colName + TextBank._EQNUM +
							Integer.parseInt(val.toString());
				}
				else {
					//TextBank.SELECTQUERY += colName + TextBank._EQSTR1 + 
						//val.toString() + TextBank._EQSTR2;
					
					TextBank.SELECTQUERY += colName + TextBank._LIKESTR +TextBank._LIKESTR1 + 
							val.toString() + TextBank._LIKESTR2;
				}
		}
		System.out.println(TextBank.SELECTQUERY);
	}
}
