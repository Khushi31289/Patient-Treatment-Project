package course;

import java.sql.SQLException;

public class GetErrorMsg {

	public static void printError(Exception e) {
		// TODO Auto-generated method stub
		e.printStackTrace();
		System.out.println("Exception : "+ e.getMessage() + ", Class Name: " 
		+  e.getClass());
	}
	
}
