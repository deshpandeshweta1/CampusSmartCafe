package csc.control;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

import csc.datalayer.DataBaseConnection;
import csc.entity.CampusCardUser;
import csc.entity.CardUserPreferences;
import csc.entity.FoodItem;
import csc.entity.SelectedFoodItemsSession;
import csc.entity.VendingMachine;

public class VendingMachineController {

	private List<FoodItem> bindFoodItemsList;
	private FoodItem foodItem;
	private SelectedFoodItemsSession session;
	private FoodItemCalorieController foodItemCalories;
	private VendingMachine vendingMachineEntity;
	Boolean flag=false;

	public VendingMachineController(String selectedVendingMachineName) throws Exception {

		bindFoodItemsList = new ArrayList<FoodItem>();
		session = new SelectedFoodItemsSession(selectedVendingMachineName);
		String id = getVendingMachineIDFromDB(selectedVendingMachineName);
		vendingMachineEntity = new VendingMachine(bindFoodItemsList, session, id, selectedVendingMachineName);
		CampusCardUserProfile.getUserProfileInfo();

	}

	public String getVendingMachineIDFromDB(String vendingMachineName) throws SQLException {

		String query = "select VendingMachine_ID from VendingMachine where name='" + vendingMachineName + "'";
		Connection con = null;
		Statement stmt = null;
		ResultSet resultSet = null;
		String VendingMachineId = null;

		try {
			con = new DataBaseConnection().openConnection();

			stmt = con.createStatement();

			PreparedStatement pre = con.prepareStatement(query);
			resultSet = pre.executeQuery();

			while (resultSet.next()) {
				VendingMachineId = resultSet.getString("VendingMachine_ID");
				//System.out.println(VendingMachineId);
			}

			// resultSet.close();

		} catch (SQLException e) {
			// e.printStackTrace();
			System.out.println("SQL Exception");
		} catch (ClassNotFoundException e) {
			System.out.println("Cannot find the database driver");
		} finally {
			stmt.close();
			con.close();
		}
		return VendingMachineId;
	}

	public List<FoodItem> getSelectedVMFoodItemsFromDB(String vendingMachineID) throws SQLException {

		String query = "select f.fooditem_ID, f.name, f.price, f.quantity, f.cooking_time, "
				+ "f.veg ,f.non_veg ,f.vegan " + "from FoodItem f, VendingMachineFoodItems VMf "
				+ "where f.fooditem_ID = VMf.fooditem_ID and VMf.VendingMachine_ID='" + vendingMachineID + "'";

		Connection con = null;
		Statement stmt = null;
		ResultSet resultSet = null;

		try {
			con = new DataBaseConnection().openConnection();

			stmt = con.createStatement();

			PreparedStatement pre = con.prepareStatement(query);
			resultSet = pre.executeQuery();

			bindFoodItemsList.clear();
			while (resultSet.next()) {
				int foodItemId = resultSet.getInt("fooditem_ID");
				String name = resultSet.getString("name");
				double price = resultSet.getDouble("price");
				int quantity = resultSet.getInt("quantity");
				int cookingTime = resultSet.getInt("cooking_time");
				String veg = resultSet.getString("veg");
				String nonVeg = resultSet.getString("non_veg");
				String vegan = resultSet.getString("vegan");

				foodItemCalories = new FoodItemCalorieController(foodItemId);
				foodItem = new FoodItem(foodItemId, name, price, quantity, cookingTime, foodItemCalories, veg, nonVeg,
						vegan);

				bindFoodItemsList.add(foodItem);
			}

			// resultSet.close();

		} catch (SQLException e) {
			 e.printStackTrace();
			//System.out.println("SQL Exception");
		} catch (ClassNotFoundException e) {
			System.out.println("Cannot find the database driver");
		} finally {

			stmt.close();
			con.close();
		}
		return bindFoodItemsList;

	}

	// Add a selected FoodItem into the current session
	public void addFoodItemToSession(int foodItemId, int quantity) {

		session.addFoodItemToSession(foodItemId, quantity);
	}

	// Remove a selected FoodItem from the current session
	public void removeFoodItemFromSession(int foodItemId) {

		session.removeFoodItemFromSession(foodItemId);
	}
	
	// Remove a selected FoodItem from the current session
		public void removeAllFoodItemFromSession() {

			session.removeAllFoodItemFromSession();
		}

	public double calculateTotalCostOfSession(List<FoodItem> foodItemsList) {

		return session.calculateTotalCostOfSession(foodItemsList);
	}

	public int calculateTotalCaloriesOfSession(List<FoodItem> foodItemsList) {

		return session.calculateTotalCaloriesOfSession(foodItemsList);
	}

	public int calculateTotalSodiumContentOfSession(List<FoodItem> foodItemsList) {

		return session.calculateTotalSodiumContentOfSession(foodItemsList);
	}

