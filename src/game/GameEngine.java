package game;

import utils.ConsolePrinter;
import utils.InputValidator;

public class GameEngine {
    
    private Board board;
    private Player player1;
    private Player player2;
    private int currentPlayerTurn;

    public GameEngine() {
        this.board = new Board();
        this.player1 = new Player(0, false);
        this.player2 = new Player(1, false);
        this.currentPlayerTurn = 0;
    }

    public void startGame() {
        ConsolePrinter.printInstructions();
        setupPlayers();

        while (!isGameOver()) {
            ConsolePrinter.printBoard(board.getHoles(), board.getPlayerScore(0), board.getPlayerScore(1));

            Player currentPlayer = (currentPlayerTurn == 0) ? player1 : player2;
            String move;

            if (currentPlayer.isAI()) {
                ConsolePrinter.printMessage("AI is making its move...");
                move = currentPlayer.playTurn(board);
                ConsolePrinter.printMessage("AI chose: " + move);
            } else {
                ConsolePrinter.printMessage("Player " + (currentPlayerTurn + 1) + "'s turn.");
                move = InputValidator.getValidMove(board, currentPlayerTurn);
            }

            board.processMove(move, currentPlayerTurn);
            currentPlayerTurn = 1 - currentPlayerTurn;
        }

        determineWinner();
    }

    private void setupPlayers() {
        ConsolePrinter.printMessage("Choose your role: ");
        boolean isPlayer1AI = InputValidator.getBooleanInput("Should Player 1 be AI? (yes/no): ");
        boolean isPlayer2AI = InputValidator.getBooleanInput("Should Player 2 be AI? (yes/no): ");
        player1.setAI(isPlayer1AI);
        player2.setAI(isPlayer2AI);
    }

    private boolean isGameOver() {
        if (board.getTotalSeeds() < 8) {
            ConsolePrinter.printMessage("Game over: Less than 8 seeds remaining.");
            return true;
        }

        if (board.hasWinner()) {
            return true;
        }

        if (!board.canPlayerPlay(currentPlayerTurn)) {
            int opponentId = 1 - currentPlayerTurn;
            ConsolePrinter.printMessage("Starvation: Player " + (currentPlayerTurn + 1) + " cannot play.");
            board.collectAllSeeds(opponentId);
            return true;
        }

        return false;
    }

    private void determineWinner() {
        int player1Score = board.getPlayerScore(0);
        int player2Score = board.getPlayerScore(1);

        ConsolePrinter.printMessage("Final Scores:");
        ConsolePrinter.printMessage("Player 1: " + player1Score);
        ConsolePrinter.printMessage("Player 2: " + player2Score);

        if (player1Score > player2Score) {
            ConsolePrinter.printMessage("Player 1 wins!");
        } else if (player2Score > player1Score) {
            ConsolePrinter.printMessage("Player 2 wins!");
        } else {
            ConsolePrinter.printMessage("It's a tie!");
        }
    }
}