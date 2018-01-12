package Mediator;

import java.io.IOException;
import java.sql.SQLException;

import Model.*;



public class ServerModelManager implements ServerModel
{
   private DataBase dbt;
   private ServerConnection server;
 
   public ServerModelManager() throws ClassNotFoundException
   {
	   dbt = new DataBase();
      this.server = new ServerConnection(this);
      new Thread(server, "Server").start();
   }

   @Override
   public void addUser(User user)
   {
      try {
		dbt.addUser(user);
	} catch (SQLException e) {
		e.printStackTrace();
	}
     
   }
   
@Override
public AuctionList loadAuction(String crit){
	   
	   switch (crit) {
	   case "all" : try {
			return dbt.loadAllAuctions();
		} catch (NullPointerException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   case "car" :  try {
			return dbt.loadWithCat("car");
		} catch (NullPointerException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   case "other" :  try {
			return dbt.loadWithCat("other");
		} catch (NullPointerException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   case "elec" :  try {
			return dbt.loadWithCat("elec");
		} catch (NullPointerException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   case "book" :  try {
			return dbt.loadWithCat("book");
		} catch (NullPointerException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   case "cloth" :  try {
			return dbt.loadWithCat("cloth");
		} catch (NullPointerException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   
	   
	   default : return null;
	   }
	
   }

@Override
public AuctionList searchList(String crit){
	try {
		return dbt.loadWithSearch(crit);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return null;
	} 
	   
   }

@Override
public boolean LogIn(String email,String password) {
	return dbt.login(email,password);
	
}



@Override
public User getUser(String email) {
	
		return dbt.loadUser(email);
	
}

//@Override
@Override
public boolean emailValidation(User user) {
	try {
		return dbt.emailValidation(user.getEmail());
	} catch (SQLException e) {
	
		e.printStackTrace();
	}
	return false;
}

@Override
public void addAuction(Auction auct) {
	try {
		dbt.addAuction(auct);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}

@Override
public AuctionList[] loadAccountAuctions(int udi) {

	return dbt.getUserAucts(udi);
}

@Override
public void addFunds(int uid, int amount) {
	try {
		dbt.addFunds(uid, amount);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}

@Override
public void bid(int aid, int uid, int amount) {
	int tbt = dbt.checkPreviousBet(uid, aid);
	dbt.remFromFunds(amount-tbt, uid);
	dbt.addBid(aid, uid, amount);
	
}

@Override
public Auction loadAuct(int aid) {
	return dbt.loadAuction(aid);
	
}

@Override
public int checkBal(int uid, int aid) {
	
	return dbt.checkPreviousBet(uid, aid); 
	
}

@Override
public void removeAuction(int aid) {
	dbt.removeAuct(aid);
	
}


 
}
