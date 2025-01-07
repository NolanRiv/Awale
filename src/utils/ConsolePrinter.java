package utils;

public class ConsolePrinter {

    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String BLUE = "\u001B[34m";

    public static void printMessage(String message) {
        System.out.println(message);
    }

    public static void printInstructions() {
        System.out.println("Welcome to the Awalé Game!");
        System.out.println("Instructions:");
        System.out.println("1. The board has 16 holes, each starting with " + RED + "2 red" + RESET + " and " + BLUE + "2 blue" + RESET + " seeds.");
        System.out.println("2. Players alternate turns, sowing seeds and capturing them based on the rules.");
        System.out.println("3. Enter moves in the format: <HoleNumber><SeedColor> (e.g., 3R for " + RED + "red" + RESET + " seeds in hole 3).");
        System.out.println("4. The game ends when there are fewer than 8 seeds remaining, or a player wins.");
        System.out.println("5. Player 1 controls even-indexed holes, Player 2 controls odd-indexed holes.");
        System.out.println("6. Captures occur when a hole ends with exactly 2 or 3 seeds.");
        System.out.println("7. The player with the most captured seeds wins!");
        System.out.println();
    }

    public static void printError(String errorMessage) {
        System.err.println("Error: " + errorMessage);
    }

    public static void printBoard(int[][] boardState, int player1Score, int player2Score) {
        System.out.println("Current Board State:");
        for (int i = 0; i < boardState.length; i++) {
            String redSeeds = RED + boardState[i][0] + RESET;
            String blueSeeds = BLUE + boardState[i][1] + RESET;

            System.out.print("H" + (i + 1) + " [" + redSeeds + "R," + blueSeeds + "B] | ");
            if ((i + 1) % 4 == 0) {
                System.out.println();
            }
        }
        System.out.println();
        System.out.println("Scores: " + "Player 1: " + player1Score + RESET + ", " + "Player 2: " + player2Score + RESET);
        System.out.println();
    }

    public static void printFinalScores(int player1Score, int player2Score) {
        System.out.println("Final Scores:");
        System.out.println(RED + "Player 1: " + player1Score + RESET);
        System.out.println(BLUE + "Player 2: " + player2Score + RESET);
    }

    public static void printWinner(int winner) {
        if (winner == 0) {
            System.out.println("The game ended in a tie!");
        } else {
            System.out.println("Player " + winner + " wins!");
        }
    }
}