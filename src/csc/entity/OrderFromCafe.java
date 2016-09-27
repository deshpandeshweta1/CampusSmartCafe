package csc.entity;
import java.time.LocalTime;

public class OrderFromCafe {
	
	// Private Data Members
	private int orderNumber;
	private int storeId;
	private SelectedFoodItemsSession session;
	
	// Public Constructor
	public OrderFromCafe(int orderNumber, int storeId) {
		this.orderNumber = orderNumber;
		this.storeId = storeId;
	}
	
	// Getter for "orderNumber"
	public int displayOrderNumber() {
		return orderNumber;
	}
	
	// Create a new Session
	public void createNewSession(int maxSessionID) {
		int sessionId = maxSessionID + 1;
		//session = new SelectedFoodItemsSession(sessionId, storeId);		
	}	

	// Calculate "pickUpTime"
	public LocalTime calculatePickUpTime() {
		
		LocalTime currentTime = LocalTime.now();
		LocalTime pickUpTime =null;
		//LocalTime pickUpTime = currentTime.plusHours(session.calculateTotalCookingTimeOfSession(Cafe.getFoodItemsList)() / 60);
		
		return pickUpTime;
	}
	
	// Getter for "session"
	public SelectedFoodItemsSession getSessionInfo() {
		return session;
	}
}
