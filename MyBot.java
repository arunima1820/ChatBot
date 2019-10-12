import org.jibble.pircbot.*;
import com.google.gson.*;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class MyBot extends PircBot
{
	private final String apiKeyWeather = "&APPID=6b870f902bc8889c4da6b941d4d2d177";
	private final String weatherURL = "api.openweathermap.org/data/2.5/weather?zip=";

	
	private final String apiKeyDictionary = "e900c3afc1c364a15857cdb6dc81c440";
	private final String dictionaryURL = "https://od-api.oxforddictionaries.com/api/v2";
		
    public MyBot() {
        this.setName("UwU-Bot");
    }

    // reads message from the channel
    public void onMessage(	String channel,
                            String sender,
                            String login,
                            String hostname,
                            String message)
    {
    	message = message.toLowerCase();
    	 
    	// check for keywords from channel
           if(message.contains("weather"))
           {
               String output = "Please say \"Weather\" followed by the ZIP code.";
				try {
					
					output = printWeather(getZIPCode(message));
						
				} catch (Exception e) {
					e.printStackTrace();
				}
				
               sendMessage(channel, "Hey " + sender + "! " + output);
           } 
    }
    
    
    
    
// WEATHER
    
    // method to parse ZIP code from input message
    public String getZIPCode(String message)
    {
    	Scanner sc = new Scanner(message);
    	String line = null;
    	int num = 0;
    	
    	while((line = sc.next()) != null)
    	{
    		if(Character.isDigit(line.charAt(0)))
    		{
    			return line;
    		}
    	}
    	
    	return null;
    }
    
    
    // getting JSON file from URL (both)
    public String APIConnect (String inputUrl) throws Exception 
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
    
    
    // returns the formatting output string
    public String printWeather(String zip) throws Exception
    {
		try {

			String url = "http://" + weatherURL + zip + apiKeyWeather;
			
			String json = APIConnect(url);
			
			// converts to String to JSON 
	    	JsonObject object = new JsonParser().parse(json).getAsJsonObject();
	    	
	    	String cityName = object.get("name").getAsString();
	    	
	    	// gets the main JSON object from the big JSON object
	    	JsonObject main = object.getAsJsonObject("main");
	    	
	    	String temperature = main.get("temp").getAsString();
	    	
	    	double kelvin = Double.parseDouble(temperature);
	    	double fahrenheit = ((9/5) * (kelvin - 273)) + 32;
	    	
	    	String output = "The temperature in " + cityName + " is " + String.format("%.2f", fahrenheit) + "F.";
	    	
	    	return output;
			
		} catch (Exception e)
		{
			return "The location does not exist!";
		}		
    }
}