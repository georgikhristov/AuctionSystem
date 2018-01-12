package View;

import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Toolkit;
import java.sql.Timestamp;
import java.util.Observable;
import java.util.Observer;

import javax.swing.SwingConstants;

import Controller.ClientController;
import Model.Auction;
import Model.AuctionList;
import Model.User;

public class BrowsingView extends Container implements Observer {
	private GridBagLayout gbl_auction_list;
	private JTextField searchField;
	private AuctionList list;
	private ClientController controller;
	private JButton buttonAll;
	private JPanel auction_list;
	private GridBagConstraints gbc_auction_box;
	private JPanel auction_box;
	private JLabel funds;
	private JLabel name;



	public BrowsingView() {
		initialize();
	
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(0, 0, screenSize.width/2, screenSize.height/2+128);
		
		this.setVisible(true);
	}

	private static class BrowsingViewHolder {
		private static final BrowsingView INSTANCE = new BrowsingView();
	}

	public static BrowsingView getInstance() {
		return BrowsingViewHolder.INSTANCE;
	}

	private void initialize() {
		controller = ClientController.getInstance();
		searchField = new JTextField();
		searchField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.getSearchAuctions(searchField.getText());

			}
		});
		searchField.setBounds(160, 20, 434, 30);
		add(searchField);
		searchField.setColumns(10);

		JButton buttonBack = new JButton("Log out");
		buttonBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.switchView("login");
			}
		});
		buttonBack.setBounds(20, 20, 122, 30);
		add(buttonBack);

		JButton buttonCreate = new JButton("Create");
		buttonCreate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.switchView("create");
				
			}
		});
		buttonCreate.setBounds(609, 20, 122, 30);
		add(buttonCreate);
		JLabel lblCategories = new JLabel("Categories");
		lblCategories.setHorizontalAlignment(SwingConstants.CENTER);
		lblCategories.setBounds(49, 107, 65, 25);
		add(lblCategories);

		auction_list = new JPanel();

		buttonAll = new JButton("All Categories");
		buttonAll.setBounds(20, 143, 122, 36);
		buttonAll.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				controller.getAuctions("all");

			}
		});
		add(buttonAll);

		JButton buttonCars = new JButton("Cars");
		buttonCars.setBounds(20, 192, 122, 36);
		buttonCars.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				controller.getAuctions("car");

			}
		});
		add(buttonCars);

		JButton buttonElectronics = new JButton("Electronics");
		buttonElectronics.setBounds(20, 241, 122, 36);
		buttonElectronics.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				controller.getAuctions("elec");

			}
		});
		add(buttonElectronics);

		JButton buttonBooks = new JButton("Books");
		buttonBooks.setBounds(20, 290, 122, 36);
		buttonBooks.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				controller.getAuctions("book");

			}
		});
		add(buttonBooks);

		JScrollPane auctionList = new JScrollPane();
		auctionList.setBounds(160, 145, 778, 501);
		add(auctionList);

		auctionList.setViewportView(auction_list);

		JPanel accountPanel = new JPanel();
		accountPanel.setBounds(741, 11, 195, 92);
		add(accountPanel);
		accountPanel.setLayout(null);
		
		name = new JLabel("Name: " + controller.getUser().getName());
		name.setBounds(10, 11, 175, 30);
		Font font = name.getFont();
		// same font but bold
		Font boldFont = new Font(font.getFontName(), Font.BOLD, font.getSize());
		name.setFont(boldFont);
		accountPanel.add(name);
		
		 funds = new JLabel("Balance: " + controller.getUser().getFunds());
		funds.setBounds(10, 52, 175, 30);
		accountPanel.add(funds);
		
		name.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ClientController.getInstance().switchView("account");
			}
		});

		JButton buttonClothing = new JButton("Clothing");
		buttonClothing.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				controller.getAuctions("cloth");
			}
		});
		buttonClothing.setBounds(20, 337, 122, 36);
		add(buttonClothing);

		JButton buttonOther = new JButton("Other");
		buttonOther.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.getAuctions("other");
			}
		});
		buttonOther.setBounds(20, 384, 122, 36);
		add(buttonOther);

	}

	public void populate() {
		auction_list.removeAll();
		gbl_auction_list = new GridBagLayout();
		auction_list.setLayout(gbl_auction_list);
		int size = list.getSize();
		gbl_auction_list.columnWidths = new int[] { 770, 0 };
		gbl_auction_list.rowHeights = new int[size];
		gbl_auction_list.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_auction_list.rowWeights = new double[] { 0.0, 0.0 };

		for (int i = 0; i < size; i++) {

			Auction tmp = list.getElem(i);
			
			auction_box = new JPanel();
			auction_box.setBackground(UIManager.getColor("InternalFrame.activeBorderColor"));
			gbc_auction_box = new GridBagConstraints();
			gbc_auction_box.fill = GridBagConstraints.HORIZONTAL;
			gbc_auction_box.insets = new Insets(10, 10, 10, 10);
			gbc_auction_box.gridx = 0;
			gbc_auction_box.gridy = i;
			auction_list.add(auction_box, gbc_auction_box);
			GridBagLayout gbl_auction_box = new GridBagLayout();
			gbl_auction_box.columnWidths = new int[] { 0, 0, 20, 0 };
			gbl_auction_box.rowHeights = new int[] { 0, 0, 14, 0 };
			gbl_auction_box.columnWeights = new double[] { 0.0, 0.0, 0.0,
					Double.MIN_VALUE };
			gbl_auction_box.rowWeights = new double[] { 0.0, 0.0, 0.0,
					Double.MIN_VALUE };
			auction_box.setLayout(gbl_auction_box);

			JLabel lblTitle = new JLabel("Title: " + tmp.getTitle());
			GridBagConstraints gbc_lblTitle = new GridBagConstraints();
			gbc_lblTitle.insets = new Insets(0, 0, 5, 5);
			gbc_lblTitle.anchor = GridBagConstraints.NORTHWEST;
			gbc_lblTitle.gridx = 0;
			gbc_lblTitle.gridy = 0;
			auction_box.add(lblTitle, gbc_lblTitle);
			JLabel lblDescription ;
			if (tmp.getDescription().length()>30){
			 lblDescription = new JLabel("Description: "
					+ tmp.getDescription().substring(0, 30) + "...");
			} else {
			 lblDescription = new JLabel("Description: "
								+ tmp.getDescription());}
			GridBagConstraints gbc_lblDescription = new GridBagConstraints();
			gbc_lblDescription.insets = new Insets(0, 0, 5, 5);
			gbc_lblDescription.gridx = 0;
			gbc_lblDescription.gridy = 1;
			auction_box.add(lblDescription, gbc_lblDescription);
			Timestamp ts = tmp.getTimelast();
			JLabel lblTimeLeft = new JLabel("Time left: " + tmp.getTimeLeft(ts));
			GridBagConstraints gbc_lblTimeLeft = new GridBagConstraints();
			gbc_lblTimeLeft.insets = new Insets(0, 0, 0, 5);
			gbc_lblTimeLeft.gridx = 0;
			gbc_lblTimeLeft.gridy = 2;
			auction_box.add(lblTimeLeft, gbc_lblTimeLeft);

			JLabel lblTopBid = new JLabel("Top Bid: " + tmp.getMinam());
			GridBagConstraints gbc_lblTopBid = new GridBagConstraints();
			gbc_lblTopBid.gridx = 2;
			gbc_lblTopBid.gridy = 2;
			auction_box.add(lblTopBid, gbc_lblTopBid);

			revalidate();
			auction_box.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() == 2) {
					    
					   
					    ClientController.getInstance().switchView("auction");
					    ClientController.getInstance().auct(tmp);
					  }
				}
			});
		}
		auction_list.repaint();
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
		if( arg instanceof AuctionList){
		list = (AuctionList) arg;

		
		populate();} else if (arg instanceof User) {
			funds.setText("Balance: "
					+ ClientController.getInstance().getUser().getFunds());
		}
	}
}