	public int calculateTotalFatContentOfSession(List<FoodItem> foodItemsList) {

		return session.calculateTotalFatContentOfSession(foodItemsList);
	}

	public int calculateTotalCarbContentOfSession(List<FoodItem> foodItemsList) {

		return session.calculateTotalCarbContentOfSession(foodItemsList);
	}

	public void insertOrderIntoDB(int userId, double purchaseTotal, int caloriesTotal, Date purchaseDate)
			throws SQLException {
		Connection con = null;
		Statement stmt = null;
		int purchaseId = 0;

		int rowCount = getOrderToChkIDFromDB();
		if (rowCount == 0) {
			purchaseId = 1;
		} else {
			purchaseId = rowCount + 1;
		}
		try {
			con = new DataBaseConnection().openConnection();

			stmt = con.createStatement();

			// User_ID integer, Purchase_total number(4,2), calories_total
			// integer,purchase_date date,
			String insertOrderIntoDB = "INSERT INTO Purchase_detail "
					+ "(Purchase_id, User_ID, Purchase_total, calories_total, purchase_date)" + "VALUES(?,?,?,?,?)";

			//System.out.println(insertOrderIntoDB);
			PreparedStatement preparedStmt = con.prepareStatement(insertOrderIntoDB);
			preparedStmt.setInt(1, purchaseId);
			preparedStmt.setInt(2, userId);
			preparedStmt.setFloat(3, (float) purchaseTotal);
			preparedStmt.setInt(4, caloriesTotal);
			preparedStmt.setDate(5, purchaseDate);

			preparedStmt.execute();
			preparedStmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
			// System.out.println("SQL Exception");
		} catch (ClassNotFoundException e) {
			System.out.println("Cannot find the database driver");
		} finally {

			stmt.close();
			con.close();
		}
	}

	public int getOrderToChkIDFromDB() throws SQLException {

		int rowCount = 0;
		String query = "select Purchase_id from Purchase_detail";
		Connection con = null;
		Statement stmt = null;
		ResultSet resultSet = null;

		try {
			con = new DataBaseConnection().openConnection();

			stmt = con.createStatement();

			PreparedStatement pre = con.prepareStatement(query);
			resultSet = pre.executeQuery();

			while (resultSet.next()) {
				int Purchase_id = resultSet.getInt("Purchase_id");
				//System.out.println(Purchase_id);
			 rowCount++;
			}

			//resultSet.close();
			
		} catch (SQLException e) {
			//e.printStackTrace();
			System.out.println("SQL Exception");
		} catch (ClassNotFoundException e) {
			System.out.println("Cannot find the database driver");
		} finally {

			stmt.close();
			con.close();
		}
		return rowCount;
	}

	public void SavePurchaseDetailsToDB() throws SQLException {
		java.util.Date utilDate = new java.util.Date();
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		insertOrderIntoDB(CampusCardUser.getcampusCardUserId(), session.getTotalCostOfSession(),
				session.getTotalCaloriesOfSession(), sqlDate);

	}

	public void  dispenseFoodItemFromVendingMachine() throws SQLException {

		SavePurchaseDetailsToDB();
		vendingMachineEntity.updateFoodItemQty(session);

	}
	
	public boolean btnBuyFoodItem() throws SQLException {
		chkDietaryRestrictions(bindFoodItemsList);
		return flag;
	}

	public void chkMoneyLeft() throws SQLException {

		if (CardUserPreferences.getMoneyLeft() > 0
				&& CardUserPreferences.getMoneyLeft() > session.getTotalCostOfSession()) {
			dispenseFoodItemFromVendingMachine();
			updateMoneyAndCaloriesSpent();
			
			flag = true;
		} else {
			JOptionPane.showMessageDialog(null, "Sorry, your purchase transaction cannot be completed. \n Your Card Balance : $"
					+ CardUserPreferences.getMoneyLeft()+ "\n is less than total cost of transcation.");

			flag=false;
		}
	}

	public void chkCalorieLeft() throws SQLException {
		if (CardUserPreferences.getCaloriesLeft() > 0
				&& CardUserPreferences.getCaloriesLeft() > session.getTotalCaloriesOfSession()) {
			
			chkMoneyLeft();
			
		}else{
			int n = JOptionPane.showConfirmDialog(null,
					"TotalCalories of this purchase exceeds the daily calorie limit. Calories Balance : "+CardUserPreferences.getCaloriesLeft(),
					"If you still want to continue, Please click YES!!", JOptionPane.YES_NO_OPTION);
			if (n == JOptionPane.YES_OPTION) {
				chkMoneyLeft();
				

			} else if (n == JOptionPane.NO_OPTION) {
				flag = false;

			} else {
				flag = false;

			}
		}
	}
	
