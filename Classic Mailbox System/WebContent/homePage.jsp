<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE >
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>Welcome to My Mail Box</title>
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
          <a class="brand" href="#">Welcome to My Mail-box</a>
          <div class="nav-collapse">
            <!--<ul class="nav">
              <li class="active"><a href="../project2/home">Home</a></li>
              <li><a href="../project2/posts">Posts</a></li>
              <li><a href="../project2/admin">Admin</a></li>
            </ul>
			-->
          </div><!--/.nav-collapse -->
        </div>
      </div>
    </div>

    <div class="container">

      <div class="row">
        <div class="offset2 span9">
          <div class="hero-unit">
            <h1>My Mail-box</h1>
			<hr>
            <p>Please select from the below mentioned choices:</p><br/>
		<!--  <input type="text"></input><br/> -->		
			<div class="searchSpan"> 
				<a class="btn btn-primary" href="<%=request.getContextPath()%>/ReadJsonData">View Entire Mail Box</a>
			</div><br>
			<div class="searchSpan" > 
				<a class="btn btn-primary" href="<%=request.getContextPath()%>/AddNewMessage">Add a new mail</a>
			</div><br>
          </div>
        </div>
      </div>

      <div class="row pagination-centered">
        <!--
        <div class="offset2 span4>
          <h2>New Users</h2>
          <hr />
           <p>New Users must register before using this site.</p>
          <p><a class="btn btn-primary" href="../project2/Register">Register »</a></p>
        </div>
        <div class="span4">
          <h2>Login</h2>
          <hr />
           <p>Already registered?  Login to begin using this site.</p>
          <p><a class="btn btn-primary" href="../project2/Login">Login »</a></p>
        </div>
		-->
      </div>
    

    </div> <!-- /container -->

    <!-- Le javascript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="../js/jquery-1.7.2.min.js"></script>
    <script src="../js/bootstrap.min.js"></script>
    

  </body>
</html>