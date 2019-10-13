import java.net.*;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

import java.io.*;
import com.google.gson.*;

public class Dictionary {
	private final static String dictionaryURL = "https://od-api.oxforddictionaries.com/api/v2/entries/en-us/";
	private final static String app_id = "8c126758";
    private final static String app_key = "e900c3afc1c364a15857cdb6dc81c440";
    private final static String urlAdd = "?fields=definitions&strictMatch=false";
	
	// gets JSON response from API
	public static String getJSON(String word_id) {
        try {
            URL url = new URL(dictionaryURL + word_id.toLowerCase() + urlAdd);
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Accept","application/json");
            urlConnection.setRequestProperty("app_id",app_id);
            urlConnection.setRequestProperty("app_key",app_key);

            // read the output from the server
            BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
            
            // System.out.println(stringBuilder);
            return stringBuilder.toString();

        }
        catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }
    }
	
	
	// extracts word from message
	public static String getWord (String message) {
		String[] word = new String [2];
		
		message = message.strip();
		word = message.substring(message.indexOf("define")).split(" ");
		
		return word[1];
	}
	
	public static String getDefinition(String message) {
		String json = getJSON(getWord(message));
		
		// converts to String to JSON and parses the definition 
    	JsonObject object = new JsonParser().parse(json).getAsJsonObject();
    	JsonArray thing = (JsonArray) object.get("results");
    	JsonObject results = thing.get(0).getAsJsonObject();
    	thing = (JsonArray) results.get("lexicalEntries");
    	JsonObject lexicalEntries = thing.get(0).getAsJsonObject();
    	thing = (JsonArray) lexicalEntries.get("entries");
    	JsonObject entries = thing.get(0).getAsJsonObject();
    	thing = (JsonArray) entries.get("senses");
    	JsonObject senses = thing.get(0).getAsJsonObject();
    	thing = (JsonArray) senses.get("definitions");
    	JsonElement definitions = thing.get(0); 
    	
    	// returns output
    	return  "The definition of \"" + getWord(message) + "\" is " + definitions.toString();
	}
			
}
