package mongodb;

import java.net.UnknownHostException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

public class QueryDriver {

	public static void main(String[] args) throws UnknownHostException
	{
		MongoClient mongoClient  = new MongoClient( "localhost" , 27017 );
		DB db = mongoClient.getDB("email");
		DBCollection dbCollection = db.getCollection("messages");
		BasicDBObject basicObject = new BasicDBObject();
		DBCursor dbCursor = dbCollection.find(basicObject);
		while(dbCursor.hasNext())
		{
			System.out.println(dbCursor.next());
		}
		mongoClient.close();
	}

}
