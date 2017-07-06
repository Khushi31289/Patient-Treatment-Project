package client;


public class ClientSideFTP {
    public static void main(String[] args) {
    	try{
			//Creating TCP client
    		ClientSideTCP clientTCP = new ClientSideTCP();
    		clientTCP.makeTCPClient();
    		
    		//Closing TCP Socket
    		clientTCP.closeAll();
	        }catch(Exception ex){
	        	ErrorHandler.printError(ex);
	        }
	}
}
