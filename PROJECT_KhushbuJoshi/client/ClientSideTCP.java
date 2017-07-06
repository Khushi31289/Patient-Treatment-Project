package client;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

import javax.net.ssl.HostnameVerifier;

import server.ErrorHandler;

public class ClientSideTCP{
    static int portNo; //Port No as last 4 digits of Access id    
    static String sHostname;
    Socket cSkt;
	 public ClientSideTCP(){
         System.out.println("Initializing Client Object");
         //Initializing client 
         cSkt = null;
         sHostname = PortHostName.getHOSTNAME();
         portNo = PortHostName.getPORT();
      }
	 
	 public void makeTCPClient(){
		 assignPortNumber();
         if(cSkt.isConnected() && cSkt != null ){
             System.out.println("Connection to Server successful..!");
             sendCommands();
         }
	 }
	 private void sendCommands() {
		// TODO Auto-generated method stub
		   try{
                 String userInput;
                 System.out.println ("Server ready to receive the data"); //Print a prompt to the user
 				System.out.println("Select fom the below choices:"
						+ "\nls"
						+ "\n!ls"
						+ "\nget filename"
						+ "\nput filename"
						+ "\nTo Exit type -- ");
	             getChoice();
	 	}catch(UnknownHostException ex){
            ErrorHandler.printError(ex);
         }catch(IOException ex){
        	 ErrorHandler.printError(ex);
        }catch(Exception ex){
        	ErrorHandler.printError(ex);
        }
	}

	 public void getChoice() throws IOException{
		 BufferedReader br = null;
		 BufferedReader input = null;
		 PrintWriter pOut = null;
		 try {
				 while(true){
					 if(true){
					 System.out.println("Enter your Choice: ");
					 // Buff reader to get i/p from user
				 		br = 
			                new BufferedReader(new InputStreamReader(System.in));
				 		//to write to skt o/p stream
						pOut =
		                        new PrintWriter(cSkt.getOutputStream(), true);
						//For skt i/p stream
				 		input =
						        new BufferedReader(new InputStreamReader(cSkt.getInputStream()));
				 		String a1 = "";
				 		//Reading data from user
						a1 = br.readLine();
						//If ls then get list of files from server
						if(a1.toLowerCase().trim().equals("ls")){
							System.out.println(listFilesFromServer(a1,input,pOut));
							continue;
						}
						//!ls list files from client
						else if(a1.toLowerCase().trim().equals("!ls")){
							listFilesFromClient(a1);
							continue;
						}
						//on get command, get file from server
						else if(a1.trim().startsWith("get ")){
							if(a1.trim().split(" ").length >= 2){
								getFileFromServer( a1.trim(),input,pOut);
								break;
							}
							else{
								System.out.println("Invalid Choice, re-enter");
							}
						}//Put will call method to put file onto server from client and then will exit client
						else if(a1.trim().startsWith("put ")){
							if(a1.trim().split(" ").length >= 2){
								PutFileonServer( a1.trim(),input,pOut);
								break;
							}
							else{
								System.out.println("Invalid Choice, re-enter");
							}
						}//on -- exit client
						else if(a1.trim().equals("--")){
							break;
						}
						else{
							System.out.println("Invalid Choice,re-enter");
							continue;
						}
					 }
			 }
		 } catch (IOException ex) {
				// TODO Auto-generated catch block
				 if(ex.getMessage().contains("Connection reset")){
	        	 }else{
	        		 ErrorHandler.printError(ex); //Exception occured
	        	 }
			}catch(Exception ex){
				ErrorHandler.printError(ex);
			}finally{
				pOut.close();
				input.close();
				br.close();
		        //closeAll();
			}
	 }
	 
