/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;


import static app.App.print;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.exit;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Sam Ramirez
 */
public class ReadWrite {
    /**
     * This function reads all the data in the customers.txt file and parses all each line to a Customer object, then adds it to an array list object. Then returns the arraylist object for easy manipulation.
     * @param None
     * @return ArrayList<Customer> The array list object of all the data in customers.txt
     */
    static ArrayList<Customer> Customers(){
        // so what this function does is read the data from
        // customers.txt file and return and parse the data and
        // put it all in an ArrayList object with Customer sub object
        // and return said ArrayList object.
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
    
    /**
     * This function basically reads all the data in the items.txt files and loads each line to a FoodItem object and puts all of the lines into an array list object.
     * @param None
     * @return ArrayList<FoodItem> data in the items.txt
     */
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
    
    /**
     * What this function does is accept a ArrayList<FoodItem> and writes the contents to the items.txt text file
     * @param ArrayList<FoodItem> The arraylist to write to file
     * @return boolean check if the operation was a success
     */
    static boolean writeToFoodFile(ArrayList<FoodItem> Data) {
        // So what this function does is write the data to the file
        // based off the parameter ArrayList
        try {
            // put a try-catch clause because this is java and it so
            // picky about everything and I hate it...
            PrintWriter pw = new PrintWriter(new FileWriter("items.txt"));
            for (int i = 0; i < Data.size(); i++) {
                    FoodItem CurrentItem = Data.get(i);
                    pw.write(   CurrentItem.name+","+
                                CurrentItem.price+","+
                                CurrentItem.rating+","+
                                CurrentItem.orderCount+"\n");
            }
            // close the files.
            pw.close();
            return(true);
        } catch (IOException e){
            System.out.println("IO Exception error : " + e);
            return(false);
        }
    }
}
