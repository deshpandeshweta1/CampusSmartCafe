package csc.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import csc.control.VendingMachineController;
import csc.entity.FoodItem;

public class VendingMachinePanel extends JPanel implements ActionListener {
	private String vendingMachineID = null;
	JPanel totalGUI = new JPanel();
	JLabel message, message1;
	JPanel welcomePanel;
	JTable sessionFoodItemTable;
	JLabel qtyLabel;
	JLabel lblTotalCostValue;
	JLabel lblTotalCalorieValue;
	private VendingMachineController vendingMachineController = null;
	//FoodItem foodItem;
	private List<FoodItem> bindFoodItemsList;
	JTabbedPane pane;
	private String selectedVendingMachineName = null;
	
	public VendingMachinePanel(JTabbedPane pane, String selectedVendingMachineName) throws Exception {
		this.selectedVendingMachineName= selectedVendingMachineName;
		this.pane = pane;
		vendingMachineController = new VendingMachineController(selectedVendingMachineName);
		vendingMachineID = vendingMachineController.getVendingMachineIDFromDB(selectedVendingMachineName);
		bindFoodItemsList = vendingMachineController.getSelectedVMFoodItemsFromDB(vendingMachineID);
		this.add(setLayout(selectedVendingMachineName));
		this.setBackground(new Color(225, 225, 128));
	}

	private JPanel setLayout(String selectedVendingMachineName) throws SQLException {
		
		JPanel layoutPanelMain = new JPanel();
		BoxLayout layoutMain = new BoxLayout(layoutPanelMain, BoxLayout.Y_AXIS);
		layoutPanelMain.setLayout(layoutMain);
		
		layoutPanelMain.add(setWelcomeLabel(selectedVendingMachineName));
		
		JPanel layoutPanel = new JPanel();
		BoxLayout layout = new BoxLayout(layoutPanel, BoxLayout.X_AXIS);
		layoutPanel.setLayout(layout);
		layoutPanel.add(setVMLayout(selectedVendingMachineName));

		JPanel panel = new JPanel();
		//panel.setLayout(new GridLayout(2, 0));
		BoxLayout layoutset = new BoxLayout(panel, BoxLayout.Y_AXIS);
		panel.setLayout(layoutset);
		panel.add(setSessionLayout());
		panel.add(setExpensePanel());
		layoutPanel.add(panel);
		
		JPanel dispense = new JPanel();
		JLabel empty = new JLabel();
		JTextArea area = new JTextArea();
		area.setRows(3);
		area.setColumns(6);
		area.setBackground(new Color(0, 0, 120));
		dispense.add(empty);
		dispense.add(empty);
		dispense.add(empty);
		dispense.add(area);
		dispense.setBackground(new Color(0, 0, 120));
		//layoutPanel.add(setExpensePanel());
		
		//layoutPanel.setBounds(getVisibleRect());
		/*layoutPanel.setOpaque(true);
		layoutPanel.setBorder(new LineBorder(Color.BLACK));*/
		//layoutPanel.setPreferredSize(new Dimension(100, 400));
		
		layoutPanel.setVisible(true);
		
		layoutPanelMain.add(layoutPanel);
		layoutPanelMain.add(dispense);
		layoutPanelMain.setBorder(new SoftBevelBorder(BevelBorder.LOWERED));
		layoutPanelMain.setBackground(Color.black);
		return layoutPanelMain;
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
		JButton btnPlaceOrder = btnBuyFoodItem(this.pane,this.selectedVendingMachineName); //new JButton("Place Order");
		orderPanel.add(btnPlaceOrder);
		layoutPanel2.add(orderPanel);
		layoutPanel2.setBackground(new Color(225, 225, 128));

		layoutPanel.add(layoutPanel2);

		layoutPanel.setBackground(new Color(225, 225, 128));
		layoutPanel.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
		return layoutPanel;
	}
	private JPanel setSessionLayout() {
		JPanel layoutPanel = new JPanel();
		BoxLayout layout2 = new BoxLayout(layoutPanel, BoxLayout.Y_AXIS);
		layoutPanel.setLayout(layout2);

/*		TitledBorder title;
		title = BorderFactory.createTitledBorder("Selected item list:");
		title.setTitleJustification(TitledBorder.CENTER);
		title.setTitleFont(new Font("Serif", Font.BOLD, 16));*/
		
		layoutPanel.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
		layoutPanel.setBackground(new Color(225, 225, 128));
		JPanel btnPanel = new JPanel();
		btnPanel.add(btnDeleteJTableRow());
		btnPanel.add(btnClearAllJTable());
		btnPanel.setBackground(new Color(225, 225, 128));
		
		layoutPanel.add(setSessionPanelLabel());
		layoutPanel.add(sessionTabel());
		layoutPanel.add(btnPanel);
		return layoutPanel;
	}

