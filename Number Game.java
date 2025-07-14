import java.util.Scanner;
import java.util.Random;

public class NumberGame {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        int maxAttempts = 5;
        int score = 0;
        boolean playAgain;

        System.out.println("🎯 Welcome to the Number Guessing Game!");

        do {
            int targetNumber = random.nextInt(100) + 1;
            int attempts = 0;
            boolean guessedCorrectly = false;

            System.out.println("\nI'm thinking of a number between 1 and 100. Can you guess it?");
            System.out.println("You have " + maxAttempts + " attempts.");

            while (attempts < maxAttempts) {
                System.out.print("Enter your guess: ");
                int guess;

                // Validate user input
                if (scanner.hasNextInt()) {
                    guess = scanner.nextInt();
                } else {
                    System.out.println("❌ Please enter a valid number!");
                    scanner.next(); // discard invalid input
                    continue;
                }

                attempts++;

                if (guess == targetNumber) {
                    System.out.println("✅ Correct! You guessed the number in " + attempts + " attempts.");
                    score++;
                    guessedCorrectly = true;
                    break;
                } else if (guess < targetNumber) {
                    System.out.println("📉 Too low!");
                } else {
                    System.out.println("📈 Too high!");
                }

                System.out.println("Attempts left: " + (maxAttempts - attempts));
            }

            if (!guessedCorrectly) {
                System.out.println("❌ You've run out of attempts. The number was: " + targetNumber);
            }

            System.out.print("🔁 Do you want to play another round? (yes/no): ");
            scanner.nextLine(); // consume newline
            playAgain = scanner.nextLine().equalsIgnoreCase("yes");

        } while (playAgain);

        System.out.println("\n🎉 Game Over! Your total score: " + score);
        System.out.println("Thanks for playing!");

        scanner.close();
    }
}
