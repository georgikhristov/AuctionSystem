package View;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Timestamp;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import Model.Auction;
import Model.AuctionList;
import Model.User;
import Controller.ClientController;

public class AccountView extends Container implements Observer {

	private JFrame frame;
	private JPanel panel_1;
	private JPanel panel_2;
	private JPanel panel_3;
	private JLabel lblNewLabel;
	private JPanel auction_box;
	private GridBagLayout gbl_panel_1;
	private GridBagLayout gbl_panel_2;
	private GridBagLayout gbl_panel_3;
	private GridBagConstraints gbc_auction_box;
	private ClientController controller;
	private JLabel lblNewLabel_1;
	// Singleton Pattern

	public AccountView() {
		initialize();
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds(0, 0, screenSize.width/2, screenSize.height/2+128);
		
		this.setVisible(true);
	}

	private static class AccountViewHolder {
		private static final AccountView INSTANCE = new AccountView();
	}

	public static AccountView getInstance() {
		ClientController.getInstance().getAccountAuctions();
		return AccountViewHolder.INSTANCE;
	
	}

	private void initialize() {
		JTabbedPane tabbedPane = new JTabbedPane(SwingConstants.TOP);
		tabbedPane.setBounds(20, 100, 916, 546);
		add(tabbedPane);

		panel_1 = new JPanel();
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 74, 648, 348);

		tabbedPane.addTab("My Auctions", null, scrollPane_1, null);
		scrollPane_1.setViewportView(panel_1);
		panel_1.setLayout(null);

		panel_2 = new JPanel();
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_1.setBounds(10, 74, 648, 348);

		tabbedPane.addTab("My Bids", null, scrollPane_2, null);
		scrollPane_2.setViewportView(panel_2);

		panel_2.setLayout(null);

		panel_3 = new JPanel();
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_1.setBounds(10, 74, 648, 348);

		tabbedPane.addTab("Won Auctions", null, scrollPane_3, null);
		scrollPane_3.setViewportView(panel_3);

		JPanel panel_5 = new JPanel();
		panel_5.setBounds(741, 11, 195, 92);
		add(panel_5);
		panel_5.setLayout(null);

		lblNewLabel_1 = new JLabel("Name: "
				+ ClientController.getInstance().getUser().getName());
		lblNewLabel_1.setBounds(10, 11, 175, 30);
		panel_5.add(lblNewLabel_1);

