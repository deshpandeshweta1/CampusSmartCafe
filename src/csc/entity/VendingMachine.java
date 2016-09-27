package csc.entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import csc.datalayer.DataBaseConnection;

public class VendingMachine implements FoodStore {

	private SelectedFoodItemsSession session;
	private List<FoodItem> bindFoodItemsList;
	String storeId = null;
	String storeName = null;

	// Public Constructor
	public VendingMachine(List<FoodItem> bindFoodItemsList, SelectedFoodItemsSession session, String id, String selectedVendingMachineName) {
		setFoodItemsList(bindFoodItemsList);
		setSession(session);
		setStoreId(id);
		setstoreName(selectedVendingMachineName);
	}

	@Override
	public String getStoreId() {
	return storeId;	
	}

	@Override
	public String getstoreName() {
		return storeName;
	}

	@Override
	public void setStoreId(String id) {
	
		this.storeId = id;
	}

	@Override
	public void setstoreName(String storeName) {
		this.storeName = storeName;
		
	}


	@Override
	public List<FoodItem> getFoodItemsList() {
		return bindFoodItemsList;
	}

	@Override
	public void setFoodItemsList(List<FoodItem> bindFoodItemsList) {
		this.bindFoodItemsList = bindFoodItemsList;
	}

	public SelectedFoodItemsSession getSession() {
		return session;
	}

	public void setSession(SelectedFoodItemsSession session) {
		this.session = session;
	}

	@Override
	public void updateFoodItemQty(SelectedFoodItemsSession session) throws SQLException {
		Set set = session.hmap.entrySet();
		Iterator iterator = set.iterator();
		while (iterator.hasNext()) {
			Map.Entry entry = (Map.Entry) iterator.next();
			int itemId = Integer.parseInt(entry.getKey().toString());
			int itemTotalSelectedQty = Integer.parseInt(entry.getValue().toString());

			if (bindFoodItemsList != null) {
				for (FoodItem item : bindFoodItemsList) {
					if (item.getItemId() == itemId) {
						// int newQty = item.getItemQuantity() -
						// itemTotalSelectedQty;
						updateFoodItemQtyIntoDB(itemId, item.getItemQuantity());
					}
				}
			}
		}
	}

	public void updateFoodItemQtyIntoDB(int fooditem_ID, int quantity) throws SQLException {
		Connection con = null;
		Statement stmt = null;

		try {
			con = new DataBaseConnection().openConnection();

			stmt = con.createStatement();

			String updateTableSQL = "UPDATE FoodItem SET quantity = ? WHERE fooditem_ID = ?";
			PreparedStatement preparedStatement = con.prepareStatement(updateTableSQL);
			preparedStatement.setInt(1, quantity);
			preparedStatement.setInt(2, fooditem_ID);
			preparedStatement.executeUpdate();
			preparedStatement.close();

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

	public void updateCaloriesSpentIntoDB(int user_ID, int newCalorieIntakeTillDate) throws SQLException {
		Connection con = null;
		Statement stmt = null;

		try {
			con = new DataBaseConnection().openConnection();

			stmt = con.createStatement();

			String updateCalories = "UPDATE CardUserPreferences SET daily_calories_spent = ? WHERE User_ID = ?";

			PreparedStatement preparedStatement1 = con.prepareStatement(updateCalories);
			preparedStatement1.setInt(1, newCalorieIntakeTillDate);
			preparedStatement1.setInt(2, user_ID);
			preparedStatement1.executeUpdate();
			preparedStatement1.close();

			//System.out.println(updateCalories);

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

	public void updateMoneySpentIntoDB(int user_ID, double newMoneySpentTillDate) throws SQLException {
		Connection con = null;
		Statement stmt = null;

		try {
			con = new DataBaseConnection().openConnection();

			stmt = con.createStatement();

			// String updateTableSQL = "UPDATE CardUserPreferences SET
			// money_spent = ? , daily_calories_spent = ? WHERE User_ID = ?";
			String updateMoney = "UPDATE CardUserPreferences SET money_spent = ? WHERE User_ID = ?";
			System.out.println(updateMoney);
			PreparedStatement preparedStatement = con.prepareStatement(updateMoney);
			preparedStatement.setFloat(1, (float) newMoneySpentTillDate);
			preparedStatement.setInt(2, user_ID);
			preparedStatement.executeUpdate();
			preparedStatement.close();

			//System.out.println(updateMoney);
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

	

}
