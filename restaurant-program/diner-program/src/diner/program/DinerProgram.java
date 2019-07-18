/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diner.program;


import java.io.File;
import java.io.FileNotFoundException;
import static java.lang.System.exit;
import java.util.ArrayList;
import java.util.Scanner;


/**
 *
 * @author Sam Ramirez
 */
public class DinerProgram {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        print("Welcome to resturant thing");
        print("--------------------------");
        print("");
        
        makeOrder();
    }
    
    static void makeOrder(){
        boolean lockedIn = true;

        
        print("Enter customer name :");
        print("---------------------");
        String customerName = input();
        if (lookUpCustomer(customerName) == -1){
            print("New customer!");
        } else if (lookUpCustomer(customerName) == 0){
            print("Found customer");
            print("This customer is registered but has not purchased anything yet");
        } else {
            print("Found Customer");
            print("This customer has made transactions with us " + lookUpCustomer(customerName) + " times");
        }
        print("");
        
        
        print("Please select foods");
        print("-------------------");
        
        listFood();
        ArrayList<FoodItem> food_list = new ArrayList<FoodItem>();
        ArrayList<Integer> food_count_list = new ArrayList<Integer>();
        ArrayList<FoodItem> order_list = LoadFoods();
        
        while(lockedIn){
            print("Enter an item and quantity");
            print("Type 'done' when finished");
            Integer[] inputFromUser = validateChoiceQ(-1, order_list.size() -1);
            
            if (inputFromUser[0] == -1 & inputFromUser[1] == -1){
                print("Order completed");
                lockedIn = false;
            } else {
                food_list.add(order_list.get(inputFromUser[0]));
                food_count_list.add(inputFromUser[1]);
            }
        }
        
        print("");
        double sum = 0.0;
        
        print("Name                                          Quantity    Price       Final");
        print("----                                          --------    -----       -----");

        for (int i = 0; i < food_list.size(); i++){
            print(printLineCol(food_list.get(i).name, food_count_list.get(i), food_list.get(i).price, (food_list.get(i).price * food_count_list.get(i))));
            sum += food_list.get(i).price;
        }
        
        double gst = sum * 0.6;
        double grandTotal = sum + gst;
        print("                                                          -------------------");
        print("                                                          Sub-total   $" + sum);
        print("                                                          6% GST      $" + gst);
        print("                                                          Total       $" + grandTotal);
        print("                                                          -------------------");
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
        history = LoadCustomers();
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
    
    static Integer[] validateChoiceQ(int min,int max){
        // so what this function does basically is lock the user 
        // in an ifinite loop untill he enters a valid input which
        // 2 integers separated by white space. The first integer is
        // the item number and the second number is the quantity of 
        // said item the user wants to order...
        //
        // to break out of the loop the user has to enter the keyword
        // 'done' into the input, or the user has to enter a valid input
        // Valid input is when the first integer is within the range of the
        // parameters of this function and the second number is greater than zero
        //
        boolean lockedIn = true;
        Integer[] returnObject = new Integer[2];
        while (lockedIn){
            String rawInput = input();
            // check if the input is 'done'
            if (rawInput.equals("done")){
                returnObject[0] = -1;
                returnObject[1] = -1;
                return(returnObject);
            }
            try { 
                // this try-catch statement will determine if the
                // intput can be parsed into integers and if
                // not catch it
                //
                String[] raw_parts = rawInput.split(" ");
                if (raw_parts.length == 2){
                    // this if condition check is there are
                    // 2 parts in the raw input... because
                    // we need exactly 2 inputs
                    //
                    int item = Integer.parseInt(raw_parts[0]);
                    int quantity = Integer.parseInt(raw_parts[1]);
                    if ((((item >= min) & (item <= max)) & (quantity > 0)) & (raw_parts.length == 2)){
                        // this if condition checks if the range for the first
                        // integer is valid
                        returnObject[0] = item;
                        returnObject[1] = quantity;
                        return(returnObject);
                    } else {
                        print("Invalid input.");
                    }
                } else {
                    print("Invalid input.");
                }
            } catch(java.lang.NumberFormatException e){
                print("Invalid input.");
            }
        }
        return(returnObject);
    }
    
    static String printLineCol(String nameIn, Integer quantity, Double price, Double total){
        // so what this function does make the output of the string really fancy looking
        // like you know how there are dots on some bills that has dots on it? Yeah. Like that
        // but this here will print THE EXACT number of dots needed to align the columns,
        // using some sweet, sweet string witchcrafting.
        //
        String quantityIn = quantity.toString();
        String priceIn = price.toString();
        String totalIn = total.toString();
        String name_space = ".............................................";
        String quantity_space = "         ";
        String price_space = "           ";
        name_space = name_space.substring(0,name_space.length() - nameIn.length());
        quantity_space = quantity_space.substring(0,quantity_space.length() - quantityIn.length());
        price_space = price_space.substring(0,price_space.length() - priceIn.length());
        String FinalReturn = nameIn + "  " + name_space + "  " + quantityIn + quantity_space + "$" + priceIn + price_space + "$" + totalIn;
        return(FinalReturn);
    }
    
    static void listFood(){
        // so what this function does is list all the available
        // food items and their details
        print("");
        ArrayList<FoodItem> rawData = LoadFoods();
        for (int i = 0; i < rawData.size(); i++){
            print(i + ". " + rawData.get(i).name);
            print(" \t > $" + rawData.get(i).price);
            print(" \t > " + rawData.get(i).rating + "/5 stars");
            print(" \t > Ordered " + rawData.get(i).orderCount + " times");
            print("");
        }
        print("");
    }
    
    static ArrayList<Customer> LoadCustomers(){
        ArrayList<Customer> ReturnObject = new ArrayList<Customer>();
        try {
            Scanner customerfile = new Scanner(new File("customers.txt"));
            while (customerfile.hasNextLine()){
                String currentLine = customerfile.nextLine();
                String[] splitted_currentLine = currentLine.split(",");
                
                if (splitted_currentLine.length != 4){
                    print("Error : customers file corrupted. " + currentLine);
                    exit(1);
                } else {
                    String ID = splitted_currentLine[0];
                    String name = splitted_currentLine[1];
                    double averagespending = Double.parseDouble(splitted_currentLine[2]);
                    int visits = Integer.parseInt(splitted_currentLine[3]);
                    Customer currentCustomerObject = new Customer(ID, name, averagespending, visits);
                    ReturnObject.add(currentCustomerObject);
                }
            }
        } catch (FileNotFoundException e){
            print("Cannot load customerfile | " + e);
        }
        return(ReturnObject);
    }
    
    static ArrayList<FoodItem> LoadFoods(){
        // what this function does is 
        // load all the data from the text file
        // and return an arraylist object with the
        // sub item as FoodItem
        ArrayList<FoodItem> foodList = new ArrayList<FoodItem>();
        try {
            Scanner elecfile = new Scanner(new File("items.txt"));
            while (elecfile.hasNextLine()) {
                String currentLine = elecfile.nextLine();
                String[] splitted_currentLine = currentLine.split(",");
                
                String nameR = splitted_currentLine[0];
                double priceR = Double.parseDouble(splitted_currentLine[1]);
                double ratingR = Double.parseDouble(splitted_currentLine[2]);
                int orderCountR = Integer.parseInt(splitted_currentLine[3]);
                
                FoodItem newItem = new FoodItem(nameR, priceR, ratingR, orderCountR);
                foodList.add(newItem);
            }
            return(foodList);
        } catch (FileNotFoundException e) {
            System.out.println("Cannot load the food file. " + e);
            exit(1);
        }
        return(foodList);
    }  
    
    static int rangedInput(int min, int max){
        // So what this function does is lock the user in a loop
        // untill they provide a number that is ranged between the 
        // parameters specified in the arguments
        boolean locked = true;
        int input = 0;
        while(locked){
                Scanner VariableInput = new Scanner(System.in);
                input = VariableInput.nextInt();
                if ((input >= min) & (input <= max)){
                        locked = false;
                } else {
                        System.out.println("The input have to be between " + min + " and " + max);
                }}
        return(input);
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
        return(input);
    }
    
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
    
    public Customer(String idIn, String nameIn, Double averagespendingIn, Integer visitsIn){
        id = idIn;
        name = nameIn;
        averagespending = averagespendingIn;
        visits = visitsIn;
    }
}