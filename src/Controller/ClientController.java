package Controller;

import java.util.Observable;
import java.util.Observer;

import Mediator.ClientModelManager;
import Mediator.ClientModel;
import Model.Auction;
import Model.LogIn;
import Model.User;
import View.AccountView;
import View.AuctionView;
import View.BrowsingView;
import View.CreateAuctionView;
import View.LogInView;
import View.RegisterView;
import View.View;



public class ClientController{
	private ClientModel model;
	private View view;
	private User user;
	 
		private ClientController() {
			this.view = View.getInstance();
			this.model = ClientModelManager.getInstance();
			((Observable) model).addObserver(view);
		}

		private static class ClientControllerHolder {
			private static final ClientController INSTANCE = new ClientController();
		}

		public static ClientController getInstance() {
			return ClientControllerHolder.INSTANCE;
		}
	 public void register(User user)
	 {
		 
		 model.register(user);
	 }
	 
	 public void logIn(LogIn login)
	 {
		
		 model.logIn(login.getEmail(),login.getPassword());
	 }
	 
	 public void getAuctions(String string){
		 model.loadAucts(user, string);
	 }
	 
	 public void getSearchAuctions(String string){
		 model.loadSearchAucts(string);
		
	 }
	 public void createAuction(Auction auction) {
			model.createAuction(auction, user);
		}
	 public void getAccountAuctions(){
		 model.loadAccountAucts();
	 }
	 public int getuid(){
		 return user.getUid();
	 }
	 
	 public User getUser(){
		 return user;
	 }
	 public void setUser(User user2){
		 user = user2;
		
	 }
	 public void addFunds(int amount){
		 model.addFunds(amount);
	 }
	 public void switchView(String name) {
			
			switch (name) {
			case "login":	
				view.setContentPane(LogInView.getInstance());
				
				((Observable) model).deleteObservers();
				((Observable) model).addObserver((Observer) view.getContentPane());
				view.setTitle("Log In");
				break;
			case "register":	
				view.setContentPane(RegisterView.getInstance());	
				((Observable) model).deleteObservers();
				((Observable) model).addObserver((Observer) view.getContentPane());
				view.setTitle("Registration");
				break;
			case "browsing":	
				view.setContentPane(BrowsingView.getInstance());	
				((Observable) model).deleteObservers();
				((Observable) model).addObserver((Observer) view.getContentPane());
				BrowsingView.getInstance().updateBalance();
				BrowsingView.getInstance().updateName();
				view.setTitle("Browse Auctions");
				break;
			case "create":	
				view.setContentPane(CreateAuctionView.getInstance());	
				((Observable) model).deleteObservers();
				((Observable) model).addObserver((Observer) view.getContentPane());
				CreateAuctionView.getInstance().updateBalance();
				CreateAuctionView.getInstance().updateName();
				view.setTitle("Create Auction");
				break;
			case "account":	
				view.setContentPane(AccountView.getInstance());	
				((Observable) model).deleteObservers();
				((Observable) model).addObserver((Observer) view.getContentPane());
				AccountView.getInstance().updateBalance();
				AccountView.getInstance().updateName();
				view.setTitle("Account details");
				
				break;
			case "auction":	
				view.setContentPane(AuctionView.getInstance());	
				((Observable) model).deleteObservers();
				((Observable) model).addObserver((Observer) view.getContentPane());
				AuctionView.getInstance().updateBalance();
				AuctionView.getInstance().updateName();;
				view.setTitle("Auction details");
				break;
				
			}
			view.getContentPane().revalidate(); 
			view.getContentPane().repaint();	
		}
	 public void auct(Auction tmp){
		 model.notifyAboutChange(tmp);
		 
	 }
	 
	 public void placeBid(int aid, int uid, int amount){
		 model.placeBid(uid, aid, amount);
	 }
	 
	 public void removeAuction(int aid){
		 model.removeAuction(aid,user.getUid());
	 }
}
