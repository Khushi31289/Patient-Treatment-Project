<%@page import="mongodb.Message" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" %>
<%@ page import="java.util.*" %>
<!DOCTYPE >
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>Welcome to My Mail Box</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- Le styles Your account password. Must contain at least one letter and number. -->
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
          <a class="brand" href="#">Add new mail to Mail-box</a>
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
		<div class="row">
		        <div class="offset3 span6">
				<form class="form-horizontal" action="AddNewMessage" method="post">
	              <fieldset> <!-- fname, _id -->
	                <legend>Add new message</legend>
	                     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	                     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

					<a style="float: right;" class="btn btn-primary" 
	                     href="<%=request.getContextPath()%>/homePage.jsp">Back</a>
					<div>
						<span>${message}</span>
					</div>
	                <div class="control-group">
	                  <label class="control-label" for="txtSender">Sender Name</label>
	                  <div class="controls">
	                    <input type="text" class="input-xlarge" id="txtSender" name="txtSender" maxlength="255" />
	                  </div>
	                </div>
	
	                <div class="control-group">
	                  <label class="control-label" for="txtRecipient">Recipients Name</label>
	                  <div class="controls">
	                    <input type="text" class="input-xlarge" id="txtRecipient"  name="txtRecipient" maxlength="255" />
	                  </div>
	                </div>
	
	                <div class="control-group">
	                  <label class="control-label" for="selFolder">Folder</label>
	                  <div class="controls">                    
	                    <select id="selFolder" name="selFolder" class="input-medium" >
			          	<%
			        	if(request.getAttribute("FolderNames") != null)
			    		{
			    			Map<String,String> mapOfFolders = new LinkedHashMap<String,String>();
			    			mapOfFolders.putAll((Map<String,String>) request.getAttribute("FolderNames"));
							if(!mapOfFolders.isEmpty() ){
								Iterator<String> itrFolder = mapOfFolders.keySet().iterator();
								while(itrFolder.hasNext()){
									String folderKey = itrFolder.next();
									String folderName = mapOfFolders.get(folderKey);
									
			    		%>
			    		<option value="<%=folderKey%>" 
			    		<% 
			    			if(request.getAttribute("isSelected") != null){
			    				String  isSelected = "";
			    				isSelected = request.getAttribute("isSelected").toString();
			    				if(isSelected.equals(folderKey)){
			    				%>
			    				selected="selected"	
			    		<%	
			    				}
			    			}
			    		%>
			    		><%=folderName%></option>
			    		<% 
								}
							}
						}
		          	%>
			        </select>
	                  </div>
	                </div>
	
	                <div class="control-group">
	                  <label class="control-label" for="txtSubject">Subject</label>
	                  <div class="controls">                    
	                    <textarea class="textarea input-xxlarge" id="txtSubject" name="txtSubject"  maxlength="10000" >
	                    </textarea> 
	                  </div>
	                </div>	

	                <div class="control-group ">
	                  <label class="control-label" for="txtMsg">Message</label>
	                  <div class="controls">
	                    <textarea type="textarea" class="input-xxlarge" id="txtMsg" name="txtMsg" maxlength="100000"  rows="22">
	                    </textarea>
	                  </div>
	                </div>

	                <div class="control-group ">
	                  <label class="control-label" for="txtEmail">CC E-mail Address</label>
	                  <div class="controls">
	                    <input type="text" class="input-xlarge" id="txtCCEmail" name="txtCCEmail" maxlength="255" />
	                  </div>
	                </div>
	
<!-- 	                <div class="control-group ">
	                  <label class="control-label" for="areaCode">Mid</label>
	                  <div class="controls">
	                    <input type="text" class="input-xlarge" id="txtMid" name="txtMid" maxlength="255" />
	                  </div>
	                </div>
	 -->
	                <div class="control-group ">
	                  <label class="control-label" for="txtBcc">Bcc Address</label>
	                  <div class="controls">                    
	                    <input type="text" class="input-xlarge" id="txtBcc" name="txtBcc" maxlength="255" /> 
	                  </div>
	                </div>
	
	                <div class="control-group ">
	                  <label class="control-label" for="txtTo">To Address</label>
	                  <div class="controls">                    
	                    <input type="text" class="input-xlarge" id="txtTo" name="txtTo"  maxlength="255" /> 
	                  </div>
	                </div>

	                <div class="control-group ">
	                  <label class="control-label" for="txtReplyTo">Reply To Address</label>
	                  <div class="controls">                    
	                    <input type="text" class="input-xlarge" id="txtReplyTo" name="txtReplyTo"  maxlength="255" /> 
	                  </div>
	                </div>
	    <!-- 
	                <div class="control-group ">
	                  <label class="control-label" for="txtCType">CType</label>
	                  <div class="controls">                    
	                    <input type="text" class="input-xlarge" id="txtCType" name="txtCType"  maxlength="255" /> 
	                  </div>
	                </div>
					<div class="control-group ">
	                  <label class="control-label" for="txtCType">Fake Path</label>
	                  <div class="controls">                    
	                    <input type="text" class="input-xlarge" id="txtPath" name="txtPath"  maxlength="255" /> 
	                  </div>
	                </div>
	        -->            
	                <div class="control-group">
	                  <label class="control-label"></label>
	                  <div class="controls">                    
	                    <hr />
	                    <button type="submit" class="btn btn-primary">Add New Message</button>
	   	                     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	                     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	   	                     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	                     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	                    
	                    <a style="float: right;" class="btn btn-primary" 
	                     href="<%=request.getContextPath()%>/homePage.jsp">Back</a>
	                    
	                  </div>
	                  
	                </div>
	                  
	                
	              </fieldset>
	            </form>
		
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
    <script src="<%=request.getContextPath() %>/js/jquery-1.7.2.min.js"></script>
    <script src="<%=request.getContextPath() %>/js/bootstrap.min.js"></script>
    

  </body>
</html>