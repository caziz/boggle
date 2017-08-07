import java.io.File;
import java.io.FileNotFoundException;
import java.util.Set;
import java.util.HashSet;
import java.util.Scanner;

/**
* This class will take the input from a user and determine if
* the input is present in the offline database
* @return returns true if the word is present in the database, if it is not found returns false.
*/
public class WordValidator {
    static Set<String> validWords = new HashSet<String>();

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
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    // check if word is valid
    public static boolean isWordValid(String word) {
        return word != null && validWords.contains(word);
    }
}
