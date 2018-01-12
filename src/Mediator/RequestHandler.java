package Mediator;

import java.util.HashMap;
import java.util.Map;

import Model.Auction;
import Model.AuctionList;
import Model.User;

public class RequestHandler {

	public static Map processReq(Map map, ServerModel model) {
		Map tmp = new HashMap<>();
		switch (map.get("action").toString()) {
		case "register":
			User user = (User) map.get("user");
			tmp.put("response", "register");
			if (!model.emailValidation(user)) {
				model.addUser(user);

				tmp.put("result", "success");
			} else {

				tmp.put("result", "failed");

			}
			break;
		case "login":
			tmp.put("response", "login");
			if (model.LogIn((String) map.get("email"),
					(String) map.get("password"))) {

				User user1 = model.getUser((String) map.get("email"));
				tmp.put("result", "success");
				tmp.put("user", user1);

			} else {
				tmp.put("result", "failed");
			}
			break;
		case "browse": {
			String crit = (String) map.get("crit");
			AuctionList list = model.loadAuction(crit);

			tmp.put("response", "browsingLoad");
			tmp.put("list", list);
		}
			break;
		case "search": {
			String crit = (String) map.get("keyword");
			AuctionList list = model.searchList(crit);
			tmp.put("response", "browsingLoad");
			tmp.put("list", list);
		}
			break;
		case "addauction": {
			Auction auction = (Auction) map.get("auction");
			model.addAuction(auction);
			tmp.put("response", "auctionAdd");
			tmp.put("result", "success");
		}
			break;

		case "accountload": {
			int udi = (int) map.get("uid");
			AuctionList[] aucs = model.loadAccountAuctions(udi);
			tmp.put("response", "accountload");
			tmp.put("list", aucs);

		}
			break;

		case "addFunds": {
			int uid = (int) map.get("user");
			int amount = Integer.parseInt(map.get("amount").toString());
			String email = (String) map.get("email");
			model.addFunds(uid, amount);
			User user1 = model.getUser(email);
			tmp.put("response", "addFunds");
			tmp.put("result", "success");
			tmp.put("user", user1);
		}
			break;
		case "placebid":

			int uid = ((User) map.get("user")).getUid();
			int aid = (int) map.get("aid");
			int amount = (int) map.get("amount");

			User user2 = model.getUser(((User) map.get("user")).getEmail());
			if (user2.getFunds() >= amount - model.checkBal(uid, aid)) {
				model.bid(aid, uid, amount);
				Auction auction = model.loadAuct(aid);
				tmp.put("response", "placebid");
				tmp.put("result", "success");
				tmp.put("auction", auction);
				User user3 = model.getUser(((User) map.get("user")).getEmail());
				tmp.put("user", user3);
			} else {
				tmp.put("response", "placebid");
				tmp.put("result", "fail");
				tmp.put("reason", "notenoughfunds");
				Auction auction = model.loadAuct(aid);
				tmp.put("auction", auction);
			}

			break;
			
		case "removeAuction" : 
			int aid1 = (int) map.get("aid");
			model.removeAuction(aid1);
			int uid1 = (int) map.get("uid");
			model.loadAccountAuctions(uid1);
			
			tmp.put("response", "removeAuction");
			tmp.put("result", "success");
			tmp.put("list", model.loadAccountAuctions(uid1));
		break;
		default:
			tmp = null;
		}

		return tmp;

	}

}
