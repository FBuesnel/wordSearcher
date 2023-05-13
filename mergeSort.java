import java.io.File;
import java.io.FileWriter;
import java.io.*;
import java.util.*;
import java.util.ArrayList;

public class mergeSort
{
    static ArrayList<word> arr;
    public static ArrayList<word> sortSentences(File f) {
        ArrayList<word> wordAndSentences = new ArrayList<word>();
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

                // Add the word and sentence to the object x and add x to the Stack
                x.word = word;
                x.sentence = sentence;
                wordAndSentences.add(x);
            }
        } catch (IOException e) {
            System.out.println("Error occured while reading file: " + e.getMessage());
        }
        //sort the array using merge Sort as it's the fastest
        wordAndSentences = mergeSort(wordAndSentences, wordAndSentences.size());
        return wordAndSentences;
    }

    public static ArrayList<word> mergeSort(ArrayList<word> arr, int n){
        if(n >= 2){
            int mid = n / 2;

            ArrayList<word> leftArray = new ArrayList<word>(arr.subList(0, mid));
            ArrayList<word> rightArray = new ArrayList<word>(arr.subList(mid, n));
            mergeSort(leftArray, mid);
            mergeSort(rightArray, n - mid);

            merge(arr, leftArray, rightArray, mid, n - mid);
        } else {
            return arr;
        }
        return arr;
    }

    public static void merge(ArrayList<word> arr, ArrayList<word> leftArray, ArrayList<word> rightArray, int left, int right){
        int i, j, k;
        i = j = k = 0;
        while(i < left && j < right){
            if(leftArray.get(i).word.compareTo(rightArray.get(j).word) <= 0){
                arr.set(k++, leftArray.get(i++));
            }else{
                arr.set(k++, rightArray.get(j++));
            }
        }

        while(i < left){
            arr.set(k++, leftArray.get(i++));
        }

        while(j < right){
            arr.set(k++, rightArray.get(j++));
        }
    }
}

