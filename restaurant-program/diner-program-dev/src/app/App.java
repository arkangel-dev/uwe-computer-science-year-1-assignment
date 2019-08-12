/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;


// import java.io.File;
// import java.io.FileNotFoundException;
// import java.io.FileWriter;
// import java.io.IOException;
// import java.io.PrintWriter;
// import static java.lang.System.exit;
import java.text.DecimalFormat;
import java.util.ArrayList;
// import java.util.Date;
import java.util.Scanner;



/**
 *
 * @author Sam Ramirez
 */
public class App {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

		while (true){
			clearConsole();
			print("Welcome to the main menu");
			print("======================== \n");
			print("\t [1] Take a new order");
			print("\t [2] Show daily sales");
			print("\t [3] Edit menu items");
			print("\t [4] Register customer");
			print("\n[+] Please make a selection");

			int input_choice = adv_input.rangedInput(1, 4);
			switch(input_choice){
				case 1:
					makeOrder();
					break;
				case 2:
					viewSales();
					break;
				case 3:
					editItemsMenu();
					break;
				case 4:
					addCustomer();
					break;

				default:
					print("Missing function error : !!!");
			}
		}

	}

	/**
	 * What this function does is clear the screen.
	 */
	public final static void clearConsole() {
		try {
			final String os = System.getProperty("os.name");
			if (os.contains("Windows")) {
					Runtime.getRuntime().exec("cls");
			} else {
					Runtime.getRuntime().exec("clear");
			}}
		catch (final Exception e) {
			//  Handle any exceptions.
		}
		LotsOfLines();

	}

	static void viewSales(){
		clearConsole();
		ArrayList<Sale> oldSales = ReadWrite.loadSales();
		double total_cost = 0.0;
		for (int i = 0; i < oldSales.size(); i++){
			Sale current = oldSales.get(i);
			print(current.saleId + " | " + current.customername + " bought items worth $" + current.revenue + " (" + current.emailaddress + ")");
			total_cost += current.revenue;
			
		}
		print("---------------------------");
		DecimalFormat twoDecimals = new DecimalFormat("####0.00");
		print("Total revenue : $" + twoDecimals.format(total_cost));
		print("\nPress any key to continue");
		input();
	}

	static void addCustomer(){
		clearConsole();
		print("Enter the name of the customer : ");
		String customer_name_input = input();
		String email_addr;
		if (adv_input.confirmAction("add an email for this customer")){
			print("\nEnter the email address : ");
			email_addr = input();
		} else {
			email_addr = "None";
		}

		Customer new_customer = new Customer(adv_input.getRandomHexString(8), customer_name_input, 0.0, 0, email_addr);

		ArrayList<Customer> old_customers = ReadWrite.loadCustomers();
		old_customers.add(new_customer);
		ReadWrite.writeToCustomerFile(old_customers);
		print("\nCustomer has been added to the system.");
		print("\nPress any key to continue...");
		input();
	}


	/**
	 * So what this function does is print out 300 lines that are blank until the terminal pushes the previous lines out of the buffer.
	 * Right now it is only called by the clearConsole() function.
	 */
    static void LotsOfLines(){
		// what this function does is print a lot of
		// lines until the previous data is scrolled
		// up
        for (int i = 0; i < 50; i++){
			System.out.println("");
		}
    }


	static void editItemsMenu(){
		clearConsole();
		print("Edit Items Menu");
		print("=============== \n");
		print("\t [1] Add food item");
		print("\t [2] Delete food item");
		print("\t [3] Edit price");
		print("\t [0] Return");
		print("\n[+] Please make a selection");

		int input_choice = adv_input.rangedInput(0, 3);
		switch(input_choice){
			case 1:
				manageFood.add_food();
				clearConsole();
				break;
			case 2:
				manageFood.delete_food();
				clearConsole();
				return;
			case 3:
				manageFood.edit_price();
				clearConsole();
				return;
			case 0:
				print("[+] Going back");
				clearConsole();
				return;

			default:
				print("Missing function error : !!!");
				return;
		}}

    static void makeOrder(){
		clearConsole();
		Customer current;
		boolean newUser = false;
        boolean lockedIn = true;
        print("Enter customer name :");
        String customerName = input();
        if (lookUpCustomer(customerName) == -1){
			print("\nNew customer!");
			newUser = true;
        } else if (lookUpCustomer(customerName) == 0){
			print("\nFound customer");
			print("\nThis customer is registered but has not purchased anything yet");
        } else {
            print("Found Customer");
			print("\nThis customer has made transactions with us " + lookUpCustomer(customerName) + " times");
		}
		
        print("");
        print("Please select your items");
        print("-------------------------");
        listFood();
        ArrayList<FoodItem> order_list = new ArrayList<FoodItem>();
        ArrayList<Integer> food_count_list = new ArrayList<Integer>();
		ArrayList<FoodItem> menue = ReadWrite.LoadFoods();
		
		ArrayList<String> saleNameEntry = new ArrayList<String>();
		ArrayList<Integer> saleQuantityEntry = new ArrayList<Integer>();
        
        while(lockedIn){
            print("\nEnter an item and quantity");
            print("Type 'done' when finished");
            Integer[] inputFromUser = adv_input.validateChoiceQ(1, menue.size());
            
            if ((inputFromUser[0]) == -1 & inputFromUser[1] == -1){
				print("\nOrder Completed");
				//TODO : Fix stupidity
                lockedIn = false;
            } else {
                inputFromUser[0]--; // this decrementer will allow the list to not start from zero
                order_list.add(menue.get(inputFromUser[0]));
                food_count_list.add(inputFromUser[1]);
				menue.get(inputFromUser[0]).orderCount ++; // increment the class attrib orderCount
				
				saleNameEntry.add(menue.get(inputFromUser[0]).name);
				saleQuantityEntry.add(inputFromUser[1]);
				
            }
		}
        
        print("");
        double sum = 0.0;
        print("Name                                          Quantity    Price       Final");
		print("----                                          --------    -----       -----");
		
		ArrayList<billEntry> emailBill = new ArrayList<billEntry>();
        for (int i = 0; i < order_list.size(); i++){
            print(adv_input.printLineCol( order_list.get(i).name,
                                food_count_list.get(i),
                                order_list.get(i).price,
								(order_list.get(i).price * food_count_list.get(i))));
			

			billEntry tempBillE = new billEntry();

			tempBillE.name = order_list.get(i).name;
			tempBillE.finalp = (order_list.get(i).price * food_count_list.get(i));
			tempBillE.price = order_list.get(i).price;
			tempBillE.quantity = food_count_list.get(i);

			emailBill.add(tempBillE);
            sum += (order_list.get(i).price * food_count_list.get(i));
		}
		
	DecimalFormat twoDecimals = new DecimalFormat("####0.00");
	

        double gst = sum * 0.06;
        double grandTotal = sum + gst;
        print("                                                          -------------------");
        print("                                                          Sub-total   $" + twoDecimals.format(sum));
        print("                                                          6% GST      $" + twoDecimals.format(gst));
        print("                                                          Total       $" + twoDecimals.format(grandTotal));
        print("                                                          -------------------");
		
        
		String customer_email = "None";
		if (adv_input.confirmAction("send an email receipt to the customer")){
			current = getCustomerByName(customerName);
			if (current.email.equals("None")){
				print("Enter the customer's email please");
				customer_email = input();
			} else {
				customer_email = current.email;
			}
			print("Sending email to " + customer_email + ". Please wait...");
			String emailBody = networkManagement.formatEmail(emailBill);
			networkManagement.sendEmail(customer_email, "Billing", "Billing", emailBody);
		}

		ArrayList<Sale> oldSales = ReadWrite.loadSales();
		Sale currentNewSale = new Sale(adv_input.getRandomHexString(6), sum, customerName, customer_email);
		oldSales.add(currentNewSale);

		ReadWrite.writeToFoodFile(menue);
		ReadWrite.WriteSales(oldSales);

		ArrayList<Customer> oldCustomers = ReadWrite.loadCustomers();
		if (newUser){
			String customerId = "0x" + adv_input.getRandomHexString(8);
			Customer newCustomer = new Customer(customerId, customerName,0.00,0,customer_email);
			oldCustomers.add(newCustomer);
			ReadWrite.writeToCustomerFile(oldCustomers);
		} else {
			current = getCustomerByName(customerName);
			int index = getIndexCustomer(oldCustomers, current.name);
			Customer selected = oldCustomers.get(index);
			selected.visits += 1;
			selected.averagespending += sum;
			oldCustomers.set(index, selected);
			ReadWrite.writeToCustomerFile(oldCustomers);
		}

		print("\nPress any key to continue...");
		input();
    }
    
	static Customer getCustomerByName(String id){
		ArrayList<Customer> oldCustomerList = ReadWrite.loadCustomers();
		Customer current;
		for (int i = 0; i < oldCustomerList.size(); i++){
			current = oldCustomerList.get(i);
			if (current.name.equals(id)){
				return(current);
			}
		}

		Customer fallback_return = new Customer("0", "0", 0.0, 0, "None");
		return(fallback_return);
	}

	static int getIndexCustomer(ArrayList<Customer> testList, String index){
		for (int i = 0; i < testList.size(); i++){
			Customer current = testList.get(i);
			if (current.id.equals(index)){
				return(i);
			}
		}
		return(0);
	}
    
    static int lookUpCustomer(String name){
        // so this function will look up the name of a customer
        // and will return an integer code based off of what the
        // history is.
        // -1       means new customer.
        // 0        means registered customer but hasn't ordered anything
        // [int]    means this customer has visited this place x number of
        //          times.
        ArrayList<Customer> history = new ArrayList<Customer>();
        history = ReadWrite.loadCustomers();
        for (int i = 0; i < history.size(); i++){
            if (history.get(i).name.equals(name)){
                if (history.get(i).averagespending != 0){
                    return(history.get(i).visits);
                } else {
                    return(0);
                }
            }
        }
        return(-1);
	}

    static void listFood(){
        // so what this function does is list all the available
        // food items and their details
        print("");
        ArrayList<FoodItem> rawData = ReadWrite.LoadFoods();
        for (int i = 0; i < rawData.size(); i++){
            print((i + 1) + ". " + rawData.get(i).name);
            print(" \t > $" + rawData.get(i).price);
            print(" \t > " + rawData.get(i).rating + "/5 stars");
            print(" \t > Ordered " + rawData.get(i).orderCount + " times");
            print("");
        }
        print("");
    }
    
    static void print(String message){
        // this function is here because the sout + tab
        // thing on my netbeans ide decided to stop working.
        // also it resembles pythons printing function.
        System.out.println(message);
        
    }
    
    static String input(){
        // this function just prompts for an input and just
        // return the string... nothing else...
        Scanner VariableInput = new Scanner(System.in);
		String input = VariableInput.nextLine();
		// VariableInput.close();
        return(input);
    }
    
}

