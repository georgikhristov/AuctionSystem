package Mediator;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import Controller.ClientController;
import Model.Auction;
import Model.AuctionList;
import Model.User;

public class ClientModelProxy implements ClientModel, Observer {
	private ObjectInputStream inFromServer;
	private ObjectOutputStream outToServer;
	private ClientModel model;
	private Socket socket;


	// private static final String HOST = "localhost";
	private static final String HOST = "localhost";
	private static final int PORT = 2910;

	public ClientModelProxy(ClientModel model) throws IOException {
		// Read line from keyboard.
		Scanner keyboard = new Scanner(System.in);
	
		String host = "127.0.0.1";
		host = (host == "") ? HOST : host;
		keyboard.close();

		this.model = model;
		try {
			socket = new Socket(host, PORT);
			outToServer = new ObjectOutputStream(socket.getOutputStream());
			inFromServer = new ObjectInputStream(socket.getInputStream());
			ClientReceiver reciever = new ClientReceiver(inFromServer, model);
			new Thread(reciever, "Reciever").start();
		} catch (Exception e) {
		}

	}

	@Override
	public void update(Observable arg0, Object arg1) {
	}


	

	@Override //MAIN WORKING
	public void loadSearchAucts(String crit) {
		Map tmp = new HashMap<>();
		tmp.put("action","search");
		tmp.put("keyword", crit);
		
		try {
			
			outToServer.writeObject(tmp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override //MAIN WORKING
	public void loadAccountAucts() {
		Map tmp = new HashMap<>();
		tmp.put("action","accountload");
		tmp.put("uid", ClientController.getInstance().getuid());
		try {
			outToServer.writeObject(tmp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}



	@Override
	public void register(User user) {
		try {
			Map tmp = new HashMap<>();
			tmp.put("action","register");
			tmp.put("user",user);
			outToServer.writeObject(tmp);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	

	@Override
	public void logIn(String email, String password) {
			try {
			
			Map tmp = new HashMap<>();
			tmp.put("action","login");
			tmp.put("email",email);
			tmp.put("password",password);
			outToServer.writeObject(tmp);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void createAuction(Auction auction, User user) {
		Map tmp = new HashMap<>();
		tmp.put("action", "addauction");
		tmp.put("user", user);
		tmp.put("auction", auction);
		try {
			outToServer.writeObject(tmp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void loadAucts(User user, String crit) {
		Map tmp = new HashMap<>();
		tmp.put("action", "browse");
		tmp.put("crit", crit);
		tmp.put("user", user);
		try {
			outToServer.writeObject(tmp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}



	@Override
	public void addFunds(int amount) {
		try {
			Map tmp = new HashMap<>();
			tmp.put("action","addFunds");
			tmp.put("user",ClientController.getInstance().getuid());
			tmp.put("amount",amount);
			tmp.put("email", ClientController.getInstance().getUser().getEmail());
			outToServer.writeObject(tmp);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	

	@Override
	public void placeBid(int uid, int aid, int amount) {
		Map tmp = new HashMap<>();
		tmp.put("action","placebid");
		tmp.put("user",ClientController.getInstance().getUser());
		tmp.put("amount",amount);
		tmp.put("aid", aid);
		
		try {
			outToServer.writeObject(tmp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	@Override
	public void removeAuction(int aid, int uid) {
		try {
			Map tmp = new HashMap<>();
			tmp.put("action","removeAuction");
			tmp.put("aid",aid);
			tmp.put("uid", uid);
			outToServer.writeObject(tmp);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void notifyAboutChange(Object obj) {
		// TODO Auto-generated method stub
		
	}

	
}
