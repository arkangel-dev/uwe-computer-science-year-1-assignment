package voter;

import java.util.Random;

public class trivial{
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
}