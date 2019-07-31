package app;

import java.util.ArrayList;

public class manageFood{

	/**
	 * So what this function does is prompt the user a series of information about the food that is about to be added to the data file.
	 * It will be using the adv_input and {@code confirmAction()} and {@code getDouble()} functions.
	 */
	static void add_food(){
		App.clearConsole();
		ArrayList<FoodItem> raw_food_list = ReadWrite.LoadFoods();
		App.print("Enter the name of the food you would like to add : ");
		String food_name_in = App.input();
		App.print("Enter a price for the item");
		double food_price_in = adv_input.getDouble();
		FoodItem new_food_item = new FoodItem(food_name_in, food_price_in,4.5,0);
		raw_food_list.add(new_food_item);

		if (adv_input.confirmAction("add a new food item to the menu")){
			ReadWrite.writeToFoodFile(raw_food_list);
		} else {
			App.print("Action aborted");}}

	/**
	 * So what this function does is prompt the user to input the name of a food item and delete it.
	 * It will be using the adv_input and {@code confirmAction()} and {@code getDouble()} functions.
	 */
	static void delete_food(){
		App.clearConsole();
		App.listFood();
		ArrayList<FoodItem> raw_food_list = ReadWrite.LoadFoods();
		App.print("Enter the name of the food item you would like to delete from the menu");
		String food_name_in = App.input();
		for (int i = 0; i < raw_food_list.size(); i++){
			if (raw_food_list.get(i).name.equals(food_name_in)){
				App.print("Food Item '" + food_name_in + "' found!");
				raw_food_list.remove(i);
				if (adv_input.confirmAction("delete a food item")){
					ReadWrite.writeToFoodFile(raw_food_list);
				} else {
					App.print("Action aborted"); 
					return;
				}}}
		App.print("Food item not found!");}

	/**
	 * So what this function does is prompt the user to enter the name of a food item and and edit the price of the item
	 * It will be using the adv_input and {@code confirmAction()} and {@code getDouble()} functions.
	 */
	static void edit_price(){
		App.clearConsole();
		App.listFood();
		ArrayList<FoodItem> raw_food_list = ReadWrite.LoadFoods();
		App.print("Enter a the name of the food item you'd like to re-price");
		String food_name = App.input();
		for (int i = 0; i < raw_food_list.size(); i++){
			if (raw_food_list.get(i).name.equals(food_name)){
				App.print("Food Item '" + food_name + "' found!");
				App.print("Please enter a new price for the item.");
				raw_food_list.get(i).price = adv_input.getDouble();
				if (adv_input.confirmAction("change the price of an item")){
					ReadWrite.writeToFoodFile(raw_food_list);
				} else {
					App.print("Action aborted");
					return;
				}}}
		App.print("Food item was not found!");}

}