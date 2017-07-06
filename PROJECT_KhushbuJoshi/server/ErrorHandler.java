package server;


public class ErrorHandler {
	public final static String ISAFile = "IS_A_FILE";
	public final static String NOTAFile = "NOT_A_FILE";
	public static void printError(Exception obj){
        // Print the class name of exception, along with the exception object
          System.err.println("Class Name: "+
        		  	obj.getClass().getName()+", Description: "+obj );
    }
	
}
