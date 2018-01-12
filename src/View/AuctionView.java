package View;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.Timer;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JTextField;

import Controller.ClientController;
import Model.Auction;
import Model.User;

import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.Color;

import javax.swing.UIManager;

public class AuctionView extends Container implements Observer {
	private Auction tmp;
	private JTextField textField;
	private JTextArea description;
	private JLabel funds;
	private JLabel lblWinner;
	private JLabel lblTitle;
	private JLabel lblHighest;
	private JButton buttonOk;
	private JLabel name;
	/**
	 * Create the application.
	 */

	// Singleton Pattern

	public AuctionView() {
		initialize();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(0, 0, screenSize.width / 2, screenSize.height / 2 + 128);

		JPanel accountPanel = new JPanel();
		accountPanel.setLayout(null);
		accountPanel.setBounds(741, 11, 195, 92);
		add(accountPanel);
		 name = new JLabel("Name: "
				+ ClientController.getInstance().getUser().getName());
		name.setBounds(10, 11, 175, 30);
		accountPanel.add(name);
		funds = new JLabel("Balance: "
				+ ClientController.getInstance().getUser().getFunds());
		funds.setBounds(10, 52, 175, 30);
		accountPanel.add(funds);

		JLabel lblDkk_1 = new JLabel("(100 dkk)");
		lblDkk_1.setForeground(UIManager.getColor("Button.shadow"));
		lblDkk_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDkk_1.setBounds(894, 253, 56, 29);
		add(lblDkk_1);

		
	
		this.setVisible(true);
		tmp = null;
	
	}

	private static class AuctionViewHolder {
		private static final AuctionView INSTANCE = new AuctionView();
	}

	public static AuctionView getInstance() {
		return AuctionViewHolder.INSTANCE;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		JButton buttonBack = new JButton("Back");
		buttonBack.setBounds(20, 20, 122, 30);
		add(buttonBack);
		buttonBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ClientController.getInstance().getAuctions("all");
				ClientController.getInstance().switchView("browsing");
				
			}
		});

		lblTitle = new JLabel("Title:");
		lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblTitle.setBounds(20, 123, 711, 39);
		add(lblTitle);

		JLabel lblItem = new JLabel("Item description");
		lblItem.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblItem.setBounds(20, 179, 711, 30);
		add(lblItem);

		JLabel lblBid = new JLabel("Bid");
		lblBid.setFont(new Font("Times New Roman", Font.PLAIN, 24));
		lblBid.setBounds(741, 173, 56, 16);
		add(lblBid);

		JButton buttonPlus = new JButton("+");
		buttonPlus.setFont(new Font("Tahoma", Font.PLAIN, 15));
		buttonPlus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int a = Integer.parseInt(textField.getText());
				a = a + 100;
				textField.setText(Integer.toString(a));
			}
		});
		buttonPlus.setBounds(741, 253, 67, 30);
		add(buttonPlus);

		JLabel lblDkk = new JLabel("dkk");
		lblDkk.setVerticalAlignment(SwingConstants.BOTTOM);
		lblDkk.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblDkk.setBounds(894, 212, 56, 30);
		add(lblDkk);
		textField = new JTextField();
		textField.setBounds(741, 212, 143, 30);
		add(textField);
		textField.setColumns(10);

		JButton buttonMinus = new JButton("-");
		buttonMinus.setFont(new Font("Tahoma", Font.PLAIN, 18));
		buttonMinus.setBounds(818, 252, 66, 30);
		add(buttonMinus);
		buttonMinus.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int a = Integer.parseInt(textField.getText());
				a = a - 100;
				textField.setText(Integer.toString(a));
			}
		});
		lblWinner = new JLabel("");	
		buttonOk = new JButton("Bid");
		buttonOk.setBounds(741, 294, 143, 30);
		add(buttonOk);
		buttonOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int a = Integer.parseInt(textField.getText());
				if (a > tmp.getMinam()) {
					ClientController.getInstance().placeBid(tmp.getId(),
							ClientController.getInstance().getuid(),
							Integer.parseInt(textField.getText()));
				} else {
					JOptionPane.showMessageDialog(null,
							"Cannot bid less than the highest bid");
				}
			}
		});
		JButton buttonCancel = new JButton("Cancel");
		buttonCancel.setBounds(741, 335, 143, 30);
		add(buttonCancel);
		buttonCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ClientController.getInstance().switchView("browsing");
			}
		});

		description = new JTextArea("New label");
		description.setEditable(false);
		description.setBackground(UIManager.getColor("Button.background"));
		description.setBounds(20, 220, 711, 421);
		description.setLineWrap(true);
		description.setWrapStyleWord(true);
		add(description);
		
		lblHighest = new JLabel("Time left:");
		lblHighest.setForeground(Color.DARK_GRAY);
		lblHighest.setVerticalAlignment(SwingConstants.BOTTOM);
		lblHighest.setFont(new Font("Times New Roman", Font.BOLD, 19));
		lblHighest.setBounds(20, 73, 238, 39);
		add(lblHighest);
		Timer t = new Timer(1000, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTime();
            }
           
        });
		
        t.start();
	}
	public void updateTime(){
		lblHighest.setText("Time left: " + tmp.getTimeLeft(tmp.getTimelast()));
		if(tmp.getTimeLeft(tmp.getTimelast()).equals("Expired")){
			buttonOk.setEnabled(false);
		}
		
	}
	
	public void updateBalance(){
		funds.setText("Balance: "
				+ ClientController.getInstance().getUser().getFunds());
		
	}
	
	public void updateName(){
		name.setText("Name: " + ClientController.getInstance().getUser().getName());
	}
	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof Auction && arg != null) {
			tmp = (Auction) arg;
			lblTitle.setText(tmp.getTitle());
			description.setText(tmp.getDescription());
			textField.setText(Integer.toString(tmp.getMinam()));
		} else if (arg instanceof User) {
			funds.setText("Balance: "
					+ ClientController.getInstance().getUser().getFunds());
		} else if (arg instanceof String && ((String) arg).equals("fail")) {
			JOptionPane.showMessageDialog(null, "Not Enough Funds");
		}
	
		if (tmp.getHighest() == ClientController.getInstance().getuid()) {
			
			lblWinner.setForeground(new Color(154, 205, 50));
			lblWinner.setVerticalAlignment(SwingConstants.BOTTOM);
			lblWinner.setFont(new Font("Times New Roman", Font.BOLD, 17));
			lblWinner.setBounds(741, 123, 209, 39);
			lblWinner.setText("You are the highest bidder");
			add(lblWinner);
		} else {
			
			lblWinner.setForeground(new Color(250, 0, 0));
			lblWinner.setVerticalAlignment(SwingConstants.BOTTOM);
			lblWinner.setFont(new Font("Times New Roman", Font.BOLD, 17));
			lblWinner.setBounds(741, 123, 209, 39);
			lblWinner.setText("You are not the highest bidder");
			add(lblWinner);
			updateTime();
		}
	}
}
