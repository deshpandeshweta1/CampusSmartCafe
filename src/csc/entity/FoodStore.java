package csc.entity;
import java.sql.SQLException;
import java.util.List;

public interface FoodStore {

	public String getStoreId();

	public String getstoreName();

	public List<FoodItem> getFoodItemsList();

	public void setFoodItemsList(List<FoodItem> foodItemsList);
	
	public void updateFoodItemQty(SelectedFoodItemsSession session) throws SQLException;

	void setStoreId(String id);

	void setstoreName(String storeName);

}

