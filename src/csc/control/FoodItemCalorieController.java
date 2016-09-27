package csc.control;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import csc.datalayer.DataBaseConnection;
import csc.entity.FoodItemCalorieProfile;

public class FoodItemCalorieController {

	int fatContent = 0;
	int sodiumContent= 0;
	int carbContent = 0;
	int proteinContent =0;
	private FoodItemCalorieProfile foodItemCalorieProfileEntity;
	
	public FoodItemCalorieController (int foodItemId) throws SQLException
	{
		getCalorieContentFromDB(foodItemId);
		foodItemCalorieProfileEntity = new FoodItemCalorieProfile(fatContent,carbContent, sodiumContent,proteinContent);
	}

	public void getCalorieContentFromDB(int foodItemId) throws SQLException {

		String query = "select fat, sodium, carbohydrates, protien from FOODITEMCALORIEPROFILE where FOODITEM_ID = " + foodItemId;
		//System.out.println(query);
		Connection con = null;
		Statement stmt = null;
		ResultSet resultSet = null;

		try {
			con = new DataBaseConnection().openConnection();

			stmt = con.createStatement();

			PreparedStatement pre = con.prepareStatement(query);
			resultSet = pre.executeQuery();

			while (resultSet.next()) {
				 fatContent = resultSet.getInt("fat");
			 sodiumContent= resultSet.getInt("sodium");
				 carbContent = resultSet.getInt("carbohydrates");
				 proteinContent = resultSet.getInt("protien");
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
	}
	
	
	public int getTotalCalories() {
		return foodItemCalorieProfileEntity.getTotalCalories();
	}
	
	public int getSodiumContent() {
		return foodItemCalorieProfileEntity.getSodiumContent();
	}
	
	public int getCarbContent() {
		return foodItemCalorieProfileEntity.getCarbContent();
	}
	
	public int getFatContent() {
		return foodItemCalorieProfileEntity.getFatContent();
	}
}
