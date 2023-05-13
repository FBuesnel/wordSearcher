import java.io.File;
import java.io.FileWriter;
import java.io.*;
import java.util.*;
import java.awt.Font;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

//Searches a website with a word and records the first outputted ten sentences to an ArrayList of type String
public class wordInContextScraper {
    public static ArrayList<String> getExampleSentence(String word) {
        ArrayList<String> sentences = new ArrayList<String>();
        try {
            // Send a  request to the website with the given word
            Document doc = Jsoup.connect("https://wordincontext.com/en/" + word.toLowerCase()).get();

            // Extract the example sentence from the response
            Elements elems = doc.select("blockquote");
            for (Element elem : elems) {
                String sentence = elem.text();
                if (sentence.toLowerCase().contains(word.toLowerCase())) {
                    sentences.add(sentence);
                }
            }
        } catch (IOException e) {
            // Handle any errors or exceptions
            e.printStackTrace();
        }

        return sentences;
    }
}
