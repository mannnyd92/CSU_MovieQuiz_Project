

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Event;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.List;
import java.awt.Panel;
import java.awt.Scrollbar;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import client.ChatClient;
import common.ChatIF;


public class ClientGUI extends Frame implements ChatIF{
	

	//
	private Choice choice = new Choice();
	private List Users = new List();
			
	//

	private JButton sendB = new JButton("Send");
	private JTextField message = new JTextField();
	private JLabel portLB;
	private JLabel hostLB;
	private JLabel messageLB = new JLabel("Message: ", Label.LEFT);
	private List messageList = new List();
	Panel bottom = new Panel();
	ChatClient client;
	private JButton privateMess = new JButton("Private");
	private JButton channel = new JButton("Channel");
	private JButton forward = new JButton("Forward");
	private JRadioButton available = new JRadioButton("Available",true);
	private JRadioButton notavailable = new JRadioButton("Not Available",false);
    private ButtonGroup group = new ButtonGroup();
    private JButton status = new JButton("Status");
    private JButton logoff = new JButton("Change Login");
    private JButton whoblocksme = new JButton("Who Blocks Me");
    private JButton whoiblock = new JButton("Who I Block");
    private JButton block = new JButton("Blocking");

	
	public ClientGUI(){
		super("Simple Chat");
		setSize(500, 600);
		setVisible(true);
		setLayout(new BorderLayout(5,5));
		Panel top = new Panel();
		Panel bottom = new Panel();
		add("Center", messageList);
		add("North", top);
		add("South", bottom);
		

		top.setLayout(new GridLayout(0,3));
		bottom.setLayout(new GridLayout(0,3));
		
//		top.add(hostLB = new JLabel("Host: "+client.getHost()));
//		top.add(portLB = new JLabel("Port: "+client.getPort()));	
		top.add(logoff);
		bottom.add(messageLB);
		bottom.add(message);
		bottom.add(sendB);

		//
//		choice.add("");
//		choice.add("this");
//		choice.add("that");
//		bottom.add(choice);
		//vbottom.add(choice);

		

		
		bottom.add(privateMess);
		bottom.add(channel);
		bottom.add(forward);
		
		bottom.add(whoblocksme);
		bottom.add(whoiblock);
		bottom.add(block);
		
		bottom.add(available);
		bottom.add(notavailable);
		group.add(available);
		group.add(notavailable);
		bottom.add(status);
		
		createLoginPopup();

		
		//
		sendB.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				send();
				message.setText("");
			}
		});
	
	
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent we){
				System.exit(0);
			}
		});
	
		logoff.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try {
					client.closeConnection();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				createLoginPopup();
			}
		});
}
	
	
	public void display(String message) {
		
		messageList.add(message);
		messageList.makeVisible(messageList.getItemCount()-1);
	}
	
	public void send(){
		try{
			client.send(message.getText());
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
	
	private void createLoginPopup(){
		LoginPopup lp = new LoginPopup(this);
		lp.show();
	}
	
	class LoginPopup extends Dialog{
		
		int H_SIZE = 200;
		int V_SIZE = 215;
		
		Panel p = new Panel();
		TextField user = new TextField("tim");
		TextField pass = new TextField("pass");
		TextField host = new TextField("localhost");
		TextField port = new TextField("5432");
		Label username = new Label("Username");
		Label password = new Label("Password");
		Label hosttext = new Label("Host");
		Label porttext = new Label("Port");
		JButton exit = new JButton("Exit");
		JButton login = new JButton("Login");
		
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
