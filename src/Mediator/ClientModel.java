package Mediator;

import Model.Auction;
import Model.AuctionList;
import Model.User;


public interface ClientModel {
	public void register(User user);		
	public void logIn(String email,String password);	
	public void createAuction(Auction auction, User user);
	public void loadAucts(User user,String crit);  
	public void loadSearchAucts(String crit); 	
	public void loadAccountAucts();
	public void addFunds(int amount);
	public void placeBid(int uid, int aid, int amount);
	public void removeAuction(int aid, int uid);
	public void notifyAboutChange(Object obj);
	
}
