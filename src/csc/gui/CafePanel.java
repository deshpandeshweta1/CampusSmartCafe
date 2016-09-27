package csc.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import csc.control.CafeController;
import csc.control.VendingMachineController;
import csc.entity.FoodItem;

public class CafePanel extends JPanel implements ActionListener {
	private String cafeID = null;
	private String cafeName = null;
	JPanel totalGUI = new JPanel();
	JLabel message, message1;
	JPanel welcomePanel;
	JTable sessionFoodItemTable;
	JLabel qtyLabel;
	JLabel lblTotalCostValue;
	JLabel lblTotalCalorieValue;
	JLabel lblPickupTime;
	JLabel lblPickupVenue;
	private CafeController cafeController = null;
	//FoodItem foodItem;
	private List<FoodItem> bindFoodItemsList;
	JTabbedPane pane;
	     
	public CafePanel(JTabbedPane pane, String selectedCafeName) throws Exception {
		this.cafeName= selectedCafeName;
		this.pane = pane;
		cafeController = new CafeController(selectedCafeName);
		cafeID = cafeController.getCafeIDFromDB(selectedCafeName);
		bindFoodItemsList = cafeController.getSelectedCafeFoodItemsFromDB(cafeID);
		
		//this.add(product);
		//product.add(setLayout(selectedCafeName));

		ImageIcon productImage = new ImageIcon(this.getClass().getResource("/coffee-banner.jpg"));
		Image newimg = productImage.getImage().getScaledInstance(1000, 100, java.awt.Image.SCALE_SMOOTH);
		JLabel product = new JLabel();
		product.setIcon(new ImageIcon(newimg));
		JPanel btnImgPanel = new JPanel();
		btnImgPanel.add(product);
		//this.add(btnImgPanel);
		//this.add(setLayout(selectedCafeName));
		
		JPanel layoutPanel = new JPanel();
		BoxLayout layout2 = new BoxLayout(layoutPanel, BoxLayout.Y_AXIS);
		layoutPanel.setLayout(layout2);
		//layoutPanel.add(btnImgPanel);
		layoutPanel.add(setLayout(selectedCafeName));
		this.add(layoutPanel);
		this.setBackground(new Color(153, 0, 0));
		
	}

	
	
