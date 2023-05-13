import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.*;

import java.net.URL;
import java.net.URI;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;

public class GUI extends JFrame
{
    //used 15 so user has more space to see and type the word
    static JTextField wordField = new JTextField(15);
    static JTextArea exampleSentenceLabel, sentenceA, sentenceB, sentenceC, sentenceD, welcomeLabel2;
    static int i, correctSentence, points;

    //create the Stack<word>
    static Stack<word> wordSentence;

    static JPanel flowPanel, mainPanel, cardPanel, welcomePanel, gamePanel, resultPanel, gameflowPanel, topGamePanel, winLoseFlowPanel, welcomePanel2, flowWelcomePanel;
    static JLabel wordLabel, gameQuestion, winLoseLabel, pointsLabel, welcomeLabel, welcomeLabel3, welcomeLabel4;
    static JButton searchBtn, forwardBtn, backBtn, recordBtn, gameBtn, getLink, startBtn, aBtn, bBtn, cBtn, dBtn, quitBtn, quitBtn2, playAgain;

    //creates the cardlayout
    static CardLayout cardLayout = new CardLayout();

    //create the ArrayList to be populated by sentences so all classes can use it.
    static ArrayList<String> exampleSentences = new ArrayList<String>();

    public GUI(){
        super("Context Searcher");
        setSize(950, 450);

        //instantiate all the different panels
        JPanel flowPanel = new JPanel(new FlowLayout());
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel welcomePanel = new JPanel(new GridLayout(2, 1));
        //first is rows, seconds is columns
        JPanel gamePanel = new JPanel(new GridLayout(5, 1));
        JPanel gameflowPanel = new JPanel(new FlowLayout());
        JPanel topGamePanel = new JPanel(new BorderLayout());
        JPanel winLoseFlowPanel = new JPanel(new FlowLayout());
        JPanel resultPanel = new JPanel(new BorderLayout());
        JPanel welcomePanel2 = new JPanel(new GridLayout(4, 1));
        JPanel flowWelcomePanel = new JPanel(new FlowLayout());
        cardPanel = new JPanel();
        cardPanel.setLayout(cardLayout);

        cardPanel.add(welcomePanel, "Welcome"); //Welcome panel
        cardPanel.add(mainPanel, "Main"); //Main Panel
        cardPanel.add(gamePanel, "Game"); //Game Panel
        cardPanel.add(resultPanel, "Result"); //Result panel for both Win and Loss Panel

        //creates a custom color for the program
        Color green = new Color(72, 199, 137);
        //set flow panels to different color to differentiate them in the program.
        gameflowPanel.setBackground(green);
        flowPanel.setBackground(green);
        winLoseFlowPanel.setBackground(green);

        // Set the flow panel as the top component of the border layout
        mainPanel.add(flowPanel, BorderLayout.NORTH);

        //Show the welcome panel

        cardLayout.show(cardPanel, "Welcome");

        welcomeLabel = new JLabel("Word in Context Searcher", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Times New Roman", Font.BOLD, 24));
        welcomeLabel3 = new JLabel("Press the button below to enter into the program.", SwingConstants.CENTER);
        welcomeLabel3.setFont(new Font("Times New Roman", Font.BOLD, 20));
        welcomeLabel2 = new JTextArea("This program allows a user to input a word and search for a sentence which contains that word in context. A user can choose from ten sentences and record it to a text file. There is also a game which can be played once at least four sentences have been recorded. It presents the user with a word and four sentences which may contain it. The user is asked to select the correct sentence which contains the word. Please enjoy the program!");
        welcomeLabel2.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        //sets the welcomeLabel opaque to make it less noticeable
        welcomeLabel2.setOpaque(false);

        welcomePanel2.add(welcomeLabel); welcomePanel2.add(welcomeLabel2); welcomePanel2.add(welcomeLabel3);
        welcomePanel.add(welcomePanel2);
        //creates a border for the welcomePanel with all the JLabels to make it look more aesthetic
        welcomePanel2.setBorder(BorderFactory.createLineBorder(Color.black, 3));

        //add button to welcome panel to switch to the main screen
        startBtn = new JButton("Start");
        startBtn.setForeground(Color.DARK_GRAY);
        startBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    cardLayout.show(cardPanel, "Main");
                }
            });
        flowWelcomePanel.add(startBtn);
        welcomePanel.add(flowWelcomePanel);

        //Add the JLabel of the word;
        wordLabel = new JLabel("    Word to Search For:    ");
        wordLabel.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        wordLabel.setSize(50, 20);
        flowPanel.add(wordLabel);

        //Adds the text box for the word to lookup
        wordField.setPreferredSize(new Dimension(50, 20));
        flowPanel.add(wordField);

        //First Button to Search for the Word
        searchBtn = new JButton("Search");
        searchBtn.setForeground(Color.DARK_GRAY);
        //Ses the size of the button object to ensure it's not too large
        searchBtn.setPreferredSize(new Dimension(120, 20));
        searchBtn.addActionListener(new wordInContext());
        flowPanel.add(searchBtn);

        //Create button to go backwards through list of sentences.
        backBtn = new JButton("<--");
        backBtn.setForeground(Color.DARK_GRAY);
        backBtn.addActionListener(new Backward());
        backBtn.setPreferredSize(new Dimension(100, 20));
        flowPanel.add(backBtn);
        backBtn.setVisible(false);

        //Create button to go forward through list of sentences.
        forwardBtn = new JButton("-->");
        forwardBtn.setForeground(Color.DARK_GRAY);
        forwardBtn.addActionListener(new Forward());
        forwardBtn.setPreferredSize(new Dimension(100, 20));
        flowPanel.add(forwardBtn);
        forwardBtn.setVisible(false);

        //Adds a button that records the word and its sentence in context.
        recordBtn = new JButton("Record");
        recordBtn.setForeground(Color.DARK_GRAY);
        recordBtn.addActionListener(new recordSentence());
        recordBtn.setPreferredSize(new Dimension(100, 20));
        flowPanel.add(recordBtn);
        recordBtn.setVisible(false);

        //Adds a button that gets the link of a sentence and directs the user to that website
        getLink = new JButton("Click here to get the link of the sentence!");
        getLink.setForeground(Color.DARK_GRAY);
        getLink.addActionListener(new getLink());
        getLink.setPreferredSize(new Dimension(300, 30));
        mainPanel.add(getLink, BorderLayout.SOUTH);
        getLink.setVisible(false);

        //adds the sentence label for word in context
        exampleSentenceLabel = new JTextArea();
        float newSize = 18.0f; // set the new size here, has to be float for decimals
        Font currentFont = exampleSentenceLabel.getFont(); // get the current font
        Font newFont = currentFont.deriveFont(newSize); // derive a new font with the new size
        exampleSentenceLabel.setFont(newFont); // set the new font
        mainPanel.add(exampleSentenceLabel, BorderLayout.CENTER);
        // Set the line wrap and wrap style word properties to true
        exampleSentenceLabel.setLineWrap(true);
        exampleSentenceLabel.setWrapStyleWord(true);

        // Add the text area to the GUI in the center.
        mainPanel.add(exampleSentenceLabel, BorderLayout.CENTER);

        //Adds a button that sends the user to the game. Below this is all the code for the game JPanel
        gameBtn = new JButton("Game");
        gameBtn.setForeground(Color.DARK_GRAY);
        gameBtn.setPreferredSize(new Dimension(100, 20));
        gameBtn.addActionListener(new startGame());
        flowPanel.add(gameBtn);
        gameBtn.setVisible(true);

        //a button
        aBtn = new JButton("Sentence A");
        aBtn.setForeground(Color.DARK_GRAY);
        aBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    cardLayout.show(cardPanel, "Result");
                    if(correctSentence == 0) {
                        winLoseLabel.setText("You are correct!");
                        points++;
                    }
                    else winLoseLabel.setText("You are incorrect :(");
                }
            });
        aBtn.setPreferredSize(new Dimension(100, 20));
        aBtn.setVisible(true);

        //b button
        bBtn = new JButton("Sentence B");
        bBtn.setForeground(Color.DARK_GRAY);
        bBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    cardLayout.show(cardPanel, "Result");
                    if(correctSentence == 1) {
                        points++;
                        winLoseLabel.setText("You are correct!");
                    }
                    else winLoseLabel.setText("You are incorrect :(");
                }
            });
        bBtn.setPreferredSize(new Dimension(100, 20));
        bBtn.setVisible(true);

        //c button
        cBtn = new JButton("Sentence C");
        cBtn.setForeground(Color.DARK_GRAY);
        cBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    cardLayout.show(cardPanel, "Result");
                    if(correctSentence == 2) {
                        winLoseLabel.setText("You are correct!");
                        points++;
                    }
                    else winLoseLabel.setText("You are incorrect :(");
                }
            });
        cBtn.setPreferredSize(new Dimension(100, 20));
        cBtn.setVisible(true);

        //d button
        dBtn = new JButton("Sentence D");
        dBtn.setForeground(Color.DARK_GRAY);
        dBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    cardLayout.show(cardPanel, "Result");
                    if(correctSentence == 3) {
                        winLoseLabel.setText("You are correct!");
                        points++;
                    }
                    else winLoseLabel.setText("You are incorrect :(");
                }
            });
        dBtn.setPreferredSize(new Dimension(100, 20));
        dBtn.setVisible(true);

        //quit button
        quitBtn = new JButton("Quit");
        quitBtn.setForeground(Color.DARK_GRAY);
        quitBtn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    cardLayout.show(cardPanel, "Main");
                }
            });

        //second quit button for the lose/win screen
        quitBtn2 = new JButton("Quit");
        quitBtn2.setForeground(Color.DARK_GRAY);
        quitBtn2.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    cardLayout.show(cardPanel, "Main");
                }
            });

        //playAgain button
        playAgain = new JButton("Play Again?");
        playAgain.setForeground(Color.DARK_GRAY);
        playAgain.addActionListener(new startGame());

        //Game Question:
        gameQuestion = new JLabel("");
        gameQuestion.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        gameQuestion.setSize(50, 20);

        //Create panels for both lose and win to utilize.
        winLoseFlowPanel.add(quitBtn2);
        winLoseFlowPanel.add(playAgain);

        //Create the Lose JLabel;
        winLoseLabel = new JLabel("You are incorrect :(", SwingConstants.CENTER);
        winLoseLabel.setFont(new Font("Times New Roman", Font.BOLD, 30));
        resultPanel.add(winLoseLabel, BorderLayout.NORTH);
        resultPanel.add(winLoseFlowPanel, BorderLayout.SOUTH);

        //Amount of Points the user has
        pointsLabel = new JLabel("Points: 0");
        pointsLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        pointsLabel.setSize(50, 20);

        //add all the buttons to the flow panel
        gameflowPanel.add(gameQuestion); gameflowPanel.add(aBtn); gameflowPanel.add(bBtn); gameflowPanel.add(cBtn); gameflowPanel.add(dBtn); gameflowPanel.add(quitBtn); gameflowPanel.add(pointsLabel);
        //Instantiates all the JTextAreas for game
        sentenceA = new JTextArea("sentence a"); sentenceB = new JTextArea("sentence b"); sentenceC = new JTextArea("sentence c"); sentenceD = new JTextArea("sentence d");
        //makes all the JTextAreas wrap:
        sentenceA.setLineWrap(true); sentenceB.setLineWrap(true); sentenceC.setLineWrap(true); sentenceD.setLineWrap(true); welcomeLabel2.setLineWrap(true);
        sentenceA.setWrapStyleWord(true); sentenceB.setWrapStyleWord(true); sentenceC.setWrapStyleWord(true); sentenceD.setWrapStyleWord(true); welcomeLabel2.setWrapStyleWord(true);
        //makes all textfields uneditable
        sentenceA.setEditable(false); sentenceB.setEditable(false); sentenceC.setEditable(false); sentenceD.setEditable(false); exampleSentenceLabel.setEditable(false); welcomeLabel2.setEditable(false);
        //add all the elements to the game panel
        gamePanel.add(gameflowPanel); gamePanel.add(sentenceA); gamePanel.add(sentenceB); gamePanel.add(sentenceC); gamePanel.add(sentenceD);
    }   

    public static void main(String[] args){
        EventQueue.invokeLater(new Runnable(){
                @Override
                public void run(){
                    GUI window = new GUI();
                    window.add(cardPanel);
                    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    window.setResizable(false);
                    window.setVisible(true);
                }
            });
    }

    private static class wordInContext extends wordInContextScraper implements ActionListener{
        public void actionPerformed(ActionEvent e){
            class AnswerWorker extends SwingWorker<Void, Void>
            {
                protected Void doInBackground() throws Exception
                {
                    exampleSentences = getExampleSentence(wordField.getText());
                    return null;
                }

                protected void done()
                {
                    try {
                        searchBtn.setText("Search!");
                        exampleSentenceLabel.setForeground(Color.BLACK);
                        i = 0;
                        if (exampleSentences.size() != 0) exampleSentenceLabel.setText("Word in Context: " + exampleSentences.get(i));
                        else exampleSentenceLabel.setText("Word not found. Search something else.");

                        forwardBtn.setVisible(true);
                        backBtn.setVisible(true);
                        recordBtn.setVisible(true); 
                        getLink.setVisible(true);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            //Sets the text to loading while the program searches for sentences
            searchBtn.setText("Loading...");
            //Executes the AnswerWorker (above) 
            new AnswerWorker().execute();
        }

        // public static JTextArea boldWord(JTextArea label, String word) {
        // //find the index of the word in the sentence
        // String sentence = label.getText();

        // Font font = new Font("Times New Roman", Font.BOLD, 18);

        // label.setFont(font);

        // // Bold the word using the String.replace() method
        // String boldText = sentence.replace(word, "<b>" + word + "</b>");

        // // Set the bold text as the text of the label
        // label.setText("<html>" + boldText + "</html>");
        // return label;
        // }
    }

    private static class Forward implements ActionListener{
        public void actionPerformed(ActionEvent e){
            if (exampleSentences.size() == i) {
                exampleSentenceLabel.setText("There is no more sentences. Please choose from one of the previous available");
                i = exampleSentences.size() - 1;
            } else {
                i = i + 1;
                exampleSentenceLabel.setText("Word in Context: " + exampleSentences.get(i));
            }
        }
    }

    private static class Backward implements ActionListener{
        public void actionPerformed(ActionEvent e){
            if (i < 0) {
                exampleSentenceLabel.setText("There is no more sentences. Please choose from one of the previous available");
                i = 0;
            } else {
                i = i - 1;
                exampleSentenceLabel.setText("Word in Context: " + exampleSentences.get(i));
            }
        }
    }

    private static class recordSentence extends writeSentence implements ActionListener{
        public void actionPerformed(ActionEvent e){
            //Outputs to the user if recording of the sentence was a success
            if (recordSentence(exampleSentences.get(i), wordField.getText()) == true) {
                class AnswerWorker extends SwingWorker<Void, Void>
                {
                    protected Void doInBackground() throws Exception
                    {
                        try {Thread.sleep(1000);}
                        catch (InterruptedException ie) {ie.printStackTrace();}
                        return null;
                    }

                    protected void done()
                    {
                        try {
                            recordBtn.setText("Record");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                //Sets the text to success for a second to show the user.
                recordBtn.setText("Success!");
                //Executes the AnswerWorker (above) 
                new AnswerWorker().execute();
            } else {
                recordBtn.setText("Error!");
            }
        }
    }

    private static class getLink extends findLink implements ActionListener{
        public void actionPerformed(ActionEvent e){
            //calls the class to find the link
            String link = findLink(wordField.getText(), i);
            //below causes errors for some reason
            try {
                Desktop.getDesktop().browse(new URI(link));
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (URISyntaxException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static class startGame extends wordGame implements ActionListener{
        public void actionPerformed(ActionEvent e){
            cardLayout.show(cardPanel, "Game");
            //create ArrayList of Sentences
            try {
                if (wordSentence.size() <= 4) wordSentence = createList();
            } catch (Exception ie) {
                wordSentence = createList();
            }

            playGame();
        }
    }

    public static void playGame() {
        //shuffle the array list
        Collections.shuffle(wordSentence);
        Random random = new Random();
        //Make a temporary array list to store the popped elements
        ArrayList<String> sentencesForGame = new ArrayList<String>();
        // Generate a random integer to pick which sentence will be the correct one
        correctSentence = random.nextInt(4);
        for (int s = 0; s < 4; s++) {
            if (s == correctSentence) gameQuestion.setText("Which sentence sounds best with the word " + wordSentence.peek().word + " in it?");
            sentencesForGame.add(wordSentence.pop().sentence);
        }

        sentenceA.setText("Sentence A - " + sentencesForGame.get(0));
        sentenceB.setText("Sentence B - " + sentencesForGame.get(1));
        sentenceC.setText("Sentence C - " + sentencesForGame.get(2));
        sentenceD.setText("Sentence D - " + sentencesForGame.get(3));
        pointsLabel.setText("Points: " + points);
    }
}
