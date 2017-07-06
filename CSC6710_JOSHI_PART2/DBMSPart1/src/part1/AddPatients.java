package part1;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AddPatients
 */
public class AddPatients extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String valPid;
	private String valFName;
	private String valLName;
	private String valGender;
	private String valAge;
	private String valAddress;
	private String valEmail;
	private String myResponse;
	private static int pID;
	private String insertStatus;
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		insertStatus= TextBank.ENTRDATAPT;
		TextBank.INSERTQUERYPT =
				 "INSERT INTO PATIENTS(firstname ,lastname, address , age , email, gender ) VALUES( ";
		getNewPatientID(req,resp);
		getFormData(req);
        req.setAttribute("insertStatus", insertStatus);
		resp.getWriter().print(insertStatus);
		System.out.println(insertStatus);
		req.getRequestDispatcher("/AddPt.jsp").forward(req, resp);
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
				insertStatus = TextBank.UNABLETOINSERTPT;
				try {
					MyJDBCDriver .getConn();
					MyJDBCDriver .getStatement();
					MyJDBCDriver.getResultSetForExecuteUpdate(TextBank.INSERTQUERYPT);
					if(MyJDBCDriver.queryOP >= 1 ){
						insertStatus = TextBank.ABLETOINSERTPT;
						pID++;
			        	setPID(pID,req);
					}
				} catch (Exception e) {
					GetErrorMsg.printError(e);
					insertStatus = TextBank.UNABLETOINSERTPT;
				}finally{
						MyJDBCDriver.closeAll();
				}
			}
	}

	private void getNewPatientID(HttpServletRequest req, HttpServletResponse resp) {
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
					MyJDBCDriver.getResultSet(TextBank.GETMAXOFPID);
			        if(MyJDBCDriver.resultSet != null){
			        	while(MyJDBCDriver.resultSet.next()){
			        		pID = Integer.parseInt(MyJDBCDriver.resultSet.getString("NEWPID"));
			        	}
			        	pID++;
			        	setPID(pID,req);
			        }
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
						MyJDBCDriver.closeAll();
				   }
			}
	}


	private void setPID(int pID2, HttpServletRequest req) {
		// TODO Auto-generated method stub
    	String  str = String.format("%03d", (Object)pID);
        req.setAttribute("newPid", str);
	}

	private void getFormData(HttpServletRequest req) {
		// TODO Auto-generated method stub
		valPid = req.getParameter("txtpid");
		valGender = req.getParameter("radioGender");
		valAge = req.getParameter("txtage");
		
		valFName = req.getParameter("txtfname");
		valLName = req.getParameter("txtlname");
		valEmail = req.getParameter("txtemail");
		valAddress = req.getParameter("txtaddress");
		if(valFName !="" && valFName !=null && 
				valLName != "" && valLName != null
				&& valEmail != "" && valEmail != null
				&&  valAddress != "" && valEmail != null
				&& valAge != "" && valAge != null){
			//"INSERT INTO PATIENTS(firstname ,lastname, address , age , email ) VALUES( ";
			TextBank.INSERTQUERYPT += "'"+ valFName +"'" + ",'"+ valLName +"'" +
									 ",'"+ valAddress +"'" + ","+ valAge + ",'"+ valEmail + "'"+
									 ",'"+ valGender  +"')"  ;
			System.out.println(TextBank.INSERTQUERYPT );
			insertValues(req);
		}
		else{
			insertStatus= TextBank.ENTRDATAPT;
		}
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
					//modifyQuery(colName, val);
		    	}
		    }
		    return temp;
	}
	
	protected void checkValue(String colName, String val){
		 boolean isNumb = false;
		 int temp = 0;
		    try{
				if(val != ""){
					//modifyQuery(colName,val);
				}
		    }catch(NumberFormatException e){
		    	isNumb = false;
		    	temp = -1;
		    }
	}
	
}
