package course;


import java.io.BufferedReader;  
 
import java.io.File;  
import java.io.FileReader; 
import java.util.ArrayList;
 
public class SQLReader
{ 
    private static ArrayList<String> QueryList = null;
    public  ArrayList<String> createQueries(String path)  
    { 
    	System.out.println("Inside SQLReader.createQueries()");
        String queryLine =      new String();
        StringBuffer sBuffer =  new StringBuffer();
        QueryList =         new ArrayList<String>();
         
        try 
        {  
            FileReader fr =     new FileReader(new File(path));       
            BufferedReader br = new BufferedReader(fr);  
       
            //read the SQL file line by line
            while((queryLine = br.readLine()) != null)  
            {  
                // ignore comments beginning with #
                int indexOfCommentSign = queryLine.indexOf('#');
                if(indexOfCommentSign != -1)
                {
                    if(queryLine.startsWith("#"))
                    {
                        queryLine = new String("");
                    }
                    else
                        queryLine = new String(queryLine.substring(0, indexOfCommentSign-1));
                }
                // ignore comments beginning with --
                indexOfCommentSign = queryLine.indexOf("--");
                if(indexOfCommentSign != -1)
                {
                    if(queryLine.startsWith("--"))
                    {
                        queryLine = new String("");
                    }
                    else
                        queryLine = new String(queryLine.substring(0, indexOfCommentSign-1));
                }
                // ignore comments surrounded by /* */
                indexOfCommentSign = queryLine.indexOf("/*");
                if(indexOfCommentSign != -1)
                {
                    if(queryLine.startsWith("#"))
                    {
                        queryLine = new String("");
                    }
                    else
                        queryLine = new String(queryLine.substring(0, indexOfCommentSign-1));
                     
                    sBuffer.append(queryLine + " "); 
                    // ignore all characters within the comment
                    do
                    {
                        queryLine = br.readLine();
                    }
                    while(queryLine != null && !queryLine.contains("*/"));
                    indexOfCommentSign = queryLine.indexOf("*/");
                    if(indexOfCommentSign != -1)
                    {
                        if(queryLine.endsWith("*/"))
                        {
                            queryLine = new String("");
                        }
                        else
                            queryLine = new String(queryLine.substring(indexOfCommentSign+2, 
                            		queryLine.length()-1));
                    }
                }
                 
                //  blank space + " " is necessary, else otherwise the content after & before "\n" gets concatenated
                // For instance, sampledb.patients FROM becomes sampledb.patientsFROM otherwise and can not be executed 
                if(queryLine != null)
                    sBuffer.append(queryLine + " ");  
            }  
            br.close();
             
            // Here is splitter ! used ";" as a delimiter for each request 
            String[] splittedQueries = sBuffer.toString().split(";");
             
            // filter out empty statements if any
            for(int i = 0; i<splittedQueries.length; i++)  
            {
                if(!splittedQueries[i].trim().equals("") && 
                		!splittedQueries[i].trim().equals("\t"))  
                {
                    QueryList.add(new String(splittedQueries[i]));
                }
            }
        }  
        catch(Exception e)  
        {  
            GetErrorMsg.printError(e);
        }
        System.out.println("Before returning SQLReader.createQueries()");
        return QueryList;
    }
}
