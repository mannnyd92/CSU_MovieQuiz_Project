

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.List;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import client.ChatClient;
import common.ChatIF;
import drawpad.OpenDrawPad;

public class ClientGUI extends Frame implements ChatIF, Observer{

	private Choice choice = new Choice();
	private List Users = new List();
	private JButton sendB = new JButton("Send");
	private JTextField message = new JTextField();
	private JLabel portLB;
	private JLabel hostLB;
	private JLabel messageLB = new JLabel("Message: ", Label.LEFT);
	private List messageList = new List();
	Panel bottom = new Panel();
	ChatClient client;
	OpenDrawPad drawpad;
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
    private JButton users = new JButton("List Users");
    private JButton draw = new JButton("DrawPad");
	
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
//mannny////////////////////////////////////////////////////
		top.add(users);
////////////////////////////////////////////////////
		top.add(draw);
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
		

		createLoginPopup();

		
//manny////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		whoiblock.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try{
				client.send("#whoiblock");
				}
				catch(Exception x){}
			}
		});
	
		whoblocksme.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try{
				client.send("#whoblocksme");
				}
				catch(Exception x){}
			}
		});
		available.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try{
				client.send("#available");
				}
				catch(Exception x){}
			}
		});
		notavailable.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try{
				client.send("#notavailable");
				}
				catch(Exception x){}
			}
		});
		status.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try{
				client.send("#status tim");
				}
				catch(Exception x){}
			}
		});
		users.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try{
				client.send("#users");
				}
				catch(Exception x){}
			}
		});

		block.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try{
				createBlockingPopup();
				}
				catch(Exception x){}
			}
		});
		

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		


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

	privateMess.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
			setPrivate();
		}
	});
	
	draw.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
			opendrawpad();
		}
	});
	}
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void opendrawpad(){
		drawpad = new OpenDrawPad(client, this);
	}
	
	
public void createBlockingPopup(){
		blockingPopup blocking = new blockingPopup(this);
		blocking.show();
}	

class blockingPopup extends Dialog{
	
	int H_SIZE = 200;
	int V_SIZE = 215;
	Panel p = new Panel();
	TextField user = new TextField("tim");
	TextField pass = new TextField("pass");
	TextField host = new TextField("localhost");
	TextField port = new TextField("4444");
	Label username = new Label("Username");
	Label password = new Label("Password");
	Label hosttext = new Label("Host");
	Label porttext = new Label("Port");
	JButton exit = new JButton("Exit");
	JButton login = new JButton("Login");
	
	public blockingPopup(Frame parent){
		super(parent, true);
		p.setLayout(new GridLayout(5,2));
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
	
	}}
	
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		


	
	




	public void display(String message) {
	      String msg = (String)message;
	      
	      if (msg.startsWith("#send"))
	      {
	        drawpad.update(client, msg.substring(6));
	        return;
	      }
		
		
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
	
	public void send(String msg){
		try{
			client.send(msg);
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

	public void setPrivate(){
		PrivatePopup pp = new PrivatePopup(this);
		pp.show();	
	}
	
	private void createLoginPopup(){
		LoginPopup lp = new LoginPopup(this);
		lp.show();
	}
	
	class PrivatePopup extends Dialog{
		
		int H_SIZE = 200;
		int V_SIZE = 215;
		
		Panel panel = new Panel();
		Label userL = new Label("User:");
		TextField userTF = new TextField();
		Label messL = new Label("Message:");
		TextField messTF = new TextField();
		JButton exit = new JButton("Exit");
		JButton send = new JButton("Send");

		public PrivatePopup(Frame parent){
			super(parent, true);
			
			panel.setLayout(new GridLayout(0,2));
			panel.add(userL);
			panel.add(userTF);
			panel.add(messL);
			panel.add(messTF);
			panel.add(exit);
			panel.add(send);
			add("Center", panel);
			resize(H_SIZE, V_SIZE);
			
			exit.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					dispose();
				}
			});
			
			send.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					try{
						String message = "#private "+userTF.getText()+" "+messTF.getText();
						client.send(message);
						dispose();
					}
					catch(Exception ex){
						dispose();
					}
				}
			});			
		}
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

	@Override
	public void update(Observable obs, Object obj) {
	    if (!(obj instanceof String))
	        return;
	      
	      String msg = (String)obj;
	      
	      if (msg.startsWith("#send"))
	      {
	        drawpad.update(obs, msg.substring(6));
	        send(msg);
	      }
		
	}

}