class billEntry{
	String name = "None";
	Integer quantity = 0;
	Double price = 0.0;
	Double finalp = 0.0;
}

class FoodItem {
    String name = "None";
    Double price = 0.0;
    Double rating = 0.0;
    Integer orderCount = 0;
    
    public FoodItem(String nameIn, Double priceIn, Double ratingIn, Integer orderCountIn){
        name = nameIn;
        price = priceIn;
        rating = ratingIn;
        orderCount = orderCountIn;
    }
}

class Customer {
    String id = "None";
    String name = "None";
    double averagespending = 0.00;
	int visits = 0;
	String email = "None";
    
    public Customer(String idIn, String nameIn, Double averagespendingIn, Integer visitsIn, String emailIn){
        id = idIn;
        name = nameIn;
        averagespending = averagespendingIn;
		visits = visitsIn;
		email = emailIn;
    }
}

/** This is the sales class. This class will store the information of all the sales. This class will be used by the main functions to interpret the previous sales and add new sales to the text file*/
class Sale {
	/** This variable uniquely identify a sale.*/
	String saleId = "None";
	/** This variable will record the revenue made by the sale. */
	double revenue = 0.0;
	/** This variable will store the name of the custom with whom the system made a transaction */
	String customername = "None";
	/** This variable will store the email address of the user. It will be used to send the bill as an email to the customer */
    String emailaddress = "None";
	
	/** This is the constructor. It will set the values to all the values to the class members.*/
    public Sale(String saleIdIn, double revenueIn, String customernameIn, String emailaddressIn){
        saleId = saleIdIn;
        revenue = revenueIn;
        customername = customernameIn;
        emailaddress = emailaddressIn;
    }
}
