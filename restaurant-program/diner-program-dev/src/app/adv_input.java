/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

// import static diner.program.DinerProgram.input;
import static app.App.print;
import static app.App.input;
import java.util.Scanner;
import java.lang.NumberFormatException;
import java.util.Random;

/**
 *
 * @author Sam Ramirez
 */
public class adv_input {
        /**
         * so what this function does basically is lock the user in an ifinite loop until he enters a valid input which 2 integers separated by white space. The first integer is the item number and the second number is the quantity of said item the user wants to order...
         * <br><br>
         * To break out of the loop the user has to enter the keyword 'done' into the input, or the user has to enter a valid input Valid input is when the first integer is within the range of the parameters of this function and the second number is greater than zero
         *  
         * @param min The smallest accepted number
         * @param max The highest accepted number
         * @return see description
         */
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
                    	print("Invalid input. Try again.");
                    }
                } else {
                    	print("Invalid input. Try again.");
                }
            } catch(java.lang.NumberFormatException e){
                	print("Invalid input.");
            }
        }
        return(returnObject);
    }
    
    /**
     * So what this function does is lock the user in a loop until they provide a number that is ranged between the parameters specified in the arguments
     * @param min smallest allowed number
     * @param max highest allowed number
     * @return the number within range
     */
    static int rangedInput(int min, int max){
        // So what this function does is lock the user in a loop
        // untill they provide a number that is ranged between the 
        // parameters specified in the arguments
        boolean locked = true;
		int input = 0;
		Scanner VariableInput = new Scanner(System.in);
        while(locked){
                input = VariableInput.nextInt();
                if ((input >= min) & (input <= max)){
                        locked = false;
                } else {
                        System.out.println("The input have to be between " + min + " and " + max);
				}}
		// VariableInput.close();
        return(input);
    }
    
    /**
     * so what this function does make the output of the string really fancy looking like you know how there are dots on some bills that has dots on it? Yeah. Like that but this here will print THE EXACT number of dots needed to align the columns, using some sweet, sweet string witchcrafting.
     * @param nameIn name of the item
     * @param quantity quantity of said item
     * @param price price of the item
     * @param total total sum of the item
     * @return Properly constructed line
     */

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
        String FinalReturn =    nameIn + "  " + 
                                name_space + "  " + 
                                quantityIn + 
                                quantity_space + "$" + 
                                priceIn + price_space + "$" + 
                                totalIn;
        return(FinalReturn);
	}
	
	static boolean confirmAction(String action_name){
		App.print("You are about to " + action_name + ". Would you like to proceed? Type 'y' for YES and 'n' for NO.");
		// boolean lockedIn = true;
		String input = input();
		if (input.toLowerCase().equals("y")){
			return(true);
		}
		return(false);
	}

	static double getDouble(){
		boolean lockedIn = true;
		while (lockedIn){
			try {
				String attemptedString = input();
				double return_value = Double.parseDouble(attemptedString);
				return(return_value);
			} catch (NumberFormatException e) {
				print("Invalid input");
			}}return(0.0);}

	/**
	 * What this function does is accept an integer and generate a random hex string with the length of the parameter provided. It is used for generating usernames.
	 * @param Intger numchars
	 * @return String
	 */
    public static String getRandomHexString(int numchars){
        // this function will generate a 10 character
        // hex string...
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        while(sb.length() < numchars){
            sb.append(Integer.toHexString(r.nextInt()));
        }
		return sb.toString().substring(0, numchars);
	}

}


