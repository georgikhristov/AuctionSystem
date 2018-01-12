package Mediator;

import java.util.Map;

import Model.*;
import Controller.ClientController;

public class ResponseHandler {
	
	
	
	public static void processResponse(Map map, ClientModel model){
	switch(map.get("response").toString()){
	case  "register":
		if(map.get("result").toString().equals("success"))
		{	
			model.notifyAboutChange("suc");
		}
		else
		{	
			
			model.notifyAboutChange("fail");
		}
		break;
	case "login":
		if(map.get("result").toString().equals("success"))
		{
			ClientController.getInstance().setUser((User)map.get("user"));
			model.notifyAboutChange("suc");
		}
		else
		{
			model.notifyAboutChange("fail");
		}
		break;
	case "browsingLoad" :
		model.notifyAboutChange((AuctionList) map.get("list"));
		break;
	case "auctionAdd" :
		model.notifyAboutChange("done");
		break;
		
	case "accountload" : 
		AuctionList[] list = (AuctionList[]) map.get("list");
		model.notifyAboutChange(list);
		break;
	case "addFunds" : 
		User user = (User) map.get("user");
		ClientController.getInstance().setUser(user);
		model.notifyAboutChange(user);
		break;
	case "placebid" : 
		if (map.get("result").toString().equals("success")){
		Auction tmp = (Auction) map.get("auction");
		User user1 = (User) map.get("user");
		ClientController.getInstance().setUser(user1);
		ClientController.getInstance().auct(tmp);
		model.notifyAboutChange(user1);}
		else {		
			
			model.notifyAboutChange("fail");
		}
		break;
	case "removeAuction" :

		AuctionList[] list1 = (AuctionList[]) map.get("list");
		model.notifyAboutChange(list1);
		break;
		}
	}
}
