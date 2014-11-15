// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.*;

import client.*;
import common.*;

/**
 * This class constructs the UI for a chat client.  It implements the
 * chat interface in order to activate the display() method.
 * Warning: Some of the code here is cloned in ServerConsole 
 *
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Dr Timothy C. Lethbridge  
 * @author Dr Robert Lagani&egrave;re
 * @version July 2000
 */
public class ClientConsole implements ChatIF 
{
  //Class variables *************************************************
  
  /**
   * The default port to connect on.
   */
  final public static int DEFAULT_PORT = 5555;
  
  //Instance variables **********************************************
  
  /**
   * The instance of the client that created this ConsoleChat.
   */
  ChatClient client;
  ClientGUI clientgui;
  
  //Constructors ****************************************************

  /**
   * Constructs an instance of the ClientConsole UI.
   *
   * @param host The host to connect to.
   * @param port The port to connect on.
   */
  public ClientConsole(){
	  clientgui = new ClientGUI();
  }
  
  public ClientConsole(String login,String pass, String host, int port) 
  {
    try 
    {
        clientgui = new ClientGUI();
        client= new ChatClient(login, pass, host, port, this);
        //clientgui.setClient(client);

    } 
    catch(IOException exception) 
    {
      System.out.println("Error: Can't setup connection!"
                + " Terminating client.");
      System.exit(1);
    }
  }

  
  //Instance methods ************************************************
  
  /**
   * This method waits for input from the console.  Once it is 
   * received, it sends it to the client's message handler.
   */
  public void accept() 
  {
    try
    {
    	
    	BufferedReader fromConsole = new BufferedReader(new InputStreamReader(System.in));
    	String message;
      
      while (true) 
      {
        message = fromConsole.readLine();
        client.handleMessageFromClientUI(message);
      }
    } 
    catch (Exception ex) 
    {
      System.out.println
        ("Unexpected error while reading from console!");
    }
  }

  /**
   * This method overrides the method in the ChatIF interface.  It
   * displays a message onto the screen.
   *
   * @param message The string to be displayed.
   */
  public void display(String message) 
  {
	  System.out.println("display: " + message);
	  clientgui.display("> " + message);
    
  }

  
  //Class methods ***************************************************
  
  /**
   * This method is responsible for the creation of the Client UI.
   *
   * @param args[0] The host to connect to.
   */
  public static void main(String[] args) 
  {
//    String host = "";
//    String login = "";
//    String pass = "";
//    int port = 0;  //The port number
//    try
//    {
//      if(args.length == 0 || args.length == 1){
//    	  System.out.println("Login ID or Password was not provided.");
//    	  System.exit(0);
//      }
//      login = args[0];
//      pass = args[1];
//      
//      if(args.length == 2){
//    	  host = "localhost";
//    	  port = DEFAULT_PORT;
//      }
//      if(args.length > 3){
//    	  host = args[2];
//    	  String portstring = args[3];
//    	  port = Integer.parseInt(portstring);
//      }
//      if(args.length == 3){
//    	  try{
//    		  String onlyport = args[2];
//    		  port = Integer.parseInt(onlyport);
//    		  host = "localhost";
//    	  }
//    	  catch(NumberFormatException nfe){
//    		  host = args[2];
//    		  port = DEFAULT_PORT;
//    	  }
//      }
//    }
//    catch(ArrayIndexOutOfBoundsException e)
//    {
//      host = "localhost";
//    }
//    ClientConsole chat = null;
//    if(port == 0){
//    	chat = new ClientConsole(login, pass, host, DEFAULT_PORT);
//    }
//    else{
//    	chat = new ClientConsole(login, pass, host, port);
//    }
//    chat.accept();  //Wait for console data
	  ClientConsole cc = new ClientConsole();
  }
}
//End of ConsoleChat class