	private JPanel setLayout(String selectedCafeName) throws SQLException {
		JPanel layoutPanel = new JPanel();
		BoxLayout layout2 = new BoxLayout(layoutPanel, BoxLayout.Y_AXIS);
		layoutPanel.setLayout(layout2);

		JPanel panel1 = new JPanel();
		panel1.setBackground(new Color(153, 0, 0));
		JLabel empty = new JLabel("");

		layoutPanel.add(setCafeLayout(selectedCafeName));
		layoutPanel.add(panel1);
		layoutPanel.add(empty);
		layoutPanel.add(new JSeparator(SwingConstants.HORIZONTAL));
		layoutPanel.add(empty);

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 2));
		panel.add(setSessionLayout());
		panel.add(setExpensePanel());
		//panel.setBackground(new Color(153, 0, 0));
		

		
		ImageIcon productImage = new ImageIcon(this.getClass().getResource("/coffee-banner.jpg"));
		Image newimg = productImage.getImage().getScaledInstance(2000, 200, java.awt.Image.SCALE_SMOOTH);
		JLabel product = new JLabel();
		product.setIcon(new ImageIcon(newimg));
		JPanel btnImgPanel = new JPanel();
		btnImgPanel.add(product);
		
		//layoutPanel.add(btnImgPanel);
		//layoutPanel.add(panel1);
		layoutPanel.add(panel);
		//layoutPanel.setBackground(new Color(153, 0, 0));
		
		return layoutPanel;
	}

	private JPanel setSessionLayout() {
		JPanel layoutPanel = new JPanel();
		BoxLayout layout2 = new BoxLayout(layoutPanel, BoxLayout.Y_AXIS);
		layoutPanel.setLayout(layout2);

		TitledBorder title;
		//Border roundedBorder = new LineBorder(new Color(255, 26, 26), 2, true);
		title = BorderFactory.createTitledBorder("Selected item list:");//.createTitledBorder("Selected item list:");
		title.setTitleFont(new Font("Serif", Font.BOLD, 16));
		layoutPanel.setBorder(title);
		
		JPanel btnPanel = new JPanel();
		btnPanel.add(btnDeleteJTableRow());
		btnPanel.add(btnClearAllJTable());
		btnPanel.setBackground(new Color(225, 225, 128));

		layoutPanel.add(sessionTabel());
		layoutPanel.add(btnPanel);
		layoutPanel.setBackground(new Color(225, 225, 128));
		return layoutPanel;
	}

	private JPanel setCafeLayout(String selectedCafeName) throws SQLException {
		JPanel containerPanel = new JPanel();
		BoxLayout layout1 = new BoxLayout(containerPanel, BoxLayout.Y_AXIS);
		containerPanel.setLayout(layout1);

		JPanel layoutPanel = new JPanel();
		BoxLayout layout2 = new BoxLayout(layoutPanel, BoxLayout.Y_AXIS);
		layoutPanel.setLayout(layout2);

		// Titled borders
		TitledBorder title;
		//Border roundedBorder = new LineBorder(new Color(255, 26, 26), 2, true);
		title = BorderFactory.createTitledBorder("Please select item:");
		title.setTitleFont(new Font("Serif", Font.BOLD, 16));
		layoutPanel.setBorder(title);
		
		JLabel empty = new JLabel();
		layoutPanel.add(empty);
		layoutPanel.add(cafePanel());
		//layoutPanel.add(sessionTabel());
		layoutPanel.setBackground(new Color(225, 225, 128));
		
		containerPanel.add(setWelcomeLabel(selectedCafeName));
		containerPanel.add(layoutPanel);
		containerPanel.setBackground(new Color(225, 225, 128));
		
		return containerPanel;
	}
	
	private JPanel setWelcomeLabel(String selectedCafeName) {
		welcomePanel = new JPanel();
		welcomePanel.setLayout(new GridLayout(3, 1));
		String vendingMachineName = "Welcome to: ";
		vendingMachineName = vendingMachineName.concat(selectedCafeName);
		message = new JLabel(vendingMachineName, SwingConstants.CENTER);
		message.setFont(new Font("Serif", Font.BOLD, 20));
		message.setForeground(Color.white);
		welcomePanel.setBackground(new Color(153, 0, 0));
		welcomePanel.add(message);

		return welcomePanel;
	}
	
	public JPanel cafePanel() throws SQLException {

		if (bindFoodItemsList != null) {
			for (FoodItem item : bindFoodItemsList) {

				ImageIcon productImage = new ImageIcon(this.getClass().getResource("/" + item.getItemId() + ".jpg"));
				Image newimg = productImage.getImage().getScaledInstance(80, 80, java.awt.Image.SCALE_SMOOTH);
				// String i =
				// String.valueOf(foodItemId)+":"+name+":"+String.valueOf(price)+":"+String.valueOf(quantity);
				JButton product = new JButton();

				product.setIcon(new ImageIcon(newimg));
				product.setName(String.valueOf(item.getItemId()));
				product.addActionListener(this);

				JPanel btnImgPanel = new JPanel();
				btnImgPanel.add(product);
				btnImgPanel.setBackground(new Color(225, 225, 128));

				JLabel productName = new JLabel(item.getItemName());
				JLabel productPrice = new JLabel(("$" + item.getItemPrice() + "0"));
				JLabel qtyLabel = new JLabel(String.valueOf("(" + item.getItemQuantity() + ")"));

				JPanel panel = new JPanel();
				panel.add(productName);
				panel.add(productPrice);
				panel.add(qtyLabel);
				panel.setBackground(new Color(225, 225, 128));

				JPanel mainPanel = new JPanel();
				mainPanel.add(btnImgPanel);
				mainPanel.add(panel);
				BoxLayout layout2 = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
				mainPanel.setLayout(layout2);
				mainPanel.setBackground(new Color(225, 225, 128));
				totalGUI.add(mainPanel);
			}
		}

		totalGUI.setBackground(new Color(225, 225, 128));
		totalGUI.setLayout(new GridLayout(2, 4, 10, 10));
		return totalGUI;
	}

	private JPanel sessionTabel() {
		JPanel panel = new JPanel();
		sessionFoodItemTable = new JTable();
		sessionFoodItemTable.setPreferredScrollableViewportSize(new Dimension(400, 120));
		sessionFoodItemTable
				.setModel(new DefaultTableModel(null, new String[] { "FoodItem ID", "Name", "Qty", "Price" }));

		sessionFoodItemTable.setEnabled(true);
		// sessionFoodItemTable.setRowSelectionAllowed(true);
		sessionFoodItemTable.setSelectionBackground(Color.LIGHT_GRAY);
		
		  JTableHeader header = sessionFoodItemTable.getTableHeader();
	      header.setBackground(new Color(153, 0, 0));
	      header.setForeground(Color.white);

		JScrollPane scrollPane = new JScrollPane(sessionFoodItemTable);

		panel.add(scrollPane);
		panel.revalidate();
		panel.setBackground(new Color(225, 225, 128));
		return panel;

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		JButton o = (JButton) e.getSource();
		String values = o.getName();

		int itemId = Integer.parseInt(values);
		boolean found = false;

		if (bindFoodItemsList != null) {
			int newQty=1;
			for (FoodItem item : bindFoodItemsList) {
				if (item.getItemId() == itemId) {
					if (item.getItemQuantity() > 0) {
						DefaultTableModel model = (DefaultTableModel) sessionFoodItemTable.getModel();
						// String[] row =
						// {values[0],values[1],"1","$"+values[2]};
						String[] row = { String.valueOf(item.getItemId()), item.getItemName(), "1",
								"$" + String.valueOf(item.getItemPrice()) };

						item.setItemQuantity(item.getItemQuantity() - 1);

						if (model != null && model.getRowCount() > 0) {
							for (int i = 0; i < model.getRowCount(); i++) {
								int rowID = Integer.parseInt((String) model.getValueAt(i, 0));
								if (item.getItemId() == rowID) {
									// int newQty = Integer.parseInt((String)
									// model.getValueAt(i, 2));
									newQty = Integer.parseInt(model.getValueAt(i, 2).toString());
									newQty = newQty + 1;
									// model.setValueAt(newQty, i, 2);
									model.setValueAt(newQty, i, 2);
									// break;
									found = true;
								}
							}
							if (found == false) {
								model.addRow(row);
							}
						} else {
							model.addRow(row);
						}

						// Session code start
						cafeController.addFoodItemToSession(itemId, newQty);
					    
						 double totalSessionCost = cafeController.calculateTotalCostOfSession(bindFoodItemsList);
						 lblTotalCostValue.setText("$" + String.valueOf(totalSessionCost));
						 lblTotalCostValue.repaint();
						 
						 int totalSessionCalories = cafeController.calculateTotalCaloriesOfSession(bindFoodItemsList);
						 lblTotalCalorieValue.setText(String.valueOf(totalSessionCalories));
						 lblTotalCalorieValue.repaint();
						// Session code end

						try {
							totalGUI.removeAll();
							cafePanel();
							totalGUI.validate();
							totalGUI.repaint();
							
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					} else {
						JOptionPane.showMessageDialog(null, item.getItemName() + " is currently out of stock");

					}
					break;
				}
			}
			// }
		}

		/*
		 * sessionFoodItemTable.revalidate(); sessionFoodItemTable.repaint();
		 */
	}

	private JButton btnDeleteJTableRow() {
		JButton btnDelete = new JButton("Delete");
		//btnDelete.setBackground(new Color(128,255,255));
		btnDelete.setBackground(new Color(153, 0, 0));
		btnDelete.setForeground(Color.white);

		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {

				DefaultTableModel model = (DefaultTableModel) sessionFoodItemTable.getModel();
				if (model != null && model.getRowCount() > 0) {
					int row = sessionFoodItemTable.getSelectedRow();
					int itemID = 0;
					if(row>=0)
					{
					itemID = Integer.parseInt(sessionFoodItemTable.getValueAt(row, 0).toString());
					}

					if (bindFoodItemsList != null && row>=0) {
						for (FoodItem item : bindFoodItemsList) {
							if (item.getItemId() == itemID) {
								item.setItemQuantity(
										item.getItemQuantity() + Integer.parseInt(model.getValueAt(row, 2).toString()));
							}
						}

						model.removeRow(row);
						cafeController.removeFoodItemFromSession(itemID);
					    
						 double totalSessionCost = cafeController.calculateTotalCostOfSession(bindFoodItemsList);
						 lblTotalCostValue.setText("$" + String.valueOf(totalSessionCost));
						 lblTotalCostValue.repaint();
						 
						 int totalSessionCalories = cafeController.calculateTotalCaloriesOfSession(bindFoodItemsList);
						 lblTotalCalorieValue.setText(String.valueOf(totalSessionCalories));
						 lblTotalCalorieValue.repaint();

						try {
							totalGUI.removeAll();
							cafePanel();
							totalGUI.validate();
							totalGUI.repaint();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}

				}
			}
		});

		return btnDelete;
	}

	private JButton btnClearAllJTable() {
		JButton btnClear = new JButton("Clear All");
		//btnClear.setBackground(new Color(128,255,255));
		btnClear.setBackground(new Color(153, 0, 0));
		btnClear.setForeground(Color.white);

		btnClear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				DefaultTableModel model = (DefaultTableModel) sessionFoodItemTable.getModel();
				if (model != null && model.getRowCount() > 0) {
					model.setRowCount(0);

					cafeController.removeAllFoodItemFromSession();

					 lblTotalCostValue.setText("$0.00");
					 lblTotalCostValue.repaint();
					 
					 lblTotalCalorieValue.setText("0");
					 lblTotalCalorieValue.repaint();

					try {
						cafeController.getSelectedCafeFoodItemsFromDB(cafeID);
						totalGUI.removeAll();
						cafePanel();
						totalGUI.validate();
						totalGUI.repaint();

					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}

		});

		return btnClear;
	}

	private JPanel setExpensePanel() {

		JPanel layoutPanel = new JPanel();
		BoxLayout layout2 = new BoxLayout(layoutPanel, BoxLayout.X_AXIS);
		layoutPanel.setLayout(layout2);

		// Titled borders
		TitledBorder title;
		//Border roundedBorder = new LineBorder(new Color(255, 26, 26), 2, true);
		title = BorderFactory.createTitledBorder("Total:");
		title.setTitleFont(new Font("Serif", Font.BOLD, 16));
		layoutPanel.setBorder(title);
		
		JPanel layoutPanel2 = new JPanel();
		BoxLayout layout3 = new BoxLayout(layoutPanel2, BoxLayout.Y_AXIS);
		layoutPanel2.setLayout(layout3);

		JPanel costPanel = new JPanel();
		JLabel lblTotalCost = new JLabel("Total Cost:");
		costPanel.add(lblTotalCost);
		
		lblTotalCostValue = new JLabel();
		costPanel.add(lblTotalCostValue);
		costPanel.setBackground(new Color(225, 225, 128));
		
		JPanel caloriePanel = new JPanel();
		JLabel lblTotalCalorie = new JLabel("Total Calories:");
		caloriePanel.add(lblTotalCalorie);
		
		lblTotalCalorieValue = new JLabel();
		caloriePanel.add(lblTotalCalorieValue);
		caloriePanel.setBackground(new Color(225, 225, 128));
		
		layoutPanel2.add(costPanel);
		layoutPanel2.add(caloriePanel);
		
		JPanel orderPanel = new JPanel();
		orderPanel.setBackground(new Color(225, 225, 128));
		JButton btnPlaceOrder = btnPlaceOrder(this.pane,this.cafeName); //new JButton("Place Order");
		orderPanel.add(btnPlaceOrder);
		layoutPanel2.add(orderPanel);
		layoutPanel2.setBackground(new Color(225, 225, 128));
		
		
		JPanel layoutPanel1 = new JPanel();
		BoxLayout layout1 = new BoxLayout(layoutPanel1, BoxLayout.Y_AXIS);
		layoutPanel1.setLayout(layout1);
		
		JPanel pickupTimePanel = new JPanel();
		JLabel pickupTime = new JLabel("Pickup Time:");
		pickupTimePanel.add(pickupTime);
		
		lblPickupTime = new JLabel();
		pickupTimePanel.add(lblPickupTime);
		pickupTimePanel.setBackground(new Color(225, 225, 128));
		
		JPanel pickupVenuePanel = new JPanel();
		JLabel pickupVenue = new JLabel("Pickup Venue:");
		pickupVenuePanel.add(pickupVenue);
		pickupVenuePanel.setBackground(new Color(225, 225, 128));
		
		lblPickupVenue = new JLabel();
		pickupVenuePanel.add(lblPickupVenue);
		
		//layoutPanel1.add(thankYouPanel);
		//layoutPanel1.add(pickupTimePanel);
		layoutPanel1.setBackground(new Color(225, 225, 128));
		//layoutPanel1.add(pickupVenuePanel);

		layoutPanel.add(layoutPanel2);
		layoutPanel.add(layoutPanel1);
		layoutPanel.setBackground(new Color(225, 225, 128));
		return layoutPanel;
	}
	
	private JButton btnPlaceOrder(JTabbedPane pane, String cafeName) {
		JButton btnPlaceOrder = new JButton("Place Order");
		//btnPlaceOrder.setBackground(new Color(128,255,255));
		btnPlaceOrder.setBackground(new Color(153, 0, 0));
		btnPlaceOrder.setForeground(Color.white);

		btnPlaceOrder.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
			
				try {
					boolean flag = false;
					flag = cafeController.btnBuyFoodItem();
					if(flag==true)
					{
						int pickUpTime=cafeController.displayPickUpTime();
						if(pickUpTime>0)
						{

					JOptionPane.showMessageDialog(null,"Your order will be ready at "+ cafeName +" Cafe"+"\n"+ "within "+ pickUpTime + "mins.","Thank You, for your order!!",JOptionPane.INFORMATION_MESSAGE);
					
						}
						else
						{
							JOptionPane.showMessageDialog(null,"Please collect your order at "+ cafeName +" Cafe","Thank You, for your order!!",JOptionPane.INFORMATION_MESSAGE);
						}
					DefaultTableModel model = (DefaultTableModel) sessionFoodItemTable.getModel();
					if (model != null && model.getRowCount() > 0) {
						model.setRowCount(0);
						cafeController.removeAllFoodItemFromSession();
						 lblTotalCostValue.setText("$0.00");
						 lblTotalCostValue.repaint();
						 lblTotalCalorieValue.setText("0");
						 lblTotalCalorieValue.repaint();
						 /*lblPickupTime.setText(String.valueOf(cafeController.displayPickUpTime()));
						 lblPickupTime.repaint();
						 lblPickupVenue.setText(cafeName);
						 lblPickupVenue.repaint();*/

						 pane.removeTabAt(1);
						 pane.insertTab("Profile", null, new JScrollPane(new Profile()), cafeName, 1);
						 //pane.addTab("Profile", new JScrollPane(new Profile()));
						 
						try {
							cafeController.getSelectedCafeFoodItemsFromDB(cafeID);
							totalGUI.removeAll();
							cafePanel();
							totalGUI.validate();
							totalGUI.repaint();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		return btnPlaceOrder;
	}

}



