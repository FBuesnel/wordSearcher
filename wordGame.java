import java.io.*;
import java.util.*;
import java.util.ArrayList;

public class wordGame
{
    public static Stack<word> createList() {
        Stack<word> wordAndSentences = new Stack<word>();
        // Open the file for reading
        try {
            //opens the bufferedreader
            BufferedReader reader = new BufferedReader(new FileReader("sentencesInContext.txt"));
            // Read each line of the file
            String line;
            while ((line = reader.readLine()) != null) {
                // Create a word object to add to the arrayList
                word x = new word();
                // Split the line into the word and sentence at the point where there is five spaces
                String[] parts = line.split("     ", 3);
                String word = parts[0];
                String sentence = parts[1];
                
                //replace the part where the word is with a ___________ for the game
                sentence = sentence.replace(word, "___________");

                // Add the word and sentence to the object x and add x to the Stack
                x.word = word;
                x.sentence = sentence;
                wordAndSentences.push(x);
            }
        } catch (IOException e) {
            System.out.println("Error occured while reading file: " + e.getMessage());
        }
        //return ArrayList
        return wordAndSentences;
    }
}
