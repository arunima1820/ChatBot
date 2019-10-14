// runs the bot
public class MyBotMain	{
   public static void main(String args[]) throws Exception   {
      MyBot bot = new MyBot();
      
      // connect to channel
      bot.setVerbose(true);
      bot.connect("irc.freenode.net");
      bot.joinChannel("#arunimas-channel");
   }

}