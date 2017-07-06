=================================================
Compilation-Client files
=================================================
Client Files
javac client/ClientSideFTP.java client/ClientSideTCP.java client/ErrorHandler.java client/GetListOfFiles.java client/PortHostName.java

------------------------------------------------
Run client files
-----------------------------------------------
java -cp ./ client.ClientSideFTP client.ClientSideTCP client.ErrorHandler client.GetListOfFiles client.PortHostName


================================================
Server Files
===============================================
javac server/ServerSideTCP.java server/ClientPerServerTCP.java server/ErrorHandler.java server/FileManagerTCP.java server/GetListOfFiles.java server/PortHostName.java

------------------------------------------------
Run Server files
-----------------------------------------------
java -cp ./ server.ServerSideTCP.java server.ClientPerServerTCP.java server.ErrorHandler.java server.GetListOfFiles.java server.PortHostName.java

NOTE: Default directory for client is myClient.So, a file to be get/put/!ls will work taking this directory into account for client.
For server, it is same directory from where command is run


References
http://www.programcreek.com/java-api-examples/index.php?api=java.nio.channels.NonWritableChannelException
http://www.codebytes.in/2014/11/file-transfer-using-tcp-java.html