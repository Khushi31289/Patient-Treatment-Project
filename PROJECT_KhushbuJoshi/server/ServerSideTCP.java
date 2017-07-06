package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import server.ErrorHandler;
import server.PortHostName;


public class ServerSideTCP {
    private int portNo;
    private static ServerSocket sSkt;
   

    public ServerSideTCP(){
        System.out.println("Initializing Server Object");
        this.sSkt = null;
        this.portNo = 6259;
    }
    public static void main(String[] args) throws IOException, UnknownHostException{
    	ServerSideTCP sObj = new ServerSideTCP();
         sObj.createServerSocket(); // creating skt
            if(sObj.sSkt.isBound()){ 
               System.out.println("Awaiting connection from Client");
               int cCount = 0; 
               while (true) { 
                   cCount++; 
                    try{
                     Socket cSkt = sObj.acceptConnection(cCount); //Every new client accept connection
                   Thread clientThread=null;
                   clientThread= new Thread(new ClientPerServerTCP(cSkt,cCount)); //1 thread per client
                   clientThread.start(); //start client thread
                  }catch(Exception ex){
                	  ErrorHandler.printError(ex);
                  }
               } 
            }
        sObj.closeServer();
        //Closing the server socket
        if(sObj.sSkt.isClosed())
             System.out.println("Server Shutting down.\nServer Socket Closed.");
     
    }
    
    private void closeServer(){
        try{
            this.sSkt.close();
            //Closing the Server object
            if(this.sSkt.isClosed()){
                System.out.println("Closing Server Socket");
            }
        }catch(IOException ex){
            //Exception in closing server so print exception and exit
        	ErrorHandler.printError(ex);
            System.exit(1);
        }catch(Exception ex){
            //Exception in closing server so print exception and exit
        	ErrorHandler.printError(ex);
            System.exit(1);
        }
    }
    
   /*Creates a server socket, bound to the specified port.*/
    private void createServerSocket()
    {
        //Enclosing the system call in try catch
        try{
            this.sSkt = new ServerSocket(this.portNo); 
            //Creating Server Socket and port assignment
            
            if(this.sSkt.isBound()){ //Checking whether the socket is bound/not
                System.out.println("Binding to Port " 
                        +this.portNo  +" is complete");
            }
        }catch(IOException ex){
         // IOException  if an I/O error occurs when opening the socket.
        	ErrorHandler.printError(ex);
             System.exit(1);
        }catch(Exception ex){
        	ErrorHandler.printError(ex);
            System.exit(1);
        }
    }
    
    private Socket acceptConnection(int cCount){
        Socket cSkt = null;
        try{
            cSkt = this.sSkt.accept(); //accept connection
            if(cSkt.isConnected()){
                System.out.println("Client"+cCount+" is connected to Server..!");
            }
         }catch(IOException ex){
        	 ErrorHandler.printError(ex);
             }catch(Exception ex){
            	 ErrorHandler.printError(ex);
           }
        return cSkt;
    }
}
