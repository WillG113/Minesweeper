import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        boolean restart = true;

        System.out.println("Play via console or gui? c/g");
        if(in.nextLine().equals("g")){

            boolean validIn = false;
            int x;
            do{
                System.out.println("What size game would you like to play?");
                x = in.nextInt();
                if(x > 2 && x < 76){
                    validIn = true;
                }
            }
            while(!validIn);

            GUI gt = new GUI(x, x);
            gt.setPreferredSize(new Dimension(1000, 1000));
            gt.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            gt.pack();
            gt.setVisible(true);


        } else {

            do {
                gameStart();

                System.out.println("\nPlay again? y/n");
                String input = in.nextLine();

                if (input.toLowerCase(Locale.ROOT).equals("n")) {
                    restart = false;
                }
            }
            while (restart);
        }
    }

    public static void gameStart() {
        Scanner in = new Scanner(System.in);
        boolean validInput;
        int x;

        do {
            System.out.println("Please enter the size of game - Games are square, so enter 10 for 10x10 etc. (Max game size is 75x75 and minimum is 3x3");
            x = in.nextInt();
            validInput = x >= 3 && x <= 75;
        }
        while(!validInput);

        Board newGame = new Board(x, x);
        newGame.createBoard();
        newGame.printBoard();

        do {
            int[] input = userInput(newGame.getX());
            if(input[0] == 1) {
                newGame.hit(input[2], input[1], false);
            } else if (input[0] == 2) {
                newGame.setFlag(newGame.getBoard()[input[2]][input[1]]);
            } else if (input[0] == 3) {
                newGame.bulk(input[2], input[1]);
            } else {
                System.out.println("Please enter a valid input");
            }
            newGame.printBoard();
        }
        while(!newGame.getGameOver());

        if(newGame.victoryState() == 1) {
            System.out.println("\nYOU WON! YOU FLAGGED THE MINES");
        } else if (newGame.victoryState() == 2) {
            System.out.println("\nYOU WON! YOU CLEARED ALL THE TILES");
        } else {
            newGame.flagAllBombs();
            System.out.println("\nYOU LOST");
        }
    }

    public static int[] userInput(int x) {
        Scanner input = new Scanner(System.in);
        boolean validInput = false;
        String[] userInputArray;

        do {
            System.out.println("\nEnter a command and coordinates - first number is horizontal, second is vertical");
            System.out.println("\nCommand List:");
            System.out.println("clear / c - clears the tile entered (e.g clear 5 3)");
            System.out.println("flag / f - places a flag on the tile entered (e.g flag 2 3)");
            System.out.println("bulk / b - clears a 3x3 of tiles around at the tile entered (e.g bulk 5 3)\n");

            String stringInput = input.nextLine();
            userInputArray = stringInput.split(" ");

            if (userInputArray.length == 3) {
                if (Integer.parseInt(userInputArray[1]) <= x && Integer.parseInt(userInputArray[2]) <= x) {
                    validInput = true;
                }
            } else {
                System.out.println("Enter a valid input");
            }
        }
        while(!validInput);


        String command = userInputArray[0].toLowerCase();
        int action;


        if (command.equals("clear") || command.equals("c")) {
            action = 1;
        } else if (command.equals("flag") || command.equals("f")) {
            action = 2;
        } else if (command.equals("bulk") || command.equals("b")) {
            action = 3;
        } else {
            action = 0;
        }

        return new int[] {action, Integer.parseInt(userInputArray[1]) - 1, Integer.parseInt(userInputArray[2]) - 1};
    }
}