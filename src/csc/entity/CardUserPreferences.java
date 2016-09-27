package csc.entity;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import csc.datalayer.DataBaseConnection;

public class CardUserPreferences {	

	// Private Data Members
	private static double monthlyExpenseLimit;
	private static double moneySpentTillDate;
	private static double moneyLeft;
	private static int dailyCaloriesLimit;
	private static int calorieIntakeTillDate;
	private static int caloriesLeft;
	private static ArrayList<String> dietaryRestrictions = new ArrayList<String>();
	private static ArrayList<Integer> caloriesList = new ArrayList<Integer>();
	private static ArrayList<Double> moneyList = new ArrayList<Double>();
	private static ArrayList<String> dateList = new ArrayList<String>();
	private static final int FAIL = 0;
	private static final int SUCCESS = 1;
	private static String dietRestrictionFlag;
	public static HashMap<String, Integer> calorieListHashMap = new HashMap<String, Integer>();
	public static HashMap<String, Integer> moneyListHashMap = new HashMap<String, Integer>();
	

	public static HashMap<String, Integer> getMoneyListHashMap() {
		return moneyListHashMap;
	}

	public static HashMap<String, Integer> getCalorieListHashMap() {
		return calorieListHashMap;
	}

	// Getter for "monthlyExpenseLimit"
	public static double getMonthlyExpenseLimit() {
		return monthlyExpenseLimit;
	}
	
	// Getter for "moneySpentTillDate"
	public static double getMoneySpentTillDate() {
		return moneySpentTillDate;
	}
	
	// Getter for "moneyLeft"
	public static double getMoneyLeft() {
		return moneyLeft;
	}
	
	// Getter for "dailyCaloriesLimit"
	public static int getDailyCaloriesLimit() {
		return dailyCaloriesLimit;
	}
	
	// Getter for "calorieIntakeTillDate"
	public static int getCalorieIntakeTillDate() {
		return calorieIntakeTillDate;
	}
	
	// Getter for "caloriesLeft"
	public static int getCaloriesLeft() {
		return caloriesLeft;
	}
	
	// Getter for "dietaryRestrictions"
	public static ArrayList<String> getDietaryRestrictions() {
		return dietaryRestrictions;
	}
	
	// Getter for "caloriesList"
	public static ArrayList<Integer> getCaloriesList() {
		return caloriesList;
	}
	// Getter for "moneyList"
	public static ArrayList<Double> getMoneyList() {
		return moneyList;
	}
	// Getter for "dateList"
	public static ArrayList<String> getDateList() {
		return dateList;
	}
	
	// Setter for "monthlyExpenseLimit"
	public static void setMonthlyExpenseLimit(double expenseLimit) {
		monthlyExpenseLimit = expenseLimit;
	}
	
	// Setter for "moneySpentTillDate"
	public static void setMoneySpentTillDate(double moneySpent) {
		moneySpentTillDate = moneySpent;
	}
	
	// Setter for "moneyLeft"
	public static void setMoneyLeft(double moneySpent) {
		moneyLeft = monthlyExpenseLimit - moneySpent;
	}
		
	// Setter for "dailyCaloriesLimit"
	public static void setDailyCaloriesLimit(int dailyCalories) {
		dailyCaloriesLimit = dailyCalories;
	}
	
	// Setter for "calorieIntakeTillDate"
	public static void setCalorieIntakeTillDate(int calorieIntake) {
		calorieIntakeTillDate = calorieIntake;
	}
	
	// Setter for "caloriesLeft"
	public static void setCaloriesLeft(int calorieIntake) {
		caloriesLeft = dailyCaloriesLimit - calorieIntake;
	}
	
	public static String getDietRestrictionFlag() {
		return dietRestrictionFlag;
	}

	public static void setDietRestrictionFlag(String dietRestrictionFlag) {
		CardUserPreferences.dietRestrictionFlag = dietRestrictionFlag;
	}
	
