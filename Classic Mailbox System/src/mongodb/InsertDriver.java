package mongodb;
import java.net.UnknownHostException;

public class InsertDriver {

	public static void main(String[] args) throws UnknownHostException {
		Message msg = new Message();
		msg.sender = "Naima";
		msg.AddRecipients("Sunny");
		msg.AddCc("Abbu");
		msg.text = "Abbu, I love you :)";
		msg.replyto = "Abbu";
		msg.folder = "inbox";
		msg.subject = "nothing";
		
		msg.Insert();
	}

}
