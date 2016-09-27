package csc.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Box;
import java.awt.*; 
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.OverlayLayout;
import javax.swing.SwingConstants;

import csc.datalayer.DataBaseConnection;

public class CampusMap extends JPanel{
	
    JButton btnConfirmVendingMachine, btnConfirmCafe;
	private JComboBox comboVendingMachine;
	private JComboBox comboCafe;
	private JFrame frame;
	private JTabbedPane pane;
	private JPanel mapPanel;
	private Image img, newimg;
    
    public CampusMap(JTabbedPane pane) throws Exception {
    	
		this.pane = pane;
    	//this.frame = frame;
    	//this.setBackground(Color.cyan);
		BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
		setLayout(boxLayout);
		
		JLabel label = new JLabel("TechTonic University Campus Map");    //, SwingConstants.LEFT);	 
		label.setFont(new Font("Serif", Font.BOLD, 34));
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		//label.setBorder(javax.swing.BorderFactory.createEmptyBorder(0,-10,0,0));
		label.setForeground(Color.DARK_GRAY);
		
		JPanel panel = new JPanel(new FlowLayout());
		//panel.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.add(displayCampusMap());
		panel.add(setList());
		panel.setBackground(new Color(225, 225, 128));
		
		add(label);
		add(Box.createRigidArea(new Dimension(0, 30)));
		add(panel);
		this.setBackground(new Color(225, 225, 128));
    } 
	    
	private JComboBox comboBoxVendingMachine() throws Exception {
			
		int i = 0;

		comboVendingMachine = new JComboBox();

		ArrayList<String> arrayList = getVendingMachineListFromDB();

		if (arrayList != null) {
			for (i = -1; i < arrayList.size(); i++) {
				if (i != -1) {
					comboVendingMachine.addItem(arrayList.get(i).toString());
				} else {
					comboVendingMachine.addItem("Select Vending Machine");
				}
			}

		}

		comboVendingMachine.setForeground(Color.BLUE);
		comboVendingMachine.setFont(new Font("Times New Roman", Font.BOLD, 16));
		comboVendingMachine.setEditable(false);

		return comboVendingMachine;
	}
	    
	private JComboBox comboBoxCafe() throws SQLException {
			
		int i = 0;

		comboCafe = new JComboBox();

		ArrayList<String> arrayList = getCafeListFromDB();

		if (arrayList != null) {
			for (i = -1; i < arrayList.size(); i++) {
				if (i != -1) {
					comboCafe.addItem(arrayList.get(i).toString());
				} else {
					comboCafe.addItem("Select Cafe");
				}
			}

		}

		comboCafe.setForeground(Color.BLUE);
		comboCafe.setFont(new Font("Times New Roman", Font.BOLD, 16));
		comboCafe.setEditable(false);

		return comboCafe;
	}
	    
