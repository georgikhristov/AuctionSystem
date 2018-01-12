package main;

import Controller.ClientController;

public class ClientMain {
	public static void main(String args[]) {
		try {

			ClientController controller = ClientController.getInstance();
			controller.switchView("login");
		} catch (Exception e) { 
			e.printStackTrace();
		}
	}
}
