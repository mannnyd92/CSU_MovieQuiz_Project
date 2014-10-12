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
		
		
		
		
		
		String serverMessage = "SERVER MSG> " + message;
		server.sendToAllClients(serverMessage);
		display(message);
	}
	  
	@Override
	public void display(String message) {
		System.out.println("SERVER MSG> " + message);
		
	}

}
