package utils;

import game.Board;
import game.Rules;

import java.util.Scanner;

public class InputValidator {
    private static final Scanner scanner = new Scanner(System.in);

    public static String getValidMove(Board board, int playerTurn) {
        String move;

        while (true) {
            System.out.print("Enter your move (e.g., 3R): ");
            move = scanner.nextLine().trim();

            if (Rules.isValidMove(move, playerTurn, board)) {
                break;
            } else {
                ConsolePrinter.printError("Invalid move. Please try again.");
            }
        }

        return move;
    }

    public static boolean getBooleanInput(String message) {
        String response;

        while (true) {
            System.out.print(message);
            response = scanner.nextLine().trim().toLowerCase();

            if (response.equals("yes")) {
                return true;
            } else if (response.equals("no")) {
                return false;
            } else {
                ConsolePrinter.printError("Invalid input. Please enter 'yes' or 'no'.");
            }
        }
    }

    public static int getIntInput(String message, int min, int max) {
        int input;

        while (true) {
            System.out.print(message);

            try {
                input = Integer.parseInt(scanner.nextLine().trim());

                if (input >= min && input <= max) {
                    return input;
                } else {
                    ConsolePrinter.printError("Input must be between " + min + " and " + max + ".");
                }
            } catch (NumberFormatException e) {
                ConsolePrinter.printError("Invalid input. Please enter a number.");
            }
        }
    }
}