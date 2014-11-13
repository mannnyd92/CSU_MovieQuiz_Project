import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.List;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import client.ChatClient;
import common.ChatIF;


public class ClientGUI extends Frame implements ChatIF{
	
	private Button closeB = new Button("Close");
	private Button openB = new Button("Open");
	private Button sendB = new Button("Send");
	private Button quitB = new Button("Quit");
	private TextField portTxF = new TextField("12345");
	private TextField hostTxF = new TextField("localhost");
	private TextField message = new TextField();
	private Label portLB = new Label("Port: ", Label.RIGHT);
	private Label hostLB = new Label("Host: ", Label.RIGHT);
	private Label messageLB = new Label("Message: ", Label.RIGHT);
	private List messageList = new List();
	private ChatClient client;
	
	public ClientGUI(String host, int port, ChatClient clientC){
		super("Simple Chat");
		setSize(300, 400);
		setVisible(true);
		client = clientC;
		
		setLayout(new BorderLayout(5,5));
		Panel bottom = new Panel();
		add("Center", messageList);
		add("South", bottom);
		
		bottom.setLayout(new GridLayout(5,2,5,5));
		bottom.add(hostLB);
		bottom.add(hostTxF);
		bottom.add(portLB);
		bottom.add(portTxF);
		bottom.add(messageLB);
		bottom.add(message);
		bottom.add(openB);
		bottom.add(sendB);
		bottom.add(closeB);
		bottom.add(quitB);
		
		sendB.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				send();
			}
		});
	}
	
	
	
	@Override
	public void display(String message) {
		messageList.add(message);
		messageList.makeVisible(messageList.getItemCount()-1);
	}
	
	public void send(){
		try{
			client.sendToServer(message.getText());
		}
		catch(Exception ex){
			messageList.add(ex.toString());
			messageList.makeVisible(messageList.getItemCount()-1);
			messageList.setBackground(Color.yellow);
		}
	}
	
	

}
