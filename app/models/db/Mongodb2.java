package models.db;

import java.net.UnknownHostException;

import play.Play;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class Mongodb2 {
	private static DB db = null;

	public Mongodb2() {
		if(Mongodb2.db == null) {
			try {
				MongoClientURI uri = new MongoClientURI(Play.application().configuration().getString("mongodb.uri"));
				MongoClient mc = new MongoClient(uri);
				Mongodb2.db = mc.getDB(uri.getDatabase());
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}
	}

	public DBCollection getCollection(String collection_name) {
		return Mongodb2.db.getCollection(collection_name);
	}

}