package course;

public class TextBank {
	//DB connection, Driver details.
	 static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	 static final String DB_URL = "jdbc:mysql://localhost:3306/sampledb";
	 static final String UNM = "root";
	 static final String PWD = "pass1234";
	 
	 //For DB login alert
	 static final String LOGINUNSUCCESS = "Unable to login/connect to Database";
	 static final String LOGINSUCCESS = "Able to login & connect to Database";
	 static final String INITSUCCESS = "successfully executed queries from project2.sql file";

	 //Query to reteieve all users
	 static final String SELECT_USERS_QUERY	= "select * from Users";
	 
	 //Query to retrieve post in orderly fashion in ascending order
	 static final String SELECT_POSTS_QUERY	= "select * from POSTS order by dttime asc";

	 //Query to retrieve comments in orderly fashion in ascending order
	 static final String SELECT_COMMENTS_QUERY	= "select * from COMMENTS order by dttime asc";
	 
	 //Insert query for new user
	 static final String INSERT_USER_QUERY = "INSERT INTO Users (firstname, lastname, email, areaCode, prefix, suffix, password) VALUES"; 
	 
	 //Insert query for inserting a new comment
	 static final String INSERT_COMMENT_QUERY = "INSERT INTO comments (postid, commentcontent,commentauthor) VALUES";

	 //Insert query for inserting a new Post
	 static final String INSERT_POST_QUERY =
	 "INSERT INTO posts (title, author, msgcontent, email) VALUES ";//('abcd', 'a', 'm', 'aa@s.com');
	 
	 //Delete query to delete post
	 static final String DELETE_POSTS_QUERY = "DELETE from posts WHERE postid = ";
	 //DELETE from posts where postid = 4; Appending id dynamically
	 //As on delete cascade option is there 
	 //so if entry is deleted from Posts table it will automatically get deleted from comments table also
	 
	 //Delete query to delete comment
	 static final String DELETE_COMMENT_QUERY = "DELETE from COMMENTS WHERE commentid = ";
}