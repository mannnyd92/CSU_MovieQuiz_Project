

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
import javax.swing.JComboBox;
import javax.swing.JLabel;

import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import client.ChatClient;
import common.ChatIF;
import drawpad.OpenDrawPad;

public class ClientGUI extends Frame implements ChatIF, Observer{
	
	private boolean flag = true;
	private boolean flag2 = false;
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
    private List BmessageList = new List();
    private List WmessageList = new List();
    
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
		top.add(logoff);
		top.add(users);
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
				//client.send("#status tim");
				createStatusPopup();
				}
				catch(Exception x){}
			}
		});
		users.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try{
				client.send("#users");
				client.send("#break");
				client.send("#valid");
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

	forward.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
			setForward();
		}
	});
	
	channel.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
			setChannel();
		}
	});
	
	}
//Status/////////////////////////////////////////////////////////////////////////////////////////////
	
	class statusPopup extends Dialog{
		
		JTextField user = new JTextField();
		Label username = new Label("User: ", Label.LEFT);
		JButton exit = new JButton("Exit");
		JButton status = new JButton("Get Status");
		
		
		public statusPopup(Frame parent){
			super(parent, true);
			
			int H_SIZE = 350;
			int V_SIZE = 600;
			Panel p = new Panel();
			Panel Bcenter = new Panel();
			Panel Bbottom = new Panel();
			
			
			add("Center", BmessageList);
			add("South", Bcenter);
			Bcenter.setLayout(new GridLayout(0,3));
			flag = false;
			BmessageList.clear();
			client.send("#listchannel");
			client.send("#break");
			client.send("#valid");
			

			Bcenter.add(status);
			Bcenter.add(exit);
			resize(H_SIZE, V_SIZE);
			
		
		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				BmessageList.clear();
				dispose();
				flag = true;
			}
		});
		
		status.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(BmessageList.getSelectedIndex() != -1){
					client.send("#line");
					client.send("#break");
					client.send("#status " + BmessageList.getSelectedItem());
					client.send("#break");
					client.send("#listchannel");
					client.send("#break");
					client.send("#valid");
				}
				
			}
		});
		
		
		
		}
	}
//Blocking//////////////////////////////////////////////////////////////////////////////////////////////

class blockingPopup extends Dialog{

	JButton exit = new JButton("Exit");
	JButton block = new JButton("Block");
	JButton unblock = new JButton("unBlock");
	JLabel select = new JLabel("-Select user to unblock");
	JLabel all	= new JLabel("-To unblock all select blank line");
	
	public blockingPopup(Frame parent){
		super(parent, true);
	
		int H_SIZE = 300;
		int V_SIZE = 800;
		Panel p = new Panel();
		Panel Bcenter = new Panel();
		Bcenter.setLayout(new GridLayout(0,2));
		p.setLayout(new GridLayout(2,2));
		add("North", p);
		add("Center", BmessageList);
		add("South", Bcenter);
		
		flag = false;
		BmessageList.clear();
		client.send("#who");
		client.send("#whoiblock");
		client.send("#break");
		client.send("#users");
		client.send("#break");
		client.send("#valid");
		client.send("#line");
		
		p.add(select);
		p.add(all);
		Bcenter.add(block);
		Bcenter.add(unblock);
		Bcenter.add(exit);
		resize(H_SIZE, V_SIZE);
	
	
	exit.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
			BmessageList.clear();
			dispose();
			flag = true;
		}
	});
	
	block.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
			
			
			
			if(BmessageList.getSelectedIndex() != -1){
				
				client.send("#break");
				client.send("#block " + BmessageList.getSelectedItem());
				client.send("#break");
				client.send("#who");
				client.send("#whoiblock");
				client.send("#break");
				client.send("#users");
				client.send("#break");
				client.send("#valid");
				client.send("#line");
				
			}
			
		}
	});
	
	unblock.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e){
			if(BmessageList.getSelectedIndex() != -1){
				client.send("#break");
				client.send("#unblock " + BmessageList.getSelectedItem());
				client.send("#break");
				client.send("#who");
				client.send("#whoiblock");
				client.send("#break");
				client.send("#users");
				client.send("#break");
				client.send("#valid");
				client.send("#line");
			}
			else{
				client.send("#break");
				client.send("#unblock");
				client.send("#break");
				client.send("#who");
				client.send("#whoiblock");
				client.send("#break");
				client.send("#users");
				client.send("#break");
				client.send("#valid");
				client.send("#line");
			}
		}
	});
	}}
	
