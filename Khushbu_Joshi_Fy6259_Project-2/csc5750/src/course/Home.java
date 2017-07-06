package course;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.ws.Response;

import com.mysql.jdbc.Statement;

/**
 * Servlet implementation class Home
 */
@WebServlet("/csc5750/fy6259/project2/Home")
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static String register = "register"; 
	private final static String login = "login";
	 static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	 static final String DB_URL = "jdbc:mysql://localhost:3306/sampledb";
	 static final String TXTUNM = "sampledb";
	 static final String TXTPWD = "pass1234";
	 static final String UNM = "root";
	 static final String PWD = "pass1234";
	 private static ArrayList<String> listOfQueries = null;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
													throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().println();
		boolean driver = true;
		try {
				Class.forName(JDBC_DRIVER);
				System.out.println("MySQL JDBC Driver Registered!");
				
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			driver =  false;
			response.getWriter().println("Exception occured :" 
			+ e.getMessage()+"\n" +e.getClass() );
		}
		if(driver ==  true){
			boolean exeSuccess= true;
			Connection conn = null; Statement stmt = null;
			try {
				conn = DriverManager.getConnection(DB_URL,UNM, TXTPWD);
				ArrayList<String> queries_01;
				String pathname_01 = "";
				/*File myFile = new File("/tempTables.sql");
				System.out.println(myFile.isFile());
				if(myFile.isFile()){
					pathname_01 = myFile.getAbsolutePath();
					System.out.println();
				}*/
				//	SQL script to create tables.				
				        pathname_01 = "C:/Users/khush/workspace/csc5750/WebContent/project2.sql";
				        System.out.println("inside mainLoader.initializeDatabase()");
				        //resp.getWriter().println("\n****************");
				        SQLReader objReader = new SQLReader();
				        queries_01 = createQueries(pathname_01);
				        ArrayList<ResultSet> RSList_01 = new ArrayList<ResultSet>();
				        stmt = (Statement) conn.createStatement();
				        for(String query: queries_01)
				        {
				        	int x= stmt.executeUpdate(query);
				        }
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}finally{
			      //finally block used to close resources
			      try{
			         if(stmt!=null)
			            conn.close();
			      }catch(SQLException ex){
			    	  response.getWriter().println(ex.getMessage());
			      }// do nothing
			      try{
			         if(conn!=null)
			            conn.close();
			      }catch(SQLException ex){
			    	  response.getWriter().println(ex.getMessage());
			      }//end finally try
			   }
			if(exeSuccess == true){
				response.getWriter().println(TextBank.INITSUCCESS.toUpperCase());
			}
			else{
				response.getWriter().println("UN" + TextBank.INITSUCCESS.toUpperCase());
			}
		}

		//Redirecting to the next page
		String _redirect = request.getParameter("redirect");
		if(_redirect != null && !_redirect.equals("")){
			if(_redirect.equals(register)){
				HttpSession session = request.getSession(); 
				if(session.getAttribute("loggedInUser") != null){
					User logUser = (User) session.getAttribute("loggedInUser");
					String loginMsg = "You are already a register User.";
					request.getRequestDispatcher("/registerPage.jsp?loginmessage=loginMsg").forward(request, response);
				}else{
					String loginMsg ="";
					request.getRequestDispatcher("/registerPage.jsp?loginmessage=loginMsg").forward(request, response);
				}
			}else if(_redirect.equals(login)){
				request.getRequestDispatcher("/login.jsp").forward(request, response);
			}
		}
	}

	/*
	 * ATTENTION: SQL file must not contain column names, etc. including comment signs (#, --, /* etc.)
	 *          like e.g. a.'#rows' etc. because every characters after # or -- in a line are filtered 
	 *          out of the query string the same is true for every characters surrounded by /* and */
	/**/
	private  ArrayList<String> createQueries(String path)  
    { 
    	System.out.println("Inside SQLReader.createQueries()");
        String queryLine =      new String();
        StringBuffer sBuffer =  new StringBuffer();
        listOfQueries =         new ArrayList<String>();
         
        try 
        {  
            FileReader fr =     new FileReader(new File(path));       
            BufferedReader br = new BufferedReader(fr);  
       
            //read the SQL file line by line
            while((queryLine = br.readLine()) != null)  
            {  
                // ignore comments beginning with #
                int indexOfCommentSign = queryLine.indexOf('#');
                if(indexOfCommentSign != -1)
                {
                    if(queryLine.startsWith("#"))
                    {
                        queryLine = new String("");
                    }
                    else
                        queryLine = new String(queryLine.substring(0, indexOfCommentSign-1));
                }
                // ignore comments beginning with --
                indexOfCommentSign = queryLine.indexOf("--");
                if(indexOfCommentSign != -1)
                {
                    if(queryLine.startsWith("--"))
                    {
                        queryLine = new String("");
                    }
                    else
                        queryLine = new String(queryLine.substring(0, indexOfCommentSign-1));
                }
                // ignore comments surrounded by /* */
                indexOfCommentSign = queryLine.indexOf("/*");
                if(indexOfCommentSign != -1)
                {
                    if(queryLine.startsWith("#"))
                    {
                        queryLine = new String("");
                    }
                    else
                        queryLine = new String(queryLine.substring(0, indexOfCommentSign-1));
                     
                    sBuffer.append(queryLine + " "); 
                    // ignore all characters within the comment
                    do
                    {
                        queryLine = br.readLine();
                    }
                    while(queryLine != null && !queryLine.contains("*/"));
                    indexOfCommentSign = queryLine.indexOf("*/");
                    if(indexOfCommentSign != -1)
                    {
                        if(queryLine.endsWith("*/"))
                        {
                            queryLine = new String("");
                        }
                        else
                            queryLine = new String(queryLine.substring(indexOfCommentSign+2, 
                            		queryLine.length()-1));
                    }
                }
                 
                //  the + " " is necessary, because otherwise the content before and after a line break are concatenated
                // like e.g. a.xyz FROM becomes a.xyzFROM otherwise and can not be executed 
                if(queryLine != null)
                    sBuffer.append(queryLine + " ");  
            }  
            br.close();
             
            // here is our splitter ! We use ";" as a delimiter for each request 
            String[] splittedQueries = sBuffer.toString().split(";");
             
            // filter out empty statements
            for(int i = 0; i<splittedQueries.length; i++)  
            {
                if(!splittedQueries[i].trim().equals("") && !splittedQueries[i].trim().equals("\t"))  
                {
                    listOfQueries.add(new String(splittedQueries[i]));
                }
            }
        }  
        catch(Exception e)  
        {  
            //GetErrorMsg.printError(e);  
        }
        System.out.println("Before returning SQLReader.createQueries()");
        return listOfQueries;
    } 
	
}
