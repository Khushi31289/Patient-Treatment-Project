package server;

import java.io.File;

public class GetListOfFiles {
	public static String ISAFILE ="IS_A_FILE";
	public static String NOTAFILE ="NOT_A_FILE";
	public static String getAllFiles() {
		File currentDir;
		currentDir = new File(".");
		//For server searches in the server's root directory (.)
        File[] filesList = currentDir.listFiles();
        String ListOfFiles ="";
        for(File f : filesList){
        	if(ListOfFiles.length() > 0)
                ListOfFiles +=  ", "+ f.getName();
        	else
        		ListOfFiles += f.getName();
        }
        return(ListOfFiles );
	}
}
