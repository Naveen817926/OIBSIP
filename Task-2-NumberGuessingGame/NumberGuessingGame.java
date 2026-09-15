import java.util.Random;
import java.util.Scanner;

public class NumberGuessingGame {

    public static void main(String[] args) {

        Random random = new Random();
        Scanner sc = new Scanner(System.in);

        int round = 0;
        int totalScore = 0;
        boolean playAgain;

        System.out.println("======================================");
        System.out.println("       NUMBER GUESSING GAME");
        System.out.println("======================================");

        do {

            round++;

            int num = random.nextInt(100) + 1;
            int attempts = 0;
            boolean guessedCorrectly = false;

            System.out.println("\n---------- ROUND " + round + " ----------");
            System.out.println("I have selected a number between 1 and 100.");
            System.out.println("You have 7 attempts.");

            while (attempts < 7) {

                System.out.print("\nEnter your guess: ");

                if (!sc.hasNextInt()) {
                    System.out.println("Invalid input! Please enter a number.");
                    sc.next();
                    continue;
                }

                int guess = sc.nextInt();

                if (guess < 1 || guess > 100) {
                    System.out.println("Please enter a number between 1 and 100.");
                    continue;
                }

                attempts++;

                if (guess > num) {
                    System.out.println("Too High!");
                } else if (guess < num) {
                    System.out.println("Too Low!");
                } else {
                    System.out.println("Correct!");
                    System.out.println("Round " + round + " — guessed in " + attempts + " attempts.");

                    guessedCorrectly = true;
                    totalScore += (8 - attempts);

                    break;
                }

                System.out.println("Attempts remaining: " + (7 - attempts));
            }

            if (!guessedCorrectly) {
                System.out.println("\nYou Lost!");
                System.out.println("The correct number was: " + num);
                System.out.println("Round " + round + " — not guessed within 7 attempts.");
            }

            System.out.println("\n======================================");
            System.out.println("Current Score: " + totalScore);
            System.out.println("Rounds Played: " + round);
            System.out.println("======================================");

            System.out.print("\nPlay Again? (yes/no): ");

            String answer = sc.next();
            playAgain = answer.equalsIgnoreCase("yes");

        } while (playAgain);

        System.out.println("\n======================================");
        System.out.println("             GAME OVER");
        System.out.println("======================================");
        System.out.println("Total Rounds Played: " + round);
        System.out.println("Final Score: " + totalScore);
        System.out.println("Thank you for playing!");

        sc.close();
    }
}
