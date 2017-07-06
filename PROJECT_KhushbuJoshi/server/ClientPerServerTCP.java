package server;

import java.io.*;
import java.net.*;
import java.net.Socket;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.logging.FileHandler;

import server.ErrorHandler;
import server.PortHostName;

public class ClientPerServerTCP implements Runnable{
	 private Socket cSkt;
	    private int cCount;
	    
	    public ClientPerServerTCP(Socket cSkt,int cCount){
	        this.cSkt = cSkt;
	        this.cCount = cCount;
	    }
	    public void run() {
	        try{
	        	PrintWriter pOut = null;
	        	BufferedReader input = null;
	             if(cSkt != null && cSkt.isConnected()){
	                         pOut =
	                             new PrintWriter(cSkt.getOutputStream(), true);
	                         input = new BufferedReader(
	                             new InputStreamReader(cSkt.getInputStream()));
	                         String option = "";
	                         System.out.println("ClientPerServerTCP.run()");
	                         while (true) { //Reads data from server
	                        	 option = input.readLine() ;
	                             if(option != null){
	                                 System.out.println (option);
	                                 //For get list of files from server
		                             if(option.trim().toLowerCase().equals("ls")){
		                 				listFileNames(option, pOut);
		                 			}else if(option.trim().toLowerCase().startsWith("get ")){
		                 				System.out.println("get selected");
		                 				//Will send file data to client
		                 				GetFile(option.split(" ")[1].trim(), pOut);
		                 				break; //once done ecit client
		                 			}else if(option.trim().toLowerCase().startsWith("put ")){
		                 				PutFile(option.split(" ")[1].trim(), input, pOut); 
		                 				//Put data from client to server
		                 				break;
		                 			}
		                 			else if (option.equals("--"))
		                             {
		                                 break;
		                             }
		                 			else{
		                 				continue;
		                 			}
	                             }else{
	                            	break; // was break changed now
	                            	 //changed now break;
	                             }
	                         }
	                     }
	             //Closing all the readers and writers that were opened
	             	closeAllStreams(pOut,input);
	             }catch(IOException ex){
	            	 if(ex.getMessage().contains("Connection reset")){
	            		 //System.out.println("");
	            	 }else{
	            		 System.out.println("Error from here 0");	 
	            		 ErrorHandler.printError(ex); //Exception occured
	            	 }
	             }catch(Exception ex){
	            	 ErrorHandler.printError(ex); //Exception occured
	             }
	        	 finally{
	                    System.out.println("Client"+cCount +" Socket Closed.");
	            	    // commented now
	             }
	         }
	    
	    private void listFileNames(String option, PrintWriter pOut){
    	    try {
			    	if(option.trim().equals("ls")){
			    		//Get list of files in server by default searches in root(.) directory 
			    		String listOfFiles= GetListOfFiles.getAllFiles();
			    		pOut.println(listOfFiles);
			    	}
				} catch (Exception ex) {
					ErrorHandler.printError(ex);
				}
			}
			
