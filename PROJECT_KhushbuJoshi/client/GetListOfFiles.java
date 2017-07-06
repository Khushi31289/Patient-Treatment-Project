package client;

import java.io.File;

public class GetListOfFiles {
	public static String ISAFILE ="IS_A_FILE";
	public static String NOTAFILE ="NOT_A_FILE";
	public static String getAllFiles(String fileName) {
		File currentDir;
		//FOr client will search in myClient directory name
		if(!fileName.equals("")){
			currentDir = new File(fileName);
		}
		else{
			currentDir = new File(".");
		}
		//returns comma seperated name of files inside myClient Directory on client
        File[] filesList = currentDir.listFiles();
        String ListOfFiles ="";
       // System.out.println("Total #Files found: "+filesList.length);
        if(filesList != null){
        for(File f : filesList){
	        	if(ListOfFiles.length() > 0)
	                ListOfFiles +=  ", "+ f.getName();
	        	else
	        		ListOfFiles += f.getName();
	        }
        }
        //System.out.println("Files are listed below:\n" +
        //					ListOfFiles);
        return(ListOfFiles );
	}
}