	private JPanel setVMLayout(String selectedVendingMachineName) throws SQLException {
		JPanel containerPanel = new JPanel();
		BoxLayout layout1 = new BoxLayout(containerPanel, BoxLayout.Y_AXIS);
		containerPanel.setLayout(layout1);
		
		JPanel layoutPanel = new JPanel();
		BoxLayout layout2 = new BoxLayout(layoutPanel, BoxLayout.Y_AXIS);
		layoutPanel.setLayout(layout2);

		//JLabel empty = new JLabel();
		//layoutPanel.add(empty);
		layoutPanel.add(vendingMachinePanel());

		containerPanel.add(layoutPanel);

		return containerPanel;
	}

	private JPanel setWelcomeLabel(String selectedVendingMachineName) {
		welcomePanel = new JPanel();
		message = new JLabel(selectedVendingMachineName, SwingConstants.CENTER);
		message.setFont(new Font("Serif", Font.BOLD, 20));
		message.setForeground(Color.white);

		welcomePanel.add(message);
		welcomePanel.setBackground(new Color (0,0,102));

		return welcomePanel;
	}
	
	
	private JPanel setSessionPanelLabel() {
		JPanel seesionPanel = new JPanel();
		JLabel seesionLabel = new JLabel("Selected FoodItem list", SwingConstants.CENTER);
		seesionLabel.setFont(new Font("Serif", Font.BOLD, 20));
		seesionLabel.setForeground(Color.white);

		seesionPanel.add(seesionLabel);
		seesionPanel.setBackground(new Color (0,0,102));

		return seesionPanel;
	}
	
	private JPanel setPurchaseLabel() {
		JPanel purchasePanel = new JPanel();
		JLabel prchaseLabel = new JLabel("Total", SwingConstants.CENTER);
		prchaseLabel.setFont(new Font("Serif", Font.BOLD, 20));
		prchaseLabel.setForeground(Color.white);

		purchasePanel.add(prchaseLabel);
		purchasePanel.setBackground(new Color (0,0,102));
		
		return purchasePanel;
	}
	

	public JPanel vendingMachinePanel() throws SQLException {

		if (bindFoodItemsList != null) {
			for (FoodItem item : bindFoodItemsList) {

				ImageIcon productImage = new ImageIcon(this.getClass().getResource("/" + item.getItemId() + ".jpg"));
				Image newimg = productImage.getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
				// String i =
				// String.valueOf(foodItemId)+":"+name+":"+String.valueOf(price)+":"+String.valueOf(quantity);
				JButton product = new JButton();

				product.setIcon(new ImageIcon(newimg));
				product.setName(String.valueOf(item.getItemId()));
				product.addActionListener(this);

				JPanel btnImgPanel = new JPanel();
				btnImgPanel.add(product);

				JLabel productName = new JLabel(item.getItemName());
				productName.setForeground(Color.WHITE);
				JLabel productPrice = new JLabel(("$" + item.getItemPrice() + "0"));
				productPrice.setForeground(Color.WHITE);
				JLabel qtyLabel = new JLabel(String.valueOf("(" + item.getItemQuantity() + ")"));
				qtyLabel.setForeground(Color.WHITE);

				JPanel panel = new JPanel();
				panel.add(productName);
				panel.add(productPrice);
				panel.add(qtyLabel);
				panel.setBackground(new Color (0,0,102));

				JPanel mainPanel = new JPanel();
				mainPanel.add(btnImgPanel);
				mainPanel.add(panel);
				BoxLayout layout2 = new BoxLayout(mainPanel, BoxLayout.Y_AXIS);
				mainPanel.setLayout(layout2);
				btnImgPanel.setBackground(new Color(225, 225, 128));

				totalGUI.add(mainPanel);
			}
		}

		totalGUI.setLayout(new GridLayout(4, 2));
		//totalGUI.setBackground(new Color(128,255,255));
		//Border roundedBorder = new LineBorder(Color.BLACK, 15, true);
		totalGUI.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
		totalGUI.setBackground(new Color(225, 225, 128));
		return totalGUI;
	}

