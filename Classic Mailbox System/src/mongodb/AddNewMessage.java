package mongodb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;



/**
 * Servlet implementation class AddNewMesage
 */
@WebServlet("/AddNewMessage")
public class AddNewMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	final static String SUCCESSMSG = "";
	final static String FAILUREMSG = "";
	String	_id;
	String sender="";
	String recipients="";
	String cc="";
	String text="";
	String mid="";
	String fpath="";
	String bcc="";
	String to="";
	String replyto="";
	String ctype="";
	String fname="";
	String date="";
	String folder="";
	String subject="";
	
    public AddNewMessage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setAttribute("FolderNames", getFolderNames());
		getFormData(request);
		request.getRequestDispatcher("/AddMessage.jsp").forward(request, response);
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	private void getFormData(HttpServletRequest req) {
		// TODO Auto-generated method stub
		boolean errorStatus = false;
		try{
			recipients = req.getParameter("txtRecipient");
			cc = req.getParameter("txtCCEmail");
			bcc = req.getParameter("txtBcc");
			to = req.getParameter("txtTo");
		//	_id = req.getParameter("_id");
			sender = req.getParameter("txtSender");
			text = req.getParameter("txtMsg");
			mid = req.getParameter("txtMid");
			fpath = req.getParameter("txtPath");
			replyto = req.getParameter("txtReplyTo");
			ctype = req.getParameter("txtCType");
			fname = req.getParameter("fname");
			//date = req.getParameter("txtDate").toString();
			folder = req.getParameter("selFolder");
			subject = req.getParameter("txtSubject");
		/*	System.out.println("ID: "+_id + "\n Recipients: "+recipients + "\ncc: "+cc + "\nbcc: "+bcc + "\nto: "+to + 
								"\nsender: "+sender+ "\ntext: "+ text+"\nmid: " +mid	+ "\nFPath: " +fpath + "\nReply to: " +replyto+
								"ctype: " + ctype+ "fname: " + fname + "\ndate: "+ date + "\nFolder: " + folder + "\nSubject: "+ subject);
			*/
			System.out.println( "msg: "+ text);
			Message msg = new Message();
				
			if(	checkValue(recipients) && checkValue(bcc) && checkValue(cc) && checkValue(subject) &&  
					checkValue(text) && checkValue(folder) && checkValue(to) && checkValue(sender )) 
			{
				msg.AddRecipients(recipients);
				msg.AddBcc(bcc);
				msg.AddCc(cc);
				msg.setSender(sender);
				msg.setSubject(subject);
				msg.setText(text);
				msg.setFolder(folder);
				msg.AddTo(to);
				msg.setReplyto(replyto);
				msg.setMid(TEXTBANK.MIDVAL);
				msg.setDate("");
				msg.setCtype(TEXTBANK.CTYPEVAL);
				int temp = (int) (Math.random() * 1000 + 1);
				msg.setFname(String.valueOf(temp));
				msg.Insert();
				errorStatus = false;
			}
		}catch(Exception ex){
			errorStatus = true;
		}
		if(errorStatus)
			req.setAttribute("message", FAILUREMSG);
		else
			req.setAttribute("message", SUCCESSMSG);
	}
	
	protected boolean checkValue(String val){
		 boolean isNumb = false;
		    try{
				if(val != null && val != ""){
					return true;
				}
				else
				{
					return false;
				}
		    }catch(Exception e){
		    	return true;
		    }
	}
	
	protected Map<String, String> getFolderNames(){
		MongoClient mongoClient = null;
		Map<String,String> foldersMap = null;
		try {
			mongoClient = new MongoClient( TEXTBANK.LOCALHOST , TEXTBANK.PORT );
			 MongoDatabase db = mongoClient.getDatabase(TEXTBANK.DBNAME);
			 MongoCursor<String>  iterator =  db.getCollection(TEXTBANK.COLLECTION).
					 distinct(TEXTBANK._FOLDER, String.class).iterator();
			 foldersMap = new LinkedHashMap<String,String>();
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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(mongoClient != null)
				mongoClient.close();
		}	
		return(foldersMap);
	}
}
