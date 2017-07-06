<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>CSC5750 Message Board</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- Le styles -->
    <link href="<%=request.getContextPath() %>/css/bootstrap.css" type="text/css"  rel="stylesheet">
    <style>
      body {
        padding-top: 60px; /* 60px to make the container go all the way to the bottom of the topbar */
      }
      
      .fontRed{
      	color: red;
      	font-weight: bold;
      	text-align: left;
      }
      
    </style>
    <link href="<%=request.getContextPath() %>/css/bootstrap-responsive.css" type="text/css"  rel="stylesheet">

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
          <a class="brand" href="#">CS5750 Message Board</a>
         <!-- <div class="nav-collapse">
            <ul class="nav">
              <li class="active"><a href="<%=request.getContextPath()%>/home.jsp">Home</a></li>
              <li><a href="<%=request.getContextPath()%>/fy6259/project2/Posts">Posts</a></li>
              <li><a href="project2/admin">Admin</a></li>
            </ul>
          </div> -->
        </div>
      </div>
    </div>
    <div class="container">
      <div class="row">
        <div class="offset3 span6">
            <form class="form-horizontal" action="csc5750/fy6259/project2/Posts?addPost=yes" method="post">
              <fieldset>
                <legend>Add a new Post</legend>
   		        <div class="control-group">
                  <label class="control-label" for="postTitle">Post Title</label>
                  <div class="controls">
                    <input type="text" class="input-xlarge" id="postTitle" name="postTitle"  
                    		required="required" value="${postTitle}" />
                    <p class="help-block">Enter your Post title here. Its a Mandatory Field.</p>
                  </div>
                </div>
                
                <div class="control-group">
                  <label class="control-label" for="postContent">Content</label>
                  <div class="controls">                    
                    <input type="text" class="input-xlarge" id="postContent" name="postContent" 
                    		required="required"	 value="${postContent}"/> 
                    <p class="help-block">Enter your message Here.Its a Mandatory Field.</p>
                  </div>
                </div>
                 
                <br>
                <div class="control-group">
                  <label class="control-label"></label>
                  <div class="controls">                    
                    <hr />
                    <button type="submit" class="btn btn-primary">Submit</button>
                  </div>
                </div>
              </fieldset>
            </form>

        </div>
      </div>
 
    </div> <!-- /container -->

    <!-- Le javascript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="<%=request.getContextPath() %>/js/jquery-1.7.2.min.js"></script>
    <script src="<%=request.getContextPath() %>/js/bootstrap.min.js"></script>
    

  </body>
</html>