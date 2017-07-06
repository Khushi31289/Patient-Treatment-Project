package course;

import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class AdminController
 */
@WebServlet("/csc5750/fy6259/project2/AdminController")
public class AdminController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String str = null;	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(); // session object
		User newUser = new User();
		newUser = (User) session.getAttribute("loggedInUser");
		 str = "";
		if(newUser != null){
			//Ensuring whether user is admin or not
			System.out.println(newUser.getEmail1());
			if(newUser.getEmail1().equals("Admin")){
				//Only admin user can view this page
				
				str = newUser.getFirstName() + " " + newUser.getLastName();
				request.setAttribute("LoggedInUserName", str);
				request.setAttribute("LoggedInUserEmailId", newUser.getEmail1());
				if(null != request.getParameter("addComment")){
					String queryParam = request.getParameter("addComment");
					if(queryParam != null && queryParam.equals("yes")){
						//System.out.println("Comment found");
						String commentContent = request.getParameter("commentContent");
						if(!commentContent.equals("") && !commentContent.isEmpty()){
							int postid = Integer.parseInt(request.getParameter("postid"));
							System.out.println("Post id:" + postid);
							System.out.println("Comment msg:" + commentContent);
							insertCommentsIntoDB(request,postid,commentContent);
							request.removeAttribute("addComment");
							//request.removeAttribute("");
						}
					}
				}
				
				
				//adding a new post
				if(null != request.getParameter("addPost")){
					String queryParam = request.getParameter("addPost");
					if(queryParam != null && queryParam.equals("yes")){
						//System.out.println("Comment found");
						String postContent = request.getParameter("postContent");
						String postTitle = request.getParameter("postTitle");
						
							System.out.println("Comment Post:" + postContent);
							System.out.println("Post Title:" + postTitle);
							insertPostsIntoDB(request,postTitle,postContent,str,newUser.getEmail1());
							request.removeAttribute("addPost");
							//request.removeAttribute("");
					}
				}
				
				//Removing the Post and the comments associated with the post
				//adding a new post
				if(null != request.getParameter("remove")){
					String removePost = request.getParameter("remove");
					if(removePost != null && removePost.equals("yes")){
						//System.out.println("Comment found");
							String postid = request.getParameter("postid");
							if(postid != null && !postid.isEmpty()){
								System.out.println("Remove Post id:" + postid);
								request.removeAttribute("remove");
								request.removeAttribute("postid");
								int pid = Integer.parseInt(postid);
								deletePostAndComments(pid);
							}
					}
				}
				
				//Removing only a specific comment
				if(null != request.getParameter("removecomment")){
					String removecomment = request.getParameter("removecomment");
					if(removecomment != null && removecomment.equals("yes")){
						//System.out.println("Comment found");
							String commentid = request.getParameter("rcommentid");
							if(commentid != null && !commentid.isEmpty()){
								System.out.println("Remove commentid:" + commentid);
								request.removeAttribute("removecomment");
								request.removeAttribute("rcommentid");
								int cid = Integer.parseInt(commentid);
								deleteComments(cid);
							}
					}
				}
				
				
				Set<Post> mapOfPosts = new LinkedHashSet<Post>();
				mapOfPosts.addAll(getAllPostsFromDB());
				
				if(!mapOfPosts.isEmpty() && mapOfPosts.size() > 0){
					request.setAttribute("mapOfPosts", mapOfPosts);
					
				}else{
					request.setAttribute("mapOfPosts", null);
				}
				
				Set<Comment> mapOfComments = new LinkedHashSet<Comment>();
				mapOfComments .addAll(getAllCommetsFromDB());
				
				if(!mapOfComments .isEmpty() && mapOfComments .size() > 0){
					request.setAttribute("mapOfComments", mapOfComments );
				}else{
					request.setAttribute("mapOfComments", null);
				}
				//Redirect to AdminView.jsp Page
				request.getRequestDispatcher("/AdminView.jsp").forward(request, response);
			}else{
				request.getRequestDispatcher("/NotAnAdmin.jsp").forward(request, response);
			}
		}else{
			request.getRequestDispatcher("/fy6259/project2/Home?redirect=login").forward(request, response);
		}
	}

	
	

	private void deleteComments(int cid) {
		// TODO Auto-generated method stub
		boolean driver= true;
		try {
				Class.forName(TextBank.JDBC_DRIVER);
		} catch (ClassNotFoundException e) {
				driver =  false;
				GetErrorMsg.printError(e);
		}
		if(driver ==  true){
				System.out.println("Inside driver for connection");
				try {
					MyJDBCDriver .getConn();
					MyJDBCDriver .getStatement();
					String deleteQuery = TextBank.DELETE_COMMENT_QUERY;
					deleteQuery += cid;
					System.out.println(deleteQuery);
					MyJDBCDriver.getResultSetForExecuteUpdate(deleteQuery);
					if(MyJDBCDriver.queryOP >= 1 ){
						System.out.println("Posts & Comments deleted successfully..!");
					}
				} catch (Exception e) {
					GetErrorMsg.printError(e);
				}finally{
						MyJDBCDriver.closeAll();
				}
		}
	}




	private void deletePostAndComments(int pid) {
		// TODO Auto-generated method stub
		boolean driver= true;
		try {
				Class.forName(TextBank.JDBC_DRIVER);
		} catch (ClassNotFoundException e) {
				driver =  false;
				GetErrorMsg.printError(e);
		}
		if(driver ==  true){
				System.out.println("Inside driver for connection");
				try {
					MyJDBCDriver .getConn();
					MyJDBCDriver .getStatement();
					String deleteQuery = TextBank.DELETE_POSTS_QUERY;
					deleteQuery += pid;
					System.out.println(deleteQuery);
					MyJDBCDriver.getResultSetForExecuteUpdate(deleteQuery);
					if(MyJDBCDriver.queryOP >= 1 ){
						System.out.println("Posts & Comments deleted successfully..!");
					}
				} catch (Exception e) {
					GetErrorMsg.printError(e);
				}finally{
						MyJDBCDriver.closeAll();
				}
		}
	}

	private void insertPostsIntoDB(HttpServletRequest request, String postTitle, 
			String postContent, String postAuthor, String emailId) {
		// TODO Auto-generated method stub
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
							String insertPost = TextBank.INSERT_POST_QUERY;
							insertPost += "('"+ postTitle+ "','" + postAuthor +"','" + postContent
											+"','"+ emailId + "')";
							System.out.println("Insert Post: "+ insertPost);
							MyJDBCDriver.getResultSetForExecuteUpdate(insertPost);
							if(MyJDBCDriver.queryOP >= 1 ){
								
							}
						} catch (Exception e) {
							GetErrorMsg.printError(e);
						}finally{
								MyJDBCDriver.closeAll();
						}
					}
	}

	private Set<Comment> getAllCommetsFromDB() {
		// TODO Auto-generated method stub
		Set<Comment> mapOfComments = new LinkedHashSet<Comment>();
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
					String query = TextBank.SELECT_COMMENTS_QUERY;
					MyJDBCDriver.getResultSet(query);
			        if(MyJDBCDriver.resultSet != null){
			        	while(MyJDBCDriver.resultSet.next()){
			        		int commentid = Integer.parseInt(MyJDBCDriver.resultSet.getString("commentid"));
			        		if(commentid > 0){
			        			int postid = Integer.parseInt(MyJDBCDriver.resultSet.getString("postid"));
				        		String msgcontent = MyJDBCDriver.resultSet.getString("commentcontent");
				        		String dttime = MyJDBCDriver.resultSet.getString("dttime");
				        		String commentauthor= MyJDBCDriver.resultSet.getString("commentauthor");
				        		Comment newComment = new Comment();
				        		newComment .setCommentid(commentid);
				        		newComment .setPostid(postid);
				        		newComment .setContent(msgcontent );
				        		newComment .setDttime(dttime);
				        		newComment.setCommentauthor(commentauthor);
				        		mapOfComments .add(newComment);
			        		}else{
			        			break;
			        		}
			        	}
			        }
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
						MyJDBCDriver.closeAll();
				   }
			}
		return mapOfComments;
	}

	private Set<Post> getAllPostsFromDB() {
		// TODO Auto-generated method stub
		Set<Post> mapOfPosts = new LinkedHashSet<Post>();
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
					String query = TextBank.SELECT_POSTS_QUERY;
					MyJDBCDriver.getResultSet(query);
			        if(MyJDBCDriver.resultSet != null){
			        	while(MyJDBCDriver.resultSet.next()){
			        		int postid = Integer.parseInt(MyJDBCDriver.resultSet.getString("postid"));
			        		if(postid > 0){
				        		String title = MyJDBCDriver.resultSet.getString("title");
				        		String author =	MyJDBCDriver.resultSet.getString("author");
				        		String msgcontent = MyJDBCDriver.resultSet.getString("msgcontent");
				        		String dttime = MyJDBCDriver.resultSet.getString("dttime");
				        		String email = MyJDBCDriver.resultSet.getString("email");
				        		Post newPost = new Post();
				        		newPost.setPostid(postid);
				        		newPost.setTitle(title);
				        		newPost.setAuthor(author);
				        		newPost.setMsgcontent(msgcontent);
				        		newPost.setDttime(dttime);
				        		newPost.setEmailid(email);
				        		mapOfPosts.add(newPost);
				        		/*System.out.println("ID: " + newPost.getPostid());
								System.out.println("title: " + newPost.getTitle());
								System.out.println("msg: " + newPost.getMsgcontent());
								System.out.println("Author: " + newPost.getAuthor());
								System.out.println("Dt: " + newPost.getDttime());
				        		*/
			        		}else{
			        			break;
			        		}
			        	}
			        }
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
						MyJDBCDriver.closeAll();
				   }
			}
		return mapOfPosts;
	}
	
	
	private void insertCommentsIntoDB(HttpServletRequest req, int postid, String commentContent) {
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
					String insertComment = TextBank.INSERT_COMMENT_QUERY;
					insertComment += "("+ postid +",'" + commentContent+"', '" + str +"' )";
					System.out.println("Insert comment: "+ insertComment);
					MyJDBCDriver.getResultSetForExecuteUpdate(insertComment);
					if(MyJDBCDriver.queryOP >= 1 ){
						
					}
				} catch (Exception e) {
					GetErrorMsg.printError(e);
				}finally{
						MyJDBCDriver.closeAll();
				}
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
