/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsonbasedvoting;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.exit;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Sam Ramirez
 */



public class JsonBasedVoting {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
//        ArrayList<String[]> DataDoo = LoadData();
//        WriteLine(AddUser("0x02", "Mohamed Isaam Rameez", "adbcd", "U0001"));
//        WriteLine(RemoveLineByMatch(DataDoo, 0, "0x02"));
        Scanner VariableInput = new Scanner(System.in);
        while(true){

            System.out.println("\nWELCOME TO THE 2019 ELECTIONS!");
            System.out.println("Please sign in using your assigned username and password");
            System.out.println("----------------------------------------------------------");

            // so no shit.. enter the username
            // because... reasons
            System.out.println("\n[!] Enter Username : ");
            String UsernameAttempt = VariableInput.nextLine();
            
            // enter the password...
            System.out.println("\n[!] Enter Password : ");
            String PasswordAtttempt = VariableInput.nextLine();
            boolean LoginValid = ValidateLogin(UsernameAttempt, PasswordAtttempt, "0x03");
            
            // now we are going to print the validation
            // if the system is correct
            if (LoginValid){ 
                System.out.println("\n[+] Login Accepted!");
            } else { 
                System.out.println("\n[-] Login Declined!\n");
		System.out.println("Your Username and/or Password is incorrect");
                System.out.println("Please contact your supervisor for assistance or try again");
            }
            System.out.println("");
            
            // enter an infinite loop...
            while(LoginValid){
                System.out.println("Welcome to the main menu");
                System.out.println("Logged in as : " + getUserDetails(UsernameAttempt, 3, 1));
                System.out.println("--------------------");
                System.out.println("");
                System.out.println("[1]\tAdd Voter");
                // System.out.println("[2]\tAdd Candidate");
                System.out.println("[2]\tAdd Officer");
                System.out.println("[3]\tRemove User");
                System.out.println("[4]\tStart Vote");
                System.out.println("");
                System.out.println("[+] Enter a choice : ");
                String choice = VariableInput.nextLine();
                
                if (choice.equals("1")){
                    CreateUser("voter", "0x01");
                // } else if (choice.equals("locked-function-0x352")){
                //     CreateUser("candidate", "0x02");
                } else if (choice.equals("2")){
                    CreateUser("officer", "0x03");
                } else if (choice.equals("3")){
                    DeleteUser();
                } else if (choice.equals("4")){
					clearConsole();
                    StartVote();
                } else {
                    System.out.println("[-] Invalid choice. Try again.");
                }             
//                break;
            }
        }
    }
	
	/** This variable stores the return object of the function LoadData() and store it. */
	static ArrayList<String[]> Data = LoadData();
	/** This variable stores the return object of the function classifyVoters(). */
	public static Voter[] voterList = classifyVoters(Data);
	/** This variable stores the return object of the function classifyCandidates(). */
	public static Candidate[] candidateList = classifyCandidates(Data);
	/** This variable stores the return object of the function classifyAdmins(). */
	public static Officer[] officerList = classifyAdmins(Data);
	/** This variable will store the boolean value whether or not the previous login was successful */
	public static boolean loginSuccessful = false;
	/** This variable will determine whether or the system will be locked in a loop that will prompt the users to login and vote */
	public static boolean stillVoting = true;
	/** This variable will store how many users have voted so far in the system. This information will be used to determine when the system will exit out of the loop automatically by setting the stillVoting variable to False */
    public static int voteCount = 0;
	
	/**
	 * So this is the functiion that will loop undefietly untill the system completes voting...
	 * <br>
	 * Ok so now we are going to validate the login credentials... But we cannot use the ValidateLogin() function because, again stupid Javas design doesn't allow us to get the class object index... Stupid Java
	 */
    static void StartVote() {

		int totalVoters = voterList.length + candidateList.length + officerList.length;
        
        while (stillVoting){
            if (totalVoters <= voteCount){
                stillVoting = false;
            }
            System.out.println("WELCOME TO THE 2019 ELECTIONS!");
            System.out.println("Please enter your credentials");
            String AttemptedUsername = getValidInput("\n[+] Enter a username : ", 8);
            String AttemptedPassword = getValidInput("\n[+] Enter a password : ", 8);
            System.out.println("\n[+] Validating login...");
            
            
//            /
//             Ok so now we are going to validate the login credentials... But we cannot
//             use the ValidateLogin() function because, again stupid Javas design doesn't
//             allow us to get the class object index... Stupid Java
//             
            
            for (int i = 0; i < voterList.length; i++){
                if (voterList[i].Identity.equals(AttemptedUsername) & voterList[i].Passphrase.equals(AttemptedPassword)){
                    // TODO enter logic if the login creds are correct
                    loginSuccessful = true;
                    showMenu("0x01", i);
                    clearConsole();
                }}
            for (int i = 0; i < candidateList.length; i++){
                if (candidateList[i].Identity.equals(AttemptedUsername) & candidateList[i].Passphrase.equals(AttemptedPassword)){
                    // TODO enter logic if the login creds are correct
                    loginSuccessful = true;
                     showMenu("0x02", i);
                     clearConsole();
                }}
            for (int i = 0; i < officerList.length; i++){
                if (officerList[i].Identity.equals(AttemptedUsername) & officerList[i].Passphrase.equals(AttemptedPassword)){
                    // TODO enter logic if the login creds are correct
                    loginSuccessful = true;
                    showMenu("0x03", i);
                    clearConsole();
                }}
            if (!loginSuccessful){
                // TODO enter logic if the login creds are incorrect
                System.out.println("Cannot validate login. Please try again.");
            }
            
		} 
		System.out.println("<< Voting Completed >>");
		System.out.println("----------------------");
		for (int i = 0; i < candidateList.length; i++){
			System.out.println(candidateList[i].UserName + "\t\t\t" + candidateList[i].votecount);
		}

		int greatestIndex = 0;
		for (int i = 0; i < candidateList.length; i++){
			if (candidateList[i].votecount > greatestIndex){
				greatestIndex = i;
			}}

		if (greatestIndex == 0){
			System.out.println("No one came to vote");
		} else {
			System.out.println("Candidate " + candidateList[greatestIndex].UserName + " has won with " + candidateList[greatestIndex].votecount + " votes.");
		}


		exit(0);
    }
	
	/**
	 * So what this function does is print out 300 lines that are blank until the terminal pushes the previous lines out of the buffer.
	 * Right now it is only called by the clearConsole() function.
	 */
    static void LotsOfLines(){
		// what this function does is print a lot of
		// lines until the previous data is scrolled
		// up
        for (int i = 0; i < 300; i++){
			System.out.println("");
		}
    }
	
	/**
	 * What this function does is accept a two paramters. The first string basically tells us which class array to use for this user. The second paramter tells us which element of the array we should select. So basically the first string tells whether the user is an officer, voter or a candidate. The second parameter tells us which one of those to select it. Aaand Im repeating my self... That's not a good sign... Wait... Im trying what Im thinking. That's bad
	 * @param String userType
	 * @param Integer index
	 */
    static void showMenu(String userType, Integer index){
        System.out.println("");
        if (userType.equals("0x01")){
			System.out.println("Welcome " + voterList[index].UserName);
			System.out.println("");
            if (!voterList[index].Voted){
				// *****************************************************
				for (int i = 0; i < candidateList.length; i++){
					System.out.println("\t\t" + (i + 1) + "." + candidateList[i].UserName);
				}
				System.out.println("");
				System.out.println("Please choose a candidate : ");
				int selectedIndex = rangedInput(1, candidateList.length);
				candidateList[selectedIndex - 1].votecount ++;
				voterList[index].Voted = true;
				// *****************************************************
            } else {
                System.out.println("You have already voted. Please leave.");
            }
        } else if (userType.equals("0x02")) {
			System.out.println("Welcome candidate " + candidateList[index].UserName);
			System.out.println("You currently have " + candidateList[index].votecount + " votes.\n");
			if (!candidateList[index].Voted){
				System.out.println("Seems like you haven't voted yet. So please make a selection.\n");
				// *****************************************************
				for (int i = 0; i < candidateList.length; i++){
					System.out.println("\t\t" + (i + 1) + "." + candidateList[i].UserName);
				}
				System.out.println("");
				System.out.println("Please make a selection.");
				int selectedIndex = rangedInput(1, candidateList.length);
				candidateList[selectedIndex - 1].votecount ++;
				candidateList[index].Voted = true;
				// *****************************************************
			} else {
				System.out.println("You have already voted.");
			}
		} else if (userType.equals("0x03")){
			System.out.println("Welcome officer " + officerList[index].UserName);

			if (!officerList[index].Voted){
				System.out.println("Seems like you haven't voted yet. So please make a selection.\n");
				// *****************************************************
				for (int i = 0; i < candidateList.length; i++){
					System.out.println("\t\t" + (i + 1) + "." + candidateList[i].UserName);
				}
				System.out.println("");
				System.out.println("Please make a selection.");
				int selectedIndex = rangedInput(1, candidateList.length);
				candidateList[selectedIndex - 1].votecount ++;
				officerList[index].Voted = true;
				// *****************************************************
			} else {
                            System.out.println("You have already voted.\n");
                        }

			if (matchInputBool("Please type in END to end the voting process or DONE if you would like to exit", "END")){
				stillVoting = false;
			}
		}
        System.out.println("");
	}

	/**
	 * What this function does if return a boolean value based on whether or not the two provided values match.
	 * @param String message
	 * @param String matchString
	 * @return Boolean
	 */
	static boolean matchInputBool(String message, String matchString){
		System.out.println(message);
		Scanner VariableInput = new Scanner(System.in);
		String input = VariableInput.nextLine();
		if (input.equals(matchString)){
			return(true);
		} else {
			return(false);
		}
	}
	
	/**
	 * What this function does is lock the user a loop untill they enter an integer that is within the range of the parameters provided. And if the input is within the range then return the integer.
	 * @param Integer min
	 * @param Integer max
	 * @return Integer
	 */
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
	
	/**
	 * What this function does is accept an ArrayList<String[]> and convert it to a Voter[] object.
	 * @param ArrayList<String[]> Input
	 * @return Voter[]
	 */
    public static Voter[] classifyVoters(ArrayList<String[]> Input){
        // so what this function does is accept a ArrayList<String[]> as
        // input and turn the users in that object into a array of class objects
        // we defined. But since this is java and we cannot return more than one value
        // and we cannot stack said values in a single array because in java all the values
        // in an array have to have the same value...
        ArrayList<Voter> ReturnList = new ArrayList<Voter>();
        for (int i = 0; i < Input.size(); i++){
            if (Input.get(i)[0].equals("0x01")){
                Voter Current = new Voter(Input.get(i)[3], Input.get(i)[1], Input.get(i)[2], Input.get(1)[3]);
                ReturnList.add(Current);
            }
        }
        // Here we are making the return object for the array
        Voter[] FinalSpace = new Voter[ReturnList.size()];
        for (int i = 0; i < FinalSpace.length; i++){
            FinalSpace[i] = ReturnList.get(i);}
        return(FinalSpace);
    }
	
	/**
	 * What this function does is accept an ArrayList<String[]> and convert it to a Candidate[] object.
	 * @param ArrayList<String[]> Input
	 * @return Candiate[]
	 */
    public static Candidate[] classifyCandidates(ArrayList<String[]> Input){
        ArrayList<Candidate> ReturnList = new ArrayList<Candidate>();
        for (int i = 0; i < Input.size(); i++){
            if (Input.get(i)[0].equals("0x02")){
                Candidate Current = new Candidate(Input.get(i)[3], Input.get(i)[1], Input.get(i)[2], Input.get(1)[3]);
                ReturnList.add(Current);
            }
        }
        Candidate[] FinalSpace = new Candidate[ReturnList.size()];
        for (int i = 0; i < FinalSpace.length; i++){
            FinalSpace[i] = ReturnList.get(i);}
        return(FinalSpace);
    }
	
	/**
	 * What this function does is accept an ArrayList<String[]> and convert it to an Officer[] object.
	 * @param ArrayList<String[]> Input
	 * @return Officer[]
	 */
    public static Officer[] classifyAdmins(ArrayList<String[]> Input){
        ArrayList<Officer> ReturnList = new ArrayList<Officer>();
        for (int i = 0; i < Input.size(); i++){
            if (Input.get(i)[0].equals("0x03")){
                Officer Current = new Officer(Input.get(i)[3], Input.get(i)[1], Input.get(i)[2], Input.get(1)[3]);
                ReturnList.add(Current);
            }
        }
        Officer[] FinalSpace = new Officer[ReturnList.size()];
        for (int i = 0; i < FinalSpace.length; i++){
            FinalSpace[i] = ReturnList.get(i);}
        return(FinalSpace);
    }
  
	/**
	 * What this function does is accept an integer and generate a random hex string with the length of the parameter provided
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
	
	/**
	 * What this function does is lock the user in a loop until the data matches a certain criteria
	 * @param String message 
	 * @param Integer min : This defines the length of the string
	 * @return String
	 */
    public static String getValidInput(String message, Integer min){
        // so this function is important because the data
        // in the text file is comma separated. If there is
        // a comma in there it will be all screwed up...
        Scanner VariableInput = new Scanner(System.in);
        boolean invalid_input = true;
        System.out.println(message);
        while(invalid_input){
            String returnString = VariableInput.nextLine();
            if (!returnString.contains(",") & returnString.length() >= min){
               invalid_input = false;
               return(returnString);
            } else {
                System.out.println("[-] The input is invalid. Try again.");
                System.out.println("[-] Input should be longer than " + min + " characters and should not contain commas");
            }
        }
        return("Error");
    }
	
	/**
	 * What this function does is create a user and accept a string for the name a of the UserTypeName and a TypeCode that defines the type of user
	 * 
	 * @param String UserTypeName
	 * @param String TypeCodeIn
	 * @return None
	 */
    public static void CreateUser(String UserTypeName, String TypeCodeIn) {
        // So this function creates a user and accepts a string for the 
        // name of the UserTypeName and a TypeCode that defines the type of 
        // user
        String TypeCode = TypeCodeIn;
        String UserID = getRandomHexString(8);
        String VoterName = getValidInput("\nEnter " + UserTypeName + " full name : ", 8);
		String UserPass = getValidInput("\nEnter a password", 8);
		String IDCard = getValidInput("\nEnter ID Card Number", 4);
        WriteLine(AddUser(TypeCode, VoterName, UserPass, UserID, IDCard));
        System.out.println("\nA " + UserTypeName + " has been added with a user ID of " + UserID+"\n");
    }
	
	/**
	 * What this function is graphically delete a user from the arraylist object. 
	 * @param None
	 * @return None
	 */
    public static void DeleteUser(){
        // this function deletes the user 
        Scanner VariableInput = new Scanner(System.in);
        System.out.println("\nEnter the user ID you'd like to delete :");
        String UserID = VariableInput.nextLine();
        ArrayList<String[]> data = LoadData();
        WriteLine(RemoveLineByMatch(data, 3, UserID));
        System.out.println("User has been removed\n");
    }
	
	/**
	 * What this function does is accept an arraylist and remove a line from the ArratList based on a match from the String MAtch from the Index provided in the variable index.
	 * @param ArrayList<String[]> Data
	 * @param String Index
	 * @param String Match
	 * @return None
	 */
    public static ArrayList<String[]> RemoveLineByMatch(ArrayList<String[]> Data, Integer Index, String Match){
        // so what this function will do is accept an arraylist and remove a line from the
        // ArrayList based on a match from the String Match from the Index provided in the 
        // variable index
        
        for (int i = 0; i < Data.size(); i++){
            // here we are going to loop through all the
            // lines until we find one that matches all the
            // requirements and remove it
            if (Data.get(i)[Index].equals(Match)){
                System.out.println("\nRemoving matching line...");
                Data.remove(i);
                return(Data);
            }
        }
        return(Data);
    }
	
	/**
	 * What this function does is make is accept an arrayList object and save the data to the data.txt file.
	 * @param Array Data
	 * @return
	 */
    public static boolean WriteLine(ArrayList<String[]> Data) {
        // So what this function does is write the data to the file
        // based off the parameter ArrayList
        try {
            // put a try-catch clause because this is java and it so
            // picky about everything and I hate it...
            PrintWriter pw = new PrintWriter(new FileWriter("data.txt"));
            for (int i = 0; i < Data.size(); i++) {
                    String[] CurrentUser = Data.get(i);
                    pw.write(CurrentUser[0]+","+CurrentUser[1]+","+CurrentUser[2]+","+CurrentUser[3]+","+CurrentUser[4] + "\n");
            }
            // close the files.
            pw.close();
            return(true);
        } catch (IOException e){
            System.out.println("IO Exception error : " + e);
            return(false);
        }
    }
	
	
	/**
	 * What this function does is add a user to the ArrayList<String[]> Object that is provided in the parameters. and return that arrayList<> object.
	 * @param String TypeCode The user type of the user (0x01, 0x02, 0x03)
	 * @param String UserName The username of the user
	 * @param String Password The password of the user
	 * @param String UserID The userID of the user
	 * @return ArrayList<String[]>
	 */
    public static ArrayList<String[]> AddUser(String TypeCode, String UserName, String Password, String UserID, String IDCard){
        // So what this function does is add a user to the arraylist data from
        // the file and add lines according to the parameters provided
        ArrayList<String[]> Data = LoadData();
        String[] DataLine = {TypeCode, UserName, Password, UserID, IDCard};
        Data.add(DataLine);
        return(Data);
    }
	
	
	/**
	 * So what this function will do is accept a username and a password and user type (which is a string). Then it will invoke the LoadData() function and store the values in an array. Then it parse through the array and try to see if there is an entry that matches the username, password and usertype id. If it finds one it will return boolean true. If it does not find one it will return false. 
	 * This function will be used for login validation. Now that I think about it would've been easier to just put this function in the class objects.
	 * 
	 * @param String Username
	 * @param String Password
	 * @param String Usertype
	 * @return Boolean
	 */
    public static boolean ValidateLogin(String Username, String Password, String UserType){
        // this function is used to validate the login 
        // so yay... Also its locked in with a boolean so
        // that it can be used in a condition check...
        ArrayList<String[]> Data = LoadData();
        for (int i = 0; Data.size() > i; i++){
            String[] current_user = Data.get(i);
            if ((current_user[3].equals(Username)) & (current_user[2].equals(Password)) & (current_user[0].equals(UserType))){
//                System.out.println("Login Successful");
                return(true);
            } else if (UserType == "0x00"){
                if ((current_user[3].equals(Username)) & (current_user[2].equals(Password))){
                    return(true);
                }
            }
        }
//        System.out.println("Login Failed");
        return(false);
    }
	
	/**
	 * What this function does is accept a testvalue, an testfield index and a returnId. So what it will do is use the LoadData() function put the result in an array. Then it will go through the 1st dimension of the array and for each index in the 1st dimension, check if the test value matches the element value of current 1st dimension's 'testfield index'th value. If it matches, then return the value of the element whose index is defined by the returnId
	 * 
	 * @param String username
	 * @param Integer indexId
	 * @param Integer returnId
	 * @return String
	 */
    public static String getUserDetails(String username, Integer indexId, Integer returnId){
        // so this function will return the user type of the
        // username provided...
        ArrayList<String[]> loadedData = LoadData();
        for (int i = 0; i < loadedData.size(); i++){
            if (loadedData.get(i)[indexId].equals(username)){
                return(loadedData.get(i)[returnId]);
            }
        }
        return("False");
    }
	
	/**
	 * What this function does is load all of the lines into an array of strings. Then it will split each of those strings by the commas and put that string into a 2D array. Which is an ArrayList object.
	 * @return ArrayList<String[]>
	 */
    public static ArrayList<String[]> LoadData(){
        Scanner VariableInput = new Scanner(System.in);

        // so here we are going to declare an ArrayList to
        // dynamically add items to it
        ArrayList<String[]> splitted_raw = new ArrayList<String[]>();
        
        try {
            // define the file...
            // so the file is syntaxed in a way each line begins with
            // a hex code that determines the type of user the rest of the line
            // defines. The second part of the line is the name of the user
            // the next line. The next lines defines the passwordl. The next line is
            // the username.
            //
            // 0x01,Sam Ramirez,87yh87JHhfr7,U00002
            Scanner elecfile = new Scanner(new File("data.txt"));
           
            
            // this here while loop will split the lines into an array and
            // split those lines into even smailler by splitting them by the
            // commas.
            // and now the data is stored in an array within an array list...
            while (elecfile.hasNextLine()) {
                String line = elecfile.nextLine();
                String[] line_splitted = line.split(",");
                splitted_raw.add(line_splitted);
            }
            
            return(splitted_raw);
           
            
        } catch (FileNotFoundException e) {
            System.out.println("Cannot load the data file. " + e);
            exit(1);
        }
		return(splitted_raw);

	}   
}

