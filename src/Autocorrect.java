import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

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
    public Autocorrect(String[] words, int threshold) {
        this.words = words;
        this.threshold = threshold;
    }

    public int findLD (String n, String m){
        int[][] LD = new int[n.length()+1][m.length()+1];
        for (int i = 0; i <= n.length(); i++){
            LD[i][0] = i;
        }
        for (int j = 0; j <= m.length(); j++){
            LD[0][j] = j;
        }
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
        return LD[n.length()][m.length()];
    }
    /**
     * Runs a test from the tester file, AutocorrectTester.
     * @param typed The (potentially) misspelled word, provided by the user.
     * @return An array of all dictionary words with an edit distance less than or equal
     * to threshold, sorted by edit distnace, then sorted alphabetically.
     */
    public String[] runTest(String typed) {
        ArrayList<LDword> holder = new ArrayList<LDword>();
        int LD = 0;
        Arrays.sort(words);
        for (int i = 0; i < words.length; i++){
            if (words[i].length() <= typed.length()+threshold){
                LD = findLD(typed, words[i]);
                if (LD <= threshold){
                    holder.add(new LDword(LD,i));
                }
            }
        }
        holder.sort(Comparator.comparing(LDword::getLD));
        String[] close = new String[holder.size()];
        for (int i = 0; i < close.length; i++){
            close[i] = words[holder.get(i).getLoc()];
        }
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