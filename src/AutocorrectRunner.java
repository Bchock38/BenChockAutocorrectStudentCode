//import java.io.BufferedReader;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.Scanner;
//
//
//public class AutocorrectRunner {
//
//    private String[] dictionary;
//    private int threshold;
//
//    public AutocorrectRunner() {
//        dictionary = loadDictionary("large");
//        threshold = 2;
//    }
//    static public void main (String[] args){
//        boolean play = true;
//        while (play == true){
//            Autocorrect auto = new Autocorrect();
//        }
//    }
//
//
//    public void getInput(){
//        Scanner input = new Scanner(System.in);
//        System.out.println("Enter a Word: ");
//        String misspelled = input.nextLine();
//        System.out.println("You typed: " + misspelled);
//        if (auto.findWord(misspelled,0,misspelled.length())){
//            System.out.println("~~~~~~~~");
//        }
//        else{
//            System.out.println("Did you mean ...");
//            String[] close = auto.runLD(misspelled,2);
//            for (String s : close) {
//                System.out.println(s);
//            }
//            System.out.println("~~~~~~~~");
//        }
//    }
//
//    private static String[] loadDictionary(String dictionary)  {
//        try {
//            String line;
//            BufferedReader dictReader = new BufferedReader(new FileReader("dictionaries/" + dictionary + ".txt"));
//            line = dictReader.readLine();
//
//            // Update instance variables with test data
//            int n = Integer.parseInt(line);
//            String[] words = new String[n];
//
//            for (int i = 0; i < n; i++) {
//                line = dictReader.readLine();
//                words[i] = line;
//            }
//            return words;
//        }
//        catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//    private static String[] loadDictionary(String dictionary)  {
//        try {
//            String line;
//            BufferedReader dictReader = new BufferedReader(new FileReader("dictionaries/" + dictionary + ".txt"));
//            line = dictReader.readLine();
//
//            // Update instance variables with test data
//            int n = Integer.parseInt(line);
//            String[] words = new String[n];
//
//            for (int i = 0; i < n; i++) {
//                line = dictReader.readLine();
//                words[i] = line;
//            }
//            return words;
//        }
//        catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
