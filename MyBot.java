import org.jibble.pircbot.*;
import com.google.gson.*;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class MyBot extends PircBot
{
	private final String botName = "Arunimas-Bot";
	
    public MyBot() {	this.setName(botName);    }

    // reads message from the channel
    public void onMessage(	String channel,
                            String sender,
                            String login,
                            String hostname,
                            String message	)	{
    	
    	message = message.toLowerCase();
    	 String output = null;
    	// check for keywords from channel
       if(message.contains("weather"))	{
            output = "Please say \"Weather\" followed by the ZIP code.";
			try {
					output = Weather.printWeather(Weather.getZIPCode(message));	
			
			} catch (Exception e) {	e.printStackTrace();	}
			
			// bot output
			sendMessage(channel, "Hey " + sender + "! " + output);
       }
       
       if (message.contains("define")) {
    	   output = "Please say \"Define\" followed by the word.";
    	   try {
    		   
    		  output = Dictionary.getDefinition(message); 
    		   
    	   } catch (Exception e) {	e.printStackTrace();	}
    	   
    	// bot output
    	sendMessage(channel, "Hey " + sender + "! " + output);
    	   
       }
       
    } // end method()
   
    // getting JSON file from URL (both)
    public static String APIConnect (String inputUrl) throws Exception 
    {
    	URL url = new URL(inputUrl);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		
		BufferedReader read = new BufferedReader(new InputStreamReader(con.getInputStream()));
		int intValueOfChar;
		String json = "";
		while ((intValueOfChar = read.read()) != -1) {
			json += (char) intValueOfChar;
		}
		return json;
    }
}