import java.util.Scanner;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Weather {

		// url and API key
		private static final String apiKeyWeather = "&APPID=6b870f902bc8889c4da6b941d4d2d177";
		private static final String weatherURL = "http://api.openweathermap.org/data/2.5/weather?zip=";
	
	// method to parse ZIP code from input message
    public static String getZIPCode(String message) {
    	Scanner sc = new Scanner(message);
    	String line = null;
    	int num = 0;
    	
    	while((line = sc.next()) != null)	{
    		if(Character.isDigit(line.charAt(0)))	{
    			return line;
    		}
    	}

    	return null;
    }
    
    // returns the formatted output string
    public static String printWeather(String zip) throws Exception
    {
		try {
				String url = weatherURL + zip + apiKeyWeather;
				String json = MyBot.APIConnect(url);
				System.out.println(json);
				
				// converts to String to JSON
		    	JsonObject object = new JsonParser().parse(json).getAsJsonObject();
		    	
		    	String cityName = object.get("name").getAsString();
		    	
		    	// gets the main JSON object from the big JSON object
		    	JsonObject main = object.getAsJsonObject("main");
		    	
		    	// get average temp
		    	String temperature = main.get("temp").getAsString();
		    	double fahrenheit = convertTemperature(Double.parseDouble(temperature));
		    	String output = "The average temperature in " + cityName + " is " + String.format("%.2f", fahrenheit) + "F";

		    	// get max temp
		    	temperature = main.get("temp_max").getAsString();
		    	fahrenheit = convertTemperature(Double.parseDouble(temperature));
		    	output += " with a high of " + String.format("%.2f", fahrenheit) + "F";
		    	
		    	// get min temp
		    	temperature = main.get("temp_min").getAsString();
		    	fahrenheit = convertTemperature(Double.parseDouble(temperature));
		    	output += " and a low of " + String.format("%.2f", fahrenheit) + "F"; 
		    	
		    	return output;
				
			} catch (Exception e)	{
				return "The location is not valid!";
		}		
    }
    
    // converts temperature in Kelvin to Fahrenheit
    private static double convertTemperature(double kelvin) {
    	return ((9/5.0) * (kelvin - 273)) + 32;
    }
}

