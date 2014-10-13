// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package client;

import ocsf.client.*;
import common.*;
import java.io.*;

/**
 * This class overrides some of the methods defined in the abstract
 * superclass in order to give more functionality to the client.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 * @version July 2000
 */
public class ChatClient extends AbstractClient
{
  //Instance variables **********************************************
  
  /**
   * The interface type variable.  It allows the implementation of 
   * the display method in the client.
   */
  ChatIF clientUI; 
  String loginID;
  
  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the chat client.
   *
   * @param host The server to connect to.
   * @param port The port number to connect on.
   * @param clientUI The interface type variable.
   */
  
  public ChatClient(String login, String host, int port, ChatIF clientUI) 
    throws IOException 
  {
    super(host, port); //Call the superclass constructor
    this.clientUI = clientUI;
    this.loginID = login;
    openConnection();
    String loginMsg = "#login <" + login + ">";
    this.sendToServer(loginMsg);
  }

  
  //Instance methods ************************************************
    
  /**
   * This method handles all data that comes in from the server.
   *
   * @param msg The message from the server.
   */
  public void handleMessageFromServer(Object msg) 
  {
	if(isConnected() == false){
		connectionClosed();
	}
    clientUI.display(msg.toString());
  }

  /**
   * This method handles all data coming from the UI            
   *
   * @param message The message from the UI.    
   */
  public void handleMessageFromClientUI(String message)
  {
	 
	  if(message.charAt(0) == '#'){
		String tmpmes = "";
		String tmpmes1 = message.substring(1,message.length());
		String tmpmes2 = message.substring(1,message.length());
		String param = "";
		String chkmes1 = "";
		String chkmes2 = "";
		boolean flag = false;
		if(tmpmes1.length() > 6){
			
			chkmes1 = tmpmes1.substring(0,5);
			
			//new code untested
			if(chkmes1.equals("block")){
				if( tmpmes1.length() > 5){
					if(tmpmes1.charAt(5) != ' '){
						System.out.println("A space is required after the command!");
					}
					else{
						param = tmpmes1.substring(6, tmpmes1.length());
						tmpmes1 = "block";
						tmpmes = tmpmes1;
					}
				}	
			}
			chkmes2 = tmpmes2.substring(0,7);
			if(chkmes2.equals("sethost")){
				if( tmpmes2.length() > 7){
					if(tmpmes2.charAt(7) != ' '){
						System.out.println("A space is required after the command!");
					}
					else{
						param = tmpmes2.substring(8, tmpmes2.length());
						tmpmes2 = "sethost";
						tmpmes = tmpmes2;
						flag = true;
					}
				}else{System.out.println("Please give parameter after command!");}
			}
			
			if(chkmes2.equals("setport")){
				if( tmpmes2.length() > 8){
					if(tmpmes2.charAt(7) != ' '){
						System.out.println("A space is required after the command!");
					}
					else{
					param = tmpmes2.substring(8, tmpmes2.length());
					tmpmes2 = "setport";
					tmpmes = tmpmes2;
					flag = true;
					}
				}else{System.out.println("Please give parameter after command!");}
			}
		}
				
		switch (tmpmes){
			case "quit":	quit();
							break;
							
			case "logoff":	try {closeConnection();}catch(Exception e){}
							break;
							
			case "sethost":	if(!isConnected() && flag){
								setHost(param);
								flag = false;
							}else if (isConnected() && flag){
								System.out.println("Already Logged in, You must logoff to sethost!");
							}
							break;
							
			case "setport":	if(!isConnected() && flag){
								try{
									int port = Integer.valueOf(param);
									setPort(port);
									flag = false;
								}catch(Exception e){}
							}else if(isConnected() && flag){
								System.out.println("Already Logged in!, You must logoff to setport!");}
							break;	
							
			case "login":	if(isConnected()){
								System.out.println("Already logged in!");
							}else{
								try{
									openConnection();
								}catch(Exception e){}
							}break;
			
			case "gethost":	System.out.println(getHost());
							break;
							
			case "getport":	System.out.println(getPort());
							break;
						
			case "block":	System.out.println(param);
							break;
							
			default:		System.out.println("# Requires that it is followed by a command and a parameter!");
			
		}
			  
	  }
	  
	  else{
    try
    {
      sendToServer(message);
    }
    catch(IOException e)
    {
      clientUI.display
        ("Could not send message to server.  Terminating client.");
      quit();
    }
  }
  }
  /**
   * This method terminates the client.
   */
  public void quit()
  {
    try
    {
      closeConnection();
    }
    catch(IOException e) {}
    System.exit(0);
  }
  
  protected void connectionClosed() {
	  System.out.println("You have been disconnected.");
  }
  
  protected void connectionException(Exception exception){
	  System.out.println("Server has shut down.");
	  quit();
	  
  }
  
}


//End of ChatClient class
