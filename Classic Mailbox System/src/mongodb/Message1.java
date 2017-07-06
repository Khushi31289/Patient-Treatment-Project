package mongodb;

import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Locale;

import java.util.ArrayList;
import java.util.Date;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class Message1 {
	private String id;
	public String sender;
	public String msg;
	public String mid;
	public String fpath; 
	public String replyto;
	public String ctype;
	public String fname;
	private Date date;
	public String folder;
	public String subject;

	
	private ArrayList<String> recipients;
	private ArrayList<String> cc;
	private ArrayList<String> bcc;
	private ArrayList<String> to;
	
	
	private static DBCollection _dbCollection = null;
	
	
	public Message1(){
		if(_dbCollection == null)
			Connect();
		Init();
	}
	
	private void Init(){
		id = "";
		sender = "";
		msg = "";
		mid = "";
		fpath = "";
		replyto = "";
		ctype = "";
		fname = "";
		folder = "";
		subject = "";
		
		recipients = new ArrayList<>();
		cc = new ArrayList<>();
		bcc = new ArrayList<>();
		to = new ArrayList<>();
	}
	
	private void Connect(){
		MongoClient mongoClient  = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB("email");
		_dbCollection = db.getCollection("messages");
	}
	
	public void Insert()
	{
		_dbCollection.insert(getObject());
	}
	
	public void SetDate(String date){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
		try {
			this.date = format.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public void AddRecipients(String s){
		recipients.add(s);
	}

	
	public void AddCc(String s){
		cc.add(s);
	}

	
	public void AddBcc(String s){
		bcc.add(s);
	}

	
	public void AddTo(String s){
		to.add(s);
	}
	
	
	private BasicDBObject getObject(){
		BasicDBObject basicObject = new BasicDBObject();
		
		basicObject.append("sender", sender);
		basicObject.append("recipients", recipients);
		basicObject.append("cc", cc);
		basicObject.append("msg", msg);
		basicObject.append("mid", mid);
		basicObject.append("fpath", fpath);
		basicObject.append("bcc", bcc);
		basicObject.append("to", to);
		basicObject.append("replyto", replyto);
		basicObject.append("ctype", ctype);
		basicObject.append("fname", fname);
		basicObject.append("date", date);
		basicObject.append("folder", folder);
		basicObject.append("subject", subject);
		return basicObject;
	}
}