//Helper////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		


public void display(String message) {
	
	CharSequence c = "wants you to monitor their messages! Type #accept to have their messages forwarded to you.";

	if(flag){
		String msg = (String)message;

		if (msg.startsWith("#send"))
		{
			drawpad.update(client, msg.substring(6));
			return;
		}

		try{

			if (message.contains(c)){

				setForwardAccept(message.split(" ",2)[0]);

			}else{

				messageList.add(message);
				messageList.makeVisible(messageList.getItemCount()-1);
			}
		}catch(Exception e){}  


	}else{

		BmessageList.add(message);
		BmessageList.makeVisible(BmessageList.getItemCount()-1);

	}

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

	private void setPrivate(){
		PrivatePopup pp = new PrivatePopup(this);
		pp.show();	
	}
	
	private void setForward(){
		ForwardPopup fp = new ForwardPopup(this);
		fp.show();	
	}
	
	private void createLoginPopup(){
		LoginPopup lp = new LoginPopup(this);
		lp.show();
	}
	
	public void setForwardAccept(String c){
		ForwardAcceptPopup ap = new ForwardAcceptPopup(this, c);
		ap.show();
	}
	
	public void setChannel(){
		ChannelPopup cp = new ChannelPopup(this);
		cp.show();
	}

	public void opendrawpad(){
		drawpad = new OpenDrawPad(client, this);
	}
	
	public void createBlockingPopup(){
		blockingPopup blocking = new blockingPopup(this);
		blocking.show();
	}
	public void createStatusPopup(){
		statusPopup status = new statusPopup(this);
		status.show();
	}
//channel stuff//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
class ChannelPopup extends Dialog{
	
	int H_SIZE = 500;
	int V_SIZE = 250;
	
	Panel panel = new Panel();
	Label createL = new Label("Create Channel:");
	TextField createTF = new TextField();
	JButton createB = new JButton("Create");
	Label joinL = new Label("Join Channel:");
	TextField joinTF = new TextField();
	JButton joinB = new JButton("Join");
	Label messageL = new Label("Message:");
	TextField messageTF = new TextField();
	Label blank = new Label("");
	Label chanmessageL = new Label("To Channel:");
	TextField chanmessageTF = new TextField();
	JButton messageB = new JButton("Send");
	Label leaveL = new Label("Leave Channel:");
	TextField leaveTF = new TextField();
	JButton leaveB = new JButton("Leave Channel");
	Label listL = new Label("List Users");
	TextField listTF = new TextField();
	JButton listB = new JButton("Channel List");
	Label blank2 = new Label("");
	Label blank3 = new Label("");
	JButton exit = new JButton("Exit");
	
	public ChannelPopup(Frame parent){
		super(parent, true);
		
		panel.setLayout(new GridLayout(6,3));
		panel.add(createL);
		panel.add(createTF);
		panel.add(createB);
		panel.add(joinL);
		panel.add(joinTF);
		panel.add(joinB);
		panel.add(messageL);
		panel.add(messageTF);
		panel.add(blank);
		panel.add(chanmessageL);
		panel.add(chanmessageTF);
		panel.add(messageB);
		panel.add(leaveL);
		panel.add(leaveTF);
		panel.add(leaveB);
		panel.add(listB);
		panel.add(blank2);
		panel.add(exit);
		
		add("Center", panel);
		resize(H_SIZE, V_SIZE);
		
		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				dispose();
			}
		});
		
		createB.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(!createTF.getText().isEmpty()){
					try{
						String message = "#createchannel "+createTF.getText();
						client.send(message);
					}
					catch(Exception ex){
						dispose();
					}
				}
			}
		});
		
		joinB.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(!joinTF.getText().isEmpty()){
					try{
						String message = "#joinchannel "+joinTF.getText();
						client.send(message);
					}
					catch(Exception ex){
						dispose();
					}
				}
			}
		});
		
		messageB.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(!chanmessageTF.getText().isEmpty() && !messageTF.getText().isEmpty()){
					try{
						String message = "#channel "+chanmessageTF.getText()+" "+messageTF.getText();
						client.send(message);
					}
					catch(Exception ex){
						dispose();
					}
				}
			}
		});
		
		leaveB.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(!leaveTF.getText().isEmpty()){
					try{
						String message = "#leavechannel "+leaveTF.getText();
						client.send(message);
					}
					catch(Exception ex){
						dispose();
					}
				}
			}
		});
		
		listB.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try{
					String message = "#listchannel";
					client.send("#listchannel");
				}
				catch(Exception ex){
					dispose();
				}
			}
		});
		
	}
	
}
//Forwarding////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