	private JPanel sessionTabel() {
		JPanel panel = new JPanel();
		sessionFoodItemTable = new JTable();
		sessionFoodItemTable.setPreferredScrollableViewportSize(new Dimension(290, 100));
		sessionFoodItemTable
				//.setModel(new DefaultTableModel(null, new String[] { "FoodItem ID", "Name", "Qty", "Unit Price", "Calories" }));
		.setModel(new DefaultTableModel(null, new String[] { "FoodItem ID", "Name", "Qty"}));
		sessionFoodItemTable.setSelectionBackground(Color.LIGHT_GRAY);

		JScrollPane scrollPane = new JScrollPane(sessionFoodItemTable);

		panel.add(scrollPane);
		panel.revalidate();
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

						//String[] row = { String.valueOf(item.getItemId()), item.getItemName(), "1",
								//"$" + String.valueOf(item.getItemPrice()), String.valueOf(item.getcalorieProfile().getTotalCalories()) };

						String[] row = { String.valueOf(item.getItemId()), item.getItemName(), "1" };

						
						item.setItemQuantity(item.getItemQuantity() - 1);

						if (model != null && model.getRowCount() > 0) {
							for (int i = 0; i < model.getRowCount(); i++) {
								int rowID = Integer.parseInt((String) model.getValueAt(i, 0));
								if (item.getItemId() == rowID) {

									newQty = Integer.parseInt(model.getValueAt(i, 2).toString());
									newQty = newQty + 1;

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
						vendingMachineController.addFoodItemToSession(itemId, newQty);
					    
						 double totalSessionCost = vendingMachineController.calculateTotalCostOfSession(bindFoodItemsList);
						 lblTotalCostValue.setText("$" + String.valueOf(totalSessionCost));
						 lblTotalCostValue.repaint();
						 
						 int totalSessionCalories = vendingMachineController.calculateTotalCaloriesOfSession(bindFoodItemsList);
						 lblTotalCalorieValue.setText(String.valueOf(totalSessionCalories));
						 lblTotalCalorieValue.repaint();
						// Session code end

						try {
							totalGUI.removeAll();
							vendingMachinePanel();
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

		}

	}

	private JButton btnDeleteJTableRow() {
		JButton btnDelete = new JButton("Delete");
		btnDelete.setBackground(new Color(0,0,102));
		btnDelete.setForeground(Color.white);
		
		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {

				DefaultTableModel model = (DefaultTableModel) sessionFoodItemTable.getModel();
				if (model != null && model.getRowCount() > 0) {
					int row = sessionFoodItemTable.getSelectedRow();
					int itemID = Integer.parseInt(sessionFoodItemTable.getValueAt(row, 0).toString());

					if (bindFoodItemsList != null) {
						for (FoodItem item : bindFoodItemsList) {
							if (item.getItemId() == itemID) {
								item.setItemQuantity(
										item.getItemQuantity() + Integer.parseInt(model.getValueAt(row, 2).toString()));
							}
						}

						model.removeRow(row);
						vendingMachineController.removeFoodItemFromSession(itemID);
					    
						 double totalSessionCost = vendingMachineController.calculateTotalCostOfSession(bindFoodItemsList);
						 lblTotalCostValue.setText("$" + String.valueOf(totalSessionCost));
						 lblTotalCostValue.repaint();
						 
						 int totalSessionCalories = vendingMachineController.calculateTotalCaloriesOfSession(bindFoodItemsList);
						 lblTotalCalorieValue.setText(String.valueOf(totalSessionCalories));
						 lblTotalCalorieValue.repaint();

						try {
							totalGUI.removeAll();
							vendingMachinePanel();
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
		JButton btnDelete = new JButton("Clear All");
		btnDelete.setBackground(new Color(0,0,102));
		btnDelete.setForeground(Color.white);

		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				DefaultTableModel model = (DefaultTableModel) sessionFoodItemTable.getModel();
				if (model != null && model.getRowCount() > 0) {
					model.setRowCount(0);

					vendingMachineController.removeAllFoodItemFromSession();

					 lblTotalCostValue.setText("$0.00");
					 lblTotalCostValue.repaint();
			
					 lblTotalCalorieValue.setText("0");
					 lblTotalCalorieValue.repaint();

					try {
					vendingMachineController.getSelectedVMFoodItemsFromDB(vendingMachineID);
						totalGUI.removeAll();
						vendingMachinePanel();
						totalGUI.validate();
						totalGUI.repaint();

					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}

		});

		return btnDelete;
	}

	
	private JButton btnBuyFoodItem(JTabbedPane pane, String cafeName) {
		JButton btnBuyFoodItems = new JButton("Buy FoodItem");
		btnBuyFoodItems.setBackground(new Color(0,0,102));
		btnBuyFoodItems.setForeground(Color.white);
		
		btnBuyFoodItems.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
			
				try {
					boolean flag = false;
					flag = vendingMachineController.btnBuyFoodItem();
					if(flag==true)
					{
					//JOptionPane.showMessageDialog(null,"Thank You. Please collect your dispensed FoodItems!!");
						JOptionPane.showMessageDialog(null,"Please collect your dispensed FoodItems!!","Thank You!!",JOptionPane.INFORMATION_MESSAGE);
					
					DefaultTableModel model = (DefaultTableModel) sessionFoodItemTable.getModel();
					if (model != null && model.getRowCount() > 0) {
						model.setRowCount(0);
						 vendingMachineController.removeAllFoodItemFromSession();
						 lblTotalCostValue.setText("$0.00");
						 lblTotalCostValue.repaint();
						 lblTotalCalorieValue.setText("0");
						 lblTotalCalorieValue.repaint();

						 
						 pane.removeTabAt(1);
						 pane.insertTab("Profile", null, new JScrollPane(new Profile()), cafeName, 1);
						try {
						vendingMachineController.getSelectedVMFoodItemsFromDB(vendingMachineID);
							totalGUI.removeAll();
							vendingMachinePanel();
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

		return btnBuyFoodItems;
	}
	
}
