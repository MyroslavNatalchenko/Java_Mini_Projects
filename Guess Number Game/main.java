import java.util.Scanner;

class Main {
    public static void main(String args[]) {        
        // Generate the secret number between 0 and 100
        int secretNumber = (int)(Math.random() * 101);
        int userGuessNumber = 0;          
        int maxAttempts = 5; 
        
        System.out.println("You are playing the game: Guess the Number.");
        System.out.println("You have a maximum of 5 attempts to guess the correct number.\n");
        
        // Game loop: Continue until the user guesses correctly or runs out of attempts
        for (int attempts = 1; attempts <= maxAttempts; attempts++) {
            userGuessNumber = takeInput();

            if (userGuessNumber == secretNumber) {
                System.out.println("Congratulations! You've guessed the correct number.");
                break; // Exit the loop if the player guesses correctly
            } else if (userGuessNumber < secretNumber) {
                System.out.println("Your guess is too low. Try again.");
            } else {
                System.out.println("Your guess is too high. Try again.");
            }

            if (attempts == maxAttempts) {
                System.out.println("Sorry, you've used all your attempts. The correct number was " + secretNumber + ".");
            }
        }
    }

    public static int takeInput() {
        Scanner userInput = new Scanner(System.in);
        System.out.print("Enter a number between 0 to 100: ");
        int guess = 0;
        if (userInput.hasNextInt()) {
            guess = userInput.nextInt(); 
        } else {
            System.out.println("Enter a valid integer number between 0 and 100");
            userInput.next(); 
        }      
        return guess;
    }
}
