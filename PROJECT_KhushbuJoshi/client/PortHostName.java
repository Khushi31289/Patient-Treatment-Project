package client;

import java.net.InetAddress;

public class PortHostName {
	private static int PORT = 6259; //last 4 digit access id
	private static String HOSTNAME = "141.217.48.36"; //"127.0.0.1"; 
	public static String CLI_DIR = "./myClient/";
	public static int getPORT() {
		return PORT;
	}
	public static String getHOSTNAME() {
		return HOSTNAME;
	}

}
	