	         private void closeClient(Socket cSkt) {
	             try{
	            	 if(cSkt != null){
	            		 cSkt.close(); // Close the client socket object
	            		 System.out.println("Client"+ cCount +" Shutting down.");
	            	 }
	             }catch(IOException ex){
	            	 ErrorHandler.printError(ex); //Exception occured while closing client 
	                 System.exit(1); // Exit the client program
	             }catch(Exception ex){
	            	 ErrorHandler.printError(ex); //Exception occured while closing client 
	                 System.exit(1); // Exit the client program
	             }
	         }
	     	
	         
	         private void GetFile(String fileName ,PrintWriter pOut ) {
	    		// TODO Auto-generated method stub
	    		String isFile="IS_A_FILE";
				File myFile = new File(fileName);
	    		if( myFile .isFile() == true){
	    				pOut.println(isFile); // if no file found then send NOT_A_FILE command to client
	    				readAndSendFileContents(fileName, pOut);
	    			}else{
	    				isFile = "NOT_A_FILE";
	    				pOut.println(isFile);
	    			}
    		}
	      // putting file from client to server
	         private void PutFile(String fileName, BufferedReader input, PrintWriter pOut) {
	     		// TODO Auto-generated method stub
	     			try {
	     				boolean makeFile =  false;
	     				if(makeFile == false){
	     					//will always be true
	     					if("IS_A_FILE".equals(GetListOfFiles.ISAFILE)){
	     						System.out.println("Waiting for Client to send size of file to be transferred");
	     						String sizeOfFile = input.readLine(); //
	     						System.out.println("File Size: " +  sizeOfFile);
	     						long noOfChunks = 0;
	     						if(sizeOfFile != null || sizeOfFile != ""){
	     							noOfChunks = Long.parseLong(sizeOfFile);
	     						}
	     						int bytesRead;
	    						byte[] contents = new byte[8*1024];
	    						String fName = fileName;
	    						fName = fileName;
	    						FileChannel inChannel = null;
	    						FileOutputStream fos = new FileOutputStream(fName ); //file op stream
	    				        BufferedOutputStream bos = new BufferedOutputStream(fos);//buf  op stream to write into file
	    				        InputStream is = cSkt.getInputStream();
	    				        boolean success = true;
	    						try { 
	    							inChannel = fos.getChannel();
	    				            FileLock lock = inChannel.tryLock();//changed here 2
	    							int cnt= 0;
	    							bytesRead=is.read(contents); // read data from client
	    							while((bytesRead=is.read(contents))!=-1){
	    								//Write in the file
										bos.write(contents, 0, bytesRead);
	    							}
	    							lock.release(); //Release the lock
	    						} catch (Exception e) {
	     							ErrorHandler.printError(e);
	     						}finally{
	     							try {
	     								//CLose all streams,channels
     									bos.flush();
	     								if(fos != null)
	     									fos.close();
	     								if(inChannel != null)
	    				                    inChannel.close();
	     								if(bos != null)
	     									bos.close();
	     								if(is != null)
	     									is.close(); 
	     							} catch (Exception e) {
	     								success = false;
	     								ErrorHandler.printError(e);
	     							} 
	     							if(success  == true){
	     								System.out.println("Received file from client successfully..!");
	     							}else{
	     								System.out.println("Unable to receive the file from client..!");
	     							}
	     						}	
	     					}else if("IS_A_FILE".equals(GetListOfFiles.NOTAFILE)){
	     						System.out.println("Sorry, No such file exists");
	     					}
	     					
	     				}else{
	     					System.out.println("No Such file on server");
	     					//return;
	     				}
	     				//System.out.println(input.readLine().trim());
	     				
	     			} catch (IOException e) {
	     				ErrorHandler.printError(e);
	     			}catch(Exception e){
	     				ErrorHandler.printError(e);
	     			}
	         }
	         
	        private void readAndSendFileContents(String fileName ,PrintWriter pOut ) {
	         byte[] contents;
			 	String line = null;
		 		File file = new File(fileName);
		 		System.out.println(fileName);
				 try {
		 		 FileInputStream fis = new FileInputStream(file);
		 		 FileChannel inChannel = null; 
		 		 BufferedInputStream bis = new BufferedInputStream(fis);
		 		 long fileLength  = file.length();
			 	    System.out.println("File size: " + file.length());
			 	    pOut.println( file.length()); // sending file size
			 	      OutputStream os = cSkt.getOutputStream();
				long current = 0;
					inChannel = fis.getChannel(); // Get file channel for locking
					FileLock lock = inChannel.tryLock(0L, Long.MAX_VALUE,true);//acquire lock for write
					  int size = 8*1024; //8 KB each data size
					 while(current!=fileLength){ 
				            if(fileLength - current >= size)
				                current += size;    
				            else{ 
				                size = (int)(fileLength - current); 
				                current = fileLength;
				            } 
				            contents = new byte[size]; 
				            int read = bis.read(contents, 0, size); // Read file contents
							     os.write(contents); //Write data into file
				        }   
				 	lock.release(); //Release lock
			     //closing all streams
			     try {
					fis.close();
					if(inChannel !=null)
	                    inChannel.close();
					 bis.close();
					 os.close(); 
					 System.out.println("File Sending Successful..!");
				} catch (Exception e) {
					ErrorHandler.printError(e);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				ErrorHandler.printError(e);
			}   
	        }
	        
	 	private void closeAllStreams(PrintWriter pOut, BufferedReader input){
	 		try{
	 			if(input != null)
	 				input.close();	//Closing the Reader
	 			if(pOut != null)
	 				pOut.close();	//Closing the Writer
	 		}catch(IOException ex){
	 			ErrorHandler.printError(ex); //Exception occured in closing the RD/WR streams
	         }catch(Exception ex){
	        	 ErrorHandler.printError(ex); //Exception occured in closing the RD/WR streams
	         }
	 	}
	 	
	 } 
