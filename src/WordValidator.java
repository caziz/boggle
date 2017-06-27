import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
/**
 * This class will take the input from a user and determine if
 * the input is present in the offline database  
 * @return returns true if the word is present in the database, if it is not found returns false.
 *
 */
public class WordValidator {

	static List<String> validWords = new ArrayList<String>();

	// create word validator and load valid word list
	public WordValidator() {
		try {
			File file = new File("validWords.txt");
			Scanner sc = new Scanner(file);
			while (sc.hasNextLine()) {
				//make upper case, just in case they aren't in the file that way
				String validWord = sc.next();
				validWords.add(validWord.toUpperCase());
			}
			//System.out.println("file has " + validWords.size() + " words");
			sc.close();
		} catch (FileNotFoundException e) {
			//System.out.println(e.getLocalizedMessage());
			e.printStackTrace();
		}

		Collections.sort(validWords);

	}

	// check if word is valid
	public static boolean isWordValid(String word) {
		if (word == null) {
			return false;
		}
		//validWords are all upper case
		int bIdx = Collections.binarySearch(validWords, word.toUpperCase());
		return (bIdx >= 0);
	}
}



