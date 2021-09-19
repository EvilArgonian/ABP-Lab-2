package abp.lab.pkg2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author pmele
 */
public class AminoQuiz {
    
    
    private static ArrayList<String> SHORT_NAMES = new ArrayList<>( Arrays.asList( new String[] {
        "A","R", "N", "D", "C", "Q", "E", 
        "G",  "H", "I", "L", "K", "M", "F", 
        "P", "S", "T", "W", "Y", "V"
    }));
    
    private static ArrayList<String> FULL_NAMES = new ArrayList<>( Arrays.asList( new String[] {
        "alanine","arginine", "asparagine", 
        "aspartic acid", "cysteine",
        "glutamine",  "glutamic acid",
        "glycine" ,"histidine","isoleucine",
        "leucine",  "lysine", "methionine", 
        "phenylalanine", "proline", 
        "serine","threonine","tryptophan", 
        "tyrosine", "valine"
    }));
    
    private ArrayList<Integer> usedQuestions = new ArrayList<>();
    
    private int seconds;
    private long millisecondsTaken;
    
    private boolean questionsLimited;
    private int numQuestions;
    private int questionsLeft;
    
    private int correctAnswers;
    private int incorrectAnswers;
    private boolean endOnIncorrect;
    private Scanner input;
    private boolean keepRunning;

    public AminoQuiz() { //Default quiz, as described before Extra Credit.
        this.seconds = 30;
        this.millisecondsTaken = 0;
        this.numQuestions = -1;
        this.questionsLeft = this.numQuestions;
        this.correctAnswers = 0;
        this.incorrectAnswers = 0;
        this.endOnIncorrect = true;
        this.input = new Scanner(System.in);
        this.keepRunning = true;
    }
    
    /**
     * Custom Quiz
     * Can either be given a time limit or a question limit (not both)
     * Can be configured to end on an incorrect answer
     * 
     * @param questionsLimited
     * @param secondsOrQuestions
     * @param endOnIncorrect 
     */
    public AminoQuiz(boolean questionsLimited, int secondsOrQuestions, boolean endOnIncorrect) {
        if (questionsLimited) {
            this.seconds = -1;
            this.numQuestions = secondsOrQuestions;
        } else {
            this.seconds = secondsOrQuestions;
            this.numQuestions = -1;
        }
        this.millisecondsTaken = 0;
        this.questionsLeft = this.numQuestions;
        this.correctAnswers = 0;
        this.incorrectAnswers = 0;
        this.endOnIncorrect = endOnIncorrect;
        this.input = new Scanner(System.in);
        this.keepRunning = true;
    }
    
    public void quizMe() {
        correctAnswers = 0;
        incorrectAnswers = 0;
        long startTime = System.currentTimeMillis();
        while (keepRunning && (questionsLeft > 0 || millisecondsTaken < (seconds*1000))) {
            serveQuestion();
            questionsLeft--;
            millisecondsTaken = (System.currentTimeMillis() - startTime);
        }
        System.out.println("Quiz over! Time taken: " + (millisecondsTaken)/1000 + " seconds.");
        System.out.println("Score: " + correctAnswers);
        if (!endOnIncorrect) {
            System.out.println("Incorrect answers: " + incorrectAnswers);
        }
    }
    
    private void serveQuestion() {
        int index = -1;
        while (usedQuestions.contains(index) || index == -1) {
            index = new Random().nextInt(SHORT_NAMES.size());
        }
        if (usedQuestions.size() >= 8) { //Doesn't ever repeat the last 7 questions
            usedQuestions.remove(0);
        }
        usedQuestions.add(index);
        
        System.out.println("Amino Acid: " + FULL_NAMES.get(index) + "\t Enter single-letter code ('Quit' to quit): ");
        String answer = input.nextLine().toUpperCase(); //Obtain answer
        switch(answer) {
            case ("QUIT"):
                keepRunning = false;
                break;
            default:
                if (answer.equals(SHORT_NAMES.get(index))) {
                    System.out.println("Correct!");
                    correctAnswers++;
                } else {
                    System.out.println("Incorrect! Actually " + SHORT_NAMES.get(index));
                    incorrectAnswers++;
                    if (endOnIncorrect) {
                        keepRunning = false;
                    }
                }
                break;
        }
    }
}