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
	<form action="ReadJsonData" method="ReadJsonData" id="frmView">
    <div class="container">

      <div class="row">
        <div class="offset2 span9">
          <div class="hero-unit">
            <h2><big>My Mail-box</big></h2>
			<hr>
            <p>Select from below given options to folders to view messages folder-wise:</p> 
          	<select id="selFolder" name="selFolder" class="input-medium" >
          	<option value="">     --     </option>
          	<%
          	
          	/*if(request.getAttribute("selFolder") != null){
          		System.out.println("This is selected folder: \n"+ request.getAttribute("selFolder"));
          		
          	}*/
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
	           	<!--  
	           	
	           	    "_sent",
			    "all_documents",
			    "business",
			    "calendar",
			    "compaq",
			    "deleted_items",
			    "discussion_threads",
			    "elizabeth",
			    "enron",
			    "family",
			    "inbox",
			    "notes_inbox",
			    "sec_panel",
			    "sent",
			    "sent_items",
			    "_send"
	           	
	           	 -->
	        </select>
	        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	        <span  style="marginLeft">
	              	<select id="selMvFolder" name="selMvFolder" class="input-medium" >
          	<option value="">     --     </option>
          	<%
          	
          	/*if(request.getAttribute("selFolder") != null){
          		System.out.println("This is selected folder: \n"+ request.getAttribute("selFolder"));
          		
          	}*/
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
	    			/*if(request.getAttribute("isSelected") != null){
	    				String  isSelected = "";
	    				isSelected = request.getAttribute("isSelected").toString();
	    				if(isSelected.equals(folderKey)){*/
	    				%>
	    				selected="selected"	
	    		<%	
	    				//}
	    			//}
	    		%>
	    		><%=folderName%></option>
	    		<% 
						}
					}
				}
	          	
          	%>
	           	<!--  
	           	
	           	    "_sent",
			    "all_documents",
			    "business",
			    "calendar",
			    "compaq",
			    "deleted_items",
			    "discussion_threads",
			    "elizabeth",
			    "enron",
			    "family",
			    "inbox",
			    "notes_inbox",
			    "sec_panel",
			    "sent",
			    "sent_items",
			    "_send"
	           	
	           	 -->
	        </select>
	        </span>
            <br/>
		<!--  <input type="text"></input><br/> -->		
			<br>
			<div class="searchSpan"> 
				<input type="submit" name="submitBtn" id="btnShow" value="Show"  class="btn btn-primary">&nbsp;&nbsp;&nbsp;&nbsp;
				<a class="btn btn-primary" href="<%=request.getContextPath()%>/homePage.jsp">Back</a>&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="submit" name="submitBtn" id="btnDelete" value="Delete"  class="btn btn-primary">&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="submit" name="submitBtn" id="btnMove" value="Move"  class="btn btn-primary">&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="submit" name="submitBtn" id="btnQuery1" value="Count All Mails"  class="btn btn-primary">&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="submit" name="submitBtn" id="btnQuery2" value="Count Mails Folder-wise"  class="btn btn-primary">&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="submit" name="submitBtn" id="btnQuery3" value="Query-3"  class="btn btn-primary">&nbsp;&nbsp;&nbsp;&nbsp;
				<!--  <span class="show" onclick="toggleClass('spanSearchLink');" id="spanSearchLink" name="span">  
					<a class="btn btn-primary" id="searchLink" name = "searchLink">Search</a>&nbsp;&nbsp;&nbsp;&nbsp; -->
				<!--  </span>  -->
				<!--  <span class="hide"  onclick="toggleClass('spanSearchBtn');" id="spanSearchBtn" name="span">  -->
				<!--  </span>  -->
			</div><br>
			<div id="divSearch" name="divSearch">
				By Text: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" id="txtSearchByText" name = "txtSearchByText" > &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="submit" name="submitBtn" id="btnSearchByText" value="Search By Text"  class="btn btn-primary">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="submit" name="submitBtn" id="btnSearchByText" value="Sort Mails by Date- Ascending"  class="btn btn-primary">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<br><br>
				By Sender: &nbsp;&nbsp;<input type="text" id="txtSearchBySender" name= "txtSearchBySender">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="submit" name="submitBtn" id="btnSearchBySender" value="Search By Sender"  class="btn btn-primary">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="submit" name="submitBtn" id="btnSearchByText" value="Sort Mails by Date-Descending"  class="btn btn-primary">
			</div>
			<div >${statusMessage}</div> 
			<br><div >${MapReduceMsg}</div>
          </div>
			<div class="fontTable">${OutputQuery3}</div>
		<%
		if(request.getAttribute("ListOfMessages") != null)
		{
			List<Message> messageList= new ArrayList<Message>();
			messageList.addAll((ArrayList<Message>) request.getAttribute("ListOfMessages"));
			if(!messageList .isEmpty() )
			{
				Iterator itr = messageList.iterator();
				/*System.out.println(msgObj.get_id()+ 
				msgObj.getBcc() + "\n" + msgObj.getCc()+"\n"+
				msgObj.getCtype()+ "\n" + msgObj.getDate()+"\n" + 
				msgObj.getFname() +  "\n" + msgObj.getFolder() + "\n" + 
				msgObj.getFpath() +  "\n" + msgObj.getMid()+ "\n"+
				msgObj.getRecipients() + "\n" + msgObj.getReplyto()+"\n"+
				msgObj.getSender() +  "\n" + msgObj.getSubject() + "\n"+
				msgObj.getText() +  "\n" +
				msgObj.getTo() +"\n" );*/

				while(itr.hasNext()){
					Message msgObj = (Message )itr.next();
					String _id = msgObj.get_id();
					String ctype = msgObj.getCtype(); 
					String date = msgObj.getDate();
					String fname = msgObj.getFname(); 
					String folder = msgObj.getFolder(); 
					String fpath = msgObj.getFpath(); 
					String mid = msgObj.getMid();
					  
					String replyto = msgObj.getReplyto();
					String sender = msgObj.getSender(); 
					String subj = msgObj.getSubject(); 
					String txt = msgObj.getText();
					String bcc ="";
					if(null != msgObj.getBcc()){
						for(String temp : msgObj.getBcc()){
							if(bcc =="")
								bcc+= temp;
							else
								bcc+=","+temp;
						}
					}
					//String bcc = msgObj.getBcc(); 
					//String cc = msgObj.getCc();
					//String recipient  = msgObj.getRecipients();
					//String to = msgObj.getTo();
					String cc = "";
					if(null != msgObj.getCc() ){
						for(String temp : msgObj.getCc()){
							if(cc =="")
								cc+= temp;
							else
								cc+=","+temp;
						}
					}
					String recipient  = "";
					if(null != msgObj.getRecipients())
					{
						for(String temp : msgObj.getRecipients()){
							if(recipient   =="")
								recipient  += temp;
							else
								recipient  +=","+temp;
						}
					}
					String to = "";
					if(null != msgObj.getTo())
					{
						for(String temp : msgObj.getTo()){
							if(to =="")
								to += temp;
							else
								to +=","+temp;
						}
					}
					
		%>
		<br>
		<input type="checkbox" id="<%=_id %>" value="<%=_id %>" name="arrayMailCheckBoxs">
		<input type="hidden" value= "<%=_id %>" >
			<!--  _id : <%=_id %> -->
		<b>Sender :</b> <%=sender%><br>
		<b>Subject:</b><%=subj%><br>
		<b>Date:</b><%=date%><br>
		<span  id = "span<%=_id %>" class="hide Word-Break">
			<b>Recipients: </b><%=recipient%><br> 
			<b>cc: </b><%=cc%><br>
			<b>Bcc: </b><%=bcc%><br>
			<b>To: </b><%=to%><br>
			<b>Reply To: </b><%=replyto%><br>
			<b>Message: </b><br><%=txt%><br>
			<!--  
			<b>File Name: </b><%=fname%><br>
			<b>Mid: </b><%=mid%><br>
			<b>Fpath: </b><%=fpath%><br>
			<b>Content Type: </b><%=ctype%><br>
			<b>Folder:</b><%=folder%><br> -->
		</span>
		<a style="cursor: pointer;" onclick="toggleClass('span<%=_id %>')" id="Myspan<%=_id %>" class=""><b>......</b></a>
		<hr>
		<%
				}
			}
		}		
		%>
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

