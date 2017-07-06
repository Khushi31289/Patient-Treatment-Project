package course;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

/**
 * Servlet implementation class Register
 * 
 */
@WebServlet("/csc5750/fy6259/project2/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String valFName;// = request.getParameter("firstName");
	private String valLName;// = request.getParameter("lastName");
	private String valEmail1;// = request.getParameter("email1");
	
	private String valEmail2;// = request.getParameter("email2");
	private String valAreaCode;// = request.getParameter("areaCode");
	private String valPrefix;// = request.getParameter("prefix");
	private String valSuffix;// = request.getParameter("suffix");
	private String valPassword1;// = request.getParameter("password1");
	private String valPassword2;// = request.getParameter("password2");
	private String insertStatus;
	private boolean validationStatus;
	private static String WELCOME_MSG = "Welcome to the course website..!";
	private boolean allFieldsCorrect;
	private final String PK_EXCEPTION= "com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException";
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		validationStatus = false;
		allFieldsCorrect = true;
		response.getWriter().append("Served at: ").append(request.getContextPath());
		setAttributeValues(request);
		System.out.println("Fname : " + valFName);
		System.out.println("Lname : " + valLName);
		System.out.println("Email1 : " + valEmail1);
		System.out.println("Email2 : " + valEmail2);
		System.out.println("Password1 : " + valPassword1);
		System.out.println("Pwd2 : " + valPassword2);
		System.out.println("Acode : " + valAreaCode);
		System.out.println("Prefix : " + valPrefix);
		System.out.println("suffix : " + valSuffix);
		User newUser = null; 
		newUser = setUserValues(request, response);

		validateValues(request);
		if(allFieldsCorrect){
			String query = TextBank.INSERT_USER_QUERY;
			//('Khushbu', 'Joshi', 'khush123@gmail.com', '+1', '313571', '4380', 'snow123');
			query = query += "('" + valFName + "'" + ", '" + valLName + "', '" 
								+ valEmail1 + "'," + valAreaCode +" ,"
								+ valPrefix +" ," + valSuffix + " , '"
								+ valPassword1 + "' )";
			boolean _status = insertValues(request, query);
			System.out.println(_status);
			if(_status ){
					Set<User> userSet = new LinkedHashSet<User>();
					if(null != request.getAttribute("ListOfUsers") 
							&& !("").equals(request.getAttribute("ListOfUsers"))){
						userSet.addAll((Collection<? extends User>) request.getAttribute("ListOfUsers"));
						userSet.add(newUser);
						request.setAttribute("ListOfUsers",userSet);
					}else{
						userSet.add(newUser);
						request.setAttribute("ListOfUsers",userSet);
					}
					
					if(userSet.size() >= 1 && userSet != null){
						request.setAttribute("ListOfUsers",newUser);
						Iterator<User> i1 = userSet.iterator();
						System.out.println("Size: " + userSet.size());
						while(i1.hasNext()){
							User thisUser = i1.next();
							System.out.println(thisUser.getFirstName());
							System.out.println(thisUser.getLastName());
							System.out.println(thisUser.getEmail1());
							System.out.println(thisUser.getPassword1());
							System.out.println(thisUser.getAreaCode());
							System.out.println(thisUser.getPrefix());
							System.out.println(thisUser.getSuffix());
						}
					}
					request.setAttribute("showMsg",WELCOME_MSG);
					request.getRequestDispatcher("/login.jsp").forward(request, response);
			}else{
				request.setAttribute("sEmail1", "error");
				request.setAttribute("sEmail2", "error");
				request.setAttribute("email1", "");
				request.setAttribute("email2", "");
				request.getRequestDispatcher("/registerPage.jsp").forward(request, response);
			}
		}else{
			request.getRequestDispatcher("/registerPage.jsp").forward(request, response);
		}
		
		//Putting user object into session
		/*
		HttpSession session = request.getSession();
		userListSessionObj  = session.getAttribute("userList");
		if(userListSessionObj != null && !userListSessionObj.equals("")){
			session.setAttribute("userList", userSet);
		}else{
			if(newUser != null){
				Set<User> userSet = new LinkedHashSet<User>();
				userSet.add(newUser);
				
				//userSet.
				Object obj = (Object)userSet;
				session.setAttribute("userList", obj);
			}
		}*/
	}
	
	
	private void validateValues(HttpServletRequest request) {
		// TODO Auto-generated method stub
		if(!validateFirstLastName(valFName)){
			request.setAttribute("sFName", "error");
			allFieldsCorrect = false;
		}else{
			request.setAttribute("sFName", "");
			request.setAttribute( "firstName",valFName);
		}
		
		if(!validateFirstLastName(valLName)){
			request.setAttribute("sLName", "error");
			allFieldsCorrect = false;
		}else{
			request.setAttribute("sLName", "");
			request.setAttribute( "lastName",valLName);
		}
		
		validateTelephoneNo(request);
		validateEmailAddress(request);
		validatePassword(valPassword1,valPassword2, request);
		
	}
	
	private void validateEmailAddress(HttpServletRequest request){
		boolean validEmail = true;
		if(notNullAndBlankCheck(valEmail1)){
			request.setAttribute("sEmail1", "");
			request.setAttribute("email1", valEmail1);
		}else{
			validEmail = false;
			allFieldsCorrect = false;
			request.setAttribute("sEmail1", "error");
			request.setAttribute("email1", "");
		}
		
		if(notNullAndBlankCheck(valEmail2)){
			request.setAttribute("sEmail2", "");
			request.setAttribute("email2", valEmail2);
		}else{
			request.setAttribute("sEmail2", "error");
			request.setAttribute("email2", "");
			validEmail = false;
			allFieldsCorrect = false;
		}
		
		if(notNullAndBlankCheck(valEmail1) &&
				notNullAndBlankCheck(valEmail2) &&
				validEmail){
			if(!valEmail1.equals(valEmail2)){
				request.setAttribute("sEmail2", "error");
				request.setAttribute("email2", "");
				allFieldsCorrect = false;
			}else{
				request.setAttribute("sEmail1", "");
				request.setAttribute("sEmail2", "");
				request.setAttribute("email1", valEmail1);
				request.setAttribute("email2", valEmail2);
			}
		}
	}
	
	private boolean validateTelephoneNo(HttpServletRequest request) {
		// TODO Auto-generated method stub
		boolean valid = false;
		boolean validAC = false;
		boolean validPrefix = false;
		boolean validSuffix = false;
		System.out.println("Prefix: " + valPrefix);
		System.out.println("Suffix: " + valSuffix);
		System.out.println("Area code: " + valAreaCode);
		
    	if( notNullAndBlankCheck(valAreaCode) &&
    			checkIsDigit(valAreaCode)){
    		valid = true;
    		request.setAttribute( "areaCode",valAreaCode);
    		validAC = true;
    	}else{
    		valid = false;
    		validAC = false;
    		allFieldsCorrect = false;
    		request.setAttribute("sPhone", "error");
    		request.setAttribute( "areaCode","");
    	}
    	//if(valid){
    		if(notNullAndBlankCheck(valPrefix) &&
        			checkIsDigit(valPrefix)){
    			valid = true;
    			validPrefix = true;
    			request.setAttribute( "prefix",valPrefix);	
    		}else{
        		request.setAttribute("sPhone", "error");
        		request.setAttribute( "prefix","");
        		valid = false;
        		allFieldsCorrect = false;
        		validPrefix = false;
    		}
    	/*}else{
    		request.setAttribute("sPhone", "error");
    		valid = false;
    		allFieldsCorrect = false;
		}*/
    	
    	//if(valid){
    		if(notNullAndBlankCheck(valSuffix) &&
        			checkIsDigit(valSuffix)){
    			valid = true;
    			request.setAttribute("sPhone", "");
    			request.setAttribute( "suffix",valSuffix);
    			validSuffix= true;
    		}else{
        		request.setAttribute("sPhone", "error");
     			request.setAttribute( "suffix","");
        		valid = false;
        		allFieldsCorrect = false;
        		validSuffix =false;
    		}
    	
    	if(validAC && validSuffix && validPrefix){
    		request.setAttribute("sPhone", "");
    	}else{
    		request.setAttribute("sPhone", "error");
    		valid = false;
    		allFieldsCorrect = false;
		}
    	return false;
	}

	public boolean checkIsDigit(String strText){
		boolean found = false;
		for(char c : strText.toCharArray()){
	    	found = Character.isDigit(c);
	        if( !found){
	            break;                
	        }
	    }
		return found;
	}

	private boolean validateFirstLastName(String name) {
		// TODO Auto-generated method stub
		//Implementing null check, empty check
		//Checking if contains space then not valid name
		//Checking if contains digit then not valid name
		if(notNullAndBlankCheck(name) &&
				!name.trim().contains(" ")){
		    boolean found = true;
		    for(char c : name.toCharArray()){
		    	found = Character.isLetter(c);
		        if( !found){
		            break;                
		        }
		    }
		    return found;
		}else{
			return false;
		}
	}

	private boolean notNullAndBlankCheck(String name){
		if( name != null &&
				!name.isEmpty()){
			return true;
		}else{
			return false;
		}
	}
	
	private void validatePassword(String pwd1, String pwd2, HttpServletRequest request){
		if( notNullAndBlankCheck(pwd1) && validPasswordCheck(pwd1)){
			request.setAttribute("sPwd1", "");
			request.setAttribute("password1",valPassword1);
		}else{
			request.setAttribute("sPwd1", "error");
			allFieldsCorrect = false;
		}
		
		if(notNullAndBlankCheck(pwd2) && validPasswordCheck(pwd2)){
			request.setAttribute("sPwd2", "");
		}else{
			request.setAttribute("sPwd2", "error");
			allFieldsCorrect = false;
		}
	
		if(notNullAndBlankCheck(pwd1) && notNullAndBlankCheck(pwd2)){
			boolean checkPwd1 = false , checkPwd2 = false;
			if(validPasswordCheck(pwd1)){
				request.setAttribute("sPwd1", "");
				request.setAttribute("password1",valPassword1);
				checkPwd1 = true;
			}else{
				request.setAttribute("sPwd1", "error");
				allFieldsCorrect = false;
				checkPwd1 = false;
			}

			if(validPasswordCheck(pwd2)){
				request.setAttribute("sPwd2", "");
				request.setAttribute("password2",valPassword2);
				checkPwd2 = true;
			}else{
				request.setAttribute("sPwd2", "error");
				checkPwd2 = false;
				allFieldsCorrect = false;
			}
			
			if(checkPwd1 && checkPwd2){
				if(pwd1.equals(pwd2)){
					request.setAttribute("sPwd1", "");
					request.setAttribute("sPwd2", "");
					request.setAttribute("password1",valPassword1);
					request.setAttribute("password2",valPassword2);
				}else{
					request.setAttribute("sPwd2", "error");
					allFieldsCorrect = false;
				}
			}
		}else{
			return;
		}
	}

	private boolean validPasswordCheck(String pwd){
	    boolean found = true;
	    for(char c : pwd.toCharArray()){
	    	found = Character.isLetter(c);
	        if( found){
	            break;                
	        }
	    }
	    
	    if(found){
	    	for(char c : pwd.toCharArray()){
		    	found = Character.isDigit(c);
		        if( found){
		            break;                
		        }
		    }
	    	return found;
	    }else{
	    	return false;
	    }
	}
	
	private void setAttributeValues(HttpServletRequest request) {
		// TODO Auto-generated method stub
		request.setAttribute("sFName", "");
		request.setAttribute("sLName", "");
		request.setAttribute("sEmail1", "");
		request.setAttribute("sEmail2", "");
		request.setAttribute("sPwd1", "");
		request.setAttribute("sPwd2", "");
		request.setAttribute("sPhone", "");
		/*request.setAttribute("sACode", "");
		request.setAttribute("sPrefix", "");
		request.setAttribute("sSuffix", "");*/
	}

	private boolean insertValues(HttpServletRequest req, String query) {
		// TODO Auto-generated method stub
		boolean driver= true;
		boolean insertSuccess = false;
		System.out.println(query);
		try {
				Class.forName(TextBank.JDBC_DRIVER);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
				driver =  false;
				//GetErrorMsg.printError(e);
		}
		if(driver ==  true){
				System.out.println("Inside driver for connection");
				try {
					MyJDBCDriver .getConn();
					MyJDBCDriver .getStatement();
					MyJDBCDriver.getResultSetForExecuteUpdate(query);
					if(MyJDBCDriver.queryOP >= 1 ){
						insertSuccess = true;
					}
				} catch (Exception e) {
					//GetErrorMsg.printError(e);
					//com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException: 
					//Duplicate entry 'a@a.com' for key 'PRIMARY'
					if(e.getClass().toString().equals(PK_EXCEPTION)){
						insertSuccess= false;
					}
					insertSuccess= false;
				}finally{
						MyJDBCDriver.closeAll();
				}
			}
		return insertSuccess;
	}
	
	
	private User setUserValues(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		User newUser = new User();
		valFName = request.getParameter("firstName");
		valLName = request.getParameter("lastName");
		valEmail1 = request.getParameter("email1");
		
		valEmail2 = request.getParameter("email2");
		valAreaCode = request.getParameter("areaCode");
		valPrefix = request.getParameter("prefix");
		valSuffix = request.getParameter("suffix");
		valPassword1 = request.getParameter("password1");
		valPassword2 = request.getParameter("password2");
/*		if(valFName != null && valLName != null && valEmail1 != null && valEmail2!= null 
				&& valAreaCode != null && valPrefix != null && valSuffix != null && valPassword1 != null && 
				valPassword2 != null ){*/
		newUser.setFirstName(valFName);
		newUser.setLastName(valLName);
		newUser.setEmail1(valEmail1);
		newUser.setEmail2(valEmail2);
		newUser.setAreaCode(valAreaCode);
		newUser.setPrefix(valPrefix);
		newUser.setSuffix(valSuffix); 
		newUser.setPassword1(valPassword1);
		newUser.setPassword2(valPassword2);
		
		return newUser;
		/*}else{
			return null;
		}*/
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
