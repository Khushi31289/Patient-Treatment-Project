package mongodb;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.*;
import java.util.Locale;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class Message {
	String	_id;
	String sender;
	ArrayList<String> recipients;
	ArrayList<String> cc;
	String text;
	String mid;
	String fpath;
	ArrayList<String> bcc;
	ArrayList<String> to;
	String replyto;
	String ctype;
	String fname;
	String date;
	String folder;
	String subject;
	
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public ArrayList<String> getRecipients() {
		return recipients;
	}
	public void setRecipients(ArrayList<String> recipients) {
		this.recipients = recipients;
	}
	public ArrayList<String> getCc() {
		return cc;
	}
	public void setCc(ArrayList<String> cc) {
		this.cc = cc;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getFpath() {
		return fpath;
	}
	public void setFpath(String fpath) {
		this.fpath = fpath;
	}
	public ArrayList<String> getBcc() {
		return bcc;
	}
	public void setBcc(ArrayList<String> bcc) {
		this.bcc = bcc;
	}
	public ArrayList<String> getTo() {
		return to;
	}
	public void setTo(ArrayList<String> to) {
		this.to = to;
	}
	public String getReplyto() {
		return replyto;
	}
	public void setReplyto(String replyto) {
		this.replyto = replyto;
	}
	public String getCtype() {
		return ctype;
	}
	public void setCtype(String ctype) {
		this.ctype = ctype;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getDate() {
		return (date.toString());
	}
	/*public void setDate(String date) {
		this.date = date;
	}*/
	
	String GetCurrentTime(){
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.ENGLISH);
		return dateFormat.format(cal.getTime());
	}
	
	public void setDate(String date){
		//DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
		//DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		//get current date time with Date()
		//Date date1 = new Date();		//System.out.println(dateFormat.format(cal.getTime())); 
		try {
			this.date = GetCurrentTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public String getFolder() {
		return folder;
	}
	public void setFolder(String folder) {
		this.folder = folder;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}

	
	private static DBCollection _dbCollection = null;
	
	
	public Message(){
		if(_dbCollection == null)
			Connect();
		Init();
	}
	
	private void Init(){
		_id = "";
		sender = "";
		text = "";
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
	
	/*
	public void SetDate(String date){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
		try {
			this.date = format.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}*/
	
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
		basicObject.append("text", text);
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

	
	
/*private static DBCollection _dbCollection = null;
	
	
	public Message1(){
		if(_dbCollection == null)
			Connect();
		Init();
	}
	
	private void Init(){
		_id = "";
		sender = "";
		text = "";
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
		basicObject.append("msg", text);
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
*/
}