/**
 * This is the voter class.
 * This class is the parent class to the other classes and they inherit this class's attribute such as Identity, password, username and the Voted Boolean variable. The voted boolean variable determins if the user has voted or not
 * <br>
 * <h2> Attributes </h2>
 * This is a list of all the attributes this class has that is not inherited from a parent class...
 * <ul>
 * 		<li>String : Identity = "None"</li>
 * 		<li>String : Username = "None"</li>
 * 		<li>String : Passphrase = "None"</li>
 * 		<li>String : IDCard = "None"</li>
 * 		<li>Boolean : Voted = "false"</li>
 * </ul>
 */
class Voter {
	/** This String will store the Name of the user */
	String Identity = "None";
	/** This string will store the username of the user */
	String UserName = "None";
	/** This string will store the password of the user */
	String Passphrase = "None";
	/** This String will store the ID Card number of the user */
	String IDCard = "None";
	/** This boolean will determine if the user has voted or not. */
    Boolean Voted = false;
	
	/** This is the contructor for the Voter class. Idk much about constructors. And there is nothing special about them.*/
    public Voter(String IdentityIn, String UsernameIn, String PassphraseIn, String IDCardIn){
        Identity = IdentityIn;
        UserName = UsernameIn;
        Passphrase = PassphraseIn;
        IDCard = IDCardIn;
    }
}

