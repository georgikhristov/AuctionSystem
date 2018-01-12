package main;

import Mediator.DataBase;
import Mediator.ServerModel;
import Mediator.ServerModelManager;

import java.util.Timer;
import java.util.TimerTask;

public class ServerMain {
	public static void main(String[] args) throws ClassNotFoundException {
		DataBase db = new DataBase();
		  ServerModel model=new ServerModelManager();
		int MINUTES = 1; // The delay in minutes
		Timer timer = new Timer();
		 timer.schedule(new TimerTask() {
		    @Override
		    public void run() { 
		        db.pickWinner();
		       
		    }
		 }, 0, 1000 * 60 * MINUTES);
	
		  
	}

}
