import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

    private static boolean needsToBeSaved = false;
    private static String currentFileName = "";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> list = new ArrayList<>();

        // Menu-driven loop
        String choice;
        do {
            // Display the menu
            displayMenu();
            // Get user choice
            choice = SafeInput.getRegExString(scanner, "Enter your choice (A/D/V/O/S/C/Q): ", "[AaDdVvOoSsCcQq]");
            // Execute the user's choice
            executeChoice(choice.toUpperCase().charAt(0), list, scanner); // Convert to upper case and get the first character
        } while (!choice.equalsIgnoreCase("Q")); // Repeat until user chooses to quit

        // Close the scanner
        scanner.close();
    }

    // Method to display the menu
    private static void displayMenu() {
        System.out.println("Menu:");
        System.out.println("A - Add an item to the list");
        System.out.println("D - Delete an item from the list");
        System.out.println("V - View the list");
        System.out.println("O - Open a list file from disk");
        System.out.println("S - Save the current list file to disk");
        System.out.println("C - Clear the current list");
        System.out.println("Q - Quit");
    }

    // Method to execute user's choice
    private static void executeChoice(char choice, ArrayList<String> list, Scanner scanner) {
        switch (choice) {
            case 'A':
                addItem(list, scanner);
                break;
            case 'D':
                deleteItem(list, scanner);
                break;
            case 'V':
                printList(list);
                break;
            case 'O':
                openListFromFile(list, scanner);
                break;
            case 'S':
                saveListToFile(list, scanner);
                break;
            case 'C':
                clearList(list);
                break;
            case 'Q':
                quitProgram(list, scanner);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    // Method to add an item to the list
    private static void addItem(ArrayList<String> list, Scanner scanner) {
        System.out.println("Adding an item to the list...");
        System.out.print("Enter the item to add: ");
        String item = scanner.nextLine();
        list.add(item);
        needsToBeSaved = true;
        System.out.println("Item added successfully.");
    }

    // Method to delete an item from the list
    private static void deleteItem(ArrayList<String> list, Scanner scanner) {
        if (list.isEmpty()) {
            System.out.println("The list is empty.");
            return;
        }
        System.out.println("Deleting an item from the list...");
        printListWithIndices(list);
        int index = SafeInput.getRangedInt(scanner, "Enter the number of the item to delete", 1, list.size()) - 1;
        String removedItem = list.remove(index);
        needsToBeSaved = true;
        System.out.println("Item '" + removedItem + "' removed successfully.");
    }

    // Method to print the list
    private static void printList(ArrayList<String> list) {
        System.out.println("Viewing the list...");
        printListWithIndices(list);
    }

    // Method to display the list with indices
    private static void printListWithIndices(ArrayList<String> list) {
        System.out.println("Current List:");
        if (list.isEmpty()) {
            System.out.println("The list is empty.");
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + ". " + list.get(i));
        }
    }

    // Method to open a list from a file
    private static void openListFromFile(ArrayList<String> list, Scanner scanner) {
        if (needsToBeSaved && !confirmSaveBeforeAction(scanner, "load")) {
            return;
        }
        System.out.print("Enter the file name to open (without extension): ");
        String fileName = scanner.nextLine().trim() + ".txt";
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("File does not exist.");
            return;
        }
        try {
            Scanner fileScanner = new Scanner(file);
            list.clear();
            while (fileScanner.hasNextLine()) {
                list.add(fileScanner.nextLine());
            }
            fileScanner.close();
            needsToBeSaved = false;
            currentFileName = fileName;
            System.out.println("List loaded successfully from " + fileName);
        } catch (IOException e) {
            System.out.println("Error occurred while reading the file: " + e.getMessage());
        }
    }

    // Method to save the list to a file
    private static void saveListToFile(ArrayList<String> list, Scanner scanner) {
        if (list.isEmpty()) {
            System.out.println("The list is empty. Nothing to save.");
            return;
        }
        String fileName = currentFileName;
        if (fileName.isEmpty()) {
            System.out.print("Enter the file name to save (without extension): ");
            fileName = scanner.nextLine().trim() + ".txt";
        }
        try {
            FileWriter writer = new FileWriter(fileName);
            for (String item : list) {
                writer.write(item + "\n");
            }
            writer.close();
            needsToBeSaved = false;
            currentFileName = fileName;
            System.out.println("List saved successfully to " + fileName);
        } catch (IOException e) {
            System.out.println("Error occurred while writing to the file: " + e.getMessage());
        }
    }

    // Method to clear the list
    private static void clearList(ArrayList<String> list) {
        if (!list.isEmpty()) {
            if (confirmClearList()) {
                list.clear();
                needsToBeSaved = true;
                System.out.println("List cleared successfully.");
            }
        } else {
            System.out.println("The list is already empty.");
        }
    }

    // Method to confirm clearing the list
    private static boolean confirmClearList() {
        Scanner scanner = new Scanner(System.in);
        return SafeInput.getYNConfirm(scanner, "Are you sure you want to clear the list? (Y/N)");
    }

    // Method to confirm saving before an action
    private static boolean confirmSaveBeforeAction(Scanner scanner, String action) {
        return SafeInput.getYNConfirm(scanner, "You have unsaved changes. Do you want to save before " + action + "? (Y/N)");
    }

    // Method to prompt the user before quitting the program
    private static void quitProgram(ArrayList<String> list, Scanner scanner) {
        if (needsToBeSaved && !confirmSaveBeforeAction(scanner, "quitting")) {
            return;
        }
        System.out.println("Exiting the program...");
    }
}

