package csc.control;

import java.sql.SQLException;

import csc.entity.CampusCard;
import csc.entity.CampusCardUser;
import csc.entity.CardUserPreferences;

public class CampusCardUserProfile {
	
	public static void getUserProfileInfo() throws SQLException {
		CardUserPreferences.getUserPreferencesFromDB(CampusCardUser.getcampusCardUserId());
	}
	
	public static void getPurchaseDetailsOfUser() throws SQLException {
		CardUserPreferences.getPurchaseDetailsFromDB(CampusCardUser.getcampusCardUserId());
	}
	
	public static int updateMonthlyExpenseLimit(double newValue) throws SQLException {
		
		int status = 0;		
		
		status = CardUserPreferences.updateMonthlyExpenseLimitInDB(CampusCardUser.getcampusCardUserId(), newValue);
		CardUserPreferences.setMoneyLeft(CardUserPreferences.getMoneySpentTillDate());
		
		if(newValue < CardUserPreferences.getMoneySpentTillDate()) {
			status = 2; 
			
			if(newValue == 0) {
				status = 3;
			}
			CardUserPreferences.setMoneyLeft(0);
		}	
		return status;
	}
	
	public static int updateDailyCalorieLimit(int newValue) throws SQLException {
		int status = 0;
		
		status = CardUserPreferences.updateDailyCalorieLimitInDB(CampusCardUser.getcampusCardUserId(), newValue);
		CardUserPreferences.setCaloriesLeft(CardUserPreferences.getCalorieIntakeTillDate());
		System.out.println(CardUserPreferences.getCalorieIntakeTillDate());
		if(newValue < CardUserPreferences.getCalorieIntakeTillDate()) {
			status = 2; 
			
			if(newValue == 0) {
				status = 3;
			}
			CardUserPreferences.setCaloriesLeft(0);
		}	
		
		return status;
	}
	
	public static int updateDietaryRestrictions(String restriction, String lowCarb, String lowFat, String lowSodium, String veg,
			String nonVeg, String vegan) throws SQLException {
		
		int status = CardUserPreferences.updateDietaryRestrictionsInDB(CampusCardUser.getcampusCardUserId(), restriction, lowCarb, lowFat, lowSodium, veg, nonVeg, vegan);
		return status;
	}	
	
	public static String checkDouble(String newValue) {
		String status = "";
		
		String regEx = "^[0-9]+.??[0-9]{1,2}?$";
		if (newValue.matches(regEx) == false) {
			status = "Invalid value. Please try again.";
		}
		
		return status;
	}
	
	public static String checkInt(String newValue) {
		String status = "";
		
		String regEx = "^[0-9]+$";
		if (newValue.matches(regEx) == false) {
			status = "Invalid value. Please try again.";
		}
		
		return status;
	}
	
	private static String[] splitData(String temp) {
		 return temp.split(", ");
	}
}



