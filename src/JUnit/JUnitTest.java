package JUnit;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import utility.persistence.MyDatabase;
import Controller.ClientController;
import Mediator.ServerModel;
import Mediator.ServerModelManager;
import Model.Auction;
import Model.AuctionList;
import Model.LogIn;
import Model.User;
import View.*;

import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JUnitTest {

	private ClientController controller;
	private ServerModel model;
	private MyDatabase db;
	private static final String DRIVER = "org.postgresql.Driver";
	private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
	private static final String USER = "postgres";
	private static final String PASSWORD = "admin";
	private boolean a=false;
	
	//Before running the JUnit test make sure all the tables in the schema are empty;
	//Seting up the client and the server
	@Before
	public void setUp() throws Exception {
		if(!a){
		db = new MyDatabase(DRIVER, URL, USER, PASSWORD);		
		model = new ServerModelManager();
		controller = ClientController.getInstance();
		View.getInstance().setVisible(false);
		a=true;
		}
	}

	//Testing account creation.
	@Test
	public void testAcreateaccount(){
		controller.register(new User("testname","testemail","testpassword"));
		String sql = "select * from auction.users a where email='testemail';";
		ArrayList<Object[]> results;
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			results = db.query(sql);
			assertEquals(1, results.size());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//Testing login with the previously created user
	@Test
	public void testBLogIn() throws InterruptedException {
		controller.logIn(new LogIn("testemail", "testpassword"));
		Thread.sleep(2000);
		assertEquals("testemail", controller.getUser().getEmail());
		assertEquals("testname", controller.getUser().getName());
	}
	
	//Testing Auction creation.
	@Test
	public void testCCreateAuction() {
		controller.logIn(new LogIn("admin", "admin"));
		Auction tmp = new Auction(30, 1, ClientController.getInstance()
				.getuid(), "Honda Jazz", "Good car", "Car");
		ClientController.getInstance().createAuction(tmp);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String sql = "select * from auction.auctions a where a.title='Honda Jazz' and a.description='Good car' and a.category = 'Car';";
		ArrayList<Object[]> results;

		try {
			results = db.query(sql);
			assertEquals(1, results.size());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//Testing funds adding to the account we had previously created
	@Test
	public void testDAddFunds(){
		String sql = "select uid from auction.users a where email='testemail';";
		ArrayList<Object[]> results = null;
		try {
			results = db.query(sql);
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		int uid = Integer.parseInt(results.get(0)[0].toString());
		model.addFunds(uid, 100);
		String sql1 = "select funds from auction.users a where email='testemail';";
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			results = db.query(sql1);
			assertEquals(100,  Integer.parseInt(results.get(0)[0].toString()));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//Testing browsing the auctions by loading the auctions
	@Test
	public void testEBrowse(){
		String sql = "select * from auction.auctions a ;";
			ArrayList<Object[]> results = null;
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			results = db.query(sql);
			assertEquals(1,  results.size());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//Testing Bidding by adding placing a bid an getting the number of bids.
	@Test
	public void testFbid(){
		controller.placeBid(1, 1, 100);
		String sql = "select * from auction.bids";
		ArrayList<Object[]> results = null;
		try {
			results=db.query(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertEquals(1,results.size());
	}
	
	
	//Testing search 
	@Test
	public void testGSearch(){
		
		AuctionList a = model.searchList("honda");
		assertEquals(1,a.getSize());	
	}
	
	
	//Testing the Won auctions and Placed bids
	@Test
	public void testHWonandPlaced() throws InterruptedException{
		
		
		String sql = "INSERT INTO auction.winner (aid,uid) values (1,1)";
		try {
			db.update(sql);
			Thread.sleep(1000);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AuctionList[] a = model.loadAccountAuctions(1);
		assertEquals(1,a[1].getSize());
		assertEquals(1,a[2].getSize());
	}
	
	//Testing the auction removing.
	@Test
	public void testIRemoveAuction(){
		String sql = "select aid from auction.auctions a;";
		String sql1 = "select * from auction.auctions a;";
			ArrayList<Object[]> results = null;
			try {
				results=db.query(sql);
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			int aid = Integer.parseInt(results.get(0)[0].toString());
		controller.removeAuction(aid);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			results = db.query(sql1);
			assertEquals(0,  results.size());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	

}
