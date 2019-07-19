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

        System.out.println("Non JSON Voting System.");
        System.out.println("Version: 0.0.0");
        System.out.println("-----------------------");
        System.out.println("");

        Scanner VariableInput = new Scanner(System.in);
        while(true){

            
            // so no shit.. enter the username
            // because... reasons
            System.out.println("[!] Enter Username : ");
            String UsernameAttempt = VariableInput.nextLine();
            
            // enter the password...
            System.out.println("[!] Enter Password : ");
            String PasswordAtttempt = VariableInput.nextLine();
            boolean LoginValid = ValidateLogin(UsernameAttempt, PasswordAtttempt, "0x03");
            
            // now we are going to print the validation
            // if the system is correct
            if (LoginValid){ 
                System.out.println("[+] Login Accepted!");
            } else { 
                System.out.println("[-] Login Declined!");
            }
            System.out.println("");
            
            // enter an infinite loop...
            while(LoginValid){
                System.out.println("Welcome to main menu");
                System.out.println("Log in as : " + getUserDetails(UsernameAttempt, 3, 1));
                System.out.println("--------------------");
                System.out.println("");
                System.out.println("[1]\t\tAdd Voter");
                System.out.println("[2]\t\tAdd Candidate");
                System.out.println("[3]\t\tAdd Officer");
                System.out.println("[4]\t\tRemove User");
                System.out.println("[5]\t\tStart Vote");
                System.out.println("");
                System.out.println("[+] Enter a choice : ");
                String choice = VariableInput.nextLine();
                
                if (choice.equals("1")){
                    CreateUser("voter", "0x01");
                } else if (choice.equals("2")){
                    CreateUser("candidate", "0x02");
                } else if (choice.equals("3")){
                    CreateUser("officer", "0x03");
                } else if (choice.equals("4")){
                    DeleteUser();
                } else if (choice.equals("5")){
                    clearConsole();
                    StartVote();
                } else {
                    System.out.println("[-] Invalid choice. Try again.");
                }             
//                break;
            }
        }
    }
    
    static ArrayList<String[]> Data = LoadData();
    public static Voter[] voterList = classifyVoters(Data);
    public static Candidate[] candidateList = classifyCandidates(Data);
    public static Officer[] officerList = classifyAdmins(Data);
    public static boolean loginSuccessful = false;
    public static boolean stillVoting = true;
    public static int voteCount = 0;
    
    static void StartVote() {
        // So this is the functiion that will loop undefietly untill
		// the system completes voting...
		int totalVoters = voterList.length + candidateList.length + officerList.length;
        
        while (stillVoting){
            if (totalVoters <= voteCount){
                stillVoting = false;
            }
            System.out.println("[+] Welcome to the voting center.");
            System.out.println("[+] Please enter your credentials.");
            String AttemptedUsername = getValidInput("[+] Enter a username : ", 8);
            String AttemptedPassword = getValidInput("[+] Enter a password : ", 8);
            System.out.println("[+] Validating login...");
            
            
//            /**
//             Ok so now we are going to validate the login credentials... But we cannot
//             use the ValidateLogin() function because, again stupid Javas design doesn't
//             allow us to get the class object index... Stupid Java
//             **/
            
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
    
    static void LotsOfLines(){
		// what this function does is print a lot of
		// lines until the previous data is scrolled
		// up
        for (int i = 0; i < 300; i++){
			System.out.println("");
		}
    }
    
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
				System.out.println("Please make a selection.");
				int selectedIndex = rangedInput(1, candidateList.length);
				candidateList[selectedIndex - 1].votecount ++;
				voterList[index].Voted = true;
				// *****************************************************
            } else {
                System.out.println("You have already voted. Go away.");
            }
        } else if (userType.equals("0x02")) {
			System.out.println("Welcome candidate " + candidateList[index].UserName);
			System.out.println("You have " + candidateList[index].votecount + " votes.");
			if (!candidateList[index].Voted){
				System.out.println("Seems like you haven't voted yet. So please make a selection.");
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
			System.out.println("<< Admin Usage >>");

			if (!officerList[index].Voted){
				System.out.println("Seems like you haven't voted.");
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
			}

			if (matchInputBool("Type in END to end the vote", "END")){
				stillVoting = false;
			}
		}
        System.out.println("");
	}

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
    
    static Voter[] classifyVoters(ArrayList<String[]> Input){
        // so what this function does is accept a ArrayList<String[]> as
        // input and turn the users in that object into a array of class objects
        // we defined. But since this is java and we cannot return more than one value
        // and we cannot stack said values in a single array because in java all the values
        // in an array have to have the same value...
        ArrayList<Voter> ReturnList = new ArrayList<Voter>();
        for (int i = 0; i < Input.size(); i++){
            if (Input.get(i)[0].equals("0x01")){
                Voter Current = new Voter(Input.get(i)[3], Input.get(i)[1], Input.get(i)[2], Input.get(1)[3]);
//                Current.Identity = Input.get(i)[3];
//                Current.Passphrase = Input.get(i)[2];
//                Current.UserName = Input.get(i)[1];
                ReturnList.add(Current);
            }
        }
        // Here we are making the return object for the array
        Voter[] FinalSpace = new Voter[ReturnList.size()];
        for (int i = 0; i < FinalSpace.length; i++){
            FinalSpace[i] = ReturnList.get(i);}
        return(FinalSpace);
    }
    
    static Candidate[] classifyCandidates(ArrayList<String[]> Input){
        ArrayList<Candidate> ReturnList = new ArrayList<Candidate>();
        for (int i = 0; i < Input.size(); i++){
            if (Input.get(i)[0].equals("0x02")){
                Candidate Current = new Candidate(Input.get(i)[3], Input.get(i)[1], Input.get(i)[2], Input.get(1)[3]);
//                Current.Identity = Input.get(i)[3];
//                Current.Passphrase = Input.get(i)[2];
//                Current.UserName = Input.get(i)[1];
                ReturnList.add(Current);
            }
        }
        Candidate[] FinalSpace = new Candidate[ReturnList.size()];
        for (int i = 0; i < FinalSpace.length; i++){
            FinalSpace[i] = ReturnList.get(i);}
        return(FinalSpace);
    }
    
    static Officer[] classifyAdmins(ArrayList<String[]> Input){
        ArrayList<Officer> ReturnList = new ArrayList<Officer>();
        for (int i = 0; i < Input.size(); i++){
            if (Input.get(i)[0].equals("0x03")){
                Officer Current = new Officer(Input.get(i)[3], Input.get(i)[1], Input.get(i)[2], Input.get(1)[3]);
//                Current.Identity = Input.get(i)[3];
//                Current.Passphrase = Input.get(i)[2];
//                Current.UserName = Input.get(i)[1];
                ReturnList.add(Current);
            }
        }
        Officer[] FinalSpace = new Officer[ReturnList.size()];
        for (int i = 0; i < FinalSpace.length; i++){
            FinalSpace[i] = ReturnList.get(i);}
        return(FinalSpace);
    }
    /** ========================================================================================= **/
    /** ========================================================================================= **/
    
    static String getRandomHexString(int numchars){
        // this function will generate a 10 character
        // hex string...
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        while(sb.length() < numchars){
            sb.append(Integer.toHexString(r.nextInt()));
        }
        return sb.toString().substring(0, numchars);
    }
    
    static String getValidInput(String message, Integer min){
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
    
    static void CreateUser(String UserTypeName, String TypeCodeIn) {
        // So this function creates a user and accepts a string for the 
        // name of the UserTypeName and a TypeCode that defines the type of 
        // user
        String TypeCode = TypeCodeIn;
        String UserID = getRandomHexString(8);
        String VoterName = getValidInput("Enter " + UserTypeName + " full name : ", 8);
        String UserPass = getValidInput("Enter a password", 8);
        WriteLine(AddUser(TypeCode, VoterName, UserPass, UserID));
        System.out.println("A " + UserTypeName + " has been added with user ID of " + UserID);
    }
    
    static void DeleteUser(){
        // this function deletes the user 
        Scanner VariableInput = new Scanner(System.in);
        System.out.println("Enter the user id you'd like to delete :");
        String UserID = VariableInput.nextLine();
        ArrayList<String[]> data = LoadData();
        WriteLine(RemoveLineByMatch(data, 3, UserID));
        System.out.println("User has been removed");
    }
    
    static ArrayList<String[]> RemoveLineByMatch(ArrayList<String[]> Data, Integer Index, String Match){
        // so what this function will do is accept an arraylist and remove a line from the
        // ArrayList based on a match from the String Match from the Index provided in the 
        // variable index
        
        for (int i = 0; i < Data.size(); i++){
            // here we are going to loop through all the
            // lines until we find one that matches all the
            // requirements and remove it
            if (Data.get(i)[Index].equals(Match)){
                System.out.println("Removing matching line...");
                Data.remove(i);
                return(Data);
            }
        }
        return(Data);
    }
    
    static boolean WriteLine(ArrayList<String[]> Data) {
        // So what this function does is write the data to the file
        // based off the parameter ArrayList
        try {
            // put a try-catch clause because this is java and it so
            // picky about everything and I hate it...
            PrintWriter pw = new PrintWriter(new FileWriter("data.txt"));
            for (int i = 0; i < Data.size(); i++) {
                    String[] CurrentUser = Data.get(i);
                    pw.write(CurrentUser[0]+","+CurrentUser[1]+","+CurrentUser[2]+","+CurrentUser[3]+"\n");
            }
            // close the files.
            pw.close();
            return(true);
        } catch (IOException e){
            System.out.println("IO Exception error : " + e);
            return(false);
        }
    }
    
    static ArrayList<String[]> AddUser(String TypeCode, String UserName, String Password, String UserID){
        // So what this function does is add a user to the arraylist data from
        // the file and add lines according to the parameters provided
        ArrayList<String[]> Data = LoadData();
        String[] DataLine = {TypeCode, UserName, Password, UserID};
        Data.add(DataLine);
        return(Data);
    }
    
    static boolean ValidateLogin(String Username, String Password, String UserType){
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
    
    static String getUserDetails(String username, Integer indexId, Integer returnId){
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
    
    static ArrayList<String[]> LoadData(){
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

class Voter {
    String Identity = "None";
    String UserName = "None";
    String Passphrase = "None";
    String IDCard = "None";
    Boolean Voted = false;
    
    public Voter(String IdentityIn, String UsernameIn, String PassphraseIn, String IDCardIn){
        Identity = IdentityIn;
        UserName = UsernameIn;
        Passphrase = PassphraseIn;
        IDCard = IDCardIn;
    }
}

class Candidate extends Voter{
//    String CandidateCity = "None";
//    String CandidateParty = "None";
    int votecount = 0;
    public Candidate(String IdentityIn, String UsernameIn, String PassphraseIn, String IDCardIn) {
        super(IdentityIn, UsernameIn, PassphraseIn, IDCardIn);
    }

}

class Officer extends Voter{
//    int OfficerVerificationID = 0;
    public Officer(String IdentityIn, String UsernameIn, String PassphraseIn, String IDCardIn) {
        super(IdentityIn, UsernameIn, PassphraseIn, IDCardIn);
    }
}
