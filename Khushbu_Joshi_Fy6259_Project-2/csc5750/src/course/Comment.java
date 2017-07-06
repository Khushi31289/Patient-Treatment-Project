package course;

public class Comment {
	private int commentid;
	private int postid;
	private String content;
	private String dttime;
	private String commentauthor;
	public String getCommentauthor() {
		return commentauthor;
	}
	public void setCommentauthor(String commentauthor) {
		this.commentauthor = commentauthor;
	}
	public Comment() {
		// TODO Auto-generated constructor stub
	}
	public int getCommentid() {
		return commentid;
	}
	public void setCommentid(int commentid) {
		this.commentid = commentid;
	}
	public int getPostid() {
		return postid;
	}
	public void setPostid(int postid) {
		this.postid = postid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDttime() {
		return dttime;
	}
	public void setDttime(String dttime) {
		this.dttime = dttime;
	}
}