	private JButton btnConfirmVendingMachine() {
	    	
    	btnConfirmVendingMachine = new JButton("Confirm"); 
    	//btnConfirmVendingMachine.setBackground(new Color(128,255,255));
    	
    	btnConfirmVendingMachine.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				try {
					int selectedindex = (int) comboVendingMachine.getSelectedIndex();
	
					if (selectedindex > 0) {
						String selectedItemName =  (String) comboVendingMachine.getSelectedItem();
						
						try {
							//pane.addTab("Vending Machine", new VendingMachine());
	/*							JLabel lblTitle = new JLabel(selectedItemName);
								JButton btnClose = new JButton("x");
								JPanel panel = new JPanel();*/
							
							int i = pane.getComponentCount();
							//pane.addTab(selectedItemName, new VendingMachine(pane, selectedItemName));
							pane.add(selectedItemName, new JScrollPane(new VendingMachinePanel(pane, selectedItemName)));
							pane.setSelectedIndex(i++);
							//JLabel selectLabel = new JLabel("Please select your items:");
							
							System.out.println(i);
	
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
		
					}
	
				} catch (Exception e) {
					//lblEnterKeyError.setText("Please Select the Key.");
				}
	
			}
    	});

		return btnConfirmVendingMachine;
	}

	public JButton btnConfirmCafe() {
		
		btnConfirmCafe = new JButton("Confirm");
		//btnConfirmCafe.setBackground(new Color(128,255,255));

		btnConfirmCafe.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent event) {
				try {
					int selectedindex = (int) comboCafe.getSelectedIndex();

					if (selectedindex > 0) {
						String selectedItemName =  (String) comboCafe.getSelectedItem();
						
						try {
							int i = pane.getComponentCount();
							//pane.addTab(selectedItemName, new Cafe(pane,selectedItemName));
							pane.add(selectedItemName, new JScrollPane(new CafePanel(pane, selectedItemName)));
							pane.setSelectedIndex(i++);
							//JLabel selectLabel = new JLabel("Please select your items:");
							
							//System.out.println(i);

						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
		
					}

				} catch (Exception e) {
					//lblEnterKeyError.setText("Please Select the Key.");
				}

			}
		});
		
		return btnConfirmCafe;
	}
	    
	private JPanel setVendingMachineList() throws Exception {

		JPanel panel = new JPanel();

    	panel.setLayout(new FlowLayout());
    	panel.add(comboBoxVendingMachine());
    	panel.add(btnConfirmVendingMachine());
    	panel.setBackground(new Color(225, 225, 128));
 		return panel;
	}
	
	private JPanel setCafeList() throws SQLException {

    	JPanel panel = new JPanel();

    	panel.setLayout(new FlowLayout());
    	panel.add(comboBoxCafe());
    	panel.add(btnConfirmCafe());
    	panel.setBackground(new Color(225, 225, 128));
 		return panel;
 	}
	    
    private JPanel setList() throws Exception {

    	JPanel panel = new JPanel();
panel.setBackground(new Color(225, 225, 128));
    	panel.setLayout(new GridLayout(2, 0, 10, 10));	    
    	panel.add(setVendingMachineList());
    	panel.add(setCafeList());
    	
 		return panel;
 	}	    
	    
	public ArrayList getVendingMachineListFromDB() throws SQLException {

		String query = "select name from VendingMachine";
		Connection con = null;
		Statement stmt = null;
		ResultSet resultSet = null;
		ArrayList<String> arrayList = null;

		try {
			con = new DataBaseConnection().openConnection();

			stmt = con.createStatement();

			PreparedStatement pre = con.prepareStatement(query);
			resultSet = pre.executeQuery();

			arrayList = new ArrayList<String>();
			while (resultSet.next()) {
				arrayList.add(resultSet.getString("name"));
			}

		} catch (SQLException e) {
			// e.printStackTrace();
			System.out.println("SQL Exception");
		} catch (ClassNotFoundException e) {
			System.out.println("Cannot find the database driver");
		} finally {

			stmt.close();
			con.close();
		}
		return arrayList;

	}
	
	public ArrayList getCafeListFromDB() throws SQLException {

		String query = "select name from Cafe";
		Connection con = null;
		Statement stmt = null;
		ResultSet resultSet = null;
		ArrayList<String> arrayList = null;

		try {
			con = new DataBaseConnection().openConnection();

			stmt = con.createStatement();

			PreparedStatement pre = con.prepareStatement(query);
			resultSet = pre.executeQuery();

			arrayList = new ArrayList<String>();
			while (resultSet.next()) {
				arrayList.add(resultSet.getString("name"));
			}

		} catch (SQLException e) {
			// e.printStackTrace();
			System.out.println("SQL Exception");
		} catch (ClassNotFoundException e) {
			System.out.println("Cannot find the database driver");
		} finally {

			stmt.close();
			con.close();
		}
		return arrayList;

	}
	
	private JPanel displayCampusMap()
	    {
	    	mapPanel = new JPanel();
	    	
	    	BufferedImage campusMap = null; 
	    	
//	    	try {
//	    		campusMap = ImageIO.read(new File("image/9.jpg"));
//			} catch (IOException ex) {
//				System.out.println("Map does not exist" + ex.getMessage()); 
//			}	
	    	
	    	img = new ImageIcon(this.getClass().getResource("/map1.png")).getImage();
	    	newimg = img.getScaledInstance(900, 590,  java.awt.Image.SCALE_SMOOTH);
//	    	img = new ImageIcon(this.getClass().getResource("/map1.png")).getImage();
//	    	Dimension size = new Dimension(img.getWidth(null), img.getHeight(null)); 
//			setPreferredSize(size);
	    	 
			JLabel label = new JLabel(); 
			label.setIcon(new ImageIcon(newimg)); 
			//label.set
			
			GridBagLayout layout = new GridBagLayout(); 
			JPanel buttonPanel = new JPanel(); 
			buttonPanel.setLayout(layout);
			img = new ImageIcon(this.getClass().getResource("/pin.png")).getImage();
	    	Image newimg1 = img.getScaledInstance(20, 30,  java.awt.Image.SCALE_SMOOTH);
	    	
			JButton	cafe1 = new JButton("StarBucks",new ImageIcon(newimg1)); 
			cafe1.setFont(new Font("Serif", Font.BOLD, 18));
			cafe1.setBorder(BorderFactory.createEmptyBorder());
			cafe1.setContentAreaFilled(false);
			
			cafe1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					try {
						int i = pane.getComponentCount();
						//pane.addTab(selectedItemName, new Cafe(pane,selectedItemName));
						pane.add("StarBucks", new JScrollPane(new CafePanel(pane, "StarBucks")));
						pane.setSelectedIndex(i++);
						//JLabel selectLabel = new JLabel("Please select your items:");
						
						System.out.println(i);
	
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});

			JButton	cafe2 = new JButton("Benson",new ImageIcon(newimg1)); 
			cafe2.setFont(new Font("Serif", Font.BOLD, 18));
			cafe2.setBorder(BorderFactory.createEmptyBorder());
			cafe2.setContentAreaFilled(false);
			
			cafe2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					try {
						int i = pane.getComponentCount();
						//pane.addTab(selectedItemName, new Cafe(pane,selectedItemName));
						pane.add("Benson", new JScrollPane(new CafePanel(pane, "Benson")));
						pane.setSelectedIndex(i++);
						//JLabel selectLabel = new JLabel("Please select your items:");
						
						System.out.println(i);
	
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			
			JButton vm1 = new JButton("VM1",new ImageIcon(newimg1)); 
			vm1.setFont(new Font("Serif", Font.BOLD, 18));
			vm1.setBorder(BorderFactory.createEmptyBorder());
			vm1.setContentAreaFilled(false); 
			
			vm1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					try {
						int i = pane.getComponentCount();
						//pane.addTab(selectedItemName, new Cafe(pane,selectedItemName));
						pane.add("VendingMachine1", new JScrollPane(new VendingMachinePanel(pane, "VendingMachine1")));
						pane.setSelectedIndex(i++);
						//JLabel selectLabel = new JLabel("Please select your items:");
						
						System.out.println(i);
	
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			
			JButton vm2 = new JButton("VM2",new ImageIcon(newimg1)); 
			vm2.setFont(new Font("Serif", Font.BOLD, 18));
			vm2.setBorder(BorderFactory.createEmptyBorder());
			vm2.setContentAreaFilled(false); 
			
			vm2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					try {
						int i = pane.getComponentCount();
						//pane.addTab(selectedItemName, new Cafe(pane,selectedItemName));
						pane.add("VendingMachine2", new JScrollPane(new VendingMachinePanel(pane, "VendingMachine2")));
						pane.setSelectedIndex(i++);
						//JLabel selectLabel = new JLabel("Please select your items:");
						
						System.out.println(i);
	
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			
			JButton vm3 = new JButton("VM3",new ImageIcon(newimg1)); 
			vm3.setFont(new Font("Serif", Font.BOLD, 18));
			vm3.setBorder(BorderFactory.createEmptyBorder());
			vm3.setContentAreaFilled(false); 
			
			vm3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					try {
						int i = pane.getComponentCount();
						//pane.addTab(selectedItemName, new Cafe(pane,selectedItemName));
						pane.add("VendingMachine3", new JScrollPane(new VendingMachinePanel(pane, "VendingMachine3")));
						pane.setSelectedIndex(i++);
						//JLabel selectLabel = new JLabel("Please select your items:");
						
						System.out.println(i);
	
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			
			JButton vm4 = new JButton("VM4",new ImageIcon(newimg1)); 
			vm4.setFont(new Font("Serif", Font.BOLD, 18));
			vm4.setBorder(BorderFactory.createEmptyBorder());
			vm4.setContentAreaFilled(false); 
			
			vm4.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					try {
						int i = pane.getComponentCount();
						//pane.addTab(selectedItemName, new Cafe(pane,selectedItemName));
						pane.add("VendingMachine4", new JScrollPane(new VendingMachinePanel(pane, "VendingMachine4")));
						pane.setSelectedIndex(i++);
						//JLabel selectLabel = new JLabel("Please select your items:");
						
						//System.out.println(i);
	
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			
			//buttonPanel.add(label);
			buttonPanel.add(cafe1);
			buttonPanel.add(vm1);
			buttonPanel.setOpaque(false);
			
			GridBagConstraints gbc_cafe1 = new GridBagConstraints(); 
			//gbc_cafe1.anchor = GridBagConstraints.NORTHEAST;
			gbc_cafe1.insets = new Insets(0, 90, 490, 90);
			gbc_cafe1.gridx = 0;
			gbc_cafe1.gridy = 0;
			buttonPanel.add(cafe1, gbc_cafe1);
			
			GridBagConstraints gbc_cafe2 = new GridBagConstraints(); 
			//gbc_cafe2.anchor = GridBagConstraints.NORTHEAST;
			gbc_cafe2.insets = new Insets(0, 0, 70, -555);
			gbc_cafe2.gridx = 0;
			gbc_cafe2.gridy = 0;
			buttonPanel.add(cafe2, gbc_cafe2);
			
			GridBagConstraints gbc_vm1 = new GridBagConstraints();
			gbc_vm1.insets = new Insets(0, 0, 230, -200);
			gbc_vm1.gridx = 0;
			gbc_vm1.gridy = 0;
			buttonPanel.add(vm1, gbc_vm1);
			
			GridBagConstraints gbc_vm2 = new GridBagConstraints();
			gbc_vm2.insets = new Insets(0, 0, 540, -650);
			gbc_vm2.gridx = 0;
			gbc_vm2.gridy = 0;
			buttonPanel.add(vm2, gbc_vm2);
			
			GridBagConstraints gbc_vm3 = new GridBagConstraints();
			gbc_vm3.insets = new Insets(75, -215, 0, 0);
			gbc_vm3.gridx = 0;
			gbc_vm3.gridy = 0;
			buttonPanel.add(vm3, gbc_vm3);
			
			GridBagConstraints gbc_vm4 = new GridBagConstraints();
			gbc_vm4.insets = new Insets(0, -350, 260, 0);
			gbc_vm4.gridx = 0;
			gbc_vm4.gridy = 0;
			buttonPanel.add(vm4, gbc_vm4);
			
			

						
			//label.add(buttonPanel);
			LayoutManager overlay = new OverlayLayout(mapPanel);
			mapPanel.setLayout(overlay);
mapPanel.setBackground(new Color(225, 225, 128));
			mapPanel.add(buttonPanel);
			mapPanel.add(label);
			return mapPanel;
	    }
	    
//	    public void paintComponent(Graphics g) {
//	    	super.paintComponent(g);
//			g.drawImage(newimg, 20, 10, getWidth(), getHeight(), this); 
//		}
}
