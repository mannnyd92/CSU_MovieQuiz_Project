// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import ocsf.server.*;

/**
 * This class overrides some of the methods in the abstract 
 * superclass in order to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 * @version July 2000
 */
public class EchoServer extends AbstractServer 
{
  //Class variables *************************************************
  
  /**
   * The default port to listen on.
   */
  final public static int DEFAULT_PORT = 5555;
  
  protected static ArrayList<String> validUsers = new ArrayList<String>();
  
  //Constructors ****************************************************
  
  /**
   * Constructs an instance of the echo server.
   *
   * @param port The port number to connect on.
   */
  public EchoServer(int port) 
  {
    super(port);
    ServerConsole sc = new ServerConsole(this);
  }

  
  //Instance methods ************************************************
  
  /**
   * This method handles any messages received from the client.
   *
   * @param msg The message received from the client.
   * @param client The connection from which the message originated.
   */
  public void handleMessageFromClient
  (Object msg, ConnectionToClient client)
{
	String login = msg.toString();
	String log = "#login";
	String connected = "connected";
	boolean logbool = false;
	try{
		if(login.substring(0, 6).equals(log)){
			logbool = true;
		}
	}catch(Exception e){
		
	}
	if(logbool == true){
		if(client.getInfo(connected) == null){

			if(!login.substring(0, 6).equals(log)){
				try {
					String error = "Login was not received.";
					client.sendToClient(error);
					client.close();
					return;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			else{
				String name = login.substring(8, login.length() - 1);
				String id = "id";
				client.setInfo(id, name);
			}
			
			client.setInfo(connected, true);
		}
		else{
			String error = "The #login command is invalid after logging in.";
			try {
				client.sendToClient(error);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	String id = "id";
  System.out.println("Message received: " + msg + " from " + client);
  String message = "<" + client.getInfo(id).toString() + "> " + msg;
  this.sendToAllClients(message);
}
    
  
  /**Method that returns an ArrayList containing names of all the
   * other users who are blocking the current client.
   * @param client who wants to know which users are blocking it
   * @return arraylist containing all users who are blocking the client
   */
  protected ArrayList<String> whoBlocksMeList(ConnectionToClient client){
	Thread[] clients = getClientConnections();
	ArrayList<String> blocklist = new ArrayList<String>();
	String block = "blocklist";
	String id = "id";
	ConnectionToClient cl;
	for(int i = 0; i < clients.length; i++){
		cl = (ConnectionToClient)clients[i];
		ArrayList<String> holder = (ArrayList<String>) cl.getInfo(block);
		if(holder.contains(client.getInfo(id).toString())){
			blocklist.add(cl.getInfo(id).toString());
		}
	}
	return blocklist;
  }
  
  /**Method that returns an arraylist containing the users
   * who are blocked from the current client
   * @param client that is blocking the other users
   * @return arraylist containing the blocked users
   */
  protected ArrayList<String> whoIBlockList(ConnectionToClient client){
	  ArrayList<String> blocklist = new ArrayList<String>();
	  String block = "blocklist";
	  blocklist = (ArrayList<String>) client.getInfo(block);
	  return blocklist;
  }
  
  protected boolean addBlockedUser(ConnectionToClient client, String blockee){
	  try{
	  	  String block = "blocklist";
		  ArrayList<String> temp = new ArrayList<String>();
		  temp = (ArrayList<String>)client.getInfo(block);
		  temp.add(blockee);
		  client.setInfo(block, temp);
		  return true;
	  }
	  catch(Exception e){
		  return false;
	  }
  }
  
  protected boolean removeBlockedUser(ConnectionToClient client, String unblockee){
	  String block = "blocklist";
	  try{
		  ArrayList<String> temp = new ArrayList<String>();
		  temp = (ArrayList<String>)client.getInfo(block);
		  int index = temp.indexOf(unblockee);
		  temp.remove(index);
		  client.setInfo(block, temp);
		  return true;
	  }
	  catch(Exception e){
		  return false;
	  }
  }
  
  /**
   * This method overrides the one in the superclass.  Called
   * when the server starts listening for connections.
   */
  protected void serverStarted()
  {
    System.out.println
      ("Server listening for connections on port " + getPort());
     
  }
  
  /**
   * This method overrides the one in the superclass.  Called
   * when the server stops listening for connections.
   */
  protected void serverStopped()
  {
    System.out.println
      ("Server has stopped listening for connections.");
  }
  
  protected void clientConnected(ConnectionToClient client) {
	  String id = "id";
	  String block = "blocklist";
	  ArrayList<String> blocklist = new ArrayList<String>();
	  String info = client.toString();
	  client.setInfo(id, info);
	  client.setInfo(block, blocklist);
	  System.out.println(client + " has connected.");
  }
  
  synchronized protected void clientDisconnected(ConnectionToClient client) {
	  System.out.println(client + " has been disconnected.");
  }
  
  synchronized protected void clientException(ConnectionToClient client, Throwable exception) {
	  String id = "id";
	  System.out.println(client.getInfo(id) + " has disconnected due to: " + exception);
  }
  
  protected void listeningException(Throwable exception) {
	  System.out.println(exception);
  }
  
  //Class methods ***************************************************
  
  /**
   * This method is responsible for the creation of 
   * the server instance (there is no UI in this phase).
   *
   * @param args[0] The port number to listen on.  Defaults to 5555 
   *          if no argument is entered.
   */
  public static void main(String[] args) 
  {
    int port = 0; //Port to listen on
    try{
    	String usernameFile = args[0];
    	Scanner s = new Scanner(new File(usernameFile));
    	while(s.hasNext()){
    		validUsers.add(s.next());
    	}
    	s.close();
    }
    catch(Exception e){
    	System.out.println("ERROR - A valid username file is required.");
    	System.exit(0);
    }
    try
    {
      port = Integer.parseInt(args[1]); //Get port from command line
    }
    catch(Throwable t)
    {
      port = DEFAULT_PORT; //Set port to 5555
    }
	
    EchoServer sv = new EchoServer(port);
    
    try 
    {
      sv.listen(); //Start listening for connections
    } 
    catch (Exception ex) 
    {
      System.out.println("ERROR - Could not listen for clients!");
    }
  }
}
//End of EchoServer class
