package csc.entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import csc.datalayer.DataBaseConnection;

public class CampusCard {

	// Private Data Members
	private static String cardNumber;   // can have all chars and digits (no special characters)
	private static String password;	 // can have all chars and digits (no special characters)
	
	// Public Constructor
	//public CampusCard() { }
	
	// Getter for "cardNumber"
	public static String getCardNumber() {
		return cardNumber;
	}
	
	// Getter for "password"
	public static String getPassword() {
		return password;
	}
	
	// Setter for "cardNumber"
	public static void setCardNumber(String userCardNumber) {
		cardNumber = userCardNumber;
	}
	
	// Setter for "password"
	public static void setPassword(String userPassword) {
		password = userPassword;
	}
	
	public static ArrayList<String> getCardInfoFromDB(String card_number) throws SQLException {
		
		String query = "select password,User_ID from CampusCard where card_number='" + card_number + "'";
		Connection con = null;
		Statement stmt = null;
		ResultSet resultSet = null;
		ArrayList<String> passwordSet = new ArrayList<String>();

		try {
			con = new DataBaseConnection().openConnection();

			stmt = con.createStatement();

			PreparedStatement pre = con.prepareStatement(query);
			resultSet = pre.executeQuery();		

			while (resultSet.next()) {
				String passwordFromDB = resultSet.getString("password");
				String User_ID = resultSet.getString("User_ID");
				String temp = passwordFromDB + " " + User_ID;
				passwordSet.add(temp);
			}
			
			resultSet.close();
			
		} catch (SQLException e) {
			//e.printStackTrace();
			System.out.println("SQL Exception5" + e.getMessage());
		} catch (ClassNotFoundException e) {
			System.out.println("Cannot find the database driver");
		} finally {

			stmt.close();
			con.close();
		}
		return passwordSet;
	}

}