/**
 * This is the Candiate class.
 * This class contails the data of the candaidates. An attribute that is not inherited from the Voter class is the vote count attribute that contains how many votes the candidate has.
 * <br>
 * <h2> Attributes </h2>
 * This is a list of all the attributes that is not inherited from parent class(es)
 * <ul>
 * 		<li> String : CandidateCity = "None"</li>
 * 		<li> String : CandidateParty = "None"</li>
 * 		<li> int : votecount = 0 </l1>
 * </ul>
 */
class Candidate extends Voter{
	/** This string variable store the city the candidate is originally from. */
	String CandidateCity = "None";
	/** This string variable stores the part the candidate represents. */
	String CandidateParty = "None";
	/** This integer variable stores the number of votes this candidate has gotten.*/
	int votecount = 0;
	
	/** This is the contructor for the candidate object. This class is inherits the attriubtes from the Voter class.*/
    public Candidate(String IdentityIn, String UsernameIn, String PassphraseIn, String IDCardIn) {
        super(IdentityIn, UsernameIn, PassphraseIn, IDCardIn);
    }

}

/**
 * This class is the officer class.
 * This class will contain all the data of the officer. It is extended to the Voter class so the Officer objects will also get to vote. This class has no special functions or variables since the system relies on user type to identify and authorise Officers.
 * 
 */
class Officer extends Voter{

//    int OfficerVerificationID = 0;
    public Officer(String IdentityIn, String UsernameIn, String PassphraseIn, String IDCardIn) {
        super(IdentityIn, UsernameIn, PassphraseIn, IDCardIn);
    }
}
