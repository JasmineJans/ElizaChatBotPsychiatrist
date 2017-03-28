import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * @author Jasmine Jans
 * @version HW 2: My Eliza
 * date 2/09/2016
 * class CPSC 224 02
 * professor Worobec
 * 
 * This program takes 2 files and loads them into 3 different String arrays.
 * One array holds default prompts, one holds terms that would possibly be entered
 * by a user, and the third holds prompts that would match these terms. Then this 
 * program takes user input and attempts to match it with a value in the terms array
 * and find the correlating prompt to the term and print that out. This continues in a loop
 * until the user enters exit and then the program ends.
 */

public class MyEliza {
	//String array of the possible terms in an Eliza script
	private String[] terms;
	//String array of the possible prompts in an Eliza script
	private  String[] prompts;
	//String array of the default prompts in an Eliza script 
	private  String[] defaults;

	//integer to track the matches of terms in a file and user input 
	private  int matches;
	//integer to track the responses to terms given throughout the program
	private  int responses;

	/**
	 * This method initializes the defaults array by taking in a 
	 * file and loading the strings into the array.
	 * 
	 * @param defaultsFile, a file to load into the defaults array
	 * @throws FileNotFoundException if a file is not found 
	 */
	public void initializeDefaults(File defaultsFile) throws FileNotFoundException{
		Scanner defaultsIn = new Scanner(defaultsFile);
		defaults = new String[(int) fileLineCount(defaultsFile)];
		int i = 0;
		while(defaultsIn.hasNextLine()){
			defaults[i] = defaultsIn.nextLine();
			i++;
		}
	}

	/**
	 * This method initializes the prompts and terms arrays by taking in a 
	 * file and loading the strings into the arrays where each line alternates
	 * starting with terms at line 0 and then prompts line 1 etc.
	 * 
	 * @param termsFile a file to load into the terms and prompts file
	 * @throws FileNotFoundException if a file is not found
	 */
	public void initializeTerms(File termsFile) throws FileNotFoundException{
		Scanner termsIn = new Scanner(termsFile);
		int oddOrEven = 0;
		prompts = new String [fileLineCount(termsFile)/2];
		terms = new String[fileLineCount(termsFile)/2];

		while(termsIn.hasNextLine()){
			terms[oddOrEven] = termsIn.nextLine();
			prompts[oddOrEven] = termsIn.nextLine();
			oddOrEven++;
		}
	}

	/**
	 * This method returns the correlating prompt to a matched
	 * term from the user and in the terms array.
	 * 
	 * @param term a String that takes the terms given through 
	 * 		the scanner to compare to the terms array. 
	 */
	public void getPrompt(String term){
		Random random = new Random();
		String[] newTerm = term.split("\\s+");
		int currentMatch = 0;
		for(int i = 0; i < newTerm.length; i++){
			for(int j=0; j < terms.length; j++){
				if(terms[j]==null){
					j = terms.length;
				}
				else if (terms[j].equalsIgnoreCase(newTerm[i])){
					System.out.println(prompts[j]);
					currentMatch ++;
					matches++;
					responses++;
					j = terms.length;
					i = newTerm.length;
				}
			}
		}
		if(currentMatch == 0){
			//System.out.println(random.nextInt(defaults.length));
			if(defaults[random.nextInt(defaults.length)]!= null){
				System.out.println(defaults[random.nextInt(defaults.length)]);
			}
			else{
				System.out.println(defaults[1]);
			}
			responses++;
		}
	}

	//Extra Methods
	/**
	 * This method gets the number of lines in a given file
	 * 
	 * @param a File to have its lines counted
	 * @return an integer that represents the number of lines in a file
	 * @throws FileNotFoundException if file not found
	 */
	private int fileLineCount(File inFile) throws FileNotFoundException{
		int lineCount = 0;
		Scanner in = new Scanner(inFile);
		while(in.hasNextLine()){
			in.nextLine();
			lineCount++;
		}
		return lineCount;
	}

	/**
	 * This method gets the amount of responses made throughout the
	 * users interaction with MyEliza
	 * 
	 * @return an integer that represents the number of responses
	 */
	public int getResponses(){
		return responses;
	}

	/**
	 * This method gets the amount of matches made throughout the
	 * users interaction with MyEliza
	 * 
	 * @return an integer that represents the number of matches
	 */
	public int getMatches(){
		return matches;
	}

	/**
	 * Main function that creates a MyEliza object and tests its
	 * function.
	 * @param args two files to be loaded into arrays Default, Prompts and Terms
	 * @throws FileNotFoundException if the files are not found
	 */
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		
		//WILL PRODUCE ERROR IN ECLIPSE: only if running in main
		///*
		if(args.length < 2){
	          System.out.println("You did not provide enough parameters! We need two parameters here!");
	          System.exit(1);
		}
		//*/
		MyEliza eliza1 = new MyEliza();
		String termsFileName = "terms.txt";
		String defaultsFileName = "defaults.txt";

		File defaultsFile = new File (defaultsFileName);
		eliza1.initializeDefaults(defaultsFile);

		File termsFile = new File(termsFileName);
		eliza1.initializeTerms(termsFile);

		System.out.println("Hello, I am the psychotherapist. Tell me your problems!");
		System.out.println("Hit return after each entry and no punctuation please.");

		boolean loop = true;
		Scanner in = new Scanner(System.in);
		while(loop){
			String line = in.nextLine();
			if(line.equalsIgnoreCase("exit")){
				loop = false;
				System.out.println("Thanks for listening to my " + eliza1.getResponses() + " response(s)");
				System.out.println("that came from our " + eliza1.matches + " match(es)!");
			}
			else{
				eliza1.getPrompt(line);
			}
		}
		in.close();
	}

}
