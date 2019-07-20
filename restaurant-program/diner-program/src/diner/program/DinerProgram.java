/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diner.program;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.exit;
import java.util.ArrayList;
import java.util.Date;
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
        ArrayList<FoodItem> order_list = ReadWrite.LoadFoods();
        
        while(lockedIn){
            print("Enter an item and quantity");
            print("Type 'done' when finished");
            Integer[] inputFromUser = adv_input.validateChoiceQ(1, order_list.size());
            
            if ((inputFromUser[0]) == -1 & inputFromUser[1] == -1){
                print("Order completed");
                lockedIn = false;
            } else {
                inputFromUser[0]--; // this decrementer will allow the list to not start from zero
                food_list.add(order_list.get(inputFromUser[0]));
                food_count_list.add(inputFromUser[1]);
                order_list.get(inputFromUser[0]).orderCount ++;
            }
        }
        
        print("");
        double sum = 0.0;
        print("Name                                          Quantity    Price       Final");
        print("----                                          --------    -----       -----");
        for (int i = 0; i < food_list.size(); i++){
            print(adv_input.printLineCol( food_list.get(i).name,
                                food_count_list.get(i),
                                food_list.get(i).price,
                                (food_list.get(i).price * food_count_list.get(i))));
            sum += food_list.get(i).price;
        }
        double gst = sum * 0.6;
        double grandTotal = sum + gst;
        print("                                                          -------------------");
        print("                                                          Sub-total   $" + sum);
        print("                                                          6% GST      $" + gst);
        print("                                                          Total       $" + grandTotal);
        print("                                                          -------------------");
        
        ReadWrite.writeToFoodFile(order_list);
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
        history = ReadWrite.Customers();
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

class Sale {
    String saleId = "None";
    double revenue = 0.0;
    Date datetime = new Date();
    String customername = "None";
    String emailaddress = "None";
    
    public Sale(String saleIdIn, double revenueIn, Date datetimeIn, String customernameIn, String emailaddressIn){
        saleId = saleIdIn;
        revenue = revenueIn;
        datetime = datetimeIn;
        customername = customernameIn;
        emailaddress = emailaddressIn;
    }
}