package Mediator;

import Model.Auction;
import Model.AuctionList;
import Model.User;


public interface ServerModel {
	public void addUser(User user);
	public boolean emailValidation(User user);
	public AuctionList loadAuction(String crit); 
	public boolean LogIn(String email,String password);
	public User getUser(String email);
	public void addAuction(Auction auct);
	public AuctionList searchList(String crit);
	public AuctionList[] loadAccountAuctions(int udi);
	public void removeAuction(int aid);
	public void addFunds(int uid,int amount);
	public Auction loadAuct(int aid);
	public void bid(int aid, int uid, int amount);
	public int checkBal(int uid, int aid);
	

}
