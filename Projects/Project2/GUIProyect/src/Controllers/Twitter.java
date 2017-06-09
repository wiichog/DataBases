/**
 * 
 */
package Controllers;

import twitter4j.Status;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

import twitter4j.Status;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.DBCursor;

/**
 * @author Perry
 *
 */
public class Twitter {
	
	//  Set the keys and secrets for auth.
	/**
	 * @param user_name
	 */
    private String key = "lnkoYWQwNYuMhMlmuJrq1ACnV";
    private String key_secret = "ZHARBM8iUNvZvy1HjI6KEsWqMOqlScq9ceREFXVwwn5my0sBJk";

    private String token = "399890384-FzHEFK7eVOVJkwhlQib2OXHg8eUeIxxw0pj88xGh";
    private String token_secret = "00VhW963RJer5xCS6RqK8Ww94BgrNDlvKZI7SUfK2BUXq";
	
    private twitter4j.Twitter twitter;
    
	public Twitter(String user_name) {
		try {
			// Make mongo stuff.
			Mongo mongo = new Mongo();
			DB db = mongo.getDB("TwitterDB");
			DBCollection collection = db.getCollection(user_name);

			// Clear the collection first.
			BasicDBObject clear = new BasicDBObject();

			// Delete All documents from collection Using blank BasicDBObject
			collection.remove(clear);
			
			// Start Twitter stuff.
			ConfigurationBuilder cb = new ConfigurationBuilder();
			
			cb.setDebugEnabled(true)
				.setOAuthConsumerKey(key)
				.setOAuthConsumerSecret(key_secret)
				.setOAuthAccessToken(token)
				.setOAuthAccessTokenSecret(token_secret);
			
			TwitterFactory tf = new TwitterFactory(cb.build());
			this.twitter = tf.getInstance();
			
			List<Status> status = this.twitter.getUserTimeline(user_name);
			
			for (Status si : status) {
				collection.insert(new BasicDBObject()
						.append("name", si.getUser().toString())
						.append("text", si.getText().toString()));
			}
			
			
		} catch (Exception e) {
			System.out.println(e.toString() + "No, ESHTAA!");
		}
		
	}
	
	/**
	 * @param user_name
	 * @return an array of data with the following structure:
	 * [0]: Happiness #.
	 * [1]: Angriness #.
	 * [2]: Relax #.
	 * [3]: Neutral #.
	 * [4]: Number of #.
	 * [5]: Words #.
	 * [6]: Tweets #.
	 * This in an Array.
	 */
	public int[] getAnalisis(String user_name) {

		List<String> status = new ArrayList<String>();
		
		try {
			// Make mongo stuff.
			Mongo mongo = new Mongo();
			DB db = mongo.getDB("TwitterDB");
			DBCollection collection = db.getCollection(user_name);
			
			BasicDBObject query = new BasicDBObject();
			query.put("text", new BasicDBObject());
			
			DBCursor cursor = collection.find();
			
			while (cursor.hasNext()) {
				DBObject object = cursor.next();
				status.add((String) object.toMap().get("text"));
			}
			
			//System.out.println(status);
			
			
		} catch (Exception e) {
			System.out.println(e.toString() + "Eshta");
		}
		
		List<Integer> semiResults = makeAnalisis(status);
		int[] results = new int[semiResults.size()];
		int i = 0;
		
		for (int it : semiResults) {
			results[i] = it;
			i ++;
		}
		
		return results;
	}
	
	
	private List<Integer> makeAnalisis(List<String> status) {
		// This function will make the analisis for the status
		List<String> happy = Arrays.asList("feliz", "emocion", "alegria", "fortuna", "bonito", "bendecido", "alegre", "bien", "orgullo", "enhorabuena", "orgulloso");
		List<String> angry = Arrays.asList("enojo", "frustrado", "infeliz", "maldito", "estupido", "me enoja", "molesto", "molesta", "ofendido", "ultimo");
		List<String> relax = Arrays.asList("chill", "tranquilo", "fresh", "relajado", "vacaciones", "descanso", "relajando", "despreocupado");
		        
        int happyCounter = 0;
        int angryCounter = 0;
        int relaxCounter = 0;
        int neutralCounter = 0;
        int hashtagCounter = 0;
        
        int wordCounter = 0;
        int tweetsCounter = 0;
        
        List<Integer> analisis = new ArrayList<Integer>();
        String[] words;
        
        for (String si : status) {
        	words = si.split(" ");
        	for (String sii : words) {
        		// Check Happy.
        		if (happy.contains(sii.toLowerCase())) {
        			happyCounter++;
        		// Check Angry.
        		} else if (angry.contains(sii.toLowerCase())) {
        			angryCounter++;
        		// Check relax.
        		} else if (relax.contains(sii.toLowerCase())) {
        			relaxCounter++;
        		// If none, then neutral.
        		} else {
        			neutralCounter++;
        		}
        		
        		// Check for Hashtags.
        		if (sii.contains("#")) {
        			hashtagCounter++;
        		}
        		
        		// Words counter.
        		wordCounter++;
        	}
        	
        	// Tweets Counter.
        	tweetsCounter++;
        }
        
        analisis.add(happyCounter);
        analisis.add(angryCounter);
        analisis.add(relaxCounter);
        analisis.add(neutralCounter);
        analisis.add(hashtagCounter);
        analisis.add(wordCounter);
        analisis.add(tweetsCounter);
        
        //System.out.println(analisis);
        return analisis;
		
	}

}
