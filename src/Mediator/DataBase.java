package Mediator;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import Model.Auction;
import Model.AuctionList;
import Model.User;
import utility.persistence.MyDatabase;

public class DataBase {
	private MyDatabase db;
	private static final String DRIVER = "org.postgresql.Driver";
	private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
	private static final String USER = "postgres";
	private static final String PASSWORD = "admin";

	public DataBase() throws ClassNotFoundException {
		this.db = new MyDatabase(DRIVER, URL, USER, PASSWORD);
	}

	public AuctionList loadAllAuctions() throws IOException,
			NullPointerException {
		String sql = "SELECT * FROM auction.auctions a,auction.bids b where timelast>now() and a.minamount=b.amount and a.aid=b.aid order by timelast desc;";
		ArrayList<Object[]> results;
		AuctionList aucs = new AuctionList();
	
		int id = -1;
		String title = "?";
		String description = "?";
		int creatorid = 0;
		String category = "?";
		Timestamp last = null;
		int minam = 0;
		try {
			results = db.query(sql);
			for (int i = 0; i < results.size(); i++) {
				Object[] row = results.get(i);
				id = Integer.parseInt(row[0].toString());
				title = row[1].toString();
				description = row[2].toString();
				category = row[3].toString();
				minam = Integer.parseInt(row[5].toString());
				creatorid = Integer.parseInt(row[6].toString());
				last = (Timestamp) row[7];
				Auction auct = new Auction(id, minam, last, creatorid, title,
						description, category);
				auct.setHighest(Integer.parseInt(row[9].toString()));
				aucs.addElem(auct);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return aucs;
	}

	public AuctionList loadWithCat(String cat) throws IOException {
		String sql = "SELECT * FROM auction.auctions a,auction.bids b where timelast>now() and a.minamount=b.amount and a.aid=b.aid and category=? order by timelast desc;";
		ArrayList<Object[]> results;
		AuctionList aucs = new AuctionList();
		
		int id = -1;
		String title = "?";
		String description = "?";
		int creatorid = 0;
		String category = "?";
		Timestamp last = null;
		int minam = 0;
		try {
			results = db.query(sql, cat);
			for (int i = 0; i < results.size(); i++) {
				Object[] row = results.get(i);
				id = Integer.parseInt(row[0].toString());
				title = row[1].toString();
				description = row[2].toString();
				category = row[3].toString();
				minam = Integer.parseInt(row[5].toString());
				creatorid = Integer.parseInt(row[6].toString());
				last = (Timestamp) row[7];
				Auction auct = new Auction(id, minam, last, creatorid, title,
						description, category);
				auct.setHighest(Integer.parseInt(row[9].toString()));
				aucs.addElem(auct);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return aucs;
	}

	public AuctionList loadWithSearch(String criteria) throws IOException {
		String sql = "SELECT * FROM auction.auctions a,auction.bids b where timelast>now() and a.minamount=b.amount and lower(title) like '%"
				+ criteria.toLowerCase()
				+ "%' order by timelast desc;";
		ArrayList<Object[]> results;
		AuctionList aucs = new AuctionList();
		
		int id = -1;
		String title = "?";
		String description = "?";
		int creatorid = 0;
		String category = "?";
		Timestamp last = null;
		int minam = 0;
		try {
			results = db.query(sql);
			for (int i = 0; i < results.size(); i++) {
				Object[] row = results.get(i);
				id = Integer.parseInt(row[0].toString());
				title = row[1].toString();
				description = row[2].toString();
				category = row[3].toString();
				minam = Integer.parseInt(row[5].toString());
				creatorid = Integer.parseInt(row[6].toString());
				last = (Timestamp) row[7];
				Auction auct = new Auction(id, minam, last, creatorid, title,
						description, category);
				auct.setHighest(Integer.parseInt(row[9].toString()));
				aucs.addElem(auct);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return aucs;
	}

	public Auction loadAuction(int aid) {
		String sql = "SELECT * FROM auction.auctions a,auction.bids b where timelast>now() and a.minamount=b.amount and a.aid = ? order by timelast desc;";
		ArrayList<Object[]> results;
		
		
		Auction auct=null;
		int id = -1;
		String title = "?";
		String description = "?";
		int creatorid = 0;
		String category = "?";
		Timestamp last = null;
		int minam = 0;
		try {
			results = db.query(sql,aid);
			if(results.size()==1)
			 {
				Object[] row = results.get(0);
				id = Integer.parseInt(row[0].toString());
				title = row[1].toString();
				description = row[2].toString();
				category = row[3].toString();
				minam = Integer.parseInt(row[5].toString());
				creatorid = Integer.parseInt(row[6].toString());
				last = (Timestamp) row[7];
				auct = new Auction(id, minam, last, creatorid, title,
						description, category);
				auct.setHighest(Integer.parseInt(row[9].toString()));
			} else auct = null;
	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return auct;
	
	}

	public AuctionList[] getUserAucts(int uid) {
		AuctionList[] tmp = new AuctionList[3];
		String sql1 = "SELECT * FROM auction.auctions a where a.creatorid=? order by a.timelast desc;";
		String sql2 = "Select distinct * from auction.auctions a, auction.bids b where b.aid=a.aid and b.uid = ? ;";
		String sql3 = "select * from auction.auctions a,auction.winner w , auction.users u where a.aid=w.aid and w.uid = ? and a.creatorid = u.uid;";
		ArrayList<Object[]> results;
		AuctionList aucs = new AuctionList();
		AuctionList aucs1 = new AuctionList();
		AuctionList aucs2 = new AuctionList();
		int id = -1;
		String title = "?";
		String description = "?";
		int creatorid = 0;
		String category = "?";
		Timestamp last = null;
		int minam = 0;
		try {
			results = db.query(sql1, uid);
			for (int i = 0; i < results.size(); i++) {
				Object[] row = results.get(i);
				id = Integer.parseInt(row[0].toString());
				title = row[1].toString();
				description = row[2].toString();
				category = row[3].toString();
				minam = Integer.parseInt(row[5].toString());
				creatorid = Integer.parseInt(row[6].toString());
				last = (Timestamp) row[7];
				Auction auct = new Auction(id, minam, last, creatorid, title,
						description, category);

				aucs.addElem(auct);
	
			}
	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		tmp[0] = aucs;
	
		try {
			results = db.query(sql2, uid);
			for (int i = 0; i < results.size(); i++) {
				Object[] row = results.get(i);
				id = Integer.parseInt(row[0].toString());
				title = row[1].toString();
				description = row[2].toString();
				category = row[3].toString();
				minam = Integer.parseInt(row[5].toString());
				creatorid = Integer.parseInt(row[6].toString());
				last = (Timestamp) row[7];
				Auction auct = new Auction(id, minam, last, creatorid, title,
						description, category);
				aucs1.addElem(auct);
			}
			tmp[1] = aucs1;
	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			results = db.query(sql3, uid);
			for (int i = 0; i < results.size(); i++) {
				Object[] row = results.get(i);
				id = Integer.parseInt(row[0].toString());
				title = row[1].toString();
				description = row[2].toString();
				category = row[3].toString();
				minam = Integer.parseInt(row[5].toString());
				creatorid = Integer.parseInt(row[6].toString());
				last = (Timestamp) row[7];
				Auction auct = new Auction(id, minam, last, creatorid, title,
						description, category);
				auct.setCreatorEmail(row[14].toString());
				aucs2.addElem(auct);
			}
			tmp[2] = aucs2;
	
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
		return tmp;
	}

	public void removeAuct(int id) {
		String sql = "DELETE FROM auction.auctions where aid=?;";
		try {
			db.update(sql, id);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void addAuction(Auction obj) throws SQLException {
		int creatorId = obj.getCreatorid();
		String title = obj.getTitle();
		String description = obj.getDescription();
		String cat = obj.getCategory();
		int minam = obj.getMinam();
		int timelast = obj.getTimeset();

		String sql = "INSERT INTO auction.auctions (title,description,creatorid,dateadded,category,minamount,timelast) values(?,?,?,NOW(),?,?,NOW() + interval '"
				+ timelast + " hour');";
		String sql1 = "insert into auction.bids (aid,uid,amount) values (?,?,?)";
		db.update(sql, title, description, creatorId, cat, minam);
		String sql2 = "select aid from auction.auctions where creatorid=? and description =? and title=? and minamount=? ";
		ArrayList<Object[]> result = db.query(sql2, creatorId,description,title,minam);
		if(result.size()==1){
			Object[] row = result.get(0);
			int aid= Integer.parseInt(row[0].toString());
		db.update(sql1,aid,creatorId,minam);}
		
	}

	public void addUser(User obj) throws SQLException {
		String name = obj.getName();
		String password = obj.getPassword();
		String email = obj.getEmail();
		String sql = "INSERT INTO auction.users (name,email,password,regdate,funds) values(?,?,?,NOW(),0);";
		db.update(sql, name, email, password);
	}

	public void addFunds(int uid, int amount) throws SQLException {
		String sql = "UPDATE auction.users SET funds=funds+? WHERE uid=?;";
		db.update(sql, amount, uid);
	}

	public void addBid(int aid, int uid, int amount) {
		String sql = "Insert into auction.bids (uid,aid,amount) values(?,?,?);";
		String sql1 = "update auction.auctions set minamount=? where aid= ? and minamount < ?;"; 
		String sql2 = "delete from auction.bids where aid = ? and uid = ? and amount < ? ;";
		
		
		try {
			db.update(sql2, aid, uid,amount);
			
			db.update(sql, uid, aid, amount);
			db.update(sql1, amount, aid,amount);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean login(String email, String password) {
		String sql = "SELECT email,password FROM auction.users where email=? ;";
		String dbemail = "";
		String dbpassword = "";
		ArrayList<Object[]> results;
		try {
			results = db.query(sql, email);
			for (int i = 0; i < results.size(); i++) {
				Object[] row = results.get(i);
				dbpassword = row[1].toString();
				dbemail = row[0].toString();

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (dbemail.equals(email) && dbpassword.equals(password)) {
			return true;
		} else
			return false;
	}

	public User loadUser(String email) {
		String sql = "SELECT * FROM auction.users where email=?;";
		String name = "";
		String semail = "";
		int uid = 0;
		int funds = 0;
		User tmp = null;
		ArrayList<Object[]> results;
		try {
			results = db.query(sql, email);
			if (results.size() == 1) {
				Object[] row = results.get(0);
				uid = Integer.parseInt(row[0].toString());
				name = row[1].toString();
				email = row[3].toString();
				funds = Integer.parseInt(row[4].toString());
				tmp = new User(uid, name, email, funds);

			} else
				return null;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return tmp;
	}

	public boolean emailValidation(String email) throws SQLException {
		String sql = "SELECT email from auction.users where email=?;";
		ArrayList<Object[]> result = db.query(sql, email);
		if (result.size() == 1) {
			return true;
		} else
			return false;
	}
	
	
	public void pickWinner() {
		String sql = "SELECT aid,minamount from auction.auctions where timelast<now() and winner_picked = false;";
		String sql2 = "INSERT into auction.winner (aid, uid) VALUES(?, (select uid from auction.bids where auction.bids.amount = ? and auction.bids.aid = ?));";
		String sql3 = "UPDATE auction.auctions set winner_picked = true where aid = ? ";
		String sql4 = "SELECT b.uid,w.aid,b.amount from auction.auctions a,auction.bids b, auction.winner w  where b.aid=w.aid and b.uid!=w.uid and a.winner_picked=true and w.aid = a.aid;";
		String sql5 = "delete from auction.bids where aid=?";
		ArrayList<Object[]> results;
		int id = -1;
		int minam = 0;
		try {
			results = db.query(sql);
			for (int i = 0; i < results.size(); i++) {
				Object[] row = results.get(i);
				id = Integer.parseInt(row[0].toString());
				minam = Integer.parseInt(row[1].toString());
				db.update(sql2,id,minam,id);	
				db.update(sql3, id);
				
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			ArrayList<Object[]>results1=db.query(sql4);
			for (int i=0;i<results1.size();i++){
				Object[] row = results1.get(i);
				addFunds(Integer.parseInt(row[0].toString()), Integer.parseInt(row[2].toString()));
				db.update(sql5,Integer.parseInt(row[1].toString()));
			}
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		
		
	}
	
	
	public int checkPreviousBet(int uid,int aid){
		String sql = "SELECT amount from auction.bids where uid = ? and aid = ?  ";
		int amount = 0;
		ArrayList<Object[]> result = null;
		try {
			result = db.query(sql, uid,aid);
		} catch (SQLException e) {
	
			e.printStackTrace();
		}
		if (result.size() == 1 && result!=null) {
			Object[] row = result.get(0);
			amount = Integer.parseInt(row[0].toString());
		} else {
			amount = 0;
		}
			
		return amount;

	}
	
	public void remFromFunds(int amount,int uid){
		String sql = "update auction.users set funds=funds-? where uid=?";
		try {
			db.update(sql, amount,uid);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
}
