package voter;

import java.util.ArrayList;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.FileWriter;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import static java.lang.System.exit;

public class read_write{
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