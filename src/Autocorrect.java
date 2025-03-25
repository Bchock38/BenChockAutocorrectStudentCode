import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Autocorrect
 * <p>
 * A command-line tool to suggest similar words when given one not in the dictionary.
 * </p>
 * @author Zach Blick
 * @author YOUR NAME HERE
 */
public class Autocorrect {

    /**
     * Constucts an instance of the Autocorrect class.
     * @param words The dictionary of acceptable words.
     * @param threshold The maximum number of edits a suggestion can have.
     */
    private String[] words;
    private int threshold;
    private HashSet wordSet;
    // Constructor that takes in dictionary and Word Threshold
    public Autocorrect(String[] words, int threshold) {
        this.words = words;
        this.threshold = threshold;
    }

    public Autocorrect (String dictionary, int threshold){
        String[] words = loadDictionary(dictionary);
        this.wordSet = new HashSet<>(List.of(words));
        this.words = words;
        this.threshold = threshold;
    }

    public static void main(String[] args) {
        Autocorrect auto = new Autocorrect("large", 2);
        boolean run = true;
        // Infinitely prompt for a word
        while (run){
            auto.getInput();
        }
    }

    public void getInput(){
        // Intialize Scanner
        Scanner input = new Scanner(System.in);
        // Prompt User for a word
        System.out.println("Enter a Word: ");
        String misspelled = input.nextLine();
        // Return word typed back to user
        System.out.println("You typed: " + misspelled);
        // If word is spelled correctly end
        if (wordSet.contains(misspelled)){
            System.out.println("~~~~~~~~");
        }
        else{
            String[] close = runLD(misspelled,2);
            // If no words in a LD distance of 2 are found return no matches found
            if (close.length == 0){
                System.out.println("No Matches Found.");
            }
            // Return up to five possible correct spellings
            else{
                System.out.println("Did you mean ...");
                for (int i = 0; i<5 && i < close.length; i++) {
                    System.out.println(close[i]);
                }
            }
            System.out.println("~~~~~~~~");
        }
    }

    // Find the Levenshtein Distance between two words
    public int findLD (String n, String m){
        int[][] LD = new int[n.length()+1][m.length()+1];
        // For all boxes at rist row and column fill with length of word/ part of word it's at
        for (int i = 0; i <= n.length(); i++){
            LD[i][0] = i;
        }
        for (int j = 0; j <= m.length(); j++){
            LD[0][j] = j;
        }
        // Tabulate through rest of array to find LD between the subset of words
        for (int i = 0; i < n.length(); i++){
            for (int j = 0; j < m.length(); j++){
                if (n.charAt(i) == m.charAt(j)){
                    LD[i+1][j+1] = LD[i][j];
                }
                else{
                    LD[i+1][j+1] = 1 + Math.min(LD[i][j+1], Math.min(LD[i+1][j],LD[i][j]));
                }
            }
        }
        // Return the LD between the two complete words
        return LD[n.length()][m.length()];
    }
    /**
     * Runs a test from the tester file, AutocorrectTester.
     * @param typed The (potentially) misspelled word, provided by the user.
     * @return An array of all dictionary words with an edit distance less than or equal
     * to threshold, sorted by edit distnace, then sorted alphabetically.
     */

    public String[] runLD (String typed, int thresh){
        ArrayList<LDword> holder = new ArrayList<LDword>();
        int LD = 0;
        // Sort the dictionary by alphabet
        Arrays.sort(words);
        // For all the words in the dictionary
        for (int i = 0; i < words.length; i++){
            // If the word is not bigger then the mispelled words length + threhold check the LD distance between it and the mispelled word
            if (words[i].length() <= typed.length()+thresh && words[i].length() >= typed.length()-thresh){
                LD = findLD(typed, words[i]);
                // If the LD is within the threshold add it an array
                if (LD <= thresh){
                    holder.add(new LDword(LD,i));
                }
            }
        }
        // Sort the array by LD distance
        holder.sort(Comparator.comparing(LDword::getLD));
        String[] close = new String[holder.size()];
        for (int i = 0; i < close.length; i++){
            close[i] = words[holder.get(i).getLoc()];
        }
        // Return the array of words
        return close;
    }

    public String[] runTest(String typed) {
        ArrayList<LDword> holder = new ArrayList<LDword>();
        int LD = 0;
        // Sort the dictionary by alphabet
        Arrays.sort(words);
        // For all the words in the dictionary
        for (int i = 0; i < words.length; i++){
            // If the word is not bigger then the mispelled words length + threhold check the LD distance between it and the mispelled word
            if (words[i].length() <= typed.length()+threshold){
                LD = findLD(typed, words[i]);
                // If the LD is within the threshold add it an array
                if (LD <= threshold){
                    holder.add(new LDword(LD,i));
                }
            }
        }
        // Sort the array by LD distance
        holder.sort(Comparator.comparing(LDword::getLD));
        String[] close = new String[holder.size()];
        for (int i = 0; i < close.length; i++){
            close[i] = words[holder.get(i).getLoc()];
        }
        // Return the array of words
        return close;
    }


    /**
     * Loads a dictionary of words from the provided textfiles in the dictionaries directory.
     * @param dictionary The name of the textfile, [dictionary].txt, in the dictionaries directory.
     * @return An array of Strings containing all words in alphabetical order.
     */
    private static String[] loadDictionary(String dictionary)  {
        try {
            String line;
            BufferedReader dictReader = new BufferedReader(new FileReader("dictionaries/" + dictionary + ".txt"));
            line = dictReader.readLine();

            // Update instance variables with test data
            int n = Integer.parseInt(line);
            String[] words = new String[n];

            for (int i = 0; i < n; i++) {
                line = dictReader.readLine();
                words[i] = line;
            }
            return words;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}