	// Add a new dietaryRestriction
	public static void addDietaryRestriction(String newRestriction) {
		//System.out.println(newRestriction);
		dietaryRestrictions.add(newRestriction);
	}
	
	
	public static int updateDietaryRestrictionsInDB(int userID, String restriction, String lowCarb, String lowFat, String lowSodium, String veg, 
			String nonVeg, String vegan) throws SQLException {
				
		String query1 = "update CardUserPreferences set dietary_restriction = '" + restriction + "' where User_ID='" + userID + "'";
		String query2 = "update diet_restriction set low_carb = '" + lowCarb + "' , low_fat = '" + lowFat + "' , low_sodium = '" + lowSodium +
				"' , veg = '" + veg + "' , non_veg = '" + nonVeg + "' , vegan = '" + vegan + "' where User_ID='" + userID + "'";
		Connection con = null;
		Statement stmt = null;
		int status = FAIL;
	
		try {			
			con = new DataBaseConnection().openConnection();

			stmt = con.createStatement();

			PreparedStatement pre1 = con.prepareStatement(query1);
			pre1.executeUpdate();	
			PreparedStatement pre2 = con.prepareStatement(query2);
			pre2.executeUpdate();
			status = SUCCESS;
			
		} catch (SQLException e) {
			//e.printStackTrace();
			System.out.println("SQL Exception10" + e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println("Cannot find the database driver");
		} finally {

			stmt.close();
			con.close();
		}
		return status;
	}
	
	public static int updateDailyCalorieLimitInDB(int userID, int newLimit) throws SQLException {
		
		String query = "update CardUserPreferences set daily_calories = '" + newLimit + "' where User_ID='" + userID + "'";
		Connection con = null;
		Statement stmt = null;
		int status = FAIL;
	
		try {
			setDailyCaloriesLimit(newLimit);
			setCaloriesLeft(getCalorieIntakeTillDate());			
			
			con = new DataBaseConnection().openConnection();

			stmt = con.createStatement();

			PreparedStatement pre = con.prepareStatement(query);
			pre.executeUpdate();				
			status = SUCCESS;
			
		} catch (SQLException e) {
			//e.printStackTrace();
			System.out.println("SQL Exception10" + e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println("Cannot find the database driver");
		} finally {

			stmt.close();
			con.close();
		}
		return status;
	}
	
	public static int updateMonthlyExpenseLimitInDB(int userID, double newLimit) throws SQLException {
		
		String query = "update CardUserPreferences set monthly_expense = '" + newLimit + "' where User_ID='" + userID + "'";
		Connection con = null;
		Statement stmt = null;
		int status = FAIL;
	
		try {
			setMonthlyExpenseLimit(newLimit);
			setMoneyLeft(getMoneySpentTillDate());			
			
			con = new DataBaseConnection().openConnection();

			stmt = con.createStatement();

			PreparedStatement pre = con.prepareStatement(query);
			pre.executeUpdate();				
			status = SUCCESS;
			
		} catch (SQLException e) {
			//e.printStackTrace();
			System.out.println("SQL Exception10" + e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println("Cannot find the database driver");
		} finally {

			stmt.close();
			con.close();
		}
		return status;
	}
	
	public static void getUserPreferencesFromDB(int userID) throws SQLException {
		
		String query = "select monthly_expense, money_spent, daily_calories, daily_calories_spent, dietary_restriction from CardUserPreferences where User_ID='" + userID + "'";
		Connection con = null;
		Statement stmt = null;
		ResultSet resultSet = null;
		String monthlyExpenseFromDB = "", moneySpentFromDB = "", dailyCaloriesFromDB = "", calorieSpentFromDB = "", dietaryRestrictionFromDB = "";
	
		try {
			con = new DataBaseConnection().openConnection();

			stmt = con.createStatement();

			PreparedStatement pre = con.prepareStatement(query);
			resultSet = pre.executeQuery();				

			while(resultSet.next()) {
				monthlyExpenseFromDB = resultSet.getString("monthly_expense");
				moneySpentFromDB = resultSet.getString("money_spent");
				dailyCaloriesFromDB = resultSet.getString("daily_calories");
				calorieSpentFromDB = resultSet.getString("daily_calories_spent");
				dietaryRestrictionFromDB = resultSet.getString("dietary_restriction");	
			}
			resultSet.close();
			
			setMonthlyExpenseLimit(Double.parseDouble(monthlyExpenseFromDB));
			setDailyCaloriesLimit(Integer.parseInt(dailyCaloriesFromDB));
			setCalorieIntakeTillDate(Integer.parseInt(calorieSpentFromDB));	
			setCaloriesLeft(Integer.parseInt(calorieSpentFromDB));			
			setMoneySpentTillDate(Double.parseDouble(moneySpentFromDB));	
			setMoneyLeft(Double.parseDouble(moneySpentFromDB));						
						
			if(dietaryRestrictionFromDB.equals("Y")) {
				getRestrictionsFromDB(userID);
			}	
			
			setDietRestrictionFlag(dietaryRestrictionFromDB);
			
		} catch (SQLException e) {
			//e.printStackTrace();
			System.out.println("SQL Exception1" + e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println("Cannot find the database driver");
		} finally {

			stmt.close();
			con.close();
		}
	}
	
	private static void getRestrictionsFromDB(int userID) throws SQLException {
	
		String query = "select low_carb, low_fat, low_sodium, veg, non_veg, vegan from diet_restriction where User_ID='" + userID + "'";
		Connection con = null;
		Statement stmt = null;
		ResultSet resultSet = null;
		String temp = "", lowCarb = "", lowFat = "", lowSodium = "", veg = "", nonVeg = "", vegan = "";
	
		try {
			con = new DataBaseConnection().openConnection();
	
			stmt = con.createStatement();
	
			PreparedStatement pre = con.prepareStatement(query);
			resultSet = pre.executeQuery();	
						
			while(resultSet.next()) {
				lowCarb = resultSet.getString("low_carb");
				lowFat = resultSet.getString("low_fat");
				lowSodium = resultSet.getString("low_sodium");
				veg = resultSet.getString("veg");
				nonVeg = resultSet.getString("non_veg");
				vegan = resultSet.getString("vegan");
			}
			resultSet.close();
			
			temp = lowCarb + "!" + lowFat + "!" + lowSodium + "!" + veg + "!" + nonVeg + "!" + vegan; 
			//System.out.println(temp);
			addDietaryRestriction(temp);
			
		} catch (SQLException e) {
			//e.printStackTrace();
			System.out.println("SQL Exception7");
		} catch (ClassNotFoundException e) {
			System.out.println("Cannot find the database driver");
		} finally {
	
			stmt.close();
			con.close();
		}		
	}
	
	public static void getPurchaseDetailsFromDB(int userID) throws SQLException {
		
	//	String query = "select Purchase_total, calories_total, purchase_date from Purchase_detail where User_ID='" + userID + "'";
		String query = "select PURCHASE_DATE, sum(Purchase_total) as PURCHASE_TOTAL, sum(calories_total) as CALORIES_TOTAL "
				+ "from Purchase_detail where User_ID='"+userID+"' group by PURCHASE_DATE order by PURCHASE_DATE desc";
	//	System.out.println(query);
		Connection con = null;
		Statement stmt = null;
		ResultSet resultSet = null;
	
		try {
			con = new DataBaseConnection().openConnection();
	
			stmt = con.createStatement();
	
			PreparedStatement pre = con.prepareStatement(query);
			resultSet = pre.executeQuery();							
			
			while(resultSet.next()) {				
				String date = resultSet.getString("PURCHASE_DATE");
				String [] splitDate = date.split(" ");
				dateList.add(splitDate[0]);				
				String calories = resultSet.getString("CALORIES_TOTAL");
				caloriesList.add(Integer.parseInt(calories));
				String money = resultSet.getString("PURCHASE_TOTAL");
				moneyList.add(Double.parseDouble(money));	
		//		calorieListHashMap.put(splitDate[0], Integer.parseInt(calories));
		//		moneyListHashMap.put(splitDate[0], Integer.parseInt(money));
			}
			resultSet.close();	
			
		} catch (SQLException e) {
			//e.printStackTrace();
			System.out.println("SQL Exception8");
		} catch (ClassNotFoundException e) {
			System.out.println("Cannot find the database driver");
		} finally {
	
			stmt.close();
			con.close();
		}		
	}
}
