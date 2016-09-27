package csc.entity;

import csc.control.FoodItemCalorieController;

public class FoodItem {
	
	// Private Data Members
	private int itemId;
	private String itemName;
	private double itemPrice;
	private int itemQuantity;
	private int cookingTime;
	String veg;
	String nonVeg;
	String vegan;
	// private FoodItemCalorieProfile calorieProfile;
	private FoodItemCalorieController calorieProfile;
	// private FoodType foodType;
	/*
	 * private enum FoodType { VEGAN, VEG, NON_VEG };
	 */

	// Public Constructor
	public FoodItem(int itemId, String name, double price, int itemQuantity, int cookingTime,
			FoodItemCalorieController calorieProfile, String veg, String nonVeg, String vegan) {

		setItemId(itemId);
		setItemName(name);
		setItemPrice(price);
		setItemQuantity(itemQuantity);
		setCookingTime(cookingTime);
		this.calorieProfile = calorieProfile;
		setVeg(veg);
		setNonVeg(nonVeg);
		setVegan(vegan);

		// this.foodType = foodType;
	}

	public String getVeg() {
		return this.veg;
	}

	public String getNonVeg() {
		return this.nonVeg;
	}

	public String getVegan() {
		return this.vegan;
	}

	public String setVeg(String veg) {
		return this.veg = veg;
	}

	public String setNonVeg(String nonVeg) {
		return this.nonVeg = nonVeg;
	}

	public String setVegan(String vegan) {
		return this.vegan = vegan;
	}

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	
	// Getter for "itemName"
	public String getItemName() {
		return itemName;
	}
	
	// Getter for "itemQuantity"
	public int getItemQuantity() {
		return itemQuantity;
	}
	
	// Getter for "itemPrice"
	public double getItemPrice() {
		return itemPrice;
	}
	
	// Getter for "cookingTime"
	public int getCookingTime() {
		return cookingTime;
	}
	
	// Getter for "calorieProfile"
	public FoodItemCalorieController getcalorieProfile() {
		return calorieProfile;
	}

	/*
	 * // Getter for "foodType" public FoodType getFoodType() { return foodType;
	 * }
	 */

	// Setter for "itemName"
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	// Setter for "itemPrice"
	public void setItemPrice(double price) {
		this.itemPrice = price;
	}
	
	// Setter for "itemQuantity"
	public void setItemQuantity(int itemQuantity) {
		this.itemQuantity = itemQuantity;
	}
	
	// Setter for "cookingTime"
	public void setCookingTime(int cookingTime) {
		this.cookingTime = cookingTime;
	}
	
	// Setter for "calorieProfile"
	public void setcalorieProfile(FoodItemCalorieController calorieProfile) {
		this.calorieProfile = calorieProfile;
	}

	/*
	 * // Setter for "foodType" public void setFoodType(FoodType foodType) {
	 * this.foodType = foodType; }
	 */
}
