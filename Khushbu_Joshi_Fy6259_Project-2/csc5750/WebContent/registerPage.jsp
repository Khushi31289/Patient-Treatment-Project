<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="course.User" %>
<!DOCTYPE html>
<html lang="en">
 <head>
    <meta charset="utf-8">
    <title>CSC5750 Message Board</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- Le styles -->
    <link rel="stylesheet" href="<%=request.getContextPath() %>/css/bootstrap.css" type="text/css" >
    <style>
      body {
        padding-top: 60px; /* 60px to make the container go all the way to the bottom of the topbar */
      }
    </style>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap-responsive.css" type="text/css">

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
              <li><a href="<%=request.getContextPath()%>/Posts">Posts</a></li>
              <li><a href="<%=request.getContextPath()%>/admin.jsp">Admin</a></li>
            </ul>

            

          </div><!--/.nav-collapse -->
        </div>
      </div>
    </div>

    <div class="container">

      <div class="row">
        <div class="offset3 span6">
        <%
        	/* session = request.getSession();
		        if(session.getAttribute("loggedInUser") != null){
					User logUser = (User) session.getAttribute("loggedInUser");
					//String loginMsg = "You are already a register User.";
					//request.getRequestDispatcher("/registerPage.jsp?loginmessage=loginMsg").forward(request, response);
					if(logUser != null){
						String fullName = logUser.getFirstName() + " " + logUser.getLastName() ;
						String msg = "You are currently logged in as "+ fullName +", Log off if you wish to register as New User";
					}
		        }*/
		%>
		
			<form class="form-horizontal" action="Register" method="post">
              <fieldset>
                <legend>New User Registration</legend>
                <div class="control-group ${sFName}">
                  <label class="control-label" for="firstName">First Name</label>
                  <div class="controls">
                    <input type="text" class="input-xlarge" id="firstName" name="firstName" maxlength="255" value="${firstName}"/>
                    <p class="help-block">Must contain only alphabets.  Spaces are not allowed.</p>
                  </div>
                </div>

                <div class="control-group ${sLName}">
                  <label class="control-label" for="lastName">Last Name</label>
                  <div class="controls">
                    <input type="text" class="input-xlarge" id="lastName"  name="lastName" maxlength="255" value="${lastName}" />
                    <p class="help-block">Must contain only alphabets.  Spaces are not allowed.</p>
                  </div>
                </div>

                <div class="control-group ${sEmail1}">
                  <label class="control-label" for="email1">E-mail Address</label>
                  <div class="controls">
                    <input type="email" class="input-xlarge" id="email1" name="email1" maxlength="255" value="${email1}"/>
                    <p class="help-block">Must be a valid e-mail address & you cannot choose an existing email address.</p>
                  </div>
                </div>

                <div class="control-group ${sEmail2}">
                  <label class="control-label" for="email2">Re-Enter E-mail Address</label>
                  <div class="controls">
                    <input type="email" class="input-xlarge" id="email2" name="email2" maxlength="255" value="${email2}"/>
                    <p class="help-block">Must be a valid e-mail address & you cannot choose an existing email address.</p>
                  </div>
                </div>

                <div class="control-group ${sPhone}">
                  <label class="control-label" for="areaCode">Telephone #</label>
                  <div class="controls">
                    ( <input type="text" class="input-small" id="areaCode" name="areaCode" maxlength="3" value="${areaCode}"  /> )
                    <input type="text" class="input-small" id="prefix" name="prefix"  maxlength="3" value="${prefix}"  /> -
                    <input type="text" class="input-small" id="suffix" name="suffix" maxlength="4" value="${suffix}"   /> 
                    <p class="help-block">Must only contain numbers.</p>
                  </div>
                </div>

                <div class="control-group ${sPwd1}">
                  <label class="control-label" for="password1">Password</label>
                  <div class="controls">                    
                    <input type="password" class="input-xlarge" id="password1" name="password1" maxlength="255" value="${password1}" /> 
                    <p class="help-block">Your account password. Must contain at least one letter and number.</p>
                  </div>
                </div>

                <div class="control-group ${sPwd2}">
                  <label class="control-label" for="password2">Re-Enter Password</label>
                  <div class="controls">                    
                    <input type="password" class="input-xlarge" id="password2" name="password2"  maxlength="255" value="${password2}"/> 
                    <p class="help-block">Your account password. Must contain at least one letter and number.</p>
                  </div>
                </div>

                <div class="control-group">
                  <label class="control-label"></label>
                  <div class="controls">                    
                    <hr />
                
                    <button type="submit" class="btn btn-primary">Register</button>
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