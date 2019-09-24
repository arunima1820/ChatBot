import java.io.*;
import java.net.*;

public class ChatBot 
{
    public static void main (String args[]) throws Exception
    {
        //The server to connect to and our details
        String server = "irc.freenode.net";
        String nick = "simple_bot";
        String login = "simple_bot";

        //The channel which the bot will join
        String channel = "#irchacks";

        //Connect directly to the IRC server
        Socket socket = new Socket (server, 6667);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        //log on to the server
        writer.write("NICK " + nick + "\r\n");
        writer.write("USER  " + login + " 8 * : Java IRC Hacks Bot\r\n ");
        writer.flush();
    
        // read lines from the server until it says CONNECTED
        String line = null;
        while ((line = reader.readLine()) != null)
        {
            if (line.indexOf("004") >= 0)
            {
                //we are now logged in
                break;
            }

            else if (line.indexOf("433") >= 0)
            {
                System.out.println("Nickname is already in use.");
                return;
            }
        }

        // join the channel
        writer.write("JOIN " + channel + "\r\n");
        writer.flush();

        // keep reading lines from the server
        while((line = reader.readLine()) != null)
        {
            if (line.toLowerCase().startsWith("PING "))
            {
                // we must respond to PINGs to avoid being disconnected
                writer.write("PONG " + line.substring(5) + "\r\n");
                writer.write("PRIVMSG " + channel + " :I got pinged!\r\n");
                writer.flush();
            }

            else
            {
                System.out.println(line);
            }
        }
    }
}