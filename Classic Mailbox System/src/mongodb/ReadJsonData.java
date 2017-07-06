package mongodb;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MapReduceCommand;
import com.mongodb.MapReduceOutput;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.sun.corba.se.spi.orbutil.fsm.Action;
import static java.util.Arrays.asList;

/**
 * Servlet implementation class ReadJsonData
 */
@WebServlet("/ReadJsonData")
public class ReadJsonData extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final String DBNAME = "email";
    private static final String COLLECTION_NAME= "messages";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReadJsonData() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		MongoClient mongoClient = null;
		String  selectedFolder = null;
		String _action= "";
		String statusMessage = "";
		selectedFolder = request.getParameter("selFolder");
		_action = request.getParameter("submitBtn");
		System.out.println("Selected Folder : " + selectedFolder);
		//arrayMailCheckBoxs
		System.out.println("Action performed : " +_action);
		boolean isMessageListSet = false;
		if(notNullCheck(_action)){
			if(_action.equals(TEXTBANK.UPDATEBUTTON) || _action.equals(TEXTBANK.DELETEBUTTON)){
				if(null != request.getParameterValues("arrayMailCheckBoxs")  ){
					String[] ArrayCheckBoxes = {};
					ArrayCheckBoxes  = request.getParameterValues("arrayMailCheckBoxs");
					System.out.println(ArrayCheckBoxes.toString());
					if(_action.equals(TEXTBANK.DELETEBUTTON)){
						System.out.println("Delete operation selected");
						int delcnt = 0;
						delcnt = deleteSelectedMessages(ArrayCheckBoxes );
						String _from = " from "+selectedFolder;
						request.setAttribute("statusMessage", delcnt + " "+TEXTBANK.DELETESTATUS + _from);
					}else if(_action.equals(TEXTBANK.UPDATEBUTTON)){
						System.out.println("Update operation selected");
						String newFolderName = request.getParameter("selMvFolder");
						if(newFolderName != null && selectedFolder !=null){
							if(!newFolderName.equals(selectedFolder) ){
								System.out.println("***********\n" + newFolderName +" , "+selectedFolder);
								int cnt = Update(newFolderName, ArrayCheckBoxes);
								String _from =  " from " + selectedFolder + " to " + newFolderName;
								request.setAttribute("statusMessage", cnt + " " +TEXTBANK.UPDATESTATUS_SUCCESS + _from);
							}else{
								request.setAttribute("statusMessage", TEXTBANK.UPDATESTATUS_FAILURE);
							}
						}
					}
				}else if(!_action.equals("") && !_action.equals(TEXTBANK.SHOWBUTTON)){
					request.setAttribute("statusMessage", TEXTBANK.NO_MAILS_SELECTED);
				}
			}else if((_action.equals(TEXTBANK.SHOWBUTTON) || _action.equals(TEXTBANK.SEARCH_BY_TEXT_BUTTON)
					&& (selectedFolder==null || selectedFolder.equals("")))){
				request.setAttribute("statusMessage", TEXTBANK.NO_FOLDER_SELECTED);
				
			}else if(_action.equals(TEXTBANK.SEARCH_BY_TEXT_BUTTON)){
				String searchText = request.getParameter("txtSearchByText");
				System.out.println("\n\nText to be searched is "+ searchText + "\n");
				if(notBlankCheck(searchText) && !selectedFolder.equals("")){
					System.out.println("$$$$$$$$$$$$$$ - SBT");
					request.setAttribute("ListOfMessages", SearchByText(searchText,selectedFolder));
					request.setAttribute("statusMessage", TEXTBANK.SEARCH_SUCCESS);
					isMessageListSet = true;
				}else if(notBlankCheck(searchText)){
					request.setAttribute("statusMessage", TEXTBANK.NO_TEXT_ENTERED_SBT);
				}else if(selectedFolder.equals("")){
					request.setAttribute("statusMessage", TEXTBANK.NO_FOLDER_SELECTED);
				}
				//System.out.println("inside text search");
			}else if(_action.equals(TEXTBANK.SEARCH_BY_SENDER_BUTTON)){
				String searchSender = request.getParameter("txtSearchBySender");
				if(notBlankCheck(searchSender)){
					System.out.println("***********- SBS");
					request.setAttribute("ListOfMessages", SearchBySender(searchSender ,selectedFolder));
					isMessageListSet = true;
				}
				//System.out.println("inside sender search");
			}else if(_action.equals(TEXTBANK.QUERY1)){
				//Count all the mails
				request.setAttribute("MapReduceMsg",TEXTBANK.QUERY1_MSG + MapReduceCountMails() );
				request.setAttribute("statusMessage","");
			}else if(_action.equals(TEXTBANK.QUERY2)){
				//Count all the MapReduce Count Mails With FolderName mails
				if( selectedFolder != null ){
					request.setAttribute("MapReduceMsg",TEXTBANK.QUERY2_MSG + selectedFolder + " are "+ 
										MapReduceCountMailsWithFolderName(selectedFolder) );
					request.setAttribute("statusMessage","");
				}
				else{
					request.setAttribute("MapReduceMsg",TEXTBANK.QUERY2_MSG_ERROR);
					request.setAttribute("statusMessage","");
				}
			}else if(_action.equals(TEXTBANK.QUERY3)){
				String val = request.getParameter("txtSearchBySender");
				System.out.println("sender= " + val);
				String s = MapReduceBySenderAndRecipients(val);
				System.out.println("Query-3\n" + s);
				request.setAttribute("OutputQuery3", s);
			}else if(_action.equals(TEXTBANK.SORT_ASC)){
				if( selectedFolder != null ){
					System.out.println("Inside asc");
					request.setAttribute("ListOfMessages", Sort(selectedFolder, 1));
					request.setAttribute("statusMessage",TEXTBANK.SORT_SUCCESS_ASC);
				}else{
					request.setAttribute("statusMessage",TEXTBANK.NO_FOLDER_SELECTED);
				}
				isMessageListSet = true;
			}else if(_action.equals(TEXTBANK.SORT_DESC)){
				System.out.println("Inside desc");
				if( selectedFolder != null ){
					request.setAttribute("ListOfMessages", Sort(selectedFolder, -1));
					request.setAttribute("statusMessage",TEXTBANK.SORT_SUCCESS_DESC);
				}else{
					request.setAttribute("statusMessage",TEXTBANK.NO_FOLDER_SELECTED);
				}
				isMessageListSet = true;
			}
		}
		
		try {
				Map<String,String> foldersMap= new LinkedHashMap<String,String>();
				mongoClient = new MongoClient( TEXTBANK.LOCALHOST , TEXTBANK.PORT );
				 MongoDatabase db = mongoClient.getDatabase(TEXTBANK.DBNAME);
				 MongoCursor<String>  iterator =  db.getCollection(TEXTBANK.COLLECTION).
						 distinct(TEXTBANK._FOLDER, String.class).iterator();
				 ArrayList<String> folders= new ArrayList<String>(); 
				 while ( iterator.hasNext()){
					 String s = iterator.next();
					 folders.add(s);
					 System.out.println(s);
				}				 
				for ( String s: folders){
					String val=s;
					foldersMap.put(s, val);
				}				
				if(mongoClient != null)
					mongoClient.close();
				System.out.println(foldersMap.toString());
			ArrayList<Message> messageList= null;
			System.out.println("Name of folder: " + selectedFolder);
			if(selectedFolder != null  && _action.equals(TEXTBANK.SHOWBUTTON))
			{
				messageList = GetMessagesByFolder(selectedFolder);
				int cnt=0;
				System.out.println( "\n"+cnt+ " records retrieved ");
				request.setAttribute("isSelected", selectedFolder);
			}
			else{
				request.setAttribute("isSelected", "");
			}
			request.setAttribute("FolderNames", foldersMap);
			if(!isMessageListSet){
				request.setAttribute("ListOfMessages", messageList);
			}
		} catch (MongoException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());
		}finally {
			if(mongoClient != null)
				mongoClient.close();
		} 
		response.getWriter().append("Served at: ").append(request.getContextPath());
		request.getRequestDispatcher("/ViewAllMessages.jsp").forward(request, response);
	}
	String MapReduceBySenderAndRecipients(String senderName){
		MongoClient mongoClient  = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB("email");
		DBCollection dbCollection = db.getCollection("messages");
		 
		String map = 
		"function()"
		+ "{"
		+ "if(this.sender==\""+senderName+"\")"
		+ "{"
		+ "emit(this.subject, this.recipients);"
		+ "}"
		+ "}";

		        String reduce = 
		        "function(key,values)"
		        + "{"
		        + "var res={subject:\"\", count:0, recipients:0};"
		        + "res.subject = key;"
		        + "for(var i=0;i<values.length;i++)"
		        + "{"
		        + "res.count = 0;"
		        + "for(var j=0; j<values[i].length; j++)"
		        + "{"
		        + "res.count +=1;"
		        + "}"
		        + "res.recipients = values[i];"
		        + "}"
		        + "return res;"
		        + "}";
		        
		        MapReduceCommand cmd = new MapReduceCommand(dbCollection, map, reduce, null, MapReduceCommand.OutputType.INLINE, null);
		        MapReduceOutput out = (dbCollection).mapReduce(cmd);
		        Iterable<DBObject> objects = out.results();
		        int c = 0;
		        StringBuilder sb = new StringBuilder();
		        sb.append("<table>");
		        sb.append("<tr class='tableRow tableCol'>");
		        sb.append("<td class='tableCol'>");
		        sb.append("Subject");
		        sb.append("</td>");
		        sb.append("<td>");
		        sb.append("#of Recipients");
		        sb.append("</td>");
//		        sb.append("<td>");
//		        sb.append("</td>");
		        sb.append("<td>");
		        sb.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Recipients");
		        sb.append("</td>");
		        sb.append("</tr>");
		        for (DBObject o : objects){
			        String line = o.get("value").toString();
			        sb.append("<tr>");
			        String[] splitted = line.split(" : ");
			        for(int i=1; i< splitted.length; i++){
				        sb.append("<td class='tableCol'>");
				        String s = splitted[i];
				        if(s.startsWith("[")){
				        	s = s.substring(1, s.length()-2);
				        	String[] mails = s.split(" , ");
				        	sb.append(s);
				        	sb.append("</td>");
				        }
				        else{
				        	 s = splitted[i].split(" , ")[0];
					        sb.append(s+"\t");
					        sb.append("</td>");
				        }
				        
			        }
			        sb.append("</tr>");
		        }
		        sb.append("</table>");
		        return sb.toString();
		}
	
	/*public ArrayList<Message> SearchBySender(String sender, String folderName){
		Document senderQuery = new Document("sender", sender);
		Document folderQuery = new Document("folder", folderName);
		Document query = new Document("$and", asList(senderQuery, folderQuery));
		FindIterable<Document> iterable = _db.getCollection("messages").find(query);
		
		System.out.println(iterable.toString());
		return GetMessageList(iterable);
	}
	
	
	public ArrayList<Message> SearchBySender(String sender){
		
		Document senderQuery = new Document("sender", sender);
		FindIterable<Document> iterable = _db.getCollection("messages").find(senderQuery);
		 
		System.out.println(iterable.toString());
		
		return GetMessageList(iterable);
	}
	*/
	
	ArrayList<Message> GetMessagesByFolder(String folderName){
		MongoClient mongoClient = null;
		ArrayList<Message> listOfMessages = null;
		try {
				mongoClient = new MongoClient( TEXTBANK.LOCALHOST , TEXTBANK.PORT );
				listOfMessages = new ArrayList<Message>();
				 MongoDatabase _db = mongoClient.getDatabase(TEXTBANK.DBNAME);
				FindIterable<Document> iterable = _db.getCollection(TEXTBANK.COLLECTION).
						find(new Document(TEXTBANK._FOLDER, folderName));
				System.out.println("inside");
				for(Document d : iterable){
					Message m = new Message();
					m._id = d.getObjectId(TEXTBANK._ID).toString(); //getString(TEXTBANK._ID);
					m.sender = d.getString(TEXTBANK._SENDER);
					m.recipients = (ArrayList<String>)d.get(TEXTBANK._RECIPIENTS);
					m.cc = (ArrayList<String>)d.get(TEXTBANK._CC);
					m.text = d.getString(TEXTBANK._TEXT);
					m.mid = d.getString(TEXTBANK._MID);
					m.fpath = d.getString(TEXTBANK._FPATH);
					m.bcc = (ArrayList<String>)d.get(TEXTBANK._BCC);
					m.to = (ArrayList<String>)d.get(TEXTBANK._TO);
					m.replyto = d.getString(TEXTBANK._REPLYTO);
					m.ctype = d.getString(TEXTBANK._CTYPE);
					m.fname = d.getString(TEXTBANK._FNAME);
					m.date = d.getString(TEXTBANK._DATE);
					m.folder = d.getString(TEXTBANK._FOLDER);
					m.subject = d.getString(TEXTBANK._SUBJECT);
					listOfMessages.add(m);
				}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}finally{
			if(mongoClient != null){
				mongoClient.close();
			}
		}
			return listOfMessages ;
		}
	
	
	public ArrayList<Message> SearchByText(String text, String folderName){
			MongoClient mongoClient = null;
			ArrayList<Message> listOfMessages = null;
			FindIterable<Document> iterable =null;
			try {
					mongoClient = new MongoClient( TEXTBANK.LOCALHOST , TEXTBANK.PORT );
					listOfMessages = new ArrayList<Message>();
					 MongoDatabase _db = mongoClient.getDatabase(TEXTBANK.DBNAME);
					Pattern match = Pattern.compile(text, Pattern.CASE_INSENSITIVE);
					Document textQuery = new Document("text", match);
					Document subQuery = new Document("subject", match);
					Document folderQuery = new Document("folder", folderName);
					Document orquery = new Document("$or", asList(textQuery, subQuery));
					Document andquery = new Document("$and", asList(orquery, folderQuery));
					iterable = _db.getCollection("messages").find(andquery);
					System.out.println("Inside SearchBytext function");
					System.out.println(iterable.toString());
			}catch(Exception ex){
				ex.printStackTrace();
			}finally{
				//mongoClient.close();
			}
			return GetMessageList(iterable);
		}
	
	
	ArrayList<Message> GetMessageList(FindIterable<Document> iterable){
		ArrayList<Message> messages = new ArrayList<Message>();
		//if(iterable != null && iterable.iterator().hasNext()){
			for(Document d : iterable){
				Message m = new Message();
				m._id= d.getObjectId("_id").toString();
				System.out.print(m._id+" ");
				m.sender = d.getString("sender");
				m.text = d.getString("text");
				m.mid = d.getString("mid");
				m.fpath = d.getString("fpath");
				m.recipients = GetArrayList("recipients", d);
				m.cc = GetArrayList("cc", d); 
				m.bcc = GetArrayList("bcc", d);
				m.to = GetArrayList("to", d);
				m.replyto = d.getString("replyto");
				m.ctype = d.getString("ctype");
				m.fname = d.getString("fname");
				m.date = d.getString("date");
				m.folder = d.getString("folder");
				m.subject = d.getString("subject");
				messages.add(m);
				System.out.println(m.sender+" "+m.folder);
			}
	/*	}else{
			System.out.println("No msgs retrieved by this search criteria");
		}*/
		return messages;
	}
	
	private ArrayList<String> GetArrayList(String fieldName, Document d){
			ArrayList<String> stringList = new ArrayList<String>();
			List list = (List)d.get(fieldName);
			Iterator< Object > it = list.iterator();
			while( it.hasNext()){
			String s = (String)it.next();
			stringList.add(s);
			}
			return stringList;
	}
	
	public ArrayList<Message> SearchBySender(String sender, String folderName){
		Document senderQuery = new Document("sender", sender);
		Document folderQuery = new Document("folder", folderName);
		Document query = new Document("$and", asList(senderQuery, folderQuery));
		FindIterable<Document> iterable = null;
		MongoClient mongoClient = null;
		ArrayList<Message> listOfMessages = null;
		try {
				mongoClient = new MongoClient( TEXTBANK.LOCALHOST , TEXTBANK.PORT );
				listOfMessages = new ArrayList<Message>();
				 MongoDatabase _db = mongoClient.getDatabase(TEXTBANK.DBNAME);
				iterable = _db.getCollection("messages").find(query);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		System.out.println(iterable.toString());
		return GetMessageList(iterable);
	}
		 
	/*public ArrayList<Message> SearchBySender(String sender){
		 
		Document senderQuery = new Document("sender", sender);
		FindIterable<Document> iterable = _db.getCollection("messages").find(senderQuery);
		 
		System.out.println(iterable.toString());
		return GetMessageList(iterable);
	}*/
	
	protected boolean notBlankCheck(String s){
		if((s != null) && !(s.equals(""))){
			return true;
		}else{
			return false;
		}
	}
	
	static String toProperCase(String s) {
		System.out.println("ReadJsonData.toProperCase()");
	    return s.substring(0, 1).toUpperCase() +
	               s.substring(1).toLowerCase();
	    
	}
	
	protected boolean notNullCheck(String str){
		if(str != null){
			return true;
		}else{
			return false;
		}
	} 
	
	
	protected int deleteSelectedMessages(String[] ArrayCheckBoxes) {
		//Query to delete messages from Database
		MongoClient mongoClient = null;
		int deletedRecords = 0;
		try {
			mongoClient = new MongoClient( TEXTBANK.LOCALHOST , TEXTBANK.PORT );
			 MongoDatabase _db = mongoClient.getDatabase(TEXTBANK.DBNAME);
				for(String id : ArrayCheckBoxes){
					//System.out.println("\nDeleted " + id + "\t");
					//_db.getCollection(TEXTBANK.COLLECTION).deleteOne(new Document(TEXTBANK._ID, "ObjectId(\""+id+"\")"));
					BasicDBObject query = new BasicDBObject();
					query.put("_id", new ObjectId(id));
					//System.out.println("Deleted: " + id);
					DeleteResult res = _db.getCollection(TEXTBANK.COLLECTION).deleteOne(query);
					System.out.println("\n" +id +"Record deleted successfully : "+res.getDeletedCount());
					deletedRecords++;
				}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(mongoClient != null){
				mongoClient.close();
			}
		}
		return deletedRecords;
	}
	
	
	public int Update(String newFolderName, String[] ArrayCheckBoxes){
		MongoClient mongoClient = null;
		int cnt = 0;
		try {
			System.out.println("\nInside move folder\n" + newFolderName);
			mongoClient = new MongoClient( TEXTBANK.LOCALHOST , TEXTBANK.PORT );
			 MongoDatabase _db = mongoClient.getDatabase(TEXTBANK.DBNAME);
				for(String id : ArrayCheckBoxes){
					//System.out.println("\nDeleted " + id + "\t");
					//_db.getCollection(TEXTBANK.COLLECTION).deleteOne(new Document(TEXTBANK._ID, "ObjectId(\""+id+"\")"));
					Document query = new Document(TEXTBANK._ID, new ObjectId(id));
					Document update = new Document(TEXTBANK._FOLDER, newFolderName);
					Document set = new Document("$set", update);
					_db.getCollection("messages").updateOne(query, set);
					System.out.println("Updated record: " + id);
					cnt++;
				}
				System.out.println("\n#Records Moved to folder "+ newFolderName +" : "+cnt);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(mongoClient != null){
				mongoClient.close();
			}
		}
		return cnt;
	}
	
	
/*	public void Delete(String id){
		MongoClient mongoClient = null;
		try {
			mongoClient = new MongoClient( TEXTBANK.LOCALHOST , TEXTBANK.PORT );
			 MongoDatabase _db = mongoClient.getDatabase(TEXTBANK.DBNAME);
			_db.getCollection(TEXTBANK.COLLECTION).deleteOne(new Document(TEXTBANK._ID, id));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(mongoClient != null){
				mongoClient.close();
			}
		}
	}
	*/
	protected void printMessage(List<Message> messageList) {
		// TODO Auto-generated method stub
		Iterator itr = messageList.iterator();
		while(itr.hasNext()){
			Message msgObj = (Message )itr.next();
			System.out.println(msgObj.get_id() + 
					msgObj.getBcc().toString() + "\n" + msgObj.getCc().toString()+"\n"+
					msgObj.getCtype()+ "\n" + msgObj.getDate()+"\n" + 
					msgObj.getFname() +  "\n" + msgObj.getFolder() + "\n" + 
					msgObj.getFpath() +  "\n" + msgObj.getMid()+ "\n"+
					msgObj.getRecipients().toString() + "\n" + msgObj.getReplyto()+"\n"+
					msgObj.getSender() +  "\n" + msgObj.getSubject() + "\n"+
					msgObj.getText() +  "\n" +
					msgObj.getTo().toString() +"\n" );
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	protected Message setValuesInMessage(DBObject dbObject ){
		Message messageObject = new Message();
		Iterator itr = null;
		try {
			itr = dbObject.keySet().iterator();
			if(itr != null)
			{	
				int cnt =0;
				while(itr.hasNext()){
					String fieldName  =itr.next().toString(); 
					String fieldValue = "";
					//if(null != dbObject.get(fieldName))
					
					//System.out.println(fieldName + " : "+ fieldValue);
					if(fieldName.equals(TEXTBANK._ID)){
						if(null != dbObject.get(fieldName)){
							fieldValue = dbObject.get(fieldName).toString();
							messageObject.set_id(fieldValue);
						}
					}else if(fieldName.equals(TEXTBANK._SENDER)){
						if(null != dbObject.get(fieldName)){
							fieldValue = dbObject.get(fieldName).toString();
							messageObject.setSender(fieldValue);
						}
					}else if(fieldName.equals(TEXTBANK._RECIPIENTS)){
						//ArrayList
						if(null != dbObject.get(fieldName)){
							messageObject.setRecipients(splitArray(dbObject.get(fieldName).toString()));
						}
					}else if(fieldName.equals(TEXTBANK._CC)){
						//ArrayList
						if(null != dbObject.get(fieldName)){
							messageObject.setCc(splitArray(dbObject.get(fieldName).toString()));
						}
					}else if(fieldName.equals(TEXTBANK._TEXT)){
						if(null != dbObject.get(fieldName)){
							fieldValue = dbObject.get(fieldName).toString();
							messageObject.setText(fieldValue);
						}
					}else if(fieldName.equals(TEXTBANK._MID)){
						if(null != dbObject.get(fieldName)){
							fieldValue = dbObject.get(fieldName).toString();
							messageObject.setMid(fieldValue);
						}
					}else if(fieldName.equals(TEXTBANK._FPATH)){
						if(null != dbObject.get(fieldName)){
							fieldValue = dbObject.get(fieldName).toString();
							messageObject.setFpath(fieldValue);
						}
					}else if(fieldName.equals(TEXTBANK._BCC)){
						//ArrayList
						if(null != dbObject.get(fieldName)){
							messageObject.setBcc(splitArray(dbObject.get(fieldName).toString()));
						}
					}else if(fieldName.equals(TEXTBANK._TO)){
						//ArrayList
						if(null != dbObject.get(fieldName)){
							messageObject.setTo(splitArray(dbObject.get(fieldName).toString()));
						}
					}else if(fieldName.equals(TEXTBANK._REPLYTO)){
						if(null != dbObject.get(fieldName)){
							fieldValue = dbObject.get(fieldName).toString();
							messageObject.setReplyto(fieldValue);
						}
					}else if(fieldName.equals(TEXTBANK._CTYPE)){
						if(null != dbObject.get(fieldName)){
							fieldValue = dbObject.get(fieldName).toString();
							messageObject.setCtype(fieldValue);
						}
					}else if(fieldName.equals(TEXTBANK._FNAME)){
						if(null != dbObject.get(fieldName)){
							fieldValue = dbObject.get(fieldName).toString();
							messageObject.setFname(fieldValue);
						}
					}else if(fieldName.equals(TEXTBANK._DATE)){
						if(null != dbObject.get(fieldName)){
							fieldValue = dbObject.get(fieldName).toString();
							messageObject.setDate(fieldValue);
						}
					}else if(fieldName.equals(TEXTBANK._FOLDER)){
						if(null != dbObject.get(fieldName)){
							fieldValue = dbObject.get(fieldName).toString();
							messageObject.setFolder(fieldValue);
						}
					}else if(fieldName.equals(TEXTBANK._SUBJECT)){
						if(null != dbObject.get(fieldName)){
							fieldValue = dbObject.get(fieldName).toString();
							messageObject.setSubject(fieldValue);
						}
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return messageObject;
	}
	
	ArrayList<String> splitArray(String r){
		ArrayList<String> list = new ArrayList<String>();
		String[] arr= r.split(",");
		for(String temp : arr){
			list.add(temp);
		}
		return list;
	}
	
	public String MapReduceWithFolder(String folderName){
		MongoClient mongoClient  = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB("email");
		DBCollection dbCollection = db.getCollection("messages");
		 
		String map = 
		"function()"
		+ "{"
		+ "if(this.folder==\""+folderName+"\")"
		+ "{"
		+ "emit(\"count\",1);"
		+ "}"
		+ "}";
		        String reduce = 
		        "function(key,values)"
		        + "{"
		        + "var tot=0;"
		        + "for(var i=0;i<values.length;i++)"
		        + "{"
		        + "tot += values[i];"
		        + "}"
		        + "return tot;"
		        + "}";
		        
		        MapReduceCommand cmd = new MapReduceCommand(dbCollection, map, reduce, null, MapReduceCommand.OutputType.INLINE, null);
		        MapReduceOutput out = (dbCollection).mapReduce(cmd);
		        Iterable<DBObject> objects = out.results();
		        String count = "0";
		        for (DBObject o : objects){
		        	count =  o.get("value").toString();
		        }
		        return count;
		}
	
	public String MapReduceCountMails(){
		MongoClient mongoClient=null;
        String count = "0";
        try {
			mongoClient = new MongoClient( TEXTBANK.LOCALHOST , TEXTBANK.PORT );
			 DB db = mongoClient.getDB(TEXTBANK.DBNAME);
			 DBCollection dbCollection = db.getCollection(TEXTBANK.COLLECTION);
			String map = 
			"function()"
			+ "{"
			+ "emit(\"count\",1);"
			+ "}";
			        String reduce = 
			        "function(key,values)"
			        + "{"
			        + "var tot=0;"
			        + "for(var i=0;i<values.length;i++)"
			        + "{"
			        + "tot += values[i];"
			        + "}"
			        + "return tot;"
			        + "}";
			        
			        MapReduceCommand cmd = new MapReduceCommand(dbCollection, map, reduce, null, 
			        									MapReduceCommand.OutputType.INLINE, null);
			        MapReduceOutput out = (dbCollection).mapReduce(cmd);
			        Iterable<DBObject> objects = out.results();
			        for (DBObject o : objects){
			        	count =  o.get("value").toString();
			        }
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(mongoClient != null)
				mongoClient.close();
		}
        System.out.println(TEXTBANK.QUERY1+ " : " +count);
	 return count;
	}
	
	public String MapReduceCountMailsWithFolderName(String folderName){
		MongoClient mongoClient=null;
		 String count = "0";
		 System.out.println("\nInside Query2 : "+folderName + "\n");
		try {
			mongoClient = new MongoClient( TEXTBANK.LOCALHOST , TEXTBANK.PORT );
			 DB db = mongoClient.getDB(TEXTBANK.DBNAME);
			 DBCollection dbCollection = db.getCollection(TEXTBANK.COLLECTION);
			 
			String map = 
			"function()"
			+ "{"
			+ "if(this.folder==\""+folderName+"\")"
			+ "{"
			+ "emit(\"count\",1);"
			+ "}"
			+ "}";
			 
			        String reduce = 
			        "function(key,values)"
			        + "{"
			        + "var tot=0;"
			        + "for(var i=0;i<values.length;i++)"
			        + "{"
			        + "tot += values[i];"
			        + "}"
			        + "return tot;"
			        + "}";
			        
			        MapReduceCommand cmd = new MapReduceCommand(dbCollection, map, reduce, null, 
			        							MapReduceCommand.OutputType.INLINE, null);
			        MapReduceOutput out = (dbCollection).mapReduce(cmd);
			        Iterable<DBObject> objects = out.results();
			        
			        for (DBObject o : objects){
			        	count =  o.get("value").toString();
			        }
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(mongoClient != null)
				mongoClient.close();
		}
		System.out.println(TEXTBANK.QUERY2+ " : " +count);
		return count;
	}	

	public ArrayList<Message> Sort(String folderName,int order){
		ArrayList<Message> messages = new ArrayList<Message>();
		MongoClient mongoClient=null;
		 String count = "0";
		 System.out.println("\nInside Sort : "+folderName + ", order : " + order);
		try {
			mongoClient = new MongoClient( TEXTBANK.LOCALHOST , TEXTBANK.PORT );
			MongoDatabase _db = mongoClient.getDatabase(TEXTBANK.DBNAME);
			
			FindIterable<Document> iterable = _db.getCollection(TEXTBANK.COLLECTION).find(new Document(TEXTBANK._FOLDER, folderName)).
					sort(new Document("date",order));
			for(Document d : iterable){
				Message m = new Message();
				m.sender = d.getString("sender");
				m.recipients = (ArrayList<String>)d.get("recipients");
				m.cc = (ArrayList<String>)d.get("cc");
				m.text = d.getString("msg");
				m.mid = d.getString("mid");
				m.fpath = d.getString("fpath");
				m.bcc = (ArrayList<String>)d.get("bcc");
				m.to = (ArrayList<String>)d.get("to");
				m.replyto = d.getString("replyto");
				m.ctype = d.getString("ctype");
				m.fname = d.getString("fname");
				//Object obj = d.getDate("date");
				m.date = d.getString("date");
				
				//(Date) formatter.parse(d.getDate("date").toString()); 
				m.folder = d.getString("folder");
				m.subject = d.getString("subject");
				messages.add(m);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}finally {
			/*if(mongoClient != null)
				mongoClient.close();*/
		}
		return messages;
	}
	
}
