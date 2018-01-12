package View;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;

import Controller.ClientController;
import Model.Auction;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.SwingConstants;

import java.awt.Font;


public class CreateAuctionView extends Container implements Observer {
	
	private JTextField fieldTitle;
	private JLabel lblCategory;
	private JLabel lblStartingPrice;
	private JTextField fieldPrice;
	private JLabel lblDkk;
	private JTextPane textPane;
	private JLabel lblItemDescription;
	private JScrollPane descriptionPane;
	private JButton buttonOk;
	private JButton buttonCancel;
	private JComboBox comboCategory;
	private JComboBox comboDuration;
	private JLabel funds ;
	private JLabel name;
	//Singleton Pattern
	
	public CreateAuctionView() {
		initialize();
		
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(0, 0, screenSize.width/2, screenSize.height/2+128);
		
		this.setVisible(true);
	}
	
	private static class CreateAuctionViewHolder {
		private static final CreateAuctionView INSTANCE = new CreateAuctionView();
	}

	public static CreateAuctionView getInstance() {
        return CreateAuctionViewHolder.INSTANCE;
    }
	
	private void initialize() {
		fieldTitle = new JTextField();
		fieldTitle.setBounds(20, 146, 630, 30);
		add(fieldTitle);
		fieldTitle.setColumns(10);
		String[] params = {"Car","Electronics","Books","Clothing","Other"};
		comboCategory = new JComboBox(params);
		comboCategory.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboCategory.setBounds(20, 222, 116, 30);
		add(comboCategory);
		
		JLabel lblTitle = new JLabel("Title");
		lblTitle.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTitle.setBounds(20, 105, 40, 30);
		add(lblTitle);
		
		lblCategory = new JLabel("Category");
		lblCategory.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCategory.setBounds(20, 181, 97, 30);
		add(lblCategory);
		
		lblStartingPrice = new JLabel("Starting price");
		lblStartingPrice.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblStartingPrice.setBounds(20, 262, 116, 30);
		add(lblStartingPrice);
		
		fieldPrice = new JTextField();
		fieldPrice.setBounds(20, 297, 59, 30);
		add(fieldPrice);
		fieldPrice.setColumns(10);
		
		lblDkk = new JLabel("dkk");
		lblDkk.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDkk.setVerticalAlignment(SwingConstants.BOTTOM);
		lblDkk.setBounds(90, 297, 46, 30);
		add(lblDkk);
		
		descriptionPane = new JScrollPane();
		descriptionPane.setBounds(20, 374, 630, 198);
		add(descriptionPane);
		
		textPane = new JTextPane();
		descriptionPane.setViewportView(textPane);
		
		lblItemDescription = new JLabel("Item description");
		lblItemDescription.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblItemDescription.setBounds(20, 333, 116, 30);
		add(lblItemDescription);
		

		JButton buttonBack = new JButton("Back");
		buttonBack.setBounds(20, 20, 122, 30);
		add(buttonBack);
		
		
		JPanel accountPanel = new JPanel();
		accountPanel.setBounds(741, 11, 195, 92);
		add(accountPanel);
		accountPanel.setLayout(null);
		
			name = new JLabel("Name: " + ClientController.getInstance().getUser().getName());
			name.setBounds(10, 11, 175, 30);
			accountPanel.add(name);
			funds = new JLabel("Balance: " + ClientController.getInstance().getUser().getFunds());
			funds.setBounds(10, 52, 175, 30);
			accountPanel.add(funds);
		
		buttonOk = new JButton("OK");
		buttonOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String description = textPane.getText();
				String title = fieldTitle.getText();
				
				int catselect= comboCategory.getSelectedIndex();
				int timeselect = comboDuration.getSelectedIndex();
				String category = null;
				int time = 0;
				switch (catselect){
				case 0: category="car";
				break;
				case 1: category="elec";
				break;
				case 2: category="book";
				break;
				case 3: category="cloth";
				break;
				case 4: category = "other";
				break;
				}
				switch (timeselect){
				case 0: time=8;
				break;
				case 1: time = 12;
				break;
				case 2: time = 24;
				break;
				case 3: time = 48;
				break;
				case 4: time = 72;
				break;
				}
				if(fieldTitle.getText().isEmpty()||fieldPrice.getText().isEmpty()||textPane.getText().isEmpty())
				{
					JOptionPane
					.showMessageDialog(null,
							"Error: missing field!");
				}
				else
				{
					try
					{
					int startingPrice = Integer.parseInt(fieldPrice.getText());
					Auction tmp = new Auction(startingPrice, time, ClientController.getInstance().getuid(), title, description, category);
					ClientController.getInstance().createAuction(tmp);
						
					}
					catch(Exception e1)
					{
						JOptionPane.showMessageDialog(null, "Starting Price can not be letters");
					}
			
			}
			}
		});
		buttonOk.setBounds(20, 591, 122, 30);
		add(buttonOk);
		
		buttonCancel = new JButton("Cancel");
		buttonCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ClientController.getInstance().getAuctions("all");
				ClientController.getInstance().switchView("browsing");
			}
		});
		buttonBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ClientController.getInstance().switchView("browsing");
			}
		});
		buttonCancel.setBounds(528, 591, 122, 30);
		add(buttonCancel);
		String[] params1 = {"8Hours","12Hours","24Hours","48Hours","72Hours"};
		comboDuration = new JComboBox(params1);
		comboDuration.setFont(new Font("Tahoma", Font.PLAIN, 14));
		comboDuration.setBounds(264, 295, 74, 30);
		add(comboDuration);
		
		JLabel lblDuration = new JLabel("Duration");
		lblDuration.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDuration.setBounds(264, 262, 116, 30);
		add(lblDuration);
		
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
		ClientController.getInstance().getAuctions("all");
		ClientController.getInstance().switchView("browsing");
		
	}
}