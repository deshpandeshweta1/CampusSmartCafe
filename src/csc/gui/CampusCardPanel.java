package csc.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import csc.control.CampusCardUserValidation;
import csc.entity.CampusCard;
import csc.entity.CampusCardUser;
import csc.entity.CardUserPreferences;

public class CampusCardPanel extends JPanel {

	private JFrame frame;
	JPanel sectionPanel;
	JLabel message, message1;
	JPanel welcomePanel;
	JPanel cardSectionPanel;
	JTextField txtCardNumber;
	JTextField txtPassword;

	public CampusCardPanel(JFrame frame) {
		setBackground(Color.cyan); 
		setBorderLayout();
		this.frame = frame;
		this.setBackground(new Color(225, 225, 128));
		//this.setBackground(new Color(44, 94, 145));
		//#D1491B
	}

	private void setBorderLayout() {
		this.setLayout(new BorderLayout());
		add(setWelcomeLabel(), BorderLayout.NORTH);
		add(setCardSectionPanel(), BorderLayout.CENTER);
		add(setFooter(), BorderLayout.SOUTH);
	}

	private JPanel setFooter() {
		JPanel footer = new JPanel();
		message = new JLabel("Address : 500 El Camino Real, Santa Clara, CA 95053");
		message1 = new JLabel("Phone: (408) 554-4000");
		message.setForeground(Color.DARK_GRAY);
		message.setFont(new Font("Serif", Font.PLAIN, 16));
		message1.setForeground(Color.DARK_GRAY);
		message1.setFont(new Font("Serif", Font.PLAIN, 16));

		footer.add(message);
		footer.add(message1);
		footer.setBackground(new Color(225, 225, 128));
		return footer;
	}

	private JPanel setWelcomeLabel() {
		welcomePanel = new JPanel();
		welcomePanel.setLayout(new GridLayout(3, 1));
		message = new JLabel("Welcome to TechTonic University", SwingConstants.CENTER);
		message.setFont(new Font("Serif", Font.BOLD, 40));
		message.setForeground(Color.DARK_GRAY);

		JLabel title = new JLabel("User Login", SwingConstants.CENTER);
		title.setFont(new Font("Serif", Font.BOLD, 22));
		JLabel empty = new JLabel("");

		welcomePanel.add(empty);
		welcomePanel.add(message);
		welcomePanel.add(title);
		welcomePanel.setBackground(new Color(225, 225, 128));
		return welcomePanel;
	}

	private JPanel setCardSectionPanel() {
		cardSectionPanel = new JPanel();
		cardSectionPanel.setBackground(new Color(225, 225, 128));

		JLabel lblCardNumber = new JLabel("Card Number:");
		JLabel lblPassword = new JLabel("Password:");

		txtCardNumber = new JTextField(10);
		txtPassword = new JPasswordField(8);

		JButton btnSubmit = new JButton("Submit");
		//btnSubmit.setBackground(new Color(128,255,255));
		JButton btnClear = new JButton("Clear All");
		//btnClear.setBackground(new Color(128,255,255));

		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				
				CampusCardUserValidation validate = new CampusCardUserValidation(txtCardNumber.getText(), txtPassword.getText());
				
				String content = "";
				try {
					content = validate.validateUser();
				} catch (SQLException e) {
					System.out.println("SQL Exception3");
				} 
				
				if(content.isEmpty() == false) {
					JOptionPane.showMessageDialog( null,
							content);
					clearLayout();
				} else {
					
					JTabbedPane pane = new JTabbedPane();
					
					try {
						pane.addTab("Campus Map", new JScrollPane(new CampusMap(pane)));
						pane.addTab("Profile", new JScrollPane(new Profile()));
						pane.setBackground(new Color(225, 225, 128));
						//pane.setTabLayoutPolicy(tabLayoutPolicy);

					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					frame.getContentPane().remove(0);// .removeAll();
					Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
					int height = screenSize.height;
					int width = screenSize.width;
					frame.setSize(width, height);
					frame.setLocationRelativeTo(null);
					frame.getContentPane().add(pane);
					frame.setVisible(true);
					frame.getContentPane().validate();
					//frame.pack();
					frame.repaint();
				 }
			}
		});

		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				clearLayout();
			}
		});

		sectionPanel = new JPanel();
		sectionPanel.setLayout(new FlowLayout());

		JPanel panel1 = new JPanel();
		panel1.setLayout(new FlowLayout());
		panel1.add(lblCardNumber);
		panel1.add(txtCardNumber);
		panel1.setBackground(new Color(225, 225, 128));
		
		JPanel panel2 = new JPanel();
		panel2.setLayout(new FlowLayout());
		panel2.add(lblPassword);
		panel2.add(txtPassword);
		panel2.setBackground(new Color(225, 225, 128));
		
		JPanel panel3 = new JPanel();
		panel3.setLayout(new FlowLayout());
		panel3.add(btnSubmit);
		panel3.add(btnClear);
		panel3.setBackground(new Color(225, 225, 128));
		
		cardSectionPanel.add(panel1);
		cardSectionPanel.add(panel2);
		cardSectionPanel.add(panel3);
		cardSectionPanel.setBackground(new Color(225, 225, 128));

		cardSectionPanel.setLayout(new GridLayout(3, 1));

		sectionPanel.add(cardSectionPanel);
		sectionPanel.setBackground(new Color(225, 225, 128));
		return sectionPanel;
	}

	private void clearLayout() {
		txtCardNumber.setText("");
		txtPassword.setText("");
	}
}