// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class EchoServer extends ObservableServer 
{
  //Class variables *************************************************
  
  /**
   * The default port to listen on.
   */
  final public static int DEFAULT_PORT = 5555;
  
  protected static ArrayList<String> validUsers = new ArrayList<String>();
  protected static ArrayList<String> LoggedInUsers = new ArrayList<String>();
  protected static ArrayList<String> channelList = new ArrayList<String>(); 
  protected static Map pass = new HashMap();
  
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

  
  //Instance methods //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  /**
   * This method handles any messages received from the client.
   *
   * @param msg The message received from the client.
   * @param client The connection from which the message originated.
   */
  
  public void login(Object msg, ConnectionToClient client){
		String login = msg.toString();
		String[] templog = ((String)msg).split(" ");
		String log = "#login";
		String connected = "connected";
		String pw = templog [2];
		boolean logbool = false;
		
		try{
			if(login.substring(0, 6).equals(log)){
				if(!templog[1].equals("<server>")){
					logbool = true;
				}else{
					client.sendToClient("Cannot log in as server, #help for more info!");
					client.close();}
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
					
					String name = templog[1];
					//checks for unique user name
					if(!LoggedInUsers.contains(name)){
						if(validUsers.contains(name)){
							
							if(pw.equals(pass.get(name))){
								ArrayList<String> chan = new ArrayList<String>();
								String id = "id";
								client.setInfo(id, name);
								client.setInfo("availability", true);
								client.setInfo("idle", false);
								client.setInfo("channels", chan);
								client.setInfo("whomonitorsme", "");
								client.setInfo("whoimonitor", "");
								LoggedInUsers.add(name);
								String logmes = "has logged in!";
								logwrite(logmes,client);
							}else{
								try{
								client.sendToClient("Incorrect Password, Try login again, See #help for more details!");
								client.close();
								}catch(Exception e){}
							}
						}
						else{
							try{
								String error = "Login is not on the Valid Users list.";
								client.sendToClient(error);
								client.close();
								return;
							} catch (Exception e){
								e.printStackTrace();
							}
							
						}
					}else{
						try{
							String error = "Username already in use please try another! Use #help to do so! ";
							client.sendToClient(error);
							client.close();
							return;
						} catch (Exception e){
							e.printStackTrace();
						}
						
						}
				}
				
				client.setInfo(connected, true);
			}
			else{
				String error = "The #login command is invalid after logging in.";
				try {
					client.sendToClient(error);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	  
  }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  public void block(Object msg, ConnectionToClient client){
	  String[] temp = ((String)msg).split(" ");
	  if(whoIBlockList(client).contains(temp[1])){
		  try {
			client.sendToClient("Messages from "+temp[1]+" were already blocked.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	  } else if(client.getInfo("id").equals(temp[1])){
		  try {
			client.sendToClient("You cannot block the sending of messages to yourself.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	  } else if (!validUsers.contains(temp[1])){
		  try {
			client.sendToClient("User "+temp[1]+" does not exist.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	  } else {
		  if(addBlockedUser(client, temp[1])){
			  try {
				client.sendToClient("Messages from "+temp[1]+" will be blocked.");
				ArrayList copy = new ArrayList((ArrayList<String>)client.getInfo("blocklist"));
				client.sendToClient(copy);
			} catch (IOException e) {
				e.printStackTrace();
			}
		  }
	  }
  }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  public void unblock(Object msg, ConnectionToClient client){
	 
	  try{
		  String[] temp = ((String)msg).split(" ");
		  String unblock = temp[1];
		  if(unblock.length() < 2){
			  
			  unblockAll(client);
		  }
		  if(!whoIBlockList(client).contains(temp[1])){
			  try {
				client.sendToClient("Messages from "+temp[1]+" were not blocked.");
			} catch (IOException e) {
				e.printStackTrace();
			}
		  }	else {
			  
			  if(removeBlockedUser(client, unblock)){
				  try {
					  	
						client.sendToClient("Messages from "+temp[1]+" will now be displayed.");
						ArrayList copy = new ArrayList((ArrayList<String>)client.getInfo("blocklist"));
						client.sendToClient(copy);

					} catch (IOException e) {
						e.printStackTrace();
					}
			  }
		  }
		  
	  } catch(ArrayIndexOutOfBoundsException e){
		  String block = "blocklist";
		  ArrayList<String> temp = new ArrayList<String>();
		  temp = (ArrayList<String>)client.getInfo(block);
		  if(temp.isEmpty()){
				try {
					client.sendToClient("No blocking is in effect.");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
		  }else{
		  temp.clear();
		  client.setInfo(block, temp);
		  }
	  }
  }
 //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  public void whoBlocksMe(ConnectionToClient client){
	  ArrayList<String> temp = new ArrayList<String>();
	  temp = whoBlocksMeList(client);
	  if(temp.isEmpty()){
		  try {
			client.sendToClient("Nobody blocks you!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	  }
	  for(int i = 0; i < temp.size(); i++) {
		  try {
			client.sendToClient("Messages to "+(temp.get(i)).toString()+" are being blocked.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	  }  
  }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  public void whoIBlock(ConnectionToClient client){
	  ArrayList<String> temp = new ArrayList<String>();
	  temp = whoIBlockList(client);
	  if(temp.isEmpty()){
		  try {
			client.sendToClient("You currently BLOCK NO ONE!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	  }
	  for(int i = 0; i < temp.size(); i++) {
		  try {
			client.sendToClient((temp.get(i)).toString()+" is blocked.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	  } 
  }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  public String status(Object msg, ConnectionToClient client){
	  String [] parmes = ((String) msg).split(" ");
	  
	  //TODO logic for determining  a user or a channel
	  //code for status on a user
	  String status = "";
	  Thread[] clientThreadList = getClientConnections();
	  ConnectionToClient cl;
	  if(channelList.contains(parmes[1])){
		  String chanName = parmes[1];
		  String chanListTag = "channels";
		 
		  for (int i = 0; i< clientThreadList.length; i++){
			  cl = (ConnectionToClient)clientThreadList[i];
			  ArrayList<String> chanList = new ArrayList<String>((ArrayList<String>)cl.getInfo(chanListTag));
			  if(chanList.contains(chanName)){
				  if (parmes[1] == null){return "";}
				  if((boolean)cl.getInfo("availability") == false){
					  status = "User " + cl.getInfo("id") + " is unavailable."; 
				  }else if((boolean)cl.getInfo("idle")){
					  status = "User " + cl.getInfo("id")  + " is idle.";
				  }else if((boolean)cl.getInfo("connected")){
					  status = "User " + cl.getInfo("id") + " is online.";
				  }else if(!(boolean)cl.getInfo("connected") || client.getInfo("connected") == null ){
					  status = "User " + cl.getInfo("id")  + " is offline.";
				  }else{status = "User " + cl.getInfo("id")  + "'s status not found.";}
				  
				  try{client.sendToClient(status);}catch(Exception e){};
				  
				  //return status;
			  }
		  }
	  }
	  else if(!validUsers.contains(parmes[1])){
		  try{
			  client.sendToClient("Not a valid username");
		  }catch(Exception e){}
	  }else if(!LoggedInUsers.contains(parmes[1])){
		  try{client.sendToClient("User " + parmes[1] + " is offline.");}
		  catch(Exception e){}
	  }else{
		  for (int i=0; i<clientThreadList.length; i++){
			  cl = (ConnectionToClient)clientThreadList[i];
			  if(cl.getInfo("id").toString().equals(parmes[1])){
				  if (parmes[1] == null){return "";}
				  if((boolean)cl.getInfo("availability") == false){
					  status = "User " + parmes[1] + " is unavailable."; 
				  }else if((boolean)cl.getInfo("idle")){
					  status = "User " + parmes[1] + " is idle.";
				  }else if((boolean)cl.getInfo("connected")){
					  status = "User " + parmes[1] + " is online.";
				  }else if(!(boolean)cl.getInfo("connected") || client.getInfo("connected") == null ){
					  status = "User " + parmes[1] + " is offline.";
				  }else{status = "User " + parmes[1] + "'s status not found.";}
				  
				  try{ client.sendToClient(status);}catch(Exception e){};
				  return status;
			  }
		  }
	  }
	  return status;
  }
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  public void handleMessageFromClient(Object msg, ConnectionToClient client)
{	
	  
	  String[] temp = ((String)msg).split(" ");
	  if(((String)msg).charAt(0) == '#'){
		  
		  switch (temp[0]){
		  		case "#block":			block(msg, client);
		  								break;
		  							
		  		case "#unblock":		unblock(msg,client);
		  								break;
		  							
		  		case "#whoblocksme":	whoBlocksMe(client);
		  								break;
		  							
		  		case "#whoiblock": 		whoIBlock(client);
		  								break;
		  							
		  		case "#login":			login(msg,client);
		  								break;
		  							
		  		case "#idle":			client.setInfo("idle", true );
		  								break;
		  							
		  		case "#available": 		client.setInfo("availability", true);
		  								try{
		  									client.sendToClient("You are now Available");
		  								}catch(Exception e){}
		  								break;
		  							
		  		case "#notavailable":	client.setInfo("availability", false);
		  								try{
		  									client.sendToClient("You are now Unavailable");
		  								}catch(Exception e){}
		  								break;
		  							
		  		case "#status": 		status(msg,client);
		  								break;
		  							
		  		case "#private":		sendToClientPrivate(msg, client);
		  								break;

		  		case "#channel":		channelChat(msg, client);
		  								break;

		  		case "#createchannel":	createChannel(msg, client);
		  								break;

		  		case "#joinchannel":	joinChannel(msg, client);
		  								break;

		  		case "#leavechannel":	leaveChannel(msg, client);
		  								break;

		  		case "#listchannel":	listChannels(msg, client);
		  								break;
		  								
		  		case "#monitor":		monitor(msg, client);
		  								break;
		  								
		  		case "#accept":			acceptMonitor(client);
		  								break;
		  								
		  		case "#cancelmonitor":	cancelMonitor(client);
		  								break;
		  								
		  		case "#users":			getUsers(client);
		  								break;
		  		
		  		case "#send":			sendToAllClients(msg);
		  								break;
		  								
		  		case "#valid":			getValid(client);
		  								break;
		  								
		  		case "#break":			addBreak(client);
		  								break;
		  				
		  		case "#line":			addLine(client);
		  								break;
		  								
		  		case "#who":			addWhoIBlock(client);
		  								break;
		  								
		  		default:				System.out.println("Server handleMessageFromClient default case got hit.");
		  								break;
		  }
		  
	  }else{
		  client.setInfo("idle", false);
		  logwrite(msg,client);
	  }
}   
  
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
public void monitor(Object msg, ConnectionToClient client){
	
	String [] temp = ((String) msg).split(" ");
	String monor = temp[1];
	String monee = client.getInfo("id").toString();
	if(monor.equals("server")){
		try {
			client.sendToClient("You cannot monitor the server.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}else{
	Thread[] clientThreadList = getClientConnections();
	ConnectionToClient cltemp;
	ConnectionToClient monitor = null;
	ConnectionToClient monitoree = null;
	
	if(!monor.equals("server")){
		for (int i=0; i<clientThreadList.length; i++){
			cltemp = (ConnectionToClient)clientThreadList[i];
			if(cltemp.getInfo("id").toString().equals(monor)){
				monitor = cltemp;
			}
			if(cltemp.getInfo("id").toString().equals(monee)){
				monitoree = cltemp;
			}
		}
	}
	
	try {
		if(((ArrayList<String>) monitoree.getInfo("blocklist")).contains(monitor.getInfo("id").toString())){
			monitoree.sendToClient("You have <"+monor+"> blocked. Please unblock to allow forwarding.");
		}else if(((ArrayList<String>) monitor.getInfo("blocklist")).contains(monitoree.getInfo("id").toString())){
			monitoree.sendToClient("<"+monor+"> has you blocked. Cannot forward messages.");
		}else if(monor.equals(monee)){
			monitoree.sendToClient("You cannot monitor yourself.");
		}else if(monitoree.getInfo("whomonitorsme") != "" || monitoree.getInfo("whoimonitor") != ""){
			monitoree.sendToClient("Monitoring already in use. Please #cancelmonitor to start another monitor.");
		}else if(monitor.getInfo("whomonitorsme") != "" || monitor.getInfo("whoimonitor") != ""){
			monitoree.sendToClient("<"+monor+"> has monitoring already in use. Please have them #cancelmonitor to start another monitor.");
		}else if(monitor.getInfo("availability").equals(false) || monitoree.getInfo("availability").equals(false)){
			monitoree.sendToClient("<"+monor+"> is currently unavailable.");		
		}else{
			monitor.sendToClient("<"+monee+"> wants you to monitor their messages! Type #accept to have their messages forwarded to you.");
			monitoree.sendToClient("<"+monor+"> will have to confirm the forward.");
			monee = "*"+monee;
			monitor.setInfo("whoimonitor", monee);
			monitoree.setInfo("whomonitorsme", monor);
			monitoree.setInfo("idle", true);
		}
	} catch (IOException e) {
		e.printStackTrace();
	}
	}
}
  
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public void acceptMonitor(ConnectionToClient client){
	String update = "";
	update = client.getInfo("whoimonitor").toString();
	update = update.substring(1);
	client.setInfo("whoimonitor", update);
	
	Thread[] clientThreadList = getClientConnections();
	ConnectionToClient cltemp;
	for (int i=0; i<clientThreadList.length; i++){
		cltemp = (ConnectionToClient) clientThreadList[i];
		if(cltemp.getInfo("id").toString().equals(update)){
			try {
				cltemp.sendToClient("<"+client.getInfo("id").toString()+"> will monitor your messages.");
				client.sendToClient("You will now view messages sent to <"+cltemp.getInfo("id").toString()+">.");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public void cancelMonitor(ConnectionToClient client){
	String update = "";
	ConnectionToClient monitor = null;
	ConnectionToClient monitoree = null;
	
	if(!client.getInfo("whoimonitor").equals("")){
		String name = client.getInfo("whoimonitor").toString();
		client.setInfo("whoimonitor", update);
		monitor = client;
		Thread[] clientThreadList = getClientConnections();
		ConnectionToClient cltemp;
		for (int i=0; i<clientThreadList.length; i++){
			cltemp = (ConnectionToClient) clientThreadList[i];
			if(cltemp.getInfo("id").toString().equals(name)){
				cltemp.setInfo("whomonitorsme", update);
				monitoree = cltemp;
			}
		}
		try {
			monitor.sendToClient("Current monitoring has been cancelled.");
			monitoree.sendToClient("Current monitoring has been cancelled.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	if(!client.getInfo("whomonitorsme").equals("")){
		String name = client.getInfo("whomonitorsme").toString();
		client.setInfo("whomonitorsme", update);
		monitor = client;
		Thread[] clientThreadList = getClientConnections();
		ConnectionToClient cltemp;
		for (int i=0; i<clientThreadList.length; i++){
			cltemp = (ConnectionToClient) clientThreadList[i];
			if(cltemp.getInfo("id").toString().equals(name)){
				cltemp.setInfo("whoimonitor", update);
				monitoree = cltemp;
			}
		}
		try {
			monitor.sendToClient("Current monitoring has been cancelled.");
			monitoree.sendToClient("Current monitoring has been cancelled.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
//Produces a list of all current channels
private void listChannels(Object msg, ConnectionToClient client) {
	try {
			client.sendToClient("Channels:");
		for(int i = 0; i < channelList.size(); i++){	
			client.sendToClient(channelList.get(i));
		}
	} catch (IOException e) {
		System.out.println("Cannot send channel list.");
		e.printStackTrace();
	}
}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//Leaves the specified channel if they are in it
private void leaveChannel(Object msg, ConnectionToClient client) {
	String [] message = ((String) msg).split(" ");
	String chan = message[1];
	String channels = "channels";
	if(!client.getInfo(channels).equals(null)){
		ArrayList<String> holder = (ArrayList<String>)client.getInfo(channels);
		if(!holder.contains(chan)){
			try {
				client.sendToClient("Cannot leave channels that you are not in.");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return;
		}
		if(holder.contains(chan)){
			holder.remove(chan);
			client.setInfo(channels, holder);
			try {
				client.sendToClient("You have left channel: " + chan);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//Joins the channel if it exists, error message if it doesnt exist
private void joinChannel(Object msg, ConnectionToClient client) {
	String [] message = ((String) msg).split(" ");
	String chan = message[1];
	String channels = "channels";
	if(!channelList.contains(chan)){
		try {
			client.sendToClient("Channel: " + chan + " does not exist, use #help for more information.");
			return;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	if(client.getInfo(channels) == null){
		ArrayList<String> temp = new ArrayList<String>();
		temp.add(chan);
		client.setInfo(channels, temp);
		return;
	}
	ArrayList<String> holder = (ArrayList<String>)client.getInfo(channels); 
	if(holder.contains(chan)){
		try {
			client.sendToClient("You are already in channel: " + chan);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}else{
		holder.add(chan);
		client.setInfo(channels, holder);
		try {
			client.sendToClient("You have joined channel: " + chan);
			System.out.println(holder);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//Creates the channel only, does not automatically join, unique list
private void createChannel(Object msg, ConnectionToClient client) {
	String [] message = ((String) msg).split(" ");
	String channel = message[1];
	if(channelList.contains(channel)){
		try {
			client.sendToClient("Channel already exists");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	else{
		channelList.add(channel);
		try {
			client.sendToClient(channel + " - channel has been created.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//Chat command
private void channelChat(Object msg, ConnectionToClient client) {
	Thread[] clientThreadList = getClientConnections();
	String [] message = ((String) msg).split(" ",3);
	String chan = message[1];
	String channels = "channels";
	ConnectionToClient ctc;
	ArrayList<String> chanlist = new ArrayList<String>();
	ArrayList<String> holder = (ArrayList<String>)client.getInfo(channels);
	if(!holder.contains(chan)){
		try{
			client.sendToClient("Cannot post message in a channel you don't belong to.");
			return;
		}catch (Exception e) {}
	}
	for (int i = 0; i< clientThreadList.length; i++){
		ctc = (ConnectionToClient)clientThreadList[i];
		chanlist = (ArrayList<String>)ctc.getInfo(channels);
		if(chanlist.contains(chan)){
			try{
				ctc.sendToClient("["+chan+"] <"+client.getInfo("id")+"> "+message[2]);
				if(!ctc.getInfo("whomonitorsme").equals("")){
	    			ConnectionToClient clnew;
	    			for(int j = 0; j < clientThreadList.length; j++){
	    				clnew = (ConnectionToClient)clientThreadList[j];
	    				if (!clnew.getInfo("whoimonitor").equals("")){
	    					if(clnew.getInfo("whoimonitor").equals(ctc.getInfo("id"))){
	    						clnew.sendToClient("(FORWARD) ["+chan+"] <"+client.getInfo("id")+"> " + message[2]);
	    					}
	    				}
	    			}  
				}
			}catch (Exception e) {}
		}
	}
	
}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public void logwrite(Object msg, ConnectionToClient client){
	  String id = "id";
	  System.out.println("Message received: " + msg + " from " + client);
	  String message = "<" + client.getInfo(id).toString() + "> " + msg;
	  this.sendToAllClients(message, client);
  }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
public void sendToAllClients(Object msg, ConnectionToClient client){
  Thread[] clientThreadList = getClientConnections();
  ConnectionToClient cl;
  for (int i=0; i<clientThreadList.length; i++){
 	  cl = (ConnectionToClient)clientThreadList[i];
    try{
    	if(cl.getInfo("availability").equals(true) && client.getInfo("availability").equals(true)){
    		if(!cl.getInfo("whomonitorsme").equals("")){
    			ConnectionToClient clnew;
    			for(int j = 0; j < clientThreadList.length; j++){
    				clnew = (ConnectionToClient)clientThreadList[j];
    				if (!clnew.getInfo("whoimonitor").equals("")){
    					if(clnew.getInfo("whoimonitor").equals(cl.getInfo("id"))){
    						clnew.sendToClient("(FORWARD) " + msg);
    					}
    				}
    			}    			
    		}
    		((ConnectionToClient)clientThreadList[i]).sendToClient(msg);
    	}
    	if(client.getInfo("availability").equals(false)){
    		client.sendToClient("Your status is currently unavailable. Only the server will be able to view your message.");
    	}
    }catch (Exception ex) {}
  }
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 

  public void sendToClientPrivate(Object msg, ConnectionToClient client){
	  Thread[] clientThreadList = getClientConnections();
	  ConnectionToClient cl;
	  String[] temp = ((String) msg).split(" ",3);
	  for (int i=0; i<clientThreadList.length; i++){
		  cl = (ConnectionToClient)clientThreadList[i];
		  if(cl.getInfo("id").toString().equals(temp[1])){
		  		try{
		  			if(cl.getInfo("availability").equals(true)){
		  				((ConnectionToClient)clientThreadList[i]).sendToClient("<"+client.getInfo("id").toString()+"> (PRIVATE) "+temp[2]);
		  				((ConnectionToClient)client).sendToClient("<"+client.getInfo("id").toString()+"> (PRIVATE) "+temp[2]);
		  			}else{
		  				((ConnectionToClient)client).sendToClient("<"+temp[1]+"> is currently unavailable!");
		  			}
		  		}catch (Exception ex) {}
		  }
	  }
  } 
  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
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
 ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
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
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
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
  
 ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  protected void unblockAll(ConnectionToClient client){
	  try{
	  	  String block = "blocklist";
		  ArrayList<String> temp = new ArrayList<String>();
		  temp = (ArrayList<String>)client.getInfo(block);
		  temp.clear();
		  client.setInfo(block, temp);
		  
		  
	  }
	  catch(Exception e){
		  
	  }
  }
	  
  
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
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
  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  /**
   * This method overrides the one in the superclass.  Called
   * when the server starts listening for connections.
   */
  
  protected void serverStarted()
  {
    System.out.println
      ("Server listening for connections on port " + getPort());
     
  }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  /**
   * This method overrides the one in the superclass.  Called
   * when the server stops listening for connections.
   */
  protected void serverStopped()
  {
    System.out.println
      ("Server has stopped listening for connections.");
  }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
   
  protected void clientConnected(ConnectionToClient client) {
	  String id = "id";
	  String block = "blocklist";
	  ArrayList<String> blocklist = new ArrayList<String>();
	  String info = client.toString();
	  client.setInfo(id, info);
	  client.setInfo(block, blocklist);
	  System.out.println(client + " has connected.");
  }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  synchronized protected void clientDisconnected(ConnectionToClient client) {
	  System.out.println(client + " has been disconnected.");
  }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  synchronized protected void clientException(ConnectionToClient client, Throwable exception) {
	  
	  String id = "id";
	  LoggedInUsers.remove(client.getInfo(id));
	  System.out.println(client.getInfo(id) + " has disconnected due to: " + exception);
  }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
  protected void listeningException(Throwable exception) {
	  System.out.println(exception);
  }
  
  //Class methods ***************************************************///////////////////////////////////////////////////////////////////////////////////////////////////////////////
  
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
    		String user = s.next();
    		validUsers.add(user);
    		pass.put(user, s.next());
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


////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

public void getValid(ConnectionToClient client){
	
	//+ status(client.getInfo("id") , client)
	
	try{	
		client.sendToClient("Valid Users:");
		for(int i = 0; i < validUsers.size(); i++) {
			client.sendToClient((validUsers.get(i)));
		}
	}catch (IOException e) {
				e.printStackTrace();
			}
}
		  
	
public void getUsers(ConnectionToClient client){

	try{
		client.sendToClient("Logged in Users:");
	for(int i = 0; i < LoggedInUsers.size(); i++) {
		client.sendToClient((LoggedInUsers.get(i)));
	}
	}catch(IOException e) {
		e.printStackTrace();
	}


	}
public void addBreak(ConnectionToClient client){
	
	try{
		client.sendToClient(" ");
	}catch(IOException e){
		e.printStackTrace();
	}
}
public void addLine(ConnectionToClient client){
	try{
		client.sendToClient("____________________________________");
	}catch(IOException e){
		e.printStackTrace();
	}
}
public void addWhoIBlock(ConnectionToClient client){
	try{
		client.sendToClient("Who I Block: ");
	}catch(IOException e){
		e.printStackTrace();
	}
}
}
//End of EchoServer class
