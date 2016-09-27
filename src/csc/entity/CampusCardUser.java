package csc.entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import csc.datalayer.DataBaseConnection;

public class CampusCardUser {
	
	// Private Data Members
	private static String name;   
	private static String email;	 
	private static String address;	
	private static int campusCardUserId;
	private static CampusCard card;	
	private static CardUserPreferences preferences;
	
	// Getter for "name"
	public static String getName() {
		return name;
	}
	
	// Getter for "email"
	public static String getEmail() {
		return email;
	}
	
	// Getter for "address"
	public static String getAddress() {
		return address;
	}
	
	// Getter for "card"
	public static CampusCard getCard() {
		return card;
	}
	
	// Getter for "preferences"
	public static CardUserPreferences getPreferences() {
		return preferences;
	}
	
	// Getter for "campusCardUserId"
	public static int getcampusCardUserId() {
		return campusCardUserId;
	}
	
	// Setter for "campusCardUserId"
	public static void setCampusCardUserId(int userId) {
		campusCardUserId = userId;
	}
	
	// Setter for "name"
	public static void setName(String userName) {
		name = userName;
	}
	
	// Setter for "email"
	public static void setEmail(String userEmail) {
		email = userEmail;
	}
	
	// Setter for "address"
	public static void setAddress(String userAddress) {
		address = userAddress;
	}
	
	// Setter for "card"
	public static void setCard(CampusCard userCard) {
		card = userCard;
	}
	
	// Setter for "preferences"
	public static void setPreferences(CardUserPreferences userPreferences) {
		preferences = userPreferences;
	}
	
	public static void getUserInfoFromDB(int userID) throws SQLException {
		
		String query = "select name, email, address from CampusCardUser where User_ID='" + userID + "'";
		Connection con = null;
		Statement stmt = null;
		ResultSet resultSet = null;
		String nameFromDB = "", emailFromDB = "", addressFromDB = "";
	
		try {
			con = new DataBaseConnection().openConnection();

			stmt = con.createStatement();

			PreparedStatement pre = con.prepareStatement(query);
			resultSet = pre.executeQuery();	
				
			while(resultSet.next()) {
				nameFromDB = resultSet.getString("name");
				emailFromDB = resultSet.getString("email");
				addressFromDB = resultSet.getString("address");
			}
			resultSet.close();
			
			setName(nameFromDB);        //may need to change this.
			setEmail(emailFromDB);
			setAddress(addressFromDB);
			
		} catch (SQLException e) {
			//e.printStackTrace();
			System.out.println("SQL Exception6" + e.getSQLState() + " " + e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println("Cannot find the database driver");
		} finally {

			stmt.close();
			con.close();
		}		
	}	
}
