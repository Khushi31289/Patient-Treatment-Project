package course;

public class Post {
	private int postid;
	private String title;
	private String author;
	private String msgcontent;
	private String dttime;
	private String emailid;
	public String getEmailid() {
		return emailid;
	}
	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}
	public int getPostid() {
		return postid;
	}
	public void setPostid(int postid) {
		this.postid = postid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getMsgcontent() {
		return msgcontent;
	}
	public void setMsgcontent(String msgcontent) {
		this.msgcontent = msgcontent;
	}
	public String getDttime() {
		return dttime;
	}
	public void setDttime(String dttime) {
		this.dttime = dttime;
	}
	public Post() {
		// TODO Auto-generated constructor stub
	}
}