</form>

    <!-- Le javascript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="<%=request.getContextPath() %>/js/jquery-1.7.2.min.js"></script>
    <script src="<%=request.getContextPath() %>/js/bootstrap.min.js"></script>
    <script type="text/javascript">
	 function toggleClass(val){
		 console.log("inside toggle");
		 val = "#" + val;
		 if($(val).hasClass("hide")){
			 $(val).addClass("show").removeClass("hide");
			// document.getElementById(Myspan).innerHTML = ".....Expand";
		 }else if($(val).hasClass("show")){
			 $(val).addClass("hide").removeClass("show");
			// document.getElementById(Myspan).innerHTML = ".....Collapse";
		 }
	 }
	 
	/* function toggleSearchBtn(val){
		 //spanSearchLink
		 val = "#" + val;
		 if(val == "#spanSearchLink"){
			 if($(val).hasClass("hide")){
				 //.hasClass("hide")
				 $(val).addClass("show").removeClass("hide");
				 if($(val).){
				 	$("#spanSearchBtn").addClass("hide");
				 }
				// document.getElementById(Myspan).innerHTML = ".....Expand";
			 }else if(val == "#spanSearchBtn"){
				 $(val).addClass("hide").removeClass("show");
				 $(val).hasClass("show");
				 $("#spanSearchBtn").addClass("show");
			 }
		 }else if(val == "#spanSearchBtn"){
			 
		 }
	 }*/
	</script>

  </body>
</html>