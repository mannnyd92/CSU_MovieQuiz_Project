// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import ocsf.client.AbstractClient;
import common.ChatIF;

/**
 * This class overrides some of the methods defined in the abstract
 * superclass in order to give more functionality to the client.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;handleMessageFromServer
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
  String password;
  ArrayList<String> blockedList = new ArrayList<String>();
  Timer timer = new Timer();
  
  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the chat client.
   *
   * @param host The server to connect to.
   * @param port The port number to connect on.
   * @param clientUI The interface type variable.
   */
  
  public ChatClient(String login, String pass, String host, int port, ChatIF clientUI) 
    throws IOException 
  {
    super(host, port); //Call the superclass constructor
    this.clientUI = clientUI;
    this.loginID = login;
    this.password = pass;
    openConnection();
    String loginMsg = "#login " + login + " " + pass ;
    this.sendToServer(loginMsg);
    timer.schedule(new FiveMinutes(), 300000);
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
	
	try{
		
		if(msg instanceof ArrayList){
			blockedList = ((ArrayList<String>) msg);
			return;
		}else{
			String userparse = "";
			userparse = msg.toString();
			userparse = userparse.substring(1, userparse.indexOf(">"));
			
			if(blockedList.contains(userparse) || blockedList.contains("server")){
				
				return;
			}
			
		}
	}
	catch(Exception e){
		
	}

	
	String userparse = (String)msg;
	try{
		userparse = userparse.substring(1, userparse.indexOf(">"));
	
	}catch(Exception e){}
	
	
	if(blockedList.contains(userparse)){
		
		return;
	}
	
    clientUI.display(msg.toString());
  }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /**
   * This method handles all data coming from the UI            
   *
   * @param message The message from the UI.    
   */
  public void handleMessageFromClientUI(String message)
  {		
	  String[] mesarray = ((String)message).split(" ");
	  String helpError = "# Requires formatting, use #help command for more info";
	  if(mesarray[0].charAt(0) == '#'){
		
		String tmp = (String)mesarray[0];
		String tmpmes = tmp.substring(1,tmp.length());
		String param = "";	
		switch (tmpmes){
		
			case "quit":		quit();
								break;
							
			case "logoff":		try {closeConnection();}catch(Exception e){}
								break;
							
			case "sethost":		if(isConnected()){
									System.out.println("Already Logged in!, You must logoff to sethost, see #help");
								}
								else if(mesarray.length < 2){
									System.out.println(helpError);
								}
								else{
									try{
										param = mesarray[1];
										int port = Integer.valueOf(param);
										setPort(port);
									
									}catch(Exception e){}
								}
								break;
							
			case "setport":		
								if(isConnected()){
									System.out.println("Already Logged in!, You must logoff to setport, see #help");
								}
								else if(mesarray.length < 2){
									System.out.println(helpError);
								}
								else{
									try{
										param = mesarray[1];
										int port = Integer.valueOf(param);
										setPort(port);
									
									}catch(Exception e){}
								}
								break;	
							
			case "login":		if(isConnected()){
									System.out.println("Already logged in!");
								}else{
									try{
										openConnection();
									    String loginMsg = "#login " + loginID + " " + password;
									    this.sendToServer(loginMsg);
									}catch(Exception e){}
								}break;
			
			case "gethost":		System.out.println(getHost());
								break;
							
			case "getport":		System.out.println(getPort());
								break;
							
			case "block":		if(mesarray.length < 2){
									System.out.println(helpError);
								}else{send(message);}
								break;
			
			case "unblock": 	if(mesarray.length < 2){
									System.out.println(helpError);
								}else{send(message);}
								break;
			
			case "whoiblock":	send(message);
								break;
			
			case "whoblocksme":	send(message);
								break;
			
			case "changelogin": if(isConnected()){
									System.out.println("Already Logged in!, You must logoff to changelogin, see #help");
								}else if(mesarray.length != 3){
									System.out.println(helpError);
								}else{
									
									loginID = mesarray[1];
									password = mesarray[2];
									System.out.println("Your new login is <" + loginID + ">");}
								break;
			
			case "private":		if(mesarray.length < 3){
									System.out.println(helpError);
								}else{send(message);}
								break;
						
			case "help":		helpInfo();
								break;
								
			case "notavailable":	send(message);
									break;
									
			case "available":		send(message);
									break;
									
			case "status":			if(mesarray.length < 2){
										System.out.println(helpError);
									}else{send(message);}
									break;
									
			case "channel":			if(mesarray.length < 3){
										System.out.println(helpError);
									}else{send(message);}
									break;
									
			case "createchannel":	if(mesarray.length < 2){
										System.out.println(helpError);
									}else{send(message);}
									break;
									
			case "joinchannel":		if(mesarray.length < 2){
										System.out.println(helpError);
									}else{send(message);}
									break;
									
			case "leavechannel":	if(mesarray.length < 2){
										System.out.println(helpError);
									}else{send(message);}
									break;
									
			case "listchannel":		if(mesarray.length < 2){
										System.out.println(helpError);
									}else{send(message);}
			break;
									
<<<<<<< HEAD
			default:		System.out.println(helpError);
=======
			case "monitor":			if(mesarray.length<2){
										System.out.println("#monitor must be followed by a parameter.");
										break;
									}else{
										send(message);
										break;
									}
			
			case "accept":	send(message);
									break;
			
			default:		System.out.println("# Requires formatting, use #help command for more info");
>>>>>>> 4950ebf87ec414c8d7e2a1cb3254e09b431e43f8
			
		}
			  
	  }
	  
	  else{
		  
		  send(message);
   
	  }
  }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /**
   * This method terminates the client.
   */
  
  public void helpInfo(){
	  System.out.println("	Command				Description");
	  System.out.println("");
	  System.out.println("	#login 					Logs user into the server.");
	  System.out.println("	#logoff 				Logs user off of the server.");
	  System.out.println("	#changelogin <NewUserName> <password> 	Changes login, Must be logged off.");
	  System.out.println("	#gethost 				Displays the host.");
	  System.out.println("	#getport 				Displays the port number.");
	  System.out.println("	#sethost <host> 			Changes the host. Must be logged off.");
	  System.out.println("	#setport <port#> 			Changes the port. Must be logged off.");
	  System.out.println("	#block <user> 				Blocks messages from user.");
	  System.out.println("	#unblock <user> 			Unblocks messages from user.");
	  System.out.println("	#whoiblock 				Displays a list of users that you are blocking.");
	  System.out.println("	#whoblocksme 				Displays a list of users that are blocking you.");
	  System.out.println("	#status <user>				Returns the status of a user or a channel.");
	  System.out.println("	#notavailable				Sets your status to unavailable.");
	  System.out.println("	#available				Sets your status to available.");
	  System.out.println("	#private  <user> <message>		Sends a message to user specified ONLY!.");
	  System.out.println("	#channel [channel] <message>		Sends message to users on that channel.");
	  System.out.println("	#createchannel [channel]		Creates the channel.");
	  System.out.println("	#joinchannel [channel] 			Assigns you to that channel.");
	  System.out.println("	#listchannel [channel] 			Lists all live channels.");
	  System.out.println("	#leavechannel [channel] 		Removes you from that channel.");
	  System.out.println("	#quit					Exits the program.");
	  
  }
  
 
 ///////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  
  
  public void send(String message){
	  if(isConnected()){
		  try{
			  
		      sendToServer(message);
			  
		      try{
		    	  timer.cancel();
		      }
		      catch(Exception e){
		    	  e.printStackTrace();
		      }
		      timer = new Timer();
		      timer.schedule(new FiveMinutes(), 300000);
		    }
		    catch(IOException e)
		    {
		      clientUI.display
		        ("Could not send message to server.  Terminating client.");
		      quit();
		    }
	  }else{System.out.println("You must be logged on to perform this function, see #help");} 
  }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  class FiveMinutes extends TimerTask{
	  public void run(){
		  try{
			  String message = "#idle";
			  sendToServer(message);
		  }
		  catch(IOException e){
			  e.printStackTrace();
		  }
	  }
  }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  public void quit()
  {
    try
    {
    	
      closeConnection();
    }
    catch(IOException e) {}
    System.exit(0);
  }
 //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  protected void connectionClosed() {
	  timer.cancel();
	  System.out.println("You have been disconnected.");
  }
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  protected void connectionException(Exception exception){
	  timer.cancel();
	  System.out.println("Connection has been closed!");
	  
	  
  }
  
}


//End of ChatClient class
