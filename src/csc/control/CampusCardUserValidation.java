package csc.control;

import csc.entity.CampusCard;
import csc.entity.CampusCardUser;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CampusCardUserValidation {

	private String enteredCardNumber;   // can have all chars and digits (no special characters)
	private String enteredPassword;
	private static final int NOT_FOUND = 0;
	private static final int FOUND = 1;
	
	// Public Constructor
	public CampusCardUserValidation(String enteredCardNumber, String enteredPassword) {
		this.enteredCardNumber = enteredCardNumber;
		this.enteredPassword = enteredPassword;		
	}
	
	public String validateUser() throws SQLException {
		
		String content = "";
		if(enteredCardNumber.isEmpty()) { 
			content = "Please enter your Card Number";
		} else if (enteredPassword.isEmpty()) {
			content = "Please enter your password";
		} else if (enteredCardNumber.length() < 8 || enteredCardNumber.length() > 8) {
			content = "Wrong credentials. Please enter again";
		} else if (enteredPassword.length() < 9 || enteredPassword.length() > 9) {
			content = "Wrong credentials. Please enter again";
		} else {					
			
			ArrayList<String> passwords = CampusCard.getCardInfoFromDB(enteredCardNumber);
			int passwordFound = NOT_FOUND;
			String passwordFromDB;
			int userIDFromDB = 0;
			
			for(int i = 0; i < passwords.size(); i++) {
				String temp = passwords.get(i);
				String [] splitTemp = temp.split(" ");
				passwordFromDB = splitTemp[0];
				userIDFromDB = Integer.parseInt(splitTemp[1]);
				
				if (passwordFromDB.equals(enteredPassword)) {
					passwordFound = FOUND;
				}				
			}
			
			if (passwordFound == FOUND) {
				
				CampusCard.setCardNumber(enteredCardNumber);
				CampusCard.setPassword(enteredPassword);				
				
				CampusCardUser.getUserInfoFromDB(userIDFromDB);					
				//CampusCardUser.setCard(CampusCard);
				CampusCardUser.setCampusCardUserId(userIDFromDB);
				
			} else {
				content = "Wrong credentials. Please enter again";
			}
		}
		
		return content;
	}
}
