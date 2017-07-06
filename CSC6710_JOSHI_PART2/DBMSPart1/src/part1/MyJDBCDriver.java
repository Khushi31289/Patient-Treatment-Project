package part1;

import java.sql.*;
import com.mysql.jdbc.Statement;

public class MyJDBCDriver{
	static Connection conn; 
	static Statement stmt;
	static ResultSet resultSet;
	static int queryOP;
	public MyJDBCDriver() {
		conn = null; stmt = null;
		// TODO Auto-generated constructor stub
	}
	
	static void getConn(){
		 try {
			conn = DriverManager.getConnection(TextBank.DB_URL,TextBank.UNM, TextBank.PWD);
		} catch (SQLException e) {
			GetErrorMsg.printError(e);
		}
	}
	
	static void getStatement(){
		 try {
			 stmt = (Statement)conn.createStatement();
		 } catch (SQLException e) {
			GetErrorMsg.printError(e);
		}
	}
	
	static void closeAll(){
		closeStatement();
		closeConnection();
	}

	static void getResultSet(String query){
		 try {
			 System.out.println("Statement:" + stmt);
			 if(stmt != null && !stmt.isClosed()){
				 resultSet = stmt.executeQuery(query);
		 	}
		 } catch (SQLException e) {
			GetErrorMsg.printError(e);
		}
	}
	
	static void getResultSetForExecuteUpdate(String query){
		 try {
			 System.out.println("Statement:" + stmt);
			 if(stmt != null && !stmt.isClosed()){
				 queryOP = stmt.executeUpdate(query);
		 	}
		 } catch (SQLException e) {
			GetErrorMsg.printError(e);
		}
	}
	
	private static void closeStatement(){
		 try {
			 if(stmt != null){
				 if(!stmt.isClosed()){
					 stmt.close();
				 }
			 }
		 } catch (SQLException e) {
			GetErrorMsg.printError(e);
		}
	}
	
	private static void closeConnection(){
		 try {
			 if(conn != null){
				 if(!conn.isClosed()){
					 conn.close();
				 }
			 }
		 } catch (SQLException e) {
			GetErrorMsg.printError(e);
		}
	}
	
}
