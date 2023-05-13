import java.io.File;
import java.io.FileWriter;
import java.io.*;
import java.util.*;
import java.awt.Font;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;
import java.io.IOException;

public class findLink
{
    public static String findLink(String word, int i) {
        String link = "";
        int n = 0;
        
        try {
            // Send a  request to the website with the given word
            Document doc = Jsoup.connect("https://wordincontext.com/en/" + word.toLowerCase()).get();

            // Extract the example sentence from the response
            Elements elems = doc.select("footer");
            for (Element elem : elems) {
                //when it finds the right link
                if (i == n) {
                    //get the value of the href attribute (the link)
                    link = elem.select("a").attr("href");
                }
                n = n + 1;
            }
        } catch (IOException e) {
            // Handle any errors or exceptions
            e.printStackTrace();
        }
        return link;
    }
}
