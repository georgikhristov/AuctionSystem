package Mediator;

import java.io.IOException;
import java.util.Observable;

import Model.Auction;
import Model.AuctionList;
import Model.User;

public class ClientModelManager extends Observable implements ClientModel {
	
	private ClientModelProxy proxy;
	private ClientModelManager() {
		try {
			proxy = new ClientModelProxy(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static class ClientModelManagerHolder {
		private static final ClientModelManager INSTANCE = new ClientModelManager();
	}

	public static ClientModelManager getInstance() {
		return ClientModelManagerHolder.INSTANCE;
	}

	@Override
	public void register(User user) {
		proxy.register(user);

	}


	

	

	@Override
	public void logIn(String email, String password) {
		proxy.logIn(email, password);

	}

	@Override
	public void createAuction(Auction auction, User user) {
		proxy.createAuction(auction, user);

	}

	@Override
	public void loadAucts(User user, String crit) {
		proxy.loadAucts(user, crit);
		
	}





	@Override
	public void loadSearchAucts(String crit) {
		proxy.loadSearchAucts(crit);
		
		
	}


	@Override
	public void loadAccountAucts() {
		proxy.loadAccountAucts();
		
	}


	@Override
	public void addFunds(int amount) {
		proxy.addFunds(amount);
		
	}



	@Override
	public void placeBid(int uid, int aid, int amount) {
		proxy.placeBid(uid, aid, amount);
		
	}


	@Override
	public void removeAuction(int aid, int uid) {
	proxy.removeAuction(aid,uid);
	
		
	}

	@Override
	public void notifyAboutChange(Object obj) {
		super.setChanged();
		super.notifyObservers(obj);
	}

}
