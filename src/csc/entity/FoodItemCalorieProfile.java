package csc.entity;

public class FoodItemCalorieProfile {

	// Private Data Members
	private int totalCalories;
	private int fatContent;
	private int carbContent;
	private int sodiumContent;
	private int proteinContent;
	
	// Public Constructor
	public FoodItemCalorieProfile(int fatContent, int carbContent,int sodiumContent, int proteinContent) {

		this.fatContent = fatContent;
		this.sodiumContent = sodiumContent;
		this.carbContent = carbContent;
		this.proteinContent = proteinContent;
		calculateTotalCalories();
	}
	
	// Getter for "totalCalories"
	public int getTotalCalories() {
		return totalCalories;
	}
	
	// Getter for "fatContent"
	public int getFatContent() {
		return fatContent;
	}
	
	// Getter for "carbContent"
	public int getCarbContent() {
		return carbContent;
	}
	
	// Getter for "sodiumContent"
	public int getSodiumContent() {
		return sodiumContent;
	}
	
	// Getter for "proteinContent"
	public int getProteinContent() {
		return proteinContent;
	}
	
	// Calculate "totalCalories"
	public void calculateTotalCalories() {
		totalCalories = fatContent + carbContent + sodiumContent + proteinContent;
	}
	
	// Setter for "fatContent"
	public void setFatContent(int fatContent) {
		this.fatContent = fatContent;
	}
	
	// Setter for "carbContent"
	public void setCarbContent(int carbContent) {
		this.carbContent = carbContent;
	}
	
	// Setter for "sodiumContent"
	public void setSodiumContente(int sodiumContent) {
		this.sodiumContent = sodiumContent;
	}
	
	// Setter for "proteinContent"
	public void setProteinContent(int proteinContent) {
		this.proteinContent = proteinContent;
	}
}
