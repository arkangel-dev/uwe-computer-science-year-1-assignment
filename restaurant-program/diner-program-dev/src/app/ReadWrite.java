/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;


// import static app.App.print;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.exit;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.text.DecimalFormat;

/**
 *
 * @author Sam Ramirez
 */
public class ReadWrite {
    // /**
    //  * This function reads all the data in the customers.txt file and parses all each line to a Customer object, then adds it to an array list object. Then returns the arraylist object for easy manipulation.
    //  * @param None
    //  * @return ArrayList<Customer> The array list object of all the data in customers.txt
    //  */
    // static ArrayList<Customer> Customers(){
    //     // so what this function does is read the data from
    //     // customers.txt file and return and parse the data and
    //     // put it all in an ArrayList object with Customer sub object
    //     // and return said ArrayList object.
    //     ArrayList<Customer> ReturnObject = new ArrayList<Customer>();
    //     try {
    //         Scanner customerfile = new Scanner(new File("customers.txt"));
    //         while (customerfile.hasNextLine()){
    //             String currentLine = customerfile.nextLine();
    //             String[] splitted_currentLine = currentLine.split(",");
                
    //             if (splitted_currentLine.length != 4){
    //                 print("Error : customers file corrupted. " + currentLine);
    //                 exit(1);
    //             } else {
    //                 String ID = splitted_currentLine[0];
    //                 String name = splitted_currentLine[1];
    //                 double averagespending = Double.parseDouble(splitted_currentLine[2]);
    //                 int visits = Integer.parseInt(splitted_currentLine[3]);
    //                 Customer currentCustomerObject = new Customer(ID, name, averagespending, visits);
    //                 ReturnObject.add(currentCustomerObject);
    //             }
    //         }
    //     } catch (FileNotFoundException e){
    //         print("Cannot load customerfile | " + e);
    //     }
    //     return(ReturnObject);
    // }
    
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
			elecfile.close();
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
	
	static ArrayList<Customer> loadCustomers(){
		ArrayList<Customer> returnObject = new ArrayList<Customer>();
		try {
			Scanner customerfile = new Scanner(new File("customers.txt"));
			while (customerfile.hasNextLine()){
				String currentline = customerfile.nextLine();
				String[] parts = currentline.split(",");
				
				String id = parts[0];
				String name = parts[1];
				double average_spending = Double.parseDouble(parts[2]);
				int visits = Integer.parseInt(parts[3]);
				String email = parts[4];

				Customer newcustomerline = new Customer(id, name, average_spending, visits, email);
				returnObject.add(newcustomerline);
			}
			customerfile.close();
			return(returnObject);
		} catch (FileNotFoundException e) {
			App.print("File not found!" + e);
		}
		return(returnObject);	
	}

	static void writeToCustomerFile(ArrayList<Customer> inputData){
		try {
			PrintWriter cf = new PrintWriter(new FileWriter("customers.txt"));
			for (int i = 0; i < inputData.size(); i++){
				Customer currentUser = inputData.get(i);
				String currentString = currentUser.id + "," + currentUser.name + "," + currentUser.averagespending + "," + currentUser.visits + "," + currentUser.email + "\n";
				cf.write(currentString);
			}
			cf.close();
		} catch (IOException e){
			App.print("Error : Cannot open customer.txt file | " + e);
		}
	}

	static ArrayList<Sale> loadSales(){
		ArrayList<Sale> returnObject = new ArrayList<Sale>();
		try {
			Scanner salesFile = new Scanner(new File("sales.txt"));
			while (salesFile.hasNextLine()){
				String currentline = salesFile.nextLine();
				String parts[] = currentline.split(",");

				String saleId = parts[0];
				double revenue = Double.parseDouble(parts[1]);
				String customerName = parts[2];
				String emailAddress = parts[3];

				Sale newSaleEntry = new Sale(saleId, revenue, customerName, emailAddress);
				returnObject.add(newSaleEntry);
			}
			return(returnObject);
		} catch (FileNotFoundException e) {
		}
		return(returnObject);
	}

	static void WriteSales(ArrayList<Sale> saveData){
		try {
			PrintWriter salesFile = new PrintWriter(new FileWriter("sales.txt"));
			DecimalFormat df = new DecimalFormat("####0.00");
			for (int i = 0; i < saveData.size(); i++){
				Sale currentSale = saveData.get(i);
				String currentLine =	currentSale.saleId + "," +
										df.format(currentSale.revenue) + "," +
										currentSale.customername + ","+
										currentSale.emailaddress + "\n";
				salesFile.write(currentLine);
			}
			salesFile.close();
		} catch (IOException e){App.print("IO Error : " + e);}
	}

	// static void writeToSalesFile(ArrayList<Sale> inputData){
	// 	try {
	// 		PrintWriter salesFile = new PrintWriter(new FileWriter("sales.txt"));
	// 		for (int i = 0; i < inputData.size(); i++){
	// 			Sale currentSale = inputData.get(i);
	// 			salesFile.write(	currentSale.saleId + "," +
	// 								currentSale.customername + "," +
	// 								currentSale.revenue + "\n"); 
	// 		}
	// 		// salesFile.close();
	// 	} catch (Exception e) {
	// 		// App.print(e);
	// 	}
	// }
}
