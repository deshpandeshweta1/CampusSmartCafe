package csc.entity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SelectedFoodItemsSession {
	// Private Data Members
	private List<FoodItem> foodItemsList;
	private double totalCostOfSession;
	private int totalCookingTimeOfSession, totalCaloriesOfSession;
	private String storeName;
	private static final int NOT_FOUND = -1;
	private static final double CALIFORNIA_TAX = 0.075;
	public static HashMap<Integer, Integer> hmap = new HashMap<Integer, Integer>();

	public SelectedFoodItemsSession(String storeName) {
		this.storeName = storeName;
	}
	
	public List<FoodItem> getSelectedFoodItemsList() {
		return this.foodItemsList;
	}

	// Getter for "totalCostOfSession"
	public double getTotalCostOfSession() {
		return totalCostOfSession;
	}

	// Getter for "totalCaloriesOfSession"
	public int getTotalCaloriesOfSession() {
		return totalCaloriesOfSession;
	}

	// Getter for "totalCostOfSession"
	public void setTotalCostOfSession(double sessionCost) {
		this.totalCostOfSession = sessionCost;
	}

	// Getter for "totalCaloriesOfSession"
	public void setTotalCaloriesOfSession(int sessionCalories) {
		this.totalCaloriesOfSession = sessionCalories;
	}

	// Getter for "storeId"
	public String getStoreName() {
		return storeName;
	}

	// Add a selected FoodItem into the current session
	public void addFoodItemToSession(int foodItemId, int quantity) {

		hmap.put(foodItemId, quantity);
	}

	// Remove a selected FoodItem from the current session
	public void removeFoodItemFromSession(int foodItemId) {

		hmap.remove(foodItemId);
		System.out.println(hmap.remove(foodItemId));
	}

	// Remove a selected FoodItem from the current session
	public void removeAllFoodItemFromSession() {

		hmap.clear();
	}

	// Calculate "totalCostOfSession"
	public double calculateTotalCostOfSession(List<FoodItem> foodItemsList) {
		double costOfSession = 0;

		Set set = hmap.entrySet();
		Iterator iterator = set.iterator();
		while (iterator.hasNext()) {
			Map.Entry entry = (Map.Entry) iterator.next();
			int itemId = Integer.parseInt(entry.getKey().toString());
			int itemTotalSelectedQty = Integer.parseInt(entry.getValue().toString());

			if (foodItemsList != null) {
				for (FoodItem item : foodItemsList) {
					if (item.getItemId() == itemId) {
						double itemUnitPrice = item.getItemPrice();
						double costOfItem = itemUnitPrice * itemTotalSelectedQty;
						costOfSession += costOfItem;
					}
				}
			}
		}

		setTotalCostOfSession(costOfSession);

		return costOfSession;
	}

	// Calculate "totalCaloriesOfSession"
	public int calculateTotalCaloriesOfSession(List<FoodItem> foodItemsList) {

		int totalCaloriesOfSession = 0;

		Set set = hmap.entrySet();
		Iterator iterator = set.iterator();
		while (iterator.hasNext()) {
			Map.Entry entry = (Map.Entry) iterator.next();
			int itemId = Integer.parseInt(entry.getKey().toString());
			int itemTotalSelectedQty = Integer.parseInt(entry.getValue().toString());

			if (foodItemsList != null) {
				for (FoodItem item : foodItemsList) {
					if (item.getItemId() == itemId) {
						int itemTotalCalories = item.getcalorieProfile().getTotalCalories();
						int totalSessionCalories = itemTotalCalories * itemTotalSelectedQty;
						totalCaloriesOfSession += totalSessionCalories;
					}
				}
			}
		}

		setTotalCaloriesOfSession(totalCaloriesOfSession);
		System.out.println(totalCaloriesOfSession);

		return totalCaloriesOfSession;
	}

	// Calculate "totalSodiumContentOfSession"
	public int calculateTotalSodiumContentOfSession(List<FoodItem> foodItemsList) {

		int totalSodiumContentOfSession = 0;

		Set set = hmap.entrySet();
		Iterator iterator = set.iterator();
		while (iterator.hasNext()) {
			Map.Entry entry = (Map.Entry) iterator.next();
			int itemId = Integer.parseInt(entry.getKey().toString());
			int itemTotalSelectedQty = Integer.parseInt(entry.getValue().toString());

			if (foodItemsList != null) {
				for (FoodItem item : foodItemsList) {
					if (item.getItemId() == itemId) {
						int itemSodium = item.getcalorieProfile().getSodiumContent();
						int totalSessionSodiumContent = itemSodium * itemTotalSelectedQty;
						totalSodiumContentOfSession += totalSessionSodiumContent;
					}
				}
			}
		}

		System.out.println(totalSodiumContentOfSession);

		return totalSodiumContentOfSession;
	}

	// Calculate "totalFatContentOfSession"
	public int calculateTotalFatContentOfSession(List<FoodItem> foodItemsList) {

		int totalFatContentOfSession = 0;

		Set set = hmap.entrySet();
		Iterator iterator = set.iterator();
		while (iterator.hasNext()) {
			Map.Entry entry = (Map.Entry) iterator.next();
			int itemId = Integer.parseInt(entry.getKey().toString());
			int itemTotalSelectedQty = Integer.parseInt(entry.getValue().toString());

			if (foodItemsList != null) {
				for (FoodItem item : foodItemsList) {
					if (item.getItemId() == itemId) {
						int itemFat = item.getcalorieProfile().getFatContent();
						int totalSessionFatContent = itemFat * itemTotalSelectedQty;
						totalFatContentOfSession += totalSessionFatContent;
					}
				}
			}
		}

		System.out.println(totalFatContentOfSession);

		return totalFatContentOfSession;
	}

	// Calculate "totalCarbContentOfSession"
	public int calculateTotalCarbContentOfSession(List<FoodItem> foodItemsList) {

		int totalCarbContentOfSession = 0;

		Set set = hmap.entrySet();
		Iterator iterator = set.iterator();
		while (iterator.hasNext()) {
			Map.Entry entry = (Map.Entry) iterator.next();
			int itemId = Integer.parseInt(entry.getKey().toString());
			int itemTotalSelectedQty = Integer.parseInt(entry.getValue().toString());

			if (foodItemsList != null) {
				for (FoodItem item : foodItemsList) {
					if (item.getItemId() == itemId) {
						int itemCarb = item.getcalorieProfile().getCarbContent();
						int totalSessionCarbContent = itemCarb * itemTotalSelectedQty;
						totalCarbContentOfSession += totalSessionCarbContent;
						;
					}
				}
			}
		}

		System.out.println(totalCarbContentOfSession);

		return totalCarbContentOfSession;
	}
	
	public int calculateTotalCookingTimeOfSession(List<FoodItem> foodItemsList) {

		totalCookingTimeOfSession = 0;

		Set set = hmap.entrySet();
		Iterator iterator = set.iterator();
		while (iterator.hasNext()) {
			Map.Entry entry = (Map.Entry) iterator.next();
			int itemId = Integer.parseInt(entry.getKey().toString());
			int itemTotalSelectedQty = Integer.parseInt(entry.getValue().toString());

			if (foodItemsList != null) {
				for (FoodItem item : foodItemsList) {
					if (item.getItemId() == itemId) {
						int itemTotalCookingTime = item.getCookingTime();
						int totalSessionCookingTime = 0;
						if(itemTotalCookingTime>0){
						totalSessionCookingTime = itemTotalCookingTime + itemTotalSelectedQty;
						}
						totalCookingTimeOfSession += totalSessionCookingTime;
					}
				}
			}
		}

		System.out.println(totalCookingTimeOfSession);

		return totalCookingTimeOfSession;
	}
	
	public boolean chkContainsVeg(List<FoodItem> foodItemsList) {

		boolean veg = false;

		Set set = hmap.entrySet();
		Iterator iterator = set.iterator();
		while (iterator.hasNext()) {
			Map.Entry entry = (Map.Entry) iterator.next();
			int itemId = Integer.parseInt(entry.getKey().toString());

			if (foodItemsList != null) {
				for (FoodItem item : foodItemsList) {
					if (item.getItemId() == itemId) {
						String itemveg = item.getVeg();
						veg = true;
					}
				}
			}
		}

		return veg;
	}
	
	public boolean chkContainsNonVeg(List<FoodItem> foodItemsList) {

		boolean nonVeg = false;

		Set set = hmap.entrySet();
		Iterator iterator = set.iterator();
		while (iterator.hasNext()) {
			Map.Entry entry = (Map.Entry) iterator.next();
			int itemId = Integer.parseInt(entry.getKey().toString());

			if (foodItemsList != null) {
				for (FoodItem item : foodItemsList) {
					if (item.getItemId() == itemId) {
						String itemveg = item.getNonVeg();
						nonVeg = true;
					}
				}
			}
		}

		return nonVeg;
	}


}
