import java.util.Scanner;

public class SafeInput {
    public static String getNonZeroLenString(Scanner pipe, String prompt) {
        String retString;
        do {
            System.out.print(prompt + ": ");
            retString = pipe.nextLine();
        } while (retString.isEmpty());
        return retString;
    }

    public static int getInt(Scanner pipe, String prompt) {
        int retInt;
        do {
            System.out.print(prompt + ": ");
            while (!pipe.hasNextInt()) {
                System.out.println("Invalid input. Please enter an integer.");
                System.out.print(prompt + ": ");
                pipe.next(); // Consume invalid input
            }
            retInt = pipe.nextInt();
            pipe.nextLine(); // Consume newline character
        } while (retInt <= 0); // Allow only positive integers
        return retInt;
    }

    public static int getRangedInt(Scanner pipe, String prompt, int low, int high) {
        int retInt;
        do {
            System.out.print(prompt + " (" + low + " - " + high + "): ");
            while (!pipe.hasNextInt()) {
                System.out.println("Invalid input. Please enter an integer within the range.");
                System.out.print(prompt + " (" + low + " - " + high + "): ");
                pipe.next(); // Consume invalid input
            }
            retInt = pipe.nextInt();
            pipe.nextLine(); // Consume newline character
        } while (retInt < low || retInt > high);
        return retInt;
    }

    public static boolean getYNConfirm(Scanner pipe, String prompt) {
        char userInput;
        do {
            System.out.print(prompt + " (Y/N): ");
            userInput = Character.toUpperCase(pipe.next().charAt(0));
            pipe.nextLine(); // Consume newline character
            if (userInput != 'Y' && userInput != 'N') {
                System.out.println("Invalid input. Please enter Y/y for Yes or N/n for No.");
            }
        } while (userInput != 'Y' && userInput != 'N');
        return userInput == 'Y';
    }

    public static String getRegExString(Scanner pipe, String prompt, String regEx) {
        String userInput;
        do {
            System.out.print(prompt + " ");
            userInput = pipe.nextLine();
            if (!userInput.matches(regEx)) {
                System.out.println("Invalid input. Please enter a valid input that matches the specified pattern.");
            }
        } while (!userInput.matches(regEx));
        return userInput;
    }
}