// Tester program to check output from APIs without running pircbot
public class Test	{
	public static void main(String args[]) throws Exception	{		
		System.out.println(Weather.getZIPCode("weather in 75080"));
		System.out.println(MyBot.APIConnect("http://api.openweathermap.org/data/2.5/weather?zip=75080&APPID=6b870f902bc8889c4da6b941d4d2d177"));
		System.out.println(Weather.printWeather("75080"));
		System.out.println(Dictionary.getDefinition("define good please"));
	}
}