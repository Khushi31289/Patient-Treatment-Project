package course;

import java.io.IOException;
import java.util.Collection;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Login
 */
@WebServlet("/csc5750/fy6259/project2/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String INVALID_USER_MSG = "Incorrect Username and/or Password was provided";
	private Set<User> listOfUsers = new LinkedHashSet<User>();
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(); // session object
		//comparing the users and checking if its a registered user.
		Set<User> userSet = new LinkedHashSet<User>();
		System.out.println(request.getAttribute("ListOfUsers"));
		String _action = null;
		
		_action = (String)request.getParameter("action");
		System.out.println("Action: "+_action );
		
		//Checking action parameter
		if( _action == null ){
			session = request.getSession(); // session object
			User logUser = null;
			logUser = (User) session.getAttribute("loggedInUser");
			String str = "";
			if(logUser == null){
				boolean foundUser = authenticateUser(request, response);
				if(foundUser){
					//storing information into httpsession object
					//then redirecting to the post page
					User newUser = new User();
					Iterator<User> i1 = listOfUsers.iterator();
					String emailId = request.getParameter("emailId");
					while(i1.hasNext()){
						User objUser = i1.next();
						if(emailId.equals(objUser.getEmail1())){
							newUser = objUser;
							break;
						}
					}
					session.setAttribute("loggedInUser", newUser);
					request.setAttribute("showMsg", "Login successful");
					System.out.println("Emailid = "+ newUser.getEmail1());
					System.out.println("Password = "+ newUser.getPassword1());
					//Checking if user is Admin
					if(newUser.getEmail1().equals("Admin") && 
							newUser.getPassword1().equals("1234")){
						System.out.println("***********  Admin  ***************");
						request.getRequestDispatcher("/fy6259/project2/AdminController").forward(request, response);
					}else{
					request.getRequestDispatcher("/fy6259/project2/Posts").forward(request, response);
					}
				}else{
					request.setAttribute("showMsg", INVALID_USER_MSG);
					request.getRequestDispatcher("/login.jsp").forward(request, response);
				}
			}else{
				request.setAttribute("showMsg", "Logged in as "+ logUser.getFirstName() +" "+ logUser.getLastName()
														+ ", logout from posts page if you are not Admin "+
						" else Logout from Admin Page.");
				request.getRequestDispatcher("/login.jsp").forward(request, response);
			}
		}else if(_action.equals("logout")){
			//If action is logout then logout user and remove the user from the HTTP session
			session.removeAttribute("loggedInUser"); 
			request.setAttribute("showMsg", "");
			request.removeAttribute("showMsg");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
	}

	private boolean authenticateUser(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		boolean foundUser = false;
		String emailId = request.getParameter("emailId");
		String password = request.getParameter("password1");
		if(notNullAndBlankCheck(emailId) && 
				notNullAndBlankCheck(password) ){
			Map<String,String> userDetailsMap = new HashMap<String,String>();
			//List<Integer> listOfPatients = new ArrayList<Integer>();
			//userDetailsMap = getMessageCount(_pids);
			userDetailsMap = checkForRegisteredUser(request, response);
			if(userDetailsMap.size() > 0){
				if(userDetailsMap.containsKey(emailId)){
					if(password.equals(userDetailsMap.get(emailId))){
						foundUser = true;
					}else{
						foundUser = false;
					}
				}else{
					foundUser = false;
				}
			}else{
				foundUser = false;
			}
		}else{
			foundUser= false;
		}
		return foundUser;
	}

	//Checking in Database to verify if user already exists. checking in Users table
	private Map<String, String> checkForRegisteredUser(HttpServletRequest req, HttpServletResponse resp) {
		// TODO Auto-generated method stub
		Map<String,String> userDetailsMap = new HashMap<String,String>();
		
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
					String query = TextBank.SELECT_USERS_QUERY;
					MyJDBCDriver.getResultSet(query);
			        if(MyJDBCDriver.resultSet != null){
			        	while(MyJDBCDriver.resultSet.next()){
			        		String fName = MyJDBCDriver.resultSet.getString("firstname");
			        		String lName = MyJDBCDriver.resultSet.getString("lastname");
			        		String email =	MyJDBCDriver.resultSet.getString("email");
			        		String pwd = MyJDBCDriver.resultSet.getString("password");
			        		String areaCode = MyJDBCDriver.resultSet.getString("areaCode");
			        		String prefix = MyJDBCDriver.resultSet.getString("prefix");
			        		String suffix = MyJDBCDriver.resultSet.getString("suffix");
			        		userDetailsMap.put(email, pwd);
			        		User newUser = new User();
			        		newUser.setFirstName(fName);
			        		newUser.setLastName(lName);
			        		newUser.setEmail1(email);
			        		newUser.setPassword1(pwd);
			        		newUser.setAreaCode(areaCode);
			        		newUser.setPrefix(prefix);
			        		newUser.setSuffix(suffix);
			        		listOfUsers.add(newUser);
			        	}
			        }
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
						MyJDBCDriver.closeAll();
				   }
			}
		return userDetailsMap;
	}
	
	private boolean notNullAndBlankCheck(String name){
		if( name != null &&
				!name.isEmpty()){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