	//This will get transfer the file from client to server
		private void PutFileonServer(String fileName, BufferedReader input, PrintWriter pOut) {
		// TODO Auto-generated method stub
			String tempName  = fileName.split(" ")[1].trim();
			//Will search inside client directory myClient
			if(!GetListOfFiles.getAllFiles(PortHostName.CLI_DIR).contains(tempName)){
				System.out.println("No such file exists on client");
			}
			else{
				//Checking if file already exists on server
				String listOfFiles = listFilesFromServer("ls",input,pOut);
				boolean doPut = true;
				System.out.println("Inside Put " + fileName.split(" ")[1].trim());
				if(listOfFiles.contains(fileName.split(" ")[1].trim())){
					System.out.println("File already exists on server, Overwrite: Y ?");
					String overwrite = null;
					BufferedReader br1 = null;
					try {
						br1 = 
				                new BufferedReader(new InputStreamReader(System.in));
						overwrite = br1.readLine();
					} catch (IOException e) {
					}
					//If user inputs Y then it will overwrite the file else will exit client
					if(overwrite  !=null){
						if(overwrite.toString().toLowerCase().equals("y")){
							System.out.println("You choose to overwrite the file.");
							doPut = true;
						}else{
							System.out.println("You choose not to overwrite the file.");
							doPut = false;
							
						}
					}
				}
				if(doPut == true){
					// sendFileToServer
					pOut.println(fileName); //send the command
					File myFile = new File(tempName);
					//Call method to read and send file contents to server
					readAndSendFileContents(tempName, pOut);
				}
			}
	}
		
		//get file from server into client machine
		private void getFileFromServer(String fileName, BufferedReader input, PrintWriter pOut) {
		// TODO Auto-generated method stub
			try {
				boolean makeFile = checkIfFileExists(fileName.split(" ")[1].trim());
				//makeFile - false then it will contact server and get file from server else will exit the client
				
				if(makeFile == false){
					pOut.println(fileName);  // to send the get command to server
					System.out.println(fileName);
					String isAFile = input.readLine(); 
					System.out.println(isAFile);
					//if isAFile = IS_A_FILE that means there is such file on server
					if(isAFile.equals(GetListOfFiles.ISAFILE)){
						System.out.println("Waiting for server to send size of the file");
						String sizeOfFile = input.readLine(); //getting filesize from server
						System.out.println("Size of file = " +  sizeOfFile);
						long noOfChunks = 0;
						if(sizeOfFile != null || sizeOfFile != ""){
							noOfChunks = Long.parseLong(sizeOfFile);
						}
						int bytesRead;
						byte[] contents = new byte[8*1024];
						//Will put the file in client directory with name myClient.
						String fName = PortHostName.CLI_DIR + fileName.split(" ")[1].trim();
						FileChannel inChannel = null; //file channel for locking
						FileOutputStream fos = new FileOutputStream(fName ); 
				        BufferedOutputStream bos = new BufferedOutputStream(fos); //write into a file
				        InputStream is = cSkt.getInputStream();
				        boolean success = true;
						try { 
							//To get the file channel
							inChannel = fos.getChannel();
							//Apply loc to the file
				            FileLock lock = inChannel.tryLock();
				            //Read from Skt i/p stream
					        while((bytesRead=is.read(contents))!=-1){
					        	//Writing onto file on client
									bos.write(contents, 0, bytesRead);
					        }
					        //Release lock after writing data onto file
							lock.release();
						} catch (Exception e) {
							ErrorHandler.printError(e);
						}finally {
							try{
								//Close all streams and file channel
								bos.flush();
								fos.close();
								if(inChannel != null)
				                    inChannel.close();
								bos.close();
								is.close();
							} catch (Exception e) {
								success = false;
								ErrorHandler.printError(e);
							} 
							if(success  == true){
								System.out.println("File uploaded on Client successfully..!");
							}
						}
					}else if(isAFile.equals(GetListOfFiles.NOTAFILE)){
						System.out.println("Sorry, No such file exists on server");
					}
					
				}else{
					return;
				}
				
			} catch (IOException e) {
				ErrorHandler.printError(e);
			}catch(Exception e){
				ErrorHandler.printError(e);
			}
	}

