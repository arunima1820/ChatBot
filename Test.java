public class Test
{
	public static void main(String args[]) throws Exception
	{
		MyBot bot = new MyBot();
		
		System.out.println(bot.getZIPCode("weather in 75080"));
		System.out.println(bot.APIConnect("http://api.openweathermap.org/data/2.5/weather?zip=75080&APPID=6b870f902bc8889c4da6b941d4d2d177"));
		System.out.println(bot.printWeather("75080"));
	}
}