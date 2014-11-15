import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.List;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import client.ChatClient;
import common.ChatIF;


public class ClientGUI extends Frame implements ChatIF{
	
	private JButton sendB = new JButton("Send");
	private JTextField message = new JTextField();
	private JLabel portLB;
	private JLabel hostLB;
	private JLabel messageLB = new JLabel("Message: ", Label.LEFT);
	private List messageList = new List();
	private ChatClient client;
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

	
	public ClientGUI(String host, int port, ChatClient clientC){
		super("Simple Chat");
		setSize(500, 600);
		setVisible(true);
		client = clientC;
		
		setLayout(new BorderLayout(5,5));
		Panel top = new Panel();
		Panel bottom = new Panel();
		Panel right = new Panel();
		add("Center", messageList);
		add("North", top);
		add("East", right);
		add("South", bottom);
		
		top.setLayout(new GridLayout(0,3));
		bottom.setLayout(new GridLayout(0,3));
		right.setLayout(new GridLayout(0,1));
		
		top.add(hostLB = new JLabel("Host: "+clientC.getHost()));
		top.add(portLB = new JLabel("Port: "+clientC.getPort()));	
		top.add(logoff);
		
		bottom.add(messageLB);
		bottom.add(message);
		bottom.add(sendB);
		
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
		
		sendB.addActionListener(new ActionListener(){
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
			client.sendToServer(message.getText());
		}
		catch(Exception ex){
			messageList.add(ex.toString());
			messageList.makeVisible(messageList.getItemCount()-1);
			messageList.setBackground(Color.yellow);
		}
	}
	
	

}
