import java.io.BufferedReader;
import java.io.InputStreamReader;

import common.ChatIF;


public class ServerConsole implements ChatIF{
	EchoServer server;
	
	
	public ServerConsole(EchoServer s){
		server = s;

		new Thread(){
			public void run(){
				accept();
			}
		}.start();
	}
	
	  public void accept() 
	  {
	    try
	    {

	      BufferedReader fromConsole = 
	        new BufferedReader(new InputStreamReader(System.in));
	      String message;
	      while (true){
	        message = fromConsole.readLine();
	        if(message != null){
	        	handleMessageFromServerUI(message);
	        }
	      }
	    } 
	    catch (Exception ex) 
	    {
	      System.out.println
	        ("Unexpected error while reading from console!");

	    }
	  }
	
	public void handleMessageFromServerUI(String message){
		
		if(message.charAt(0) == '#'){
			String tmpmes = message.substring(1,message.length());
			String param = "";
			String chkmes = "";
			boolean flag = false;
			if(tmpmes.length() > 6){
				chkmes = tmpmes.substring(0,7);
				if(tmpmes.length() > 6){
							
					chkmes = tmpmes.substring(0,7);
						
					
					if(chkmes.equals("setport")){
						if( tmpmes.length() > 8){
							if(tmpmes.charAt(7) != ' '){
								System.out.println("A space is required after the command!");
							}
							else{
							param = tmpmes.substring(8, tmpmes.length());
							tmpmes = "setport";
							flag = true;
							}
						}else{System.out.println("Please give parameter after command!");}
					}
				
				}}		
						switch (tmpmes){
							case "quit":	try{server.close();
											System.exit(0);
											}catch(Exception e){}
											break;
					
											
							case "setport":	if(!server.isListening() && flag){
												try{
													int port = Integer.valueOf(param);
													server.setPort(port);
													flag = false;
												}catch(Exception e){}
											}else if(server.isListening() && flag == true){
												System.out.println("Server already up! Stop server to change port");}
											break;	
											
											
							case "getport":	System.out.println(server.getPort());
											break;
								
							case "stop": 	server.stopListening();
											break;
											
							case "start": 	if(!server.isListening()){
												try{server.listen();}catch(Exception e){}
											}
											else{
												System.out.println("Server already up!");
											}
											break;
											
							case "close":	try{server.close();
											
											}catch(Exception e){}
											break;
											
							default:		System.out.println("# Requires that it is followed by a valid command and a parameter!");
							
						}
							  
					  }
		
					  else{
							
							String serverMessage = "SERVER MSG> " + message;
							server.sendToAllClients(serverMessage);
							display(message);
					}
		}
	  
	@Override
	public void display(String message) {
		System.out.println("SERVER MSG> " + message);
		
	}

}
