package View;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JTextField;
import javax.swing.JButton;

import Controller.ClientController;
import Model.User;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.SwingConstants;

public class RegisterView extends Container implements Observer {

	private JFrame frame;
	private JTextField fieldUsername;
	private JTextField fieldPassword;
	private JTextField fieldConfirm;
	private JTextField fieldEmail;
	private JButton buttonOk;

	public static void main(String[] args) {
		new RegisterView();
	}

	

	// Singleton Pattern

	public RegisterView() {
		// super("Register");
		try {
			initialize();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		addListener();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(0, 0, screenSize.width/2, screenSize.height/2+128);
	}

	private static class RegisterViewHolder {
		private static final RegisterView INSTANCE = new RegisterView();
	}

	public static RegisterView getInstance() {
		return RegisterViewHolder.INSTANCE;
	}



	

	public User getUser() {
		String name = fieldUsername.getText();
		String password = fieldPassword.getText();
		String email = fieldEmail.getText();
		User user = new User(name, email, password);
		return user;
	}

	private void initialize() throws ClassNotFoundException {
		setLayout(null);

		JLabel labelTitle = new JLabel("Create Acount\r\n");
		labelTitle.setHorizontalAlignment(SwingConstants.CENTER);
		labelTitle.setFont(new Font("Tahoma", Font.PLAIN, 37));
		labelTitle.setBounds(282, 167, 376, 37);
		add(labelTitle);

		JLabel labelUsername = new JLabel("Name:");
		labelUsername.setHorizontalAlignment(SwingConstants.TRAILING);
		labelUsername.setBounds(245, 266, 225, 30);
		add(labelUsername);

		JLabel labelPassword = new JLabel("Password:\r\n");
		labelPassword.setHorizontalAlignment(SwingConstants.TRAILING);
		labelPassword.setBounds(245, 307, 225, 30);
		add(labelPassword);

		JLabel labelConfirm = new JLabel("Confirm Password:\r\n");
		labelConfirm.setHorizontalAlignment(SwingConstants.TRAILING);
		labelConfirm.setBounds(245, 348, 225, 30);
		add(labelConfirm);

		JLabel labelEmail = new JLabel("Email:\r\n");
		labelEmail.setHorizontalAlignment(SwingConstants.TRAILING);
		labelEmail.setBounds(245, 389, 225, 30);
		add(labelEmail);

		fieldUsername = new JTextField();
		fieldUsername.setBounds(490, 266, 225, 30);
		add(fieldUsername);
		fieldUsername.setColumns(10);

		fieldPassword = new JTextField();
		fieldPassword.setBounds(490, 307, 225, 30);
		add(fieldPassword);
		fieldPassword.setColumns(10);

		fieldConfirm = new JTextField();
		fieldConfirm.setBounds(490, 348, 225, 30);
		add(fieldConfirm);
		fieldConfirm.setColumns(10);

		fieldEmail = new JTextField();
		fieldEmail.setBounds(490, 389, 225, 30);
		add(fieldEmail);
		fieldEmail.setColumns(10);

		buttonOk = new JButton("OK");

		buttonOk.setBounds(245, 445, 225, 30);
		add(buttonOk);

		JButton buttonCancel = new JButton("Cancel");
		buttonCancel.setBounds(490, 445, 225, 30);
		add(buttonCancel);
		buttonCancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ClientController.getInstance().switchView("login");

			}
		});

	}

	public void addListener() {
		buttonOk.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				int exit = JOptionPane.showConfirmDialog(null, "Are you sure?",
						null, JOptionPane.OK_CANCEL_OPTION,
						JOptionPane.INFORMATION_MESSAGE);
				if (exit == JOptionPane.YES_OPTION) {
					String name = fieldUsername.getText();
					String password = fieldPassword.getText();
					String confirm = fieldConfirm.getText();
					String email = fieldEmail.getText();

					if (name.isEmpty() || password.isEmpty() || email.isEmpty()) {
						JOptionPane.showMessageDialog(null,
								"Error missing field!");
					} else if (password.equals(confirm)) {
						ClientController.getInstance().register(getUser());
					}

					else {
						JOptionPane
								.showMessageDialog(null,
										"Password and confirm password must be the same!");
					}

				}
			}

		});
	}

	@Override
	public void update(Observable o, Object arg) {
		if (((String) arg).equals("fail")) {
			
		} else
			ClientController.getInstance().switchView("login");
	}
}
