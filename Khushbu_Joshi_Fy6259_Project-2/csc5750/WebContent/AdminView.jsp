<%@page import="course.Comment"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" %>
<%@ page import="java.util.*" %>
<%@ page import="course.Post" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <title>CSC5750 Message Board</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- Le styles -->
    <link href="<%=request.getContextPath() %>/css/bootstrap.css" rel="stylesheet">
    <style>
      body {
        padding-top: 60px; /* 60px to make the container go all the way to the bottom of the topbar */
      }
    </style>
    <link href="<%=request.getContextPath() %>/css/bootstrap-responsive.css" rel="stylesheet">

    <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

    <!-- Le fav and touch icons -->
    <link rel="shortcut icon" href="ico/favicon.ico">
    <link rel="apple-touch-icon-precomposed" sizes="144x144" href="ico/apple-touch-icon-144-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="114x114" href="ico/apple-touch-icon-114-precomposed.png">
    <link rel="apple-touch-icon-precomposed" sizes="72x72" href="ico/apple-touch-icon-72-precomposed.png">
    <link rel="apple-touch-icon-precomposed" href="ico/apple-touch-icon-57-precomposed.png">
  </head>

  <body>

    <div class="navbar navbar-fixed-top">
      <div class="navbar-inner">
        <div class="container">
          <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </a>
          <a class="brand" href="#">CSC5750 Message Board</a>
          <div class="nav-collapse">
            <ul class="nav">
       		 <li class="active"><a href="<%=request.getContextPath()%>/home.jsp">Home</a></li>
              <li><a href="<%=request.getContextPath()%>/fy6259/project2/Posts">Posts</a></li>
              <li><a href="<%=request.getContextPath()%>/fy6259/project2/AdminController">Admin</a></li>
            </ul>

            <ul class="nav pull-right">                            
              <li class="divider-vertical"></li>
              <li class="dropdown"> 
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                  <b class="icon-user icon-white"></b>
                  <!-- ##############################################
                       CSC5750: This is where you display the Users name.
                       ############################################## -->
                  ${LoggedInUserName}
                  <b class="caret"></b>
                </a>
                <ul class="dropdown-menu">
                  <li><a href="<%=request.getContextPath()%>/fy6259/project2/Login?action=logout">Logout</a></li>                
                </ul>
              </li>
            </ul>

          </div><!--/.nav-collapse -->
        </div>
      </div>
    </div>

    <div class="container">

      <div class="page-header">
        <h1>Admin View - Posts</h1>       
        <a href="<%=request.getContextPath()%>/NewPost.jsp">Write a Post</a> 
      </div>

     <!-- <p>This page will be populated in a future assignment.</p>  --> 
    <%
	try
	{
		String loggedEmail = (String)request.getAttribute("LoggedInUserEmailId");
		
		if(request.getAttribute("mapOfPosts") != null)
		{
			Set<Post> mapOfPosts = new LinkedHashSet<Post>();
			mapOfPosts.addAll((Set<Post>) request.getAttribute("mapOfPosts"));
				if(!mapOfPosts.isEmpty() ){
					Iterator<Post> itrPost = mapOfPosts.iterator();
					while(itrPost.hasNext()){
						Post newPost = itrPost.next();
						int id = newPost.getPostid();
						String title = newPost.getTitle();
						String  msg = newPost.getMsgcontent();
						String  author = newPost.getAuthor();
						String  dttime = newPost.getDttime();
						String  email = newPost.getEmailid();
						boolean removeLink = false;
						if(email.equals(loggedEmail )){
							removeLink = true;
						}
    %>
		<div id="<%=id %>">
			<h2><%=title %></h2> by <%= author%> - <i><%= dttime%></i>   
			<br><%=msg %>
			<br>
			<% 
			if(request.getAttribute("mapOfComments") != null)
			{
				Set<Comment> mapOfComments = new LinkedHashSet<Comment>();
				mapOfComments .addAll((Set<Comment>) request.getAttribute("mapOfComments"));
				int i=0;
				if(!mapOfComments .isEmpty() ){
					Iterator<Comment> itrComment = mapOfComments.iterator();
					while(itrComment .hasNext()){
						Comment newComment = itrComment.next();
						int pid  = newComment.getPostid();
						int commentid  = newComment.getCommentid();
						if(pid == id){
							i++;
							int cid = newComment .getCommentid();
							String content = newComment.getContent(); //newPost.getTitle();
							String  dt = newComment.getDttime();
							String commentAuth = newComment.getCommentauthor();
				%>
						<br><b><%=commentAuth %></b> Commented, <%=content %> - <i><%= dt%></i> &nbsp;&nbsp;
						| <a href="<%=request.getContextPath()%>/fy6259/project2/AdminController?rcommentid=<%=cid%>&removecomment=yes">Remove Comment</a>
				<%	
						}
					}
				}				
			}
			%>
			<br><br><a href="<%=request.getContextPath()%>/Comment.jsp?postid=<%=id%>">Comment</a>
				 | <a href="<%=request.getContextPath()%>/fy6259/project2/AdminController?postid=<%=id%>&remove=yes">Remove</a>
		</div>
		<hr>
	<% 
					System.out.println("ID: " + newPost.getPostid());
					System.out.println("title: " + newPost.getTitle());
					System.out.println("msg: " + newPost.getMsgcontent());
					System.out.println("Author: " + newPost.getAuthor());
					System.out.println("Dt: " + newPost.getDttime());
				}
			}
	   }
	}catch(Exception e){
	%>
		<p>No Posts found currently</p>
	<%
		}
	%>
    </div> <!-- /container -->

    <!-- Le javascript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="<%=request.getContextPath() %>/js/jquery-1.7.2.min.js"></script>
    <script src="<%=request.getContextPath() %>/js/bootstrap.min.js"></script>
    

  </body>
</html>