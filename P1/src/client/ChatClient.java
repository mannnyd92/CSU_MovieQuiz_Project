// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import ocsf.client.ObservableClient;
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
public class ChatClient extends ObservableClient
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
//  public void handleMessageFromClientUI(String message)
//  {		
//	  String[] mesarray = ((String)message).split(" ");
//	  String helpError = "# Requires formatting, use #help command for more info";
//	  if(mesarray[0].charAt(0) == '#'){
//		
//		String tmp = (String)mesarray[0];
//		String tmpmes = tmp.substring(1,tmp.length());
//		String param = "";	
//		switch (tmpmes){
//		
//			case "quit":		quit();
//								break;
//							
//			case "logoff":		try {closeConnection();}catch(Exception e){}
//								break;
//							
//			case "sethost":		if(isConnected()){
//									clientUI.display("Already Logged in!, You must logoff to sethost, see #help");
//								}
//								else if(mesarray.length < 2){
//									clientUI.display(helpError);
//								}
//								else{
//									try{
//										param = mesarray[1];
//										int port = Integer.valueOf(param);
//										setPort(port);
//									
//									}catch(Exception e){}
//								}
//								break;
//							
//			case "setport":		
//								if(isConnected()){
//									clientUI.display("Already Logged in!, You must logoff to setport, see #help");
//								}
//								else if(mesarray.length < 2){
//									clientUI.display(helpError);
//								}
//								else{
//									try{
//										param = mesarray[1];
//										int port = Integer.valueOf(param);
//										setPort(port);
//									
//									}catch(Exception e){}
//								}
//								break;	
//							
//			case "login":		if(isConnected()){
//									clientUI.display("Already logged in!");
//								}else{
//									try{
//										openConnection();
//									    String loginMsg = "#login " + loginID + " " + password;
//									    this.sendToServer(loginMsg);
//									}catch(Exception e){}
//								}break;
//			
//			case "gethost":		clientUI.display(getHost());
//								break;
//							
//			case "getport":		clientUI.display(getPort());
//								break;
//							
//			case "block":		if(mesarray.length < 2){
//									clientUI.display(helpError);
//								}else{send(message);}
//								break;
//			
//			case "unblock": 	if(mesarray.length < 2){
//									clientUI.display(helpError);
//								}else{send(message);}
//								break;
//			
//			case "whoiblock":	send(message);
//								break;
//			
//			case "whoblocksme":	send(message);
//								break;
//			
//			case "changelogin": if(isConnected()){
//									clientUI.display("Already Logged in!, You must logoff to changelogin, see #help");
//								}else if(mesarray.length != 3){
//									clientUI.display(helpError);
//								}else{
//									
//									loginID = mesarray[1];
//									password = mesarray[2];
//									clientUI.display("Your new login is <" + loginID + ">");}
//								break;
//			
//			case "private":		if(mesarray.length < 3){
//									clientUI.display(helpError);
//								}else{send(message);}
//								break;
//						
//			case "help":		helpInfo();
//								break;
//								
//			case "notavailable":	send(message);
//									break;
//									
//			case "available":		send(message);
//									break;
//									
//			case "status":			if(mesarray.length < 2){
//										clientUI.display(helpError);
//									}else{send(message);}
//									break;
//									
//			case "channel":			if(mesarray.length < 3){
//										clientUI.display(helpError);
//									}else{send(message);}
//									break;
//									
//			case "createchannel":	if(mesarray.length < 2){
//										clientUI.display(helpError);
//									}else{send(message);}
//									break;
//									
//			case "joinchannel":		if(mesarray.length < 2){
//										clientUI.display(helpError);
//									}else{send(message);}
//									break;
//									
//			case "leavechannel":	if(mesarray.length < 2){
//										clientUI.display(helpError);
//									}else{send(message);}
//									break;
//									
//			case "listchannel":		send(message);
//									break;
//
//
//
//			case "monitor":			if(mesarray.length<2){
//										clientUI.display("#monitor must be followed by a parameter.");
//										break;
//									}else{
//										send(message);
//										break;
//									}
//			
//			case "accept":			send(message);
//									break;
//									
//			case "cancelmonitor":	send(message);
//									break;
//									
//			case "users":			send(message);
//									break;
//									
//			default:		clientUI.display(helpError);
//							break;
//			
//		}
//			  
//	  }
//	  
//	  else{
//		  
//		  send(message);
//   
//	  }
//  }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  /**
   * This method terminates the client.
   */
  
  public void helpInfo(){
	  clientUI.display("	Command				Description");
	  clientUI.display("");
	  clientUI.display("	#login 					Logs user into the server.");
	  clientUI.display("	#logoff 				Logs user off of the server.");
	  clientUI.display("	#changelogin <NewUserName> <password> 	Changes login, Must be logged off.");
	  clientUI.display("	#gethost 				Displays the host.");
	  clientUI.display("	#getport 				Displays the port number.");
	  clientUI.display("	#sethost <host> 			Changes the host. Must be logged off.");
	  clientUI.display("	#setport <port#> 			Changes the port. Must be logged off.");
	  clientUI.display("	#block <user> 				Blocks messages from user.");
	  clientUI.display("	#unblock <user> 			Unblocks messages from user.");
	  clientUI.display("	#whoiblock 				Displays a list of users that you are blocking.");
	  clientUI.display("	#whoblocksme 				Displays a list of users that are blocking you.");
	  clientUI.display("	#status <user>				Returns the status of a user or a channel.");
	  clientUI.display("	#notavailable				Sets your status to unavailable.");
	  clientUI.display("	#available				Sets your status to available.");
	  clientUI.display("	#private  <user> <message>		Sends a message to user specified ONLY!.");
	  clientUI.display("	#channel [channel] <message>		Sends message to users on that channel.");
	  clientUI.display("	#createchannel [channel]		Creates the channel.");
	  clientUI.display("	#joinchannel [channel] 			Assigns you to that channel.");
	  clientUI.display("	#listchannel [channel] 			Lists all live channels.");
	  clientUI.display("	#leavechannel [channel] 		Removes you from that channel.");
	  clientUI.display("	#quit					Exits the program.");
	  
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
	  }else{clientUI.display("You must be logged on to perform this function, see #help");} 
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
	  clientUI.display("You have been disconnected.");
  }
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  protected void connectionException(Exception exception){
	  timer.cancel();
	  clientUI.display("Connection has been closed!");
	  
	  
  }
  
}


//End of ChatClient class
