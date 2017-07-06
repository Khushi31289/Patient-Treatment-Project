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
    <link href="css/bootstrap.css" type="text/css" rel="stylesheet">
    <style>
      body {
        padding-top: 60px; /* 60px to make the container go all the way to the bottom of the topbar */
      }
    </style>
    <link href="css/bootstrap-responsive.css" type="text/css" rel="stylesheet">

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

          </div><!--/.nav-collapse -->
        </div>
      </div>
    </div>

    <div class="container">

      <div class="row">
        <div class="offset2 span9">

          <div class="hero-unit">
            <h1>CSC5750 Message Board</h1>
            <p>Welcome to CSC5750's Course Message Board!</p>
          </div>

        </div>
      </div>
	
      <div class="row pagination-centered">
        
        <div class="offset2 span4">
          <h2>New Users</h2>
          <hr />
           <p>New Users must register before using this site.</p>
          <p id="pRegister">
          	<a class="btn btn-primary" 
          	href="<%=request.getContextPath()%>/fy6259/project2/Home?redirect=register">Register »</a>
          </p>
        </div>
        <div class="span4">
          <h2>Login</h2>
          <hr />
           <p>Already registered?  Login to begin using this site.</p>
          <p id="pLogin">
          	<a class="btn btn-primary" 
          		href="<%=request.getContextPath()%>/fy6259/project2/Home?redirect=login">Login »</a>
          </p>
        </div>

      </div>
    

    </div> <!-- /container -->

    <!-- Le javascript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="js/jquery-1.7.2.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script type="text/javascript">
    /*	function changeValue(obj){
    		var _id = $(obj).attr('id');
    		if(_id != undefined && _id != null){
    			if(_id == 'pRegister'){
    				$('#hiddenRedirect').attr('value','register');
    			}else if(_id == 'pLogin'){
    				$('#hiddenRedirect').attr('value','login');
    			}
    		}
    		alert($('#hiddenRedirect').val());
    	}*/
    </script>
    

  </body>
</html>