		private boolean checkIfFileExists(String fileName) throws IOException {
			// TODO Auto-generated method stub
			File myFile = new File(fileName);
			BufferedReader br1;
			//Checks if file exists on client inside name myClient
			if(GetListOfFiles.getAllFiles(PortHostName.CLI_DIR).contains(fileName)){
				System.out.println("File already exists, Overwrite: Y/N ?");
				String overwrite = null;
				try {
					br1 = 
			                new BufferedReader(new InputStreamReader(System.in));
					overwrite = br1.readLine();
					//br1.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					ErrorHandler.printError(e);
				}
				if(overwrite  !=null){
					if(overwrite.toString().toLowerCase().equals("y")){
						System.out.println("You choose to overwrite the file.");
						return false;
					}else{
						System.out.println("You choose not to overwrite the file.");
						return true;
					}
				}
			}
				
			return false;
		}

		private void listFilesFromClient(String a1) {
		// TODO Auto-generated method stub
			System.out.println(GetListOfFiles.getAllFiles(PortHostName.CLI_DIR));
	}

		public String listFilesFromServer(String strChoice, BufferedReader input,
				PrintWriter  pOut){
			//Create packet to be send the server
			try {
				//sends ls and receives back the list of files from server
				pOut.println(strChoice);
				return (input.readLine().trim());
			} catch (Exception e) {
				ErrorHandler.printError(e);
			}
			return "";
		}
		

        private void readAndSendFileContents(String fileName ,PrintWriter pOut ) {
	         byte[] contents;
	       //pOut.write("YES"); //Sending confirmation that its a file
			 	String line = null;
			 	
			 	//Client directory will be myClient, Here it will search for the files on client
			 	File file = new File(PortHostName.CLI_DIR+fileName);
		 		pOut.println(file.length());
		 		FileChannel inChannel = null;
		 		FileInputStream fis = null;
		 		OutputStream os= null;
				 try {
		 		  fis = new FileInputStream(file);
		 		  inChannel = fis.getChannel(); // Get file channel for locking file
		 		  
		 		  //Lock the file
		 		 FileLock lock = inChannel.tryLock(0, Long.MAX_VALUE, true);
		            BufferedInputStream bis = new BufferedInputStream(fis);
		            
		 		long fileLength  = file.length();
		            // FileReader reads text files in the default encoding.
			 	    System.out.println("File size: " + file.length());
			 	    pOut.println( file.length()); // sending file size to server
			 	    os = cSkt.getOutputStream();
			 	     int size = 8*1024; //Each Chunk is of 8 KB
			 	    long current = 0;
					 while(current!=fileLength){ 
				            if(fileLength - current >= size)
				                current += size;    
				            else{ 
				                size = (int)(fileLength - current); 
				                current = fileLength;
				            } 
				            contents = new byte[size]; 
				            //Reading contents from skt stream and writing to skt o/p stream
				            int read = bis.read(contents, 0, size);
							     os.write(contents);
				        }   
				 	lock.release(); // Releasing the lock acquired for file
			  
			     try {
			    	   //closing all streams
			    	 lock.release();//release the lock
					  bis.close(); 
					 if(inChannel != null){
						 inChannel.close(); // Close channel
					 }
					 fis.close(); // close FIle i/p stream
					 os.close(); //Fix for Socket close exception
					 System.out.println("File Send to server successfully..!");
				} catch (IOException e) {
					ErrorHandler.printError(e);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				ErrorHandler.printError(e);
			}catch (Exception e) {
				// TODO Auto-generated catch block
				ErrorHandler.printError(e);
			}      
	   }
		
	public void closeAll() {
	        try{ //Enclosing System call into try catch
	        	if(cSkt != null){
	        		cSkt.close(); //closing skt
	        		System.out.println("Client is Shutting down.");
	        	}
	        }catch(IOException ex){
	             ErrorHandler.printError(ex);
	        }   
	}
	
    private void assignPortNumber()
    {
        try{ //Enclosing System call into try catch
        	//Creating socket and assigining port 
            cSkt = new Socket(this.sHostname, this.portNo); 
            //UDP client skt
        }catch(UnknownHostException ex){
            ErrorHandler.printError(ex);
        }catch(IOException ex){
             ErrorHandler.printError(ex);
        }catch(Exception ex){
        	ErrorHandler.printError(ex);
        }
    }
}
