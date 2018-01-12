package View;

import javax.swing.*;

import Controller.ClientController;
import Model.LogIn;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

public class LogInView extends Container implements Observer {

	private JTextField fieldEmail;
	private JTextField fieldPassword;
	private JButton buttonLogin;
	private JButton buttonCreate;
	
	/**
	 * Launch the application.
	 */


	/**
	 * Create the application.
	 */
	//Singleton Pattern
	
	public LogInView() {
		initialize();
		addListener();
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(0, 0, screenSize.width/2, screenSize.height/2+128);
		
		JLabel lblEmail = new JLabel("Email:\r\n");
		lblEmail.setBounds(247, 299, 225, 30);
		add(lblEmail);
		lblEmail.setHorizontalAlignment(SwingConstants.TRAILING);
		
		this.setVisible(true);
	}
	
	private static class LogInViewHolder {
		private static final LogInView INSTANCE = new LogInView();
	}

	public static LogInView getInstance() {
        return LogInViewHolder.INSTANCE;
    }
	 
	private void initialize() {
		setLayout(null);
		
		JLabel lblAccountLogIn = new JLabel("Log in to your account\r\n");
		lblAccountLogIn.setVerticalAlignment(SwingConstants.BOTTOM);
		lblAccountLogIn.setBounds(289, 214, 376, 37);
		lblAccountLogIn.setFont(new Font("Tahoma", Font.PLAIN, 37));
		add(lblAccountLogIn);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setHorizontalAlignment(SwingConstants.TRAILING);
		lblPassword.setBounds(247, 338, 225, 30);
		add(lblPassword);
		
		fieldPassword = new JTextField();
		fieldPassword.setBounds(492, 338, 225, 30);
		add(fieldPassword);
		fieldPassword.setColumns(10);
		
		fieldEmail = new JTextField();
		fieldEmail.setBounds(492, 299, 225, 30);
		add(fieldEmail);
		fieldEmail.setColumns(10);
		
		buttonLogin = new JButton("Log In");
		buttonLogin.setBounds(247, 398, 225, 30);
		add(buttonLogin);
		
		buttonCreate = new JButton("Create Account");
		buttonCreate.setBounds(492, 398, 225, 30);
		add(buttonCreate);
	}
	
	public LogIn getLogInIfo()
	{
		String email=fieldEmail.getText();
		String password=fieldPassword.getText();
		LogIn login=new LogIn(email,password);
		return login;
	}
	
	public void addListener()
	{
		buttonCreate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//controller.logIn();
				ClientController.getInstance().switchView("register");
			}
		});
		buttonLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!fieldEmail.getText().equals("") || !fieldPassword.getText().equals("")){
					LogIn login = getLogInIfo();
				ClientController.getInstance().logIn(login);} else {
					JOptionPane.showMessageDialog(null, "Missing Fields");
				}
				
			
			}
		});
	}

	@Override
	public void update(Observable o, Object arg) {
		
		if(((String) arg).equals("suc")){
		ClientController.getInstance().switchView("browsing");
		} else {
			JOptionPane.showMessageDialog(null, "Invalid email or password");
		}
	}

}
