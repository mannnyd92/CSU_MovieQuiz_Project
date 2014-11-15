

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Event;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.List;
import java.awt.Panel;
import java.awt.PopupMenu;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

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
	private PopupMenu popup = new PopupMenu("Enter login info");
	Panel bottom = new Panel();
	ChatClient client;
	ChatIF chatif;
	
	public ClientGUI(){
		super("Simple Chat");
		setSize(300, 400);
		setVisible(true);
//		client = clientC;
//		this.chatif = chatif;
		
		setLayout(new BorderLayout(5,5));
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
		bottom.add(popup);
		
		LoginPopup lp = new LoginPopup(this);
		lp.show();

		
		sendB.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				send();
			}
		});
	
	
	quitB.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
			send();
		}
	});
	
	
	this.addWindowListener(new WindowAdapter(){
		public void windowClosing(WindowEvent we){
			System.exit(0);
		}
	});
}
	
	
	public void display(String message) {
		messageList.add(message);
		messageList.makeVisible(messageList.getItemCount()-1);
	}
	
	public void send(){
		try{
			client.handleMessageFromClientUI(message.getText());
		}
		catch(Exception ex){
			messageList.add(ex.toString());
			messageList.makeVisible(messageList.getItemCount()-1);
			messageList.setBackground(Color.yellow);
		}
	}
	
	public void setClient(String user, String pass, String host, int portint){
		try {
			client = new ChatClient(user, pass, host, portint, this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	class LoginPopup extends Dialog{
		
		int H_SIZE = 200;
		int V_SIZE = 200;
		
		Panel p = new Panel();
		TextField user = new TextField();
		TextField pass = new TextField();
		TextField host = new TextField();
		TextField port = new TextField();
		Label username = new Label("Username");
		Label password = new Label("Password");
		Label hosttext = new Label("Host");
		Label porttext = new Label("Port");
		Button exit = new Button("Exit");
		Button login = new Button("Login");
		
		public LoginPopup(Frame parent){
			super(parent, true);
			

			p.setLayout(new GridLayout(5,2,5,5));
			p.add(username);
			p.add(user);
			p.add(password);
			p.add(pass);
			p.add(hosttext);
			p.add(host);
			p.add(porttext);
			p.add(port);
			p.add(exit);
			p.add(login);
			add("South",p);
			resize(H_SIZE, V_SIZE);
		
		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.exit(0);
			}
		});
		
		login.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				int portint = Integer.parseInt(port.getText());
				setClient(user.getText(), pass.getText(), host.getText(), portint);
				dispose();
			}
		});
		
		}
	}
	
	public static void main(String[] args){
		ClientGUI cg = new ClientGUI();
	}
	
}