	private void updateMoneyAndCaloriesSpent() throws SQLException {
		double newMoneySpentTillDate = CardUserPreferences.getMoneySpentTillDate() + session.getTotalCostOfSession();
		int newCalorieIntakeTillDate = CardUserPreferences.getCalorieIntakeTillDate() + session.getTotalCaloriesOfSession();
		
		vendingMachineEntity.updateMoneySpentIntoDB(CampusCardUser.getcampusCardUserId(), newMoneySpentTillDate);
		vendingMachineEntity.updateCaloriesSpentIntoDB(CampusCardUser.getcampusCardUserId(), newCalorieIntakeTillDate);
	}

	public void chkDietaryRestrictions(List<FoodItem> foodItemsList) throws SQLException {
		if (CardUserPreferences.getDietRestrictionFlag().equals("Y")) {
			// CardUserPreferences.getDietaryRestrictions();

			String restrictionText = "Total Contents details are as follows: \n";

			String[] temp = splitRestrictions();
			boolean shouldPrint = false;
			String getRestrictions = "";
			if (temp != null) {
				getRestrictions = getRestrictions(temp);

				if (getRestrictions.equals("None") == false) {
					String[] splitTemp = splitRestrictions();
					if (splitTemp[0].equals("Y")) {
						// lowCarb.setSelected(true);
						int carbTotal = calculateTotalCarbContentOfSession(foodItemsList);
						if (carbTotal > 5) {
							restrictionText = restrictionText.concat("Carb% : " + String.valueOf(carbTotal));
							shouldPrint = true;
						}
					}
					if (splitTemp[1].equals("Y")) {
						// lowFat.setSelected(true);
						int fatTotal = calculateTotalFatContentOfSession(foodItemsList);
						if (fatTotal > 5) {
							restrictionText = restrictionText.concat("\n Fat% : " + String.valueOf(fatTotal));
							shouldPrint = true;
						}

					}
					if (splitTemp[2].equals("Y")) {
						// lowSodium.setSelected(true);

						int sodiumTotal = calculateTotalSodiumContentOfSession(foodItemsList);
						if (sodiumTotal > 5) {
							restrictionText = restrictionText
									.concat("\n Sodium% : " + String.valueOf(sodiumTotal));
							shouldPrint = true;
						}
					}
					if (splitTemp[5].equals("Y")) {
						 boolean veg =	session.chkContainsVeg(foodItemsList);
						 boolean nonVeg = session.chkContainsVeg(foodItemsList);
						 if(veg == true || nonVeg == true)
						 {
							 restrictionText = restrictionText
										.concat("\n Also have Non Vegan foodItem");
							 shouldPrint = true;
						 }
					}

					if (shouldPrint == true) {
						String alert = "****************************************Alert****************************************";

						int n = JOptionPane.showConfirmDialog(null,
								"Some selected fooditems matchs against your diet restrictions!! \n \n" + restrictionText
										+ "\n \n If you still want to continue, Please click YES!!",
								alert, JOptionPane.YES_NO_OPTION);
						if (n == JOptionPane.YES_OPTION) {
							chkCalorieLeft();

						} else if (n == JOptionPane.NO_OPTION) {

							flag = false;
						}

					}
					else {
						chkCalorieLeft();
						//flag = false;
					}
				}
				else {
					chkCalorieLeft();
				}
			}
		} else {
			chkCalorieLeft();
		}
	}

	private String[] splitData(String temp) {
		return temp.split("!");
	}

	private String[] splitRestrictions() {

		ArrayList<String> restrictions = CardUserPreferences.getDietaryRestrictions();
		String[] splitTemp = null;
		// System.out.println(restrictions);

		if (restrictions != null) {
			for (int i = 0; i < restrictions.size(); i++) {
				// System.out.println(restrictions);
				String temp = restrictions.get(i);
				splitTemp = splitData(temp);
			}
		}
		return splitTemp;
	}

	private String getRestrictions(String[] splitTemp) {

		String returnString = "";

		if (splitTemp[0].equals("Y")) {
			returnString = returnString.concat("Low Carb, ");
		}
		if (splitTemp[1].equals("Y")) {
			returnString = returnString.concat("Low Fat, ");
		}
		if (splitTemp[2].equals("Y")) {
			returnString = returnString.concat("Low Sodium, ");
		}
		if (splitTemp[3].equals("Y")) {
			returnString = returnString.concat("Veg , ");
		}
		if (splitTemp[4].equals("Y")) {
			returnString = returnString.concat("Non Veg, ");
		}
		if (splitTemp[5].equals("Y")) {
			returnString = returnString.concat("Vegan, ");
		}
		returnString = returnString.substring(0, returnString.length() - 2);
		return returnString;
	}

}