class ForwardAcceptPopup extends Dialog{
		
		int H_SIZE = 300;
		int V_SIZE = 215;
		
		Panel panel = new Panel();
		Label userL;
		JButton cancelforward = new JButton("Cancel Forwarding");
		JButton acceptforward = new JButton("Accept Forwarding");
		
		public ForwardAcceptPopup(Frame parent, String c){
			super(parent, true);
			
			userL = new Label(c+" wants you to monitor their messages.");
			panel.setLayout(new GridLayout(3,2));
			panel.add(userL);
			panel.add(acceptforward);
			panel.add(cancelforward);
			add("Center", panel);
			resize(H_SIZE, V_SIZE);
			
			cancelforward.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					try{
						String message = "Monitoring not accepted!";
						client.send(message);
						dispose();
					}
					catch(Exception ex){
						dispose();
					}
				}
			});
			
			acceptforward.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					try{
						String message = "#accept";
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
	
	class ForwardPopup extends Dialog{
		
		int H_SIZE = 200;
		int V_SIZE = 215;
		
		Panel panel = new Panel();
		Label userL = new Label("Forward To:");
		TextField userTF = new TextField();
		JButton cancelforward = new JButton("Cancel Forwarding");
		JButton acceptforward = new JButton("Forward");
		JButton exit = new JButton("Exit");
		
		public ForwardPopup(Frame parent){
			super(parent, true);
			
			panel.setLayout(new GridLayout(5,2));
			panel.add(userL);
			panel.add(userTF);
			panel.add(acceptforward);
			panel.add(cancelforward);
			panel.add(exit);
			add("Center", panel);
			resize(H_SIZE, V_SIZE);
			
			exit.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					dispose();
				}
			});
			
			cancelforward.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					try{
						String message = "#cancelmonitor";
						client.send(message);
						dispose();
					}
					catch(Exception ex){
						dispose();
					}
				}
			});
			
			acceptforward.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					if(!userTF.getText().isEmpty()){
						try{
							String message = "#monitor "+userTF.getText();
							client.send(message);
							dispose();
						}
						catch(Exception ex){
							dispose();
						}
					}
				}
			});			
		}
	}
//Private//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
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
					if(!userTF.getText().isEmpty() && !messTF.getText().isEmpty()){
						try{
							String message = "#private "+userTF.getText()+" "+messTF.getText();
							client.send(message);
							dispose();
						}
						catch(Exception ex){
							dispose();
						}
					}
				}
			});			
		}
	}
//Login///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	class LoginPopup extends Dialog{

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
