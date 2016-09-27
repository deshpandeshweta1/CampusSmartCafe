package csc.gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import csc.control.CampusCardUserProfile;
import csc.entity.CampusCard;
import csc.entity.CampusCardUser;
import csc.entity.CardUserPreferences;

public class Profile extends JPanel{

	JPanel radioBtnPanel;
	JPanel expensePanel;
	JPanel dietaryPanel, inputPanel;
	final JRadioButton btnExpense;
	final JRadioButton btnDietary;
	
	public Profile()
	{		
		try {
			CampusCardUserProfile.getUserProfileInfo();
			CampusCardUserProfile.getPurchaseDetailsOfUser();
		} catch (SQLException e) {
			System.out.println("SQL Exception2");
		}
		this.setBackground(new Color(225, 225, 128));
		radioBtnPanel = new JPanel();
		
		btnExpense = new JRadioButton("Expense Profile");
		btnDietary = new JRadioButton("Dietary Profile");
		btnExpense.setFont(new Font("Serif", Font.PLAIN, 20));
		btnDietary.setFont(new Font("Serif", Font.PLAIN, 20));
		
		ButtonGroup editableGroup = new ButtonGroup();
	    editableGroup.add(btnExpense);
	    editableGroup.add(btnDietary);
	    
	    JPanel dietaryProfile = showDietaryPanel(); 
	    JPanel expenseProfile = showExpensePanel(); 
	    dietaryProfile.setBackground(new Color(225, 225, 128));
	    dietaryProfile.setVisible(false);
	    expenseProfile.setVisible(false);
	    expenseProfile.setBackground(new Color(225, 225, 128));
	    
		btnExpense.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				expenseProfile.setVisible(true);
				dietaryProfile.setVisible(false);		
			}
		});
		
		btnDietary.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				expenseProfile.setVisible(false);
				dietaryProfile.setVisible(true);
			}
		});		
	    
		radioBtnPanel.add(btnDietary);
		radioBtnPanel.add(btnExpense);
		radioBtnPanel.setBackground(new Color(225, 225, 128));
	    
		JPanel temp = new JPanel();
		temp.setLayout(new FlowLayout());
		temp.add(expenseProfile);
		temp.add(dietaryProfile);	
		temp.setBackground(new Color(225, 225, 128));
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));	
		this.add(radioBtnPanel);
		this.add(temp);	
	}	
	
	private JPanel showExpensePanel()
	{
		ExpenseProfile expenseProfile = new ExpenseProfile();
		expensePanel = expenseProfile.displayUserExpenseProfile();
		expensePanel.setBackground(new Color(225, 225, 128));
		return expensePanel;
	}
	
	private JPanel showDietaryPanel()
	{
		DietaryProfile dietaryProfile = new DietaryProfile(); 
		dietaryPanel = dietaryProfile.displayUserDietaryProfile();
		dietaryPanel.setBackground(new Color(225, 225, 128));
		return dietaryPanel;
	}
	
}