		lblNewLabel = new JLabel("Balance: "
				+ ClientController.getInstance().getUser().getFunds());
		lblNewLabel.setBounds(10, 52, 175, 30);
		Font font = lblNewLabel.getFont();
		// same font but bold
		Font boldFont = new Font(font.getFontName(), Font.BOLD, font.getSize());
		lblNewLabel.setFont(boldFont);
		panel_5.add(lblNewLabel);
		lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String ammount = JOptionPane.showInputDialog(getParent(),
						"Select the amount to add:", null);
				if(ammount!=null)
				{
					try{
					ClientController.getInstance().addFunds(
							Integer.parseInt(ammount));
					}
					catch(Exception e1)
					{
						JOptionPane
						.showMessageDialog(null,
								"Amount can not be letters or empty ");
					}
				}
				
			}
		});
		JButton btnBack = new JButton("Back");
		btnBack.setBounds(20, 20, 122, 29);
		add(btnBack);
		btnBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ClientController.getInstance().getAuctions("all");
				ClientController.getInstance().switchView("browsing");
				
			}
		});

		JButton btnCreate = new JButton("Create");
		btnCreate.setBounds(653, 20, 78, 29);
		add(btnCreate);
		btnCreate.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ClientController.getInstance().switchView("create");

			}
		});
		ClientController.getInstance().getAccountAuctions();
	}

	public void populate1(AuctionList list) {
		panel_1.removeAll();
		gbl_panel_1 = new GridBagLayout();
		panel_1.setLayout(gbl_panel_1);
		int size = list.getSize();
		gbl_panel_1.columnWidths = new int[] { 900, 0 };
		gbl_panel_1.rowHeights = new int[size];
		gbl_panel_1.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 0.0, 0.0 };

		for (int i = 0; i < size; i++) {

			Auction tmp = list.getElem(i);

			auction_box = new JPanel();
			auction_box.setBackground(UIManager.getColor("InternalFrame.activeBorderColor"));
			gbc_auction_box = new GridBagConstraints();
			gbc_auction_box.fill = GridBagConstraints.HORIZONTAL;
			gbc_auction_box.insets = new Insets(10, 10, 10, 10);
			gbc_auction_box.gridx = 0;
			gbc_auction_box.gridy = i;
			panel_1.add(auction_box, gbc_auction_box);
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

			JLabel lblDescription;
			if (tmp.getDescription().length() > 30) {
				lblDescription = new JLabel("Description: "
						+ tmp.getDescription().substring(0, 30) + "...");
			} else {
				lblDescription = new JLabel("Description: "
						+ tmp.getDescription());
			}
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
			JButton btnX = new JButton("X");
			GridBagConstraints gbc_btnX = new GridBagConstraints();
			gbc_btnX.insets = new Insets(0, 0, 5, 0);
			gbc_btnX.gridx = 22;
			gbc_btnX.gridy = 1;
			auction_box.add(btnX, gbc_btnX);
			btnX.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {

					ClientController.getInstance().removeAuction(tmp.getId());;

				}
			});
			revalidate();

		}
		panel_1.repaint();
	}

	public void populate2(AuctionList list) {
		panel_2.removeAll();
		gbl_panel_2 = new GridBagLayout();
		panel_2.setLayout(gbl_panel_2);
		int size = list.getSize();
		gbl_panel_2.columnWidths = new int[] { 900, 0 };
		gbl_panel_2.rowHeights = new int[size];
		gbl_panel_2.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_panel_2.rowWeights = new double[] { 0.0, 0.0 };

		for (int i = 0; i < size; i++) {

			Auction tmp = list.getElem(i);

			auction_box = new JPanel();
			auction_box.setBackground(UIManager.getColor("InternalFrame.activeBorderColor"));
			gbc_auction_box = new GridBagConstraints();
			gbc_auction_box.fill = GridBagConstraints.HORIZONTAL;
			gbc_auction_box.insets = new Insets(10, 10, 10, 10);
			gbc_auction_box.gridx = 0;
			gbc_auction_box.gridy = i;
			panel_2.add(auction_box, gbc_auction_box);
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

			JLabel lblDescription;
			if (tmp.getDescription().length() > 30) {
				lblDescription = new JLabel("Description: "
						+ tmp.getDescription().substring(0, 30) + "...");
			} else {
				lblDescription = new JLabel("Description: "
						+ tmp.getDescription());
			}
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

		}
		panel_2.repaint();
	}

	public void populate3(AuctionList list) {
		panel_3.removeAll();
		gbl_panel_3 = new GridBagLayout();
		panel_3.setLayout(gbl_panel_3);
		int size = list.getSize();
		gbl_panel_3.columnWidths = new int[] { 900, 0 };
		gbl_panel_3.rowHeights = new int[size];
		gbl_panel_3.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_panel_3.rowWeights = new double[] { 0.0, 0.0 };

		for (int i = 0; i < size; i++) {

			Auction tmp = list.getElem(i);

			auction_box = new JPanel();
			auction_box.setBackground(UIManager.getColor("InternalFrame.activeBorderColor"));
			gbc_auction_box = new GridBagConstraints();
			gbc_auction_box.fill = GridBagConstraints.HORIZONTAL;
			gbc_auction_box.insets = new Insets(10, 10, 10, 10);
			gbc_auction_box.gridx = 0;
			gbc_auction_box.gridy = i;
			panel_3.add(auction_box, gbc_auction_box);
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

			JLabel lblDescription;
			if (tmp.getDescription().length() > 30) {
				lblDescription = new JLabel("Description: "
						+ tmp.getDescription().substring(0, 30) + "...");
			} else {
				lblDescription = new JLabel("Description: "
						+ tmp.getDescription());
			}
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
			JLabel lblWinnerM = new JLabel("Contact Email "
					+ tmp.getCreatorEmail());
			GridBagConstraints gbc_lblWinnerM = new GridBagConstraints();
			gbc_lblWinnerM.gridx = 4;
			gbc_lblWinnerM.gridy = 2;
			auction_box.add(lblWinnerM, gbc_lblWinnerM);
			revalidate();

		}
		panel_3.repaint();
	}
	public void updateBalance(){
		lblNewLabel.setText("Balance: "
				+ ClientController.getInstance().getUser().getFunds());
	}
	
	public void updateName(){
		lblNewLabel_1.setText("Name: " + ClientController.getInstance().getUser().getName());
	}
	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof AuctionList[]) {
			AuctionList[] tmp = (AuctionList[]) arg;
		
			populate1(tmp[0]);
			populate2(tmp[1]);
			populate3(tmp[2]);
		} else if (arg instanceof User) {
			lblNewLabel.setText("Balance: "
					+ ClientController.getInstance().getUser().getFunds());
		}

	}
}