import java.io.File;
import java.io.FileWriter;
import java.io.*;
import java.util.*;
import java.awt.Font;
import java.util.ArrayList;

//Takes arguments of a sentence and word. Then it records both of those into a text file called sentencesInContext. Returns a boolean to allow GUI
//to know if the writing was successful.
public class writeSentence extends mergeSort
{
    public static Boolean recordSentence(String sentence, String word) {
        File f = new File("sentencesInContext.txt");
        try {
            //Opens file writer
            FileWriter writer = new FileWriter(f, true);
            //Writes the word followed by its sentence in context that the use choose
            writer.write(word.toLowerCase() + "     " + sentence + "\n");
            //Closes file writer
            writer.close();
        } catch (IOException e) {
            //Handles any errors
            e.printStackTrace();
        } 
        ArrayList<word> array = sortSentences(f);
        try {
            //Opens file writer
            FileWriter writer = new FileWriter(f, false);
            for (int z = 0; z < array.size(); z++) {
                String word1 = array.get(z).word;
                String sentence1 = array.get(z).sentence;
                //Writes the word followed by its sentence in context that the use choose
                writer.write(word1.toLowerCase() + "     " + sentence1 + "\n");
            }
            //Closes file writer
            writer.close();
            return true;
        } catch (IOException e) {
            //Handles any errors
            e.printStackTrace();
            return false;
        }
    }
}
